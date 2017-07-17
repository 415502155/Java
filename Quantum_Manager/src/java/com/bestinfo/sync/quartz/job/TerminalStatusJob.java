package com.bestinfo.sync.quartz.job;

import com.bestinfo.bean.business.TmnInfo;
import com.bestinfo.bean.terminal.SyncTmnRank;
import com.bestinfo.redis.business.TmnInfoRedisCache;
import com.bestinfo.service.terminal.ITmnStatusService;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;
import javax.annotation.Resource;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

@Component
public class TerminalStatusJob {

    private static final Logger logger = Logger.getLogger(TerminalStatusJob.class);
    @Resource
    private ITmnStatusService tmnStatusService;    
    @Resource
    private TmnInfoRedisCache tmnInfoRedisCache;
    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
    private static final SimpleDateFormat dateFormatday = new SimpleDateFormat("yyyy-MM-dd");
    public synchronized void masterThread() throws FileNotFoundException, IOException, ParseException {
        String date = dateFormat.format(new Date());//获取执行该方法的当前时间
        long current = System.currentTimeMillis();//当前时间毫秒数
        SimpleDateFormat formater = new SimpleDateFormat("yyyy/MM/dd");
        SimpleDateFormat formater2 = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");	         

        Date zero = formater2.parse(formater.format(new Date())+ " 00:00:00");
        Date zero_30 = formater2.parse(formater.format(new Date())+ " 00:30:00");        
        Date eleven_30 = formater2.parse(formater.format(new Date())+ " 23:00:00");
        Date twelve = formater2.parse(formater.format(new Date())+ " 23:59:59");
        long zeros = zero.getTime();
        long zeros_30 = zero_30.getTime();
        long elevens_30 = eleven_30.getTime();
        long twelves = twelve.getTime();
        
        String beginTime = dateFormatday.format(new Date());//获取执行该方法的当前时间
        //通过日历获取下一天日期  
        Calendar cal = Calendar.getInstance();  
        cal.setTime(dateFormatday.parse(beginTime));  
        cal.add(Calendar.DAY_OF_YEAR, +1);  
        String endTime = dateFormatday.format(cal.getTime()); 
        if(current <= twelves && current >= elevens_30){//判断当前时间戳大于23:30小于等于23:59:59
            logger.info("task 1:starting................................");
            try {
                int executeStatus = 0;
                int tmnStatus = 0;
                //查询是否有符合条件的计划
                List<SyncTmnRank> strList = tmnStatusService.getSyncTmnRanks(beginTime, endTime, executeStatus);            
                if(strList == null || strList.isEmpty()){
                    logger.info("strList is null, There are no qualified plans to carry out");
                    return ;
                }else{
                    //查询要修改星级的终端列表
                    List<TmnInfo> tiList = tmnStatusService.getTmnInfos(beginTime, endTime);
                    if(tiList == null || tiList.isEmpty()){
                       logger.info("tiList is null");
                        return ; 
                    }
                    tmnStatus = 2;
                    //修改终端状态为2（暂停）
                    for(TmnInfo ti : tiList){
                        ti.setTerminal_status(tmnStatus);
                    }
                    int ts = tmnStatusService.batchUpdateTmnStatus(tiList);
                    if(ts < 0){
                        logger.info("batch update tmn status to upgrade from DB failed.");
                        return;
                    }
                    //更新Redis
                    int re1 = tmnInfoRedisCache.batchAddTmnList(tiList);
                    if (re1 < 0) {
                        logger.error("batch update tmn upgrade mark to upgrade from Redis failed.");
                        return ;
                    }
                    executeStatus = 1;
                    //把查出的list中的execut_status设置为1
                    for(SyncTmnRank str : strList){
                        str.setExecute_status(executeStatus);
                    }                     
                    int re = tmnStatusService.batchUpdateTmnExecuteStatus(strList);
                    if (re < 0) {
                        logger.info("batch update tmn execute status to upgrade from DB failed.");
                        return;
                    }
                    logger.info("set execute status is :"+executeStatus);
                }

            } catch (Exception e) {
                logger.info("TerminalStatusJob quartz task 1 ex:",e);
            }
        }else if(current >= zeros && current < zeros_30 ){//00:00:00  ----  00:30:00
            logger.info("task 2:starting................................");
            try {
                int executeStatus = 0;
                int tmnStatus = 0;
                //查询是否有符合条件的计划
                executeStatus = 1 ;
                List<SyncTmnRank> strList = tmnStatusService.getCurDaySyncTmnRanks(beginTime, endTime, executeStatus);            
                if(strList == null || strList.isEmpty()){
                    logger.info("strList is null, There are no qualified plans to carry out");
                    return ;
                }else{
                    //修改终端星级
                    int bustr = tmnStatusService.batchUpdateTmnStationRank(strList);
                    if(bustr < 0){
                        logger.info("batch update tmn station rank to upgrade from DB failed.");
                        return;
                    }
                    logger.info("update tmn station rank count:"+bustr);
                    //查询要修改星级的终端列表
                    List<TmnInfo> tiList = tmnStatusService.getCurDayTmnInfos(beginTime, endTime);
                    if(tiList == null || tiList.isEmpty()){
                       logger.info("tiList is null");
                        return ; 
                    }
                    //修改终端状态为0
                    for(TmnInfo ti : tiList){
                        ti.setTerminal_status(tmnStatus);
                    }
                    int ts = tmnStatusService.batchUpdateTmnStatus(tiList);
                    if(ts < 0){
                        logger.info("batch update tmn status to upgrade from DB failed.");
                        return;
                    }
                    //更新Redis
                    int re1 = tmnInfoRedisCache.batchAddTmnList(tiList);
                    if (re1 < 0) {
                        logger.error("batch update tmn upgrade mark to upgrade from Redis failed.");
                        return ;
                    }
                    logger.error("batch update tmn upgrade mark to upgrade from Redis success.");
                    executeStatus = 2;
                    //把查出的list中的execut_status设置为2
                    for(SyncTmnRank str : strList){
                        str.setExecute_status(executeStatus);
                    } 
                    int re = tmnStatusService.batchUpdateTmnExecuteStatus(strList);                    
                    if (re < 0) {
                        logger.info("batch update tmn execute status to upgrade from DB failed.");
                        return;
                    }
                    logger.info("update execut_status success");
                }

            } catch (Exception e) {
                logger.info("TerminalStatusJob2 quartz task 2 ex:",e);
            }
        }else{
            logger.info("current time is:"+date+",Do not perform task");
        }
    }
}
