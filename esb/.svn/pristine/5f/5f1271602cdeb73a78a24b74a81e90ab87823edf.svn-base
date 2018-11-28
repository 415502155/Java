package cn.edugate.esb.web.manage;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import cn.edugate.esb.ResultDwz;
import cn.edugate.esb.entity.Function;
import cn.edugate.esb.entity.KeyValue;
import cn.edugate.esb.entity.Menu;
import cn.edugate.esb.entity.Module;
import cn.edugate.esb.entity.Organization;
import cn.edugate.esb.redis.dao.RedisMenuDao;
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
 * 菜单配置Controller
 * Title:MmenuController
 * Description:
 * Company:SJWY
 * @author:Liuy
 * @Date:2017年6月6日上午10:32:15
 */
@Controller
@RequestMapping("/manage/menu")
public class MmenuController {

	static Logger logger=LoggerFactory.getLogger(MmenuController.class);
	@Autowired
	private KeyValueService keyValueService;
	@Autowired
	private ModuleService moduleService;
	@Autowired
	private RedisMenuDao redisMenuDao;
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
	private Util util;
	
	/**
	 * 菜单配置下展示机构列表
	 * @param numPerPage
	 * @param totalPage
	 * @param pageNum
	 * @param searchName
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/list")
	public ModelAndView list(@RequestParam(value="numPerPage",defaultValue="25") Integer numPerPage,
			@RequestParam(value="totalPage",defaultValue="") Integer totalPage,
			@RequestParam(value="pageNum",defaultValue="1") Integer pageNum,
			@RequestParam(value = "searchName", defaultValue = "") String searchName,
			HttpServletRequest request) {
		Paging<Organization> paging = new Paging<Organization>();
		paging.setLimit(numPerPage);
		paging.setPage(pageNum);
		Organization organization = new Organization();
		organization.setOrg_name_cn(searchName.replace(",", ""));
		paging = organizationService.getOrgListWithPaging(paging, organization);
		ModelAndView mav = new ModelAndView("/menu/list");
		mav.addObject("orgList", paging);
		mav.addObject("org", organization);
		mav.addObject("ctx", request.getContextPath());
		return mav;
	}

	/**
	 * 点击机构下的菜单编辑按钮，进入菜单编辑页
	 * @param id
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/goEdit",method = RequestMethod.GET)
	public ModelAndView goUserEdit(@RequestParam(value="id") Integer id,HttpServletRequest request) {
		ModelAndView mav = new ModelAndView("/menu/edit");
		mav.addObject("org",organizationService.getOrgById(id));
		List<Menu> list = menuService.getMenusForOrg(id, true);
		List<Menu> idList = menuService.getMenusForOrg(id, false);
		List<Integer> menuIds = new ArrayList<Integer>();
		for (Menu m : idList) {
			menuIds.add(m.getMenu_id());
		}
		mav.addObject("menuIds", StringUtils.join(menuIds, ","));
		mav.addObject("menus",list);
		mav.addObject("ctx", request.getContextPath());
		mav.addObject("action", "edit");
		return mav;
	}
	
	/**
	 * 修改菜单条目
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/edit",method = RequestMethod.POST)
	public @ResponseBody ResultDwz edit(@RequestParam(value="menu_id") Integer menu_id,
			@RequestParam(value="menu_name") String menu_name,
			@RequestParam(value="parent_id",defaultValue="0") String parent_id,
			@RequestParam(value="menu_order") Integer menu_order,
			@RequestParam(value="menu_type") String menu_type,
			@RequestParam(value="open_mode") String open_mode,
			@RequestParam(value="fun_id") Integer fun_id,
			@RequestParam(value="menu_status") String menu_status,
			@RequestParam(value="menu_note",defaultValue="") String menu_note,
			@RequestParam(value="menu_url",defaultValue="") String menu_url,
			@RequestParam(value="fun_code",defaultValue="") String fun_code,
			@RequestParam(value="category") Integer category,
			HttpServletRequest request) {
		Menu menu = menuService.getById(menu_id);
		menu.setMenu_name(menu_name);
		menu.setParent_id(Integer.parseInt(parent_id));
		menu.setMenu_type(Integer.parseInt(menu_type));
		menu.setMenu_status(Integer.parseInt(open_mode));
		menu.setMenu_url(menu_url);
		menu.setMenu_order(menu_order);
		menu.setOpen_mode(Integer.parseInt(open_mode));
		menu.setMenu_note(menu_note);
		menu.setFun_id(fun_id);
		menu.setFun_code(fun_code);
		menu.setCategory(category);
		MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
		String logo = util.upload(multipartRequest, "logo_upload");
		if(StringUtils.isNotEmpty(logo)){
			menu.setMenu_photo(logo);
		}
		menuService.update(menu);
		updateUseType(menu);
		ResultDwz result = new ResultDwz();
		result.setStatusCode("200");
		result.setMessage("操作成功");
		result.setNavTabId("editMenuDialog");
		result.setCallbackType("closeCurrent");
		return result;
	}
	
	/**
	 * 刷新菜单使用状态
	 * @param parentId
	 * @param type
	 */
	private void updateUseType(Menu menu){
		//如果当前修改的菜单是一级菜单
		if(Constant.NO.equals(menu.getParent_id())){
			Map<String,Menu> map = redisMenuDao.getOrgMenusForCode(menu.getOrg_id(),true);
			for (String key : map.keySet()) {
				Menu m = map.get(key);
				System.out.println(m.getParent_id());
				if(m.getParent_id().equals(menu.getMenu_id())){
					m.setMenu_type(menu.getMenu_type());
					menuService.update(m);
				}
			}
		}else{
			Integer using = 0;
			Integer trying = 0;
			Integer unused = 0;
			Menu parentMenu = new Menu();
			List<Menu> menus = new ArrayList<Menu>();
			Map<String,Menu> map = redisMenuDao.getOrgMenusForCode(menu.getOrg_id(),true);
			for (String key : map.keySet()) {
				Menu m = map.get(key);
				if(Constant.NO.equals(m.getParent_id())&&menu.getFun_code().startsWith(m.getFun_code())){
					parentMenu = m;
				}else if(m.getParent_id().equals(menu.getParent_id())){
					menus.add(m);
				}
			}
			if(menu.getMenu_type().equals(Constant.MENU_TYPE_USING)){
				using++;
			}else if(menu.getMenu_type().equals(Constant.MENU_TYPE_TRYING)){
				trying++;
			}else if(menu.getMenu_type().equals(Constant.MENU_TYPE_STAYING)){
				unused++;
			}
			for (Menu me : menus) {
				if(me.getMenu_type().equals(Constant.MENU_TYPE_USING)){
					using++;
				}else if(me.getMenu_type().equals(Constant.MENU_TYPE_TRYING)){
					trying++;
				}else if(me.getMenu_type().equals(Constant.MENU_TYPE_STAYING)){
					unused++;
				}
			}
			if(using+trying==0){
				parentMenu.setMenu_type(Constant.MENU_TYPE_STAYING);
			}else if(using+unused==0){
				parentMenu.setMenu_type(Constant.MENU_TYPE_TRYING);
			}else if(trying+unused==0){
				parentMenu.setMenu_type(Constant.MENU_TYPE_USING);
			}else if(using==0){
				parentMenu.setMenu_type(Constant.MENU_TYPE_TRYING);
			}else{
				parentMenu.setMenu_type(Constant.MENU_TYPE_USING);
			}
			if(null!=parentMenu.getMenu_id()){
				menuService.update(parentMenu);
			}
		}
	}
	
	/**
	 * 添加自定义菜单条目
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/add",method = RequestMethod.POST)
	public @ResponseBody ResultDwz add(@RequestParam(value="menu_name") String menu_name,
			@RequestParam(value="parent_id",defaultValue="0") String parent_id,
			@RequestParam(value="menu_order") Integer menu_order,
			@RequestParam(value="org_id") Integer org_id,
			@RequestParam(value="menu_type") String menu_type,
			@RequestParam(value="open_mode") String open_mode,
			@RequestParam(value="menu_status") String menu_status,
			@RequestParam(value="menu_note",defaultValue="") String menu_note,
			@RequestParam(value="menu_url",defaultValue="") String menu_url,
			@RequestParam(value="fun_id") Integer fun_id,
			@RequestParam(value="category") Integer category,
			@RequestParam(value="fun_code",defaultValue="") String fun_code,
			HttpServletRequest request) {
		Menu menu = new Menu();
		menu.setMenu_name(menu_name);
		menu.setCategory(category);
		menu.setParent_id(Integer.parseInt(parent_id));
		menu.setMenu_type(Integer.parseInt(menu_type));
		menu.setMenu_status(Integer.parseInt(open_mode));
		menu.setMenu_url(menu_url);
		menu.setMenu_order(menu_order);
		menu.setOpen_mode(Integer.parseInt(open_mode));
		menu.setMenu_note(menu_note);
		menu.setOrg_id(org_id);
		menu.setFun_id(fun_id);
		menu.setFun_code(fun_code);
		MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
		String logo = util.upload(multipartRequest, "logo_upload");
		if(StringUtils.isNotEmpty(logo)){
			menu.setMenu_photo(logo);
		}
		menuService.add(menu);
		ResultDwz result = new ResultDwz();
		result.setStatusCode("200");
		result.setMessage("操作成功");
		result.setNavTabId("editMenuDialog");
		result.setCallbackType("closeCurrent");
		return result;
	}
	
	/**
	 * 更新机构下的菜单顺序
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/update",method = RequestMethod.POST)
	public @ResponseBody ResultDwz update(HttpServletRequest request) {
		String menuIds = request.getParameter("menuIds");
		String[] menuId = menuIds.split(",");
		for (String id : menuId) {
			Menu menu = redisMenuDao.getMenuById(Integer.parseInt(id));
			menu.setMenu_order(Integer.parseInt(request.getParameter("order_"+id)));
			menuService.update(menu);
		}
		ResultDwz result = new ResultDwz();
		result.setStatusCode("200");
		result.setMessage("操作成功");
		result.setNavTabId("user");
		result.setCallbackType("closeCurrent");
		return result;
	}

	/**
	 * 点击添加或编辑菜单条目
	 * @param type
	 * @param id
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/goMenu",method = RequestMethod.GET)
	public ModelAndView goMenu(@RequestParam(value="id") Integer id,
			@RequestParam(value="org_id") Integer org_id,
			HttpServletRequest request) {
		ModelAndView mav = new ModelAndView("/menu/add");
		String oldName = "";
		String code = "0";
		Menu menu = new Menu();
		List<Menu> list = new ArrayList<Menu>();
		mav.addObject("action", "add");
		if(0!=id){
			menu = menuService.getById(id);
			try {
				if(null!=menu.getFun_id()){
					Function function = functionService.getById(menu.getFun_id());
					oldName = function.getFun_name();
					code = function.getFun_code();
				}else if(null!=menu.getMod_id()){
					Module module = moduleService.getById(menu.getMod_id());
					oldName = module.getMod_name();
					code = module.getMod_code();
				}else{
					oldName = "";
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			mav.addObject("action", "edit");
			mav.addObject("oldName",oldName);
		}
		menu.setFun_code(code);
		list = menuService.getAllParent(org_id);
		if (StringUtils.isNotEmpty(menu.getMenu_photo())) {
			menu.setMenu_photo(util.getPathByPicName(menu.getMenu_photo()));
		} else {
			menu.setMenu_photo("");
		}
		
		//符合机构 的功能list
		List<Function> funList = functionService.getFunctionForOrg(org_id+"");

		List<KeyValue> category = keyValueService.getListByType(Constant.KEY_MENU_CATEGORY);
		mav.addObject("category",category);
		
		mav.addObject("menu",menu);
		mav.addObject("org_id",org_id);
		mav.addObject("menus",list);
		mav.addObject("funList",funList);
		mav.addObject("ctx", request.getContextPath());
		return mav;
	}
}
