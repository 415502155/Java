package com.shihy.springboot.designmode_memento;
/***
 * 负责人
 * @Title: springstarter
 * @author shy
 * @Description TODO
 * @data 2019年3月20日 下午2:09:48
 *
 */
public class Caretaker {
	
	private Memento memento;
	
	public Memento retrieveMemento() {
		return this.memento;
	}
	
	public void saveMemento(Memento memento) {
		this.memento = memento;
	}
}
