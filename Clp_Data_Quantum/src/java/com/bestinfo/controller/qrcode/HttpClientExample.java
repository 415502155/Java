package com.bestinfo.controller.qrcode;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.Provider;
import java.security.Security;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLException;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocket;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLContexts;
import org.apache.http.conn.ssl.X509HostnameVerifier;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.protocol.HttpContext;
import org.apache.http.protocol.HttpCoreContext;
import org.apache.http.util.EntityUtils;

/**
 * 这里假设server端的Https配置已经设置好<br>
 * 以下内容为Ukey调用HttpClient进行Https链接
 * 
 * @author yangqiju
 *
 */
public class HttpClientExample {

	private static final String[] TLS_Protocols = { "TLSv1.2" };
        private static final String ukeyPassword = "12345678";//TODO ukey pin码
	public static SSLContext getSSLContext() throws KeyManagementException,
			KeyStoreException, NoSuchProviderException,
			NoSuchAlgorithmException, CertificateException, IOException,
			UnrecoverableKeyException {
		//提供pkcs11 的基本配置,library 指定了Ukey的驱动程序地址，不同的厂商提供的驱动不同
		//name 对ukey进行标识
		String pkcs11config ="library = F:\\gm3000_pkcs11_tw.dll\n"+"name = safenetSC\n";
//                String pkcs11config = "library = /usr/lib64/libgm3000_pkcs11.so\n"
//				+ "name = safenetSC\n";
                //libgm3000_pkcs11.so

		Provider provider = new sun.security.pkcs11.SunPKCS11(
				new ByteArrayInputStream(pkcs11config.getBytes()));
		Security.addProvider(provider);// 添加支持

		//调用上面设置的标识 SunPKCS11-safenetSC 
		KeyStore pkcss11KS = KeyStore.getInstance("PKCS11",
				"SunPKCS11-safenetSC");
		//ukey 的pin码   12345678
		pkcss11KS.load(null, ukeyPassword.toCharArray());

		KeyManagerFactory kmf = KeyManagerFactory.getInstance("SunX509");
		kmf.init(pkcss11KS, null);

		SSLContext defaultSSLContexts = SSLContexts.createDefault();
		javax.net.ssl.TrustManager[] trustAllCerts = new javax.net.ssl.TrustManager[] { new AllTrustManager() };
		defaultSSLContexts.init(kmf.getKeyManagers(), trustAllCerts, null);
		return defaultSSLContexts;
	}

	public static void main(String[] args) throws KeyStoreException,
			NoSuchProviderException, NoSuchAlgorithmException,
			CertificateException, IOException, UnrecoverableKeyException,
			KeyManagementException {
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

		//https 的地址
		String url = "https://219.143.230.77:4481/lotteryPromotion/keyRegistration";
		HttpGet httpGet = new HttpGet(url);
                //HttpPost httpPost = new HttpPost(url);
		//user token 是为了让链接保持长连接的重复使用
		// 需要设置 userToken属性，否则每次请求时都需要重新建立连接
		// userToken 是证书中的名称
		// 如果使用nginx keepalive，nginx默认 100次请求会断开连接，需要设置keepalive_requests 参数。
		
		HttpContext context = new HttpCoreContext();
		context.setAttribute(HttpClientContext.USER_TOKEN,"CN=yangqiju.joyveb.com");
                CloseableHttpResponse response = httpclient.execute(httpGet,context);
		String responseMsg = EntityUtils.toString(response.getEntity(),"UTF-8");
		System.out.println("response:" + responseMsg);
		httpclient.close();
	}

}