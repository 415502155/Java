package com.bestinfo.gambler.rsasign;

import static com.bestinfo.arithmetic.CaTool.X509;
import static com.bestinfo.arithmetic.NewSign.MD5Encode;
import com.bestinfo.gambler.swing.CreateRequestMapXml;
import com.bestinfo.util.FileUtil;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.security.KeyStore;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.cert.Certificate;
import java.security.cert.CertificateFactory;
import java.util.Map;
import java.util.Properties;
import java.util.logging.Level;

/**
 * @author Administrator
 */
public class HPRSATester {
    //public static final String KEY_STORE = "JKS";
    private static final String KEY_STORE = "PKCS12";//keystore格式
//    static String publicKey;
//    static String privateKey;

//    static {
//        try {
//            Map<String, Object> keyMap = RSAUtils.genKeyPair();
//            publicKey = RSAUtils.getPublicKey(keyMap);
//            privateKey = RSAUtils.getPrivateKey(keyMap);
//            System.err.println("公钥: \n\r" + publicKey);
//            System.err.println("私钥： \n\r" + privateKey);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
    
    public static void main(String[] args) throws Exception {
        //test();
        //testSign();
        testHttpSign();
    }

//    static void test() throws Exception {
//        System.err.println("公钥加密——私钥解密");
//        String source = "gongyaojiami，siyaojiemi";
//        System.out.println("\r加密前文字：\r\n" + source);
//        byte[] data = source.getBytes();
//        byte[] encodedData = RSAUtils.encryptByPublicKey(data, publicKey);
//        System.out.println("加密后文字：\r\n" + new String(encodedData));
//        byte[] decodedData = RSAUtils.decryptByPrivateKey(encodedData, privateKey);
//        String target = new String(decodedData);
//        System.out.println("解密后文字: \r\n" + target);
//    }

//    static void testSign() throws Exception {
//        System.err.println("私钥加密——公钥解密");
//        String source = "siyaojiami,gongyaojiemi";
//        System.out.println("原文字：\r\n" + source);
//        byte[] data = source.getBytes();
//        byte[] encodedData = RSAUtils.encryptByPrivateKey(data, privateKey);
//        System.out.println("加密后：\r\n" + new String(encodedData));
//        byte[] decodedData = RSAUtils.decryptByPublicKey(encodedData, publicKey);
//        String target = new String(decodedData);
//        System.out.println("解密后: \r\n" + target);
//        System.err.println("私钥签名——公钥验证签名");
//        String sign = RSAUtils.sign(encodedData, privateKey);
//        System.err.println("签名:\r" + sign);
//        boolean status = RSAUtils.verify(encodedData, publicKey, sign);
//        System.err.println("验证结果:\r" + status);
//    }
        /***
         * 读取属性文件内容获取
         * @return 
         */
        public static String getSyKey(){
         //获取代理商ID的证书信息
        String privateKey="";
        String keyStorePath="";
        String keyStorePassword="";
        String alias="";
        String aliasPassword="";

        Properties dpro1 = new Properties();
        InputStream dealerins1=null;
        String dealerIDFilePath ="F:\\无纸化数据补传\\config\\"+152+".properties";  
        try {
            dealerins1 = new BufferedInputStream(new FileInputStream(dealerIDFilePath));
            dpro1.load(dealerins1);
            keyStorePath=dpro1.getProperty("keyStorePath").trim();  
            keyStorePath=new String(keyStorePath.getBytes("ISO-8859-1"),"utf-8");
            System.out.println("keyStorePath:"+keyStorePath);
            keyStorePassword=dpro1.getProperty("keyStorePassword").trim();  
            alias=dpro1.getProperty("alias").trim(); 
            aliasPassword=dpro1.getProperty("aliasPassword").trim();                
            KeyStore ks = getKeyStore(keyStorePath, keyStorePassword);
            PrivateKey prkey = (PrivateKey) ks.getKey(alias,
                aliasPassword.toCharArray());
            privateKey=Base64Utils.encode(prkey.getEncoded());
            dealerins1.close();
            return privateKey;
        } catch (Exception e) {
            System.out.println("e:"+e);
        }
        return privateKey;
        
    }
    /***
     * 通过4个参数获取私钥
     * @param keyStorePath
     * @param keyStorePassword
     * @param alias
     * @param aliasPassword
     * @return 
     */
    public static String getSyKey(String keyStorePath,String keyStorePassword,String alias,String aliasPassword){
         //获取代理商ID的证书信息
        String privateKey="";

        try {            
            KeyStore ks = getKeyStore(keyStorePath, keyStorePassword);
            PrivateKey prkey = (PrivateKey) ks.getKey(alias,
                aliasPassword.toCharArray());
            privateKey=Base64Utils.encode(prkey.getEncoded());
            return privateKey;
        } catch (Exception e) {
            System.out.println("e:"+e);
        }
        return privateKey;
        
    }
    /***
     * 根据参数获取公钥
     * @param CertificateAddress
     * @return
     * @throws Exception 
     */
    public static String getGyKey(String CertificateAddress) throws Exception{
         //获取代理商ID的证书信息
           String publicKey="";        
            Certificate certificate =getCertificate(CertificateAddress);
            PublicKey key = certificate.getPublicKey();
            //Base64Utils.encode(key.getEncoded());
            publicKey=Base64Utils.encode(key.getEncoded());            
        return publicKey;
        
    }
    public static String getGyKey(){
         //获取代理商ID的证书信息
        String publicKey="";
        String CertificateAddress="";
        Properties dpro = new Properties();
        InputStream dealerins=null;
        String dealerIDFilePath ="F:\\无纸化数据补传\\config\\"+152+".properties";  
        try {
            dealerins = new BufferedInputStream(new FileInputStream(dealerIDFilePath));
            dpro.load(dealerins);
            CertificateAddress = dpro.getProperty("DealerCertificateAddress");
            CertificateAddress=new String(CertificateAddress.getBytes("ISO-8859-1"),"utf-8");
            Certificate certificate =getCertificate(CertificateAddress);
            PublicKey key = certificate.getPublicKey();
            //Base64Utils.encode(key.getEncoded());
            publicKey=Base64Utils.encode(key.getEncoded());            
            dealerins.close();
            return publicKey;
        } catch (Exception e) {
            System.out.println("e:"+e);
        }
        return publicKey;
        
    }
        private static Certificate getCertificate(String certificatePath)
            throws Exception {
        CertificateFactory certificateFactory = CertificateFactory
                .getInstance(X509);
        FileInputStream in = new FileInputStream(certificatePath);

        Certificate certificate = certificateFactory.generateCertificate(in);
        in.close();

        return certificate;
    }
    public static PrivateKey getPrivateKey(String keyStorePath,
            String keyStorePassword, String alias, String aliasPassword) {
        try {
            KeyStore ks = getKeyStore(keyStorePath, keyStorePassword);
            PrivateKey key = (PrivateKey) ks.getKey(alias,
                    aliasPassword.toCharArray());
            return key;
        } catch (Exception e) {
            System.out.println("e:"+e);
        }
        return null;
    }
        private static KeyStore getKeyStore(String keyStorePath, String password)
            throws Exception {
            FileInputStream is = new FileInputStream(keyStorePath);
            KeyStore ks = KeyStore.getInstance(KEY_STORE);
            ks.load(is, password.toCharArray());
            is.close();
            return ks;
    }
    static void testHttpSign() throws Exception {
       // String FilePath="F:\\request\\hp_C515_2017088_data.xml";
        String FilePath ="E:\\gamb\\xx.txt";  
        String privateKey1=getSyKey();
        System.err.println("privatekey:"+privateKey1);
        String publicKey1=getGyKey();
        System.err.println("publickey:"+publicKey1);
        File f =FileUtil.getFile(FilePath);
            //读取文件内容
        String param=FileUtil.getFileContent(f);
        byte[] body_enc = MD5Encode(param);
        System.err.println("ssssssssssssss"+body_enc.length);
        byte[] encodedData = RSAUtils.encryptByPrivateKey(body_enc, privateKey1);
        System.out.println("加密后：" + encodedData);
        
        byte[] decodedData = RSAUtils.decryptByPublicKey(encodedData, publicKey1);
        System.out.println("解密后：" + new String(decodedData));
        
        String sign = RSAUtils.sign(encodedData, privateKey1);
        System.err.println("签名：" + sign);    
        FileOutputStream out = null;
        try {
            //out = new FileOutputStream(FileUtil.makeNewFile("F:\\request\\hp_C515_2017088_data_sign.xml")); //  
            out = new FileOutputStream(FileUtil.makeNewFile("E:\\gamb\\xx_sign.txt"));
            out.write(sign.getBytes());  
            out.close();
        } catch (FileNotFoundException ex) {
            java.util.logging.Logger.getLogger(CreateRequestMapXml.class.getName()).log(Level.SEVERE, null, ex);
        }
                       
        boolean status = RSAUtils.verify(encodedData, publicKey1, sign);
        System.err.println("签名验证结果：" + status);
    }
}
