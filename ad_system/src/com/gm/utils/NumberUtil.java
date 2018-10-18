package com.gm.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class NumberUtil {

	public NumberUtil() {
		// TODO Auto-generated constructor stub
	}
	
	public static boolean isNumeric(String str) {
		Pattern pattern = Pattern.compile("^-?\\d+$");
		Matcher isNum = pattern.matcher(str);
		return isNum.matches();
	}

	/**
	 * 将int转为低字节在前，高字节在后的byte数组
	 */
	public static byte[] int2byte(int n) {
		byte[] b = new byte[4];
		b[0] = (byte) (n & 0xff);
		b[1] = (byte) (n >> 8 & 0xff);
		b[2] = (byte) (n >> 16 & 0xff);
		b[3] = (byte) (n >> 24 & 0xff);
		return b;
	}

	public static byte[] intToByte(int i) {

		byte[] abyte0 = new byte[4];

		abyte0[0] = (byte) (0xff & i);

		abyte0[1] = (byte) ((0xff00 & i) >> 8);

		abyte0[2] = (byte) ((0xff0000 & i) >> 16);

		abyte0[3] = (byte) ((0xff000000 & i) >> 24);

		return abyte0;

	}

	public static int bytesToInt(byte[] bytes) {

		int addr = bytes[0] & 0xFF;

		addr |= ((bytes[1] << 8) & 0xFF00);

		addr |= ((bytes[2] << 16) & 0xFF0000);

		addr |= ((bytes[3] << 24) & 0xFF000000);

		return addr;

	}

	/**
	 * 将short转成byte[2]
	 * 
	 * @param a
	 * @return
	 */
	public static byte[] short2Byte(short a) {
		byte[] b = new byte[2];

		b[0] = (byte) (a >> 8);
		b[1] = (byte) (a);

		return b;
	}

	public static byte[] byteMerger(byte[] byte_1, byte[] byte_2,
			byte[] byte_3, byte[] byte_4) {
		byte[] byte_total = new byte[byte_1.length + byte_2.length
				+ byte_3.length + byte_4.length];
		System.arraycopy(byte_1, 0, byte_total, 0, byte_1.length);
		System.arraycopy(byte_2, 0, byte_total, byte_1.length, byte_2.length);
		System.arraycopy(byte_3, 0, byte_total, byte_1.length+byte_2.length, byte_3.length);
		System.arraycopy(byte_4, 0, byte_total, byte_1.length+byte_2.length+byte_3.length, byte_4.length);
		return byte_total;
	}
	
	public static byte[] byteMerger(byte[] byte_1, byte[] byte_2) {
		byte[] byte_total = new byte[byte_1.length + byte_2.length];
		System.arraycopy(byte_1, 0, byte_total, 0, byte_1.length);
		System.arraycopy(byte_2, 0, byte_total, byte_1.length, byte_2.length);
		return byte_total;
	}

	public static void main() {
		if(isNumeric("17000954856C5206")){
			System.out.println("NumberUtil.main() is true");
		}else {
			System.out.println("false");
		}
	}
}
