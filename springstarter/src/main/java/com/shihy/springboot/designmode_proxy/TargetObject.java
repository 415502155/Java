package com.shihy.springboot.designmode_proxy;
/***
 * 
 * @Title: springstarter
 * @author shy
 * @Description TODO
 * @data 2019年3月27日 下午3:30:35
 *
 */
public abstract class TargetObject {
	
	public abstract void query(String param);
	
	public abstract void insert(String param);
	
	public abstract void update(String param);
	
	public void deprecated(String param) {
		System.out.println("输出参数 >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>> :" + param);
	}

}
