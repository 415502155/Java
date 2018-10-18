package com.bestinfo.controller.clpdata;

import com.bestinfo.bean.clpdata.TmnClpKey;
import com.bestinfo.service.impl.clpdata.TmnClpKeySerImpl;
import com.bestinfo.util.FileUtil;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import net.sf.json.JSONObject;
import org.apache.log4j.Logger;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Administrator
 */
public class MyTask implements Runnable{
    private final Logger logger = Logger.getLogger(MyTask.class);
    private int start;
    private int end;
    private int taskNum;
    TmnClpKeySerImpl tmnclpkeyser; 
    public MyTask(int num) {
        this.taskNum = num;
    }
     
    @Override
    public void run() {
        System.out.println("正在执行task "+taskNum);
        logger.info(Thread.currentThread().getName()+"_start:"+start+"_end:"+end);
        JSONObject backJson=new JSONObject();       
        StringBuffer sb=new StringBuffer();
        try {
            TmnClpKeySerImpl tmnclpkeyser1 = SpringContextUtil.getBean("tmnClpKeySerImpl");  
            List<TmnClpKey> tckList=tmnclpkeyser1.getTmnClpkeyListByTms(start,end);           
            if(tckList==null||tckList.isEmpty()){
                logger.info("tckList is null"); 
            }else{                
                for(TmnClpKey tmnClpKey:tckList){
                    String terminalId=tmnClpKey.getTerminal_id().toString()+"|";
                    sb.append(terminalId);
                }
                //logger.info("tckList len:"+tckList.size());                        
                //backJson.put("list", tckList);
                
                //String result=sb.substring(0, sb.length()-1);
                String result =sb.toString();
                logger.info("result："+result);
                SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
                String time = sf.format(new Date());
                String year = time.substring(0, 4);
                String month = time.substring(5, 7);
                String day = time.substring(8, time.length());
                String timefile =year+'-'+month+'-'+day;
                String filename="e:\\"+timefile+"\\test.txt";
                logger.info("filename:"+filename);
                try {
                    int isSuccess=FileUtil.fileAppend(filename, result);
                    if(isSuccess==0){
                        logger.info("fileAppend is success!");
                    }else{
                        logger.info("fileAppend is failed!");
                    }
                } catch (Exception e) {
                    logger.error("exception e_", e);
                }

            }
            Thread.currentThread().sleep(10);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("task "+taskNum+"执行完毕");
    }

    /**
     * @return the start
     */
    public int getStart() {
        return start;
    }

    /**
     * @param start the start to set
     */
    public void setStart(int start) {
        this.start = start;
    }

    /**
     * @return the end
     */
    public int getEnd() {
        return end;
    }

    /**
     * @param end the end to set
     */
    public void setEnd(int end) {
        this.end = end;
    }
}
