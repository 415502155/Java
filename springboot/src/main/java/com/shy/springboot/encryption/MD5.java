package com.shy.springboot.encryption;

import java.security.MessageDigest;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

/**
 * md5摘要算法
 *
 * @author chenliping
 */
public class MD5 {

    private static final Logger logger = Logger.getLogger(MD5.class);

    public String genTicketDigest(String cipher, int gameId, int ticketDrawId, int serialNo, String betLine) throws Exception {
        StringBuilder sb = new StringBuilder();
        sb.append(gameId);
        sb.append(ticketDrawId);
        sb.append(cipher);
        sb.append("guanlottery");
        sb.append(serialNo);
        sb.append(betLine);
        return digest(sb.toString(), "MD5");
    }

    public String genTicketDigest(int gameId, int ticketDrawId, int serialNo, String betLine) throws Exception {
        StringBuilder sb = new StringBuilder();
        sb.append(gameId);
        sb.append(ticketDrawId);
        sb.append(serialNo);
        sb.append(betLine);
        return digest(sb.toString(), "MD5");
    }

    public String digest(byte[] src, String name) throws Exception {
        if (name.equals("MD5")) {
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] digest = md.digest(src);
            byte[] encodeBase64 = Base64.encodeBase64(digest);
            String newPasswd = new String(encodeBase64, "UTF-8");
            return newPasswd;
        } else {
            return StringUtils.EMPTY;
        }
    }

    public byte[] digestB(byte[] src) {
        try {

            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] digest = md.digest(src);
            return digest;

        } catch (Exception e) {
            logger.error("", e);
        }
        return ArrayUtils.EMPTY_BYTE_ARRAY;
    }

    public String digest(String src, String name) throws Exception {
        if (name.equals("MD5")) {
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] digest = md.digest(src.getBytes());
            byte[] encodeBase64 = Base64.encodeBase64(digest);
            String newPasswd = new String(encodeBase64, "UTF-8");
            return newPasswd;
        } else {
            return StringUtils.EMPTY;
        }
    }

    public static void main(String[] args) throws Exception {
        System.out.println(new MD5().digest("111111", "MD5"));
    }
}