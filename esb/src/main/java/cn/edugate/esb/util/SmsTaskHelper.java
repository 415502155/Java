package cn.edugate.esb.util;

import java.util.concurrent.Executors;
import javax.annotation.PostConstruct;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import cn.edugate.esb.disruptor.SmsTask;
import cn.edugate.esb.disruptor.SmsTaskEvent;
import cn.edugate.esb.disruptor.SmsTaskEventHandler;
import com.lmax.disruptor.BlockingWaitStrategy;
import com.lmax.disruptor.IgnoreExceptionHandler;
import com.lmax.disruptor.RingBuffer;
import com.lmax.disruptor.Sequence;
import com.lmax.disruptor.WorkerPool;
import com.lmax.disruptor.dsl.ProducerType;

public class SmsTaskHelper {
	private static Logger logger = Logger.getLogger(SmsTaskHelper.class);	
    /**
     * ringBuffer的容量，必须是2的N次方
     */
//    private static final int BUFFER_SIZE = 1024*1024;
    private RingBuffer<SmsTaskEvent> ringBuffer;
//    private SequenceBarrier sequenceBarrier;
    private SmsTaskEventHandler smsTaskEventHandler;
    
    @Autowired
	public void setSmsTaskEventHandler(SmsTaskEventHandler smsTaskEventHandler) {
		this.smsTaskEventHandler = smsTaskEventHandler;
	}

	@PostConstruct  
    public void  init() {
    	ringBuffer = RingBuffer.create(ProducerType.MULTI,         //定义一个ringBuffer,也就是相当于一个队列  
    			SmsTaskEvent.EVENT_FACTORY, 1 << 10, new BlockingWaitStrategy()); 
        //
        //消费者
//        handler=new SmsTaskEventHandler();
//        LSHelper.processInjection(handler);
    	@SuppressWarnings("unchecked")
		WorkerPool<SmsTaskEvent> workerPool = new WorkerPool<SmsTaskEvent>(ringBuffer,  
				ringBuffer.newBarrier(), new IgnoreExceptionHandler(), smsTaskEventHandler);  
        //每个消费者，也就是 workProcessor都有一个sequence，表示上一个消费的位置,这个在初始化时都是-1  
        Sequence[] sequences = workerPool.getWorkerSequences();   
        //将其保存在ringBuffer中的 sequencer 中，在为生产申请slot时要用到,也就是在为生产者申请slot时不能大于此数组中的最小值,否则产生覆盖  
        ringBuffer.addGatingSequences(sequences);  
        workerPool.start(Executors.newFixedThreadPool(5));         //用executor 来启动 workProcessor 线程 
        logger.info("disruptor started ");
    }

    private void doProduce(SmsTask task){
        //获取下一个序号
        long sequence = ringBuffer.next();
        //写入数据
        ringBuffer.get(sequence).setSmsTask(task);
        //通知消费者该资源可以消费了
        ringBuffer.publish(sequence);
    }
    /**
     * 生产者压入生产数据
     * @param data
     */
    public void produce(SmsTask task){
        this.doProduce(task);
    }
}
