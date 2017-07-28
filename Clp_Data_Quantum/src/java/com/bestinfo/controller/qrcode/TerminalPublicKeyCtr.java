/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.bestinfo.controller.qrcode;

import com.bestinfo.bean.business.TmnInfo;
import com.bestinfo.bean.clpdata.TmnClpKey;
import com.bestinfo.bean.sysUser.SystemInfo;
import static com.bestinfo.controller.qrcode.RsaExample.getSignature;
import static com.bestinfo.controller.qrcode.UkeyAliasId.getKey;
import com.bestinfo.service.clpdata.ISystemInfoSer;
import com.bestinfo.service.clpdata.ITmnClpKeySer;
import com.bestinfo.service.clpdata.ITmnerInfoSer;
import java.io.File;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import org.apache.http.ParseException;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 *
 * @author Administrator
 */
@Controller
@RequestMapping(value = "/publickey")
public class TerminalPublicKeyCtr {
    private final Logger logger = Logger.getLogger(TerminalPublicKeyCtr.class);
    @Resource
    private ITmnerInfoSer tmnser;
    @Resource
    private ISystemInfoSer systemser;
    @Resource
    private ITmnClpKeySer tmnclpkeyser; 
    
    private static final String ukeyPassword = "12345678";//TODO ukey pin码
    private static String ukeyid = "";//TODO 私钥的id号
    @RequestMapping(value = "/scfile")
    @ResponseBody
    public String scTerminalPublicKey(int type,String date) throws Exception {
        System.out.println("type:"+type+"shijian:"+date);
        /***
         * 当type=1时，查询所有的终端clpkey写入文件
         * 当type=2时，需要判断所传的时间是否为空，根据时间来查询当天的所有的clpkey写入文件 
         */
        List<SystemInfo> systemInfoslist =systemser.getISystemInfoList();
        SystemInfo systemInfo=systemInfoslist.get(0);
        Integer Province_id= systemInfo.getProvince_id();
        String provinceidString=String.valueOf(Province_id);
        logger.info("省份id为："+Province_id);
        if(type==1){
            logger.info("input parameters type is :"+type);            
             //List<TmnInfo> tmnInfoslist = tmnser.getITmnInfoList();//去掉t_tmn_info列表查询
             List<TmnClpKey> tckList=tmnclpkeyser.tmnClpkeyList();
             int tms =tmnclpkeyser.getAllTnmClpKeyCount();
             String countkey="";
             String bwcountkey="";
             if(tms!=0){
                 countkey=String.valueOf(tms);
                 int len=countkey.length();
                 if(len<6){
                     int n0=6-len;               
                     bwcountkey=String.format("%1$0"+(6-countkey.length())+"d",0)+countkey;
                     logger.info("bwcountkey:"+bwcountkey);
                 }
             }
             StringBuilder strBuf = new StringBuilder();
             String bwcountkeyjfh =bwcountkey+";";
             strBuf.append(bwcountkeyjfh);
             if(tckList!=null){
                 for(TmnClpKey tcKey:tckList){
                     String terminalidString=String.valueOf(tcKey.getTerminal_id());
                    String bwterminalID="";
                    int terminalLen=terminalidString.length();
                    if(terminalLen<12){
                        int t0=12-terminalLen;  
                        bwterminalID=String.format("%1$0"+(12-terminalidString.length())+"d",0)+terminalidString;
                        logger.info("bwterminalID   bu ling:"+bwterminalID);
                    }
                        String tmnclpkey =tcKey.getPublic_key();
                        String yuan3 =provinceidString+bwterminalID+tmnclpkey;
                        logger.info("ukeyPassword:"+ukeyPassword);
                        ukeyid=getKey(ukeyPassword);//获取uKeyId
                        logger.info("ukeyid:"+ukeyid);
                        String jmptp=getSignature(ukeyPassword,ukeyid,yuan3);//省码+逻辑机号+终端机公钥信息签名
                        logger.info("yuanptp:"+jmptp);
                        String TerminalPublicKey =provinceidString+","+bwterminalID+","+tmnclpkey+","+jmptp+";";
                        strBuf.append(TerminalPublicKey);
                        System.out.println(bwcountkey+","+provinceidString+","+bwterminalID+","+jmptp+";");
                }
             }
             
             String result =strBuf.substring(0, strBuf.length()-1);
             //String  remark256=getSignature(ukeyPassword,ukeyid,result);
             String  remark256=StringEncrytp.Encrypt(result, "");
             logger.info("remark256:"+remark256+"chang du wei :"+remark256.length());
             //StringEncrypt.Encrypt(result, "");
             FileOutputStream out = null;
             FileOutputStream out256 = null;
             SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
             String time = sf.format(new Date());
             String year = time.substring(0, 4);
             String month = time.substring(5, 7);
             String day = time.substring(8, time.length());
             String timefile =year+month+day;
            try {  

                File file =new File("/lottery/"+timefile);    
                //File file =new File("e:/"+timefile);    
                //如果文件夹不存在则创建    
                if  (!file .exists()  && !file .isDirectory())      
                {       
                    System.out.println("//不存在");  
                    file .mkdir();    
                     //out = new FileOutputStream("e:/"+timefile+"/TerminalPublicKey.data"); // 输出公钥记录文件路径  
                     //out256 = new FileOutputStream("e:/"+timefile+"/TerminalPublicKey.data.sha256"); // 输出摘要文件路径  
                    out = new FileOutputStream("/lottery/"+timefile+"/TerminalPublicKey.data"); // 输出公钥记录文件路径  
                    out256 = new FileOutputStream("/lottery/"+timefile+"/TerminalPublicKey.data.sha256"); // 输出摘要文件路径
                } else   
                {  
                    System.out.println("//目录存在"); 
                    out = new FileOutputStream("/lottery/"+timefile+"/TerminalPublicKey.data"); // 输出公钥记录文件路径   
                    out256 = new FileOutputStream("/lottery/"+timefile+"/TerminalPublicKey.data.sha256"); // 输出摘要文件路径   
                      //out = new FileOutputStream("e:/"+timefile+"/TerminalPublicKey.data"); // 输出公钥记录文件路径   
                      //out256 = new FileOutputStream("e:/"+timefile+"/TerminalPublicKey.data.sha256"); // 输出摘要文件路径
                }  
                out.write(result.getBytes());  
                out.close(); 
                out256.write(remark256.getBytes());
                out256.close();
            } catch (Exception e) {  
                logger.error("write TerminalPublicKey is fail"+e);
            } 
        }else if(type==2){
            logger.info("input parameters type is :"+type); 
            if(date!=null && date!=""){
                 logger.info("input parameters type is :"+type);
//                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");  
//                Date querydate = sdf.parse(date); 
//                SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy/MM/dd");
//                System.out.println(sdf1.format(new java.util.Date()));
//                //System.out.println(sdf.parse(new Date().toString()));
//                SimpleDateFormat sdf3 = new SimpleDateFormat ("EEE MMM dd HH:mm:ss Z yyyy", Locale.UK);
//                try
//                {
//                     Date dates=sdf3.parse(querydate.toString());
//                    SimpleDateFormat sdf4=new SimpleDateFormat("yyyy-MM-dd");
//                    String sDate=sdf4.format(dates);
//                    System.out.println("zui huo  de  time:"+sDate);
//                }
//                catch (ParseException e)
//                {
//                    e.printStackTrace();
//                }
                logger.info("Query time is :"+date);
                List<TmnClpKey> tckList=tmnclpkeyser.tmnClpkeyListByDate(date);
                int tms =tmnclpkeyser.getOneDayTnmClpKeyCount(date);
                String countkey="";
                String bwcountkey="";
                if(tms!=0){
                    countkey=String.valueOf(tms);
                    int len=countkey.length();
                    if(len<6){
                        int n0=6-len;               
                        bwcountkey=String.format("%1$0"+(6-countkey.length())+"d",0)+countkey;
                        logger.info("bwcountkey:"+bwcountkey);
                    }
                }
                StringBuilder strBuf = new StringBuilder();
                String bwcountkeyjfh =bwcountkey+";";
                strBuf.append(bwcountkeyjfh);
                if(tckList!=null){
                    for(TmnClpKey tcKey:tckList){
                        String terminalidString=String.valueOf(tcKey.getTerminal_id());
                       String bwterminalID="";
                       int terminalLen=terminalidString.length();
                       if(terminalLen<12){
                           int t0=12-terminalLen;  
                           bwterminalID=String.format("%1$0"+(12-terminalidString.length())+"d",0)+terminalidString;
                           logger.info("bwterminalID   bu ling:"+bwterminalID);
                       }
                           String tmnclpkey =tcKey.getPublic_key();
                           String yuan3 =provinceidString+bwterminalID+tmnclpkey;
                           logger.info("ukeyPassword:"+ukeyPassword);
                           ukeyid=getKey(ukeyPassword);//获取uKeyId
                           logger.info("ukeyid:"+ukeyid);
                           String jmptp=getSignature(ukeyPassword,ukeyid,yuan3);//省码+逻辑机号+终端机公钥信息签名
                           logger.info("yuanptp:"+jmptp);
                           String TerminalPublicKey =provinceidString+","+bwterminalID+","+tmnclpkey+","+jmptp+";";
                           strBuf.append(TerminalPublicKey);
                           System.out.println(bwcountkey+","+provinceidString+","+bwterminalID+","+jmptp+";");
                   }
                }
             
                String result =strBuf.substring(0, strBuf.length()-1);
                //String  remark256=getSignature(ukeyPassword,ukeyid,result);
                String  remark256=StringEncrytp.Encrypt(result, "");
                logger.info("remark256:"+remark256+"chang du wei :"+remark256.length());
                //StringEncrypt.Encrypt(result, "");
                FileOutputStream out = null;
                FileOutputStream out256 = null;
                SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
                String time = sf.format(new Date());
                String year = time.substring(0, 4);
                String month = time.substring(5, 7);
                String day = time.substring(8, time.length());
                //String timefile =year+month+day;
                String timefile =date;
                try {  

                    File file =new File("/lottery/"+timefile);    
                    //File file =new File("e:/"+timefile);    
                    //如果文件夹不存在则创建    
                    if  (!file .exists()  && !file .isDirectory())      
                    {       
                        System.out.println("//不存在");  
                        file .mkdir();    
                         //out = new FileOutputStream("e:/"+timefile+"/TodayTerminalPublicKey.data"); // 输出公钥记录文件路径  
                         //out256 = new FileOutputStream("e:/"+timefile+"/TodayTerminalPublicKey.data.sha256"); // 输出摘要文件路径  
                        out = new FileOutputStream("/lottery/"+timefile+"/TerminalPublicKey.data"); // 输出公钥记录文件路径  
                        out256 = new FileOutputStream("/lottery/"+timefile+"/TerminalPublicKey.data.sha256"); // 输出摘要文件路径
                    } else   
                    {  
                        System.out.println("//目录存在"); 
                        out = new FileOutputStream("/lottery/"+timefile+"/TerminalPublicKey.data"); // 输出公钥记录文件路径   
                        out256 = new FileOutputStream("/lottery/"+timefile+"/TerminalPublicKey.data.sha256"); // 输出摘要文件路径   
                          //out = new FileOutputStream("e:/"+timefile+"/TodayTerminalPublicKey.data"); // 输出公钥记录文件路径   
                          //out256 = new FileOutputStream("e:/"+timefile+"/TodayTerminalPublicKey.data.sha256"); // 输出摘要文件路径
                    }  
                        out.write(result.getBytes());  
                        out.close(); 
                        out256.write(remark256.getBytes());
                        out256.close();
                    } catch (Exception e) {  
                        logger.error("write TerminalPublicKey is fail"+e);
                    } 
            }else{
                logger.info("Please input parameters Date !");
            }
        }else{
          logger.info("Please input parameters  type !");  
        }     
        return "success";
    }
    
    
    @RequestMapping(value = "/bycsfile")
    @ResponseBody
    public String byscTerminalPublicKey() throws Exception {
        List<SystemInfo> systemInfoslist =systemser.getISystemInfoList();
        SystemInfo systemInfo=systemInfoslist.get(0);
        Integer Province_id= systemInfo.getProvince_id();
        String provinceidString=String.valueOf(Province_id);
        logger.info("省份id为："+Province_id);
        //int tms =tmnser.getITmnInfoSum();
        //String count =String.valueOf(tms);
         List<TmnInfo> tmnInfoslist = tmnser.getITmnInfoList();
         int tms =tmnclpkeyser.getTnmClpKeyCount();
         String countkey="";
         String bwcountkey="";
         if(tms!=0){
             countkey=String.valueOf(tms);
             int len=countkey.length();
             if(len<6){
                 int n0=6-len;               
                 bwcountkey=String.format("%1$0"+(6-countkey.length())+"d",0)+countkey;
                 logger.info("bwcountkey:"+bwcountkey);
             }
         }
         StringBuilder strBuf = new StringBuilder();
         String bwcountkeyjfh =bwcountkey+";";
         strBuf.append(bwcountkeyjfh);
         if(tmnInfoslist!=null){
             for(TmnInfo tmnInfo:tmnInfoslist){
             //String pkString=tmnInfo.getPublic_key();
                String terminalidString=String.valueOf(tmnInfo.getTerminal_id());
                String bwterminalID="";
                int terminalLen=terminalidString.length();
                if(terminalLen<12){
                    int t0=12-terminalLen;  
                    bwterminalID=String.format("%1$0"+(12-terminalidString.length())+"d",0)+terminalidString;
                    logger.info("bwterminalID   bu ling:"+bwterminalID);
                }
                Integer terminalId=tmnInfo.getTerminal_id();
                TmnClpKey tmnClpKey =tmnclpkeyser.getClpKey(terminalId);
                if(tmnClpKey!=null){
                    String tmnclpkey =tmnClpKey.getPublic_key();
                    //String newclpkey =tmnclpkey.replaceAll("\n", "\\\\n");
                    String yuan3 =provinceidString+bwterminalID+tmnclpkey;
                    logger.info("ukeyPassword:"+ukeyPassword);
                    ukeyid=getKey(ukeyPassword);//获取uKeyId
                    logger.info("ukeyid:"+ukeyid);
                    String jmptp=getSignature(ukeyPassword,ukeyid,yuan3);//省码+逻辑机号+终端机公钥信息签名
                    logger.info("yuanptp:"+jmptp);
                    //String signature=RsaExample1.jiajie("","",yuan3);
                    //String filepath="E:/tmp/";
                    //RSAEncrypt.genKeyPair(filepath);
                    //String signstr=RSASignature.sign(yuan3,RSAEncrypt.loadPrivateKeyByFile(filepath));
                    String TerminalPublicKey =provinceidString+","+bwterminalID+","+tmnclpkey+","+jmptp+";";
                    strBuf.append(TerminalPublicKey);
                    System.out.println(bwcountkey+","+provinceidString+","+bwterminalID+","+jmptp+";");
                }             
            }
         }
         
         String result =strBuf.substring(0, strBuf.length()-1);
         //String  remark256=getSignature(ukeyPassword,ukeyid,result);
         String  remark256=StringEncrytp.Encrypt(result, "");
         logger.info("remark256:"+remark256+"chang du wei :"+remark256.length());
         //StringEncrypt.Encrypt(result, "");
         FileOutputStream out = null;
         FileOutputStream out256 = null;
         SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
         String time = sf.format(new Date());
         String year = time.substring(0, 4);
         String month = time.substring(5, 7);
         String day = time.substring(8, time.length());
         String timefile =year+month+day;
        try {  
            
            File file =new File("/lottery/"+timefile);    
            //如果文件夹不存在则创建    
            if  (!file .exists()  && !file .isDirectory())      
            {       
                System.out.println("//不存在");  
                file .mkdir();    
                 //out = new FileOutputStream("e:/"+timefile+"/TerminalPublicKey.data"); // 输出公钥记录文件路径  
                 //out256 = new FileOutputStream("e:/"+timefile+"/TerminalPublicKey.data.sha256"); // 输出摘要文件路径  
                out = new FileOutputStream("/lottery/"+timefile+"/TerminalPublicKey.data"); // 输出公钥记录文件路径  
                out256 = new FileOutputStream("/lottery/"+timefile+"/TerminalPublicKey.data.sha256"); // 输出摘要文件路径
            } else   
            {  
                System.out.println("//目录存在"); 
                out = new FileOutputStream("/lottery/"+timefile+"/TerminalPublicKey.data"); // 输出公钥记录文件路径   
                out256 = new FileOutputStream("/lottery/"+timefile+"/TerminalPublicKey.data.sha256"); // 输出摘要文件路径    
            }  
            out.write(result.getBytes());  
            out.close(); 
            out256.write(remark256.getBytes());
            out256.close();
        } catch (Exception e) {  
            logger.error("write TerminalPublicKey is fail"+e);
        } 
        return "success";
    }
        @RequestMapping(value = "/downfiles")
    @ResponseBody
    public String DownFiles(HttpServletResponse response,String sj){
        DownloadFile df =new DownloadFile();
        df.filePublicKeyDownload(response, sj);
    return "success";
    }
}
