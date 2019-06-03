package com.shy.springboot.service.impl;

import java.util.Date;
import java.util.Map;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.alibaba.fastjson.JSONObject;
import com.shy.springboot.dao.UserDao;
import com.shy.springboot.entity.User;
import com.shy.springboot.service.ICommonService;
import com.shy.springboot.utils.CommonUtils;
import com.shy.springboot.utils.Constant;
import com.shy.springboot.utils.ReturnResult;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@Transactional
public class UserService implements ICommonService {

	@Resource
	private UserDao userDao;
	
	@Override
	public ReturnResult execute(JSONObject json) throws Exception {
		ReturnResult result = null;
		Integer type = (Integer) json.get("type");
		switch (type) {
        case 1:
        	result = getUserMapList(json);
        	log.info("根據type獲取到指定的service方法 ：getUserMapList();" );
            break;       
        case 2:
        	result = getUserInfo(json);
        	log.info("根據type獲取到指定的service方法 ：getUserInfo();" );
            break; 
        case 3:
        	result = addUser(json);
        	log.info("根據type獲取到指定的service方法 ：addUser();" );
            break; 
        case 4:
        	result = updateUser(json);
        	log.info("根據type獲取到指定的service方法 ：updateUser();" );
            break; 
    }
		return result;
	}
	
	private ReturnResult getUserMapList(JSONObject json) throws Exception {
		String name = json.getString("name");
		String startTime = json.getString("startTime");
		String endTime = json.getString("endTime");
		Integer page = json.getInteger("page");
		Integer pageSize = json.getInteger("pageSize");
		Map<String, Object> userMapList = userDao.getUserMapList(name, startTime, endTime, page, pageSize);
		return ReturnResult.success(userMapList);
	}
	
	private ReturnResult getUserInfo(JSONObject json) throws Exception {
		Integer id = json.getInteger("id");
		Map<String, Object> userInfo = userDao.getUserInfo(id);
		return ReturnResult.success(userInfo);
	}

	private ReturnResult addUser(JSONObject json) throws Exception {
		User user = new User();
		user.setUser_name(json.getString("userName"));
		user.setUser_pass(json.getString("userPass"));
		user.setSex(json.getInteger("sex"));
		user.setToken(json.getString("token"));
		user.setBirthday(CommonUtils.stringToDate(json.getString("birthday"), null));
		user.setCreate_time(new Date());
		user.setUpdate_time(new Date());
		user.setIs_del(Constant.IS_DEL_NO);
		int add = userDao.add(user);
		return ReturnResult.success(add);
	}
	
	private ReturnResult updateUser(JSONObject json) throws Exception {
		User user = new User();
		user.setUser_id(json.getInteger("userId"));
		user.setUser_name(json.getString("userName"));
		user.setUser_pass(json.getString("userPass"));
		user.setSex(json.getInteger("sex"));
		user.setToken(json.getString("token"));
		user.setBirthday(CommonUtils.stringToDate(json.getString("birthday"), null));
		user.setCreate_time(new Date());
		user.setUpdate_time(new Date());
		user.setIs_del(Constant.IS_DEL_NO);
		int update = userDao.update(user);
		return ReturnResult.success(update);
	}
}
