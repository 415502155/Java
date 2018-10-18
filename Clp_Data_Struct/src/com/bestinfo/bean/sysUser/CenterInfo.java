/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bestinfo.bean.sysUser;

import java.io.Serializable;

/**
 * 中心信息 包括 省 市 县 以及投注机信息
 *
 * @author lvchangrong
 */
public class CenterInfo implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8926257469091474772L;
	private String name; // 中心名称
	private String address; // 中心地址
	private String link_phone; // 中心联系电话

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name
	 *            the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the address
	 */
	public String getAddress() {
		return address;
	}

	/**
	 * @param address
	 *            the address to set
	 */
	public void setAddress(String address) {
		this.address = address;
	}

	/**
	 * @return the link_phone
	 */
	public String getLink_phone() {
		return link_phone;
	}

	/**
	 * @param link_phone
	 *            the link_phone to set
	 */
	public void setLink_phone(String link_phone) {
		this.link_phone = link_phone;
	}

}
