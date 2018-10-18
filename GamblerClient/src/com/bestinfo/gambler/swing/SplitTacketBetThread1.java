package com.bestinfo.gambler.swing;
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
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.logging.Level;
import org.apache.log4j.Logger;

/**
 *
 * @author Administrator
 */
public class SplitTacketBetThread1 implements Runnable{
    private final Logger logger = Logger.getLogger(SplitTacketBetThread1.class);
    AtomicInteger failedatomicInteger = new AtomicInteger(0); 
    AtomicInteger successatomicInteger = new AtomicInteger(0);  
    private Integer start;
    private Integer end;
    private String xml;
    private AppHeader header;
    private int threadId;  
    private volatile int j=0;
    private volatile int k=0;
    private int successnum=0;
    private int failednum=0;
    public SplitTacketBetThread1(int id)  
    {  
    this.threadId = id;  
    }  
    public synchronized void incSucNum() {  
        successnum++;  
    }  
    public synchronized void incFailedNum() {  
        failednum++;  
    }  
    @Override  
    public synchronized void run()   
    {   
        logger.info("start:"+start+"..end:"+end);
        PBetSchemeResponse tbrr =null;
        int resultcode=-1;
        StringBuffer sb = new StringBuffer();
        StringBuffer sbLog = new StringBuffer();
        String[] arrxml = xml.split(",");
        int arrlen=arrxml.length;
        System.err.println("arrlen:"+arrlen);
        File dir = new File("");
        String path = dir.getAbsolutePath();
        String FailedFilePath=path+"\\Fail_"+StaticFile.dealerName+"_"+StaticFile.gameName+"_"+StaticFile.drawName+".xml";
        String ErrorFilePath=path+"\\Error_"+StaticFile.dealerName+"_"+StaticFile.gameName+"_"+StaticFile.drawName+".log";
        String header1=generateXML(header);//要去掉先去</pkg>最后再加
        String header2=header1.substring(0, header1.length()-6);
        Thread current = Thread.currentThread();  
        //try {
            for (int i = start; i < end; i++) {
                String requestxml="";
                byte[] ret_key;
                if(i>=(arrlen-1)){                    
                    logger.info("equals:"+i+"=="+arrlen);
                    return;
                }else{
                    requestxml =header2+arrxml[i]+"</pkg>";//header未加签名的请求xml
                }
                try {
                    ret_key = NewSign.GetSign(requestxml, StaticVariable.privateKey, StaticVariable.SESSION);
                    header.setBody_sign(ret_key);
                    String hearders = generateXML(header);//添加签名的header
                    String reqCs=arrxml[i];
                    System.err.println("reqCs:"+reqCs);
                    String requestxmls=hearders.substring(0, hearders.length()-6)+arrxml[i]+"</pkg>";//header加签名的请求xml
                    requestxmls = SerialNo.getxml(requestxmls, ActionID.SCHEMES_BET_SH);
                    String responsexml = HttpUtil.httpSend(requestxmls, StaticVariable.SERVERURL, false);
                    if (responsexml == null) {
                    System.out.println("responsexml is null");
                    }
                    APPMessage ap = XmlFactoryClient.getInstance().getAppClientXF(ActionID.SCHEMES_BET).parseXML(responsexml);
                    if (ap == null) {
                        logger.info("ap is null,ActionID.SCHEMES_BET is null");
                        return ;
                    }
                    tbrr = (PBetSchemeResponse) ap.getContent();
                    resultcode = tbrr.getResult().getResultCode();
                    if (resultcode != 0) {
                        incFailedNum();
                        failedatomicInteger.getAndIncrement();
                        logger.error("ERROR LOG:rescode:" + resultcode+"successnum:"+successatomicInteger+"\failednum:"+failedatomicInteger + "\tresultDes:" + tbrr.getResult().getResultDes() + "\txml:" + requestxmls);
                        String jd=reqCs+",";
                        sb.append(jd);
                        String log="rescode:" + resultcode+"successnum:"+successnum+"\failednum:"+failednum + "\tresultDes:" + tbrr.getResult().getResultDes() + jd;
                        System.err.println("log:"+log);
                        sbLog.append(log);
                    }else{
                        incSucNum();
                        successatomicInteger.getAndIncrement();
                        logger.info("SUCCESS LOG:rescode:" + resultcode + "\tresultDes:" + tbrr.getResult().getResultDes()+"successnum:"+successatomicInteger+"\failednum:"+failedatomicInteger + "\txml:" + requestxmls);
                    }                                          
                } catch (Exception ex) {
                    java.util.logging.Logger.getLogger(SplitTacketBetThread1.class.getName()).log(Level.SEVERE, null, ex);
                }                                                         
            } 
            logger.info("Current Thread :"+current.getName()+"."+successatomicInteger+"\failednum:"+failedatomicInteger);
            if(sb.length()!=0){//投注失败的请求xml
                    String FailedAllRequest=sb.substring(0, sb.length()-1);      
                    FileOutputStream out = null;
                    try {
                        FileUtil.fileAppend(FailedFilePath, FailedAllRequest);
                    } catch (Exception ex) {
                        logger.info("fileAppend failed content exception!"+ex);
                    }
                }
                if(sbLog.length()!=0){//投注失败的log
                    String ErrorLog=sbLog.substring(0, sb.length()-1);      
                    FileOutputStream out = null;
                    try {
                        FileUtil.fileAppend(ErrorFilePath, ErrorLog);
                    } catch (Exception ex) {
                        logger.info("fileAppend error log content exception!"+ex);
                    }
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
