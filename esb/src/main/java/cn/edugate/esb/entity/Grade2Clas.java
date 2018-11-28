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
@Table(name ="grade2clas")
public class Grade2Clas{
	// 年级2班级ID
	private Integer gra2cls_id;
	// 年级ID
	private Integer grade_id;
	// 班级ID
	private Integer clas_id;
	// 添加时间
	private Date insert_time;
	// 删除时间
	private Date del_time;
	// 是否删除
	private Boolean is_del;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	public Integer getGra2cls_id() {
		return gra2cls_id;
	}
	public void setGra2cls_id(Integer gra2cls_id) {
		this.gra2cls_id = gra2cls_id;
	}
	public Integer getGrade_id() {
		return grade_id;
	}
	public void setGrade_id(Integer grade_id) {
		this.grade_id = grade_id;
	}
	public Integer getClas_id() {
		return clas_id;
	}
	public void setClas_id(Integer clas_id) {
		this.clas_id = clas_id;
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
	public Boolean getIs_del() {
		return is_del;
	}
	public void setIs_del(Boolean is_del) {
		this.is_del = is_del;
	}
	
}

