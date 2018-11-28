package cn.edugate.esb.pojo;

import java.math.BigInteger;

/**
 * 统计数据
 * Title: Data
 * Description: 
 * Company: 世纪伟业
 * @author Liuyang
 * @date 2018年5月13日下午1:54:59
 */
public class Data {
	
	/**
	 * 序号
	 */
	private Integer id;
	/**
	 * 学校名称
	 */
	private String org_name;
	/**
	 * 班级名称
	 */
	private String clas_name;
	/**
	 * 学生总数
	 */
	private BigInteger student_num;
	/**
	 * 学生关注总数
	 */
	private BigInteger student_bind;
	/**
	 * 学生关注比例
	 */
	private String student_rate;
	/**
	 * 教师总数
	 */
	private BigInteger teacher_num;
	/**
	 * 教师关注总数
	 */
	private BigInteger teacher_bind;
	/**
	 * 教师关注比例
	 */
	private String teacher_rate;
	/**
	 * 家长总人数
	 */
	private BigInteger parent_num;
	/**
	 * 家长关注总数
	 */
	private BigInteger parent_bind;
	/**
	 * 家长关注比例
	 */
	private String parent_rate;
	/**
	 * 教师姓名
	 */
	private String teacher_name;
	/**
	 * 内容
	 */
	private String note;
	/**
	 * 评论数
	 */
	private Integer pinglun_num;
	/**
	 * 点赞数
	 */
	private Integer dianzan_num;
	/**
	 * 时间
	 */
	private String time;
	/**
	 * 数量
	 */
	private Integer num;
	/**
	 * 类型
	 */
	private String type;
	/**
	 * 开始时间
	 */
	private String start_time;
	/**
	 * 截止时间
	 */
	private String end_time;
	/**
	 * 金额
	 */
	private String money;
	/**
	 * 应收人数
	 */
	private Integer ys_num;
	/**
	 * 实收人数
	 */
	private Integer ss_num;
	/**
	 * 应收总额
	 */
	private String ys_money;
	/**
	 * 实收总额
	 */
	private Double ss_money;
	/**
	 * 状态
	 */
	private String status;
	/**
	 * 部门名称
	 */
	private String dep_name;
	/**
	 * 手机号码
	 */
	private String mobile;
	/**
	 * 是否关注
	 */
	private String is_bind;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getOrg_name() {
		return org_name;
	}
	public void setOrg_name(String org_name) {
		this.org_name = org_name;
	}
	public String getClas_name() {
		return clas_name;
	}
	public void setClas_name(String clas_name) {
		this.clas_name = clas_name;
	}
	public BigInteger getStudent_num() {
		return student_num;
	}
	public void setStudent_num(BigInteger student_num) {
		this.student_num = student_num;
	}
	public BigInteger getStudent_bind() {
		return student_bind;
	}
	public void setStudent_bind(BigInteger student_bind) {
		this.student_bind = student_bind;
	}
	public String getStudent_rate() {
		return student_rate;
	}
	public void setStudent_rate(String student_rate) {
		this.student_rate = student_rate;
	}
	public BigInteger getTeacher_num() {
		return teacher_num;
	}
	public void setTeacher_num(BigInteger teacher_num) {
		this.teacher_num = teacher_num;
	}
	public BigInteger getTeacher_bind() {
		return teacher_bind;
	}
	public void setTeacher_bind(BigInteger teacher_bind) {
		this.teacher_bind = teacher_bind;
	}
	public String getTeacher_rate() {
		return teacher_rate;
	}
	public void setTeacher_rate(String teacher_rate) {
		this.teacher_rate = teacher_rate;
	}
	public BigInteger getParent_num() {
		return parent_num;
	}
	public void setParent_num(BigInteger parent_num) {
		this.parent_num = parent_num;
	}
	public BigInteger getParent_bind() {
		return parent_bind;
	}
	public void setParent_bind(BigInteger parent_bind) {
		this.parent_bind = parent_bind;
	}
	public String getParent_rate() {
		return parent_rate;
	}
	public void setParent_rate(String parent_rate) {
		this.parent_rate = parent_rate;
	}
	public String getTeacher_name() {
		return teacher_name;
	}
	public void setTeacher_name(String teacher_name) {
		this.teacher_name = teacher_name;
	}
	public String getNote() {
		return note;
	}
	public void setNote(String note) {
		this.note = note;
	}
	public Integer getPinglun_num() {
		return pinglun_num;
	}
	public void setPinglun_num(Integer pinglun_num) {
		this.pinglun_num = pinglun_num;
	}
	public Integer getDianzan_num() {
		return dianzan_num;
	}
	public void setDianzan_num(Integer dianzan_num) {
		this.dianzan_num = dianzan_num;
	}
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	public Integer getNum() {
		return num;
	}
	public void setNum(Integer num) {
		this.num = num;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getStart_time() {
		return start_time;
	}
	public void setStart_time(String start_time) {
		this.start_time = start_time;
	}
	public String getEnd_time() {
		return end_time;
	}
	public void setEnd_time(String end_time) {
		this.end_time = end_time;
	}
	public String getMoney() {
		return money;
	}
	public void setMoney(String money) {
		this.money = money;
	}
	public Integer getYs_num() {
		return ys_num;
	}
	public void setYs_num(Integer ys_num) {
		this.ys_num = ys_num;
	}
	public Integer getSs_num() {
		return ss_num;
	}
	public void setSs_num(Integer ss_num) {
		this.ss_num = ss_num;
	}
	public String getYs_money() {
		return ys_money;
	}
	public void setYs_money(String ys_money) {
		this.ys_money = ys_money;
	}
	public Double getSs_money() {
		return ss_money;
	}
	public void setSs_money(Double ss_money) {
		this.ss_money = ss_money;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getDep_name() {
		return dep_name;
	}
	public void setDep_name(String dep_name) {
		this.dep_name = dep_name;
	}
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public String getIs_bind() {
		return is_bind;
	}
	public void setIs_bind(String is_bind) {
		this.is_bind = is_bind;
	}

}
