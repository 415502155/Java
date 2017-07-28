package com.bestinfo.controller.clpdata;

import com.bestinfo.bean.clpdata.TmnClpKey;
import static com.bestinfo.controller.clpdata.ReadWriteFile.readFile;
import static com.bestinfo.controller.clpdata.ReadWriteFile.writeFile;
import com.bestinfo.service.clpdata.ITmnClpKeySer;
import com.bestinfo.service.impl.clpdata.TmnClpKeySerImpl;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import javax.annotation.Resource;
import net.sf.json.JSONObject;
import org.apache.log4j.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;


/**
 *
 * @author Administrator
 */
public class TransmissionThread implements Runnable{
    private final Logger logger = Logger.getLogger(TransmissionThread.class);
    private String name;
    Integer game_id;
    Integer draw_id;
    Integer cs=0;
    int j=100;
    private volatile int i=0;
    public void setGameId(Integer game_id) {
        this.game_id = game_id;
    } 
    public void setDrawId(Integer draw_id){
        this.draw_id=draw_id;
    }
    public void run() {
        //TmnClpKeySerImpl tmnclpkeyser = SpringContextUtil.getBean("tmnClpKeySerImpl");
        //List<TmnClpKey> tckList=tmnclpkeyser.tmnClpkeyList();
        if(cs==0){
            //cs++;
            //int status=gdis.getGameDrawInfoStatus(game_id, draw_id);
        int status=500;
        if(status==500){
            int maxSequenceId=251;
            String MaxSequenceIdString=String.valueOf(maxSequenceId);
            writeFile(MaxSequenceIdString);
            int count=maxSequenceId;
            int xcs=0;
            if(count==0){
                System.out.println("without more data!");
            }else{
                int ys=count%50;
                if(ys==0){
                    xcs=count/50;
                }else{
                    xcs=count/50+1;
                }
            }
            CountDownLatch latch = new CountDownLatch(xcs) ;
            JSONObject jsonobj = new JSONObject();
            logger.info("create thread num :"+xcs);
            for (int i = 0; i < xcs; ++i)
            {
                SplitTacketBetThread sb =new SplitTacketBetThread(i);
                sb.setDrawId(50*i+50);
                sb.setGameId(50*i);
                Thread thread =new Thread(sb);
                thread.start();                
                //String dataString="name:admin";
                //writeFile1(dataString);
                //writeFile(result);
                
                //new Thread(new SplitTacketBetThread(i)).start(); 
                //latch.await();
                //latch.countDown();
            }
        }
        String lists=ReadWriteFile.readFile1();
        System.out.println("lists:"+lists);
    }else{
            System.out.println("else    ");
             String minSequenceIdString=readFile();  
             int minSequenceId =Integer.parseInt(minSequenceIdString);
            try {
                Thread.sleep(10000);
                //int maxSequenceId=ttfs.getTicketBetInfoMaxSequenceId(game_id, draw_id);
                int maxSequenceId=8250;
                String MaxSequenceIdString=String.valueOf(maxSequenceId);
                writeFile(MaxSequenceIdString);
                int count=maxSequenceId-minSequenceId;
            } catch (InterruptedException ex) {
            }
        }
} 

    public static void main(String [] args){
        ApplicationContext ctx = new ClassPathXmlApplicationContext("applicationContext.xml");
        //InitializeBean.setApplicationContext(ctx); 
        ReadWriteFile.delFile1();
        TransmissionThread tt = new TransmissionThread();
        tt.setDrawId(30);
        tt.setGameId(5);
        Thread t = new Thread(tt); 
        t.start();
    }
}
