package sng.pojo;

import java.math.BigInteger;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/***
 * 
 * @Company sjwy
 * @Title: StudentRoster.java
 * @Description: 学员花名册
 * @author: shy
 * @date: 2018年11月13日 下午5:30:02
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class StudentRoster implements java.io.Serializable {

	private static final long serialVersionUID = 3297526701297601053L;

	private Integer stud_id;// 学员id

	private Integer stu_userId;// 学生的userId

	private String stud_name;// 学员名称

	private String sex;// 性别（男、女、保密）

	private BigInteger age;// 年龄

	private String parent_name;// 家长名称

	private String user_idnumber;// 学生身份证号码

	private String user_mobile;// 手机号码

	private String openid;// 微信

	public Integer getStud_id() {
		return stud_id;
	}

	public void setStud_id(Integer stud_id) {
		this.stud_id = stud_id;
	}

	public Integer getStu_userId() {
		return stu_userId;
	}

	public void setStu_userId(Integer stu_userId) {
		this.stu_userId = stu_userId;
	}

	public String getOpenid() {
		return openid;
	}

	public void setOpenid(String openid) {
		this.openid = openid;
	}

	public String getStud_name() {
		return stud_name;
	}

	public void setStud_name(String stud_name) {
		this.stud_name = stud_name;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public BigInteger getAge() {
		return age;
	}

	public void setAge(BigInteger age) {
		this.age = age;
	}

	public String getParent_name() {
		return parent_name;
	}

	public void setParent_name(String parent_name) {
		this.parent_name = parent_name;
	}

	public String getUser_idnumber() {
		return user_idnumber;
	}

	public void setUser_idnumber(String user_idnumber) {
		this.user_idnumber = user_idnumber;
	}

	public String getUser_mobile() {
		return user_mobile;
	}

	public void setUser_mobile(String user_mobile) {
		this.user_mobile = user_mobile;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

}
