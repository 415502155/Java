package com.shihy.springboot.designmode_memento;
/***
 * 
 * @Title: springstarter
 * @author shy
 * @Description 备忘录测试
 * @data 2019年3月20日 下午2:50:15
 *
 */
public class MementoClient {

	public static void main(String[] args) {
		
		Originator originator = new Originator();
		Caretaker caretaker = new Caretaker();
		String state = "starting";
		originator.setState(state);
		System.out.println("初始化状态 ---------------------------------- ： " + originator.getState());
		Memento createMemento = originator.createMemento();
		caretaker.saveMemento(createMemento);
		originator.setState("ending");
		System.out.println("更改后状态 ---------------------------------- ： " + originator.getState());
		originator.restoreMemento(caretaker.retrieveMemento());
		System.out.println("恢复后状态 ---------------------------------- ： " + originator.getState());
	}

}
