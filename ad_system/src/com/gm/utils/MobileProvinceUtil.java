package com.gm.utils;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import net.sf.json.JSONObject;

public class MobileProvinceUtil {
	
	public static String request(String mobile) {
		String province="";
	    try {
			String url="https://tcc.taobao.com/cc/json/mobile_tel_segment.htm?tel="+mobile;
			String jsonstr=HttpClientUtil.get(url);
			province=JSONObject.fromObject(jsonstr.substring(jsonstr.indexOf(" = ")+3)).getString("province");
	    } catch (Exception e) {
	        e.printStackTrace();
	        String httpUrl = "http://apis.baidu.com/apistore/mobilephoneservice/mobilephone";
			String httpArg = "tel="+mobile;
		    BufferedReader reader = null;
		    String result = null;
		    StringBuffer sbf = new StringBuffer();
		    httpUrl = httpUrl + "?" + httpArg; 
	        URL url;
			try {
				url = new URL(httpUrl);
				HttpURLConnection connection = (HttpURLConnection) url.openConnection();
		        connection.setRequestMethod("GET");
		        // 填入apikey到HTTP header
		        connection.setRequestProperty("apikey",  "2bce69a0c1bc225a57b7eea8667cc91c");
		        connection.connect();
		        InputStream is = connection.getInputStream();
		        reader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
		        String strRead = null;
		        while ((strRead = reader.readLine()) != null) {
		            sbf.append(strRead);
		            sbf.append("\r\n");
		        }
		        reader.close();
		        result = sbf.toString();
		        String retData=JSONObject.fromObject(result).getString("retData");
		        JSONObject jsonObject=JSONObject.fromObject(retData);
		        province=jsonObject.getString("province");
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
	    }
	    
	    return province;
	}
	
	public static void main(String[] args) {
		System.out.println(MobileProvinceUtil.request("18310545658"));
	}

}
