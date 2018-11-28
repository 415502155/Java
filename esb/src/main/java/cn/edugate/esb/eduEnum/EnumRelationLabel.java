package cn.edugate.esb.eduEnum;

public enum EnumRelationLabel {

	父亲(0),
	母亲(1),
	爷爷(2),
	奶奶(3),
	外公(4),
	外婆(5),
	其他(6),;
	
	private EnumRelationLabel(Integer code) {
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
