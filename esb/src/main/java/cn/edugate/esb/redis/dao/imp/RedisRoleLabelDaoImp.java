package cn.edugate.esb.redis.dao.imp;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.stereotype.Repository;

import cn.edugate.esb.entity.RoleLabel;
import cn.edugate.esb.entity.TeacherRole;
import cn.edugate.esb.entity.TechRange;
import cn.edugate.esb.entity.UcuserOrguser;
import cn.edugate.esb.redis.RedisGeneratorDao;
import cn.edugate.esb.redis.dao.RedisRoleLabelDao;
import cn.edugate.esb.redis.dao.RedisTechRangeDao;
import cn.edugate.esb.util.Util;
import cn.edugate.esb.util.Utils;

@Repository
public class RedisRoleLabelDaoImp extends RedisGeneratorDao<String, String> implements RedisRoleLabelDao {
	
	private Util util;
	@Autowired
	public void setUtil(Util util) {
		this.util = util;
	}

	@Override
	public boolean add(final RoleLabel tr) {
		// TODO Auto-generated method stub
		boolean result = redisTemplate.execute(new RedisCallback<Boolean>() {  
            public Boolean doInRedis(RedisConnection connection)  
                    throws DataAccessException {  
                RedisSerializer<String> serializer = redisTemplate.getStringSerializer();              	
                if(!tr.getIs_del()){
	            	String json="";
	            	json = util.getJSONFromPOJO(tr);
	            	byte[] value = serializer.serialize(json); 
	        		byte[] key  = serializer.serialize("rolelabel:"+tr.getRl_id());  
	        		connection.set(key, value);  
                }        
                return true;  
            }
        }, false, true);  
        return result;  
	}
	
	@Override
	public boolean add(final List<RoleLabel> trs) {
		// TODO Auto-generated method stub
		boolean result = redisTemplate.execute(new RedisCallback<Boolean>() {  
            public Boolean doInRedis(RedisConnection connection)  
                    throws DataAccessException {  
                RedisSerializer<String> serializer = redisTemplate.getStringSerializer();              	
                for (RoleLabel tr : trs) { 
                	//if(!tr.getIs_del()){
                		String json="";
    	            	json = util.getJSONFromPOJO(tr);
    	            	byte[] value = serializer.serialize(json); 
    	        		byte[] key  = serializer.serialize("rolelabel:"+tr.getRl_id());  
    	        		connection.set(key, value); 
                	//}
                }                  
                return true;  
            }
        }, false, true);  
        return result;  
	}	

	@Override
	public boolean delete(final RoleLabel tr) {
		// TODO Auto-generated method stub
		boolean result = redisTemplate.execute(new RedisCallback<Boolean>() {  
            public Boolean doInRedis(RedisConnection connection)  
                    throws DataAccessException {  
                RedisSerializer<String> serializer = redisTemplate.getStringSerializer();
        		byte[] key  = serializer.serialize("rolelabel:"+tr.getRl_id());  
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
                byte[] key = serializer.serialize("rolelabel:"+"*");
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
	public RoleLabel getById(final String rl_id) {
		// TODO Auto-generated method stub
		RoleLabel result = redisTemplate.execute(new RedisCallback<RoleLabel>() {  
            public RoleLabel doInRedis(RedisConnection connection)  
                    throws DataAccessException {  
                RedisSerializer<String> serializer = redisTemplate.getStringSerializer();
                byte[] key = serializer.serialize("rolelabel:"+rl_id);  
                RoleLabel user = null;
				try {
					byte[] value = connection.get(key); 
	                String evalue = serializer.deserialize(value); 
	                if(StringUtils.isNotEmpty(evalue))
	                	user = util.getPojoFromJSON(evalue, RoleLabel.class);
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
