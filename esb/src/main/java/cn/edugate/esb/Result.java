package cn.edugate.esb;

import org.codehaus.jackson.annotate.JsonIgnore;

public class Result<T> {
	private T data=null;
	private boolean success=false;
	private Throwable lastError=null;
	private String message="";
	private Integer code=400;	

	private String JSESSIONID;
	@JsonIgnore
	public String getJSESSIONID() {
		return JSESSIONID;
	}

	public void setJSESSIONID(String jSESSIONID) {
		JSESSIONID = jSESSIONID;
	}

	public Result() {
	}

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	@JsonIgnore
	public Throwable getLastError() {
		return lastError;
	}

	public void setLastError(Throwable lastError) {
		this.lastError = lastError;
	}

	public String getMessage() {
		if(message==null&&this.lastError!=null)
		{
			return this.lastError.getMessage();
		}
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
	public T getData() {
		return data;
	}

	public void setData(T data) {
		this.data = data;
	}
	
	public Integer getCode() {
		if(this.success){
			code = 200;
		}
		return code;
	}

	public void setCode(Integer code) {
		this.code = code;
	}
}

