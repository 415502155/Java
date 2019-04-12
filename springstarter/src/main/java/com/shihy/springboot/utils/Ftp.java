package com.shihy.springboot.utils;

import lombok.Data;

/***
 * 
 * @Title: springstarter
 * @author shy
 * @Description ftp连接常量
 * @data 2019年2月18日 下午4:18:05
 *
 */
@Data
public class Ftp {
	private String ipAddr;
	private Integer port;
	private String userName;
	private String pwd;
	private String path;
	
}
