package cn.edugate.esb.entity;

import java.sql.Time;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "attendance_conf")
public class AttendanceConf {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;

	private Integer org_id;

	private Integer attnd_num;

	private Integer type;

	private Integer grade_id;

	private String grade_name;

	private Time AM_in_time;

	private Time AM_in_scope_begin;

	private Time AM_in_scope_end;

	private Time AM_out_time;

	private Time AM_out_scope_begin;

	private Time AM_out_scope_end;

	private Time PM_in_time;

	private Time PM_in_scope_begin;

	private Time PM_in_scope_end;

	private Time PM_out_time;

	private Time PM_out_scope_begin;

	private Time PM_out_scope_end;

	private Date insert_time;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getOrg_id() {
		return org_id;
	}

	public void setOrg_id(Integer org_id) {
		this.org_id = org_id;
	}

	public Integer getAttnd_num() {
		return attnd_num;
	}

	public void setAttnd_num(Integer attnd_num) {
		this.attnd_num = attnd_num;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public Integer getGrade_id() {
		return grade_id;
	}

	public void setGrade_id(Integer grade_id) {
		this.grade_id = grade_id;
	}

	public String getGrade_name() {
		return grade_name;
	}

	public void setGrade_name(String grade_name) {
		this.grade_name = grade_name;
	}

	public Time getAM_in_time() {
		return AM_in_time;
	}

	public void setAM_in_time(Time aM_in_time) {
		AM_in_time = aM_in_time;
	}

	public Time getAM_in_scope_begin() {
		return AM_in_scope_begin;
	}

	public void setAM_in_scope_begin(Time aM_in_scope_begin) {
		AM_in_scope_begin = aM_in_scope_begin;
	}

	public Time getAM_in_scope_end() {
		return AM_in_scope_end;
	}

	public void setAM_in_scope_end(Time aM_in_scope_end) {
		AM_in_scope_end = aM_in_scope_end;
	}

	public Time getAM_out_time() {
		return AM_out_time;
	}

	public void setAM_out_time(Time aM_out_time) {
		AM_out_time = aM_out_time;
	}

	public Time getAM_out_scope_begin() {
		return AM_out_scope_begin;
	}

	public void setAM_out_scope_begin(Time aM_out_scope_begin) {
		AM_out_scope_begin = aM_out_scope_begin;
	}

	public Time getAM_out_scope_end() {
		return AM_out_scope_end;
	}

	public void setAM_out_scope_end(Time aM_out_scope_end) {
		AM_out_scope_end = aM_out_scope_end;
	}

	public Time getPM_in_time() {
		return PM_in_time;
	}

	public void setPM_in_time(Time pM_in_time) {
		PM_in_time = pM_in_time;
	}

	public Time getPM_in_scope_begin() {
		return PM_in_scope_begin;
	}

	public void setPM_in_scope_begin(Time pM_in_scope_begin) {
		PM_in_scope_begin = pM_in_scope_begin;
	}

	public Time getPM_in_scope_end() {
		return PM_in_scope_end;
	}

	public void setPM_in_scope_end(Time pM_in_scope_end) {
		PM_in_scope_end = pM_in_scope_end;
	}

	public Time getPM_out_time() {
		return PM_out_time;
	}

	public void setPM_out_time(Time pM_out_time) {
		PM_out_time = pM_out_time;
	}

	public Time getPM_out_scope_begin() {
		return PM_out_scope_begin;
	}

	public void setPM_out_scope_begin(Time pM_out_scope_begin) {
		PM_out_scope_begin = pM_out_scope_begin;
	}

	public Time getPM_out_scope_end() {
		return PM_out_scope_end;
	}

	public void setPM_out_scope_end(Time pM_out_scope_end) {
		PM_out_scope_end = pM_out_scope_end;
	}

	public Date getInsert_time() {
		return insert_time;
	}

	public void setInsert_time(Date insert_time) {
		this.insert_time = insert_time;
	}

}
