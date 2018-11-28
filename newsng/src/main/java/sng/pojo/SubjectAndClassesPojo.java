package sng.pojo;
/**
 * 科目与班级关系对象
 * @author magq
 *
 */
public class SubjectAndClassesPojo {
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
	
	/**
	 * 班级ID
	 */
	private Integer clas_id;
	/**
	 * 班级名称
	 */
	private String clas_name;
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
	public Integer getClas_id() {
		return clas_id;
	}
	public void setClas_id(Integer clas_id) {
		this.clas_id = clas_id;
	}
	public String getClas_name() {
		return clas_name;
	}
	public void setClas_name(String clas_name) {
		this.clas_name = clas_name;
	}
	@Override
	public String toString() {
		return "SubjectAndClassesPojo [subject_id=" + subject_id + ", org_id=" + org_id + ", category_id=" + category_id
				+ ", category_name=" + category_name + ", subject_name=" + subject_name + ", clas_id=" + clas_id
				+ ", clas_name=" + clas_name + "]";
	}
	
	

}
