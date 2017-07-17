package com.guoan.demo.controller;

import java.util.List;
import com.guoan.demo.controller.BaseController;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.guoan.demo.entity.Class;
import com.guoan.demo.entity.Student;
import com.guoan.demo.service.StudentService;
import com.guoan.demo.vo.StudentVo;

@Controller
@RequestMapping(value = "/student")
public class StudentController extends BaseController {

	@Autowired
	private StudentService studentService;

	@RequestMapping(value = "/index", method = RequestMethod.GET)
	public String index() {
		return "student";
	}

	/**
	 * 添加学生操作	
	 * @param request
	 * @param response
	 * @param student 客户端传递封装好的学生对象
	 */
	@RequestMapping(value = "/addStudent",method = RequestMethod.POST)
	public void addStudent(HttpServletRequest request,HttpServletResponse response, @RequestBody Student student) {
		Boolean flag = studentService.addStudent(student);
		this.printJson(flag, response);
	}
	/**
	 * 查询所有学生操作
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = "/searchAllStudent",method = RequestMethod.GET)
	public void getAllStudent(HttpServletRequest request,HttpServletResponse response,Integer cId){
		List<StudentVo> studentList = studentService.searchAllStudent(cId);
		this.printJson(studentList, response);
	}
	/**
	 * 获取班级信息列表
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = "/getClassList",method = RequestMethod.GET)
	public void getClassList(HttpServletRequest request,HttpServletResponse response){
		List<Class> classList = studentService.getClassList();
		this.printJson(classList, response);
	}
	/**
	 * 修改学生姓名
	 */
	@RequestMapping(value = "/editStudent",method = RequestMethod.POST)
	public void editStudent(HttpServletRequest request,HttpServletResponse response,@RequestBody Student student){
		Boolean flag = studentService.editStudent(student);
		this.printJson(flag, response);
	}
	/**
	 * 删除学生
	 */
	@RequestMapping(value = "/deleteStudent",method = RequestMethod.GET)
	public void deleteStudent(HttpServletRequest request,HttpServletResponse response,Long sId){
		Boolean flag = studentService.deleteStudent(sId);
		this.printJson(flag, response);
	}
	/**
	 * 根据学生姓名模糊查询
	 */
	@RequestMapping(value = "/searchStudentByName",method = RequestMethod.GET)
	public void searchStudentByName(HttpServletRequest request,HttpServletResponse response,String name){
		List<StudentVo> studentList = studentService.searchStudentByName(name);
		this.printJson(studentList, response);
	}
}
