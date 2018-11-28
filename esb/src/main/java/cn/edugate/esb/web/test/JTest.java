package cn.edugate.esb.web.test;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.junit.Test;

/**
 *  @Company sjwy
 *  @Title: test.java 
 *  @Description: 
 *	@author: shy
 *  @date: 2018年10月12日 下午2:02:54 
 */
public class JTest {
	
	/**
	 *  @Description: 
	 *	@author: shy
	 *  @date: 2018年10月12日 下午2:03:12 
	 *  @return
	 */
	@Test
	public void TestStringUtils() {
		String a = "12 3	";
		String b = " ";
		String c = "qwertyuioo";
		String d = "";
		String e = "10001";
		String f = "1 1";
		String g = "QwerTYuioO";
		String h = "administrator";
		
		String str = StringUtils.deleteWhitespace(a);
		String str1 = StringUtils.capitalise(str);
		
		boolean flag1 = StringUtils.isEmpty(a);
		boolean flag3 = StringUtils.isEmpty(b);//判断是否为空  空字符串不为空
		boolean flag4 = StringUtils.isBlank(b);//空字符串也为空
		boolean flag5 = StringUtils.contains(a, "1");//判断当前字符串是否包含1
		boolean flag6 = StringUtils.equals(a, b);//判断两个字符串是否相等
		boolean flag7 = StringUtils.isAllLowerCase(c);//判断字符串的所有字母是否都是小写
		boolean flag8 = StringUtils.isAllUpperCase(c);
		boolean flag9 = StringUtils.isNumeric(a);
		boolean flag10 = StringUtils.isNumeric(d);//"" 返回true  在3.0版本已经解决该问题
		boolean flag11 = StringUtils.isNumericSpace(a);
		boolean flag12 = StringUtils.isNumericSpace(f);//是否是number 可以去除字符串间的空格   前后的不可以
		List<Integer> list = new ArrayList<>();
		list.add(1);
		list.add(2);
		list.add(1);
		list.add(2);
		list.add(1);
		list.add(3);
		
		String str2 = StringUtils.join(list, "--");//拼接数组集合等元素通过制定的拼接符
		int l1 = StringUtils.length(null);
		int l2 = StringUtils.length("");
		int l3 = StringUtils.length("   ");
		int l4 = StringUtils.length("ad min");//获取当前字符串的长度   null 和  “” 长度为0
		String str3 = StringUtils.lowerCase(g);//改变字符串中的大写字母变小写
		String strr1 = StringUtils.remove(h, "t");//删除当前字符串的某一段（可是是String 也可是char）
		String strr2 = StringUtils.remove(h, 'i');
		String strr3 = StringUtils.remove(h, "istrator");
		String strr4 = StringUtils.replace(h, "t", "xxx");//将当前字符串的某一段替换成另一个字符串   当xxx变为null 当前字符串无变化
		String strr5 = StringUtils.repeat("admin", 5);
		String strr6 = StringUtils.substringBeforeLast(h, "i");//获取某个字符串中某段字符串在当前字符串最后存在之前的字符串  例： asdfghj fg ---》asd
		String strr7 = StringUtils.substringBeforeLast(h, "t");//asdfghjasdfghjkl fg ---》asdfghjasd
		String strr8 = StringUtils.substringBeforeLast(h, "a");
		String strr9 = StringUtils.upperCase(h);//所有引文小写转大写
		
		System.out.println("输出结果为 :\nstr :" + str + "\nstr1 :" + str1
							+ "\nflag1 :" + flag1
							+ "\nflag3 :" + flag3
							+ "\nflag4 :" + flag4
							+ "\nflag5 :" + flag5
							+ "\nflag6 :" + flag6
							+ "\nflag7 :" + flag7
							+ "\nflag8 :" + flag8
							+ "\nflag9 :" + flag9
							+ "\nflag10 :" + flag10
							+ "\nflag11 :" + flag11
							+ "\nflag12 :" + flag12
							+ "\nstr2 :" + str2
							+ "\nl1 :" + l1
							+ "\nl2 :" + l2
							+ "\nl3 :" + l3
							+ "\nl4 :" + l4
							+ "\nstr3 :" + str3
							+ "\nstrr1 :" + strr1
							+ "\nstrr2 :" + strr2
							+ "\nstrr3 :" + strr3
							+ "\nstrr4 :" + strr4
							+ "\nstrr5 :" + strr5
							+ "\nstrr6 :" + strr6
							+ "\nstrr7 :" + strr7
							+ "\nstrr8 :" + strr8
							+ "\nstrr9 :" + strr9
						);	
	}
}
