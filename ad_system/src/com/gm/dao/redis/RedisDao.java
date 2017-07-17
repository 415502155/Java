package com.gm.dao.redis;

public interface RedisDao {

	void saveData(String key, String data);

	String getData(String key);

}
