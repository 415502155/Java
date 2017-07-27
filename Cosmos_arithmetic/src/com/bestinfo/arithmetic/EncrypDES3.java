package com.bestinfo.arithmetic;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import org.apache.log4j.Logger;

/**
 *
 * @author cry
 */
public class EncrypDES3 {

    private static final Logger logger = Logger.getLogger(EncrypDES3.class);

    public SecretKey genSessionKey() {
        try {
//            Security.addProvider(new com.sun.crypto.provider.SunJCE());
            KeyGenerator keygen = KeyGenerator.getInstance("DESede");
            keygen.init(192);
            SecretKey deskey = keygen.generateKey();
            return deskey;
        } catch (Exception e) {
            logger.error("", e);
            return null;
        }
    }

    public byte[] genSessionKeyBytes() {
        try {
//            Security.addProvider(new com.sun.crypto.provider.SunJCE());
            KeyGenerator keygen = KeyGenerator.getInstance("DESede");
            keygen.init(192);
            SecretKey deskey = keygen.generateKey();
            return deskey.getEncoded();
        } catch (Exception e) {
            logger.error("", e);
            return null;
        }
    }

    public byte[] encrytor(byte[] src, SecretKey sessionKey) {
        try {
//            Security.addProvider(new com.sun.crypto.provider.SunJCE());
            //KeyGenerator keygen = KeyGenerator.getInstance("DESede");
            //SecretKey deskey = keygen.generateKey();

            Cipher c = Cipher.getInstance("DESede/ECB/NoPadding");

            c.init(Cipher.ENCRYPT_MODE, sessionKey);
            byte[] cipherByte = c.doFinal(src);
            return cipherByte;
        } catch (Exception e) {
            logger.error("", e);
            return null;
        }
    }

    /**
     * 对字符串加密,key为二进制数组,src的长度必须为8的倍数
     *
     * @param src
     * @param randomKeyBright
     * @return
     */
    public byte[] encrytor(byte[] src, byte[] randomKeyBright) {
        try {
//            Security.addProvider(new com.sun.crypto.provider.SunJCE());
            //KeyGenerator keygen = KeyGenerator.getInstance("DESede");
            //SecretKey deskey = keygen.generateKey();
            SecretKey deskey = new SecretKeySpec(randomKeyBright, "DESede");

            Cipher c = Cipher.getInstance("DESede/ECB/NoPadding");

            c.init(Cipher.ENCRYPT_MODE, deskey);
            byte[] cipherByte = c.doFinal(src);
            return cipherByte;
        } catch (Exception e) {
            logger.error("", e);
            return null;
        }
    }

    /**
     * 对字符串加密
     *
     * @param src
     * @return
     */
    public byte[] encrytor(byte[] src, String randomKeyBright) {
        try {
//            Security.addProvider(new com.sun.crypto.provider.SunJCE());
            //KeyGenerator keygen = KeyGenerator.getInstance("DESede");
            //SecretKey deskey = keygen.generateKey();
            SecretKey deskey = new SecretKeySpec(randomKeyBright.getBytes(), "DESede");//getBytes()????
            Cipher c = Cipher.getInstance("DESede");
            c.init(Cipher.ENCRYPT_MODE, deskey);
            byte[] cipherByte = c.doFinal(src);
            return cipherByte;
        } catch (Exception e) {
            logger.error("", e);
            return null;
        }
    }

    public byte[] encrytor(byte[] src, byte[] randomKeyBright, byte[] inv) {
        try {
            SecretKey deskey = new SecretKeySpec(randomKeyBright, "DESede");
            IvParameterSpec iv = new IvParameterSpec(inv);  //初始化向量
            Cipher c = Cipher.getInstance("DESede/CBC/NoPadding");
            c.init(Cipher.ENCRYPT_MODE, deskey, iv);
            byte[] cipherByte = c.doFinal(src);
            return cipherByte;
        } catch (Exception e) {
            logger.error("", e);
            return null;
        }
    }

    /**
     * 解密 无padding ,input长度必须为8的倍数
     *
     * @param buff
     * @param randomKeyBright
     * @return
     */
    public byte[] decryptor(byte[] buff, byte[] randomKeyBright) {
        try {
//            Security.addProvider(new com.sun.crypto.provider.SunJCE());
            //KeyGenerator keygen = KeyGenerator.getInstance("DESede");
            //SecretKey deskey = keygen.generateKey();
            SecretKey deskey = new SecretKeySpec(randomKeyBright, "DESede");
            Cipher c = Cipher.getInstance("DESede/ECB/NoPadding");
//            Cipher c = Cipher.getInstance("DESede");
            c.init(Cipher.DECRYPT_MODE, deskey);
            byte[] cipherByte = c.doFinal(buff);
            return cipherByte;
        } catch (Exception e) {
            logger.error("", e);
          return null;
        }
    }

    public byte[] decryptor(byte[] buff, byte[] randomKeyBright, byte[] inv) {
        try {
            SecretKey deskey = new SecretKeySpec(randomKeyBright, "DESede");
            IvParameterSpec iv = new IvParameterSpec(inv);  //初始化向量
            Cipher c = Cipher.getInstance("DESede/CBC/NoPadding");
            c.init(Cipher.DECRYPT_MODE, deskey, iv);
            byte[] cipherByte = c.doFinal(buff);
            return cipherByte;
        } catch (Exception e) {
            logger.error("", e);
            return null;
        }
    }
    public byte[] decryptor(byte[] buff, int startPos, int len, byte[] randomKeyBright,byte[] inv) {
        try {
            SecretKey deskey = new SecretKeySpec(randomKeyBright, "DESede");
            IvParameterSpec iv = new IvParameterSpec(inv);  //初始化向量
            Cipher c = Cipher.getInstance("DESede/CBC/NoPadding");
            c.init(Cipher.DECRYPT_MODE, deskey,iv);
            byte[] cipherByte = c.doFinal(buff, startPos, len);
            return cipherByte;
        } catch (Exception e) {
            logger.error("", e);
            return null;
        }
    }
    public byte[] decryptor(byte[] buff, int startPos, int len, SecretKey sessionKey) {
        try {
//            Security.addProvider(new com.sun.crypto.provider.SunJCE());
            //KeyGenerator keygen = KeyGenerator.getInstance("DESede");
            //SecretKey deskey = keygen.generateKey();
            Cipher c = Cipher.getInstance("DESede/ECB/NoPadding");
            c.init(Cipher.DECRYPT_MODE, sessionKey);
            byte[] cipherByte = c.doFinal(buff, startPos, len);
            return cipherByte;
        } catch (Exception e) {
            logger.error("", e);
           return null;
        }
    }

    public byte[] decryptor(byte[] buff, int startPos, int len, byte[] randomKeyBright) {
        try {
//            Security.addProvider(new com.sun.crypto.provider.SunJCE());
            //KeyGenerator keygen = KeyGenerator.getInstance("DESede");
            //SecretKey deskey = keygen.generateKey();
            SecretKey deskey = new SecretKeySpec(randomKeyBright, "DESede");
            Cipher c = Cipher.getInstance("DESede/ECB/NoPadding");
            c.init(Cipher.DECRYPT_MODE, deskey);
            byte[] cipherByte = c.doFinal(buff, startPos, len);
            return cipherByte;
        } catch (Exception e) {
            logger.error("", e);
           return null;
        }
    }

    /**
     * 对字符串解密
     *
     * @param buff
     * @return
     */
    public byte[] decryptor(byte[] buff, String randomKeyBright) {
        try {
//            Security.addProvider(new com.sun.crypto.provider.SunJCE());
            //KeyGenerator keygen = KeyGenerator.getInstance("DESede");
            //SecretKey deskey = keygen.generateKey();
            SecretKey deskey = new SecretKeySpec(randomKeyBright.getBytes(), "DESede");
            Cipher c = Cipher.getInstance("DESede");
            c.init(Cipher.DECRYPT_MODE, deskey);
            byte[] cipherByte = c.doFinal(buff);
            return cipherByte;
        } catch (Exception e) {
            logger.error("", e);
           return null;
        }
    }

    /**
     * @param args
     * @throws NoSuchPaddingException
     * @throws NoSuchAlgorithmException
     * @throws BadPaddingException
     * @throws IllegalBlockSizeException
     * @throws InvalidKeyException
     */
    public static void main(String[] args) throws Exception {
        EncrypDES3 des = new EncrypDES3();
        //String msg ="郭XX-搞笑相声全集";  
        //byte[] encontent = des.Encrytor(msg);  
        //byte[] decontent = des.Decryptor(encontent);  
        //System.out.println("明文是:" + msg);  
        //System.out.println("加密后:" + new String(encontent));  
        //System.out.println("解密后:" + new String(decontent));  
    }

}
