package cn.edugate.esb.redis.dao.imp;

import java.util.HashMap;
import java.util.LinkedHashMap;
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

import cn.edugate.esb.entity.Role;
import cn.edugate.esb.redis.RedisGeneratorDao;
import cn.edugate.esb.redis.dao.RedisRoleDao;
import cn.edugate.esb.util.Util;
import cn.edugate.esb.util.Utils;

@Repository
public class RedisRoleDaoImp extends RedisGeneratorDao<String, String> implements RedisRoleDao {
	
	private Util util;
	@Autowired
	public void setUtil(Util util) {
		this.util = util;
	}

	@Override
	public boolean add(final Role tr) {
		// TODO Auto-generated method stub
		boolean result = redisTemplate.execute(new RedisCallback<Boolean>() {  
            public Boolean doInRedis(RedisConnection connection)  
                    throws DataAccessException {  
                RedisSerializer<String> serializer = redisTemplate.getStringSerializer();
                if(!tr.getIs_del()){
	            	String json="";
	            	json = util.getJSONFromPOJO(tr);
	            	byte[] value = serializer.serialize(json); 
	        		byte[] key  = serializer.serialize("role:"+tr.getRole_id());  
	            	connection.set(key, value); 
	            	
	            	byte[] key2  = serializer.serialize("orgrole:"+tr.getOrg_id().toString());
	            	byte[] field  = serializer.serialize(tr.getRole_id().toString());
	            	connection.hSet(key2, field, value);
                }
            	
                return true;  
            }
        }, false, true);  
        return result;  
	}
	
	@Override
	public boolean add(final List<Role> trs) {
		// TODO Auto-generated method stub
		boolean result = redisTemplate.execute(new RedisCallback<Boolean>() {  
            public Boolean doInRedis(RedisConnection connection)  
                    throws DataAccessException {  
                RedisSerializer<String> serializer = redisTemplate.getStringSerializer();              	
                for (Role tr : trs) { 
                	//if(!tr.getIs_del()){
	                	String json="";
	                	json = util.getJSONFromPOJO(tr);
	                	byte[] value = serializer.serialize(json); 
	            		byte[] key  = serializer.serialize("role:"+tr.getRole_id());
	                	connection.set(key, value);                 	
	                	
	                	byte[] key2  = serializer.serialize("orgrole:"+tr.getOrg_id().toString());
	                	byte[] field  = serializer.serialize(tr.getRole_id().toString());
	                	connection.hSet(key2, field, value);
                	//}
                	
                }                  
                return true;  
            }
        }, false, true);  
        return result;  
	}	

	@Override
	public boolean delete(final Role tr) {
		// TODO Auto-generated method stub
		boolean result = redisTemplate.execute(new RedisCallback<Boolean>() {  
            public Boolean doInRedis(RedisConnection connection)  
                    throws DataAccessException {  
                RedisSerializer<String> serializer = redisTemplate.getStringSerializer();                
        		byte[] key  = serializer.serialize("role:"+tr.getRole_id());  
        		connection.del(key);
        		
        		byte[] key2  = serializer.serialize("orgrole:"+tr.getOrg_id().toString()); 
        		byte[] field  = serializer.serialize(tr.getRole_id().toString());
        		connection.hDel(key2, field);
        		
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
                
                deleteRedis(connection, serializer, "role:"+"*");
                deleteRedis(connection, serializer, "orgrole:"+"*");
          
                return true;
            }  
        });  
        return result;   
	}

	@Override
	public Role getByRoleId(final String roleid) {
		// TODO Auto-generated method stub
		Role result = redisTemplate.execute(new RedisCallback<Role>() {  
            public Role doInRedis(RedisConnection connection)  
                    throws DataAccessException {  
                RedisSerializer<String> serializer = redisTemplate.getStringSerializer();
                byte[] key = serializer.serialize("role:"+roleid);  
                Role user=null;
				try {
					byte[] value = connection.get(key); 
					if(value!=null){
		                String evalue = serializer.deserialize(value); 
		                if(StringUtils.isNotEmpty(evalue))
		                	user = util.getPojoFromJSON(evalue, Role.class);
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
	public Map<String, Role> getByOrgId(final Integer org_id) {
		// TODO Auto-generated method stub
		Map<String,Role> result = redisTemplate.execute(new RedisCallback<Map<String,Role>>() {  
            public Map<String,Role> doInRedis(RedisConnection connection)  
                    throws DataAccessException {  
                RedisSerializer<String> serializer = redisTemplate.getStringSerializer();
                byte[] key = serializer.serialize("orgrole:"+org_id);  
                Map<String,Role> teachers = new HashMap<String,Role>();
                Map<String, Role> tempMap = new LinkedHashMap<String, Role>();
				try {
					Map<byte[], byte[]> values = connection.hGetAll(key);
					for (Entry<byte[], byte[]> teacher : values.entrySet()) {
						String ekey = serializer.deserialize(teacher.getKey());
						String evaluestr = serializer.deserialize(teacher.getValue());
						if(StringUtils.isNotEmpty(evaluestr)){
							Role evalue = util.getPojoFromJSON(evaluestr, Role.class);
							teachers.put(ekey, evalue);
						}
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


}
