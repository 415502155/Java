package cn.edugate.esb.web.test;

/**
 * <p>Title: ReturnResult.java<／p>
 * <p>Description: 返回封裝結果集<／p>
 * @author shy
 * @Version 1.0
 * @Date 2018年7月3日上午10:36:40
 */
public class ReturnResult {
 
	private long total;
	private long totalPage;
	private Object data;
	private String code;
	private String msg;
 
	private ReturnResult(long total, long totalPage, Object data, String code, String msg) {
		this.total = total;
		this.totalPage = totalPage;
		this.data = data;
		this.code = code;
		this.msg = msg;
	}
	
	private ReturnResult(long total, long totalPage, Object data) {
		this.total = total;
		this.totalPage = totalPage;
		this.data = data;
		this.code = ReturnMsg.SUCCESS.getCode();
		this.msg = ReturnMsg.SUCCESS.getMsg();
	}
	
	private ReturnResult(long total, Object data, String code, String msg) {
		this.total = total;
		this.data = data;
		this.code = code;
		this.msg = msg;
	}
 
	private ReturnResult(Object data) {
		this.data = data;
		this.code = ReturnMsg.SUCCESS.getCode();
		this.msg = ReturnMsg.SUCCESS.getMsg();
	}
 
	private ReturnResult(long total, Object data) {
		this.total = total;
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
	public static ReturnResult success(long total, Object data, String code, String msg) {
		return new ReturnResult(total, data, code, msg);
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
 
	public static ReturnResult error(String code, String msg) {
		return new ReturnResult(code, msg);
	}
 
	public long getTotal() {
		return total;
	}
 
	public void setTotal(long total) {
		this.total = total;
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
 
	public String getMsg() {
		return msg;
	}
 
	public void setMsg(String msg) {
		this.msg = msg;
	}
	
	public long getTotalPage() {
		return totalPage;
	}

	public void setTotalPage(long totalPage) {
		this.totalPage = totalPage;
	}
}
