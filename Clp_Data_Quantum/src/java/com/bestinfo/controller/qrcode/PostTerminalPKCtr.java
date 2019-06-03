/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.bestinfo.controller.qrcode;

import com.alibaba.fastjson.JSONArray;
import com.bestinfo.bean.business.TmnInfo;
import com.bestinfo.bean.clpdata.TmnClpKey;
import com.bestinfo.bean.sysUser.SystemInfo;
import static com.bestinfo.controller.qrcode.HttpClientExample.getSSLContext;
import static com.bestinfo.controller.qrcode.RsaExample.getSignature;
import static com.bestinfo.controller.qrcode.UkeyAliasId.getKey;
import com.bestinfo.service.clpdata.ISystemInfoSer;
import com.bestinfo.service.clpdata.ITmnClpKeySer;
import com.bestinfo.service.clpdata.ITmnerInfoSer;
import java.io.File;
import java.io.FileInputStream;
import java.util.List;
import java.util.Map;
import javax.annotation.Resource;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import java.io.IOException;
import java.security.cert.X509Certificate;
import java.util.LinkedHashMap;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLException;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocket;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.X509HostnameVerifier;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.protocol.HttpContext;
import org.apache.http.protocol.HttpCoreContext;
import org.apache.http.util.EntityUtils;
/**
 *
 * @author Administrator
 */
@Controller
@RequestMapping(value = "/postvalue")
public class PostTerminalPKCtr {
    private final Logger logger = Logger.getLogger(PostTerminalPKCtr.class);
    private static final String[] TLS_Protocols = { "TLSv1.2" };
    
    @Resource
    private ITmnerInfoSer tmnser;
    @Resource
    private ISystemInfoSer systemser;
     @Resource
    private ITmnClpKeySer tmnclpkeyser;
     @RequestMapping(value = "/down")
     @ResponseBody
     public void fileDownload(HttpServletResponse response){  
        //获取网站部署路径(通过ServletContext对象)，用于确定下载文件位置，从而实现下载  
        String path = "E:/2017-01-17/TodayTerminalPublicKey.data";  
  
        //1.设置文件ContentType类型，这样设置，会自动判断下载文件类型  
        response.setContentType("multipart/form-data");  
        //2.设置文件头：最后一个参数是设置下载文件名(假如我们叫a.pdf)  
        response.setHeader("Content-Disposition", "attachment;fileName="+"TodayTerminalPublicKey.data");  
        ServletOutputStream out;  
        //通过文件路径获得File对象(假如此路径中有一个download.pdf文件)  
        File file = new File(path);  
  
        try {  
            FileInputStream inputStream = new FileInputStream(file);  
  
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
    
    @RequestMapping(value = "/test")  //,method = RequestMethod.GET, consumes="application/json", produces="application/json"
    @ResponseBody
    public String test() throws Exception{
        HttpClientBuilder builder = HttpClientBuilder.create();

        // 为了测试，所有都是true
        X509HostnameVerifier verifier = new X509HostnameVerifier() {
                @Override
                public boolean verify(String hostname, SSLSession session) {
                        return true;
                }

            @Override
            public void verify(String string, SSLSocket ssls) throws IOException {
            }

            @Override
            public void verify(String string, X509Certificate xc) throws SSLException {
            }

            @Override
            public void verify(String string, String[] strings, String[] strings1) throws SSLException {
            }
        };

        SSLContext defaultSSLContexts = getSSLContext();
        SSLConnectionSocketFactory sslConnectionFactory = new SSLConnectionSocketFactory(
				defaultSSLContexts, TLS_Protocols, null, verifier);
        builder.setSSLSocketFactory(sslConnectionFactory);

        CloseableHttpClient httpclient = builder.build();
        String url = "https://219.143.230.77:4481/lotteryPromotion/keyRegistration";
        //HttpGet httpGet = new HttpGet(url);
        HttpPost httpPost = new HttpPost(url);
        //user token 是为了让链接保持长连接的重复使用
        // 需要设置 userToken属性，否则每次请求时都需要重新建立连接
        // userToken 是证书中的名称
        // 如果使用nginx keepalive，nginx默认 100次请求会断开连接，需要设置keepalive_requests 参数。
        List<SystemInfo> systemInfoslist =systemser.getISystemInfoList();
        SystemInfo systemInfo=systemInfoslist.get(0);
        Integer Province_id= systemInfo.getProvince_id();
        String provinceidString=String.valueOf(Province_id);
        logger.info("省份id为："+Province_id);
        int tms =tmnser.getITmnInfoSum();
        String count =String.valueOf(tms);
        List<TmnInfo> tmnInfoslist = tmnser.getITmnInfoList();
        String ukeyPassword = "12345678";//TODO ukey pin码   
        String responseMsg="";
        if(tmnInfoslist!=null){
                for(TmnInfo tmnInfo:tmnInfoslist){
                 //String pkString=tmnInfo.getPublic_key();
                 Integer terminalId=tmnInfo.getTerminal_id();
                 String terminalidString=String.valueOf(tmnInfo.getTerminal_id());
                 int terminalLen=terminalidString.length();
                 String bwterminalID="";
                if(terminalLen<12){
                    int t0=12-terminalLen;  
                    bwterminalID=String.format("%1$0"+(12-terminalidString.length())+"d",0)+terminalidString;
                    logger.info("bwterminalID   bu ling:"+bwterminalID);
                }
                 //TmnClpKey tck=tmnclpkeyser.getClpKey(terminalId);
                 //int terminalId=tmnInfo.getTerminal_id();
                 TmnClpKey tmnClpKey =tmnclpkeyser.getClpKey(terminalId);
                 if(tmnClpKey!=null){
                        String tmnclpkey =tmnClpKey.getPublic_key();
                        String newclpkey =tmnclpkey.replaceAll("\n", "\\\n");
                        String yuan3 =provinceidString+bwterminalID+newclpkey;
                        logger.info("ukeyPassword:"+ukeyPassword);
                        String ukeyid=getKey(ukeyPassword);//获取uKeyId
                        //String ukeyid="TWT1612000000007";
                        logger.info("ukeyid:"+ukeyid);
                        String signy3=getSignature(ukeyPassword,ukeyid,yuan3);//省码+逻辑机号+终端机公钥信息签名
                        logger.info("signy3:"+signy3);
                        Map map = new LinkedHashMap();
                        map.put("provinceId", provinceidString);
                        map.put("agentId", bwterminalID);
                        map.put("publicKey", newclpkey);
                        map.put("sign", signy3);
                        String resultString=JSONArray.toJSONString(map);
                        logger.info(resultString);
                        HttpContext context = new HttpCoreContext();
                        context.setAttribute(HttpClientContext.USER_TOKEN,"CN=yangqiju.joyveb.com");
                        HttpEntity entity = new StringEntity(resultString);  
                        httpPost.setEntity(entity); 
                        httpPost.addHeader("accept", "application/json");
                        httpPost.addHeader("Content-Type", "application/json");
                       CloseableHttpResponse response = httpclient.execute(httpPost);
                       System.out.println("status:"+response.getStatusLine()); 
                       logger.info("status:"+response.getStatusLine());
                       logger.info("response  :"+response);
                       responseMsg = EntityUtils.toString(response.getEntity(), "UTF-8");
                       System.out.println("response:" + responseMsg);
                       logger.info("response:" + responseMsg);
                 }           
            }   
        }    
        //CloseableHttpResponse chr = 
        httpclient.close(); 
        //return "success";
        return responseMsg;
    }
    
    //private static DefaultHttpClient httpclient = new DefaultHttpClient();
//    @RequestMapping(value = "/reqpost")
//    @ResponseBody
//    public String reqPost() throws Exception{
//            // 为了测试，所有都是true
//            X509HostnameVerifier verifier = new X509HostnameVerifier() {
//                    @Override
//                    public boolean verify(String hostname, SSLSession session) {
//                            return true;
//                    }
//
//                @Override
//                public void verify(String string, SSLSocket ssls) throws IOException {
//                }
//
//                @Override
//                public void verify(String string, X509Certificate xc) throws SSLException {
//                }
//
//                @Override
//                public void verify(String string, String[] strings, String[] strings1) throws SSLException {
//                }
//            };
//
//            SSLContext defaultSSLContexts = getSSLContext();
//            SSLConnectionSocketFactory sslConnectionFactory = new SSLConnectionSocketFactory(
//                                    defaultSSLContexts, TLS_Protocols, null, verifier);
//            builder.setSSLSocketFactory(sslConnectionFactory);
//            httpclient.getParams().setParameter(SO_TIMEOUT, 10000);
//            StringEntity entity = new StringEntity("", "UTF-8");
//            HttpPost httppost = new HttpPost(REQUESTURL);
//            httppost.setEntity(entity);
//            httppost.addHeader("accept", "application/json");
//            httppost.addHeader("Content-Type","application/json");
//            ResponseHandler<String> responseHandler = new BasicResponseHandler();
//            System.out.println("Request: " + REQ_STR);
//            String response = httpclient.execute(httppost, responseHandler);
//            System.out.println("Response: " + response);
//
//        return  "";
//    }
}