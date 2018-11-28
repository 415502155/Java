package cn.edugate.esb.redis;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.stereotype.Repository;

import cn.edugate.esb.entity.OrgUser;
import cn.edugate.esb.entity.UcUser;
import cn.edugate.esb.util.Util;

@Repository
public class RedisDaoImp extends RedisGeneratorDao<String, String> implements RedisDao {
	
	private Util util;
	@Autowired
	public void setUtil(Util util) {
		this.util = util;
	}

	@Override
	public UcUser getUserByLoginName(final String login_name, final Integer org_type) {
		// TODO Auto-generated method stub
		UcUser result = redisTemplate.execute(new RedisCallback<UcUser>() {  
            public UcUser doInRedis(RedisConnection connection)  
                    throws DataAccessException {  
                RedisSerializer<String> serializer = redisTemplate.getStringSerializer();
                byte[] key = serializer.serialize("loginuser:"+login_name+":"+org_type);  
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
	public boolean addOrgUsers(final List<OrgUser> users) {
		// TODO Auto-generated method stub
		boolean result = redisTemplate.execute(new RedisCallback<Boolean>() {  
            public Boolean doInRedis(RedisConnection connection)  
                    throws DataAccessException {  
                RedisSerializer<String> serializer = redisTemplate.getStringSerializer();              	
                for (OrgUser user : users) {  
                	String json="";
                	json = util.getJSONFromPOJO(user);
                	byte[] value = serializer.serialize(json); 
            		byte[] key  = serializer.serialize("orguser:"+user.getUser_id());  
            		byte[] key2  = serializer.serialize("orgloginuser:"+user.getUser_loginname()+":"+user.getOrg_id()); 
                	connection.set(key, value);    
                	connection.set(key2, value); 
                }                  
                return true;  
            }
        }, false, true);  
        return result; 
	}
	
	

}
