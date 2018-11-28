package cn.edugate.esb.redis.dao;

import java.util.List;
import java.util.Map;

import cn.edugate.esb.entity.TeacherRole;

public interface RedisTeacherRoleDao {

	boolean add(TeacherRole tr);
	
	boolean add(List<TeacherRole> trs);
	
	boolean delete(TeacherRole tr);

	boolean deleteAll();

	TeacherRole getByTeach2role_id(String teach2role_id);
	
	Map<String,TeacherRole> getByTeacherId(String tech_id);

	Map<String, TeacherRole> getByRole_id(Integer role_id);
	
}
