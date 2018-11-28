package cn.edugate.esb.entity;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name ="user2rank")
public class User2Rank{
	// 用户2等级ID
	private Integer user2rank_id;
	// 用户ID
	private Integer sso_id;
	// 等级ID
	private String rank_id;
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
	public Integer getUser2rank_id() {
		return user2rank_id;
	}
	public void setUser2rank_id(Integer user2rank_id) {
		this.user2rank_id = user2rank_id;
	}
	public Integer getSso_id() {
		return sso_id;
	}
	public void setSso_id(Integer sso_id) {
		this.sso_id = sso_id;
	}
	public String getRank_id() {
		return rank_id;
	}
	public void setRank_id(String rank_id) {
		this.rank_id = rank_id;
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

