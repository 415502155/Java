package sng.pojo.base;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name ="role_code",catalog="`edugate_base`")
public class RoleCode implements Serializable {

	private static final long serialVersionUID = 4129810201724317030L;
	/**
	 * 主键
	 */
	private Integer role_code_id;
	/**
	 * 权限编码
	 */
	private String code;
	/**
	 * 权限值
	 */
	private Integer value;
	/**
	 * 权限名称
	 */
	private String name;
	/**
	 * 父级主键
	 */
	private Integer parent_id;
	/**
	 * 使用机构类型（已弃用）
	 */
	private Integer type;
	/**
	 * 删除标记
	 */
	private Integer is_del;
	/**
	 * 子编码
	 */
	private List<RoleCode> list;
	
	@Id
	public Integer getRole_code_id() {
		return role_code_id;
	}
	public void setRole_code_id(Integer role_code_id) {
		this.role_code_id = role_code_id;
	}
	public Integer getValue() {
		return value;
	}
	public void setValue(Integer value) {
		this.value = value;
	}
	public Integer getParent_id() {
		return parent_id;
	}
	public void setParent_id(Integer parent_id) {
		this.parent_id = parent_id;
	}
	@Deprecated
	public Integer getType() {
		return type;
	}
	@Deprecated
	public void setType(Integer type) {
		this.type = type;
	}
	public Integer getIs_del() {
		return is_del;
	}
	public void setIs_del(Integer is_del) {
		this.is_del = is_del;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	@Transient
	public List<RoleCode> getList() {
		return list;
	}
	public void setList(List<RoleCode> list) {
		this.list = list;
	}
	
}
