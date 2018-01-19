package com.java.collection.activemq;

public class ComsumerThread implements Runnable{
	
	Comsumer comsumer;
	public ComsumerThread(Comsumer comsumer) {
		// TODO Auto-generated constructor stub
		this.comsumer = comsumer;
	}
	
	@Override
	public void run() {
		// TODO Auto-generated method stub
		while(true) {
			try {
				comsumer.getMessage("mq_shy");				
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}
		}
	}
}
