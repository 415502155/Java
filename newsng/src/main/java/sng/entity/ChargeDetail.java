package sng.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * 支付详情 
 * Title: ChargeDetail 
 * Description: 记录支付的详细信息 
 * Company: 世纪伟业
 * 
 * @author Liuyang
 * @date 2018年1月17日下午1:50:57
 * updateTime:2018-10-23
 * 更新人:马国庆
 */
@Entity
@Table(name = "charge_detail")
public class ChargeDetail implements Serializable {


	/**
	 *@Fields serialVersionUID : 
	 */
	private static final long serialVersionUID = 2317197378017250718L;
	/**
	 * 主键
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer cd_id;
	/**
	 * 收费表主键
	 */
	private Integer cid;
	/**
	 * 机构主键
	 */
	private Integer org_id;

	/**
	 * 校区id
	 */
	private Integer cam_id;
	/**
	 * 班级学生关系表主键
	 */
	private Integer stu_class_id;
	/**
	 * 学生主键
	 */
	private Integer stud_id;
	/**
	 * 学生姓名
	 */
	private String stud_name;
	/**
	 * 班级主键
	 */
	private Integer clas_id;
	/**
	 * 班级名称
	 */
	private String clas_name;
	/**
	 * 开始时间
	 */
	private Date start_time;
	/**
	 * 结束时间
	 */
	private Date end_time;
	/**
	 * 【银联】商户订单号 （"6"+5位机构主键+13位主表时间戳+年级主键+"l"+班级主键+"l"+"学生主键"+13位明细表时间戳）.前40位
	 */
	private String order_id;
	/**
	 * 【银联】实缴金额（单位：分）
	 */
	private String txnAmt;
	/**
	 * 应收金额（单位：元）
	 */
	private String money;
	/**
	 * 【银联】缴费时间
	 */
	private Date txnTime;
	/**
	 * 支付方式【1现金2银行卡3手机】
	 */
	private Integer pay_method;
	/**
	 * 状态【0未支付1已支付2申请退款3已退全款4已退部分款5已驳回6退款失败 9不参与】
	 */
	private Integer status;
	/**
	 * 线上支付结果【0：未支付，1：已支付】
	 */
	private Integer online_pay;
	/**
	 * 线下支付结果【0：未支付，1：已支付】
	 */
	private Integer offline_pay;
	/**
	 * 支付方式【1:统一收费；9：个性化收费】
	 */
	private Integer pay_type;
	/**
	 * 通知发送状态【0:未发送; 1:仅发送短信； 2：仅发送微信； 9:都发送了】
	 */
	private Integer is_send;
	/**
	 * 账户信息主键
	 */
	@JsonIgnore
	private Integer accId;
	/**
	 * 删除标记
	 */
	@JsonIgnore
	private Integer is_del;
	/**
	 * 创建时间
	 */
	@JsonIgnore
	private Date insert_time;
	/**
	 * 修改时间
	 */
	@JsonIgnore
	private Date modify_time;
	/**
	 * 删除时间
	 */
	@JsonIgnore
	private Date del_time;
	/**
	 * 尝试支付时间
	 */
	@JsonIgnore
	private Date try_time;
	/**
	 * 【银联】订单描述
	 */
	@JsonIgnore
	private String orderDesc;

	/********************************* @Transient ***********************************/
	/**
	 * 退费状态【0:不允许退费，1：可以退费】
	 */
	@Transient
	private Integer refund;
	/**
	 * 家长的openid
	 */
	@Transient
	@JsonIgnore
	private String openid;
	/**
	 * 家长手机号
	 */
	@Transient
	private String user_mobile;
	/**
	 * 家长手机类型
	 */
	@Transient
	@JsonIgnore
	private Byte user_mobile_type;
	/**
	 * 缴费主题
	 */
	@Transient
	private String item;
	/**
	 * 收费内容
	 */
	@Transient
	private String content;
	/**
	 * 最后提醒时间
	 */
	@Transient
	@JsonIgnore
	private Date send_time;
	/**
	 * 账户别名
	 */
	@Transient
	private String accName;
	/**
	 * 账户帐号
	 */
	@Transient
	private String accNo;
	/**
	 * 商户号
	 */
	@Transient
	@JsonIgnore
	private String merId;
	/**
	 * 商户类别【1银联2光大】
	 */
	@Transient
	@JsonIgnore
	private Integer accType;
	/**
	 * 开户行
	 */
	@Transient
	@JsonIgnore
	private String bankName;
	/**
	 * 流水号
	 */
	@Transient
	private String queryId;
	/**
	 * Charge的status
	 */
	@Transient
	private Integer c_status;
	/**
	 * ChargeRecord的status
	 */
	@Transient
	private String cr_status;
	/**
	 * ChargeRecord的交易类型 银联：01银联缴费04银联退费
	 * 自定义：SQYLTF申请银联退费TYYLTF同意银联退费YYTFSB银联退费失败BHYLTF驳回银联退费XXJF线下缴费SQXXTF申请线下退费TYXXTF同意线下退费BHXXTF驳回线下退费
	 */
	@Transient
	private String txnType;
	/**
	 * 密钥
	 */
	@Transient
	@JsonIgnore
	private String signCert;
	/**
	 * 密码
	 */
	@Transient
	@JsonIgnore
	private String pwd;
	/**
	 * 光大查询码
	 */
	@Transient
	@JsonIgnore
	private String billNum;
	/**
	 * 光大对账文件码
	 */
	@Transient
	@JsonIgnore
	private String fileNum;
	/**
	 * 年级主键
	 */
	@Transient
	@JsonIgnore
	private Integer grade_id;
	/**
	 * 年级名称
	 */
	@Transient
	@JsonIgnore
	private String grade_name;
	/********************************* @Transient ***********************************/
	
	/** 
	 * 主键  
	 */
	public Integer getCd_id() {
		return cd_id;
	}
	/** 
	 * 主键  
	 */
	public void setCd_id(Integer cd_id) {
		this.cd_id = cd_id;
	}
	/** 
	 * 收费表主键  
	 */
	public Integer getCid() {
		return cid;
	}
	/** 
	 * 收费表主键  
	 */
	public void setCid(Integer cid) {
		this.cid = cid;
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
	 * 校区id  
	 */
	public Integer getCam_id() {
		return cam_id;
	}
	/** 
	 * 校区id  
	 */
	public void setCam_id(Integer cam_id) {
		this.cam_id = cam_id;
	}
	/** 
	 * 班级学生关系表主键  
	 */
	public Integer getStu_class_id() {
		return stu_class_id;
	}
	/** 
	 * 班级学生关系表主键  
	 */
	public void setStu_class_id(Integer stu_class_id) {
		this.stu_class_id = stu_class_id;
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
	 * 年级主键  
	 */
	public Integer getGrade_id() {
		return grade_id;
	}
	/** 
	 * 年级主键  
	 */
	public void setGrade_id(Integer grade_id) {
		this.grade_id = grade_id;
	}
	/** 
	 * 年级名称  
	 */
	public String getGrade_name() {
		return grade_name;
	}
	/** 
	 * 年级名称  
	 */
	public void setGrade_name(String grade_name) {
		this.grade_name = grade_name;
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
	 * 开始时间  
	 */
	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
	public Date getStart_time() {
		return start_time;
	}
	/** 
	 * 开始时间  
	 */
	public void setStart_time(Date start_time) {
		this.start_time = start_time;
	}
	/** 
	 * 结束时间  
	 */
	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
	public Date getEnd_time() {
		return end_time;
	}
	/** 
	 * 结束时间  
	 */
	public void setEnd_time(Date end_time) {
		this.end_time = end_time;
	}
	/** 
	 * 【银联】商户订单号（"6"+5位机构主键+13位主表时间戳+年级主键+"l"+班级主键+"l"+"学生主键"+13位明细表时间戳）.前40位  
	 */
	public String getOrder_id() {
		return order_id;
	}
	/** 
	 * 【银联】商户订单号（"6"+5位机构主键+13位主表时间戳+年级主键+"l"+班级主键+"l"+"学生主键"+13位明细表时间戳）.前40位  
	 */
	public void setOrder_id(String order_id) {
		this.order_id = order_id;
	}
	/** 
	 * 【银联】实缴金额（单位：分）  
	 */
	public String getTxnAmt() {
		return txnAmt;
	}
	/** 
	 * 【银联】实缴金额（单位：分）  
	 */
	public void setTxnAmt(String txnAmt) {
		this.txnAmt = txnAmt;
	}
	/** 
	 * 应收金额（单位：元）  
	 */
	public String getMoney() {
		return money;
	}
	/** 
	 * 应收金额（单位：元）  
	 */
	public void setMoney(String money) {
		this.money = money;
	}
	/** 
	 * 【银联】缴费时间  
	 */
	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
	public Date getTxnTime() {
		return txnTime;
	}
	/** 
	 * 【银联】缴费时间  
	 */
	public void setTxnTime(Date txnTime) {
		this.txnTime = txnTime;
	}
	/** 
	 * 支付方式  【1现金2银行卡3手机】
	 */
	public Integer getPay_method() {
		return pay_method;
	}
	/** 
	 * 支付方式  【1现金2银行卡3手机】
	 */
	public void setPay_method(Integer pay_method) {
		this.pay_method = pay_method;
	}
	/** 
	 * 状态【0未支付1已支付2申请退款3已退全款4已退部分款5已驳回6退款失败9不参与】  
	 */
	public Integer getStatus() {
		return status;
	}
	/** 
	 * 状态【0未支付1已支付2申请退款3已退全款4已退部分款5已驳回6退款失败9不参与】  
	 */
	public void setStatus(Integer status) {
		this.status = status;
	}
	/** 
	 * 线上支付结果【0：未支付，1：已支付】  
	 */
	public Integer getOnline_pay() {
		return online_pay;
	}
	/** 
	 * 线上支付结果【0：未支付，1：已支付】  
	 */
	public void setOnline_pay(Integer online_pay) {
		this.online_pay = online_pay;
	}
	/** 
	 * 线下支付结果【0：未支付，1：已支付】  
	 */
	public Integer getOffline_pay() {
		return offline_pay;
	}
	/** 
	 * 线下支付结果【0：未支付，1：已支付】  
	 */
	public void setOffline_pay(Integer offline_pay) {
		this.offline_pay = offline_pay;
	}
	/** 
	 * 支付方式【1:统一收费；9：个性化收费】  
	 */
	public Integer getPay_type() {
		return pay_type;
	}
	/** 
	 * 支付方式【1:统一收费；9：个性化收费】  
	 */
	public void setPay_type(Integer pay_type) {
		this.pay_type = pay_type;
	}
	/** 
	 * 通知发送状态【0:未发送;1:仅发送短信；2：仅发送微信；9:都发送了】  
	 */
	public Integer getIs_send() {
		return is_send;
	}
	/** 
	 * 通知发送状态【0:未发送;1:仅发送短信；2：仅发送微信；9:都发送了】  
	 */
	public void setIs_send(Integer is_send) {
		this.is_send = is_send;
	}
	/** 
	 * 账户信息主键  
	 */
	public Integer getAccId() {
		return accId;
	}
	/** 
	 * 账户信息主键  
	 */
	public void setAccId(Integer accId) {
		this.accId = accId;
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
	/** 
	 * 创建时间  
	 */
	public Date getInsert_time() {
		return insert_time;
	}
	/** 
	 * 创建时间  
	 */
	public void setInsert_time(Date insert_time) {
		this.insert_time = insert_time;
	}
	/** 
	 * 修改时间  
	 */
	public Date getModify_time() {
		return modify_time;
	}
	/** 
	 * 修改时间  
	 */
	public void setModify_time(Date modify_time) {
		this.modify_time = modify_time;
	}
	/** 
	 * 删除时间  
	 */
	public Date getDel_time() {
		return del_time;
	}
	/** 
	 * 删除时间  
	 */
	public void setDel_time(Date del_time) {
		this.del_time = del_time;
	}
	/** 
	 * 尝试支付时间  
	 */
	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
	public Date getTry_time() {
		return try_time;
	}
	/** 
	 * 尝试支付时间  
	 */
	public void setTry_time(Date try_time) {
		this.try_time = try_time;
	}
	/** 
	 * 【银联】订单描述  
	 */
	public String getOrderDesc() {
		return orderDesc;
	}
	/** 
	 * 【银联】订单描述  
	 */
	public void setOrderDesc(String orderDesc) {
		this.orderDesc = orderDesc;
	}
	/** 
	 *   
	 */
	public Integer getRefund() {
		return refund;
	}
	/** 
	 *   
	 */
	public void setRefund(Integer refund) {
		this.refund = refund;
	}
	/** 
	 * 家长的openid  
	 */
	public String getOpenid() {
		return openid;
	}
	/** 
	 * 家长的openid  
	 */
	public void setOpenid(String openid) {
		this.openid = openid;
	}
	/** 
	 * 家长手机号  
	 */
	public String getUser_mobile() {
		return user_mobile;
	}
	/** 
	 * 家长手机号  
	 */
	public void setUser_mobile(String user_mobile) {
		this.user_mobile = user_mobile;
	}
	/** 
	 * 家长手机类型  
	 */
	public Byte getUser_mobile_type() {
		return user_mobile_type;
	}
	/** 
	 * 家长手机类型  
	 */
	public void setUser_mobile_type(Byte user_mobile_type) {
		this.user_mobile_type = user_mobile_type;
	}
	/** 
	 * 缴费主题  
	 */
	public String getItem() {
		return item;
	}
	/** 
	 * 缴费主题  
	 */
	public void setItem(String item) {
		this.item = item;
	}
	/** 
	 * 收费内容  
	 */
	public String getContent() {
		return content;
	}
	/** 
	 * 收费内容  
	 */
	public void setContent(String content) {
		this.content = content;
	}
	/** 
	 * 最后提醒时间  
	 */
	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
	public Date getSend_time() {
		return send_time;
	}
	/** 
	 * 最后提醒时间  
	 */
	public void setSend_time(Date send_time) {
		this.send_time = send_time;
	}
	/** 
	 * 账户别名  
	 */
	public String getAccName() {
		return accName;
	}
	/** 
	 * 账户别名  
	 */
	public void setAccName(String accName) {
		this.accName = accName;
	}
	/** 
	 * 账户帐号  
	 */
	public String getAccNo() {
		return accNo;
	}
	/** 
	 * 账户帐号  
	 */
	public void setAccNo(String accNo) {
		this.accNo = accNo;
	}
	/** 
	 * 商户号  
	 */
	public String getMerId() {
		return merId;
	}
	/** 
	 * 商户号  
	 */
	public void setMerId(String merId) {
		this.merId = merId;
	}
	/** 
	 * 商户类别【1银联2光大】  
	 */
	public Integer getAccType() {
		return accType;
	}
	/** 
	 * 商户类别【1银联2光大】  
	 */
	public void setAccType(Integer accType) {
		this.accType = accType;
	}
	/** 
	 * 开户行  
	 */
	public String getBankName() {
		return bankName;
	}
	/** 
	 * 开户行  
	 */
	public void setBankName(String bankName) {
		this.bankName = bankName;
	}
	/** 
	 * 流水号  
	 */
	public String getQueryId() {
		return queryId;
	}
	/** 
	 * 流水号  
	 */
	public void setQueryId(String queryId) {
		this.queryId = queryId;
	}
	/** 
	 * Charge的status  
	 */
	public Integer getC_status() {
		return c_status;
	}
	/** 
	 * Charge的status  
	 */
	public void setC_status(Integer c_status) {
		this.c_status = c_status;
	}
	/** 
	 * ChargeRecord的status  
	 */
	public String getCr_status() {
		return cr_status;
	}
	/** 
	 * ChargeRecord的status  
	 */
	public void setCr_status(String cr_status) {
		this.cr_status = cr_status;
	}
	/** 
	 * ChargeRecord的交易类型银联：01银联缴费04银联退费自定义：SQYLTF申请银联退费TYYLTF同意银联退费YYTFSB银联退费失败BHYLTF驳回银联退费XXJF线下缴费SQXXTF申请线下退费TYXXTF同意线下退费BHXXTF驳回线下退费  
	 */
	public String getTxnType() {
		return txnType;
	}
	/** 
	 * ChargeRecord的交易类型银联：01银联缴费04银联退费自定义：SQYLTF申请银联退费TYYLTF同意银联退费YYTFSB银联退费失败BHYLTF驳回银联退费XXJF线下缴费SQXXTF申请线下退费TYXXTF同意线下退费BHXXTF驳回线下退费  
	 */
	public void setTxnType(String txnType) {
		this.txnType = txnType;
	}
	/** 
	 * 密钥  
	 */
	public String getSignCert() {
		return signCert;
	}
	/** 
	 * 密钥  
	 */
	public void setSignCert(String signCert) {
		this.signCert = signCert;
	}
	/** 
	 * 密码  
	 */
	public String getPwd() {
		return pwd;
	}
	/** 
	 * 密码  
	 */
	public void setPwd(String pwd) {
		this.pwd = pwd;
	}
	/** 
	 * 光大查询码  
	 */
	public String getBillNum() {
		return billNum;
	}
	/** 
	 * 光大查询码  
	 */
	public void setBillNum(String billNum) {
		this.billNum = billNum;
	}
	/** 
	 * 光大对账文件码  
	 */
	public String getFileNum() {
		return fileNum;
	}
	/** 
	 * 光大对账文件码  
	 */
	public void setFileNum(String fileNum) {
		this.fileNum = fileNum;
	}


}
