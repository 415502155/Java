package com.bestinfo.gambler.all;

import java.io.IOException;
import java.nio.charset.UnsupportedCharsetException;
import org.apache.commons.httpclient.MultiThreadedHttpConnectionManager;
import java.security.PrivateKey;
import org.apache.commons.httpclient.params.HttpConnectionManagerParams;

import org.apache.commons.httpclient.DefaultHttpMethodRetryHandler;
import org.apache.commons.httpclient.HttpConnectionManager;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.RequestEntity;
import org.apache.commons.httpclient.params.HttpMethodParams;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.ParseException;
import org.apache.http.client.CookieStore;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.protocol.ClientContext;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.HttpContext;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;
import org.apache.commons.httpclient.methods.StringRequestEntity;

public class HttpUtil {

    private static final Logger logger = Logger.getLogger(HttpUtil.class);
//    private static final String KEY_STORE_TYPE_JKS = "jks";
    private static final String KEY_STORE_TYPE_P12 = "PKCS12";
    private static final String SCHEME_HTTPS = "https";
    private static final int HTTPS_PORT = 443;
//    private static final String HTTPS_URL = "https://192.168.0.246/LTSHAPPY/forwardForwardAction.action";
    public static String KEY_STORE_CLIENT_PATH = "";
//    public static String KEY_STORE_TRUST_PATH = "";
    public static String KEY_STORE_PASSWORD = "";
//    public static String KEY_STORE_TRUST_PASSWORD = "";
    public static PrivateKey priKey = null;
//public static PoolingClientConnectionManager cm ;
    private final static HttpConnectionManager cm;

    static {
        cm = new MultiThreadedHttpConnectionManager();
        HttpConnectionManagerParams params = cm.getParams();
        params.setConnectionTimeout(10000);
        params.setSoTimeout(600000);
        params.setDefaultMaxConnectionsPerHost(1000);
        params.setMaxTotalConnections(1000);
        // 使用DefaultHttpMethodRetryHandler是希望在发送失败后能够自动重新发送
        params.setParameter(HttpMethodParams.RETRY_HANDLER, new DefaultHttpMethodRetryHandler());
        cm.setParams(params);
    }

//    public static void initPoolConn(String url, int port) {
//        //参数含义解释 http://xcxn.iteye.com/blog/1826759
//        BasicHttpParams params = new BasicHttpParams();
//        params.setBooleanParameter(CoreConnectionPNames.TCP_NODELAY, true);
////        params.setIntParameter(CoreConnectionPNames.SOCKET_BUFFER_SIZE, 64 * 1024);
//        params.setIntParameter(CoreConnectionPNames.SO_TIMEOUT, 30 * 1000); //数据传输时间
//        params.setIntParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, 30 * 1000); // 连接时间
//
//        params.setParameter(HttpProtocolParams.PROTOCOL_VERSION, HttpVersion.HTTP_1_1);
//        params.setBooleanParameter(HttpProtocolParams.USE_EXPECT_CONTINUE, true);
//        params.setBooleanParameter(CoreConnectionPNames.STALE_CONNECTION_CHECK, false);
//
//        SchemeRegistry schemeRegistry = new SchemeRegistry();
//        schemeRegistry.register(new Scheme("http", 80, PlainSocketFactory.getSocketFactory()));
////        Scheme sch = readsslfile();
////        if (sch == null) {
////            logger.error("证书错误");
////            return;
////        }
////        schemeRegistry.register(sch);
//        cm = new MultiThreadedHttpConnectionManager();
//        HttpConnectionManagerParams params = httpConnectionManager.getParams();
//        params.setConnectionTimeout(10000);
//        params.setSoTimeout(60000);
//        params.setDefaultMaxConnectionsPerHost(1000);
//        params.setMaxTotalConnections(500);
//        // 使用DefaultHttpMethodRetryHandler是希望在发送失败后能够自动重新发送
//        params.setParameter(HttpMethodParams.RETRY_HANDLER, new DefaultHttpMethodRetryHandler());
//         cm.setParams(params);
//    
//        
//         cm = new PoolingClientConnectionManager(schemeRegistry);
//        cm.setMaxTotal(800);
//        cm.setDefaultMaxPerRoute(800);
//
//        StaticVariable.httpclient = new DefaultHttpClient(cm, params);
//    }
    public static String httpSend(String xml, String url, boolean login) {
        HttpPost httppost = null;
//        HttpClient httpclient = StaticVariable.httpclient;
        org.apache.commons.httpclient.HttpClient httpclient = new org.apache.commons.httpclient.HttpClient(cm);
//        if (httpclient == null) { //add for not init StaticVariable
//              httpclient = new DefaultHttpClient();
//        }
        try {
            // logger.warn(xml);
//            if (url.split(":")[0].equals("http")) {
//                httpclient = StaticVariable.httpclient;
//            } else if (url.split(":")[0].equals("https")) {
//                httpclient = getsslHttpclient();
//            }
            // 创建一个本地Cookie存储的实例
            if (StaticVariable.cookieStore == null && login) {//单用户登录，需要记cookie才记
                logger.info("创建cookie");
                StaticVariable.cookieStore = new BasicCookieStore();
            }
            //创建一个本地上下文信息
            HttpContext localContext = new BasicHttpContext();
            //在本地上下问中绑定一个本地存储
            localContext.setAttribute(ClientContext.COOKIE_STORE, StaticVariable.cookieStore);

            StringEntity myEntity = new StringEntity(xml, ContentType.create("text/xml", "UTF-8"));
            httppost = new HttpPost(url);
            httppost.setEntity(myEntity);

            RequestEntity requestEntity = new StringRequestEntity(xml);
            //  EntityEnclosingMethod postMethod = new PostMethod();
            PostMethod postMethod = new PostMethod();
            postMethod.setRequestEntity(requestEntity);
            postMethod.setPath(url);

            int i = httpclient.executeMethod(postMethod);
            String result = null;
            int code = postMethod.getStatusCode();
            if (code == 200) {
                return postMethod.getResponseBodyAsString();
//                HttpEntity entity = hr.getEntity();
//                result = EntityUtils.toString(entity);
                // EntityUtils.consume(entity);

            } else {
                logger.error("code:" + code);
                return "sendError";
            }

//            if (result == null) {
//                logger.error("response is null,xml:" + xml);
//                return null;
//            }
//            if (result.trim().equals("null") || result.trim().isEmpty()) {
//                logger.error("response is null,xml:" + xml);
//                return null;
//            } else if (result.startsWith("<")) {
//               // logger.warn(result);
//                return result;
//            } else {
//                logger.error(result + ",xml:" + xml);
//                return null;
//            }
        } catch (IOException | UnsupportedCharsetException | ParseException e) {
            logger.error("", e);
            return "sendError";
        } finally {
            if (httppost != null) {
                httppost.releaseConnection();
            }
        }
    }

    public static String httpSend(String xml, String url, CookieStore cookieStore) {
        HttpPost httppost = null;
        HttpClient httpclient = StaticVariable.httpclient;
        if (httpclient == null) { //add for not init StaticVariable
            httpclient = new DefaultHttpClient();
        }

        try {
            //  logger.warn(xml);
//            if (url.split(":")[0].equals("http")) {
//                httpclient = new DefaultHttpClient();
//            } else if (url.split(":")[0].equals("https")) {
//                httpclient = getsslHttpclient();
//            }
            //创建一个本地上下文信息
            HttpContext localContext = new BasicHttpContext();
            //在本地上下问中绑定一个本地存储
            localContext.setAttribute(ClientContext.COOKIE_STORE, cookieStore);

            StringEntity myEntity = new StringEntity(xml, ContentType.create("text/xml", "UTF-8"));
            httppost = new HttpPost(url);
            httppost.setEntity(myEntity);
            HttpResponse hr = httpclient.execute(httppost, localContext);
            String result = null;
            int code = hr.getStatusLine().getStatusCode();
            if (code == 200) {
                HttpEntity entity = hr.getEntity();
                result = EntityUtils.toString(entity);
                EntityUtils.consume(entity);
            } else {
                logger.error("code:" + code);
                return "sendError";
            }
            if (result == null) {
                logger.error("response is null,xml:" + xml);
                return null;
            }
            if (result.trim().equals("null") || result.trim().isEmpty()) {
                logger.error("response is null,xml:" + xml);
                return null;
            } else if (result.startsWith("<")) {
                // logger.warn(result);
                return result;
            } else {
                logger.error(result + ",xml:" + xml);
                return null;
            }
        } catch (IOException | UnsupportedCharsetException | ParseException e) {
            logger.error("", e);
            return "sendError";
        } finally {
            if (httppost != null) {
                httppost.releaseConnection();
            }

        }
    }

//    private static class MySSLSocketFactory extends SSLSocketFactory {
//
//        SSLContext sslContext = SSLContext.getInstance("TLS");
//
//        public MySSLSocketFactory(KeyStore keystore, String keystorePassword, KeyStore truststore) throws NoSuchAlgorithmException, KeyManagementException, KeyStoreException, UnrecoverableKeyException {
//            super(keystore,keystorePassword,truststore);
//            TrustManager tm = new X509TrustManager() {
//                @Override
//                public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {
//                }
//
//                @Override
//                public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {
//                }
//
//                @Override
//                public X509Certificate[] getAcceptedIssuers() {
//                    return null;
//                }
//            };
//            sslContext.init(null, new TrustManager[]{tm}, null);
//        }
//
//        @Override
//        public Socket createSocket(Socket socket, String host, int port, boolean autoClose) throws IOException, UnknownHostException {
//            return sslContext.getSocketFactory().createSocket(socket, host, port, autoClose);
//        }
//
//        @Override
//        public Socket createSocket() throws IOException {
//            return sslContext.getSocketFactory().createSocket();
//        }
//    }
//    private static Scheme readsslfile1() {
//        try {
//            KeyManagerFactory kmf = KeyManagerFactory.getInstance(ALGORITHM);
//            TrustManagerFactory tmf = TrustManagerFactory.getInstance(ALGORITHM);
//
//            KeyStore keyStore = KeyStore.getInstance(KEY_STORE_TYPE_P12);
//            InputStream ksIn = new FileInputStream(KEY_STORE_CLIENT_PATH);
//            try {
//                keyStore.load(ksIn, KEY_STORE_PASSWORD.toCharArray());
//            } finally {
//                ksIn.close();
//            }
//            kmf.init(keyStore, KEY_STORE_PASSWORD.toCharArray());
//            tmf.init(keyStore);
//            X509TrustManager defaultTrustManager = (X509TrustManager) tmf.getTrustManagers()[0];
//            SavingTrustManager tm = new SavingTrustManager(defaultTrustManager);
//
//            SSLContext sslc = SSLContext.getInstance("SSL");
//            sslc.init(kmf.getKeyManagers(), new TrustManager[]{tm}, new java.security.SecureRandom());
//
//            SocketFactory sf = sslc.getSocketFactory();
//
//            KeyStore jskStore = createJKSHelp(keyStore, KEY_STORE_PASSWORD);
//            if (null == jskStore) {
//                logger.error("jskStore is null");
//                return null;
//            }
//
//            SSLSocketFactory socketFactory = new SSLSocketFactory(null, keyStore, KEY_STORE_PASSWORD, jskStore, null, SSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);
//            Scheme sch = new Scheme(SCHEME_HTTPS, HTTPS_PORT, socketFactory);
//            return sch;
//        } catch (Exception e) {
//            logger.error("", e);
//            return null;
//        }
//    }
    /**
     * 读取证书
     *
     * @return
     */
//    private static Scheme readsslfile() {
//        try {
////            KeyManagerFactory kmf = KeyManagerFactory.getInstance(ALGORITHM);
////            TrustManagerFactory tmf = TrustManagerFactory.getInstance(ALGORITHM);
////            X509TrustManager defaultTrustManager = (X509TrustManager) tmf.getTrustManagers()[0];
////            SavingTrustManager tm = new SavingTrustManager(defaultTrustManager);
//
//            KeyStore keyStore = KeyStore.getInstance(KEY_STORE_TYPE_P12);
//            InputStream ksIn = new FileInputStream(KEY_STORE_CLIENT_PATH);
//            try {
//                keyStore.load(ksIn, KEY_STORE_PASSWORD.toCharArray());
//            } finally {
//                ksIn.close();
//            }
//            KeyStore jskStore = createJKSHelp(keyStore, KEY_STORE_PASSWORD);
//            if (null == jskStore) {
//                logger.error("jskStore is null");
//                return null;
//            }
//
//            SSLSocketFactory socketFactory = new SSLSocketFactory(null, keyStore, KEY_STORE_PASSWORD, jskStore, null, SSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);
////            SSLSocketFactory socketFactory = new MySSLSocketFactory(keyStore, KEY_STORE_PASSWORD, jskStore);
////            socketFactory.setHostnameVerifier(SSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);
//            Scheme sch = new Scheme(SCHEME_HTTPS, HTTPS_PORT, socketFactory);
//            return sch;
//        } catch (Exception e) {
//            logger.error("", e);
//            return null;
//        }
//    }
    /**
     * 获取证书
     *
     * @param keyStore
     * @param certificatePwd
     * @return
     */
//    private static KeyStore createJKSHelp(KeyStore keyStore, String certificatePwd) {
//        try {
//            Enumeration enume = keyStore.aliases();
//            String keyAlias = (String) enume.nextElement();
//            Certificate[] certs = keyStore.getCertificateChain(keyAlias);//CN名,通用名
//            X509Certificate[] x509Certs = X509CertUtil.convertCertificates(certs);//得出多个X509证书，0.证书，1.ca证书
//            X509Certificate cert = x509Certs[0];
//            if (cert == null) {
//                throw new IllegalArgumentException("Parameter cert cannot be null.");
//            }
//            Key priKey = keyStore.getKey(keyAlias, KEY_STORE_PASSWORD.toCharArray());//得出私钥Key
//
//            BouncyCastleProvider bc = new org.bouncycastle.jce.provider.BouncyCastleProvider();
//            Security.addProvider(bc);
//            final String caAlias = "cacert";
//            int len = 1;
//            if (certs != null) {
//                len += certs.length;
//            }
//            final Certificate[] chain = new Certificate[len];
//            chain[0] = cert;
//            if (certs != null) {
//                for (int i = 0; i < certs.length; i++) {
//                    chain[i + 1] = certs[i];
//                }
//            }
//            // store the key and the certificate chain
//            final KeyStore store = KeyStore.getInstance("JKS");
//            store.load(null, null);
//
//            // First load the key entry
//            final X509Certificate[] usercert = new X509Certificate[1];
//            usercert[0] = cert;
//            store.setKeyEntry(keyAlias, priKey, certificatePwd.toCharArray(), usercert);
//            if (certs != null) {
//                if (!getDN(certs[certs.length - 1], 1).equals(getDN(certs[certs.length - 1], 2))) {
//                    throw new IllegalArgumentException("Root cert is not self-signed.");
//                }
//                store.setCertificateEntry(caAlias, certs[certs.length - 1]);
//            }
//            // Set the complete chain
//            store.setKeyEntry(keyAlias, priKey, certificatePwd.toCharArray(), chain);
//            return store;
//        } catch (Exception e) {
//            logger.error("", e);
//            return null;
//        }
//    }
//    private static String getDN(Certificate cert, int which) {
//        String ret = null;
//        if (cert == null) {
//            return null;
//        }
//        if (cert instanceof X509Certificate) {
//            try {
//                CertificateFactory cf = getCertificateFactory("BC");
//                X509Certificate x509cert = (X509Certificate) cf.generateCertificate(new ByteArrayInputStream(cert.getEncoded()));
//                String dn = null;
//                if (which == 1) {
//                    dn = x509cert.getSubjectDN().toString();
//                } else {
//                    dn = x509cert.getIssuerDN().toString();
//                }
//                ret = dn;
//            } catch (CertificateException ce) {
//                return null;
//            }
//        }
//        return ret;
//    }
//    private static CertificateFactory getCertificateFactory(String provider) {
//        String prov = provider;
//        if (provider == null) {
//            prov = "BC";
//        }
//        if (StringUtils.equals(prov, "BC")) {
//            installBCProvider();
//        }
//        try {
//            return CertificateFactory.getInstance("X.509", prov);
//        } catch (Exception ce) {
//            ce.printStackTrace();
//        }
//        return null;
//    }
//    public static synchronized void installBCProvider() {
//        boolean installImplicitlyCA = false;
//        if (Security.addProvider(new BouncyCastleProvider()) < 0) {
//            Security.removeProvider("BC");
//            Security.removeProvider("CVC");
//            Security.addProvider(new BouncyCastleProvider());
//            installImplicitlyCA = true;
//        } else {
//            installImplicitlyCA = true;
//        }
//        if (installImplicitlyCA) {
//            final ECCurve curve = new ECCurve.Fp(
//                    new BigInteger("883423532389192164791648750360308885314476597252960362792450860609699839"), // q
//                    new BigInteger("7fffffffffffffffffffffff7fffffffffff8000000000007ffffffffffc", 16), // a
//                    new BigInteger("6b016c3bdcf18941d0d654921475ca71a9db2fb27d1d37796185c2942c0a", 16)); // b
//            final org.bouncycastle.jce.spec.ECParameterSpec implicitSpec = new org.bouncycastle.jce.spec.ECParameterSpec(
//                    curve,
//                    curve.decodePoint(Hex.decode("020ffa963cdca8816ccc33b8642bedf905c3d358573d3f27fbbd3b3cb9aaaf")), // G
//                    new BigInteger("883423532389192164791648750360308884807550341691627752275345424702807307")); // n
//            final ConfigurableProvider config = (ConfigurableProvider) Security.getProvider("BC");
//            if (config != null) {
//                config.setParameter(ConfigurableProvider.EC_IMPLICITLY_CA, implicitSpec);
//            } else {
//                logger.error("Can not get ConfigurableProvider, implicitlyCA EC parameters NOT set!");
//            }
//        }
//        X509Name.DefaultSymbols.put(X509Name.SN, "SN");
//    }
//    public static void main(String[] args) throws Exception {
//        String xml = "action=212&<?xml version=\"1.0\" encoding=\"UTF-8\"?><pkg><pkgH><type>4</type><action>212</action><version></version><dealer_id>140</dealer_id><terminal_id></terminal_id><mobile></mobile><phone></phone><sent_time>2014-02-24 14:45:24</sent_time></pkgH><pkgC><user>140华祥</user><pwd>56d4566d5651cc7a59ae85e976313cca04650997004db118c08822f4ffced830c3e45fd8c44cd842e0a1f1f5b27fdc2d797c7e16ee1d0a022f059d1a7579c9cc0bb45a810186765cb760d920e262de4f25fac20142f5ce762b19415c08df2991231065354c0b4a8b8eb3452fb29d324ace65cca1ff8236400136fb0373d89e73</pwd></pkgC></pkg>";
//        //121.33.237.103:8403
//        HttpUtil.httpSend(xml, "https://121.33.237.103:8403/LTSHAPPY/ForwardAction.action", false);
//
//    }
    /**
     * 使用证书给给定字符加密
     *
     * @param planText 待加密字符
     * @param keypath 证书文件名 为null，使用https使用证书的私钥，也可以指定证书
     * @return
     */
//    public static String encryptPwd(String planText, String keypath) {
//        BouncyCastleProvider bc = new org.bouncycastle.jce.provider.BouncyCastleProvider();
//        Security.addProvider(bc);
//        try {
//            if (Security.getProvider(bc.getName()) == null) {
//                logger.error("null");
//                return null;
//            }
//            KeyStore ks = KeyStore.getInstance(KEY_STORE_TYPE_P12);
//            String keypathS = KEY_STORE_CLIENT_PATH;
//            String keypathP = KEY_STORE_PASSWORD;
//            if (keypath != null) {
//                keypathS = StaticVariable.KEYPATH + keypath + ".p12";
////                System.out.println(keypathS);
////                keypathP = keypath + "pwd";
//            }
//            FileInputStream fls = new FileInputStream(keypathS);
//            char[] pwd = keypathP.toCharArray();
//            ks.load(fls, pwd);
//            fls.close();
//
//            Enumeration enume = ks.aliases();
//            String keyAlias = null;
//            while (enume.hasMoreElements()) {
//                keyAlias = (String) enume.nextElement();
//            }
//            Key privateKey = ks.getKey(keyAlias, keypathP.toCharArray());//得出私钥Key
//            return new CAUtil().encryptCipher(privateKey, planText);
//
//        } catch (KeyStoreException | IOException | NoSuchAlgorithmException | CertificateException | UnrecoverableKeyException ex) {
//            logger.error("p12 parse error", ex);
//            return null;
//        }
//    }
}
