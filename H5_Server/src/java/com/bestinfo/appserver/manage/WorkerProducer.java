package com.bestinfo.appserver.manage;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.rocketmq.client.exception.MQClientException;
import com.alibaba.rocketmq.client.producer.DefaultMQProducer;
import com.alibaba.rocketmq.client.producer.SendResult;
import com.alibaba.rocketmq.client.producer.SendStatus;
import com.alibaba.rocketmq.common.message.Message;
import com.bestinfo.bean.h5.monitor.RequestModel;
import com.bestinfo.bean.h5.monitor.Session;
import com.bestinfo.define.h5.monitor.InitializeData;
import org.apache.log4j.Logger;
import java.io.ByteArrayOutputStream;
import java.io.ObjectOutputStream;
import javax.servlet.http.HttpServletResponse;

public class WorkerProducer {

    private static final Logger logger = Logger.getLogger(WorkerProducer.class);
    private static DefaultMQProducer producer = new DefaultMQProducer("Producer");

    private static WorkerProducer instance;

    public static WorkerProducer getInstance() {
        if (instance == null) {
            instance = new WorkerProducer();
        }
        return instance;
    }

    public boolean startQueue() {
        boolean bRet = true;
        try {
            producer.setNamesrvAddr(InitializeData.queueServerAddress);
            producer.start();
        } catch (MQClientException e) {
            logger.error("Start queue error,error info(" + e.getMessage() + ")!");
            bRet = false;
        }
        return bRet;
    }

    public void stopQueue() {
        producer.shutdown();
    }

    synchronized public boolean addQueue(String sRequestIp, byte btEncryptType, int iCmdVersion, Session session, JSONArray jsonArray, HttpServletResponse response) {
        if (jsonArray == null || response == null) {
            return false;
        }

        if (!ResponsePool.getInstance().checkResponsePoolStatus()) {
            logger.error("Response pool is full!");
            return false;
        }
        String sKey = ResponsePool.getInstance().addResponse(response);
        if (sKey == null) {
            logger.error("Add response to pool failed!");
            return false;
        }

        RequestModel request = new RequestModel();
//        request.setRequestIp(sRequestIp);
//        request.setEncryptType(btEncryptType);
//        request.setCmdVersion(iCmdVersion);
        request.setJsonArray(jsonArray);
//        request.setSession(session);
//        request.setResponseKey(sKey);

        boolean bRet = true;
        try {
            ByteArrayOutputStream byteArray = new ByteArrayOutputStream();
            ObjectOutputStream ObjOutput = new ObjectOutputStream(byteArray);
            ObjOutput.writeObject(request);
            byte[] body = byteArray.toByteArray();

            Message msg = new Message(InitializeData.queueTopic, body);
            SendResult result = producer.send(msg);
            if (result.getSendStatus() != SendStatus.SEND_OK) {
                logger.error("Send message error,error info(" + result.getSendStatus() + ")!");
                bRet = false;
            }
        } catch (Exception e) {
            logger.error("Send message error,error info(" + e.getMessage() + ")!");
            bRet = false;
        }
        return bRet;
    }
}
