package com.guoan.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.guoan.demo.entity.Class;
import com.guoan.demo.entity.Student;
import com.guoan.demo.mapper.StudentMapper;
import com.guoan.demo.vo.StudentVo;


@Service
@Transactional
public class StudentService {
	
	@Autowired
	private StudentMapper studentMapper;
	/**
	 * 添加学生操作
	 * @param student
	 */
	public Boolean addStudent(Student student) {
		Boolean flag = true;
		Integer sqlFlag = studentMapper.addStudent(student);
		if(sqlFlag != 1){
			flag = false;
		}
		return flag;
	}
	/**
	 * 查询所有的学生
	 * @param type 
	 * @return
	 */
	public List<StudentVo> searchAllStudent(Integer cId) {
		if(cId != 0){
			return studentMapper.searchStudentByClass(cId);
		}
		return studentMapper.searchAllStudent();
		
	}
	/**
	 * 获取班级信息
	 * @return
	 */
	public List<Class> getClassList() {
		return studentMapper.getClassList();
	}
	/**
	 * 修改学生姓名
	 * @param student
	 * @return
	 */
	public Boolean editStudent(Student student) {
		return studentMapper.editStudent(student);
	}
	/**
	 * 删除学生
	 * @param sId
	 * @return
	 */
	public Boolean deleteStudent(Long sId) {
		return studentMapper.deleteStudent(sId);
	}
	/**
	 * 根据学生姓名查询学生
	 * @param name
	 * @return
	 */
	public List<StudentVo> searchStudentByName(String name) {
		return studentMapper.searchStudentByName(name);
	}
}
