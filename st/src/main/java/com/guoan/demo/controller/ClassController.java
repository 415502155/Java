package com.guoan.demo.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.guoan.demo.entity.Class;
import com.guoan.demo.entity.Student;
import com.guoan.demo.service.ClassService;

@Controller
@RequestMapping(value = "/class")
public class ClassController extends BaseController {
	
	@Autowired
	private ClassService classService;
	/**
	 * 跳转到班级管理页面
	 * @return
	 */
	@RequestMapping(value = "/index", method = RequestMethod.GET)
	public String index(){
		return "class";
	}
	
	/**
	 * 添加班级	
	 * @param request
	 * @param response
	 * @param student 客户端传递封已封装的班级对象
	 */
	@RequestMapping(value = "/addClass",method = RequestMethod.POST)
	public void addStudent(HttpServletRequest request,HttpServletResponse response, @RequestBody Class cls){
		Boolean flag = classService.addStudent(cls);
		this.printJson(flag, response);
	}
	
	/**
	 * 查询所有班级
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = "/searchAllClass",method = RequestMethod.GET)
	public void getAllStudent(HttpServletRequest request,HttpServletResponse response){
		List<Class> classList = classService.searchAllClass();
		this.printJson(classList, response);
	}
	
	/**
	 * 修改班级
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = "/editClass",method = RequestMethod.POST)
	public void editClass(HttpServletRequest request,HttpServletResponse response,@RequestBody Class cls){
		Boolean flag = classService.editClass(cls);
		this.printJson(flag, response);
	}
	
	/**
	 * 删除班级
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = "/deleteClass",method = RequestMethod.GET)
	public void deleteClass(HttpServletRequest request,HttpServletResponse response,Long cId){
		Boolean flag = classService.deleteClass(cId);
		this.printJson(flag, response);
	}
	
	/**
	 * 查询未添加班级的学生
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = "/searchNoCheckedClassStudent",method = RequestMethod.GET)
	public void searchNoCheckedClassStudent(HttpServletRequest request,HttpServletResponse response){
		List<Student> studentList = classService.searchNoCheckedClassStudent();
		this.printJson(studentList, response);
	}
	/**
	 * 保存修改学生的班级信息
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = "/updateStudentClass",method = RequestMethod.POST)
	public void updateStudentClass(HttpServletRequest request,HttpServletResponse response,@RequestBody Student student){
		Boolean flag = classService.updateStudentClass(student);
		this.printJson(flag, response);
	}
	

}
