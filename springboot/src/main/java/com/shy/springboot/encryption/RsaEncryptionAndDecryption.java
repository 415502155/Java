package com.shy.springboot.encryption;

import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import lombok.extern.slf4j.Slf4j;
 @Slf4j
public class RsaEncryptionAndDecryption {
 
	/***
	 * RSA 加解密
	 * 
	 * @param agrs
	 */
	public static void main(String agrs[]) throws Exception {
		String data = "qwertyuiopasdfghjklzxcvbnmqwertyuiopasdfghjklzxcvbnmqwertyuiopasdfghjklzxcvbnmqwertyuiopasdfghjklzxcvbnmqwertyuiopasdfghjklzxcvbnmqwertyuiopasdfghjklzxcvbnmqwertyuiopasdfghjklzxcvbnmqwertyuiopasdfghjklzxcvbnmqwertyuiopasdfghjklzxcvbnmqwertyuiopasdfghjklzxcvbnmqwertyuiopasdfghjklzxcvbnmqwertyuiopasdfghjklzxcvbnmqwertyuiopasdfghjklzxcvbnmqwertyuiopasdfghjklzxcvbnmqwertyuiopasdfghjklzxcvbnmqwertyuiopasdfghjklzxcvbnmqwertyuiopasdfghjklzxcvbnmqwertyuiopasdfghjklzxcvbnmqwertyuiopasdfghjklzxcvbnmqwertyuiopasdfghjklzxcvbnmqwertyuiopasdfghjklzxcvbnmqwertyuiopasdfghjklzxcvbnmqwertyuiopasdfghjklzxcvbnmqwertyuiopasdfghjklzxcvbnmqwertyuiopasdfghjklzxcvbnmqwertyuiopasdfghjklzxcvbnmqwertyuiopasdfghjklzxcvbnmqwertyuiopasdfghjklzxcvbnmqwertyuiopasdfghjklzxcvbnmqwertyuiopasdfghjklzxcvbnmqwertyuiopasdfghjklzxcvbnmqwertyuiopasdfghjklzxcvbnmqwertyuiopasdfghjklzxcvbnmqwertyuiopasdfghjklzxcvbnmqwertyuiopasdfghjklzxcvbnmqwertyuiopasdfghjklzxcvbnmqwertyuiopasdfghjklzxcvbnmqwertyuiopasdfghjklzxcvbnmqwertyuiopasdfghjklzxcvbnmqwertyuiopasdfghjklzxcvbnmqwertyuiopasdfghjklzxcvbnmqwertyuiopasdfghjklzxcvbnmqwertyuiopasdfghjklzxcvbnmqwertyuiopasdfghjklzxcvbnmqwertyuiopasdfghjklzxcvbnmqwertyuiopasdfghjklzxcvbnmqwertyuiopasdfghjklzxcvbnmqwertyuiopasdfghjklzxcvbnmqwertyuiopasdfghjklzxcvbnmqwertyuiopasdfghjklzxcvbnmqwertyuiopasdfghjklzxcvbnmqwertyuiopasdfghjklzxcvbnmqwertyuiopasdfghjklzxcvbnmqwertyuiopasdfghjklzxcvbnmqwertyuiopasdfghjklzxcvbnmqwertyuiopasdfghjklzxcvbnmqwertyuiopasdfghjklzxcvbnmqwertyuiopasdfghjklzxcvbnmqwertyuiopasdfghjklzxcvbnmqwertyuiopasdfghjklzxcvbnmqwertyuiopasdfghjklzxcvbnmqwertyuiopasdfghjklzxcvbnmqwertyuiopasdfghjklzxcvbnmqwertyuiopasdfghjklzxcvbnmqwertyuiopasdfghjklzxcvbnmqwertyuiopasdfghjklzxcvbnmqwertyuiopasdfghjklzxcvbnmqwertyuiopasdfghjklzxcvbnmqwertyuiopasdfghjklzxcvbnmqwertyuiopasdfghjklzxcvbnmqwertyuiopasdfghjklzxcvbnmqwertyuiopasdfghjklzxcvbnmqwertyuiopasdfghjklzxcvbnmqwertyuiopasdfghjklzxcvbnmqwertyuiopasdfghjklzxcvbnmqwertyuiopasdfghjklzxcvbnmqwertyuiopasdfghjklzxcvbnmqwertyuiopasdfghjklzxcvbnmqwertyuiopasdfghjklzxcvbnmqwertyuiopasdfghjklzxcvbnmqwertyuiopasdfghjklzxcvbnmqwertyuiopasdfghjklzxcvbnmqwertyuiopasdfghjklzxcvbnmqwertyuiopasdfghjklzxcvbnmqwertyuiopasdfghjklzxcvbnmqwertyuiopasdfghjklzxcvbnmqwertyuiopasdfghjklzxcvbnmqwertyuiopasdfghjklzxcvbnmqwertyuiopasdfghjklzxcvbnmqwertyuiopasdfghjklzxcvbnmqwertyuiopasdfghjklzxcvbnmqwertyuiopasdfghjklzxcvbnmqwertyuiopasdfghjklzxcvbnmqwertyuiopasdfghjklzxcvbnmqwertyuiopasdfghjklzxcvbnmqwertyuiopasdfghjklzxcvbnmqwertyuiopasdfghjklzxcvbnmqwertyuiopasdfghjklzxcvbnmqwertyuiopasdfghjklzxcvbnmqwertyuiopasdfghjklzxcvbnmqwertyuiopasdfghjklzxcvbnmqwertyuiopasdfghjklzxcvbnmqwertyuiopasdfghjklzxcvbnmqwertyuiopasdfghjklzxcvbnmqwertyuiopasdfghjklzxcvbnmqwertyuiopasdfghjklzxcvbnmqwertyuiopasdfghjklzxcvbnmqwertyuiopasdfghjklzxcvbnmqwertyuiopasdfghjklzxcvbnmqwertyuiopasdfghjklzxcvbnmqwertyuiopasdfghjklzxcvbnmqwertyuiopasdfghjklzxcvbnmqwertyuiopasdfghjklzxcvbnmqwertyuiopasdfghjklzxcvbnmqwertyuiopasdfghjklzxcvbnmqwertyuiopasdfghjklzxcvbnmqwertyuiopasdfghjklzxcvbnmqwertyuiopasdfghjklzxcvbnmqwertyuiopasdfghjklzxcvbnmqwertyuiopasdfghjklzxcvbnmqwertyuiopasdfghjklzxcvbnm";
		/***
		 * 如果未生成公钥和私钥文件执行 RasKeyPairGenerator.writeKey();
		 */
		//RasKeyPairGenerator.writeKey();//生成公钥私钥并写入文件
		/***
		 * 方式一：读取公钥私钥文件并验证数据的加解密
		 */
		encryptionAndDecryption(data);
		/***
		 * 方式二：读取公钥私钥文件并验证数据的加解密（n，e，d）
		 */
		/*encryptionAndDecryptionByNED(data);
		String publicKeyPath = "d:\\PublicKey.xml";
		String privateKeyPath = "d:\\PrivateKey.xml";
		String publicKey = RasKeyPairGenerator.readKey(publicKeyPath);
		String privateKey = RasKeyPairGenerator.readKey(privateKeyPath);
		String encode = "UTF8";
		String signStr = new String(RSASignature.sign(data, privateKey, encode));
		System.out.println("签名 data："+ signStr);
		boolean check = RSASignature.doCheck(data, signStr, publicKey, encode);
		if (!true) {
			System.out.println("验签失败");
		} else {
			System.out.println("验签成功");
		}*/
	}
 
	/***
	 * 读取公钥和私钥，进行数据加解密
	 * 
	 * @param data
	 */
	public static int encryptionAndDecryption(String data) {
		log.info("要加密或解密的数据为:  " + data); 
		int flag = 200;
		try {
			// 获取公钥
			String publicKeyPath = "C:\\sjwy\\rsakey\\PublicKey.xml";
			PublicKey publicKey = RasKeyPairGenerator.getPublicKey(RasKeyPairGenerator.readKey(publicKeyPath));
			// 获取私钥
			String privateKeyPath = "C:\\sjwy\\rsakey\\PrivateKey.xml";
			PrivateKey privateKey = RasKeyPairGenerator.getPrivateKey(RasKeyPairGenerator.readKey(privateKeyPath));
			MD5 md5 = new MD5();
			// MD5是防止加密数据的长度过长而引起异常 javax.crypto.IllegalBlockSizeException:Data must not be longer than 117 bytes
			data = md5.digest(data, "MD5");
			System.out.println("MD5 data：" + data);
			// 公钥加密
			byte[] encryptedBytes = RasKeyPairGenerator.encrypt(data.getBytes(), publicKey);
			System.out.println("公钥加密：" + new String(encryptedBytes));
			// 私钥解密
			byte[] decryptedBytes = RasKeyPairGenerator.decrypt(encryptedBytes, privateKey);
			System.out.println("私钥解密：" + new String(decryptedBytes));
		} catch (Exception e) {
			e.printStackTrace();
			log.info("加解密异常：" + e);
			flag = 400;
		}
		return flag;
	}
 
	/***
	 * 由n和e获取公钥，由n和d获取私钥，进行数据加解密
	 * 
	 * @param data
	 */
	public static void encryptionAndDecryptionByNED(String data) {
 
		try {
			// 获取公钥
			String publicKeyPath = "d:\\PublicKey.xml";
			RSAPublicKey rsaPublicKey = (RSAPublicKey) RasKeyPairGenerator
					.getPublicKey(RasKeyPairGenerator.readKey(publicKeyPath));
			String modulus1 = rsaPublicKey.getModulus().toString();
			String exponent1 = rsaPublicKey.getPublicExponent().toString();
			// 获取私钥
			String privateKeyPath = "d:\\PrivateKey.xml";
			RSAPrivateKey rsaPrivateKey = (RSAPrivateKey) RasKeyPairGenerator
					.getPrivateKey(RasKeyPairGenerator.readKey(privateKeyPath));
			String modulus2 = rsaPrivateKey.getModulus().toString();
			String exponent2 = rsaPrivateKey.getPrivateExponent().toString();
			MD5 md5 = new MD5();
			// MD5是防止加密数据的长度过长而引起异常
			data = md5.digest(data, "MD5");
			// 由n和e获取公钥
			PublicKey publicKey = RasKeyPairGenerator.getPublicKey(modulus1, exponent1);
			// 由n和d获取私钥
			PrivateKey privateKey = RasKeyPairGenerator.getPrivateKey(modulus2, exponent2);
			System.out.println("MD5 data：" + data);
			// 公钥加密
			byte[] encryptedBytes = RasKeyPairGenerator.encrypt(data.getBytes(), publicKey);
			String jia = RasKeyPairGenerator.encryptBASE64(encryptedBytes);
			System.out.println("公钥加密：" + new String(encryptedBytes));
			System.out.println("公钥加密： base64编码：" + jia);
			// 私钥解密
			String jie = new String(RasKeyPairGenerator.decrypt(RasKeyPairGenerator.decryptBASE64(jia), privateKey));
			byte[] decryptedBytes = RasKeyPairGenerator.decrypt(encryptedBytes, privateKey);
			
			System.out.println("私钥解密：" + new String(decryptedBytes));
			System.out.println("私钥解密： base64解码：" + jie);
		} catch (Exception e) {
			System.out.println("加解密异常：" + e);
		}
	}
}
