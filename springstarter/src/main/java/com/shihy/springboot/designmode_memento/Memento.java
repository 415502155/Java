package com.shihy.springboot.designmode_memento;
/***
 * 
 * @Title: springstarter
 * @author shy
 * @Description 备忘录角色类，备忘录对象将发起人对象传入的状态存储起来。
 * @data 2019年3月20日 下午2:00:48
 *
 */
public class Memento {
	
	private String state;
	
	public Memento(String state) {
		this.state = state;
	}

	/**
	 * @return the state
	 */
	public String getState() {
		return state;
	}

	/**
	 * @param state the state to set
	 */
	public void setState(String state) {
		this.state = state;
	}
	
	

}
