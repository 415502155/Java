package cn.edugate.esb.entity;

import java.math.BigInteger;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.springframework.beans.factory.annotation.Autowired;

import cn.edugate.esb.util.Util;

@Entity
@Table(name ="`function`")
public class Function{
	private Util util;
	@Autowired
	public void setUtil(Util util) {
		this.util = util;
	}
	// 功能ID
	private Integer fun_id;
	// 功能CODE
	private String fun_code;
	// 功能名称
	private String fun_name;
	// 功能地址
	private String fun_url;
	// 功能备注
	private String fun_note;
	// 功能状态(1未用2启用3过期)
	private Integer fun_status;
	// 功能版本
	private String fun_version;
	// 功能顺序
	private Integer fun_order;
	// 创建时间
	private Date insert_time;
	// 修改时间
	private Date update_time;
	// 删除时间
	private Date del_time;
	// 是否删除
	private Integer is_del;
	// 适用范围
	private String userRange;
	// 使用数量
	private BigInteger useingNumber;
	// 模块主键
	private Integer mod_id;
	/**
	 * 分类
	 */
	private Integer category;
	/**
	 * 图标
	 */
	private String logo;
	/**
	 * 机构主键Transient
	 */
	private Integer org_id;
	/**
	 * 父级主键Transient
	 */
	private Integer parent_id;

	private String logo_url;
	
	@Transient
	public String getLogo_url() {
		if (util!=null&&this.logo!=null&&!"".equals(this.logo)) {
			this.logo_url = util.getPathByPicName(this.logo);
		} else {
			this.logo_url = "";
		}
		return logo_url;
	}
	public void setLogo_url(String logo_url) {
		this.logo_url = logo_url;
	}
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	public Integer getFun_id() {
		return fun_id;
	}
	public void setFun_id(Integer fun_id) {
		this.fun_id = fun_id;
	}
	public String getFun_code() {
		return fun_code;
	}
	public void setFun_code(String fun_code) {
		this.fun_code = fun_code;
	}
	public String getFun_name() {
		return fun_name;
	}
	public void setFun_name(String fun_name) {
		this.fun_name = fun_name;
	}
	public String getFun_url() {
		return fun_url;
	}
	public void setFun_url(String fun_url) {
		this.fun_url = fun_url;
	}
	public String getFun_note() {
		return fun_note;
	}
	public void setFun_note(String fun_note) {
		this.fun_note = fun_note;
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
	public Integer getFun_status() {
		return fun_status;
	}
	public void setFun_status(Integer fun_status) {
		this.fun_status = fun_status;
	}
	public String getFun_version() {
		return fun_version;
	}
	public void setFun_version(String fun_version) {
		this.fun_version = fun_version;
	}
	public Integer getFun_order() {
		return fun_order;
	}
	public void setFun_order(Integer fun_order) {
		this.fun_order = fun_order;
	}
	@Transient
	public String getUserRange() {
		return userRange;
	}
	public void setUserRange(String userRange) {
		this.userRange = userRange;
	}
	@Transient
	public BigInteger getUseingNumber() {
		return useingNumber;
	}
	public void setUseingNumber(BigInteger useingNumber) {
		this.useingNumber = useingNumber;
	}
	@Transient
	public Integer getMod_id() {
		return mod_id;
	}
	public void setMod_id(Integer mod_id) {
		this.mod_id = mod_id;
	}
	@Transient
	public Integer getOrg_id() {
		return org_id;
	}
	public void setOrg_id(Integer org_id) {
		this.org_id = org_id;
	}
	@Transient
	public Integer getParent_id() {
		return parent_id;
	}
	public void setParent_id(Integer parent_id) {
		this.parent_id = parent_id;
	}
	public String getLogo() {
		return logo;
	}
	public void setLogo(String logo) {
		this.logo = logo;
	}
	public Integer getCategory() {
		return category;
	}
	public void setCategory(Integer category) {
		this.category = category;
	}
	
}

