package com.bestinfo.gambler.all;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

/**
 *
 * @author chenliping
 */
public class HttpSend {

    public static  HttpClient httpclient = null;

    public static String httpSend(String url, String xmlStr) {
        try {
            if (httpclient == null) {
                httpclient = new DefaultHttpClient();
            }
            HttpPost httppost = new HttpPost(url);
//            ByteArrayEntity reqEntity = new ByteArrayEntity(xmlStr);
            StringEntity reqEntity = new StringEntity(xmlStr, "UTF-8");
            httppost.setEntity(reqEntity);
            HttpResponse hr = httpclient.execute(httppost);
            int statuscode = hr.getStatusLine().getStatusCode();
            System.out.println("结果：" + statuscode+" ？？？？？？？");
            String result = null;
            if (statuscode == 200) {
                result = EntityUtils.toString(hr.getEntity());
                if (null == result || result.length() == 0) {
                    System.out.println("response is null");
                    return null;
                }
                System.out.println("respons is "+result);
                EntityUtils.consume(hr.getEntity());
            }
            return result;
        } catch (Exception e) {
            System.out.println("httpsend异常: "+e);
            return null;
        }
        
        
    }
    
    public  String httpSendThread(String url, String xmlStr) {
        HttpClient httpclient = null;
        try {
            if (httpclient == null) {
                httpclient = new DefaultHttpClient();
            }
            HttpPost httppost = new HttpPost(url);
//            ByteArrayEntity reqEntity = new ByteArrayEntity(xmlStr);
            StringEntity reqEntity = new StringEntity(xmlStr, "UTF-8");
            httppost.setEntity(reqEntity);
            HttpResponse hr = httpclient.execute(httppost);
            int statuscode = hr.getStatusLine().getStatusCode();
            System.out.println("结果：" + statuscode+" ？？？？？？？");
            String result = null;
            if (statuscode == 200) {
                result = EntityUtils.toString(hr.getEntity());
                if (null == result || result.length() == 0) {
                    System.out.println("response is null");
                    return null;
                }
                System.out.println("respons is "+result);
                EntityUtils.consume(hr.getEntity());
            }
            return result;
        } catch (Exception e) {
            System.out.println("httpsend异常: "+e);
            return null;
        }
        
        
    }

}
