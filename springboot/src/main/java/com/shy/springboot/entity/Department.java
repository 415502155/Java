package com.shy.springboot.entity;

import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;
@Data
public class Department implements Serializable {

	private static final long serialVersionUID = 2728723895834786255L;	
	private Integer dept_id;
	private String dept_name;
	private Integer dept_num;
	private Integer is_del;
	@JsonFormat(pattern="yyyy-MM-dd hh:mm:ss",locale="zh",timezone="GMT+8")
	private Date insert_time;
	@JsonFormat(pattern="yyyy-MM-dd hh:mm:ss",locale="zh",timezone="GMT+8")
	private Date update_time;
	@JsonFormat(pattern="yyyy-MM-dd hh:mm:ss",locale="zh",timezone="GMT+8")
	private Date del_time;

}
