package cn.edugate.esb.redis.dao;

import java.util.List;

import cn.edugate.esb.entity.Parent;

public interface RedisParentDao {

	boolean add(Parent user);

	boolean add(List<Parent> users);

	boolean delete(Parent user);

	boolean deleteAll();

	Parent getByParentId(String parentid);

	List<Parent> getParentsByOrgId(Integer org_id);
	public Parent getParentsByUserId(String user_id);
}
