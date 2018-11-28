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
@Table(name ="group_member")
public class GroupMember {
	// 组成员主键
	private Integer group_member_id;
	// 组主键
	private Integer group_id;
	// 成员类型(1学生2教师)
	private Integer type;
	// 成员主键(学生主键或教师主键)
	private Integer mem_id;
	// 创建时间
	private Date insert_time;
	// 删除时间
	private Date del_time;
	// 是否删除
	private Integer is_del;
	/**
	 * 成员对应教师信息Transient
	 */
	private Teacher teacher;
	/**
	 * 成员对应学生信息Transient
	 */
	private Student student;
	/**
	 * 机构主键Transient
	 */
	private Integer org_id;
	/**
	 * 成员对应机构信息Transient
	 */
	private Organization organization;
	
	@Transient
	private String hx_groupid;
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	public Integer getGroup_member_id() {
		return group_member_id;
	}
	public void setGroup_member_id(Integer group_member_id) {
		this.group_member_id = group_member_id;
	}
	public Integer getGroup_id() {
		return group_id;
	}
	public void setGroup_id(Integer group_id) {
		this.group_id = group_id;
	}
	public Integer getType() {
		return type;
	}
	public void setType(Integer type) {
		this.type = type;
	}
	public Integer getMem_id() {
		return mem_id;
	}
	public void setMem_id(Integer mem_id) {
		this.mem_id = mem_id;
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
	public Integer getIs_del() {
		return is_del;
	}
	public void setIs_del(Integer is_del) {
		this.is_del = is_del;
	}
	@Transient
	public Teacher getTeacher() {
		return teacher;
	}
	public void setTeacher(Teacher teacher) {
		this.teacher = teacher;
	}
	@Transient
	public Student getStudent() {
		return student;
	}
	public void setStudent(Student student) {
		this.student = student;
	}
	@Transient
	public Organization getOrganization() {
		return organization;
	}
	public void setOrganization(Organization organization) {
		this.organization = organization;
	}
	@Transient
	public Integer getOrg_id() {
		return org_id;
	}
	public void setOrg_id(Integer org_id) {
		this.org_id = org_id;
	}
	@Transient
	public String getHx_groupid() {
		return hx_groupid;
	}
	public void setHx_groupid(String hx_groupid) {
		this.hx_groupid = hx_groupid;
	}
}
