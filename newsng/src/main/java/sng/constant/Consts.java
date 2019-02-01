package sng.constant;

import java.util.LinkedHashMap;
import java.util.Map;

public class Consts {
	/**学员支付状态 （8种）**/
	public static final Map<String, String> STUD_PAY_STATUS_MAP=new LinkedHashMap<>();
	/**星期**/
	public static final Map<String, String> WEEK_MAP=new LinkedHashMap<>();
	/**学员认证状态 （已认证 未认证）**/
	public static final Map<String, String> STUD_AUTH_STATUS_MAP=new LinkedHashMap<>();
	/**性别 （男女）**/
	public static final Map<String, String> SEX_MAP=new LinkedHashMap<>();
	/**证件类型 （身份证等）**/
	public static final Map<String, String> CARD_TYPE_MAP=new LinkedHashMap<>();
	/**与学员的关系 （父亲母亲等）**/
	public static final Map<String, String> RELATION_MAP=new LinkedHashMap<>();
	/**支付类型（微信 现金 刷卡）**/
	public static final Map<String, String> PAY_TYPE_MAP=new LinkedHashMap<>();
	/**打印状态（已打印 未打印）**/
	public static final Map<String, String> PRINT_STATUS_MAP=new LinkedHashMap<>();
	/**学期类型（学期 假期）**/
	public static final Map<String, String> TERM_TYPE_MAP=new LinkedHashMap<>();
	/**缴费操作动作（名额保留 现金缴费 刷卡缴费）**/
	public static final Map<String, String> PAY_ACTION_MAP=new LinkedHashMap<>();
	/**项目类型（1学费2认证费）**/
	public static final Map<String, String> CHARGE_TYPE_MAP=new LinkedHashMap<>();
	/** 操作类型 **/
	public static final Map<String, String> TXN_TYPE_MAP=new LinkedHashMap<>();
	/**凭证**/
	public static final Map<String, String> VOUCHER = new LinkedHashMap<String, String>();
	
	/**
	 * 班级操作动作包括：创建班级、编辑班级、删除班级、导出班级
	 * 学员操作动作包括：名额保留、缴费、转班、批量转班、删除、批量删除、退费、退费审核、批量退费审核、退费撤销、财务退费、插班。
	**/
	public static final Map<Integer, String> LOG_ACTION_MAP=new LinkedHashMap<>();
	/**班级类型**/
	public static final Map<String, String> CLASS_TYPE_MAP=new LinkedHashMap<>();
	
	/**
	 *认证类型'1':'线上' 
	 */
	public static final int AUTH_TYPE1=1;
	/**
	 * 认证类型'2':'线下'
	 */
	public static final int AUTH_TYPE2=2;
	
	/**
	 * 已认证
	 */
	public static final int AUTH_STATUS_OK=1;
	/**
	 * 未认证
	 */
	public static final int AUTH_STATUS_NO=0;
	
	/**
	 *家长端
	 */
	public static final int IDENTITY_PARENT=0;
	/**
	 *教师端
	 */
	public static final int IDENTITY_TEACHER=1;
	
	
	/**
	 * 学员状态：老生待续费
	 */
	public static final int STUD_STATUS_1OLD_TO_PAY = 1;
	/**
	 * 学员状态：名额保留中
	 */
	public static final int STUD_STATUS_2QUOTA_RESERVED = 2;
	/**
	 * 学员状态：报名待缴费
	 */
	public static final int STUD_STATUS_3SIGNUP_TO_PAY = 3;
	/**
	 * 学员状态：报名已作废
	 */
	public static final int STUD_STATUS_4SIGNUP_VOIDED = 4;
	/**
	 * 学员状态：缴费已完成
	 */
	public static final int STUD_STATUS_5PAID = 5;
	/**
	 * 学员状态：退费待审核
	 */
	public static final int STUD_STATUS_6REFUND_TO_AUDIT = 6;
	/**
	 * 学员状态：退费已审核
	 */
	public static final int STUD_STATUS_7REFUND_AUDITED = 7;
	/**
	 * 学员状态：退费已完成
	 */
	public static final int STUD_STATUS_8REFUND_FINISHED = 8;
	
	/**
	 * 学员批量转班条数限制
	 */
	public static final int STU_MOVE_COUNT = 500;
	
	public static final String REQUEST_SUCCESS = "0";
	public static final String REQUEST_EXCEPTION = "-1";
	
	/**
	 *主家长
	 */
	public static final int IS_MAIN = 1;
	
	static {
		//顺序不能变动
		STUD_PAY_STATUS_MAP.put("5", "缴费已完成");
		STUD_PAY_STATUS_MAP.put("6", "退费待审核");
		STUD_PAY_STATUS_MAP.put("1", "老生待续费");
		STUD_PAY_STATUS_MAP.put("3", "报名待缴费");
		STUD_PAY_STATUS_MAP.put("2", "名额保留中");
		STUD_PAY_STATUS_MAP.put("7", "退费已审核");
		STUD_PAY_STATUS_MAP.put("8", "退费已完成");
		STUD_PAY_STATUS_MAP.put("4", "报名已作废");
		
		CHARGE_TYPE_MAP.put("1", "学费");
		CHARGE_TYPE_MAP.put("2", "认证费");
		
		WEEK_MAP.put("1", "周一");
		WEEK_MAP.put("2", "周二");
		WEEK_MAP.put("3", "周三");
		WEEK_MAP.put("4", "周四");
		WEEK_MAP.put("5", "周五");
		WEEK_MAP.put("6", "周六");
		WEEK_MAP.put("7", "周日");
		WEEK_MAP.put("8", "其他");
		
		STUD_AUTH_STATUS_MAP.put("1", "已认证");
		STUD_AUTH_STATUS_MAP.put("0", "未认证");
		
		SEX_MAP.put("1", "男");
		SEX_MAP.put("0", "女");
		
		CARD_TYPE_MAP.put("1", "身份证");
		CARD_TYPE_MAP.put("2", "台胞证");
		CARD_TYPE_MAP.put("3", "护照");
		
		RELATION_MAP.put("1", "父亲");
		RELATION_MAP.put("2", "母亲");
		RELATION_MAP.put("3", "爷爷");
		RELATION_MAP.put("4", "奶奶");
		RELATION_MAP.put("5", "外公");
		RELATION_MAP.put("6", "外婆");
		RELATION_MAP.put("7", "其他");
		
		PAY_TYPE_MAP.put("0", "未支付");
		PAY_TYPE_MAP.put("1", "微信");
		PAY_TYPE_MAP.put("2", "现金");
		PAY_TYPE_MAP.put("3", "刷卡");
		PAY_TYPE_MAP.put("4", "银联");
		
		PRINT_STATUS_MAP.put("1", "已打印");
		PRINT_STATUS_MAP.put("2", "未打印");
		
		TERM_TYPE_MAP.put("1", "学期");
		TERM_TYPE_MAP.put("2", "假期");
		
		PAY_ACTION_MAP.put("0", "尚未支付");
		PAY_ACTION_MAP.put("1", "名额保留");
		PAY_ACTION_MAP.put("2", "现金缴费");
		PAY_ACTION_MAP.put("3", "刷卡缴费");
		PAY_ACTION_MAP.put("4", "手机银联");
		
		TXN_TYPE_MAP.put("01", "线上缴费");
		TXN_TYPE_MAP.put("04", "线上退费");
		TXN_TYPE_MAP.put("SQYLTF", "申请线上退费");
		TXN_TYPE_MAP.put("TYYLTF", "同意线上退费");
		TXN_TYPE_MAP.put("BHYLTF", "驳回线上退费");
		TXN_TYPE_MAP.put("XXJF", "线下缴费");
		TXN_TYPE_MAP.put("SQXXTF", "退费已审核");
		TXN_TYPE_MAP.put("PREVIEW", "退费待审核");
		TXN_TYPE_MAP.put("TYXXTF", "同意线下退费");
		TXN_TYPE_MAP.put("BHXXTF", "驳回线下退费");
		
		LOG_ACTION_MAP.put(1, "创建班级");
		LOG_ACTION_MAP.put(2, "编辑班级");
		LOG_ACTION_MAP.put(3, "删除班级");
		LOG_ACTION_MAP.put(4, "导出班级");
		LOG_ACTION_MAP.put(5, "名额保留");
		LOG_ACTION_MAP.put(6, "缴费");
		LOG_ACTION_MAP.put(7, "转班");
		LOG_ACTION_MAP.put(8, "批量转班");
		LOG_ACTION_MAP.put(9, "删除");
		LOG_ACTION_MAP.put(10, "批量删除");
		LOG_ACTION_MAP.put(11, "退费");
		LOG_ACTION_MAP.put(12, "退费审核");
		LOG_ACTION_MAP.put(13, "批量退费审核");
		LOG_ACTION_MAP.put(14, "退费撤销");
		LOG_ACTION_MAP.put(15, "财务退费");
		LOG_ACTION_MAP.put(16, "插班");
		
		CLASS_TYPE_MAP.put("1", "老生班");
		CLASS_TYPE_MAP.put("2", "新生班");
		
		VOUCHER.put("type_1", "主标题");
		VOUCHER.put("type_2", "副标题");
		VOUCHER.put("type_3", "学期");
		VOUCHER.put("type_4", "学员姓名");
		VOUCHER.put("type_5", "证件号");
		VOUCHER.put("type_6", "教师姓名");
		VOUCHER.put("type_7", "科目班级");
		VOUCHER.put("type_8", "班级类型");
		VOUCHER.put("type_9", "课时数");
		VOUCHER.put("type_10", "开课日期");
		VOUCHER.put("type_11", "上课时间");
		VOUCHER.put("type_12", "学费标准");
		VOUCHER.put("type_13", "校区");
		VOUCHER.put("type_14", "教室");
		VOUCHER.put("type_15", "班容量");
		VOUCHER.put("type_16", "缴费方式");
		VOUCHER.put("type_17", "缴费金额");
		VOUCHER.put("type_18", "缴费金额大写");
		VOUCHER.put("type_19", "缴费时间");
		VOUCHER.put("type_20", "凭证编号");
		VOUCHER.put("type_21", "凭证日期");
		VOUCHER.put("type_22", "操作人");
		VOUCHER.put("type_23", "自定义1");
		VOUCHER.put("type_24", "自定义2");
		VOUCHER.put("type_25", "自定义3");
		VOUCHER.put("type_26", "自定义4");
		VOUCHER.put("type_27", "自定义5");
		VOUCHER.put("type_28", "自定义6");
	}
}
