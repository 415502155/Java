package com.shihy.springboot.designmode_command;

import java.text.ParseException;

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
public class UpdateCommandConsume implements Command {

	private ConsumeCommand consume;

	public UpdateCommandConsume(ConsumeCommand consume) {
		this.consume = consume;
	}

	@Override
	public void execute(String data) {
		log.info("param ------------------------------- : " + data);
		try {
			consume.update(data);
		} catch (ParseException e) {
			log.info("excute ParseException :" + e);
		}
	}
}
