package com.shihy.springboot.designmode_memento;
/***
 * 发起人
 * @Title: springstarter
 * @author shy
 * @Description TODO
 * @data 2019年3月20日 下午1:58:24
 *
 */
public class Originator {
	
	private  String state;
	/***
	 * @Description: 工厂方法，返回新的备忘录对象
	 */
	public Memento createMemento() {
		return new Memento(state);
	}
	
	public void restoreMemento(Memento memento) {
		this.state = memento.getState();
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
