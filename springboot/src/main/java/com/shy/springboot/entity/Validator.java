package com.shy.springboot.entity;

import java.util.Date;

import javax.validation.constraints.AssertFalse;
import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.Digits;
import javax.validation.constraints.Future;
import javax.validation.constraints.Max;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.CreditCardNumber;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.Range;
import org.hibernate.validator.constraints.URL;

import com.shy.springboot.utils.ValidatorUtil;

public class Validator {
	/***
	 * 被注解的元素必须为false
	 */
	@AssertFalse(message = "Boolean flag cannot be true;")
	private boolean flag;
	
	/***
	 * 被注解的元素必须为一个数字，其值必须小于等于指定的最小值
	 */
	@DecimalMax(value = "10", message = "Max value cannot greater than 10;")
	private int num;
	
	/***
	 * 被注解的元素必须为一个数字，其值必须在可接受的范围内
	 */
	@Digits(integer = 10, message = "Must be a number,The value must be within an acceptable range;", fraction = 0)
	private String digits;
	
	/***
	 * 被注解的元素必须是日期，检查给定的日期是否比现在晚
	 */
	@Future(message = "Must be date, check if the given date is later than now;")
	private Date date;
	
	/***
	 * 被注解的元素必须为一个数字，其值必须小于等于指定的最小值
	 */
	@Max(value = 10, message = "Must be a number whose value must be less than or equal to the specified minimum value")
	private Integer maxNum;
	
	/***
	 * 被注解的元素必须不为null
	 */
	@NotNull(message = "The parameter must not be null")
	private String name;
	
	/***
	 *telphone: 必须符合正则表达式，检查该字符串是否能够在match指定的情况下被regex定义的正则表达式匹配
	 */
	@Pattern(regexp = ValidatorUtil.REGEX_MOBILE, message = "Cell phone Numbers must be legal;")
	private String telphone;
	
	/***
	 * 被注解的元素必须在制定的范围(数据类型:String, Collection, Map and arrays)
	 */
	@Size(min = 3, max = 10, message = "Annotated elements must be within the specified scope;")
	private String des;
	
	/***
	 * 对信用卡号进行一个大致的验证
	 */
	@CreditCardNumber(message = "Do a rough verification of the credit card number;")
	private String creditCardNumber;
	
	/***
	 *identityCardNumber: 必须符合正则表达式，检查该字符串是否能够在match指定的情况下被regex定义的正则表达式匹配
	 */
	@Pattern(regexp = ValidatorUtil.REGEX_ID_CARD, message = "The id number is illegal;")
	private String identityCardNumber;
	
	/***
	 * 被注释的元素必须是电子邮箱地址
	 */
	@Email(regexp = ValidatorUtil.REGEX_EMAIL, message = "It must be an email address")
	private String email;

	/***
	 * 被注解的对象必须是字符串的大小必须在制定的范围内
	 */
	@Length(min = 3, max = 12, message = "The annotated object must be string size must be within the specified range;")
	private String length;
	
	/***
	 * 被注释的元素必须在合适的范围内 (数据：BigDecimal, BigInteger, String, byte, short, int, long and 原始类型的包装类 )
	 */
	@Range(min = 1, max = 0, message = "The annotated elements must be in the appropriate range;")
	private String range;
	
	/***
	 * 被注解的对象必须是字符串，检查是否是一个有效的URL，如果提供了protocol，host等，则该URL还需满足提供的条件
	 */
	@URL(/*protocol = "", host = "", */regexp = ValidatorUtil.REGEX_URL, message = "The annotated object must be a string, not a valid URL;")
	private String url;
	
	public boolean isFlag() {
		return flag;
	}

	public void setFlag(boolean flag) {
		this.flag = flag;
	}

	public int getNum() {
		return num;
	}

	public void setNum(int num) {
		this.num = num;
	}

	public String getDigits() {
		return digits;
	}

	public void setDigits(String digits) {
		this.digits = digits;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public Integer getMaxNum() {
		return maxNum;
	}

	public void setMaxNum(Integer maxNum) {
		this.maxNum = maxNum;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getTelphone() {
		return telphone;
	}

	public void setTelphone(String telphone) {
		this.telphone = telphone;
	}

	public String getDes() {
		return des;
	}

	public void setDes(String des) {
		this.des = des;
	}

	public String getCreditCardNumber() {
		return creditCardNumber;
	}

	public void setCreditCardNumber(String creditCardNumber) {
		this.creditCardNumber = creditCardNumber;
	}

	public String getIdentityCardNumber() {
		return identityCardNumber;
	}

	public void setIdentityCardNumber(String identityCardNumber) {
		this.identityCardNumber = identityCardNumber;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getLength() {
		return length;
	}

	public void setLength(String length) {
		this.length = length;
	}

	public String getRange() {
		return range;
	}

	public void setRange(String range) {
		this.range = range;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	@Override
	public String toString() {
		return "Validator [flag=" + flag + ", num=" + num + ", digits=" + digits + ", date=" + date + ", maxNum="
				+ maxNum + ", name=" + name + ", telphone=" + telphone + ", des=" + des + ", creditCardNumber="
				+ creditCardNumber + ", identityCardNumber=" + identityCardNumber + ", email=" + email + ", length="
				+ length + ", range=" + range + ", url=" + url + "]";
	}
}
