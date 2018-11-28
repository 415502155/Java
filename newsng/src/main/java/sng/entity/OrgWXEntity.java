package sng.entity;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "org_wx_tab")
public class OrgWXEntity {

	@Id
	private Integer org_id;

	private String app_id;

	private String app_secret;

	private String access_token;

	private Date access_token_expires_time;

	private String jsapi_ticket;

	private Date jsapi_ticket_expires_time;
	
	private String notice_mod;
	private String notice_mod_no;
	private String pay_mod;
	private String pay_mod_no;
	private String homework_mod;
	private String homework_mod_no;
	private String wages_mod;
	private String wages_mod_no;
	private String charge_remind_mod;
	private String charge_remind_no;

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

	public String getAccess_token() {
		return access_token;
	}

	public void setAccess_token(String access_token) {
		this.access_token = access_token;
	}

	public Date getAccess_token_expires_time() {
		return access_token_expires_time;
	}

	public void setAccess_token_expires_time(Date access_token_expires_time) {
		this.access_token_expires_time = access_token_expires_time;
	}

	public String getJsapi_ticket() {
		return jsapi_ticket;
	}

	public void setJsapi_ticket(String jsapi_ticket) {
		this.jsapi_ticket = jsapi_ticket;
	}

	public Date getJsapi_ticket_expires_time() {
		return jsapi_ticket_expires_time;
	}

	public void setJsapi_ticket_expires_time(Date jsapi_ticket_expires_time) {
		this.jsapi_ticket_expires_time = jsapi_ticket_expires_time;
	}

	public String getNotice_mod() {
		return notice_mod;
	}

	public void setNotice_mod(String notice_mod) {
		this.notice_mod = notice_mod;
	}

	public String getNotice_mod_no() {
		return notice_mod_no;
	}

	public void setNotice_mod_no(String notice_mod_no) {
		this.notice_mod_no = notice_mod_no;
	}

	public String getPay_mod() {
		return pay_mod;
	}

	public void setPay_mod(String pay_mod) {
		this.pay_mod = pay_mod;
	}

	public String getPay_mod_no() {
		return pay_mod_no;
	}

	public void setPay_mod_no(String pay_mod_no) {
		this.pay_mod_no = pay_mod_no;
	}

	public String getHomework_mod() {
		return homework_mod;
	}

	public void setHomework_mod(String homework_mod) {
		this.homework_mod = homework_mod;
	}

	public String getHomework_mod_no() {
		return homework_mod_no;
	}

	public void setHomework_mod_no(String homework_mod_no) {
		this.homework_mod_no = homework_mod_no;
	}

	public String getWages_mod() {
		return wages_mod;
	}

	public void setWages_mod(String wages_mod) {
		this.wages_mod = wages_mod;
	}

	public String getWages_mod_no() {
		return wages_mod_no;
	}

	public void setWages_mod_no(String wages_mod_no) {
		this.wages_mod_no = wages_mod_no;
	}

	public String getCharge_remind_mod() {
		return charge_remind_mod;
	}

	public void setCharge_remind_mod(String charge_remind_mod) {
		this.charge_remind_mod = charge_remind_mod;
	}

	public String getCharge_remind_no() {
		return charge_remind_no;
	}

	public void setCharge_remind_no(String charge_remind_no) {
		this.charge_remind_no = charge_remind_no;
	}

}
