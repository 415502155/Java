package sng.pojo;

public class SessionInfo {
	private Integer userId;//用户id
	private Integer orgId;//机构id
	private Integer identity;//用户身份 1教师 0家长 2学生
	private String loginName;//登录者用户名
	private String openId;
	
	
	
	public String getOpenId() {
		return openId;
	}
	public void setOpenId(String openId) {
		this.openId = openId;
	}
	public String getLoginName() {
		return loginName;
	}
	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}
	public Integer getUserId() {
		return userId;
	}
	public void setUserId(Integer userId) {
		this.userId = userId;
	}
	public Integer getOrgId() {
		return orgId;
	}
	public void setOrgId(Integer orgId) {
		this.orgId = orgId;
	}
	public Integer getIdentity() {
		return identity;
	}
	public void setIdentity(Integer identity) {
		this.identity = identity;
	}
}
