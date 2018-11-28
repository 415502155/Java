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

import cn.edugate.esb.entity.UserAccount;
import cn.edugate.esb.redis.RedisGeneratorDao;
import cn.edugate.esb.redis.dao.RedisUserAccountDao;
import cn.edugate.esb.util.Util;

@Repository
public class RedisUserAccountDaoImp extends RedisGeneratorDao<String, String> implements RedisUserAccountDao {
	
	private Util util;
	@Autowired
	public void setUtil(Util util) {
		this.util = util;
	}

	@Override
	public boolean add(final UserAccount user) {
		// TODO Auto-generated method stub
		boolean result = redisTemplate.execute(new RedisCallback<Boolean>() {  
            public Boolean doInRedis(RedisConnection connection)  
                    throws DataAccessException {  
                RedisSerializer<String> serializer = redisTemplate.getStringSerializer();              	
                 
            	String json="";
            	json = util.getJSONFromPOJO(user);
            	byte[] value = serializer.serialize(json); 
        		byte[] key  = serializer.serialize("useraccount:"+user.getType()+":"+user.getVersion()+":"+user.getTarget_id());  
            	connection.set(key, value);                                  
                return true;  
            }
        }, false, true);  
        return result;  
	}
	
	@Override
	public boolean add(final List<UserAccount> users) {
		// TODO Auto-generated method stub
		boolean result = redisTemplate.execute(new RedisCallback<Boolean>() {  
            public Boolean doInRedis(RedisConnection connection)  
                    throws DataAccessException {  
                RedisSerializer<String> serializer = redisTemplate.getStringSerializer();              	
                for (UserAccount user : users) {  
                	String json="";
                	json = util.getJSONFromPOJO(user);
                	byte[] value = serializer.serialize(json); 
            		byte[] key  = serializer.serialize("useraccount:"+user.getType()+":"+user.getVersion()+":"+user.getTarget_id());  
                	connection.set(key, value);                           
                }                  
                return true;  
            }
        }, false, true);  
        return result;  
	}	

	@Override
	public boolean delete(final UserAccount user) {
		// TODO Auto-generated method stub
		boolean result = redisTemplate.execute(new RedisCallback<Boolean>() {  
            public Boolean doInRedis(RedisConnection connection)  
                    throws DataAccessException {  
                RedisSerializer<String> serializer = redisTemplate.getStringSerializer();
        		byte[] key  = serializer.serialize("useraccount:"+user.getType()+":"+user.getVersion()+":"+user.getTarget_id()); 
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
                byte[] key = serializer.serialize("useraccount:"+"*");  
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
	public UserAccount getUserAccount(final String targetid, final String version,
			final String type) {
		// TODO Auto-generated method stub
		UserAccount result = redisTemplate.execute(new RedisCallback<UserAccount>() {  
            public UserAccount doInRedis(RedisConnection connection)  
                    throws DataAccessException {  
                RedisSerializer<String> serializer = redisTemplate.getStringSerializer();
                byte[] key = serializer.serialize("useraccount:"+type+":"+version+":"+targetid);  
                UserAccount user=null;
				try {
					byte[] value = connection.get(key); 
	                String evalue = serializer.deserialize(value); 
	                if(StringUtils.isNotEmpty(evalue))
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
