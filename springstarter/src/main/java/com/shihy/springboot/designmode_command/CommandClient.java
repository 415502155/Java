package com.shihy.springboot.designmode_command;

import java.util.Date;

import com.shihy.springboot.constant.Constant;
import com.shihy.springboot.entity.User;
import com.shihy.springboot.utils.JsonUtils;

/***
 * 
 * @Title: springstarter
 * @author shy
 * @Description 命令模式测试
 * 1. 命令(Command)角色：声明了一个给所有具体命令类的抽象接口。
 * 2. 具体命令(ConcreteCommand)角色：定义一个接收者和行为之间的弱耦合；
 *    实现execute()方法，负责调用接收者的相应操作。execute()方法通常叫做执行方法。
 * 3. 接收者(Receiver)角色：负责具体实施和执行一个请求。
 *    任何一个类都可以成为接收者，实施和执行请求的方法叫做行动方法。
 * 4. 请求者(Invoker)角色：负责调用命令对象执行请求，相关的方法叫做行动方法。
 * 5. 客户端(Client)角色：创建一个具体命令(ConcreteCommand)对象并确定其接收者。
 * @data 2019年3月20日 上午10:49:20
 */
public class CommandClient {

	public static void main(String[] args) {
		User user = new User();
		user.setUser_id(10001);
		user.setUser_name("命令模式");
		user.setUser_pass("mingling");
		user.setSex(Constant.MAN_SEX);
		user.setBirthday(new Date());
		user.setIs_del(Constant.IS_DEL_NO);
		user.setUpdate_time(new Date());
		String data = JsonUtils.objectToJson(user);
		// 创建接受对象
		ConsumeCommand cc = new ConsumeCommand();
		// 创建命令对象(添加)
		Command command = new AddCommandConsume(cc);
		// 创建命令对象(修改)
		Command command1 = new UpdateCommandConsume(cc);
		// 创建请求者对象
		InvokerCommand invoker = new InvokerCommand();
		invoker.setAddCommandConsume(command);
		invoker.add(data);
		
		InvokerCommand invoker1 = new InvokerCommand();
		invoker1.setUpdateCommandConsume(command1);
		invoker1.update(data);
	}

}
