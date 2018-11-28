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
	/**
	 * 班级操作动作包括：创建班级、编辑班级、删除班级、导出班级
	 * 学员操作动作包括：名额保留、缴费、转班、批量转班、删除、批量删除、退费、退费审核、批量退费审核、退费撤销、财务退费、插班。
	**/
	public static final Map<Integer, String> LOG_ACTION_MAP=new LinkedHashMap<>();
	/**班级类型**/
	public static final Map<String, String> CLASS_TYPE_MAP=new LinkedHashMap<>();
	
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
		
		PAY_TYPE_MAP.put("1", "微信");
		PAY_TYPE_MAP.put("2", "现金");
		PAY_TYPE_MAP.put("3", "刷卡");
		
		PRINT_STATUS_MAP.put("1", "已打印");
		PRINT_STATUS_MAP.put("2", "未打印");
		
		TERM_TYPE_MAP.put("1", "学期");
		TERM_TYPE_MAP.put("2", "假期");
		
		PAY_ACTION_MAP.put("1", "名额保留");
		PAY_ACTION_MAP.put("2", "现金缴费");
		PAY_ACTION_MAP.put("3", "刷卡缴费");
		
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
	}
}
