package com.java.collection.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.java.collection.entity.UserInfo;

public interface TestMapper {
	
	public List<UserInfo> queryList();
	
	public List<UserInfo> queryListByPage(@Param("start")int start,@Param("end")int end);
}
