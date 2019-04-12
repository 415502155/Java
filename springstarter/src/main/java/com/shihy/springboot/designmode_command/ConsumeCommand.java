package com.shihy.springboot.designmode_command;

import java.text.ParseException;
import java.util.Date;

import com.alibaba.fastjson.JSONObject;
import com.shihy.springboot.constant.Constant;
import com.shihy.springboot.entity.User;
import com.shihy.springboot.utils.JsonUtils;
import lombok.extern.slf4j.Slf4j;
/***
 * 
 * @Title: springstarter
 * @author shy
 * @Description TODO
 * @data 2019年3月27日 下午3:30:35
 *
 */
@Slf4j
public class ConsumeCommand {
	
	public void add(String data) {
		log.info("-----------------------add-------------------------");
		User user = new User();
		user.setUser_id(10001);
		user.setUser_name("命令模式");
		user.setUser_pass("mingling");
		user.setSex(Constant.MAN_SEX);
		user.setBirthday(new Date());
		user.setIs_del(Constant.IS_DEL_NO);
		user.setUpdate_time(new Date());
		log.info("add >>>>>>>>>>>>>> :" + data);
	}
	
	public void update(String data) throws ParseException {
		log.info("-------------------------update-----------------------");
		JSONObject jsonObject = JSONObject.parseObject(data);
		Integer userId = (Integer) jsonObject.get("user_id");
		String userName = (String) jsonObject.get("user_name");
		String userPass = (String) jsonObject.get("user_pass");
		Integer sex = (Integer) jsonObject.get("sex");
		Integer isDel = (Integer) jsonObject.get("is_del");
		User user = new User();
		user.setUser_id(userId);
		user.setUser_name(userName);
		user.setUser_pass(userPass);
		user.setSex(sex);
		user.setBirthday(new Date());
		user.setIs_del(isDel);
		user.setUpdate_time(new Date());
		log.info("update >>>>>>>>>>> :" + JsonUtils.objectToJson(user));
	}
	
	public void del(User user) {
		log.info("------------------------del------------------------");
	}

}
