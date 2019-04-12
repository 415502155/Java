package com.shihy.springboot.test;
/***
 * 
 * @Title: springstarter
 * @author shy
 * @Description TODO
 * @data 2019年3月27日 下午3:30:35
 *
 */
public class BigNumSumTest {

	public static void main(String[] args) {
		String n1 = "9911";
		String n2 = "2292";
		System.out.println(BigNumSumTest.add(n1, n2));
	}

	/*
	 * 功能：利用字符串模拟大数加法
	 */
	public static String add(String n1, String n2) {
		StringBuilder result = new StringBuilder();
		// 反转字符串
 		String rn1 = new StringBuilder(n1).reverse().toString();
		String rn2 = new StringBuilder(n2).reverse().toString();
		int len1 = rn1.length();
		int len2 = rn2.length();
		int maxLen = len1 > len2 ? len1 : len2;
		boolean nOverFlow = false;// 是否越界
		int nTakeOver = 0;// 溢出数量
		// 把两个字符串补齐，即短字符串的高位用0补齐
		if (len1 < len2) {
			for (int i = len1; i < len2; i++) {
				rn1 += "0";
			}
		} else {
			for (int i = len2; i < len1; i++) {
				rn2 += "0";
			}
		}
		// 把两个正整数相加，一位一位的加 并加上进位
		for (int i = 0; i < maxLen; i++) {
			int nSum = Integer.parseInt(rn1.charAt(i) + "") + Integer.parseInt(rn2.charAt(i) + "");
			nSum = nSum + nTakeOver;// 加上前一位的进位
			if (nSum >= 10) {
 				if (i == maxLen - 1) {// 已经计算到最后一位了
					nOverFlow = true;
				}
				nTakeOver = 1;// 溢出了
				result.append(nSum - 10);
			} else {
				nTakeOver = 0;// 没溢出
				result.append(nSum);
			}
		}
		// 如果溢出的话 表示位增加了
		if (nOverFlow) {
			result.append(nTakeOver);
		}
		return result.reverse().toString();
	}

}
