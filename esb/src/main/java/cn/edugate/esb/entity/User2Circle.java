package cn.edugate.esb.entity;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name ="user2circle")
public class User2Circle{
	// 用户2机构圈ID
	private Integer user2cir_id;
	// 机构圈ID
	private Integer circle_id;
	// 用户ID
	private Integer sso_id;
	// 是否删除
	private Integer is_del;
	// 创建时间
	private Date insert_time;
	// 修改时间
	private Date update_time;
	// 删除时间
	private Date del_time;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	public Integer getUser2cir_id() {
		return user2cir_id;
	}
	public void setUser2cir_id(Integer user2cir_id) {
		this.user2cir_id = user2cir_id;
	}
	public Integer getCircle_id() {
		return circle_id;
	}
	public void setCircle_id(Integer circle_id) {
		this.circle_id = circle_id;
	}
	public Integer getSso_id() {
		return sso_id;
	}
	public void setSso_id(Integer sso_id) {
		this.sso_id = sso_id;
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

