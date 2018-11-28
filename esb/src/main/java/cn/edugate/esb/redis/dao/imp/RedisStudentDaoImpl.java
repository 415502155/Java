package cn.edugate.esb.redis.dao.imp;

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

import cn.edugate.esb.entity.Campus;
import cn.edugate.esb.entity.Clas2Student;
import cn.edugate.esb.entity.Classes;
import cn.edugate.esb.entity.Grade;
import cn.edugate.esb.entity.Student;
import cn.edugate.esb.redis.RedisGeneratorDao;
import cn.edugate.esb.redis.dao.RedisCampusDao;
import cn.edugate.esb.redis.dao.RedisClassStudentDao;
import cn.edugate.esb.redis.dao.RedisClassesDao;
import cn.edugate.esb.redis.dao.RedisGradeDao;
import cn.edugate.esb.redis.dao.RedisStudentDao;
import cn.edugate.esb.util.Constant;
import cn.edugate.esb.util.Util;
import cn.edugate.esb.util.Utils;

@Repository
public class RedisStudentDaoImpl  extends RedisGeneratorDao<String, String> implements RedisStudentDao{
	
	private static Logger logger = Logger.getLogger(RedisStudentDaoImpl.class);

	private Util util;
	@Autowired
	public void setUtil(Util util) {
		this.util = util;
	}
	@Autowired
	private RedisClassesDao redisClassesDao;
	@Autowired
	private RedisGradeDao redisGradeDao;
	@Autowired
	private RedisCampusDao redisCampusDao;
	@Autowired
	private RedisClassStudentDao redisClassStudentDao;
	
	@Override
	public boolean add(final Student student) {
		boolean result = redisTemplate.execute(new RedisCallback<Boolean>() {  
            public Boolean doInRedis(RedisConnection connection) throws DataAccessException {  
            	if(student.getIs_del()==Constant.IS_DEL_NO){
	                RedisSerializer<String> serializer = redisTemplate.getStringSerializer();
	                String json="";
            		json = util.getJSONFromPOJO(student);
            		byte[] value = serializer.serialize(json);       		
            		// 学生主键 ：学生个人信息
            		byte[] key1  = serializer.serialize("studentId:"+student.getStud_id());  
            		byte[] field2  = serializer.serialize(student.getStud_id().toString()); 
            		connection.set(key1, value); 
            		// 机构用户主键 ： 学生个人信息
            		byte[] key2  = serializer.serialize("studentUserId:"+student.getUser_id());  
            		connection.set(key2, value); 
            		
            		// 机构主键：学生信息集合
            		byte[] key3  = serializer.serialize("studentOrgId:"+student.getOrg_id());                	  
            		connection.hSet(key3,field2, value); 
//            		// 年级主键：学生信息集合
//            		byte[] key4  = serializer.serialize("studentGradeId:"+student.getGrade_id());                	  
//            		connection.hSet(key4,field2, value); 
//            		// 班级主键：学生信息集合
//            		byte[] key5  = serializer.serialize("studentClassId:"+student.getClas_id());                	  
//            		connection.hSet(key5,field2, value); 
//            		
//	            	// 保存班级信息
//	            	Classes classes = new Classes();
//	            	classes = redisClassesDao.getClassesById(student.getClas_id().toString(),connection);
//	            	String jsonClasses = util.getJSONFromPOJO(classes);
//	            	byte[] keyClasses = serializer.serialize("classOfStudent:"+student.getStud_id()); 
//	            	byte[] valueClasses = serializer.serialize(jsonClasses); 
//	            	byte[] fieldClasses  = serializer.serialize(classes.getClas_id().toString()); 
//	            	connection.hSet(keyClasses, fieldClasses, valueClasses);
            		
            		byte[] key4  = serializer.serialize("student_orgId_name:"+student.getOrg_id()+":"+student.getStud_name());    
            		byte[] field4  = serializer.serialize(student.getStud_id().toString()); 
            		connection.hSet(key4,field4, value); 
            	}
                return true;  
            }
        }, false, true);  
        return result; 
	}

	@Override
	public boolean add(final List<Student> students) {
		boolean result = redisTemplate.execute(new RedisCallback<Boolean>() {  
            public Boolean doInRedis(RedisConnection connection) throws DataAccessException {  
                RedisSerializer<String> serializer = redisTemplate.getStringSerializer();
                for (Student student : students) {
                	//if(student.getIs_del()==Constant.IS_DEL_NO){
                		String json="";
                		json = util.getJSONFromPOJO(student);
                		byte[] value = serializer.serialize(json);       		
                		// 学生主键 ：学生个人信息
                		byte[] key1  = serializer.serialize("studentId:"+student.getStud_id());  
                		byte[] field2  = serializer.serialize(student.getStud_id().toString()); 
                		connection.set(key1, value); 
                		// 机构用户主键 ： 学生个人信息
                		byte[] key2  = serializer.serialize("studentUserId:"+student.getUser_id());  
                		connection.set(key2, value); 
                		
                		// 机构主键：学生信息集合
                		byte[] key3  = serializer.serialize("studentOrgId:"+student.getOrg_id());                	  
                		connection.hSet(key3,field2, value); 
//                		// 年级主键：学生信息集合
//                		byte[] key4  = serializer.serialize("studentGradeId:"+student.getGrade_id());                	  
//                		connection.hSet(key4,field2, value); 
//                		// 班级主键：学生信息集合
//                		byte[] key5  = serializer.serialize("studentClassId:"+student.getClas_id());                	  
//                		connection.hSet(key5,field2, value); 
//                		
//    	            	// 保存班级信息
//    	            	Classes classes = new Classes();
//    	            	classes = redisClassesDao.getClassesById(student.getClas_id().toString(),connection);
//    	            	String jsonClasses = util.getJSONFromPOJO(classes);
//    	            	byte[] keyClasses = serializer.serialize("classOfStudent:"+student.getStud_id()); 
//    	            	byte[] valueClasses = serializer.serialize(jsonClasses); 
//    	            	if(classes!=null && classes.getClas_id()!=null){
//	    	            	byte[] fieldClasses  = serializer.serialize(classes.getClas_id().toString()); 
//	    	            	connection.hSet(keyClasses, fieldClasses, valueClasses);
//    	            	}
//                		byte[] key4  = serializer.serialize("student_orgId_name:"+student.getOrg_id()+":"+student.getStud_name());                	  
//                		connection.set(key4, value); 
                		try {
                			byte[] key4  = serializer.serialize("student_orgId_name:"+student.getOrg_id()+":"+student.getStud_name());    
                    		if(student.getStud_id()!=null){
                    			byte[] field4  = serializer.serialize(student.getStud_id().toString()); 
                        		connection.hSet(key4,field4, value);
                    		}
						} catch (Exception e) {
							logger.info("=======e========"+e.getMessage());
						}
                //	}
                }
                return true;  
            }
        }, false, true);  
        return result; 
	}

	@Override
	public boolean delete(final Student student) {
		boolean result = redisTemplate.execute(new RedisCallback<Boolean>() {  
            public Boolean doInRedis(RedisConnection connection) throws DataAccessException {  
            	RedisSerializer<String> serializer = redisTemplate.getStringSerializer();
            	if(student.getStud_id()!=null){
	            	byte[] field  = serializer.serialize(student.getStud_id().toString()); 
	        		// 学生主键 ：学生个人信息
	        		byte[] key1  = serializer.serialize("studentId:"+student.getStud_id());                	  
	        		connection.del(key1); 
	        		// 机构用户主键 ： 学生个人信息
	        		byte[] key2  = serializer.serialize("studentUserId:"+student.getUser_id());                	  
	        		connection.del(key2); 
	        		// 机构主键：学生信息集合
	        		byte[] key3  = serializer.serialize("studentOrgId:"+student.getOrg_id());                	  
	        		connection.hDel(key3,field); 
//	        		// 年级主键：学生信息集合
//	        		byte[] key4  = serializer.serialize("studentGradeId:"+student.getGrade_id());                	  
//	        		connection.hDel(key4,field); 
//	        		// 班级主键：学生信息集合
//	        		byte[] key5  = serializer.serialize("studentClassId:"+student.getClas_id());                	  
//	        		connection.hDel(key5,field); 
	        		byte[] key4  = serializer.serialize("student_orgId_name:"+student.getOrg_id()+":"+student.getStud_name());                	  
	        		connection.hDel(key4,field); 
            	}
        		
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
					deleteRedis(connection,serializer,"studentId:*");
					deleteRedis(connection,serializer,"studentUserId:*");
					deleteRedis(connection,serializer,"studentOrgId:*");
					deleteRedis(connection,serializer,"studentGradeId:*");
					deleteRedis(connection,serializer,"studentClassId:*");
					deleteRedis(connection,serializer,"classOfStudent:*");
					deleteRedis(connection,serializer,"student_orgId_name:*");
				} catch (Exception e) {
					logger.error("=====deleteAllGroups error:"+e.getMessage());
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
	public Student getByStudentId(final Integer studentId) {
		Student result = redisTemplate.execute(new RedisCallback<Student>() {  
            public Student doInRedis(RedisConnection connection) throws DataAccessException {  
                RedisSerializer<String> serializer = redisTemplate.getStringSerializer();
                Student student = new Student();
				try {
					byte[] key = serializer.serialize("studentId:"+studentId);
					byte[] value = connection.get(key);
	                String evalue = serializer.deserialize(value); 
	                if(StringUtils.isNotEmpty(evalue)){
		                student = util.getPojoFromJSON(evalue, Student.class);
		                //为学生添加班级、年级、校区等信息
		                student = studentHandler(student);
	                }
				} catch (Exception e) {
					logger.error("=====getGroupById error:"+e.getMessage());
					return null;
				}                
                return student;
            }  
        });  
        return result;
	}
	/**
	 * 刷新缓存  用到此方法
	 * 
	 * **/
	@Override
	public Map<String,Student> getStudentsByOrgId(final Integer orgId) {
		Map<String,Student> result = redisTemplate.execute(new RedisCallback<Map<String,Student>>() {  
            public Map<String,Student> doInRedis(RedisConnection connection) throws DataAccessException {  
                RedisSerializer<String> serializer = redisTemplate.getStringSerializer();
                byte[] key = serializer.serialize("studentOrgId:"+orgId);  
                Map<String,Student> studentsMap = new HashMap<String,Student>();
                Map<String, Student> tempMap = new LinkedHashMap<String, Student>();
				try {
					Map<byte[], byte[]> values = connection.hGetAll(key);
					for (Entry<byte[], byte[]> group : values.entrySet()) {
						String ekey = serializer.deserialize(group.getKey());
						String evaluestr = serializer.deserialize(group.getValue());
						if(StringUtils.isNotEmpty(evaluestr)){
							Student evalue = util.getPojoFromJSON(evaluestr, Student.class);
							//为学生添加班级、年级、校区等信息
							//evalue = studentHandler(evalue);
							tempMap.put(ekey, evalue);
						}
					}					
				} catch (Exception e) {
					logger.error("=====getStudentsByOrgId error:"+e.getMessage());
					return null;
				}   
				studentsMap = Utils.sortMapByKey(tempMap);
                return studentsMap;
            }  
        });  
        return result;
	}

	@Override
	public Map<String,Student> getStudentsByClassId(final Integer classId,final RedisConnection connection) {
		if(null!=connection){
			Map<String,Student> result = redisTemplate.execute(new RedisCallback<Map<String,Student>>() {  
	            public Map<String,Student> doInRedis(RedisConnection redisConnection) throws DataAccessException {  
	                RedisSerializer<String> serializer = redisTemplate.getStringSerializer();
	                byte[] key = serializer.serialize("studentClassId:"+classId);  
	                Map<String,Student> studentsMap = new HashMap<String,Student>();
	                Map<String, Student> tempMap = new LinkedHashMap<String, Student>();
					try {
						Map<byte[], byte[]> values = connection.hGetAll(key);
						for (Entry<byte[], byte[]> group : values.entrySet()) {
							String ekey = serializer.deserialize(group.getKey());
							String evaluestr = serializer.deserialize(group.getValue());
							if(StringUtils.isNotEmpty(evaluestr)){
								Student evalue = util.getPojoFromJSON(evaluestr, Student.class);
								//为学生添加班级、年级、校区等信息
								//evalue = studentHandler(evalue);
								tempMap.put(ekey, evalue);
							}
						}					
					} catch (Exception e) {
						logger.error("=====getStudentsByClassId error:"+e.getMessage());
						return null;
					}   
					studentsMap = Utils.sortMapByKey(tempMap);
	                return studentsMap;
	            }  
	        });  
	        return result;
		}else{
			Map<String,Student> result = redisTemplate.execute(new RedisCallback<Map<String,Student>>() {  
	            public Map<String,Student> doInRedis(RedisConnection redisConnection) throws DataAccessException {  
	                RedisSerializer<String> serializer = redisTemplate.getStringSerializer();
	                byte[] key = serializer.serialize("studentClassId:"+classId);  
	                Map<String,Student> studentsMap = new HashMap<String,Student>();
	                Map<String, Student> tempMap = new LinkedHashMap<String, Student>();
					try {
						Map<byte[], byte[]> values = redisConnection.hGetAll(key);
						for (Entry<byte[], byte[]> group : values.entrySet()) {
							String ekey = serializer.deserialize(group.getKey());
							String evaluestr = serializer.deserialize(group.getValue());
							if(StringUtils.isNotEmpty(evaluestr)){
								Student evalue = util.getPojoFromJSON(evaluestr, Student.class);
								//为学生添加班级、年级、校区等信息
								//evalue = studentHandler(evalue);
								tempMap.put(ekey, evalue);
							}
						}					
					} catch (Exception e) {
						logger.error("=====getStudentsByClassId error:"+e.getMessage());
						return null;
					}   
					studentsMap = Utils.sortMapByKey(tempMap);
	                return studentsMap;
	            }  
	        });  
	        return result;
		}
	}

	@Override
	public Map<String,Student> getStudentsByGradeId(final Integer gradeId,final RedisConnection connection) {
		if(null!=connection){
			Map<String,Student> result = redisTemplate.execute(new RedisCallback<Map<String,Student>>() {  
				public Map<String,Student> doInRedis(RedisConnection redisConnection) throws DataAccessException {  
					RedisSerializer<String> serializer = redisTemplate.getStringSerializer();
					byte[] key = serializer.serialize("studentGradeId:"+gradeId);  
					Map<String,Student> studentsMap = new HashMap<String,Student>();
					Map<String, Student> tempMap = new LinkedHashMap<String, Student>();
					try {
						Map<byte[], byte[]> values = connection.hGetAll(key);
						for (Entry<byte[], byte[]> group : values.entrySet()) {
							String ekey = serializer.deserialize(group.getKey());
							String evaluestr = serializer.deserialize(group.getValue());
							if(StringUtils.isNotEmpty(evaluestr)){
								Student evalue = util.getPojoFromJSON(evaluestr, Student.class);
								//为学生添加班级、年级、校区等信息
								//evalue = studentHandler(evalue);
								tempMap.put(ekey, evalue);
							}
						}					
					} catch (Exception e) {
						logger.error("=====getStudentsByGradeId error:"+e.getMessage());
						return null;
					}   
					studentsMap = Utils.sortMapByKey(tempMap);
					return studentsMap;
				}  
			});  
			return result;
		}else{
			Map<String,Student> result = redisTemplate.execute(new RedisCallback<Map<String,Student>>() {  
	            public Map<String,Student> doInRedis(RedisConnection redisConnection) throws DataAccessException {  
	                RedisSerializer<String> serializer = redisTemplate.getStringSerializer();
	                byte[] key = serializer.serialize("studentGradeId:"+gradeId);  
	                Map<String,Student> studentsMap = new HashMap<String,Student>();
	                Map<String, Student> tempMap = new LinkedHashMap<String, Student>();
					try {
						Map<byte[], byte[]> values = redisConnection.hGetAll(key);
						for (Entry<byte[], byte[]> group : values.entrySet()) {
							String ekey = serializer.deserialize(group.getKey());
							String evaluestr = serializer.deserialize(group.getValue());
							if(StringUtils.isNotEmpty(evaluestr)){
								Student evalue = util.getPojoFromJSON(evaluestr, Student.class);
								//为学生添加班级、年级、校区等信息
								//evalue = studentHandler(evalue);
								tempMap.put(ekey, evalue);
							}
						}					
					} catch (Exception e) {
						logger.error("=====getStudentsByGradeId error:"+e.getMessage());
						return null;
					}   
					studentsMap = Utils.sortMapByKey(tempMap);
	                return studentsMap;
	            }  
	        });  
	        return result;
		}
	}
	
	private Student studentHandler(Student student){
		//因为下面查询key 不存在 所以注释掉
//		List<Classes> classList = redisClassesDao.getClassesByStudId(student.getStud_id());
//		for (Classes cla : classList) {
//			Grade grade = redisGradeDao.getGradeByClassId(cla.getClas_id());
//			cla.setGrade(grade);
//			Campus campus = redisCampusDao.getById(cla.getCam_id());
//			cla.setCampus(campus);
//		}
//		student.setClassList(classList);
		return student;
	}

	@Override
	public Student getSimpleInfoByStudentId(final Integer studentId,final RedisConnection connection) {
		if(null!=connection){
			Student result = redisTemplate.execute(new RedisCallback<Student>() {  
				public Student doInRedis(RedisConnection redisConnection) throws DataAccessException {  
					RedisSerializer<String> serializer = redisTemplate.getStringSerializer();
					Student student = new Student();
					try {
						byte[] key = serializer.serialize("studentId:"+studentId);
						byte[] value = connection.get(key);
						String evalue = serializer.deserialize(value); 
						if(StringUtils.isNotEmpty(evalue))
							student = util.getPojoFromJSON(evalue, Student.class);
					} catch (Exception e) {
						logger.error("=====getGroupById error:"+e.getMessage());
						return null;
					}                
					return student;
				}  
			});  
			return result;
		}else{
			Student result = redisTemplate.execute(new RedisCallback<Student>() {  
				public Student doInRedis(RedisConnection redisConnection) throws DataAccessException {  
					RedisSerializer<String> serializer = redisTemplate.getStringSerializer();
					Student student = new Student();
					try {
						byte[] key = serializer.serialize("studentId:"+studentId);
						byte[] value = redisConnection.get(key);
						String evalue = serializer.deserialize(value); 
						if(StringUtils.isNotEmpty(evalue))
							student = util.getPojoFromJSON(evalue, Student.class);
					} catch (Exception e) {
						logger.error("=====getGroupById error:"+e.getMessage());
						return null;
					}                
					return student;
				}  
			});  
			return result;
		}
	}
	
	
	
	@Override
	public Map<String,Student> getStudentsByOrgIdWX(final Integer orgId) {
		Map<String,Student> result = redisTemplate.execute(new RedisCallback<Map<String,Student>>() {  
            public Map<String,Student> doInRedis(RedisConnection connection) throws DataAccessException {  
                RedisSerializer<String> serializer = redisTemplate.getStringSerializer();
                byte[] key = serializer.serialize("studentOrgId:"+orgId);  
                Map<String,Student> studentsMap = new HashMap<String,Student>();
                Map<String, Student> tempMap = new LinkedHashMap<String, Student>();
				try {
					Map<byte[], byte[]> values = connection.hGetAll(key);
					for (Entry<byte[], byte[]> group : values.entrySet()) {
						String ekey = serializer.deserialize(group.getKey());
						String evaluestr = serializer.deserialize(group.getValue());
						if(StringUtils.isNotEmpty(evaluestr)){
							Student evalue = util.getPojoFromJSON(evaluestr, Student.class);
							//为学生添加班级、年级、校区等信息
							evalue = studentHandler(evalue);
							tempMap.put(ekey, evalue);
						}
					}					
				} catch (Exception e) {
					logger.error("=====getStudentsByOrgId error:"+e.getMessage());
					return null;
				}   
				studentsMap = Utils.sortMapByKey(tempMap);
                return studentsMap;
            }  
        });  
        return result;
	}

	@Override
	public Student getByUserId(final String studentId) {
		// TODO Auto-generated method stub
		Student result = redisTemplate.execute(new RedisCallback<Student>() {  
			public Student doInRedis(RedisConnection connection) throws DataAccessException {  
				RedisSerializer<String> serializer = redisTemplate.getStringSerializer();
				Student student = new Student();
				try {
					byte[] key = serializer.serialize("studentUserId:"+studentId);
					byte[] value = connection.get(key);
					String evalue = serializer.deserialize(value); 
					if(StringUtils.isNotEmpty(evalue))
						student = util.getPojoFromJSON(evalue, Student.class);
				} catch (Exception e) {
					logger.error("=====getGroupById error:"+e.getMessage());
					return null;
				}                
				return student;
			}  
		});  
		return result;
	}
	

}
