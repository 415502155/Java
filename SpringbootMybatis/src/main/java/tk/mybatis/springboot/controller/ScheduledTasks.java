package tk.mybatis.springboot.controller;

import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;


import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;
@Component
@EnableScheduling  
public class ScheduledTasks {
	
    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
    @Scheduled(cron = " 0 40,46 10,11 ? * *")//每天10:40 10:46 11:40 11:46执行
    //@Scheduled(fixedRate = 1000)//每秒执行一次
    public void reportCurrentTime() throws ParseException {
	        SimpleDateFormat formater = new SimpleDateFormat("yyyy/MM/dd");
	        SimpleDateFormat formater2 = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");	         
	        Date start = formater2.parse(formater.format(new Date())+ " 00:00:00");
	        Date end = formater2.parse(formater.format(new Date())+ " 23:59:59");
	        long s1 = start.getTime();
	        long s2 = end.getTime();
	        
	        
    	
    		long current = System.currentTimeMillis();//当前时间毫秒数
		    long zero = System.currentTimeMillis()/(1000*3600*24)*(1000*3600*24)-TimeZone.getDefault().getRawOffset();//今天零点零分零秒的毫秒数        long current = System.currentTimeMillis();//当前时间毫秒数
		    //System.out.println("零点零分零秒:"+zero);
		    //System.out.println("当前时间:"+current);
//		    System.out.println("TIMEZONE:"+TimeZone.getDefault().getRawOffset());
//		    System.out.println((current-zero));
		    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		    Date date = new Date(current);
		    String s = simpleDateFormat.format(date);
		    System.out.println("**********************************************************************************************************************:"+s);
		    Date date1 = new Date(s1);
		    String t1 = simpleDateFormat.format(date1);
		    System.out.println("零点零分零秒时间:"+t1+",毫秒数："+s1+",当前时间："+s+",毫秒数："+current);
//		    long zero_add_5min = current/(1000*3600*24)*(1000*3600*24)-TimeZone.getDefault().getRawOffset()+30*1000*60;//今天零点30分零秒的毫秒数
//		    Date date2 = new Date(zero_add_5min);
//		    String s2 = simpleDateFormat.format(date2);
//		    System.out.println(s2);
//			
//			long current=System.currentTimeMillis();//当前时间毫秒数
//			System.out.println(current+1000*60);
//	        long zero=current/(1000*3600*24)*(1000*3600*24)-TimeZone.getDefault().getRawOffset();//今天零点零分零秒的毫秒数
//	        long twelve=zero+24*60*60*1000-1;//今天23点59分59秒的毫秒数
//	        long yesterday=System.currentTimeMillis()-24*60*60*1000;//昨天的这一时间的毫秒数
//	        System.out.println(new Timestamp(current));//当前时间
//	        System.out.println(new Timestamp(yesterday));//昨天这一时间点
//	        System.out.println(new Timestamp(zero));//今天零点零分零秒
//	        System.out.println(new Timestamp(twelve));//今天23点59分59秒
        
        //System.out.println("当前时间："+ dateFormat.format(new Date()));
		    SimpleDateFormat dateFormatday = new SimpleDateFormat("yyyy-MM-dd");
	        String beginTime = dateFormatday.format(new Date());//获取执行该方法的当前时间
	        //通过日历获取下一天日期  
	        Calendar cal = Calendar.getInstance();  
	        cal.setTime(dateFormatday.parse(beginTime));  
	        cal.add(Calendar.DAY_OF_YEAR, +1);  
	        String endTime = dateFormatday.format(cal.getTime()); 
	        System.out.println("begin:"+beginTime+",end:"+endTime);
    }

}
