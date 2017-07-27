package com.bestinfo.bean.h5.monitor;

import com.alibaba.fastjson.JSONArray;
import java.io.Serializable;

public class RequestModel implements Serializable {

    private String sRequestIp = null;
    private String sResponseKey = null;
    private JSONArray jsonArray = null;
    private Session session = null;
    private byte btEncryptType = 0;
    private int iCmdVersion = 0;

    public RequestModel() {
    }

    public void setResponseKey(String sResponseKey) {
        this.sResponseKey = sResponseKey;
    }

    public String getResponseKey() {
        return sResponseKey;
    }

    public String getRequestIp() {
        return sRequestIp;
    }

    public void setRequestIp(String sRequestIp) {
        this.sRequestIp = sRequestIp;
    }

    public JSONArray getJsonArray() {
        return jsonArray;
    }

    public void setJsonArray(JSONArray jsonArray) {
        this.jsonArray = jsonArray;
    }

    public void setSession(Session session) {
        this.session = session;
    }

    public Session getSession() {
        return session;
    }

    public void setEncryptType(byte btEncryptType) {
        this.btEncryptType = btEncryptType;
    }

    public byte getEncryptType() {
        return btEncryptType;
    }

    public void setCmdVersion(int iCmdVersion) {
        this.iCmdVersion = iCmdVersion;
    }

    public int getCmdVersion() {
        return iCmdVersion;
    }
}
