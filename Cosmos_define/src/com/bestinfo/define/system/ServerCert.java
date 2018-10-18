package com.bestinfo.define.system;

import java.security.PrivateKey;
import java.security.PublicKey;

/**
 * 服务密钥类
 *
 * @author chenliping
 */
public class ServerCert {

    public static final int RANDKEY_LEN = 32;//随机密钥长度
    public static final int SESSIONKEY_INV_LEN = 32;//24+8  session key + inv
    public static final int SESSIONKEY_LEN = 24;
    public static final int INV_LEN = 8;
    public static PrivateKey serverPrivateKey = null;//终端服务器私钥
    public static PublicKey serverPublicKey = null;//终端服务器公钥
    public static byte[] timerKey = null;//定时des3密钥 ,SystemKeyService负责赋值
    public static byte[] timerKeyInv = null;//初始化向量

    public static byte[] registerKey = null;//注册des3密钥
    public static byte[] registerKeyInv = null;

    public static byte[] loginKey = null;//登陆des3密钥
    public static byte[] loginKeyInv = null;

    public static byte[] loginCheckKey = null;//登陆验证des3密钥
    public static byte[] loginCheckKeyInv = null;

    public static String serverPublicKeyPemString = null;//终端服务器公钥pem字符串

    public static int ENCRYPT_FLAG = 1;
//    public static int ENCRYPT_FLAG = 0;

}
