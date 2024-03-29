package sng.pojo;

import java.util.ArrayList;
import java.util.List;

/**
 * 类目科目类
 * @author magq
 *
 */
public class CategoryAndSubjectPojo {
	/**
	 * 科目id
	 */
	private Integer subject_id;
	/**
	 * 机构主键
	 */
	private Integer org_id;
	/**
	 * 类目ID
	 */
	private Integer category_id;
	
	/**
	 * 类目名称
	 */
	private String category_name;
	/***
	 * 科目名称
	 */
	private String subject_name;
	
	/***
	 * 是否删除 (0:否；1：是)
	 */
	private Integer is_del;


	private List<CategoryAndSubjectPojo> list=new ArrayList<>();


	public Integer getSubject_id() {
		return subject_id;
	}

	public void setSubject_id(Integer subject_id) {
		this.subject_id = subject_id;
	}

	public Integer getOrg_id() {
		return org_id;
	}

	public void setOrg_id(Integer org_id) {
		this.org_id = org_id;
	}

	public Integer getCategory_id() {
		return category_id;
	}

	public void setCategory_id(Integer category_id) {
		this.category_id = category_id;
	}

	public String getCategory_name() {
		return category_name;
	}

	public void setCategory_name(String category_name) {
		this.category_name = category_name;
	}

	public String getSubject_name() {
		return subject_name;
	}

	public void setSubject_name(String subject_name) {
		this.subject_name = subject_name;
	}

	public Integer getIs_del() {
		return is_del;
	}

	public void setIs_del(Integer is_del) {
		this.is_del = is_del;
	}

	public List<CategoryAndSubjectPojo> getList() {
		return list;
	}

	public void setList(List<CategoryAndSubjectPojo> list) {
		this.list = list;
	}

	@Override
	public String toString() {
		return "CategoryAndSubjectPojo [subject_id=" + subject_id + ", org_id=" + org_id + ", category_id="
				+ category_id + ", category_name=" + category_name + ", subject_name=" + subject_name + ", is_del="
				+ is_del + "]";
	}
	
	
	

}
