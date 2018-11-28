package cn.edugate.esb.entity;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

/**
 * 场地
 * Title:Field
 * Description:
 * Company:SJWY
 * @author:Liuy
 * @Date:2017年5月27日上午9:29:29
 */
@Entity
@Table(name ="field", catalog = "`edugate_base`")
public class Field{

	// 场地ID
	private Integer field_id;

	// 机构ID
	private Integer org_id;

	// 场地名称
	private String field_name;

	// 场地类型（1活动室、2办公室、3教室、4会议室）
	private Integer field_type;

	// 场地所属校区
	private Integer campus_id;
	
	// 场地所属校区名称（仅页面显示用）
	@Transient
	private String campusName;

	@Transient
	public String getCampusName() {
		return campusName;
	}

	@Transient
	public void setCampusName(String campusName) {
		this.campusName = campusName;
	}

	// 场地编号
	private String field_number;

	// 场地备注
	private String field_note;

	// 是否删除
	private Integer is_del;

	// 创建时间
	private Date insert_time;

	// 修改时间
	private Date update_time;

	// 删除时间
	private Date del_time;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public Integer getField_id() {
		return field_id;
	}

	public void setField_id(Integer field_id) {
		this.field_id = field_id;
	}

	public Integer getOrg_id() {
		return org_id;
	}

	public void setOrg_id(Integer org_id) {
		this.org_id = org_id;
	}

	public String getField_name() {
		return field_name;
	}

	public void setField_name(String field_name) {
		this.field_name = field_name;
	}

	public Integer getField_type() {
		return field_type;
	}

	public void setField_type(Integer field_type) {
		this.field_type = field_type;
	}

	public Integer getCampus_id() {
		return campus_id;
	}

	public void setCampus_id(Integer campus_id) {
		this.campus_id = campus_id;
	}

	public String getField_number() {
		return field_number;
	}

	public void setField_number(String field_number) {
		this.field_number = field_number;
	}

	public String getField_note() {
		return field_note;
	}

	public void setField_note(String field_note) {
		this.field_note = field_note;
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
}

