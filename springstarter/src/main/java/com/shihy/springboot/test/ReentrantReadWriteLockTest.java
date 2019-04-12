package com.shihy.springboot.test;

import java.math.BigDecimal;
/***
 * @author sjwy-0001
 */
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import com.shihy.springboot.entity.MyCount;
import com.shihy.springboot.task.BankTask;

import lombok.extern.slf4j.Slf4j;

/***
 * 
 * @Title: springstarter
 * @author shy
 * @Description TODO
 * @data 2019年3月27日 下午3:05:54
 *
 */
@Slf4j
public class ReentrantReadWriteLockTest {
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static void main(String[] args) throws InterruptedException, ExecutionException {

		// 账户余额
		MyCount myCount = new MyCount();
		myCount.setBalance(new BigDecimal(10000));
		myCount.setCardNumber("100086110120119");

		// 查询余额参数为true 存取操作参数为false
		ReadWriteLock lock = new ReentrantReadWriteLock(false);

		// Executors.newCachedThreadPool();
		ExecutorService pool = new ThreadPoolExecutor(0, Integer.MAX_VALUE, 60L, TimeUnit.SECONDS,
				new SynchronousQueue<Runnable>(), new ThreadPoolExecutor.DiscardOldestPolicy());
		String[] name = { "张三", "张三的父亲", "张三的母亲", "张三的大儿子", "张三的二儿子", "张三的三儿子", "张三的四儿子", "张三的五儿子", "张三的六儿子",
				"张三的七儿子" };
		String[] operateBalance = { "-20000", "8000", "-1000", "500", "500", "-1000", "1000", "2000", "3000",
				"-10000" };

		for (int i = 0; i < name.length; i++) {
			BankTask bankTask = new BankTask(name[i], myCount, new BigDecimal(operateBalance[i]), lock, false);
			Future future = pool.submit(bankTask);
			Map<String, Object> returnMap = (Map<String, Object>) future.get();
			log.info("Thread " + i + ": " + name[i] + "操作结果  result : { CODE :" + returnMap.get("CODE") + " MSG :"
					+ returnMap.get("MSG") + " SUCCESS :" + returnMap.get("SUCCESS") + "};");
		}
		pool.shutdown();
	}
}
