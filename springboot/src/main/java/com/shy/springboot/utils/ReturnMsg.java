package com.shy.springboot.utils;
/***
 * 
 * @Title: springstarter
 * @author shy
 * @Description TODO
 * @data 2019年3月27日 下午3:56:23
 *
 */
public enum ReturnMsg {
	/***
	 * 请求返回状态码以及相应的返回信息
	 */
    SUCCESS("0", "成功"),
    UPLOAD_FILE_EX1("10002", "上传文件不能为空"),
    UPLOAD_FILE_EX2("10003", "上传文件超过最大限制"),
    UPLOAD_FILE_EX3("10001", "请求参数不正确"),
    LOGIN_IN_EX1("20001", "该用户不存在，请先注册"),
    LOGIN_IN_EX2("20002", "用户或密码错误，请重新输入"),
    LOGIN_IN_EX3("20003", "用户或密码不能为空，请重新输入"),
    LOGIN_IN_EX4("20004", "该用户没有分配权限，请咨询管理员"),
    
    EXCEL_IMPORT_EX1("30001", "已注册，不可导入"),
    
    PARAM_ERROR_EX1("40001", "请求参数不能为空"),
    ERROR("-1","数据服务异常");
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
