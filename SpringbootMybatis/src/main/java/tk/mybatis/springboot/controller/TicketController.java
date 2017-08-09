package tk.mybatis.springboot.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;

import freemarker.core.ReturnInstruction.Return;
import tk.mybatis.springboot.mapper.TicketMapper;
import tk.mybatis.springboot.model.TicketInfo;
import tk.mybatis.springboot.util.ReturnMsg;

@RestController
@RequestMapping(value="/ticket")
public class TicketController {
	
	private static final Logger log = LoggerFactory.getLogger(TicketController.class);
	@Autowired
	TicketMapper ticketMapper;
	
	/***
	 * @author Administrator
	 * add ticket
	 * @return
	 */
	@RequestMapping(value="/add100")
	@ResponseBody
	public JSONObject addTicket() {
		JSONObject backJson = new JSONObject();
		List<TicketInfo> ticketInfos = new ArrayList<TicketInfo>();
		int sno = 2017081100;
		for(int i=0;i<100;i++) {
			TicketInfo t1 = new TicketInfo();
			t1.setT_id(20170809);
			t1.setTicket_money(49);
			t1.setTicket_name("WAR WOLF 2");
			t1.setTicket_no(sno+i);
			t1.setTicket_type(0);//0代表未售出的票
			t1.setTicket_num(0);
			ticketInfos.add(t1);
		}
		int status = ticketMapper.addTicket(ticketInfos);
		log.info("ticket size:"+ticketInfos.size());
		backJson.put("CODE", 0);
		backJson.put("MSG", "success");
		backJson.put("STATUS", status);
		return backJson;
		
	}
	/***
	 * @author Administrator
	 * @deprecated sale ticket
	 * 
	 * 0.count ticket count = 0
	 * 1.change ticket type =1
	 * 2.--ticket_num
	 * @return
	 * @throws InterruptedException
	 * @throws ExecutionException
	 */
	@RequestMapping(value="/sale")
	@ResponseBody
	public JSONObject saleTicket() throws InterruptedException, ExecutionException {
		JSONObject backJson = new JSONObject();
		ThreadPoolExecutor threadPoolExecutor =new ThreadPoolExecutor(5, 100, 5000, TimeUnit.MILLISECONDS,new LinkedBlockingQueue<Runnable>(50), new ThreadPoolExecutor.AbortPolicy());// new ThreadPoolExecutor(5, 50, 5000, TimeUnit.MILLISECONDS, new LinkedBlockingQueue<Runable>(100), new ThreadPoolExecutor.AbortPolicy());
		for(int i=0;i<120;i++) {
			TicketTask thread = new TicketTask(i);
			Future future = threadPoolExecutor.submit(thread);
			int status = (int) future.get();
			backJson.put("STATUS"+i, status);
		}
		backJson.put("CODE", ReturnMsg.SUCCESS.getCode());
		backJson.put("MSG", ReturnMsg.SALE_TICKET_EXCEPTION.getMsg());
		log.info("BackJson: "+backJson);
		return backJson;
	}
}
