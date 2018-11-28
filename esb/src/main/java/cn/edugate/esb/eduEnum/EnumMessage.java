package cn.edugate.esb.eduEnum;

public enum EnumMessage {
	success(200,"请求成功"),
	fail(204,"请求失败"),
	group_not_found(3304,"没有找到对应的组"),
	groupmembers_add_failed(3401,"批量添加组成员失败"),
	groupmembers_not_found(3404,"没有找到对应的组成员"),
	organization_not_found(3504,"没有找到对应的组织机构"),
	student_not_found(3604,"没有找到对应的学生信息"),
	field_not_found(3704,"没有找到对应的场地信息"), 
	department_not_found(3804,"没有找到对应的部门信息"),
	department_name_passed(3810,"部门名称可用"),
	department_name_illegal(3814,"部门名称不可用"),
	phone_illegal(3814,"手机号不可用"),
	param_illegal(3815,"信息不完整"),
	;
	

	private EnumMessage(Integer code, String message) {
		this.code = code;
		this.message = message;
	}
	
	private Integer code = 0;
	private String message = "";
	public Integer getCode() {
		return code;
	}
	public void setCode(Integer code) {
		this.code = code;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	
}
