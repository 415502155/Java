package com.bestinfo.gambler.completedata;

import com.bestinfo.arithmetic.NewSign;
import com.bestinfo.gambler.all.HttpUtil;
import com.bestinfo.gambler.all.StaticFile;
import com.bestinfo.gambler.all.StaticVariable;
import com.bestinfo.gambler.createBetNumber.SerialNo;
import com.bestinfo.gambler.protocols.ActionID;
import static com.bestinfo.gambler.swing.NewJFrame.generateXML;
import com.bestinfo.protocols.bet.PBetSchemeResponse;
import com.bestinfo.protocols.message.APPMessage;
import com.bestinfo.protocols.message.AppHeader;
import com.bestinfo.protocols.xml.client.XmlFactoryClient;
import com.bestinfo.util.FileUtil;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.atomic.AtomicInteger;
import org.apache.log4j.Logger;

/**
 *
 * 电话投注线下灌票数据到数据库
 */
public class SplitTacketBetThread implements Runnable {

    private final Logger logger = Logger.getLogger(SplitTacketBetThread.class);
    AtomicInteger failedatomicInteger = new AtomicInteger(0);
    AtomicInteger successatomicInteger = new AtomicInteger(0);
    private Integer start;
    private Integer end;
    private String xml;
    private String dealerId;
    //private AppHeader header;
    private int threadId;
    private volatile int j = 0;
    private volatile int k = 0;
    private int successnum = 0;
    private int failednum = 0;

    public SplitTacketBetThread(int id) {
        this.threadId = id;
    }

    public synchronized void incSucNum() {
        successnum++;
    }

    public synchronized void incFailedNum() {
        failednum++;
    }

    public SplitTacketBetThread() {
    }

    @Override
    public synchronized void run() {
        AppHeader header = new AppHeader();
        header.setType(3);
        header.setAction(ActionID.SCHEMES_BET_SH);
        header.setVersion(0);
        header.setDealer_id(dealerId);
        header.setTerminal_id(100);
        header.setMobile("15101105612");
        header.setPhone("15101105612");
        header.setSent_time(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
        logger.info("start:" + start + "..end:" + end);
        PBetSchemeResponse tbrr = null;
        int resultcode = -1;
        StringBuffer sb = new StringBuffer();
        StringBuffer sbLog = new StringBuffer();
        String[] arrxml = xml.split(",");
        int arrlen = arrxml.length;
        System.err.println("arrlen:" + arrlen);
        File dir = new File("");
        String path = dir.getAbsolutePath();
        String FailedFilePath = path + "\\log" + "\\Fail_" + StaticFile.dealerName + "_" + StaticFile.gameName + "_" + StaticFile.drawName + ".xml";
        String ErrorFilePath = path + "\\log" + "\\Error_" + StaticFile.dealerName + "_" + StaticFile.gameName + "_" + StaticFile.drawName + ".log";
        logger.info("FailedFilePath:" + FailedFilePath);
        logger.info("ErrorFilePath" + ErrorFilePath);
        String header1 = generateXML(header);//要去掉先去</pkg>最后再加
        String header2 = header1.substring(0, header1.length() - 6);
        Thread current = Thread.currentThread();
        //try {
        for (int i = start; i < end; i++) {
            try {
                String requestxml = "";
                byte[] ret_key;
                if (i > (arrlen - 1)) {
                    logger.info("equals:" + i + "==" + arrlen);
                    i = end;
                    //return;
                } else {
                    requestxml = header2 + arrxml[i] + "</pkg>";//header未加签名的请求xml                    
                    ret_key = NewSign.GetSign(requestxml, StaticVariable.privateKey, StaticVariable.SESSION);
                    header.setBody_sign(ret_key);
                    String hearders = generateXML(header);//添加签名的header
                    String reqCs = arrxml[i];
                    String requestxmls = hearders.substring(0, hearders.length() - 6) + arrxml[i] + "</pkg>";//header加签名的请求xml
                    requestxmls = SerialNo.getxml(requestxmls, ActionID.SCHEMES_BET_SH);
                    String responsexml = HttpUtil.httpSend(requestxmls, StaticVariable.SERVERURL, false);
                    if (responsexml == null) {
                        System.out.println("responsexml is null");
                    }
                    APPMessage ap = XmlFactoryClient.getInstance().getAppClientXF(ActionID.SCHEMES_BET).parseXML(responsexml);
                    if (ap == null) {
                        logger.info("ap is null,ActionID.SCHEMES_BET is null");
                        return;
                    }
                    tbrr = (PBetSchemeResponse) ap.getContent();
                    resultcode = tbrr.getResult().getResultCode();
                    if (resultcode != 0) {
                        incFailedNum();
                        failedatomicInteger.getAndIncrement();
                        logger.error("ERROR LOG:rescode:" + resultcode + "," + "\\successnum:" + successatomicInteger + "," + "\failednum:" + failedatomicInteger + "," + "\tresultDes:" + tbrr.getResult().getResultDes() + "," + "\txml:" + requestxmls);
                        String jd = arrxml[i] + ",";
                        sb.append(jd);
                        System.err.println("sbXml123:" + sb);
                        String log = "rescode:" + resultcode + "," + "\tresultDes:" + tbrr.getResult().getResultDes() + "," + "\txml:" + jd;
                        sbLog.append(log);
                        System.err.println("sbLog123:" + sbLog);
                    } else {
                        incSucNum();
                        successatomicInteger.getAndIncrement();
                        logger.info("SUCCESS LOG:rescode:" + resultcode + "\tresultDes:" + tbrr.getResult().getResultDes() + "successnum:" + successatomicInteger + "\failednum:" + failedatomicInteger + "\txml:" + requestxmls);
                    }
                }
            } catch (Exception ex) {
                logger.error("有异常", ex);
            }

        }
        logger.info("..................xxx..................................");
        if (sb.length() != 0) {//投注失败的请求xml
            try {
                logger.info("..........................sb");
                String FailedAllRequest = sb.substring(0, sb.length() - 1);
                logger.info("FailedAllRequest:" + FailedAllRequest);
                FileUtil.fileAppend(FailedFilePath, FailedAllRequest);
            } catch (Exception ex) {
                logger.info("fileAppend failed content exception!" + ex);
            }
        }
        logger.info("..................yyy..................................");
        if (sbLog.length() != 0) {//投注失败的log               
            try {
                logger.info("..........................sbLog");
                String ErrorLog = sbLog.toString().substring(0, sbLog.toString().length() - 1);
                logger.info("ErrorLog:" + ErrorLog);
                FileUtil.fileAppend(ErrorFilePath, ErrorLog);
            } catch (Exception ex) {
                logger.info("fileAppend error log content exception!" + ex);
            }
        }
        logger.info("..................zzz..................................");
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
