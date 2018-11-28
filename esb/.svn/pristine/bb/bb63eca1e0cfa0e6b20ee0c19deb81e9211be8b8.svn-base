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

import cn.edugate.esb.entity.Organization;
import cn.edugate.esb.redis.RedisGeneratorDao;
import cn.edugate.esb.redis.dao.RedisOrganizationDao;
import cn.edugate.esb.util.Constant;
import cn.edugate.esb.util.Util;
import cn.edugate.esb.util.Utils;
@Repository
public class RedisOrganizationDaoImpl extends RedisGeneratorDao<String, String> implements RedisOrganizationDao {
	
	@Autowired
	private Util util;

	@Override
	public boolean add(final Organization org) {
		// TODO Auto-generated method stub
		boolean result = redisTemplate.execute(new RedisCallback<Boolean>() {  
            public Boolean doInRedis(RedisConnection connection)  
                    throws DataAccessException { 
            	if(!org.getIs_del()){
	                RedisSerializer<String> serializer = redisTemplate.getStringSerializer();
	            	String json="";
	            	json = util.getJSONFromPOJO(org);
	            	byte[] value = serializer.serialize(json);       		
	        		byte[] key2  = serializer.serialize("orginfo:"+org.getOrg_id());                	  
	            	connection.set(key2, value);  
	            	byte[] key1  = serializer.serialize("orginfo_type:"+org.getType());  
	        		byte[] field2  = serializer.serialize(org.getOrg_id().toString()); 
	        		connection.hSet(key1,field2, value); 
	        		
	        		byte[] key3  = serializer.serialize("orgName:"+org.getOrg_name_cn()); 
	        		connection.set(key3, value);
	        		
	        		byte[] keyall  = serializer.serialize("organizationall");  
	        		byte[] fieldall  = serializer.serialize(org.getOrg_id().toString()); 
	        		connection.hSet(keyall,fieldall, value);
            	}
                return true;  
            }
        }, false, true);  
        return result;  
	}

	@Override
	public boolean add(final List<Organization> orgs) {
		// TODO Auto-generated method stub   
		boolean result = redisTemplate.execute(new RedisCallback<Boolean>() {  
            public Boolean doInRedis(RedisConnection connection)  
                    throws DataAccessException {  
                RedisSerializer<String> serializer = redisTemplate.getStringSerializer();
            	 for (Organization org : orgs) {
            		//if(!org.getIs_del()){
	            		String json="";
		            	json = util.getJSONFromPOJO(org);	            	
		            	byte[] value = serializer.serialize(json);       		
		        		byte[] key2  = serializer.serialize("orginfo:"+org.getOrg_id());                	  
		            	connection.set(key2, value); 
		            	byte[] key1  = serializer.serialize("orginfo_type:"+org.getType());  
	            		byte[] field2  = serializer.serialize(org.getOrg_id().toString()); 
	            		connection.hSet(key1,field2, value); 
	            		
	            		byte[] key3  = serializer.serialize("orgName:"+org.getOrg_name_cn()); 
	            		connection.set(key3, value);
	            		
	            		byte[] keyall  = serializer.serialize("organizationall");  
	            		byte[] fieldall  = serializer.serialize(org.getOrg_id().toString()); 
	            		connection.hSet(keyall,fieldall, value);
            		//}
            	 }
                return true;  
            }
        }, false, true);  
        return result;  
	}

	@Override
	public boolean delete(final Organization org) {
		// TODO Auto-generated method stub
		boolean result = redisTemplate.execute(new RedisCallback<Boolean>() {  
            public Boolean doInRedis(RedisConnection connection)  
                    throws DataAccessException {  
                RedisSerializer<String> serializer = redisTemplate.getStringSerializer();
        		byte[] key  = serializer.serialize("orginfo:"+org.getOrg_id()); 
            	connection.del(key);
            	
            	byte[] key2  = serializer.serialize("orgName:"+org.getOrg_name_cn()); 
            	connection.del(key2);
            	
            	byte[] key1  = serializer.serialize("orginfo_type:"+org.getType());  
        		byte[] field1 = serializer.serialize(org.getOrg_id().toString()); 
        		connection.hDel(key1, field1);
            	           	
                return true;  
            }
        }, false, true);  
        return result; 
	}

	@Override
	public Organization getByOrgId(final String org_id,final RedisConnection connection) {
		if(null!=connection){
			Organization result = redisTemplate.execute(new RedisCallback<Organization>() {  
				public Organization doInRedis(RedisConnection redisConnection) throws DataAccessException {  
					RedisSerializer<String> serializer = redisTemplate.getStringSerializer();
					byte[] key = serializer.serialize("orginfo:"+org_id);  
					Organization org=null;
					try {
						byte[] value = connection.get(key); 
						String evalue = serializer.deserialize(value); 
						if(StringUtils.isNotEmpty(evalue))
							org = util.getPojoFromJSON(evalue, Organization.class);
					} catch (Exception e) {
						return null;
					}                
					return org;
				}  
			});  
			return result;
		}else{
			Organization result = redisTemplate.execute(new RedisCallback<Organization>() {  
				public Organization doInRedis(RedisConnection redisConnection) throws DataAccessException {  
					RedisSerializer<String> serializer = redisTemplate.getStringSerializer();
					byte[] key = serializer.serialize("orginfo:"+org_id);  
					Organization org=null;
					try {
						byte[] value = redisConnection.get(key); 
						String evalue = serializer.deserialize(value); 
						if(StringUtils.isNotEmpty(evalue))
							org = util.getPojoFromJSON(evalue, Organization.class);
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
	public boolean deleteAll() {
		// TODO Auto-generated method stub
		boolean result = redisTemplate.execute(new RedisCallback<Boolean>() {  
            public Boolean doInRedis(RedisConnection connection)  
                    throws DataAccessException {  
                RedisSerializer<String> serializer = redisTemplate.getStringSerializer();
                
				try {
					deleteRedis(connection,serializer,"orginfo:*");
					deleteRedis(connection,serializer,"orginfo_type:*");
					deleteRedis(connection,serializer,"orgName:*");
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
	public List<Organization> getOrgsByType(final String type) {
		// TODO Auto-generated method stub
		List<Organization> result = redisTemplate.execute(new RedisCallback<List<Organization>>() {  
            public List<Organization> doInRedis(RedisConnection connection)  
                    throws DataAccessException {  
                RedisSerializer<String> serializer = redisTemplate.getStringSerializer();
                byte[] key = serializer.serialize("orginfo_type:"+type);  
                List<Organization> list = new ArrayList<Organization>();
				try {
					Map<byte[], byte[]> values = connection.hGetAll(key);
					for (Entry<byte[], byte[]> org : values.entrySet()) {
						String evaluestr = serializer.deserialize(org.getValue());
						if(StringUtils.isNotEmpty(evaluestr)){
							Organization evalue = util.getPojoFromJSON(evaluestr, Organization.class);
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

	@Override
    public Map<String,Organization> getChildOrg(final Integer orgId) {
        Map<String,Organization> result = redisTemplate.execute(new RedisCallback<Map<String,Organization>>() {  
            public Map<String,Organization> doInRedis(RedisConnection connection) throws DataAccessException {  
                RedisSerializer<String> serializer = redisTemplate.getStringSerializer();
                byte[] key = serializer.serialize("orgParentId:"+orgId);  
                Map<String,Organization> orgMap = new HashMap<String,Organization>();
                Map<String, Organization> tempMap = new LinkedHashMap<String, Organization>();
                try {
                    Map<byte[], byte[]> values = connection.hGetAll(key);
                    for (Entry<byte[], byte[]> org : values.entrySet()) {
                        String ekey = serializer.deserialize(org.getKey());
                        String evaluestr = serializer.deserialize(org.getValue());
                        if(StringUtils.isNotEmpty(evaluestr)){
	                        Organization evalue = util.getPojoFromJSON(evaluestr, Organization.class);
	                        tempMap.put(ekey, evalue);
                        }
                    }                    
                } catch (Exception e) {
                    return null;
                }   
                orgMap = Utils.sortMapByKey(tempMap);
                return orgMap;
            }  
        });  
        return result;
    }

	@Override
	public Organization getByOrgName(final String org_name_cn) {
		// TODO Auto-generated method stub
		Organization result = redisTemplate.execute(new RedisCallback<Organization>() {  
			public Organization doInRedis(RedisConnection redisConnection) throws DataAccessException {  
				RedisSerializer<String> serializer = redisTemplate.getStringSerializer();
				byte[] key = serializer.serialize("orgName:"+org_name_cn);  
				Organization org=null;
				try {
					byte[] value = redisConnection.get(key); 
					String evalue = serializer.deserialize(value); 
					if(StringUtils.isNotEmpty(evalue))
						org = util.getPojoFromJSON(evalue, Organization.class);
				} catch (Exception e) {
					return null;
				}                
				return org;
			}  
		});  
		return result;
	}

}
