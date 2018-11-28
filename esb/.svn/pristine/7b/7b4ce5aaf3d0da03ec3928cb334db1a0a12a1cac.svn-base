package cn.edugate.esb.entity;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 功能适用范围
 * Title:FunctionUseRange
 * Description:
 * Company:SJWY
 * @author:Liuy
 * @Date:2017年5月11日下午3:17:38
 */
@Entity
@Table(name ="`fun_use_range`")
public class FunctionUseRange {
	//适用范围逐渐
	private Integer fur_id;
	//功能主键
	private Integer fun_id;
	//适用类型 （0学校，2培训机构，3教育局）
	private Integer fur_type;
	//类型（1：幼儿；2：小学  3：初中年级；4：高中年级；5：特殊类）
	private Integer org_type;
	//幼儿园11-14，小学21-26，初中31-33，高中41-43）
	private Integer grade_number;
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
	public Integer getFur_id() {
		return fur_id;
	}
	public void setFur_id(Integer fur_id) {
		this.fur_id = fur_id;
	}
	public Integer getFun_id() {
		return fun_id;
	}
	public void setFun_id(Integer fun_id) {
		this.fun_id = fun_id;
	}
	public Integer getFur_type() {
		return fur_type;
	}
	public void setFur_type(Integer fur_type) {
		this.fur_type = fur_type;
	}
	public Integer getOrg_type() {
		return org_type;
	}
	public void setOrg_type(Integer org_type) {
		this.org_type = org_type;
	}
	public Integer getGrade_number() {
		return grade_number;
	}
	public void setGrade_number(Integer grade_number) {
		this.grade_number = grade_number;
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
}
