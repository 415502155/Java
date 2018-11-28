package cn.edugate.esb.entity;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
@Entity
@Table(name ="tech2role",catalog="`edugate_base`")
public class TeacherRole{
	// 教师与权限关系主键
	private Integer tech2role_id;
	// 实际存储的是user_id
	private Integer tech_id;	
	private Integer role_id;
	private Date insert_time;
	private Date update_time;
	private Date del_time;
	private Boolean is_del;
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	public Integer getTech2role_id() {
		return tech2role_id;
	}
	public void setTech2role_id(Integer tech2role_id) {
		this.tech2role_id = tech2role_id;
	}
	public Integer getTech_id() {
		return tech_id;
	}
	public void setTech_id(Integer tech_id) {
		this.tech_id = tech_id;
	}
	public Integer getRole_id() {
		return role_id;
	}
	public void setRole_id(Integer role_id) {
		this.role_id = role_id;
	}
	public Date getInsert_time() {
		return insert_time;
	}
	public void setInsert_time(Date insert_time) {
		this.insert_time = insert_time;
	}
	public Date getUpdate_time() {
		return update_time;
	}
	public void setUpdate_time(Date update_time) {
		this.update_time = update_time;
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
	
	
}

