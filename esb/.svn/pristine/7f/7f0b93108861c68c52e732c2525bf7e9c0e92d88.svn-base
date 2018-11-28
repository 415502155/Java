package cn.edugate.esb.redis.dao.imp;

import java.math.BigInteger;
import java.util.ArrayList;
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

import cn.edugate.esb.entity.Department;
import cn.edugate.esb.entity.Teacher;
import cn.edugate.esb.redis.RedisGeneratorDao;
import cn.edugate.esb.redis.dao.RedisDepartmentDao;
import cn.edugate.esb.redis.dao.RedisTeacherDao;
import cn.edugate.esb.redis.dao.RedisTechRangeDao;
import cn.edugate.esb.util.Constant;
import cn.edugate.esb.util.Util;
import cn.edugate.esb.util.Utils;
@Repository
public class RedisDepartmentDaoImpl extends RedisGeneratorDao<String, String> implements  RedisDepartmentDao{
	@Autowired
	private Util util;
	@Autowired
	private RedisTechRangeDao redisTechRangeDao;
	@Autowired
	private RedisTeacherDao redisTeacherDao;
	
	@Override
	public boolean add(final Department tr) {
		// TODO Auto-generated method stub
		boolean result = redisTemplate.execute(new RedisCallback<Boolean>() {  
            public Boolean doInRedis(RedisConnection connection)  
                    throws DataAccessException {  
            	if(tr.getIs_del()==Constant.IS_DEL_NO){
	                RedisSerializer<String> serializer = redisTemplate.getStringSerializer();
	            	String json="";
	            	json = util.getJSONFromPOJO(tr);
	            	byte[] value = serializer.serialize(json);       		
	        		byte[] key2  = serializer.serialize("depinfo:"+tr.getDep_id());                	  
	            	connection.set(key2, value);  
	            	byte[] key1  = serializer.serialize("depinfo_org:"+tr.getOrg_id());  
	        		byte[] field2  = serializer.serialize(tr.getDep_id().toString()); 
	        		connection.hSet(key1,field2, value); 
            	}
            	return true;  
            }
        }, false, true);  
        return result; 
	}

	@Override
	public boolean add(final List<Department> trs) {
		// TODO Auto-generated method stub
		boolean result = redisTemplate.execute(new RedisCallback<Boolean>() {  
            public Boolean doInRedis(RedisConnection connection)  
                    throws DataAccessException {  
                RedisSerializer<String> serializer = redisTemplate.getStringSerializer();
            	 for (Department dep : trs) {  
            		 //if(dep.getIs_del()==Constant.IS_DEL_NO){
	            		Map<String,String> map = new HashMap<String,String>();
	            		String json="";
		            	json = util.getJSONFromPOJO(dep);
		            	map.put("dep_note", dep.getDep_note());
		            	map.put("dep_id", dep.getDep_id().toString());
		            	map.put("dep_name", dep.getDep_name());
		            	map.put("dep_officephone", dep.getDep_officephone());
		            	map.put("hx_groupid", dep.getHx_groupid());
		            	String json1 = util.getJSONFromPOJO(map);
		            	
		            	byte[] value1 = serializer.serialize(json1);   
		            	
		            	byte[] value = serializer.serialize(json);       		
		        		byte[] key2  = serializer.serialize("depinfo:"+dep.getDep_id());                	  
		            	connection.set(key2, value); 
		            	byte[] key1  = serializer.serialize("depinfo_org:"+dep.getOrg_id());  
	            		byte[] field2  = serializer.serialize(dep.getDep_id().toString()); 
	            		connection.hSet(key1,field2, value1); 
            		// }
            	 }
                return true;  
            }
        }, false, true);  
        return result;
	}

	@Override
	public boolean delete(final Department tr) {
		// TODO Auto-generated method stub
		boolean result = redisTemplate.execute(new RedisCallback<Boolean>() {  
            public Boolean doInRedis(RedisConnection connection)  
                    throws DataAccessException {  
                RedisSerializer<String> serializer = redisTemplate.getStringSerializer();
        		byte[] key  = serializer.serialize("depinfo:"+tr.getDep_id()); 
            	connection.del(key);
            	
            	byte[] key1  = serializer.serialize("depinfo_org:"+tr.getOrg_id());  
        		byte[] field1 = serializer.serialize(tr.getDep_id().toString()); 
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
				deleteRedis(connection, serializer, "depinfo:"+"*");       
				deleteRedis(connection, serializer, "depinfo_org:"+"*");       
                return true;
            }  
        });  
        return result;  
	}

	@Override
	public Department getByDepId(final String dep_id,final RedisConnection connection) {
		if(null!=connection){
			Department result = redisTemplate.execute(new RedisCallback<Department>() {  
	            public Department doInRedis(RedisConnection redisConnection)  
	                    throws DataAccessException {  
	                RedisSerializer<String> serializer = redisTemplate.getStringSerializer();
	                byte[] key = serializer.serialize("depinfo:"+dep_id);  
	                Department org=null;
					try {
						byte[] value = connection.get(key); 
		                String evalue = serializer.deserialize(value); 
		                if(StringUtils.isNotEmpty(evalue))
		                	org = util.getPojoFromJSON(evalue, Department.class);
					} catch (Exception e) {
						// TODO Auto-generated catch block
						return null;
					}                
	                return org;
	            }  
	        });  
	        return result;
		}else{
			Department result = redisTemplate.execute(new RedisCallback<Department>() {  
	            public Department doInRedis(RedisConnection redisConnection)  
	                    throws DataAccessException {  
	                RedisSerializer<String> serializer = redisTemplate.getStringSerializer();
	                byte[] key = serializer.serialize("depinfo:"+dep_id);  
	                Department org=null;
					try {
						byte[] value = redisConnection.get(key); 
		                String evalue = serializer.deserialize(value); 
		                if(StringUtils.isNotEmpty(evalue))
		                	org = util.getPojoFromJSON(evalue, Department.class);
					} catch (Exception e) {
						// TODO Auto-generated catch block
						return null;
					}                
	                return org;
	            }  
	        });  
	        return result;
		}
		
	}

	@Override
	public List<Department> getDepsByOrgId(final String org_id) {
		List<Department> result = redisTemplate.execute(new RedisCallback<List<Department>>() {  
            public List<Department> doInRedis(RedisConnection connection)  
                    throws DataAccessException {  
                RedisSerializer<String> serializer = redisTemplate.getStringSerializer();
                byte[] key = serializer.serialize("depinfo_org:"+org_id);  
                Map<String,Department> deps = new HashMap<String,Department>();
                Map<String, Department> sortDeps = new LinkedHashMap<String, Department>();
                List<Department> list = new ArrayList<Department>();
				try {
					Map<byte[], byte[]> values = connection.hGetAll(key);
					if(null!=values&&!values.isEmpty()){
						for (Entry<byte[], byte[]> org : values.entrySet()) {
							String ekey = serializer.deserialize(org.getKey());
							String evaluestr = serializer.deserialize(org.getValue());
							if(StringUtils.isNotEmpty(evaluestr)){
								Department evalue = util.getPojoFromJSON(evaluestr, Department.class);
								deps.put(ekey, evalue);
//							list.add(evalue);
							}						
						}					
					}
				} catch (Exception e) {
					// TODO Auto-generated catch block
					return null;
				} 
				sortDeps = Utils.sortMapByKey(deps);
				list = new ArrayList<Department>(sortDeps.values());
                return list;
            }  
        });  
        return result;
	}

	@Override
	public List<Department> getDepsDetailList(final String org_id) {
		List<Department> result = redisTemplate.execute(new RedisCallback<List<Department>>() {  
            public List<Department> doInRedis(RedisConnection connection) throws DataAccessException {  
                RedisSerializer<String> serializer = redisTemplate.getStringSerializer();
                byte[] key = serializer.serialize("depinfo_org:"+org_id);  
                List<Department> list = new ArrayList<Department>();
                Map<String,Department> deps = new HashMap<String,Department>();
                Map<String, Department> sortDeps = new LinkedHashMap<String, Department>();
				Map<byte[], byte[]> values = connection.hGetAll(key);
				for (Entry<byte[], byte[]> org : values.entrySet()) {
					try {
						String ekey = serializer.deserialize(org.getKey());
						String evaluestr = serializer.deserialize(org.getValue());
						if(StringUtils.isNotEmpty(evaluestr)){
							Department evalue = util.getPojoFromJSON(evaluestr, Department.class);
							Map<String,Object> map = redisTechRangeDao.getDepinfoManager(evalue.getDep_id().toString(),connection);
							if(null!=map&&map.size()!=0){
								List<Teacher> teacherList = redisTeacherDao.getTechsByDepId(evalue.getDep_id().toString(),connection);
								Teacher t = new Teacher();
								String managerName = "";
								for(String tid : map.keySet()){
									t = (Teacher) map.get(tid);
									managerName += t.getTech_name()+" ";
								}
								evalue.setManager_name(managerName);
								evalue.setMem_num(new BigInteger(teacherList.size()+""));
							}
							deps.put(ekey, evalue);
						}
					} catch (Exception e) {
						e.printStackTrace();
					}   
				}					
				sortDeps = Utils.sortMapByKey(deps);
				list = new ArrayList<Department>(sortDeps.values());
                return list;
            }  
        });  
        return result;
	}


}
