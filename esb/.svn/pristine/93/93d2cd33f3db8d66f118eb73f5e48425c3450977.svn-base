package cn.edugate.esb.redis.dao;

import java.util.List;
import java.util.Map;

import org.springframework.data.redis.connection.RedisConnection;

import cn.edugate.esb.entity.Course;
import cn.edugate.esb.entity.Teacher;
import cn.edugate.esb.entity.TechRange;

public interface RedisTechRangeDao {

	boolean add(TechRange tr);
	
	boolean add(List<TechRange> trs);
	
	boolean delete(TechRange tr);

	boolean deleteAll();

	TechRange getByTechRange(Integer tech_id, Integer rl_id,
			Integer tr_id);

	/**
	 * 获取教师全部范围(用于删除教师时删除全部范围)
	 * @param tech_id
	 * @return
	 */
	List<TechRange> getAllOfTechId(Integer tech_id);
	
	/**
	 * 根据教师主键和角色标签查找所有对象
	 * 部门管理员对应部门，年级组长对应年级。。以此类推
	 */
	List<Map<String,Object>> getIdentityById(String tech_id,Integer rl_id, RedisConnection connection);
	 
	//班级下的 授课教师
	List<Map<String,Object>> getSkClasTech(String clas_id, RedisConnection connection);
	
	
	//班级下的 授课教师
	List<Map<String,Object>> getSkClasCourTech(String clas_id, String cour_id);
	
	//教师授课班级
	List<Map<String,Object>> getSkTechClas(String tech_id);
	
	//年级下课程
	Map<String,Object> getSkGradeCourse(String grade_id);
	
	//年级课程下 班级
	Map<String,Object> getSkGradeCourClas(String grade_id,String cour_id);
	
	//年级课程教师下 班级
	Map<String,Object> getSkGradeCourTechClas(String grade_id,String cour_id,String tech_id);

	//年级下教师
	Map<String,Object> getSkGradeTech(String grade_id,RedisConnection connection);
	
	//学生组下 管理员
	Map<String, Object> getGroupStudManager(String group_id);
	
	//班级下 班主任
	Map<String, Object>  getSkClasBZR(String clas_id, RedisConnection connection);
	
	//部门下 管理员
	Map<String, Object> getDepinfoManager(String dep_id, RedisConnection connection);
	
	//教师组下 管理员
	Map<String, Object> getGroupTechManager(String group_id);

	//获取班级下所有授课老师id
	List<String> getSkClasTechId(final String clas_id);
	
	//年级下 管理员
	Map<String, Object> getGradeTechManager(String grade_id, RedisConnection connection);
	
	List<Map<String,Object>> getManagerGroupById(String tech_id);
	
	List<Map<String,Object>> getIdentityByIds(String tech_id,Map<String,String> rl_ids, RedisConnection connection);
	
	List<Course> getTechCour(Integer tech_id);
	
	List<TechRange> getTechRK(Integer tech_id);
	
	//多班级下 班主任
	Map<String,List<Teacher>>  getSkClasBZRs(String clas_ids);
	
	List<TechRange> getTechRangeByOrg(Integer org_id);
}
