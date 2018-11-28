package cn.edugate.esb.redis.dao.imp;

import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.stereotype.Repository;
import cn.edugate.esb.pojo.Login;
import cn.edugate.esb.redis.RedisGeneratorDao;
import cn.edugate.esb.redis.dao.RedisLoginDao;

@Repository
public class RedisLoginDaoImp extends RedisGeneratorDao<String, String> implements RedisLoginDao {

	@Override
	public boolean add(final Login login) {
		// TODO Auto-generated method stub
		boolean result = redisTemplate.execute(new RedisCallback<Boolean>() {  
            public Boolean doInRedis(RedisConnection connection)  
                    throws DataAccessException {  
                RedisSerializer<String> serializer = redisTemplate.getStringSerializer();                
            	byte[] value = serializer.serialize(login.getExtratime().toString()); 
        		byte[] key  = serializer.serialize("login:"+login.getType()+":"+login.getUser_id());  
            	connection.set(key, value);           	
                return true;  
            }
        }, false, true);  
        return result;  
	}

	@Override
	public Long getExtratime(final String type, final String user_id) {
		// TODO Auto-generated method stub
		Long result = redisTemplate.execute(new RedisCallback<Long>() {  
            public Long doInRedis(RedisConnection connection)  
                    throws DataAccessException {  
                RedisSerializer<String> serializer = redisTemplate.getStringSerializer();
                byte[] key = serializer.serialize("login:"+type+":"+user_id);  
                Long exttime=0L;
				try {
					byte[] value = connection.get(key); 
					if(value!=null){
						String evalue = serializer.deserialize(value); 
						exttime = Long.parseLong(evalue);
					}
				} catch (Exception e) {
					// TODO Auto-generated catch block
					return 0L;
				}                
                return exttime;
            }  
        });  
        return result;
	}
	
	

}
