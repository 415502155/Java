package com.shihy.springboot.utils;

public class ReturnResult {
	private long totalSize;
	private long totalPage;
	private Object data;
	private String code;
	private String msg;
 
	private ReturnResult(long totalSize, Object data, String code, String msg) {
		this.totalSize = totalSize;
		this.data = data;
		this.code = code;
		this.msg = msg;
	}

	private ReturnResult(long totalSize, long totalPage , Object data) {
		this.totalPage = totalPage;
		this.totalSize = totalSize;
		this.data = data;
		this.code = ReturnMsg.SUCCESS.getCode();
		this.msg = ReturnMsg.SUCCESS.getMsg();
	}
 
	
	private ReturnResult(Object data) {
		this.data = data;
		this.code = ReturnMsg.SUCCESS.getCode();
		this.msg = ReturnMsg.SUCCESS.getMsg();
	}
 
	private ReturnResult(long totalSize, Object data) {
		this.totalSize = totalSize;
		this.data = data;
		this.code = ReturnMsg.SUCCESS.getCode();
		this.msg = ReturnMsg.SUCCESS.getMsg();
	}
 
	private ReturnResult(String code, String msg) {
		this.code = code;
		this.msg = msg;
	}
 
	private ReturnResult() {
		this.code = ReturnMsg.ERROR.getCode();
		this.msg = ReturnMsg.ERROR.getMsg();
	}
 
	/***
	 * 输入参数:
	 * 
	 * @param totalSize
	 * @param data
	 * @param code
	 * @param msg
	 * @return 返回成功，包含totalSize、code、msg、data
	 */
	public static ReturnResult success(long totalSize, Object data, String code, String msg) {
		return new ReturnResult(totalSize, data, code, msg);
	}
 
	/***
	 * 输入参数
	 * 
	 * @param totalSize
	 * @param data
	 * @return 返回成功，包含totalSize、code、msg、data
	 */
	public static ReturnResult success(long totalSize, Object data) {
		return new ReturnResult(totalSize, data);
	}
	
	/***
	 * 
	 * @param totalSize 总条数
	 * @param totalPage 总页数
	 * @param data
	 * @param code
	 * @param msg
	 * @return
	 */
	public static ReturnResult success(long totalSize, long totalPage, Object data) {
		return new ReturnResult(totalSize, totalPage, data);
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
 
	public static ReturnResult error(String code, String msg) {
		return new ReturnResult(code, msg);
	}
 
	public Object getData() {
		return data;
	}
 
	public void setData(Object data) {
		this.data = data;
	}
 
	public String getCode() {
		return code;
	}
 
	public void setCode(String code) {
		this.code = code;
	}
 
	public long getTotalSize() {
		return totalSize;
	}

	public void setTotalSize(long totalSize) {
		this.totalSize = totalSize;
	}

	public long getTotalPage() {
		return totalPage;
	}

	public void setTotalPage(long totalPage) {
		this.totalPage = totalPage;
	}

	public String getMsg() {
		return msg;
	}
 
	public void setMsg(String msg) {
		this.msg = msg;
	}
	
	
}
