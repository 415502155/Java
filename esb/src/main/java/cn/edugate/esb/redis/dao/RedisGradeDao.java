package cn.edugate.esb.redis.dao;

import java.util.List;
import java.util.Map;

import org.springframework.data.redis.connection.RedisConnection;

import cn.edugate.esb.entity.Grade;

/**
 * 年级DAO接口
 * Title:RedisGradeDao
 * Description:
 * Company:SJWY
 * @author:Liuy
 * @Date:2017年5月22日上午10:28:42
 */
public interface RedisGradeDao {
	/**
	 * 添加年级
	 * @param grade
	 * @return
	 */
	boolean addGrade(Grade grade);
	/**
	 * 批量添加年级(For Redis)
	 * @param grades
	 * @return 
	 */
	boolean addGrades(List<Grade> grades);
	/**
	 * 删除年级
	 * @param grade
	 * @return
	 */
	boolean deleteGrade(Grade grade);
	/**
	 * 无条件的删除全部(For Redis)
	 * @return 
	 */
	boolean deleteAllGrades();
	/**
	 * 根据主键获取年级
	 * @param GradeId
	 * @return
	 */
	Grade getGradeById(Integer GradeId,RedisConnection connection);
	/**
	 * 根据机构主键获取年级
	 * @param orgId
	 * @return
	 */
	List<Grade> getGrades(Integer orgId);
	/**
	 * 根据班级主键获取年级
	 * @param classId
	 * @return
	 */
	Grade getGradeByClassId(Integer classId);
	/**
	 * 查询机构下的年级及其全套附属信息(年级组长、下属班级、班主任、任课教师)
	 * @param orgId
	 * @return
	 */
	List<Grade> getGradesWithAllInfo(Integer orgId);
	
	Map<String,Grade> getGrades(Map<String,Object> gradeIds);
}
