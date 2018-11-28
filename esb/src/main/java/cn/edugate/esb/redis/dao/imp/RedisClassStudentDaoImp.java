package cn.edugate.esb.redis.dao.imp;

import java.util.ArrayList;
import java.util.Collections;
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

import cn.edugate.esb.comparator.StudToSortComparator;
import cn.edugate.esb.comparator.StudToSortComparatorWX;
import cn.edugate.esb.entity.Clas2Student;
import cn.edugate.esb.entity.Classes;
import cn.edugate.esb.entity.Grade;
import cn.edugate.esb.entity.StudentParent;
import cn.edugate.esb.redis.RedisGeneratorDao;
import cn.edugate.esb.redis.dao.RedisClassStudentDao;
import cn.edugate.esb.redis.dao.RedisClassesDao;
import cn.edugate.esb.util.Util;
import cn.edugate.esb.util.Utils;

@Repository
public class RedisClassStudentDaoImp extends RedisGeneratorDao<String, String> implements RedisClassStudentDao {
	
	private Util util;
	@Autowired
	public void setUtil(Util util) {
		this.util = util;
	}
	@Autowired
    private RedisClassesDao redisClassesDao;
	@Override
	public boolean add(final Clas2Student cs) {
		// TODO Auto-generated method stub
		boolean result = redisTemplate.execute(new RedisCallback<Boolean>() {  
            public Boolean doInRedis(RedisConnection connection)  
                    throws DataAccessException {  
                RedisSerializer<String> serializer = redisTemplate.getStringSerializer();
                if(!cs.getIs_del()){
	            	String json="";
	            	json = util.getJSONFromPOJO(cs);
	            	byte[] value = serializer.serialize(json); 
	        		byte[] key  = serializer.serialize("Clas2StudentId:"+cs.getClas2stud_id()); 
	        		connection.set(key,value);	        		
	        		byte[] key1  = serializer.serialize("Clas2Student:"+cs.getClas_id()); 	        		
	        		if(cs.getStud_id()!=null){
		        		byte[] field1  = serializer.serialize(cs.getStud_id().toString()); 
		        		connection.hSet(key1,field1, value); 
		        		byte[] key2  = serializer.serialize("Student2Clas:"+cs.getStud_id().toString());
		        		if(cs.getClas_id()!=null){
			        		byte[] field2 = serializer.serialize(cs.getClas_id().toString()); 
			            	connection.hSet(key2,field2, value); 
		        		}
	        		}
	            	byte[] key2  = serializer.serialize("Clas2Student_orgid:"+cs.getOrg_id());  
	            	byte[] field2  = serializer.serialize(cs.getClas2stud_id().toString());
	            	connection.hSet(key2,field2, value); 
                }
                return true;  
            }
        }, false, true);  
        return result;  
	}
	
	@Override
	public boolean adds(final List<Clas2Student> css) {
		// TODO Auto-generated method stub
		boolean result = redisTemplate.execute(new RedisCallback<Boolean>() {  
            public Boolean doInRedis(RedisConnection connection)  
                    throws DataAccessException {  
                RedisSerializer<String> serializer = redisTemplate.getStringSerializer();              	
                for (Clas2Student cs : css) {  
                	//if(!cs.getIs_del()){
	                	String json="";
	                	json = util.getJSONFromPOJO(cs);
		            	byte[] value = serializer.serialize(json); 
		        		byte[] key  = serializer.serialize("Clas2StudentId:"+cs.getClas2stud_id()); 
		        		connection.set(key,value);	        		
		        		byte[] key1  = serializer.serialize("Clas2Student:"+cs.getClas_id()); 	        		
		        		if(cs.getStud_id()!=null){
			        		byte[] field1  = serializer.serialize(cs.getStud_id().toString()); 
			        		connection.hSet(key1,field1, value); 
			        		byte[] key2  = serializer.serialize("Student2Clas:"+cs.getStud_id().toString()); 
			        		if(cs.getClas_id()!=null){
				        		byte[] field2 = serializer.serialize(cs.getClas_id().toString()); 
				            	connection.hSet(key2,field2, value); 
			        		}
		        		}
		            	byte[] key2  = serializer.serialize("Clas2Student_orgid:"+cs.getOrg_id());  
		            	byte[] field2  = serializer.serialize(cs.getClas2stud_id().toString());
		            	connection.hSet(key2,field2, value); 
                	//}
                }                  
                return true;  
            }
        }, false, true);  
        return result;  
	}	

	@Override
	public boolean delete(final Clas2Student cs) {
		// TODO Auto-generated method stub
		boolean result = redisTemplate.execute(new RedisCallback<Boolean>() {  
            public Boolean doInRedis(RedisConnection connection)  
                    throws DataAccessException {  
                RedisSerializer<String> serializer = redisTemplate.getStringSerializer();
                byte[] key  = serializer.serialize("Clas2StudentId:"+cs.getClas2stud_id());
        		connection.del(key);
        		byte[] key1  = serializer.serialize("Clas2Student:"+cs.getClas_id());  
        		byte[] field1 = serializer.serialize(cs.getStud_id().toString()); 
        		connection.hDel(key1, field1);
        		byte[] key2  = serializer.serialize("Student2Clas:"+cs.getStud_id().toString()); 
        		byte[] field2 = serializer.serialize(cs.getClas_id().toString()); 
            	connection.hDel(key2,field2);
            	byte[] key3  = serializer.serialize("Clas2Student_orgid:"+cs.getOrg_id());  
            	byte[] field3 = serializer.serialize(cs.getClas2stud_id().toString());
            	connection.hDel(key3, field3);
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
					deleteRedis(connection, serializer, "Clas2StudentId:"+"*");
					deleteRedis(connection, serializer, "Clas2Student:"+"*");
					deleteRedis(connection, serializer, "Student2Clas:"+"*");
					deleteRedis(connection, serializer, "Clas2Student_orgid:"+"*");
				} catch (Exception e) {
					// TODO Auto-generated catch block
					return false;
				}                
                return true;
            }  
        });  
        return result;   
	}
	/**
	 * 刷新缓存  用到此方法
	 * 
	 * **/
	@Override
	public Clas2Student getById(final String clas2stud_id) {
		// TODO Auto-generated method stub
		Clas2Student result = redisTemplate.execute(new RedisCallback<Clas2Student>() {  
            public Clas2Student doInRedis(RedisConnection connection)  
                    throws DataAccessException {  
                RedisSerializer<String> serializer = redisTemplate.getStringSerializer();
                byte[] key = serializer.serialize("Clas2StudentId:"+clas2stud_id);  
                Clas2Student cs = null;
				try {
					byte[] value = connection.get(key); 
	                String evalue = serializer.deserialize(value); 
	                if(StringUtils.isNotEmpty(evalue))
	                	cs = util.getPojoFromJSON(evalue, Clas2Student.class);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					return null;
				}                
                return cs;
            }  
        });  
        return result;
	}

	@Override
	public Map<String, Clas2Student> getByStud_id(final String stud_id) {
		// TODO Auto-generated method stub
		Map<String, Clas2Student> result = redisTemplate.execute(new RedisCallback<Map<String, Clas2Student>>() {  
            public Map<String, Clas2Student> doInRedis(RedisConnection redisConnection)  
                    throws DataAccessException {  
                RedisSerializer<String> serializer = redisTemplate.getStringSerializer();
                byte[] key = serializer.serialize("Student2Clas:"+stud_id);
                Map<String, Clas2Student> map = new HashMap<String, Clas2Student>();
                Map<String, Clas2Student> sortMap = new LinkedHashMap<String, Clas2Student>();
				try {
					Map<byte[], byte[]> values = redisConnection.hGetAll(key);
					for (Entry<byte[], byte[]> entrySet : values.entrySet()) {
						String cckey = serializer.deserialize(entrySet.getKey());
						String ccvalue = serializer.deserialize(entrySet.getValue());
						if(StringUtils.isNotEmpty(ccvalue)){
							Clas2Student cct = util.getPojoFromJSON(ccvalue, Clas2Student.class);
							map.put(cckey, cct);
						}
					}	
					sortMap = Utils.sortMapByKey(map);
				} catch (Exception e) {
					e.printStackTrace();
					return null;
				}   
                return sortMap;
            }  
        });  
        return result;
	}	
	
	
	@Override
	public List<Clas2Student> getStudsByCid(final String clas_id) {
		// TODO Auto-generated method stub
		List<Clas2Student> result = redisTemplate.execute(new RedisCallback<List<Clas2Student>>() {  
            public List<Clas2Student> doInRedis(RedisConnection redisConnection)  
                    throws DataAccessException {  
                RedisSerializer<String> serializer = redisTemplate.getStringSerializer();
                byte[] key = serializer.serialize("Clas2Student:"+clas_id);
                
                List<Clas2Student> list = new ArrayList<Clas2Student>();
				try {
					Map<byte[], byte[]> values = redisConnection.hGetAll(key);
					for (Entry<byte[], byte[]> entrySet : values.entrySet()) {
						String cckey = serializer.deserialize(entrySet.getKey());
						String ccvalue = serializer.deserialize(entrySet.getValue());
						if(StringUtils.isNotEmpty(ccvalue)){
							Clas2Student cct = util.getPojoFromJSON(ccvalue, Clas2Student.class);
							list.add(cct);
						}
					}	
				} catch (Exception e) {
					e.printStackTrace();
					return null;
				}   
				//StudToSortComparator mc = new StudToSortComparator();
				StudToSortComparatorWX mc = new StudToSortComparatorWX();
				Collections.sort(list,mc);
                return list;
            }  
        });  
        return result;
	}	
	
	
	
	@Override
	public List<Clas2Student> getStudsByCids(final Map<String,Classes> clas_ids,final Map<String,Object> stud_ids) {
		// TODO Auto-generated method stub
		List<Clas2Student> result = redisTemplate.execute(new RedisCallback<List<Clas2Student>>() {  
            public List<Clas2Student> doInRedis(RedisConnection redisConnection)  
                    throws DataAccessException {  
                RedisSerializer<String> serializer = redisTemplate.getStringSerializer();

                List<Clas2Student> list = new ArrayList<Clas2Student>();
				try {
					
					for(Map.Entry<String, Classes> entry : clas_ids.entrySet()){
						byte[] key = serializer.serialize("Clas2Student:"+entry.getKey().toString());
						Map<byte[], byte[]> values = redisConnection.hGetAll(key);
						for (Entry<byte[], byte[]> entrySet : values.entrySet()) {
							String cckey = serializer.deserialize(entrySet.getKey());
							String ccvalue = serializer.deserialize(entrySet.getValue());
							if(StringUtils.isNotEmpty(ccvalue)){
								Clas2Student cct = util.getPojoFromJSON(ccvalue, Clas2Student.class);
								//Grade grade = redisClassesDao.getGradesByCid(cct.getClas_id());
								cct.setGrade_id(entry.getValue().getGrade_id());
								cct.setGrade_name(entry.getValue().getGrade_name());
								list.add(cct);
							}
						}	
					}
					
					for(Map.Entry<String, Object> entry : stud_ids.entrySet()){
						byte[] key = serializer.serialize("Student2Clas:"+entry.getKey().toString());
						Map<byte[], byte[]> values = redisConnection.hGetAll(key);
						for (Entry<byte[], byte[]> entrySet : values.entrySet()) {
							String cckey = serializer.deserialize(entrySet.getKey());
							String ccvalue = serializer.deserialize(entrySet.getValue());
							if(StringUtils.isNotEmpty(ccvalue)){
								Clas2Student cct = util.getPojoFromJSON(ccvalue, Clas2Student.class);
								Grade grade = redisClassesDao.getGradesByCid(cct.getClas_id());
								cct.setGrade_id(grade.getGrade_id());
								cct.setGrade_name(grade.getGrade_name());
								list.add(cct);
							}
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
	public List<Clas2Student> getStudsBySids(final Map<String, Object> stud_ids) {
		// TODO Auto-generated method stub
		List<Clas2Student> result = redisTemplate.execute(new RedisCallback<List<Clas2Student>>() {  
            public List<Clas2Student> doInRedis(RedisConnection redisConnection)  
                    throws DataAccessException {  
                RedisSerializer<String> serializer = redisTemplate.getStringSerializer();
                
                
                List<Clas2Student> list = new ArrayList<Clas2Student>();
				try {
					
					for(Map.Entry<String, Object> entry : stud_ids.entrySet()){
						byte[] key = serializer.serialize("Student2Clas:"+entry.getKey().toString());
						Map<byte[], byte[]> values = redisConnection.hGetAll(key);
						for (Entry<byte[], byte[]> entrySet : values.entrySet()) {
							String cckey = serializer.deserialize(entrySet.getKey());
							String ccvalue = serializer.deserialize(entrySet.getValue());
							if(StringUtils.isNotEmpty(ccvalue)){
								Clas2Student cct = util.getPojoFromJSON(ccvalue, Clas2Student.class);
								list.add(cct);
							}
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
	public Integer getStudCountByCid(final String clas_id) {
		// TODO Auto-generated method stub
		Integer result = redisTemplate.execute(new RedisCallback<Integer>() {  
            public Integer doInRedis(RedisConnection redisConnection)  
                    throws DataAccessException {  
                RedisSerializer<String> serializer = redisTemplate.getStringSerializer();
                byte[] key = serializer.serialize("Clas2Student:"+clas_id);
                
                Integer num = 0;
				try {
					Map<byte[], byte[]> values = redisConnection.hGetAll(key);
					for (Entry<byte[], byte[]> entrySet : values.entrySet()) {
						num++;
					}	
				} catch (Exception e) {
					e.printStackTrace();
					return null;
				}   
			
                return num;
            }  
        });  
        return result;
	}
	/**
	 * 刷新缓存  用到此方法
	 * 
	 * **/
	@Override
	public List<Clas2Student> getByOrgId(final String org_id) {
		List<Clas2Student> result = redisTemplate.execute(new RedisCallback<List<Clas2Student>>() {  
            public List<Clas2Student> doInRedis(RedisConnection connection)  
                    throws DataAccessException {  
                RedisSerializer<String> serializer = redisTemplate.getStringSerializer();
                byte[] key = serializer.serialize("Clas2Student_orgid:"+org_id);  
                List<Clas2Student> list = new ArrayList<Clas2Student>();
				try {
					Map<byte[], byte[]> values = connection.hGetAll(key);
					for (Entry<byte[], byte[]> sp : values.entrySet()) {
						String evaluestr = serializer.deserialize(sp.getValue());
						if(StringUtils.isNotEmpty(evaluestr)){
							Clas2Student evalue = util.getPojoFromJSON(evaluestr, Clas2Student.class);
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
	
	
}
