package com.shy.springboot.mq;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSONObject;
import com.shy.springboot.dao.UserDao;
import com.shy.springboot.entity.User;
import com.shy.springboot.utils.Constant;

import lombok.extern.slf4j.Slf4j;
@Slf4j
@Component
public class ConsumeDirect {
	
	@Resource
	private UserDao userDao;
	
	@RabbitListener(queues = "test_direct_queue")
    public void processDirectMessage(String message) throws ParseException{
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        log.info("消息接受时间》》》》》》》》》》》》》》》》》》》》》》》》》》》》》》》》》》》》》》》》》》》》》》》》》》》》》：" + sdf.format(new Date()));
        log.info("已消费的信息》》》》》》》》》》》》》》》》》》》》》》》》》》》》》》》》》》》》》》》》》》》》》》》》》》》》》》：" + message);
	}
	
	@RabbitListener(queues = Constant.DIRECT_QUEUE_A)
    public void processDirectAMessage(String message) throws ParseException{
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        log.info("消息接受时间》》》》》》》》》》》》》》》》》》》》》》》》》》》》》》》》》》》》》》》》》》》》》》》》》》》》》：" + sdf.format(new Date()));
        List<User> userListByName = userDao.getUserListByName(message);
        if (userListByName != null && userListByName.size() > 0) {
        	log.info("获取列表信息 》》》》》》》》》》》》》》》》》》》》》》》》》》》》》》》》》》》》》》》》》》》》》》》》》》》：" + JSONObject.toJSONString(userListByName));
        } else {
        	log.info("获取列表信息为空；");
		}
	}
}
