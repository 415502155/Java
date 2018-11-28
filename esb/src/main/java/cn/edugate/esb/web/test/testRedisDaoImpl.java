package cn.edugate.esb.web.test;

import java.io.Serializable;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.stereotype.Repository;

import cn.edugate.esb.entity.UserAccount;
import cn.edugate.esb.redis.RedisGeneratorDao;
import cn.edugate.esb.util.Util;

@Repository
public class testRedisDaoImpl extends RedisGeneratorDao<Serializable, Serializable> implements testRedisDao {

	@Autowired
	private Util util;

	@Override
	public UserAccount getUserAccount(final String targetid, final String version, final String type) {
		// TODO Auto-generated method stub
		UserAccount account = redisTemplate.execute(new RedisCallback<UserAccount>() {
			@Override
			public UserAccount doInRedis(RedisConnection connection) throws DataAccessException {
				// TODO Auto-generated method stub
				UserAccount userAccount = null;
				RedisSerializer<String> redisSerializer = redisTemplate.getStringSerializer();
				try {
					byte[] key = redisSerializer.serialize("useraccount:" + type + ":" + version + ":" + targetid);
					byte[] value = connection.get(key);
					String valueStr = redisSerializer.deserialize(value);
					if (StringUtils.isNotEmpty(valueStr)) {
						userAccount = util.getPojoFromJSON(valueStr, UserAccount.class);
					}
				} catch (Exception e) {
					return null;
				}
				return userAccount;
			}
		});
		return account;
	}

	public UserAccount getUserAccount1(final String targetid, final String version, final String type) {
		// TODO Auto-generated method stub
		UserAccount result = redisTemplate.execute(new RedisCallback<UserAccount>() {
			public UserAccount doInRedis(RedisConnection connection) throws DataAccessException {
				RedisSerializer<String> serializer = redisTemplate.getStringSerializer();
				byte[] key = serializer.serialize("useraccount:" + type + ":" + version + ":" + targetid);
				UserAccount user = null;
				try {
					byte[] value = connection.get(key);
					String evalue = serializer.deserialize(value);
					if (StringUtils.isNotEmpty(evalue))
						user = util.getPojoFromJSON(evalue, UserAccount.class);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					return null;
				}
				return user;
			}
		});
		return result;
	}

}
