package com.shy.springboot.mq;

import java.text.ParseException;
import java.util.Date;
import javax.annotation.Resource;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;
/*import com.shihy.springboot.constant.Constant;
import com.shihy.springboot.dao.UserMapper;
import com.shihy.springboot.entity.User;*/
import lombok.extern.slf4j.Slf4j;

/***
 * 
 * @author sjwy-0001 消费 添加用户
 *
 */
@Slf4j
@Component
@RabbitListener(queues = "fanout.test")
public class ConsumeTestFanout {

	@RabbitHandler
	public void process(String message) throws ParseException {
		log.info("-----------------------------------ConsumeFanout  fanout.test-------------------------------------");
		log.info("消費（fanout.test）信息 ：" + message);
	}

	/*
	 * @Resource private UserMapper userMapper;
	 * 
	 * @RabbitHandler public void process(User user) throws ParseException{ log.
	 * info("-----------------------------------ConsumeFanout  add user-------------------------------------"
	 * ); user.setIs_del(Constant.IS_DEL_NO); user.setUpdate_time(new Date());
	 * user.setCreate_time(new Date()); userMapper.insert(user); }
	 */
	/*
	 * @RabbitHandler public void process(Map<String, Object> message) throws
	 * ParseException{ log.
	 * info("-----------------------------------ConsumeFanout  add user-------------------------------------"
	 * ); User user = new User(); user.setUser_name((String)
	 * message.get("user_name")); user.setUser_pass((String)
	 * message.get("user_pass")); user.setSex((Integer) message.get("sex")); String
	 * birthday = (String) message.get("birthday"); Date birthdayDate =
	 * CommonUtils.stringToDate(birthday, "yyyy-MM-dd");
	 * user.setBirthday(birthdayDate); user.setIs_del(Constant.IS_DEL_NO);
	 * user.setUpdate_time(new Date()); user.setCreate_time(new Date());
	 * userMapper.insert(user); }
	 */

	/*
	 * @RabbitHandler public void process(String message) throws ParseException{
	 * log.
	 * info("-----------------------------------ConsumeFanout  add user-------------------------------------"
	 * ); JSONObject messageObj = (JSONObject) JSON.parse(message); JSONObject
	 * messageJson = (JSONObject) messageObj.get("key"); User user = new User();
	 * user.setUser_name((String) messageJson.get("user_name"));
	 * user.setUser_pass((String) messageJson.get("user_pass"));
	 * user.setSex((Integer) messageJson.get("sex")); String birthday = (String)
	 * messageJson.get("birthday"); Date birthdayDate =
	 * CommonUtils.stringToDate(birthday, "yyyy-MM-dd");
	 * user.setBirthday(birthdayDate); user.setIs_del(Constant.IS_DEL_NO);
	 * user.setUpdate_time(new Date()); user.setCreate_time(new Date());
	 * userMapper.insert(user); }
	 */
}
