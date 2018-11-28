package cn.edugate.esb.redis.dao.imp;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.lang.StringUtils;
import org.codehaus.jackson.map.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.stereotype.Repository;

import cn.edugate.esb.comparator.TechToSortComparator;
import cn.edugate.esb.entity.Department;
import cn.edugate.esb.entity.OrgUser;
import cn.edugate.esb.entity.Teacher;
import cn.edugate.esb.redis.RedisGeneratorDao;
import cn.edugate.esb.redis.dao.RedisDepartmentDao;
import cn.edugate.esb.redis.dao.RedisOrgUserDao;
import cn.edugate.esb.redis.dao.RedisTeacherDao;
import cn.edugate.esb.redis.dao.RedisTechRangeDao;
import cn.edugate.esb.util.Constant;
import cn.edugate.esb.util.LSHelper;
import cn.edugate.esb.util.Util;

@Repository
public class RedisTeacherDaoImpl  extends RedisGeneratorDao<String, String> implements RedisTeacherDao{
	
	private static final Logger logger = LoggerFactory.getLogger(RedisTeacherDaoImpl.class);

	@Autowired
	private Util util;
	@Autowired
	private RedisTechRangeDao redisTechRangeDao;
	@Autowired
	private RedisDepartmentDao redisDepartmentDao;
	@Autowired
	private RedisOrgUserDao redisOrgUserDao;
	@Override
	public boolean add(final Teacher tr) {
		// TODO Auto-generated method stub
		boolean result = redisTemplate.execute(new RedisCallback<Boolean>() {  
            public Boolean doInRedis(RedisConnection connection)  
                    throws DataAccessException {  
                RedisSerializer<String> serializer = redisTemplate.getStringSerializer();            	
            	if(tr.getIs_del()==Constant.IS_DEL_NO){
	        		String json="";
	            	json = util.getJSONFromPOJO(tr);
	            	byte[] value = serializer.serialize(json);       		
	            	byte[] field  = serializer.serialize(tr.getTech_id().toString()); 
	        		byte[] key1  = serializer.serialize("techinfo:"+tr.getTech_id());                	  
	            	connection.set(key1, value);  
	            	byte[] key2  = serializer.serialize("techinfo_dep:"+tr.getDep_id());  
	        		connection.hSet(key2,field, value); 
	        		byte[] key3  = serializer.serialize("techinfo_org:"+tr.getOrg_id());  
	        		connection.hSet(key3,field, value); 
	        		byte[] key4  = serializer.serialize("techinfo_user:"+tr.getUser_id());                	  
	            	connection.set(key4, value);	            	
            	}        		
                return true;  
            }
        }, false, true);  
        return result; 
	}

	@Override
	public boolean add(final List<Teacher> trs) {
		// TODO Auto-generated method stub
		boolean result = redisTemplate.execute(new RedisCallback<Boolean>() {  
            public Boolean doInRedis(RedisConnection connection)  
                    throws DataAccessException {  
                RedisSerializer<String> serializer = redisTemplate.getStringSerializer();
            	 for (Teacher tr : trs) {  
            		 //if(tr.getIs_del()==Constant.IS_DEL_NO){
	            		String json="";
		            	json = util.getJSONFromPOJO(tr);
		            	
		            	byte[] value = serializer.serialize(json);       		
		            	byte[] field  = serializer.serialize(tr.getTech_id().toString()); 
		        		byte[] key1  = serializer.serialize("techinfo:"+tr.getTech_id());                	  
		            	connection.set(key1, value);  
		            	byte[] key2  = serializer.serialize("techinfo_dep:"+tr.getDep_id());  
		        		connection.hSet(key2,field, value); 
		        		byte[] key3  = serializer.serialize("techinfo_org:"+tr.getOrg_id());  
		        		connection.hSet(key3,field, value); 
		        		byte[] key4  = serializer.serialize("techinfo_user:"+tr.getUser_id());                	  
		            	connection.set(key4, value);  
		            	
            		 //}
            	 }
                return true;  
            }
        }, false, true);  
        return result;
	}

	@Override
	public boolean delete(final Teacher tr) {
		// TODO Auto-generated method stub
		boolean result = redisTemplate.execute(new RedisCallback<Boolean>() {  
            public Boolean doInRedis(RedisConnection connection)  
                    throws DataAccessException {  
                RedisSerializer<String> serializer = redisTemplate.getStringSerializer();
        		byte[] key  = serializer.serialize("techinfo:"+tr.getTech_id()); 
            	connection.del(key);
            	
            	byte[] key1  = serializer.serialize("techinfo_dep:"+tr.getDep_id());  
        		byte[] field1 = serializer.serialize(tr.getTech_id().toString()); 
        		connection.hDel(key1, field1);
        		byte[] key3  = serializer.serialize("techinfo_org:"+tr.getOrg_id());  
        		connection.hDel(key3,field1); 
        		
        		byte[] keyt  = serializer.serialize("techinfo_user:"+tr.getUser_id()); 
            	connection.del(keyt);  
            	           	
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
            	logger.info("==========delete==========={}","deleteAll");
                RedisSerializer<String> serializer = redisTemplate.getStringSerializer();
				try {					
					deleteRedis(connection,serializer,"techinfo:*");
					deleteRedis(connection,serializer,"techinfo_dep:*");
					deleteRedis(connection,serializer,"techinfo_org:*");
					deleteRedis(connection,serializer,"techinfo_user:*");
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					return false;
				}                
                return true;
            }  
        });  
        return result;  
	}

	@Override
	public Teacher getByTechId(final String tech_id,final RedisConnection connection) {
		if(null!=connection){
			Teacher result = redisTemplate.execute(new RedisCallback<Teacher>() {  
				public Teacher doInRedis(RedisConnection redisConnection) throws DataAccessException {  
					RedisSerializer<String> serializer = redisTemplate.getStringSerializer();
					byte[] key = serializer.serialize("techinfo:"+tech_id);  
					Teacher org=null;
					try {
						byte[] value = connection.get(key); 
						String evalue = serializer.deserialize(value); 
						if(StringUtils.isNotEmpty(evalue)){
							org = util.getPojoFromJSON(evalue, Teacher.class);
							Department department = redisDepartmentDao.getByDepId(org.getDep_id().toString(),connection);
							org.setDep_name(department.getDep_name());
						}
					} catch (Exception e) {
						return null;
					}                
					return org;
				}  
			});  
			return result;
		}else{
			Teacher result = redisTemplate.execute(new RedisCallback<Teacher>() {  
				public Teacher doInRedis(RedisConnection redisConnection)  
						throws DataAccessException {  
					RedisSerializer<String> serializer = redisTemplate.getStringSerializer();
					byte[] key = serializer.serialize("techinfo:"+tech_id);  
			
					Teacher org=null;
					try {
						byte[] value = redisConnection.get(key); 
						String evalue = serializer.deserialize(value); 
						if(StringUtils.isNotEmpty(evalue)){
							org = util.getPojoFromJSON(evalue, Teacher.class);
							Department department = redisDepartmentDao.getByDepId(org.getDep_id().toString(),redisConnection);
							if(null!=department){
								org.setDep_name(department.getDep_name());
							}
						}
					} catch (Exception e) {
						return null;
					}                
					return org;
				}  
			});  
			return result;
		}
	}

	@Override
	public List<Teacher> getTechsByDepId(final String dep_id,final RedisConnection connection) {
		if(null!=connection){
			List<Teacher> result = redisTemplate.execute(new RedisCallback<List<Teacher>>() {  
	            public List<Teacher> doInRedis(RedisConnection redisConnection) throws DataAccessException {  
	                RedisSerializer<String> serializer = redisTemplate.getStringSerializer();
	                byte[] key = serializer.serialize("techinfo_dep:"+dep_id);  
	                List<Teacher> list = new ArrayList<Teacher>();
					try {
						Map<byte[], byte[]> values = connection.hGetAll(key);
						for (Entry<byte[], byte[]> org : values.entrySet()) {
							String evaluestr = serializer.deserialize(org.getValue());
							if(StringUtils.isNotEmpty(evaluestr)){
							Teacher evalue = util.getPojoFromJSON(evaluestr, Teacher.class);
							evalue.setIs_selected(0);
							if( !evalue.getTech_name().equals("edugate"+evalue.getOrg_id()))
								list.add(evalue);
							}
						}					
					} catch (Exception e) {
						//return null;
					}  
					TechToSortComparator mc = new TechToSortComparator();  
			        Collections.sort(list,mc);
	                return list;
	            }  
	        });  
	        return result;
		}else{
			List<Teacher> result = redisTemplate.execute(new RedisCallback<List<Teacher>>() {  
	            public List<Teacher> doInRedis(RedisConnection redisConnection)  
	                    throws DataAccessException {  
	                RedisSerializer<String> serializer = redisTemplate.getStringSerializer();
	                byte[] key = serializer.serialize("techinfo_dep:"+dep_id);  
	                List<Teacher> list = new ArrayList<Teacher>();
					try {
						Map<byte[], byte[]> values = redisConnection.hGetAll(key);
						for (Entry<byte[], byte[]> org : values.entrySet()) {
							String evaluestr = serializer.deserialize(org.getValue());
							if(StringUtils.isNotEmpty(evaluestr)){
							Teacher evalue = util.getPojoFromJSON(evaluestr, Teacher.class);
							evalue.setIs_selected(0); 
							if( !evalue.getTech_name().equals("edugate"+evalue.getOrg_id()))
								list.add(evalue);
							}
						}					
					} catch (Exception e) {
						return null;
					}   
					TechToSortComparator mc = new TechToSortComparator();  
			        Collections.sort(list,mc);
	                return list;
	            }  
	        });  
	        return result;
		}
	}

	@Override
	public Teacher getByUserId(final String user_id) {
		// TODO Auto-generated method stub
		Teacher result = redisTemplate.execute(new RedisCallback<Teacher>() {  
            public Teacher doInRedis(RedisConnection connection)  
                    throws DataAccessException {  
                RedisSerializer<String> serializer = redisTemplate.getStringSerializer();
                byte[] key = serializer.serialize("techinfo_user:"+user_id);  
                Teacher org=null;
				try {
					byte[] value = connection.get(key); 
	                String evalue = serializer.deserialize(value); 
	                if(StringUtils.isNotEmpty(evalue))
	                	org = util.getPojoFromJSON(evalue, Teacher.class);
	                
		                Department department = redisDepartmentDao.getByDepId(org.getDep_id()+"",null);
		                if(null!=department){
		                	org.setDep_name(department.getDep_name());
		                }
	                
	                
	                
	                	LSHelper.processInjection(org);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					return null;
				}                
                return org;
            }  
        });  
        return result;
	}

	@Override
	public List<Teacher> getTeacherListByOrgId(final String org_id,final RedisConnection connection) {
		if(null!=connection){
			List<Teacher> result = redisTemplate.execute(new RedisCallback<List<Teacher>>() {  
	            public List<Teacher> doInRedis(RedisConnection redisConnection) throws DataAccessException {  
	                RedisSerializer<String> serializer = redisTemplate.getStringSerializer();
	                byte[] key = serializer.serialize("techinfo_org:"+org_id);  
	                List<Teacher> list = new ArrayList<Teacher>();
					try {
						Map<byte[], byte[]> values = connection.hGetAll(key);
						for (Entry<byte[], byte[]> org : values.entrySet()) {
							String evaluestr = serializer.deserialize(org.getValue());
							if(StringUtils.isNotEmpty(evaluestr)){
								Teacher evalue = util.getPojoFromJSON(evaluestr, Teacher.class);
								
								OrgUser orgUser = redisOrgUserDao.getUserById(evalue.getUser_id().toString());
								
								evalue.setUser(orgUser);
//								List<Map<String,Object>> mapList = redisTechRangeDao.getIdentityById(evalue.getTech_id().toString(), EnumRoleLabel.部门管理员.getCode(), connection);
//								StringBuffer departmentName = new StringBuffer();
//								for (Map<String,Object> map : mapList) {
//									for (Object o : map.values()) {
//										departmentName.append(((Department)o).getDep_name()).append(" ");
//									}
//								}
//								evalue.setManaging_dep_name(departmentName.toString());
								list.add(evalue);
							}
						}
					} catch (Exception e) {
						return null;
					}  
					TechToSortComparator mc = new TechToSortComparator();  
			        Collections.sort(list,mc);
	                return list;
	            }  
	        });  
	        return result;
		}else{
			List<Teacher> result = redisTemplate.execute(new RedisCallback<List<Teacher>>() {  
	            public List<Teacher> doInRedis(RedisConnection redisConnection)  
	                    throws DataAccessException {  
	                RedisSerializer<String> serializer = redisTemplate.getStringSerializer();
	                //byte[] key = serializer.serialize("techinfo_dep:"+org_id);  
	                byte[] key = serializer.serialize("techinfo_org:"+org_id);  
	                List<Teacher> list = new ArrayList<Teacher>();
					try {
						Map<byte[], byte[]> values = redisConnection.hGetAll(key);
						for (Entry<byte[], byte[]> org : values.entrySet()) {
							String evaluestr = serializer.deserialize(org.getValue());
							if(StringUtils.isNotEmpty(evaluestr)){
								Teacher evalue = util.getPojoFromJSON(evaluestr, Teacher.class);
								OrgUser orgUser = redisOrgUserDao.getUserById(evalue.getUser_id().toString());
								
								evalue.setUser(orgUser);
//								List<Map<String,Object>> mapList = redisTechRangeDao.getIdentityById(evalue.getTech_id().toString(), EnumRoleLabel.部门管理员.getCode(), redisConnection);
//								StringBuffer departmentName = new StringBuffer();
//								for (Map<String,Object> map : mapList) {
//									for (Object o : map.values()) {
//										departmentName.append(((Department)o).getDep_name()).append(" ");
//									}
//								}
//								evalue.setManaging_dep_name(departmentName.toString());
								list.add(evalue);
							}
						}					
					} catch (Exception e) {
						e.printStackTrace();
						return null;
					}   
					TechToSortComparator mc = new TechToSortComparator();  
			        Collections.sort(list,mc);
	                return list;
	            }  
	        });  
	        return result;
		}
	}
	
	
	
	
	@Override
	public List<Teacher> getTechMapListByOrgId(final String org_id,final RedisConnection connection) {
		
		List<Teacher> result = redisTemplate.execute(new RedisCallback<List<Teacher>>() {  
            public List<Teacher> doInRedis(RedisConnection redisConnection)  
                    throws DataAccessException {  
                RedisSerializer<String> serializer = redisTemplate.getStringSerializer();
                //byte[] key = serializer.serialize("techinfo_dep:"+org_id);  
                byte[] key = serializer.serialize("techinfo_org:"+org_id);  
                List<Teacher> list = new ArrayList<Teacher>();
				try {
					Map<byte[], byte[]> values = redisConnection.hGetAll(key);
					for (Entry<byte[], byte[]> org : values.entrySet()) {
						String evaluestr = serializer.deserialize(org.getValue());
						if(StringUtils.isNotEmpty(evaluestr)){
							Teacher evalue = util.getPojoFromJSON(evaluestr, Teacher.class);
							String openid = redisOrgUserDao.getUserOpenidById(evalue.getUser_id().toString());
							if (openid != null && StringUtils.isNotBlank(openid)) {
								evalue.setOpenid(openid);
							}
							list.add(evalue);
						}
					}					
				} catch (Exception e) {
					e.printStackTrace();
					return null;
				}   
				TechToSortComparator mc = new TechToSortComparator();  
		        Collections.sort(list,mc);
                return list;
            }  
        });  
        return result;
	
	}
}
