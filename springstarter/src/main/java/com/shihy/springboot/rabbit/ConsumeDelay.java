package com.shihy.springboot.rabbit;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import com.rabbitmq.client.Channel;
import com.shihy.springboot.constant.Constant;
import com.shihy.springboot.dao.ChargeMapper;
import lombok.extern.slf4j.Slf4j;
@Slf4j
@Component
//@RabbitListener(queues = "delayqueue")
public class ConsumeDelay {
	
	@Resource
	private ChargeMapper chargeMapper;
	
	//@RabbitHandler
	@RabbitListener(queues = Constant.DELAY_ORDER_STATUS_QUEUE)
    public void process(Integer cdId) throws ParseException, IOException{
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		//byte[] body = msg.getBody();
		//log.info("Msg body :" + body.toString());
		chargeMapper.updatePayTimeOut(cdId);
		////确认消息成功消费  
		//channel.basicAck(msg.getMessageProperties().getDeliveryTag(), false);
        log.info("缴费时间超时，更改完成支付订单状态 时间为 :" + sdf.format(new Date()));
        log.info("消费成功延迟推送信息》》》》》》》》》》》》》》》》》》》》》》》》》》》》》》》》》》》》》》》》》》》》》》》》》》》》》" + cdId);
    }
	
	@RabbitListener(queues = Constant.DELAY_TEST_QUEUE)
    public void processTest(Map<String, Object> map) throws ParseException{
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        log.info("消息接受时间》》》》》》》》》》》》》》》》》》》》》》》》》》》》》》》》》》》》》》》》》》》》》》》》》》》》》：" + sdf.format(new Date()));
        log.info("接受的信息》》》》》》》》》》》》》》》》》》》》》》》》》》》》》》》》》》》》》》》》》》》》》》》》》》》》》》：" + map.get("msg"));
	}
}
