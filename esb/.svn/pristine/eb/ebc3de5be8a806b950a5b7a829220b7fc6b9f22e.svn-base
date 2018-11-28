package cn.edugate.esb.redis.dao;

import java.util.List;
import cn.edugate.esb.entity.UcUser;

public interface RedisUcUserDao {

	boolean addUsers(UcUser user);
	
	boolean addUsers(List<UcUser> users);
	
	boolean deleteUsers(UcUser user);

	UcUser getUserById(String id);
	
	UcUser getUserByLoginName(String login_name);

	boolean deleteAll();
	
}
