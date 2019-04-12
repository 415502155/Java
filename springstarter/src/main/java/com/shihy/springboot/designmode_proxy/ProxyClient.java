package com.shihy.springboot.designmode_proxy;
/***
 * 
 * @Title: springstarter
 * @author shy
 * @Description TODO
 * @data 2019年3月27日 下午3:30:35
 *
 */
public class ProxyClient {
	
	public static void main(String[] args) {
		TargetObject targetObject = new ProxyObject();
		targetObject.deprecated("123");
		
		targetObject.query("1");
	}

}
