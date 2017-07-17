package com.gm.bean;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="t_interface_ad_search_oneday")
public class AdSearchBean {
	private static final long serialVersionUID = -2L;
	private long id;
	private String search_term;
	private String trandate;
	private long imeinum;

	public String getSearch_term() {
		return search_term;
	}
	public void setSearch_term(String search_term) {
		this.search_term = search_term;
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
