package cn.edugate.esb.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 键值
 * Title:KeyValue
 * Description:键值对对象，按type类型分类
 * Company:SJWY
 * @author:Liuy
 * @Date:2017年7月17日上午9:54:20
 */
@Entity
@Table(name ="keyvalue")
public class KeyValue{
	/**
	 * 主键
	 */
	private Integer id;
	/**
	 * 键
	 */
	private String key;
	/**
	 * 值
	 */
	private String value;
	/**
	 * 类型(1:机构UI包2菜单分组category)
	 */
	private Integer type;
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getKey() {
		return key;
	}
	public void setKey(String key) {
		this.key = key;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	public Integer getType() {
		return type;
	}
	public void setType(Integer type) {
		this.type = type;
	}
}

