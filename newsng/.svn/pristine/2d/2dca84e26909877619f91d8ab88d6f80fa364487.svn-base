package sng.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 类目表 
 * Title: Category 
 * Description: 类目详细信息表 
 * Company: 世纪伟业
 */
@Entity
@Table(name = "category")
public class Category implements Serializable {

	private static final long serialVersionUID = -1340534223146197481L;

	/**
	 * 类目表主键
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer category_id;
	/**
	 * 机构主键
	 */
	private Integer org_id;
	/**
	 * 类目名称
	 */
	private String category_name;
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

	public Integer getCategory_id() {
		return category_id;
	}

	public void setCategory_id(Integer category_id) {
		this.category_id = category_id;
	}

	public Integer getOrg_id() {
		return org_id;
	}

	public void setOrg_id(Integer org_id) {
		this.org_id = org_id;
	}

	public String getCategory_name() {
		return category_name;
	}

	public void setCategory_name(String category_name) {
		this.category_name = category_name;
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
