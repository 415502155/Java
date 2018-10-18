package com.gm.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.SimpleTimeZone;
import java.util.TimeZone;

/**
 * 时区时间工具类
 * @author jiazhq
 *
 */
public class TimeZoneUtil {

	private static final String YYYY_MM_DD_HH_MM_SS = "yyyy-MM-dd HH:mm:ss";

	/**
	 * 取北京时间
	 * 
	 * @return
	 */
	public static String getBeijingTime() {
		return getFormatedDateString(8);
	}

	/**
	 * 取越南时间
	 * 
	 * @return
	 */
	public static String getYuenanTime() {
		return getFormatedDateString(7);
	}

	/**
	 * 取班加罗尔时间
	 * 
	 * @return
	 */
	public static String getBangaloreTime() {
		return getFormatedDateString(5.5f);
	}

	/**
	 * 取纽约时间
	 * 
	 * @return
	 */
	public static String getNewyorkTime() {
		return getFormatedDateString(-5);
	}

	/**
	 * 此函数非原创，从网上搜索而来，timeZoneOffset原为int类型，为班加罗尔调整成float类型
	 * timeZoneOffset表示时区，如中国一般使用东八区，因此timeZoneOffset就是8
	 * 
	 * @param timeZoneOffset
	 * @return
	 */
	public static String getFormatedDateString(float timeZoneOffset) {
		if (timeZoneOffset > 13 || timeZoneOffset < -12) {
			timeZoneOffset = 0;
		}

		int newTime = (int) (timeZoneOffset * 60 * 60 * 1000);
		TimeZone timeZone;
		String[] ids = TimeZone.getAvailableIDs(newTime);
		if (ids.length == 0) {
			timeZone = TimeZone.getDefault();
		} else {
			timeZone = new SimpleTimeZone(newTime, ids[0]);
		}

		SimpleDateFormat sdf = new SimpleDateFormat(YYYY_MM_DD_HH_MM_SS);
		sdf.setTimeZone(timeZone);
		Date date = new Date();
		return sdf.format(date);
	}

	

	private static long parseStr2Long(String yuenanTime) {
		Date date2 = null;
		try {
			date2 = new SimpleDateFormat(YYYY_MM_DD_HH_MM_SS).parse(yuenanTime);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		long l = date2.getTime()/1000;
		return l;
	}
	
	public static void main(String[] args) {
		String yuenanTime = TimeZoneUtil.getYuenanTime();
		System.out.println(yuenanTime);
		
		long l = parseStr2Long(yuenanTime);
		System.out.println(l);
	}

}
