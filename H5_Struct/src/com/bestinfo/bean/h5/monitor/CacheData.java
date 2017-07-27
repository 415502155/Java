package com.bestinfo.bean.h5.monitor;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import java.io.Serializable;
import org.apache.commons.codec.binary.Base64;

public class CacheData implements Serializable {

    private String key = "";
    private byte[] response = null;

    public CacheData() {
    }

    public CacheData(String cacheData) {
        JSONObject json = (JSONObject) JSON.parseObject(cacheData);
        key = String.valueOf(json.get("KEY"));
        String jsonResponse = String.valueOf(json.get("RESPONSE"));
        if (jsonResponse == null || jsonResponse.isEmpty()) {
            return;
        }
        response = decodeData(jsonResponse.getBytes());
    }

    @Override
    public String toString() {
        JSONObject json = new JSONObject();
        json.put("KEY", key);
        if (response != null) {
            String sResponse = encodeData(response);
            json.put("RESPONSE", sResponse);
        } else {
            json.put("RESPONSE", "");
        }
        return json.toJSONString();
    }

    public String getKey() {
        return key;
    }

    public void setKey(String sKey) {
        this.key = sKey;
    }

    public byte[] getResponse() {
        return response;
    }

    public void setResponse(byte[] btResponse) {
        this.response = btResponse;
    }

    private String encodeData(byte[] btData) {
        if (btData == null) {
            return null;
        }
        return new String(Base64.encodeBase64(btData));
    }

    private byte[] decodeData(byte[] btData) {
        if (btData == null) {
            return null;
        }
        return Base64.decodeBase64(btData);
    }
}
