package sng.util;

import sng.pojo.Result;

public class ReturnResult extends Result<Object> {
 
	//private long total;
	//private long totalPage;
	private Object data;
	private Integer code;
	private String message;
	boolean success;
 
	private ReturnResult(long total, long totalPage, Object data, Integer code, String message, boolean success) {
		//this.total = total;
		//this.totalPage = totalPage;
		this.data = data;
		this.code = code;
		this.message = message;
		this.success = success;
	}
	
	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	private ReturnResult(double a) {
		super();
	}

	private ReturnResult(long total, long totalPage, Object data) {
		//this.total = total;
		//this.totalPage = totalPage;
		this.data = data;
		this.code = ReturnMsg.SUCCESS.getCode();
		this.message = ReturnMsg.SUCCESS.getMsg();
		this.success = true;
	}
	
	private ReturnResult(long total, Object data, Integer code, String message) {
		//this.total = total;
		this.data = data;
		this.code = code;
		this.message = message;
		this.success = true;
	}
	
	private ReturnResult(Integer code, String message, Object data) {
		this.data = data;
		this.code = code;
		this.message = message;
		this.success = false;
	}
 
	private ReturnResult(Object data) {
		this.data = data;
		this.code = ReturnMsg.SUCCESS.getCode();
		this.message = ReturnMsg.SUCCESS.getMsg();
		this.success = true;
	}
 
	private ReturnResult(long total, Object data) {
		//this.total = total;
		this.data = data;
		this.code = ReturnMsg.SUCCESS.getCode();
		this.message = ReturnMsg.SUCCESS.getMsg();
		this.success = true;
	}
 
	private ReturnResult(Integer code, String message) {
		this.code = code;
		this.message = message;
		this.success = false;
	}
	
	private ReturnResult() {
		this.code = ReturnMsg.ERROR.getCode();
		this.message = ReturnMsg.ERROR.getMsg();
		this.success = false;
	}
	
	/***
	 * 输入参数:
	 * 
	 * @param total
	 * @param totalPage
	 * @param data
	 * @param code
	 * @param msg
	 * @return 返回成功，包含total、totalPage、code、msg、data
	 */
	public static ReturnResult success(long total, long totalPage, Object data) {
		return new ReturnResult(total, totalPage, data);
	}
 
	/***
	 * 输入参数:
	 * 
	 * @param total
	 * @param data
	 * @param code
	 * @param msg
	 * @return 返回成功，包含total、code、msg、data
	 */
	public static ReturnResult success(long total, Object data, Integer code, String message) {
		return new ReturnResult(total, data, code, message);
	}
 
	/***
	 * 输入参数
	 * 
	 * @param total
	 * @param data
	 * @return 返回成功，包含total、code、msg、data
	 */
	public static ReturnResult success(long total, Object data) {
		return new ReturnResult(total, data);
	}
 
	/***
	 * 输入参数：
	 * 
	 * @param data
	 * @return 返回成功，包含code、msg、data
	 */
	public static ReturnResult success(Object data) {
		return new ReturnResult(data);
	}
 
	/***
	 * 无输入参数 返回成功，包含code、msg
	 * 
	 * @return
	 */
	public static ReturnResult success() {
		return new ReturnResult("");
	}
 
	/***
	 * 无输入参数 返回失败，包含code、msg
	 * 
	 * @return
	 */
	public static ReturnResult error() {
		return new ReturnResult();
	}
 
	public static ReturnResult error(Integer code, String message) {
		return new ReturnResult(code, message);
	}
	
	public static ReturnResult error(Integer code, String message, Object data) {
		return new ReturnResult(code, message, data);
	}
 
//	public long getTotal() {
//		return total;
//	}
// 
//	public void setTotal(long total) {
//		this.total = total;
//	}
 
	public Object getData() {
		return data;
	}
 
	public void setData(Object data) {
		this.data = data;
	}
	
//	public long getTotalPage() {
//		return totalPage;
//	}
//
//	public void setTotalPage(long totalPage) {
//		this.totalPage = totalPage;
//	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Integer getCode() {
		return code;
	}

	public void setCode(Integer code) {
		this.code = code;
	}
	
}
