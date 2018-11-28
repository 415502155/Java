package sng.entity;

import sng.util.DateUtil;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

/**
 * 学期表 Title: Term Description: 学期详细信息表 Company: 世纪伟业
 */
@Entity
@Table(name = "term")
public class Term implements Serializable {

	private static final long serialVersionUID = -1340534853146197481L;

	/**
	 * 学期表主键
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer term_id;
	/**
	 * 机构主键
	 */
	private Integer org_id;
	/**
	 * 学期名称
	 */
	private String term_name;
	/**
	 * 所属年份
	 */
	private String cur_year;
	/**
	 * 学期类型 (0:学期;1:假期)
	 */
	private Integer term_type;

	@Transient
	private String time;
	
	
	/***
	 * 开始时间
	 */
	private Date start_time;
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

	public Integer getTerm_id() {
		return term_id;
	}

	public void setTerm_id(Integer term_id) {
		this.term_id = term_id;
	}

	public Integer getOrg_id() {
		return org_id;
	}

	public void setOrg_id(Integer org_id) {
		this.org_id = org_id;
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

	public Integer getTerm_type() {
		return term_type;
	}

	public void setTerm_type(Integer term_type) {
		this.term_type = term_type;
	}

	public Date getStart_time() {
		return start_time;
	}

	public void setStart_time(Date start_time) {
		this.start_time = start_time;
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
	
	
	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		Date date = DateUtil.toDate(time);
		setStart_time(date);
		this.time = time;
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
