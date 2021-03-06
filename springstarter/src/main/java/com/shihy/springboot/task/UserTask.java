package com.shihy.springboot.task;

import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;

import com.shihy.springboot.config.SpringContextUtil;
import com.shihy.springboot.dao.UserMapper;
/***
 * 
 * @Title: springstarter
 * @author shy
 * @Description TODO
 * @data 2019年3月27日 下午3:30:35
 *
 */
public class UserTask implements Callable<Object> {
	
	private UserMapper userMapper = (UserMapper) SpringContextUtil.getBean("userMapper");
	private String userName;
	
	@Override
	public Object call() throws Exception {
		List<Map<String, Object>> list = userMapper.getList(userName);
		return list;
	}

	public UserTask(String userName) {
		this.userName = userName;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

}
