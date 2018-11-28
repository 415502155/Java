package cn.edugate.esb.redis.dao.imp;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.stereotype.Repository;
import cn.edugate.esb.comparator.ClasToSortComparator;
import cn.edugate.esb.entity.Classes;
import cn.edugate.esb.entity.Course;
import cn.edugate.esb.entity.Grade;
import cn.edugate.esb.entity.Teacher;
import cn.edugate.esb.redis.RedisGeneratorDao;
import cn.edugate.esb.redis.dao.RedisClassStudentDao;
import cn.edugate.esb.redis.dao.RedisClassesDao;
import cn.edugate.esb.redis.dao.RedisGradeDao;
import cn.edugate.esb.redis.dao.RedisTeacherDao;
import cn.edugate.esb.redis.dao.RedisTechRangeDao;
import cn.edugate.esb.service.StudentService;
import cn.edugate.esb.util.Constant;
import cn.edugate.esb.util.Util;

/**
 * Title:RedisGradeDaoImpl
 * Description:
 * Company:SJWY
 * @author:Liuy
 * @Date:2017年5月23日下午6:08:50
 */
@Repository
public class RedisGradeDaoImpl extends RedisGeneratorDao<String, String> implements RedisGradeDao {

	private static Logger logger = Logger.getLogger(RedisGradeDaoImpl.class);
	
	private Util util;
	@Autowired
	public void setUtil(Util util) {
		this.util = util;
	}

	@Autowired
	private RedisTeacherDao redisTeacherDao;
	@Autowired
	private RedisTechRangeDao redisTechRangeDao;
	@Autowired
	private RedisClassesDao redisClassesDao;
	@Autowired
	private StudentService studentService;
	@Autowired
	private RedisClassStudentDao redisClassStudentDao;
	
	/**
	 * 刷新缓存  用到此方法
	 * 
	 * **/
	@Override
	public  List<Grade> getGrades(final Integer orgId) {
		 List<Grade> result = redisTemplate.execute(new RedisCallback< List<Grade>>() {  
            public  List<Grade> doInRedis(RedisConnection connection) throws DataAccessException { 
            	RedisSerializer<String> serializer = redisTemplate.getStringSerializer();
                byte[] key = serializer.serialize("gradeOrg:"+orgId);  
                List<Grade> list = new ArrayList<Grade>();
 				try {
 					Map<byte[], byte[]> values = connection.hGetAll(key);
 					for (Entry<byte[], byte[]> org : values.entrySet()) {
 						String evaluestr = serializer.deserialize(org.getValue());
 						Grade evalue = util.getPojoFromJSON(evaluestr, Grade.class);
 						int gewei = evalue.getGrade_number()%10;
 						if(gewei==Constant.GRADE_NUMBER_YINGJIE || gewei==Constant.GRADE_NUMBER_WANGJIE)//非 应届和往届年级
 							continue;
 						else
 							list.add(evalue);
 					}					
 				} catch (Exception e) {
 					logger.error("=====getGrades error:"+e.getMessage());
 					return null;
 				}   
                return list;
            }  
        });  
        return result;
	}
	
	@Override
	public boolean addGrade(final Grade grade) {
		boolean result = redisTemplate.execute(new RedisCallback<Boolean>() {  
            public Boolean doInRedis(RedisConnection connection) throws DataAccessException {  
                RedisSerializer<String> serializer = redisTemplate.getStringSerializer();  
                if(grade.getIs_del()==Constant.IS_DEL_NO){
	            	String json="";
	            	json = util.getJSONFromPOJO(grade);
	            	byte[] value = serializer.serialize(json); 
	            	byte[] field  = serializer.serialize(grade.getGrade_id().toString()); 
	            	
	        		byte[] key1  = serializer.serialize("grade:"+grade.getGrade_id());  
	        		connection.set(key1, value);    
            		byte[] key3  = serializer.serialize("gradeOrg:"+grade.getOrg_id());  
            		connection.hSet(key3,field, value);
            		byte[] key4  = serializer.serialize("gradeType:"+grade.getGrade_type());  
            		connection.hSet(key4,field, value);
            		byte[] key7  = serializer.serialize("gradeOrg:"+grade.getOrg_id()+":gradeType:"+grade.getGrade_type());  
            		connection.hSet(key7,field, value);
            		byte[] key9  = serializer.serialize("gradeAll");  
	        		connection.hSet(key9,field, value);  
                }        
                return true;  
            }
        }, false, true);  
        return result;  
	}
	
	@Override
	public boolean addGrades(final List<Grade> grades) {
		boolean result = redisTemplate.execute(new RedisCallback<Boolean>() {  
            public Boolean doInRedis(RedisConnection connection) throws DataAccessException {  
                RedisSerializer<String> serializer = redisTemplate.getStringSerializer();  
                for (Grade grade : grades) {
                	//if(grade.getIs_del()==Constant.IS_DEL_NO){
		            	String json="";
		            	json = util.getJSONFromPOJO(grade);
		            	byte[] value = serializer.serialize(json); 
		            	byte[] field  = serializer.serialize(grade.getGrade_id().toString()); 
		            	//年级
		        		byte[] key1  = serializer.serialize("grade:"+grade.getGrade_id());  
		        		connection.set(key1, value); 
		        		//机构下年级
	            		byte[] key3  = serializer.serialize("gradeOrg:"+grade.getOrg_id());  
	            		connection.hSet(key3,field, value);
	            		//年级类型下 年级
	            		byte[] key4  = serializer.serialize("gradeType:"+grade.getGrade_type());  
	            		connection.hSet(key4,field, value);
	            		//机构+年级类型下  年级
	            		byte[] key7  = serializer.serialize("gradeOrg:"+grade.getOrg_id()+":gradeType:"+grade.getGrade_type());  
	            		connection.hSet(key7,field, value);
	            		byte[] key9  = serializer.serialize("gradeAll");  
		        		connection.hSet(key9,field, value);  
                	//}
				}
                return true;  
            }
        }, false, true);  
        return result;  
	}

	@Override
	public boolean deleteGrade(final Grade grade) {
		boolean result = redisTemplate.execute(new RedisCallback<Boolean>() {  
            public Boolean doInRedis(RedisConnection connection) throws DataAccessException {  
                RedisSerializer<String> serializer = redisTemplate.getStringSerializer();
                byte[] field = serializer.serialize(grade.getGrade_id().toString()); 
                
        		byte[] key1  = serializer.serialize("grade:"+grade.getGrade_id());  
        		connection.del(key1);    
        		byte[] key3  = serializer.serialize("gradeOrg:"+grade.getOrg_id());  
        		connection.hDel(key3,field);
        		byte[] key4  = serializer.serialize("gradeType:"+grade.getGrade_type());  
        		connection.hDel(key4,field);
        		byte[] key7  = serializer.serialize("gradeOrg:"+grade.getOrg_id()+":gradeType:"+grade.getGrade_type());  
        		connection.hDel(key7,field);
        		byte[] key9  = serializer.serialize("gradeAll");  
        		connection.hDel(key9,field);  
                return true;  
            }
        }, false, true);  
        return result;
	}
	
	@Override
	public boolean deleteAllGrades() {
		boolean result = redisTemplate.execute(new RedisCallback<Boolean>() {  
            public Boolean doInRedis(RedisConnection connection) throws DataAccessException {  
                RedisSerializer<String> serializer = redisTemplate.getStringSerializer();
				try {
					deleteRedis(connection,serializer,"grade:*");
					deleteRedis(connection,serializer,"gradeUser:*");
					deleteRedis(connection,serializer,"gradeOrg:*");
					deleteRedis(connection,serializer,"gradeType:*");
					deleteRedis(connection,serializer,"gradeUser:*:gradeOrg:*");
					deleteRedis(connection,serializer,"gradeUser:*:gradeType:*");
					deleteRedis(connection,serializer,"gradeOrg:*:gradeType:*");
					deleteRedis(connection,serializer,"gradeUser:*:gradeOrg:*:gradeType:*");
					deleteRedis(connection,serializer,"gradeAll");
				} catch (Exception e) {
					logger.error("=====deleteAllGrades error:"+e.getMessage());
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
	public Grade getGradeById(final Integer gradeId,final RedisConnection connection) {
		if(null!=connection){
			Grade result = redisTemplate.execute(new RedisCallback<Grade>() {  
	            public Grade doInRedis(RedisConnection redisConnection) throws DataAccessException {  
	                RedisSerializer<String> serializer = redisTemplate.getStringSerializer();
	                Grade grade = new Grade();
					try {
						byte[] key = serializer.serialize("grade:"+gradeId);
						byte[] value = connection.get(key);
		                String evalue = serializer.deserialize(value); 
		                if(StringUtils.isNotEmpty(evalue))
		                	grade = util.getPojoFromJSON(evalue, Grade.class);
					} catch (Exception e) {
						logger.error("=====getGradeById error:"+e.getMessage());
						return null;
					}                
	                return grade;
	            }  
	        });  
	        return result;
		}else{
			Grade result = redisTemplate.execute(new RedisCallback<Grade>() {  
	            public Grade doInRedis(RedisConnection redisConnection) throws DataAccessException {  
	                RedisSerializer<String> serializer = redisTemplate.getStringSerializer();
	                Grade grade = new Grade();
					try {
						byte[] key = serializer.serialize("grade:"+gradeId);
						byte[] value = redisConnection.get(key);
		                String evalue = serializer.deserialize(value); 
		                if(StringUtils.isNotEmpty(evalue))
		                	grade = util.getPojoFromJSON(evalue, Grade.class);
					} catch (Exception e) {
						logger.error("=====getGradeById error:"+e.getMessage());
						return null;
					}                
	                return grade;
	            }  
	        });  
	        return result;
		}
		
	}

	@Override
	public Grade getGradeByClassId(final Integer classId) {
		Grade result = redisTemplate.execute(new RedisCallback<Grade>() {  
            public Grade doInRedis(RedisConnection connection) throws DataAccessException {  
                RedisSerializer<String> serializer = redisTemplate.getStringSerializer();
                Grade grade = new Grade();
				try {
					byte[] key = serializer.serialize("gradeOfClassId:"+classId);
					byte[] value = connection.get(key);
	                String evalue = serializer.deserialize(value); 
	                if(StringUtils.isNotEmpty(evalue))
	                	grade = util.getPojoFromJSON(evalue, Grade.class);
				} catch (Exception e) {
					logger.error("=====getGradeById error:"+e.getMessage());
					return null;
				}                
                return grade;
            }  
        });  
        return result;
	}

	@Override
	public List<Grade> getGradesWithAllInfo(final Integer orgId) {
		List<Grade> result = redisTemplate.execute(new RedisCallback< List<Grade>>() {  
            @SuppressWarnings({ "unchecked", "rawtypes" })
			public  List<Grade> doInRedis(RedisConnection connection) throws DataAccessException { 
            	RedisSerializer<String> serializer = redisTemplate.getStringSerializer();
                byte[] key = serializer.serialize("gradeOrg:"+orgId);  
                List<Grade> list = new ArrayList<Grade>();
 				try {
 					Map<byte[], byte[]> values = connection.hGetAll(key);
 					for (Entry<byte[], byte[]> org : values.entrySet()) {
 						String evaluestr = serializer.deserialize(org.getValue());
 						Grade grade = util.getPojoFromJSON(evaluestr, Grade.class);
 						int gewei = grade.getGrade_number()%10;
 						if(gewei==Constant.GRADE_NUMBER_YINGJIE || gewei==Constant.GRADE_NUMBER_WANGJIE){//非 应届和往届年级
 						    continue;
 						}
 						else{
 							//获取年级相关信息
	 						//Map<String,Object> gradeMaster = redisTechRangeDao.getSkGradeTech(grade.getGrade_id().toString(),connection);
	 						Map<String,Object> gradeMaster = redisTechRangeDao.getGradeTechManager(grade.getGrade_id().toString(),null);
	 						
	 						grade.setGrade_master(new ArrayList(gradeMaster.values()));
	 						//获取班级相关信息
	// 						List<Classes> clasList = redisClassesDao.getClassesOfGrade(grade.getGrade_id(),null);
	 						byte[] cgkey = serializer.serialize("classesGrade:"+grade.getGrade_id());  
	 		                List<Classes> cglist = new ArrayList<Classes>();
							Map<byte[], byte[]> cgvalues = connection.hGetAll(cgkey);
							for (Entry<byte[], byte[]> cgorg : cgvalues.entrySet()) {
								String cgevaluestr = serializer.deserialize(cgorg.getValue());
								if(StringUtils.isNotEmpty(cgevaluestr)){
									Classes evalue = util.getPojoFromJSON(cgevaluestr, Classes.class);	
									if(evalue.getIs_graduated()==Constant.CLAS_IS_GRADUATED_NO){//非 毕业班级
		//								Map<String,Object> classMaster = redisTechRangeDao.getSkClasBZR(evalue.getClas_id().toString(),null);
										byte[] trkey = serializer.serialize("sk_clas_bzr:"+evalue.getClas_id().toString());
						                Map<String, Object> trmap = new TreeMap<String, Object>(new Comparator<String>(){
						                    public int compare(String o1,String o2){
						                        return  o2.compareTo(o1); //用正负表示大小值
						                    }
						                });				                
										Map<byte[], byte[]> trvalues = connection.hGetAll(trkey);
										for (Entry<byte[], byte[]> entrySet : trvalues.entrySet()) {
											String tech_id = serializer.deserialize(entrySet.getValue());
											Teacher teacher = redisTeacherDao.getByTechId(tech_id,null);
											if(teacher!=null)
												trmap.put(tech_id, teacher);
										}
			 							if(trmap!=null && trmap.size()>0)
			 								evalue.setMasters(new ArrayList(trmap.values()));
			 							
			 							
										//任课教师
		//								List<Map<String,Object>> classCourseTeacher = redisTechRangeDao.getSkClasTech(evalue.getClas_id().toString(),null);
			 							byte[] cctkey = serializer.serialize("sk_clas_tech:"+evalue.getClas_id().toString());
			 			                List<Map<String, Object>> tempMap = new ArrayList<Map<String, Object>>();	 
		 								Map<byte[], byte[]> cctvalues = connection.hGetAll(cctkey);
		 								for (Entry<byte[], byte[]> entrySet : cctvalues.entrySet()) {
		 									Map<String, Object> objMap = new TreeMap<String, Object>(new Comparator<String>(){
		 					                    public int compare(String o1,String o2){
		 					                        return  o2.compareTo(o1); //用正负表示大小值
		 					                    }
		 					                });
		 									String cctevaluestr = serializer.deserialize(entrySet.getValue());
		 									if(StringUtils.isNotEmpty(cctevaluestr)){
		 										Map<String,Object> cctevalue = util.getPojoFromJSON(cctevaluestr, Map.class);
		 										objMap.put("history", cctevalue.get("history"));
		 										objMap.put("cour_id", cctevalue.get("cour_id"));
		// 										Course course = redisCourseDao.getCourseById(evalue.get("cour_id").toString(),null);
		 										Course course = null;
		 										byte[] coursekey = serializer.serialize("course:"+cctevalue.get("cour_id").toString());
		 										byte[] coursevalue = connection.get(coursekey);
		 						                String coursevaluestr = serializer.deserialize(coursevalue); 
		 						                if(StringUtils.isNotEmpty(coursevaluestr)){
		 						                	course = util.getPojoFromJSON(coursevaluestr, Course.class);
		 						                	objMap.put("cour_name", course.getCour_name());
		 						                }
		 										
		 										objMap.put("tech_id", cctevalue.get("tech_id"));
		 										Teacher teacher = redisTeacherDao.getByTechId(cctevalue.get("tech_id").toString(),null);
		 										if(teacher!=null)
		 											objMap.put("tech_name",teacher.getTech_name());
		 										tempMap.add(objMap);
		 									}
		 								}	 							
										evalue.setClass_master(tempMap);
										//Integer studentNum = studentService.getStudentsNumber(cla.getClas_id());
										Integer studentNum = redisClassStudentDao.getStudCountByCid(evalue.getClas_id()+"");
										evalue.setStudent_num(studentNum);
										
										cglist.add(evalue);
									}
								}
							} 
							ClasToSortComparator mc = new ClasToSortComparator();  
					        Collections.sort(cglist,mc);
	
	 						grade.setClasses(cglist);
	 						list.add(grade);
 						}
 					}					
 				} catch (Exception e) {
 					logger.error("=====getGrades error:"+e.getMessage());
 					e.printStackTrace();
 					return null;
 				}   
                return list;
            }  
        });  
        return result;
	}
	
	
	
	@Override
	public  Map<String,Grade> getGrades(final Map<String,Object> gradeIds) {
		Map<String,Grade> result = redisTemplate.execute(new RedisCallback<Map<String,Grade>>() {  
            public  Map<String,Grade> doInRedis(RedisConnection connection) throws DataAccessException { 
            	RedisSerializer<String> serializer = redisTemplate.getStringSerializer();
            	Map<String, Grade> map = new HashMap<String, Grade>();
 				try {
 					for(Map.Entry<String, Object> entry : gradeIds.entrySet()){
	 					byte[] key = serializer.serialize("grade:"+entry.getKey().toString());
						byte[] value = connection.get(key);
		                String evalue = serializer.deserialize(value); 
		                if(StringUtils.isNotEmpty(evalue)){
		                	Grade grade = util.getPojoFromJSON(evalue, Grade.class);
		                	map.put(entry.getKey().toString(), grade);
		                }
 					}
 				} catch (Exception e) {
 					logger.error("=====getGrades error:"+e.getMessage());
 					return null;
 				}   
                return map;
            }  
        });  
        return result;
	}

}
