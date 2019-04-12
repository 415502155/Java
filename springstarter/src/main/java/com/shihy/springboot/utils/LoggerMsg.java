package com.shihy.springboot.utils;
/***
 * 
 * @Title: springstarter
 * @author shy
 * @Description 操作日志 （动作、类型、操作人等）枚举类
 * @data 2019年3月27日 下午3:30:35
 *
 */
public enum LoggerMsg {

	/***
	 * 操作动作 
	 ***/
    ACTION_ADD_USER(1, "添加用户"),
    ACTION_UPDATE_USER(2, "修改用户"),
    
    /***
	 * 操作类型
	 ***/
    TARGET_TYPE_CLASS(10001, "班级"),
    TARGET_TYPE_STUDENT(10002, "学生"),
    
    /***
	 * 操作人类型
	 ***/
    USER_TYPE_PARENT(20001, "家长"),
    USER_TYPE_TEACHER(20002, "教师")
    ;
    private Integer code;
    private String msg;

    private LoggerMsg(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

}
