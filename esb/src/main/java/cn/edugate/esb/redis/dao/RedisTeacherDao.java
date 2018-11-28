package cn.edugate.esb.redis.dao;

import java.util.List;

import org.springframework.data.redis.connection.RedisConnection;

import cn.edugate.esb.entity.Teacher;

public interface RedisTeacherDao {
	
	boolean add(Teacher tr);
	
	boolean add(List<Teacher> trs);
	
	boolean delete(Teacher tr);

	boolean deleteAll();

	Teacher getByTechId(String tech_id,RedisConnection connection);
	
	List<Teacher> getTechsByDepId(String dep_id, RedisConnection connection);

	Teacher getByUserId(String string);

	/**
	 * 根据机构主键查询教师列表
	 * @param org_id
	 * @param object
	 * @return
	 */
	List<Teacher> getTeacherListByOrgId(String org_id, RedisConnection connection);
	
	List<Teacher> getTechMapListByOrgId(String org_id, RedisConnection connection);
	
}
