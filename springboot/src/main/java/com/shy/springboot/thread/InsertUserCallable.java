package com.shy.springboot.thread;

import java.util.List;
import java.util.concurrent.Callable;

import com.shy.springboot.dao.UserDao;
import com.shy.springboot.entity.User;

import lombok.extern.slf4j.Slf4j;
/***
 * 线程内防注入，通过InitializeBean注入service或dao
 * @author sjwy-0001
 *
 */
@Slf4j
public class InsertUserCallable implements Callable<Object>{
	
	UserDao userDao = (UserDao) InitializeBean.getBean("userDao");
	
	private String name;
	private List<User> list;
	
	@Override
	public Object call() throws Exception {
		String name = Thread.currentThread().getName();
		log.info("当前线程名称 ：》》》》》》》》》》》》》》》》》》》》》》》》》》》》》》》》》》》》》》》  " + name);
		int[] insertReturnPrimaryKey = userDao.batchAdd(list);
		return insertReturnPrimaryKey;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public UserDao getUserDao() {
		return userDao;
	}

	public void setUserDao(UserDao userDao) {
		this.userDao = userDao;
	}

	public List<User> getList() {
		return list;
	}

	public void setList(List<User> list) {
		this.list = list;
	}
}
