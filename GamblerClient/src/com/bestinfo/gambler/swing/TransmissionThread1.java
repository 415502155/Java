package com.bestinfo.gambler.swing;

import com.bestinfo.protocols.message.AppHeader;
import java.util.concurrent.CountDownLatch;
import org.apache.log4j.Logger;



/**
 *
 * @author Administrator
 */
public class TransmissionThread1 implements Runnable{
    private final Logger logger = Logger.getLogger(TransmissionThread1.class);
    private Integer start;
    private Integer end;
    private Integer total;
    private String xml;
    private AppHeader header;
    Integer cs=0;
    int j=100;
    int num=20;
    public void run() {
            int count=total;
            int xcs=0;
            int ys=count%num;
            if(ys==0){
                xcs=count/num;
            }else{
                xcs=count/num+1;
            }
            logger.info("create thread num :"+xcs);
            for (int i = 0; i < xcs; ++i)
            {
                SplitTacketBetThread1 sb =new SplitTacketBetThread1(i);
                sb.setStart(num*i);
                sb.setEnd(num*i+num);
                sb.setXml(xml);
                sb.setHeader(header);
                Thread thread =new Thread(sb);
                thread.start();                
            }
} 

    /**
     * @return the start
     */
    public Integer getStart() {
        return start;
    }

    /**
     * @param start the start to set
     */
    public void setStart(Integer start) {
        this.start = start;
    }

    /**
     * @return the end
     */
    public Integer getEnd() {
        return end;
    }

    /**
     * @param end the end to set
     */
    public void setEnd(Integer end) {
        this.end = end;
    }

    /**
     * @return the total
     */
    public Integer getTotal() {
        return total;
    }

    /**
     * @param total the total to set
     */
    public void setTotal(Integer total) {
        this.total = total;
    }

    /**
     * @return the xml
     */
    public String getXml() {
        return xml;
    }

    /**
     * @param xml the xml to set
     */
    public void setXml(String xml) {
        this.xml = xml;
    }

    /**
     * @return the header
     */
    public AppHeader getHeader() {
        return header;
    }

    /**
     * @param header the header to set
     */
    public void setHeader(AppHeader header) {
        this.header = header;
    }

}
