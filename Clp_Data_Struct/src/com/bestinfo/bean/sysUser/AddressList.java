package com.bestinfo.bean.sysUser;

import java.io.Serializable;

/**
 * 地址列表
 *
 * @author hhhh
 */
public class AddressList implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8632122650130049611L;
	
	private Integer app_id; // 应用编号
	private String app_name;// 应用名称
	private String app_url;// 应用地址
	private Integer app_type; // 应用类型
	private Integer center_type; // 中心类型
	private Integer work_status; // 使用状态

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

	public String getApp_url() {
		return app_url;
	}

	public void setApp_url(String app_url) {
		this.app_url = app_url;
	}

	public Integer getApp_type() {
		return app_type;
	}

	public void setApp_type(Integer app_type) {
		this.app_type = app_type;
	}

	public Integer getCenter_type() {
		return center_type;
	}

	public void setCenter_type(Integer center_type) {
		this.center_type = center_type;
	}

	public Integer getWork_status() {
		return work_status;
	}

	public void setWork_status(Integer work_status) {
		this.work_status = work_status;
	}

}
