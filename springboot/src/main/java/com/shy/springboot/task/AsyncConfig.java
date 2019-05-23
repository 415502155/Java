package com.shy.springboot.task;

import java.util.concurrent.Executor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
//表明该类是一个配置类 
@Configuration
//开启异步事件的支持
@EnableAsync
public class AsyncConfig {
	
	@Value("${CORE_POOL_SIZE}")
	private String corePoolSize;
	@Value("${MAX_POOL_SIZE}")
	private String maxPoolSize;
	@Value("${QUEUE_CAPACITY}")
	private String queueCapacity;
	
	@Bean("task")
	public Executor taskExecutor() {
		ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
		executor.setCorePoolSize(Integer.parseInt(corePoolSize));
		executor.setMaxPoolSize(Integer.parseInt(maxPoolSize));
		executor.setQueueCapacity(Integer.parseInt(queueCapacity));
		executor.initialize();
		return executor;
	}

}
