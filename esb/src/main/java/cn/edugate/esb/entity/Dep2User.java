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
@Table(name ="dep2user")
public class Dep2User{
	// 部门2用户ID
	private Integer dep2user_id;
	// 部门ID
	private Integer dep_id;
	// 用户ID
	private String sso_id;
	// 是否删除
	private Integer is_del;
	// 创建时间
	private Date insert_time;
	// 修改时间
	private Date update_time;
	// 删除时间
	private Date del_time;
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	public Integer getDep2user_id() {
		return dep2user_id;
	}
	public void setDep2user_id(Integer dep2user_id) {
		this.dep2user_id = dep2user_id;
	}
	public Integer getDep_id() {
		return dep_id;
	}
	public void setDep_id(Integer dep_id) {
		this.dep_id = dep_id;
	}
	public String getSso_id() {
		return sso_id;
	}
	public void setSso_id(String sso_id) {
		this.sso_id = sso_id;
	}
	public Integer getIs_del() {
		return is_del;
	}
	public void setIs_del(Integer is_del) {
		this.is_del = is_del;
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
}

