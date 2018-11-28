package cn.edugate.esb.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Title: Feedback
 * Description:用户反馈 
 * Company: 世纪伟业
 * @author Liu Yang
 * @date 2018年8月1日上午10:33:32
 */
@Entity
@Table(name = "feedback")
public class Feedback implements Serializable{
	
	private static final long serialVersionUID = 7236709026498044101L;
	/**
	 * 主键
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private	Integer	feed_id;
	/**
	 * 机构主键
	 */
	private Integer org_id;
	/**
	 * 机构名称
	 */
	private String org_name;
	/**
	 * 内容
	 */
	private String content;
	/**
	 * 用户填写手机号码
	 */
	private String mobile;
	/**
	 * 机构用户主键
	 */
	private Integer user_id;
	/**
	 * 姓名
	 */
	private String name;
	/**
	 * 角色
	 */
	private Integer identity;
	/**
	 * 系统登录手机号码
	 */
	private String login_name;
	/**
	 * 创建时间
	 */
	private Date insert_time;
	/**
	 * 状态
	 */
	private Integer status;
	/**
	 * 是否删除
	 */
	private Integer is_del;
	
	public Integer getFeed_id() {
		return feed_id;
	}
	public void setFeed_id(Integer feed_id) {
		this.feed_id = feed_id;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public Integer getUser_id() {
		return user_id;
	}
	public void setUser_id(Integer user_id) {
		this.user_id = user_id;
	}
	public Date getInsert_time() {
		return insert_time;
	}
	public void setInsert_time(Date insert_time) {
		this.insert_time = insert_time;
	}
	public Integer getIs_del() {
		return is_del;
	}
	public void setIs_del(Integer is_del) {
		this.is_del = is_del;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public String getOrg_name() {
		return org_name;
	}
	public void setOrg_name(String org_name) {
		this.org_name = org_name;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Integer getIdentity() {
		return identity;
	}
	public void setIdentity(Integer identity) {
		this.identity = identity;
	}
	public String getLogin_name() {
		return login_name;
	}
	public void setLogin_name(String login_name) {
		this.login_name = login_name;
	}
	public Integer getOrg_id() {
		return org_id;
	}
	public void setOrg_id(Integer org_id) {
		this.org_id = org_id;
	}
	
}
