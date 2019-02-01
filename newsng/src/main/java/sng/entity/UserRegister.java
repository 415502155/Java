package sng.entity;

import java.io.Serializable;
import java.lang.annotation.Retention;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.springframework.format.annotation.DateTimeFormat;

/**
 * 注册表信息实体 
 * Title: UserRegister 
 * Description:记录用户注册信息 
 * Company: 世纪伟业
 * @author 马国庆
 * @date 2018年10月23日上午10:51
 */
@Entity
@Table(name = "user_register")
public class UserRegister implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 4784898411865937290L;

	public UserRegister() {
		super();
	}

	
	
	

	public UserRegister(Integer userRegisterId, Integer orgId, String studName, Integer cardType, String cardNum,
			Date brithday, String telephone, Integer relation, Integer authStatus, String loginName,
			String loginPassword, String loginSalt, Integer isDel, Date insertTime, Date updateTime, Date delTime,
			String nick_name, String open_id, String head_url) {
		super();
		this.userRegisterId = userRegisterId;
		this.orgId = orgId;
		this.studName = studName;
		this.cardType = cardType;
		this.cardNum = cardNum;
		this.brithday = brithday;
		this.telephone = telephone;
		this.relation = relation;
		this.authStatus = authStatus;
		this.loginName = loginName;
		this.loginPassword = loginPassword;
		this.loginSalt = loginSalt;
		this.isDel = isDel;
		this.insertTime = insertTime;
		this.updateTime = updateTime;
		this.delTime = delTime;
		this.nick_name = nick_name;
		this.open_id = open_id;
		this.head_url = head_url;
	}



	
	@Transient
	private String login_password;
	@Transient
	private Integer org_id;
	@Transient
	private String login_salt;
	


	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "user_register_id")
	private Integer userRegisterId; // 用户注册表主键

	@Column(name = "org_id")
	private Integer orgId; // 机构id
	@Column(name = "stud_name")
	private String studName; // 学员名称
	@Column(name = "card_type")
	private Integer cardType;// 证件类型（身份证、台胞证、护照）

	@Column(name = "card_num")
	private String cardNum;// 证件号码

	@Column(name = "brithday")
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date brithday;// 出生日期

	@Column(name = "telephone")
	private String telephone;// 手机号

	@Column(name = "relation")
	private Integer relation;// 与学员关系（父亲母亲）

	@Column(name = "auth_status")
	private Integer authStatus;// 认证状态（未认证、已认证）

	@Column(name = "login_name")
	private String loginName;// 登录名称

	@Column(name = "login_password")
	private String loginPassword; // 登录密码

	@Column(name = "login_salt")
	private String loginSalt;// 登录状态

	@Column(name = "is_del")
	private Integer isDel; // 删除标记 是否删除(0:否；1：是)

	@Column(name = "insert_time")
	private Date insertTime; // 插入时间

	@Column(name = "update_time")
	private Date updateTime;// 更新时间

	@Column(name = "del_time")
	private Date delTime; // 删除时间
	
	
	@Transient
	private int identity;  //身份 1教师 0家长
	@Transient
	private String new_user_mobil;
	

	
	
	public int getIdentity() {
		return identity;
	}





	public void setIdentity(int identity) {
		this.identity = identity;
	}





	public String getNew_user_mobil() {
		return new_user_mobil;
	}





	public void setNew_user_mobil(String new_user_mobil) {
		this.new_user_mobil = new_user_mobil;
	}




	/**
	 * 昵称
	 */
	@Column(name = "nick_name")
	private String nick_name;
	/**
	 * 微信openid
	 */
	@Column(name = "open_id")
	private String open_id;
	/**
	 * 头像url
	 */
	@Column(name = "head_url")
	private String head_url;
	
	
	
	
	

	public String getLogin_salt() {
		return login_salt;
	}





	public void setLogin_salt(String login_salt) {
		this.login_salt = login_salt;
	}





	public String getLogin_password() {
		return login_password;
	}
	public void setLogin_password(String login_password) {
		this.login_password = login_password;
	}
	public Integer getOrg_id() {
		return org_id;
	}
	public void setOrg_id(Integer org_id) {
		this.org_id = org_id;
	}

	public String getNick_name() {
		return nick_name;
	}

	public void setNick_name(String nick_name) {
		this.nick_name = nick_name;
	}

	public String getOpen_id() {
		return open_id;
	}

	public void setOpen_id(String open_id) {
		this.open_id = open_id;
	}

	public String getHead_url() {
		return head_url;
	}

	public void setHead_url(String head_url) {
		this.head_url = head_url;
	}

	public Integer getUserRegisterId() {
		return userRegisterId;
	}

	public void setUserRegisterId(Integer userRegisterId) {
		this.userRegisterId = userRegisterId;
	}

	public Integer getOrgId() {
		return orgId;
	}

	public void setOrgId(Integer orgId) {
		this.orgId = orgId;
	}

	public String getStudName() {
		return studName;
	}

	public void setStudName(String studName) {
		this.studName = studName == null ? null : studName.trim();
	}

	public Integer getCardType() {
		return cardType;
	}

	public void setCardType(Integer cardType) {
		this.cardType = cardType;
	}

	public String getCardNum() {
		return cardNum;
	}

	public void setCardNum(String cardNum) {
		this.cardNum = cardNum == null ? null : cardNum.trim();
	}

	public Date getBrithday() {
		return brithday;
	}

	public void setBrithday(Date brithday) {
		this.brithday = brithday;
	}

	public String getTelephone() {
		return telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone == null ? null : telephone.trim();
	}

	public Integer getRelation() {
		return relation;
	}

	public void setRelation(Integer relation) {
		this.relation = relation;
	}

	public Integer getAuthStatus() {
		return authStatus;
	}

	public void setAuthStatus(Integer authStatus) {
		this.authStatus = authStatus;
	}

	public String getLoginName() {
		return loginName;
	}

	public void setLoginName(String loginName) {
		this.loginName = loginName == null ? null : loginName.trim();
	}

	public String getLoginPassword() {
		return loginPassword;
	}

	public void setLoginPassword(String loginPassword) {
		this.loginPassword = loginPassword == null ? null : loginPassword.trim();
	}

	public String getLoginSalt() {
		return loginSalt;
	}

	public void setLoginSalt(String loginSalt) {
		this.loginSalt = loginSalt == null ? null : loginSalt.trim();
	}

	public Integer getIsDel() {
		return isDel;
	}

	public void setIsDel(Integer isDel) {
		this.isDel = isDel;
	}

	public Date getInsertTime() {
		return insertTime;
	}

	public void setInsertTime(Date insertTime) {
		this.insertTime = insertTime;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public Date getDelTime() {
		return delTime;
	}

	public void setDelTime(Date delTime) {
		this.delTime = delTime;
	}





	@Override
	public String toString() {
		return "UserRegister [login_password=" + login_password + ", org_id=" + org_id + ", login_salt=" + login_salt
				+ ", userRegisterId=" + userRegisterId + ", orgId=" + orgId + ", studName=" + studName + ", cardType="
				+ cardType + ", cardNum=" + cardNum + ", brithday=" + brithday + ", telephone=" + telephone
				+ ", relation=" + relation + ", authStatus=" + authStatus + ", loginName=" + loginName
				+ ", loginPassword=" + loginPassword + ", loginSalt=" + loginSalt + ", isDel=" + isDel + ", insertTime="
				+ insertTime + ", updateTime=" + updateTime + ", delTime=" + delTime + ", nick_name=" + nick_name
				+ ", open_id=" + open_id + ", head_url=" + head_url + "]";
	}





	






	
	
}