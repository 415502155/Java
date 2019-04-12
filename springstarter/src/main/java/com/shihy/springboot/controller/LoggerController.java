package com.shihy.springboot.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import javax.annotation.Resource;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.shihy.springboot.constant.Constant;
import com.shihy.springboot.entity.Logger;
import com.shihy.springboot.service.LoggerService;
import com.shihy.springboot.utils.CommonUtils;
import com.shihy.springboot.utils.LoggerMsg;
import com.shihy.springboot.utils.Page;
import com.shihy.springboot.utils.ReturnMsg;
import com.shihy.springboot.utils.ReturnResult;
import lombok.extern.slf4j.Slf4j;
/***
 * 
 * @Title: springstarter
 * @author shy
 * @Description 日志管理
 * @data 2019年3月28日 下午4:45:05
 *
 */
@Slf4j
@RestController
@RequestMapping(value = "/logger")
public class LoggerController {
	@Resource
	private LoggerService loggerService;

	@RequestMapping(value = "/insert")
	public ReturnResult insertLogger() {
		String randomStr = CommonUtils.getRandomStr(1, 3);
		String targetName = "学生";
		String userName = "administrator";
		Logger logger = new Logger();
		Integer targetId = 1;
		Integer userId = 1;
		String currentTime = CommonUtils.dateFormat(new Date(), null);
		String content = userName + "操作 {" + LoggerMsg.ACTION_ADD_USER.getMsg() + " : " + targetName + " }  操作时间为 ："
				+ currentTime;
		log.info("logger content :" + content);
		logger.setTarget_id(targetId);
		logger.setTarget_type(LoggerMsg.TARGET_TYPE_STUDENT.getCode());
		logger.setTarget_name(targetName + randomStr);
		logger.setUser_id(userId);
		logger.setUser_name(userName);
		logger.setUser_type(LoggerMsg.USER_TYPE_TEACHER.getCode());
		logger.setAction(LoggerMsg.ACTION_ADD_USER.getCode());
		logger.setContent(content + randomStr);
		logger.setInsert_time(new Date());
		logger.setUpdate_time(new Date());
		logger.setDel_time(new Date());
		logger.setIs_del(Constant.IS_DEL_NO);
		loggerService.save(logger);
		return ReturnResult.success();
	}

	@RequestMapping(value = "/page")
	public ReturnResult getLoggerList() {
		int page = 1;
		int size = 5;
		String serchName = "1";
		Integer action = 1;
		List<Logger> loggerList = loggerService.getLoggerList(serchName, action, page, size);
		Integer totalSize = loggerService.getLoggerTotalPage(serchName, action);
		Page pageInfo = new Page();
		return ReturnResult.success(totalSize, pageInfo.getTotalPage(totalSize, size), loggerList);
	}
	
	@RequestMapping(value = "/del")
	public ReturnResult delLoggerByIds(String ids) {
		if (StringUtils.isBlank(ids)) {
			return ReturnResult.error(ReturnMsg.PARAM_ERROR_EX1.getCode(), ReturnMsg.PARAM_ERROR_EX1.getMsg());
		}
		Integer res = loggerService.delLoggerByIdList(ids);
		return ReturnResult.success(res);
	}
	
	@RequestMapping(value = "/batchAdd")
	public ReturnResult batchAddLogger() {
		List<Logger> list = new ArrayList<Logger>();
		int length = 20;
		String targetName = "王五";
		Integer targetId = 1;
		Integer userId = 1;
		String userName = "Administrator";
		String content = "";
		//根据目标对象的类型来确定被操作对象的身份（学员、员工）
		String opeatorTime = CommonUtils.dateFormat(new Date(), null);
		for (int i = 0; i < length; i++) {
			Logger logger = new Logger();
			logger.setLog_id(null);
			logger.setTarget_id(targetId);
			logger.setTarget_type(LoggerMsg.USER_TYPE_PARENT.getCode());
			logger.setTarget_name(targetName + i);
			
			logger.setUser_id(userId);
			logger.setUser_type(LoggerMsg.USER_TYPE_TEACHER.getCode());
			logger.setUser_name(userName);
			
			logger.setAction(LoggerMsg.ACTION_ADD_USER.getCode());
			content = "{ 批量操作：" + LoggerMsg.ACTION_ADD_USER.getMsg() + "“" + targetName+i + "”" + " 操作人 ：" + userName + " 操作时间 ：" + opeatorTime + " }";
			logger.setContent(content);
			logger.setIs_del(Constant.IS_DEL_NO);
			
			logger.setInsert_time(new Date());
			logger.setUpdate_time(new Date());
			logger.setDel_time(null);
			list.add(logger);
		}
		String json = com.alibaba.fastjson.JSONObject.toJSONString(list);
		System.out.println("json :" + json);
		int res = loggerService.batchAddLogger(list);
		return ReturnResult.success(res);
	} 
	@RequestMapping(value = "/list")
	public ReturnResult getLoggerInfoList() {
		Page pageInfo = new Page();
		Integer page = 1;
		Integer size = 10;
		pageInfo.setPageNum(page);
		pageInfo.setPageSize(size);
		String targetName = "王五";
		Integer action = 1;
		List<Map<String, Object>> loggerInfoList = loggerService.getLoggerInfoList(targetName, action, pageInfo);
		return ReturnResult.success(loggerInfoList);
	}
}
