package sng.controller.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import sng.controller.common.BaseAction;
import sng.entity.Campus;
import sng.entity.Category;
import sng.entity.Cooperative;
import sng.entity.Rulelist;
import sng.entity.Subject;
import sng.entity.Term;
import sng.pojo.ParamObj;
import sng.pojo.Result;
import sng.service.BaseSetService;

/**
 * @desc 基础设置模块(校区管理、学期管理、科目管理、教室管理、合作机构、规则设置)
 * @author magq
 * @data 2018-10-26
 * @version 1.0
 */
@Controller
@RequestMapping("/baseSet")
public class BaseSetWebController extends BaseAction {

	@Autowired
	private BaseSetService baseSetService;

	/**
	 * 创建/编辑校区
	 * 
	 * @param request
	 * @param response
	 * @param campus
	 * @throws Exception
	 */
	
	@RequestMapping(value = "/campusManage/createAndUpdateCampus.json", method = RequestMethod.POST, produces = "application/*")
	@ResponseBody
	public Result createAndUpdateCampus(HttpServletRequest request, HttpServletResponse response, Campus campus)
			throws Exception {
		Result result = baseSetService.createAndUpdateCampus(campus);
		return result;
	}

	/**
	 * 校区管理列表
	 * 
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value = "/campusManage/queryCampusListInfo.json", method = RequestMethod.POST, produces = "application/*")
	public Result queryCampusListInfo(HttpServletRequest request, HttpServletResponse response, ParamObj paramObj)
			throws Exception {
		Result result = baseSetService.queryCampusListInfo(paramObj);
		return result;
	}

	/**
	 * 删除校区信息
	 * 
	 * @param request
	 * @param response
	 * @param campus
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value = "/campusManage/deleteCampus.json", method = RequestMethod.POST, produces = "application/*")
	public Result deleteCampus(HttpServletRequest request, HttpServletResponse response, ParamObj paramObj)
			throws Exception {
		Result result = baseSetService.deleteCampus(paramObj);
		return result;
	}

	/**
	 * 创建学期
	 * 
	 * @param request
	 * @param response
	 * @param term
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value = "/termManage/createAndUpdateTerm.json", method = RequestMethod.POST, produces = "application/*")
	public Result createAndUpdateTerm(HttpServletRequest request, HttpServletResponse response, Term term)
			throws Exception {
		
		Result result = baseSetService.createAndUpdateTerm(term);
		return result;
	}

	/**
	 * 学期列表
	 * 
	 * @param request
	 * @param response
	 * @param paramObj
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value = "/termManage/queryTermListInfo.json", method = RequestMethod.POST, produces = "application/*")
	public Result queryTermListInfo(HttpServletRequest request, HttpServletResponse response, ParamObj paramObj)
			throws Exception {
		Result result = baseSetService.queryTermListInfo(paramObj);
		return result;
	}

	/**
	 * 删除学期
	 * 
	 * @param request
	 * @param response
	 * @param paramObj
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value = "/termManage/deleteTermByTerm.json", method = RequestMethod.POST, produces = "application/*")
	public Result deleteTermByTerm(HttpServletRequest request, HttpServletResponse response, ParamObj paramObj)
			throws Exception {
		Result result = baseSetService.deleteTermByTerm(paramObj);
		return result;
	}

	/**
	 * 创建/编辑类目
	 * 
	 * @param request
	 * @param response
	 * @param paramObj
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value = "/categoryManage/createAndUpdateCategoryInfo.json", method = RequestMethod.POST, produces = "application/*")
	public Result createAndUpdateCategoryInfo(HttpServletRequest request, HttpServletResponse response, Category category)
			throws Exception {
		Result result = baseSetService.createAndUpdateCategoryInfo(category);
		return result;
	}

	/**
	 * 获取类目信息
	 * 
	 * @param request
	 * @param response
	 * @param paramObj
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value = "/categoryManage/queryCategoryListInfo.json", method = RequestMethod.POST, produces = "application/*")
	public Result queryCategoryListInfo(HttpServletRequest request, HttpServletResponse response, ParamObj paramObj)
			throws Exception {
		Result result = baseSetService.queryCategoryListInfo(paramObj);
		return result;
	}

	/**
	 * 创建/编辑科目
	 * 
	 * @param request
	 * @param response
	 * @param paramObj
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value = "/categoryManage/createAndUpdateSubjectInfo.json", method = RequestMethod.POST, produces = "application/*")
	public Result createAndUpdateSubjectInfo(HttpServletRequest request, HttpServletResponse response, Subject subject)
			throws Exception {
		Result result = baseSetService.createAndUpdateSubjectInfo(subject);
		return result;
	}

	/**
	 * 科目管理列表
	 * 
	 * @param request
	 * @param response
	 * @param paramObj
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value = "/categoryManage/querySubjectListInfo.json", method = RequestMethod.POST, produces = "application/*")
	public Result querySubjectListInfo(HttpServletRequest request, HttpServletResponse response, ParamObj paramObj)
			throws Exception {
		Result result = baseSetService.querySubjectListInfo(paramObj);
		return result;
	}

	/**
	 * 删除科目
	 * 
	 * @param request
	 * @param response
	 * @param paramObj
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value = "/categoryManage/deleteSubjectInfo.json", method = RequestMethod.POST, produces = "application/*")
	public Result deleteSubjectInfo(HttpServletRequest request, HttpServletResponse response, ParamObj paramObj)
			throws Exception {
		Result result = baseSetService.deleteSubjectInfo(paramObj);
		return result;

	}

	/**
	 * 创建/编辑教室
	 * 
	 * @param request
	 * @param response
	 * @param paramObj
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value = "/classRoomManage/createAndUpdateClassRoomInfo.json", method = RequestMethod.POST, produces = "application/*")
	public Result createAndUpdateClassRoomInfo(HttpServletRequest request, HttpServletResponse response,
			ParamObj paramObj) throws Exception {
		Result result = baseSetService.createAndUpdateClassRoomInfo(paramObj);
		return result;
	}

	/**
	 * 获取教室信息
	 * 
	 * @param request
	 * @param response
	 * @param paramObj
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value = "/classRoomManage/queryClassRoomListInfo.json", method = RequestMethod.POST, produces = "application/*")
	public Result queryClassRoomListInfo(HttpServletRequest request, HttpServletResponse response, ParamObj paramObj)
			throws Exception {
		Result result = baseSetService.queryClassRoomListInfo(paramObj);
		return result;
	}

	/**
	 * 删除教室
	 * 
	 * @param request
	 * @param response
	 * @param paramObj
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value = "/classRoomManage/deleteClassRoomInfo.json", method = RequestMethod.POST, produces = "application/*")
	public Result deleteClassRoomInfo(HttpServletRequest request, HttpServletResponse response, ParamObj paramObj)
			throws Exception {
		Result result = baseSetService.deleteClassRoomInfo(paramObj);
		return result;
	}

	/**
	 * 创建/编辑合作机构保存
	 * 
	 * @param request
	 * @param response
	 * @param cooperative
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value = "/cooperativeManage/createAndUpdateCooperativeInfo.json", method = RequestMethod.POST, produces = "application/*")
	public Result createAndUpdateCooperativeInfo(HttpServletRequest request, HttpServletResponse response,
			Cooperative cooperative) throws Exception {
		Result result = baseSetService.createAndUpdateCooperativeInfo(cooperative);
		return result;
	}

	/**
	 * 获取合作机构信息列表
	 * 
	 * @param request
	 * @param response
	 * @param cooperative
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value = "/cooperativeManage/queryCooperativeListInfo.json", method = RequestMethod.POST, produces = "application/*")
	public Result queryCooperativeListInfo(HttpServletRequest request, HttpServletResponse response, ParamObj paramObj)
			throws Exception {
		Result result = baseSetService.queryCooperativeListInfo(paramObj);
		return result;
	}

	/**
	 * 删除合作机构信息
	 * 
	 * @param request
	 * @param response
	 * @param cooperative
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value = "/cooperativeManage/deleteCooperativeInfo.json", method = RequestMethod.POST, produces = "application/*")
	public Result deleteCooperativeInfo(HttpServletRequest request, HttpServletResponse response, ParamObj paramObj)
			throws Exception {
		Result result = baseSetService.deleteCooperativeInfo(paramObj);
		return result;
	}

	/**
	 * 缴费新建/编辑保存接口
	 * 
	 * @param request
	 * @param response
	 * @param rulelist
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value = "/ruleManage/createAndUpdatePaySetInfo.json", method = RequestMethod.POST, produces = "application/*")
	public Result createAndUpdatePaySetInfo(HttpServletRequest request, HttpServletResponse response, Rulelist rulelist)
			throws Exception {
		Result result = baseSetService.createAndUpdatePaySetInfo(rulelist);
		return result;
	}

	/**
	 * 获取缴费设置信息
	 * 
	 * @param request
	 * @param response
	 * @param paramObj
	 * @throws Exception
	 */
	@RequestMapping(value = "/payRuleManage/queryPaySetInfo.json", method = RequestMethod.POST, produces = "application/*")
	@ResponseBody
	public Result queryPaySetInfo(HttpServletRequest request, HttpServletResponse response, ParamObj paramObj)
			throws Exception {
		Result result = baseSetService.queryPaySetInfo(paramObj);
		return result;
		
	}

	/**
	 * 认证设置/编辑保存
	 * 
	 * @param request
	 * @param response
	 * @param rulelist
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value = "/authManage/createAndUpdateAuthSetInfo.json", method = RequestMethod.POST, produces = "application/*")
	public Result createAndUpdateAuthSetInfo(HttpServletRequest request, HttpServletResponse response, Rulelist rulelist)
			throws Exception {
		Result result = baseSetService.createAndUpdateAuthSetInfo(rulelist);
		return result;
	}

	/**
	 * 获取认证设置信息
	 * 
	 * @param request
	 * @param response
	 * @param paramObj
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value = "/authManage/queryAuthSetInfo.json", method = RequestMethod.POST, produces = "application/*")
	public Result queryAuthSetInfo(HttpServletRequest request, HttpServletResponse response, ParamObj paramObj)
			throws Exception {
		Result result = baseSetService.queryAuthSetInfo(paramObj);
		return result;
	}

}
