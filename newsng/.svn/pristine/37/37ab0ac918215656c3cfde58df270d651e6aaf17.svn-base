package sng.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @类 名： CertifiationRecord
 * @功能描述：认证缴费记录表 
 * @作者信息： LiuYang
 * @创建时间： 2018年12月11日下午8:35:41
 */
@Entity
@Table(name = "certification_record")
public class CertifiationRecord implements Serializable{
	
	/**
	 *@Fields serialVersionUID : 
	 */
	private static final long serialVersionUID = -7754944570264804004L;
	/**
	 * 主键
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private	Integer	id;
	/**
	 * 注册用户表主键
	 */
	private	Integer	register_id;
	/**
	 * 订单编号
	 */
	private	String	order_id;
	/**
	 * 认证时间
	 */
	private	Date	time;
	/**
	 * 认证费
	 */
	private	String	txnAmt;
	/**
	 * 认证姓名
	 */
	private	String	name;
	/**
	 * 认证身份证号
	 */
	private	String	id_number;
	/** 
	 * 主键  
	 */
	public Integer getId() {
		return id;
	}
	/** 
	 * 主键  
	 */
	public void setId(Integer id) {
		this.id = id;
	}
	/** 
	 * 注册用户表主键  
	 */
	public Integer getRegister_id() {
		return register_id;
	}
	/** 
	 * 注册用户表主键  
	 */
	public void setRegister_id(Integer register_id) {
		this.register_id = register_id;
	}
	/** 
	 * 订单编号  
	 */
	public String getOrder_id() {
		return order_id;
	}
	/** 
	 * 订单编号  
	 */
	public void setOrder_id(String order_id) {
		this.order_id = order_id;
	}
	/** 
	 * 认证时间  
	 */
	public Date getTime() {
		return time;
	}
	/** 
	 * 认证时间  
	 */
	public void setTime(Date time) {
		this.time = time;
	}
	/** 
	 * 认证费  
	 */
	public String getTxnAmt() {
		return txnAmt;
	}
	/** 
	 * 认证费  
	 */
	public void setTxnAmt(String txnAmt) {
		this.txnAmt = txnAmt;
	}
	/** 
	 * 认证姓名  
	 */
	public String getName() {
		return name;
	}
	/** 
	 * 认证姓名  
	 */
	public void setName(String name) {
		this.name = name;
	}
	/** 
	 * 认证身份证号  
	 */
	public String getId_number() {
		return id_number;
	}
	/** 
	 * 认证身份证号  
	 */
	public void setId_number(String id_number) {
		this.id_number = id_number;
	}
	
	
}
