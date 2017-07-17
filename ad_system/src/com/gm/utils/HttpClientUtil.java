package com.gm.utils;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.ParseException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;

public class HttpClientUtil {
	
	private static Logger log = Logger.getLogger(HttpClientUtil.class);

	public static String post(String url, Map<String, String> params) throws ClientProtocolException, IOException {
		DefaultHttpClient httpclient = new DefaultHttpClient();
		String body = null;
		log.info("create httppost:" + url);
		HttpPost post = postForm(url, params);
		body = invoke(httpclient, post);
		httpclient.getConnectionManager().shutdown();
		return body;
	}

	public static String get(String url) throws ClientProtocolException, IOException {
		DefaultHttpClient httpclient = new DefaultHttpClient();
		String body = null;

		log.info("create HttpGet:" + url);
		HttpGet get = new HttpGet(url);
		body = invoke(httpclient, get);

		httpclient.getConnectionManager().shutdown();

		return body;
	}

	private static String invoke(DefaultHttpClient httpclient,
			HttpUriRequest httpost) throws ClientProtocolException, IOException {
		HttpResponse response = sendRequest(httpclient, httpost);
		return paseResponse(response);
	}

	private static String paseResponse(HttpResponse response) {
		log.info("get response from http server..");
		HttpEntity entity = response.getEntity();
		log.info("response status: " + response.getStatusLine());
		String body = null;
		try {
			body = EntityUtils.toString(entity, HTTP.UTF_8);
			log.info(body);
		} catch (ParseException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return body;
	}

	private static HttpResponse sendRequest(DefaultHttpClient httpclient,
			HttpUriRequest httpost) throws ClientProtocolException, IOException {
		log.info("execute post...");
		return httpclient.execute(httpost);
	}

	private static HttpPost postForm(String url, Map<String, String> params) {

		HttpPost httpost = new HttpPost(url);
		List<NameValuePair> nvps = new ArrayList<NameValuePair>();

		Set<String> keySet = params.keySet();
		for (String key : keySet) {
			nvps.add(new BasicNameValuePair(key, params.get(key)));
		}

		try {
			log.info("set utf-8 form entity to httppost");
			httpost.setEntity(new UrlEncodedFormEntity(nvps, HTTP.UTF_8));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}

		return httpost;
	}

	public static void main(String[] args) throws Exception {
		
		final String ip = "http://10.1.100.30:8083";// 测试服务器ip

		String p = "u.rid=5712&u.uid=5712&u.puid=abc&u.sid=1&u.pid=1&u.channel=web"
				+ "&u.ip=10.1.104.233&u.pname=abc&u.rname=abc&u.prof=10&"
				+ "u.reg_time=1408948141&u.time=1408948141";
		String address = ip + "/user.htm";
		Map<String, String> parasMap = new HashMap<String, String>();
		parasMap.put("u.rid", "5712");
		parasMap.put("u.uid", "5712");
		parasMap.put("u.puid", "afc");
		parasMap.put("u.sid", "1");
		parasMap.put("u.pid", "1");
		parasMap.put("u.channel", "web");
		
		parasMap.put("u.ip", "10.1.104.233");
		parasMap.put("u.pname", "rao");
		parasMap.put("u.rname", "sss");
		parasMap.put("u.prof", "10");
		parasMap.put("u.reg_time", "1408948141");
		parasMap.put("u.time", "1408948141");
		
		String string = post(address,parasMap);
		System.err.println(string);

	}

}
