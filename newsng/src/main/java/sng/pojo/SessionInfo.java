package sng.pojo;

public class SessionInfo {
	private Integer userId;//用户id
	private Integer orgId;//机构id
	private String token;
	private Integer camId;//校区id
	private Integer identity;//用户身份 1教师 0家长 2学生
	private Integer isAuth;//是否认证 1是0否
	private String loginName;//登录者用户名
	
	public String getLoginName() {
		return loginName;
	}
	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}
	public Integer getIsAuth() {
		return isAuth;
	}
	public void setIsAuth(Integer isAuth) {
		this.isAuth = isAuth;
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
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
	public Integer getCamId() {
		return camId;
	}
	public void setCamId(Integer camId) {
		this.camId = camId;
	}
	public Integer getIdentity() {
		return identity;
	}
	public void setIdentity(Integer identity) {
		this.identity = identity;
	}
}
