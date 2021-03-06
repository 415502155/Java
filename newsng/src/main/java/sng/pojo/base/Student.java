package sng.pojo.base;

import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import sng.pojo.Campus;
import sng.pojo.Classes;
import sng.pojo.Grade;




@JsonIgnoreProperties(ignoreUnknown = true)
@Entity
@Table(name = "student", catalog = "`edugate_base`")
public class Student {
	// 学生主键
	private Integer stud_id;
	// 机构主键
	private Integer org_id;
	// 用户主键
	private Integer user_id;
	// 学生姓名
	private String stud_name;
	// 学生学籍
	private String stud_record;
	// 学生卡
	private String stud_card;
	// 学生描述
	private String stud_note;
	// 创建时间
	private Date insert_time;
	// 更新时间
	private Date update_time;
	// 删除时间
	private Date del_time;
	// 是否删除
	private Integer is_del;
	/**
	 * 是否有标记
	 */
	private Integer is_selected;
	
	private String json;
	
	private String head;
	
	private String headurl;
	
	private Date birthday;
	
	private Byte sex;
	/**
	 * 手机号
	 */
	private String user_mobile;
	/**
	 * 当前班级主键Transient
	 */
	private Integer clas_id;
	/**
	 * 当前年级主键Transient
	 */
	private Integer grade_id;	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	public Integer getStud_id() {
		return stud_id;
	}
	public void setStud_id(Integer stud_id) {
		this.stud_id = stud_id;
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
	public String getStud_name() {
		return stud_name;
	}
	public void setStud_name(String stud_name) {
		this.stud_name = stud_name;
	}
	public String getStud_record() {
		return stud_record;
	}
	public void setStud_record(String stud_record) {
		this.stud_record = stud_record;
	}
	public String getStud_card() {
		return stud_card;
	}
	public void setStud_card(String stud_card) {
		this.stud_card = stud_card;
	}
	public String getStud_note() {
		return stud_note;
	}
	public void setStud_note(String stud_note) {
		this.stud_note = stud_note;
	}
	@Temporal(TemporalType.DATE)
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
	
	public String getHead() {
		return head;
	}
	public void setHead(String head) {
		this.head = head;
	}
	public Date getBirthday() {
		return birthday;
	}
	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}
	public Byte getSex() {
		return sex;
	}
	public void setSex(Byte sex) {
		this.sex = sex;
	}
	
	@Transient
	public Integer getClas_id() {
		return clas_id;
	}
	public void setClas_id(Integer clas_id) {
		this.clas_id = clas_id;
	}
	@Transient
	public Integer getGrade_id() {
		return grade_id;
	}
	public void setGrade_id(Integer grade_id) {
		this.grade_id = grade_id;
	}
	@Transient
	public String getUser_mobile() {
		return user_mobile;
	}
	public void setUser_mobile(String user_mobile) {
		this.user_mobile = user_mobile;
	}
	public String getJson() {
		return json;
	}
	public void setJson(String json) {
		this.json = json;
	}
	@Transient
	public Integer getIs_selected() {
		return is_selected;
	}
	public void setIs_selected(Integer is_selected) {
		this.is_selected = is_selected;
	}
	@Transient
	public String getHeadurl() {
		return headurl;
	}
	public void setHeadurl(String headurl) {
		this.headurl = headurl;
	}
	//家长
	public List<Parent> parents;
	@Transient
	public List<Parent> getParents() {
		return parents;
	}
	public void setParents(List<Parent> parents) {
		this.parents = parents;
	}
	
	//家长名称
	private String parent_name;
	@Transient
	public String getParent_name() {
		return parent_name;
	}
	public void setParent_name(String parent_name) {
		this.parent_name = parent_name;
	}
	
	//家长手机号
	private String parent_mobile;
	@Transient
	public String getParent_mobile() {
		return parent_mobile;
	}
	public void setParent_mobile(String parent_mobile) {
		this.parent_mobile = parent_mobile;
	}
	
	/**
	 * 所属全部班级列表Transient
	 */
	private List<Classes> classList;
	@Transient
	public List<Classes> getClassList() {
		return classList;
	}
	public void setClassList(List<Classes> classList) {
		this.classList = classList;
	}
	//校区列表
	private List<Campus> campusList;
	@Transient
	public List<Campus> getCampusList() {
		return campusList;
	}
	public void setCampusList(List<Campus> campusList) {
		this.campusList = campusList;
	}
	//年级列表
	private List<Grade> gradeList;
	@Transient
	public List<Grade> getGradeList() {
		return gradeList;
	}
	public void setGradeList(List<Grade> gradeList) {
		this.gradeList = gradeList;
	}
	
	private Integer clas2stud_id;
	@Transient
	public Integer getClas2stud_id() {
		return clas2stud_id;
	}
	public void setClas2stud_id(Integer clas2stud_id) {
		this.clas2stud_id = clas2stud_id;
	}
	
	private Integer relation;
	@Transient
	public Integer getRelation() {
		return relation;
	}
	public void setRelation(Integer relation) {
		this.relation = relation;
	}
	
	private String relation_name;
	@Transient
	public String getRelation_name() {
		return relation_name;
	}
	public void setRelation_name(String relation_name) {
		this.relation_name = relation_name;
	}
	
}
