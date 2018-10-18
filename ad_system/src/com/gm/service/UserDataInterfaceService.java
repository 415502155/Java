package com.gm.service;

import net.sf.json.JSONObject;


public interface UserDataInterfaceService {
	
	/**
	 * 用户打开应用数据入库，并更新用户distinct表
	 */
	public String InsertAndUpdateUserInfo(JSONObject jsonObject) throws Exception;
	
	/**
	 * 用户访问打开站点记录入库
	 */
	public String InsertUserUrl(JSONObject jsonObject) throws Exception;
	
	/**
	 * 用户搜索关键词记录入库
	 */
	public String InsertUserSearch(JSONObject jsonObject) throws Exception;
}
