package sng.util;
/***
 * 
 *  @Company sjwy
 *  @Title: EnumLog.java 
 *  @Description: 班级操作动作 学员操作动作
 */
public enum EnumLog {
	
	OPERATION_CLASS_CREATE(1,"创建班级"),
	OPERATION_CLASS_EDIT(2,"编辑班级"),
	OPERATION_CLASS_DEL(3,"删除班级"),
	OPERATION_CLASS_EXPORT(4,"导出班级"),
	OPERATION_CLASS_IMPORT(5,"导入班级"),

	OPERATION_STU_RESERVED(6,"名额保留"),
	OPERATION_STU_PAY_FEES(7,"学员缴费"),
	OPERATION_STU_TRANSFER(8,"学员转班"),
	OPERATION_STU_BATCH_TRANSFER(9,"批量转班"),
	OPERATION_STU_DEL(10,"删除学员"),
	OPERATION_STU_BATCH_DEL(11,"批量删除学员"),
	OPERATION_STU_RETURN_PREMIUM(12,"后台退费"),
	OPERATION_STU_RETURN_AUDIT(13,"退费审核"),
	OPERATION_STU_BATCH_RETURN_AUDIT(14,"批量退费审核"),
	OPERATION_STU_RETURN_REVOCATION(15,"学员撤销"),
	OPERATION_STU_FINANCIAL_REFUND(16,"执行退费"),
	OPERATION_STU_JOIN_CLASS(17,"学员插班"),
	OPERATION_STU_IMPORT_CLASS(18,"导入学员"),
	
	TARGET_TYPE_CLASS(1,"操作对象-班级"),
	TARGET_TYPE_STU(2,"操作对象-学员"),
	
	USER_TYPE_TEACH(1,"用户类型-教师"),
	USER_TYPE_PARENT(2,"用户类型-家长"),
	;
	
	private Integer key;
	private String value;
	
	public Integer getKey() {
		return key;
	}
	public void setKey(Integer key) {
		this.key = key;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	
	EnumLog(Integer key, String value) {
		this.key = key;
		this.value = value;
	}
	
}
