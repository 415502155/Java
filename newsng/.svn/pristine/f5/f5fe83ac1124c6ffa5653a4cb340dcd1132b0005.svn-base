package sng.pojo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ResultEx extends Result {

	public ResultEx() {
		super();
	}
	
	public static ResultEx success(Object data) {
		ResultEx resultEx=new ResultEx();
		resultEx.setSuccess(true);
		resultEx.setData(data);
		return resultEx;
	}
	
	public static ResultEx success(String message,Object data) {
		ResultEx resultEx=new ResultEx();
		resultEx.setSuccess(true);
		resultEx.setMessage(message);
		resultEx.setData(data);
		return resultEx;
	}
	
	public static ResultEx error(int code,String message,Object data) {
		ResultEx resultEx=new ResultEx();
		resultEx.setSuccess(false);
		resultEx.setMessage(message);
		resultEx.setCode(code);
		resultEx.setData(data);
		return resultEx;
	}
	
	public static ResultEx response(boolean success,int code,String message,Object data) {
		ResultEx resultEx=new ResultEx();
		resultEx.setSuccess(success);
		resultEx.setCode(code);
		resultEx.setMessage(message);
		resultEx.setData(data);
		return resultEx;
	}
}
