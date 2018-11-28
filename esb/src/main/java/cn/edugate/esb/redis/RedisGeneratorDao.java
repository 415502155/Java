package cn.edugate.esb.redis;

import java.io.Serializable;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.RedisSerializer;

public abstract class RedisGeneratorDao<K extends Serializable, V extends Serializable> {

	@Autowired
	protected RedisTemplate<K, V> redisTemplate;

	/**
	 * 设置redisTemplate
	 * 
	 * @param redisTemplate
	 *            the redisTemplate to set
	 */
	public void setRedisTemplate(RedisTemplate<K, V> redisTemplate) {
		this.redisTemplate = redisTemplate;
	}

	/**
	 * 获取 RedisSerializer <br>
	 * ------------------------------<br>
	 */
	protected RedisSerializer<String> getRedisSerializer() {
		return redisTemplate.getStringSerializer();
	}
	
	public void deleteRedis(RedisConnection connection,RedisSerializer<String> serializer,String redisKey){
		byte[] key = serializer.serialize(redisKey);  
		Set<byte[]> value = connection.keys(key);					
		byte[][] keys=new byte[value.size()][];
		int i=0;
		for (byte[] bs : value) {
			keys[i] = bs;
			i++;
		}
		if(keys.length>0){
			connection.del(keys);
		}
	}
}