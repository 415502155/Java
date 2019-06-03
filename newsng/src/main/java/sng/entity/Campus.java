package sng.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 校区表 
 * Title: Campus 
 * Description: 校区详细信息表 
 * Company: 世纪伟业
 */
@Entity
@Table(name = "campus")
public class Campus implements Serializable {

	private static final long serialVersionUID = -1340534853146197487L;

	/**
	 * 校区表主键
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer cam_id;
	/**
	 * 机构主键
	 */
	private Integer org_id;
	/**
	 * 校区名称
	 */
	private String cam_name;
	/**
	 * 校区类型
	 */
	private Integer cam_type;
	/**
	 * 校区地址
	 */
	private String cam_address;
	/***
	 * 联系电话
	 */
	private String cam_mobile;
	/***
	 * 导航信息
	 */
	private String navigation_info;
	/***
	 * 备注
	 */
	private String note;
	/***
	 * 新增时间
	 */
	private Date insert_time;
	/***
	 * 修改时间
	 */
	private Date update_time;
	/***
	 * 删除时间
	 */
	private Date del_time;
	/***
	 * 是否删除 (0:否；1：是)
	 */
	private Integer is_del;

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

	public Integer getCam_type() {
		return cam_type;
	}

	public void setCam_type(Integer cam_type) {
		this.cam_type = cam_type;
	}

	public String getCam_address() {
		return cam_address;
	}

	public void setCam_address(String cam_address) {
		this.cam_address = cam_address;
	}

	public String getCam_mobile() {
		return cam_mobile;
	}

	public void setCam_mobile(String cam_mobile) {
		this.cam_mobile = cam_mobile;
	}

	public String getNavigation_info() {
		return navigation_info;
	}

	public void setNavigation_info(String navigation_info) {
		this.navigation_info = navigation_info;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
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

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

}