package sng.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.URL;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.json.XML;

import sng.entity.Account;
import sng.entity.ChargeDetail;
import sng.entity.ChargeRecord;
import unionpay.acp.sdk.LogUtil;

/**
 * Title: CebUtils
 * Description:光大支付业务相关的Util 
 * Company: 世纪伟业
 * @author Liu Yang
 * @date 2018年9月13日下午2:56:14
 */
public class CebUtils {
	

	/**
	 * 发送XML请求并解析返回的XML
	 * @param url
	 * @param postXML
	 * @return
	 */
	public static org.json.JSONObject getJSONObjectByPostXML(String url,String postXML){
		
		try {
			String data = HttpRequest.post(url).connectTimeout(300000).readTimeout(300000).trustAllHosts().trustAllCerts().send(postXML).body();
			LogUtil.writeErrorLog("【光大退费】data:"+data.toString());
			System.out.println("data"+data.toString());
			return XML.toJSONObject(data.toString());
			
			
			/*System.out.println("1111111111111111111111111111111111111111111111");

			Map<String,String> header = new HashMap<String,String>();
			header.put("contentType", "text/xml");
			header.put("Charset", "GBK");
			header.put("Connection", "Keep-Alive");
			header.put("Content-Length", String.valueOf(postXML.getBytes().length));
			System.out.println(header.values().toString());
			
			String recieveData = HttpRequest.post(url).headers(header).trustAllCerts().send(postXML.getBytes()).body();
			System.out.println("22222222222222222222222222222222"+recieveData);
			return XML.toJSONObject(recieveData.toString());*/
	        /*URL postUrl=new URL(url);
	        System.out.println("urlurlurlurlurlurlurlurlurlurlurlurlurlurlurlurlurl"+url);
	        HttpsURLConnection conn=(HttpsURLConnection) postUrl.openConnection();
	        conn.setRequestMethod("POST");//设定请求方法
	        conn.setConnectTimeout(20000);
	        conn.setReadTimeout(20000);
	        conn.setDoInput(true);//打开输入流
	        conn.setDoOutput(true);//打开输出流写入写出参数必需
	        conn.setSSLSocketFactory(trustAllHosts(conn));//添加ssl参数
            System.out.println("222222222222222222222222222222222222222222"+postXML);
            
            conn.setRequestProperty("Connection", "Keep-Alive");
            conn.setRequestProperty("Charset", "GBK");
            byte[] data = postXML.getBytes();
            System.out.println("xxxxxxxxxxxxxxxxxxxxxxxxxxxxxx"+data.toString());
            conn.setRequestProperty("Content-Length", String.valueOf(data.length));
            conn.setRequestProperty("contentType", "text/xml");
            try {
            	conn.connect();
			} catch (Exception e) {
				e.printStackTrace();
			}
            System.out.println("33333333333333333333333333333333333333");
            OutputStream  out = conn.getOutputStream();     
            System.out.println("44444444444444444444444444444"+data.toString());
            out.write(data);
            System.out.println("3333333333333333333333data"+data.toString());
            out.flush();
            out.close();
            System.out.println(conn.getResponseCode());
            if (conn.getResponseCode() == 200) {
                System.out.println("连接成功");
                String inputLine = null;
                StringBuffer recieveData = new StringBuffer();
                InputStream in = conn.getInputStream();
                BufferedReader br = null;
        		try {
        			br = new BufferedReader(new InputStreamReader(in, "GBK"));
        			while ((inputLine = br.readLine()) != null) {
        				recieveData.append(inputLine);
        			}
        		} catch (IOException e) {
        			return null;
        		} finally {
        			try {
        				if (null != in) {
        					in.close();
        				}
        			} catch (IOException e) {
        				return null;
        			}
        		}
        		return XML.toJSONObject(recieveData.toString());
            } else {
                System.out.println("no++");
                return null;
            }*/
        } catch (Exception e) {
        	return null;
        }
	}
/*
	private static void trustAllHttpsCertificates()  throws NoSuchAlgorithmException, KeyManagementException {
		TrustManager[] trustAllCerts = new TrustManager[1]; 
		trustAllCerts[0] = new TrustAllManager(); 
		SSLContext sc = SSLContext.getInstance("SSL"); 
	    sc.init(null, trustAllCerts, null); 
	    HttpsURLConnection.setDefaultSSLSocketFactory(
	        sc.getSocketFactory());
	}
		 
		  private static class TrustAllManager implements X509TrustManager {
		    public X509Certificate[] getAcceptedIssuers() 
		    {
		      return null;
		    } 
		    public void checkServerTrusted(X509Certificate[] certs, 
		        String authType)
		      throws CertificateException 
		    {
		    } 
		    public void checkClientTrusted(X509Certificate[] certs, 
		        String authType)
		    throws CertificateException 
		    {
		    }
		  }
		  
    *//**
     * 覆盖java默认的证书验证
     *//*
	public static final TrustManager[] trustAllCerts = new TrustManager[]{new X509TrustManager() {
        public X509Certificate[] getAcceptedIssuers() {
        	X509Certificate[] aa = new X509Certificate[0];
        	try {
        		InputStream fileInputStream = ESBPropertyReader.class.getClassLoader().getResourceAsStream("cebip_server_test.cer");
				aa[0] = getX509CerCate(fileInputStream);
			} catch (Exception e) {
			}
            return aa;
        }

		@Override
		public void checkClientTrusted(X509Certificate[] arg0, String arg1) throws CertificateException {
		}

		@Override
		public void checkServerTrusted(X509Certificate[] arg0, String arg1) throws CertificateException {
		}
    }};
    
	public static X509Certificate getX509CerCate(InputStream fileInputStream) throws Exception {
		X509Certificate x509Certificate = null;
		CertificateFactory certificateFactory = CertificateFactory.getInstance("X.509");
//		FileInputStream fileInputStream = new FileInputStream(cerPath);
		x509Certificate = (X509Certificate) certificateFactory.generateCertificate(fileInputStream);
		fileInputStream.close();
		System.out.println("读取Cer证书信息...");
		System.out.println("x509Certificate_SerialNumber_序列号___:"+x509Certificate.getSerialNumber());
		System.out.println("x509Certificate_getIssuerDN_发布方标识名___:"+x509Certificate.getIssuerDN()); 
		System.out.println("x509Certificate_getSubjectDN_主体标识___:"+x509Certificate.getSubjectDN());
		System.out.println("x509Certificate_getSigAlgOID_证书算法OID字符串___:"+x509Certificate.getSigAlgOID());
		System.out.println("x509Certificate_getNotBefore_证书有效期___:"+x509Certificate.getNotAfter());
		System.out.println("x509Certificate_getSigAlgName_签名算法___:"+x509Certificate.getSigAlgName());
		System.out.println("x509Certificate_getVersion_版本号___:"+x509Certificate.getVersion());
		System.out.println("x509Certificate_getPublicKey_公钥___:"+x509Certificate.getPublicKey());
		return x509Certificate;
	}

    *//**
     * 设置不验证主机
     *//*
	public static final HostnameVerifier DO_NOT_VERIFY = new HostnameVerifier() {

		@Override
		public boolean verify(String hostname, SSLSession session) {
			return false;
		}
    };
    
    
    *//** 
     * 绕过验证 
     *   
     * @return 
     * @throws NoSuchAlgorithmException  
     * @throws KeyManagementException  
     *//*  
    public static SSLContext createIgnoreVerifySSL() throws NoSuchAlgorithmException, KeyManagementException {  
        SSLContext sc = SSLContext.getInstance("SSLv3");  
      
        // 实现一个X509TrustManager接口，用于绕过验证，不用修改里面的方法  
        X509TrustManager trustManager = new X509TrustManager() {  
            @Override  
            public void checkClientTrusted(  
                    java.security.cert.X509Certificate[] paramArrayOfX509Certificate,  
                    String paramString) throws CertificateException {  
            }  
      
            @Override  
            public void checkServerTrusted(  
                    java.security.cert.X509Certificate[] paramArrayOfX509Certificate,  
                    String paramString) throws CertificateException {  
            }  
      
            @Override  
            public java.security.cert.X509Certificate[] getAcceptedIssuers() {  
                return null;  
            }  
        };  
      
        sc.init(null, new TrustManager[] { trustManager }, null);  
        return sc;  
    }  
    
    
    public static String send(String url, Map<String,String> map,String encoding) throws KeyManagementException, NoSuchAlgorithmException, ClientProtocolException, IOException {  
        String body = "";  
        //采用绕过验证的方式处理https请求  
        SSLContext sslcontext = createIgnoreVerifySSL();  
          
           // 设置协议http和https对应的处理socket链接工厂的对象  
           Registry<ConnectionSocketFactory> socketFactoryRegistry = RegistryBuilder.<ConnectionSocketFactory>create()  
               .register("http", PlainConnectionSocketFactory.INSTANCE)  
               .register("https", new SSLConnectionSocketFactory(sslcontext))  
               .build();  
           PoolingHttpClientConnectionManager connManager = new PoolingHttpClientConnectionManager(socketFactoryRegistry);  
           HttpClients.custom().setConnectionManager(connManager);  
      
           //创建自定义的httpclient对象  
        CloseableHttpClient client = HttpClients.custom().setConnectionManager(connManager).build();  
    //       CloseableHttpClient client = HttpClients.createDefault();  
          
        //创建post方式请求对象  
        HttpPost httpPost = new HttpPost(url);  
          
        //装填参数  
        List<NameValuePair> nvps = new ArrayList<NameValuePair>();  
        if(map!=null){  
            for (Entry<String, String> entry : map.entrySet()) {  
                nvps.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));  
            }  
        }  
        //设置参数到请求对象中  
        httpPost.setEntity(new UrlEncodedFormEntity(nvps, encoding));  
        
        System.out.println("请求地址："+url);  
        System.out.println("请求参数："+nvps.toString());  
          
        //设置header信息  
        //指定报文头【Content-type】、【User-Agent】  
        httpPost.setHeader("Content-type", "text/xml");  
        httpPost.setHeader("Charset", "GBK");
        httpPost.setHeader("User-Agent", "Mozilla/4.0 (compatible; MSIE 5.0; Windows NT; DigExt)");  
        System.out.println("xxxxxxxxxxxxxxxxxxxxxxxxxxxxxx");
        
        
          
        //执行请求操作，并拿到结果（同步阻塞）  
        CloseableHttpResponse response = client.execute(httpPost);  
        //获取结果实体  
        HttpEntity entity = response.getEntity();  
        if (entity != null) {  
            //按指定编码转换结果实体为String类型  
            body = EntityUtils.toString(entity, encoding);  
        }  
        EntityUtils.consume(entity);  
        //释放链接  
        response.close();  
           return body;  
    }

    *//**
     * 信任所有
     * @param connection
     * @return
     *//*
    public static SSLSocketFactory trustAllHosts(HttpsURLConnection connection) {
        SSLSocketFactory oldFactory = connection.getSSLSocketFactory();
        try {
            SSLContext sc = SSLContext.getInstance("TLS");
            sc.init(null, trustAllCerts, new java.security.SecureRandom());
            SSLSocketFactory newFactory = sc.getSocketFactory();
            connection.setSSLSocketFactory(newFactory);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return oldFactory;
    }
    */
	/**
	 * 获取xml字符串
	 * @param type
	 * @param cd
	 * @param errorCode
	 * @param obj
	 * @param parent
	 * @return
	 */
    public static String getXML(String type,ChargeDetail cd, ChargeRecord cr){
		String xml = "<?xml version=\"1.0\" encoding=\"ISO-8859-1\" ?>";
		switch (type) {
		case Constant.CEB_TYPE_REFUND:
			try {
				xml += "<In><Head><FTranCode>"+type+"</FTranCode><InstID>"
						+ cd.getOrg_id() + "</InstID><TranDateTime>"
						+ Constant.ceb.format(cr.getTxnTime()) + "</TranDateTime><BankNum>"
						+ cr.getQueryId() +"</BankNum><AnsCode>"+Constant.CEB_ANSCODE_SUCCESS+"</AnsCode></Head><Body><billNum>"
						+ cd.getBillNum()+ "</billNum><TradeNo>"
						+ cr.getQueryId() + "</TradeNo><TradeDate>"
						+ Constant.ceb.format(cr.getTxnTime()) + "</TradeDate><totalFee>"
						+ cd.getMoney() + "</totalFee><refundNo>"
						+ ((int)(Math.random()*90+10))+Constant.ceb.format(new Date()).substring(2)+(new Date().getTime()+"").substring(6) + "</refundNo><refundFee>"
						+ MoneyUtil.fromFENtoYUAN(cr.getTxnAmt()) + "</refundFee><refundDate>"
						+ Constant.ceb.format(new Date()) + "</refundDate><areaCode>"
						+ Constant.CEB_AREA_CODE + "</areaCode><refundNote>默认退款</refundNote></Body></In>";
			} catch (Exception e) {
				e.printStackTrace();
			}
			break;
		case Constant.CEB_TYPE_QUERY_PAY:
			xml += "<In><Head><FTranCode>"+type+"</FTranCode><InstID>"
					+ cd.getOrg_id() + "</InstID><TranDateTime>"
					+ Constant.ceb.format(cd.getTxnTime()) + "</TranDateTime><BankNum>"
					+ cd.getQueryId() +"</BankNum><AnsCode>"+Constant.CEB_ANSCODE_SUCCESS+"</AnsCode></Head><Body><billNum>"
					+ cd.getBillNum() + "</billNum><payNo>"
					+ cd.getCd_id() + "</payNo><TradeDate>"
					+ Constant.ceb.format(cd.getTxnTime()) + "</TradeDate></Body></In>";
			break;
		case Constant.CEB_TYPE_QUERY_REFUND:
			xml += "<In><Head><FTranCode>"+type+"</FTranCode><InstID>"
					+ cd.getOrg_id() + "</InstID><TranDateTime>"
					+ Constant.ceb.format(cd.getTxnTime()) + "</TranDateTime><BankNum>"
					+ cd.getQueryId() +"</BankNum><AnsCode>"+Constant.CEB_ANSCODE_SUCCESS+"</AnsCode></Head><Body><billNum>"
					+ cd.getCd_id()+ "</billNum><refundNo>"
					+ cr.getQueryId() + "</refundNo><refundDate>"
					+ cr.getTxnTime() + "</refundDate></Body></In>";
			break;
		default:
			break;
		}
		return xml;
	}
	
    public static Map<String,String[]> getPaidBills(){
    	Map<String,String[]> map = new HashMap<String,String[]>();
    	
    	Date date=new Date();
    	Calendar calendar = Calendar.getInstance();
    	calendar.setTime(date);
    	calendar.add(Calendar.DATE, -1);
    	date = calendar.getTime();
        String filename = "SJWY_"+Constant.ceb.format(date)+"_10002.txt";
    	File file = new File(filename);
    	BufferedReader reader = null;
    	try {
    		reader = new BufferedReader(new FileReader(file));
    		String tempString = null;
    		String[] strs = null;
    		// 一次读入一行，直到读入null为文件结束
    		while ((tempString = reader.readLine()) != null) {
    			strs = tempString.split("|");
    			if(strs.length==6){
    				map.put(strs[2], strs);
    			}
    		}
    		reader.close();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (reader != null) {
				try {
					reader.close();
				} catch (IOException e1) {
				}
			}
		}
    	return map;
    }
}