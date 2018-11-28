package sng.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.LinkedHashMap;
import java.util.Locale;
import java.util.Map;
import java.util.TimeZone;

public class DateUtil {
	/** 本地化 */
	private static Locale locale = Locale.SIMPLIFIED_CHINESE;

	/** 缺省的DateFormat对象，可以将一个java.util.Date格式化成 yyyy-mm-dd 输出 */
	private static DateFormat dateDF = DateFormat.getDateInstance(
			DateFormat.MEDIUM, locale);

	/** 缺省的DateFormat对象，可以将一个java.util.Date格式化成 HH:SS:MM 输出 */
	private static DateFormat timeDF = DateFormat.getTimeInstance(
			DateFormat.MEDIUM, locale);

	/** 缺省的DateFormat对象，可以将一个java.util.Date格式化成 yyyy-mm-dd HH:SS:MM 输出 */
	private static DateFormat datetimeDF = DateFormat.getDateTimeInstance(
			DateFormat.MEDIUM, DateFormat.MEDIUM, locale);

	private DateUtil() {
	}

	public static Date getNewDate() {
		Date dNow = new Date();
		return dNow;
	}
	
	public static void main(String[] args) {
		String a = "12:10";
		String b = "11:10";
		try {
			System.out.println(compTime(a,b));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * 返回一个当前的时间，并按格式转换为字符串 例：17:27:03
	 * 
	 * @return String
	 */
	public static String getTime() {
		GregorianCalendar gcNow = new GregorianCalendar();
		Date dNow = gcNow.getTime();
		return timeDF.format(dNow);
	}

	/**
	 * 返回一个当前日期，并按格式转换为字符串 例：2004-4-30
	 * 
	 * @return String
	 */
	public static String getDate() {
		// GregorianCalendar gcNow = new GregorianCalendar();
		// Date dNow = gcNow.getTime();
		Date dNow = new Date();
		return dateDF.format(dNow);
	}

	/**
	 * 返回一个当前日期和时间，并按格式转换为字符串 例：2004-4-30 17:27:03
	 * 
	 * @return String
	 */
	public static String getDateTimeByString() {
		GregorianCalendar gcNow = new GregorianCalendar();
		Date dNow = gcNow.getTime();
		return datetimeDF.format(dNow);
	}

	/**
	 * 返回一个当前日期和时间，并按格式转换为字符串 例：2004-4-30 17:27:03
	 * 
	 * @return Date
	 */
	public static Date getDateTimeByDate() {
		return toDateTime(getDateTimeByString());
	}

	/**
	 * 返回一个当前日期和时间，并按格式转换为字符串 例：2004-4-30
	 * 
	 * @return Date
	 */
	public static Date getDateByDate() {
		return toDate(getDateTimeByString());
	}

	/**
	 * 返回当前年的年号
	 * 
	 * @return int
	 */
	public static int getYear() {
		GregorianCalendar gcNow = new GregorianCalendar();
		return gcNow.get(GregorianCalendar.YEAR);
	}

	/**
	 * 取得指定年月的当月总天数
	 * 
	 * @param year
	 *            年
	 * @param month
	 *            月
	 * @return 当月总天数
	 */
	public static int getLastDay(int year, int month) {
		int day = 1;
		Calendar cal = Calendar.getInstance();
		cal.set(year, month - 1, day);
		int last = cal.getActualMaximum(Calendar.DATE);
		return last;
	}

	/**
	 * 返回本月月号：从 0 开始
	 * 
	 * @return int
	 */
	public static int getMonth() {
		GregorianCalendar gcNow = new GregorianCalendar();
		return gcNow.get(GregorianCalendar.MONTH);
	}

	/**
	 * 返回今天是本月的第几天
	 * 
	 * @return int 从1开始
	 */
	public static int getToDayOfMonth() {
		GregorianCalendar gcNow = new GregorianCalendar();
		return gcNow.get(GregorianCalendar.DAY_OF_MONTH);
	}

	/**
	 * 返回一格式化的日期
	 * 
	 * @param date
	 *            java.util.Date
	 * @return String yyyy-mm-dd 格式
	 */
	public static String formatDate(java.util.Date date) {
		DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		formatter.setTimeZone(TimeZone.getTimeZone("GMT+8"));
		return formatter.format(date);
	}

	/**
	 * 返回一格式化的日期
	 * 
	 * @param String
	 *            Date
	 * @return String yyyy年mm月dd日 格式
	 */
	public static String formatDateCN(String date) throws Exception {
		Date time = dateDF.parse(date);
		DateFormat format = new SimpleDateFormat("yyyy年MM月dd");
		format.setTimeZone(TimeZone.getTimeZone("GMT+8"));
		return format.format(time);
	}

	/**
	 * 返回一格式化的日期
	 * 
	 * @param date
	 * @return
	 */
	public static String formatDate(long date) {
		return formatDate(new java.util.Date(date));
	}

	/**
	 * 返回一格式化的时间
	 * 
	 * @param date
	 *            Date
	 * @return String hh:ss:mm 格式
	 */
	public static String formatTime(java.util.Date date) {
		return timeDF.format(date);
	}

	/**
	 * 返回一格式化的时间
	 * 
	 * @param String
	 *            Date
	 * @return String hh:ss格式
	 */
	public static String formatTimeMS(String date) {

		DateFormat format1 = new SimpleDateFormat("HH:mm");
		format1.setTimeZone(TimeZone.getTimeZone("GMT+8"));
		Date time = DateUtil.toDateTime(date);
		String times = format1.format(time);
		return times;
	}

	/**
	 * 返回一格式化的时间
	 * 
	 * @param date
	 * @return
	 */
	public static String formatTime(long date) {
		return formatTime(new java.util.Date(date));
	}

	/**
	 * 返回一格式化的日期时间
	 * 
	 * @param date
	 *            Date
	 * @return String yyyy-mm-dd hh:ss:mm 格式
	 */
	public static String formatDateTime(java.util.Date date) {
		DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		formatter.setTimeZone(TimeZone.getTimeZone("GMT+8"));
		return date == null ? "" : formatter.format(date);
	}
	
	/**
	 * 返回yyyy-MM-dd HH:mm格式的日期时间
	 * 
	 * @param date
	 *            Date
	 * @return String yyyy-mm-dd hh:ss 格式
	 */
	public static String formatDateTime1(java.util.Date date) {
		DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		formatter.setTimeZone(TimeZone.getTimeZone("GMT+8"));
		return date == null ? "" : formatter.format(date);
	}
	/**
	 * 返回yyyy年MM月dd日 格式的日期时间
	 * 
	 * @param date
	 *            Date
	 * @return String yyyy-mm-dd hh:ss 格式
	 */
	public static String formatDateTime2(java.util.Date date) {
		DateFormat formatter = new SimpleDateFormat("yyyy年MM月dd日");
		formatter.setTimeZone(TimeZone.getTimeZone("GMT+8"));
		return date == null ? "" : formatter.format(date);
	}

	/**
	 * 返回一格式化的日期时间
	 * 
	 * @param date
	 * @return
	 */
	public static String formatDateTime(long date) {
		return formatDateTime(new java.util.Date(date));
	}

	/**
	 * 将字串转成日期和时间，字串格式: yyyy-MM-dd HH:mm:ss
	 * 
	 * @param string
	 *            String
	 * @return Date
	 */
	public static Date toDateTime(String string) {
		try {
			DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			formatter.setTimeZone(TimeZone.getTimeZone("GMT+8"));
			return (java.util.Date) formatter.parse(string + ":00");
		} catch (Exception ex) {
			return null;
		}
	}

	/**
	 * 
	 * toDateTime: 将毫秒转换成 date 类型 格式: yyyy-MM-dd HH:mm:ss
	 * 
	 * @param @param time
	 * @param @return 设定文件
	 * @return Date DOM对象
	 * @throws
	 * @since CodingExample　Ver 1.1
	 */
	public static Date toDateTime(long time) {
		try {
			return toDateTime(formatDateTime(time));
		} catch (Exception ex) {
			return null;
		}
	}

	/**
	 * 
	 * toDateCustomFormat:自定义日期规则
	 * 
	 * @param @param string
	 * @param @param dateFormat
	 * @return Date DOM对象
	 * @throws
	 */
	public static Date toDateCustomFormat(String string, String dateFormat) {
		try {
			DateFormat formatter = new SimpleDateFormat(dateFormat, locale);
			formatter.setTimeZone(TimeZone.getTimeZone("GMT+8"));
			return (java.util.Date) formatter.parse(string);
		} catch (Exception ex) {
			return null;
		}
	}

	/**
	 * 将字串转成日期，字串格式: yyyy-MM-dd
	 * 
	 * @param string
	 *            String
	 * @return Date
	 */
	public static Date toDate(String string) {
		try {
			DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
			formatter.setTimeZone(TimeZone.getTimeZone("GMT+8"));
			if (null == string || "".equals(string)) {
				return null;
			}
			return (java.util.Date) formatter.parse(string);
		} catch (Exception ex) {
			return null;
		}
	}
	
	public static Date toDateTime1(String string) {
		try {
			DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm");
			formatter.setTimeZone(TimeZone.getTimeZone("GMT+8"));
			if (null == string || "".equals(string)) {
				return null;
			}
			return (java.util.Date) formatter.parse(string);
		} catch (Exception ex) {
			return null;
		}
	}

	public static Date toDateTime2(String string) {
		try {
			DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			formatter.setTimeZone(TimeZone.getTimeZone("GMT+8"));
			if (null == string || "".equals(string)) {
				return null;
			}
			return (java.util.Date) formatter.parse(string);
		} catch (Exception ex) {
			return null;
		}
	}

	/**
	 * 将字串转成日期，字串格式: yyyyMMdd
	 * 
	 * @param string
	 *            String
	 * @return Date
	 */
	public static Date toDates(String string) {
		try {
			DateFormat formatter = new SimpleDateFormat("yyyyMMdd");
			formatter.setTimeZone(TimeZone.getTimeZone("GMT+8"));
			if (null == string || "".equals(string)) {
				return null;
			}
			return (java.util.Date) formatter.parse(string);
		} catch (Exception ex) {
			return null;
		}
	}
	
	/**
	 * 返回当前时间的yyyyMMddHHmmss格式
	 * @return
	 */
	public static String toDateTimes() {
		try {
			DateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
			return formatter.format(new Date());
		} catch (Exception ex) {
			return "";
		}
	}

	/**
	 * 取值：某日期的年号
	 * 
	 * @param date
	 *            格式: yyyy/MM/dd
	 * @return
	 */
	public static int getYear(String date) {
		java.util.Date d = toDate(date);
		if (d == null)
			return 0;

		Calendar calendar = Calendar.getInstance(locale);
		calendar.setTime(d);
		return calendar.get(Calendar.YEAR);
	}

	/**
	 * 取值：某日期的月号
	 * 
	 * @param date
	 *            格式: yyyy/MM/dd
	 * @return
	 */
	public static int getMonth(String date) {
		java.util.Date d = toDate(date);
		if (d == null)
			return 0;

		Calendar calendar = Calendar.getInstance(locale);
		calendar.setTime(d);
		return calendar.get(Calendar.MONTH);
	}

	/**
	 * 取值：某日期的日号
	 * 
	 * @param date
	 *            格式: yyyy/MM/dd
	 * @return 从1开始
	 */
	public static int getDayOfMonth(String date) {
		java.util.Date d = toDate(date);
		if (d == null)
			return 0;

		Calendar calendar = Calendar.getInstance(locale);
		calendar.setTime(d);
		return calendar.get(Calendar.DAY_OF_MONTH);
	}

	/**
	 * 取值：某日期的日号
	 * 
	 * @param date
	 *            格式: yyyy/MM/dd
	 * @return 从1开始
	 */
	public static int getDayOfMonth(Date d) {
		if (d == null)
			return 0;

		Calendar calendar = Calendar.getInstance(locale);
		calendar.setTime(d);
		return calendar.get(Calendar.DAY_OF_MONTH);
	}

	/**
	 * 计算两个日期的年数差
	 * 
	 * @param one
	 *            格式: yyyy/MM/dd
	 * @param two
	 *            格式: yyyy/MM/dd
	 * @return
	 */
	public static int compareYear(String one, String two) {
		return getYear(one) - getYear(two);
	}

	/**
	 * 计算岁数
	 * 
	 * @param date
	 *            格式: yyyy/MM/dd
	 * @return
	 */
	public static int compareYear(String date) {
		return getYear() - getYear(date);
	}

	/**
	 * 
	 * dateCompare:比较日期大小
	 * 
	 * @param @param dat1
	 * @param @param dat2
	 * @return 1 dat1 > dat2 -1 dat1 < dat2 0 dat1 = dat2
	 */
	public static int compareDate(Date dat1, Date dat2) {
		if (dat1.getTime() > dat2.getTime()) {
			return 1;
		} else if (dat1.getTime() < dat2.getTime()) {
			return -1;
		} else {
			return 0;
		}
	}

	// 指定的日期是星期几
	public static String getWeekOfDate(String date) {
		String[] weekDays = { "7", "1", "2", "3", "4", "5", "6" };
		Calendar cal = Calendar.getInstance();
		cal.setTime(DateUtil.toDate(date));

		int w = cal.get(Calendar.DAY_OF_WEEK) - 1;
		if (w < 0)
			w = 0;

		return weekDays[w];
	}

	// 指定的日期是星期几
	public static String getWeekOfDate(Date date) {
		String[] weekDays = { "7", "1", "2", "3", "4", "5", "6" };
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);

		int w = cal.get(Calendar.DAY_OF_WEEK) - 1;
		if (w < 0)
			w = 0;

		return weekDays[w];
	}
	public static String getWeekcnOfDate(Date date) {
		String[] weekDays = { "星期日", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六" };
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);

		int w = cal.get(Calendar.DAY_OF_WEEK) - 1;
		if (w < 0)
			w = 0;
		return weekDays[w];
	}

	// 指定的日期是星期几
	public static String getWeekOfDateCN(String date) {
		String[] weekDays = { "星期日", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六" };
		Calendar cal = Calendar.getInstance();
		cal.setTime(DateUtil.toDate(date));

		int w = cal.get(Calendar.DAY_OF_WEEK) - 1;
		if (w < 0)
			w = 0;

		return weekDays[w];
	}

	// //指定的日期是星期几
	// public static String getWeekOfDate(String date) {
	// String[] weekDays = {"星期日", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六"};
	// Calendar cal = Calendar.getInstance();
	// cal.setTime(DateUtil.toDate(date));
	//
	// int w = cal.get(Calendar.DAY_OF_WEEK) - 1;
	// if (w < 0)
	// w = 0;
	//
	// return weekDays[w];
	// }

	public static int getHao() {
		Date date = new Date();
		// 今天是几号
		@SuppressWarnings("deprecation")
		int day = date.getDate();

		return day;
	}

	/**
	 * 
	 * dateCompare:增 减天数
	 * 
	 * @param @param date 日期
	 * @param @param day 天数
	 * @return String
	 */
	public static String addDays(String date, int day) {
		Calendar c = Calendar.getInstance();
		c.setTime(DateUtil.toDate(date));
		c.add(Calendar.DATE, day); // 日期加1
		Date dates = c.getTime();

		return DateUtil.formatDate(dates);
	}

	public static Date addDaysCustom(Date stime, int day) {
		Calendar c = Calendar.getInstance();
		c.setTime(stime);
		c.add(Calendar.DATE, day); // 日期加1
		Date dates = c.getTime();
		return dates;
	}

	/**
	 * 返回两个日期相差的天数
	 * 
	 * @param startDate
	 * @param endDate
	 * @return
	 * @throws ParseException
	 */
	public static long getDistDates(Date startDate, Date endDate) {
		long totalDate = 0;
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(startDate);
		long timestart = calendar.getTimeInMillis();
		calendar.setTime(endDate);
		long timeend = calendar.getTimeInMillis();
		totalDate = Math.abs((timeend - timestart)) / (1000 * 60 * 60 * 24);
		return totalDate;
	}
	
	public static Date getThisWeekMonday(Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		// 获得当前日期是一个星期的第几天
		int dayWeek = cal.get(Calendar.DAY_OF_WEEK);
		if (1 == dayWeek) {
			cal.add(Calendar.DAY_OF_MONTH, -1);
		}
		// 设置一个星期的第一天，按中国的习惯一个星期的第一天是星期一
		cal.setFirstDayOfWeek(Calendar.MONDAY);
		// 获得当前日期是一个星期的第几天
		int day = cal.get(Calendar.DAY_OF_WEEK);
		// 根据日历的规则，给当前日期减去星期几与一个星期第一天的差值
		cal.add(Calendar.DATE, cal.getFirstDayOfWeek() - day);
		return cal.getTime();
	}
	
	/***
	 * 
	 *  @Description: 十分 比较大小
	 *  @param s1
	 *  @param s2
	 *  @return
	 */
	public static boolean compTime(String s1,String s2){
		try {
			if (s1.indexOf(":")<0||s1.indexOf(":")<0) {
				System.out.println("格式不正确");
			}else{
				String[]array1 = s1.split(":");
				int total1 = Integer.valueOf(array1[0])*3600+Integer.valueOf(array1[1])*60;
				String[]array2 = s2.split(":");
				int total2 = Integer.valueOf(array2[0])*3600+Integer.valueOf(array2[1])*60;
				return total1-total2>0?true:false;
			}
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			return true;
		}
		return false;
 
	}
	

}
