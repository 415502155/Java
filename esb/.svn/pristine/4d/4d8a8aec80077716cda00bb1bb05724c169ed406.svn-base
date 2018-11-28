package cn.edugate.esb.redis.dao.imp;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
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

import cn.edugate.esb.entity.Classes;
import cn.edugate.esb.entity.Grade;
import cn.edugate.esb.entity.Group;
import cn.edugate.esb.redis.RedisGeneratorDao;
import cn.edugate.esb.redis.dao.RedisClassesDao;
import cn.edugate.esb.redis.dao.RedisGradeDao;
import cn.edugate.esb.util.Constant;
import cn.edugate.esb.util.Util;

/**
 * Title:RedisClassesDaoImpl
 * Description:
 * Company:SJWY
 * @author:Liuy
 * @Date:2017年5月23日下午6:08:50
 */
@Repository
public class RedisClassesDaoImpl extends RedisGeneratorDao<String, String> implements RedisClassesDao {

	private static Logger logger = Logger.getLogger(RedisClassesDaoImpl.class);
	
	@Autowired
	private RedisGradeDao redisGradeDao;
	
	private Util util;
	@Autowired
	public void setUtil(Util util) {
		this.util = util;
	}
	/**
	 * 刷新缓存  用到此方法
	 * 
	 * **/
	@Override
	public List<Classes> getClassesOfOrg(final int orgId) {
		List<Classes> result = redisTemplate.execute(new RedisCallback<List<Classes>>() {  
            public List<Classes> doInRedis(RedisConnection connection) throws DataAccessException {  
                RedisSerializer<String> serializer = redisTemplate.getStringSerializer();
                byte[] key = serializer.serialize("classesOrg:"+orgId);  
                List<Classes> list = new ArrayList<Classes>();
				try {
					Map<byte[], byte[]> values = connection.hGetAll(key);
					for (Entry<byte[], byte[]> org : values.entrySet()) {
						String evaluestr = serializer.deserialize(org.getValue());
						if(StringUtils.isNotEmpty(evaluestr)){
							Classes evalue = util.getPojoFromJSON(evaluestr, Classes.class);
							if(evalue.getIs_graduated()==0)
								list.add(evalue);
						}
					}					
				} catch (Exception e) {
					logger.error("=====getClassesOfOrg error:"+e.getMessage());
					return null;
				}   
                return list;
            }  
        });  
        return result;
	}
	
	@Override
	public List<Classes> getClassesOfGrade(final int gradeId,final RedisConnection connection) {
		if(null!=connection){
			List<Classes> result = redisTemplate.execute(new RedisCallback<List<Classes>>() {  
	            public List<Classes> doInRedis(RedisConnection redisConnection) throws DataAccessException {  
	                RedisSerializer<String> serializer = redisTemplate.getStringSerializer();
	                byte[] key = serializer.serialize("classesGrade:"+gradeId);  
	                List<Classes> list = new ArrayList<Classes>();
					try {
						Map<byte[], byte[]> values = connection.hGetAll(key);
						for (Entry<byte[], byte[]> org : values.entrySet()) {
							String evaluestr = serializer.deserialize(org.getValue());
							if(StringUtils.isNotEmpty(evaluestr)){
								Classes evalue = util.getPojoFromJSON(evaluestr, Classes.class);
								list.add(evalue);
							}
						}					
					} catch (Exception e) {
						logger.error("=====getClassesOfGrade error:"+e.getMessage());
						return null;
					}   
	                return list;
	            }  
	        });  
	        return result;
		}else{
			List<Classes> result = redisTemplate.execute(new RedisCallback<List<Classes>>() {  
	            public List<Classes> doInRedis(RedisConnection redisConnection) throws DataAccessException {  
	                RedisSerializer<String> serializer = redisTemplate.getStringSerializer();
	                byte[] key = serializer.serialize("classesGrade:"+gradeId);  
	                List<Classes> list = new ArrayList<Classes>();
					try {
						Map<byte[], byte[]> values = redisConnection.hGetAll(key);
						for (Entry<byte[], byte[]> org : values.entrySet()) {
							String evaluestr = serializer.deserialize(org.getValue());
							if(StringUtils.isNotEmpty(evaluestr)){
								Classes evalue = util.getPojoFromJSON(evaluestr, Classes.class);
								list.add(evalue);
							}
						}					
					} catch (Exception e) {
						logger.error("=====getClassesOfGrade error:"+e.getMessage());
						return null;
					}   
	                return list;
	            }  
	        });  
	        return result;
		}
		
	}
	
	@Override
	public List<Classes> getClassesOfCampus(final int campusId) {
		List<Classes> result = redisTemplate.execute(new RedisCallback<List<Classes>>() {  
            public List<Classes> doInRedis(RedisConnection connection) throws DataAccessException {  
                RedisSerializer<String> serializer = redisTemplate.getStringSerializer();
                byte[] key = serializer.serialize("classesCampus:"+campusId);  
                List<Classes> list = new ArrayList<Classes>();
				try {
					Map<byte[], byte[]> values = connection.hGetAll(key);
					for (Entry<byte[], byte[]> org : values.entrySet()) {
						String evaluestr = serializer.deserialize(org.getValue());
						if(StringUtils.isNotEmpty(evaluestr)){
							Classes evalue = util.getPojoFromJSON(evaluestr, Classes.class);
							if(evalue.getIs_graduated()==0)
								list.add(evalue);
						}
					}					
				} catch (Exception e) {
					logger.error("=====getClassesOfCampus error:"+e.getMessage());
					return null;
				}   
                return list;
            }  
        });  
        return result;
	}
	
	@Override
	public boolean addClasses(final Classes classes) {
		boolean result = redisTemplate.execute(new RedisCallback<Boolean>() {  
            public Boolean doInRedis(RedisConnection connection) throws DataAccessException {  
                RedisSerializer<String> serializer = redisTemplate.getStringSerializer();  
                if(classes.getIs_del()==Constant.IS_DEL_NO){
	            	String json="";
	            	json = util.getJSONFromPOJO(classes);
	            	byte[] value = serializer.serialize(json); 
	            	byte[] clasfield  = serializer.serialize(classes.getClas_id().toString());
	            	//班级
	        		byte[] key1  = serializer.serialize("classes:"+classes.getClas_id());  
	        		connection.set(key1, value); 
	        		//机构下班级
            		byte[] key3  = serializer.serialize("classesOrg:"+classes.getOrg_id());  
            		connection.hSet(key3,clasfield, value);
            		
            		//机构下班级+名称
            		byte[] keyclass  = serializer.serialize("classesOrgClassName:"+classes.getOrg_id()+":"+classes.getClas_name());  
            		byte[] classfield  = serializer.serialize(classes.getClas_id().toString());
            		connection.hSet(keyclass,classfield, value);
            		
            		//机构下 校区下  班级
            		byte[] key6  = serializer.serialize("classesCampus:"+classes.getCam_id());  
            		connection.hSet(key6,clasfield, value);
            		//年级下 班级
            		if(null!=classes.getGrade_id()){
            			// 用年级查班级
            			byte[] key4  = serializer.serialize("classesGrade:"+classes.getGrade_id());  
            			connection.hSet(key4,clasfield, value);
            			// 用班级查年级
            			Grade grade = redisGradeDao.getGradeById(classes.getGrade_id(),null);
            			String jsonGrade = util.getJSONFromPOJO(grade);
            			byte[] keyGrade = serializer.serialize("gradeOfClassId:"+classes.getClas_id()); 
            			byte[] valueGrade = serializer.serialize(jsonGrade); 
            			connection.set(keyGrade, valueGrade);
            		}
            		byte[] key9  = serializer.serialize("classesAll");  
	        		connection.hSet(key9,clasfield, value);  
                }        
                return true;  
            }
        }, false, true);  
        return result;  
	}
	
	@Override
	public boolean addClassess(final List<Classes> classess) {
		boolean result = redisTemplate.execute(new RedisCallback<Boolean>() {  
            public Boolean doInRedis(RedisConnection connection) throws DataAccessException {  
                RedisSerializer<String> serializer = redisTemplate.getStringSerializer();  
                for (Classes classes : classess) {
                	//if(classes.getIs_del()==Constant.IS_DEL_NO){
		            	String json="";
		            	json = util.getJSONFromPOJO(classes);
		            	byte[] value = serializer.serialize(json); 
		            	byte[] clasfield  = serializer.serialize(classes.getClas_id().toString());
		            	//班级
		        		byte[] key1  = serializer.serialize("classes:"+classes.getClas_id());  
		        		connection.set(key1, value); 
		        		//机构下班级
	            		byte[] key3  = serializer.serialize("classesOrg:"+classes.getOrg_id());  
	            		connection.hSet(key3,clasfield, value);
	            		
	            		//机构下班级+名称
	            		byte[] keyclass  = serializer.serialize("classesOrgClassName:"+classes.getOrg_id()+":"+classes.getClas_name());  
	            		byte[] classfield  = serializer.serialize(classes.getClas_id().toString());
	            		connection.hSet(keyclass,classfield, value);
	            		
	            		//机构下 校区下  班级
	            		byte[] key6  = serializer.serialize("classesCampus:"+classes.getCam_id());  
	            		connection.hSet(key6,clasfield, value);
	            		//年级下 班级
	            		if(null!=classes.getGrade_id()){
		            		byte[] key4  = serializer.serialize("classesGrade:"+classes.getGrade_id());  
		            		connection.hSet(key4,clasfield, value);    
		            		// 用班级查年级
	            			Grade grade = redisGradeDao.getGradeById(classes.getGrade_id(),null);
	            			String jsonGrade = util.getJSONFromPOJO(grade);
	            			byte[] keyGrade = serializer.serialize("gradeOfClassId:"+classes.getClas_id()); 
	            			byte[] valueGrade = serializer.serialize(jsonGrade); 
	            			connection.set(keyGrade, valueGrade);
	            		}
	            		
	            		byte[] key9  = serializer.serialize("classesAll");  
		        		connection.hSet(key9,clasfield, value); 
                	//}
				}
                return true;  
            }
        }, false, true);  
        return result;  
	}

	@Override
	public boolean deleteClasses(final Classes classes) {
		boolean result = redisTemplate.execute(new RedisCallback<Boolean>() {  
            public Boolean doInRedis(RedisConnection connection) throws DataAccessException {  
                RedisSerializer<String> serializer = redisTemplate.getStringSerializer();
                byte[] field = serializer.serialize(classes.getClas_id().toString()); 
                
        		byte[] key1  = serializer.serialize("classes:"+classes.getClas_id());  
        		connection.del(key1);    
        		byte[] key3  = serializer.serialize("classesOrg:"+classes.getOrg_id());  
        		connection.hDel(key3,field);
        		byte[] key4  = serializer.serialize("classesGrade:"+classes.getGrade_id());  
        		connection.hDel(key4,field);
        		byte[] key6  = serializer.serialize("classesCampus:"+classes.getCam_id());  
        		connection.hDel(key6,field);
        		byte[] key7  = serializer.serialize("gradeOfClassId:"+classes.getClas_id());  
        		connection.del(key7);
        		byte[] key9  = serializer.serialize("classesAll");  
        		connection.hDel(key9,field);  
        		
        		byte[] key10  = serializer.serialize("classesOrgClassName:"+classes.getOrg_id()+":"+classes.getClas_name());  
        		connection.hDel(key10,field);
        		
                return true;  
            }
        }, false, true);  
        return result;
	}

	@Override
	public boolean deleteAllClassess() {
		boolean result = redisTemplate.execute(new RedisCallback<Boolean>() {  
            public Boolean doInRedis(RedisConnection connection) throws DataAccessException {  
                RedisSerializer<String> serializer = redisTemplate.getStringSerializer();
				try {
					deleteRedis(connection,serializer,"classes:*");
					deleteRedis(connection,serializer,"classesGrade:*");
					deleteRedis(connection,serializer,"classesOrg:*");
					deleteRedis(connection,serializer,"classesAll:*");
					deleteRedis(connection,serializer,"classesCampus:*");
					deleteRedis(connection,serializer,"gradeOfClassId:*");
					deleteRedis(connection,serializer,"classesOrgClassName:*");
				} catch (Exception e) {
					logger.error("=====deleteAllClassess error:"+e.getMessage());
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
	public Classes getClassesById(final String classesId,final RedisConnection connection) {
		if(null!=connection){
			Classes result = redisTemplate.execute(new RedisCallback<Classes>() {  
	            public Classes doInRedis(RedisConnection redisConnection) throws DataAccessException {  
	                RedisSerializer<String> serializer = redisTemplate.getStringSerializer();
	                Classes classes = new Classes();
					try {
						byte[] key = serializer.serialize("classes:"+classesId);
						byte[] value = connection.get(key);
		                String evalue = serializer.deserialize(value); 
		                if(StringUtils.isNotEmpty(evalue))
		                	classes = util.getPojoFromJSON(evalue, Classes.class);
					} catch (Exception e) {
						logger.error("=====getClassesById error:"+e.getMessage());
						return null;
					}                
	                return classes;
	            }  
	        });  
	        return result;
		}else{
			Classes result = redisTemplate.execute(new RedisCallback<Classes>() {  
	            public Classes doInRedis(RedisConnection redisConnection) throws DataAccessException {  
	                RedisSerializer<String> serializer = redisTemplate.getStringSerializer();
	                Classes classes = new Classes();
					try {
						byte[] key = serializer.serialize("classes:"+classesId);
						byte[] value = redisConnection.get(key);
		                String evalue = serializer.deserialize(value); 
		                if(StringUtils.isNotEmpty(evalue))
		                	classes = util.getPojoFromJSON(evalue, Classes.class);
					} catch (Exception e) {
						logger.error("=====getClassesById error:"+e.getMessage());
						return null;
					}                
	                return classes;
	            }  
	        });  
	        return result;
		}
	}

//	@Override
//	public List<Classes> getClassesByStudId(final Integer stud_id) {
//		List<Classes> result = redisTemplate.execute(new RedisCallback<List<Classes>>() {  
//            public List<Classes> doInRedis(RedisConnection connection) throws DataAccessException {  
//                RedisSerializer<String> serializer = redisTemplate.getStringSerializer();
//                byte[] key = serializer.serialize("Student2Clas:"+stud_id);  
//                List<Classes> list = new ArrayList<Classes>();
//				try {
//					Map<byte[], byte[]> values = connection.hGetAll(key);
//					for (Entry<byte[], byte[]> org : values.entrySet()) {
//						String evaluestr = serializer.deserialize(org.getValue());
//						if(StringUtils.isNotEmpty(evaluestr)){
//							Classes evalue = util.getPojoFromJSON(evaluestr, Classes.class);
//							list.add(evalue);
//						}
//					}					
//				} catch (Exception e) {
//					logger.error("=====getClassesByStudId error:"+e.getMessage());
//					return null;
//				}   
//                return list;
//            }  
//        });  
//        return result;
//	}

	
	@Override
	public Grade getGradesByCid(final Integer clas_id) {
		Grade result = redisTemplate.execute(new RedisCallback<Grade>() {  
            public Grade doInRedis(RedisConnection connection) throws DataAccessException {  
                RedisSerializer<String> serializer = redisTemplate.getStringSerializer();
                Grade grade = null;
				try {
					byte[] key = serializer.serialize("gradeOfClassId:"+clas_id);
					byte[] value = connection.get(key);
	                String evalue = serializer.deserialize(value); 
	                if(StringUtils.isNotEmpty(evalue))
	                	grade = util.getPojoFromJSON(evalue, Grade.class);					
				} catch (Exception e) {
					logger.error("=====getClassesByStudId error:"+e.getMessage());
					return null;
				}   
                return grade;
            }  
        });  
        return result;
	}
	
	
	
	
	
	@Override
	public Map<String, Classes> getClassesOfGIds(final Map<String,Grade> gradeIds,final Map<String,Object> cmap) {
		
		Map<String, Classes> result = redisTemplate.execute(new RedisCallback<Map<String, Classes>>() {  
            public Map<String, Classes> doInRedis(RedisConnection redisConnection) throws DataAccessException {  
                RedisSerializer<String> serializer = redisTemplate.getStringSerializer();
                Map<String, Classes> tempMap = new HashMap<String, Classes>();
				try {
					
					for(Map.Entry<String, Grade> entry : gradeIds.entrySet()){
						byte[] key = serializer.serialize("classesGrade:"+entry.getKey().toString());  
						Map<byte[], byte[]> values = redisConnection.hGetAll(key);
						for (Entry<byte[], byte[]> org : values.entrySet()) {
							String evaluestr = serializer.deserialize(org.getValue());
							if(StringUtils.isNotEmpty(evaluestr)){
								Classes evalue = util.getPojoFromJSON(evaluestr, Classes.class);
								evalue.setGrade_id(entry.getValue().getGrade_id());
								evalue.setGrade_name(entry.getValue().getGrade_name());
								tempMap.put(evalue.getClas_id().toString(), evalue);
							}
						}	
					}
					
					for(Map.Entry<String, Object> entry : cmap.entrySet()){

						byte[] key = serializer.serialize("classes:"+entry.getKey().toString());
						byte[] value = redisConnection.get(key);
		                String evalue = serializer.deserialize(value); 
		                if(StringUtils.isNotEmpty(evalue)){
		                	Classes classes = util.getPojoFromJSON(evalue, Classes.class);
		                	Grade grade = redisGradeDao.getGradeByClassId(classes.getClas_id());
		                	classes.setGrade_id(grade.getGrade_id());
		                	classes.setGrade_name(grade.getGrade_name());
							tempMap.put(classes.getClas_id().toString(), classes);
		                }
					}
				} catch (Exception e) {
					logger.error("=====getClassesOfGrade error:"+e.getMessage());
					return null;
				}   
                return tempMap;
            }  
        });  
        return result;

	}
	
	
	
}
