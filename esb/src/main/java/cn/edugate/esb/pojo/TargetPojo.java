package cn.edugate.esb.pojo;

import java.util.List;

public class TargetPojo implements java.io.Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Integer sid;
	private List<Object> tels;
	private Integer type;
	
	public Integer getType() {
		return type;
	}
	public void setType(Integer type) {
		this.type = type;
	}
	public Integer getSid() {
		return sid;
	}
	public void setSid(Integer sid) {
		this.sid = sid;
	}
	
	public List<Object> getTels() {
		return tels;
	}
	public void setTels(List<Object> tels) {
		this.tels = tels;
	}
}
