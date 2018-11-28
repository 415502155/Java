package cn.edugate.esb.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name ="app")
public class App {
	// 应用ID
	private Integer a_id;
	
	// sso 应用名称
	private String name;
	
	// sso 应用标识
	private String key;
	
	// sso 应用首页
	private String index;	
	
	//sso 验证失败跳转页面
	private String login;
	
	//sso 应用登录结果处理页
	private String loginProcess;
	
	//sso 应用登出页面
	private String logout;
	
	//sso 应用设置会话页面
	private String setSession;
	
	private String sessionkey;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	public Integer getA_id() {
		return a_id;
	}

	public void setA_id(Integer a_id) {
		this.a_id = a_id;
	}

	@Column(name="`name`")
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Column(name="`key`")
	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	@Column(name="`index`")
	public String getIndex() {
		return index;
	}

	public void setIndex(String index) {
		this.index = index;
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getLoginProcess() {
		return loginProcess;
	}

	public void setLoginProcess(String loginProcess) {
		this.loginProcess = loginProcess;
	}

	public String getLogout() {
		return logout;
	}

	public void setLogout(String logout) {
		this.logout = logout;
	}

	public String getSetSession() {
		return setSession;
	}

	public void setSetSession(String setSession) {
		this.setSession = setSession;
	}

	public String getSessionkey() {
		return sessionkey;
	}

	public void setSessionkey(String sessionkey) {
		this.sessionkey = sessionkey;
	}	
	
}

