package sng.pojo;

import java.math.BigInteger;

/**
 * @类 名： Counter
 * @功能描述：带金额统计的计数器 
 * @作者信息： LiuYang
 * @创建时间： 2018年12月5日上午9:49:30
 */
public class Counter  {

	/**
	 * 总数
	 */
	private BigInteger num;
	/**
	 * 总金额
	 */
	private Double money;
	/** 
	 * 总数  
	 */
	public BigInteger getNum() {
		return num;
	}
	/** 
	 * 总数  
	 */
	public void setNum(BigInteger num) {
		this.num = num;
	}
	/** 
	 * 总金额  
	 */
	public Double getMoney() {
		return money;
	}
	/** 
	 * 总金额  
	 */
	public void setMoney(Double money) {
		this.money = money;
	}
	
	
}
