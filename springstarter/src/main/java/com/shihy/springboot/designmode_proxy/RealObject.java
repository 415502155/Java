package com.shihy.springboot.designmode_proxy;

import org.apache.commons.lang3.StringUtils;
/***
 * 
 * @Title: springstarter
 * @author shy
 * @Description TODO
 * @data 2019年3月27日 下午3:30:35
 *
 */
public class RealObject extends TargetObject {

	@Override
	public void query(String param) {
		if (StringUtils.isNotBlank(param)) {
			if ("1".equals(param)) {
				System.out.println("1111111111111");
			} else {
				System.out.println("0000000000000");
			}
		}
	}

	@Override
	public void insert(String param) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void update(String param) {
		// TODO Auto-generated method stub
		
	}
	
}
