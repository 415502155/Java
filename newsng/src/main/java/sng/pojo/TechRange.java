package sng.pojo;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class TechRange implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -4911540941560501220L;
	private Integer tr_id;
	private Integer org_id;
	private Integer tech_id;
	private Integer rl_id;
	private Integer clas_id;
	private Integer grade_id;
	private String group_id;
	private String dep_id;
	private Integer cour_id;


	
	
	public Integer getTr_id() {
		return tr_id;
	}
	public void setTr_id(Integer tr_id) {
		this.tr_id = tr_id;
	}
	public Integer getOrg_id() {
		return org_id;
	}
	public void setOrg_id(Integer org_id) {
		this.org_id = org_id;
	}
	public Integer getTech_id() {
		return tech_id;
	}
	public void setTech_id(Integer tech_id) {
		this.tech_id = tech_id;
	}
	public Integer getRl_id() {
		return rl_id;
	}
	public void setRl_id(Integer rl_id) {
		this.rl_id = rl_id;
	}
	public Integer getClas_id() {
		return clas_id;
	}
	public void setClas_id(Integer clas_id) {
		this.clas_id = clas_id;
	}
	public Integer getGrade_id() {
		return grade_id;
	}
	public void setGrade_id(Integer grade_id) {
		this.grade_id = grade_id;
	}

	public String getGroup_id() {
		return group_id;
	}
	public void setGroup_id(String group_id) {
		this.group_id = group_id;
	}
	public String getDep_id() {
		return dep_id;
	}
	public void setDep_id(String dep_id) {
		this.dep_id = dep_id;
	}
	public Integer getCour_id() {
		return cour_id;
	}
	public void setCour_id(Integer cour_id) {
		this.cour_id = cour_id;
	}

}
