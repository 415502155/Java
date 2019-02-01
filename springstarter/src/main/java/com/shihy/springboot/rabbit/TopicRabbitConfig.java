package com.shihy.springboot.rabbit;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/***
 * 
 * @author sjwy-0001
 *
 */
@Configuration
public class TopicRabbitConfig {

    final static String message = "topic.message";
    final static String messages = "topic.messages";

    @Bean
    public Queue queueMessage(){
        return new Queue(message);
    }

    @Bean
    public Queue queueMessages(){
        return new Queue(messages);
    }

    @Bean
    TopicExchange exchange(){
        return new TopicExchange("topicExchange");
    }

    @Bean
    Binding bindingExchangeToMessage(Queue queueMessage, TopicExchange exchange){
        return BindingBuilder.bind(queueMessage).to(exchange).with("topic.message");
    }

    @Bean
    Binding bindingExchangeToMessages(Queue queueMessages, TopicExchange exchange){
        return BindingBuilder.bind(queueMessages).to(exchange).with("topic.#");
    }
}
