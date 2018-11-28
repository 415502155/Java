package cn.edugate.esb.entity;

import java.math.BigInteger;
import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
@Entity
@Table(name ="department", catalog = "`edugate_base`")
public class Department{
	// 部门ID
	private Integer dep_id;
	// 机构ID
	private Integer org_id;
	// 部门名称
	private String dep_name;
	// 部门办公室地址
	private String dep_officeplace;
	// 部门办公电话
	private String dep_officephone;
	// 部门备注
	private String dep_note;
	// 是否删除
	private Integer is_del;
	// 创建时间
	private Date insert_time;
	// 修改时间
	private Date update_time;
	// 删除时间
	private Date del_time;
	
	
	private Integer type;
	/**
	 * 部门管理员名称Transient
	 */
	private String manager_name;
	/**
	 * 部门成员Transient
	 */
	private List<Teacher> teachers;
	/**
	 * 部门成员人数Transient
	 */
	private BigInteger mem_num;
	
	private Integer parent_id;
	
	private String hx_groupid;
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	public Integer getDep_id() {
		return dep_id;
	}
	public void setDep_id(Integer dep_id) {
		this.dep_id = dep_id;
	}
	public Integer getOrg_id() {
		return org_id;
	}
	public void setOrg_id(Integer org_id) {
		this.org_id = org_id;
	}
	public String getDep_name() {
		return dep_name;
	}
	public void setDep_name(String dep_name) {
		this.dep_name = dep_name;
	}
	public String getDep_officeplace() {
		return dep_officeplace;
	}
	public void setDep_officeplace(String dep_officeplace) {
		this.dep_officeplace = dep_officeplace;
	}
	public String getDep_officephone() {
		return dep_officephone;
	}
	public void setDep_officephone(String dep_officephone) {
		this.dep_officephone = dep_officephone;
	}
	public String getDep_note() {
		return dep_note;
	}
	public void setDep_note(String dep_note) {
		this.dep_note = dep_note;
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
	public Integer getParent_id() {
		return parent_id;
	}
	public void setParent_id(Integer parent_id) {
		this.parent_id = parent_id;
	}
	@Transient
	public List<Teacher> getTeachers() {
		return teachers;
	}
	public void setTeachers(List<Teacher> teachers) {
		this.teachers = teachers;
	}
	@Transient
	public String getManager_name() {
		return manager_name;
	}
	public void setManager_name(String manager_name) {
		this.manager_name = manager_name;
	}
	@Transient
	public BigInteger getMem_num() {
		return mem_num;
	}
	public void setMem_num(BigInteger mem_num) {
		this.mem_num = mem_num;
	}
	public Integer getType() {
		return type;
	}
	public void setType(Integer type) {
		this.type = type;
	}
	
	public String getHx_groupid() {
		return hx_groupid;
	}
	public void setHx_groupid(String hx_groupid) {
		this.hx_groupid = hx_groupid;
	}
	
}

