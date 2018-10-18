package tk.mybatis.springboot.controller;


import java.util.concurrent.Callable;

import org.apache.log4j.Logger;
import tk.mybatis.springboot.conf.InitializeBean;
import tk.mybatis.springboot.mapper.TicketMapper;

public class TicketTask implements Callable<Object>{
	Logger logger = Logger.getLogger(TicketTask.class);
	private int name;
	TicketMapper ticketMapper = (TicketMapper) InitializeBean.getBean("ticketMapper");
	@Override
	public Object call() throws Exception {
		// TODO Auto-generated method stub
		int tnum = ticketMapper.countTicket();
		if(tnum == 0) {
			logger.info("ticket is null!");
			return -666;
		}
		int status = sale();
		return status;
	}
	
	public synchronized int sale() {
		try {
			int s1 = ticketMapper.saleTicketByType();
			if(s1 != 1) {
				logger.info("update sale ticket by type is failed");
				return -666;
			}
			logger.info("sale by type num:"+s1);
			int s2 = ticketMapper.saleTicketByNum();
			logger.info("sale ticket num:"+s2);
		} catch (Exception e) {
			// TODO: handle exception
			logger.info("sale ex",e);
			return -666;
		}

		return 666;
	}
	
	public TicketTask(int name) {
		this.name = name;
	}
	
	public int getName() {
		return name;
	}

	public void setName(int name) {
		this.name = name;
	}



}
