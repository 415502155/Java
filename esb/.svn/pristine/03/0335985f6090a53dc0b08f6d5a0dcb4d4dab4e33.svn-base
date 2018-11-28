package cn.edugate.esb.dao;

import java.util.List; 

import cn.edugate.esb.entity.Course;

public interface CourseDao extends BaseDAO<Course> {

	public abstract List<Course> getCourseAll();

	public abstract int checkName(int orgID, String courseName);

	/**
	 * 根据机构查询所有课程
	 * @param org_id
	 * @return
	 */
	public abstract List<Course> getCourseList(int org_id);
	
	
	public abstract List<Course> getCourseListIncludeDeleted(int org_id);
}
