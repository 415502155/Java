package sng.pojo.base;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name ="role",catalog="`edugate_base`")
public class Role implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	// 角色ID
	private Integer role_id;
	// 组织ID
	private Integer org_id;
	// 角色表情ID
	private Integer rl_id;
	// 角色名称
	private String role_name;
	// 角色备注
	private String role_note;
	// 角色权限
	private String authorities;
	// 添加时间
	private Date insert_time;	
	// 修改时间
	private Date update_time;
	// 删除时间
	private Date del_time;
	// 删除标记
	private Boolean is_del;
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	public Integer getRole_id() {
		return role_id;
	}
	public void setRole_id(Integer role_id) {
		this.role_id = role_id;
	}
	public Integer getOrg_id() {
		return org_id;
	}
	public void setOrg_id(Integer org_id) {
		this.org_id = org_id;
	}
	public Integer getRl_id() {
		return rl_id;
	}
	public void setRl_id(Integer rl_id) {
		this.rl_id = rl_id;
	}
	public String getRole_name() {
		return role_name;
	}
	public void setRole_name(String role_name) {
		this.role_name = role_name;
	}
	public String getRole_note() {
		return role_note;
	}
	public void setRole_note(String role_note) {
		this.role_note = role_note;
	}
	public String getAuthorities() {
		return authorities;
	}
	public void setAuthorities(String authorities) {
		this.authorities = authorities;
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
	
	private String firstLetter;
	@Transient
	public String getFirstLetter() {
		return firstLetter;
	}
	public void setFirstLetter(String firstLetter) {
		this.firstLetter = firstLetter;
	}
	
	private String rl_name;
	@Transient
	public String getRl_name() {
		return rl_name;
	}
	public void setRl_name(String rl_name) {
		this.rl_name = rl_name;
	}
	
}

