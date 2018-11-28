package sng.exception;

public enum EnumException {
	user_esb_error(400,"操作不成功"),
	user_mobile_empty(1001,"登陆手机号不能为空"), 
	user_invalid_mobile_format(1002 , "手机号格式错误"), 
	user_mobile_has_been_occupied(1003 , "手机号已经被占用"),
	user_name_not_exist(1004 , "账号不存在"),
	user_nick_already_exists(1008 , "昵称已经被占用" ),
	user_password_empty(1009 , "登陆密码不能为空" ),
	user_invalid_assword_format(1010 , "密码格式错误" ),
	user_password_wrong(1011 , "账号或密码错误" ),
	user_invalid_sms_code(1012 , "验证码错误" ),
	user_expire_sms_code(1022 , "验证码失效" ),
	user_invalid_decode_error(1013 , "参数格式错误" ),
	quiz_option_rule_unit_error(2001 , "标记重复" ),
	user_invalid_old_pwd(1014 , "原密码错误" ),
	common_permission_denied(9005 , "您没有权限执行该操作" ),
	common_params_error(9002,"参数不全或格式错误"),
	common_another_device_login( 9004 , "本账号已在其他地点登录" ),
	common_must_login( 9003 , "请登陆后再操作" ),	
	common_token_expire( 9009 , "token过期" )
	;
	
	
	
	
	EnumException(Integer value,String msg){
		this.value = value;
		this.msg = msg;
	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return super.toString();
	}
	private String msg="";
	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}
	private int value = 0;
	public int getValue() {
		return value;
	}
	public void setValue(int value) {
		this.value = value;
	}
}
