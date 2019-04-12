package com.shihy.springboot.test;

import org.apache.commons.lang3.StringUtils;
import com.shihy.springboot.utils.CommonUtils;
import lombok.extern.slf4j.Slf4j;
/***
 * 
 * @Title: springstarter
 * @author shy
 * @Description TODO
 * @data 2019年3月27日 下午3:30:35
 *
 */
@Slf4j
public class ToFixedTest {
	
	public static void main(String[] args) {
		double d = 1.1234567879;
		round(d, 0);
		subDecimalFormat(d, "#.0000000");
		doubleToPercent(d, Integer.MAX_VALUE, 1);
		String str = "12.3%";
		getPercentToDouble(str, 5);
	}
	
	public static void round(double d, int digit) {
		double d1 = CommonUtils.round(d, digit);
		log.info("四舍五入保留小数点后"+ digit + "位， 输出结果为 ：" + d1);
	} 
	
	public static void subDecimalFormat(double d, String type) {
		double d1 = CommonUtils.subDecimalFormat(d, type);
		if (StringUtils.isBlank(type)) {
			type = "#.0000000";
		}
		log.info("四舍五入保留小数点按照" + type +"格式， 输出结果为 ：" + d1);
	}
	
	public static void doubleToPercent(double d, int integerDigits, int fractionDigits) {
		String percent = CommonUtils.getPercentFormat(d, integerDigits, fractionDigits);
		log.info("浮点转为百分数, 输出结果为 ：" + percent);
	}
	
	public static void getPercentToDouble(String d, int digit) {
		double d1 = CommonUtils.getPercentToDouble(d, digit);
		log.info("百分数转为浮点,保留小数点后"+ digit +"位, 输出结果为 ：" + d1);
	}
	

}
