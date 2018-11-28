package sng.constant;

import java.util.HashMap;
import java.util.Map;

/**
 * @类 名： ExceptionConstant
 * @功能描述：异常通知处理常量类 
 * @作者信息： LiuYang
 * @创建时间： 2018年11月2日上午10:36:50
 */
public class ErrorConstant {


	/*********************************系统9开头**************************************/
	/**
	 * 异常类型：【模板】未查询到模板
	 */
	public static final int ERROR_TYPE_TEMP_NOTEMP = 910001;
	/**
	 * 异常类型：【缓存】缓存数据异常
	 */
	public static final int ERROR_TYPE_REDIS_DATA = 920001;
	/*********************************支付6开头**************************************/
	/**
	 * 异常类型：【支付】重复的支付
	 */
	public static final int ERROR_TYPE_PAY_DUPLICATION = 601001;
	/**
	 * 异常类型：【支付】银联退费返回异常
	 */
	public static final int ERROR_TYPE_UNION_REFUND_ERROR = 612001;
	/**
	 * 异常类型:【支付】光大支付返回异常
	 */
	public static final int ERROR_TYPE_CEB_PAY_ERROR = 621001;
	/**
	 * 异常类型:【支付】光大退费返回异常
	 */	
	public static final int ERROR_TYPE_CEB_REFUND_ERROR = 622001;
	/**
	 * 异常类型:【支付】光大对账返回异常
	 */
	public static final int ERROR_TYPE_CEB_BILL_ERROR = 623001;
	/**
	 * 异常类型：【支付】银联验签失败
	 */
	public static final int ERROR_TYPE_VALIDATE_ERROR = 613001;
	/**
	 * 异常类型：【支付】银联通讯返回代码异常
	 */
	public static final int ERROR_TYPE_REFUND_HTTPERROR = 614001;
	/**
	 * 异常类型：【支付】15分钟查询未果
	 */
	public static final int ERROR_TYPE_15QUERY_NORESULT = 602001;
	/**
	 * 异常类型：【支付】支付展开异常
	 */
	public static final int ERROR_TYPE_CHARGE_CREATE_ERROR = 603001;
	/**
	 * 异常类型：【支付】退费异常结果通知
	 */
	public static final int ERROR_TYPE_CEB_REFUND_RESULT = 604001;
	
	
	/**
	 * 异常通知目标：通知教师-财务管理员
	 */
	public static final int EXCEPTION_TARGET_TEACHER_CAIWU = 0;
	/**
	 * 异常通知目标：通知世纪伟业软件工程师-支付
	 */
	public static final int EXCEPTION_TARGET_PROGRAMMER_PAY = 1;
	/**
	 * 异常通知目标：通知世纪伟业软件工程师-微信模板配置
	 */
	public static final int EXCEPTION_TARGET_PROGRAMMER_WX = 4;
	/**
	 * 异常通知目标：通知世纪伟业软件工程师-缓存和ESB数据管理员
	 */
	public static final int EXCEPTION_TARGET_PROGRAMMER_REDIS = 5;
	/**
	 * 异常通知目标：通知世纪伟业业务人员-支付
	 */
	public static final int EXCEPTION_TARGET_SJWY_BUSINESS_PAY = 2;
	/**
	 * 异常通知目标：通知世纪伟业客服人员-支付
	 */
	public static final int EXCEPTION_TARGET_SJWY_CUSTOMER_SERVICE = 3;
	
	
	/**
	 * 异常通知等级：不分昼夜，即刻通知
	 */
	public static final int EXCEPTION_LEVEL_IMMEDIATELY = 0;
	/**
	 * 异常通知等级：夜间不通知，8:00-22:00通知
	 */
	public static final int EXCEPTION_LEVEL_NOT_NIGHT = 1;
	/**
	 * 异常通知等级：工作日的9:00-17:00通知
	 */
	public static final int EXCEPTION_LEVEL_WORKTIME = 2;
	
	
	/**
	 * 需要通知业务管理员的异常代码
	 */
	public static final Map<String,String> ERROR_MAP_BUSINESS_MANAGER = new HashMap<String,String>();
	/**
	 * 需要通知系统管理员的异常代码
	 */
	public static final Map<String,String> ERROR_MAP_SYSTEM_MANAGER = new HashMap<String,String>();
	/**
	 * 需要通知财务管理员老师的异常代码
	 */
	public static final Map<String,String> ERROR_MAP_TEACHER_MANAGER = new HashMap<String,String>();



	
	static {
		//通知系统管理员的异常
		ERROR_MAP_SYSTEM_MANAGER.put("LJTK001","网络缴费解包失败");
		ERROR_MAP_SYSTEM_MANAGER.put("LJTK002","网络缴费打包失败");
		ERROR_MAP_SYSTEM_MANAGER.put("LJTK003","缴费项目编号不能为空");
		ERROR_MAP_SYSTEM_MANAGER.put("LJTK004","交易订单号不能为空");
		ERROR_MAP_SYSTEM_MANAGER.put("LJTK005","交易日期不能为空");
		ERROR_MAP_SYSTEM_MANAGER.put("LJTK006","退款订单号不能为空");
		ERROR_MAP_SYSTEM_MANAGER.put("LJTK007","退款日期不能为空");
		ERROR_MAP_SYSTEM_MANAGER.put("LJTK008","退款金额不能为空");
		ERROR_MAP_SYSTEM_MANAGER.put("LJTK009","退款结果不能为空");
		ERROR_MAP_SYSTEM_MANAGER.put("LJTK010","商户号不能为空");
		ERROR_MAP_SYSTEM_MANAGER.put("LJTK011","终端流水号不能为空");
		ERROR_MAP_SYSTEM_MANAGER.put("LJTK012","原交易记录不存在");
		ERROR_MAP_SYSTEM_MANAGER.put("LJTK013","取表数据失败");
		ERROR_MAP_SYSTEM_MANAGER.put("LJTK014","记录与信息不匹配");
		ERROR_MAP_SYSTEM_MANAGER.put("LJTK015","原交易金额不能为空");
		ERROR_MAP_SYSTEM_MANAGER.put("LJTK016","异常退款申请,受理失败");
		ERROR_MAP_SYSTEM_MANAGER.put("LJTK017","原交易金额错误");
		ERROR_MAP_SYSTEM_MANAGER.put("LJTK018","该交易正在受理退款请求，请稍后再试！");
		ERROR_MAP_SYSTEM_MANAGER.put("LJTK019","该缴费项目未开通联机退款功能");
		ERROR_MAP_SYSTEM_MANAGER.put("LJTK020","对应渠道未开通联机退款功能");
		ERROR_MAP_SYSTEM_MANAGER.put("LJTK021","原交易允许退款次数已满，不能继续受理！");
		ERROR_MAP_SYSTEM_MANAGER.put("LJTK022","退款金额已经超过原交易总金额！");
		ERROR_MAP_SYSTEM_MANAGER.put("LJTK023","查询分行代码失败");
		//通知老师的异常
		ERROR_MAP_TEACHER_MANAGER.put("LJTK024","处理超时,请稍后进行状态查询");
		ERROR_MAP_TEACHER_MANAGER.put("EGG0006","主帐户不存在");
		ERROR_MAP_TEACHER_MANAGER.put("EGG0009","卡不存在");
		ERROR_MAP_TEACHER_MANAGER.put("EGG0014","主帐户状态不正常");
		ERROR_MAP_TEACHER_MANAGER.put("EGG0017","卡状态不正常");
		ERROR_MAP_TEACHER_MANAGER.put("EGG0021","卡余额不足");
		ERROR_MAP_TEACHER_MANAGER.put("EGG0023","帐户已封存");
		ERROR_MAP_TEACHER_MANAGER.put("EGG0025","帐户已被删除");
		ERROR_MAP_TEACHER_MANAGER.put("EGG0044","帐户余额不足");
		ERROR_MAP_TEACHER_MANAGER.put("EGG0845","帐号不存在");
		ERROR_MAP_TEACHER_MANAGER.put("EGG0861","余额不足");
		ERROR_MAP_TEACHER_MANAGER.put("EGG0867","客户账号不存在");
		ERROR_MAP_TEACHER_MANAGER.put("EGG0879","该帐户已销户！");
		//通知业务管理员的异常
		ERROR_MAP_BUSINESS_MANAGER.put("EGG0006","主帐户不存在");
		ERROR_MAP_BUSINESS_MANAGER.put("EGG0009","卡不存在");
		ERROR_MAP_BUSINESS_MANAGER.put("EGG0014","主帐户状态不正常");
		ERROR_MAP_BUSINESS_MANAGER.put("EGG0017","卡状态不正常");
		ERROR_MAP_BUSINESS_MANAGER.put("EGG0021","卡余额不足");
		ERROR_MAP_BUSINESS_MANAGER.put("EGG0023","帐户已封存");
		ERROR_MAP_BUSINESS_MANAGER.put("EGG0025","帐户已被删除");
		ERROR_MAP_BUSINESS_MANAGER.put("EGG0044","帐户余额不足");
		ERROR_MAP_BUSINESS_MANAGER.put("EGG0077","此借据已入帐");
		ERROR_MAP_BUSINESS_MANAGER.put("EGG0112","还本金额大于拆借户余额");
		ERROR_MAP_BUSINESS_MANAGER.put("EGG0114","还款金额超过了还款户可用金额");
		ERROR_MAP_BUSINESS_MANAGER.put("EGG0115","还款凭证种类和借据中登记的不一致");
		ERROR_MAP_BUSINESS_MANAGER.put("EGG0128","借据号必须全部由数字组成");
		ERROR_MAP_BUSINESS_MANAGER.put("EGG0129","借据号的长度不正确");
		ERROR_MAP_BUSINESS_MANAGER.put("EGG0130","借据号中的年份不正确");
		ERROR_MAP_BUSINESS_MANAGER.put("EGG0150","起始日期大于终止日期");
		ERROR_MAP_BUSINESS_MANAGER.put("EGG0200","终止日必须大于起始日");
		ERROR_MAP_BUSINESS_MANAGER.put("EGG0214","注意交易密码连续输错超过3次");
		ERROR_MAP_BUSINESS_MANAGER.put("EGG0215","维护日期已经被修改无法冲正");
		ERROR_MAP_BUSINESS_MANAGER.put("EGG0228","不到发行期,不能兑付");
		ERROR_MAP_BUSINESS_MANAGER.put("EGG0229","不到国债发售期,不能出售");
		ERROR_MAP_BUSINESS_MANAGER.put("EGG0253","此笔预授权已完成或取消");
		ERROR_MAP_BUSINESS_MANAGER.put("EGG0256","此方式下不可以归还本金");
		ERROR_MAP_BUSINESS_MANAGER.put("EGG0258","此授信已经过期");
		ERROR_MAP_BUSINESS_MANAGER.put("EGG0259","存款金额未到大额起存金额");
		ERROR_MAP_BUSINESS_MANAGER.put("EGG0261","存期已经被修改无法冲正");
		ERROR_MAP_BUSINESS_MANAGER.put("EGG0274","到期日必须大于当前日期");
		ERROR_MAP_BUSINESS_MANAGER.put("EGG0277","到期日小于当前日期");
		ERROR_MAP_BUSINESS_MANAGER.put("EGG0278","到期日已经被修改无法冲正");
		ERROR_MAP_BUSINESS_MANAGER.put("EGG0293","冻结金额错误");
		ERROR_MAP_BUSINESS_MANAGER.put("EGG0294","冻结金额小于预授权金额");
		ERROR_MAP_BUSINESS_MANAGER.put("EGG0305","二磁道长度错误");
		ERROR_MAP_BUSINESS_MANAGER.put("EGG0339","国债额度表中无此记录");
		ERROR_MAP_BUSINESS_MANAGER.put("EGG0340","国债额度表中已有下拨记录,不能再下拨");
		ERROR_MAP_BUSINESS_MANAGER.put("EGG0341","国债管理表中无此期国债");
		ERROR_MAP_BUSINESS_MANAGER.put("EGG0347","划款金额不能为零");
		ERROR_MAP_BUSINESS_MANAGER.put("EGG0356","记录数为零,没有符合查询条件的数据");
		ERROR_MAP_BUSINESS_MANAGER.put("EGG0363","交易金额不符");
		ERROR_MAP_BUSINESS_MANAGER.put("EGG0364","交易金额不能小于等于零");
		ERROR_MAP_BUSINESS_MANAGER.put("EGG0365","交易金额不足自助设备/电话汇款最低限额");
		ERROR_MAP_BUSINESS_MANAGER.put("EGG0366","交易金额超过预授权金额");
		ERROR_MAP_BUSINESS_MANAGER.put("EGG0367","交易金额小于该储种的最低支取金额");
		ERROR_MAP_BUSINESS_MANAGER.put("EGG0368","交易金额与预授权金额不符");
		ERROR_MAP_BUSINESS_MANAGER.put("EGG0369","交易金额与约定金额不符");
		ERROR_MAP_BUSINESS_MANAGER.put("EGG0370","交易密码错误");
		ERROR_MAP_BUSINESS_MANAGER.put("EGG0372","交易日期大于到期日期");
		ERROR_MAP_BUSINESS_MANAGER.put("EGG0376","金额必须大于零");
		ERROR_MAP_BUSINESS_MANAGER.put("EGG0378","金额不相符");
		ERROR_MAP_BUSINESS_MANAGER.put("EGG0388","买入金额必须大于0");
		ERROR_MAP_BUSINESS_MANAGER.put("EGG0389","卖出金额必须大于0");
		ERROR_MAP_BUSINESS_MANAGER.put("EGG0391","密码不符");
		ERROR_MAP_BUSINESS_MANAGER.put("EGG0475","退货交易要素和原交易不匹配");
		ERROR_MAP_BUSINESS_MANAGER.put("EGG0476","退款金额不能为零");
		ERROR_MAP_BUSINESS_MANAGER.put("EGG0477","退款金额应该等于挂帐金额");
		ERROR_MAP_BUSINESS_MANAGER.put("EGG0482","网点已日结");
		ERROR_MAP_BUSINESS_MANAGER.put("EGG0510","移存金额必须大于零");
		ERROR_MAP_BUSINESS_MANAGER.put("EGG0514","已到国债兑付期,不能出售");
		ERROR_MAP_BUSINESS_MANAGER.put("EGG0517","已挂失");
		ERROR_MAP_BUSINESS_MANAGER.put("EGG0521","已无符合条件的记录");
		ERROR_MAP_BUSINESS_MANAGER.put("EGG0522","已销记");
		ERROR_MAP_BUSINESS_MANAGER.put("EGG0536","终止日期不合法");
		ERROR_MAP_BUSINESS_MANAGER.put("EGG0550","非POS柜员，拒绝");
		ERROR_MAP_BUSINESS_MANAGER.put("EGG0657","帐号不合法");
		ERROR_MAP_BUSINESS_MANAGER.put("EGG0664","账号长度错误");
		ERROR_MAP_BUSINESS_MANAGER.put("EGG0674","查询记录不存在");
		ERROR_MAP_BUSINESS_MANAGER.put("EGG0684","被冲正明细记录状态异常,无法冲正");
		ERROR_MAP_BUSINESS_MANAGER.put("EGG0845","帐号不存在");
		ERROR_MAP_BUSINESS_MANAGER.put("EGG0861","余额不足");
		ERROR_MAP_BUSINESS_MANAGER.put("EGG0867","客户账号不存在");
		ERROR_MAP_BUSINESS_MANAGER.put("EGG0879","该帐户已销户！");
		ERROR_MAP_BUSINESS_MANAGER.put("ERRSYS","统错");
		ERROR_MAP_BUSINESS_MANAGER.put("ETS0125","非活期帐户不能使用活期互转交易");
		ERROR_MAP_BUSINESS_MANAGER.put("ETS0136","关联帐户不允许转帐");
		ERROR_MAP_BUSINESS_MANAGER.put("ETS0137","国债登记簿中无此帐户");
		ERROR_MAP_BUSINESS_MANAGER.put("ETS0148","取款后帐户余额小于通知存款起存金额");
		ERROR_MAP_BUSINESS_MANAGER.put("ETS0151","预授权完成后,帐户可用余额小于0");
		ERROR_MAP_BUSINESS_MANAGER.put("ETS0155","帐户冻结金额错误,无法冲正");
		ERROR_MAP_BUSINESS_MANAGER.put("ETS0157","帐户起息日已经被修改无法冲正");
		ERROR_MAP_BUSINESS_MANAGER.put("ETS0158","帐户属性已经被修改无法冲正");
		ERROR_MAP_BUSINESS_MANAGER.put("ETS0229","此卡已超过有效期");
		ERROR_MAP_BUSINESS_MANAGER.put("ETS0251","卡二磁道信息不正确");
		ERROR_MAP_BUSINESS_MANAGER.put("ETS0297","卡合法性检查失败");
		ERROR_MAP_BUSINESS_MANAGER.put("ETS0301","卡密码不相符");
		ERROR_MAP_BUSINESS_MANAGER.put("ETS0311","请在开卡行打印国债凭证");
		ERROR_MAP_BUSINESS_MANAGER.put("ETS0317","系统无该卡的相关信息");
		ERROR_MAP_BUSINESS_MANAGER.put("ETS0319","预授权卡号不是此卡号");
		ERROR_MAP_BUSINESS_MANAGER.put("ETS0338","授信客户号与存款户的客户号不一致");
		ERROR_MAP_BUSINESS_MANAGER.put("ETS0357","客户帐号对照表中没有卖出币种帐号");
		ERROR_MAP_BUSINESS_MANAGER.put("ETS0359","客户帐户与凭证记录不一致");
		ERROR_MAP_BUSINESS_MANAGER.put("ETS0394","借,贷不能是同一帐号");
		ERROR_MAP_BUSINESS_MANAGER.put("ETS0396","申请人帐号不符");
		ERROR_MAP_BUSINESS_MANAGER.put("ETS0404","无收款帐号");
		ERROR_MAP_BUSINESS_MANAGER.put("ETS0437","此交易不能使用该帐号");
		ERROR_MAP_BUSINESS_MANAGER.put("ETS0479","贷款到期日应小于抵质押到期日");
		ERROR_MAP_BUSINESS_MANAGER.put("ETS0492","原交易日期必须是当前交易日期");
		ERROR_MAP_BUSINESS_MANAGER.put("ETS0505","磁道号输入错误");
		ERROR_MAP_BUSINESS_MANAGER.put("ETS0512","客户号,帐号不可同时有值");
		ERROR_MAP_BUSINESS_MANAGER.put("ETS0513","卡的客户号与输入的客户号不匹配");
		ERROR_MAP_BUSINESS_MANAGER.put("ETS0553","金额不能小于零");
		ERROR_MAP_BUSINESS_MANAGER.put("ETS0566","总笔数必须大于0");
		ERROR_MAP_BUSINESS_MANAGER.put("ETS0567","总金额必须大于0");
		ERROR_MAP_BUSINESS_MANAGER.put("ETS0649","原交易状态不正常");
	}
	
}
