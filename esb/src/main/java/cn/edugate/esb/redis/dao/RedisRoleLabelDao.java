package cn.edugate.esb.redis.dao;

import java.util.List;

import cn.edugate.esb.entity.RoleLabel;

public interface RedisRoleLabelDao {

	boolean add(RoleLabel tr);
	
	boolean add(List<RoleLabel> trs);
	
	boolean delete(RoleLabel tr);

	boolean deleteAll();

	RoleLabel getById(String rl_id);
}
