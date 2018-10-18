package com.bestinfo.controller.qrcode;
/**
 * 为了测试，对证书都不校验
 * @author yangqiju
 *
 */
public class AllTrustManager implements javax.net.ssl.TrustManager,
		javax.net.ssl.X509TrustManager {
	public java.security.cert.X509Certificate[] getAcceptedIssuers() {
		return null;
	}

	public boolean isServerTrusted(java.security.cert.X509Certificate[] certs) {
		return true;
	}

	public boolean isClientTrusted(java.security.cert.X509Certificate[] certs) {
		return true;
	}

	public void checkServerTrusted(java.security.cert.X509Certificate[] certs,
			String authType) throws java.security.cert.CertificateException {
		return;
	}

	public void checkClientTrusted(java.security.cert.X509Certificate[] certs,
			String authType) throws java.security.cert.CertificateException {
		return;
	}
}