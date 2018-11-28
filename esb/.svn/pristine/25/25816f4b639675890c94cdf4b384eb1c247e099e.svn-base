package cn.edugate.esb.redis.dao.imp;

import java.util.List;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.stereotype.Repository;
import cn.edugate.esb.entity.WxConfig;
import cn.edugate.esb.redis.RedisGeneratorDao;
import cn.edugate.esb.redis.dao.RedisWxConfigDao;
import cn.edugate.esb.util.Util;
@Repository
public class RedisWxConfigDaoImpl  extends RedisGeneratorDao<String, String> implements RedisWxConfigDao{
	private static Logger logger = Logger.getLogger(RedisWxConfigDaoImpl.class);
	@Autowired
	private Util util;
	
	@Override
	public boolean add(final WxConfig tr) {
		boolean result = redisTemplate.execute(new RedisCallback<Boolean>() {  
            public Boolean doInRedis(RedisConnection connection) throws DataAccessException { 
        		RedisSerializer<String> serializer = redisTemplate.getStringSerializer();
            	String json="";
            	json = util.getJSONFromPOJO(tr);
            	byte[] value = serializer.serialize(json); 
        		byte[] keyt  = serializer.serialize("weixin:"+tr.getOrg_id());                	  
            	connection.set(keyt, value);
            	byte[] keyall  = serializer.serialize("weixinall");  
            	byte[] fieldall  = serializer.serialize(tr.getOrg_id().toString());	        		
        		connection.hSet(keyall,fieldall, value);
            	return true; 
            }
        }, false, true);  
        return result; 
	}

	@Override
	public boolean add(final List<WxConfig> trs) {
		boolean result = redisTemplate.execute(new RedisCallback<Boolean>() {  
            public Boolean doInRedis(RedisConnection connection) throws DataAccessException {  
                RedisSerializer<String> serializer = redisTemplate.getStringSerializer();
            	 for (WxConfig campus : trs) { 
					String json = "";
					json = util.getJSONFromPOJO(campus);
					byte[] value = serializer.serialize(json);
					byte[] keyt = serializer.serialize("weixin:" + campus.getOrg_id());
					connection.set(keyt, value);
					
					byte[] keyall  = serializer.serialize("weixinall");  
//					connection.lPush(keyall, value);
	        		byte[] fieldall  = serializer.serialize(campus.getOrg_id().toString());	        		
	        		connection.hSet(keyall,fieldall, value);					
            	 }
                return true;  
            }
        }, false, true);  
        return result;
	}

	@Override
	public boolean delete(final WxConfig tr) {
		boolean result = redisTemplate.execute(new RedisCallback<Boolean>() {  
            public Boolean doInRedis(RedisConnection connection) throws DataAccessException {  
                RedisSerializer<String> serializer = redisTemplate.getStringSerializer();                
        		byte[] key  = serializer.serialize("weixin:"+tr.getOrg_id());  
        		connection.del(key);
        		
        		byte[] key1  = serializer.serialize("weixinall");  
        		byte[] field1 = serializer.serialize(tr.getOrg_id().toString()); 
        		connection.hDel(key1, field1);
        		
                return true;  
            }
        }, false, true);
        return result;
	}

	@Override
	public boolean deleteAll() {
		boolean result = redisTemplate.execute(new RedisCallback<Boolean>() {  
            public Boolean doInRedis(RedisConnection connection) throws DataAccessException {  
                RedisSerializer<String> serializer = redisTemplate.getStringSerializer();
				try {
					deleteRedis(connection,serializer,"weixin:*");	
					byte[] key  = serializer.serialize("weixinall");
	        		connection.del(key);
				} catch (Exception e) {
					logger.error("=====deleteAllGroups error:"+e.getMessage());
					return false;
				}                
                return true;
            }  
        });  
        return result; 
	}

}
