package cn.edugate.esb.web.api;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.edugate.esb.Result;
import cn.edugate.esb.authentication.FPSet;
import cn.edugate.esb.eduEnum.EnumMessage;
import cn.edugate.esb.eduEnum.EnumRoleLabel;
import cn.edugate.esb.entity.Menu;
import cn.edugate.esb.entity.Organization;
import cn.edugate.esb.entity.Role;
import cn.edugate.esb.entity.RoleLabel;
import cn.edugate.esb.entity.Teacher;
import cn.edugate.esb.entity.TeacherRole;
import cn.edugate.esb.params.EduJsonFilter;
import cn.edugate.esb.params.EduJsonFilters;
import cn.edugate.esb.params.filter.MenuFilter;
import cn.edugate.esb.params.filter.RoleFilter;
import cn.edugate.esb.params.filter.RoleLabelFilter;
import cn.edugate.esb.params.filter.TeacherFilter;
import cn.edugate.esb.redis.dao.RedisMenuDao;
import cn.edugate.esb.redis.dao.RedisOrganizationDao;
import cn.edugate.esb.redis.dao.RedisRoleDao;
import cn.edugate.esb.redis.dao.RedisTeacherDao;
import cn.edugate.esb.service.OrganizationService;
import cn.edugate.esb.service.RoleService;
import cn.edugate.esb.service.TeacherRoleService;
import cn.edugate.esb.service.TeacherService;
import cn.edugate.esb.service.UserService;
import cn.edugate.esb.util.Constant;
import cn.edugate.esb.util.PinyinUtil;
import cn.edugate.esb.util.Util;

/**
 * 
 * @Name: 权限管理controller
 * @Description: 维护权限(添加和删除权限)。
 * @date  2013-4-11
 * @version V1.0
 */
@Controller
@RequestMapping("/api/role")
public class RoleController {
	static Logger logger=LoggerFactory.getLogger(RoleController.class);
	private TeacherRoleService teacherRoleService;
	private RedisTeacherDao redisTeacherDao;
	private TeacherService teacherService;
	private TeacherRoleService user2roleService;
	private RedisMenuDao redisMenuDao;
	private RedisOrganizationDao redisOrganizationDao;
	private RedisRoleDao redisRoleDao;
	@Autowired
	public void setRedisRoleDao(RedisRoleDao redisRoleDao) {
		this.redisRoleDao = redisRoleDao;
	}
	@Autowired
	public void setRedisOrganizationDao(RedisOrganizationDao redisOrganizationDao) {
		this.redisOrganizationDao = redisOrganizationDao;
	}
	@Autowired
	public void setUser2roleService(TeacherRoleService user2roleService) {
		this.user2roleService = user2roleService;
	}
	@Autowired
	public void setTeacherService(TeacherService teacherService) {
		this.teacherService = teacherService;
	}
	@Autowired
	public void setRedisTeacherDao(RedisTeacherDao redisTeacherDao) {
		this.redisTeacherDao = redisTeacherDao;
	}
	@Autowired
	public void setTeacherRoleService(TeacherRoleService teacherRoleService) {
		this.teacherRoleService = teacherRoleService;
	}
	@Autowired
	public void setRedisMenuDao(RedisMenuDao redisMenuDao) {
		this.redisMenuDao = redisMenuDao;
	}


	private RoleService roleService;
	@SuppressWarnings("unused")
	private UserService userService;
	@SuppressWarnings("unused")
	private OrganizationService organizationService;
	@SuppressWarnings("unused")
	private Util util;
	
	@Autowired
	public void setOrganizationService(OrganizationService organizationService) {
		this.organizationService = organizationService;
	}
	
	@Autowired
	public void setUtil(Util util) {
		this.util = util;
	}
	@Autowired
	public void setRoleService(RoleService roleService) {
		this.roleService = roleService;
	}	
	@Autowired
	public void setUserService(UserService userService) {
		this.userService = userService;
	}

	@RequestMapping(value = "/list")
	@EduJsonFilters(value={@EduJsonFilter(mixin=RoleFilter.class, target=Role.class)})
	public @ResponseBody Result<List<Role>> list(@RequestParam Integer org_id,
			HttpServletRequest request){
		Result<List<Role>> result = new Result<List<Role>>();
		List<Role> ls = this.roleService.getByOrgId(org_id);
		if(ls!=null){
			for (Role role : ls) {
				if(role.getRole_name()!=null&&!"".equals(role.getRole_name())){
					role.setFirstLetter(PinyinUtil.hanziToPinyin(role.getRole_name()).substring(0,1));
				}
			}
		}
		result.setData(ls);
		result.setSuccess(true);
		return result;
	}
	
	@RequestMapping(value = "/getRoleFPSet")
	@EduJsonFilters(value={@EduJsonFilter(mixin=RoleFilter.class, target=Role.class)})
	public @ResponseBody Result<List<FPSet>> getRoleFPSet(@RequestParam Integer role_id,
			HttpServletRequest request){
		Result<List<FPSet>> result = new Result<List<FPSet>>();
		if(role_id!=null){
//			Map<String,List<FPSet>> fsmap = new HashMap<String, List<FPSet>>();
			List<FPSet> fps = this.roleService.getFPSetsByRole(role_id);
//			for (FPSet fpSet : fps) {
//				String keystr = (fpSet.getFun_id()!=null&&!"".equals(fpSet.getFun_id()))?fpSet.getFun_id():"0";
//				Function function = this.functionService.getById(Integer.parseInt(keystr));
//				if(function!=null){
//					keystr +=":"+function.getFun_name();
//				}else{
//					keystr +=":"+"暂无分类";
//				}
//				if(fsmap.containsKey(keystr)){
//					fsmap.get(keystr).add(fpSet);
//				}else{
//					List<FPSet> innertemp = new ArrayList<>();
//					innertemp.add(fpSet);
//					fsmap.put(keystr, innertemp);
//				}
//			}
			result.setData(fps);
			result.setSuccess(true);
		}
		return result;
	}

	@RequestMapping(value="/fpsets_update")
	public @ResponseBody Result<String> fpsets_update(@RequestParam Integer role_id,
			@RequestParam String authorities) {
		Result<String> result=new Result<String>();		
		Role role = this.roleService.getRoleById(role_id);
		role.setAuthorities(authorities);
		this.roleService.update(role);
		result.setMessage("更新成功");
		result.setSuccess(true);		
		return result;
	}
	
	@RequestMapping(value="/updateAuth")
	public @ResponseBody Result<String> updateAuth(@RequestParam Integer roleid,
			@RequestParam String auth) {
		Result<String> result=new Result<String>();		
		Role role = redisRoleDao.getByRoleId(roleid.toString());
		role.setAuthorities(auth);
		this.roleService.update(role);
		result.setMessage("更新成功");
		result.setSuccess(true);		
		return result;
	}
	
	@RequestMapping(value="/getRoleLabels")
	public @ResponseBody List<RoleLabel> getRoleLabels(@RequestParam Integer org_id) {
//		Result<List<RoleLabel>> result=new Result<List<RoleLabel>>();	
		Organization org = this.redisOrganizationDao.getByOrgId(org_id.toString(), null);
		List<RoleLabel> rls =  this.roleService.getRoleLabels(org.getType());
//		result.setData(rls);
//		result.setSuccess(true);		
		return rls;
	}
	
	@RequestMapping(value="/getRoleLabels_json")
	public @ResponseBody Result<List<RoleLabel>>  getRoleLabels_json(@RequestParam Integer org_id) {
		Result<List<RoleLabel>> result=new Result<List<RoleLabel>>();	
		Organization org = this.redisOrganizationDao.getByOrgId(org_id.toString(), null);
		List<RoleLabel> rls =  this.roleService.getRoleLabels(org.getType());
		result.setData(rls);
		result.setSuccess(true);		
		return result;
	}
	
	@RequestMapping(value = "/add",method = RequestMethod.POST)
	@EduJsonFilters(value={@EduJsonFilter(mixin=RoleFilter.class, target=Role.class)})
	public @ResponseBody Result<Role> add(@RequestParam String roleName,@RequestParam String roleNote
			,@RequestParam Integer org_id,@RequestParam Integer rl_id,@RequestParam String authorities){
		Result<Role> result = new Result<Role>();
		Role role = new Role();
		role.setRole_name(roleName);
		role.setRole_note(roleNote);
		role.setOrg_id(org_id);
		role.setRl_id(rl_id);
		if(rl_id==EnumRoleLabel.管理员.getCode() ||rl_id==EnumRoleLabel.校长.getCode()||rl_id==EnumRoleLabel.分校校长.getCode()||rl_id==EnumRoleLabel.局管理员.getCode()||rl_id==EnumRoleLabel.局长.getCode()){
			authorities = "{\""+Constant.ALL_AUTH+"\":262143}";
		}
		role.setAuthorities(authorities);
		role.setIs_del(false);
		role.setInsert_time(new Date());
		this.roleService.add(role);
		result.setData(role);
		result.setSuccess(true);
		return result;
	}
	
	@RequestMapping(value = "/update",method = RequestMethod.POST)
	@EduJsonFilters(value={@EduJsonFilter(mixin=RoleFilter.class, target=Role.class)})
	public @ResponseBody Result<Role> update(@RequestParam Integer roleId,@RequestParam String roleName,@RequestParam String roleNote
			,@RequestParam Integer org_id,@RequestParam Integer rl_id,@RequestParam String authorities){
		Result<Role> result = new Result<Role>();
		Role role = this.roleService.getById(roleId);
		role.setRole_name(roleName);
		role.setRole_note(roleNote);
		role.setOrg_id(org_id);
		role.setRl_id(rl_id);
		role.setAuthorities(authorities);
		this.roleService.update(role);
		result.setData(role);
		result.setSuccess(true);
		return result;
	}
	
	@RequestMapping(value = "/delete",method = RequestMethod.POST)
	@EduJsonFilters(value={@EduJsonFilter(mixin=RoleFilter.class, target=Role.class)})
	public @ResponseBody Result<String> delete(@RequestParam Integer roleId){
		Result<String> result = new Result<String>();	
		this.roleService.deleteById(roleId);
		result.setSuccess(true);
		result.setMessage("删除成功");
		return result;
	}
	
//	@RequestMapping(value = "/getRoleLabels",method = RequestMethod.GET)
//	@EduJsonFilters(value={@EduJsonFilter(mixin=RoleLabelFilter.class, target=RoleLabel.class)})
//	public @ResponseBody Result<List<RoleLabel>> getRoleLabels(@RequestParam Integer rl_type){
//		Result<List<RoleLabel>> result = new Result<List<RoleLabel>>();	
//		List<RoleLabel> ls = this.roleService.getRoleLabels();
//		result.setData(ls);
//		result.setSuccess(true);
//		return result;
//	}
	
	@RequestMapping(value = "/setTeacherRoles",method = RequestMethod.POST)
	@EduJsonFilters(value={@EduJsonFilter(mixin=RoleLabelFilter.class, target=RoleLabel.class)})
	public @ResponseBody Result<List<RoleLabel>> setTeacherRoles(@RequestParam Integer tech_id,@RequestParam String roleIds){
		Result<List<RoleLabel>> result = new Result<List<RoleLabel>>();	
		String[] ids = roleIds.split(",");
		Teacher Teacher = this.redisTeacherDao.getByTechId(tech_id.toString(),null);
		List<TeacherRole> user2roleList = new ArrayList<TeacherRole>();
		for (String id : ids) {
			TeacherRole u2r = this.teacherRoleService.getTeachRole(Integer.parseInt(id),Teacher.getUser_id());
			if(u2r==null){
				u2r = new TeacherRole();
				u2r.setRole_id(Integer.parseInt(id));
				u2r.setTech_id(Teacher.getUser_id());
				u2r.setInsert_time(new Date());
				u2r.setIs_del(false);
			}
			user2roleList.add(u2r);
		}
		this.teacherRoleService.saveOrUpdateUser2Role(user2roleList);
		result.setSuccess(true);
		return result;
	}		

	@ResponseBody
	@RequestMapping(value = "/getTeacherRole")	
	public Result<List<Role>> getTeacherRole(@RequestParam Integer tech_id,@RequestParam Integer org_id,HttpServletRequest request, HttpServletResponse response) throws IOException  {		
		Result<List<Role>> result = new Result<List<Role>>();	
		Teacher teacher = this.teacherService.getTechById(tech_id);
		if(teacher!=null&&teacher.getUser_id()!=null){
			List<Role> roles = this.roleService.getByUserId(teacher.getUser_id(),org_id);
			result.setData(roles);
			result.setSuccess(true);
		}
		return result;
	}
	
	@SuppressWarnings("rawtypes")
	@ResponseBody
	@RequestMapping(value = "/getUserAuth")	
	public Result<Map<String,Integer>> getUserAuth(
			@RequestParam Integer user_id,
			@RequestParam Integer org_id,
			HttpServletRequest request, 
			HttpServletResponse response) throws IOException  {		
		Result<Map<String,Integer>> result = new Result<Map<String,Integer>>();	
		List<Role> roles = this.roleService.getByUserId(user_id,org_id); 
		Map<String,Integer> rights = new HashMap<String,Integer>();
		for (Role role : roles) {
			if(!"".equals(role.getAuthorities())){
				JSONObject authorities = new JSONObject(role.getAuthorities());
				Iterator iterator = authorities.keys();
				while(iterator.hasNext()){
		            String key = iterator.next().toString();
		            if(Constant.ALL_AUTH.equals(key)){
		            	rights = new HashMap<String,Integer>();
		            	Integer value = authorities.getInt(key);
		            	rights.put(key, value);
		            	result.setData(rights);
		        		result.setSuccess(true);
		        		result.setMessage(EnumMessage.success.getMessage());
		        		result.setCode(EnumMessage.success.getCode());
		        		return result;
		            }
		            Integer value = authorities.getInt(key);
		            if(null!=rights.get(key)){
		            	Integer current = rights.get(key)|value;
		            	rights.put(key,current);
		            }else{
		            	rights.put(key,value);
		            }
				}
			}
		}
		result.setData(rights);
		result.setSuccess(true);
		result.setMessage(EnumMessage.success.getMessage());
		result.setCode(EnumMessage.success.getCode());
		return result;
	}

	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "/getOrgUserMenus")
	@EduJsonFilters(value={@EduJsonFilter(mixin=MenuFilter.class, target=Menu.class)})
	public @ResponseBody Result<List<Menu>> getOrgUserMenus(
			@RequestParam(value="org_id") Integer org_id,
			@RequestParam(value="user_id") Integer user_id,
			@RequestParam(value="category", defaultValue="") String category,
			HttpServletRequest request) {
		
		String token = request.getParameter("token");
		String udid = request.getParameter("udid");
		
		Result<List<Menu>> result = new Result<List<Menu>>();
		List<Menu> menus = new ArrayList<Menu>();
		Set<String> parentMenuCode = new HashSet<String>();
		Map<String,Menu> menusMap = redisMenuDao.getOrgMenusForCode(org_id,false,token,udid);
		String[] categoryArr = new String[]{};
		if(StringUtils.isNotEmpty(category)){
			categoryArr = category.split(",");
			if(null!=menusMap){
				List<Role> roles = this.roleService.getByUserId(user_id,org_id); 
				for (Role role : roles) {
					if(!"".equals(role.getAuthorities())){
						JSONObject authorities = new JSONObject(role.getAuthorities());
						Iterator iterator = authorities.keys();
						while(iterator.hasNext()){
							String key = iterator.next().toString();
							if(Constant.ALL_AUTH.equals(key)){
								for (Menu m : menusMap.values()) {
									if(Arrays.asList(categoryArr).contains(m.getCategory()+"")){
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
				}
				for (String code : parentMenuCode) {
					if(menusMap.containsKey(code)){
						if(Arrays.asList(categoryArr).contains(menusMap.get(code).getCategory()+"")){
							menus.add(menusMap.get(code));
						}
					}
				}
			}
		}else{
			if(null!=menusMap){
				List<Role> roles = this.roleService.getByUserId(user_id,org_id); 
				Map<String,Object> onemap = new HashMap<String,Object>();
				for (Role role : roles) {
					//if(!"".equals(role.getAuthorities())){
					if(StringUtils.isNotBlank(role.getAuthorities())){
						JSONObject authorities = new JSONObject(role.getAuthorities());
						Iterator iterator = authorities.keys();
						while(iterator.hasNext()){
							String key = iterator.next().toString();
							onemap.put(key, key);
							
//							if(Constant.ALL_AUTH.equals(key)){
//								menus = new ArrayList<Menu>(menusMap.values());
//								result.setData(menus);
//								result.setSuccess(true);
//								result.setMessage(EnumMessage.success.getMessage());
//								result.setCode(EnumMessage.success.getCode());
//								return result;
//							}else{
//								if(menusMap.containsKey(key)){//包含
//									parentMenuCode.add(key.substring(0,key.length()-2));
//									menus.add(menusMap.get(key));
//								}
//							}
						}
					}
				}
				
				if(onemap!=null &&onemap.size()>0){
					for (String key : onemap.keySet()) {  
						if(Constant.ALL_AUTH.equals(key)){
							menus = new ArrayList<Menu>(menusMap.values());
							result.setData(menus);
							result.setSuccess(true);
							result.setMessage(EnumMessage.success.getMessage());
							result.setCode(EnumMessage.success.getCode());
							return result;
						}else{
							if(menusMap.containsKey(key)){//包含
								parentMenuCode.add(key.substring(0,key.length()-2));
								menus.add(menusMap.get(key));
							}
						}
					}  
				}
				for (String code : parentMenuCode) {
					if(menusMap.containsKey(code)){
						menus.add(menusMap.get(code));
					}
				}
			}
		}
		result.setData(menus);
		result.setSuccess(true);
		result.setMessage(EnumMessage.success.getMessage());
		result.setCode(EnumMessage.success.getCode());
		return result;
	}
	
	@RequestMapping(value = "/getRoleCode")
	public @ResponseBody Result<Map<String, Object>> getRoleCode(
			@RequestParam(value="org_id") Integer org_id,
			HttpServletRequest request) {
		Result<Map<String, Object>> result = new Result<Map<String, Object>>();
		Map<String,Object> map = new HashMap<String,Object>();
		List<Object> codeList = roleService.getOrgRoleCode(org_id);
		List<Object> roleList = roleService.getOrgRole(org_id);
		map.put("code", codeList);
		map.put("role", roleList);
		result.setData(map);
		result.setSuccess(true);
		result.setMessage(EnumMessage.success.getMessage());
		result.setCode(EnumMessage.success.getCode());
		return result;
	}
	
	@ResponseBody
	@RequestMapping(value = "/editRoleSetSave")	
	public Result<String> editRoleSetSave(@RequestParam Integer tech_id,@RequestParam String role_ids,
			HttpServletRequest request, HttpServletResponse response) throws IOException  {		
		Result<String> result = new Result<String>();	
		Teacher teacher = this.teacherService.getTechById(tech_id);
		if(teacher!=null&&teacher.getUser_id()!=null){
			String[] ids = role_ids.split(",");
			if(teacher.getUser_id()!=null&&!"".equals(teacher.getUser_id())){
				this.user2roleService.saveOrUpdateUser2Role(teacher.getUser_id(),ids);
			}
		}
		result.setSuccess(true);
		return result;
	}
	
	@ResponseBody
	@RequestMapping(value = "/deleteRole")	
	public Result<String> deleteRole(@RequestParam Integer role_id,
			HttpServletRequest request, HttpServletResponse response) throws IOException  {		
		Result<String> result = new Result<String>();	
		Role role = this.roleService.getById(role_id);
		if(role!=null){
			this.roleService.removeRole(role);
//			Map<String,TeacherRole> trs = this.redisTeacherRoleDao.getByRole_id(role_id);
			List<TeacherRole> trlist = this.teacherRoleService.getTeachRoleByRoleId(role_id);
			TeacherRole[] trarray = trlist.toArray(new TeacherRole[trlist.size()]);
			for (TeacherRole tr : trarray) {
				this.teacherRoleService.removetr(tr);
			}
			
			result.setSuccess(true);
		}else{
			result.setSuccess(false);
			result.setMessage("找不到该权限");
		}
		return result;
	}
	
	@RequestMapping(value = "/getTeachersByRoleName")
	@EduJsonFilters(value = { @EduJsonFilter(mixin = TeacherFilter.class, target = Teacher.class) })
	public @ResponseBody Result<List<Teacher>> getTeachersByRoleName(
			@RequestParam String role_name,
			@RequestParam Integer org_id,
			HttpServletRequest request) {
		Result<List<Teacher>> result = new Result<List<Teacher>>();		
		List<Role> roles = this.roleService.getByRoleName(role_name,org_id);
		if(roles!=null&&roles.size()>0){
			Integer roleId = roles.get(0).getRole_id();
			List<Teacher> teachers = this.roleService.getTeachersByRoleId(roleId);
			result.setData(teachers);
			result.setSuccess(true);
			result.setMessage(EnumMessage.success.getMessage());
		}else{
			result.setMessage("找不到该角色");
		}		
		return result;
	}
}
