package com.bestinfo.controller.qrcode;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.net.URLEncoder;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.Provider;
import java.security.Security;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.Enumeration;

public class UkeyAliasId {
	
	private static final String ukeyPassword = "12345678";//TODO ukey pin码
	
	public static void main(String[] args) throws Exception {
            getKey("12345678");
		//提供pkcs11 的基本配置,library 指定了Ukey的驱动程序地址,不同的厂商提供的驱动不同
		//name 对ukey进行标识
//		String pkcs11config_bak = "library = /usr/local/lib/libeTPkcs11.dylib\n"
//				+ "name = safenetSC\n";  C:\Windows\System32\gm3000_pkcs11_tw.dll C:\Windows\SysWOW64
//                String pkcs11config ="library = F:\\gm3000_pkcs11_tw.dll\n"+"name = safenetSC\n";
//                System.out.println("a:"+pkcs11config);
//                //System.out.println("zhe shi shen me dong dong:"+pkcs11config_bak);
//		Provider testProvider = new sun.security.pkcs11.SunPKCS11(new ByteArrayInputStream(pkcs11config.getBytes()));
//                System.out.println("zhege shi what:"+testProvider);
//		Security.addProvider(testProvider);//添加支持
//		//调用上面设置的标识 SunPKCS11-safenetSC 
//		KeyStore testKeystore = KeyStore.getInstance("PKCS11","SunPKCS11-safenetSC");
//		
//		testKeystore.load(null, ukeyPassword.toCharArray());
//		
//		Enumeration<String> aliasesEnum = testKeystore.aliases();
//		while (aliasesEnum.hasMoreElements()) {
//			String alias = (String) aliasesEnum.nextElement();
//			X509Certificate cert = (X509Certificate) testKeystore
//					.getCertificate(alias);//证书内容
//			System.out.println("alias::"+alias);
//                        System.out.println("alias::"+cert);
//		}
	}
        public static String getKey(String ukeyPassword) throws KeyStoreException, NoSuchProviderException, IOException, NoSuchAlgorithmException, CertificateException{
		//提供pkcs11 的基本配置,library 指定了Ukey的驱动程序地址,不同的厂商提供的驱动不同
		//name 对ukey进行标识
//		String pkcs11config_bak = "library = /usr/local/lib/libeTPkcs11.dylib\n"
//				+ "name = safenetSC\n";  C:\Windows\System32\gm3000_pkcs11_tw.dll C:\Windows\SysWOW64
                String pkcs11config ="library = F:\\gm3000_pkcs11_tw.dll\n"+"name = safenetSC\n";
//                String pkcs11config = "library = /usr/lib64/libgm3000_pkcs11.so\n"
//				+ "name = safenetSC\n";
                //System.out.println("a:"+pkcs11config);
                //System.out.println("zhe shi shen me dong dong:"+pkcs11config_bak);
		Provider testProvider = new sun.security.pkcs11.SunPKCS11(new ByteArrayInputStream(pkcs11config.getBytes()));
                System.out.println("zhege shi what:"+testProvider);
		Security.addProvider(testProvider);//添加支持
		//调用上面设置的标识 SunPKCS11-safenetSC 
		KeyStore testKeystore = KeyStore.getInstance("PKCS11","SunPKCS11-safenetSC");
		
		testKeystore.load(null, ukeyPassword.toCharArray());
		
		Enumeration<String> aliasesEnum = testKeystore.aliases();
                System.out.println("..."+aliasesEnum);
                String alias=null;
		while (aliasesEnum.hasMoreElements()) {
			alias = (String) aliasesEnum.nextElement();
                        System.out.println("alis.."+alias);
			X509Certificate cert = (X509Certificate) testKeystore
					.getCertificate(alias);//证书内容
			System.out.println("alias::"+alias);
                        

                        //String aliasstr = "tw00561508000152 ";
                        String alias_utf8 =new String(alias.toString().getBytes("UTF-8"));  
                        String utf=URLEncoder.encode(alias_utf8, "UTF-8");  
                        String alias_utf81 =new String(alias.toString().getBytes("GB2312"));  
                        String utf1=URLEncoder.encode(alias_utf8, "GB2312"); 
                        System.out.println("utf-8 编码：" + utf) ; 
                        System.out.println("GB2312 编码：" + utf1) ; 
                        System.out.println("alias_utf8 编码：" + alias_utf8) ; 
                        System.out.println("alias_utf81 编码：" + alias_utf81) ; 
		}
                return  alias;
	}
}
