package cn.edugate.esb.eduEnum;

public enum EnumExcelRes {
	资源添加(0),
	批量添加学生(1),
	批量添加家长(2),
	批量添加教师(3)
	;
	

	private EnumExcelRes(Integer code) {
		this.code = code;
	}
	
	private Integer code = 0;
	public final Integer getCode() {
		return code;
	}
	public void setCode(Integer code) {
		this.code = code;
	}
	
}
