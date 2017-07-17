package com.gm.utils;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;

import org.apache.log4j.Logger;

public class HttpInvoker {

	public static Logger log = Logger.getLogger(HttpInvoker.class); // 日志

	public static String readContentFromGet(String url) throws Exception {

		// 拼凑get请求的URL字串，使用URLEncoder.encode对特殊和不可见字符进行编码

		URL getUrl = new URL(url);

		// 根据拼凑的URL，打开连接，URL.openConnection()函数会根据
		// URL的类型，返回不同的URLConnection子类的对象，在这里我们的URL是一个http，因此它实际上返回的是HttpURLConnection

		HttpURLConnection connection = (HttpURLConnection) getUrl
				.openConnection();

		connection.setRequestMethod("GET");
		connection.setRequestProperty("contentType", "utf-8");
		connection.setConnectTimeout(120000);
		connection.setReadTimeout(120000);
		// 发送数据到服务器并使用Reader读取返回的数据

		BufferedReader reader = new BufferedReader(new InputStreamReader(
				connection.getInputStream(),"utf-8"));
		

		log.info(" ============================= ");

		log.info(" Contents of get request start");

		log.info(" ============================= ");

		String lines;
		
		StringBuffer result = new StringBuffer();

		while ((lines = reader.readLine()) != null) {
			result.append(lines);
		}

		log.info(result.toString());
		
		reader.close();

		// 断开连接

		connection.disconnect();

		log.info(" ============================= ");

		log.info(" Contents of get request ends ");

		log.info(" ============================= ");
		
		return result.toString();

	}

	public static String readContentFromPost(String url, String content) throws IOException {

		// Post请求的url，与get不同的是不需要带参数

		URL postUrl = new URL(url);

		// 打开连接

		HttpURLConnection connection = (HttpURLConnection) postUrl
				.openConnection();

		// 打开读写属性，默认均为false

		connection.setDoOutput(true);

		connection.setDoInput(true);

		// 设置请求方式，默认为GET

		connection.setRequestMethod("POST");
		connection.setRequestProperty("contentType", "utf-8");
		connection.setConnectTimeout(120000);
		connection.setReadTimeout(120000);

		// Post 请求不能使用缓存

		connection.setUseCaches(false);

		// URLConnection.setFollowRedirects是static 函数，作用于所有的URLConnection对象。

		// connection.setFollowRedirects(true);

		// URLConnection.setInstanceFollowRedirects 是成员函数，仅作用于当前函数

		connection.setInstanceFollowRedirects(true);

		// 配置连接的Content-type，配置为application/x-
		// www-form-urlencoded的意思是正文是urlencoded编码过的form参数，下面我们可以看到我们对正文内容使用URLEncoder.encode进行编码

		connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");

		// 连接，从postUrl.openConnection()至此的配置必须要在 connect之前完成，

		// 要注意的是connection.getOutputStream()会隐含的进行调用 connect()，所以这里可以省略

		// connection.connect();

		DataOutputStream out = new DataOutputStream(connection

		.getOutputStream());

		out.writeBytes(content);

		out.flush();

		out.close(); // flush and close

		BufferedReader reader = new BufferedReader(new InputStreamReader(
				connection.getInputStream(),"utf-8"));
		String line;
		
		StringBuffer result = new StringBuffer();

/*
		log.info(" ============================= ");

		log.info(" Contents of post request start");

		log.info(" ============================= ");
*/
		while ((line = reader.readLine()) != null) {
			result.append(line);
		}
		
//		log.info(result.toString());

/*
		log.info(" ============================= ");

		log.info(" Contents of post request ends ");

		log.info(" ============================= ");*/

		reader.close();
		connection.disconnect();
		
		return result.toString();

	}
	
	public static String readStreamPost(String url, String content) throws IOException {

		// Post请求的url，与get不同的是不需要带参数

		URL postUrl = new URL(url);

		// 打开连接

		HttpURLConnection connection = (HttpURLConnection) postUrl
				.openConnection();

		// 打开读写属性，默认均为false

		connection.setDoOutput(true);

		connection.setDoInput(true);

		// 设置请求方式，默认为GET

		connection.setRequestMethod("POST");
		connection.setRequestProperty("contentType", "utf-8");
		connection.setConnectTimeout(120000);
		connection.setReadTimeout(120000);

		// Post 请求不能使用缓存

		connection.setUseCaches(false);

		// URLConnection.setFollowRedirects是static 函数，作用于所有的URLConnection对象。

		// connection.setFollowRedirects(true);

		// URLConnection.setInstanceFollowRedirects 是成员函数，仅作用于当前函数

		connection.setInstanceFollowRedirects(true);

		// 配置连接的Content-type，配置为application/x-
		// www-form-urlencoded的意思是正文是urlencoded编码过的form参数，下面我们可以看到我们对正文内容使用URLEncoder.encode进行编码

		connection.setRequestProperty("Content-Type", "text/xml");

		// 连接，从postUrl.openConnection()至此的配置必须要在 connect之前完成，

		// 要注意的是connection.getOutputStream()会隐含的进行调用 connect()，所以这里可以省略

		// connection.connect();

		OutputStream out = connection.getOutputStream();

		// 正文内容其实跟get的URL中'?'后的参数字符串一致

//		String content = " firstname= "	+ URLEncoder.encode(" 一个大肥人 ", " utf-8 ");

		// DataOutputStream.writeBytes将字符串中的16位的 unicode字符以8位的字符形式写道流里面

		out.write(content.getBytes());

		out.flush();

		out.close(); // flush and close

		BufferedReader reader = new BufferedReader(new InputStreamReader(
				connection.getInputStream(),"utf-8"));
		String line;
		
		StringBuffer result = new StringBuffer();

/*
		log.info(" ============================= ");

		log.info(" Contents of post request start");

		log.info(" ============================= ");
*/
		while ((line = reader.readLine()) != null) {
			result.append(line);
		}
		
//		log.info(result.toString());

/*
		log.info(" ============================= ");

		log.info(" Contents of post request ends ");

		log.info(" ============================= ");*/

		reader.close();
		connection.disconnect();
		
		return result.toString();

	}

	public static void main(String[] args) throws Exception {

		try {
			
			/*String s = "data={\"sid\":\"9998\","
					+ "\"dept\":'263',"
					+ "\"time\":1435033536"
					+ "}";

			String url = "";
			//url = "http://10.1.100.29:2224/?op=send_mail&user_id=191&timestamp=1417579178&server_id=1&title=1&content=1&gold=10&bind_gold=10&role_id=18000022";
			url = "http://10.0.101.52:8080/DataFbServiceNew521/getapi/save_server.htm?"+s;
			readContentFromGet(url);*/
			//String url = "http://notify.lightcloud.cn:8333/forward";

			String url = "http://notify.lightcloud.cn:8333/forward/ding";
			Map<String, Object> params=new HashMap<String, Object>();
			params.put("content", "抱歉，再测试zaice");
			params.put("toparty", 3281708);
			System.out.println(HttpUtil.doPost(url, JSONObject.fromObject(params).toString()));
			
			//部门ID是： 3281708
			
/*			String url = "http://notify.lightcloud.cn:8333/forward";
			List list = new ArrayList();
			list.add("tangcf");
			Map<String, Object> params=new HashMap<String, Object>();
			params.put("content", "新开服数据");
			params.put("reciver", list);
			System.out.println(HttpUtil.doPost(url, JSONObject.fromObject(params).toString()));*/
			
			//readStreamPost(url,JSONObject.fromObject(params).toString());
			//部门ID是： 3281708

		} catch (IOException e) {

			e.printStackTrace();

		}

	}

}