package com.bestinfo.bean.h5.monitor;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.bestinfo.define.h5.monitor.InitializeData;
import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.UUID;

public class Session implements Serializable {

    private String sessionId;
    private long createDate;// session的创建时间
    private long lastAccessDate;// 最后一次的访问时间
    private long timeOutInterval;// 过期时间 负值表示永不过期

    private Map<String, String> keyData = null;
    private CacheData cacheData = null;

    public Session() {
        sessionId = UUID.randomUUID().toString().replaceAll("-", "");
        createDate = new Date().getTime();
        lastAccessDate = new Date().getTime();
        timeOutInterval = InitializeData.sessionTimeOutInterval; //1000 * 60 * 60 * 1;//1小时
        keyData = new HashMap<String, String>();
        cacheData = new CacheData();
    }

    public Session(String session) {
        JSONObject json = (JSONObject) JSON.parseObject(session);
        sessionId = String.valueOf(json.get("SESSION_ID"));
        createDate = Long.parseLong(String.valueOf(json.get("CREATE_DATE")));
        lastAccessDate = Long.parseLong(String.valueOf(json.get("LAST_ACCESS_DATE")));
        timeOutInterval = Long.parseLong(String.valueOf(json.get("TIME_OUT_INTERVAL")));

        keyData = new HashMap<String, String>();
        JSONObject mapJson = (JSONObject) JSON.parseObject(String.valueOf(json.get("KEY_DATA")));
        for (Map.Entry<String, Object> entry : mapJson.entrySet()) {
            keyData.put(entry.getKey(), (String) entry.getValue());
        }

        cacheData = new CacheData(String.valueOf(json.get("CACHE_DATA")));
    }

    @Override
    public String toString() {
        JSONObject json = new JSONObject();
        json.put("SESSION_ID", sessionId);
        json.put("CREATE_DATE", Long.toString(createDate));
        json.put("LAST_ACCESS_DATE", Long.toString(lastAccessDate));
        json.put("TIME_OUT_INTERVAL", Long.toString(timeOutInterval));

        JSONObject mapJson = new JSONObject();
        Iterator iter = keyData.entrySet().iterator();
        while (iter.hasNext()) {
            Map.Entry entry = (Map.Entry) iter.next();
            mapJson.put((String) entry.getKey(), (String) entry.getValue());
        }
        json.put("KEY_DATA", mapJson.toJSONString());

        json.put("CACHE_DATA", cacheData.toString());

        return json.toJSONString();
    }

    public void updateAccessDate() {
        lastAccessDate = new Date().getTime();
    }

    //session是否超时，true超时，false未超时
    public boolean isSessionTimeOut() {
        if (timeOutInterval > 0) {
            long lTemp = new Date().getTime() - lastAccessDate;
            if (lTemp <= timeOutInterval) {
                return false;
            }
        }
        return true;
    }

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sSessionId) {
        this.sessionId = sSessionId;
    }

    public long getCreateDate() {
        return createDate;
    }

    public void setCreateDate(long lCreateData) {
        this.createDate = lCreateData;
    }

    public long getAccessDate() {
        return lastAccessDate;
    }

    public void setAccessDate(long lLastAccessData) {
        this.lastAccessDate = lLastAccessData;
    }

    public long getTimeOutInterval() {
        return timeOutInterval;
    }

    public void setTimeOutInterval(long lTimeOutInterval) {
        this.timeOutInterval = lTimeOutInterval;
    }

    public void setCache(CacheData cacheData) {
        this.cacheData = cacheData;
    }

    public long getLastAccessDate() {
        return lastAccessDate;
    }

    public void setLastAccessDate(long lastAccessDate) {
        this.lastAccessDate = lastAccessDate;
    }

    public Map<String, String> getKeyData() {
        return keyData;
    }

    public void setKeyData(Map<String, String> keyData) {
        this.keyData = keyData;
    }

    public CacheData getCacheData() {
        return cacheData;
    }

    public void setCacheData(CacheData cacheData) {
        this.cacheData = cacheData;
    }

    public CacheData getCache() {
        return cacheData;
    }

    public void setAttribute(String key, String obj) {
        updateAccessDate();
        keyData.put(key, obj);
    }

    public String getAttribute(String key) {
        updateAccessDate();
        return keyData.get(key);
    }

    public void removeAttribute(String key) {
        updateAccessDate();
        keyData.remove(key);
    }

}
