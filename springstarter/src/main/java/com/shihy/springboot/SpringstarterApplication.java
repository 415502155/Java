package com.shihy.springboot;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
/***
 * 
 * @Title: springstarter
 * @author shy
 * @Description TODO
 * @data 2019年1月14日 上午11:32:50
 *
 */
@SpringBootApplication
@MapperScan("com.shihy.springboot.dao")
@EnableScheduling
@EnableEurekaClient
//@EnableWebSecurity//启用web安全
public class SpringstarterApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringstarterApplication.class, args);
	}

	/***
	 * 
	 * @Description: 默认情况下 TaskScheduler 的 poolSize = 1
	 * @param @return   
	 * @return TaskScheduler  
	 * @throws
	 * @author shy
	 * @date 2019年1月14日
	 */
	@Bean
	public TaskScheduler taskScheduler() {
		ThreadPoolTaskScheduler taskScheduler = new ThreadPoolTaskScheduler();
		taskScheduler.setPoolSize(10);
		return taskScheduler;
	}
}

