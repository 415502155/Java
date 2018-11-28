package cn.edugate.esb.web.manage;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.integration.MessageChannel;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import cn.edugate.esb.ResultDwz;
import cn.edugate.esb.entity.Function;
import cn.edugate.esb.entity.FunctionUseRange;
import cn.edugate.esb.entity.KeyValue;
import cn.edugate.esb.entity.Module;
import cn.edugate.esb.entity.Module2Function;
import cn.edugate.esb.entity.Organization;
import cn.edugate.esb.service.FunctionService;
import cn.edugate.esb.service.FunctionUseRangeService;
import cn.edugate.esb.service.KeyValueService;
import cn.edugate.esb.service.MenuService;
import cn.edugate.esb.service.Module2FunctionService;
import cn.edugate.esb.service.ModuleService;
import cn.edugate.esb.service.OrganizationService;
import cn.edugate.esb.util.Constant;
import cn.edugate.esb.util.Paging;
import cn.edugate.esb.util.Util;

/**
 * 功能配置Controller
 * Title:MfunctionController
 * Description:配置模块、功能及其关系
 * Company:SJWY
 * @author:Liuy
 * @Date:2017年5月12日上午10:26:28
 */
@Controller
@RequestMapping("/manage/function")
public class MfunctionController {

	static Logger logger=LoggerFactory.getLogger(MfunctionController.class);
	@Autowired
	@Qualifier("menuChannel")
	MessageChannel messageChannel;
	@Autowired
	private ModuleService moduleService;
	@Autowired
	private FunctionService functionService;
	@Autowired
	private Module2FunctionService module2functionService;
	@Autowired
	private FunctionUseRangeService functionUseRangeService;
	@Autowired
	private OrganizationService organizationService;
	@Autowired
	private MenuService menuService;
	@Autowired
	private KeyValueService keyValueService;
	@Autowired
	private Util util;

	/**
	 * 查看列表页
	 * @param numPerPage
	 * @param totalPage
	 * @param pageNum
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/list")
	public ModelAndView list(@RequestParam(value="numPerPage",defaultValue="25") Integer numPerPage,
			@RequestParam(value="totalPage",defaultValue="") Integer totalPage,@RequestParam(value="pageNum",defaultValue="1") Integer pageNum,
			HttpServletRequest request) {
		ModelAndView mav = new ModelAndView("/function/list");
		Paging<Module> page = new Paging<Module>();
		page.setLimit(numPerPage);
		page.setPage(pageNum);
		page.setStart(0);
		Module module = new Module();
		if(StringUtils.isEmpty(request.getParameter("searchName"))){
			page = moduleService.getListWithPaging(page,module);
		}else{
			module.setMod_name(request.getParameter("searchName"));
			page = moduleService.getQueryListWithPaging(page,module);
		}
		page = moduleService.setFunctionsForModule(page);
		mav.addObject("modules", page);
		mav.addObject("ctx", request.getContextPath());
		return mav;
	}

	/**
	 * 点击新增按钮进入新增页
	 * @param request.id 模块ID，此时只能添加该模块下的功能
	 * @return
	 */
	@RequestMapping(value = "/goAdd",method = RequestMethod.GET)
	public ModelAndView goUserAdd(HttpServletRequest request) {
		ModelAndView mav = new ModelAndView("/function/edit");
		if(StringUtils.isNotEmpty(request.getParameter("id"))){
			Module module = moduleService.getById(Integer.parseInt(request.getParameter("id")));
			mav.addObject("mod_id",module.getMod_id());
			mav.addObject("showFunction",true);
			mav.addObject("showModule",false);
		}
		List<Module> list = new ArrayList<Module>();
		list = moduleService.getList(new Module());
		List<KeyValue> category = keyValueService.getListByType(Constant.KEY_MENU_CATEGORY);
		mav.addObject("category",category);
		mav.addObject("modules",list);
		mav.addObject("ctx", request.getContextPath());
		mav.addObject("action", "add");
		return mav;
	}

	/**
	 * 点击保存按钮创建新记录
	 */
	@RequestMapping(value = "/add",method = RequestMethod.POST)
	public @ResponseBody ResultDwz add(@RequestParam(value="fname",defaultValue="") String name,@RequestParam(value="fun_url",defaultValue="") String url,
			@RequestParam(value="mod_code",defaultValue="") String modCode,@RequestParam(value="mod_order",defaultValue="9999") Integer modOrder,
			@RequestParam(value="fun_code",defaultValue="") String funCode,@RequestParam(value="fun_order",defaultValue="9999") Integer funOrder,
			@RequestParam(value="fun_id",defaultValue="") Integer functionId,@RequestParam(value="mod_id",defaultValue="") Integer modId,
			@RequestParam(value="fun_version",defaultValue="") String version,@RequestParam(value="fun_status",defaultValue="") Integer status,
			@RequestParam(value="grade_number",defaultValue="") String gradeNumber,@RequestParam(value="note",defaultValue="") String note,
			@RequestParam(value="module_id",defaultValue="") Integer moduleId,@RequestParam(value="category") Integer category,
			HttpServletRequest request) {
		if(StringUtils.isEmpty(url)){//没有地址，创建新模块
			if(modOrder<1){
				modOrder=1;
			}
			Module module = new Module();
			module.setMod_name(name);
			module.setMod_note(note);
			module.setMod_code(modCode);
			module.setMod_order(modOrder);
			module.setCategory(category);
			MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
			String logo = util.upload(multipartRequest, "logo_upload");
			if(StringUtils.isNotEmpty(logo)){
				module.setLogo(logo);
			}
			moduleService.add(module);
			moduleService.updateOrder();
		}else{
			//创建新功能
			Function function = new Function();
			function.setFun_code(funCode);
			function.setFun_name(name);
			function.setFun_url(url);
			function.setFun_note(note);
			function.setFun_status(status);
			function.setFun_version(version);
			function.setFun_order(funOrder);
			function.setCategory(category);
			MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
			String logo = util.upload(multipartRequest, "logo_upload");
			if(StringUtils.isNotEmpty(logo)){
				function.setLogo(logo);
			}
			functionService.add(function);
			//建立与模块之间的关系
			Module2Function mf = new Module2Function();
			Integer funId = function.getFun_id();
			mf.setMod_id(moduleId);
			mf.setFun_id(funId);
			module2functionService.add(mf);
			//建立功能适用范围
			FunctionUseRange functionUseRange = new FunctionUseRange();
			functionUseRange.setFun_id(funId);
			functionUseRangeService.addByGradeNumber(gradeNumber,functionUseRange);
			functionService.updateOrder(moduleId);
			//创建一级、二级菜单
			//Message<Integer> message = MessageBuilder.withPayload(function.getFun_id()).build();
			//messageChannel.send(message);
			List<Module> orgModuleList = moduleService.getModuleForMenu(funId); 
			if(null!=orgModuleList&&orgModuleList.size()!=0){
				menuService.createModuleMenus(orgModuleList);
			}
			//创建二级菜单
			List<Function> orgFunctionList = functionService.getFunctionForMenu(funId);
			if(null!=orgFunctionList&&orgFunctionList.size()!=0){
				menuService.createFunctionMenus(orgFunctionList);
			}
		}
		ResultDwz result = new ResultDwz();
		result.setStatusCode("200");
		result.setMessage("操作成功");
		result.setNavTabId("function_list");
		result.setCallbackType("closeCurrent");
		return result;
	}

	/**
	 * 点击编辑按钮，进入编辑页
	 * @param type
	 * @param id
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/goEdit",method = RequestMethod.GET)
	public ModelAndView goUserEdit(@RequestParam(value="type") String type,@RequestParam(value="id") Integer id,
			HttpServletRequest request) {
		ModelAndView mav = new ModelAndView("/function/edit");
		if(type.startsWith("m")){//编辑模块
			Module module = moduleService.getById(id);
			Function function = new Function();
			function.setFun_name(module.getMod_name());
			function.setFun_note(module.getMod_note());
			if (StringUtils.isNotEmpty(module.getLogo())) {
				function.setLogo(util.getPathByPicName(module.getLogo()));
			} else {
				function.setLogo("");
			}
			mav.addObject("mod_id", module.getMod_id());
			mav.addObject("module",module);
			mav.addObject("func",function);
			mav.addObject("showFunction",false);
			mav.addObject("showModule",true);
		}else{//编辑功能
			Function function = functionService.getById(id);
			if (StringUtils.isNotEmpty(function.getLogo())) {
				function.setLogo(util.getPathByPicName(function.getLogo()));
			} else {
				function.setLogo("");
			}
			mav.addObject("func",function);
			Module2Function mf = new Module2Function();
			mf.setFun_id(id);
			List<Module2Function> list = new ArrayList<Module2Function>();
			list = module2functionService.getList(mf);
			if(list!=null&&list.size()!=0){
				mav.addObject("mod_id",list.get(0).getMod_id());
			}
			mav.addObject("gradeNumber",functionUseRangeService.getGradeNumStringByFunID(id));
			mav.addObject("showFunction",true);
			mav.addObject("showModule",false);
		}
		List<KeyValue> category = keyValueService.getListByType(Constant.KEY_MENU_CATEGORY);
		mav.addObject("category",category);
		mav.addObject("modules",moduleService.getList(new Module()));
		mav.addObject("ctx", request.getContextPath());
		mav.addObject("action", "edit");
		return mav;
	}
	
	/**
	 * 编辑模块或功能，同时更新关系表和菜单
	 */
	@RequestMapping(value = "/edit",method = RequestMethod.POST)
	public @ResponseBody ResultDwz edit(@RequestParam(value="fname",defaultValue="") String name,@RequestParam(value="fun_url",defaultValue="") String url,
			@RequestParam(value="mod_code",defaultValue="") String modCode,@RequestParam(value="mod_order",defaultValue="9999") Integer modOrder,
			@RequestParam(value="fun_code",defaultValue="") String funCode,@RequestParam(value="fun_order",defaultValue="9999") Integer funOrder,
			@RequestParam(value="fun_id",defaultValue="") Integer functionId,@RequestParam(value="mod_id",defaultValue="") Integer modId,
			@RequestParam(value="fun_version",defaultValue="") String version,@RequestParam(value="fun_status",defaultValue="") Integer status,
			@RequestParam(value="grade_number",defaultValue="") String gradeNumber,@RequestParam(value="note",defaultValue="") String note,
			@RequestParam(value="module_id",defaultValue="") Integer moduleId,@RequestParam(value="category") Integer category,
			HttpServletRequest request) {
		String oldName = "";
		if(StringUtils.isEmpty(url)){//没有地址，更新模块
			if(modOrder<1){
				modOrder=1;
			}
			Module module = moduleService.getById(moduleId);
			oldName = module.getMod_name();
			module.setMod_name(name);
			module.setMod_note(note);
			module.setMod_code(modCode);
			module.setMod_order(modOrder);
			module.setCategory(category);
			MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
			String logo = util.upload(multipartRequest, "logo_upload");
			if(StringUtils.isNotEmpty(logo)){
				module.setLogo(logo);
			}
			moduleService.update(module);
			//更新一级菜单
			menuService.updateModuleMenu(module,oldName);
			moduleService.updateOrder();
		}else{
			//更新功能
			Function function = functionService.getById(functionId);
			oldName = function.getFun_name();
			function.setFun_id(functionId);
			function.setFun_code(funCode);
			function.setFun_name(name);
			function.setFun_url(url);
			function.setFun_note(note);
			function.setFun_status(status);
			function.setFun_version(version);
			function.setFun_order(funOrder);
			function.setCategory(category);
			try {
				MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
				String logo = util.upload(multipartRequest, "logo_upload");
				if(StringUtils.isNotEmpty(logo)){
					function.setLogo(logo);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			functionService.update(function);
			//更新与模块之间的关系
			Integer funId = function.getFun_id();
			Module2Function mf = new Module2Function();
			mf.setFun_id(funId);
			List<Module2Function> list = module2functionService.getList(mf);
			if(null!=list&&list.size()!=0){
				mf = list.get(0);
			}
			mf.setMod_id(moduleId);
			module2functionService.update(mf);
			//更新功能适用范围
			FunctionUseRange functionUseRange = new FunctionUseRange();
			functionUseRange.setFun_id(funId);
			functionUseRangeService.updateByGradeNumber(gradeNumber,functionUseRange);
			functionService.updateOrder(function.getMod_id());
			//更新二级菜单
			menuService.updateFunctionMenu(function,oldName);
			//补建一级菜单
			List<Module> orgModuleList = moduleService.getModuleForMenu(funId); 
			if(null!=orgModuleList&&orgModuleList.size()!=0){
				List<String> orgids = organizationService.getOrgInModuleByFunId(funId);
				menuService.additionalModuleMenus(orgModuleList,orgids);
			}
			//补建二级菜单
			List<Function> orgFunctionList = functionService.getFunctionForMenu(funId);
			if(null!=orgFunctionList&&orgFunctionList.size()!=0){
				List<String> orgids = organizationService.getOrgInFunctionByFunId(funId);
				menuService.additionalFunctionMenus(orgFunctionList,orgids);
			}
		}
		ResultDwz result = new ResultDwz();
		result.setStatusCode("200");
		result.setMessage("操作成功");
		result.setNavTabId("function_list");
		result.setCallbackType("closeCurrent");
		return result;
	}

	/**
	 * 删除功能或模块
	 * @param ssoId
	 * @return
	 */
	@RequestMapping(value = "/delete",method = RequestMethod.POST)
	public @ResponseBody ResultDwz delete(@RequestParam(value="uid") Integer ssoId) {
		ResultDwz result = new ResultDwz();
		result.setStatusCode("200");
		result.setMessage("删除成功");
		result.setNavTabId("user");
		return result;
	}
	
	/**
	 * 模块或功能名称校验
	 * @param name
	 * @param version 校验功能时的必选项
	 * @param type 区分功能/模块
	 * @param response
	 * @throws IOException
	 */
	@RequestMapping(value = "/checkName")
	public void checkName(@RequestParam(value="name") String name,@RequestParam(value="version") String version,
			@RequestParam(value="type") String type,HttpServletResponse response) throws IOException {
		JSONObject json = new JSONObject(); 
    	response.setContentType("application/json; charset=UTF-8");
    	PrintWriter responseWriter = response.getWriter();        	
    	try {
    		if(type.startsWith("f")){
    			boolean isUseable = functionService.checkName(name,version);
    			if(isUseable){
    				json.put("code", "200");
    			}else{
    				json.put("code", "203");
    			}
    		}else if(type.startsWith("m")){
    			boolean isUseable = moduleService.checkName(name);
    			if(isUseable){
    				json.put("code", "200");
    			}else{
    				json.put("code", "205");
    			}
    		}
			json.write(responseWriter);
		} catch (JSONException e) {
			logger.error("JSONException====", e);
		}
    	responseWriter.flush();
    	responseWriter.close();
	}

	/**
	 * 查看功能下的机构
	 * @param id
	 * @param request
	 * @param numPerPage
	 * @param totalPage
	 * @param pageNum
	 * @return
	 */
	@RequestMapping(value = "/goLook")
	@ResponseBody
	public ModelAndView goLook(@RequestParam(value="id") Integer id,
			@RequestParam(value="numPerPage",defaultValue="20") Integer numPerPage,
			@RequestParam(value="totalPage",defaultValue="") Integer totalPage,
			@RequestParam(value="orgName", defaultValue="") String orgName,
			@RequestParam(value="pageNum",defaultValue="1") Integer pageNum,
			HttpServletRequest request) {
		Paging<Organization> paging = new Paging<Organization>();
		paging.setLimit(numPerPage);
		paging.setPage(pageNum);
		paging = organizationService.getOrgListByUsingFunctionWithPaging(paging, orgName, id);
		ModelAndView mav = new ModelAndView("/function/orgList");
		Function function = functionService.getById(id);
		mav.addObject("function", function);
		mav.addObject("orgList", paging);
		mav.addObject("ctx", request.getContextPath());
		return mav;
	}
}
