package com.shihy.springboot.test;
/***
 * 
 * @Title: springstarter
 * @author shy
 * @Description TODO
 * @data 2019年3月27日 下午3:30:35
 *
 */
import com.shihy.springboot.utils.MoneyUtil;
import lombok.extern.slf4j.Slf4j;
@Slf4j
public class DigitalCaseConversionTest {
	
	public static void main(String[] args) {
		String money = "123465789.123";
		upperCaseConversionTest(money);
		
		//String chineseMoney = "壹亿贰仟叁佰肆拾陆万伍仟柒佰捌拾玖元";
		String chineseMoney = "壹亿零捌拾玖元";
		Boolean flag = true;
		lowerCaseConversionTest(chineseMoney, flag);
	}
	
	public static void upperCaseConversionTest(String money) {
		String chineseMoney = MoneyUtil.toChinese(money);
		log.info("小写转大写 >>>>>>>>>> chineseMoney :" + chineseMoney);
	}
	/***
	 * 
	 * @Description: 小写转换 有问题  不能用
	 * @param money
	 * @param flag   
	 * @return void  
	 * @throws @throws
	 */
	public static void lowerCaseConversionTest(String money, Boolean flag) {
		int digitalMoney = MoneyUtil.cnNumericToArabic(money, flag);
		log.info("digitalMoney :" + digitalMoney);
	}

}
