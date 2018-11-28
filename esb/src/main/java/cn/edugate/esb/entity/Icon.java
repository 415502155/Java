package cn.edugate.esb.entity;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
@Entity
@Table(name ="icon")
public class Icon{

	/**
	 * 主键
	 */
	private Integer icon_id;
	/**
	 * 顺序号
	 */
	private Integer order;
	/**
	 * [教师]图标编码，对应页面上的id
	 */
	private String t_key;
	/**
	 * [教师]图标中文名称
	 */
	private String t_name;
	/**
	 * [教师]图标点击事件跳转地址
	 */
	private String t_href;
	/**
	 * [教师]图标LOGO地址或class
	 */
	private String t_image;
	/**
	 * [教师]图标是否显示
	 * 0：不显示
	 * 1：显示
	 */
	private Integer t_show;
	/**
	 * [家长]图标编码，对应页面上的id
	 */
	private String p_key;
	/**
	 * [家长]图标中文名称
	 */
	private String p_name;
	/**
	 * [家长]图标点击事件跳转地址
	 */
	private String p_href;
	/**
	 * [家长]图标LOGO地址或class
	 */
	private String p_image;
	/**
	 * [家长]图标是否显示
	 * 0：不显示
	 * 1：显示
	 */
	private Integer p_show;
	/**
	 * [学生]图标编码，对应页面上的id
	 */
	private String s_key;
	/**
	 * [学生]图标中文名称
	 */
	private String s_name;
	/**
	 * [学生]图标点击事件跳转地址
	 */
	private String s_href;
	/**
	 * [学生]图标LOGO地址或class
	 */
	private String s_image;
	/**
	 * [学生]图标是否显示
	 * 0：不显示
	 * 1：显示
	 */
	private Integer s_show;
	/**
	 * [WEB]图标编码，对应页面上的id
	 */
	private String w_key;
	/**
	 * [WEB]图标中文名称
	 */
	private String w_name;
	/**
	 * [WEB]图标点击事件跳转地址
	 */
	private String w_href;
	/**
	 * [WEB]图标LOGO地址或class
	 */
	private String w_image;
	/**
	 * [WEB]图标是否显示
	 * 0：不显示
	 * 1：显示
	 */
	private Integer w_show;
	/**
	 * 备注说明
	 */
	private String remarks;
	/**
	 * 使用状态
	 * 0：未启用
	 * 1：已启用
	 */
	private Integer status;
	/**
	 * 创建时间
	 */
	private Date insert_time;
	/**
	 * 修改时间
	 */
	private Date update_time;
	/**
	 * 删除时间
	 */
	private Date del_time;
	/**
	 * 删除标记
	 */
	private Integer is_del;
	/**
	 * 机构主键
	 */
	@Transient
	private Integer org_id;
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	public Integer getIcon_id() {
		return icon_id;
	}
	public void setIcon_id(Integer icon_id) {
		this.icon_id = icon_id;
	}
	public Integer getOrder() {
		return order;
	}
	public void setOrder(Integer order) {
		this.order = order;
	}
	public String getT_key() {
		return t_key;
	}
	public void setT_key(String t_key) {
		this.t_key = t_key;
	}
	public String getT_name() {
		return t_name;
	}
	public void setT_name(String t_name) {
		this.t_name = t_name;
	}
	public String getT_href() {
		return t_href;
	}
	public void setT_href(String t_href) {
		this.t_href = t_href;
	}
	public String getT_image() {
		return t_image;
	}
	public void setT_image(String t_image) {
		this.t_image = t_image;
	}
	public Integer getT_show() {
		return t_show;
	}
	public void setT_show(Integer t_show) {
		this.t_show = t_show;
	}
	public String getP_key() {
		return p_key;
	}
	public void setP_key(String p_key) {
		this.p_key = p_key;
	}
	public String getP_name() {
		return p_name;
	}
	public void setP_name(String p_name) {
		this.p_name = p_name;
	}
	public String getP_href() {
		return p_href;
	}
	public void setP_href(String p_href) {
		this.p_href = p_href;
	}
	public String getP_image() {
		return p_image;
	}
	public void setP_image(String p_image) {
		this.p_image = p_image;
	}
	public Integer getP_show() {
		return p_show;
	}
	public void setP_show(Integer p_show) {
		this.p_show = p_show;
	}
	public String getS_key() {
		return s_key;
	}
	public void setS_key(String s_key) {
		this.s_key = s_key;
	}
	public String getS_name() {
		return s_name;
	}
	public void setS_name(String s_name) {
		this.s_name = s_name;
	}
	public String getS_href() {
		return s_href;
	}
	public void setS_href(String s_href) {
		this.s_href = s_href;
	}
	public String getS_image() {
		return s_image;
	}
	public void setS_image(String s_image) {
		this.s_image = s_image;
	}
	public Integer getS_show() {
		return s_show;
	}
	public void setS_show(Integer s_show) {
		this.s_show = s_show;
	}
	public String getRemarks() {
		return remarks;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
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
	@Transient
	public Integer getOrg_id() {
		return org_id;
	}
	public void setOrg_id(Integer org_id) {
		this.org_id = org_id;
	}
	public String getW_key() {
		return w_key;
	}
	public void setW_key(String w_key) {
		this.w_key = w_key;
	}
	public String getW_name() {
		return w_name;
	}
	public void setW_name(String w_name) {
		this.w_name = w_name;
	}
	public String getW_href() {
		return w_href;
	}
	public void setW_href(String w_href) {
		this.w_href = w_href;
	}
	public String getW_image() {
		return w_image;
	}
	public void setW_image(String w_image) {
		this.w_image = w_image;
	}
	public Integer getW_show() {
		return w_show;
	}
	public void setW_show(Integer w_show) {
		this.w_show = w_show;
	}
	
}

