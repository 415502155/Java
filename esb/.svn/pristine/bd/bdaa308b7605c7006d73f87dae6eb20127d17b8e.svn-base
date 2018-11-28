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
import cn.edugate.esb.redis.RedisGeneratorDao;
import cn.edugate.esb.redis.dao.RedisParentDao;
import cn.edugate.esb.util.Util;

@Repository
public class RedisParentDaoImp extends RedisGeneratorDao<String, String> implements RedisParentDao {
	
	private Util util;
	@Autowired
	public void setUtil(Util util) {
		this.util = util;
	}

	@Override
	public boolean add(final Parent parent) {
		// TODO Auto-generated method stub
		boolean result = redisTemplate.execute(new RedisCallback<Boolean>() {  
            public Boolean doInRedis(RedisConnection connection)  
                    throws DataAccessException {  
                RedisSerializer<String> serializer = redisTemplate.getStringSerializer();              	
                if(!parent.getIs_del()){
	            	String json="";
	            	json = util.getJSONFromPOJO(parent);
	            	byte[] value = serializer.serialize(json); 
	        		byte[] key  = serializer.serialize("parent:"+parent.getParent_id());  
	            	connection.set(key, value);    
	            	
	            	byte[] key2  = serializer.serialize("parentuser:"+parent.getUser_id());  
	            	connection.set(key2, value); 
	            	
	            	//机构下家长
            		byte[] key3  = serializer.serialize("parentOrg:"+parent.getOrg_id());  
            		byte[] parentfield  = serializer.serialize(parent.getParent_id().toString());
            		connection.hSet(key3,parentfield, value);
	            	
	            	
                }      
                return true;  
            }
        }, false, true);  
        return result;  
	}
	
	@Override
	public boolean add(final List<Parent> parents) {
		// TODO Auto-generated method stub
		boolean result = redisTemplate.execute(new RedisCallback<Boolean>() {  
            public Boolean doInRedis(RedisConnection connection)  
                    throws DataAccessException {  
                RedisSerializer<String> serializer = redisTemplate.getStringSerializer();              	
                for (Parent parent : parents) { 
                	//if(!parent.getIs_del()){
    	            	String json="";
    	            	json = util.getJSONFromPOJO(parent);
    	            	byte[] value = serializer.serialize(json); 
    	        		byte[] key  = serializer.serialize("parent:"+parent.getParent_id());     	        		
    	            	connection.set(key, value);    
    	            	 
    	            	byte[] key2  = serializer.serialize("parentuser:"+parent.getUser_id());  
    	            	connection.set(key2, value);
    	            	
    	            	//机构下家长
                		byte[] key3  = serializer.serialize("parentOrg:"+parent.getOrg_id());  
                		byte[] parentfield  = serializer.serialize(parent.getParent_id().toString());
                		connection.hSet(key3,parentfield, value);
    	            	
                    //}  
                }                  
                return true;  
            }
        }, false, true);  
        return result;  
	}	

	@Override
	public boolean delete(final Parent parent) {
		// TODO Auto-generated method stub
		boolean result = redisTemplate.execute(new RedisCallback<Boolean>() {  
            public Boolean doInRedis(RedisConnection connection)  
                    throws DataAccessException {  
                RedisSerializer<String> serializer = redisTemplate.getStringSerializer();              	
                 
        		byte[] key  = serializer.serialize("parent:"+parent.getParent_id());  
            	connection.del(key);
            	
            	byte[] key2  = serializer.serialize("parentuser:"+parent.getUser_id());  
            	connection.del(key2);
            	
            	 byte[] field = serializer.serialize(parent.getParent_id().toString()); 
            	byte[] key3  = serializer.serialize("parentOrg:"+parent.getOrg_id());  
        		connection.hDel(key3,field);
            	
            	
                                 
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
					deleteRedis(connection, serializer, "parent:"+"*");
					deleteRedis(connection, serializer, "parentuser:"+"*");
					deleteRedis(connection, serializer, "parentOrg:"+"*");
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
	public Parent getByParentId(final String parentid) {
		// TODO Auto-generated method stub
		Parent result = redisTemplate.execute(new RedisCallback<Parent>() {  
			public Parent doInRedis(RedisConnection connection) throws DataAccessException {  
				RedisSerializer<String> serializer = redisTemplate.getStringSerializer();
				Parent sp = new Parent();
				try {
					byte[] key = serializer.serialize("parent:"+parentid);
					byte[] value = connection.get(key);
					String evalue = serializer.deserialize(value); 
					if(StringUtils.isNotEmpty(evalue))
						sp = util.getPojoFromJSON(evalue, Parent.class);
				} catch (Exception e) {
					return null;
				}                
				return sp;
			}  
		});  
		return result;
	}
	
	
	
	
	@Override
	public List<Parent> getParentsByOrgId(final Integer org_id) {
		List<Parent> result = redisTemplate.execute(new RedisCallback<List<Parent>>() {  
            public List<Parent> doInRedis(RedisConnection connection) throws DataAccessException {  
                RedisSerializer<String> serializer = redisTemplate.getStringSerializer();
                byte[] key = serializer.serialize("parentOrg:"+org_id);  
                List<Parent> list = new ArrayList<Parent>();
				try {
					Map<byte[], byte[]> values = connection.hGetAll(key);
					for (Entry<byte[], byte[]> org : values.entrySet()) {
						String evaluestr = serializer.deserialize(org.getValue());
						if(StringUtils.isNotEmpty(evaluestr)){
							Parent evalue = util.getPojoFromJSON(evaluestr, Parent.class);
							list.add(evalue);
						}
					}					
				} catch (Exception e) {
					e.printStackTrace();
					return null;
				}   
                return list;
            }  
        });  
        return result;
	}
	
	
	@Override
	public Parent getParentsByUserId(final String user_id) {
		Parent result = redisTemplate.execute(new RedisCallback<Parent>() {  
			public Parent doInRedis(RedisConnection connection) throws DataAccessException {  
				RedisSerializer<String> serializer = redisTemplate.getStringSerializer();
				Parent sp = new Parent();
				try {
					byte[] key = serializer.serialize("parentuser:"+user_id);
					byte[] value = connection.get(key);
					String evalue = serializer.deserialize(value); 
					if(StringUtils.isNotEmpty(evalue))
						sp = util.getPojoFromJSON(evalue, Parent.class);
				} catch (Exception e) {
					return null;
				}                
				return sp;
			}  
		});  
		return result;
	}
}
