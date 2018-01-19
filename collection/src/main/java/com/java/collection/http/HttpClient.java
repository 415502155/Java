package com.java.collection.http;

import java.io.IOException;
import java.util.Date;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import com.alibaba.fastjson.JSONObject;

@SuppressWarnings("deprecation")
public class HttpClient {
	
	public static String Send() {
	@SuppressWarnings({ "resource" })
	DefaultHttpClient httpclient = new DefaultHttpClient();  
	  
	  
    String smsUrl="http://127.0.0.1:8081/web/api";  
    HttpPost httppost = new HttpPost(smsUrl);  
    String strResult = "";
    	
    try {  
        JSONObject jobj = new JSONObject();  
        jobj.put("PAGESIZE", 2);  
        jobj.put("PAGENUM", 10);  
        jobj.put("REQ", new Date().getTime());  

        System.out.println(jobj.toString());  
        //nameValuePairs.add(new BasicNameValuePair("msg", (jobj.toString())));  
        /*httppost.addHeader("Content-type", "application/json; charset=utf-8"); 
         httppost.setHeader("Accept", "application/json"); 
         httppost.setEntity(new StringEntity(jobj.toString(), Charset.forName("UTF-8")));*/  

        StringEntity s = new StringEntity(jobj.toString());  
        s.setContentEncoding("UTF-8");  
        s.setContentType("application/json");//发送json数据需要设置contentType  
        httppost.setEntity(s);  
        HttpResponse response = httpclient.execute(httppost);  
        if (response.getStatusLine().getStatusCode() == 200) {  
             /*读返回数据*/  
             String conResult = EntityUtils.toString(response  
            		 .getEntity());  
             System.out.println(conResult);  
            JSONObject sobj = new JSONObject();  
            sobj = JSONObject.parseObject(conResult);  
            String result = sobj.getString("result");  
            String code = sobj.getString("code");  
            if(code.equals("500")){  
                 System.out.println(result);  
                 strResult += "发送成功";  
             }else{  
                 strResult += "发送失败，"+code;  
             }  

         } else {  
             String err = response.getStatusLine().getStatusCode()+"";  
             strResult += "发送失败:"+err;  
         }  
     } catch (ClientProtocolException e1) {  
         e1.printStackTrace();  
     } catch (IOException e) {  
         e.printStackTrace();  
     }  

     return strResult;  
	}     
}
