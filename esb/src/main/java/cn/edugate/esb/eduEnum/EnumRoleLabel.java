package cn.edugate.esb.eduEnum;

public enum EnumRoleLabel {
	管理员(1),
	校长(2),
	任课教师(3),
	班主任(4),
	年级组长(5),
	学生组管理员(6),
	部门管理员(7),
	教师组管理员(8),
	功能管理员(9),
	分校校长(10),
	财务管理员(17),
	财务审批员(18),
	局管理员(11),
	局长(12),
	局部门管理员(13),
	局教师组管理员(14),
	局功能管理员(15),
	局职员(16)
	;
	

	private EnumRoleLabel(Integer code) {
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
