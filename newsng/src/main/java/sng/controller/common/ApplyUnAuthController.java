package sng.controller.common;

import java.util.Date;
import java.util.HashMap;
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
import sng.service.impl.ApplyServiceImpl;
import sng.util.CommonUtils;
import sng.util.Paging;

/**
 * 报名-未认证时
 * @author s
 *
 */
@Controller
@RequestMapping("/apply")
public class ApplyUnAuthController extends BaseAction{
	@Resource
	private ApplyServiceImpl applyServiceImpl;

	/**
	 * 招生计划列表-家长报名
	 */
	@RequestMapping("/plan/list")
	@ResponseBody
	public Result getPlanList(HttpServletRequest request) {
		SessionInfo sessionInfo=this.getSessionInfo(request);
		
		List<Plan> planList=applyServiceImpl.getPlanList(sessionInfo);
		String serverTime=CommonUtils.dateFormat(new Date(), null);
		
		Map<String,Object> map=new HashMap<>();
		map.put("serverTime", serverTime);
		map.put("list", planList);
		return ResultEx.success(map);
	}

	/**
	 * 按计划获取班级-用于报名
	 */
	@RequestMapping("/class/list")
	@ResponseBody
	public Result getPagePlanClass(HttpServletRequest request,Integer page,Integer limit) {
		SessionInfo sessionInfo=this.getSessionInfo(request);
		Map<String,Object> paramMap=CommonUtils.doParameters(request);
		Paging paging=applyServiceImpl.getPageClass(sessionInfo, page, limit, paramMap);
		return ResultEx.success(paging);
	}
	
	/**
	 * 查询招生计划详情
	 */
	@RequestMapping("/plan/info")
	@ResponseBody
	public Result getPlanInfo(HttpServletRequest request,Integer planId) {
		Plan plan=applyServiceImpl.getPlanInfo(planId);
		return ResultEx.success(plan);
	}

	
	
}
