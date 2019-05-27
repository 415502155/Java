package com.shy.springboot.mq;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/***
 * 
 * @author sjwy-0001
 *
 */
@Configuration
public class TestFanoutConfig {
	
    @Bean
    public Queue testMessage() {
        return new Queue("fanout.test");
    }
 

    @Bean
    FanoutExchange testExchange() {
        return new FanoutExchange("testFanoutExchange");
    }

    @Bean
    Binding testBindingExchangeA(Queue testMessage,FanoutExchange testExchange) {
        return BindingBuilder.bind(testMessage).to(testExchange);
    }
}
