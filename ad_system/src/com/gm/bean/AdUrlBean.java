package com.gm.bean;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="t_interface_ad_url_oneday")
public class AdUrlBean {
	private static final long serialVersionUID = -1L;
	private long id;
	private String url;
	private String trandate;
	private long imeinum;
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getTrandate() {
		return trandate;
	}
	public void setTrandate(String trandate) {
		this.trandate = trandate;
	}
	public long getImeinum() {
		return imeinum;
	}
	public void setImeinum(long imeinum) {
		this.imeinum = imeinum;
	}
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
}
