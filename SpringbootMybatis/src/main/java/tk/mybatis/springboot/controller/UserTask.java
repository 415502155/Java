package tk.mybatis.springboot.controller;

import java.util.List;
import java.util.concurrent.Callable;

import org.apache.log4j.Logger;

import tk.mybatis.springboot.conf.InitializeBean;
import tk.mybatis.springboot.mapper.UserInfoMapper;
import tk.mybatis.springboot.model.UserInfo;

public class UserTask implements Callable<Object>{
	Logger logger = Logger.getLogger(UserTask.class);
	private int name;
	private int start;
	private int end;

	@Override
	public Object call() throws Exception {
		// TODO Auto-generated method stub
		UserInfoMapper userInfoMapper = (UserInfoMapper) InitializeBean.getBean("userInfoMapper");
		//int count = userInfoMapper.CountUserInfo();
		List<UserInfo> userInfos = userInfoMapper.getPageUserInfo(start,end);
		return userInfos;
	}
	
	public UserTask(int name) {
		this.name = name;
	}
	
	public int getName() {
		return name;
	}

	public void setName(int name) {
		this.name = name;
	}

	public int getStart() {
		return start;
	}

	public void setStart(int start) {
		this.start = start;
	}

	public int getEnd() {
		return end;
	}

	public void setEnd(int end) {
		this.end = end;
	}

}
