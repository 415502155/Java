package cn.edugate.esb.redis.dao;

import java.util.List;
import java.util.Map;

import cn.edugate.esb.entity.Role;

public interface RedisRoleDao {

	boolean add(Role tr);
	
	boolean add(List<Role> trs);
	
	boolean delete(Role tr);

	boolean deleteAll();

	Role getByRoleId(String roleid);

	Map<String, Role> getByOrgId(Integer org_id);
	
}
