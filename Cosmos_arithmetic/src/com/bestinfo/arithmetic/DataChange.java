package com.bestinfo.arithmetic;

import org.apache.commons.codec.binary.Hex;
import org.apache.commons.lang.ArrayUtils;


/**
 * 数据转换
 *
 * @author chenliping
 */
public class DataChange {

    private final byte[] hex = "0123456789ABCDEF".getBytes();

    /**
     * 从字节数组到十六进制字符串转换
     *
     * @param b
     * @return
     */
    public String bytes2HexString(byte[] b) {
//        byte[] buff = new byte[2 * b.length];
//        for (int i = 0; i < b.length; i++) {
//            buff[2 * i] = hex[(b[i] >> 4) & 0x0f];
//            buff[2 * i + 1] = hex[b[i] & 0x0f];
//        }
//        return new String(buff);
        return Hex.encodeHexString(b);//YR 2014.10.20
    }

    private int parse(char c) {
        if (c >= 'a') {
            return (c - 'a' + 10) & 0x0f;
        }
        if (c >= 'A') {
            return (c - 'A' + 10) & 0x0f;
        }
        return (c - '0') & 0x0f;
    }

    /**
     * 从十六进制字符串到字节数组转换
     *
     * @param hexstr
     * @return
     */
    public byte[] hexString2Bytes(String hexstr) {
//        byte[] b = new byte[hexstr.length() / 2];
//        int j = 0;
//        for (int i = 0; i < b.length; i++) {
//            char c0 = hexstr.charAt(j++);
//            char c1 = hexstr.charAt(j++);
//            b[i] = (byte) ((parse(c0) << 4) | parse(c1));
//        }
//        return b;
        //YR.2014.10.20
        byte[] b = null;
        try {
            b = Hex.decodeHex(hexstr.toCharArray());
            return b;
        } catch (Exception e) {
            return null;
        }
    }

}
