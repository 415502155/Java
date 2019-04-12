package com.shihy.springboot.designmode_command;
/***
 * 
 * @Title: springstarter
 * @author shy
 * @Description TODO
 * @data 2019年3月27日 下午3:30:35
 *
 */
public class InvokerCommand {
	
	private Command addCommandConsume;
	
	private Command updateCommandConsume;
	
	public void setAddCommandConsume(Command addCommandConsume) {
		this.addCommandConsume = addCommandConsume;
	}
	
	public void setUpdateCommandConsume(Command updateCommandConsume) {
		this.updateCommandConsume = updateCommandConsume;
	}
	
	public void add(String data) {
		addCommandConsume.execute(data);
	}
	
	public void update(String data) {
		updateCommandConsume.execute(data);
	}

}
