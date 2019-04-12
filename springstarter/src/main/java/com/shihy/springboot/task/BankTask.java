package com.shihy.springboot.task;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.locks.ReadWriteLock;

import com.shihy.springboot.constant.Constant;
import com.shihy.springboot.entity.MyCount;
import lombok.extern.slf4j.Slf4j;
/***
 * 
 * @Title: springstarter
 * @author shy
 * @Description 存取
 * @data 2019年3月26日 下午3:03:55
 *
 */
@SuppressWarnings("rawtypes")
@Slf4j
public class BankTask implements Callable {
	
	 private String name;            //用户名
     private MyCount myCount;        //所要操作的账户
     private BigDecimal cash;        //操作的金额
     private ReadWriteLock myLock;   //执行操作所需的锁对象
     private boolean ischeck;        //是否查询

	public BankTask(String name, MyCount myCount, BigDecimal cash, ReadWriteLock myLock, boolean ischeck) {
		super();
		this.name = name;
		this.myCount = myCount;
		this.cash = cash;
		this.myLock = myLock;
		this.ischeck = ischeck;
	}

	@Override
	public Object call() throws Exception {
		Map<String, Object> map = new HashMap<String, Object>(16);
		if (ischeck) {
			myLock.readLock().lock();
			log.info("用户" + name + "正在查询" + myCount + "账户，当前金额为 :" + myCount.getBalance());
			myLock.readLock().unlock();
		} else {
			myLock.writeLock().lock();
			log.info("用户操作前卡中信息 ：" + name + "正在查询" + myCount + "账户，当前金额为 :" + myCount.getBalance());
			String balance = myCount.getBalance().toString();
			//假如为取钱业务，需要判断当前账户余额是否充足
			BigDecimal drawMoney = myCount.getBalance().add(cash);
			int compare = drawMoney.compareTo(BigDecimal.ZERO);
			if (compare == Constant.NEGATIVE) {//余额不足
				map.put("CODE", Constant.NEGATIVE);
				map.put("MSG", "FAIL : not sufficient funds .");
				map.put("SUCCESS", false);
			} else {
				myCount.setBalance(myCount.getBalance().add(cash));
				log.info("操作银行卡(存钱/取钱) ：￥" + cash + "元   , 操作前余额为 ：$" + balance + "元   ," + name + "正在查询" + myCount + "账户，当前金额为 :" + myCount.getBalance());
				myLock.writeLock().unlock();
				map.put("CODE", 0);
				map.put("MSG", "SUCCESS");
				map.put("SUCCESS", true);
			}
		}
		
		return map;
	}

}
