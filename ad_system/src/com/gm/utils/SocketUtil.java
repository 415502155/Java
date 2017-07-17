package com.gm.utils;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.UnknownHostException;

public class SocketUtil {

	private static final String ADDRESS = "10.1.101.98";
	private static final int PORT = 40060;

	/**
	 * 请求socket接口
	 * 
	 * @param s
	 * @return
	 */
	public static String request(String s) {
		String result = null;
		Socket socket = null;
		try {
			socket = new Socket(ADDRESS, PORT);
			socket.setSoTimeout(30000);
			InputStream in = socket.getInputStream();
			OutputStream out = socket.getOutputStream();
			out.write(s.getBytes());
			out.flush();
			// out.close();
			byte[] data = new byte[1024];
			int bytesRcvd = in.read(data, 0, 1024);
			result = new String(data, "utf-8");
			int jsonLength = parseContentHeader(result);
			out.close();
			in.close();
			// 二次调用
//			result = requestContent(s.getBytes(), jsonLength);
		} catch (UnknownHostException e) {
			return "1";// 连接错误
		} catch (IOException e) {
			return "2";// 读写错误
		} finally {
			try {
				if (socket != null) {
					socket.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return result;
	}

	/**
	 * 获取指定长度的content
	 * 
	 * @param s
	 * @param jsonLength
	 * @return
	 */
	public static byte[] requestContent(byte[] s, int jsonLength) {
		byte[] r = new byte[0];
		Socket socket = null;
		InputStream in = null;
		OutputStream out = null;
		try {
			socket = new Socket(ADDRESS, PORT);
			socket.setSoTimeout(30000);
			in = socket.getInputStream();
			out = socket.getOutputStream();
			out.write(s);
			out.flush();
			byte[] buffer = new byte[jsonLength];
			in.read(buffer, 0, jsonLength);
			out.close();
			in.close();
			return buffer;
		} catch (UnknownHostException e) {
			// return "1";//连接错误
		} catch (IOException e) {
			// return "2";//读写错误
		} finally {
			try {
				if (socket != null) {
					socket.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return r;
	}

	/**
	 * 解析头信息
	 * 
	 * @param result
	 * @return
	 */
	private static int parseContentHeader(String result) {
		/*
		 * QYUGG 1.0 REP command:21 content_length:1147 req_addr:1
		 */
		String contentLenthStr = "";

		if (null != result) {
			String header[] = result.split("\r\n");
			for (int i = 0; i < header.length; i++) {
				if (header[i].indexOf("content_length") != -1) {
					contentLenthStr = header[i];
					break;
				}
			}
			try {
				String lenString = contentLenthStr.split(":")[1];
				int headerIndex = result.indexOf("{", 0);
				return Integer.valueOf(lenString) + headerIndex;
			} catch (NumberFormatException e) {
				return 0;
			}
		}
		return 0;
	}
}
