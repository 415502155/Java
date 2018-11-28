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
@Table(name ="campus", catalog = "`edugate_base`")
public class Campus{
	//校区主键
	private Integer cam_id;
	//机构主键
	private Integer org_id;
	
	private Integer cam_type;
	//校区名称
	private String cam_name;
	//校区备注
	private String cam_note;
	// 创建时间
	private Date insert_time;
	// 修改时间
	private Date update_time;
	// 是否删除
	private Integer is_del;
	// 删除时间
	private Date del_time;	
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	public Integer getCam_id() {
		return cam_id;
	}
	public void setCam_id(Integer cam_id) {
		this.cam_id = cam_id;
	}
	public Integer getOrg_id() {
		return org_id;
	}
	public void setOrg_id(Integer org_id) {
		this.org_id = org_id;
	}
	public String getCam_name() {
		return cam_name;
	}
	public void setCam_name(String cam_name) {
		this.cam_name = cam_name;
	}
	public String getCam_note() {
		return cam_note;
	}
	public void setCam_note(String cam_note) {
		this.cam_note = cam_note;
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
	public Integer getCam_type() {
		return cam_type;
	}
	public void setCam_type(Integer cam_type) {
		this.cam_type = cam_type;
	}
	
}

