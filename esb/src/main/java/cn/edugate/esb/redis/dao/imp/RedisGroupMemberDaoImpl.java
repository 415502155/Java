package cn.edugate.esb.redis.dao.imp;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.Map.Entry;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.stereotype.Repository;

import cn.edugate.esb.comparator.MapKeyTeacherComparator;
import cn.edugate.esb.entity.Grade;
import cn.edugate.esb.entity.Group;
import cn.edugate.esb.entity.GroupMember;
import cn.edugate.esb.entity.Organization;
import cn.edugate.esb.entity.Student;
import cn.edugate.esb.entity.Teacher;
import cn.edugate.esb.redis.RedisGeneratorDao;
import cn.edugate.esb.redis.dao.RedisGroupDao;
import cn.edugate.esb.redis.dao.RedisGroupMemberDao;
import cn.edugate.esb.redis.dao.RedisOrganizationDao;
import cn.edugate.esb.redis.dao.RedisStudentDao;
import cn.edugate.esb.redis.dao.RedisTeacherDao;
import cn.edugate.esb.util.Constant;
import cn.edugate.esb.util.Util;
import cn.edugate.esb.util.Utils;

/**
 * Title:RedisGroupMemberDaoImpl
 * Description:
 * Company:SJWY
 * @author:Liuy
 * @Date:2017年5月23日下午6:09:38
 */
@Repository
public class RedisGroupMemberDaoImpl extends RedisGeneratorDao<String, String> implements RedisGroupMemberDao {

	private static Logger logger = Logger.getLogger(RedisGroupMemberDaoImpl.class);
	
	private Util util;
	@Autowired
	public void setUtil(Util util) {
		this.util = util;
	}
	private RedisGroupDao redisGroupDao;
	@Autowired
	public void setRedisGroupDao(RedisGroupDao redisGroupDao) {
		this.redisGroupDao = redisGroupDao;
	}
	@Autowired
	private RedisStudentDao redisStudentDao;
	@Autowired
	private RedisTeacherDao redisTeacherDao;
	@Autowired
	private RedisOrganizationDao redisOrganizationDao;

	@Override
	public boolean addGroupMember(final GroupMember groupMember) {
		boolean result = redisTemplate.execute(new RedisCallback<Boolean>() {  
            public Boolean doInRedis(RedisConnection connection) throws DataAccessException {  
            	if(groupMember.getIs_del()==Constant.IS_DEL_NO){
	                RedisSerializer<String> serializer = redisTemplate.getStringSerializer();  
	            	String json="";
	            	json = util.getJSONFromPOJO(groupMember);
	            	byte[] value = serializer.serialize(json); 
	            	byte[] field = serializer.serialize(groupMember.getGroup_member_id().toString()); 
	            	//根据关系表ID  存储组成员单条信息
	        		byte[] key1  = serializer.serialize("groupMember:"+groupMember.getGroup_member_id());  
	            	connection.set(key1, value);   
	            	//根据组ID  存储组成员集合信息
	            	byte[] key2  = serializer.serialize("groupMemberGroupID:"+groupMember.getGroup_id());  
	            	connection.hSet(key2, field, value);
	            	// 老师或学生id所在的所有的组
	            	byte[] gKey  = serializer.serialize("memberGroup:"+groupMember.getMem_id()+":type:"+groupMember.getType());  
	            	byte[] gField = serializer.serialize(groupMember.getGroup_id().toString());
	            	Group group = redisGroupDao.getGroupById(groupMember.getGroup_id(),null);
	            	String gJson = util.getJSONFromPOJO(group);
	            	byte[] gValue = serializer.serialize(gJson); 
	            	connection.hSet(gKey, gField, gValue);
	            	
            	}
                return true;  
            }
        }, false, true);  
        return result;  
	}

	@Override
	public boolean addGroupMembers(final List<GroupMember> groupMembers) {
		boolean result = redisTemplate.execute(new RedisCallback<Boolean>() {  
            public Boolean doInRedis(RedisConnection connection) throws DataAccessException {  
                RedisSerializer<String> serializer = redisTemplate.getStringSerializer();  
                for (GroupMember gm : groupMembers) {
                	//if(gm.getIs_del()==Constant.IS_DEL_NO){
	                	String json="";
	                	json = util.getJSONFromPOJO(gm);
	                	byte[] value = serializer.serialize(json); 
	                	byte[] field = serializer.serialize(gm.getGroup_member_id().toString()); 
	                	
	            		byte[] key1  = serializer.serialize("groupMember:"+gm.getGroup_member_id());  
	                	connection.set(key1, value);    
	                	byte[] key2  = serializer.serialize("groupMemberGroupID:"+gm.getGroup_id());  
	                	connection.hSet(key2, field, value);
	                	// 老师或学生id所在的所有的组
	                	byte[] gKey  = serializer.serialize("memberGroup:"+gm.getMem_id()+":type:"+gm.getType());  
	                	byte[] gField = serializer.serialize(gm.getGroup_id().toString());
	                	Group group = redisGroupDao.getGroupById(gm.getGroup_id(),null);
	                	String gJson = util.getJSONFromPOJO(group);
	                	byte[] gValue = serializer.serialize(gJson); 
	                	connection.hSet(gKey, gField, gValue);
                	//}
				}
                return true;  
            }
        }, false, true);  
        return result;  
	}

	@Override
	public boolean deleteGroupMember(final GroupMember groupMember) {
		boolean result = redisTemplate.execute(new RedisCallback<Boolean>() {  
            public Boolean doInRedis(RedisConnection connection) throws DataAccessException {  
                RedisSerializer<String> serializer = redisTemplate.getStringSerializer();
                byte[] field = serializer.serialize(groupMember.getGroup_member_id().toString()); 
        		byte[] key1  = serializer.serialize("groupMember:"+groupMember.getGroup_member_id());  
            	connection.del(key1);
            	byte[] key2 = serializer.serialize("groupMemberGroupID:"+groupMember.getGroup_id()); 
        		connection.hDel(key2, field);
            	// 老师或学生id所在的所有的组
            	byte[] gKey  = serializer.serialize("memberGroup:"+groupMember.getMem_id()+":type:"+groupMember.getType());  
            	byte[] gField = serializer.serialize(groupMember.getGroup_id().toString());
            	connection.hDel(gKey, gField);
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
 					deleteRedis(connection,serializer,"groupMember:*");
 					deleteRedis(connection,serializer,"groupMemberGroupID:*");
 					deleteRedis(connection,serializer,"memberGroup:*:type:*");
 				} catch (Exception e) {
 					logger.error("=====deleteAllGroupMembers error:"+e.getMessage());
 					return false;
 				}                
                 return true;
            }  
        });  
        return result; 
	}

	@Override
	public GroupMember getGroupMemberById(final Integer GroupMemberId, final boolean needOrgInfo) {
		GroupMember result = redisTemplate.execute(new RedisCallback<GroupMember>() {  
            public GroupMember doInRedis(RedisConnection connection) throws DataAccessException {  
                RedisSerializer<String> serializer = redisTemplate.getStringSerializer();
                GroupMember groupMember = new GroupMember();
				try {
					byte[] key = serializer.serialize("groupMember:"+GroupMemberId);
					byte[] value = connection.get(key);
	                String evalue = serializer.deserialize(value); 
	                if(StringUtils.isNotEmpty(evalue)){
		                groupMember = util.getPojoFromJSON(evalue, GroupMember.class);
		                if(groupMember.getType()==Constant.GROUPMEMBER_TYPE_STUDENT){
		                	Student student = redisStudentDao.getByStudentId(groupMember.getMem_id());
		                	groupMember.setStudent(student);
		                }else if(groupMember.getType()==Constant.GROUPMEMBER_TYPE_TEACHER){
		                	Teacher teacher = redisTeacherDao.getByTechId(groupMember.getMem_id().toString(),null);
		                	groupMember.setTeacher(teacher);
		                }
		                if(needOrgInfo && null!=groupMember.getOrg_id()){
	                		Organization org = redisOrganizationDao.getByOrgId(groupMember.getOrg_id().toString(),null);
	                		groupMember.setOrganization(org);
	                	}
	                }
				} catch (Exception e) {
					logger.error("=====getGroupMemberById error:"+e.getMessage());
					return null;
				}                
                return groupMember;
            }  
        });  
        return result;
	}

	@Override
	public Map<String, Object> getMembers(final Integer groupId, final boolean needOrgInfo) {
		Map<String,Object> result = redisTemplate.execute(new RedisCallback<Map<String,Object>>() {  
            public Map<String,Object> doInRedis(RedisConnection connection) throws DataAccessException {  
                RedisSerializer<String> serializer = redisTemplate.getStringSerializer();
                byte[] key = null;
                key = serializer.serialize("groupMemberGroupID:"+groupId); 
                Map<String,Object> membersMap = new HashMap<String,Object>();
                Map<String, Object> tempMap = new LinkedHashMap<String, Object>();
				try {
					Map<byte[], byte[]> values = connection.hGetAll(key);
					for (Entry<byte[], byte[]> member : values.entrySet()) {
						Map<String, Object> map = new LinkedHashMap<String, Object>();
						String ekey = serializer.deserialize(member.getKey());
						String evaluestr = serializer.deserialize(member.getValue());
						if(StringUtils.isNotEmpty(evaluestr)){
							GroupMember evalue = util.getPojoFromJSON(evaluestr, GroupMember.class);
//							map.put("group_member_id", evalue.getGroup_member_id());
			                if(evalue.getType()==Constant.GROUPMEMBER_TYPE_STUDENT){
			                	Student student = redisStudentDao.getSimpleInfoByStudentId(evalue.getMem_id(),null);
			                	if(student!=null){
			                		map.put("group_member_id", evalue.getGroup_member_id());
				                	map.put("mem_id", student.getStud_id());
				                	map.put("mem_name", student.getStud_name());
				                	map.put("mobile", student.getUser_mobile());
				                	map.put("mem_nick", "");
				                	map.put("user_id", student.getUser_id());
			                	}
			                }else if(evalue.getType()==Constant.GROUPMEMBER_TYPE_TEACHER){
			                	Teacher teacher = redisTeacherDao.getByTechId(evalue.getMem_id().toString(),null);
			                	if(teacher!=null && !teacher.getTech_name().equals("edugate"+teacher.getOrg_id())){
			                		map.put("group_member_id", evalue.getGroup_member_id());
				                	map.put("mem_id", teacher.getTech_id());
				                	map.put("mem_name", teacher.getTech_name());
				                	map.put("mobile", teacher.getUser_mobile());
				                	map.put("mem_nick", teacher.getTech_nick());
				                	map.put("user_id", teacher.getUser_id());
				                	map.put("headurl", teacher.getHeadurl());
				                	map.put("tech_head", teacher.getUser_id());
			                	    if(needOrgInfo && null!=evalue.getOrg_id()){
				                		Organization org = redisOrganizationDao.getByOrgId(teacher.getOrg_id().toString(),null);
				                		map.put("org_id", org.getOrg_id());
				                		map.put("org_name", org.getOrg_name_cn());
					                }
			                	}
			                }
			                if(map.size()>0)
			                	tempMap.put(ekey, map);
						}
					}					
				} catch (Exception e) {
					e.printStackTrace();
					logger.error("=====getMembers error:"+e.getMessage());
					return null;
				}   
				membersMap = Utils.sortMapByKey(tempMap);
                return membersMap;
            }  
        });  
        return result;
	}

	@Override
	public Map<String, Student> getStudentMembers(final String groupId) {
		Map<String,Student> result = redisTemplate.execute(new RedisCallback<Map<String,Student>>() {  
            public Map<String,Student> doInRedis(RedisConnection connection) throws DataAccessException {  
                RedisSerializer<String> serializer = redisTemplate.getStringSerializer();
                byte[] key = serializer.serialize("groupMemberGroupID:"+groupId); 
                Map<String,Student> membersMap = new HashMap<String,Student>();
                Map<String, Student> tempMap = new LinkedHashMap<String, Student>();
				try {
					Map<byte[], byte[]> values = connection.hGetAll(key);
					for (Entry<byte[], byte[]> member : values.entrySet()) {
						String evaluestr = serializer.deserialize(member.getValue());
						GroupMember evalue = util.getPojoFromJSON(evaluestr, GroupMember.class);
						if(null!=evalue){
							Student student = redisStudentDao.getSimpleInfoByStudentId(evalue.getMem_id(),null);
							if(student!=null)
								tempMap.put(student.getStud_id().toString(), student);
						}
					}					
				} catch (Exception e) {
					logger.error("=====getStudentMembers error:"+e.getMessage());
					return null;
				}   
				membersMap = Utils.sortMapByKey(tempMap);
                return membersMap;
            }  
        });  
        return result;
	}

	@Override
	public LinkedHashMap<String, Teacher> getTeacherMembers(final String groupId) {
		LinkedHashMap<String,Teacher> result = redisTemplate.execute(new RedisCallback<LinkedHashMap<String,Teacher>>() {  
            public LinkedHashMap<String,Teacher> doInRedis(RedisConnection connection) throws DataAccessException {  
                RedisSerializer<String> serializer = redisTemplate.getStringSerializer();
                byte[] key = serializer.serialize("groupMemberGroupID:"+groupId); 
//                Map<String,Teacher> membersMap = new HashMap<String,Teacher>();
                TreeMap<String, Teacher> tempMap = new TreeMap<String, Teacher>();
                MapKeyTeacherComparator bvc =  new MapKeyTeacherComparator(tempMap);
				Map<byte[], byte[]> values = connection.hGetAll(key);
				for (Entry<byte[], byte[]> member : values.entrySet()) {
					try {
						String evaluestr = serializer.deserialize(member.getValue());
						if(StringUtils.isNotEmpty(evaluestr)){
							GroupMember evalue = util.getPojoFromJSON(evaluestr, GroupMember.class);
							Teacher teacher = redisTeacherDao.getByTechId(evalue.getMem_id().toString(), null);
							if(teacher!=null && !teacher.getTech_name().equals("edugate"+teacher.getOrg_id()))
								tempMap.put(teacher.getTech_id().toString(), teacher);
						}
					} catch (Exception e) {
						logger.error("=====getTeacherMembers error:"+e.getMessage());
						return null;
					}   
				}		
				LinkedHashMap<String, Teacher> returndata = new LinkedHashMap<String, Teacher>();
				if(tempMap.size()>0){
					TreeMap<String, Teacher> sortMap = new TreeMap<String, Teacher>(bvc);
					sortMap.putAll(tempMap);
					returndata.putAll(sortMap);
					return returndata;
				}else{
					return null;
				}
            }  
        });  
        return result;
	}

	@Override
	public Map<Integer,GroupMember> getStudentGroupMembersById(final String groupId) {
		Map<Integer,GroupMember> result = redisTemplate.execute(new RedisCallback<Map<Integer,GroupMember>>() {  
            public Map<Integer,GroupMember> doInRedis(RedisConnection connection) throws DataAccessException {  
                RedisSerializer<String> serializer = redisTemplate.getStringSerializer();
                byte[] key = null;
                key = serializer.serialize("groupMemberGroupID:"+groupId); 
                Map<Integer,GroupMember> membersMap = new HashMap<Integer,GroupMember>();
				try {
					Map<byte[], byte[]> values = connection.hGetAll(key);
					for (Entry<byte[], byte[]> member : values.entrySet()) {
						String evaluestr = serializer.deserialize(member.getValue());
						if(StringUtils.isNotEmpty(evaluestr)){
							GroupMember evalue = util.getPojoFromJSON(evaluestr, GroupMember.class);
							membersMap.put(evalue.getMem_id(), evalue);
						}
					}					
				} catch (Exception e) {
					e.printStackTrace();
					logger.error("=====getMembers error:"+e.getMessage());
					return null;
				}   
                return membersMap;
            }  
        });  
        return result;
	}

}
