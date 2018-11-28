package cn.edugate.esb.parallel;

import java.util.concurrent.Callable;
import java.util.concurrent.CountDownLatch;

public class EduTask implements Callable<EduTaskResult>{
	private final TaskItem taskItem;
    private final CountDownLatch threadsSignal;
    
    public EduTask(CountDownLatch threadsSignal,TaskItem taskItem) {
        this.threadsSignal= threadsSignal;
        this.taskItem=taskItem;
    }
    
    @Override
    public EduTaskResult call() throws Exception {
    	EduTaskResult result=new EduTaskResult(this.taskItem.getName());
        
        // 核心处理逻辑处理
        Thread.sleep(2000);
        
        System.out.println("task id:" + taskItem.getId() +" >>>>等待結束");
        System.out.println("task id:" + taskItem.getId() + " >>>>线程名称:" + Thread.currentThread().getName() + "结束. 还有" + threadsSignal.getCount() + " 个线程");

        // 必须等核心处理逻辑处理完成后才可以减1
        this.threadsSignal.countDown();
        
        return result;
    }
}
