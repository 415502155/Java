package sng.entity;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * 账户信息
 * Title: Account
 * Description: 账户信息主表 
 * Company: 世纪伟业
 * @author Liuyang
 * @date 2018年1月24日下午2:22:52
 */
@Entity
@Table(name = "account")
public class Account implements Serializable{
	
	/**
	 *@Fields serialVersionUID : 
	 */
	private static final long serialVersionUID = -4200696286910202798L;
	/**
	 * 主键
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private	Integer	accId;
	/**
	 * 机构主键
	 */
	private	Integer	org_id;
	/**
	 * 校区主键
	 */
	private Integer cam_id;
	/**
	 * 是否默认学费账户
	 */
	private Integer is_default;
	/**
	 * 【银联】商户号
	 */
	private	String	merId;
	/**
	 * 帐号
	 */
	private	String accNo;
	/**
	 * 账号名称
	 */
	private	String accName;
	/**
	 * 【银联】帐号类型
	 * 1：银联；2：光大
	 */
	private	Integer accType;
	/**
	 * 开户行
	 */
	private String openingBank;
	/**
	 * 银行名称
	 */
	private	String bankName;
	/**
	 * 类型（1代收2自收）
	 */
	private Integer type;
	/**
	 * 签名证书
	 */
	@JsonIgnore
	private	String signCert;
	/**
	 * 敏感信息加密证书
	 */
	@JsonIgnore
	private	String encryptCert;
	/**
	 * 中级证书
	 */
	@JsonIgnore
	private	String middleCert;
	/**
	 * 根证书
	 */
	@JsonIgnore
	private	Integer	rootCert;
	/**
	 * 签名证书密码
	 */
	@JsonIgnore
	private	Integer	pwd;
	/**
	 * 光大银行支付跳转地址
	 */
	@JsonIgnore
	private String url;
	/**
	 * （光大专用）机构查询编号
	 */
	@JsonIgnore
	private String billNum;
	/**
	 * (光大专用)对账文件编号
	 */
	@JsonIgnore
	private String fileNum;
	/**
	 * 备注说明
	 */
	private String note;
	/**
	 * 删除标记
	 */
	private Integer is_del;
	/** 
	 * 主键  
	 */
	public Integer getAccId() {
		return accId;
	}
	/** 
	 * 主键  
	 */
	public void setAccId(Integer accId) {
		this.accId = accId;
	}
	/** 
	 * 机构主键  
	 */
	public Integer getOrg_id() {
		return org_id;
	}
	/** 
	 * 机构主键  
	 */
	public void setOrg_id(Integer org_id) {
		this.org_id = org_id;
	}
	/** 
	 * 校区主键  
	 */
	public Integer getCam_id() {
		return cam_id;
	}
	/** 
	 * 校区主键  
	 */
	public void setCam_id(Integer cam_id) {
		this.cam_id = cam_id;
	}
	/** 
	 * 是否默认学费账户  
	 */
	public Integer getIs_default() {
		return is_default;
	}
	/** 
	 * 是否默认学费账户  
	 */
	public void setIs_default(Integer is_default) {
		this.is_default = is_default;
	}
	/** 
	 * 【银联】商户号  
	 */
	public String getMerId() {
		return merId;
	}
	/** 
	 * 【银联】商户号  
	 */
	public void setMerId(String merId) {
		this.merId = merId;
	}
	/** 
	 * 帐号  
	 */
	public String getAccNo() {
		return accNo;
	}
	/** 
	 * 帐号  
	 */
	public void setAccNo(String accNo) {
		this.accNo = accNo;
	}
	/** 
	 * 账号名称  
	 */
	public String getAccName() {
		return accName;
	}
	/** 
	 * 账号名称  
	 */
	public void setAccName(String accName) {
		this.accName = accName;
	}
	/** 
	 * 【银联】帐号类型1：银联；2：光大  
	 */
	public Integer getAccType() {
		return accType;
	}
	/** 
	 * 【银联】帐号类型1：银联；2：光大  
	 */
	public void setAccType(Integer accType) {
		this.accType = accType;
	}
	/** 
	 * 开户行  
	 */
	public String getOpeningBank() {
		return openingBank;
	}
	/** 
	 * 开户行  
	 */
	public void setOpeningBank(String openingBank) {
		this.openingBank = openingBank;
	}
	/** 
	 * 银行名称  
	 */
	public String getBankName() {
		return bankName;
	}
	/** 
	 * 银行名称  
	 */
	public void setBankName(String bankName) {
		this.bankName = bankName;
	}
	/** 
	 * 类型（1代收2自收）  
	 */
	public Integer getType() {
		return type;
	}
	/** 
	 * 类型（1代收2自收）  
	 */
	public void setType(Integer type) {
		this.type = type;
	}
	/** 
	 * 签名证书  
	 */
	public String getSignCert() {
		return signCert;
	}
	/** 
	 * 签名证书  
	 */
	public void setSignCert(String signCert) {
		this.signCert = signCert;
	}
	/** 
	 * 敏感信息加密证书  
	 */
	public String getEncryptCert() {
		return encryptCert;
	}
	/** 
	 * 敏感信息加密证书  
	 */
	public void setEncryptCert(String encryptCert) {
		this.encryptCert = encryptCert;
	}
	/** 
	 * 中级证书  
	 */
	public String getMiddleCert() {
		return middleCert;
	}
	/** 
	 * 中级证书  
	 */
	public void setMiddleCert(String middleCert) {
		this.middleCert = middleCert;
	}
	/** 
	 * 根证书  
	 */
	public Integer getRootCert() {
		return rootCert;
	}
	/** 
	 * 根证书  
	 */
	public void setRootCert(Integer rootCert) {
		this.rootCert = rootCert;
	}
	/** 
	 * 签名证书密码  
	 */
	public Integer getPwd() {
		return pwd;
	}
	/** 
	 * 签名证书密码  
	 */
	public void setPwd(Integer pwd) {
		this.pwd = pwd;
	}
	/** 
	 * 光大银行支付跳转地址  
	 */
	public String getUrl() {
		return url;
	}
	/** 
	 * 光大银行支付跳转地址  
	 */
	public void setUrl(String url) {
		this.url = url;
	}
	/** 
	 * （光大专用）机构查询编号  
	 */
	public String getBillNum() {
		return billNum;
	}
	/** 
	 * （光大专用）机构查询编号  
	 */
	public void setBillNum(String billNum) {
		this.billNum = billNum;
	}
	/** 
	 * (光大专用)对账文件编号  
	 */
	public String getFileNum() {
		return fileNum;
	}
	/** 
	 * (光大专用)对账文件编号  
	 */
	public void setFileNum(String fileNum) {
		this.fileNum = fileNum;
	}
	/** 
	 * 备注说明  
	 */
	public String getNote() {
		return note;
	}
	/** 
	 * 备注说明  
	 */
	public void setNote(String note) {
		this.note = note;
	}
	/** 
	 * 删除标记  
	 */
	public Integer getIs_del() {
		return is_del;
	}
	/** 
	 * 删除标记  
	 */
	public void setIs_del(Integer is_del) {
		this.is_del = is_del;
	}
	
}
