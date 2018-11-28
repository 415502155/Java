package sng.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "teacher_class")
public class TeacherClass implements Serializable {
	@Id
	@Column(name="teacher_class_id")
	@GeneratedValue
    private Integer teacher_class_id;

	@Column(name="tech_id")
    private Integer tech_id;

	@Column(name="clas_id")
    private Integer clas_id;

	@Column(name="is_del")
    private Integer is_del;

	@Column(name="insert_time")
    private Date insert_time;

	@Column(name="update_time")
    private Date update_time;

	@Column(name="del_time")
    private Date del_time;

    private String creater;

	public Integer getTeacher_class_id() {
		return teacher_class_id;
	}

	public void setTeacher_class_id(Integer teacher_class_id) {
		this.teacher_class_id = teacher_class_id;
	}

	public Integer getTech_id() {
		return tech_id;
	}

	public void setTech_id(Integer tech_id) {
		this.tech_id = tech_id;
	}

	public Integer getClas_id() {
		return clas_id;
	}

	public void setClas_id(Integer clas_id) {
		this.clas_id = clas_id;
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

	public String getCreater() {
		return creater;
	}

	public void setCreater(String creater) {
		this.creater = creater;
	}
}