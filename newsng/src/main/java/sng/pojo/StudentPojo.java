package sng.pojo;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * @类 名： StudentList
 * @功能描述：学员 
 * @作者信息： LiuYang
 * @创建时间： 2018年11月21日上午10:38:46
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class StudentPojo implements java.io.Serializable {

	/**
	 *@Fields serialVersionUID : 
	 */
	private static final long serialVersionUID = 7928597668103770659L;
	/**
	 * 学生主键
	 */
	private Integer stud_id;
	/**
	 * 学生姓名
	 */
	private String stud_name;
	/**
	 * 学生证件号码
	 */
	private String user_idnumber;
	/**
	 * 班级主键
	 */
	private Integer clas_id;
	/**
	 * 班级名称
	 */
	private String clas_name;
	/**
	 * 教师的userId
	 */
	private Integer teacher_user_id;
	/**
	 * 教师主键
	 */
	private Integer tech_id;
	/**
	 * 教师姓名（多个用逗号连接）
	 */
	private String tech_name;
	/**
	 * 家长的userId
	 */
	private Integer parent_user_id;
	/**
	 * 家长主键
	 */
	private Integer parent_id;
	/**
	 * 家长姓名（多个用逗号连接）
	 */
	private String parent_name;
	/**
	 * 学员状态
	 */
	private Integer stu_status;
	/**
	 * 班级收费标准
	 */
	private String tuition_fees;
	/**
	 * 学费支付方式
	 */
	private Integer pay_method;
	/**
	 * 支付详情主键
	 */
	private Integer cd_id;
	/**
	 * 班级学费支付项目主键
	 */
	private Integer cid;
	/**
	 * 学费支付状态
	 */
	private Integer status;
	/**
	 * 学费应付总额
	 */
	private String money;
	/**
	 * 学费当前余额
	 */
	private String txnAmt;
	/**
	 * 学费退费总额
	 */
	private String tf_money;
	/**
	 * 学生班级关系主键
	 */
	private Integer stu_clas_id;
	
	/** 
	 * 学生姓名  
	 */
	public String getStud_name() {
		return stud_name;
	}
	/** 
	 * 学生姓名  
	 */
	public void setStud_name(String stud_name) {
		this.stud_name = stud_name;
	}
	/** 
	 * 学生证件号码  
	 */
	public String getUser_idnumber() {
		return user_idnumber;
	}
	/** 
	 * 学生证件号码  
	 */
	public void setUser_idnumber(String user_idnumber) {
		this.user_idnumber = user_idnumber;
	}
	/** 
	 * 班级名称  
	 */
	public String getClas_name() {
		return clas_name;
	}
	/** 
	 * 班级名称  
	 */
	public void setClas_name(String clas_name) {
		this.clas_name = clas_name;
	}
	/** 
	 * 教师姓名（多个用逗号连接）  
	 */
	public String getTech_name() {
		return tech_name;
	}
	/** 
	 * 教师姓名（多个用逗号连接）  
	 */
	public void setTech_name(String tech_name) {
		this.tech_name = tech_name;
	}
	/** 
	 * 学员状态  
	 */
	public Integer getStu_status() {
		return stu_status;
	}
	/** 
	 * 学员状态  
	 */
	public void setStu_status(Integer stu_status) {
		this.stu_status = stu_status;
	}
	/** 
	 * 班级收费标准  
	 */
	public String getTuition_fees() {
		return tuition_fees;
	}
	/** 
	 * 班级收费标准  
	 */
	public void setTuition_fees(String tuition_fees) {
		this.tuition_fees = tuition_fees;
	}
	/** 
	 * 学费支付方式  
	 */
	public Integer getPay_method() {
		return pay_method;
	}
	/** 
	 * 学费支付方式  
	 */
	public void setPay_method(Integer pay_method) {
		this.pay_method = pay_method;
	}
	/** 
	 * 学费支付状态  
	 */
	public Integer getStatus() {
		return status;
	}
	/** 
	 * 学费支付状态  
	 */
	public void setStatus(Integer status) {
		this.status = status;
	}
	/** 
	 * 学费应付总额  
	 */
	public String getMoney() {
		return money;
	}
	/** 
	 * 学费应付总额  
	 */
	public void setMoney(String money) {
		this.money = money;
	}
	/** 
	 * 学费当前余额  
	 */
	public String getTxnAmt() {
		return txnAmt;
	}
	/** 
	 * 学费当前余额  
	 */
	public void setTxnAmt(String txnAmt) {
		this.txnAmt = txnAmt;
	}
	/** 
	 * 学费退费总额  
	 */
	public String getTf_money() {
		return tf_money;
	}
	/** 
	 * 学费退费总额  
	 */
	public void setTf_money(String tf_money) {
		this.tf_money = tf_money;
	}
	/** 
	 * 学生主键  
	 */
	public Integer getStud_id() {
		return stud_id;
	}
	/** 
	 * 学生主键  
	 */
	public void setStud_id(Integer stud_id) {
		this.stud_id = stud_id;
	}
	/** 
	 * 班级主键  
	 */
	public Integer getClas_id() {
		return clas_id;
	}
	/** 
	 * 班级主键  
	 */
	public void setClas_id(Integer clas_id) {
		this.clas_id = clas_id;
	}
	/** 
	 * 支付详情主键  
	 */
	public Integer getCd_id() {
		return cd_id;
	}
	/** 
	 * 支付详情主键  
	 */
	public void setCd_id(Integer cd_id) {
		this.cd_id = cd_id;
	}
	/** 
	 * 班级学费支付项目主键  
	 */
	public Integer getCid() {
		return cid;
	}
	/** 
	 * 班级学费支付项目主键  
	 */
	public void setCid(Integer cid) {
		this.cid = cid;
	}
	/** 
	 * 学生班级关系主键  
	 */
	public Integer getStu_clas_id() {
		return stu_clas_id;
	}
	/** 
	 * 学生班级关系主键  
	 */
	public void setStu_clas_id(Integer stu_clas_id) {
		this.stu_clas_id = stu_clas_id;
	}
	/** 
	 * 教师的userId  
	 */
	public Integer getTeacher_user_id() {
		return teacher_user_id;
	}
	/** 
	 * 教师的userId  
	 */
	public void setTeacher_user_id(Integer teacher_user_id) {
		this.teacher_user_id = teacher_user_id;
	}
	/** 
	 * 教师主键  
	 */
	public Integer getTech_id() {
		return tech_id;
	}
	/** 
	 * 教师主键  
	 */
	public void setTech_id(Integer tech_id) {
		this.tech_id = tech_id;
	}
	/** 
	 * 家长的userId  
	 */
	public Integer getParent_user_id() {
		return parent_user_id;
	}
	/** 
	 * 家长的userId  
	 */
	public void setParent_user_id(Integer parent_user_id) {
		this.parent_user_id = parent_user_id;
	}
	/** 
	 * 家长主键  
	 */
	public Integer getParent_id() {
		return parent_id;
	}
	/** 
	 * 家长主键  
	 */
	public void setParent_id(Integer parent_id) {
		this.parent_id = parent_id;
	}
	/** 
	 * 家长姓名（多个用逗号连接）  
	 */
	public String getParent_name() {
		return parent_name;
	}
	/** 
	 * 家长姓名（多个用逗号连接）  
	 */
	public void setParent_name(String parent_name) {
		this.parent_name = parent_name;
	}
	
}
