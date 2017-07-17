package com.gm.bean;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="t_d_pass_channel_data")
public class PassChannelDataBean implements Serializable{
	
	private static final long serialVersionUID = -8856070990001925100L;
		
	private long id;
//	private String p_id;
	private String mobile;
	private String province;
//	private String orderId;
//	private String operator;
//	private String price;
//	private String changeCode;
//	private String channel_id;
//	private String status;
//	private String type;
//	private String time;
//	private String oper_type;
//	private String order_type;
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
//	public String getP_id() {
//		return p_id;
//	}
//	public void setP_id(String p_id) {
//		this.p_id = p_id;
//	}
	
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public String getProvince() {
		return province;
	}
	public void setProvince(String province) {
	this.province = province;
}
//	public String getOrderId() {
//		return orderId;
//	}
//	public void setOrderId(String orderId) {
//		this.orderId = orderId;
//	}
//	public String getOperator() {
//		return operator;
//	}
//	public void setOperator(String operator) {
//		this.operator = operator;
//	}
//	public String getPrice() {
//		return price;
//	}
//	public void setPrice(String price) {
//		this.price = price;
//	}
//	public String getChangeCode() {
//		return changeCode;
//	}
//	public void setChangeCode(String changeCode) {
//		this.changeCode = changeCode;
//	}
//	public String getChannel_id() {
//		return channel_id;
//	}
//	public void setChannel_id(String channel_id) {
//		this.channel_id = channel_id;
//	}
//	public String getStatus() {
//		return status;
//	}
//	public void setStatus(String status) {
//		this.status = status;
//	}
//	public String getType() {
//		return type;
//	}
//	public void setType(String type) {
//		this.type = type;
//	}
//	public String getTime() {
//		return time;
//	}
//	public void setTime(String time) {
//		this.time = time;
//	}
//	public String getOper_type() {
//		return oper_type;
//	}
//	public void setOper_type(String oper_type) {
//		this.oper_type = oper_type;
//	}
//	public String getOrder_type() {
//		return order_type;
//	}
//	public void setOrder_type(String order_type) {
//		this.order_type = order_type;
//	}
}
