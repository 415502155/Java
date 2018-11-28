package cn.edugate.esb.redis.dao;

import java.util.List;
import java.util.Map;

import cn.edugate.esb.authentication.FP;
import cn.edugate.esb.entity.Right;

public interface RedisRightDao {

	boolean add(Right tr);
	
	boolean add(List<Right> trs);
	
	boolean delete(Right tr);

	boolean deleteAll();

	Right getByRightId(String right_id);

	Map<String, FP> getAllFp();
	
}
