package com.java.collection.activemq;

public class ProducterThread implements Runnable{
	
	Producter producter;
	public ProducterThread(Producter producter) {
		// TODO Auto-generated constructor stub
		this.producter = producter;
	}
	
	@Override
	public void run() {
		// TODO Auto-generated method stub
		//while(true) {
			try {
				producter.sendMessage("admin1");				
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}
		//}
	}
}
