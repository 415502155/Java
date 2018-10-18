/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bestinfo.controller.clpdata;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.bestinfo.bean.ticket.TicketBet;
import com.bestinfo.service.clpdata.IGameDrawInfoSer;
import com.bestinfo.service.clpdata.ITicketBetInfoSer;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 *
 * @author Administrator
 */
@Controller
@RequestMapping(value = "/ticket")
public class TacketBetInfoCtr {
     private final Logger logger = Logger.getLogger(TacketBetInfoCtr.class);

    @Resource
    private ITicketBetInfoSer ttfs;
    @Resource
    private IGameDrawInfoSer gdis;
    private static int state = 0;
    private int ts=5;
    Integer game_id=1;
    Integer draw_id=30;
    @RequestMapping(value = "/demo",method = RequestMethod.GET)
    @ResponseBody
    public String ticketBet() {
        logger.info("*****************************   kkkkkkk    *********************************************");
        ReadWriteFile.delFile1();
        TransmissionThread tt = new TransmissionThread();
        tt.setDrawId(30);
        tt.setGameId(5);
        Thread t = new Thread(tt); 
        t.start();       
        return "success";
    }
    @RequestMapping(value = "/demo1",method = RequestMethod.GET)
    @ResponseBody
    public String TestThreadPoolExecutor(){
        //ExecutorService executor=CreateThreadPool.newCachedThreadPool();
        ThreadPoolExecutor executor = new ThreadPoolExecutor(5, 5, 200, TimeUnit.MILLISECONDS,
                 new ArrayBlockingQueue<Runnable>(50));
        List<TicketBet> list=ttfs.getTicketBetsList(game_id,draw_id);
        if(list.size()==0){
            logger.info("list is null !");
            return "error";
        }
        int threadNnm=0;
        int num =list.size();
        int sh=5;
        threadNnm=num/sh;   
        if(num%100!=0){
            threadNnm+=1;
        }
        logger.info("ThreadNum:"+threadNnm+".list num:"+num);
        for(int i=0;i<threadNnm;i++){
             MyTask myTask = new MyTask(i);
             myTask.setStart(i*sh);
             myTask.setEnd((i+1)*sh+1);
             executor.execute(myTask);             
             System.out.println("线程池中线程数目："+executor.getPoolSize()+"，队列中等待执行的任务数目："+
             executor.getQueue().size()+"，已执行完别的任务数目："+executor.getCompletedTaskCount());
             if(executor.getQueue().size() == 50){
                 
             }
         }
         executor.shutdown();
        return "succes";
    }
    @RequestMapping(value = "/datatransmission",method = RequestMethod.POST)
    @ResponseBody
    public String tacketBetDataTransmission() {
        Thread  A = new Thread(new Runnable(){  
            @Override
            public void run() {
                        int ys=state+1;                        
                        int ends=ys*ts;
                        int starts=state*ts+1;                       
                        String resultStringA="";  
                        try {
                            List<TicketBet> list=ttfs.getTicketInfoPage(game_id,draw_id,ends,starts);
                            resultStringA=JSONArray.toJSONString(list);
                            String keyString=String.valueOf(state);
                        } catch (Exception e) {
                            //e.printStackTrace();
                        }
            }
        });  
        return "";
    }
    /**
     * queryall
     * @param response
     * @param USER_NAME
     * @param PWD
     * @param TYPE
     * @param LOGIN_CODE
     * @return 
     */
    @RequestMapping(value = "/angularpost",method = RequestMethod.POST)
    @ResponseBody
    public String angularPost(HttpServletResponse response ,String USER_NAME ,String PWD,String TYPE,String LOGIN_CODE) {
        /**设置响应头允许ajax跨域访问**/
        response.setHeader("Access-Control-Allow-Origin","*");//'Access-Control-Allow-Origin:*'
        /*星号表示所有的异域请求都可以接受，*/
        response.setHeader("Access-Control-Allow-Methods","GET,POST");
        HttpServletRequest request=null;
        //logger.info("PWD:"+request.getParameter("PWD"));
        logger.info("jin lai le !");
        logger.info("username:"+USER_NAME);
        logger.info("password:"+PWD);
        logger.info("TYPE:"+TYPE);
        logger.info("LOGIN_CODE:"+LOGIN_CODE);
        //String username=REQ.get("USER_NAME").toString();
        //logger.info("username:"+username);
        //logger.info("map:"+REQ);
         //logger.info(request.getParameter("USER_NAME")+"aaaa:");
        Map map = new LinkedHashMap();
        map.put("status", 0);
        map.put("msg", "Request success");
        String result=JSONArray.toJSONString(map);
        return result;
    }
     @RequestMapping(value = "/queryall")
    @ResponseBody
    public String queryInfo(HttpServletRequest request) {   //  
        //Integer game_id=Integer.parseInt(request.getParameter("game_id"));
        //Integer draw_id=Integer.parseInt(request.getParameter("draw_id"));
        Integer game_id=1;
        Integer draw_id=30;
        logger.info("jin lai le !");
        String resultString="";  
        try {
             List<TicketBet> list=ttfs.getTicketBetsList(game_id,draw_id);
             logger.info("list >>>>:"+list);
             resultString=JSONArray.toJSONString(list);
        } catch (Exception e) {
            logger.error("query TicketBet list info error,error info(" + e.getMessage() + ")!");
            //e.printStackTrace();
        }       
        return resultString;
    }
    @RequestMapping(value = "/querypage")
    @ResponseBody
    public String queryPageInfo(HttpServletRequest request) {   //  
         final Lock l = new ReentrantLock();  
         final StringBuilder sb = null;
         HttpSession session=null;
         List <Map> list = new LinkedList<Map>();
         Map map=null;
         //logger.info(game_id+"sssssssssssss"+draw_id);
         int zts =ttfs.getTicketInfoCount(game_id, draw_id);
         //logger.info("总条数："+zts);
         final int cs =zts/ts+1;
         //logger.info("循环次数："+cs);
        Thread  A = new Thread(new Runnable(){  
            @Override
            public void run() {
                while (state<=cs) {
                    l.lock();
                    if(state%3==0){
                        System.out.println("A");  
                        //state ++;   
                        int ys=state+1;                        
                        int ends=ys*ts;
                        int starts=state*ts+1;                       
                        String resultStringA="";  
                        try {
                            logger.info("A 111111111111111111111111");
                            List<TicketBet> list=ttfs.getTicketInfoPage(game_id,draw_id,ends,starts);
                            resultStringA=JSONArray.toJSONString(list);
                            logger.info("A 222222222222222222222222");
                            //sb.append(resultStringA);
                            String keyString=String.valueOf(state);
                            logger.info("A 333333333333333333333333");
                            //session.setAttribute(keyString, resultStringA);
                            logger.info("resultStringAAAAAAAAA:"+resultStringA);
                            logger.info("sb:"+resultStringA);
                            System.out.println("123456789");
                            System.out.println("resultStringAAAAAAAAA:"+resultStringA);
                            System.out.println("sb:"+resultStringA);
                        } catch (Exception e) {
                            //e.printStackTrace();
                        }
                        state ++; 
                    }
                    l.unlock();
                }
            }
        });  
        Thread B = new Thread(new Runnable(){  
            @Override  
            public void run() {  
                while (state<=cs) {  
                    l.lock();  
                    if(state%3==1){  
                        System.out.println("B");  
                        logger.info("B");
                        //state ++;
                         int ys=state+1;                        
                        int ends=ys*ts;
                        int starts=state*ts+1;                       
                        String resultStringB="";  
                        try {
                            List<TicketBet> list=ttfs.getTicketInfoPage(game_id,draw_id,ends,starts);
                            resultStringB=JSONArray.toJSONString(list);
                            //sb.append(resultStringB);
                            logger.info("resultStringBBBBBBBBB:"+resultStringB);
                            logger.info("sb B:"+resultStringB);
                        } catch (Exception e) {
                            //e.printStackTrace();
                        }
                        state ++; 
                    }  
                    l.unlock();  
                }  
            }  
        });  
        Thread C = new Thread(new Runnable(){  
            @Override  
            public void run() {  
                while (state<=cs) {  
                    l.lock();  
                    if(state%3==2){  
                        System.out.println("C");  
                        logger.info("C");
                        //state ++; 
                         int ys=state+1;                        
                        int ends=ys*ts;
                        int starts=state*ts+1;                       
                        String resultStringC="";  
                        try {
                            List<TicketBet> list=ttfs.getTicketInfoPage(game_id,draw_id,ends,starts);
                            resultStringC=JSONArray.toJSONString(list);
                            //sb.append(resultStringC);
                            logger.info("resultStringCCCCCC:"+resultStringC);
                            logger.info("sb C:"+resultStringC);
                        } catch (Exception e) {
                            //e.printStackTrace();
                        }
                        state ++; 
                    }  
                    l.unlock();  
                }  
            }  
        });  
        A.start();  
        B.start();  
        C.start();  
        //System.out.println("sb....:"+sb);
        //String resString =JSONObject.toJSONString(sb);
        String resString=JSONObject.toJSONString(sb);
        return "success";
    }
}
