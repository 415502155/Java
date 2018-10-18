package com.guoan.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.guoan.demo.entity.Class;
import com.guoan.demo.entity.Student;
import com.guoan.demo.mapper.ClassMapper;

/**
 * 班级操作service
 * @author fan
 *
 */
@Service
@Transactional
public class ClassService {
	
	@Autowired
	private ClassMapper classMapper;
	/**
	 * 添加班级
	 * @param cls
	 * @return
	 */
	public Boolean addStudent(Class cls){
		Boolean flag = true;
		Integer sqlFlag = classMapper.addClass(cls);
		if(sqlFlag != 1){
			flag = false;
		}
		return flag;
	}
	/**
	 * 查询所有班级
	 * @return
	 */
	public List<Class> searchAllClass() {
		return classMapper.searchAllClass();
		
	}
	/**
	 * 修改班级
	 * @param cls
	 * @return
	 */
	public Boolean editClass(Class cls) {
		Boolean flag = true;
		Integer sqlFlag = classMapper.editClass(cls);
		if(sqlFlag != 1){
			flag = false;
		}
		return flag;
	}
	/**
	 * 删除班级
	 * @param cId
	 * @return
	 */
	public Boolean deleteClass(Long cId) {
		Boolean flag = true;
		Integer studentCount = classMapper.searchIsExistStudentByClass(cId);
		if(studentCount != 0){
			flag = false;
		}else{
			Integer sqlFlag = classMapper.deleteClass(cId);
			if(sqlFlag != 1 || studentCount > 0){
				flag = false;
			}
		}
		return flag;
	}
	/**
	 * 查询未添加班级的学生
	 * @return
	 */
	public List<Student> searchNoCheckedClassStudent() {
		return classMapper.searchNoCheckedClassStudent();
	}
	/**
	 * 保存修改学生的班级信息
	 * @param student
	 * @return
	 */
	public Boolean updateStudentClass(Student student) {
		return classMapper.updateStudentClass(student);
	}
}
