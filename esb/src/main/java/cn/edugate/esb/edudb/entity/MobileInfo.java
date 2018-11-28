package cn.edugate.esb.edudb.entity;

import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name ="jxt_mobile_info")
public class MobileInfo {
	private String mobile;	
	private String mobile_type;	
	private String mobile_service;
	private String mobile_code;
	private Date inserttime;	
	private String mobile_300_code;
	
	public String getMobile_300_code() {
		return mobile_300_code;
	}
	public void setMobile_300_code(String mobile_300_code) {
		this.mobile_300_code = mobile_300_code;
	}
	@Id
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public String getMobile_type() {
		return mobile_type;
	}
	public void setMobile_type(String mobile_type) {
		this.mobile_type = mobile_type;
	}
	public String getMobile_service() {
		return mobile_service;
	}
	public void setMobile_service(String mobile_service) {
		this.mobile_service = mobile_service;
	}
	public String getMobile_code() {
		return mobile_code;
	}
	public void setMobile_code(String mobile_code) {
		this.mobile_code = mobile_code;
	}
	public Date getInserttime() {
		return inserttime;
	}
	public void setInserttime(Date inserttime) {
		this.inserttime = inserttime;
	}
		
}
