package com.java.collection.util;


/**
 * 枚举类（提示信息）
 * @author Administrator
 * @param code
 * @param msg
 * @2018年1月16日
 */

public enum ReturnMsg {
	
    SUCCESS("0", "成功"),
    EXCEPTION_1("10001","ex .........1"),
    EXCEPTION_2("10002","ex .........2"),
    EXCEPTION_3("10003","ex .........3"),
    EXCEPTION_4("10004","ex .........4"),
    ERROR("-1","system ex ............");
	
    private String code;
    private String msg;
	
    private ReturnMsg(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }
	
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	
	
	

}
