package com.bestinfo.appserver.manage;

import com.alibaba.rocketmq.client.consumer.DefaultMQPushConsumer;
import com.alibaba.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import com.alibaba.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import com.alibaba.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import com.alibaba.rocketmq.client.exception.MQClientException;
import com.alibaba.rocketmq.common.consumer.ConsumeFromWhere;
import com.alibaba.rocketmq.common.message.Message;
import com.alibaba.rocketmq.common.message.MessageExt;
import com.bestinfo.appserver.initialize.InitializeBean;
import com.bestinfo.bean.h5.monitor.RequestModel;
import com.bestinfo.define.h5.monitor.InitializeData;
import java.io.ByteArrayInputStream;
import java.io.ObjectInputStream;
import java.util.List;
import javax.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Controller;

@Controller
public class WorkerConsumer {

    private static final Logger logger = Logger.getLogger(WorkerConsumer.class);
    private static final DefaultMQPushConsumer consumer = new DefaultMQPushConsumer("PushConsumer");

    private static WorkerConsumer instance;

    public static WorkerConsumer getInstance() {
        if (instance == null) {
            instance = new WorkerConsumer();
        }
        return instance;
    }

    public boolean startDeal() {
        boolean bRet = true;
        try {
            consumer.setNamesrvAddr(InitializeData.queueServerAddress);

            consumer.subscribe(InitializeData.queueTopic, "");
            consumer.setConsumeFromWhere(ConsumeFromWhere.CONSUME_FROM_FIRST_OFFSET);
            consumer.registerMessageListener(
                    new MessageListenerConcurrently() {
                        @Override
                        public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> list, ConsumeConcurrentlyContext Context) {
                            Message msg = list.get(0);
                            try {
                                ApplicationContext ctx = InitializeBean.getApplicationContext();
                                ThreadPoolTaskExecutor taskExecutor = (ThreadPoolTaskExecutor) ctx.getBean("taskExecutor");

                                ByteArrayInputStream byteArray = new ByteArrayInputStream(msg.getBody());
                                ObjectInputStream objInput = new ObjectInputStream(byteArray);
                                RequestModel request = (RequestModel) objInput.readObject();

                                if (request != null) {
                                    String sKey = request.getResponseKey();
                                    HttpServletResponse response = ResponsePool.getInstance().getResponse(sKey);
                                    if (response != null) {
                                        if (taskExecutor.getActiveCount() < taskExecutor.getMaxPoolSize()) {
                                            taskExecutor.execute(new WorkerProcess(request, response));
                                        } else {
                                            return ConsumeConcurrentlyStatus.RECONSUME_LATER;
                                        }
                                    } else {
                                        logger.error("Get response from pool failed!");
                                    }
                                } else {
                                    logger.error("Read request from queue failed!");
                                }
                            } catch (Exception e) {
                                logger.error("Get request object error,error info(" + e.getMessage() + ")!");
                                return ConsumeConcurrentlyStatus.RECONSUME_LATER;
                            }
                            return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
                        }
                    }
            );
            consumer.start();
        } catch (MQClientException e) {
            logger.error("Start queue error,error info(" + e.getMessage() + ")!");
            bRet = false;
        }
        return bRet;
    }

    public void stopDeal() {
        consumer.shutdown();
    }
}
