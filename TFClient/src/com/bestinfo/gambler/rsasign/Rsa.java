package com.bestinfo.gambler.rsasign;

import com.bestinfo.arithmetic.CaTool;
import java.io.StringReader;
import java.io.StringWriter;
import java.math.BigInteger;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Signature;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.RSAPublicKeySpec;
import java.security.*;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.KeySpec;
import java.security.spec.X509EncodedKeySpec;
import org.bouncycastle.asn1.x509.SubjectPublicKeyInfo;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.openssl.PEMParser;
import org.bouncycastle.openssl.jcajce.JcaPEMKeyConverter;
import org.bouncycastle.util.io.pem.PemObject;
import org.bouncycastle.util.io.pem.PemWriter;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

public class Rsa
{
	
	
    // 使用N、e值还原公钥
	  public static PublicKey getPublicKey(byte[] modulus)
	          throws NoSuchAlgorithmException, InvalidKeySpecException {
	      BigInteger bigIntModulus = new BigInteger(1, modulus);
	      BigInteger bigIntPrivateExponent = BigInteger.valueOf(65537L);
	
	      RSAPublicKeySpec keySpec = new RSAPublicKeySpec(bigIntModulus, bigIntPrivateExponent);
	      KeyFactory keyFactory = KeyFactory.getInstance("RSA");
	      PublicKey publicKey = keyFactory.generatePublic(keySpec);
	      return publicKey;
	  }
	
	
	/**
	 * 外部密钥签名，使用设备内部已经存在的密钥
	 * @param hashalg 摘要算法，支持算法：SHA1, SHA224, SHA384, SHA512, MD2, MD5, MD4 
	 * @param prikey RSA私钥
	 * @param indata 待签名数据
	 * @return 签名后的数据
	 */
	public byte[] ExternalRSASign(String hashalg, PrivateKey prikey, byte[] indata)
	{
		String alg = hashalg + "withRSA";
		byte[] ret = null; 
		
		try{
			Signature sg = Signature.getInstance(alg);
			sg.initSign(prikey);
			sg.update(indata);
			ret = sg.sign();
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}
		return ret;
	}
        /***
         * 将字符串形式的key转为PublicKey
         * @param key
         * @return
         * @throws Exception 
         */
	public static PublicKey getPublicKey(String key) throws Exception {
             byte[] keyBytes;
             keyBytes = (new BASE64Decoder()).decodeBuffer(key);
             X509EncodedKeySpec keySpec = new X509EncodedKeySpec(keyBytes);
             KeyFactory keyFactory = KeyFactory.getInstance("RSA");
             PublicKey publicKey = keyFactory.generatePublic(keySpec);
             return publicKey;
       }
        /***
         * 将字符串形式的key转为PublicKey
         * @param key
         * @return
         * @throws Exception 
         */
	public static PublicKey getPemPublicKey(String key) throws Exception {
             byte[] keyBytes;
             keyBytes = (new BASE64Decoder()).decodeBuffer(key);
             X509EncodedKeySpec keySpec = new X509EncodedKeySpec(keyBytes);
             KeyFactory keyFactory = KeyFactory.getInstance("RSA");
             PublicKey publicKey = keyFactory.generatePublic(keySpec);
//            Security.addProvider(new BouncyCastleProvider());
//            PEMReader pemReader = new PEMReader(new StringReader(pemKey));
//            RSAPublicKey publicKey = (RSAPublicKey) pemReader.readObject();
//            byte[] keyBytes = key.getBytes();        
//            KeySpec keySpec = new X509EncodedKeySpec(keyBytes);
//            KeyFactory factory = KeyFactory.getInstance("RSA");
//            PublicKey publicKey = factory.generatePublic(keySpec);
             return publicKey;
       }
	/**
	 * 外部RSA密钥验签
	 * @param hashalg 摘要算法
	 * @param pubkey RSA公钥
	 * @param indata 原始数据
	 * @param signdata 签名数据
	 * @return 验签结果 true或false
	 */
	public boolean ExternalRSAVerify(String hashalg, PublicKey pubkey, byte[] indata, byte[] signdata)
	{
		boolean rv = false; 
		String alg = hashalg + "withRSA";
	
		try{
			Signature sg = Signature.getInstance(alg);
			sg.initVerify(pubkey);
			sg.update(indata);
			if(sg.verify(signdata)){
				rv = true;
			}else{
				rv = false;
			}	
		}catch(Exception e){
			e.printStackTrace();
			return false;
		}
		return rv;
	}
        /**
	 * 外部RSA密钥验签
	 * @param hashalg 摘要算法
	 * @param pubkey RSA公钥
	 * @param indata 原始数据
	 * @param signdata 签名数据
	 * @return 验签结果 true或false
	 */
	public boolean ExternalRSAStringVerify(String hashalg, String pubkey, byte[] indata, byte[] signdata) throws Exception
	{
		boolean rv = false; 
		String alg = hashalg + "withRSA";
                
                CaTool ct = new CaTool();
                PublicKey pk = ct.loadPublicKeyFromPem(pubkey);
                //PublicKey pk = getPemPublicKey(pubkey);
		try{
			Signature sg = Signature.getInstance(alg);
			sg.initVerify(pk);
			sg.update(indata);
			if(sg.verify(signdata)){
				rv = true;
			}else{
				rv = false;
			}	
		}catch(Exception e){
			e.printStackTrace();
			return false;
		}
		return rv;
	}
	
	public static void main(String[] args) throws InterruptedException
	{
		byte[] m = new byte[]{
				(byte)0xE8, (byte)0xD2, (byte)0x83, (byte)0x64, (byte)0xB9, (byte)0x3A, (byte)0xDA, (byte)0x9C, (byte)0x88, (byte)0xA5, (byte)0x69, (byte)0x15, (byte)0x09, (byte)0x6A, (byte)0xDA, (byte)0x3B, (byte)0xBA, (byte)0xB6, (byte)0x3D, (byte)

				0x2F, (byte)0xC6, (byte)0x92, (byte)0x5E, (byte)0xA7, (byte)0x08, (byte)0x85, (byte)0x42, (byte)0x74, (byte)0x5B, (byte)0x11, (byte)0x52, (byte)0xED, (byte)0xA2, (byte)0x3A, (byte)0xC8, (byte)0x69, (byte)0x08, (byte)0x93, (byte)0x38, 

				(byte)0x52, (byte)0x21, (byte)0x84, (byte)0xB7, (byte)0x68, (byte)0xA4, (byte)0xEF, (byte)0xB1, (byte)0x88, (byte)0xE6, (byte)0x1F, (byte)0x8F, (byte)0x3D, (byte)0xCD, (byte)0x44, (byte)0x79, (byte)0x2E, (byte)0x77, (byte)0x4B, (byte)

				0x0C, (byte)0xB4, (byte)0xC6, (byte)0xAF, (byte)0x54, (byte)0x2B, (byte)0x2C, (byte)0xCB, (byte)0xE7, (byte)0xDE, (byte)0x19, (byte)0x09, (byte)0x87, (byte)0xE6, (byte)0x3C, (byte)0x30, (byte)0xD8, (byte)0xC7, (byte)0x38, (byte)0x52, 

				(byte)0x1C, (byte)0x78, (byte)0xF2, (byte)0x32, (byte)0x0F, (byte)0x21, (byte)0x8B, (byte)0x86, (byte)0xA9, (byte)0x05, (byte)0xBC, (byte)0x78, (byte)0x65, (byte)0xDE, (byte)0x42, (byte)0x87, (byte)0x20, (byte)0x6C, (byte)0xA8, (byte)

				0x2B, (byte)0x83, (byte)0xAD, (byte)0x76, (byte)0x2D, (byte)0x5E, (byte)0x6E, (byte)0xE1, (byte)0x3F, (byte)0x26, (byte)0x7A, (byte)0xC9, (byte)0xD7, (byte)0x54, (byte)0x61, (byte)0xB6, (byte)0x59, (byte)0xF5, (byte)0x33, (byte)0x75, 

				(byte)0x99, (byte)0x40, (byte)0x4D, (byte)0x8E, (byte)0xD7, (byte)0x8D, (byte)0x7E, (byte)0xC1, (byte)0xC1, (byte)0xA0, (byte)0x13
		};
		//byte[] indata = new byte[]{0x31, 0x32, 0x33, 0x34, 0x35, 0x36, 0x37, 0x38};
                String str = "12345678";
                //String indata=new String(indata);
                byte [] indata = str.getBytes();
                System.out.println("The String str5 is "+indata);
		byte[] signdata = new byte[]{
				(byte)0xB6, (byte)0xAD, (byte)0x74, (byte)0x1C, (byte)0x97, (byte)0xF6, (byte)0xC4, (byte)0xDE, 
				(byte)0xDC, (byte)0x38, (byte)0x8B, (byte)0x97, (byte)0x85, (byte)0xA6, (byte)0xF5, (byte)0xB9, 
				(byte)0xF2, (byte)0x9C, (byte)0xE7, (byte)0xF9, (byte)0x89, (byte)0x68, (byte)0x54, (byte)0x13, 
				(byte)0x85, (byte)0x07, (byte)0x41, (byte)0x9A, (byte)0x7C, (byte)0xB8, (byte)0x81, (byte)0xBB, 
				(byte)0x5B, (byte)0x20, (byte)0xDE, (byte)0x52, (byte)0xDC, (byte)0x74, (byte)0x87, (byte)0xC5, 
				(byte)0xAF, (byte)0x08, (byte)0x79, (byte)0x2F, (byte)0xA7, (byte)0x7B, (byte)0xA5, (byte)0x0E, 
				(byte)0x5E, (byte)0xE5, (byte)0xDD, (byte)0x14, (byte)0x0E, (byte)0x5D, (byte)0x73, (byte)0x07, 
				(byte)0x36, (byte)0x0E, (byte)0x9D, (byte)0x5F, (byte)0x8A, (byte)0x8E, (byte)0xE2, (byte)0x66,
				(byte)0xEE, (byte)0xBB, (byte)0x2C, (byte)0x20, (byte)0x92, (byte)0xCC, (byte)0xA0, (byte)0x31, 
				(byte)0x7A, (byte)0x97, (byte)0x6F, (byte)0xB6, (byte)0x00, (byte)0xAD, (byte)0xD5, (byte)0xE0, 
				(byte)0x88, (byte)0xBD, (byte)0xD0, (byte)0x11, (byte)0x83, (byte)0x6B, (byte)0x33, (byte)0xE7, 
				(byte)0x8F, (byte)0xF3, (byte)0x5E, (byte)0x42, (byte)0xA7, (byte)0xE0, (byte)0x9E, (byte)0x8D,
				(byte)0x4D, (byte)0xA2, (byte)0x76, (byte)0x07, (byte)0xEA, (byte)0x74, (byte)0xAD, (byte)0x7B, 
				(byte)0xEE,(byte)0x31, (byte)0xF2, (byte)0x9B, (byte)0x59, (byte)0x9B, (byte)0x80, (byte)0xBB, 
				(byte)0xCC, (byte)0xB1, (byte)0xB7, (byte)0x02, (byte)0x43, (byte)0x79, (byte)0xA3, (byte)0xBE, 
				(byte)0x44, (byte)0x68, (byte)0xA3, (byte)0xE5, (byte)0xB7, (byte)0xC4, (byte)0xC8, (byte)0xFA
		};
		Rsa myrsa = new Rsa();
		try{
                        System.err.println("mmmmmm:"+m.length);
			PublicKey pubkey = myrsa.getPublicKey(m);
                        byte[] keyBytes = pubkey.getEncoded();
                        String gyzfc = (new BASE64Encoder()).encode(keyBytes);
                        StringWriter writer = new StringWriter();
                        PemWriter pemwriter = new PemWriter(writer);
                        pemwriter.writeObject(new PemObject("PUBLIC KEY", keyBytes));
                        pemwriter.flush();
                        pemwriter.close();
            //            PEMWriter pemwriter = new PEMWriter(writer);
            //            pemwriter.writeObject(key);
            //            pemwriter.flush();
            //            pemwriter.close();
                        String pemkey = writer.toString();
                        System.out.println("pemkey:"+pemkey);
                        String subPemKey=pemkey.substring(28,pemkey.length()-28);
                        System.err.println("pk str:"+subPemKey);
			boolean rv = myrsa.ExternalRSAStringVerify("SHA1", pemkey, indata, signdata);
			if(rv){
				System.out.println("ExternalRSAVerify ok!");
			}else{
				System.out.println("ExternalRSAVerify err!");
				return;
			}
			
		}catch(Exception e)
		{
			e.printStackTrace();
		}
		
		
		System.exit(0);
	}
        
            /**
     * 将pem字符串转成公钥
     *
     * @param pemString
     * @return
     */
    public PublicKey loadPublicKeyFromPem(final String pemString) {
        try {
            StringReader sr = new StringReader(pemString);
            PEMParser pemParser = new PEMParser(sr);
            JcaPEMKeyConverter converter = new JcaPEMKeyConverter().setProvider("BC");
            Object object = pemParser.readObject();
            PublicKey pb = converter.getPublicKey((SubjectPublicKeyInfo) object);
            pemParser.close();
            return pb;
        } catch (Exception e) {
            System.err.println("ex:"+e);
            return null;
        }

    }
}
	
	