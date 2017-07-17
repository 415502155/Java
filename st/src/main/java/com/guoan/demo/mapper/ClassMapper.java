package com.guoan.demo.mapper;

import java.util.List;

import com.guoan.demo.entity.Class;
import com.guoan.demo.entity.Student;

/**
 * 学生操作mapper
 * @author fan
 *
 */
public interface ClassMapper {
    /**
     * 添加学生操作
     * @return
     */
	public Integer addClass(Class cls);

	/**
	 * 查询所有的学生
	 * @return
	 */
	public List<Class> searchAllClass();
	
	/**
	 * 修改班级
	 * @param cls
	 * @return
	 */
	public Integer editClass(Class cls);

	/**
	 * 删除班级
	 * @param cId
	 * @return
	 */
	public Integer deleteClass(Long cId);

	/**
	 * 查询未添加班级的学生
	 * @return
	 */
	public List<Student> searchNoCheckedClassStudent();

	/**
	 * 保存修改学生的班级信息
	 * @param student
	 * @return
	 */
	public Boolean updateStudentClass(Student student);

	/**
	 * 查询班级下是否存在学生
	 * @param cId
	 * @return 学生数量
	 */
	public Integer searchIsExistStudentByClass(Long cId);

}
