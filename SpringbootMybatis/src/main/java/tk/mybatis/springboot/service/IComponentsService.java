package tk.mybatis.springboot.service;

import com.alibaba.fastjson.JSONObject;

public interface IComponentsService {
	public JSONObject execute(JSONObject json, String ip);
}
