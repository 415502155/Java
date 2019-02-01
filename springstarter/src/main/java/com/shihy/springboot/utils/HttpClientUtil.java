package com.shihy.springboot.utils;

import java.io.File;
import java.net.SocketTimeoutException;
import java.security.GeneralSecurityException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.Consts;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.config.RequestConfig.Builder;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ConnectTimeoutException;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

public class HttpClientUtil {

	private static final int connTimeout = 120000;

	private static final int readTimeout = 120000;

	private static final String charset = "UTF-8";

	private static HttpClient client = null;

	// User-Agent为模拟浏览器时使用
	@SuppressWarnings("unused")
	private static final String HEADER_USER_AGENT_CHROME = "Mozilla/5.0 (Windows NT 6.1; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/63.0.3239.132 Safari/537.36";

	@SuppressWarnings("unused")
	private static final String HEADER_USER_AGENT_FIREFOX = "Mozilla/5.0 (Windows NT 6.1; Win64; x64; rv:57.0) Gecko/20100101 Firefox/57.0";

	static {
		PoolingHttpClientConnectionManager cm = new PoolingHttpClientConnectionManager();
		cm.setMaxTotal(128);
		cm.setDefaultMaxPerRoute(128);
		client = HttpClients.custom().setConnectionManager(cm).build();
	}

	public static String post(String url, String parameterStr) throws ConnectTimeoutException, SocketTimeoutException, Exception {
		return post(url, parameterStr, "application/x-www-form-urlencoded", charset, connTimeout, readTimeout);
	}

	public static String post(String url, String parameterStr, String mimeType) throws ConnectTimeoutException,
			SocketTimeoutException, Exception {
		return post(url, parameterStr, mimeType, charset, connTimeout, readTimeout);
	}

	public static String post(String url, Map<String, Object> params) throws ConnectTimeoutException, SocketTimeoutException,
			Exception {
		return postForm(url, params, null, connTimeout, readTimeout);
	}

	public static String post(String url, Map<String, Object> params, Map<String, String> headers)
			throws ConnectTimeoutException, SocketTimeoutException, Exception {
		return postForm(url, params, headers, connTimeout, readTimeout);
	}

	public static String get(String url) throws Exception {
		return get(url, charset, connTimeout, readTimeout);
	}

	public static String get(String url, Map<String, Object> paramMap) throws Exception {
		return get(url, paramMap, charset, connTimeout, readTimeout);
	}

	public static String get(String url, String charset) throws Exception {
		return get(url, charset, connTimeout, readTimeout);
	}

	/**
	 * 发送一个 Post 请求, 使用指定的字符集编码.
	 * 
	 * @param url
	 * @param body RequestBody
	 * @param mimeType 例如 application/xml "application/x-www-form-urlencoded" a=1&b=2&c=3
	 * @param charset 编码
	 * @param connTimeout 建立链接超时时间,毫秒.
	 * @param readTimeout 响应超时时间,毫秒.
	 * @return ResponseBody, 使用指定的字符集编码.
	 * @throws ConnectTimeoutException 建立链接超时异常
	 * @throws SocketTimeoutException 响应超时
	 * @throws Exception
	 */
	private static String post(String url, String body, String mimeType, String charset, Integer connTimeout, Integer readTimeout)
			throws ConnectTimeoutException, SocketTimeoutException, Exception {
		HttpClient client = null;
		HttpPost post = new HttpPost(url);
		String result = "";
		try {
			if (StringUtils.isNotBlank(body)) {
				HttpEntity entity = new StringEntity(body, ContentType.create(mimeType, charset));
				post.setEntity(entity);
			}
			// 设置参数
			Builder customReqConf = RequestConfig.custom();
			if (connTimeout != null) {
				customReqConf.setConnectTimeout(connTimeout);
			}
			if (readTimeout != null) {
				customReqConf.setSocketTimeout(readTimeout);
			}
			post.setConfig(customReqConf.build());

			HttpResponse res;
			if (url.startsWith("https")) {
				// 执行 Https 请求.
				client = createSSLInsecureClient();
				res = client.execute(post);
			} else {
				// 执行 Http 请求.
				client = HttpClientUtil.client;
				res = client.execute(post);
			}
			result = IOUtils.toString(res.getEntity().getContent(), charset);
		} finally {
			post.releaseConnection();
			if (url.startsWith("https") && client != null && client instanceof CloseableHttpClient) {
				((CloseableHttpClient) client).close();
			}
		}
		return result;
	}

	/**
	 * 提交form表单
	 * 
	 * @param url
	 * @param params
	 * @param connTimeout
	 * @param readTimeout
	 * @return
	 * @throws ConnectTimeoutException
	 * @throws SocketTimeoutException
	 * @throws Exception
	 */
	private static String postForm(String url, Map<String, Object> params, Map<String, String> headers, Integer connTimeout,
			Integer readTimeout) throws ConnectTimeoutException, SocketTimeoutException, Exception {

		HttpClient client = null;
		HttpPost post = new HttpPost(url);
		try {
			if (params != null && !params.isEmpty()) {
				List<NameValuePair> formParams = new ArrayList<NameValuePair>(params.size());
				Set<Entry<String, Object>> entrySet = params.entrySet();
				for (Entry<String, Object> entry : entrySet) {
					formParams.add(new BasicNameValuePair(entry.getKey(), entry.getValue().toString()));
				}
				UrlEncodedFormEntity entity = new UrlEncodedFormEntity(formParams, Consts.UTF_8);
				post.setEntity(entity);
			}

			if (headers != null && !headers.isEmpty()) {
				for (Entry<String, String> entry : headers.entrySet()) {
					post.addHeader(entry.getKey(), entry.getValue());
				}
			}
			// 设置参数
			Builder customReqConf = RequestConfig.custom();
			if (connTimeout != null) {
				customReqConf.setConnectTimeout(connTimeout);
			}
			if (readTimeout != null) {
				customReqConf.setSocketTimeout(readTimeout);
			}
			post.setConfig(customReqConf.build());
			HttpResponse res = null;
			if (url.startsWith("https")) {
				// 执行 Https 请求.
				client = createSSLInsecureClient();
				res = client.execute(post);
			} else {
				// 执行 Http 请求.
				client = HttpClientUtil.client;
				res = client.execute(post);
			}
			return IOUtils.toString(res.getEntity().getContent(), charset);
		} finally {
			post.releaseConnection();
			if (url.startsWith("https") && client != null && client instanceof CloseableHttpClient) {
				((CloseableHttpClient) client).close();
			}
		}
	}

	private static String get(String url, Map<String, Object> paramMap, String charset, Integer connTimeout, Integer readTimeout)
			throws ConnectTimeoutException, SocketTimeoutException, Exception {

		if (paramMap != null && paramMap.size() > 0) {
			List<NameValuePair> pairs = new ArrayList<NameValuePair>(paramMap.size());
			for (String key : paramMap.keySet()) {
				pairs.add(new BasicNameValuePair(key, paramMap.get(key).toString()));
			}
			url += "?" + EntityUtils.toString(new UrlEncodedFormEntity(pairs, charset));
		}

		return get(url, charset, connTimeout, readTimeout);
	}

	/**
	 * 发送一个 GET 请求
	 * 
	 * @param url
	 * @param charset
	 * @param connTimeout 建立链接超时时间,毫秒.
	 * @param readTimeout 响应超时时间,毫秒.
	 * @return
	 * @throws ConnectTimeoutException 建立链接超时
	 * @throws SocketTimeoutException 响应超时
	 * @throws Exception
	 */
	private static String get(String url, String charset, Integer connTimeout, Integer readTimeout)
			throws ConnectTimeoutException, SocketTimeoutException, Exception {

		HttpClient client = null;
		HttpGet get = new HttpGet(url);
		String result = "";
		try {
			// 设置参数
			Builder customReqConf = RequestConfig.custom();
			if (connTimeout != null) {
				customReqConf.setConnectTimeout(connTimeout);
			}
			if (readTimeout != null) {
				customReqConf.setSocketTimeout(readTimeout);
			}
			get.setConfig(customReqConf.build());

			HttpResponse res = null;

			if (url.startsWith("https")) {
				// 执行 Https 请求.
				client = createSSLInsecureClient();
				res = client.execute(get);
			} else {
				// 执行 Http 请求.
				client = HttpClientUtil.client;
				res = client.execute(get);
			}

			result = IOUtils.toString(res.getEntity().getContent(), charset);
		} finally {
			get.releaseConnection();
			if (url.startsWith("https") && client != null && client instanceof CloseableHttpClient) {
				((CloseableHttpClient) client).close();
			}
		}
		return result;
	}

	/**
	 * 发送文件
	 * @param url
	 * @param params
	 * @param file
	 * @return
	 * @throws Exception
	 */
	public static String postFile(String url, Map<String, Object> params, File file) throws Exception {
		HttpClient client = null;
		HttpPost post = new HttpPost(url);
		try {
			// 设置参数
			Builder customReqConf = RequestConfig.custom();
			customReqConf.setConnectTimeout(connTimeout);
			customReqConf.setSocketTimeout(readTimeout);

			post.setConfig(customReqConf.build());

			MultipartEntityBuilder builder = MultipartEntityBuilder.create();
			if (params != null && params.size() > 0) {
				// 设置其他参数
				for (Entry<String, Object> entry : params.entrySet()) {
					builder.addTextBody(entry.getKey(), entry.getValue().toString(), ContentType.TEXT_PLAIN.withCharset(charset));
				}
			}

			if (file != null && file.isFile() && file.length() > 0) {
				// 加入file
				builder.addBinaryBody("file", file);
			}
			post.setEntity(builder.build());

			HttpResponse res = null;
			if (url.startsWith("https")) {
				// 执行 Https 请求.
				client = createSSLInsecureClient();
				res = client.execute(post);
			} else {
				// 执行 Http 请求.
				client = HttpClientUtil.client;
				res = client.execute(post);
			}
			return IOUtils.toString(res.getEntity().getContent(), charset);
		} finally {
			post.releaseConnection();
			if (url.startsWith("https") && client != null && client instanceof CloseableHttpClient) {
				((CloseableHttpClient) client).close();
			}
		}
	}

	/**
	 * 从 response 里获取 charset
	 * 
	 * @param ressponse
	 * @return
	 */
	@SuppressWarnings("unused")
	private static String getCharsetFromResponse(HttpResponse ressponse) {
		// Content-Type:text/html; charset=GBK
		if (ressponse.getEntity() != null && ressponse.getEntity().getContentType() != null
				&& ressponse.getEntity().getContentType().getValue() != null) {
			String contentType = ressponse.getEntity().getContentType().getValue();
			if (contentType.contains("charset=")) {
				return contentType.substring(contentType.indexOf("charset=") + 8);
			}
		}
		return null;
	}

	/**
	 * 创建 SSL连接
	 * 
	 * @return
	 * @throws GeneralSecurityException
	 */
	private static CloseableHttpClient createSSLInsecureClient() throws GeneralSecurityException {
		try {
			SSLContext sslContext = SSLContext.getInstance("SSL");

			sslContext.init(null, new TrustManager[] { new TrustAnyTrustManager() }, new java.security.SecureRandom());

			return HttpClients.custom().setSSLContext(sslContext).build();

		} catch (GeneralSecurityException e) {
			throw e;
		}
	}

	// 自定义私有类
	private static class TrustAnyTrustManager implements X509TrustManager {

		public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {
		}

		public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {
		}

		public X509Certificate[] getAcceptedIssuers() {
			return new X509Certificate[] {};
		}
	}

	public static void main(String[] args) {
		try {
			// String str = post("https://localhost:443/ssl/test.shtml", "name=12&page=34", "application/x-www-form-urlencoded",
			// "UTF-8", 10000, 10000);
			// String str= get("https://localhost:443/ssl/test.shtml?name=12&page=34","GBK");
			/*
			 * Map<String,String> map = new HashMap<String,String>(); map.put("name", "111"); map.put("page", "222"); String str=
			 * postForm("https://localhost:443/ssl/test.shtml",map,null, 10000, 10000);
			 */

			String str = post(
					"http://yun.5tree.cn/esb/api/student/getStudentsByRange?token=474525a2edc4caf9c3fd4d3fb1ecdbd4_1517815973284_119438_0_18becc39&udid=e277ede5-ec69-4426-b680-730b18becc39&version=3",
					"{\"c\":[78839,78836],\"s\":[2299,2302],\"g\":[1027,1028,1034,1035,1036,908]}", "application/json", "UTF-8",
					10000, 10000);

			System.out.println(str);
		} catch (ConnectTimeoutException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SocketTimeoutException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
