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
@Table(name ="`group`")
public class Group{
	// 组ID
	private Integer group_id;
	// 组名称
	private String group_name;
	// 组类型(1教师组2机构内组3机构外组4学生组)
	private Integer group_type;
	// 机构主键
	private Integer org_id;
	// 创建者ID
	private Integer tech_id;
	// 备注
	private String note;
	// 创建时间
	private Date insert_time;
	// 修改时间
	private Date update_time;
	// 删除时间
	private Date del_time;
	// 是否删除
	private Integer is_del;
	// 环信主键
	private String hx_groupid;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	public Integer getGroup_id() {
		return group_id;
	}
	public void setGroup_id(Integer group_id) {
		this.group_id = group_id;
	}
	public String getGroup_name() {
		return group_name;
	}
	public void setGroup_name(String group_name) {
		this.group_name = group_name;
	}
	public Integer getGroup_type() {
		return group_type;
	}
	public void setGroup_type(Integer group_type) {
		this.group_type = group_type;
	}
	
	public Integer getTech_id() {
		return tech_id;
	}
	public void setTech_id(Integer tech_id) {
		this.tech_id = tech_id;
	}
	public String getNote() {
		return note;
	}
	public void setNote(String note) {
		this.note = note;
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
	public Integer getIs_del() {
		return is_del;
	}
	public void setIs_del(Integer is_del) {
		this.is_del = is_del;
	}
	public Integer getOrg_id() {
		return org_id;
	}
	public void setOrg_id(Integer org_id) {
		this.org_id = org_id;
	}
	public String getHx_groupid() {
		return hx_groupid;
	}
	public void setHx_groupid(String hx_groupid) {
		this.hx_groupid = hx_groupid;
	}
	
}

