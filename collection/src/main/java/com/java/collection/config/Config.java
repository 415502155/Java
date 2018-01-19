package com.java.collection.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

//加上注释@Component，可以直接在其他地方使用@Autowired来创建其实例对象  
/**
 * @author Administrator
 * 属性文件实体
 *
 * @2018年1月17日
 */
@Component  
@ConfigurationProperties(prefix = "config",locations = "classpath:/config/config.properties") 
public class Config {

	private String url;
	private String host;
	private String username;
	private String password;

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}
	
	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	
}
