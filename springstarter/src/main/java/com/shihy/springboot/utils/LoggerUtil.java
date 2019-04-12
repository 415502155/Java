package com.shihy.springboot.utils;

import java.util.Date;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.shihy.springboot.constant.Constant;
import com.shihy.springboot.entity.Logger;
import com.shihy.springboot.service.LoggerService;

/***
 * 
 * @Title: springstarter
 * @author shy
 * @Description 保存操作日志
 * @data 2019年2月15日 下午2:41:29
 *
 */
@Component
public class LoggerUtil {
	@Autowired
	private LoggerService loggerService;
	private static LoggerUtil logUtil;
    @PostConstruct 
	public void init() {       
		 logUtil= this; 
		 logUtil.loggerService= this.loggerService; 
	}
    
    public static void save(Integer targetId, Integer targetType, String targetName, Integer action,
			String content, Integer userType, Integer userId, String userName) {
    	Logger logger = new Logger();
    	logger.setTarget_id(targetId);
    	logger.setTarget_type(targetType);
    	logger.setTarget_name(targetName);
    	logger.setAction(action);
    	logger.setContent(content);
    	logger.setUser_id(userId);
    	logger.setUser_type(userType);
    	logger.setUser_name(userName);
    	logger.setInsert_time(new Date());
    	logger.setUpdate_time(new Date());
    	logger.setIs_del(Constant.IS_DEL_NO);
		logUtil.loggerService.save(logger);
	}
}
