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

import cn.edugate.esb.entity.Group;
import cn.edugate.esb.redis.RedisGeneratorDao;
import cn.edugate.esb.redis.dao.RedisGroupDao;
import cn.edugate.esb.util.Constant;
import cn.edugate.esb.util.Util;
import cn.edugate.esb.util.Utils;

/**
 * Title:RedisGroupDaoImpl
 * Description:
 * Company:SJWY
 * @author:Liuy
 * @Date:2017年5月23日下午6:08:50
 */
@Repository
public class RedisGroupDaoImpl extends RedisGeneratorDao<String, String> implements RedisGroupDao {

	private static Logger logger = Logger.getLogger(RedisGroupDaoImpl.class);
	
	private Util util;
	@Autowired
	public void setUtil(Util util) {
		this.util = util;
	}
	
	@Override
	public boolean addGroup(final Group group) {
		boolean result = redisTemplate.execute(new RedisCallback<Boolean>() {  
            public Boolean doInRedis(RedisConnection connection) throws DataAccessException {  
                RedisSerializer<String> serializer = redisTemplate.getStringSerializer();  
                if(group.getIs_del()==Constant.IS_DEL_NO){
	            	String json="";
	            	json = util.getJSONFromPOJO(group);
	            	byte[] value = serializer.serialize(json); 
	            	byte[] field  = serializer.serialize(group.getGroup_id().toString()); 
	            	
	            	//根据组id  存储的组信息
	        		byte[] key1  = serializer.serialize("group:"+group.getGroup_id());  
	        		connection.set(key1, value);    
	        		//byte[] key2  = serializer.serialize("groupUser:"+group.getTech_id());  
            		//connection.hSet(key2,field, value);
	        		//根据机构id  存储的所有组信息
            		byte[] key3  = serializer.serialize("groupOrg:"+group.getOrg_id());  
            		connection.hSet(key3,field, value);
            		//byte[] key4  = serializer.serialize("groupType:"+group.getGroup_type());  
            		//connection.hSet(key4,field, value);
            		//byte[] key5  = serializer.serialize("groupUser:"+group.getTech_id()+":groupOrg:"+group.getOrg_id());  
            		//connection.hSet(key5,field, value);
            		//byte[] key6  = serializer.serialize("groupUser:"+group.getTech_id()+":groupType:"+group.getGroup_type());  
            		//connection.hSet(key6,field, value);
            		
            		//根据机构id+组类型   存储的所有组信息
            		byte[] key7  = serializer.serialize("groupOrg:"+group.getOrg_id()+":groupType:"+group.getGroup_type());  
            		connection.hSet(key7,field, value);
            		//byte[] key8  = serializer.serialize("groupUser:"+group.getTech_id()+":groupOrg:"+group.getOrg_id()+":groupType:"+group.getGroup_type());  
            		//connection.hSet(key8,field, value);
            		byte[] key9  = serializer.serialize("groupAll");  
	        		connection.hSet(key9,field, value);  
                }        
                return true;  
            }
        }, false, true);  
        return result;  
	}
	
	@Override
	public boolean addGroups(final List<Group> groups) {
		boolean result = redisTemplate.execute(new RedisCallback<Boolean>() {  
            public Boolean doInRedis(RedisConnection connection) throws DataAccessException {  
                RedisSerializer<String> serializer = redisTemplate.getStringSerializer();  
                for (Group group : groups) {
                	//if(group.getIs_del()==Constant.IS_DEL_NO){
		            	String json="";
		            	json = util.getJSONFromPOJO(group);
		            	byte[] value = serializer.serialize(json); 
		            	byte[] field  = serializer.serialize(group.getGroup_id().toString()); 
		            	
		        		byte[] key1  = serializer.serialize("group:"+group.getGroup_id());  
		        		connection.set(key1, value);    
		        		//byte[] key2  = serializer.serialize("groupUser:"+group.getTech_id());  
	            		//connection.hSet(key2,field, value);
	            		byte[] key3  = serializer.serialize("groupOrg:"+group.getOrg_id());  
	            		connection.hSet(key3,field, value);
	            		//byte[] key4  = serializer.serialize("groupType:"+group.getGroup_type());  
	            		//connection.hSet(key4,field, value);
	            		//byte[] key5  = serializer.serialize("groupUser:"+group.getTech_id()+":groupOrg:"+group.getOrg_id());  
	            		//connection.hSet(key5,field, value);
	            		//byte[] key6  = serializer.serialize("groupUser:"+group.getTech_id()+":groupType:"+group.getGroup_type());  
	            		//connection.hSet(key6,field, value);
	            		byte[] key7  = serializer.serialize("groupOrg:"+group.getOrg_id()+":groupType:"+group.getGroup_type());  
	            		connection.hSet(key7,field, value);
	            		//byte[] key8  = serializer.serialize("groupUser:"+group.getTech_id()+":groupOrg:"+group.getOrg_id()+":groupType:"+group.getGroup_type());  
	            		//connection.hSet(key8,field, value);
	            		byte[] key9  = serializer.serialize("groupAll");  
		        		connection.hSet(key9,field, value);  
                	//}
				}
                return true;  
            }
        }, false, true);  
        return result;  
	}

	@Override
	public boolean deleteGroup(final Group group) {
		boolean result = redisTemplate.execute(new RedisCallback<Boolean>() {  
            public Boolean doInRedis(RedisConnection connection) throws DataAccessException {  
                RedisSerializer<String> serializer = redisTemplate.getStringSerializer();
                byte[] field = serializer.serialize(group.getGroup_id().toString()); 
                
        		byte[] key1  = serializer.serialize("group:"+group.getGroup_id());  
        		connection.del(key1);    
//        		byte[] key2  = serializer.serialize("groupUser:"+group.getTech_id());  
//        		connection.hDel(key2,field);
        		byte[] key3  = serializer.serialize("groupOrg:"+group.getOrg_id());  
        		connection.hDel(key3,field);
//        		byte[] key4  = serializer.serialize("groupType:"+group.getGroup_type());  
//        		connection.hDel(key4,field);
//        		byte[] key5  = serializer.serialize("groupUser:"+group.getTech_id()+":groupOrg:"+group.getOrg_id());  
//        		connection.hDel(key5,field);
//        		byte[] key6  = serializer.serialize("groupUser:"+group.getTech_id()+":groupType:"+group.getGroup_type());  
//        		connection.hDel(key6,field);
        		byte[] key7  = serializer.serialize("groupOrg:"+group.getOrg_id()+":groupType:"+group.getGroup_type());  
        		connection.hDel(key7,field);
//        		byte[] key8  = serializer.serialize("groupUser:"+group.getTech_id()+":groupOrg:"+group.getOrg_id()+":groupType:"+group.getGroup_type());  
//        		connection.hDel(key8,field);
        		byte[] key9  = serializer.serialize("groupAll");  
        		connection.hDel(key9,field);  
                return true;  
            }
        }, false, true);  
        return result;
	}


	@Override
	public boolean deleteAllGroups() {
		boolean result = redisTemplate.execute(new RedisCallback<Boolean>() {  
            public Boolean doInRedis(RedisConnection connection) throws DataAccessException {  
                RedisSerializer<String> serializer = redisTemplate.getStringSerializer();
				try {
					deleteRedis(connection,serializer,"group:*");
					//deleteRedis(connection,serializer,"groupUser:*");
					deleteRedis(connection,serializer,"groupOrg:*");
					//deleteRedis(connection,serializer,"groupType:*");
					//deleteRedis(connection,serializer,"groupUser:*:groupOrg:*");
					//deleteRedis(connection,serializer,"groupUser:*:groupType:*");
					deleteRedis(connection,serializer,"groupOrg:*:groupType:*");
					//deleteRedis(connection,serializer,"groupUser:*:groupOrg:*:groupType:*");
					deleteRedis(connection,serializer,"groupAll");
				} catch (Exception e) {
					logger.error("=====deleteAllGroups error:"+e.getMessage());
					return false;
				}                
                return true;
            }  
        });  
        return result; 
	}

	@Override
	public Group getGroupById(final Integer groupId,final RedisConnection connection) {
		if(null!=connection){
			Group result = redisTemplate.execute(new RedisCallback<Group>() {  
				public Group doInRedis(RedisConnection redisConnection) throws DataAccessException {  
					RedisSerializer<String> serializer = redisTemplate.getStringSerializer();
					Group group = new Group();
					try {
						byte[] key = serializer.serialize("group:"+groupId);
						byte[] value = connection.get(key);
						String evalue = serializer.deserialize(value); 
						if(StringUtils.isNotEmpty(evalue))
							group = util.getPojoFromJSON(evalue, Group.class);
						else
							return null;
					} catch (Exception e) {
						logger.error("=====getGroupById error:"+e.getMessage());
						return null;
					}                
					return group;
				}  
			});  
			return result;
		}else{
			Group result = redisTemplate.execute(new RedisCallback<Group>() {  
				public Group doInRedis(RedisConnection redisConnection) throws DataAccessException {  
					RedisSerializer<String> serializer = redisTemplate.getStringSerializer();
					Group group = new Group();
					try {
						byte[] key = serializer.serialize("group:"+groupId);
						byte[] value = redisConnection.get(key);
						String evalue = serializer.deserialize(value); 
						if(StringUtils.isNotEmpty(evalue))
							group = util.getPojoFromJSON(evalue, Group.class);
						else 
							return null;
					} catch (Exception e) {
						logger.error("=====getGroupById error:"+e.getMessage());
						return null;
					}                
					return group;
				}  
			});  
			return result;
		}
	}

	@Override
	public Map<String, Group> getGroups(final Integer orgId,final Integer groupType) {
        Map<String,Group> result = redisTemplate.execute(new RedisCallback<Map<String,Group>>() {  
            public Map<String,Group> doInRedis(RedisConnection connection) throws DataAccessException {  
                RedisSerializer<String> serializer = redisTemplate.getStringSerializer();
                byte[] key = null;
                if(null!=orgId&&null!=groupType){
                	key = serializer.serialize("groupOrg:"+orgId+":groupType:"+groupType); 
                }else if(null!=orgId){
                	key = serializer.serialize("groupOrg:"+orgId); 
                }else if(null!=groupType){
                	key = serializer.serialize("groupType:"+groupType); 
                }else{
                	key = serializer.serialize("groupAll"); 
                }
                Map<String,Group> groupsMap = new HashMap<String,Group>();
                Map<String, Group> tempMap = new LinkedHashMap<String, Group>();
				try {
					Map<byte[], byte[]> values = connection.hGetAll(key);
					for (Entry<byte[], byte[]> group : values.entrySet()) {
						String ekey = serializer.deserialize(group.getKey());
						String evaluestr = serializer.deserialize(group.getValue());
						if(StringUtils.isNotEmpty(evaluestr)){
							Group evalue = util.getPojoFromJSON(evaluestr, Group.class);
							tempMap.put(ekey, evalue);
						}
					}					
				} catch (Exception e) {
					logger.error("=====getGroups error:"+e.getMessage());
					return null;
				}   
				groupsMap = Utils.sortMapByKey(tempMap);
                return groupsMap;
            }  
        });  
        return result;
	}

	@Override
	public Map<String, Group> getGroupsByMember(final Integer memberId,final Integer type) {
		Map<String,Group> result = redisTemplate.execute(new RedisCallback<Map<String,Group>>() {  
            public Map<String,Group> doInRedis(RedisConnection connection) throws DataAccessException {  
                RedisSerializer<String> serializer = redisTemplate.getStringSerializer();
                byte[] key = serializer.serialize("memberGroup:"+memberId+":type:"+type);  
                Map<String,Group> groupsMap = new HashMap<String,Group>();
                Map<String, Group> tempMap = new LinkedHashMap<String, Group>();
				try {
					Map<byte[], byte[]> values = connection.hGetAll(key);
					for (Entry<byte[], byte[]> group : values.entrySet()) {
						String ekey = serializer.deserialize(group.getKey());
						String evaluestr = serializer.deserialize(group.getValue());
						if(StringUtils.isNotEmpty(evaluestr)){
							Group evalue = util.getPojoFromJSON(evaluestr, Group.class);
							tempMap.put(ekey, evalue);
						}
					}					
				} catch (Exception e) {
					logger.error("=====getGroupsByMemberId error:"+e.getMessage());
					return null;
				}   
				groupsMap = Utils.sortMapByKey(tempMap);
                return groupsMap;
            }  
        });  
        return result;
	}

}
