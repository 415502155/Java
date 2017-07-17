package com.guoan.demo.entity;

/**
 * 班级类
 * @author fan
 *
 */

public class Class {

	private long id;	//班级id
	private String name;//班级名称
	private Boolean isAvailable;//是否可用
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Boolean getIsAvailable() {
		return isAvailable;
	}
	public void setIsAvailable(Boolean isAvailable) {
		this.isAvailable = isAvailable;
	}
	
} 
