package com.shihy.springboot.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;
/***
 * 
 * @Title: springstarter
 * @author shy
 * @Description TODO
 * @data 2019年3月27日 下午3:30:35
 *
 */
public class DateTest {
    private static final SimpleDateFormat dateFormatday = new SimpleDateFormat("yyyy-MM-dd");

	public static void main(String[] args) throws ParseException{
		//获取执行该方法的当前时间
		String beginTime = dateFormatday.format(new Date());
		System.out.println(beginTime);
        //通过日历获取下一天日期  
        Calendar cal = Calendar.getInstance();  
        cal.setTime(dateFormatday.parse(beginTime));  
        cal.add(Calendar.DAY_OF_YEAR, +1);  
        String endTime = dateFormatday.format(cal.getTime());  
        System.out.println(endTime);
        
        long current = System.currentTimeMillis();//当前时间毫秒数
        long zero = current/(1000*3600*24)*(1000*3600*24)-TimeZone.getDefault().getRawOffset();//今天零点零分零秒的毫秒数        long current = System.currentTimeMillis();//当前时间毫秒数
        System.out.println(zero);
        System.out.println("dq:"+current);
        System.out.println((current-zero));
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = new Date(current);
        String s = simpleDateFormat.format(date);
        System.out.println(s);
        Date date1 = new Date(zero);
        String s1 = simpleDateFormat.format(date1);
        System.out.println(s1);
        long zero_add_5min = current/(1000*3600*24)*(1000*3600*24)-TimeZone.getDefault().getRawOffset()+30*1000*60;//今天零点30分零秒的毫秒数
        Date date2 = new Date(zero_add_5min);
        String s2 = simpleDateFormat.format(date2);
        System.out.println(s2);
        System.out.println("<<<<<<<<<<<<<<<<<<<<<<<<<<<>>>>>>>>>>>>>>>>>>>>");
        Date date3 = CommonUtils.getDate((long) (1*1000*60));
        String s3 = simpleDateFormat.format(date3);
        System.out.println("@@@@:" + s3);
	}

}
