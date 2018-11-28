package sng.exception;

public class EsbException extends Exception {
	/**
	 * 通用异常
	 */
	private static final long serialVersionUID = 1L;
	private Integer code;
	public Integer getCode() {
		return code;
	}

	public void setCode(Integer code) {
		this.code = code;
	}
	
	public EsbException(String message) {
        super(message);
    }
	
	public EsbException(EnumException enumexception) {		
        super(enumexception.getMsg());
        this.code = enumexception.getValue();
    }

}
