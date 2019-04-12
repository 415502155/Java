package com.shihy.springboot.designmode_proxy;
/***
 * 
 * @Title: springstarter
 * @author shy
 * @Description TODO
 * @data 2019年3月27日 下午3:30:35
 *
 */
public class ProxyObject extends TargetObject {

	RealObject realObject = new RealObject();
	
	@Override
	public void query(String param) {
		realObject.query(param);
	}

	@Override
	public void insert(String param) {
		realObject.insert(param);
	}

	@Override
	public void update(String param) {
		realObject.update(param);	
	}

}
