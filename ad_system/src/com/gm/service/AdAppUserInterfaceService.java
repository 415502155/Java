package com.gm.service;

import net.sf.json.JSONObject;

public interface AdAppUserInterfaceService {

	/**
	 * 获取用户信息列表
	 */
	public String getUserList(JSONObject jsonObject) throws Exception;
	
	/**
	 * 获取单个用户详细信息
	 */
	public String getUserInfo(JSONObject jsonObject) throws Exception;
	
	/**
	 * 获取前一天用户站点访问排行
	 */
	public String getUserUrlRank(JSONObject jsonObject) throws Exception;
	
	/**
	 * 获取前一天用户搜索关键词排行
	 */
	public String getUserSearchRank(JSONObject jsonObject) throws Exception;
	
}
