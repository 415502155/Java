package com.bestinfo.arithmetic;

import java.io.FileInputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.InputStream;
import java.io.StringReader;
import java.io.StringWriter;
import java.security.KeyPair;
import java.security.KeyStore;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Security;
import java.security.Signature;
import java.security.cert.Certificate;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.util.Date;

import javax.crypto.Cipher;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.bouncycastle.asn1.x509.SubjectPublicKeyInfo;
import org.bouncycastle.openssl.PEMParser;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.openssl.PEMDecryptorProvider;
import org.bouncycastle.openssl.PEMEncryptedKeyPair;
import org.bouncycastle.openssl.PEMKeyPair;
import org.bouncycastle.openssl.jcajce.JcaPEMKeyConverter;
import org.bouncycastle.openssl.jcajce.JcePEMDecryptorProviderBuilder;
import org.bouncycastle.util.io.pem.PemObject;
import org.bouncycastle.util.io.pem.PemWriter;

/**
 *
 * @author YangRong
 */
public class CaTool {

    private static final Logger logger = Logger.getLogger(CaTool.class);
    /**
     * Java密钥库(Java Key Store，JKS)KEY_STORE
     */
    private static final String KEY_STORE = "PKCS12";//keystore格式
    public static final String X509 = "X.509";//证书格式

    public CaTool() {
        if (Security.getProvider(BouncyCastleProvider.PROVIDER_NAME) == null) {
            Security.addProvider(new BouncyCastleProvider());
        }

    }

    /**
     * 将p12输入流里的私钥读出,转成pem字符串
     *
     * @param is
     * @param storePassword
     * @param alias
     * @param keyPassword
     * @return
     */
    private String privatekeyToPemString(InputStream is, String storePassword, String alias, String keyPassword) {
        try {
            PrivateKey key = getPrivateKey(is, storePassword, alias, keyPassword);
            StringWriter writer = new StringWriter();
            PemWriter pemwriter = new PemWriter(writer);
            pemwriter.writeObject(new PemObject("RSA PRIVATE KEY", key.getEncoded()));
            pemwriter.flush();
            pemwriter.close();
//            PEMWriter pemwriter = new PEMWriter(writer);
//            pemwriter.writeObject(key);
//            pemwriter.flush();
//            pemwriter.close();

            return writer.toString();
        } catch (Exception e) {
            logger.error("", e);
        }
        return null;
    }

    /**
     * 将p12文件里的私钥读出,转成pem字符串
     *
     * @param storePath
     * @param storePassword
     * @param alias
     * @param keyPassword
     * @return
     */
    private String privatekeyToPemString(String storePath, String storePassword, String alias, String keyPassword) {
        try {
            FileInputStream fis = new FileInputStream(storePath);
            PrivateKey key = getPrivateKey(fis, storePassword, alias, keyPassword);
            StringWriter writer = new StringWriter();
            PemWriter pemwriter = new PemWriter(writer);
            pemwriter.writeObject(new PemObject("RSA PRIVATE KEY", key.getEncoded()));
            pemwriter.flush();
            pemwriter.close();
//            PEMWriter pemwriter = new PEMWriter(writer);
//            pemwriter.writeObject(key);
//            pemwriter.flush();
//            pemwriter.close();

            return writer.toString();
        } catch (Exception e) {
            logger.error("", e);
        }
        return null;
    }

    /**
     * 私钥转成pem字符串
     *
     * @param key
     * @return
     */
    public String privatekeyToPemString(PrivateKey key) {
        try {

            StringWriter writer = new StringWriter();
            PemWriter pemwriter = new PemWriter(writer);
            pemwriter.writeObject(new PemObject("RSA PRIVATE KEY", key.getEncoded()));
            pemwriter.flush();
            pemwriter.close();
//            PEMWriter pemwriter = new PEMWriter(writer);
//            pemwriter.writeObject(key);
//            pemwriter.flush();
//            pemwriter.close();

            return writer.toString();
        } catch (Exception e) {
            logger.error("", e);
        }
        return null;
    }

    /**
     * 将p12输入流里的公钥读出,转成pem字符串
     *
     * @param is
     * @param storePassword
     * @param alias
     * @return
     */
    public String publickeyToPemString(InputStream is, String storePassword, String alias) {
        try {
            PublicKey key = getPublicKey(is, storePassword, alias);
            StringWriter writer = new StringWriter();
            PemWriter pemwriter = new PemWriter(writer);
            pemwriter.writeObject(new PemObject("PUBLIC KEY", key.getEncoded()));
            pemwriter.flush();
            pemwriter.close();
//            PEMWriter pemwriter = new PEMWriter(writer);
//            pemwriter.writeObject(key);
//            pemwriter.flush();
//            pemwriter.close();
            return writer.toString();
        } catch (Exception e) {
            logger.error("", e);
        }
        return null;
    }

    /**
     * 将p12文件里的公钥读出,转成pem字符串
     *
     * @param storePath
     * @param storePassword
     * @param alias
     * @return
     */
    public String publickeyToPemString(String storePath, String storePassword, String alias) {
        try {
            FileInputStream fis = new FileInputStream(storePath);
            PublicKey key = getPublicKey(fis, storePassword, alias);
            StringWriter writer = new StringWriter();
            PemWriter pemwriter = new PemWriter(writer);
            pemwriter.writeObject(new PemObject("PUBLIC KEY", key.getEncoded()));
            pemwriter.flush();
            pemwriter.close();
//            PEMWriter pemwriter = new PEMWriter(writer);
//            pemwriter.writeObject(key);
//            pemwriter.flush();
//            pemwriter.close();
            return writer.toString();
        } catch (Exception e) {
            logger.error("", e);
        }
        return null;
    }

    /**
     * 从pem文件读出公钥
     *
     * @param filePath　文件路径
     * @return 公钥
     */
    public PublicKey loadPublicKeyFromPemFile(final String filePath) {
        try {
            FileReader sr = new FileReader(filePath);
            PEMParser pemParser = new PEMParser(sr);
            JcaPEMKeyConverter converter = new JcaPEMKeyConverter().setProvider("BC");
            Object object = pemParser.readObject();
            PublicKey pb = converter.getPublicKey((SubjectPublicKeyInfo) object);
            pemParser.close();
            return pb;
        } catch (Exception e) {
            logger.error("", e);
            return null;
        }

    }

    /**
     * 保存公钥到pem文件
     *
     * @param key　公钥
     * @param filePath　pem文件
     * @return
     */
    public int savePublickeyToPemFile(PublicKey key, String filePath) {
        try {

            FileWriter writer = new FileWriter(filePath);
            PemWriter pemwriter = new PemWriter(writer);
            pemwriter.writeObject(new PemObject("PUBLIC KEY", key.getEncoded()));
            pemwriter.flush();
            pemwriter.close();
            writer.close();
//            PEMWriter pemwriter = new PEMWriter(writer);
//            pemwriter.writeObject(key);
//            pemwriter.flush();
//            pemwriter.close();
            return 0;
        } catch (Exception e) {
            logger.error("", e);
        }
        return -1;

    }

    /**
     * 公钥转成pem字符串
     *
     * @param key
     * @return
     */
    public String publickeyToPemString(PublicKey key) {
        try {

            StringWriter writer = new StringWriter();
            PemWriter pemwriter = new PemWriter(writer);
            pemwriter.writeObject(new PemObject("PUBLIC KEY", key.getEncoded()));
            pemwriter.flush();
            pemwriter.close();
//            PEMWriter pemwriter = new PEMWriter(writer);
//            pemwriter.writeObject(key);
//            pemwriter.flush();
//            pemwriter.close();
            return writer.toString();
        } catch (Exception e) {
            logger.error("", e);
        }
        return null;
    }

    public KeyPair loadPrivateKeyFromPem(final String pemString, String password) {
        try {
            StringReader sr = new StringReader(pemString);
            PEMParser pemParser = new PEMParser(sr);
            PEMDecryptorProvider decProv = new JcePEMDecryptorProviderBuilder().build(password.toCharArray());
            JcaPEMKeyConverter converter = new JcaPEMKeyConverter().setProvider("BC");
            Object object = pemParser.readObject();
            KeyPair kp = null;
            if (object instanceof PEMEncryptedKeyPair) {
                logger.info("Encrypted key - we will use provided password");
                kp = converter.getKeyPair(((PEMEncryptedKeyPair) object).decryptKeyPair(decProv));
            } else {
                logger.info("Unencrypted key - no password needed");
                kp = converter.getKeyPair((PEMKeyPair) object);
            }

            pemParser.close();
            return kp;

        } catch (Exception e) {
            logger.error("", e);
            return null;
        }

    }

    /**
     * 将pem字符串转成私钥公钥对
     *
     * @param pemString
     * @return
     */
    public KeyPair loadPrivateKeyFromPem(final String pemString) {
        try {
            StringReader sr = new StringReader(pemString);
            PEMParser pemParser = new PEMParser(sr);
//           PEMDecryptorProvider decProv = new JcePEMDecryptorProviderBuilder().build(password.toCharArray());
            JcaPEMKeyConverter converter = new JcaPEMKeyConverter().setProvider("BC");
            Object object = pemParser.readObject();
            KeyPair kp = converter.getKeyPair((PEMKeyPair) object);
//            if (object instanceof PEMEncryptedKeyPair) {
//                logger.info("Encrypted key - we will use provided password");
//                kp = converter.getKeyPair(((PEMEncryptedKeyPair) object).decryptKeyPair(decProv));
//            } else {
//                logger.info("Unencrypted key - no password needed");
//                kp = converter.getKeyPair((PEMKeyPair) object);
//            }

            pemParser.close();
            return kp;

        } catch (Exception e) {
            logger.error("", e);
            return null;
        }

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
            logger.error("", e);
            return null;
        }

    }

    /**
     * 由 KeyStore获得私钥
     *
     * @param keyStorePath
     * @param keyStorePassword
     * @param alias
     * @param aliasPassword
     * @return
     *
     */
    public PrivateKey getPrivateKey(String keyStorePath,
            String keyStorePassword, String alias, String aliasPassword) {
        try {
            KeyStore ks = getKeyStore(keyStorePath, keyStorePassword);
            PrivateKey key = (PrivateKey) ks.getKey(alias,
                    aliasPassword.toCharArray());
            return key;
        } catch (Exception e) {
            logger.error("", e);
        }
        return null;
    }

    /**
     * 由 KeyStore获得私钥
     *
     * @param is
     * @param keyStorePassword
     * @param alias
     * @param aliasPassword
     * @return
     *
     */
    public PrivateKey getPrivateKey(InputStream is,
            String keyStorePassword, String alias, String aliasPassword) {
        try {
            KeyStore ks = getKeyStore(is, keyStorePassword);

            PrivateKey key = (PrivateKey) ks.getKey(alias,
                    aliasPassword.toCharArray());
            return key;
        } catch (Exception e) {
            logger.error("", e);
        }
        return null;
    }

    /**
     * 由 Certificate获得公钥
     *
     * @param certificatePath
     * @return
     * @throws Exception
     */
    public PublicKey getPublicKey(String certificatePath) {
        try {
            Certificate certificate = getCertificate(certificatePath);
            PublicKey key = certificate.getPublicKey();
            return key;
        } catch (Exception e) {
            logger.error("", e);
            return null;
        }
    }

    /**
     * 获得公钥
     *
     * @param is
     * @param keyStorePassword
     * @param alias
     * @return
     *
     */
    public PublicKey getPublicKey(InputStream is,
            String keyStorePassword, String alias) {
        try {
            Certificate certificate = getCertificate(is, keyStorePassword, alias);
            PublicKey key = certificate.getPublicKey();

            return key;
        } catch (Exception e) {
            logger.error("", e);
        }
        return null;
    }

    /**
     * 获得公钥
     *
     * @param keyStorePath
     * @param keyStorePassword
     * @param alias
     * @return
     *
     */
    public PublicKey getPublicKey(String keyStorePath,
            String keyStorePassword, String alias) {
        try {
            Certificate certificate = getCertificate(keyStorePath, keyStorePassword, alias);
            PublicKey key = certificate.getPublicKey();
//            X509Certificate x509Certificate = (X509Certificate) certificate;
//            byte[] sign =x509Certificate.getSignature();
            return key;
        } catch (Exception e) {
            logger.error("", e);
        }
        return null;
    }

    /**
     * 读取证书文件,获得Certificate
     *
     * @param certificatePath
     * @return
     * @throws Exception
     */
    private static Certificate getCertificate(String certificatePath)
            throws Exception {
        CertificateFactory certificateFactory = CertificateFactory
                .getInstance(X509);
        FileInputStream in = new FileInputStream(certificatePath);

        Certificate certificate = certificateFactory.generateCertificate(in);
        in.close();

        return certificate;
    }

    /**
     * 从keystore里获得Certificate
     *
     * @param keyStorePath
     * @param keyStorePassword
     * @param alias
     * @return
     * @throws Exception
     */
    private Certificate getCertificate(String keyStorePath,
            String keyStorePassword, String alias) throws Exception {
        KeyStore ks = getKeyStore(keyStorePath, keyStorePassword);
        Certificate certificate = ks.getCertificate(alias);

        return certificate;
    }

    /**
     * 从keystore里获得Certificate
     *
     * @param is
     * @param keyStorePassword
     * @param alias
     * @return
     * @throws Exception
     */
    private Certificate getCertificate(InputStream is,
            String keyStorePassword, String alias) throws Exception {
        KeyStore ks = getKeyStore(is, keyStorePassword);
        Certificate certificate = ks.getCertificate(alias);

        return certificate;
    }

    /**
     * 获得KeyStore
     *
     * @param keyStorePath
     * @param password
     * @return
     * @throws Exception
     */
    private KeyStore getKeyStore(String keyStorePath, String password)
            throws Exception {
        FileInputStream is = new FileInputStream(keyStorePath);
        KeyStore ks = KeyStore.getInstance(KEY_STORE);
        ks.load(is, password.toCharArray());
        is.close();
        return ks;
    }

    /**
     * 获得KeyStore
     *
     * @param is
     * @param password
     * @return
     * @throws Exception
     */
    private KeyStore getKeyStore(InputStream is, String password)
            throws Exception {
        KeyStore ks = KeyStore.getInstance(KEY_STORE);
        ks.load(is, password.toCharArray());
        is.close();
        return ks;
    }

    /**
     * 私钥加密
     *
     * @param data
     * @param keyStorePath
     * @param keyStorePassword
     * @param alias
     * @param aliasPassword
     * @return
     * @throws Exception
     */
    public byte[] encryptByPrivateKey(byte[] data, String keyStorePath,
            String keyStorePassword, String alias, String aliasPassword) {
        try {
            // 取得私钥  
            PrivateKey privateKey = getPrivateKey(keyStorePath, keyStorePassword,
                    alias, aliasPassword);
            if(privateKey==null){
                logger.error("private key is null");
                return null;
            }
            // 对数据加密  
            Cipher cipher = Cipher.getInstance(privateKey.getAlgorithm());
            cipher.init(Cipher.ENCRYPT_MODE, privateKey);

            return cipher.doFinal(data);
        } catch (Exception e) {
            logger.error(e);
        }
        return null;
    }

    /**
     * 私钥加密
     *
     * @param data
     * @param privateKey
     * @return
     */
    public byte[] encryptByPrivateKey(byte[] data, PrivateKey privateKey) {
        try {
            if(privateKey==null){
                logger.error("private key is null");
                return null;
            }
            // 对数据加密  
            Cipher cipher = Cipher.getInstance(privateKey.getAlgorithm());
            cipher.init(Cipher.ENCRYPT_MODE, privateKey);

            return cipher.doFinal(data);
        } catch (Exception e) {
            logger.error(e);
        }
        return null;

    }

    /**
     * 私钥解密
     *
     * @param data
     * @param keyStorePath
     * @param alias
     * @param keyStorePassword
     * @param aliasPassword
     * @return
     * @throws Exception
     */
    public byte[] decryptByPrivateKey(byte[] data, String keyStorePath,
            String alias, String keyStorePassword, String aliasPassword) {
        try {
            // 取得私钥  
            PrivateKey privateKey = getPrivateKey(keyStorePath, keyStorePassword,
                    alias, aliasPassword);
            if(privateKey==null){
                logger.error("private key is null");
                return null;
            }
            // 对数据加密  
            Cipher cipher = Cipher.getInstance(privateKey.getAlgorithm());
            cipher.init(Cipher.DECRYPT_MODE, privateKey);

            return cipher.doFinal(data);
        } catch (Exception e) {
            logger.error(e);
        }
        return null;
    }

    /**
     * 私钥解密
     *
     * @param data
     * @param privateKey
     * @return
     */
    public byte[] decryptByPrivateKeyBC(byte[] data, PrivateKey privateKey) {
        try {
            if(privateKey==null){
                logger.error("private key is null");
                return null;
            }
            // 对数据加密  
            Cipher cipher = Cipher.getInstance(privateKey.getAlgorithm(), "BC");
//            Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding", "BC");
            cipher.init(Cipher.DECRYPT_MODE, privateKey);
            return cipher.doFinal(data);
        } catch (Exception e) {
            logger.error(e);
        }
        return ArrayUtils.EMPTY_BYTE_ARRAY;
    }

    public byte[] decryptByPrivateKey(byte[] data, PrivateKey privateKey) {
        try {
            if (privateKey == null) {
                logger.error("private key is null");
                return null;
            }
            // 对数据加密  
            Cipher cipher = Cipher.getInstance(privateKey.getAlgorithm());
//            Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
            cipher.init(Cipher.DECRYPT_MODE, privateKey);

            return cipher.doFinal(data);
        } catch (Exception e) {
            logger.error(e);
        }
        return null;
    }

    /**
     * 公钥加密,读取证书公钥,然后加密
     *
     * @param data
     * @param certificatePath
     * @return
     * @throws Exception
     */
    public byte[] encryptByPublicKey(byte[] data, String certificatePath) {
        try {
            // 取得公钥  
            PublicKey publicKey = getPublicKey(certificatePath);
            if(publicKey==null){
                logger.error("public key is null");
                return null;
            }
            // 对数据加密  
            Cipher cipher = Cipher.getInstance(publicKey.getAlgorithm());
            cipher.init(Cipher.ENCRYPT_MODE, publicKey);

            return cipher.doFinal(data);
        } catch (Exception e) {
            logger.error(e);
        }
        return null;

    }

    /**
     * 读取keystore里的公钥,然后加密
     *
     * @param data
     * @param keyStorePath
     * @param storePassword
     * @param alias
     * @return
     */
    public byte[] encryptByPublicKey(byte[] data, String keyStorePath, String storePassword, String alias) {
        try {
            // 取得公钥  
            PublicKey publicKey = getPublicKey(keyStorePath, storePassword, alias);
            if(publicKey==null){
                logger.error("public key is null");
                return null;
            }
            // 对数据加密  
            Cipher cipher = Cipher.getInstance(publicKey.getAlgorithm());
            cipher.init(Cipher.ENCRYPT_MODE, publicKey);
            return cipher.doFinal(data);
        } catch (Exception e) {
            logger.error(e);
        }
        return null;

    }

    /**
     * 公钥加密
     *
     * @param data
     * @param publicKey
     * @return
     */
    public byte[] encryptByPublicKeyBC(byte[] data, PublicKey publicKey) {
        try {

            // 对数据加密  
            Cipher cipher = Cipher.getInstance(publicKey.getAlgorithm(), "BC");
//            Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding", "BC");
            cipher.init(Cipher.ENCRYPT_MODE, publicKey);

            return cipher.doFinal(data);
        } catch (Exception e) {
            logger.error(e);
        }
        return null;

    }

    public byte[] encryptByPublicKey(byte[] data, PublicKey publicKey) {
        try {
            if(publicKey==null){
                logger.error("publicKey is null");
                return null;
            }
            // 对数据加密  
            Cipher cipher = Cipher.getInstance(publicKey.getAlgorithm());
//            Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
            cipher.init(Cipher.ENCRYPT_MODE, publicKey);

            return cipher.doFinal(data);
        } catch (Exception e) {
            logger.error(e);
        }
        return null;

    }

    /**
     * 公钥解密,读取证书公钥,然后解密
     *
     * @param data
     * @param certificatePath
     * @return
     * 
     */
    public byte[] decryptByPublicKey(byte[] data, String certificatePath) {
        try {
            
            // 取得公钥  
            PublicKey publicKey = getPublicKey(certificatePath);
            if(publicKey==null){
                logger.error("public key is null");
                return null;
            }
            // 对数据加密  
            Cipher cipher = Cipher.getInstance(publicKey.getAlgorithm());
            cipher.init(Cipher.DECRYPT_MODE, publicKey);

            return cipher.doFinal(data);
        } catch (Exception e) {
            logger.error(e);
        }
        return null;

    }

    /**
     * 公钥解密,读取keystore里的公钥,然后解密
     *
     * @param data
     * @param keyStorePath
     * @param storePassword
     * @param alias
     * @return
     */
    public byte[] decryptByPublicKey(byte[] data, String keyStorePath, String storePassword, String alias) {
        try {
            // 取得公钥  
            PublicKey publicKey = getPublicKey(keyStorePath, storePassword, alias);
            if(publicKey==null){
                logger.error("public key is null");
                return null;
            }
            // 对数据加密  
            Cipher cipher = Cipher.getInstance(publicKey.getAlgorithm());
            cipher.init(Cipher.DECRYPT_MODE, publicKey);

            return cipher.doFinal(data);
        } catch (Exception e) {
            logger.error(e);
        }
        return null;

    }

    /**
     * 公钥解密
     *
     * @param data
     * @param publicKey
     * @return
     */
    public byte[] decryptByPublicKey(byte[] data, PublicKey publicKey) {
        try {
            if (publicKey == null) {
                logger.error("public key is null");
                return null;
            }
            // 对数据加密  
            Cipher cipher = Cipher.getInstance(publicKey.getAlgorithm());
            cipher.init(Cipher.DECRYPT_MODE, publicKey);

            return cipher.doFinal(data);
        } catch (Exception e) {
            logger.error(e);
        }
        return null;

    }

    /**
     * 验证Certificate
     *
     * @param certificatePath
     * @return
     */
    public boolean verifyCertificate(String certificatePath) {
        return verifyCertificate(new Date(), certificatePath);
    }

    /**
     * 验证Certificate是否过期或无效
     *
     * @param date
     * @param certificatePath
     * @return
     */
    public boolean verifyCertificate(Date date, String certificatePath) {
        boolean status = true;
        try {
            // 取得证书  
            Certificate certificate = getCertificate(certificatePath);
            // 验证证书是否过期或无效  
            status = verifyCertificate(date, certificate);
        } catch (Exception e) {
            status = false;
        }
        return status;
    }

    /**
     * 验证证书是否过期或无效
     *
     * @param date
     * @param certificate
     * @return
     */
    private boolean verifyCertificate(Date date, Certificate certificate) {
        boolean status = true;
        try {
            X509Certificate x509Certificate = (X509Certificate) certificate;
            x509Certificate.checkValidity(date);
        } catch (Exception e) {
            status = false;
        }
        return status;
    }

    /**
     * 日志签名,采用Md5withRsa,
     *
     * @param log 日志字符串
     * @param privateKey 服务器私钥,必须是RSA
     * @return base64编码 签名字符串
     */
    public String logSign(String log, PrivateKey privateKey) {
        try {
            byte[] sign = sign(log.getBytes("UTF-8"), privateKey, "MD5withRSA");
            if(sign==null){
                logger.error("sign is null");
                return null;
            }
            return Base64.encodeBase64String(sign);
        } catch (Exception e) {
            logger.error("", e);
        }
        return null;
    }

    /**
     * 日志字符串签名校验
     *
     * @param log 日志字符串
     * @param logSineString base64编码 签名字符串
     * @param publicKey 服务器公钥,必须是RSA
     * @return
     */
    public boolean logVerify(String log, String logSineString, PublicKey publicKey) {
        try {
            byte[] sign = Base64.decodeBase64(logSineString);
            return verify(log.getBytes("UTF-8"), sign, publicKey, "MD5withRSA");
        } catch (Exception e) {
            logger.error("", e);
        }
        return false;
    }

    /**
     * 签名
     *
     * @param keyStorePath
     * @param alias
     * @param keyStorePassword
     * @param aliasPassword
     * @return
     * @throws Exception
     */
    public byte[] sign(byte[] sign, String keyStorePath, String alias,
            String keyStorePassword, String aliasPassword) throws Exception {
        // 获得证书  
        X509Certificate x509Certificate = (X509Certificate) getCertificate(
                keyStorePath, keyStorePassword, alias);

        // 取得私钥  
        PrivateKey privateKey = getPrivateKey(keyStorePath, keyStorePassword,
                alias, aliasPassword);

        // 构建签名  
        Signature signature = Signature.getInstance(x509Certificate
                .getSigAlgName());
        signature.initSign(privateKey);
        signature.update(sign);
        return signature.sign();
    }

    /**
     * 生成签名
     *
     * @param data
     * @param privateKey
     * @param signAlg
     * @return
     */
    public byte[] sign(byte[] data, PrivateKey privateKey, String signAlg) {
        try {

            // 构建签名  
            Signature signature = Signature.getInstance(signAlg);
            signature.initSign(privateKey);
            signature.update(data);
            return signature.sign();
        } catch (Exception e) {
            logger.error(e);
        }
        return null;
    }

    /**
     * 验证签名
     *
     * @param data
     * @param sign
     * @param publicKey
     * @param signAlg
     * @return
     * @throws Exception
     */
    public boolean verify(byte[] data, byte[] sign, PublicKey publicKey,
            String signAlg) {
        try {
            // 构建签名  
            Signature signature = Signature.getInstance(signAlg);
            signature.initVerify(publicKey);
            signature.update(data);

            return signature.verify(sign);
        } catch (Exception e) {
            logger.error(e);
        }
        return false;

    }

    /**
     * 验证签名
     *
     * @param data
     * @param sign
     * @param certificatePath
     * @return
     * @throws Exception
     */
    public boolean verify(byte[] data, byte[] sign,
            String certificatePath) throws Exception {
        // 获得证书  
        X509Certificate x509Certificate = (X509Certificate) getCertificate(certificatePath);
        // 获得公钥  
        PublicKey publicKey = x509Certificate.getPublicKey();
        // 构建签名  
        Signature signature = Signature.getInstance(x509Certificate
                .getSigAlgName());
        signature.initVerify(publicKey);
        signature.update(data);

        return signature.verify(sign);

    }

    /**
     * 验证Certificate
     *
     * @param keyStorePath
     * @param keyStorePassword
     * @param alias
     * @return
     */
    public boolean verifyCertificate(Date date, String keyStorePath,
            String keyStorePassword, String alias) {
        boolean status = true;
        try {
            Certificate certificate = getCertificate(keyStorePath,
                    keyStorePassword, alias);
            status = verifyCertificate(date, certificate);
        } catch (Exception e) {
            status = false;
        }
        return status;
    }

    /**
     * 验证Certificate
     *
     * @param keyStorePath
     * @param keyStorePassword
     * @param alias
     * @return
     */
    public boolean verifyCertificate(String keyStorePath,
            String keyStorePassword, String alias) {
        return verifyCertificate(new Date(), keyStorePath, keyStorePassword,
                alias);
    }

    /**
     * 生成文件md5值
     *
     * @param filePath
     * @return
     */
//    public String getMd5ByFile(String filePath) {
//        String value = null;
//        FileInputStream in = null;
//        try {
//            File file = new File(filePath);
//            in = new FileInputStream(file);
//            MessageDigest md5 = MessageDigest.getInstance("MD5");
//            byte[] buffer = new byte[8192];
//            int length = -1;
//            while ((length = in.read(buffer)) != -1) {
//                md5.update(buffer, 0, length);
//            }
//            byte[] bt = md5.digest();
//            value = Hex.toHexString(bt).toUpperCase();
//        } catch (Exception e) {
//            logger.error("", e);
//        } finally {
//            if (null != in) {
//                try {
//                    in.close();
//                } catch (IOException e) {
//                    logger.error("", e);
//                }
//            }
//        }
//        return value;
//    }
}
