package com.shy.springboot.mq;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.shy.springboot.utils.Constant;

/***
 * @Title: springstarter
 * @author shy
 * @Description direct队列
 *
 */
@Configuration
public class DirectConfig {
	/*********************************test_direct_queue**************************************/
	@Bean
	public DirectExchange getDirectExchange() {
		return new DirectExchange("test_direct_exchange", true, true);
	} 
	@Bean
	public Queue testDirectQueue() {
		Queue queue = new Queue("test_direct_queue", true);
		return queue;
	} 
	@Bean
	public Binding testDirectBinding() {
		return BindingBuilder.bind(testDirectQueue()).to(getDirectExchange()).with("test_direct_queue");
	} 
	
	/*********************************direct_queue_a**************************************/
	
	@Bean
	public DirectExchange getDirectExchangeA() {
		return new DirectExchange(Constant.DIRECT_EXCHANGE_A, true, true);
	}
	
	@Bean
	public Queue getDirectQueueA() {
		return new Queue(Constant.DIRECT_QUEUE_A, true);
	}
	
	@Bean
	public Binding directBindingA() {
		return BindingBuilder.bind(getDirectQueueA()).to(getDirectExchangeA()).with(Constant.DIRECT_QUEUE_A);
	}
}
