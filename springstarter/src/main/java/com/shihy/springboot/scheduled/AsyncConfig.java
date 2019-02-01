package com.shihy.springboot.scheduled;

import java.util.concurrent.Executor;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import lombok.extern.slf4j.Slf4j;
/***
 * 
 * @Title: springstarter
 * @author shy
 * @Description TODO
 * @data 2019年1月14日 上午11:35:18
 *
 */
@Configuration
@EnableAsync//开启异步事件的支持
@PropertySource({"classpath:config.properties"})
@Slf4j
public class AsyncConfig {

	@Value("${CORE_POOL_SIZE}")
	private String CORE_POOL_SIZE;
	@Value("${MAX_POOL_SIZE}")
	private String MAX_POOL_SIZE;
	@Value("${QUEUE_CAPACITY}")
	private String QUEUE_CAPACITY;
	
	@Bean
	public Executor taskExecutor() {
		ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
		log.info("CORE_POOL_SIZE :" + CORE_POOL_SIZE);
		executor.setCorePoolSize(Integer.parseInt(CORE_POOL_SIZE));
		executor.setMaxPoolSize(Integer.parseInt(MAX_POOL_SIZE));
		executor.setQueueCapacity(Integer.parseInt(QUEUE_CAPACITY));
		executor.initialize();
		return executor;
	}
}
