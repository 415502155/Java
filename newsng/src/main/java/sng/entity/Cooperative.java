package sng.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 合作机构表 
 * Title: Cooperative 
 * Description: 合作机构详细信息表 
 * Company: 世纪伟业
 */
@Entity
@Table(name = "cooperative")
public class Cooperative implements Serializable {

	private static final long serialVersionUID = -1266534923446197481L;

	/**
	 * 合作机构表主键
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer cooperative_id;
	/**
	 * 机构主键
	 */
	private Integer org_id;
	/**
	 * 合作机构名称
	 */
	private String name;
	/***
	 * 联系人
	 */
	private String linkman;
	/***
	 * 联系电话
	 */
	private String phone;
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

	public Integer getCooperative_id() {
		return cooperative_id;
	}

	public void setCooperative_id(Integer cooperative_id) {
		this.cooperative_id = cooperative_id;
	}

	public Integer getOrg_id() {
		return org_id;
	}

	public void setOrg_id(Integer org_id) {
		this.org_id = org_id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getLinkman() {
		return linkman;
	}

	public void setLinkman(String linkman) {
		this.linkman = linkman;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
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
