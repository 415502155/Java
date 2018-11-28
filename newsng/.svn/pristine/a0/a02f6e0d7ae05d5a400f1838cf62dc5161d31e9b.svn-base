package sng.controller.web;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import sng.pojo.Result;
import sng.pojo.StudentPojo;
import sng.service.ChargeDetailService;
import sng.service.ChargeRecordService;
import sng.service.ChargeService;
import sng.service.ClassService;
import sng.service.StudentClassService;
import sng.service.StudentService;
import sng.util.Constant;
import sng.util.Paging;
import sng.controller.common.BaseAction;
import sng.entity.Classes;

@Controller
@RequestMapping("/student")
public class StudentController extends BaseAction{
	
	@Autowired
	private ChargeService chargeService;
	@Autowired
	private ChargeDetailService chargeDetailService;
	@Autowired
	private ChargeRecordService chargeRecordService;
	@Autowired
	private StudentService studentService;
	@Autowired
	private ClassService classService;
	@Autowired
	private StudentClassService studentClassService;
	@Autowired
	private RabbitTemplate rabbitTemplate;
	
	/**
	 * @Title : getSutdentList 
	 * @功能描述:查询学员列表 
	 * @返回类型：Result<Paging<StudentPojo>> 
	 * @throws ：
	 */
	@RequestMapping(value = "/getSutdentList.json")
	@ResponseBody
	public Result<Paging<StudentPojo>> getSutdentList(HttpServletRequest request,
			@RequestParam(name = "currentPageNum", defaultValue="1") Integer currentPageNum,
			@RequestParam(name = "pageSize", defaultValue=Constant.NUM_PER_PCPAGE_STRING) Integer pageSize,
			@RequestParam(name = "term_id",defaultValue="") String term_id,
			@RequestParam(name = "category_id",defaultValue="") String category_id,
			@RequestParam(name = "subject_id",defaultValue="") String subject_id,
			@RequestParam(name = "clas_type",defaultValue="") String clas_type,
			@RequestParam(name = "cam_id",defaultValue="") String cam_id,
			@RequestParam(name = "stu_status",defaultValue="") String stu_status,
			@RequestParam(name = "pay_method",defaultValue="") String pay_method,
			@RequestParam(name = "is_print",defaultValue="") String is_print,
			@RequestParam(name = "keyword",defaultValue="") String keyword,
			HttpServletResponse response, HttpSession session) throws Exception {
		Result<Paging<StudentPojo>> result = new Result<Paging<StudentPojo>>();
		Paging<StudentPojo> page = new Paging<StudentPojo>();
		page.setPage(currentPageNum);
		page.setLimit(pageSize);
		page = studentService.queryList(term_id,category_id,subject_id,clas_type,cam_id,stu_status,pay_method,is_print,keyword,page);
		result.setData(page);
		result.setSuccess(true);
		return result;
	}
	
	/**
	 * @Title : getSutdentList 
	 * @功能描述:查询学员列表 
	 * @返回类型：Result<Paging<StudentPojo>> 
	 * @throws ：
	 */
	@RequestMapping(value = "/getClassesList.json")
	@ResponseBody
	public Result<List<Classes>> getClassesList(HttpServletRequest request,
			@RequestParam(name = "stu_class_id") Integer stu_class_id,
			HttpServletResponse response, HttpSession session) throws Exception {
		Result<List<Classes>> result = new Result<List<Classes>>();
		List<Classes> list = classService.queryListForExchangeClass(stu_class_id);
		result.setData(list);
		result.setSuccess(true);
		return result;
	}

}
