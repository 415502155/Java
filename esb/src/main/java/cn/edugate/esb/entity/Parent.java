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
@Table(name = "parent", catalog = "`edugate_base`")
public class Parent {
	
	private Integer parent_id;
	private Integer org_id;
	private Integer user_id;
	private String parent_name;
	private String parent_note;
	private Date insert_time;
	private Date update_time;
	private Date del_time;
	private Boolean is_del;
	private String json;	
	private Byte sex;
	private String head;
	private String openid;
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	public Integer getParent_id() {
		return parent_id;
	}
	public void setParent_id(Integer parent_id) {
		this.parent_id = parent_id;
	}
	public Integer getOrg_id() {
		return org_id;
	}
	public void setOrg_id(Integer org_id) {
		this.org_id = org_id;
	}
	public Integer getUser_id() {
		return user_id;
	}
	public void setUser_id(Integer user_id) {
		this.user_id = user_id;
	}
	public String getParent_name() {
		return parent_name;
	}
	public void setParent_name(String parent_name) {
		this.parent_name = parent_name;
	}
	public String getParent_note() {
		return parent_note;
	}
	public void setParent_note(String parent_note) {
		this.parent_note = parent_note;
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
	public String getJson() {
		return json;
	}
	public void setJson(String json) {
		this.json = json;
	}
	public Byte getSex() {
		return sex;
	}
	public void setSex(Byte sex) {
		this.sex = sex;
	}
	public String getHead() {
		return head;
	}
	public void setHead(String head) {
		this.head = head;
	}

	/**
	 * 家长学生关系
	 */
	private Integer relation;
	@Transient
	public Integer getRelation() {
		return relation;
	}
	public void setRelation(Integer relation) {
		this.relation = relation;
	}
	/**
	 * 手机
	 */
	private String mobile;
	@Transient
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	/**
	 * 手机类型
	 */
	private Byte mobile_type;
	@Transient
	public Byte getMobile_type() {
		return mobile_type;
	}
	public void setMobile_type(Byte mobile_type) {
		this.mobile_type = mobile_type;
	}
	/**
	 * 学生家长关系主键
	 */
	private Integer stud2parent_id;
	@Transient
	public Integer getStud2parent_id() {
		return stud2parent_id;
	}
	public void setStud2parent_id(Integer stud2parent_id) {
		this.stud2parent_id = stud2parent_id;
	}
	private String relation_name;
	@Transient
	public String getRelation_name() {
		return relation_name;
	}
	public void setRelation_name(String relation_name) {
		this.relation_name = relation_name;
	}
	@Transient
	public String getOpenid() {
		return openid;
	}
	public void setOpenid(String openid) {
		this.openid = openid;
	}
	
}
