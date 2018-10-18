package com.bestinfo.arithmetic;

import java.security.Key;

import javax.crypto.Cipher;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESedeKeySpec;
import javax.crypto.spec.IvParameterSpec;
import org.apache.commons.codec.binary.Base64;

public class Des3 {

    public static byte[] des3EncodeECB(byte[] key, byte[] data)
            throws Exception {
        Key deskey = null;
        DESedeKeySpec spec = new DESedeKeySpec(key);
        SecretKeyFactory keyfactory = SecretKeyFactory.getInstance("desede");
        deskey = keyfactory.generateSecret(spec);

        Cipher cipher = Cipher.getInstance("desede" + "/ECB/PKCS5Padding");
        cipher.init(Cipher.ENCRYPT_MODE, deskey);
        byte[] bOut = cipher.doFinal(data);

        return bOut;
    }

    public static byte[] des3DecodeECB(byte[] key, byte[] data)
            throws Exception {
        Key deskey = null;
        DESedeKeySpec spec = new DESedeKeySpec(key);
        SecretKeyFactory keyfactory = SecretKeyFactory.getInstance("desede");
        deskey = keyfactory.generateSecret(spec);

        Cipher cipher = Cipher.getInstance("desede" + "/ECB/PKCS5Padding");
        cipher.init(Cipher.DECRYPT_MODE, deskey);
        byte[] bOut = cipher.doFinal(data);

        return bOut;
    }

    public static byte[] des3EncodeCBC(byte[] key, byte[] keyiv, byte[] data)
            throws Exception {
        Key deskey = null;
        DESedeKeySpec spec = new DESedeKeySpec(key);
        SecretKeyFactory keyfactory = SecretKeyFactory.getInstance("desede");
        deskey = keyfactory.generateSecret(spec);

        Cipher cipher = Cipher.getInstance("desede" + "/CBC/PKCS5Padding");
        IvParameterSpec ips = new IvParameterSpec(keyiv);
        cipher.init(Cipher.ENCRYPT_MODE, deskey, ips);
        byte[] bOut = cipher.doFinal(data);

        return bOut;
    }

    public static byte[] des3DecodeCBC(byte[] key, byte[] keyiv, byte[] data)
            throws Exception {
        Key deskey = null;
        DESedeKeySpec spec = new DESedeKeySpec(key);
        SecretKeyFactory keyfactory = SecretKeyFactory.getInstance("desede");
        deskey = keyfactory.generateSecret(spec);

        Cipher cipher = Cipher.getInstance("desede" + "/CBC/PKCS5Padding");
        IvParameterSpec ips = new IvParameterSpec(keyiv);
        cipher.init(Cipher.DECRYPT_MODE, deskey, ips);
        byte[] bOut = cipher.doFinal(data);

        return bOut;
    }

    public static void main(String[] args) throws Exception {
//        byte[] key = Base64.decodeBase64("shfcksewmcx20170401yrjzzshfcksewmcx20170401yrjzzshfcksewmcx20170401yrjzzshfcksewmcx20170401yrjzz");
//        byte[] keyiv = {1, 2, 3, 4, 5, 6, 7, 8};
//        byte[] data = "中国ABCabc123".getBytes("UTF-8");
//
//        System.out.println("ECB加密解密");
//        byte[] encryptBytes = des3EncodeECB(key, data);
//        byte[] base64EncryptBytes = Base64.encodeBase64(encryptBytes);
//        String base64EncryptStr = new String(base64EncryptBytes, "UTF-8");
//        System.out.println(base64EncryptStr);
//        
//        byte[] base64DncryptBytes = Base64.decodeBase64(base64EncryptStr.getBytes("UTF-8"));
//        byte[] dncryptBytes = des3DecodeECB(key, base64DncryptBytes);
//        System.out.println(new String(dncryptBytes, "UTF-8"));
//
//        System.out.println("CBC加密解密");
//        byte[] str5 = des3EncodeCBC(key, keyiv, data);
//        byte[] str6 = des3DecodeCBC(key, keyiv, str5);
//        System.out.println(Base64.encodeBase64(str5));
//        System.out.println(new String(str6, "UTF-8"));
        
        
        
        //qrcodeStr: 1 ,129  ,2017046 ,31010099,7    ,2.0         ,1   ,1   ,1   ,2017-03-06 14:45:16,1
        //encryQrcodeStr: nvfcF2y/2oXnibOaG+EeV6Ok0m1aMmZF729JUD41y3a0va5NGJeqe9pIeu7lpzryLw93Plpxar7hnxdbF5QB5BS2s/GflCgNYs2rnULm1aG0oFK91qatsg==
        //加解密key
        String key = "shfcksewmcx20170401yrjzzshfcksewmcx20170401yrjzzshfcksewmcx20170401yrjzzshfcksewmcx20170401yrjzz";
        byte[] keyBytes = Base64.decodeBase64(key);
        //加密串的base64串
        String base64EncryptStr = "nvfcF2y/2oXnibOaG+EeV6Ok0m1aMmZF729JUD41y3a0va5NGJeqe9pIeu7lpzryLw93Plpxar7hnxdbF5QB5BS2s/GflCgNYs2rnULm1aG0oFK91qatsg==";
        //先进行base64解析
        byte[] base64DecryptBytes = Base64.decodeBase64(base64EncryptStr.getBytes("UTF-8"));
        //des3解密
        byte[] des3DecryptBytes = des3DecodeECB(keyBytes, base64DecryptBytes);
        System.out.println(new String(des3DecryptBytes, "UTF-8"));
    }

}
