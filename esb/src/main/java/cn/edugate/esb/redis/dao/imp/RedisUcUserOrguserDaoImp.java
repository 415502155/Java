package cn.edugate.esb.redis.dao.imp;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.stereotype.Repository;
import cn.edugate.esb.entity.UcuserOrguser;
import cn.edugate.esb.redis.RedisGeneratorDao;
import cn.edugate.esb.redis.dao.RedisUcUserOrguserDao;
import cn.edugate.esb.util.Util;
import cn.edugate.esb.util.Utils;

@Repository
public class RedisUcUserOrguserDaoImp extends RedisGeneratorDao<String, String> implements RedisUcUserOrguserDao {
	
	private Util util;
	@Autowired
	public void setUtil(Util util) {
		this.util = util;
	}

	@Override
	public boolean add(final UcuserOrguser user) {
		// TODO Auto-generated method stub
		boolean result = redisTemplate.execute(new RedisCallback<Boolean>() {  
            public Boolean doInRedis(RedisConnection connection)  
                    throws DataAccessException {  
                RedisSerializer<String> serializer = redisTemplate.getStringSerializer();
                if(!user.getIs_del()){
	            	String json="";
	            	json = util.getJSONFromPOJO(user);
	            	byte[] value = serializer.serialize(json); 
	        		byte[] key  = serializer.serialize("uc2org:"+user.getUc_id());  
	        		byte[] field1  = serializer.serialize(user.getUser_id().toString()); 
	        		connection.hSet(key,field1, value);         		
	        		byte[] key2  = serializer.serialize("org2uc:"+user.getUser_id());                	  
	            	connection.set(key2, value);    
                }
                return true;  
            }
        }, false, true);  
        return result;  
	}
	
	@Override
	public boolean adds(final List<UcuserOrguser> users) {
		// TODO Auto-generated method stub
		boolean result = redisTemplate.execute(new RedisCallback<Boolean>() {  
            public Boolean doInRedis(RedisConnection connection)  
                    throws DataAccessException {  
                RedisSerializer<String> serializer = redisTemplate.getStringSerializer();              	
                for (UcuserOrguser user : users) {  
                	if(!user.getIs_del()){
	                	String json="";
	                	json = util.getJSONFromPOJO(user);
	                	byte[] value = serializer.serialize(json); 
	            		byte[] key  = serializer.serialize("uc2org:"+user.getUc_id());  
	            		byte[] field1  = serializer.serialize(user.getUser_id().toString()); 
	            		connection.hSet(key,field1, value);
	            		byte[] key2  = serializer.serialize("org2uc:"+user.getUser_id());                	  
	                	connection.set(key2, value); 
                	}
                }                  
                return true;  
            }
        }, false, true);  
        return result;  
	}	

	@Override
	public boolean delete(final UcuserOrguser user) {
		// TODO Auto-generated method stub
		boolean result = redisTemplate.execute(new RedisCallback<Boolean>() {  
            public Boolean doInRedis(RedisConnection connection)  
                    throws DataAccessException {  
                RedisSerializer<String> serializer = redisTemplate.getStringSerializer();
        		byte[] key  = serializer.serialize("uc2org:"+user.getUc_id());  
        		byte[] field1 = serializer.serialize(user.getUser_id().toString()); 
        		connection.hDel(key, field1);
        		byte[] key2  = serializer.serialize("org2uc:"+user.getUser_id());             	  
            	connection.del(key2);
                return true;  
            }
        }, false, true);  
        return result;
	}

	@Override
	public UcuserOrguser getByUserId(final String user_id) {
		// TODO Auto-generated method stub
		UcuserOrguser result = redisTemplate.execute(new RedisCallback<UcuserOrguser>() {  
            public UcuserOrguser doInRedis(RedisConnection connection)  
                    throws DataAccessException {  
                RedisSerializer<String> serializer = redisTemplate.getStringSerializer();
                byte[] key = serializer.serialize("org2uc:"+user_id);  
                UcuserOrguser user=null;
				try {
					byte[] value = connection.get(key); 
	                String evalue = serializer.deserialize(value); 
	                if(evalue!=null){
	                	user = util.getPojoFromJSON(evalue, UcuserOrguser.class);
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
	public Map<String,UcuserOrguser> getByUcId(final String uc_id) {
		// TODO Auto-generated method stub
		Map<String,UcuserOrguser> result = redisTemplate.execute(new RedisCallback<Map<String,UcuserOrguser>>() {  
            public Map<String,UcuserOrguser> doInRedis(RedisConnection connection)  
                    throws DataAccessException {  
                RedisSerializer<String> serializer = redisTemplate.getStringSerializer();
                byte[] key = serializer.serialize("uc2org:"+uc_id);  
                Map<String,UcuserOrguser> teachers = new HashMap<String,UcuserOrguser>();
                Map<String, UcuserOrguser> tempMap = new LinkedHashMap<String, UcuserOrguser>();
				try {
					Map<byte[], byte[]> values = connection.hGetAll(key);
					for (Entry<byte[], byte[]> teacher : values.entrySet()) {
						String ekey = serializer.deserialize(teacher.getKey());
						String evaluestr = serializer.deserialize(teacher.getValue());
						UcuserOrguser evalue = util.getPojoFromJSON(evaluestr, UcuserOrguser.class);
						teachers.put(ekey, evalue);
					}					
				} catch (Exception e) {
					// TODO Auto-generated catch block
					return null;
				}   
				tempMap = Utils.sortMapByKey(teachers);
                return tempMap;
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
                byte[] key = serializer.serialize("uc2org:"+"*");  
                byte[] key_1 = serializer.serialize("org2uc:"+"*"); 
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
