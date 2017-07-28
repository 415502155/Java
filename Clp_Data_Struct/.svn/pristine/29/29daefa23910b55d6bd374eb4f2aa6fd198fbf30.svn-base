package com.bestinfo.util;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 时间工具类
 *
 * @author yangyuefu
 */
public class TimeUtil {

    public static SimpleDateFormat simple_formatter_date_time = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    public static DateFormat formatter_date_time = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    public static SimpleDateFormat simple_formatter_date = new SimpleDateFormat("yyyy-MM-dd");
    public static SimpleDateFormat simple_formatter_date1 = new SimpleDateFormat("yyyy年MM月dd日");
    public static SimpleDateFormat simple_formatter_time = new SimpleDateFormat("HH:mm:ss");
    public static SimpleDateFormat simple_formatter_minute = new SimpleDateFormat("HH:mm");

    public static SimpleDateFormat simple_formatter_yy = new SimpleDateFormat("yy");
    public static SimpleDateFormat simple_formatter_yyyy = new SimpleDateFormat("yyyy");
    public static SimpleDateFormat simple_formatter_MM = new SimpleDateFormat("MM");
    public static SimpleDateFormat simple_formatter_dd = new SimpleDateFormat("dd");

    public static SimpleDateFormat simple_formatter_8 = new SimpleDateFormat("yyyyMMdd");
    public static SimpleDateFormat simple_formatter_12 = new SimpleDateFormat("yyyyMMddHHmm");
    public static SimpleDateFormat simple_formatter_14 = new SimpleDateFormat("yyyyMMddHHmmss");
    public static SimpleDateFormat simple_formatter_17 = new SimpleDateFormat("yyyyMMddHHmmssSSS");

    /**
     * date转Calendar实例
     *
     * @param date
     * @return
     */
    public static Calendar calendarOf(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        return cal;
    }

    /**
     * 毫秒转Calendar实例
     *
     * @param millseconds
     * @return
     */
    public static Calendar calendarOf(long millseconds) {
        return calendarOf(new Date(millseconds));
    }

    /**
     * 增加时间间隔
     *
     * @param date
     * @param field
     * @param amount
     * @return
     */
    public static Calendar addInterval(Calendar date, int field, int amount) {
        Calendar cal = (Calendar) date.clone();
        cal.add(field, amount);
        return cal;
    }

    /**
     * 两个日期之间的间隔，秒
     *
     * @param calendar1
     * @param calendar2
     * @return
     */
    public static int secondIntervalBetween(Calendar calendar1, Calendar calendar2) {
        long time1 = calendar1.getTimeInMillis();
        long time2 = calendar2.getTimeInMillis();
        return (int) ((time1 - time2) / 1000);
    }

    /**
     * 两个日期之间的间隔
     *
     * @param date1
     * @param date2
     * @return calendar1>calendar2大于0
     */
    public static int secondIntervalBetween(Date date1, Date date2) {
        long time1 = date1.getTime();
        long time2 = date2.getTime();
        return (int) ((time1 - time2) / 1000);
    }

    /**
     * 设置cal日期为该日开始时刻
     *
     * @param cal
     * @return
     */
    public static Calendar setStartOfDay(Calendar cal) {
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        return cal;
    }

    /**
     * 设置cal日期为该日最后时刻
     *
     * @param cal
     * @return
     */
    public static Calendar setEndOfDay(Calendar cal) {
        cal.set(Calendar.HOUR_OF_DAY, 23);
        cal.set(Calendar.MINUTE, 59);
        cal.set(Calendar.SECOND, 59);
        return cal;
    }

    /**
     * 将日期格式化
     *
     * @param date
     * @param format
     * @return
     */
    public static String formatDate(Date date, String format) {
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        return sdf.format(date);
    }

    /**
     * 将日期格式化为yy的形式
     *
     * @param date
     * @return
     */
    public static String formatDate_yy(Date date) {
        return simple_formatter_yy.format(date);
    }

    /**
     * 将日期格式化为MM的形式
     *
     * @param date
     * @return
     */
    public static String formatDate_MM(Date date) {
        return simple_formatter_MM.format(date);
    }

    /**
     * 将日期格式化为dd的形式
     *
     * @param date
     * @return
     */
    public static String formatDate_dd(Date date) {
        return simple_formatter_dd.format(date);
    }

    /**
     * 将日期格式化为yyyy的形式
     *
     * @param date
     * @return
     */
    public static String formatDate_yyyy(Date date) {
        return simple_formatter_yyyy.format(date);
    }

    /**
     * 将日期格式化为HH:mm:ss的形式
     *
     * @param date
     * @return
     */
    public static String formatDate_time(Date date) {
        return simple_formatter_time.format(date);
    }

    /**
     * 将日期格式化为HH:mm的形式
     *
     * @param date
     * @return
     */
    public static String formatDate_Minute(Date date) {
        return simple_formatter_minute.format(date);
    }

    /**
     * 将日期格式化为HH:mm:ss.MMM的形式
     *
     * @param date
     * @return
     */
    public static String formatDate_HMSS(Date date) {
        return formatDate(date, "HH:mm:ss.SSS");
    }

    /**
     * 将日期格式化为yyyy-MM-dd的形式
     *
     * @param date
     * @return
     */
    public synchronized static String formatDate_date(Date date) {
        return simple_formatter_date.format(date);
    }

    /**
     * 将日期格式化为yyyy-MM-dd HH:mm:ss的形式
     *
     * @param date
     * @return
     */
    public synchronized static String formatDate_YMDT(Date date) {
        return simple_formatter_date_time.format(date);
    }

    /**
     * 将日期格式化为yyyyMMdd的形式
     *
     * @param date
     * @return
     */
    public static String formatDate_YMD8(Date date) {
        return simple_formatter_8.format(date);
    }

    /**
     * 将日期格式化为yyyyMMddHHmm的形式
     *
     * @param date
     * @return
     */
    public static String formatDate_YMDT12(Date date) {
        return simple_formatter_12.format(date);
    }

    /**
     * 将日期格式化为yyyyMMddHHmmss的形式
     *
     * @param date
     * @return
     */
    public static String formatDate_YMDT14(Date date) {
        return simple_formatter_14.format(date);
    }

    /**
     * 将日期格式化为yyyyMMddHHmmssSSS的形式
     *
     * @param date
     * @return
     */
    public static String formatDate_YMDT17(Date date) {
        return simple_formatter_17.format(date);
    }

    /**
     * 解析日期
     *
     * @param date
     * @param format
     * @return
     * @throws ParseException
     */
//    public static Date parseDate(String date, String format) throws ParseException {
//        SimpleDateFormat sdf = new SimpleDateFormat(format);
//        return sdf.parse(date);
//    }
    /**
     * 解析日期(yyyy-MM-dd)
     *
     * @param date
     * @return
     * @throws ParseException
     */
    public synchronized static Date parseDate_YMD(String date) throws ParseException {
        return simple_formatter_date.parse(date);
    }

    /**
     * 解析日期(yyyyMMdd)
     *
     * @param date
     * @return
     * @throws ParseException
     */
    public static Date parseDate_YMD8(String date) throws ParseException {
        return simple_formatter_8.parse(date);
    }

    /**
     * 解析日期(yyyyMMddHHmmss)
     *
     * @param date
     * @return
     * @throws ParseException
     */
    public static Date parseDate_YMD14(String date) throws ParseException {
        return simple_formatter_14.parse(date);
    }

    /**
     * 解析日期(yyyy-MM-dd HH:mm:ss)
     *
     * @param date
     * @return
     * @throws ParseException
     */
    public synchronized static Date parseDate_YMDT(String date) throws ParseException {
        return simple_formatter_date_time.parse(date);
    }

    /**
     * 解析日期(HH:mm:ss)
     *
     * @param date
     * @return
     * @throws ParseException
     */
    public static Date parseDate_hms(String date) throws ParseException {
        return simple_formatter_time.parse(date);
    }

    /**
     * 将时间和日期结合
     *
     * @param day
     * @param time
     * @return
     */
    public static Calendar joinDayAndTime(Calendar day, Calendar time) {
        Calendar cal = (Calendar) day.clone();

        cal.set(Calendar.HOUR_OF_DAY, time.get(Calendar.HOUR_OF_DAY));
        cal.set(Calendar.MINUTE, time.get(Calendar.MINUTE));
        cal.set(Calendar.SECOND, time.get(Calendar.SECOND));

        return cal;
    }

    /**
     * 将day的时分秒设置为time的时分秒,即将日期和时间结合
     *
     * @param day
     * @param time
     * @return
     */
    public static Date joinDayAndTime(Date day, Date time) {
        Calendar calDay = Calendar.getInstance();
        calDay.setTime(day);

        Calendar calTime = Calendar.getInstance();
        calTime.setTime(time);

        return joinDayAndTime(calDay, calTime).getTime();
    }

    /**
     * 将时间转为秒(从1980-1-1计算)
     *
     * @param date
     * @return
     */
    public static long secondOf(Date date) {
        if (date == null) {
            return 0L;
        }
        return date.getTime() / 1000L;
    }

    /**
     * 判断两个日期是否为同一天
     *
     * @param date1
     * @param date2
     * @return
     */
    public static boolean theSameDay(Date date1, Date date2) {
        if (date1 != null && date2 != null) {
            if (formatDate_date(date1).equals(formatDate_date(date2))) {
                return true;
            }
        }
        return false;
    }

    /**
     * 加减月份
     *
     * @param startDate
     * @param n
     * @return
     */
    public static Date monthAdd(Date startDate, int n) {
        Calendar calendar = Calendar.getInstance();// 日历对象
        calendar.setTime(startDate);// 设置当前日期
        calendar.add(Calendar.MONTH, n);// 正数加,负数减
        return calendar.getTime();
    }

    /**
     * 加减天
     *
     * @param startDate
     * @param n
     * @return
     */
    public static Date dayAdd(Date startDate, int n) {
        Calendar calendar = Calendar.getInstance();// 日历对象
        calendar.setTime(startDate);// 设置当前日期
        calendar.add(Calendar.DAY_OF_MONTH, n);// 正数加,负数减
        return calendar.getTime();
    }

    /**
     * 两个时间之间相差距离多少天
     *
     * @param str1 时间参数1 yyyy-MM-dd
     * @param str2 时间参数2 yyyy-MM-dd
     * @return 相差天数
     * @throws java.lang.Exception
     */
    public static long getDistanceDays(String str1, String str2) throws Exception {
        long time1 = new SimpleDateFormat("yyyy-MM-dd").parse(str1).getTime();
        long time2 = new SimpleDateFormat("yyyy-MM-dd").parse(str2).getTime();
        long diff;
        if (time1 < time2) {
            diff = time2 - time1;
        } else {
            diff = time1 - time2;
        }
        return diff / (1000 * 60 * 60 * 24);
    }

    /**
     * 计算两个日期之间相差的天数
     *
     * @param smdate 较小时间
     * @param bdate 较大时间
     * @return 相差天数
     * @throws ParseException
     */
    public static int daysBetween(Date smdate, Date bdate) throws ParseException {
        //年月日格式的date
        smdate = new SimpleDateFormat("yyyy-MM-dd").parse(new SimpleDateFormat("yyyy-MM-dd").format(smdate));
        bdate = new SimpleDateFormat("yyyy-MM-dd").parse(new SimpleDateFormat("yyyy-MM-dd").format(bdate));

        Calendar cal = Calendar.getInstance();
        cal.setTime(smdate);
        long time1 = cal.getTimeInMillis();
        cal.setTime(bdate);
        long time2 = cal.getTimeInMillis();
        long between_days = (time2 - time1) / (1000 * 3600 * 24);

        return Integer.parseInt(String.valueOf(between_days));
    }

    /**
     * 判断日期格式及日期数据的合法性: yyyy-MM-dd
     *
     * @param sDate yyyy-MM-dd格式的字符串
     * @return true：合法日期 false: 非法日期
     */
    public static boolean isValidDate(String sDate) {
        String datePattern1 = "\\d{4}-\\d{2}-\\d{2}";
        String datePattern2 = "^((\\d{2}(([02468][048])|([13579][26]))"
                + "[\\-\\/\\s]?((((0?[13578])|(1[02]))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|"
                + "(3[01])))|(((0?[469])|(11))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(30)))|(0?2[\\-\\/\\s]?"
                + "((0?[1-9])|([1-2][0-9])))))|(\\d{2}(([02468][1235679])|([13579][01345789]))[\\-\\/\\s]?("
                + "(((0?[13578])|(1[02]))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(3[01])))|(((0?[469])|(11))[\\-\\/\\s]?"
                + "((0?[1-9])|([1-2][0-9])|(30)))|(0?2[\\-\\/\\s]?((0?[1-9])|(1[0-9])|(2[0-8]))))))";
        if ((sDate != null)) {
            Pattern pattern = Pattern.compile(datePattern1);
            Matcher match = pattern.matcher(sDate);
            if (match.matches()) {
                pattern = Pattern.compile(datePattern2);
                match = pattern.matcher(sDate);
                return match.matches();
            } else {
                return false;
            }
        }
        return false;
    }

    public static String getTime(long tm) {
        int ms = (int) (tm % 1000);
        tm /= 1000;
        int sc = (int) (tm % 60);
        tm /= 60;
        int mn = (int) (tm % 60);
        tm /= 60;
        int hr = (int) (tm % 24);
        long dy = tm / 24;
        return dy + " days " + hr + " hours " + mn + " minutes " + sc + "." + ms + " sec ";
    }

    /**
     * 获取时间的具体描述<br>
     * x 小时 x分钟...
     *
     * @param tm
     * @return
     */
    public static String getTime2(long tm) {
        int ms = (int) (tm % 1000);
        tm /= 1000;
        int sc = (int) (tm % 60);
        tm /= 60;
        int mn = (int) (tm % 60);
        tm /= 60;
        int hr = (int) (tm % 24);
        long dy = tm / 24;

        StringBuilder sb = new StringBuilder();
        if (dy > 0) {
            sb.append(dy).append(" days ");
        }
        if (hr > 0) {
            sb.append(hr).append(" hours ");
        }
        if (mn > 0) {
            sb.append(mn).append(" minutes ");
        }
        if (sc > 0) {
            sb.append(sc).append(" seconds ");
        }
        if (ms > 0) {
            sb.append(ms).append(" milliseconds ");
        }
        return sb.toString();
    }

    public static Date timeStampToDate(Timestamp ts) {
        Date date = null;
        try {
            String str = formatter_date_time.format(ts);
            date = parseDate_YMDT(str);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return date;
    }

    public static String yesterdayYMD() {
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE, -1);
        String yesterday = simple_formatter_date.format(cal.getTime());
        return yesterday;
    }

    /**
     * 获取指定日期段内的所有日期
     *
     * @param dBegin
     * @param dEnd
     * @return
     */
    public static List<String> findDates(Date dBegin, Date dEnd) {
        List<String> stringDate = new ArrayList<String>();
        stringDate.add(simple_formatter_date.format(dBegin));
        Calendar calBegin = Calendar.getInstance();
        // 使用给定的 Date 设置此 Calendar 的时间     
        calBegin.setTime(dBegin);
        Calendar calEnd = Calendar.getInstance();
        // 使用给定的 Date 设置此 Calendar 的时间     
        calEnd.setTime(dEnd);
        // 测试此日期是否在指定日期之后     
        while (dEnd.after(calBegin.getTime())) {
            // 根据日历的规则，为给定的日历字段添加或减去指定的时间量     
            calBegin.add(Calendar.DAY_OF_MONTH, 1);
            stringDate.add(simple_formatter_date.format(calBegin.getTime()));
        }
        return stringDate;
    }

    /**
     * 将日期格式化为yyyy年MM月dd日的形式
     *
     * @param date
     * @return
     */
    public static String formatDate_NYR(Date date) {
        return simple_formatter_date1.format(date);
    }

    public static void main(String[] args) {
//        System.out.println(TimeUtil.simple_formatter_dd.format(new Date()));
//        System.out.println(TimeUtil.timeStampToDate(new Timestamp(new Date().getTime())));
//        System.out.println(yesterdayYMD());
        List<String> list = null;
        try {
            list = findDates(simple_formatter_date.parse("2015-03-28"), simple_formatter_date.parse("2015-04-12"));
        } catch (Exception e) {
            e.printStackTrace();
            list = new ArrayList<String>();
        }
        System.out.println(list.toString());
    }
}
