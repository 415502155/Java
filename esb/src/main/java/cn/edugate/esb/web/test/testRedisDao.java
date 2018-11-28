package cn.edugate.esb.web.test;

import cn.edugate.esb.entity.UserAccount;

public interface testRedisDao {
	
	UserAccount getUserAccount(String targetid, String version, String type);

}
