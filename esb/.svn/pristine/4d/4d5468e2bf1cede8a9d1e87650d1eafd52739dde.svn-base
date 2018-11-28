package cn.edugate.esb.redis.dao;

import java.util.List;
import java.util.Map;

import org.codehaus.jackson.map.ObjectMapper;

import cn.edugate.esb.entity.OrgUser;

public interface RedisOrgUserDao {

	boolean addUsers(OrgUser user);
	
	boolean addUsers(List<OrgUser> users);
	
	boolean deleteUsers(OrgUser user);

	OrgUser getUserById(String id);
	
	OrgUser getUserByLoginName(String login_name, Integer org_type);
	
	Map<String,OrgUser> getUserByLoginName(String login_name);

	boolean deleteAll();

	List<OrgUser> getUserByOrgId(Integer id);

	
	OrgUser getUserByLoginNameWX(String login_name,Integer org_id,String identity);
	
	String getUserOpenidById(String id);
	
	OrgUser getUserByOpenId(String openId, Integer org_id, Integer identity);
}
