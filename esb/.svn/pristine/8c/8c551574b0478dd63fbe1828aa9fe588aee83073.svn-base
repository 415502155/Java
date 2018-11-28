package cn.edugate.esb.redis.dao;

import java.util.List;

import cn.edugate.esb.entity.Campus;

public interface RedisCampusDao {
	boolean add(Campus tr);
	
	boolean add(List<Campus> trs);
	
	boolean delete(Campus tr);

	boolean deleteAll();
	
	List<Campus> getCampusByOrgId(String org_id);
	
	Campus getById(Integer id);
}
