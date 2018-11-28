package sng.pojo.base;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name ="edugate_base.wx_config")
public class WxConfig {
	private Integer org_id;
	private String app_id;
	private String app_secret;
	private String app_aeskey;
	private String access_token;
	private String jsapi_ticket;
	
	@Id
	public Integer getOrg_id() {
		return org_id;
	}
	public void setOrg_id(Integer org_id) {
		this.org_id = org_id;
	}
	public String getApp_id() {
		return app_id;
	}
	public void setApp_id(String app_id) {
		this.app_id = app_id;
	}
	public String getApp_secret() {
		return app_secret;
	}
	public void setApp_secret(String app_secret) {
		this.app_secret = app_secret;
	}
	public String getApp_aeskey() {
		return app_aeskey;
	}
	public void setApp_aeskey(String app_aeskey) {
		this.app_aeskey = app_aeskey;
	}
	public String getAccess_token() {
		return access_token;
	}
	public void setAccess_token(String access_token) {
		this.access_token = access_token;
	}
	public String getJsapi_ticket() {
		return jsapi_ticket;
	}
	public void setJsapi_ticket(String jsapi_ticket) {
		this.jsapi_ticket = jsapi_ticket;
	}
}
