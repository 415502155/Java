package com.gm.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import javax.servlet.http.HttpServletRequest;

public class HttpUtil {

	public static String getRequestInputStreamStr(HttpServletRequest request) {
		String isStr = null;
		try {
			InputStream is = request.getInputStream();
			isStr = getRequestInputStreamStr(is, request.getCharacterEncoding());
			is.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return isStr;
	}
	
	public static String getRequestInputStreamStr(InputStream is, String encoding) {
		String isStr = null;
		try {
			InputStreamReader isr = null;
			if (encoding != null) {
				isr = new InputStreamReader(is, encoding);
			} else {
				isr = new InputStreamReader(is);
			}
			BufferedReader bf = new BufferedReader(isr);
			StringBuffer sb = new StringBuffer();
			String read = null;
			while ((read = bf.readLine()) != null) {
				sb.append(read + "\r\n");
			}
			bf.close();
			isr.close();

			isStr = sb.toString();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return isStr;
	}

	public static String doPost(String address, String paras) throws MalformedURLException{
		String result = "连接失败,请检查网络...";
		URL url = new URL(address);
		String json = paras;
		HttpURLConnection huConn = null;
		try {
			huConn = (HttpURLConnection) url.openConnection();
			huConn.setDoInput(true);
			huConn.setDoOutput(true);
			huConn.setRequestProperty("content-type", "text/xml");
			huConn.setRequestProperty("contentType", "utf-8");
			huConn.setRequestProperty("Accept-Charset", "utf-8");
			huConn.setRequestMethod("POST");
			huConn.connect();
			OutputStream os = huConn.getOutputStream();
			os.write(json.getBytes());
			os.flush();
			os.close();
			InputStream is = huConn.getInputStream();
			result = HttpUtil.getRequestInputStreamStr(is, "UTF-8");
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			huConn.disconnect();
		}
		return result;
	}
}
