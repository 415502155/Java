package cn.edugate.esb.entity;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name ="`module2function`")
public class Module2Function{
	// 模块2功能ID
	private Integer mod2fun_id;
	// 模块ID
	private Integer mod_id;
	// 功能ID
	private Integer fun_id;
	// 创建时间
	private Date insert_time;
	// 修改时间
	private Date update_time;
	// 删除时间
	private Date del_time;
	// 是否删除
	private Integer is_del;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	public Integer getMod2fun_id() {
		return mod2fun_id;
	}
	public void setMod2fun_id(Integer mod2fun_id) {
		this.mod2fun_id = mod2fun_id;
	}
	public Integer getMod_id() {
		return mod_id;
	}
	public void setMod_id(Integer mod_id) {
		this.mod_id = mod_id;
	}
	public Integer getFun_id() {
		return fun_id;
	}
	public void setFun_id(Integer fun_id) {
		this.fun_id = fun_id;
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

