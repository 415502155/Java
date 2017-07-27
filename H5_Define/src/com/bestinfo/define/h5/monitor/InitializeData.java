package com.bestinfo.define.h5.monitor;

public class InitializeData {

    public static String localDesKey = null;
    public static int waitResponseTime = 20;
    public static String queueServerAddress = null;
    public static String queueTopic = null;
    public static String checkNumSmsFormat = null;
    public static long sendCheckNumInterval = 0;
    public static long checkNumTimeOutInterval = 0;
    public static int maxTryLoginCount = 0;
    public static long maxTryLoginTime = 0;
    public static int maxResponsePoolSize = 0;
    public static String serverPublicKeyPem;
    public static long sessionTimeOutInterval;

    public static int SMS_OPEN = 0;//是否发短信验证码
    public static String SMS_URL = "";//短信发送url

}
