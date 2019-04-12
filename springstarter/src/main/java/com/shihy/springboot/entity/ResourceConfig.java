package com.shihy.springboot.entity;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
/***
 * 
 * @Title: springstarter
 * @author shy
 * @Description TODO
 * @data 2019年3月27日 下午3:30:35
 *
 */
@Configuration
@ConfigurationProperties(prefix = "springstarter")
@PropertySource(value = "classpath:resource.properties")
public class ResourceConfig {
	
	private String username;
	
	private Integer usersex;
	
	private String usermobile;
	
	private String useraddress;

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public Integer getUsersex() {
		return usersex;
	}

	public void setUsersex(Integer usersex) {
		this.usersex = usersex;
	}

	public String getUsermobile() {
		return usermobile;
	}

	public void setUsermobile(String usermobile) {
		this.usermobile = usermobile;
	}

	public String getUseraddress() {
		return useraddress;
	}

	public void setUseraddress(String useraddress) {
		this.useraddress = useraddress;
	}

	@Override
	public String toString() {
		return "Resource [username=" + username + ", usersex=" + usersex + ", usermobile=" + usermobile
				+ ", useraddress=" + useraddress + "]";
	}
	
}
