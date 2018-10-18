package com.java.collection.activemq;

import java.util.concurrent.atomic.AtomicInteger;


import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.JMSException;
import javax.jms.MessageProducer;
import javax.jms.Queue;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.log4j.Logger;

/**
 * @author Administrator
 * 生产者
 *
 * @2018年1月19日
 */
public class Producter {
	private static Logger logger = Logger.getLogger(Producter.class);
	
	//ActiveMq的默认用户名
	private static final String USERNAME = ActiveMQConnection.DEFAULT_USER;
	//ActiveMq的默认密码
	private static final String PASSWORD = ActiveMQConnection.DEFAULT_PASSWORD;
	//ActiveMq的链接地址
	private static final String BROKEN_URL = ActiveMQConnection.DEFAULT_BROKER_URL;
	
	AtomicInteger count = new AtomicInteger();
	//链接工厂
	ConnectionFactory  connfactory;
	//链接对象
	Connection conn;
	//事务管理
	Session session;
	ThreadLocal<MessageProducer> threadLocal = new ThreadLocal<MessageProducer>();
	
	public void init() {
		
		try {
			connfactory = new ActiveMQConnectionFactory(USERNAME, PASSWORD, BROKEN_URL);
			conn = connfactory.createConnection();
			conn.start();
			//创建事务
			session = conn.createSession(true, Session.SESSION_TRANSACTED);
		} catch (JMSException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	 public void sendMessage(String disname){
	        try {
	            //创建一个消息队列
	            Queue queue = session.createQueue(disname);
	            //消息生产者
	            MessageProducer messageProducer = null;
	            if(threadLocal.get() != null){
	                messageProducer = threadLocal.get();
	            }else{
	                messageProducer = session.createProducer(queue);
	                threadLocal.set(messageProducer);
	            }
	            logger.info("start send msg ......");
				for(int i=0; i<10; i++) {
					TextMessage textMessage = session.createTextMessage("producter msg :"+i);
					logger.info("shy_a :"+textMessage);
					System.out.println("shy_a :"+textMessage);
					messageProducer.send(textMessage);
					session.commit();
				}
//	           while(true){
//	                Thread.sleep(1000);
//	                int num = count.getAndIncrement();
//	                //创建一条消息
//	                TextMessage msg = session.createTextMessage(Thread.currentThread().getName()+
//	                        "productor:我是大帅哥，我现在正在生产东西！,count:"+num);
//	                System.out.println(Thread.currentThread().getName()+
//	                        "productor:我是大帅哥，我现在正在生产东西！,count:"+num);
//	                //发送消息
//	                messageProducer.send(msg);
//	                //提交事务
//	                session.commit();
//	            }
	        } catch (JMSException e) {
	            e.printStackTrace();
	        }
	    }
	 
}
