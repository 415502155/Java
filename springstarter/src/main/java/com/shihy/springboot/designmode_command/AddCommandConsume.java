package com.shihy.springboot.designmode_command;

import lombok.extern.slf4j.Slf4j;
/***
 * 
 * @Title: springstarter
 * @author shy
 * @Description TODO
 * @data 2019年3月27日 下午3:30:35
 *
 */
@Slf4j
public class AddCommandConsume implements Command {

	private ConsumeCommand consume;

	public AddCommandConsume(ConsumeCommand consume) {
		this.consume = consume;
	}

	@Override
	public void execute(String data) {
		log.info("param ------------------------------- : " + data);
		consume.add(data);
		/*List<User> list = JsonUtils.jsonToList(data, User.class);
		if (list != null && list.size() > 0) {
			User user = list.get(0);
			log.info("user ----------------------------- :" + JsonUtils.objectToJson(user));
			consume.add(user);
		}*/
	}

}
