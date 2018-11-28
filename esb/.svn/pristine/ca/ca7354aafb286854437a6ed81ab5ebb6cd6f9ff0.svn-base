package cn.edugate.esb.entity;

import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.springframework.beans.factory.annotation.Autowired;

import cn.edugate.esb.util.Util;

@JsonIgnoreProperties(ignoreUnknown = true)
@Entity
@Table(name ="`menu`")
public class Menu {
	
	private Util util;
	@Autowired
	public void setUtil(Util util) {
		this.util = util;
	}
	
	// 菜单主键
	private Integer menu_id;
	// 机构主键
	private Integer org_id;
	// 功能主键
	private Integer fun_id;
	// 模块主键
	private Integer mod_id;
	// 菜单打开方式(0默认1新窗口)
	private Integer open_mode;
	// 菜单地址(自定义菜单)
	private String menu_url;
	// 菜单名称
	private String menu_name;
	// 菜单备注
	private String menu_note;
	// 菜单顺序
	private Integer menu_order;
	// 父级主键
	private Integer parent_id;
	// 菜单类型(0 使用 1未使用 2 试用中)
	private Integer menu_type;
	// 菜单状态(0正常 1 维护 2 过期)
	private Integer menu_status;
	// 添加时间
	private Date insert_time;
	// 更新时间
	private Date update_time;
	// 删除时间
	private Date del_time;
	// 是否删除
	private Integer is_del;
	// 菜单编码
	private String fun_code;
	
	// 菜单编码
	private String menu_photo;
	
	
	// 菜单编码
		private String menu_photo_url;
	
	/**
	 * 菜单分类
	 */
	private Integer category;
		
	/**
	 * 子菜单
	 */
	private List<Menu> smenu;
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	public Integer getMenu_id() {
		return menu_id;
	}
	public void setMenu_id(Integer menu_id) {
		this.menu_id = menu_id;
	}
	public Integer getOrg_id() {
		return org_id;
	}
	public void setOrg_id(Integer org_id) {
		this.org_id = org_id;
	}
	public Integer getFun_id() {
		return fun_id;
	}
	public void setFun_id(Integer fun_id) {
		this.fun_id = fun_id;
	}
	public String getMenu_name() {
		return menu_name;
	}
	public void setMenu_name(String menu_name) {
		this.menu_name = menu_name;
	}
	public String getMenu_note() {
		return menu_note;
	}
	public void setMenu_note(String menu_note) {
		this.menu_note = menu_note;
	}
	public Integer getMenu_order() {
		return menu_order;
	}
	public void setMenu_order(Integer menu_order) {
		this.menu_order = menu_order;
	}
	public Integer getParent_id() {
		return parent_id;
	}
	public void setParent_id(Integer parent_id) {
		this.parent_id = parent_id;
	}
	public Integer getMenu_type() {
		return menu_type;
	}
	public void setMenu_type(Integer menu_type) {
		this.menu_type = menu_type;
	}
	public Integer getMenu_status() {
		return menu_status;
	}
	public void setMenu_status(Integer menu_status) {
		this.menu_status = menu_status;
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
	public Integer getMod_id() {
		return mod_id;
	}
	public void setMod_id(Integer mod_id) {
		this.mod_id = mod_id;
	}
	public Integer getOpen_mode() {
		return open_mode;
	}
	public void setOpen_mode(Integer open_mode) {
		this.open_mode = open_mode;
	}
	public String getMenu_url() {
		return menu_url;
	}
	public void setMenu_url(String menu_url) {
		this.menu_url = menu_url;
	}
	@Transient
	public List<Menu> getSmenu() {
		return smenu;
	}
	public void setSmenu(List<Menu> smenu) {
		this.smenu = smenu;
	}
	@Transient
	public String getFun_code() {
		return fun_code;
	}
	public void setFun_code(String fun_code) {
		this.fun_code = fun_code;
	}
	public String getMenu_photo() {
		return menu_photo;
	}
	public void setMenu_photo(String menu_photo) {
		this.menu_photo = menu_photo;
	}
	@Transient
	public String getMenu_photo_url() {
		if (util!=null&&this.menu_photo!=null&&!"".equals(this.menu_photo)) {
			this.menu_photo_url = util.getPathByPicName(this.menu_photo);
		} else {
			this.menu_photo_url = "";
		}
		return menu_photo_url;
	}
	public void setMenu_photo_url(String menu_photo_url) {
		this.menu_photo_url = menu_photo_url;
	}
	public Integer getCategory() {
		return category;
	}
	public void setCategory(Integer category) {
		this.category = category;
	}
	
}
