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
	public Map<String, Object> login() throws Exception {
		String userName = "admin";
		String userPass = "123456";
		Map<String, Object> login = userDao.login(userName, userPass);
		return login;
	}
	
	@RequestMapping(value= "/register")
	@ResponseBody
	public Map<String, Object> register() throws Exception {
		String userName = "admin";
		String userPass = "123456";
		Integer sex = 1;
		String birthday = "2008-08-08 00:00:00";
		log.info("注冊用戶信息 》》》》》》》》》》》》》》》 ：{ userName: " + userName);
		Map<String, Object> register = userDao.register(userName, userPass, sex, birthday);
		return register;
	}	

}
