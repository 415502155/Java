package com.shihy.springboot.designmode_command;

/***
 * @author sjwy-0001
 * @Description: 执行方法  
 * @return void  
 * @throws @throws
 * @date 2019年3月20日
 */
public interface Command {
	
	public void execute(String data);
}
