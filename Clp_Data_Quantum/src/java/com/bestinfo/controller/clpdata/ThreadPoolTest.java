/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.bestinfo.controller.clpdata;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 *
 * @author Administrator
 */
public class ThreadPoolTest {
    public static void main(String[] args) throws ExecutionException {
        /*
         * 1. 创建线程池
         * 
         * 创建一个固定线程数目的线程池。corePoolSize = maximumPoolSize = 5
         * 即线程池的基本线程数和最大线程数相等。
         * 相当于：
         * new ThreadPoolExecutor(nThreads, nThreads, 0L, TimeUnit.MILLISECONDS,new LinkedBlockingQueue<Runnable>());
         */
        ExecutorService threadPool = Executors.newFixedThreadPool(5);
        /*
         *  2. 封装任务并提交给线程池
         */
        final ThreadPoolExecutor threadPoolExecutor = (ThreadPoolExecutor) threadPool;
        Runnable task = new Runnable() {
            @Override
            public void run() {
                System.out.println("Task is running by " + Thread.currentThread().getName());
                System.out.println("线程池正在执行的线程数：" + threadPoolExecutor.getActiveCount());
                try {
                    TimeUnit.SECONDS.sleep(10);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };
        /*
         * Starts all core threads, causing them to idly wait for work. 
         * This overrides the default policy of starting core threads 
         * only when new tasks are executed.
         */
        int count = threadPoolExecutor.prestartAllCoreThreads();
        System.out.println("开启的所有core线程数：" + count);
        System.out.println("线程池当前线程数：" + threadPoolExecutor.getPoolSize());
        System.out.println("线程池的core number of threads：" + threadPoolExecutor.getCorePoolSize());
        System.out.println("线程池中的最大线程数：" + threadPoolExecutor.getLargestPoolSize());
        // 3. 执行，获取返回结果
        /**
         * execute方式提交任务
         */
        // threadPool.execute(task);
        /**
         * submit方式提交任务
         */
        Future<?> future = threadPool.submit(task);
        try {
            // 阻塞，等待线程执行完成，并获得结果
            Object result = future.get();
            System.out.println("任务是否完成：" + future.isDone());
            System.out.println("返回的结果为：" + result);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            System.out.println("线程池中已经执行完的任务数：" + threadPoolExecutor.getCompletedTaskCount());
            // 4. 关闭线程池
            /*
             * shutdown方法平滑地关闭线程池，将线程池的状态设为：SHUTDOWN
             * 停止接受任何新的任务且等待已经提交的任务执行完成,当所有已经
             * 提交的任务执行完毕后将会关闭线程池
             */
            threadPool.shutdown();
            /*
             *  shutdownNow方法强制关闭线程池，将线程池状态设置为：STOP
             *  取消所有运行中的任务和在工作队列中等待的任务，并返回所有未执行的任务List
             */
            // hreadPool.shutdownNow();
            System.out.println("线程池是否关闭：" + threadPool.isShutdown());
            try {
                //当前线程阻塞10ms后，去检测线程池是否终止，终止则返回true
                while(!threadPool.awaitTermination(10, TimeUnit.MILLISECONDS)) {
                    System.out.println("检测线程池是否终止：" + threadPool.isTerminated());
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("线程池是否终止：" + threadPool.isTerminated());
        }
    }
}
