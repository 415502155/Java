package cn.edugate.esb.entity;

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
@Table(name ="grade")
public class Grade{
	// 年级ID
	private Integer grade_id;
	// 机构ID
	private Integer org_id;
	// 年级名称
	private String grade_name;
	/**
	 * 年级编号
	 * 10幼儿园新生年级11小小班12小班13中班14大班15学前班 18幼儿园应届毕业 19幼儿园往届毕业
	 * 20小学新生年级21一年级22二年级23三年级24四年级25五年级26六年级  28小学应届毕业29小学往届毕业
	 * 30初中新生年级31六年级32七年级33八年级34九年级   38初中应届毕业 39初中往届毕业
	 * 40高中新生年级41高一42高二43高三  48高中应届毕业49高中往届毕业
	 */
	private Integer grade_number;
	// 年级备注
	private String grade_note;
	/**
	 * 年级类型(1幼儿2小学3初中4高中5特殊类)
	 */
	private Integer grade_type;
	// 创建时间
	private Date insert_time;
	// 修改时间
	private Date update_time;
	// 删除时间
	private Date del_time;
	// 是否删除
	private Integer is_del;
	/**
	 * 年级组长Transient
	 */
	private List<Teacher> grade_master;
	/**
	 * 班级数量Transient
	 */
	private Integer classNum;
	/**
	 * 学生人数Transient
	 */
	private Integer studentNum;
	/**
	 * 下属班级Transient
	 */
	private List<Classes> classes;
	

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	public Integer getGrade_id() {
		return grade_id;
	}
	public void setGrade_id(Integer grade_id) {
		this.grade_id = grade_id;
	}
	public Integer getOrg_id() {
		return org_id;
	}
	public void setOrg_id(Integer org_id) {
		this.org_id = org_id;
	}
	public String getGrade_name() {
		return grade_name;
	}
	public void setGrade_name(String grade_name) {
		this.grade_name = grade_name;
	}
	public Integer getGrade_number() {
		return grade_number;
	}
	public void setGrade_number(Integer grade_number) {
		this.grade_number = grade_number;
	}
	public String getGrade_note() {
		return grade_note;
	}
	public void setGrade_note(String grade_note) {
		this.grade_note = grade_note;
	}
	public Integer getGrade_type() {
		return grade_type;
	}
	public void setGrade_type(Integer grade_type) {
		this.grade_type = grade_type;
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
	public List<Teacher> getGrade_master() {
		return grade_master;
	}
	public void setGrade_master(List<Teacher> grade_master) {
		this.grade_master = grade_master;
	}
	@Transient
	public Integer getClassNum() {
		return classNum;
	}
	public void setClassNum(Integer classNum) {
		this.classNum = classNum;
	}
	@Transient
	public Integer getStudentNum() {
		return studentNum;
	}
	public void setStudentNum(Integer studentNum) {
		this.studentNum = studentNum;
	}
	@Transient
	public List<Classes> getClasses() {
		return classes;
	}
	public void setClasses(List<Classes> classes) {
		this.classes = classes;
	}
}

