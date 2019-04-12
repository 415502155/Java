package com.shihy.springboot.exception;

public final class ApiResultGenerator {
	
	public static ApiResult result(boolean flag, String msg, Object object, Throwable throwable) {
		ApiResult apiResult = ApiResult.newInstance();
		apiResult.setFlag(flag);
		apiResult.setMsg(msg == "" ? "success" : msg);
		apiResult.setObject(object);
		return apiResult;
	}
	
	public static ApiResult successResult(Object object) {
		return result(true, "", object, null);
	}
	
	public static ApiResult errorApiResult(String msg, Throwable throwable) {
		return result(false, msg, "", throwable);
	}
}
