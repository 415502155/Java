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

import cn.edugate.esb.entity.TeacherRole;
import cn.edugate.esb.redis.RedisGeneratorDao;
import cn.edugate.esb.redis.dao.RedisTeacherRoleDao;
import cn.edugate.esb.util.Constant;
import cn.edugate.esb.util.Util;
import cn.edugate.esb.util.Utils;

@Repository
public class RedisTeacherRoleDaoImp extends RedisGeneratorDao<String, String> implements RedisTeacherRoleDao {
	
	private Util util;
	@Autowired
	public void setUtil(Util util) {
		this.util = util;
	}

	@Override
	public boolean add(final TeacherRole tr) {
		// TODO Auto-generated method stub
		boolean result = redisTemplate.execute(new RedisCallback<Boolean>() {  
            public Boolean doInRedis(RedisConnection connection)  
                    throws DataAccessException {  
            	 if(!tr.getIs_del()){
	                RedisSerializer<String> serializer = redisTemplate.getStringSerializer();
	            	String json="";
	            	json = util.getJSONFromPOJO(tr);
	            	byte[] value = serializer.serialize(json); 
	        		byte[] key1  = serializer.serialize("t2role:"+tr.getTech_id());  
	        		byte[] field1  = serializer.serialize(tr.getRole_id().toString()); 
	        		connection.hSet(key1,field1, value);  
	        		byte[] key2  = serializer.serialize("role2t:"+tr.getRole_id());  
	        		byte[] field2  = serializer.serialize(tr.getTech_id().toString()); 
	        		connection.hSet(key2,field2, value);        		
	        		byte[] key3  = serializer.serialize("teacher2role:"+tr.getTech2role_id()); 
	            	connection.set(key3, value); 
            	 }
                return true;  
            }
        }, false, true);  
        return result;  
	}
	
	@Override
	public boolean add(final List<TeacherRole> trs) {
		// TODO Auto-generated method stub
		boolean result = redisTemplate.execute(new RedisCallback<Boolean>() {  
            public Boolean doInRedis(RedisConnection connection)  
                    throws DataAccessException {  
                RedisSerializer<String> serializer = redisTemplate.getStringSerializer();              	
                for (TeacherRole tr : trs) {  
                	//if(!tr.getIs_del()){
	                	String json="";
	                	json = util.getJSONFromPOJO(tr);
	                	byte[] value = serializer.serialize(json); 
	            		byte[] key1  = serializer.serialize("t2role:"+tr.getTech_id());  
	            		byte[] field1  = serializer.serialize(tr.getRole_id().toString()); 
	            		connection.hSet(key1,field1, value);  
	            		byte[] key2  = serializer.serialize("role2t:"+tr.getRole_id());  
	            		byte[] field2  = serializer.serialize(tr.getTech_id().toString()); 
	            		connection.hSet(key2,field2, value); 
	            		byte[] key3  = serializer.serialize("teacher2role:"+tr.getTech2role_id()); 
	                	connection.set(key3, value); 
                	//}
                }                  
                return true;  
            }
        }, false, true);  
        return result;  
	}	

	@Override
	public boolean delete(final TeacherRole tr) {
		// TODO Auto-generated method stub
		boolean result = redisTemplate.execute(new RedisCallback<Boolean>() {  
            public Boolean doInRedis(RedisConnection connection)  
                    throws DataAccessException {  
                RedisSerializer<String> serializer = redisTemplate.getStringSerializer();
                byte[] key1  = serializer.serialize("t2role:"+tr.getTech_id());  
                byte[] field1  = serializer.serialize(tr.getRole_id().toString()); 
        		connection.hDel(key1, field1);
        		byte[] key2  = serializer.serialize("role2t:"+tr.getRole_id());  
        		byte[] field2  = serializer.serialize(tr.getTech_id().toString()); 
        		connection.hDel(key2, field2);        		
        		byte[] key3  = serializer.serialize("teacher2role:"+tr.getTech2role_id());  
        		connection.del(key3);
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
					deleteRedis(connection,serializer,"t2role:*");
					deleteRedis(connection,serializer,"role2t:*");
					deleteRedis(connection,serializer,"teacher2role:*");
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
	public TeacherRole getByTeach2role_id(final String teach2role_id) {
		// TODO Auto-generated method stub
		TeacherRole result = redisTemplate.execute(new RedisCallback<TeacherRole>() {  
            public TeacherRole doInRedis(RedisConnection connection)  
                    throws DataAccessException {  
                RedisSerializer<String> serializer = redisTemplate.getStringSerializer();
                byte[] key = serializer.serialize("teacher2role:"+teach2role_id);  
                TeacherRole user = null;
				try {
					byte[] value = connection.get(key); 
	                String evalue = serializer.deserialize(value); 
	                if(StringUtils.isNotEmpty(evalue))
	                	user = util.getPojoFromJSON(evalue, TeacherRole.class);
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
	public Map<String, TeacherRole> getByTeacherId(final String tech_id) {
		// TODO Auto-generated method stub
		Map<String,TeacherRole> result = redisTemplate.execute(new RedisCallback<Map<String,TeacherRole>>() {  
            public Map<String,TeacherRole> doInRedis(RedisConnection connection)  
                    throws DataAccessException {  
                RedisSerializer<String> serializer = redisTemplate.getStringSerializer();
                byte[] key = serializer.serialize("t2role:"+tech_id);  
                Map<String,TeacherRole> teachers = new HashMap<String,TeacherRole>();
                Map<String, TeacherRole> tempMap = new LinkedHashMap<String, TeacherRole>();
				try {
					Map<byte[], byte[]> values = connection.hGetAll(key);
					for (Entry<byte[], byte[]> teacher : values.entrySet()) {
						String ekey = serializer.deserialize(teacher.getKey());
						String evaluestr = serializer.deserialize(teacher.getValue());
						if(StringUtils.isNotEmpty(evaluestr)){
						TeacherRole evalue = util.getPojoFromJSON(evaluestr, TeacherRole.class);
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

	@Override
	public Map<String, TeacherRole> getByRole_id(final Integer role_id) {
		// TODO Auto-generated method stub
		Map<String,TeacherRole> result = redisTemplate.execute(new RedisCallback<Map<String,TeacherRole>>() {  
            public Map<String,TeacherRole> doInRedis(RedisConnection connection)  
                    throws DataAccessException {  
                RedisSerializer<String> serializer = redisTemplate.getStringSerializer();
                byte[] key = serializer.serialize("role2t:"+role_id);  
                Map<String,TeacherRole> teachers = new HashMap<String,TeacherRole>();
                Map<String, TeacherRole> tempMap = new LinkedHashMap<String, TeacherRole>();
				try {
					Map<byte[], byte[]> values = connection.hGetAll(key);
					for (Entry<byte[], byte[]> teacher : values.entrySet()) {
						String ekey = serializer.deserialize(teacher.getKey());
						String evaluestr = serializer.deserialize(teacher.getValue());
						if(StringUtils.isNotEmpty(evaluestr)){
						TeacherRole evalue = util.getPojoFromJSON(evaluestr, TeacherRole.class);
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
