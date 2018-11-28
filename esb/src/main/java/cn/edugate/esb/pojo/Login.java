package cn.edugate.esb.pojo;

public class Login {
	private String user_id;  			//用户ID
	private String type;			//用户类型
	private Long extratime;				//额外增加时间
	public String getUser_id() {
		return user_id;
	}
	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public Long getExtratime() {
		return extratime;
	}
	public void setExtratime(Long extratime) {
		this.extratime = extratime;
	}

}
