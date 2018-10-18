/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bestinfo.arithmetic;

//import com.sun.org.apache.xerces.internal.impl.dv.util.Base64;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.Arrays;
import org.apache.log4j.Logger;
import org.apache.commons.codec.binary.Base64;

public class NewSign {

    private static final Logger logger = Logger.getLogger(NewSign.class);

    /**
     *
     * @param xml 报文
     * @param keyStorePath PS12文件路径
     * @param keyStorePassword PS12密码
     * @param alias alias用户
     * @param str_key des3密钥
     * @return
     * @throws Exception
     */
    public static byte[] GetSign(String xml, String keyStorePath,
            String keyStorePassword, String alias, String str_key) throws Exception {

        CaTool ct = new CaTool();
        EncrypDES3 crypt = new EncrypDES3();

        String str_body = xml.substring(xml.indexOf("<pkgC>"), xml.length() - 6);
        if (str_body == null || str_body.isEmpty()) {
            return null;
        }

        //MD5加密pkgC内容
        byte[] b_body = MD5Encode(str_body);

        byte[] body_enc = ct.encryptByPrivateKey(b_body, keyStorePath, keyStorePassword, alias, keyStorePassword);
        //3DES加密
        return crypt.encrytor(body_enc, str_key);
    }

    public static byte[] GetSign(String xml, PrivateKey privateKey, String str_key) throws Exception {
        CaTool ct = new CaTool();
        EncrypDES3 crypt = new EncrypDES3();

        String str_body = xml.substring(xml.indexOf("<pkgC>"), xml.length() - 6);
        if (str_body == null || str_body.isEmpty()) {
            return null;
        }

        //MD5加密pkgC内容
        byte[] b_body = MD5Encode(str_body);
        byte[] body_enc = ct.encryptByPrivateKey(b_body, privateKey);
        return crypt.encrytor(body_enc, str_key);
    }

    /*测试客户端调用
     获取签名内容
     xml:报文内容
     privateKey：运营商私密
     */
    public static byte[] GetLogSin(String xml, PrivateKey privateKey, String str_key) {
        try {
            CaTool ct = new CaTool();
            EncrypDES3 crypt = new EncrypDES3();

            String str_body = xml.substring(xml.indexOf("<pkgC>"), xml.length() - 6);
            if (str_body == null || str_body.isEmpty()) {
                return null;
            }

            byte[] body_enc = Base64.decodeBase64(ct.logSign(str_body, privateKey));
            return crypt.encrytor(body_enc, str_key);
        } catch (Exception e) {
            return null;
        }
    }

    public static byte[] GetS(String xml, String keyStorePath, String str_key) throws Exception {
        String str_body = xml.substring(xml.indexOf("<pkgC>"), xml.length() - 6);
        if (str_body == null || str_body.isEmpty()) {
            return null;
        }

        CaTool ct = new CaTool();
        EncrypDES3 crypt = new EncrypDES3();

        //MD5加密pkgC内容
        String sMD5_body = new MD5().digest(str_body, "MD5");
        byte[] b_body = sMD5_body.trim().getBytes();

        //RSA加密pkgC(MD5后)
        PublicKey pk = ct.getPublicKey(keyStorePath);
        byte[] body_enc = ct.encryptByPublicKey(b_body, pk);

        //3DES加密
        return crypt.encrytor(body_enc, str_key);
    }

    /**
     * 验证运营商签名数据
     *
     * @param xml 提交xml内容
     * @param Sign 营运商签名内容
     * @param PublicKeyPath 运营商公钥路径
     * @param key session_key会话密钥
     * @return
     */
    public static boolean CheckDealerSign(String xml, byte[] Sign, String PublicKeyPath, String key) {
        try {
            String str_body = xml.substring(xml.indexOf("<pkgC>"), xml.length() - 6);
            if (str_body == null || str_body.isEmpty()) {
                return false;
            }

            EncrypDES3 crypt = new EncrypDES3();
            DataChange dc = new DataChange();
            CaTool ct = new CaTool();
            //3DEDS解密Sign
            byte[] dealerSign = crypt.decryptor(Sign, key);
            //公钥解密
            PublicKey pk = ct.getPublicKey(PublicKeyPath);
            byte[] b_body = ct.decryptByPublicKey(dealerSign, pk);

            byte[] body_enc = MD5Encode(str_body);
            //使用客户公钥RSA加密文件

            if (Arrays.equals(b_body, body_enc)) {
                return true;
            } else {
                logger.error("b_body is:" + Base64.encodeBase64(b_body) + ",body_enc is:" + Base64.encodeBase64(body_enc));
                return false;
            }
        } catch (Exception e) {
            logger.error("Error:" + e.getMessage());
        }

        return false;
    }

    public static boolean CheckDealerSign(String xml, byte[] Sign, PublicKey pk, String key) {
        try {
            String str_body = xml.substring(xml.indexOf("<pkgC>"), xml.length() - 6);
            if (str_body == null || str_body.isEmpty()) {
                return false;
            }
            EncrypDES3 crypt = new EncrypDES3();
            // DataChange dc = new DataChange();
            CaTool ct = new CaTool();
            //3DEDS解密Sign
            byte[] dealerSign = crypt.decryptor(Sign, key);
            //公钥解密
            byte[] b_body = ct.decryptByPublicKey(dealerSign, pk);

            byte[] body_enc = MD5Encode(str_body);
            //使用客户公钥RSA加密文件

            if (Arrays.equals(b_body, body_enc)) {
                return true;
            } else {
                logger.error("b_body is:" + Base64.encodeBase64(b_body) + ",body_enc is:" + Base64.encodeBase64(body_enc));
                return false;
            }
        } catch (Exception e) {
            logger.error("Error:" + e.getMessage());
        }

        return false;
    }

    /**
     *
     */
    /**
     * MD5加密字符串，返回加密后的字节数组
     *
     * @param origin
     * @return
     */
    public static byte[] MD5Encode(String origin) {
        return MD5Encode(origin.getBytes());
    }

    /**
     *
     */
    /**
     * MD5加密字节数组，返回加密后的字节数组
     *
     * @param bytes
     * @return
     */
    public static byte[] MD5Encode(byte[] bytes) {
        MessageDigest md = null;
        try {
            md = MessageDigest.getInstance("MD5");
            return md.digest(bytes);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return new byte[0];
        }
    }
}
