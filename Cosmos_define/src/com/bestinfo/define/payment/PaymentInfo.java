/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.bestinfo.define.payment;

/**
 *
 * @author wanglei
 */
public class PaymentInfo {
    public static String serviceType;
    public static String serviceQueryType;
    public static String body;
    public static String byName;
    public static String key;
    public static String iceProxy;
    public static boolean testSwitch;

    public static String getServiceType() {
        return serviceType;
    }

    public static void setServiceType(String serviceType) {
        PaymentInfo.serviceType = serviceType;
    }

    public static String getServiceQueryType() {
        return serviceQueryType;
    }

    public static void setServiceQueryType(String serviceQueryType) {
        PaymentInfo.serviceQueryType = serviceQueryType;
    }

    public static String getBody() {
        return body;
    }

    public static void setBody(String body) {
        PaymentInfo.body = body;
    }

    public static String getByName() {
        return byName;
    }

    public static void setByName(String byName) {
        PaymentInfo.byName = byName;
    }

    public static String getKey() {
        return key;
    }

    public static void setKey(String key) {
        PaymentInfo.key = key;
    }

    public static String getIceProxy() {
        return iceProxy;
    }

    public static void setIceProxy(String iceProxy) {
        PaymentInfo.iceProxy = iceProxy;
    }

    public static boolean isTestSwitch() {
        return testSwitch;
    }

    public static void setTestSwitch(boolean testSwitch) {
        PaymentInfo.testSwitch = testSwitch;
    }
    
}
