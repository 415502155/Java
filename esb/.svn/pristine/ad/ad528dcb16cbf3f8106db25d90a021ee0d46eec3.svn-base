package cn.edugate.esb.redis.dao;

import java.util.List;
import java.util.Map;

import org.springframework.data.redis.connection.RedisConnection;

import cn.edugate.esb.entity.Student;
import cn.edugate.esb.entity.Teacher;

/**
 * 学生DAO接口
 * Title:RedisStudentDao
 * Description:
 * Company:SJWY
 * @author:Liuy
 * @Date:2017年5月26日上午11:11:25
 */
public interface RedisStudentDao {
	/**
	 * 添加学生
	 * @param student
	 * @return
	 */
	boolean add(Student student);
	/**
	 * 批量添加学生列表
	 * @param students
	 * @return
	 */
	boolean add(List<Student> students);
	/**
	 * 删除学生
	 * @param student
	 * @return
	 */
	boolean delete(Student student);
	/**
	 * 删除全部学生
	 * @return
	 */
	boolean deleteAll();
	/**
	 * 根据主键查询学生信息
	 * @param studentId
	 * @param needDetails 是否需要班级年级校区信息
	 * @return
	 */
	Student getByStudentId(Integer studentId);
	/**
	 * 根据主键查询学生信息
	 * @param studentId
	 * @param needDetails 是否需要班级年级校区信息
	 * @return
	 */
	Student getSimpleInfoByStudentId(Integer studentId,RedisConnection connection);
	/**
	 * 根据机构主键查询学生列表
	 * @param student
	 * @return
	 */
	Map<String,Student> getStudentsByOrgId(Integer orgId);
	/**
	 * 根据班级主键查询学生列表
	 * @param student
	 * @return
	 */
	Map<String,Student> getStudentsByClassId(Integer classId,RedisConnection connection);
	/**
	 * 根据年级主键查询学生列表
	 * @param student
	 * @return
	 */
	Map<String,Student> getStudentsByGradeId(Integer gradeId,RedisConnection connection);
	
	Map<String,Student> getStudentsByOrgIdWX(Integer orgId);
	
	Student getByUserId(String studentId);
}
