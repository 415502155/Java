package com.bestinfo.controller.qrcode;

import com.bestinfo.bean.sysUser.SystemInfo;
import com.bestinfo.bean.terminal.TmnClpKey;
import static com.bestinfo.controller.qrcode.RsaExample.getSignature;
import static com.bestinfo.controller.qrcode.UkeyAliasId.getKey;
import com.bestinfo.dao.clpdata.ISystemInfoDao;
import com.bestinfo.dao.clpdata.ScITmnClpKeyDao;
import com.bestinfo.define.filepath.FilePath;
import com.bestinfo.util.FileUtil;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;
import org.springframework.jdbc.core.JdbcTemplate;
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
    private JdbcTemplate termJdbcTemplate;
    @Resource
    private ScITmnClpKeyDao itck;
    @Resource
    private ISystemInfoDao is;
   
    private static final String ukeyPassword = "12345678";//TODO ukey pin码
    private static String ukeyid = "";//TODO 私钥的id号
    private static String publickeyfile="TerminalPublicKey.data";
    private static String zhaiyaofile="TerminalPublicKey.data.sha256";
    /***
     * 
     * @param sj
     * @return
     * @throws Exception 
     */
    @RequestMapping(value = "/scfile")
    @ResponseBody
    public String scTerminalPublicKey(String sj) throws Exception {
        int sjlen =sj.length();
        String syear="";
        String smonth="";
        String sday="";
        if(sjlen==8){
            syear=sj.substring(0, 4);
            smonth=sj.substring(4, 6);
            sday=sj.substring(6, 8);
        }
        List<SystemInfo> systemInfoslist =is.getSystemInfoList(termJdbcTemplate);
        SystemInfo systemInfo=systemInfoslist.get(0);
        Integer Province_id= systemInfo.getProvince_id();
        String provinceidString=String.valueOf(Province_id);
        logger.info("省份id为："+Province_id);
        if("1".equals(sj)){          
             List<TmnClpKey> tckList=itck.getTmnClpkeyList(termJdbcTemplate);
             int tms =itck.getAllTnmClpKeyCount(termJdbcTemplate);
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
                        try {
                            ukeyid=getKey(ukeyPassword);//获取uKeyId
                            logger.info("ukeyid:"+ukeyid);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        String jmptp="";
                        try {
                            jmptp=getSignature(ukeyPassword,ukeyid,yuan3);//省码+逻辑机号+终端机公钥信息签名
                            logger.info("yuanptp:"+jmptp);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        String TerminalPublicKey =provinceidString+","+bwterminalID+","+tmnclpkey+","+jmptp+";";
                        strBuf.append(TerminalPublicKey);
                }
             }
             
             String result =strBuf.substring(0, strBuf.length()-1);
             String  remark256=StringEncrytp.Encrypt(result, "");
             logger.info("remark256:"+remark256+"chang du wei :"+remark256.length());
             FileOutputStream out = null;
             FileOutputStream out256 = null;
             String timefile ="allKeyAndRemark";
             String publicKeyFilePath = FilePath.getTmnClpKeyFilePath(timefile, publickeyfile);
             String zhaiyaoFilePath = FilePath.getTmnClpKeyFilePath(timefile, zhaiyaofile);
             FileOutputStream outpublickey = null;
             FileOutputStream outzhaiyao = null;
             outpublickey = new FileOutputStream(FileUtil.makeNewFile(publicKeyFilePath)); // 输出公钥记录文件路径  
             outzhaiyao = new FileOutputStream(FileUtil.makeNewFile(zhaiyaoFilePath)); // 输出摘要文件路径
             outpublickey.write(result.getBytes());  
             outpublickey.close();
             outzhaiyao.write(remark256.getBytes());  
             outzhaiyao.close();
        }else if(sjlen==8){
            logger.info("input parameters length :8"); 
            String resString=jiaoyan(sj);
            if(resString=="success"){
                String date =syear+"-"+smonth+"-"+sday;
                logger.info("Query time is :"+date);
                List<TmnClpKey> tckList=itck.getTmnClpkeyListByDate(termJdbcTemplate, date);
                int tms =itck.getOneDayTnmClpKeyCount(termJdbcTemplate, date);
                logger.info("tckList is:"+tckList);
                logger.info("tms is:"+tms);
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
                }else{
                    return "There is no data for the current time";
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
                           try {
                           ukeyid=getKey(ukeyPassword);//获取uKeyId
                           logger.info("ukeyid:"+ukeyid);
                            } catch (Exception e) {
                                e.printStackTrace();
                                logger.info("get ukeyid define");
                            }
                           String jmptp="";
                           try {
                            jmptp=getSignature(ukeyPassword,ukeyid,yuan3);//省码+逻辑机号+终端机公钥信息签名
                           logger.info("yuanptp:"+jmptp);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                           
                           String TerminalPublicKey =provinceidString+","+bwterminalID+","+tmnclpkey+","+jmptp+";";
                           strBuf.append(TerminalPublicKey);
                   }
                }             
                String result =strBuf.substring(0, strBuf.length()-1);
                String  remark256=StringEncrytp.Encrypt(result, "");
                logger.info("remark256:"+remark256+"chang du wei :"+remark256.length());                 
                FileOutputStream out = null;
                FileOutputStream out256 = null;
                String timefile =syear+smonth+sday;
                String publicKeyFilePath = FilePath.getTmnClpKeyFilePath(timefile, publickeyfile);
                String zhaiyaoFilePath = FilePath.getTmnClpKeyFilePath(timefile, zhaiyaofile);
                FileOutputStream outpublickey = null;
                FileOutputStream outzhaiyao = null;
                outpublickey = new FileOutputStream(FileUtil.makeNewFile(publicKeyFilePath)); // 输出公钥记录文件路径  
                outzhaiyao = new FileOutputStream(FileUtil.makeNewFile(zhaiyaoFilePath)); // 输出摘要文件路径
                outpublickey.write(result.getBytes());  
                outpublickey.close();
                outzhaiyao.write(remark256.getBytes());  
                outzhaiyao.close();                 
            }else{
                logger.info("Input time format error !");
                return "Input time format error";
            }
        }else{
          logger.info("Input time format error !");  
          return "Input time format error";
        }     
        return "success";
    }
    
     @RequestMapping(value = "/downpublickeyfile")
     @ResponseBody
     public void filePublicKeyDownload(HttpServletResponse response,String sj){  
        int sjlen =sj.length();
        String syear="";
        String smonth="";
        String sday="";
        if(sjlen==8){
            syear=sj.substring(0, 4);
            smonth=sj.substring(4, 6);
            sday=sj.substring(6, 8);
        }
        System.out.println("ss:"+syear+"ssss:"+smonth+"ssssss:"+sday);   
        if("1".equals(sj)){
            String timefile ="allKeyAndRemark";///lottery/dat/tmnclpkey/allKeyAndRemark/TerminalPublicKey.data
            //String publicKeyFilePath = FilePath.getTmnClpKeyFilePath(timefile, publickeyfile);
            String publicKeyFilePath = "/lottery/dat/tmnclpkey/allKeyAndRemark/TerminalPublicKey.data";
            logger.info("publicKeyFilePath in :"+publicKeyFilePath);
            //String zhaiyaoFilePath = FilePath.getTmnClpKeyFilePath(timefile, zhaiyaofile);
            //1.设置文件ContentType类型，这样设置，会自动判断下载文件类型  
            response.setContentType("multipart/form-data");  
            //2.设置文件头：最后一个参数是设置下载文件名(假如我们叫a.pdf)  
            response.setHeader("Content-Disposition", "attachment;fileName="+"TerminalPublicKey.data");  
            ServletOutputStream out;  
            //通过文件路径获得File对象(假如此路径中有一个download.pdf文件)  
            File filepublicKey = new File(publicKeyFilePath); 
            //File filezhaiyao = new File(zhaiyaoFilePath); 
            try {  
                FileInputStream inputStream = new FileInputStream(filepublicKey);  

                //3.通过response获取ServletOutputStream对象(out)  
                out = response.getOutputStream();  

                int b = 0;  
                byte[] buffer = new byte[512];  
                while (b != -1){  
                    b = inputStream.read(buffer);  
                    //4.写到输出流(out)中  
                    out.write(buffer,0,b);  
                }  
                inputStream.close();  
                out.close();  
                out.flush();  

            } catch (IOException e) {  
                e.printStackTrace();  
            }           
        }else if(sjlen==8){
            String resString=jiaoyan(sj);
            if(resString=="success"){
            //String date =syear+"-"+smonth+"-"+sday;
            //String path = "E:/2017-01-17/TodayTerminalPublicKey.data"; 

            String publicKeyFilePath = FilePath.getTmnClpKeyFilePath(sj, publickeyfile);
            //String zhaiyaoFilePath = FilePath.getTmnClpKeyFilePath(sj, zhaiyaofile);
            logger.info("publicKeyFilePath in :"+publicKeyFilePath);
            //1.设置文件ContentType类型，这样设置，会自动判断下载文件类型  
            response.setContentType("multipart/form-data");  
            //2.设置文件头：最后一个参数是设置下载文件名  
            response.setHeader("Content-Disposition", "attachment;fileName="+"TerminalPublicKey.data");  
            ServletOutputStream out;  
            //通过文件路径获得File对象(假如此路径中有一个download.pdf文件)  
            File filepublicKey = new File(publicKeyFilePath); 
            //File filezhaiyao = new File(zhaiyaoFilePath);
            try {  
                FileInputStream inputStream = new FileInputStream(filepublicKey);  

                //3.通过response获取ServletOutputStream对象(out)  
                out = response.getOutputStream();  

                int b = 0;  
                byte[] buffer = new byte[512];  
                while (b != -1){  
                    b = inputStream.read(buffer);  
                    //4.写到输出流(out)中  
                    out.write(buffer,0,b);  
                }  
                inputStream.close();  
                out.close();  
                out.flush();  

            } catch (IOException e) {  
                e.printStackTrace();  
            }           
        }else{
                logger.info("Input time format error !");
            } 
    
    }
}
     
     @RequestMapping(value = "/remarkfile")
     @ResponseBody
     public void fileRemarkDownload(HttpServletResponse response,String sj){  
        int sjlen =sj.length();
        String syear="";
        String smonth="";
        String sday="";
        if(sjlen==8){
            syear=sj.substring(0, 4);
            smonth=sj.substring(4, 6);
            sday=sj.substring(6, 8);
        }
        System.out.println("ss:"+syear+"ssss:"+smonth+"ssssss:"+sday); 
        if("1".equals(sj)){
            String timefile ="allKeyAndRemark";
            //String publicKeyFilePath = FilePath.getTmnClpKeyFilePath(timefile, publickeyfile);
            String zhaiyaoFilePath = FilePath.getTmnClpKeyFilePath(timefile, zhaiyaofile);
            logger.info("zhaiyaoFilePath in :"+zhaiyaoFilePath);
            //1.设置文件ContentType类型，这样设置，会自动判断下载文件类型  
            response.setContentType("multipart/form-data");  
            //2.设置文件头：最后一个参数是设置下载文件名(假如我们叫a.pdf)  
            response.setHeader("Content-Disposition", "attachment;fileName="+zhaiyaofile);  
            ServletOutputStream out;  
            //通过文件路径获得File对象(假如此路径中有一个download.pdf文件)  
            //File filepublicKey = new File(publicKeyFilePath); 
            File filezhaiyao = new File(zhaiyaoFilePath);            
            try {  
                FileInputStream inputStream = new FileInputStream(filezhaiyao);  

                //3.通过response获取ServletOutputStream对象(out)  
                out = response.getOutputStream();  

                int b = 0;  
                byte[] buffer = new byte[512];  
                while (b != -1){  
                    b = inputStream.read(buffer);  
                    //4.写到输出流(out)中  
                    out.write(buffer,0,b);  
                }  
                inputStream.close();  
                out.close();  
                out.flush();  

            } catch (IOException e) {  
                e.printStackTrace();  
            }
        }else if(sjlen==8){
            String resString=jiaoyan(sj);
            if(resString=="success"){
            //String date =syear+"-"+smonth+"-"+sday;
            //String path = "E:/2017-01-17/TodayTerminalPublicKey.data"; 

            //String publicKeyFilePath = FilePath.getTmnClpKeyFilePath(sj, publickeyfile);
            String zhaiyaoFilePath = FilePath.getTmnClpKeyFilePath(sj, zhaiyaofile);
            logger.info("zhaiyaoFilePath in :"+zhaiyaoFilePath);
            //1.设置文件ContentType类型，这样设置，会自动判断下载文件类型  
            response.setContentType("multipart/form-data");  
            //2.设置文件头：最后一个参数是设置下载文件名  
            response.setHeader("Content-Disposition", "attachment;fileName="+zhaiyaofile);  
            ServletOutputStream out;  
            //通过文件路径获得File对象(假如此路径中有一个download.pdf文件)  
            //File filepublicKey = new File(publicKeyFilePath); 
            File filezhaiyao = new File(zhaiyaoFilePath);
            try {  
                FileInputStream inputStream = new FileInputStream(filezhaiyao);  

                //3.通过response获取ServletOutputStream对象(out)  
                out = response.getOutputStream();  

                int b = 0;  
                byte[] buffer = new byte[512];  
                while (b != -1){  
                    b = inputStream.read(buffer);  
                    //4.写到输出流(out)中  
                    out.write(buffer,0,b);  
                }  
                inputStream.close();  
                out.close();  
                out.flush();  

            } catch (IOException e) {  
                e.printStackTrace();  
            }           
        }else{
                logger.info("Input time format error !");
            } 
    
    }
}
    public String jiaoyan(String str){
       //String str="20170130";
       String convertSuccess="success";
       SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
       try {
          format.setLenient(false);
          format.parse(str);
       } catch (ParseException e) {
          // e.printStackTrace();
// 如果throw java.text.ParseException或者NullPointerException，就说明格式不对
           convertSuccess="error";
       } 
       return convertSuccess;
   }
    public void read(String path,HttpServletResponse response){
         ServletOutputStream out;  
        try {  
                FileInputStream inputStream = new FileInputStream(path);  

                //3.通过response获取ServletOutputStream对象(out)  
                out = response.getOutputStream();  

                int b = 0;  
                byte[] buffer = new byte[512];  
                while (b != -1){  
                    b = inputStream.read(buffer);  
                    //4.写到输出流(out)中  
                    out.write(buffer,0,b);  
                }  
                inputStream.close();  
                out.close();  
                out.flush();  

            } catch (IOException e) {  
                e.printStackTrace();  
            }
    }
    
    
}
