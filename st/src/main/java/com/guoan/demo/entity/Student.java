package com.guoan.demo.entity;

/**
 * 学生类
 * @author fan
 *
 */
public class Student {
	private long id;	//学生id
	private String name;//学生姓名
	private Integer cId;//学生班级id
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
	public Integer getcId() {
		return cId;
	}
	public void setcId(Integer cId) {
		this.cId = cId;
	}
	public Boolean getIsAvailable() {
		return isAvailable;
	}
	public void setIsAvailable(Boolean isAvailable) {
		this.isAvailable = isAvailable;
	}
	
	
} 
