package cn.edugate.esb.redis.dao;

import java.util.List;
import java.util.Map;

import org.springframework.data.redis.connection.RedisConnection;

import cn.edugate.esb.entity.Classes;
import cn.edugate.esb.entity.Grade;

/**
 * 班级DAO接口
 * Title:RedisClassesDao
 * Description:
 * Company:SJWY
 * @author:Liuy
 * @Date:2017年5月22日上午10:28:42
 */
public interface RedisClassesDao {
	/**
	 * 添加班级
	 * @param classes
	 * @return
	 */
	boolean addClasses(Classes classes);
	/**
	 * 批量添加班级(For Redis)
	 * @param classess
	 * @return 
	 */
	boolean addClassess(List<Classes> classess);
	/**
	 * 删除班级
	 * @param classes
	 * @return
	 */
	boolean deleteClasses(Classes classes);
	/**
	 * 无条件的删除全部(For Redis)
	 * @return 
	 */
	boolean deleteAllClassess();
	/**
	 * 根据主键获取班级
	 * @param ClassesId
	 * @return
	 */
	Classes getClassesById(String ClassesId,RedisConnection connection);
	/**
	 * 根据查询条件获取班级信息
	 * @param orgId       【必填】机构主键
	 * @param campusId    [选填] 校区主键
	 * @param gradeNumber [选填] 年级代码
	 * @param isGraduated [选填] 是否毕业
	 * @return
	 */
	//Map<String, Classes> getClasses(Integer orgId, Integer campusId, Integer gradeNumber, Integer isGraduated);
	List<Classes> getClassesOfOrg(int orgId);
	
	List<Classes> getClassesOfGrade(int gradeId,RedisConnection connection);
	
	List<Classes> getClassesOfCampus(int campusId);
	
	/**
	 * 根据学生主键查询班级列表
	 * @param stud_id
	 * @return
	 */
	//List<Classes> getClassesByStudId(Integer stud_id);
	
	
	Grade getGradesByCid(Integer clas_id);
	
	Map<String, Classes> getClassesOfGIds(Map<String,Grade> gradeIds,Map<String,Object> cmap);
}
