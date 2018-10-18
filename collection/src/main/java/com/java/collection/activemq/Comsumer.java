package com.java.collection.activemq;

import java.util.concurrent.atomic.AtomicInteger;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.JMSException;
import javax.jms.MessageConsumer;
import javax.jms.Queue;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQConnectionFactory;

/**
 * @author Administrator
 * 消费者
 *
 * @2018年1月19日
 */
public class Comsumer {
	
	private static final String USERNAME = ActiveMQConnection.DEFAULT_USER;
	private static final String PASSWORD = ActiveMQConnection.DEFAULT_PASSWORD;
	private static final String BROKRN_URL = ActiveMQConnection.DEFAULT_BROKER_URL;
	
	ConnectionFactory connectionFactory;
	
	Connection connection;
	
	Session session;
	
	ThreadLocal<MessageConsumer> threadLocal = new ThreadLocal<MessageConsumer>();
	
	AtomicInteger count = new AtomicInteger();
	
	public void init() {
		
		try {
			connectionFactory = new ActiveMQConnectionFactory(USERNAME, PASSWORD, BROKRN_URL);
			connection = connectionFactory.createConnection();
			connection.start();
			session = connection.createSession(true, Session.SESSION_TRANSACTED);
		} catch (JMSException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public void getMessage(String disname) {
		
		try {
			Queue queue = session.createQueue(disname);
			MessageConsumer messageConsumer;
			if(threadLocal.get() != null) {
				messageConsumer = threadLocal.get();
			}else {
				messageConsumer = session.createConsumer(queue);
				threadLocal.set(messageConsumer);
			}
			while(true) {
				Thread.sleep(1000);
				TextMessage textMessage = (TextMessage) messageConsumer.receive();			
				if(textMessage != null) {
					textMessage.acknowledge();
					System.out.println(Thread.currentThread().getName()+": Consumer:我是消费者，我正在消费Msg"+
					textMessage.getText()+"--->"+count.getAndIncrement());
				}
				else {
					break;
				}
			}
			
		} catch (JMSException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
