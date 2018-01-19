package com.java.collection.controller;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;
import com.java.collection.config.Config;
import com.java.collection.entity.UserInfo;
import com.java.collection.service.TestService;
import com.java.collection.util.ReturnMsg;


/**
 * @author Administrator
 * 
 *
 * @2018年1月17日
 */
@RestController
//@Controller
@RequestMapping(value = "/web")
public class TestController {
	private static final Logger log = Logger.getLogger(TestController.class);
 
	@Autowired
	TestService testService;
	@Autowired
	Config config;
	
	@RequestMapping(value = "/api")
	public JSONObject getValue() {

		JSONObject backJson = new JSONObject();
		List<UserInfo> list = testService.queryList();
		if(null == list) {
			backJson.put(ReturnMsg.EXCEPTION_1.getCode(), ReturnMsg.EXCEPTION_1.getMsg());
			return backJson;
		}else {
			backJson.put(ReturnMsg.SUCCESS.getCode(), ReturnMsg.SUCCESS.getMsg());
		}
		backJson.put("LIST", list);
		
		return backJson;
	}
	
	@RequestMapping(value = "/page")
	public JSONObject getPage() {
		
		JSONObject backJson = new JSONObject();
		int pagenum = 10;//每一页的数量
		int pagesize = 2;//当前页数
		List<UserInfo> list = null;
		String url = config.getUrl();
		try {
			list = testService.queryListByPage(pagenum, pagesize);
			backJson.put("LIST", list);
			backJson.put("CODE", ReturnMsg.SUCCESS.getCode());
			backJson.put("MSG", ReturnMsg.SUCCESS.getMsg());
			backJson.put("PAGENUM", pagenum);
			backJson.put("PAGESIZE", pagesize);
			backJson.put("URL", url);
		} catch (Exception e) {
			// TODO: handle exception
			log.info("ex :"+e);
			backJson.put(ReturnMsg.EXCEPTION_1.getCode(), ReturnMsg.EXCEPTION_1.getMsg());
			return backJson;
			
		}
		
		return backJson;		
	}
}
