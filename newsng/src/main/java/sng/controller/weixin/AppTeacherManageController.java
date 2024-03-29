package sng.controller.weixin;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import sng.controller.common.BaseAction;
import sng.pojo.ParamObj;
import sng.pojo.Result;
import sng.service.AppTeacherService;

/**
 * @desc 手机--教师端
 * @author magq
 * @data 2018-10-26
 * @version 1.0
 */
@Controller
@RequestMapping("/mobile/teacherApp")
public class AppTeacherManageController extends BaseAction {
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	
	@Autowired
	private AppTeacherService appTeacherService;

	/**
	 * 获取职教班级
	 * 
	 * @param request
	 * @param response
	 * @param paramObj
	 * @throws Exception
	 */
	@RequestMapping(value = "/queryClassByTeacher.json", method = RequestMethod.POST, produces = "application/*")
	@ResponseBody
	public Result queryClassByTeacher(HttpServletRequest request, HttpServletResponse response, ParamObj paramObj)
			throws Exception {
		Result result = appTeacherService.queryClassByTeacher(paramObj);
		return result;
	}

	/**
	 * 按班级查询学员列表
	 * 
	 * @param request
	 * @param response
	 * @param paramObj
	 * @throws Exception
	 */
	@RequestMapping(value = "/queryStudentByClass.json", method = RequestMethod.POST, produces = "application/*")
	@ResponseBody
	public Result queryStudentByClass(HttpServletRequest request, HttpServletResponse response, ParamObj paramObj)
			throws Exception {
		Result result = appTeacherService.queryStudentByClass(paramObj);
		return result;
	}

	/**
	 * 学员获取家长电话
	 * 
	 * @param request
	 * @param response
	 * @param paramObj
	 * @throws Exception
	 */
	@RequestMapping(value = "/queryTelNumByStudent.json", method = RequestMethod.POST, produces = "application/*")
	@ResponseBody
	public Result queryTelNumByStudent(HttpServletRequest request, HttpServletResponse response, ParamObj paramObj)
			throws Exception {
		Result result = appTeacherService.queryParentInfoByStu(paramObj);
		return result;

	}

}
