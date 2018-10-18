package com.guoan.demo.vo;

/**
 * 学生类
 * @author fan
 *
 */
public class StudentVo {
	private long id;	//学生id
	private String name;//学生姓名
	private Integer cId;//学生班级id
	private String className;//班级名称
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
	public String getClassName() {
		return className;
	}
	public void setClassName(String className) {
		this.className = className;
	}
} 
