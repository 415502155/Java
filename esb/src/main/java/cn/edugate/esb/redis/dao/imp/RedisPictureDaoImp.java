package cn.edugate.esb.redis.dao.imp;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.stereotype.Repository;

import cn.edugate.esb.entity.Parent;
import cn.edugate.esb.entity.Picture;
import cn.edugate.esb.redis.RedisGeneratorDao;
import cn.edugate.esb.redis.dao.RedisParentDao;
import cn.edugate.esb.redis.dao.RedisPictureDao;
import cn.edugate.esb.util.Util;

@Repository
public class RedisPictureDaoImp extends RedisGeneratorDao<String, String> implements RedisPictureDao {
	
	private Util util;
	@Autowired
	public void setUtil(Util util) {
		this.util = util;
	}

	@Override
	public boolean add(final Picture picture) {
		// TODO Auto-generated method stub
		boolean result = redisTemplate.execute(new RedisCallback<Boolean>() {  
            public Boolean doInRedis(RedisConnection connection)  
                    throws DataAccessException {  
                RedisSerializer<String> serializer = redisTemplate.getStringSerializer();
            	String json="";
            	json = util.getJSONFromPOJO(picture);
            	byte[] value = serializer.serialize(json); 
        		byte[] key  = serializer.serialize("picture:"+picture.getId());  
            	connection.set(key, value);
                return true;  
            }
        }, false, true);  
        return result;  
	}
	
	@Override
	public boolean add(final List<Picture> pictures) {
		// TODO Auto-generated method stub
		boolean result = redisTemplate.execute(new RedisCallback<Boolean>() {  
            public Boolean doInRedis(RedisConnection connection)  
                    throws DataAccessException {  
                RedisSerializer<String> serializer = redisTemplate.getStringSerializer();              	
                for (Picture picture : pictures) { 
                	String json="";
                	json = util.getJSONFromPOJO(picture);
                	byte[] value = serializer.serialize(json); 
            		byte[] key  = serializer.serialize("picture:"+picture.getId());
                	connection.set(key, value);
                }                  
                return true;  
            }
        }, false, true);  
        return result;  
	}	

	@Override
	public boolean delete(final Picture picture) {
		// TODO Auto-generated method stub
		boolean result = redisTemplate.execute(new RedisCallback<Boolean>() {  
            public Boolean doInRedis(RedisConnection connection)  
                    throws DataAccessException {  
                RedisSerializer<String> serializer = redisTemplate.getStringSerializer();              	
                 
        		byte[] key  = serializer.serialize("picture:"+picture.getId());  
            	connection.del(key);            	
                                 
                return true;  
            }
        }, false, true);  
        return result;
	}

	@Override
	public boolean deleteAll() {
		// TODO Auto-generated method stub
		boolean result = redisTemplate.execute(new RedisCallback<Boolean>() {  
            public Boolean doInRedis(RedisConnection connection)  
                    throws DataAccessException {  
                RedisSerializer<String> serializer = redisTemplate.getStringSerializer();
				try {
					deleteRedis(connection, serializer, "picture:"+"*");
				} catch (Exception e) {
					// TODO Auto-generated catch block
					return false;
				}                
                return true;
            }  
        });  
        return result;   
	}

	@Override
	public Picture getPictureById(Integer id, Object object) {
		// TODO Auto-generated method stub
		return null;
	}	
}
