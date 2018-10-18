package com.bestinfo.controller.qrcode;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.KeyFactory;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.PrivateKey;
import java.security.Provider;
import java.security.PublicKey;
import java.security.Security;
import java.security.Signature;
import java.security.SignatureException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;
/**
 * 
 * @author yangqiju
 *
 */
public class RsaExample {
	
	private static final String ukeyPassword = "12345678";//TODO ukey pin码
	private static final String privateKeyId = "TWT1612000000007";//"TW00561508000106";//TODO 私钥的id号
	private static final String yuanptp = "省码加终端机号加公钥";//TODO 私钥的id号
	public static void main(String[] args) throws Exception {
            getSignature(ukeyPassword,privateKeyId,yuanptp);
		//提供pkcs11 的基本配置,library 指定了Ukey的驱动程序地址,不同的厂商提供的驱动不同
		//name 对ukey进行标识
//		String pkcs11config ="library = F:\\gm3000_pkcs11_tw.dll\n"+"name = safenetSC\n";
//		Provider testProvider = new sun.security.pkcs11.SunPKCS11(
//				new ByteArrayInputStream(pkcs11config.getBytes()));
//		Security.addProvider(testProvider);//添加支持
//		//调用上面设置的标识 SunPKCS11-safenetSC 
//		KeyStore testKeystore = KeyStore.getInstance("PKCS11","SunPKCS11-safenetSC");
//		
//		testKeystore.load(null, ukeyPassword.toCharArray());
//		KeyFactory keyFactory = KeyFactory.getInstance("RSA",testKeystore.getProvider());
//		String id = privateKeyId;//id
//		PrivateKey privateKey = (PrivateKey) testKeystore.getKey(id, null);
//		PublicKey publicKey = testKeystore.getCertificate(id).getPublicKey();
		
		//加密
//		Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
//		cipher.init(Cipher.ENCRYPT_MODE, privateKey);
//		byte[] doFinal = cipher.doFinal("test".getBytes());
		
		//解密
//		cipher.init(Cipher.DECRYPT_MODE, publicKey);
//		byte[] plainBytes = cipher.doFinal(doFinal);
		
		//签名
//		Signature signatureEngine = Signature.getInstance("SHA256WithRSA",
//				testKeystore.getProvider());
//		signatureEngine.initSign(privateKey);
//		signatureEngine.update("sign data".getBytes());
//		byte[] signature = signatureEngine.sign();
//		          System.out.println("ssss:"+signature);
		//验证
//		Signature signatureEngine2 = Signature.getInstance("SHA256WithRSA");
//		signatureEngine2.initVerify(publicKey);
//		signatureEngine2.update("sign data".getBytes());
//		boolean verify = signatureEngine2.verify(signature);
		
	}
        
        public static String getSignature(String ukeyPassword,String privateKeyId,String yuanptp) throws KeyStoreException, NoSuchProviderException, IOException, NoSuchAlgorithmException, CertificateException, UnrecoverableKeyException, InvalidKeyException, SignatureException{
		//提供pkcs11 的基本配置,library 指定了Ukey的驱动程序地址,不同的厂商提供的驱动不同
		//name 对ukey进行标识
		//String pkcs11config ="library = F:\\gm3000_pkcs11_tw.dll\n"+"name = safenetSC\n";
            String pkcs11config = "library = /usr/lib64/libgm3000_pkcs11.so\n"
				+ "name = safenetSC\n";
		Provider testProvider = new sun.security.pkcs11.SunPKCS11(
				new ByteArrayInputStream(pkcs11config.getBytes()));
		Security.addProvider(testProvider);//添加支持
		//调用上面设置的标识 SunPKCS11-safenetSC 
		KeyStore testKeystore = KeyStore.getInstance("PKCS11","SunPKCS11-safenetSC");
		
		testKeystore.load(null, ukeyPassword.toCharArray());
		KeyFactory keyFactory = KeyFactory.getInstance("RSA",testKeystore.getProvider());
		String id = privateKeyId;//id
		PrivateKey privateKey = (PrivateKey) testKeystore.getKey(id, null);
                System.out.println("privateKey..."+privateKey);
		PublicKey publicKey = testKeystore.getCertificate(id).getPublicKey();
		

		Signature signatureEngine = Signature.getInstance("SHA256WithRSA",
				testKeystore.getProvider());
		signatureEngine.initSign(privateKey);
		signatureEngine.update(yuanptp.getBytes());
		byte[] signature = signatureEngine.sign();
		System.out.println("ssss:"+signature);
                String signatureKey= bytesToHexString(signature);
                //String signatureKey=new String(signature);
                return signatureKey;
	}
            /*来转换成16进制字符串。  
            * @param src byte[] data  
            * @return hex string  
            */     
           public static String bytesToHexString(byte[] src){  
               StringBuilder stringBuilder = new StringBuilder("");  
               if (src == null || src.length <= 0) {  
                   return null;  
               }  
               for (int i = 0; i < src.length; i++) {  
                   int v = src[i] & 0xFF;  
                   String hv = Integer.toHexString(v);  
                   if (hv.length() < 2) {  
                       stringBuilder.append(0);  
                   }  
                   stringBuilder.append(hv);  
               }  
               return stringBuilder.toString();  
           }  
}