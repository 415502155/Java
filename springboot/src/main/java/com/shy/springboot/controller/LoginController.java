package com.shy.springboot.controller;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.amqp.AmqpException;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessagePostProcessor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import com.alibaba.fastjson.JSONObject;
import com.shy.springboot.dao.UserDao;
import com.shy.springboot.entity.User;
import com.shy.springboot.thread.InsertUserCallable;
import com.shy.springboot.utils.CommonUtils;
import com.shy.springboot.utils.Constant;
import com.shy.springboot.utils.QRCodeUtil;
import com.wordnik.swagger.annotations.ApiImplicitParam;
import com.wordnik.swagger.annotations.ApiImplicitParams;
import com.wordnik.swagger.annotations.ApiOperation;

import io.goeasy.GoEasy;
import io.goeasy.publish.GoEasyError;
import io.goeasy.publish.PublishListener;
import lombok.extern.slf4j.Slf4j;
/***
 * 
 * @author sjwy-0001
 *
 */
@Slf4j
@RestController
@RequestMapping(value = "/login")
public class LoginController {
	
	@Resource
	private UserDao userDao;
	
	@RequestMapping(value= "/in")
	@ResponseBody
	@ApiOperation(value = "登录", notes = "")
	@ApiImplicitParams({
		@ApiImplicitParam(paramType="query", name = "userName", value = "用户名", required = false, dataType = "VARCHAR"),
		@ApiImplicitParam(paramType="query", name = "userPass", value = "密码", required = false, dataType = "VARCHAR")
		})
	public Map<String, Object> login() throws Exception {
		String userName = "admin";
		String userPass = "123456";
		if (StringUtils.isNotBlank(userName) && StringUtils.isNotBlank(userPass)) {
			Map<String, Object> login = userDao.login(userName, userPass);
			return login;
		} else {
			Map<String, Object> returnMap = new HashMap<String, Object>();
			returnMap.put("code", 400);
			returnMap.put("msg", "用户名或密码都不能为空；");
			returnMap.put("success", "false");
			return returnMap;
		}
	}
	
	@RequestMapping(value= "/register")
	@ResponseBody
	public Map<String, Object> register() throws Exception {
		String userName = "admin";
		String userPass = "123456";
		Integer sex = 1;
		String sexName = "";
		switch (sex) {
		case 1:
			sexName = "男";
			break;
		case 2:
			sexName = "女";
			break;
		default:
			break;
		}
		String birthday = "2008-08-08 00:00:00";
		log.info("注冊用戶信息 》》》》》》》》》》》》》》》 ：{ userName: '" + userName + "', userPass: '" + userPass + "', sex: '" + sexName + "', birthday: '" + birthday + "' }");
		Map<String, Object> register = userDao.register(userName, userPass, sex, birthday);
		return register;
	}	

}
