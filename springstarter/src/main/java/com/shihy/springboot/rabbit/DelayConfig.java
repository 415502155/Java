package com.shihy.springboot.rabbit;

import java.util.HashMap;
import java.util.Map;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.CustomExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.shihy.springboot.constant.Constant;
/***
 * @Title: springstarter
 * @author shy
 * @Description 延迟推送队列
 * @data 2019年4月11日 上午9:05:00
 *
 */
@Configuration
public class DelayConfig {
	@Bean
    public CustomExchange delayTimeOutExchange() {
        Map<String, Object> args = new HashMap<>();
        args.put(Constant.DELAY_ARGS_KEY, Constant.DELAY_ARGS_VALUE);
        return new CustomExchange(Constant.DELAY_ORDER_STATUS_EXCHANGE, Constant.X_DELAYED_MESSAGE,true, false,args);
    }
	/***
	 * 
	 * @Description: 创建持久队列 DELAY_ORDER_STATUS_QUEUE
	 * @param @return
	 * @return Queue  
	 * @throws @throws
	 */
    @Bean
    public Queue queue() {
        Queue queue = new Queue(Constant.DELAY_ORDER_STATUS_QUEUE, true);
        return queue;
    }

    @Bean
    public Binding binding() {
        return BindingBuilder.bind(queue()).to(delayTimeOutExchange()).with(Constant.DELAY_ORDER_STATUS_QUEUE).noargs();
    }
    
    
	@Bean
    public CustomExchange delayTestExchange() {
        Map<String, Object> args = new HashMap<>();
        args.put(Constant.DELAY_ARGS_KEY, Constant.DELAY_ARGS_VALUE);
        return new CustomExchange(Constant.DELAY_TEST_EXCHANGE, Constant.X_DELAYED_MESSAGE,true, false,args);
    }

    @Bean
    public Queue testQueue() {
        Queue queue = new Queue(Constant.DELAY_TEST_QUEUE, true);
        return queue;
    }

    @Bean
    public Binding testBinding() {
        return BindingBuilder.bind(testQueue()).to(delayTestExchange()).with(Constant.DELAY_TEST_QUEUE).noargs();
    }
}
