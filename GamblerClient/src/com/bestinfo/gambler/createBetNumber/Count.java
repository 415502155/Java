/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bestinfo.gambler.createBetNumber;

import java.util.HashMap;
import java.util.Map;
import org.apache.http.client.CookieStore;

/**
 *
 * @author chenliping
 */
public class Count {

    private Integer count = 0;
    private Integer failcount = 0;
    private Integer sendFail = 0;
    private Integer outthirty = 0;
    
    private Map<String,CookieStore> mcookiestore = new HashMap<String,CookieStore>();
    private Integer tmn = 0;

    public Integer getOutthirty() {
        return outthirty;
    }

    public void addOutthirty() {
        synchronized (outthirty) {
            ++outthirty;
        }
    }    
    
    public void addtmn() {
        synchronized (tmn) {
            ++tmn;
        }
    }

    public void addSendFail() {
        synchronized (sendFail) {
            ++sendFail;
        }
    }
    
    public Map<String, CookieStore> getMcookiestore() {
        return mcookiestore;
    }

    public synchronized void addMcookiestore(String name,CookieStore mcookiestore) {
        synchronized (mcookiestore) {
            this.mcookiestore.put(name,mcookiestore);
        }
    }

    public void addCount() {
        synchronized (count) {
            ++count;
        }
    }

    public synchronized void addfailcount() {
        synchronized (failcount) {
            ++failcount;
        }
    }

    public Integer gettmn() {
        return tmn;
    }
    
    public Integer getCount() {
        return count;
    }

//    public void setCount(Integer count) {
//        this.count = count;
//    }

    public Integer getFailcount() {
        return failcount;
    }

//    public void setFailcount(Integer failcount) {
//        this.failcount = failcount;
//    }
    public Integer getSendFail() {
        return sendFail;
    }
    
    
}
