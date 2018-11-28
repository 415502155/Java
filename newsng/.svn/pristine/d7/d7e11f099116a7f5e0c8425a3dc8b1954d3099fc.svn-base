package sng.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 操作日志 
 * Title: OperationLog 
 * Description:记录系统操作动作的过程
 * Company: 世纪伟业
 * @author 马国庆
 * @date 2018年10月23日上午10:51
 */
@Entity
@Table(name = "operation_log")
public class OperationLog implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 3497685449958393467L;


	public Integer getOperation_id() {
		return operation_id;
	}

	public void setOperation_id(Integer operation_id) {
		this.operation_id = operation_id;
	}

	public Integer getOrg_id() {
		return org_id;
	}

	public void setOrg_id(Integer org_id) {
		this.org_id = org_id;
	}

	public Integer getCam_id() {
		return cam_id;
	}

	public void setCam_id(Integer cam_id) {
		this.cam_id = cam_id;
	}

	public Integer getTarget_id() {
		return target_id;
	}

	public void setTarget_id(Integer target_id) {
		this.target_id = target_id;
	}



	public String getTarget_name() {
		return target_name;
	}

	public void setTarget_name(String target_name) {
		this.target_name = target_name;
	}

	public Integer getTarget_type() {
		return target_type;
	}

	public void setTarget_type(Integer target_type) {
		this.target_type = target_type;
	}

	public Integer getAction() {
		return action;
	}

	public void setAction(Integer action) {
		this.action = action;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Integer getUser_type() {
		return user_type;
	}

	public void setUser_type(Integer user_type) {
		this.user_type = user_type;
	}

	public Integer getUser_type_id() {
		return user_type_id;
	}

	public void setUser_type_id(Integer user_type_id) {
		this.user_type_id = user_type_id;
	}

	public String getUser_type_name() {
		return user_type_name;
	}

	public void setUser_type_name(String user_type_name) {
		this.user_type_name = user_type_name;
	}

	public Date getOperation_time() {
		return operation_time;
	}

	public void setOperation_time(Date operation_time) {
		this.operation_time = operation_time;
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

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "operation_id")
	private Integer operation_id;// 操作日志主键不能重复

	@Column(name = "org_id")
	private Integer org_id; // 机构ID

	@Column(name = "cam_id")
	private Integer cam_id; // 校区ID

	@Column(name = "target_id")
	private Integer target_id;// 操作对象ID

	@Column(name = "target_name")
	private String target_name;// 操作对象名称

	@Column(name = "target_type")
	private Integer target_type;// 操作对象类型（班级、学员）

	@Column(name = "action")
	private Integer action;// 操作动作

	@Column(name = "content")
	private String content;// 操作内容
	
	@Column(name="user_type")
	private Integer user_type; //用户类型 0：家长  1：教师

	@Column(name = "user_type_id")
	private Integer user_type_id;// 用户类型ID

	@Column(name = "user_type_name")
	private String user_type_name;// 用户类型名称

	@Column(name = "operation_time")
	private Date operation_time; // 操作时间

	@Column(name = "is_del")
	private Integer is_del; // 删除标记 是否删除(0:否；1：是)

	@Column(name = "insert_time")
	private Date insert_time;// 插入时间

	@Column(name = "update_time")
	private Date update_time;// 更新时间

	@Column(name = "del_time")
	private Date del_time;// 删除时间



}
