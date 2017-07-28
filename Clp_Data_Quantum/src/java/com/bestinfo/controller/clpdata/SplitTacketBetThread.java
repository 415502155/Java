package com.bestinfo.controller.clpdata;

import com.bestinfo.bean.clpdata.TmnClpKey;
import com.bestinfo.bean.ticket.TicketBet;
import com.bestinfo.service.impl.clpdata.TicketBetInfoSerImpl;
import com.bestinfo.service.impl.clpdata.TmnClpKeySerImpl;
import java.util.List;
import java.util.Random;
import net.sf.json.JSONObject;
import org.apache.log4j.Logger;

/**
 *
 * @author Administrator
 */
public class SplitTacketBetThread implements Runnable{
    private final Logger logger = Logger.getLogger(SplitTacketBetThread.class);
//    @Resource
//    private ITicketBetInfoSer ttfs;
//    @Resource
//    private ITicketInfoDao it;
//    @Resource
//    private JdbcTemplate termJdbcTemplate;
    TmnClpKeySerImpl tmnclpkeyser;
    private String name;
    Integer game_id;
    Integer draw_id;
    private int threadId;  
    int ts=1000;
    private volatile int j=100;
    public SplitTacketBetThread(int id)  
    {  
    this.threadId = id;  
    }  
    public void setGameId(Integer game_id)  
    {  
        this.game_id = game_id;  
    } 
    public void setDrawId(Integer draw_id){
        this.draw_id=draw_id;
    }
    @Override  
    public synchronized void run()   
    {  
        //inc(game_id, draw_id);
        logger.info("game_id:"+game_id+"..draw_id:"+draw_id);
        JSONObject backJson=new JSONObject();             
        try {
            TmnClpKeySerImpl tmnclpkeyser1 = SpringContextUtil.getBean("tmnClpKeySerImpl");  
            List<TmnClpKey> tckList=tmnclpkeyser1.getTmnClpkeyListByTms(game_id,draw_id);
            logger.info("game_id111:"+game_id+"..draw_id111:"+draw_id);
            logger.info("tckList:"+tckList);
            if(tckList==null||tckList.isEmpty()){
                logger.info("tckList is null"); 
            }else{                
                logger.info("tckList len:"+tckList.size());                        
                backJson.put("list", tckList);
                String result=backJson.toString();
                logger.info("result："+result);
                ReadWriteFile.writeFile1(result);
            }

        } catch (Exception e) {
            System.out.println("e:"+e);
        }

    }  
            private synchronized void inc(Integer game_id,Integer draw_id) {  
                JSONObject backJson=new JSONObject();             
                try {
                    TmnClpKeySerImpl tmnclpkeyser1 = SpringContextUtil.getBean("tmnClpKeySerImpl");  
                    List<TmnClpKey> tckList=tmnclpkeyser1.tmnClpkeyList(game_id,draw_id);
                    if(tckList==null||tckList.isEmpty()){
                    
                    }else{
                        backJson.put("list", tckList);
                        String result=backJson.toString();
                        logger.info("******************************："+result);
                        ReadWriteFile.writeFile1(result);
                    }

                } catch (Exception e) {
                    System.out.println("e:"+e);
                }
        }

    public int RanDomNum() {
        int max=20;
        int min=10;
        Random random = new Random();

        int s = random.nextInt(max)%(max-min+1) + min;
        System.out.println(s);
        return s;
    }
  
}
