package sng.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 规则表 
 * Title: Rulelist 
 * Description: 规则详细信息表 
 * Company: 世纪伟业
 */
@Entity
@Table(name = "rulelist")
public class Rulelist implements Serializable {

	private static final long serialVersionUID = -1240534923446197481L;

	/**
	 * 规则表主键
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer rule_id;
	/**
	 * 机构主键
	 */
	private Integer org_id;
	/**
	 * 缴费有效时间
	 */
	private Integer payment_period;
	/***
	 * 退费审核开关（0:开；1：关）
	 */
	private Integer audit_switch;
	/***
	 * 退费说明
	 */
	private String refund_instructions;
	/***
	 * 认证须知
	 */
	private String certification_requirements;
	/***
	 * 认证方式（0: 线上；1：线下）
	 */
	private Integer authentication_method;
	/***
	 * 新增时间
	 */
	private Date insert_time;
	/***
	 * 修改时间
	 */
	private Date update_time;
	/***
	 * 删除时间
	 */
	private Date del_time;
	/***
	 * 是否删除 (0:否；1：是)
	 */
	private Integer is_del;

	public Integer getRule_id() {
		return rule_id;
	}

	public void setRule_id(Integer rule_id) {
		this.rule_id = rule_id;
	}

	public Integer getOrg_id() {
		return org_id;
	}

	public void setOrg_id(Integer org_id) {
		this.org_id = org_id;
	}

	public Integer getPayment_period() {
		return payment_period;
	}

	public void setPayment_period(Integer payment_period) {
		this.payment_period = payment_period;
	}

	public Integer getAudit_switch() {
		return audit_switch;
	}

	public void setAudit_switch(Integer audit_switch) {
		this.audit_switch = audit_switch;
	}

	public String getRefund_instructions() {
		return refund_instructions;
	}

	public void setRefund_instructions(String refund_instructions) {
		this.refund_instructions = refund_instructions;
	}

	public String getCertification_requirements() {
		return certification_requirements;
	}

	public void setCertification_requirements(String certification_requirements) {
		this.certification_requirements = certification_requirements;
	}

	public Integer getAuthentication_method() {
		return authentication_method;
	}

	public void setAuthentication_method(Integer authentication_method) {
		this.authentication_method = authentication_method;
	}

	public Date getInsert_time() {
		return insert_time;
	}

	public void setInsert_time(Date insert_time) {
		this.insert_time = insert_time;
	}

	public Date getUpdate_time() {
		return update_time;
	}

	public void setUpdate_time(Date update_time) {
		this.update_time = update_time;
	}

	public Date getDel_time() {
		return del_time;
	}

	public void setDel_time(Date del_time) {
		this.del_time = del_time;
	}

	public Integer getIs_del() {
		return is_del;
	}

	public void setIs_del(Integer is_del) {
		this.is_del = is_del;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

}
