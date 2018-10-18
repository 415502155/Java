/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.bestinfo.controller.clpdata;

import java.util.Date;

/**
 *
 * @author Administrator
 */
public class Systemlog {
    private static Date time;
    private static String logdescribe;

    /**
     * @return the time
     */
    public static Date getTime() {
        return time;
    }

    /**
     * @param aTime the time to set
     */
    public static void setTime(Date aTime) {
        time = aTime;
    }

    /**
     * @return the logdescribe
     */
    public static String getLogdescribe() {
        return logdescribe;
    }

    /**
     * @param aLogdescribe the logdescribe to set
     */
    public static void setLogdescribe(String aLogdescribe) {
        logdescribe = aLogdescribe;
    }
}
