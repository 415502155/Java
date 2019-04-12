package com.shihy.springboot.executor;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
/***
 * 
 * @Title: springstarter
 * @author shy
 * @Description 线程池
 * @data 2019年1月24日 下午3:13:30
 *
 */
@Configuration
@PropertySource({"classpath:config.properties"})
public class ThreadPoolConfig {
	
	@Value("${CORE_POOL_SIZE}")
	private String CORE_POOL_SIZE;
	
	@Bean
    public ExecutorService getExecutorTools(){
		/***
		 * 创建一个指定工作线程数量的线程池。每当提交一个任务就创建一个工作线程，如果工作线程数量达到线程池初始的最大数，则将提交的任务存入到池队列中。
		 */
        //ExecutorService executorService = Executors.newFixedThreadPool(Integer.parseInt(CORE_POOL_SIZE));//固定线程池
		/***
		 * 创建一个单线程化的Executor，即只创建唯一的工作者线程来执行任务，如果这个线程异常结束，会有另一个取代它，保证顺序执行(我觉得这点是它的特色)。单工作线程最大的特点是可保证顺序地执行各个任务，并且在任意给定的时间不会有多个线程是活动的 。
		 */
		//ExecutorService executorService = Executors.newSingleThreadExecutor();
		/***
		 * 创建缓存线程池
		 * 工作线程的创建数量几乎没有限制(其实也有限制的,数目为Interger. MAX_VALUE), 这样可灵活的往线程池中添加线程。 
		 * 如果长时间没有往线程池中提交任务，即如果工作线程空闲了指定的时间(默认为1分钟)，则该工作线程将自动终止。终止后，如果你又提交了新的任务，则线程池重新创建一个工作线程。
		 */
		ExecutorService executorService = new ThreadPoolExecutor(0, Integer.MAX_VALUE,
                60L, TimeUnit.SECONDS, new SynchronousQueue<Runnable>(), new ThreadPoolExecutor.DiscardOldestPolicy() );
		/***
		 * 创建一个定长的线程池，而且支持定时的以及周期性的任务执行，类似于Timer。
		 */
		//ExecutorService executorService = Executors.newScheduledThreadPool(Integer.parseInt(CORE_POOL_SIZE));
		return  executorService;
    }
}
