package com.java.collection.activemq;

import org.apache.log4j.Logger;

public class Test {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Logger logger = Logger.getLogger(Test.class);
		Producter producter = new Producter();
		producter.init();
		logger.info("producter init ......");
		System.out.println("producter init ......");
		Thread t1 = new Thread(new ProducterThread(producter));
//		Thread t2 = new Thread(new ProducterThread(producter));
//		Thread t3 = new Thread(new ProducterThread(producter));
//		Thread t4 = new Thread(new ProducterThread(producter));
//		Thread t5 = new Thread(new ProducterThread(producter));
		t1.start();
		logger.info("t1 starting ......");
		System.out.println("t1 starting ......");
//		t2.start();
//		t3.start();
//		t4.start();
//		t5.start();
		
//		Comsumer comsumer = new Comsumer();
//		comsumer.init();
//		
//		Thread t11 = new Thread(new ComsumerThread(comsumer));
//		Thread t22 = new Thread(new ComsumerThread(comsumer));
//		Thread t33 = new Thread(new ComsumerThread(comsumer));
//		Thread t44 = new Thread(new ComsumerThread(comsumer));
//		t11.start();
//		t22.start();
//		t33.start();
//		t44.start();
		
	}

}


