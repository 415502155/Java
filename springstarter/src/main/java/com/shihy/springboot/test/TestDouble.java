package com.shihy.springboot.test;

import lombok.extern.slf4j.Slf4j;
/***
 * 
 * @Title: springstarter
 * @author shy
 * @Description add, subtract, multiply and divide
 * @data 2019年2月14日 下午3:41:58
 *
 */
@Slf4j
public class TestDouble {

	public static void main(String[] args) {
		double num1 = 1.11111111111;
		double num2 = 1.11;
		addDouble(num1, num2);
		multiplyDouble(num1, num2);
		divideDouble(num1, num2);
	}
	
	public static void addDouble(double a, double b) {
		double sum = a + b;
		log.info("a+b =" + sum);
	}
	/***
	 * 
	 * @Description: double乘法 精度缺失
	 * @param a
	 * @param b   
	 * @return void  
	 * @throws @throws
	 */
	public static void multiplyDouble(double a, double b) {
		double multiply = a * b;
		log.info("a*b =" + multiply);
	}

	public static void divideDouble(double a, double b) {
		double divide = a / b;
		log.info("a/b =" + divide);
	}

}
