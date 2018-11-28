package cn.edugate.esb.redis.dao;

import java.util.List;
import cn.edugate.esb.entity.UserAccount;

public interface RedisUserAccountDao {

	boolean add(UserAccount user);
	
	boolean add(List<UserAccount> users);
	
	boolean delete(UserAccount user);

	boolean deleteAll();

	UserAccount getUserAccount(String targetid, String version, String type);
	
}
