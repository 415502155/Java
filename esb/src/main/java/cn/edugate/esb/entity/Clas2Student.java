package cn.edugate.esb.entity;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
@Entity
@Table(name ="class2student")
public class Clas2Student{
	// 班级2用户ID
	private Integer clas2stud_id;
	// 用户ID
	private Integer stud_id;
	// 班级ID
	private Integer clas_id;
	// 创建时间
	private Date insert_time;
	// 删除时间
	private Date del_time;
	// 是否删除
	private Boolean is_del;
	
	private String stud_name;
	
	private String clas_name;
	
	private Integer grade_id;
	
	private String grade_name;
	
	private Integer user_id;
	
	private Integer org_id;
	
	/**
	 * 是否关注
	 */
	private String is_bind;

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
	public Date getInsert_time() {
		return insert_time;
	}
	public void setInsert_time(Date insert_time) {
		this.insert_time = insert_time;
	}
	public Date getDel_time() {
		return del_time;
	}
	public void setDel_time(Date del_time) {
		this.del_time = del_time;
	}
	public Boolean getIs_del() {
		return is_del;
	}
	public void setIs_del(Boolean is_del) {
		this.is_del = is_del;
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
	@Transient
	public Integer getOrg_id() {
		return org_id;
	}
	public void setOrg_id(Integer org_id) {
		this.org_id = org_id;
	}
	@Transient
	public String getIs_bind() {
		return is_bind;
	}
	public void setIs_bind(String is_bind) {
		this.is_bind = is_bind;
	}
	
	
}

