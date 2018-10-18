package com.gm.service;

import java.util.Calendar;
import java.util.Date;

import net.sf.json.JSONObject;

import org.apache.log4j.Logger;
import org.hibernate.envers.Audited;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gm.analy.service.AnalyOneDayServiceImpl;
import com.gm.utils.DataUtil;
import com.gm.utils.DateUtil;

@Service
@Transactional
public class Job {

//	@Audited 
//	AnalyOneDayServiceImpl analyOneDayServiceImpl;
	
	public Logger logger = Logger.getLogger(getClass()); 
	
	/**
	 * 每小时查询一次数据库，看是否有任务需要被执行，如果有任务需要被执行，则执行任务
	 * 定时：每小时执行一次，定时发送邮件 
	 * */
	//	@Scheduled(cron="0 */1 * * * ?")
	//	@Scheduled(cron="0 0 */1 * * ?") //每小时查询一次数据库，看是否有任务需要被执行，如果有任务需要被执行，则执行任务
	public void sendEmail(){
		try {
			Date date=new Date();
			String now=DateUtil.DateToString(date, DateUtil.DATE_FORMAT);
			String hour=date.getHours()+"";
			int dateday=date.getDate();
			Calendar cal = Calendar.getInstance();
			String start_time=now+" "+(date.getHours()-1)+":00:00";
			String end_time=now+" "+hour+":00:00";
			JSONObject jsonObject=new JSONObject();
			jsonObject.put("hour", hour);
			
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {		
		double decimal = Double.parseDouble(DataUtil.division2(100, 100));
		System.out.println(decimal);		
		System.out.println(100%20);
		
	}
}
