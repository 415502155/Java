package com.bestinfo.gambler.completedata;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;
import org.apache.log4j.Logger;

/**
 *
 * 电话投注线下灌票数据到数据库
 */
public class TransmissionThread implements Runnable {

    private final Logger logger = Logger.getLogger(TransmissionThread.class);
    private Integer start;
    private Integer end;
    private Integer total;
    private String xml;
    private String dealerId;
    Integer cs = 0;
    int j = 100;
    int num = 1;

    public int getNum() {
        File dir = new File("");
        String path = dir.getAbsolutePath();//获取当前文件运行的目录
        String[] splitpath = path.split("\\\\");
        StringBuffer sbpath = new StringBuffer();
        String gml = sbpath.append(splitpath[0] + "\\").toString();//盘符
        Properties props = new Properties();
        InputStream in = null;
        //String filePath =gml+"gamblerClient\\config\\conf.properties";
        String filePath = path + "\\" + "config\\conf.properties";
        try {//读取属性文件内容
            in = new BufferedInputStream(new FileInputStream(filePath));
            props.load(in);
            num = Integer.parseInt(props.getProperty("num"));
            logger.info("get num:" + num);
            return num;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                in.close();//-----------------------------------important
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        return num;
    }

    public void run() {
        //logger.info("NUM:"+getNum());
        num = getNum();
        int count = total;
        int xcs = 0;
        int ys = count % num;
        if (ys == 0) {
            xcs = count / num;
        } else {
            xcs = count / num + 1;
        }
        logger.info("create thread num :" + xcs);
        for (int i = 0; i < xcs; ++i) {
            SplitTacketBetThread sb = new SplitTacketBetThread(i);
            sb.setStart(num * i);
            sb.setEnd(num * i + num);
            sb.setXml(xml);
            sb.setDealerId(dealerId);
            Thread thread = new Thread(sb);
            thread.start();
        }

    }

    private String transferLongToDate(Long millSec) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = new Date(millSec);
        return sdf.format(date);
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
     * @return the dealerId
     */
    public String getDealerId() {
        return dealerId;
    }

    /**
     * @param dealerId the dealerId to set
     */
    public void setDealerId(String dealerId) {
        this.dealerId = dealerId;
    }

}
