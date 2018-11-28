package sharding.entity;

import java.math.BigInteger;
import java.sql.Timestamp;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name = "sng_notice_detail")
public class NoticeDetail {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private BigInteger id;
	
	private BigInteger notice_id;

	private Integer org_id;
	
	/*
	 * 发送类型：0-学生通知；1-教师通知
	 */
	private Integer type;

	/**
	 * 人员id（org_user表id）
	 */
	private Integer user_id;
	
	/**
	 * 当类型为学生通知时填写所在班级ID
	 */
	private Integer class_id;
	
	/**
	 * 类型为教师通知时填写教师所在部门id
	 */
	private Integer dept_id;
	
	/**
	 * 状态：0-待发送，1-已撤回，2-发送成功，3-发送失败
	 */
	private Integer status;
	
	private Timestamp insert_time;
	
	private Timestamp update_time;
	
	private Integer is_del;
	
	private Timestamp delete_time;

	
	
	/*@Transient
	private BigInteger ORDER_BY_DERIVED_0;
	
	@Transient
	private Date GROUP_BY_DERIVED_0;*/
	
	@Transient
	private String id_str;
	
	

	public BigInteger getId() {
		return id;
	}

	public void setId(BigInteger id) {
		this.id = id;
	}

	public BigInteger getNotice_id() {
		return notice_id;
	}

	public void setNotice_id(BigInteger notice_id) {
		this.notice_id = notice_id;
	}

	public Integer getOrg_id() {
		return org_id;
	}

	public void setOrg_id(Integer org_id) {
		this.org_id = org_id;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public Integer getUser_id() {
		return user_id;
	}

	public void setUser_id(Integer user_id) {
		this.user_id = user_id;
	}

	public Integer getClass_id() {
		return class_id;
	}

	public void setClass_id(Integer class_id) {
		this.class_id = class_id;
	}

	public Integer getDept_id() {
		return dept_id;
	}

	public void setDept_id(Integer dept_id) {
		this.dept_id = dept_id;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Timestamp getInsert_time() {
		return insert_time;
	}

	public void setInsert_time(Timestamp insert_time) {
		this.insert_time = insert_time;
	}

	public Timestamp getUpdate_time() {
		return update_time;
	}

	public void setUpdate_time(Timestamp update_time) {
		this.update_time = update_time;
	}

	public Integer getIs_del() {
		return is_del;
	}

	public void setIs_del(Integer is_del) {
		this.is_del = is_del;
	}

	public Timestamp getDelete_time() {
		return delete_time;
	}

	public void setDelete_time(Timestamp delete_time) {
		this.delete_time = delete_time;
	}


	/*public BigInteger getORDER_BY_DERIVED_0() {
		return ORDER_BY_DERIVED_0;
	}

	public void setORDER_BY_DERIVED_0(BigInteger oRDER_BY_DERIVED_0) {
		ORDER_BY_DERIVED_0 = oRDER_BY_DERIVED_0;
	}
	
	public Date getGROUP_BY_DERIVED_0() {
		return GROUP_BY_DERIVED_0;
	}

	public void setGROUP_BY_DERIVED_0(Date gROUP_BY_DERIVED_0) {
		GROUP_BY_DERIVED_0 = gROUP_BY_DERIVED_0;
	}*/

	
	public String getId_str() {
		if (this.id != null && this.id.longValue() > 0) {
			return id.toString();
		} else {
			return "";
		}
	}

}
