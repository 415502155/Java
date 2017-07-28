package com.bestinfo.gambler.rsasign;

import it.sauronsoftware.base64.Base64;
import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.security.KeyStore;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Signature;
import java.security.cert.Certificate;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.util.Date;
import java.util.Properties;

import javax.crypto.Cipher;

/** *//**
 * <p>
 * ����ǩ��/���ܽ��ܹ��߰�
 * </p>
 * 
 * @author IceWee
 * @date 2012-4-26
 * @version 1.0
 */
public class CertificateUtils {

	public static String KEY_STORE_NAME = "myprivate";
	public static String CERTIFICATE_NAME = "02_public";
	public static String password = "12345";
	public static String alias = "xinguan";
	public static String certificatePath;
	public static String keyStorePath;
	public static String keysPath;
    
//    static {
//       
//        
//	    try{
//	      	Properties prop = new Properties();
//	        FileInputStream fis = new FileInputStream("conf/interface.properties");
//	        prop.load(fis);
//	        password = prop.getProperty("bys_xg_sigpassword");
//	        alias = prop.getProperty("bys_xg_alias");
//	        KEY_STORE_NAME = prop.getProperty("bys_xg_private");
//	        CERTIFICATE_NAME = prop.getProperty("bys_xg_public"); 
//	        keysPath = prop.getProperty("bys_keys_path"); 
//	       
//	        keyStorePath = keysPath + KEY_STORE_NAME;
//	        certificatePath = keysPath + CERTIFICATE_NAME;
//	        
//	    }catch(Exception ne){
//	    	ne.printStackTrace();
//	    }
//        
//    }
	

    public static final String KEY_STORE = "JKS";
    //public static final String KEY_STORE = "PKCS12";

    public static final String X509 = "X.509";
    
    private static final int CACHE_SIZE = 2048;
    
    private static final int MAX_ENCRYPT_BLOCK = 117;
    
    private static final int MAX_DECRYPT_BLOCK = 128;

    public static void main(String[] args) throws Exception {
         String keyStorePath="e:\\151.p12";
         String alias="xinguan01";
         String password="745302";
         getPrivateKey(keyStorePath,alias,password);
    }
    public static PrivateKey getPrivateKey(String keyStorePath, String alias, String password) 
            throws Exception {
        KeyStore keyStore = getKeyStore(keyStorePath, password);
        PrivateKey privateKey = (PrivateKey) keyStore.getKey(alias, password.toCharArray());
        String a =Base64Utils.encode(privateKey.getEncoded());
        System.err.println("privateKey:"+a);
        return privateKey;
    }  
    public static String getPKEY(String keyStorePath, String alias, String password) 
            throws Exception {
        KeyStore keyStore = getKeyStore(keyStorePath, password);
        PrivateKey pk = (PrivateKey) keyStore.getKey(alias, password.toCharArray());
        String privateKey =Base64Utils.encode(pk.getEncoded());
        System.err.println("privateKey:"+privateKey);
        return privateKey;
    }
    
    /** *//**
     * <p>
     * �����Կ��
     * </p>
     * 
     * @param keyStorePath ��Կ��洢·��
     * @param password ��Կ������
     * @return
     * @throws Exception
     */
    public static KeyStore getKeyStore(String keyStorePath, String password)
            throws Exception {
        FileInputStream in = new FileInputStream(keyStorePath);
        KeyStore keyStore = KeyStore.getInstance(KEY_STORE);
        keyStore.load(in, password.toCharArray());
        in.close();
        return keyStore;
    }

    /** *//**
     * <p>
     * ����֤���ù�Կ
     * </p>
     * 
     * @param certificatePath ֤��洢·��
     * @return
     * @throws Exception
     */
    public static PublicKey getPublicKey(String certificatePath)
            throws Exception {
        Certificate certificate = getCertificate(certificatePath);
        PublicKey publicKey = certificate.getPublicKey();
        return publicKey;
    }

    /** *//**
     * <p>
     * ���֤��
     * </p>
     * 
     * @param certificatePath ֤��洢·��
     * @return
     * @throws Exception
     */
    public static Certificate getCertificate(String certificatePath)
            throws Exception {
        CertificateFactory certificateFactory = CertificateFactory.getInstance(X509);
        FileInputStream in = new FileInputStream(certificatePath);
        Certificate certificate = certificateFactory.generateCertificate(in);
        in.close();
        return certificate;
    }

    /** *//**
     * <p>
     * ������Կ����֤��
     * </p>
     * 
     * @param keyStorePath ��Կ��洢·��
     * @param alias ��Կ�����
     * @param password ��Կ������
     * @return
     * @throws Exception
     */
    public static Certificate getCertificate(String keyStorePath, String alias, String password) 
            throws Exception {
        KeyStore keyStore = getKeyStore(keyStorePath, password);
        Certificate certificate = keyStore.getCertificate(alias);
        return certificate;
    }

    /** *//**
     * <p>
     * ˽Կ����
     * </p>
     * 
     * @param data Դ����
     * @param keyStorePath ��Կ��洢·��
     * @param alias ��Կ�����
     * @param password ��Կ������
     * @return
     * @throws Exception
     */
    public static byte[] encryptByPrivateKey(byte[] data, String keyStorePath, String alias, String password) 
            throws Exception {
        // ȡ��˽Կ
        PrivateKey privateKey = getPrivateKey(keyStorePath, alias, password);
        Cipher cipher = Cipher.getInstance(privateKey.getAlgorithm());
        cipher.init(Cipher.ENCRYPT_MODE, privateKey);
        int inputLen = data.length;
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        int offSet = 0;
        byte[] cache;
        int i = 0;
        // �����ݷֶμ���
        while (inputLen - offSet > 0) {
            if (inputLen - offSet > MAX_ENCRYPT_BLOCK) {
                cache = cipher.doFinal(data, offSet, MAX_ENCRYPT_BLOCK);
            } else {
                cache = cipher.doFinal(data, offSet, inputLen - offSet);
            }
            out.write(cache, 0, cache.length);
            i++;
            offSet = i * MAX_ENCRYPT_BLOCK;
        }
        byte[] encryptedData = out.toByteArray();
        out.close();
        return encryptedData;
    }
    
    

    /** *//**
     * <p>
     * �ļ�����
     * </p>
     * 
     * @param srcFilePath Դ�ļ�
     * @param destFilePath ���ܺ��ļ�
     * @param keyStorePath ��Կ��洢·��
     * @param alias ��Կ�����
     * @param password ��Կ������
     * @throws Exception
     */
    public static void encryptFileByPrivateKey(String srcFilePath, String destFilePath, String keyStorePath, String alias, String password)
            throws Exception {
        // ȡ��˽Կ
        PrivateKey privateKey = getPrivateKey(keyStorePath, alias, password);
        Cipher cipher = Cipher.getInstance(privateKey.getAlgorithm());
        cipher.init(Cipher.ENCRYPT_MODE, privateKey);
        File srcFile = new File(srcFilePath);
        FileInputStream in = new FileInputStream(srcFile);
        File destFile = new File(destFilePath);
        if (!destFile.getParentFile().exists()) {
            destFile.getParentFile().mkdirs();
        }
        destFile.createNewFile();
        OutputStream out = new FileOutputStream(destFile);   
        byte[] data = new byte[MAX_ENCRYPT_BLOCK];
        byte[] encryptedData;    // ���ܿ�
        while (in.read(data) != -1) {
            encryptedData = cipher.doFinal(data);
            out.write(encryptedData, 0, encryptedData.length);
            out.flush();
        }
        out.close();
        in.close();
    }

    
    
    /** *//**
     * <p>
     * ˽Կ����
     * </p>
     * 
     * @param encryptedData �Ѽ�������
     * @param keyStorePath ��Կ��洢·��
     * @param alias ��Կ�����
     * @param password ��Կ������
     * @return
     * @throws Exception
     */
    public static byte[] decryptByPrivateKey(byte[] encryptedData, String keyStorePath, String alias, String password) 
            throws Exception {
        // ȡ��˽Կ
        PrivateKey privateKey = getPrivateKey(keyStorePath, alias, password);
        Cipher cipher = Cipher.getInstance(privateKey.getAlgorithm());
        cipher.init(Cipher.DECRYPT_MODE, privateKey);
        // ����byte������󳤶�����: 128
        int inputLen = encryptedData.length;
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        int offSet = 0;
        byte[] cache;
        int i = 0;
        // �����ݷֶν���
        while (inputLen - offSet > 0) {
            if (inputLen - offSet > MAX_DECRYPT_BLOCK) {
                cache = cipher.doFinal(encryptedData, offSet, MAX_DECRYPT_BLOCK);
            } else {
                cache = cipher.doFinal(encryptedData, offSet, inputLen - offSet);
            }
            out.write(cache, 0, cache.length);
            i++;
            offSet = i * MAX_DECRYPT_BLOCK;
        }
        byte[] decryptedData = out.toByteArray();
        out.close();
        return decryptedData;
    }

    /** *//**
     * <p>
     * ��Կ����
     * </p>
     * 
     * @param data Դ����
     * @param certificatePath ֤��洢·��
     * @return
     * @throws Exception
     */
    public static byte[] encryptByPublicKey(byte[] data, String certificatePath)
            throws Exception {
        // ȡ�ù�Կ
        PublicKey publicKey = getPublicKey(certificatePath);
        Cipher cipher = Cipher.getInstance(publicKey.getAlgorithm());
        cipher.init(Cipher.ENCRYPT_MODE, publicKey);
        int inputLen = data.length;
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        int offSet = 0;
        byte[] cache;
        int i = 0;
        // �����ݷֶμ���
        while (inputLen - offSet > 0) {
            if (inputLen - offSet > MAX_ENCRYPT_BLOCK) {
                cache = cipher.doFinal(data, offSet, MAX_ENCRYPT_BLOCK);
            } else {
                cache = cipher.doFinal(data, offSet, inputLen - offSet);
            }
            out.write(cache, 0, cache.length);
            i++;
            offSet = i * MAX_ENCRYPT_BLOCK;
        }
        byte[] encryptedData = out.toByteArray();
        out.close();
        return encryptedData;
    }

    /** *//**
     * <p>
     * ��Կ����
     * </p>
     * 
     * @param encryptedData �Ѽ�������
     * @param certificatePath ֤��洢·��
     * @return
     * @throws Exception
     */
    public static byte[] decryptByPublicKey(byte[] encryptedData, String certificatePath)
            throws Exception {
        PublicKey publicKey = getPublicKey(certificatePath);
        Cipher cipher = Cipher.getInstance(publicKey.getAlgorithm());
        cipher.init(Cipher.DECRYPT_MODE, publicKey);
        int inputLen = encryptedData.length;
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        int offSet = 0;
        byte[] cache;
        int i = 0;
        // �����ݷֶν���
        while (inputLen - offSet > 0) {
            if (inputLen - offSet > MAX_DECRYPT_BLOCK) {
                cache = cipher.doFinal(encryptedData, offSet, MAX_DECRYPT_BLOCK);
            } else {
                cache = cipher.doFinal(encryptedData, offSet, inputLen - offSet);
            }
            out.write(cache, 0, cache.length);
            i++;
            offSet = i * MAX_DECRYPT_BLOCK;
        }
        byte[] decryptedData = out.toByteArray();
        out.close();
        return decryptedData;
    }
    
    /** *//**
     * <p>
     * �ļ�����
     * </p>
     * 
     * @param srcFilePath Դ�ļ�
     * @param destFilePath Ŀ���ļ�
     * @param certificatePath ֤��洢·��
     * @throws Exception
     */
    public static void decryptFileByPublicKey(String srcFilePath, String destFilePath, String certificatePath)
            throws Exception {
        PublicKey publicKey = getPublicKey(certificatePath);
        Cipher cipher = Cipher.getInstance(publicKey.getAlgorithm());
        cipher.init(Cipher.DECRYPT_MODE, publicKey);
        File srcFile = new File(srcFilePath);
        FileInputStream in = new FileInputStream(srcFile);
        File destFile = new File(destFilePath);
        if (!destFile.getParentFile().exists()) {
            destFile.getParentFile().mkdirs();
        }
        destFile.createNewFile();
        OutputStream out = new FileOutputStream(destFile);   
        byte[] data = new byte[MAX_DECRYPT_BLOCK];
        byte[] decryptedData;    // ���ܿ�
        while (in.read(data) != -1) {
            decryptedData = cipher.doFinal(data);
            out.write(decryptedData, 0, decryptedData.length); 
            out.flush();
        }
        out.close();
        in.close();
    }
    
    /** *//**
     * <p>
     * ��������ǩ��
     * </p>
     * 
     * @param data Դ����
     * @param keyStorePath ��Կ��洢·��
     * @param alias ��Կ�����
     * @param password ��Կ������
     * @return
     * @throws Exception
     */
    public static byte[] sign(byte[] data, String keyStorePath, String alias, String password) 
            throws Exception {
        // ���֤��
        X509Certificate x509Certificate = (X509Certificate) getCertificate(keyStorePath, alias, password);
        // ��ȡ˽Կ
        KeyStore keyStore = getKeyStore(keyStorePath, password);
        // ȡ��˽Կ
        PrivateKey privateKey = (PrivateKey) keyStore.getKey(alias, password.toCharArray());
        // ����ǩ��
        Signature signature = Signature.getInstance(x509Certificate.getSigAlgName());
        signature.initSign(privateKey);
        signature.update(data);
        return signature.sign();
    }
    
    /** *//**
     * <p>
     * ��������ǩ������BASE64����
     * </p>
     * 
     * @param data Դ����
     * @param keyStorePath ��Կ��洢·��
     * @param alias ��Կ�����
     * @param password ��Կ������
     * @return
     * @throws Exception
     */
    public static String signToBase64(byte[] data, String keyStorePath, String alias, String password) 
            throws Exception {
        return new String(Base64.encode(sign(data, keyStorePath, alias, password)));
    }
    
   
    
    /** *//**
     * <p>
     * �����ļ�ǩ��
     * </p>
     * <p>
     * ע�⣺<br>
     * ������ʹ����FileChannel����޴�Bug���ǲ����ͷ��ļ����������ǩ�����ļ��޷�����(�ƶ���ɾ����)<br>
     * �÷����ѱ�generateFileSignȡ��
     * </p>
     * 
     * @param filePath �ļ�·��
     * @param keyStorePath ��Կ��洢·��
     * @param alias ��Կ�����
     * @param password ��Կ������
     * @return
     * @throws Exception
     */
    @Deprecated
    public static byte[] signFile(String filePath, String keyStorePath, String alias, String password)
            throws Exception {
        byte[] sign = new byte[0];
        // ���֤��
        X509Certificate x509Certificate = (X509Certificate) getCertificate(keyStorePath, alias, password);
        // ��ȡ˽Կ
        KeyStore keyStore = getKeyStore(keyStorePath, password);
        // ȡ��˽Կ
        PrivateKey privateKey = (PrivateKey) keyStore.getKey(alias, password.toCharArray());
        // ����ǩ��
        Signature signature = Signature.getInstance(x509Certificate.getSigAlgName());
        signature.initSign(privateKey);
        File file = new File(filePath);
        if (file.exists()) {
            FileInputStream in = new FileInputStream(file);
            FileChannel fileChannel = in.getChannel();
            MappedByteBuffer byteBuffer = fileChannel.map(FileChannel.MapMode.READ_ONLY, 0, file.length());
            signature.update(byteBuffer);
            fileChannel.close();
            in.close();
            sign = signature.sign();
         }
        return sign;
    }
    
    /** *//**
     * <p>
     * �����ļ�����ǩ��
     * </p>
     * 
      * <p>
     * <b>ע�⣺</b><br>
     * ����ǩ��ʱupdate��byte�����С����֤ǩ��ʱ�Ĵ�СӦ��ͬ��������֤�޷�ͨ��
     * </p>
     * 
     * @param filePath
     * @param keyStorePath
     * @param alias
     * @param password
     * @return
     * @throws Exception
     */
    public static byte[] generateFileSign(String filePath, String keyStorePath, String alias, String password)
            throws Exception {
        byte[] sign = new byte[0];
        // ���֤��
        X509Certificate x509Certificate = (X509Certificate) getCertificate(keyStorePath, alias, password);
        // ��ȡ˽Կ
        KeyStore keyStore = getKeyStore(keyStorePath, password);
        // ȡ��˽Կ
        PrivateKey privateKey = (PrivateKey) keyStore.getKey(alias, password.toCharArray());
        // ����ǩ��
        Signature signature = Signature.getInstance(x509Certificate.getSigAlgName());
        signature.initSign(privateKey);
        File file = new File(filePath);
        if (file.exists()) {
            FileInputStream in = new FileInputStream(file);
            byte[] cache = new byte[CACHE_SIZE];
            int nRead = 0;
            while ((nRead = in.read(cache)) != -1) {
                signature.update(cache, 0, nRead);
            }
            in.close();
            sign = signature.sign();
         }
        return sign;
    }
    
    /** *//**
     * <p>
     * �ļ�ǩ����BASE64�����ַ���
     * </p>
     * 
     * @param filePath
     * @param keyStorePath
     * @param alias
     * @param password
     * @return
     * @throws Exception
     */
    public static String signFileToBase64(String filePath, String keyStorePath, String alias, String password)
            throws Exception {
        return new String(Base64.encode(generateFileSign(filePath, keyStorePath, alias, password)));
    }
    
 
}

