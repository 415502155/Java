package cn.edugate.esb.entity;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name ="role_label",catalog="`edugate_base`")
public class RoleLabel{
	// 角色ID
	private Integer rl_id;
	// 类型 （0学校，2培训机构，3教育局）
	private Integer rl_type;
	// 等级（1管理员2校长3任课教师4班主任5年级组长6学生组管理员7部门管理员8教师组管理员9功能管理员）
	private Integer rl_level;
	// 角色名称
	private String rl_name;
	// 角色备注
	private String rl_note;
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
	public Integer getRl_id() {
		return rl_id;
	}
	public void setRl_id(Integer rl_id) {
		this.rl_id = rl_id;
	}
	public Integer getRl_type() {
		return rl_type;
	}
	public void setRl_type(Integer rl_type) {
		this.rl_type = rl_type;
	}
	public Integer getRl_level() {
		return rl_level;
	}
	public void setRl_level(Integer rl_level) {
		this.rl_level = rl_level;
	}
	public String getRl_name() {
		return rl_name;
	}
	public void setRl_name(String rl_name) {
		this.rl_name = rl_name;
	}
	public String getRl_note() {
		return rl_note;
	}
	public void setRl_note(String rl_note) {
		this.rl_note = rl_note;
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
	
	private Boolean permissionsAll;
	
	public Boolean getPermissionsAll() {
		return permissionsAll;
	}
	public void setPermissionsAll(Boolean permissionsAll) {
		this.permissionsAll = permissionsAll;
	}
	
}

