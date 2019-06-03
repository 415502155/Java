package com.shy.springboot.utils;
/***
 * 
 * @Title: springstarter
 * @author shy
 * @Description TODO
 * @data 2019年3月27日 下午3:30:35
 *
 */
public class Constant {
	
	/**
	 * 是否删除   0：未删除
	 */
	public static final Integer IS_DEL_NO = 0;
	/**
	 * 是否删除  1：删除
	 */
	public static final Integer IS_DEL_YES = 1;
	/**
	 * 日期格式 YYMMDD 年月日（20180808）
	 */
	public static final String YYMMDD = "yyyy-MM-dd";
	/**
	 * 日期格式 YYMMDDHHMMSS 年月日 时:分:秒（2018-08-08 10:10:10）
	 */
	public static final String YYMMDDHHMMSS = "yyyy-MM-dd HH:mm:ss";
	/***
	 * MAN： 男
	 */
	public static final String MAN = "男";
	/***
	 * MAN： 女
	 */
	public static final String WOMAN = "女";
	/***
	 * SECRECY： 保密
	 */
	public static final String SECRECY = "保密";
	/***
	 * MAN_SEX：1 男
	 */
	public static final Integer MAN_SEX = 1;
	/***
	 * WOMAN_SEX：2 女
	 */
	public static final Integer WOMAN_SEX = 2;
	/***
	 * SECRECY_SEX：0  保密
	 */
	public static final Integer SECRECY_SEX = 0;
	/***
	 * 常量：0
	 */
	public static final Integer ZERO = 0;
	/***
	 * 常量：1
	 */
	public static final Integer ONE = 1;
	/***
	 * 常量：-1
	 */
	public static final Integer NEGATIVE = -1;
	/***
	 * 逗号
	 */
	public static final String COMMA = ",";
	/***
	 *  0:待缴费；1：缴费；2：退费；
	 */
	public static final Integer UNPAY_METHOD = 0;
	/***
	 *  1：缴费完成
	 */
	public static final Integer PAY_FINISH_METHOD = 1;
	/***
	 *  2：退费完成；
	 */
	public static final Integer RETURN_PREMIUM_METHOD = 2;
	/***
	 *  3：缴费超时；
	 */
	public static final Integer PAY_TIMEOUT_METHOD = 3;
	/***
	 * 延迟队列type x-delayed-message
	 */
	public static final String X_DELAYED_MESSAGE = "x-delayed-message";
	/***
	 * 延迟队列args key = "x-delayed-type"
	 */
	public static final String DELAY_ARGS_KEY = "x-delayed-type";
	/***
	 * 延迟队列args value = "direct"
	 */
	public static final String DELAY_ARGS_VALUE = "direct";
	/***
	 * 
	 */
	public static final String DELAY_HEADER_KEY = "x-delay"; 
	/***
	 * 延迟队列delay_order_status_queue
	 */
	public static final String DELAY_ORDER_STATUS_QUEUE = "delay_order_status_queue";
	/***
	 * 延迟队列delay_order_status_exchange
	 */
	public static final String DELAY_ORDER_STATUS_EXCHANGE = "delay_order_status_exchange";
	/***
	 * 延迟队列delay_test_queue
	 */
	public static final String DELAY_TEST_QUEUE = "delay_test_queue";
	/***
	 * 延迟队列delay_test_exchange
	 */
	public static final String DELAY_TEST_EXCHANGE = "delay_test_exchange";
	/***
	 * 延迟队列delay_update_user_queue
	 */
	public static final String DELAY_UPDATE_USER_QUEUE = "delay_update_user_queue";
	/***
	 * 延迟队列delay_update_user_exchange
	 */
	public static final String DELAY_UPDATE_USER_EXCHANGE = "delay_update_user_exchange";
	
	/***
	 * DirectConfig 
	 * direct_exchange_a
	 */
	public static final String DIRECT_EXCHANGE_A = "direct_exchange_a";
	public static final String DIRECT_QUEUE_A = "direct_queue_a";

	
	/***
	 * 初始化密码
	 */
	public static final String INITIALIZE_PASSWORD = "111111";
	/***
	 * 导出EXCEL文件名称 USER_EXCEL_FILENAME=用户列表.xls
	 */
	public static final String USER_EXCEL_FILENAME = "用户列表.xls";
	/***
	 * 导出EXCEL下的SHEET名称 USER_EXCEL_SHEETNAME=用户列表
	 */
	public static final String USER_EXCEL_SHEETNAME = "用户列表";
	
	
}
