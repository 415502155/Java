package cn.edugate.esb.redis.dao.imp;

import java.util.List;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.stereotype.Repository;

import cn.edugate.esb.entity.UcUser;
import cn.edugate.esb.redis.RedisGeneratorDao;
import cn.edugate.esb.redis.dao.RedisUcUserDao;
import cn.edugate.esb.util.Util;

@Repository
public class RedisUcUserDaoImp extends RedisGeneratorDao<String, String> implements RedisUcUserDao {
	
	private Util util;
	@Autowired
	public void setUtil(Util util) {
		this.util = util;
	}

	@Override
	public boolean addUsers(final UcUser user) {
		// TODO Auto-generated method stub
		boolean result = redisTemplate.execute(new RedisCallback<Boolean>() {  
            public Boolean doInRedis(RedisConnection connection)  
                    throws DataAccessException {  
                RedisSerializer<String> serializer = redisTemplate.getStringSerializer();              	
                if(!user.getIs_del()){
	            	String json="";
	            	json = util.getJSONFromPOJO(user);
	            	byte[] value = serializer.serialize(json); 
	        		byte[] key  = serializer.serialize("ucuser:"+user.getUc_id());  
	        		byte[] key2  = serializer.serialize("ucloginuser:"+user.getUc_loginname()); 
	            	connection.set(key, value);    
	            	connection.set(key2, value); 
                }      
                return true;  
            }
        }, false, true);  
        return result;  
	}
	
	@Override
	public boolean addUsers(final List<UcUser> users) {
		// TODO Auto-generated method stub
		boolean result = redisTemplate.execute(new RedisCallback<Boolean>() {  
            public Boolean doInRedis(RedisConnection connection)  
                    throws DataAccessException {  
                RedisSerializer<String> serializer = redisTemplate.getStringSerializer();              	
                for (UcUser user : users) { 
                	if(!user.getIs_del()){
	                	String json="";
	                	json = util.getJSONFromPOJO(user);
	                	byte[] value = serializer.serialize(json); 
	            		byte[] key  = serializer.serialize("ucuser:"+user.getUc_id());  
	            		byte[] key2  = serializer.serialize("ucloginuser:"+user.getUc_loginname()); 
	                	connection.set(key, value);    
	                	connection.set(key2, value); 
                	}
                }                  
                return true;  
            }
        }, false, true);  
        return result;  
	}	

	@Override
	public boolean deleteUsers(final UcUser user) {
		// TODO Auto-generated method stub
		boolean result = redisTemplate.execute(new RedisCallback<Boolean>() {  
            public Boolean doInRedis(RedisConnection connection)  
                    throws DataAccessException {  
                RedisSerializer<String> serializer = redisTemplate.getStringSerializer();              	
                 
        		byte[] key  = serializer.serialize("ucuser:"+user.getUc_id());  
        		byte[] key2  = serializer.serialize("ucloginuser:"+user.getUc_loginname()); 
            	connection.del(key);   
            	connection.del(key2); 
                                 
                return true;  
            }
        }, false, true);  
        return result;
	}

	@Override
	public UcUser getUserById(final String id) {
		// TODO Auto-generated method stub
		UcUser result = redisTemplate.execute(new RedisCallback<UcUser>() {  
            public UcUser doInRedis(RedisConnection connection)  
                    throws DataAccessException {  
                RedisSerializer<String> serializer = redisTemplate.getStringSerializer();
                byte[] key = serializer.serialize("ucuser:"+id);  
                UcUser user = null;
				try {
					byte[] value = connection.get(key); 
	                String evalue = serializer.deserialize(value);
	                if (StringUtils.isNotBlank(evalue)) {
	                	user = util.getPojoFromJSON(evalue, UcUser.class);
	                }
				} catch (Exception e) {
					// TODO Auto-generated catch block
					return null;
				}                
                return user;
            }  
        });  
        return result;
	}

	@Override
	public UcUser getUserByLoginName(final String login_name) {
		// TODO Auto-generated method stub
		UcUser result = redisTemplate.execute(new RedisCallback<UcUser>() {  
            public UcUser doInRedis(RedisConnection connection)  
                    throws DataAccessException {  
                RedisSerializer<String> serializer = redisTemplate.getStringSerializer();
                byte[] key = serializer.serialize("ucloginuser:"+login_name);  
                UcUser user;
				try {
					byte[] value = connection.get(key); 
	                String evalue = serializer.deserialize(value); 
					user = util.getPojoFromJSON(evalue, UcUser.class);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					return null;
				}                
                return user;
            }  
        });  
        return result; 
	}

	@Override
	public boolean deleteAll() {
		// TODO Auto-generated method stub
		boolean result = redisTemplate.execute(new RedisCallback<Boolean>() {  
            public Boolean doInRedis(RedisConnection connection)  
                    throws DataAccessException {  
                RedisSerializer<String> serializer = redisTemplate.getStringSerializer();
                byte[] key = serializer.serialize("ucuser:"+"*");  
                byte[] key_1 = serializer.serialize("ucloginuser:"+"*"); 
				try {
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
					
					Set<byte[]> value_1 = connection.keys(key_1);					
					byte[][] keys_1=new byte[value_1.size()][];
					int j=0;
					for (byte[] bs : value_1) {
						keys_1[j] = bs;
						j++;
					}
					if(keys_1.length>0){
						connection.del(keys_1);
					}
				} catch (Exception e) {
					// TODO Auto-generated catch block
					return false;
				}                
                return true;
            }  
        });  
        return result;   
	}
	
	

}
