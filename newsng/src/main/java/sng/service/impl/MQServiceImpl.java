package sng.service.impl;

import org.springframework.amqp.AmqpException;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessagePostProcessor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import sng.service.MQService;
import sng.util.PropertyReader;

/**
 * @类 名： MQServiceImpl
 * @功能描述：调用MQ的接口实现类 
 * @作者信息： LiuYang
 * @创建时间： 2018年12月21日上午11:26:18
 */
@Component
@Transactional("transactionManager")
public class MQServiceImpl implements MQService{

	@Autowired
	private RabbitTemplate rabbitTemplate;
	
	private static final String item = PropertyReader.getCommonProperty("item");
	private static final String switchs = PropertyReader.getCommonProperty("switch");
	private static final String custom = PropertyReader.getCommonProperty("custom");

	/**
	 * 调用MQ消息队列
	 * @param mqName 消息队列名称，自动添加配置文件config.properties里的前后缀，前缀${item}_,后缀_${switch}${custom}
	 */
	@Override
	public void sendMessage(String mqName, String routingKey, Object object) {
		rabbitTemplate.convertAndSend(item+"_"+mqName+"_"+switchs+custom, routingKey, object);
	}

	/**
	 * 调用MQ消息队列
	 * @param delay 延迟毫秒数
	 * @param mqName 消息队列名称，自动添加配置文件config.properties里的前后缀，前缀${item}_,后缀_${switch}${custom}
	 */
	@Override
	public void sendMessage(String mqName, String routingKey, Object object,final int delay) {
		rabbitTemplate.convertAndSend(item+"_"+mqName+"_"+switchs+custom,routingKey,object,new MessagePostProcessor() {
			@Override
			public Message postProcessMessage(Message message) throws AmqpException {
				message.getMessageProperties().setDelay(delay);
				return message;
			}
		});
	}
}
