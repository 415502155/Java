package cn.edugate.esb.entity;

import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name ="org_icon")
public class OrgIcon{
	/**
	 * 关系主键
	 */
	private Integer oi_id;
	/**
	 * 图标主键
	 */
	private Integer icon_id;
	/**
	 * 机构主键
	 */
	private Integer org_id;
	/**
	 * 机构顺序(默认为Icon.order)
	 */
	private Integer org_order;
	/**
	 * 教师端是否显示图标【0:不显示,1:显示】
	 */
	private Integer t_show;
	/**
	 * 学生端是否显示图标【0:不显示,1:显示】
	 */
	private Integer s_show;
	/**
	 * 家长端是否显示图标【0:不显示,1:显示】
	 */
	private Integer p_show;
	/**
	 * WEB端图标是否显示【0:不显示,1:显示】
	 */
	private Integer w_show;
	/**
	 * 启用状态【0:不启用,1:启用】
	 */
	private Integer status;
	/**
	 * 创建时间
	 */
	private Date insert_time;
	/**
	 * 删除时间
	 */
	private Date del_time;
	/**
	 * 删除标记
	 */
	private Integer is_del;
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	public Integer getOi_id() {
		return oi_id;
	}
	public void setOi_id(Integer oi_id) {
		this.oi_id = oi_id;
	}
	public Integer getIcon_id() {
		return icon_id;
	}
	public void setIcon_id(Integer icon_id) {
		this.icon_id = icon_id;
	}
	public Integer getOrg_id() {
		return org_id;
	}
	public void setOrg_id(Integer org_id) {
		this.org_id = org_id;
	}
	public Integer getOrg_order() {
		return org_order;
	}
	public void setOrg_order(Integer org_order) {
		this.org_order = org_order;
	}
	public Integer getT_show() {
		return t_show;
	}
	public void setT_show(Integer t_show) {
		this.t_show = t_show;
	}
	public Integer getS_show() {
		return s_show;
	}
	public void setS_show(Integer s_show) {
		this.s_show = s_show;
	}
	public Integer getP_show() {
		return p_show;
	}
	public void setP_show(Integer p_show) {
		this.p_show = p_show;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public Date getInsert_time() {
		return insert_time;
	}
	public void setInsert_time(Date insert_time) {
		this.insert_time = insert_time;
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
	public Integer getW_show() {
		return w_show;
	}
	public void setW_show(Integer w_show) {
		this.w_show = w_show;
	}

}

