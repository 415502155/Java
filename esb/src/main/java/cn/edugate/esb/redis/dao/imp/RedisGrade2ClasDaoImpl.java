package cn.edugate.esb.redis.dao.imp;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.stereotype.Repository;

import cn.edugate.esb.entity.Card;
import cn.edugate.esb.entity.Grade2Clas;
import cn.edugate.esb.entity.Group;
import cn.edugate.esb.redis.RedisGeneratorDao;
import cn.edugate.esb.redis.dao.RedisCardDao;
import cn.edugate.esb.redis.dao.RedisGrade2ClasDao;
import cn.edugate.esb.util.Util;

@Repository
public class RedisGrade2ClasDaoImpl  extends RedisGeneratorDao<String, String> implements RedisGrade2ClasDao{
	private static Logger logger = Logger.getLogger(RedisGrade2ClasDaoImpl.class);
	@Autowired
	private Util util;
	
	@Override
	public boolean add(final Grade2Clas tr) {
		boolean result = redisTemplate.execute(new RedisCallback<Boolean>() {  
            public Boolean doInRedis(RedisConnection connection) throws DataAccessException { 
        		RedisSerializer<String> serializer = redisTemplate.getStringSerializer();
        		if(!tr.getIs_del()){
        			String json = "";
					json = util.getJSONFromPOJO(tr);
					byte[] value = serializer.serialize(json);        			
	        		byte[] keyt  = serializer.serialize("grade2clas:"+tr.getGra2cls_id().toString());                	  
	            	connection.set(keyt, value);
	            	
	            	byte[] keyg  = serializer.serialize("gradeClass:"+tr.getGrade_id());  
	            	byte[] valueg  = serializer.serialize(tr.getClas_id().toString());	        		
	        		connection.sAdd(keyg, valueg);
	        		
	        		byte[] keyc  = serializer.serialize("classGrade:"+tr.getClas_id());  
	            	byte[] valuec  = serializer.serialize(tr.getGrade_id().toString());	        		
	        		connection.set(keyc, valuec);
        		}
            	return true; 
            }
        }, false, true);  
        return result; 
	}

	@Override
	public boolean add(final List<Grade2Clas> trs) {
		boolean result = redisTemplate.execute(new RedisCallback<Boolean>() {  
            public Boolean doInRedis(RedisConnection connection) throws DataAccessException {  
                RedisSerializer<String> serializer = redisTemplate.getStringSerializer();
	        	for (Grade2Clas tr : trs) { 
	        		if(!tr.getIs_del()){
	        			String json = "";
						json = util.getJSONFromPOJO(tr);
						byte[] value = serializer.serialize(json);        			
		        		byte[] keyt  = serializer.serialize("grade2clas:"+tr.getGra2cls_id().toString());                	  
		            	connection.set(keyt, value);
		            	
		            	byte[] keyg  = serializer.serialize("gradeClass:"+tr.getGrade_id());  
		            	byte[] valueg  = serializer.serialize(tr.getClas_id().toString());	        		
		        		connection.sAdd(keyg, valueg);
		        		
		        		byte[] keyc  = serializer.serialize("classGrade:"+tr.getClas_id());  
		            	byte[] valuec  = serializer.serialize(tr.getGrade_id().toString());	        		
		        		connection.set(keyc, valuec);
	        		}
	        	}
                return true;  
            }
        }, false, true);  
        return result;
	}

	@Override
	public boolean delete(final Grade2Clas tr) {
		boolean result = redisTemplate.execute(new RedisCallback<Boolean>() {  
            public Boolean doInRedis(RedisConnection connection) throws DataAccessException {  
                RedisSerializer<String> serializer = redisTemplate.getStringSerializer();                
        		byte[] key  = serializer.serialize("grade2clas:"+tr.getGra2cls_id().toString());  
        		connection.del(key);
        		
        		byte[] key1  = serializer.serialize("gradeClass:"+tr.getGrade_id());  
        		byte[] field1 = serializer.serialize(tr.getClas_id().toString()); 
        		connection.sRem(key1, field1);
        		
        		byte[] key2  = serializer.serialize("classGrade:"+tr.getClas_id());  
        		connection.del(key2);
        		
                return true;  
            }
        }, false, true);
        return result;
	}

	@Override
	public boolean deleteAll() {
		boolean result = redisTemplate.execute(new RedisCallback<Boolean>() {  
            public Boolean doInRedis(RedisConnection connection) throws DataAccessException {  
                RedisSerializer<String> serializer = redisTemplate.getStringSerializer();
				try {
					deleteRedis(connection,serializer,"grade2clas:*");	
					deleteRedis(connection,serializer,"gradeClass:*");
					deleteRedis(connection,serializer,"classGrade:*");
				} catch (Exception e) {
					logger.error("=====deleteAll error:"+e.getMessage());
					return false;
				}                
                return true;
            }  
        });  
        return result; 
	}

	@Override
	public Grade2Clas getById(final Integer gra2cls_id) {
		// TODO Auto-generated method stub
		Grade2Clas result = redisTemplate.execute(new RedisCallback<Grade2Clas>() {  
			public Grade2Clas doInRedis(RedisConnection connection) throws DataAccessException {  
				RedisSerializer<String> serializer = redisTemplate.getStringSerializer();
				Grade2Clas group = new Grade2Clas();
				try {
					byte[] key = serializer.serialize("grade2clas:"+gra2cls_id);
					byte[] value = connection.get(key);
					String evalue = serializer.deserialize(value); 
					if(StringUtils.isNotEmpty(evalue))
						group = util.getPojoFromJSON(evalue, Grade2Clas.class);
					else
						return null;
				} catch (Exception e) {
					logger.error("=====getById error:"+e.getMessage());
					return null;
				}                
				return group;
			}  
		});  
		return result;
	}

	@Override
	public Integer getGradeIdByClassId(final Integer classid) {
		// TODO Auto-generated method stub
		Integer result = redisTemplate.execute(new RedisCallback<Integer>() {  
			public Integer doInRedis(RedisConnection connection) throws DataAccessException {  
				RedisSerializer<String> serializer = redisTemplate.getStringSerializer();
				Integer group = null;
				try {
					byte[] key = serializer.serialize("classGrade:"+classid);
					byte[] value = connection.get(key);
					String evalue = serializer.deserialize(value); 
					if(StringUtils.isNotEmpty(evalue))
						group = Integer.parseInt(evalue);
					else
						return null;
				} catch (Exception e) {
					logger.error("=====getById error:"+e.getMessage());
					return null;
				}                
				return group;
			}  
		});  
		return result;
	}

}
