package cn.edugate.esb.redis;

/**
 * 
 * 
 * @Name: 时间段类型
 * @Description: 
 */
public enum TimeField {
	/**
	 * 无时间段变更可过期
	 */
	NONE,
	/**
	 * 跨小时变更可过期
	 */
	HOUR,
	/**
	 * 跨日期变更可过期
	 */
	DATE,
	/**
	 * 跨月变更可过期
	 */
	MONTH,
	/**
	 * 跨年变更可过期
	 */
	YEAR
}
