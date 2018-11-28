package cn.edugate.esb.service;

import java.util.List;

import cn.edugate.esb.entity.Course;

public interface CourseService {
 
	/**
	 * 获得所有课程列表
	 * 
	 * @param function
	 * @return
	 */
	public abstract List<Course> getAll();

	/**
	 * 更新机构下某课程的名称
	 * 
	 * @param orgID
	 * @param courseID
	 * @param courseName
	 * @return
	 */
	public abstract int updateCourseName(int orgID, int courseID, String courseName);

	/**
	 * 检查机构下某名称的课程是否存在
	 * 
	 * @param orgID
	 * @param courseID
	 * @param courseName
	 * @return
	 */
	public abstract int checkName(int orgID, String courseName);
	
	/**
	 * 机构下保存课程
	 * @param orgID
	 * @param courseName
	 * @return
	 */
	public abstract int saveCourse(int orgID, String courseName);

	/**
	 * 查询机构下课程
	 * @param org_id
	 * @return
	 */
	public abstract List<Course> getByOrgId(String org_id);
}
