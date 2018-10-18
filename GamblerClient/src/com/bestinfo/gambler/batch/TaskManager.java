package com.bestinfo.gambler.batch;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import org.apache.log4j.Logger;

/**
 * 线程池管理
 */
public final class TaskManager {

    private static final Logger logger = Logger.getLogger(TaskManager.class);
    private static TaskManager taskManager = null;
    private final static int MAX_THREAD = 10000;//最大线程
    private final static int COREPOOLSIZE = 1000;//核心线程数

    /**
     * 线程池对象
     */
    private ThreadPoolExecutor threadPool = new ThreadPoolExecutor(COREPOOLSIZE, MAX_THREAD, 0, TimeUnit.SECONDS, new ArrayBlockingQueue<Runnable>(MAX_THREAD), new ThreadPoolExecutor.CallerRunsPolicy());

    /**
     * 构造函数
     */
    private TaskManager() {
    }

    /**
     * 返回 TaskManager 实例
     *
     * @return TaskManager
     */
    public static TaskManager getInstance() {
        if (taskManager == null) {
            taskManager = new TaskManager();
        }
        return taskManager;
    }

    /**
     * 任务程序执行
     *
     * @param task 任务程序
     */
    synchronized public void execute(Runnable task) {
        if (task == null) {
            return;
        }
        logger.info("任务程序执行");
        threadPool.execute(task);
        logger.info("任务程序执行完了吗玩了吗");
    }

    /**
     * 得到当前线程数
     *
     * @return integer
     */
    public int getThreadNum() {
        return threadPool.getActiveCount();
    }

    /*
     * 关闭线程池
     */
    public void threadPoolShutDown() {
        threadPool.shutdown();
    }
}
