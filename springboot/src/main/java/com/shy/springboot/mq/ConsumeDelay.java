package com.shy.springboot.mq;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

import javax.annotation.Resource;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;
import com.shy.springboot.dao.UserDao;
import com.shy.springboot.entity.User;
import com.shy.springboot.utils.Constant;
import lombok.extern.slf4j.Slf4j;
@Slf4j
@Component
//@RabbitListener(queues = "delayqueue")
public class ConsumeDelay {
	
	@Resource
	private UserDao userDao;
	
	//@RabbitHandler
	@RabbitListener(queues = Constant.DELAY_UPDATE_USER_QUEUE)
    public void process(User user) throws ParseException, IOException{
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		user.setUser_id(1);
		int update = userDao.update(user);
        log.info("更新用户信息，更新时间为 :" + sdf.format(new Date()));
    }
	
	@RabbitListener(queues = Constant.DELAY_TEST_QUEUE)
    public void processTest(Map<String, Object> map) throws ParseException{
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        log.info("消息接受时间》》》》》》》》》》》》》》》》》》》》》》》》》》》》》》》》》》》》》》》》》》》》》》》》》》》》》：" + sdf.format(new Date()));
        log.info("接受的信息》》》》》》》》》》》》》》》》》》》》》》》》》》》》》》》》》》》》》》》》》》》》》》》》》》》》》》：" + map.get("msg"));
	}
}
