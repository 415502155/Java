package com.shy.springboot.entity;

import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;
@Data
public class Employee implements Serializable {

	private static final long serialVersionUID = 2728723895834786255L;
	
	private Integer emp_id;
	private String emp_name;
	private Integer age;
	@JsonFormat(pattern="yyyy-MM-dd",locale="zh",timezone="GMT+8")
	private Date birthday;
	private Integer sex;
	@JsonFormat(pattern="yyyy-MM-dd",locale="zh",timezone="GMT+8")
	private Date hiredate;
	@JsonFormat(pattern="yyyy-MM-dd",locale="zh",timezone="GMT+8")
	private Date departure_time;
	private String address;
	private String salary;
	private String emp_no;
	private Integer dept_id;
	private Integer is_del;
	@JsonFormat(pattern="yyyy-MM-dd hh:mm:ss",locale="zh",timezone="GMT+8")
	private Date insert_time;
	@JsonFormat(pattern="yyyy-MM-dd hh:mm:ss",locale="zh",timezone="GMT+8")
	private Date update_time;
	@JsonFormat(pattern="yyyy-MM-dd hh:mm:ss",locale="zh",timezone="GMT+8")
	private Date del_time;

}
