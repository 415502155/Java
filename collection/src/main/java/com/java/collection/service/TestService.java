package com.java.collection.service;

import java.util.List;

import com.java.collection.entity.UserInfo;

public interface TestService {
	
	public List<UserInfo> queryList();
	
	public List<UserInfo> queryListByPage(int pagenum, int pagesize);

}
