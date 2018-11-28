package cn.edugate.esb.entity;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
@Entity
@Table(name ="user_account",catalog="`edugate_base`")
public class UserAccount{
	// 用户平台关系主键
	private Integer ua_id;
	// 中心用户id
	private String target_id;
	// 机构用户id
	private Integer type;	
	// 平台标识
	private Integer version;	
	// 设备唯一标识
	private String udid;	
	//当前机构用户id
	private Integer user_id;	
	//当前选择机构
	private Integer org_id;	
	// 创建时间
	private Date created_time;
	// 更新时间
	private Date updated_time;
	
	private String services;
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	public Integer getUa_id() {
		return ua_id;
	}
	public void setUa_id(Integer ua_id) {
		this.ua_id = ua_id;
	}
	public String getTarget_id() {
		return target_id;
	}
	public void setTarget_id(String target_id) {
		this.target_id = target_id;
	}
	public Integer getType() {
		return type;
	}
	public void setType(Integer type) {
		this.type = type;
	}
	public Integer getVersion() {
		return version;
	}
	public void setVersion(Integer version) {
		this.version = version;
	}
	public String getUdid() {
		return udid;
	}
	public void setUdid(String udid) {
		this.udid = udid;
	}
	public Date getCreated_time() {
		return created_time;
	}
	public void setCreated_time(Date created_time) {
		this.created_time = created_time;
	}
	public Date getUpdated_time() {
		return updated_time;
	}
	public void setUpdated_time(Date updated_time) {
		this.updated_time = updated_time;
	}
	public Integer getUser_id() {
		return user_id;
	}
	public void setUser_id(Integer user_id) {
		this.user_id = user_id;
	}
	public Integer getOrg_id() {
		return org_id;
	}
	public void setOrg_id(Integer org_id) {
		this.org_id = org_id;
	}
	public String getServices() {
		return services;
	}
	public void setServices(String services) {
		this.services = services;
	}
	
}

