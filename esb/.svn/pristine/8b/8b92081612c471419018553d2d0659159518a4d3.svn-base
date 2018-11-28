package cn.edugate.esb.entity;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name ="rank")
public class Rank{
	// 等级ID
	private Integer rank_id;
	// 机构ID
	private Integer org_id;
	// 等级名称
	private String rank_name;
	// 值
	private String rank_val;
	// 等级类型
	private Integer rank_type;
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
	public Integer getRank_id() {
		return rank_id;
	}
	public void setRank_id(Integer rank_id) {
		this.rank_id = rank_id;
	}
	public Integer getOrg_id() {
		return org_id;
	}
	public void setOrg_id(Integer org_id) {
		this.org_id = org_id;
	}
	public String getRank_name() {
		return rank_name;
	}
	public void setRank_name(String rank_name) {
		this.rank_name = rank_name;
	}
	public String getRank_val() {
		return rank_val;
	}
	public void setRank_val(String rank_val) {
		this.rank_val = rank_val;
	}
	public Integer getRank_type() {
		return rank_type;
	}
	public void setRank_type(Integer rank_type) {
		this.rank_type = rank_type;
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

