package sng.pojo;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ClassToTeacher implements Serializable {

	/**
	 * 班级-教师 列表
	 */
	private static final long serialVersionUID = 1L;

	private Integer clas_id;

	private Integer category_id;
	private Integer subject_id;
	private Integer cam_id;
	private Integer plan_id;
	private Integer plan_switch;//计划开关
	
	private String clas_name;
	private Integer clas_type;
	private String clas_type_name;
	private String cam_name;
	private String age_range;
	
	@JsonFormat(pattern="yyyy-MM-dd HH:mm",timezone="GMT+8")
	private Date class_start_date;
	private String class_start_date_str;
	private String tuition_fees;//学费标准
	private BigInteger size; // 班容量
	private BigInteger ss_num;// 已缴费人数
	private Integer ys_num;// 应缴费人数
	private Integer st_num;// 已退费人数
	private BigInteger num;//已缴费人数-已退费人数
	private String ss_money;//总实收学费数
	private String st_money;//总实退学费数
	private String ys_money;//总应收学费数

	private String category_name;
	private String subject_name;
	private Integer classroom_id;
	private String classroom_name;
	private String building;
	private String floor;
	private Date start_birthday;
	private String start_birthday_str;
	private Date end_birthday;
	private String end_birthday_str;
	private BigInteger total_hours;
	private Integer class_week;
	private String class_week_name;
	private String class_begin_time;

	private String class_over_time;
	private String class_unset_time;
	private Integer cooperation_id;
	private String name;

	private String tech_names;// 教师名称_教师电话号码
	private String tech_name;// 教师名称
	private String tech_id;//教师主键ID
	private String user_id;//用户ID
	private String divide;// （已缴费人数-已退费人数）/班容量
	private String ss_tuition;// 实收学费（班级）
	private Integer term_id;
	private String term_name;
	private String cur_year;
	
	public Integer getTerm_id() {
		return term_id;
	}

	public void setTerm_id(Integer term_id) {
		this.term_id = term_id;
	}

	public String getTerm_name() {
		return term_name;
	}

	public void setTerm_name(String term_name) {
		this.term_name = term_name;
	}

	public String getCur_year() {
		return cur_year;
	}

	public void setCur_year(String cur_year) {
		this.cur_year = cur_year;
	}

	private String error_msg;
	public Integer getPlan_switch() {
		return plan_switch;
	}

	public void setPlan_switch(Integer plan_switch) {
		this.plan_switch = plan_switch;
	}

	public Integer getPlan_id() {
		return plan_id;
	}

	public void setPlan_id(Integer plan_id) {
		this.plan_id = plan_id;
	}

	public Integer getYs_num() {
		return ys_num;
	}

	public void setYs_num(Integer ys_num) {
		this.ys_num = ys_num;
	}

	public Integer getSt_num() {
		return st_num;
	}

	public void setSt_num(Integer st_num) {
		this.st_num = st_num;
	}

	public String getYs_money() {
		return ys_money;
	}

	public void setYs_money(String ys_money) {
		this.ys_money = ys_money;
	}

	public Integer getClas_id() {
		return clas_id;
	}

	public void setClas_id(Integer clas_id) {
		this.clas_id = clas_id;
	}

	public String getClas_name() {
		return clas_name;
	}

	public void setClas_name(String clas_name) {
		this.clas_name = clas_name;
	}

	public Integer getClas_type() {
		return clas_type;
	}

	public void setClas_type(Integer clas_type) {
		this.clas_type = clas_type;
	}

	public String getCam_name() {
		return cam_name;
	}

	public void setCam_name(String cam_name) {
		this.cam_name = cam_name;
	}

	public Date getClass_start_date() {
		return class_start_date;
	}

	public void setClass_start_date(Date class_start_date) {
		this.class_start_date = class_start_date;
	}

	public String getTuition_fees() {
		return tuition_fees;
	}

	public void setTuition_fees(String tuition_fees) {
		this.tuition_fees = tuition_fees;
	}

	public BigInteger getSize() {
		return size;
	}

	public void setSize(BigInteger size) {
		this.size = size;
	}

	public BigInteger getSs_num() {
		return ss_num;
	}

	public void setSs_num(BigInteger ss_num) {
		this.ss_num = ss_num;
	}
	
	public BigInteger getNum() {
		return num;
	}

	public void setNum(BigInteger num) {
		this.num = num;
	}

	public String getSs_money() {
		return ss_money;
	}

	public void setSs_money(String ss_money) {
		this.ss_money = ss_money;
	}

	public String getSt_money() {
		return st_money;
	}

	public void setSt_money(String st_money) {
		this.st_money = st_money;
	}

	public String getCategory_name() {
		return category_name;
	}

	public void setCategory_name(String category_name) {
		this.category_name = category_name;
	}

	public String getSubject_name() {
		return subject_name;
	}

	public void setSubject_name(String subject_name) {
		this.subject_name = subject_name;
	}

	public String getClassroom_name() {
		return classroom_name;
	}

	public void setClassroom_name(String classroom_name) {
		this.classroom_name = classroom_name;
	}

	public Date getStart_birthday() {
		return start_birthday;
	}

	public void setStart_birthday(Date start_birthday) {
		this.start_birthday = start_birthday;
	}

	public Date getEnd_birthday() {
		return end_birthday;
	}

	public void setEnd_birthday(Date end_birthday) {
		this.end_birthday = end_birthday;
	}

	public BigInteger getTotal_hours() {
		return total_hours;
	}

	public void setTotal_hours(BigInteger total_hours) {
		this.total_hours = total_hours;
	}

	public Integer getClass_week() {
		return class_week;
	}

	public void setClass_week(Integer class_week) {
		this.class_week = class_week;
	}

	

	public String getClass_begin_time() {
		return class_begin_time;
	}

	public void setClass_begin_time(String class_begin_time) {
		this.class_begin_time = class_begin_time;
	}

	

	public String getClass_over_time() {
		return class_over_time;
	}

	public void setClass_over_time(String class_over_time) {
		this.class_over_time = class_over_time;
	}

	public String getClass_unset_time() {
		return class_unset_time;
	}

	public void setClass_unset_time(String class_unset_time) {
		this.class_unset_time = class_unset_time;
	}

	public Integer getCooperation_id() {
		return cooperation_id;
	}

	public void setCooperation_id(Integer cooperation_id) {
		this.cooperation_id = cooperation_id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getTech_names() {
		return tech_names;
	}

	public void setTech_names(String tech_names) {
		this.tech_names = tech_names;
	}
	
	

	public String getTech_id() {
		return tech_id;
	}

	public void setTech_id(String tech_id) {
		this.tech_id = tech_id;
	}
	
	

	public String getUser_id() {
		return user_id;
	}

	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}

	public String getDivide() {
		return divide;
	}

	public void setDivide(String divide) {
		this.divide = divide;
	}

	public String getSs_tuition() {
		return ss_tuition;
	}

	public void setSs_tuition(String ss_tuition) {
		this.ss_tuition = ss_tuition;
	}

	public String getAge_range() {
		return age_range;
	}

	public void setAge_range(String age_range) {
		this.age_range = age_range;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public Integer getClassroom_id() {
		return classroom_id;
	}

	public void setClassroom_id(Integer classroom_id) {
		this.classroom_id = classroom_id;
	}

	public String getBuilding() {
		return building;
	}

	public void setBuilding(String building) {
		this.building = building;
	}

	public String getFloor() {
		return floor;
	}

	public void setFloor(String floor) {
		this.floor = floor;
	}

	public String getClas_type_name() {
		return clas_type_name;
	}

	public void setClas_type_name(String clas_type_name) {
		this.clas_type_name = clas_type_name;
	}

	public String getError_msg() {
		return error_msg;
	}

	public void setError_msg(String error_msg) {
		this.error_msg = error_msg;
	}

	public String getClass_week_name() {
		return class_week_name;
	}

	public void setClass_week_name(String class_week_name) {
		this.class_week_name = class_week_name;
	}

	public String getTech_name() {
		return tech_name;
	}

	public void setTech_name(String tech_name) {
		this.tech_name = tech_name;
	}

	public Integer getCategory_id() {
		return category_id;
	}

	public void setCategory_id(Integer category_id) {
		this.category_id = category_id;
	}

	public Integer getSubject_id() {
		return subject_id;
	}

	public void setSubject_id(Integer subject_id) {
		this.subject_id = subject_id;
	}

	public Integer getCam_id() {
		return cam_id;
	}

	public void setCam_id(Integer cam_id) {
		this.cam_id = cam_id;
	}

	public String getClass_start_date_str() {
		return class_start_date_str;
	}

	public void setClass_start_date_str(String class_start_date_str) {
		this.class_start_date_str = class_start_date_str;
	}

	public String getStart_birthday_str() {
		return start_birthday_str;
	}

	public void setStart_birthday_str(String start_birthday_str) {
		this.start_birthday_str = start_birthday_str;
	}

	public String getEnd_birthday_str() {
		return end_birthday_str;
	}

	public void setEnd_birthday_str(String end_birthday_str) {
		this.end_birthday_str = end_birthday_str;
	}

	
}
