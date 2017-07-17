package com.bestinfo.sync.quartz.job;

import com.bestinfo.bean.business.TmnInfo;
import com.bestinfo.bean.terminal.SyncTmnRank;
import com.bestinfo.redis.business.TmnInfoRedisCache;
import com.bestinfo.service.terminal.ITmnStatusService;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import javax.annotation.Resource;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

@Component
public class TerminalStatusJob2 {

    private static final Logger logger = Logger.getLogger(TerminalStatusJob2.class);
    @Resource
    private ITmnStatusService tmnStatusService;    
    @Resource
    private TmnInfoRedisCache tmnInfoRedisCache;
    
    private static final SimpleDateFormat dateFormatday = new SimpleDateFormat("yyyy-MM-dd");

    public synchronized void masterThread() throws FileNotFoundException, IOException {
        try {
            int executeStatus = 0;
            int tmnStatus = 0;
            String beginTime = dateFormatday.format(new Date());//获取执行该方法的当前时间
            //通过日历获取下一天日期  
            Calendar cal = Calendar.getInstance();  
            cal.setTime(dateFormatday.parse(beginTime));  
            cal.add(Calendar.DAY_OF_YEAR, +1);  
            String endTime = dateFormatday.format(cal.getTime());  
            //查询是否有符合条件的计划
            executeStatus = 1 ;
            List<SyncTmnRank> strList = tmnStatusService.getCurDaySyncTmnRanks(beginTime, endTime, executeStatus);            
            if(strList == null){
                logger.info("strList is null, There are no qualified plans to carry out");
                return ;
            }else{
                //
                int s = tmnStatusService.batchUpdateTmnStationRank(strList);
                //查询要修改星级的终端列表
                List<TmnInfo> tiList = tmnStatusService.getCurDayTmnInfos(beginTime, endTime);
                if(tiList == null){
                   logger.info("tiList is null");
                    return ; 
                }
                tmnStatus = 0;
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
                executeStatus = 2;
                //把查出的list中的execut_status设置为2
                for(SyncTmnRank str : strList){
                    str.setExecute_status(executeStatus);
                    logger.info("set execute status is :"+str.getExecute_status());
                } 
                int re = tmnStatusService.batchUpdateTmnExecuteStatus(strList);
                if (re < 0) {
                    logger.info("batch update tmn execute status to upgrade from DB failed.");
                    return;
                }
            }

        } catch (Exception e) {
            logger.info("TerminalStatusJob2 quartz ex:",e);
        }
    }
}
