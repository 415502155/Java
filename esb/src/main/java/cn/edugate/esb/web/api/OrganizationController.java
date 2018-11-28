package cn.edugate.esb.web.api;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.lang.StringUtils;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import cn.edugate.esb.Result;
import cn.edugate.esb.comparator.CompusToSortComparator;
import cn.edugate.esb.eduEnum.EnumMessage;
import cn.edugate.esb.entity.Campus;
import cn.edugate.esb.entity.Classes;
import cn.edugate.esb.entity.Course;
import cn.edugate.esb.entity.Icon;
import cn.edugate.esb.entity.Menu;
import cn.edugate.esb.entity.Organization;
import cn.edugate.esb.entity.Role;
import cn.edugate.esb.params.EduJsonFilter;
import cn.edugate.esb.params.EduJsonFilters;
import cn.edugate.esb.params.filter.CampusFilter;
import cn.edugate.esb.params.filter.MenuFilter;
import cn.edugate.esb.params.filter.OrganizationFilter;
import cn.edugate.esb.redis.dao.RedisCampusDao;
import cn.edugate.esb.redis.dao.RedisClassesDao;
import cn.edugate.esb.redis.dao.RedisCourseDao;
import cn.edugate.esb.redis.dao.RedisIconDao;
import cn.edugate.esb.redis.dao.RedisMenuDao;
import cn.edugate.esb.redis.dao.RedisOrganizationDao;
import cn.edugate.esb.service.CampusService;
import cn.edugate.esb.service.ClassesService;
import cn.edugate.esb.service.CourseService;
import cn.edugate.esb.service.IconService;
import cn.edugate.esb.service.RoleService;
import cn.edugate.esb.util.Constant;
import cn.edugate.esb.util.Util;

@Controller
@RequestMapping("/api/org")
public class OrganizationController {

	@Autowired
	private RedisClassesDao redisClassesDao;
	
	RedisMenuDao redisMenuDao;
	@Autowired
	private RedisOrganizationDao orgDao;
	
	@Autowired
	private RedisCourseDao courseDao;
	
	@Autowired
	private CourseService courseService;
	
	@Autowired
	private ClassesService classesService;
	
	@Autowired
	private RedisCampusDao redisCampusDao;
	
	@Autowired
	private CampusService campusService;
	@Autowired
	private RoleService roleService;
	@SuppressWarnings("unused")
	@Autowired
	private IconService iconService;
	@Autowired
	private RedisIconDao redisIconDao;
	@Autowired	
	public void setRedisMenuDao(RedisMenuDao redisMenuDao) {
		this.redisMenuDao = redisMenuDao;
	}
	@Autowired
	private Util util;
	
	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "/getMenus")
	@EduJsonFilters(value={@EduJsonFilter(mixin=MenuFilter.class, target=Menu.class)})
	public @ResponseBody Result<List<Menu>> getMenusByOrgId(
			@RequestParam(value="org_id") Integer org_id,
			HttpServletRequest request) {
		
		String token = request.getParameter("token");
		String udid = request.getParameter("udid");
		
		
		//为特殊机构设定的特殊菜单分类
		List<Integer> category = new ArrayList<Integer>();
		category.add(Constant.MENU_CATEGORY_ZHENSHENGKEJI);
		
		List<Menu> menus = new ArrayList<Menu>();
		Result<List<Menu>> result = new Result<List<Menu>>();
		String user_id = request.getParameter("user_id");
		if(StringUtils.isEmpty(user_id)){
			Map<String,Menu> menusMap = new HashMap<String,Menu>();
			//menusMap = redisMenuDao.getMenusByOrgId(org_id);
			menusMap = redisMenuDao.getMenusByOrgId(org_id,token,udid);
			if(menusMap!=null){
				for (String key : menusMap.keySet()) {
					if(category.contains(menusMap.get(key).getCategory())){
						menus.add(menusMap.get(key));
					}
				}
			}
			result.setData(menus);
			result.setSuccess(true);
			result.setMessage(EnumMessage.success.getMessage());
			result.setCode(EnumMessage.success.getCode());
		}else{
			Set<String> parentMenuCode = new HashSet<String>();
			//Map<String,Menu> menusMap = redisMenuDao.getOrgMenusForCode(org_id,false);
			Map<String,Menu> menusMap = redisMenuDao.getOrgMenusForCode(org_id,false,token,udid);
			if(null!=menusMap){
				List<Role> roles = this.roleService.getByUserId(Integer.parseInt(user_id),org_id); 
				for (Role role : roles) {
					JSONObject authorities = new JSONObject(role.getAuthorities());
					Iterator iterator = authorities.keys();
					while(iterator.hasNext()){
						String key = iterator.next().toString();
						if(Constant.ALL_AUTH.equals(key)){
							for (Menu m : menusMap.values()) {
								if(category.contains(m.getCategory())){
									menus.add(m);
								}
							}
							result.setData(menus);
							result.setSuccess(true);
							result.setMessage(EnumMessage.success.getMessage());
							result.setCode(EnumMessage.success.getCode());
							return result;
						}else{
							if(menusMap.containsKey(key)){
								parentMenuCode.add(key.substring(0,key.length()-2));
								menus.add(menusMap.get(key));
							}
						}
					}
				}
				for (String code : parentMenuCode) {
					if(menusMap.containsKey(code)){
						if(category.contains(menusMap.get(code).getCategory())){
							menus.add(menusMap.get(code));
						}
					}
				}
			}
			result.setData(menus);
			result.setSuccess(true);
			result.setMessage(EnumMessage.success.getMessage());
			result.setCode(EnumMessage.success.getCode());
		}
		return result;
	}
	
	@ResponseBody
	@EduJsonFilters(value={@EduJsonFilter(mixin=OrganizationFilter.class, target=Organization.class)})
	@RequestMapping(value = "/getOrgInfo")
	public Result<Organization> getOrgInfo(@RequestParam String org_id,
			HttpServletRequest request, HttpServletResponse response) throws IOException  {		
		Result<Organization> result = new Result<Organization>();	
		Organization org = orgDao.getByOrgId(org_id,null);
		if(org==null){
			result.setMessage("获取失败");
			result.setSuccess(false);
			return result;
		}
		org.setCampusList(redisCampusDao.getCampusByOrgId(org_id));
		

		if (StringUtils.isNotBlank(org.getLogo())) {
			org.setLogoUrl(util.getPathByPicName(org.getLogo()));
		} else {
			org.setLogoUrl("");
		}
		
		List<String> bgUrlList = new ArrayList<String>();
		
		if (StringUtils.isNotBlank(org.getBg())) {
			String[] bgNameArray = org.getBg().split(";");
			for (String bgName : bgNameArray) {
				String bgUrl = util.getPathByPicName(bgName);
				bgUrlList.add(bgUrl);
			}
		}
		org.setBgUrlList(bgUrlList);
		
		
		
		
		try{
			result.setData(org);
			result.setMessage("获取成功");
			result.setSuccess(true);
		} catch (Exception e) {
			result.setMessage(e.getMessage());
			result.setSuccess(false);
		}	
		return result;
	
		
	}
	
	@ResponseBody
	@RequestMapping(value = "/getOrgsOfType")
	@EduJsonFilters(value={@EduJsonFilter(mixin=OrganizationFilter.class, target=Organization.class)})
	public Result<List<Organization>> getOrgsOfType(@RequestParam String org_type,
			HttpServletRequest request, HttpServletResponse response) throws IOException  {			
		Result<List<Organization>> result = new Result<List<Organization>>();
		List<Organization> list = orgDao.getOrgsByType(org_type);
		try{		
			result.setData(list);
			result.setMessage("获取成功");
			result.setSuccess(true);
		} catch (Exception e) {
			result.setMessage(e.getMessage());
			result.setSuccess(false);
		}	
		return result;
	}
	
	
	@ResponseBody
	@RequestMapping(value = "/getCoursByOrgId")
	@EduJsonFilters(value={@EduJsonFilter(mixin=OrganizationFilter.class, target=Course.class)})
	public Result<List<Course>> getCoursByOrgId(@RequestParam String org_id, HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		Result<List<Course>> result = new Result<List<Course>>();
		
		try {
			List<Course> list = courseDao.getCoursByOrgId(org_id);
			result.setData(list);
			result.setMessage("获取成功");
			result.setSuccess(true);
		} catch (Exception e) {
			result.setData(null);
			result.setMessage(e.getMessage());
			result.setSuccess(false);
		}
		return result;
	}
	
	@RequestMapping(value="/editCourseName")
	@ResponseBody
	@EduJsonFilters(value={@EduJsonFilter(mixin=OrganizationFilter.class, target=Course.class)})
	public Result<String> editCourseName(HttpServletRequest request, HttpServletResponse response) {
		String orgID = request.getParameter("org_id");
		String courseID = request.getParameter("cour_id");
		String courseName = request.getParameter("cour_name");
		
		Result<String> result = new Result<String>();
		try {
			int resultCount = courseService.updateCourseName(Integer.valueOf(orgID), Integer.valueOf(courseID), courseName);
			
			if (resultCount == 1) {
				result.setData("成功");
				result.setSuccess(true);
			} else {
				result.setData("失败");
				result.setSuccess(false);
			}
		} catch (Exception ex) {
			result.setData("失败");
			result.setSuccess(false);
		}
		
		return result;
	}
	
	
	@RequestMapping(value="/saveCourse")
	@ResponseBody
	@EduJsonFilters(value={@EduJsonFilter(mixin=OrganizationFilter.class, target=Course.class)})
	public Result<String> saveCourse(HttpServletRequest request, HttpServletResponse response) {
		String orgID = request.getParameter("org_id");
		String courseName = request.getParameter("cour_name");
		
		Result<String> result = new Result<String>();
		try {
			int resultCount = courseService.saveCourse(Integer.valueOf(orgID), courseName);
			
			if (resultCount != 0) {
				result.setData(String.valueOf(resultCount));
				result.setSuccess(true);
			} else {
				result.setData("");
				result.setSuccess(false); 
			}
		} catch (Exception ex) {
			result.setData("失败");
			result.setSuccess(false);
		}
		
		return result;
	}
	
	
	@ResponseBody
	@RequestMapping(value = "/getCompusByOrgId")
	@EduJsonFilters(value={@EduJsonFilter(mixin=CampusFilter.class, target=Campus.class)})
	public Result<List<Campus>> getCompusByOrgId(@RequestParam String org_id,
			HttpServletRequest request, HttpServletResponse response) throws IOException  {			
		Result<List<Campus>> result = new Result<List<Campus>>();		
		try{	
			List<Campus> list = redisCampusDao.getCampusByOrgId(org_id);
			CompusToSortComparator cmc = new CompusToSortComparator();  
	        Collections.sort(list,cmc); 
			result.setData(list);
			result.setSuccess(true);
			result.setMessage(EnumMessage.success.getMessage());
			result.setCode(EnumMessage.success.getCode());
		} catch (Exception e) {
			result.setSuccess(false);
			result.setMessage(EnumMessage.fail.getMessage());
			result.setCode(EnumMessage.fail.getCode());
		}	
		return result;
	}
	
    @RequestMapping(value = "/getChildOrg")
    @EduJsonFilters(value={@EduJsonFilter(mixin=OrganizationFilter.class, target=Organization.class)})
    public @ResponseBody Result<Object> getChildOrg(
            @RequestParam(value="org_id") Integer org_id,
            HttpServletRequest request) {
        Result<Object> result = new Result<Object>();
        Map<String,Organization> orgMap = new HashMap<String,Organization>();
        Organization org = orgDao.getByOrgId(org_id.toString(),null);
        if(null==org){
            result.setSuccess(false);
            result.setMessage(EnumMessage.organization_not_found.getMessage());
            result.setCode(EnumMessage.organization_not_found.getCode());
        }
        orgMap = orgDao.getChildOrg(org_id);
        result.setMessage(EnumMessage.success.getMessage());
        if(null==orgMap){
        	result.setMessage(EnumMessage.success.getMessage()+":没有子机构！");
        	 result.setSuccess(false);
        	 result.setCode(EnumMessage.fail.getCode());
        }else{
	        result.setData(orgMap.values());
	        result.setSuccess(true);
	        result.setCode(EnumMessage.success.getCode());
        }
        return result;
    }
    
    
    @RequestMapping(value="/editCompus")
	@ResponseBody
	@EduJsonFilters(value={@EduJsonFilter(mixin=OrganizationFilter.class, target=Course.class)})
	public Result<Campus> editCompus(HttpServletRequest request, HttpServletResponse response) {
    	Result<Campus> result = new Result<Campus>();
    	String orgID = request.getParameter("orgID");
    	String cam_id = request.getParameter("cam_id");
		String cam_name = request.getParameter("cam_name");
		String cam_note = request.getParameter("cam_note");
		String cam_type = request.getParameter("cam_type");
		if(StringUtils.isNotEmpty(orgID) && StringUtils.isNotEmpty(cam_name)){
			Campus campus = new Campus();
			try{
				if(StringUtils.isNotEmpty(cam_id)){
					campus = redisCampusDao.getById(Integer.parseInt(cam_id));
					campus.setCam_id(new Integer(cam_id));
					campus.setCam_name(cam_name);
					campus.setCam_note(cam_note);
					campus.setOrg_id(Integer.parseInt(orgID));
					campus.setCam_type(Integer.parseInt(cam_type));
					campusService.update(campus);
				}else{
					campus.setCam_name(cam_name);
					campus.setCam_note(cam_note);
					campus.setInsert_time(new Date());
					campus.setIs_del(Constant.IS_DEL_NO);
					campus.setOrg_id(Integer.parseInt(orgID));
					campus.setCam_type(Integer.parseInt(cam_type));
					campusService.add(campus);
				}
				result.setData(campus);
				result.setSuccess(true);
				result.setMessage(EnumMessage.success.getMessage());
				result.setCode(EnumMessage.success.getCode());
			}catch(Exception e){
				e.printStackTrace();
				result.setSuccess(false);
				result.setMessage(EnumMessage.fail.getMessage());
				result.setCode(EnumMessage.fail.getCode());
			}
		}else{
			result.setSuccess(false);
			result.setMessage(EnumMessage.fail.getMessage());
			result.setCode(EnumMessage.fail.getCode());
		}

		return result;
	}
    
    @ResponseBody
	@RequestMapping(value = "/delCompus")
	@EduJsonFilters(value={@EduJsonFilter(mixin=CampusFilter.class, target=Campus.class)})
	public Result<Campus> delCompus(@RequestParam String cam_id,@RequestParam int org_id,
			HttpServletRequest request, HttpServletResponse response) throws IOException  {			
    	Result<Campus> result = new Result<Campus>();	
		try{
			
			
			int returnnum = campusService.delete(Integer.parseInt(cam_id));
			if(returnnum==-1){
				result.setMessage("主校区不能删除");
				result.setCode(EnumMessage.fail.getCode());
				result.setSuccess(true);
			}else{
				
				List<Classes> clasList = redisClassesDao.getClassesOfCampus(Integer.parseInt(cam_id));
				Campus campus = campusService.getCampusByType(org_id);
				for (Classes classes : clasList) {
					classes.setCam_id(campus.getCam_id());
					classes.setUpdate_time(new Date());
					classesService.update(classes);
				}
				result.setSuccess(true);
				result.setMessage(EnumMessage.success.getMessage());
				result.setCode(EnumMessage.success.getCode());
			}
		} catch (Exception e) {
			e.printStackTrace();
			result.setMessage(EnumMessage.fail.getMessage());
			result.setSuccess(false);
			result.setCode(EnumMessage.fail.getCode());
		}	
		return result;
	}

    @ResponseBody
	@RequestMapping(value = "/getCampusByOrgId")
	public Result<List<Campus>> getCampusByOrgId(@RequestParam Integer org_id,
			HttpServletRequest request, HttpServletResponse response) {			
    	Result<List<Campus>> result = new Result<List<Campus>>();	
    	List<Campus> campus = this.campusService.getByOrgId(org_id);
    	CompusToSortComparator cmc = new CompusToSortComparator();  
        Collections.sort(campus,cmc); 
    	result.setData(campus);
		return result;
	}
    
    
    @ResponseBody
	@RequestMapping(value = "/getIcon")
	public Result<String> getIcon(@RequestParam Integer org_id,@RequestParam Integer identity,
			HttpServletRequest request, HttpServletResponse response) {			
    	Result<String> result = new Result<String>();
    	//通过数据库读取
    	/*StringBuffer onUsing = new StringBuffer();
    	List<Icon> list = iconService.getOnUsing(org_id,identity);
    	for (Icon icon : list) {
    		if(Constant.IDENTITY_PARENT==identity&&icon.getP_show()==Constant.YES){
    			onUsing.append(icon.getP_key()+",");
    		}else if(Constant.IDENTITY_TEACHER==identity&&icon.getT_show()==Constant.YES){
    			onUsing.append(icon.getT_key()+",");
    		}else if(Constant.IDENTITY_STUDENT==identity&&icon.getS_show()==Constant.YES){
    			onUsing.append(icon.getS_key()+",");
    		}else if(Constant.IDENTITY_MANAGER==identity){
    			onUsing.append(icon.getS_key()+",");
    		}
		}
    	if(onUsing.toString().indexOf(",")!=0){
    		result.setData(onUsing.toString().substring(0, onUsing.toString().length()-1));
    	}*/
    	//通过缓存读取启用的图标
//    	result.setData(redisIconDao.getOnUsing(org_id, identity));
    	//通过缓存读取禁用的图标
    	result.setData(redisIconDao.getOnClosing(org_id, identity));
    	result.setSuccess(true);
		return result;
	}
    
    
}
