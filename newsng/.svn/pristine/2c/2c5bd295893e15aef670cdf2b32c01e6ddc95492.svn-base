package sng.controller.weixin;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import sng.controller.common.BaseAction;
import sng.pojo.ParamObj;
import sng.pojo.Result;
import sng.pojo.ResultEx;
import sng.pojo.SessionInfo;
import sng.pojo.base.Teacher;
import sng.service.AppTeacherService;
import sng.service.ClassService;
import sng.service.TeacherService;
import sng.service.impl.ReportTecherServiceImpl;
import sng.util.Constant;


@RestController
@RequestMapping("/wechat/portal/echart")
public class EchartController extends BaseAction{

	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Resource
	private ReportTecherServiceImpl reportTecherServiceImpl;
	@Autowired
	private TeacherService teacherService;
	@Autowired
	private ClassService classService;
	@Autowired
	private AppTeacherService appTeacherService;
	
	/**
	 * 教师排名前10
	 * @param termId
	 * @param camId
	 * @throws Exception 
	 */
	@RequestMapping(value="/rtTop10")
	@ResponseBody
	public Result reportTecherTop10(HttpServletRequest request,int termId,String camId) throws Exception {
		SessionInfo sessionInfo=super.getSessionInfo(request);
		List<Map<String, Object>> list=reportTecherServiceImpl.reportTecherTop10(sessionInfo,termId, camId);
		return ResultEx.success(list);
	}
	
	/**
	 * 所有教师统计列表
	 * @param termId
	 * @param camId
	 * @throws Exception 
	 */
	@RequestMapping(value="/rtTopAll")
	@ResponseBody
	public Result reportTecherAll(HttpServletRequest request,int termId,String camId,String techName) throws Exception {
		SessionInfo sessionInfo=super.getSessionInfo(request);
		List<Map<String, Object>> list=reportTecherServiceImpl.reportTecherAll(sessionInfo,termId, camId,techName);
		return ResultEx.success(list);
	}
	
	/**
	 * 查看注册率详情
	 */
	@RequestMapping(value = "/regRateDetail.json")
	@ResponseBody
	public Result<List<Teacher>> regRateDetail(HttpServletRequest request,
			@RequestParam(name = "cur_term_id") Integer cur_term_id,
			@RequestParam(name = "cur_cam_id") String cur_cam_id,
			@RequestParam(name = "pre_term_id") Integer pre_term_id,
			HttpServletResponse response, HttpSession session) throws Exception {
		Result<List<Teacher>> result = new Result<List<Teacher>>();
		List<Teacher> list = teacherService.regRateDetail(cur_term_id, cur_cam_id, pre_term_id);
		result.setData(list);
		result.setSuccess(true);
		return result;
	}

	/**
	 * 查看流失率详情
	 */
	@RequestMapping(value = "/wastageRateDetail.json")
	@ResponseBody
	public Result<List<Teacher>> wastageRateDetail(HttpServletRequest request,
			@RequestParam(name = "term_id") Integer term_id,
			@RequestParam(name = "cam_id") String cam_id,
			HttpServletResponse response, HttpSession session) throws Exception {
		Result<List<Teacher>> result = new Result<List<Teacher>>();
		List<Teacher> list = teacherService.wastageRateDetail(term_id, cam_id);
		result.setData(list);
		result.setSuccess(true);
		return result;
	}
	
	
	/**
	 * 更具种类和科目分别统计学费数、 班级数、学员数、注册率、流失率
	 * @author magq
	 */
	@RequestMapping(value = "/categoryAndSubjectEchart.json")
	@ResponseBody
	public Result wastageRateDetail(HttpServletRequest request,
			HttpServletResponse response, ParamObj paramObj) throws Exception {
		Result result = appTeacherService.echartsInfo(paramObj);
		return result;
	}
	
	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "/clasAndStu")
	@ResponseBody
	public Result getclasAndStu(HttpServletRequest request, Integer org_id, Integer termId, String camId, HttpServletResponse response) {
		SessionInfo sessionInfo = this.getSessionInfo(request);
		Integer orgId = org_id;
		if (null == orgId) {
			orgId = sessionInfo.getOrgId();
		}
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			map = classService.getStatisticsInfoByTermAndCampus(orgId, termId, camId, null);
		} catch (Exception e) {
			e.printStackTrace();
			logger.info("getclasAndStu Ex : " + e);
			return ResultEx.error(Constant.ERROR_CODE, Constant.ERROR_MSG, "");
		}
		return ResultEx.success(map);
	}
	
	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "/clasAndStuComparison")
	@ResponseBody
	public Result termCampusStatisticalComparison(HttpServletRequest request, Integer org_id, Integer termId1, String camId1,
			Integer termId2, String camId2, HttpServletResponse response) {
		SessionInfo sessionInfo = this.getSessionInfo(request);
		Integer orgId = org_id;
		if (null == orgId) {
			orgId = sessionInfo.getOrgId();
		}
		Map<String, Object> map = new HashMap<String, Object>();
		Integer clasType = 1;
		try {
			map = classService.termCampusStatisticalComparison(orgId, termId1, camId1, termId2, camId2, clasType);
		} catch (Exception e) {
			e.printStackTrace();
			logger.info("termCampusStatisticalComparison Ex : " + e);
			return ResultEx.error(Constant.ERROR_CODE, Constant.ERROR_MSG, "");
		}
		return ResultEx.success(map);
	}
}
