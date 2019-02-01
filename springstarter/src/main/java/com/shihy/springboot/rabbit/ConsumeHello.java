package com.shihy.springboot.rabbit;

import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/***
 * 
 * @author sjwy-0001
 *
 */
@Component
@RabbitListener(queues = "hello")
public class ConsumeHello {


    @RabbitHandler
    public void process(String hello){
        System.out.println("hello = [" + hello + "]");
    }
}
