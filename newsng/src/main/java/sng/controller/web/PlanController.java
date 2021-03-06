package sng.controller.web;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import sng.controller.common.BaseAction;
import sng.entity.Plan;
import sng.pojo.Result;
import sng.pojo.ResultEx;
import sng.pojo.SessionInfo;
import sng.service.impl.PlanServiceImpl;
import sng.util.CommonUtils;
import sng.util.Paging;

/**
 * 招生计划
 * @author s
 *
 */
@Controller
@RequestMapping("/plan")
public class PlanController extends BaseAction{
	@Resource
	private PlanServiceImpl planServiceImpl;

	/**
	 * 测试用
	 */
	private SessionInfo getSessionInfo() {
		SessionInfo sessionInfo=new SessionInfo();
		sessionInfo.setLoginName("user1");
		sessionInfo.setOrgId(46);
		sessionInfo.setUserId(11783);
		return sessionInfo;
	}

	/**
	 * 年份列表
	 */
	@RequestMapping("/yearList")
	@ResponseBody
	public Result getYearList(HttpServletRequest request) {
		SessionInfo sessionInfo=this.getSessionInfo(request);
		List<String> yearList=planServiceImpl.getYearList(sessionInfo.getOrgId());
		return ResultEx.success(yearList);
	}
	
	/**
	 * 招生计划列表
	 * @throws Exception 
	 */
	@RequestMapping("/list")
	@ResponseBody
	public Result getPagePlan(HttpServletRequest request,String year,Integer page,Integer limit) throws Exception {
		SessionInfo sessionInfo=this.getSessionInfo(request);
		Paging paging=planServiceImpl.getPagePlan(sessionInfo.getOrgId(),year, page, limit);
		return ResultEx.success(paging);
	}
	
	/**
	 * 查询招生计划详情
	 */
	@RequestMapping("/info")
	@ResponseBody
	public Result getPlanInfo(HttpServletRequest request,Integer planId) {
		Map<String,Object> paramMap=CommonUtils.doParameters(request);
		Plan plan=planServiceImpl.getPlanInfo(planId,paramMap);
		return ResultEx.success(plan);
	}
	
	/**
	 * 保存/更新招生计划
	 */
	@RequestMapping("/save")
	@ResponseBody
	public Result savePlan(HttpServletRequest request,Plan plan,String classIds) {
		SessionInfo sessionInfo=this.getSessionInfo(request);
		try {
			Map<String,Object> paramMap=CommonUtils.doParameters(request);
			planServiceImpl.savePlan(sessionInfo,plan,classIds,paramMap);
			return ResultEx.success(null);
		}catch(Exception ex) {
			ex.printStackTrace();
			return ResultEx.error(500, "保存失败", null);
		}
	}
	
	/**
	 * 查询招生计划要选择的班级列表
	 */
	@RequestMapping("/selectCampusClass")
	@ResponseBody
	public Result selectCampusClass(HttpServletRequest request,Integer page,Integer limit) {
		SessionInfo sessionInfo=this.getSessionInfo(request);
		Map<String,Object> paramMap=CommonUtils.doParameters(request);
		Paging paging=planServiceImpl.getPageCampusClass(sessionInfo,paramMap, page, limit);
		return ResultEx.success(paging);
	}
	
	/**
	 * 查询招生计划要选择的班级列表-点击选择全部分页数据
	 */
	@RequestMapping("/selectAllClass")
	@ResponseBody
	public Result selectAllClass(HttpServletRequest request) {
		SessionInfo sessionInfo=this.getSessionInfo(request);
		Map<String,Object> paramMap=CommonUtils.doParameters(request);
		List<String> classIdList=planServiceImpl.selectAllClass(sessionInfo, paramMap);
		return ResultEx.success(classIdList);
	}
	
	/**
	 * 查询招生计划要选择的班级列表-列表下面的统计
	 */
	@RequestMapping("/classData")
	@ResponseBody
	public Result getClassData(HttpServletRequest request) {
		SessionInfo sessionInfo=this.getSessionInfo(request);
		Map<String,Object> paramMap=CommonUtils.doParameters(request);
		Map<String,Object> map=planServiceImpl.getClassData(sessionInfo, paramMap);
		return ResultEx.success(map);
	}
	
	/**
	 * 开启关闭招生计划
	 * @param request
	 */
	@RequestMapping("/updatePlanSwitch")
	@ResponseBody
	public Result updatePlanSwitch(HttpServletRequest request,Integer planId) {
		try {
			Map<String,Object> paramMap=CommonUtils.doParameters(request);
			int canEdit=planServiceImpl.updatePlanSwitch(planId,paramMap);
			if(canEdit==1) {
				return ResultEx.success(null);
			}else {
				return ResultEx.error(403, "没有修改权限", null);
			}
		}catch(Exception ex) {
			ex.printStackTrace();
			return ResultEx.error(500, "保存失败", null);
		}
	}

}
