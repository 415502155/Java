package com.shy.springboot.service;

import com.alibaba.fastjson.JSONObject;
import com.shy.springboot.utils.ReturnResult;

public interface ICommonService {
	public ReturnResult execute(JSONObject json) throws Exception;
}
