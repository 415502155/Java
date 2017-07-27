package com.bestinfo.appserver.manage;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import javax.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Service;

@Service("ResponsePool")
public class ResponsePool {
    private int iMaxPoolSize=0;
    private Map<String,HttpServletResponse> responseMap=new HashMap<String,HttpServletResponse>();
    
    private static ResponsePool instance;    
    public static ResponsePool getInstance() {
        if (instance == null) {
            instance = new ResponsePool();
        }
        return instance;
    }

    public int getMaxPoolSize() {
        return iMaxPoolSize;
    }

    public void setMaxPoolSize(int iMaxPoolSize) {
        this.iMaxPoolSize = iMaxPoolSize;
    }
    
    public boolean checkResponsePoolStatus() {
        return responseMap.size()<=iMaxPoolSize;
    }
    
    public String addResponse(HttpServletResponse response) {
        if(response==null) {
            return null;
        }
        String sKey = UUID.randomUUID().toString().replaceAll("-", "");
        responseMap.put(sKey, response);
        return sKey;        
    }
    
    public HttpServletResponse getResponse(String sKey) {
        if(sKey==null) {
            return null;
        }
        HttpServletResponse response=responseMap.get(sKey);
        responseMap.remove(sKey);
        return response;        
    }
}
