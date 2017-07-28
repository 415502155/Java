package com.bestinfo.quartz.job;

import com.bestinfo.bean.clpdata.TmnClpKey;
import com.bestinfo.bean.sysUser.SystemInfo;
import static com.bestinfo.controller.qrcode.RsaExample.getSignature;
import com.bestinfo.controller.qrcode.StringEncrytp;
import static com.bestinfo.controller.qrcode.UkeyAliasId.getKey;
import com.bestinfo.define.filepath.FilePath;
import com.bestinfo.service.clpdata.ISystemInfoSer;
import com.bestinfo.service.clpdata.ITmnClpKeySer;
import com.bestinfo.util.FileUtil;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import javax.annotation.Resource;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

@Component
public class TerminalkeyJob {

    private static final Logger logger = Logger.getLogger(TerminalkeyJob.class);
    @Resource
    private ISystemInfoSer systemser;
    @Resource
    private ITmnClpKeySer tmnclpkeyser;
    private static final String ukeyPassword = "12345678";//TODO ukey pin码
    private static String ukeyid = "";//TODO 私钥的id号
    private static String publickeyfile="TerminalPublicKey.data";
    private static String zhaiyaofile="TerminalPublicKey.data.sha256";
    public synchronized void masterThread() throws FileNotFoundException, IOException {
        logger.warn("******************** terminalkey job start  ********************");
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");//设置日期格式
        String date = df.format(new Date());
        logger.info("now date is :"+date);
        String sj=date.replaceAll("-","");
        logger.info("now sj is :"+sj);
        List<SystemInfo> systemInfoslist =systemser.getISystemInfoList();
        SystemInfo systemInfo=systemInfoslist.get(0);
        Integer Province_id= systemInfo.getProvince_id();
        String provinceidString=String.valueOf(Province_id);
        logger.info("省份id为："+Province_id);
        List<TmnClpKey> tckList=tmnclpkeyser.tmnClpkeyListByDate(date);
                int tms =tmnclpkeyser.getOneDayTnmClpKeyCount(date);
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
                    logger.info("There is no data for the current time");
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
                           System.out.println(bwcountkey+","+provinceidString+","+bwterminalID+","+jmptp+";");
                   }
                }
                String result =strBuf.substring(0, strBuf.length()-1);
                //String  remark256=getSignature(ukeyPassword,ukeyid,result);
                String  remark256=StringEncrytp.Encrypt(result, "");
                logger.info("remark256:"+remark256+"chang du wei :"+remark256.length());                 
                FileOutputStream out = null;
                FileOutputStream out256 = null;
                SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
                String time = sf.format(new Date());
                String year = time.substring(0, 4);
                String month = time.substring(5, 7);
                String day = time.substring(8, time.length());
                String timefile =year+month+day;
                //String timefile ="allKeyAndRemark";
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
        logger.warn("******************** terminalkey job complete 5 ********************");
    }
}
