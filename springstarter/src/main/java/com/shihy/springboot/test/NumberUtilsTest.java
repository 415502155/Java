package com.shihy.springboot.test;

import org.apache.commons.lang.math.NumberUtils;
import org.apache.commons.lang.math.RandomUtils;
/***
 * 
 * @Title: springstarter
 * @author shy
 * @Description TODO
 * @data 2019年3月27日 下午3:30:35
 *
 */
public class NumberUtilsTest {
	
	public static void main(String[] args) {
		test();
	}
	
	@SuppressWarnings("deprecation")
	public static void test() {
		int intValue = NumberUtils.BYTE_MINUS_ONE.intValue();
		System.out.println(intValue);
		int stringToInt = NumberUtils.stringToInt("aaaaaaa123");
		System.out.println(stringToInt);
		for (int i = 0; i < 10; i++) {
			System.out.println("RandomUtils :" + RandomUtils.nextInt(100000));
		}
		int compareAACaaAA122 = NumberUtils.compare(1, 2);
		System.out.println("duibi shei da :" + compareAACaaAA122);
		int da = NumberUtils.compare(2, 1);
		System.out.println("dui bi da :" + da);
		int deng = NumberUtils.compare(1, 1);
		System.out.println("dui bi da :" + deng);
	}
}
