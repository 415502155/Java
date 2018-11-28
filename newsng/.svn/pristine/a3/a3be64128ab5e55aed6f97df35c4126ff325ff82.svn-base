package sng.pojo;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Clas2Student implements java.io.Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 4135641097326938100L;
	// 班级2用户ID
	private Integer clas2stud_id;
	// 用户ID
	private Integer stud_id;
	// 班级ID
	private Integer clas_id;

	private Integer org_id;
	
	private String stud_name;
	
	private String clas_name;
	
	private Integer grade_id;
	
	private String grade_name;
	
	private Integer user_id;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	public Integer getClas2stud_id() {
		return clas2stud_id;
	}
	public void setClas2stud_id(Integer clas2stud_id) {
		this.clas2stud_id = clas2stud_id;
	}
	public Integer getStud_id() {
		return stud_id;
	}
	public void setStud_id(Integer stud_id) {
		this.stud_id = stud_id;
	}
	public Integer getClas_id() {
		return clas_id;
	}
	public void setClas_id(Integer clas_id) {
		this.clas_id = clas_id;
	}

	@Transient
	public String getStud_name() {
		return stud_name;
	}
	public void setStud_name(String stud_name) {
		this.stud_name = stud_name;
	}
	@Transient
	public String getClas_name() {
		return clas_name;
	}
	public void setClas_name(String clas_name) {
		this.clas_name = clas_name;
	}
	@Transient
	public Integer getGrade_id() {
		return grade_id;
	}
	public void setGrade_id(Integer grade_id) {
		this.grade_id = grade_id;
	}
	@Transient
	public String getGrade_name() {
		return grade_name;
	}
	public void setGrade_name(String grade_name) {
		this.grade_name = grade_name;
	}
	@Transient
	public Integer getUser_id() {
		return user_id;
	}
	public void setUser_id(Integer user_id) {
		this.user_id = user_id;
	}
	public Integer getOrg_id() {
		return org_id;
	}
	public void setOrg_id(Integer org_id) {
		this.org_id = org_id;
	}
	
	
}

