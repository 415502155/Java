package cn.edugate.esb.redis.dao;

import java.util.List;
import java.util.Map;

import cn.edugate.esb.entity.Parent;
import cn.edugate.esb.entity.Student;
import cn.edugate.esb.entity.StudentParent;

public interface RedisStudentParentDao {

	boolean add(StudentParent user);
	
	boolean add(List<StudentParent> users);
	
	boolean delete(StudentParent user);

	boolean deleteAll();
	

	StudentParent getByStud2parent_id(String stud2parent_id);

	Map<String,StudentParent> getByStudentId(String stud_id);
	
	List<Student> getStudsByPId(String parent_id);
	List<Parent> getParentsBySId(String stud_id);
	List<StudentParent> getStudParentsByPId(String parent_id);
	List<StudentParent> getStudParentByOrgId(String org_id);
}
