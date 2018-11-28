package sng.controller.web;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import sng.controller.common.BaseAction;
import sng.pojo.Result;
import sng.pojo.ResultEx;
import sng.pojo.SessionInfo;
import sng.service.impl.PlanServiceImpl;
import sng.util.CommonUtils;
import sng.util.Paging;

/**
 * 发送学生或教师通知
 * 
 * @author sunwei
 * 
 */
@RestController
@RequestMapping("/notice")
public class NoticeController extends BaseAction {

	@Resource
	private PlanServiceImpl planServiceImpl;

	/**
	 * 测试用
	 */
	private SessionInfo getSessionInfo() {
		SessionInfo sessionInfo = new SessionInfo();
		sessionInfo.setCamId(1);
		sessionInfo.setIsAuth(1);
		sessionInfo.setLoginName("user1");
		sessionInfo.setOrgId(46);
		sessionInfo.setUserId(11783);
		return sessionInfo;
	}

	/**
	 * 年份列表
	 */
	@RequestMapping("/yearList")
	public Result getYearList(HttpServletRequest request) {
		SessionInfo sessionInfo = this.getSessionInfo(request);
		List<String> yearList = planServiceImpl.getYearList(sessionInfo.getOrgId());
		return ResultEx.success(yearList);
	}

	/**
	 * 显示班级列表
	 */
	@RequestMapping("/getClassList")
	public Result selectCampusClass(HttpServletRequest request, Integer page, Integer limit) {
		SessionInfo sessionInfo = this.getSessionInfo(request);
		Map<String, Object> paramMap = CommonUtils.doParameters(request);
		Paging paging = planServiceImpl.getPageCampusClass(sessionInfo, paramMap, page, limit);
		return ResultEx.success(paging);
	}

	/**
	 * 查询招生计划要选择的班级列表-点击选择全部分页数据
	 */
	@RequestMapping("/selectAllClass")
	public Result selectAllClass(HttpServletRequest request) {
		SessionInfo sessionInfo = this.getSessionInfo(request);
		Map<String, Object> paramMap = CommonUtils.doParameters(request);
		List<String> classIdList = planServiceImpl.selectAllClass(sessionInfo, paramMap);
		return ResultEx.success(classIdList);
	}

	/**
	 * 提交发送学生通知
	 */
	@RequestMapping(value = "/submitStudentNoticeForm", method=RequestMethod.POST)
	public Result getClassData(HttpServletRequest request) {
		SessionInfo sessionInfo = getSessionInfo(request);
		int org_id = sessionInfo.getOrgId();
		String classIds = request.getParameter("classIds");
		String studentStatus = request.getParameter("studentStatus");
		String content = request.getParameter("content");
		
		if (StringUtils.isNoneBlank(String.valueOf(org_id), classIds, studentStatus, content)) {
			
			
			return ResultEx.success("通知发送成功！", null);
		} else {
			return ResultEx.error(0, "请求参数不完整！", null);
		}
	}
	
	
	/**
	 * 查询学生通知发送记录
	 */
	@RequestMapping("/getStudentNoticeSendHistory")
	public Result getStudentNoticeSendHistory(HttpServletRequest request) {
		
		
		
		return null;
	}

}
