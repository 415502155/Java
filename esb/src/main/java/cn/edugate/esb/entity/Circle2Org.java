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
@Table(name ="circle2org")
public class Circle2Org{
	// 机构圈2机构ID
	private Integer cir2org_id;
	// 机构圈id
	private Integer circle_id;
	// 机构id
	private Integer org_id;
	// 等级（1,2）
	private Integer rank;
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
	public Integer getCir2org_id() {
		return cir2org_id;
	}
	public void setCir2org_id(Integer cir2org_id) {
		this.cir2org_id = cir2org_id;
	}
	public Integer getCircle_id() {
		return circle_id;
	}
	public void setCircle_id(Integer circle_id) {
		this.circle_id = circle_id;
	}
	public Integer getOrg_id() {
		return org_id;
	}
	public void setOrg_id(Integer org_id) {
		this.org_id = org_id;
	}
	public Integer getRank() {
		return rank;
	}
	public void setRank(Integer rank) {
		this.rank = rank;
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

