package cn.edugate.esb.redis.dao.imp;

import java.util.ArrayList;
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

import cn.edugate.esb.entity.Clas2Student;
import cn.edugate.esb.entity.Classes;
import cn.edugate.esb.entity.OrgUser;
import cn.edugate.esb.entity.Parent;
import cn.edugate.esb.entity.Student;
import cn.edugate.esb.entity.StudentParent;
import cn.edugate.esb.redis.RedisGeneratorDao;
import cn.edugate.esb.redis.dao.RedisClassStudentDao;
import cn.edugate.esb.redis.dao.RedisClassesDao;
import cn.edugate.esb.redis.dao.RedisOrgUserDao;
import cn.edugate.esb.redis.dao.RedisParentDao;
import cn.edugate.esb.redis.dao.RedisStudentDao;
import cn.edugate.esb.redis.dao.RedisStudentParentDao;
import cn.edugate.esb.util.Constant;
import cn.edugate.esb.util.Util;
import cn.edugate.esb.util.Utils;

@Repository
public class RedisStudentParentDaoImp extends RedisGeneratorDao<String, String> implements RedisStudentParentDao {
	
	private Util util;
	@Autowired
	public void setUtil(Util util) {
		this.util = util;
	}
	@Autowired
	RedisStudentDao redisStudentDao;
	@Autowired
	RedisClassStudentDao redisClassStudentDao;
	@Autowired
	RedisClassesDao redisClassesDao;
	@Autowired
	RedisParentDao redisParentDao;
	@Autowired
	RedisOrgUserDao redisOrgUserDao;
	@Override
	public boolean add(final StudentParent sp) {
		// TODO Auto-generated method stub
		boolean result = redisTemplate.execute(new RedisCallback<Boolean>() {  
            public Boolean doInRedis(RedisConnection connection)  
                    throws DataAccessException {  
                RedisSerializer<String> serializer = redisTemplate.getStringSerializer();              	
                if(!sp.getIs_del()){
	            	String json="";
	            	json = util.getJSONFromPOJO(sp);
	            	byte[] value = serializer.serialize(json); 
	        		byte[] key1  = serializer.serialize("parent2student:"+sp.getParent_id());  
	        		byte[] field1  = serializer.serialize(sp.getStud_id().toString()); 
	            	connection.hSet(key1,field1, value);  
	            	
	        		byte[] key2  = serializer.serialize("student2parent:"+sp.getStud_id()); 
	        		byte[] field2  = serializer.serialize(sp.getParent_id().toString());
	            	connection.hSet(key2,field2, value); 
	            	
	        		byte[] key3  = serializer.serialize("studentparent_id:"+sp.getStud2parent_id());  
	            	connection.set(key3, value);

	            	byte[] key4  = serializer.serialize("studentparent_orgid:"+sp.getOrg_id());  
	            	byte[] field4  = serializer.serialize(sp.getStud2parent_id().toString());
	            	connection.hSet(key4,field4, value); 
                }      
                return true;  
            }
        }, false, true);  
        return result;  
	}
	
	@Override
	public boolean add(final List<StudentParent> sps) {
		// TODO Auto-generated method stub
		boolean result = redisTemplate.execute(new RedisCallback<Boolean>() {  
            public Boolean doInRedis(RedisConnection connection)  
                    throws DataAccessException {  
                RedisSerializer<String> serializer = redisTemplate.getStringSerializer();              	
                for (StudentParent sp : sps) { 
                	if(!sp.getIs_del()){
    	            	String json="";
    	            	json = util.getJSONFromPOJO(sp);
    	            	byte[] value = serializer.serialize(json); 
    	        		byte[] key1  = serializer.serialize("parent2student:"+sp.getParent_id());  
    	        		byte[] field1  = serializer.serialize(sp.getStud_id().toString()); 
    	            	connection.hSet(key1,field1, value);  
    	            	
    	        		byte[] key2  = serializer.serialize("student2parent:"+sp.getStud_id()); 
    	        		byte[] field2  = serializer.serialize(sp.getParent_id().toString());
    	            	connection.hSet(key2,field2, value); 
    	            	
    	        		byte[] key3  = serializer.serialize("studentparent_id:"+sp.getStud2parent_id());  
    	            	connection.set(key3, value);
    	            	
    	            	byte[] key4  = serializer.serialize("studentparent_orgid:"+sp.getOrg_id());  
    	            	byte[] field4  = serializer.serialize(sp.getStud2parent_id().toString());
    	            	connection.hSet(key4,field4, value); 
                    }  
                }                  
                return true;  
            }
        }, false, true);  
        return result;  
	}	

	@Override
	public boolean delete(final StudentParent sp) {
		// TODO Auto-generated method stub
		boolean result = redisTemplate.execute(new RedisCallback<Boolean>() {  
            public Boolean doInRedis(RedisConnection connection)  
                    throws DataAccessException {  
                RedisSerializer<String> serializer = redisTemplate.getStringSerializer();              	
                 
        		byte[] key1  = serializer.serialize("parent2student:"+sp.getParent_id());  
        		byte[] field1 = serializer.serialize(sp.getStud_id().toString());
            	connection.hDel(key1, field1);
            	
            	byte[] key2  = serializer.serialize("student2parent:"+sp.getStud_id());  
            	byte[] field2 = serializer.serialize(sp.getParent_id().toString());
            	connection.hDel(key2, field2);

            	byte[] key4  = serializer.serialize("student2parent_orgid:"+sp.getOrg_id());  
            	byte[] field4 = serializer.serialize(sp.getStud2parent_id().toString());
            	connection.hDel(key4, field4);

            	byte[] key3  = serializer.serialize("studentparent_id:"+sp.getStud2parent_id());  
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
					deleteRedis(connection, serializer, "parent2student:"+"*");
					deleteRedis(connection, serializer, "student2parent:"+"*");
					deleteRedis(connection, serializer, "studentparent_id:"+"*");
					deleteRedis(connection, serializer, "studentparent_orgid:"+"*");
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
	public StudentParent getByStud2parent_id(final String stud2parent_id) {
		// TODO Auto-generated method stub
		StudentParent result = redisTemplate.execute(new RedisCallback<StudentParent>() {  
			public StudentParent doInRedis(RedisConnection connection) throws DataAccessException {  
				RedisSerializer<String> serializer = redisTemplate.getStringSerializer();
				StudentParent sp = new StudentParent();
				try {
					byte[] key = serializer.serialize("studentparent_id:"+stud2parent_id);
					byte[] value = connection.get(key);
					String evalue = serializer.deserialize(value); 
					if(StringUtils.isNotEmpty(evalue))
						sp = util.getPojoFromJSON(evalue, StudentParent.class);
				} catch (Exception e) {
					return null;
				}                
				return sp;
			}  
		});  
		return result;
	}

	@Override
	public Map<String,StudentParent> getByStudentId(final String stud_id) {
		// TODO Auto-generated method stub
		Map<String,StudentParent> result = redisTemplate.execute(new RedisCallback<Map<String,StudentParent>>() {  
            public Map<String,StudentParent> doInRedis(RedisConnection connection)  
                    throws DataAccessException {  
                RedisSerializer<String> serializer = redisTemplate.getStringSerializer();
                byte[] key = serializer.serialize("student2parent:"+stud_id);  
                Map<String,StudentParent> teachers = new HashMap<String,StudentParent>();
                Map<String, StudentParent> tempMap = new LinkedHashMap<String, StudentParent>();
				try {
					Map<byte[], byte[]> values = connection.hGetAll(key);
					for (Entry<byte[], byte[]> teacher : values.entrySet()) {
						String ekey = serializer.deserialize(teacher.getKey());
						String evaluestr = serializer.deserialize(teacher.getValue());
						if(StringUtils.isNotEmpty(evaluestr)){
							StudentParent evalue = util.getPojoFromJSON(evaluestr, StudentParent.class);
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
	public List<Student> getStudsByPId(final String parent_id) {
		// TODO Auto-generated method stub
		List<Student> result = redisTemplate.execute(new RedisCallback<List<Student>>() {  
            public List<Student> doInRedis(RedisConnection connection)  
                    throws DataAccessException {  
                RedisSerializer<String> serializer = redisTemplate.getStringSerializer();
                byte[] key = serializer.serialize("parent2student:"+parent_id);  
                List<Student> list = new ArrayList<Student>();
				try {
					Map<byte[], byte[]> values = connection.hGetAll(key);
					for (Entry<byte[], byte[]> teacher : values.entrySet()) {
						String ekey = serializer.deserialize(teacher.getKey());
						String evaluestr = serializer.deserialize(teacher.getValue());
						if(StringUtils.isNotEmpty(evaluestr)){
							StudentParent evalue = util.getPojoFromJSON(evaluestr, StudentParent.class);
							Student student = redisStudentDao.getSimpleInfoByStudentId(evalue.getStud_id(), null);
							student.setRelation(evalue.getRelation());
							student.setRelation_name(Constant.getRelation(evalue.getRelation()));
							
							Map<String, Clas2Student> csMap = redisClassStudentDao.getByStud_id(student.getStud_id().toString());
							List<Classes> classList = new ArrayList<Classes>();
							for (Map.Entry<String, Clas2Student> entry : csMap.entrySet()) {  
								Clas2Student cs = entry.getValue();  
								
								Classes clas = redisClassesDao.getClassesById(cs.getClas_id().toString(), null);
								classList.add(clas); 
							  
							} 
							student.setClassList(classList);
							list.add(student);
						}
					}					
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
	public List<Parent> getParentsBySId(final String stud_id) {
		// TODO Auto-generated method stub
		List<Parent> result = redisTemplate.execute(new RedisCallback<List<Parent>>() {  
            public List<Parent> doInRedis(RedisConnection connection)  
                    throws DataAccessException {  
                RedisSerializer<String> serializer = redisTemplate.getStringSerializer();
                byte[] key = serializer.serialize("student2parent:"+stud_id);  
                List<Parent> list = new ArrayList<Parent>();
                Map<String, StudentParent> tempMap = new LinkedHashMap<String, StudentParent>();
				try {
					Map<byte[], byte[]> values = connection.hGetAll(key);
					for (Entry<byte[], byte[]> teacher : values.entrySet()) {
						String ekey = serializer.deserialize(teacher.getKey());
						String evaluestr = serializer.deserialize(teacher.getValue());
						if(StringUtils.isNotEmpty(evaluestr)){
							StudentParent evalue = util.getPojoFromJSON(evaluestr, StudentParent.class);
							Parent parent = redisParentDao.getByParentId(evalue.getParent_id().toString());
							parent.setRelation(evalue.getRelation());
							OrgUser orgUser = redisOrgUserDao.getUserById(parent.getUser_id().toString());
							parent.setMobile(orgUser.getUser_mobile());
							parent.setRelation_name(Constant.getRelation(evalue.getRelation()));
							parent.setOpenid(orgUser.getOpenid());
							list.add(parent);
						}
					}					
				} catch (Exception e) {
					// TODO Auto-generated catch block
					return null;
				}   
				//tempMap = Utils.sortMapByKey(teachers);
                return list;
            }  
        });  
        return result;
	}
	
	
	
	
	
	@Override
	public List<StudentParent> getStudParentsByPId(final String parent_id) {
		// TODO Auto-generated method stub
		List<StudentParent> result = redisTemplate.execute(new RedisCallback<List<StudentParent>>() {  
            public List<StudentParent> doInRedis(RedisConnection connection)  
                    throws DataAccessException {  
                RedisSerializer<String> serializer = redisTemplate.getStringSerializer();
                byte[] key = serializer.serialize("parent2student:"+parent_id);  
                List<StudentParent> list = new ArrayList<StudentParent>();
				try {
					Map<byte[], byte[]> values = connection.hGetAll(key);
					for (Entry<byte[], byte[]> teacher : values.entrySet()) {
						String ekey = serializer.deserialize(teacher.getKey());
						String evaluestr = serializer.deserialize(teacher.getValue());
						if(StringUtils.isNotEmpty(evaluestr)){
							StudentParent evalue = util.getPojoFromJSON(evaluestr, StudentParent.class);
							
							list.add(evalue);
						}
					}					
				} catch (Exception e) {
					// TODO Auto-generated catch block
					return null;
				}
                return list;
            }  
        });  
        return result;
	}
	/**
	 * 刷新缓存  用到此方法
	 * 
	 * **/
	@Override
	public List<StudentParent> getStudParentByOrgId(final String org_id) {
		List<StudentParent> result = redisTemplate.execute(new RedisCallback<List<StudentParent>>() {  
            public List<StudentParent> doInRedis(RedisConnection connection)  
                    throws DataAccessException {  
                RedisSerializer<String> serializer = redisTemplate.getStringSerializer();
                byte[] key = serializer.serialize("studentparent_orgid:"+org_id);  
                List<StudentParent> list = new ArrayList<StudentParent>();
				try {
					Map<byte[], byte[]> values = connection.hGetAll(key);
					for (Entry<byte[], byte[]> sp : values.entrySet()) {
						String evaluestr = serializer.deserialize(sp.getValue());
						if(StringUtils.isNotEmpty(evaluestr)){
							StudentParent evalue = util.getPojoFromJSON(evaluestr, StudentParent.class);
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
