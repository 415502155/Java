package cn.edugate.esb.redis.dao.imp;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.stereotype.Repository;

import cn.edugate.esb.entity.Campus;
import cn.edugate.esb.redis.RedisGeneratorDao;
import cn.edugate.esb.redis.dao.RedisCampusDao;
import cn.edugate.esb.util.Constant;
import cn.edugate.esb.util.Util;
@Repository
public class RedisCampusDaoImpl  extends RedisGeneratorDao<String, String> implements RedisCampusDao{
	private static Logger logger = Logger.getLogger(RedisGroupDaoImpl.class);
	@Autowired
	private Util util;
	
	@Override
	public boolean add(final Campus tr) {
		boolean result = redisTemplate.execute(new RedisCallback<Boolean>() {  
            public Boolean doInRedis(RedisConnection connection) throws DataAccessException {  
            	if(Constant.IS_DEL_NO==tr.getIs_del()){
            		RedisSerializer<String> serializer = redisTemplate.getStringSerializer();
                	String json="";
                	json = util.getJSONFromPOJO(tr);
                	byte[] value = serializer.serialize(json); 
                	byte[] key1  = serializer.serialize("orginfo_campus:"+tr.getOrg_id());  
            		byte[] field2  = serializer.serialize(tr.getCam_id().toString()); 
            		connection.hSet(key1,field2, value);
            		byte[] keyt  = serializer.serialize("campus:"+tr.getCam_id());                	  
                	connection.set(keyt, value);  
            	}
            	return true; 
            }
        }, false, true);  
        return result; 
	}

	@Override
	public boolean add(final List<Campus> trs) {
		boolean result = redisTemplate.execute(new RedisCallback<Boolean>() {  
            public Boolean doInRedis(RedisConnection connection) throws DataAccessException {  
                RedisSerializer<String> serializer = redisTemplate.getStringSerializer();
            	 for (Campus campus : trs) { 
            		 //if(Constant.IS_DEL_NO==campus.getIs_del()){
            			 String json="";
     	            	json = util.getJSONFromPOJO(campus);
     	            	byte[] value = serializer.serialize(json);
     	            	byte[] key1  = serializer.serialize("orginfo_campus:"+campus.getOrg_id());  
                 		byte[] field2  = serializer.serialize(campus.getCam_id().toString()); 
                 		connection.hSet(key1,field2, value); 

                 		byte[] keyt  = serializer.serialize("campus:"+campus.getCam_id());                	  
                     	connection.set(keyt, value);  
            		 //}
            	 }
                return true;  
            }
        }, false, true);  
        return result;
	}

	@Override
	public boolean delete(final Campus tr) {
		boolean result = redisTemplate.execute(new RedisCallback<Boolean>() {  
            public Boolean doInRedis(RedisConnection connection) throws DataAccessException {  
                RedisSerializer<String> serializer = redisTemplate.getStringSerializer();
                
                byte[] key1  = serializer.serialize("orginfo_campus:"+tr.getOrg_id());  
        		byte[] field1 = serializer.serialize(tr.getCam_id().toString()); 
        		connection.hDel(key1, field1);
                
        		byte[] key2  = serializer.serialize("campus:"+tr.getCam_id());  
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
					deleteRedis(connection,serializer,"orginfo_campus:*");
					deleteRedis(connection,serializer,"campus:*");
					
				} catch (Exception e) {
					logger.error("=====deleteAllGroups error:"+e.getMessage());
					return false;
				}                
                return true;
            }  
        });  
        return result; 
	}

	@Override
	public List<Campus> getCampusByOrgId(final String org_id) {
		List<Campus> result = redisTemplate.execute(new RedisCallback<List<Campus>>() {  
            public List<Campus> doInRedis(RedisConnection connection) throws DataAccessException {  
                RedisSerializer<String> serializer = redisTemplate.getStringSerializer();
                byte[] key = serializer.serialize("orginfo_campus:"+org_id);  
                List<Campus> list = new ArrayList<Campus>();
				try {
					Map<byte[], byte[]> values = connection.hGetAll(key);
					for (Entry<byte[], byte[]> org : values.entrySet()) {
						String evaluestr = serializer.deserialize(org.getValue());
						if(StringUtils.isNotEmpty(evaluestr)){
							Campus evalue = util.getPojoFromJSON(evaluestr, Campus.class);
							list.add(evalue);
						}
					}					
				} catch (Exception e) {					
					return null;
				}   
                return list;
            }  
        });  
        return result;
	}

	@Override
	public Campus getById(final Integer id) {
		Campus result = redisTemplate.execute(new RedisCallback<Campus>() {  
            public Campus doInRedis(RedisConnection connection) throws DataAccessException {  
                RedisSerializer<String> serializer = redisTemplate.getStringSerializer();
                Campus campus = new Campus();
				try {
					byte[] key = serializer.serialize("campus:"+id);
					byte[] value = connection.get(key);
	                String evalue = serializer.deserialize(value);
	                if(StringUtils.isNotEmpty(evalue))
	                	campus = util.getPojoFromJSON(evalue, Campus.class);
				} catch (Exception e) {
					logger.error("=====getGradeById error:"+e.getMessage());
					return null;
				}                
                return campus;
            }  
        });  
        return result;
	}

}
