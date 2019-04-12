package com.shihy.springboot.utils;

import java.util.HashMap;
import java.util.Map;

/***
 * 
 * @Title: springstarter
 * @author shy
 * @Description 金额大小写转换
 * @data 2019年2月15日 上午8:52:59
 *
 */
public class MoneyUtil {
	/***
	 * 小写转大写
	 */
	 /** 大写数字 */
	  private static final String[] NUMBERS = { "零", "壹", "贰", "叁", "肆", "伍", "陆",
	      "柒", "捌", "玖" };
	  /** 整数部分的单位 */
	  private static final String[] IUNIT = { "元", "拾", "佰", "仟", "万", "拾", "佰",
	      "仟", "亿", "拾", "佰", "仟", "万", "拾", "佰", "仟" };
	  /** 小数部分的单位 */
	  private static final String[] DUNIT = { "角", "分", "厘" };
	  /**
	   * 得到大写金额。
	   */
	  public static String toChinese(String str) {
		// 去掉","
	    str = str.replaceAll(",", "");
	    String integerStr;// 整数部分数字
	    String decimalStr;// 小数部分数字
	    // 初始化：分离整数部分和小数部分
	    if (str.indexOf(".") > 0) {
	      integerStr = str.substring(0, str.indexOf("."));
	      decimalStr = str.substring(str.indexOf(".") + 1);
	    } else if (str.indexOf(".") == 0) {
	      integerStr = "";
	      decimalStr = str.substring(1);
	    } else {
	      integerStr = str;
	      decimalStr = "";
	    }
	    // integerStr去掉首0，不必去掉decimalStr的尾0(超出部分舍去)
    	if (!"".equals(integerStr)) {
	      integerStr = Long.toString(Long.parseLong(integerStr));
	      if ("0".equals(integerStr)) {
	        integerStr = "";
	      }
	    }
	    // overflow超出处理能力，直接返回
	    if (integerStr.length() > IUNIT.length) {
	      System.out.println(str + ":超出处理能力");
	      return str;
	    }
	    // 整数部分数字
	    int[] integers = toArray(integerStr);
	    // 设置万单位
	    boolean isMust5 = isMust5(integerStr);
	    // 小数部分数字
	    int[] decimals = toArray(decimalStr);
	    return getChineseInteger(integers, isMust5) + getChineseDecimal(decimals);
	  }
	  /**
	   * 整数部分和小数部分转换为数组，从高位至低位
	   */
	  private static int[] toArray(String number) {
	    int[] array = new int[number.length()];
	    for (int i = 0; i < number.length(); i++) {
	      array[i] = Integer.parseInt(number.substring(i, i + 1));
	    }
	    return array;
	  }
	  /**
	   * 得到中文金额的整数部分。
	   */
	  private static String getChineseInteger(int[] integers, boolean isMust5) {
	    StringBuffer chineseInteger = new StringBuffer("");
	    int length = integers.length;
	    for (int i = 0; i < length; i++) {
	      // 0出现在关键位置：1234(万)5678(亿)9012(万)3456(元)
	      // 特殊情况：10(拾元、壹拾元、壹拾万元、拾万元)
	      String key = "";
	      if (integers[i] == 0) {
	    	// 万(亿)(必填)
	        if ((length - i) == 13) {
	        	key = IUNIT[4];
	        }
	        // 亿(必填)
	        else if ((length - i) == 9) {
	        	key = IUNIT[8];
	        }
	        // 万(不必填)
	        else if ((length - i) == 5 && isMust5) {
	        	key = IUNIT[4];
	        }
	        // 元(必填)
	        else if ((length - i) == 1) {
	        	key = IUNIT[0];
	        }
	        // 0遇非0时补零，不包含最后一位
	        if ((length - i) > 1 && integers[i + 1] != 0) {
	        	key += NUMBERS[0];
	        }
	      }
	      chineseInteger.append(integers[i] == 0 ? key
	          : (NUMBERS[integers[i]] + IUNIT[length - i - 1]));
	    }
	    return chineseInteger.toString();
	  }
	  /**
	   * 得到中文金额的小数部分。
	   */
	  private static String getChineseDecimal(int[] decimals) {
	    StringBuffer chineseDecimal = new StringBuffer("");
	    for (int i = 0; i < decimals.length; i++) {
	      // 舍去3位小数之后的
	      if (i == 3) {
	    	  break;
	      }
	      chineseDecimal.append(decimals[i] == 0 ? ""
	          : (NUMBERS[decimals[i]] + DUNIT[i]));
	    }
	    return chineseDecimal.toString();
	  }
	  /**
	   * 判断第5位数字的单位"万"是否应加。
	   */
	  private static boolean isMust5(String integerStr) {
	    int length = integerStr.length();
	    if (length > 4) {
	      String subInteger = "";
	      if (length > 8) {
	        // 取得从低位数，第5到第8位的字串
	        subInteger = integerStr.substring(length - 8, length - 4);
	      } else {
	        subInteger = integerStr.substring(0, length - 4);
	      }
	      return Integer.parseInt(subInteger) > 0;
	    } else {
	      return false;
	    }
	  }
	  
	  /***
	   * 大写转小写  有问题？？？
	   */
	
    private static final Character[] CN_NUMERIC = { '一', '二', '三', '四', '五',  
            '六', '七', '八', '九', '壹', '贰', '叁', '肆', '伍', '陆', '柒', '捌', '玖',  
            '十', '百', '千', '拾', '佰', '仟', '万', '亿', '○', 'Ｏ', '零' };  
  
    private static Map<Character, Integer> cnNumeric = null;  
  
    static {  
        cnNumeric = new HashMap<Character, Integer>(40, 0.85f);  
        for (int j = 0; j < 9; j++) {
        	cnNumeric.put(CN_NUMERIC[j], j + 1);  
        } 
        for (int j = 9; j < 18; j++) {
        	cnNumeric.put(CN_NUMERIC[j], j - 8);  
        }  
        cnNumeric.put('两', 2);  
        cnNumeric.put('十', 10);  
        cnNumeric.put('拾', 10);  
        cnNumeric.put('百', 100);  
        cnNumeric.put('佰', 100);  
        cnNumeric.put('千', 1000);  
        cnNumeric.put('仟', 1000);  
        cnNumeric.put('万', 10000);  
        cnNumeric.put('亿', 100000000);  
    }  
    public static int isCNNumeric(char c) {  
        Integer i = cnNumeric.get(c);  
        if (i == null) {
        	return -1;  
        } 
        return i.intValue();  
    }  
    public static int cnNumericToArabic(String cnn, boolean flag) {  
  
        cnn = cnn.trim();  
        if (cnn.length() == 1) {
        	return isCNNumeric(cnn.charAt(0));  
        }
  
        if (flag) {
        	cnn = cnn.replace('佰', '百').replace('仟', '千').replace('拾', '十')  
        			.replace('零', ' ');  
        }
        // System.out.println(cnn);  
        int yi = -1, wan = -1, qian = -1, bai = -1, shi = -1;  
        int val = 0;  
        yi = cnn.lastIndexOf('亿');  
        if (yi > -1) {  
            val += cnNumericToArabic(cnn.substring(0, yi), false) * 100000000;  
            if (yi < cnn.length() - 1) {
            	cnn = cnn.substring(yi + 1, cnn.length());  
            }
            else {
            	cnn = "";  
            } 
  
            if (cnn.length() == 1) {  
                int arbic = isCNNumeric(cnn.charAt(0));  
                if (arbic <= 10) {
                	val += arbic * 10000000;  
                	cnn = "";  
                }  
            }  
        }  
  
        wan = cnn.lastIndexOf('万');  
        if (wan > -1) {  
            val += cnNumericToArabic(cnn.substring(0, wan), false) * 10000;  
            if (wan < cnn.length() - 1) {
            	cnn = cnn.substring(wan + 1, cnn.length());  
            }  
            else {
            	cnn = "";  
            }  
            if (cnn.length() == 1) {  
                int arbic = isCNNumeric(cnn.charAt(0));  
                if (arbic <= 10) {
                	val += arbic * 1000;  
                	cnn = "";  
                } 
            }  
        }  
  
        qian = cnn.lastIndexOf('千');  
        if (qian > -1) {  
            val += cnNumericToArabic(cnn.substring(0, qian), false) * 1000;  
            if (qian < cnn.length() - 1) {
            	cnn = cnn.substring(qian + 1, cnn.length());  
            } 
            else {
            	cnn = "";  
            } 
            if (cnn.length() == 1) {  
                int arbic = isCNNumeric(cnn.charAt(0));  
                if (arbic <= 10) {
                	val += arbic * 100;  
                	cnn = "";  
                }  
            }  
        }  
  
        bai = cnn.lastIndexOf('百');  
        if (bai > -1) {  
            val += cnNumericToArabic(cnn.substring(0, bai), false) * 100;  
            if (bai < cnn.length() - 1) {
            	cnn = cnn.substring(bai + 1, cnn.length());  
            } 
            else {
            	cnn = "";  
            } 
            if (cnn.length() == 1) {  
                int arbic = isCNNumeric(cnn.charAt(0));  
                if (arbic <= 10) {
                	val += arbic * 10;  
                	cnn = "";  
                }  
            }  
        }  
  
        shi = cnn.lastIndexOf('十');  
        if (shi > -1) {  
            if (shi == 0) {
            	val += 1 * 10;  
            } 
            else {
            	val += cnNumericToArabic(cnn.substring(0, shi), false) * 10;  
            } 
            if (shi < cnn.length() - 1) {
            	cnn = cnn.substring(shi + 1, cnn.length());  
            } 
            else {
            	cnn = "";  
            }  
        }  
  
        cnn = cnn.trim();  
        for (int j = 0; j < cnn.length(); j++) {
        	val += isCNNumeric(cnn.charAt(j))  
        			* Math.pow(10, cnn.length() - j - 1);  
        } 
  
        return val;  
    }  
  
    public static int qCNNumericToArabic(String cnn) {  
        int val = 0;  
        cnn = cnn.trim();  
        for (int j = 0; j < cnn.length(); j++) {
        	val += isCNNumeric(cnn.charAt(j))  
        			* Math.pow(10, cnn.length() - j - 1);  
        } 
        return val;  
    }  
  
    public static void main(String[] args) {
        int val = 0;  
        val = cnNumericToArabic("三亿二千零六万七千五百六拾玖元", true);  
        System.out.println(val);  
        val = cnNumericToArabic("一九九八", true);  
    }  
}
