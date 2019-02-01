package com.shihy.springboot.rabbit;

import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/***
 * 
 * @author sjwy-0001
 *
 */
@Configuration
public class RabbitConfig {

    @Bean
    public Queue helloQueue(){
        return new Queue("hello");
    }

}
