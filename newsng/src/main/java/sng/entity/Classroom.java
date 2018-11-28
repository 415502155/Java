package sng.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

/**
 * 教室表 
 * Title: Classroom 
 * Description: 教室详细信息表 
 * Company: 世纪伟业
 */
@Entity
@Table(name = "classroom")
public class Classroom implements Serializable {

	private static final long serialVersionUID = -1240534923446197481L;

	/**
	 * 教室表主键
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer classroom_id;
	/**
	 * 校区ID
	 */
	private Integer cam_id;
	
	@Transient
	private Integer org_id;
	/***
	 * 教室名称
	 */
	private String classroom_name;
	/***
	 * 所在教学楼
	 */
	private String building;
	/***
	 * 所在楼层
	 */
	private String floor;
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
	
	
	
	
	public Integer getOrg_id() {
		return org_id;
	}

	public void setOrg_id(Integer org_id) {
		this.org_id = org_id;
	}

	public Integer getClassroom_id() {
		return classroom_id;
	}

	public void setClassroom_id(Integer classroom_id) {
		this.classroom_id = classroom_id;
	}

	public Integer getCam_id() {
		return cam_id;
	}

	public void setCam_id(Integer cam_id) {
		this.cam_id = cam_id;
	}

	public String getClassroom_name() {
		return classroom_name;
	}

	public void setClassroom_name(String classroom_name) {
		this.classroom_name = classroom_name;
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

	
}
