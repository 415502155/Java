package cn.edugate.esb.redis.dao.imp;

import java.util.ArrayList;
import java.util.Collections;
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

import cn.edugate.esb.comparator.CourseComparator;
import cn.edugate.esb.entity.Course;
import cn.edugate.esb.redis.RedisGeneratorDao;
import cn.edugate.esb.redis.dao.RedisCourseDao;
import cn.edugate.esb.util.Constant;
import cn.edugate.esb.util.Util;

@Repository
public class RedisCourseDaoImpl extends RedisGeneratorDao<String, String> implements RedisCourseDao{

	
	@Autowired
	private Util util;
	
	@Override
	public boolean add(final Course tr) {
		// TODO Auto-generated method stub
		boolean result = redisTemplate.execute(new RedisCallback<Boolean>() {  
            public Boolean doInRedis(RedisConnection connection)  
                    throws DataAccessException { 
            	if(tr.getIs_del()==Constant.IS_DEL_NO){
	                RedisSerializer<String> serializer = redisTemplate.getStringSerializer();
	            	String json="";
	            	json = util.getJSONFromPOJO(tr);
	            	byte[] value = serializer.serialize(json); 
	            	
	            	byte[] key2  = serializer.serialize("course:"+tr.getCour_id());
	            	connection.set(key2, value);   
	            	
	            	byte[] key1  = serializer.serialize("orginfo_course:"+tr.getOrg_id());  
	        		byte[] field2  = serializer.serialize(tr.getCour_id().toString()); 
	        		connection.hSet(key1,field2, value); 
	                
            	}
            	return true;  
            }
        }, false, true);  
        return result; 
	}

	@Override
	public boolean add(final List<Course> trs) {
		// TODO Auto-generated method stub
		boolean result = redisTemplate.execute(new RedisCallback<Boolean>() {  
            public Boolean doInRedis(RedisConnection connection)  
                    throws DataAccessException {  
                RedisSerializer<String> serializer = redisTemplate.getStringSerializer();
            	 for (Course tech : trs) {
            		 //if(tech.getIs_del()==Constant.IS_DEL_NO){
	            		String json="";
		            	json = util.getJSONFromPOJO(tech);
		            	
		            	byte[] value = serializer.serialize(json);
		            	
		            	byte[] key2  = serializer.serialize("course:"+tech.getCour_id());
		            	connection.set(key2, value);   
		            	byte[] key1  = serializer.serialize("orginfo_course:"+tech.getOrg_id());  
	            		byte[] field2  = serializer.serialize(tech.getCour_id().toString()); 
	            		connection.hSet(key1,field2, value); 
            		// }
            	 }
                return true;  
            }
        }, false, true);  
        return result;
	}

	@Override
	public boolean delete(final Course tr) {
		// TODO Auto-generated method stub
		boolean result = redisTemplate.execute(new RedisCallback<Boolean>() {  
            public Boolean doInRedis(RedisConnection connection)  
                    throws DataAccessException {  
                RedisSerializer<String> serializer = redisTemplate.getStringSerializer();
        		
                byte[] key2  = serializer.serialize("course:"+tr.getOrg_id());  
                connection.del(key2);
                
            	byte[] key1  = serializer.serialize("orginfo_course:"+tr.getOrg_id());  
        		byte[] field1 = serializer.serialize(tr.getCour_id().toString()); 
        		connection.hDel(key1, field1);
            	           	
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
					deleteRedis(connection,serializer,"orginfo_course:*");
					deleteRedis(connection,serializer,"course:*");
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
	public List<Course> getCoursByOrgId(final String org_id) {
		// TODO Auto-generated method stub
		List<Course> result = redisTemplate.execute(new RedisCallback<List<Course>>() {

			public List<Course> doInRedis(RedisConnection connection) throws DataAccessException {
				RedisSerializer<String> serializer = redisTemplate.getStringSerializer();
				byte[] key = serializer.serialize("orginfo_course:" + org_id);
				List<Course> list = new ArrayList<Course>();
				try {
					Map<byte[], byte[]> values = connection.hGetAll(key);
					for (Entry<byte[], byte[]> org : values.entrySet()) {
						String evaluestr = serializer.deserialize(org.getValue());
						if(StringUtils.isNotEmpty(evaluestr)){
						Course evalue = util.getPojoFromJSON(evaluestr, Course.class);
						list.add(evalue);
						}
					}

					// 按主键升序排序
					Collections.sort(list, new CourseComparator());
				} catch (Exception e) {
					// TODO Auto-generated catch block
					return null;
				}
				return list;
			}
		});
		return result;
	}

	@Override
	public Course getCourseById(final String cour_id,final RedisConnection connection) {
		if(null!=connection){
			Course result = redisTemplate.execute(new RedisCallback<Course>() {  
	            public Course doInRedis(RedisConnection redisConnection) throws DataAccessException {  
	                RedisSerializer<String> serializer = redisTemplate.getStringSerializer();
	                Course course = new Course();
					try {
						byte[] key = serializer.serialize("course:"+cour_id);
						byte[] value = connection.get(key);
		                String evalue = serializer.deserialize(value); 
		                if(StringUtils.isNotEmpty(evalue))
		                	course = util.getPojoFromJSON(evalue, Course.class);
					} catch (Exception e) {
						
						return null;
					}                
	                return course;
	            }  
	        });  
	        return result;
		}else{
			Course result = redisTemplate.execute(new RedisCallback<Course>() {  
	            public Course doInRedis(RedisConnection redisConnection) throws DataAccessException {  
	                RedisSerializer<String> serializer = redisTemplate.getStringSerializer();
	                Course course = new Course();
					try {
						byte[] key = serializer.serialize("course:"+cour_id);
						byte[] value = redisConnection.get(key);
		                String evalue = serializer.deserialize(value); 
		                if(StringUtils.isNotEmpty(evalue))
		                	course = util.getPojoFromJSON(evalue, Course.class);
					} catch (Exception e) {
						
						return null;
					}                
	                return course;
	            }  
	        });  
	        return result;
		}
	}

}
