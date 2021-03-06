package cn.edugate.esb.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.springframework.beans.factory.annotation.Autowired;

import cn.edugate.esb.util.Util;

@JsonIgnoreProperties(ignoreUnknown = true)
@Entity
@Table(name ="classes",catalog="`edugate_base`")
public class Classes implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Util util;
	@Autowired
	public void setUtil(Util util) {
		this.util = util;
	}
	// 班级ID
	private Integer clas_id;
	// 机构ID
	private Integer org_id;
	// 校区主键
	private Integer cam_id;
	// 是否毕业(0否1是）
	private Integer is_graduated;
	// 入学时间
	private String start_time;
	// 班级名称
	private String clas_name;
	// 班级卡
	private String clas_card;
	// 备注
	private String clas_note;
	// 班级LOGO
	private String clas_logo;
	// 黑板报
	private String clas_newspaper;
	// 创建时间
	private Date insert_time;
	// 修改时间
	private Date update_time;
	// 删除时间
	private Date del_time;
	// 是否删除
	private Integer is_del;
	/**
	 * 当前年级主键Transient
	 */
	private Integer grade_id;
	/**
	 * 年级Transient
	 */
	private Grade grade;
	/**
	 * 校区Transient
	 */
	private Campus campus;
	/**
	 * 任课教师
	 */
	private List<Map<String,Object>> class_master;	
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	public Integer getClas_id() {
		return clas_id;
	}
	public void setClas_id(Integer clas_id) {
		this.clas_id = clas_id;
	}
	public Integer getOrg_id() {
		return org_id;
	}
	public void setOrg_id(Integer org_id) {
		this.org_id = org_id;
	}
	public Integer getCam_id() {
		return cam_id;
	}
	public void setCam_id(Integer cam_id) {
		this.cam_id = cam_id;
	}
	public Integer getIs_graduated() {
		return is_graduated;
	}
	public void setIs_graduated(Integer is_graduated) {
		this.is_graduated = is_graduated;
	}
	public String getStart_time() {
		return start_time;
	}
	public void setStart_time(String start_time) {
		this.start_time = start_time;
	}
	public String getClas_name() {
		return clas_name;
	}
	public void setClas_name(String clas_name) {
		this.clas_name = clas_name;
	}
	public String getClas_card() {
		return clas_card;
	}
	public void setClas_card(String clas_card) {
		this.clas_card = clas_card;
	}
	public String getClas_note() {
		return clas_note;
	}
	public void setClas_note(String clas_note) {
		this.clas_note = clas_note;
	}
	public String getClas_logo() {
		return clas_logo;
	}
	public void setClas_logo(String clas_logo) {
		this.clas_logo = clas_logo;
	}
	public String getClas_newspaper() {
		return clas_newspaper;
	}
	public void setClas_newspaper(String clas_newspaper) {
		this.clas_newspaper = clas_newspaper;
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
	@Transient
	public Integer getGrade_id() {
		return grade_id;
	}
	public void setGrade_id(Integer grade_id) {
		this.grade_id = grade_id;
	}
	@Transient
	public Grade getGrade() {
		return grade;
	}
	public void setGrade(Grade grade) {
		this.grade = grade;
	}
	@Transient
	public Campus getCampus() {
		return campus;
	}
	public void setCampus(Campus campus) {
		this.campus = campus;
	}
	@Transient
	public List<Map<String,Object>> getClass_master() {
		return class_master;
	}
	public void setClass_master(List<Map<String,Object>> class_master) {
		this.class_master = class_master;
	}
	
	//校区名称
	private String cam_name;
	@Transient
	public String getCam_name() {
		return cam_name;
	}
	public void setCam_name(String cam_name) {
		this.cam_name = cam_name;
	}

	//所在年级
	private String grade_name;
	@Transient
	public String getGrade_name() {
		return grade_name;
	}
	public void setGrade_name(String grade_name) {
		this.grade_name = grade_name;
	}
	
	//班级logo地址
	@SuppressWarnings("unused")
	private String clas_logoUrl;
	@Transient
	public String getClas_logoUrl() {
		String clas_logoUrlstr="";
		if (this.clas_logo!=null&&!"".equals(this.clas_logo)) {
			clas_logoUrlstr = util.getPathByPicName(this.clas_logo);
		} 
		return clas_logoUrlstr;
	}
	public void setClas_logoUrl(String clas_logoUrl) {
		this.clas_logoUrl = clas_logoUrl;
	}
	//学生
	private List<Student> students;
	@Transient
	public List<Student> getStudents() {
		return students;
	}
	public void setStudents(List<Student> students) {
		this.students = students;
	}
	//教师
	private List<Teacher> teachers;
	@Transient
	public List<Teacher> getTeachers() {
		return teachers;
	}
	public void setTeachers(List<Teacher> teachers) {
		this.teachers = teachers;
	}
	
	//班主任
	private List<Teacher> masters ;
	@Transient
	public List<Teacher> getMasters() {
		return masters;
	}
	public void setMasters(List<Teacher> masters) {
		this.masters = masters;
	}
	
	// 学生人数
	private Integer student_num;
	@Transient
	public Integer getStudent_num() {
		return student_num;
	}
	public void setStudent_num(Integer student_num) {
		this.student_num = student_num;
	}
	
	// 教师人数
	private Integer teacher_num;
	@Transient
	public Integer getTeacher_num() {
		return teacher_num;
	}
	public void setTeacher_num(Integer teacher_num) {
		this.teacher_num = teacher_num;
	}
	//学生班级关系id
	private Integer clas2stud_id;
	@Transient
	public Integer getClas2stud_id() {
		return clas2stud_id;
	}
	public void setClas2stud_id(Integer clas2stud_id) {
		this.clas2stud_id = clas2stud_id;
	} 
	
}

