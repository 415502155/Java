package com.bestinfo.util;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 字符串工具类
 *
 * @author yangyangfu
 */
public class StringUtil {

    /**
     * 校验字符串不为null且不为空
     *
     * @param str
     * @return
     */
    public static boolean notNull(String str) {
        return str != null && !"".equals(str);
    }

    /**
     * 将数值形式的字符串转为BigDecimal类型的数值（四舍五入，保留四位小数）
     *
     * @param str
     * @return
     */
    public static BigDecimal parseString(String str) {
        BigDecimal bd = new BigDecimal(str);
        bd = bd.setScale(4, BigDecimal.ROUND_HALF_UP);
        return bd;
    }

    /**
     * 判断字符串是否是大于0的整数或有小数位的数值型字符串，小数位最多4位
     *
     * @param str
     * @return
     */
    public static boolean isNumeric(String str) {
        Pattern pattern = Pattern.compile("^([1-9][0-9]*(\\.[0-9]{1,4})?|0\\.(?!0+$)[0-9]{1,4})$");
        Matcher isNum = pattern.matcher(str);
        if (!isNum.matches()) {
            return false;
        }
        return true;
    }

    /**
     * 判断字符串是否是大于等于0的整数或有小数位的数值型字符串，小数位最多4位 
     *
     * @param str
     * @return
     */
    public static boolean isNumericIncludeZero(String str) {
        Pattern pattern = Pattern.compile("^([0-9][0-9]*(\\.[0-9]{1,4})?|0\\.(?!0+$)[0-9]{1,4})$");
        Matcher isNum = pattern.matcher(str);
        if (!isNum.matches()) {
            return false;
        }
        return true;
    }

    /**
     * 判断字符串是否是日期格式
     *
     * @param date
     * @return
     */
    public static boolean isDate(String date) {
        try {
            TimeUtil.parseDate_YMD(date);
            return true;
        } catch (ParseException ex) {
            return false;
        }
    }

    /**
     * 判断字符串是否是日期格式
     *
     * @param date
     * @return
     */
    public static boolean isDateYMD(String date) {
        try {
            TimeUtil.parseDate_YMD8(date);
            return true;
        } catch (ParseException ex) {
            return false;
        }
    }

    /**
     * 给日志加上当前时间
     *
     * @param log
     * @return
     */
    public static String addTimeToLog(String log) {
        StringBuilder sb = new StringBuilder();
        sb.append(TimeUtil.formatDate_time(new Date()));
        sb.append("\t");
        sb.append(log);
        return sb.toString();
    }

    /**
     * 在个位数字前面补0
     *
     * @param number
     * @return
     */
    public static String appendZero(int number) {
        String pattern = "00";
        DecimalFormat df = new DecimalFormat(pattern);
        return df.format(number);
    }

    public static void main(String[] args) {
        System.out.println(StringUtil.appendZero(9));
    }
}
