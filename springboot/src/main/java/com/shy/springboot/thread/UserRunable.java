package com.shy.springboot.thread;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicInteger;

import com.alibaba.fastjson.JSONObject;
import com.shy.springboot.dao.UserDao;
import lombok.extern.slf4j.Slf4j;
@Slf4j
public class UserRunable implements Runnable {
	
	/*private int id;*/
	//UserDao userDao = (UserDao) InitializeBean.getBean("userDao");
	private int num;
	public static volatile int number = 0;  
/*	@Override
	public synchronized void run() {
		//synchronized (this) {
			number++;
			log.info("当前线程名称 >>>>>>>>>>>>>>>>>>>>>> ：" + Thread.currentThread().getName() + "======number : " + number);
		//}
	}*/
	public int getNum() {
		return num;
	}
	public void setNum(int num) {
		this.num = num;
	}
	@Override
	public void run() {
		int plus = plus(number);
		log.info("》》》》》》》》》 ：" + plus);
	}
	
	
	public synchronized int plus(int number) {
		number += number;
		return number;
	} 
	
	/*@Override
	public void run() {
		User user = userDao.getById(id);
		log.info("用户信息 ：" + JSONObject.toJSONString(user));
	}*/

	/*public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}*/

}
