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
 * 缴费对象 
 *Title: Charge 
 *Description:缴费信息主表
 *Company: 世纪伟业
 * @author Liuyang
 * @date 2018年1月17日下午3:23:00
 * @updateTime:2018-10-23
     * 更新操作人:马国庆
 */
@Entity
@Table(name = "charge")
public class Charge implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 9126115664867082752L;
	/**
	 * 主键
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer cid;
	/**
	 * 机构主键
	 */
	private Integer org_id;

	/**
	 * 校区ID
	 */
	private Integer cam_id;

	/**
	 * 项目类型（如：学费）
	 */
	private Integer type;

	/**
	 * 班级ID
	 */
	private Integer clas_id;

	/**
	 * 创建教师主键
	 */
	private Integer tech_id;
	/**
	 * 创建教师名称
	 */
	private String tech_name;
	/**
	 * 审核教师主键
	 */
	private Integer audit_id;
	/**
	 * 审核教师名称
	 */
	private String audit_name;
	/**
	 * 收费项
	 */
	private String item;
	/**
	 * 收费单价(单位:分)
	 */
	private String txnAmt;
	/**
	 * 收费金额(用于展示，可显示范围，带特殊符号，单位:元)
	 */
	private String money;
	/**
	 * 总实退金额
	 */
	private String st_money;
	/**
	 * 总实退人数
	 */
	private Integer st_num;
	/**
	 * 商户订单号前缀
	 */
	@JsonIgnore
	private String order_prefix;
	/**
	 * 账户信息主键
	 */
	private Integer accId;
	/**
	 * 账户信息
	 */
	@Transient
	private String accInfo;
	/**
	 * 支付方式【1:统一收费；9：个性化收费】
	 */
	private Integer pay_type;
	/**
	 * 开始时间
	 */
	private Date start_time;
	/**
	 * 结束时间
	 */
	private Date end_time;
	/**
	 * 收费内容说明
	 */
	private String content;
	/**
	 * 备注
	 */
	private String note;
	/**
	 * 应收费人数
	 */
	private Integer ys_num;
	/**
	 * 实收已付款人数
	 */
	private Integer ss_num;
	/**
	 * 退费状态【0:不允许退费，1：可以退费】
	 */
	private Integer refund;
	/**
	 * 收费范围【供页面渲染】 格式：{"g":[1,2],"c":[1,2],"s":[1,2],"t":[1,2],"d":[1,2],"tg":[1,2]}
	 * 说明 ：g年级 c班级 s学生 t教师 d部门 tg组
	 */
	private String sf_range;
	/**
	 * 收费范围【供后台使用】 格式："1,2,3,4" 说明：学生主键，逗号隔开
	 */
	@JsonIgnore
	private String ids;
	/**
	 * 删除标记【0：未删除，1：已删除】
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
	 * 实收金额(单位：分)
	 */
	private String ss_money;
	/**
	 * 应收金额(单位：分)
	 */
	private String ys_money;

	/**
	 * 不参与人数（暂不使用）
	 */
	@JsonIgnore
	@Transient
	private Integer no_num;

	/**
	 * 未支付人数
	 */
	private Integer nopay_num;

	/**
	 * 收费明细临时文件
	 */
	@JsonIgnore
	private String pay_file;
	/**
	 * 状态【1待执行2已撤回3执行成功4执行失败5手动关闭6已结束】
	 */
	private Integer status;
	/** 
	 * 主键  
	 */
	public Integer getCid() {
		return cid;
	}
	/** 
	 * 主键  
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
	 * 校区ID  
	 */
	public Integer getCam_id() {
		return cam_id;
	}
	/** 
	 * 校区ID  
	 */
	public void setCam_id(Integer cam_id) {
		this.cam_id = cam_id;
	}
	/** 
	 * 项目类型（如：学费）  
	 */
	public Integer getType() {
		return type;
	}
	/** 
	 * 项目类型（如：学费）  
	 */
	public void setType(Integer type) {
		this.type = type;
	}
	/** 
	 * 班级ID  
	 */
	public Integer getClas_id() {
		return clas_id;
	}
	/** 
	 * 班级ID  
	 */
	public void setClas_id(Integer clas_id) {
		this.clas_id = clas_id;
	}
	/** 
	 * 创建教师主键  
	 */
	public Integer getTech_id() {
		return tech_id;
	}
	/** 
	 * 创建教师主键  
	 */
	public void setTech_id(Integer tech_id) {
		this.tech_id = tech_id;
	}
	/** 
	 * 创建教师名称  
	 */
	public String getTech_name() {
		return tech_name;
	}
	/** 
	 * 创建教师名称  
	 */
	public void setTech_name(String tech_name) {
		this.tech_name = tech_name;
	}
	/** 
	 * 审核教师主键  
	 */
	public Integer getAudit_id() {
		return audit_id;
	}
	/** 
	 * 审核教师主键  
	 */
	public void setAudit_id(Integer audit_id) {
		this.audit_id = audit_id;
	}
	/** 
	 * 审核教师名称  
	 */
	public String getAudit_name() {
		return audit_name;
	}
	/** 
	 * 审核教师名称  
	 */
	public void setAudit_name(String audit_name) {
		this.audit_name = audit_name;
	}
	/** 
	 * 收费项  
	 */
	public String getItem() {
		return item;
	}
	/** 
	 * 收费项  
	 */
	public void setItem(String item) {
		this.item = item;
	}
	/** 
	 * 收费单价(单位:分)  
	 */
	public String getTxnAmt() {
		return txnAmt;
	}
	/** 
	 * 收费单价(单位:分)  
	 */
	public void setTxnAmt(String txnAmt) {
		this.txnAmt = txnAmt;
	}
	/** 
	 * 收费金额(用于展示，可显示范围，带特殊符号，单位:元)  
	 */
	public String getMoney() {
		return money;
	}
	/** 
	 * 收费金额(用于展示，可显示范围，带特殊符号，单位:元)  
	 */
	public void setMoney(String money) {
		this.money = money;
	}
	/** 
	 * 总实退金额  
	 */
	public String getSt_money() {
		return st_money;
	}
	/** 
	 * 总实退金额  
	 */
	public void setSt_money(String st_money) {
		this.st_money = st_money;
	}
	/** 
	 * 总实退人数  
	 */
	public Integer getSt_num() {
		return st_num;
	}
	/** 
	 * 总实退人数  
	 */
	public void setSt_num(Integer st_num) {
		this.st_num = st_num;
	}
	/** 
	 * 商户订单号前缀  
	 */
	public String getOrder_prefix() {
		return order_prefix;
	}
	/** 
	 * 商户订单号前缀  
	 */
	public void setOrder_prefix(String order_prefix) {
		this.order_prefix = order_prefix;
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
	 * 账户信息  
	 */
	public String getAccInfo() {
		return accInfo;
	}
	/** 
	 * 账户信息  
	 */
	public void setAccInfo(String accInfo) {
		this.accInfo = accInfo;
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
	 * 收费内容说明  
	 */
	public String getContent() {
		return content;
	}
	/** 
	 * 收费内容说明  
	 */
	public void setContent(String content) {
		this.content = content;
	}
	/** 
	 * 备注  
	 */
	public String getNote() {
		return note;
	}
	/** 
	 * 备注  
	 */
	public void setNote(String note) {
		this.note = note;
	}
	/** 
	 * 应收费人数  
	 */
	public Integer getYs_num() {
		return ys_num;
	}
	/** 
	 * 应收费人数  
	 */
	public void setYs_num(Integer ys_num) {
		this.ys_num = ys_num;
	}
	/** 
	 * 实收已付款人数  
	 */
	public Integer getSs_num() {
		return ss_num;
	}
	/** 
	 * 实收已付款人数  
	 */
	public void setSs_num(Integer ss_num) {
		this.ss_num = ss_num;
	}
	/** 
	 * 退费状态【0:不允许退费，1：可以退费】  
	 */
	public Integer getRefund() {
		return refund;
	}
	/** 
	 * 退费状态【0:不允许退费，1：可以退费】  
	 */
	public void setRefund(Integer refund) {
		this.refund = refund;
	}
	/** 
	 * 收费范围【供页面渲染】
	 * 格式：{"g":[12]"c":[12]"s":[12]"t":[12]"d":[12]"tg":[12]}说明：g年级c班级s学生t教师d部门tg组  
	 */
	public String getSf_range() {
		return sf_range;
	}
	/** 
	 * 收费范围【供页面渲染】
	 * 格式：{"g":[12]"c":[12]"s":[12]"t":[12]"d":[12]"tg":[12]}说明：g年级c班级s学生t教师d部门tg组  
	 */
	public void setSf_range(String sf_range) {
		this.sf_range = sf_range;
	}
	/** 
	 * 收费范围【供后台使用】格式："1234"说明：学生主键，逗号隔开  
	 */
	public String getIds() {
		return ids;
	}
	/** 
	 * 收费范围【供后台使用】格式："1234"说明：学生主键，逗号隔开  
	 */
	public void setIds(String ids) {
		this.ids = ids;
	}
	/** 
	 * 删除标记【0：未删除，1：已删除】  
	 */
	public Integer getIs_del() {
		return is_del;
	}
	/** 
	 * 删除标记【0：未删除，1：已删除】  
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
	 * 实收金额(单位：分)  
	 */
	public String getSs_money() {
		return ss_money;
	}
	/** 
	 * 实收金额(单位：分)  
	 */
	public void setSs_money(String ss_money) {
		this.ss_money = ss_money;
	}
	/** 
	 * 应收金额(单位：分)  
	 */
	public String getYs_money() {
		return ys_money;
	}
	/** 
	 * 应收金额(单位：分)  
	 */
	public void setYs_money(String ys_money) {
		this.ys_money = ys_money;
	}
	/** 
	 * 不参与人数（暂不使用）  
	 */
	public Integer getNo_num() {
		return no_num;
	}
	/** 
	 * 不参与人数（暂不使用）  
	 */
	public void setNo_num(Integer no_num) {
		this.no_num = no_num;
	}
	/** 
	 * 未支付人数  
	 */
	public Integer getNopay_num() {
		return nopay_num;
	}
	/** 
	 * 未支付人数  
	 */
	public void setNopay_num(Integer nopay_num) {
		this.nopay_num = nopay_num;
	}
	/** 
	 * 收费明细临时文件  
	 */
	public String getPay_file() {
		return pay_file;
	}
	/** 
	 * 收费明细临时文件  
	 */
	public void setPay_file(String pay_file) {
		this.pay_file = pay_file;
	}
	/** 
	 * 状态【1待执行2已撤回3执行成功4执行失败5手动关闭6已结束】  
	 */
	public Integer getStatus() {
		return status;
	}
	/** 
	 * 状态【1待执行2已撤回3执行成功4执行失败5手动关闭6已结束】  
	 */
	public void setStatus(Integer status) {
		this.status = status;
	}

}
