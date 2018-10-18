package com.bestinfo.gambler.all;

import com.bestinfo.protocols.message.AppHeader;
import com.bestinfo.protocols.users.UserLoginReq;
import java.security.PrivateKey;
import org.apache.http.client.CookieStore;
import org.apache.http.client.HttpClient;

/**
 *
 * @author chenliping
 */
public class StaticVariable {

    public static final String RHCLIENTPROPERTIES = "rhclient.properties";
    public static final String USERDIRECTORY = "userdata\\";
    public static final String NUMBERCONTROLDIRECTORY = "number\\control\\";
    public static final String NUMBERMODELFILE = "/model.xml";
    /**
     * 号码文件存放位置*
     */
    public static final String NUMBERDIRECTORY = "number\\number\\";
    /**
     * *投注号码请求写入文件位置**
     */
    public static final String NUMBERREQXML = "number\\numberReq\\";
    /**
     * * 投注号码回复写入位置
     */
    public static final String NUMBERRESXML = "number\\numberRes\\";
    /**
     * 证书存放路径
     */
    public static final String KEYPATH = "hapyyCA/";
    public static final String ACTION = "action=";
    public static final String GAMEFILE = "gamefile\\";
    public static final String LUCKFILE = "luckfile\\";
    public static AppHeader head;
    //public static String SERVERURL="http://localhost:8080/Quantum_Gambler/gambler";
    public static String SERVERURL;
    public static String SESSION;
    public static PrivateKey privateKey;
    public static HttpClient httpclient;
    public static CookieStore cookieStore;
    public static float TASTE_TIME = 0;
    public static String cifer;
    public static String serino;
    /**
     * 用户信息
     */
    public static boolean remeberMe = false;
    public static UserLoginReq us;
}
