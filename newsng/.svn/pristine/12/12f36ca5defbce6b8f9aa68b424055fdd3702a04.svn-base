package sng.util;

import java.util.Map;

import javax.annotation.Resource;

import org.springframework.amqp.AmqpException;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessagePostProcessor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

@Component
public class RabbitmqUtils {
	@Resource
	private RabbitTemplate rabbitTemplate;
	
	/**
	 * rabbitmq消息发送
	 * @param exchange
	 * @param routingKey
	 * @param message
	 */
	public void sendMessage(String exchange,String routingKey,Map<String,Object> message) {
		rabbitTemplate.convertAndSend(exchange,routingKey,message);
	}
	
	/**
	 * rabbitmq消息发送
	 * @param exchange
	 * @param routingKey
	 * @param message
	 * @param delay 延迟发送的毫秒数
	 */
	public void sendMessage(String exchange,String routingKey,Map<String,Object> message,final int delay) {
		rabbitTemplate.convertAndSend(exchange,routingKey,message,new MessagePostProcessor() {
			@Override
			public Message postProcessMessage(Message message) throws AmqpException {
				message.getMessageProperties().setDelay(delay);
				return message;
			}
		});
	}
}
