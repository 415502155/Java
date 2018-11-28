package cn.edugate.esb.entity;

import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name ="`module`")
public class Module{
	// 模块ID
	private Integer mod_id;
	// 应用ID
	private String mod_code;
	// 模块名称
	private String mod_name;
	// 模块备注
	private String mod_note;
	// 模块顺序
	private Integer mod_order;
	// 创建时间
	private Date insert_time;
	// 修改时间
	private Date update_time;
	// 删除时间
	private Date del_time;
	// 是否删除
	private Integer is_del;
	// 下属功能
	private List<Function> functionList;
	/**
	 * 图标
	 */
	private String logo;
	/**
	 * 机构主键Transient
	 */
	private Integer org_id;
	/**
	 * 分类
	 */
	private Integer category;
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	public Integer getMod_id() {
		return mod_id;
	}
	public void setMod_id(Integer mod_id) {
		this.mod_id = mod_id;
	}
	public String getMod_code() {
		return mod_code;
	}
	public void setMod_code(String mod_code) {
		this.mod_code = mod_code;
	}
	public String getMod_name() {
		return mod_name;
	}
	public void setMod_name(String mod_name) {
		this.mod_name = mod_name;
	}
	public String getMod_note() {
		return mod_note;
	}
	public void setMod_note(String mod_note) {
		this.mod_note = mod_note;
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
	public Integer getMod_order() {
		return mod_order;
	}
	public void setMod_order(Integer mod_order) {
		this.mod_order = mod_order;
	}
	@Transient
	public List<Function> getFunctionList() {
		return functionList;
	}
	public void setFunctionList(List<Function> functionList) {
		this.functionList = functionList;
	}
	@Transient
	public Integer getOrg_id() {
		return org_id;
	}
	public void setOrg_id(Integer org_id) {
		this.org_id = org_id;
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

