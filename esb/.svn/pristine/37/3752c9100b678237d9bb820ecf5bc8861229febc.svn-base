package cn.edugate.esb.redis.dao;

import java.util.List;

import org.springframework.data.redis.connection.RedisConnection;

import cn.edugate.esb.entity.Course;

public interface RedisCourseDao {
    
	boolean add(Course tr);
	
	boolean add(List<Course> trs);
	
	boolean delete(Course tr);

	boolean deleteAll();
	
	List<Course> getCoursByOrgId(String org_id);
	
	Course getCourseById(String cour_id,RedisConnection connection);
}
