package cn.edugate.esb.redis.dao.imp;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.lang.StringUtils;
import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.map.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.stereotype.Repository;

import cn.edugate.esb.entity.OrgUser;
import cn.edugate.esb.redis.RedisGeneratorDao;
import cn.edugate.esb.redis.dao.RedisOrgUserDao;
import cn.edugate.esb.util.Util;
import cn.edugate.esb.util.Utils;

@Repository
public class RedisOrgUserDaoImp extends RedisGeneratorDao<String, String> implements RedisOrgUserDao {
	static Logger logger=LoggerFactory.getLogger(RedisOrgUserDaoImp.class);
	private Util util;
	@Autowired
	public void setUtil(Util util) {
		this.util = util;
	}
	static ObjectMapper oMapper = new ObjectMapper();
	@Override
	public boolean addUsers(final OrgUser user) {
		// TODO Auto-generated method stub
		boolean result = redisTemplate.execute(new RedisCallback<Boolean>() {  
            public Boolean doInRedis(RedisConnection connection)  
                    throws DataAccessException {  
                RedisSerializer<String> serializer = redisTemplate.getStringSerializer();              	
                if(!user.getIs_del()){
	            	String json="";
	            	json = util.getJSONFromPOJO(user);
	            	byte[] value = serializer.serialize(json); 
	        		byte[] key  = serializer.serialize("orguser:"+user.getUser_id());  
	        		byte[] key2  = serializer.serialize("orgloginuser:"+user.getUser_loginname()+":"+user.getOrg_id()); 	        		
	            	connection.set(key, value);    
	            	connection.set(key2, value); 
	            	byte[] key3  = serializer.serialize("orgloginname:"+user.getUser_loginname());
	            	byte[] field  = serializer.serialize(user.getOrg_id().toString()+"_"+user.getIdentity()); 
	        		connection.hSet(key3,field, value);
	        		byte[] key4  = serializer.serialize("orguserOrg:"+user.getOrg_id());
	        		byte[] field4  = serializer.serialize(user.getUser_id().toString()); 
	        		connection.hSet(key4,field4, value);
	        		if(StringUtils.isNotEmpty(user.getOpenid())){
		        		byte[] key5  = serializer.serialize("orguserOpenid:"+user.getOpenid()+":"+user.getOrg_id()+":"+user.getIdentity());
		        		connection.set(key5, value);
	        		}
                }        
                return true;  
            }
        }, false, true);  
        return result;  
	}
	
	@Override
	public boolean addUsers(final List<OrgUser> users) {
		// TODO Auto-generated method stub
		boolean result = redisTemplate.execute(new RedisCallback<Boolean>() {  
            public Boolean doInRedis(RedisConnection connection)  
                    throws DataAccessException {  
                RedisSerializer<String> serializer = redisTemplate.getStringSerializer();              	
                for (OrgUser user : users) { 
                	//if(!user.getIs_del()){
	                	String json="";
	                	json = util.getJSONFromPOJO(user);
	                	byte[] value = serializer.serialize(json); 
	            		byte[] key  = serializer.serialize("orguser:"+user.getUser_id());  
	            		byte[] key2  = serializer.serialize("orgloginuser:"+user.getUser_loginname()+":"+user.getOrg_id()); 
	                	connection.set(key, value);    
	                	connection.set(key2, value); 
	                	byte[] key3  = serializer.serialize("orgloginname:"+user.getUser_loginname());
		            	byte[] field  = serializer.serialize(user.getOrg_id().toString()+"_"+user.getIdentity()); 
		        		connection.hSet(key3,field, value);
		        		byte[] key4  = serializer.serialize("orguserOrg:"+user.getOrg_id());
		        		byte[] field4  = serializer.serialize(user.getUser_id().toString()); 
		        		connection.hSet(key4,field4, value);
		        		
		        		if(StringUtils.isNotEmpty(user.getOpenid())){
			        		byte[] key5  = serializer.serialize("orguserOpenid:"+user.getOpenid()+":"+user.getOrg_id()+":"+user.getIdentity());
			        		connection.set(key5, value);
		        		}
                	//}
                }                  
                return true;  
            }
        }, false, true);  
        return result;  
	}	

	@Override
	public boolean deleteUsers(final OrgUser user) {
		// TODO Auto-generated method stub
		boolean result = redisTemplate.execute(new RedisCallback<Boolean>() {  
            public Boolean doInRedis(RedisConnection connection)  
                    throws DataAccessException {  
                RedisSerializer<String> serializer = redisTemplate.getStringSerializer();              	
                 
        		byte[] key  = serializer.serialize("orguser:"+user.getUser_id());  
        		byte[] key2  = serializer.serialize("orgloginuser:"+user.getUser_loginname()+":"+user.getOrg_id()); 
            	connection.del(key);   
            	connection.del(key2); 
            	byte[] key3  = serializer.serialize("orgloginname:"+user.getUser_loginname());
            	byte[] field  = serializer.serialize(user.getOrg_id().toString()+"_"+user.getIdentity()); 
            	connection.hDel(key3, field);  
            	byte[] key4  = serializer.serialize("orguserOrg:"+user.getOrg_id());
        		byte[] field4  = serializer.serialize(user.getUser_id().toString()); 
        		connection.hDel(key4,field4);
        		
        		byte[] key5  = serializer.serialize("orguserOpenid:"+user.getOpenid()+":"+user.getOrg_id()+":"+user.getIdentity()); 
        		connection.del(key5);   
                return true;  
            }
        }, false, true);  
        return result;
	}

	@Override
	public OrgUser getUserById(final String id) {
		// TODO Auto-generated method stub
		OrgUser result = redisTemplate.execute(new RedisCallback<OrgUser>() {  
            public OrgUser doInRedis(RedisConnection connection)  
                    throws DataAccessException {  
                RedisSerializer<String> serializer = redisTemplate.getStringSerializer();
                byte[] key = serializer.serialize("orguser:"+id);  
                OrgUser user = null;
				try {
					byte[] value = connection.get(key); 
	                String evalue = serializer.deserialize(value); 
	                if(StringUtils.isNotEmpty(evalue)){
	                	user = util.getPojoFromJSON(evalue, OrgUser.class);
	                }
				} catch (Exception e) {
					// TODO Auto-generated catch block
					return null;
				}                
                return user;
            }  
        });  
        return result;
	}

	@Override
	public OrgUser getUserByLoginName(final String login_name, final Integer org_id) {
		// TODO Auto-generated method stub
		OrgUser result = redisTemplate.execute(new RedisCallback<OrgUser>() {  
            public OrgUser doInRedis(RedisConnection connection)  
                    throws DataAccessException {  
                RedisSerializer<String> serializer = redisTemplate.getStringSerializer();
                byte[] key = serializer.serialize("orgloginuser:"+login_name+":"+org_id);  
                OrgUser user = null;
				try {
					byte[] value = connection.get(key); 
	                String evalue = serializer.deserialize(value); 
	                if(StringUtils.isNotEmpty(evalue))
	                	user = util.getPojoFromJSON(evalue, OrgUser.class);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					return null;
				}                
                return user;
            }  
        });  
        return result; 
	}
	
	@Override
	public Map<String,OrgUser> getUserByLoginName(final String login_name) {
		// TODO Auto-generated method stub
		Map<String,OrgUser> result = redisTemplate.execute(new RedisCallback<Map<String,OrgUser>>() {  
            public Map<String,OrgUser> doInRedis(RedisConnection connection)  
                    throws DataAccessException {  
                RedisSerializer<String> serializer = redisTemplate.getStringSerializer();
                byte[] key = serializer.serialize("orgloginname:"+login_name); 
                System.out.println(login_name);
                Map<String,OrgUser> teachers = new HashMap<String,OrgUser>();
                Map<String, OrgUser> tempMap = new LinkedHashMap<String, OrgUser>();
				try {
					Map<byte[], byte[]> values = connection.hGetAll(key);
					for (Entry<byte[], byte[]> teacher : values.entrySet()) {
						String ekey = serializer.deserialize(teacher.getKey());
						String evaluestr = serializer.deserialize(teacher.getValue());
						if(StringUtils.isNotEmpty(evaluestr)){
							OrgUser evalue = util.getPojoFromJSON(evaluestr, OrgUser.class);
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
	public OrgUser getUserByLoginNameWX(final String login_name,final Integer org_id,final String identity) {
		// TODO Auto-generated method stub
		OrgUser result = redisTemplate.execute(new RedisCallback<OrgUser>() {  
            public OrgUser doInRedis(RedisConnection connection)  
                    throws DataAccessException {  
                RedisSerializer<String> serializer = redisTemplate.getStringSerializer();
                byte[] key = serializer.serialize("orgloginname:"+login_name); 
 
				try {
					Map<byte[], byte[]> values = connection.hGetAll(key);
					for (Entry<byte[], byte[]> teacher : values.entrySet()) {
						String ekey = serializer.deserialize(teacher.getKey());
						
						if(ekey.equals(org_id+"_"+identity)){
							String evaluestr = serializer.deserialize(teacher.getValue());
							if(StringUtils.isNotEmpty(evaluestr)){
								OrgUser evalue = util.getPojoFromJSON(evaluestr, OrgUser.class);
								return evalue;
							}
						}
					}					
				} catch (Exception e) {
					// TODO Auto-generated catch block
					logger.error(e.getMessage());
					return null;
				}
                return null;
            }  
        });  
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
					deleteRedis(connection, serializer, "orguser:"+"*");
	                deleteRedis(connection, serializer, "orgloginuser:"+"*");
	                deleteRedis(connection, serializer, "orgloginname:"+"*");	                
	                deleteRedis(connection, serializer, "orguserOrg:"+"*");	      
	                deleteRedis(connection, serializer, "orguserOpenid:"+"*");
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
	public List<OrgUser> getUserByOrgId(final Integer id) {
		List<OrgUser> result = redisTemplate.execute(new RedisCallback<List<OrgUser>>() {  
            public List<OrgUser> doInRedis(RedisConnection connection) throws DataAccessException {  
                RedisSerializer<String> serializer = redisTemplate.getStringSerializer();
                byte[] key = serializer.serialize("orguserOrg:"+id);  
                List<OrgUser> list = new ArrayList<OrgUser>();
				try {
					Map<byte[], byte[]> values = connection.hGetAll(key);
					for (Entry<byte[], byte[]> orgUser : values.entrySet()) {
						String evaluestr = serializer.deserialize(orgUser.getValue());
						if(StringUtils.isNotEmpty(evaluestr)){
							OrgUser evalue = util.getPojoFromJSON(evaluestr, OrgUser.class);
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
	public String getUserOpenidById(final String id) {
		// TODO Auto-generated method stub
		String result = redisTemplate.execute(new RedisCallback<String>() {  
            public String doInRedis(RedisConnection connection)  
                    throws DataAccessException {  
                RedisSerializer<String> serializer = redisTemplate.getStringSerializer();
                byte[] key = serializer.serialize("orguser:"+id);  
                String openid = null;
				try {
					byte[] value = connection.get(key); 
	                String evalue = serializer.deserialize(value); 
	                if(StringUtils.isNotEmpty(evalue)){
	                	
	                	JsonNode jsonNode = oMapper.readTree(evalue);
	                	if(jsonNode.get("openid")!=null) {
	                		openid = jsonNode.get("openid").asText();
	                		if (StringUtils.isBlank(openid) || "null".equals(openid)) {
	                			openid = null;
	                		}
	                	}
	                }
				} catch (Exception e) {
					// TODO Auto-generated catch block
					return null;
				}                
                return openid;
            }  
        });  
        return result;
	}
	
	@Override
	public OrgUser getUserByOpenId(final String openId,final Integer org_id,final Integer identity) {
		// TODO Auto-generated method stub
		OrgUser result = redisTemplate.execute(new RedisCallback<OrgUser>() {  
            public OrgUser doInRedis(RedisConnection connection)  
                    throws DataAccessException {  
                RedisSerializer<String> serializer = redisTemplate.getStringSerializer();
                byte[] key = serializer.serialize("orguserOpenid:"+openId+":"+org_id+":"+identity);  
                OrgUser user = null;
				try {
					byte[] value = connection.get(key); 
	                String evalue = serializer.deserialize(value); 
	                if(StringUtils.isNotEmpty(evalue)){
	                	user = util.getPojoFromJSON(evalue, OrgUser.class);
	                }
				} catch (Exception e) {
					// TODO Auto-generated catch block
					return null;
				}                
                return user;
            }  
        });  
        return result;
	}
}
