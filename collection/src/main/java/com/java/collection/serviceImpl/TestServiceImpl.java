package com.java.collection.serviceImpl;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.java.collection.entity.UserInfo;
import com.java.collection.mapper.TestMapper;
import com.java.collection.service.TestService;
import com.java.collection.util.PageInfo;

@Component
public class TestServiceImpl implements TestService{
	
	private static Logger logger = Logger.getLogger(TestServiceImpl.class);
	
	@Autowired
	TestMapper testMapper;
	public List<UserInfo> queryList() {
		
		// TODO Auto-generated method stub
		List<UserInfo> list = null;
		try {
			list = testMapper.queryList();
		} catch (Exception e) {
			// TODO: handle exception
			logger.info("UserInfo querylist ex :"+e);
		}
		
		return list;
	}
	
	public List<UserInfo> queryListByPage(int pagenum, int pagesize) {
		// TODO Auto-generated method stub
		
		PageInfo page = new PageInfo();
		page.setPagenum(pagenum);
		page.setCurpagesize(pagesize);
		int start = page.getStartNum(page.getPagenum(), page.getCurpagesize());
		/**
		 * mysql数据库limit(a,b) b是偏移量
		 * oracle数据库rownum end用注释的代码获取
		 */
		int end = page.getPagenum();//page.getEndNum(page.getPagenum(), page.getCurpagesize());
		List<UserInfo> list =  testMapper.queryListByPage(start, end);
		
		return list;
	}

}
