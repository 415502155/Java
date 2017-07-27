package com.bestinfo.arithmetic;

/**
 * 会话缓存key算法
 *
 * @author chenliping
 */
public class LoginSession {

    /**
     * 通过sessionid得到终端机号
     *
     * @param sessonid
     * @return
     */
    public String deskey(String sessonid) {
        String terminalid = sessonid;
        return terminalid;
    }

    /**
     * 通过终端机号得到sessionid
     *
     * @param terminalid
     * @return
     */
    public String getkey(String terminalid) {
        String sessionid = terminalid;
        return sessionid;
    }

}
