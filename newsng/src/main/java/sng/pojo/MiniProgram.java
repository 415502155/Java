package sng.pojo;

import java.io.Serializable;

public class MiniProgram implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 174733207868687257L;

	private String appid;

	private String pagepath;

	public MiniProgram() {
	}

	public MiniProgram(String appid, String pagePath) {
		this.appid = appid;
		this.pagepath = pagePath;
	}

	public String getAppid() {
		return this.appid;
	}

	public void setAppid(String appid) {
		this.appid = appid;
	}

	public String getPagepath() {
		return pagepath;
	}

	public void setPagepath(String pagepath) {
		this.pagepath = pagepath;
	}
}
