package com.gm.service;

import net.sf.json.JSONObject;


public interface OpenService {
	
	/**
	 * 获取数据入口
	 * @param jsonObject
	 * @return
	 * @throws Exception
	 */
	public String controlService(JSONObject jsonObject) throws Exception;
	
	/**
	 * 数据入口接口分配
	 */
	public String controlInsertService(JSONObject jsonObject) throws Exception;
	
	/**
	 * 获取错误信息
	 * @return
	 */
	public String getErrMsg(int errno,String ...errmsg);
}
