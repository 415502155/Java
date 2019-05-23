package com.shy.springboot.task;

import java.text.ParseException;
import java.util.Date;

import javax.annotation.Resource;

import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.shy.springboot.dao.UserDao;
import com.shy.springboot.entity.User;
import com.shy.springboot.utils.CommonUtils;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
@Async
//每一个任务都是在不同的线程中
public class SchedulingTask {
	/*@Resource
	private UserDao userDao;
	*//***
	 * cron：通过表达式来配置任务执行时间
	 *//*
	//@Async("task")
	@Scheduled(cron = "0/5 * * * * *")
	public void scheduled(){
		int count = userDao.count();
		log.info("线程 ：‘" + Thread.currentThread().getName() + "’ 每五秒執行一次=====>>>>>使用cron  {} " + "当前的用户总量为 ：" + count, CommonUtils.dateFormat(new Date(), null));
	}
	*//***
	 * fixedRate：定义一个按一定频率执行的定时任务
	 *//*
	///@Async("task")
	@Scheduled(fixedRate = 60000)
	public void scheduled1() {
		log.info("线程 ：‘" + Thread.currentThread().getName() + "’ 每一分鐘執行一次=====>>>>>使用fixedRate{}", CommonUtils.dateFormat(new Date(), null));
	}
	*//***
	 * fixedDelay：定义一个按一定频率执行的定时任务，与上面不同的是，改属性可以配合initialDelay， 定义该任务延迟执行时间
	 * 这个定时器就是在上一个的基础上加了一个initialDelay = 10000 
	 * 意思就是在容器启动后,延迟10秒后再执行一次定时器,以后每15秒再执行一次该定时器。
	 * @throws ParseException 
	 *//*
	//@Async("task")
	@Scheduled(fixedDelay = 15000, initialDelay = 10000)
	public void scheduled2() throws ParseException {
		User user = new User();
		String userName = CommonUtils.getRandomStr(8, 2);
		user.setUser_name(userName);
		user.setUser_pass(CommonUtils.getRandomStr(8, 3));
		user.setSex(1);
		String birthday = "2000-08-08 10:10:10";
		user.setBirthday(CommonUtils.stringToDate(birthday, null));
		user.setToken(CommonUtils.getRandomStr(23, 1));
		user.setIs_del(0);
		user.setCreate_time(new Date());
		user.setUpdate_time(new Date());
		userDao.add(user);
		
		log.info("线程 ：‘" + Thread.currentThread().getName() + "’ 定時添加用戶 :" + userName + "=====>>>>>fixedDelay{}", CommonUtils.dateFormat(new Date(), null));
	}*/
}