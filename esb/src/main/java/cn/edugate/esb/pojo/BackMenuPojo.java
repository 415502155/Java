package cn.edugate.esb.pojo;

import java.util.List;

public class BackMenuPojo implements java.io.Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String title;
	
	private List<SubMenuPojo> subs;
	
	public List<SubMenuPojo> getSubs() {
		return subs;
	}

	public void setSubs(List<SubMenuPojo> subs) {
		this.subs = subs;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	
	
}
