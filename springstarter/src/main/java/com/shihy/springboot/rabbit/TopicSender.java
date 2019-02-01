package com.shihy.springboot.rabbit;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/***
 * 
 * @author sjwy-0001
 *
 */
@Component
public class TopicSender {

    @Autowired
    private AmqpTemplate rabbitTemplate;

    public void send(){
        String context = "Hi i am message all";
        System.out.println("Sender: " + context);
        this.rabbitTemplate.convertAndSend("topicExchange", "topic.1", context);
    }

    public void send1(){
        String context = "Hi i am message 1";
        System.out.println("Sender: " + context);
        this.rabbitTemplate.convertAndSend("topicExchange", "topic.message", context);
    }

    public void send2(){
        String context = "Hi i am message 2";
        System.out.println("Sender: " + context);
        this.rabbitTemplate.convertAndSend("topicExchange", "topic.messages", context);
    }
}
