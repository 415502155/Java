package cn.edugate.esb.redis.dao;

import java.util.List;
import java.util.Map;

import cn.edugate.esb.entity.UcuserOrguser;

public interface RedisUcUserOrguserDao {

	boolean add(UcuserOrguser user);
	
	boolean adds(List<UcuserOrguser> users);
	
	boolean delete(UcuserOrguser user);

	UcuserOrguser getByUserId(String user_id);
	
	Map<String,UcuserOrguser> getByUcId(String uc_id);
	
	boolean deleteAll();
	
}
