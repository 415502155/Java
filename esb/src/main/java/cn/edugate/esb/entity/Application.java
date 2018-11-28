package cn.edugate.esb.entity;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name ="edugate_base.application")
public class Application {
	// 应用ID
	private Integer app_id;
	// 应用名称
	private String app_name;
	// 应用标识
	private String app_key;
	
	// 应用密钥
	private String app_secret;
	
	// 编号
	private Integer code;
	// 后台地址
	private String back_url;
	// 接口路径
	private String interface_url;
	// 备注
	private String note;
	
	//删除标记
	private Byte is_del;
	//删除时间
	private Date del_time;
	//更新时间
	private Date update_time;
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	public Integer getApp_id() {
		return app_id;
	}
	public void setApp_id(Integer app_id) {
		this.app_id = app_id;
	}
	public String getApp_name() {
		return app_name;
	}
	public void setApp_name(String app_name) {
		this.app_name = app_name;
	}
	public Integer getCode() {
		return code;
	}
	public void setCode(Integer code) {
		this.code = code;
	}
	public String getBack_url() {
		return back_url;
	}
	public void setBack_url(String back_url) {
		this.back_url = back_url;
	}
	public String getInterface_url() {
		return interface_url;
	}
	public void setInterface_url(String interface_url) {
		this.interface_url = interface_url;
	}
	public String getNote() {
		return note;
	}
	public void setNote(String note) {
		this.note = note;
	}
	public String getApp_secret() {
		return app_secret;
	}
	public void setApp_secret(String app_secret) {
		this.app_secret = app_secret;
	}
	public String getApp_key() {
		return app_key;
	}
	public void setApp_key(String app_key) {
		this.app_key = app_key;
	}
	public Byte getIs_del() {
		return is_del;
	}
	public void setIs_del(Byte is_del) {
		this.is_del = is_del;
	}
	public Date getDel_time() {
		return del_time;
	}
	public void setDel_time(Date del_time) {
		this.del_time = del_time;
	}
	public Date getUpdate_time() {
		return update_time;
	}
	public void setUpdate_time(Date update_time) {
		this.update_time = update_time;
	}
}

