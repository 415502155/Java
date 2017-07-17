package com.guoan.demo.mapper;

import java.util.List;

import com.guoan.demo.entity.Class;
import com.guoan.demo.entity.Student;
import com.guoan.demo.vo.StudentVo;

/**
 * 学生操作mapper
 * @author fan
 *
 */
public interface StudentMapper {
    /**
     * 添加学生操作
     * @return
     */
	public Integer addStudent(Student student);

	/**
	 * 查询所有的学生
	 * @return
	 */
	public List<StudentVo> searchAllStudent();

	/**
	 * 获取班级信息
	 * @return
	 */
	public List<Class> getClassList();

	/**
	 * 修改学生姓名
	 * @param student
	 * @return
	 */
	public Boolean editStudent(Student student);

	/**
	 * 删除学生
	 * @param sId
	 * @return
	 */
	public Boolean deleteStudent(Long sId);

	/**
	 * 通过班级查询学生
	 * @param type
	 * @return
	 */
	public List<StudentVo> searchStudentByClass(Integer cId);

	/**
	 * 根据学生姓名查询学生
	 * @param name
	 * @return
	 */
	public List<StudentVo> searchStudentByName(String name);

}
