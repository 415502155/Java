package com.gm.utils;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
/**
 * <p>
 * Title: 开发工具
 * </p>
 * <p>
 * Description: 时间工具类
 * </p>
 * <p>
 * Copyright: Copyright (c) 2007
 * </p>
 * <p>
 * Company:
 * </p>
 * 
 * @version 1.0
 */
public class DateUtil {
	public static final String DEFAULT_DATE_FORMAT = "yyyy-MM-dd HH:mm:ss SSS";
	public static final String DEFAULT_DATE_FORMAT2 = "yyyy-MM-dd HH:mm:ss";
	public static final String DATETIME_FORMAT = "yyyyMMddHHmmssSSS";
	public static final String DATE_FORMAT = "yyyy-MM-dd";

	public static final String DATE_TYPE_DAY = "DAY";
	public static final String DATE_TYPE_MONTH = "MONTH";
	public static final String DATE_TYPE_YEAR = "YEAR";
	public static final String DATE_TYPE_WEEK = "WEEK";
	public static final String DATE_TYPE_HOUR = "HOUR";
	public static final String DATE_TYPE_MINUTE = "MINUTE";
	public static final String DATE_TYPE_SECOND = "SECOND";
	public static final SimpleDateFormat SIMPLEDATEFORMAT = new SimpleDateFormat(
			DEFAULT_DATE_FORMAT2);

	public DateUtil() {
	}

	/**
	 * 将Date转换为字符串
	 * 
	 * @param date
	 *            Date 要转换的日期
	 * @param dateFormatStr
	 *            String 要转换的日期类型
	 * @return String 返加String类型的日期
	 */
	public static String DateToString(Date date, String dateFormatStr) {
		if (dateFormatStr == null || "".equals(dateFormatStr)) {
			dateFormatStr = DEFAULT_DATE_FORMAT;
		}
		SimpleDateFormat simpleDteFormat = new SimpleDateFormat(dateFormatStr);
		return simpleDteFormat.format(date);
	}

	public static String DateToString(Date date) {
		return DateToString(date, DEFAULT_DATE_FORMAT2);
	}

	/**
	 * 跟参数参数day 返回 day 那天00:00:00 时候的时间戳long
	 * 
	 * @return
	 */
	public static Long dayToTimestampStart(Date day) {
		String dayStr = DateUtil.DateToString(day, DateUtil.DATE_FORMAT);
		Timestamp start = DateUtil.cString2Timestamp(dayStr + " 00:00:00",
				DateUtil.DEFAULT_DATE_FORMAT2);
		return start.getTime();
	}

	/**
	 * 跟参数参数day 返回 day 那天23:59:59 时候的时间戳long
	 * 
	 * @return
	 */
	public static Long dayToTimestampEnd(Date day) {
		String dayStr = DateUtil.DateToString(day, DateUtil.DATE_FORMAT);
		Timestamp start = DateUtil.cString2Timestamp(dayStr + " 23:59:59",
				DateUtil.DEFAULT_DATE_FORMAT2);
		return start.getTime();
	}


	/**
	 * 跟参数参数day 返回 day 那天相隔多少秒的的时间戳时候的时间戳long
	 * Date day 这个时间是带小时 分钟 秒的 用于5分钟
	 * @return
	 */
	public static Long dayTimeToTimestampStart(Date day,int temp) {
		String dayStr = DateUtil.DateToString(day, DateUtil.DEFAULT_DATE_FORMAT2);

		String day_str = dayStr.substring(0,15);
		String day_str_start = day_str+"0:00";
		Timestamp start = DateUtil.cString2Timestamp(day_str_start,
				DateUtil.DEFAULT_DATE_FORMAT2);
		long start_time = start.getTime() - temp;//
		return start_time;
	}
	
	
	/**
	 * 跟参数参数day 返回 day 那天相隔多少秒的的时间戳时候的时间戳long
	 * Date day 这个时间是带小时 分钟 秒的
	 * @return
	 */
	public static Long dayTimeToTimestampEnd(Date day,int temp) {
		String dayStr = DateUtil.DateToString(day, DateUtil.DEFAULT_DATE_FORMAT2);
		//if()
		String day_str = dayStr.substring(0,15);
		String day_str_end = day_str+"0:00";
		Timestamp start = DateUtil.cString2Timestamp(day_str_end,
				DateUtil.DEFAULT_DATE_FORMAT2);
		long end_time = start.getTime() + temp;//
		return end_time;
	}
	
	/**
	 * 跟参数参数day 返回 day 那天相隔多少秒的的时间戳时候的时间戳long
	 * Date day 这个时间是带小时 分钟 秒的 用于1小时
	 * @return
	 */
	public static Long dayTimeToTimestampStartOneHour(Date day,int temp) {
		String dayStr = DateUtil.DateToString(day, DateUtil.DEFAULT_DATE_FORMAT2);

		String day_str = dayStr.substring(0,14);
		String day_str_start = day_str+"00:00";
		Timestamp start = DateUtil.cString2Timestamp(day_str_start,
				DateUtil.DEFAULT_DATE_FORMAT2);
		long start_time = start.getTime() - temp;//
		return start_time;
	}
	/**
	 * 将string转换成指定类型的Timestamp
	 * 
	 * @param str
	 *            String 被转换的String类型的日期
	 * @param type
	 *            String 日期格式
	 * @return Long 返加long类型的日期 如：1368979200
	 */
	public static Long cString2Timestamp2(String str, String type) {
		if (type == null || type.equals("")) {
			type = DEFAULT_DATE_FORMAT2;
		}
		if (str.length() <= 10) {
			str = str + " 00:00:00";
		}
		SimpleDateFormat df = new java.text.SimpleDateFormat(type);
		try {
			return new Timestamp(df.parse(str).getTime() / 1000).getTime();
		} catch (ParseException e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * 返回当前日期时间的字符串
	 * 
	 * @param dateFormatStr
	 *            String 要转换的日期类型
	 * @return String 返加String类型的日期
	 */
	public static String getCurrentDateTime(String dateFormatStr) {
		if (dateFormatStr == null || "".equals(dateFormatStr)) {
			dateFormatStr = DEFAULT_DATE_FORMAT2;
		}
		Date date = new Date();
		SimpleDateFormat simpleDteFormat = new SimpleDateFormat(dateFormatStr);
		return simpleDteFormat.format(date);
	}

	/**
	 * 返回当前日期的字符串
	 * 
	 * @param dateFormatStr
	 *            String 日期格式
	 * @return String 返回当前日期的字符串
	 */
	public static String getCurrentDate(String dateFormatStr) {
		if (dateFormatStr == null || "".equals(dateFormatStr)) {
			dateFormatStr = DATE_FORMAT;
		}
		Date date = new Date();
		SimpleDateFormat simpleDteFormat = new SimpleDateFormat(dateFormatStr);
		return simpleDteFormat.format(date);
	}

	/**
	 * 将字符串转换为Date
	 * 
	 * @param strDate
	 *            String 被转换的String类型的日期
	 * @param strDateFormat
	 *            String Date格式
	 * @return Date 返加Date类型的日期
	 */
	public static Date StringTodate(String strDate, String strDateFormat) {
		if (strDate == null || "".equals(strDateFormat)
				|| strDateFormat == null) {
			strDateFormat = DEFAULT_DATE_FORMAT2;
		}
		Date rDate;
		SimpleDateFormat format = new SimpleDateFormat(strDateFormat);
		try {
			rDate = format.parse(strDate);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		return rDate;
	}

	/**
	 * 将string转换成指定类型的Timestamp
	 * 
	 * @param str
	 *            String 被转换的String类型的日期
	 * @param type
	 *            String 日期格式
	 * @return Timestamp 返加Timestamp类型的日期
	 */
	public static Timestamp cString2Timestamp(String str, String type) {
		if (type == null || type.equals("")) {
			type = DEFAULT_DATE_FORMAT2;
		}
		if (str.length() <= 10) {
			str = str + " 00:00:00";
		}
		SimpleDateFormat df = new java.text.SimpleDateFormat(type);
		try {
			return new Timestamp(df.parse(str).getTime() / 1000);
		} catch (ParseException e) {
			e.printStackTrace();
			return null;
		}
	}

	public static Timestamp getTimeStamp() {
		return cString2Timestamp(getCurrentDateTime(null), null);
	}

	/**
	 * 将Timestamp转换成指定类型的string
	 * 
	 * @param ts
	 *            Timestamp 被转换的Timestamp类型的日期
	 * @param type
	 *            String 日期格式
	 * @return String 返加String类型的日期
	 */
	public static String cTimestamp2String(Timestamp ts, String type) {
		SimpleDateFormat df = new java.text.SimpleDateFormat(type);
		return df.format(new java.util.Date(ts.getTime()));
	}

	/**
	 * 将long型的Timestamp值转换成指定类型的string 例如将1362120197734数值转换为制定类型的String日期
	 * 
	 * @param ts
	 *            Timestamp 被转换的Timestamp类型的日期
	 * @param type
	 *            String 日期格式
	 * @return String 返加String类型的日期
	 */
	public static String cTimestampLong2String(long ts, String type) {
		SimpleDateFormat df = new java.text.SimpleDateFormat(type);
		return df.format(new java.util.Date(ts * 1000));
	}

	/**
	 * 将字符串型的Timestamp值转换成指定类型的string 例如将"1362120197734" 字符创转换为制定类型的String日期
	 * 
	 * @param ts
	 *            Timestamp 被转换的Timestamp类型的日期
	 * @param type
	 *            String 日期格式
	 * @return String 返加String类型的日期
	 */
	public static String cTimestampStr2String(String ts, String type) {
		Double tmp = new Double(ts);
		tmp = Math.floor(tmp);
		return cTimestampLong2String(tmp.longValue(), type);
	}

	/**
	 * 将字符串型的Timestamp值转换成Date 例如将"1362120197734" 字符创转换为日期
	 * 
	 * @param ts
	 *            Timestamp 被转换的Timestamp类型的日期
	 * @return date
	 */
	public static Date cTimestampStr2Date(String ts) {
		Double tmp = new Double(ts);
		tmp = Math.floor(tmp);
		// long tem=Long.parseLong(ts);
		return StringTodate(
				cTimestampLong2String(tmp.longValue(), DATE_FORMAT),
				DATE_FORMAT);
	}

	/**
	 * 将Timestamp转换成date
	 * 
	 * @param ts
	 *            Timestamp 被转换的Timestamp类型的日期
	 * @return Date 返加Date类型的日期
	 */
	public static Date cTimestamp2Date(Timestamp ts) {
		String strDate = cTimestamp2String(ts, DEFAULT_DATE_FORMAT2);
		return StringTodate(strDate, DEFAULT_DATE_FORMAT2);
	}

	/**
	 * 日期相加函数
	 * 
	 * @param sorDate
	 *            String 被加的日期,必须为String类型
	 * @param value
	 *            int 天数,可为负数
	 * @param dateType
	 *            String 日期格式
	 * @return String 相加后的日期,必须为String类型
	 */
	public static String dateAdd(String sorDate, int value, String dateType) {
		if (dateType == null || value == 0 || sorDate == null) {
			return sorDate;
		}
		Date date = DateUtil.StringTodate(sorDate, DATE_FORMAT);
		Date getDate = DateUtil.dateAdd(date, value, dateType);
		return DateUtil.DateToString(getDate, DATE_FORMAT);
	}

	/**
	 * 日期相加函数
	 * 
	 * @param sorDate
	 *            Date 被加的日期,必须为Date类型
	 * @param value
	 *            int 天数,可为负数
	 * @param dateType
	 *            String 日期格式
	 * @return Date 相加后的日期
	 */
	public static Date dateAdd(Date sorDate, int value, String dateType) {
		if (dateType == null || value == 0 || sorDate == null) {
			return sorDate;
		}
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(sorDate);
		if (dateType.equalsIgnoreCase(DATE_TYPE_SECOND)) {
			calendar.add(Calendar.SECOND, value);
		} else if (dateType.equalsIgnoreCase(DATE_TYPE_MINUTE)) {
			calendar.add(Calendar.MINUTE, value);
		} else if (dateType.equalsIgnoreCase(DATE_TYPE_HOUR)) {
			calendar.add(Calendar.HOUR, value);
		} else if (dateType.equalsIgnoreCase(DATE_TYPE_WEEK)) {
			calendar.add(Calendar.WEDNESDAY, value);
		} else if (dateType.equalsIgnoreCase(DATE_TYPE_DAY)) {
			calendar.add(Calendar.DATE, value);
		} else if (dateType.equalsIgnoreCase(DATE_TYPE_MONTH)) {
			calendar.add(Calendar.MONTH, value);
		} else if (dateType.equalsIgnoreCase(DATE_TYPE_YEAR)) {
			calendar.add(Calendar.YEAR, value);
		}
		return calendar.getTime();
	}

	public static long getTimemillon(String start, String end) {
		try {
			Date startDate = SIMPLEDATEFORMAT.parse(start);
			Date endDate = SIMPLEDATEFORMAT.parse(end);
			return getTimemillon(startDate, endDate);
		} catch (ParseException e) {
			System.err.println("字符串： " + start + " or  " + end + " 转换日期错误");
			return 0;
		}
	}

	public static long getTimemillon(Date startDate, Date endDate) {
		return endDate.getTime() - startDate.getTime();
	}

	public static String getNowDateTimeStr() {
		return DateToString(new Date());
	}

	public static String getNowDateString() {
		return DateToString(new Date(), DATE_FORMAT);
	}

	/**
	 * 获得当前时间的前一天，就是昨天的这个时刻
	 * 
	 * @return
	 */
	public static Date getYesterday() {
		Date date = new Date();
		return dateAdd(date, -1, DATE_TYPE_DAY);

	}

	// 得到个时间段的差值
	public static Long betWeenDate(String startDate, String endDate) {
		long zero = 0;
		Date start = DateUtil.StringTodate(startDate, DateUtil.DATE_FORMAT);
		Date end = DateUtil.StringTodate(endDate, DateUtil.DATE_FORMAT);
		long d1 = start.getTime();
		long d2 = end.getTime();

		if (d2 - d1 == 0) {
			return zero;
		}
		long date = (d2 - d1) / (24 * 60 * 62 * 1000) + 1;
		return date;
	}

	/**
	 * 判断两天是否为同一天
	 * 
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	public static boolean isSameDay(String startDate, String endDate) {
		boolean flag = false;
		Date start = DateUtil.StringTodate(startDate, DateUtil.DATE_FORMAT);
		Date end = DateUtil.StringTodate(endDate, DateUtil.DATE_FORMAT);
		Calendar calendar1 = Calendar.getInstance();
		calendar1.setTimeInMillis(start.getTime());
		Calendar calendar2 = Calendar.getInstance();
		calendar2.setTimeInMillis(end.getTime());
		if (calendar1.get(Calendar.YEAR) == calendar2.get(Calendar.YEAR)
				&& calendar1.get(Calendar.MONTH) == calendar2
						.get(Calendar.MONTH)
				&& calendar1.get(Calendar.DATE) == calendar2.get(Calendar.DATE)) {
			flag = true;
		}
		return flag;
	}

	/**
	 * 返回当前时间所属5分钟范围的开始时间。
	 * 
	 * @param date
	 * @return
	 */
	public static String getStart5TimeStr(Date date) {
		String dayStr = DateUtil.DateToString(date, DateUtil.DATE_FORMAT);
		Calendar calendar1 = Calendar.getInstance();
		calendar1.setTime(date);
		int hour = calendar1.get(Calendar.HOUR_OF_DAY);
		String hour_str = "";
		int minute = calendar1.get(Calendar.MINUTE);
		String minute_str = "";
		if (hour < 10) {
			hour_str = "0" + hour;
		} else {
			hour_str = "" + hour;
		}
		if (minute >= 10) {
			minute_str = (minute + "").substring(0, 1);
			minute = Integer.parseInt((minute + "").substring(1, 2));
			if (minute >= 5) {
				minute_str = minute_str + "0";
				/*
				 * }else if(minute == 0){ minute_str =
				 * (Integer.parseInt(minute_str)-1)+"0";
				 */
			} else {
				minute_str = (Integer.parseInt(minute_str) - 1) + "" + 5;
			}
			return dayStr + " " + hour_str + ":" + minute_str + ":" + "00";
		} else {
			if (minute >= 5) {
				calendar1.set(Calendar.MINUTE, 0);
			} else if (minute == 0) {
				calendar1.set(Calendar.HOUR_OF_DAY, hour - 1);
				calendar1.set(Calendar.MINUTE, 55);
			} else {
				calendar1.set(Calendar.HOUR_OF_DAY, hour - 1);
				calendar1.set(Calendar.MINUTE, 55);
			}
			calendar1.set(Calendar.SECOND, 0);
			return DateUtil.DateToString(calendar1.getTime(),
					DateUtil.DEFAULT_DATE_FORMAT2);
		}

	}

	/**
	 * 返回当前时间所属5分钟范围的结束时间。
	 * 
	 * @param date
	 * @return
	 */
	public static String getEnd5TimeStr(Date date) {
		String dayStr = DateUtil.DateToString(date, DateUtil.DATE_FORMAT);
		Calendar calendar1 = Calendar.getInstance();
		calendar1.setTime(date);
		int hour = calendar1.get(Calendar.HOUR_OF_DAY);
		String hour_str = "";
		int minute = calendar1.get(Calendar.MINUTE);
		String minute_str = "";
		if (hour < 10) {
			hour_str = "0" + hour;
		} else {
			hour_str = "" + hour;
		}
		if (minute >= 10) {
			minute_str = (minute + "").substring(0, 1);
			minute = Integer.parseInt((minute + "").substring(1, 2));
			if (minute >= 5) {
				minute_str = minute_str + "4";
				/*
				 * }else if(minute == 0){ minute_str =
				 * (Integer.parseInt(minute_str)-1)+"4";
				 */
			} else {
				minute_str = (Integer.parseInt(minute_str) - 1) + "" + 9;
			}
			return dayStr + " " + hour_str + ":" + minute_str + ":" + "59";
		} else {
			if (minute >= 5) {
				calendar1.set(Calendar.MINUTE, 4);
			} else {
				calendar1.set(Calendar.HOUR_OF_DAY, hour - 1);
				calendar1.set(Calendar.MINUTE, 59);
			}
			calendar1.set(Calendar.SECOND, 59);
			return DateUtil.DateToString(calendar1.getTime(),
					DateUtil.DEFAULT_DATE_FORMAT2);
		}
	}

	/**
	 * 返回当前时间前一小时的开始时间。
	 * 
	 * @param date
	 * @return
	 */
	public static String getStartHourTimeStr(Date date) {
		Calendar calendar1 = Calendar.getInstance();
		calendar1.setTime(date);
		calendar1.add(Calendar.HOUR, -1);
		calendar1.set(Calendar.MINUTE, 0);
		calendar1.set(Calendar.SECOND, 0);
		return DateUtil.DateToString(calendar1.getTime(),
				DateUtil.DEFAULT_DATE_FORMAT2);
	}
	/**
	 * 返回当前时间前一小时的结束时间。
	 * 
	 * @param date
	 * @return
	 */
	public static String getEndHourTimeStr(Date date) {
		Calendar calendar1 = Calendar.getInstance();
		calendar1.setTime(date);
		calendar1.add(Calendar.HOUR, -1);
		calendar1.set(Calendar.MINUTE, 59);
		calendar1.set(Calendar.SECOND, 59);
		return DateUtil.DateToString(calendar1.getTime(),
				DateUtil.DEFAULT_DATE_FORMAT2);
	}
	/**
	 * 获取一个星期的第一天 礼拜一的日期
	 * 具体日期型
	 *  例如：2015-12-23
	 * @return
	 */
	public static String getWeekStartStr(Date date) {
//		Calendar cal = Calendar.getInstance();
//		cal.setTime(date);
//		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
//		cal.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY); // 获取本周一的日期
		
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		int day_of_week = c.get(Calendar.DAY_OF_WEEK) - 1;
		if (day_of_week == 0)
			day_of_week = 7;
		c.add(Calendar.DATE, -day_of_week + 1);
		return DateUtil.DateToString(c.getTime(),
				DateUtil.DATE_FORMAT);
	}

	/**
	 * 获取一个星期的最后日期 礼拜日的日期
	 * 具体日期型
	 *  例如：2015-12-23
	 * @return
	 */
	public static String getWeekEndStr(Date date) {
//		Calendar cal = Calendar.getInstance();
//		cal.setTime(date);
//		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
//		cal.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);// 获取本周末的日期
//		cal.add(Calendar.WEEK_OF_YEAR, 1);// 增加一个星期，才是我们中国人理解的本周日的日期
		
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		int day_of_week = c.get(Calendar.DAY_OF_WEEK) - 1;
	    if (day_of_week == 0)
	    	day_of_week = 7;
		c.add(Calendar.DATE, -day_of_week + 7);
		  
		return DateUtil.DateToString(c.getTime(),
				DateUtil.DATE_FORMAT);
	}

	/**
	 * 得到当前日期所在周的第一天  
	 * 以时间戳形式
	 * @param date
	 * @return
	 */
	public static long getWeekStartTime(Date date){
		
		Timestamp start = DateUtil.cString2Timestamp(getWeekStartStr(date) + " 00:00:00",
				DateUtil.DEFAULT_DATE_FORMAT2);
		return start.getTime();
	
	}
	
	/**
	 * 得到当前日期所在周的最后一天
	 * 以时间戳形式
	 * @param date
	 * @return
	 */
	public static long getWeekEndTime(Date date){
		
		Timestamp start = DateUtil.cString2Timestamp(getWeekEndStr(date) + " 23:59:59",
				DateUtil.DEFAULT_DATE_FORMAT2);
		return start.getTime();
	
	}
	
	
	/**
	 * 获取一个月的第一天的日期日期
	 * 具体日期型
	 *  例如：2015-12-01
	 * @return
	 */
	public static String getMonthStartStr(Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(Calendar.MONTH, 0);
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		cal.set(Calendar.DAY_OF_MONTH,1); // 获取本周一的日期
		return DateUtil.DateToString(cal.getTime(),
				DateUtil.DATE_FORMAT);
	}
	
	/**
	 * 获取某个月的第几个月之前或者之后的日期
	 * 具体日期型  datestr 是 2015-03-27   i是之前或者之后叠加日期
	 * @return
	 */
	public static String getMonthStarttime(String datestr,int i) {
		Date date=DateUtil.StringTodate(datestr, DateUtil.DATE_FORMAT);
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(Calendar.MONTH, i);
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
	//	cal.set(Calendar.DAY_OF_MONTH,i); // 获取本周一的日期
		return df.format(cal.getTime());
	}


	/**
	 * 获取一个月的最后日期
	 * 具体日期型
	 *  例如：2015-12-31
	 * @return
	 */
	public static String getMonthEndStr(Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(Calendar.MONTH, 0);
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		cal.set(Calendar.DAY_OF_MONTH, cal.getActualMaximum(Calendar.DAY_OF_MONTH));
		return DateUtil.DateToString(cal.getTime(),
				DateUtil.DATE_FORMAT);
	}
	
	
	/**
	 * 得到当前日期所在周的第一天  
	 * 以时间戳形式
	 * @param date
	 * @return
	 */
	public static long getMonthStartTime(Date date){
		
		Timestamp start = DateUtil.cString2Timestamp(getMonthStartStr(date) + " 00:00:00",
				DateUtil.DEFAULT_DATE_FORMAT2);
		return start.getTime();
	
	}
	
	/**
	 * 计算两个日期相差的月份 
	 * @param start String 类型的两个时间  如： 2015-03-27 
	 * @param end String 类型的两个时间  如： 2015-03-27 
	 * @return
	 */
    public static int getDateMonth(String starttime, String endtime) {
    	Date start=DateUtil.StringTodate(starttime, DateUtil.DATE_FORMAT);
    	Date end=DateUtil.StringTodate(endtime, DateUtil.DATE_FORMAT);
        if (start.after(end)) {
            Date t = start;
            start = end;
            end = t;
        }
        Calendar startCalendar = Calendar.getInstance();
        startCalendar.setTime(start);
        Calendar endCalendar = Calendar.getInstance();
        endCalendar.setTime(end);
        Calendar temp = Calendar.getInstance();
        temp.setTime(end);
        temp.add(Calendar.DATE, 1);

        int year = endCalendar.get(Calendar.YEAR)
                - startCalendar.get(Calendar.YEAR);
        int month = endCalendar.get(Calendar.MONTH)
                - startCalendar.get(Calendar.MONTH);

        if ((startCalendar.get(Calendar.DATE) == 1)
                && (temp.get(Calendar.DATE) == 1)) {
            return year * 12 + month + 1;
        } else if ((startCalendar.get(Calendar.DATE) != 1)
                && (temp.get(Calendar.DATE) == 1)) {
            return year * 12 + month;
        } else if ((startCalendar.get(Calendar.DATE) == 1)
                && (temp.get(Calendar.DATE) != 1)) {
            return year * 12 + month;
        } else {
            return (year * 12 + month - 1) < 0 ? 0 : (year * 12 + month);
        }
    }
	
	
	/**
	 * 得到当前日期所在周的最后一天
	 * 以时间戳形式
	 * @param date
	 * @return
	 */
	public static long getMonthEndTime(Date date){
		
		Timestamp start = DateUtil.cString2Timestamp(getMonthEndStr(date) + " 23:59:59",
				DateUtil.DEFAULT_DATE_FORMAT2);
		return start.getTime();
	
	}
	
	 /**  
     * 计算两个日期之间相差的天数  
     * @param smdate 较小的时间 
     * @param bdate  较大的时间 
     * @return 相差天数 
     * @throws ParseException  
     */    
    public static int daysBetween(Date smdate,Date bdate) throws ParseException    
    {    
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");  
        smdate=sdf.parse(sdf.format(smdate));  
        bdate=sdf.parse(sdf.format(bdate));  
        Calendar cal = Calendar.getInstance();    
        cal.setTime(smdate);    
        long time1 = cal.getTimeInMillis();                 
        cal.setTime(bdate);    
        long time2 = cal.getTimeInMillis();         
        long between_days=(time2-time1)/(1000*3600*24);  
            
       return Integer.parseInt(String.valueOf(between_days));           
    }    
	
    public static int getMonthSpace(String date1, String date2)throws ParseException {
        int result = 0;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Calendar c1 = Calendar.getInstance();
        Calendar c2 = Calendar.getInstance();
        c1.setTime(sdf.parse(date1));
        c2.setTime(sdf.parse(date2));
        result = c2.get(Calendar.MONTH) - c1.get(Calendar.MONTH);
        return Math.abs(result);

    }

    static Calendar getCalendar(String time)  
    {  
        if (null == time)  
        {  
            return null;  
        }  
        String[] times = time.split("-");  
        Calendar calendar = Calendar.getInstance();  
        calendar.set(Integer.valueOf(times[0]), Integer.valueOf(times[1]), Integer.valueOf(times[2]));  
        return calendar;  
    }  
    
    public static int getMonth(String date1, String date2) {
    	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    	Date start=null;
    	Date end=null;
		try {
			start = sdf.parse(date1);
			end = sdf.parse(date2);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    
        if (start.after(end)) {
            Date t = start;
            start = end;
            end = t;
        }
        Calendar startCalendar = Calendar.getInstance();
        startCalendar.setTime(start);
        Calendar endCalendar = Calendar.getInstance();
        endCalendar.setTime(end);
        Calendar temp = Calendar.getInstance();
        temp.setTime(end);
        temp.add(Calendar.DATE, 1);

        int year = endCalendar.get(Calendar.YEAR)
                - startCalendar.get(Calendar.YEAR);
        int month = endCalendar.get(Calendar.MONTH)
                - startCalendar.get(Calendar.MONTH);

        if ((startCalendar.get(Calendar.DATE) == 1)
                && (temp.get(Calendar.DATE) == 1)) {
            return year * 12 + month + 1;
        } else if ((startCalendar.get(Calendar.DATE) != 1)
                && (temp.get(Calendar.DATE) == 1)) {
            return year * 12 + month;
        } else if ((startCalendar.get(Calendar.DATE) == 1)
                && (temp.get(Calendar.DATE) != 1)) {
            return year * 12 + month;
        } else {
            return (year * 12 + month - 1) < 0 ? 0 : (year * 12 + month);
        }
    }
    
    /**
     * @param stringdate 2015-12-20
     * @return  2015年12月20日
     */
    public static String getDateYMD(String stringdate){
    	String yearstr=stringdate.substring(0,4);
		String monthStr=stringdate.substring(5,7);
		String dayStr=stringdate.substring(8,10);
		String datestr=yearstr+"年"+monthStr+"月"+dayStr+"日";
		return datestr;
    }
   
    /**
     * 
     * @param stringdate  2015-12-20
     * @param 所跨月份可为正负
     * @return month=1 ，返回 2016-01-20
     */
    public static String getDateByMonth(String stringdate,int month) {
    	Date date=DateUtil.StringTodate(stringdate, DATE_FORMAT);
    	Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.MONTH, month);
        Date theDate = calendar.getTime();
    	String datestr=DateUtil.DateToString(theDate, DATE_FORMAT);
    	return datestr;
	}
    
	public static void main(String[] args) {
//		Calendar cl = Calendar.getInstance();
//		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
//		String date1="2015-01-11";
//		String date2="2014-12-04";
//		try {
//			cl.setTime(sdf.parse(date1));
//		} catch (ParseException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		System.out.println(cl.get(Calendar.WEEK_OF_YEAR));
//		Date date = StringTodate("2015-02-01", DateUtil.DATE_FORMAT);
//		System.out.println(getWeekEndStr(date));
//		String monthdatesend=DateUtil.cTimestampStr2String(DateUtil.getMonthEndTime(date)+"",DateUtil.DEFAULT_DATE_FORMAT2);                                                                                
//		System.out.println(monthdatesend);
//		System.out.println(DateUtil.getMonthStartStr(new Date()));
		String query_timeYMD="2016-06-22";
		//获取已知日期的周一和周末
		long startdate=DateUtil.getWeekStartTime(DateUtil.StringTodate(query_timeYMD, DateUtil.DATE_FORMAT));
		long enddate=DateUtil.getWeekEndTime(DateUtil.StringTodate(query_timeYMD, DateUtil.DATE_FORMAT));
		System.out.println(startdate+"----"+enddate);
		
		//获取已知日期的月初和月末
		startdate=DateUtil.getMonthStartTime(DateUtil.StringTodate(query_timeYMD, DateUtil.DATE_FORMAT));
		enddate=DateUtil.getMonthEndTime(DateUtil.StringTodate(query_timeYMD, DateUtil.DATE_FORMAT));
		System.out.println(startdate+"----"+enddate);
		
		Date day=new Date();
		String now=DateUtil.DateToString(day, DateUtil.DATE_FORMAT);
		String nowbefore1=DateUtil.dateAdd(now, -1,DateUtil.DATE_TYPE_DAY);//前1天日期
		Date day_1=DateUtil.StringTodate(nowbefore1, DateUtil.DATE_FORMAT);
		System.out.println(DateUtil.dayToTimestampStart(day_1));
	}
}
