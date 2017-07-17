package com.gm.dao.redis.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import com.gm.dao.redis.RedisDao;

@Repository
public class RedisDAoImpl implements RedisDao {
	
	@Autowired
    protected RedisTemplate<?, ?> redisTemplate;

	@Override
    public void saveData(final String key, final String data) {
        redisTemplate.execute(new RedisCallback<Object>() {

            @Override
            public Object doInRedis(RedisConnection connection) throws DataAccessException {
            	//connection.flushAll();
                connection.set(redisTemplate.getStringSerializer().serialize(key),
                               redisTemplate.getStringSerializer().serialize(data));
                return null;
            }
        });
    }

    @Override
    public String getData(final String key1) {
        return redisTemplate.execute(new RedisCallback<String>() {
            @Override
            public String doInRedis(RedisConnection connection) throws DataAccessException {
                byte[] key = redisTemplate.getStringSerializer().serialize(key1);
                if (connection.exists(key)) {
                    byte[] value = connection.get(key);
                    String name = (String) redisTemplate.getStringSerializer().deserialize(value);
                    return name;
                }
                return null;
            }
        });
    }
}
