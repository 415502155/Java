package com.shihy.springboot.exception;

import java.io.Serializable;
import lombok.Data;
@Data
public class ApiResult implements Serializable {
	
	/**
	 * @Fields serialVersionUID : 
	 */
	private static final long serialVersionUID = -1450204351804859591L;

	private ApiResult() {};
	
	public static ApiResult newInstance() {
		return new ApiResult();
	}
	
	private String msg;
	
	private boolean flag;
	
	private Object object;
}
