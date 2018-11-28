package cn.edugate.esb.web.api;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import cn.edugate.esb.Result;
import cn.edugate.esb.comparator.GroupToSortComparator;
import cn.edugate.esb.comparator.StudentToSortComparator;
import cn.edugate.esb.eduEnum.EnumRoleLabel;
import cn.edugate.esb.entity.Classes;
import cn.edugate.esb.entity.OrgUser;
import cn.edugate.esb.entity.Organization;
import cn.edugate.esb.entity.Parent;
import cn.edugate.esb.entity.Role;
import cn.edugate.esb.entity.Student;
import cn.edugate.esb.entity.Teacher;
import cn.edugate.esb.entity.TeacherRole;
import cn.edugate.esb.entity.UcUser;
import cn.edugate.esb.entity.UcuserOrguser;
import cn.edugate.esb.entity.UserAccount;
import cn.edugate.esb.exception.EnumException;
import cn.edugate.esb.exception.EsbException;
import cn.edugate.esb.params.EduJsonFilters;
import cn.edugate.esb.params.EduJsonFilter;
import cn.edugate.esb.params.filter.ClassesShortFilterBF;
import cn.edugate.esb.params.filter.OrgUserFilter;
import cn.edugate.esb.params.filter.OrgUserFilterWX;
import cn.edugate.esb.params.filter.OrganizationFilter;
import cn.edugate.esb.params.filter.OrganizationShortFilter;
import cn.edugate.esb.params.filter.ParentFilter;
import cn.edugate.esb.params.filter.RoleFilter;
import cn.edugate.esb.params.filter.StudentShortFilter;
import cn.edugate.esb.params.filter.TeacherFilter;
import cn.edugate.esb.params.filter.TeacherForGroupMemberFilter;
import cn.edugate.esb.params.filter.UcUserFilter;
import cn.edugate.esb.pojo.Login;
import cn.edugate.esb.redis.dao.RedisLoginDao;
import cn.edugate.esb.redis.dao.RedisOrgUserDao;
import cn.edugate.esb.redis.dao.RedisOrganizationDao;
import cn.edugate.esb.redis.dao.RedisParentDao;
import cn.edugate.esb.redis.dao.RedisRoleDao;
import cn.edugate.esb.redis.dao.RedisStudentParentDao;
import cn.edugate.esb.redis.dao.RedisTeacherDao;
import cn.edugate.esb.redis.dao.RedisTeacherRoleDao;
import cn.edugate.esb.redis.dao.RedisTechRangeDao;
import cn.edugate.esb.redis.dao.RedisUcUserDao;
import cn.edugate.esb.redis.dao.RedisUcUserOrguserDao;
import cn.edugate.esb.service.ResourcesService;
import cn.edugate.esb.service.TeacherService;
import cn.edugate.esb.service.UserService;
import cn.edugate.esb.util.Base64;
import cn.edugate.esb.util.Constant;
import cn.edugate.esb.util.DateUtil;
import cn.edugate.esb.util.Util;
import cn.edugate.esb.util.Utils;
import cn.edugate.esb.version.ApiVersion;

@Controller
@RequestMapping("/api")
public class UserController {
	static Logger logger=LoggerFactory.getLogger(UserController.class);
	
	private Util util;
	private UserService userService;
	private RedisUcUserOrguserDao redisUcUserOrguserDao;
	private RedisUcUserDao redisUcUserDao;
	private RedisOrgUserDao redisOrgUserDao;
	private RedisOrganizationDao redisOrganizationDao;
	private RedisLoginDao redisLoginDao;
	private ResourcesService resourcesService;
	private TeacherService teacherService;
	private RedisTeacherDao redisTeacherDao;
	private RedisTeacherRoleDao redisTeacherRoleDao;
	private RedisRoleDao redisRoleDao;
	private RedisTechRangeDao  redisTechRangeDao;
	private RedisParentDao redisParentDao;
	private RedisStudentParentDao  redisStudentParentDao;
	
	@Autowired
	public void setRedisParentDao(RedisParentDao redisParentDao) {
		this.redisParentDao = redisParentDao;
	}
	@Autowired
	public void setRedisStudentParentDao(
			RedisStudentParentDao redisStudentParentDao) {
		this.redisStudentParentDao = redisStudentParentDao;
	}
	@Autowired
	public void setRedisTeacherRoleDao(RedisTeacherRoleDao redisTeacherRoleDao) {
		this.redisTeacherRoleDao = redisTeacherRoleDao;
	}
	@Autowired
	public void setRedisRoleDao(RedisRoleDao redisRoleDao) {
		this.redisRoleDao = redisRoleDao;
	}

	@Autowired
	public void setRedisTeacherDao(RedisTeacherDao redisTeacherDao) {
		this.redisTeacherDao = redisTeacherDao;
	}

	@Autowired
	public void setTeacherService(TeacherService teacherService) {
		this.teacherService = teacherService;
	}

	@Autowired
	public void setUtil(Util util) {
		this.util = util;
	}

	@Autowired
	public void setResourcesService(ResourcesService resourcesService) {
		this.resourcesService = resourcesService;
	}	

	@Autowired
	public void setRedisLoginDao(RedisLoginDao redisLoginDao) {
		this.redisLoginDao = redisLoginDao;
	}

	@Autowired
	public void setRedisOrganizationDao(RedisOrganizationDao redisOrganizationDao) {
		this.redisOrganizationDao = redisOrganizationDao;
	}

	@Autowired
	public void setRedisOrgUserDao(RedisOrgUserDao redisOrgUserDao) {
		this.redisOrgUserDao = redisOrgUserDao;
	}

	@Autowired
	public void setRedisUcUserDao(RedisUcUserDao redisUcUserDao) {
		this.redisUcUserDao = redisUcUserDao;
	}

	@Autowired
	public void setRedisUcUserOrguserDao(RedisUcUserOrguserDao redisUcUserOrguserDao) {
		this.redisUcUserOrguserDao = redisUcUserOrguserDao;
	}

	@Autowired
	public void setUserService(UserService userService) {
		this.userService = userService;
	}
	@Autowired
	public void setRedisTechRangeDao(RedisTechRangeDao redisTechRangeDao) {
		this.redisTechRangeDao = redisTechRangeDao;
	}
	@ResponseBody
	@RequestMapping(value = "/uclogin")
	@EduJsonFilters(value={@EduJsonFilter(mixin=UcUserFilter.class, target=UcUser.class),
			@EduJsonFilter(mixin=OrgUserFilter.class, target=OrgUser.class)})
	public Result<Map<String, Object>> uclogin(@RequestParam String login_name,
			@RequestParam String login_pass,
			@RequestParam(defaultValue="0") Integer version,
			@RequestParam(defaultValue="") String udid,
			HttpServletRequest request, HttpServletResponse response) throws IOException  {		
		Result<Map<String, Object>> result = new Result<Map<String, Object>>();		
		Map<String, Object> data = new HashMap<String, Object>();
		UcUser user = null;
		try {
			user = this.userService.uclogin(login_name,login_pass,version);
			if("".equals(udid)){
				UUID uuid = UUID.randomUUID();
				udid = uuid.toString();
			}else{
				if(udid.length()<8){
					throw new EsbException(EnumException.user_invalid_decode_error);
				}
			}
			List<OrgUser> orgusers = new ArrayList<OrgUser>();
			UserAccount ua = null;
			
			ua = this.userService.getUserAccount(user.getUc_id().toString(),version.toString(),"1");
			this.userService.addUserAccount(user.getUc_id().toString(),version,udid,1,null,null);
			
			Map<String,UcuserOrguser> orgus = this.redisUcUserOrguserDao.getByUcId(user.getUc_id().toString());
			if(orgus!=null){
				for (String orguserid : orgus.keySet()) {
					OrgUser ou = this.redisOrgUserDao.getUserById(orguserid);
					orgusers.add(ou);
				}
			}						 
			Boolean first_login = ua!=null?false:true;			
			String type = "1";
			String token = this.userService.createToken(user.getUc_id(),user.getUc_salt(),version,udid,type);
			data.put("ucuser", user);
			data.put("orgusers", orgusers);
			data.put("token", token);
			data.put("udid", udid);
			data.put("first_login", first_login);
			
			Login login = new Login();
			login.setType(type);
			login.setUser_id(user.getUc_id().toString());
			login.setExtratime(0L);
			this.redisLoginDao.add(login);
			
			result.setData(data);
			result.setMessage("登陆成功");
			result.setSuccess(true);
		} catch (EsbException e) {
			// TODO Auto-generated catch block
			result.setMessage(e.getMessage());
			result.setCode(e.getCode());
			result.setSuccess(false);
		}	
		return result;
	}

	@ResponseBody
	@RequestMapping(value = "/orglogin")
	@EduJsonFilters(value={@EduJsonFilter(mixin=UcUserFilter.class, target=UcUser.class),
			@EduJsonFilter(mixin=OrgUserFilter.class, target=OrgUser.class),
			@EduJsonFilter(mixin=RoleFilter.class, target=Role.class),
			@EduJsonFilter(mixin=TeacherFilter.class, target=Teacher.class),
			@EduJsonFilter(mixin=OrganizationFilter.class, target=Organization.class)}
	)
	public Result<Map<String, Object>> orglogin(@RequestParam String login_name,
			@RequestParam String login_pass,
			@RequestParam(defaultValue="0") Integer version,
			@RequestParam(defaultValue="") String udid,
			HttpServletRequest request, HttpServletResponse response) throws IOException  {		
		Result<Map<String, Object>> result = new Result<Map<String, Object>>();		
		Map<String, Object> data = new HashMap<String, Object>();
		OrgUser user = null;
		try {
			
			String identity = request.getParameter("identity")!=null?request.getParameter("identity"):"1";
			
			Map<String, OrgUser> users = this.userService.login(login_name,login_pass,identity);
			if(users==null||users.size()<=0){
				throw new EsbException(EnumException.user_password_wrong);
			}
			if("".equals(udid)){
				UUID uuid = UUID.randomUUID();
				udid = uuid.toString();
			}else{
				if(udid.length()<8){
					throw new EsbException(EnumException.user_invalid_decode_error);
				}
			}
			user = users.get(Utils.getFirstOrNull(users));			
			
			UcuserOrguser uo = this.redisUcUserOrguserDao.getByUserId(user.getUser_id().toString());
			UcUser ucuser = null;
			List<OrgUser> orgusers = new ArrayList<OrgUser>();
			UserAccount ua = null;
			if(uo!=null){
				ua = this.userService.getUserAccount(uo.getUc_id().toString(),version.toString(),"1");
				this.userService.addUserAccount(uo.getUc_id().toString(),version,udid,user.getUser_id(),user.getOrg_id(),1);
				ucuser = this.redisUcUserDao.getUserById(uo.getUc_id().toString());
				Map<String,UcuserOrguser> orgus = this.redisUcUserOrguserDao.getByUcId(uo.getUc_id().toString());
				if(orgus!=null){
					for (String orguserid : orgus.keySet()) {
						OrgUser ou = this.redisOrgUserDao.getUserById(orguserid);
						/**
						Organization org = this.redisOrganizationDao.getByOrgId(ou.getOrg_id().toString(),null);
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
						ou.setOrganization(org);**/
						
						String targetIdentity = ou.getIdentity().equals(99)?"1":ou.getIdentity().toString();
						if(identity.equals(targetIdentity)){
							orgusers.add(ou);
						}
					}
				}
			}else{
				ua = this.userService.getUserAccount(user.getUser_loginname(),version.toString(),"0");
				this.userService.addUserAccount(user.getUser_loginname(),version,udid,user.getUser_id(),user.getOrg_id(),0);
//				OrgUser ou = this.redisOrgUserDao.getUserById(user.getUser_id().toString());
				
				for (OrgUser orgUser : users.values()) {
					/**
					Organization org = this.redisOrganizationDao.getByOrgId(orgUser.getOrg_id().toString(),null);
					if(org!=null){
						if (StringUtils.isNotEmpty(org.getLogo())) {
							org.setLogoUrl(util.getPathByPicName(org.getLogo()));
						} else {
							org.setLogoUrl("");
						}
						List<String> bgUrlList = new ArrayList<String>();
						if (StringUtils.isNotEmpty(org.getBg())) {
							String[] bgNameArray = org.getBg().split(";");
							for (String bgName : bgNameArray) {
								String bgUrl = util.getPathByPicName(bgName);
								bgUrlList.add(bgUrl);
							}
						}
						org.setBgUrlList(bgUrlList);
					}
					List<Role> roleList = new ArrayList<Role>();
					if(redisTeacherRoleDao!=null){
				    	Map<String, TeacherRole> maps = this.redisTeacherRoleDao.getByTeacherId(orgUser.getUser_id().toString());
				    	if(maps!=null){
				        	for (String roleid : maps.keySet()) {
				    			Role role = redisRoleDao.getByRoleId(roleid);
				    			if(role!=null&&role.getOrg_id()==org.getOrg_id()){
				    				roleList.add(role);
				    			}
				    		} 
				        	orgUser.setRoles(roleList);
				    	}
					}
					orgUser.setOrganization(org);
					**/
					String rlids = "";
					Map<String, TeacherRole> maps = this.redisTeacherRoleDao.getByTeacherId(orgUser.getUser_id().toString());
					if (maps != null) {
						for (String roleid : maps.keySet()) {
							Role role = this.redisRoleDao.getByRoleId(roleid);
							if (role != null && role.getOrg_id().intValue()==orgUser.getOrg_id()) {
								rlids = rlids+role.getRl_id()+",";
							}
						}
					}
					orgUser.setRlids(rlids);
					
					
					
					
					
					Teacher teacher = this.redisTeacherDao.getByUserId(orgUser.getUser_id().toString());
					Map<String,String> rl_ids = new HashMap<String,String>();
					rl_ids.put(EnumRoleLabel.任课教师.getCode().toString(), EnumRoleLabel.任课教师.getCode().toString());
					rl_ids.put(EnumRoleLabel.班主任.getCode().toString(), EnumRoleLabel.班主任.getCode().toString());
					rl_ids.put(EnumRoleLabel.年级组长.getCode().toString(), EnumRoleLabel.年级组长.getCode().toString());
					List<Map<String,Object>> listIdentity = redisTechRangeDao.getIdentityByIds(teacher.getTech_id().toString(), rl_ids, null);
					orgUser.setIdentityDatas(listIdentity);
					
					
					String targetIdentity = orgUser.getIdentity().equals(99)?"1":orgUser.getIdentity().toString();
					if(identity.equals(targetIdentity)){
						orgusers.add(orgUser);
					}
				}
				
			}			 
			Boolean first_login = ua!=null?false:true;
			
			String type = "0";
			String token = this.userService.createToken(user.getUser_id(),user.getUser_salt(),version,udid,type);
			data.put("ucuser", ucuser);
			data.put("orgusers", orgusers);
			data.put("token", token);
			data.put("udid", udid);
			data.put("first_login", first_login);
			
			Login login = new Login();
			login.setType(type);
			login.setUser_id(user.getUser_id().toString());
			login.setExtratime(0L);
			this.redisLoginDao.add(login);
			
			result.setData(data);
			result.setMessage("登陆成功");
			result.setSuccess(true);
		} catch (EsbException e) {
			// TODO Auto-generated catch block
			result.setMessage(e.getMessage());
			result.setCode(e.getCode());
			result.setSuccess(false);
		}	
		return result;
	}
	
	@RequestMapping(value = "/selectOrg")
	@ApiVersion(1)
	public @ResponseBody Result<OrgUser> selectOrg(@RequestParam Integer org_id,
			HttpServletRequest request) {
		Result<OrgUser> result = new Result<OrgUser>();
		String user_id = request.getAttribute("user_id").toString();
		String type = request.getAttribute("type").toString();
		Map<Integer, Integer> orgmap = new HashMap<Integer, Integer>();
		if("0".equals(type)){
			UcuserOrguser uo = this.redisUcUserOrguserDao.getByUserId(user_id);
			if(uo!=null){
				Map<String,UcuserOrguser> orgus = this.redisUcUserOrguserDao.getByUcId(user_id);
				for (UcuserOrguser uor : orgus.values()) {
					orgmap.put(uor.getOrg_id(), uor.getUser_id());
				}
			}else{
				OrgUser ouser = this.userService.getOrgUserById(Integer.parseInt(user_id));
				Map<String, OrgUser> users = this.redisOrgUserDao.getUserByLoginName(ouser.getUser_loginname());
				for (OrgUser iou : users.values()) {
					orgmap.put(iou.getOrg_id(), iou.getUser_id());
				}
			}
		}else{
			Map<String,UcuserOrguser> orgus = this.redisUcUserOrguserDao.getByUcId(user_id);
			for (UcuserOrguser uo : orgus.values()) {
				orgmap.put(uo.getOrg_id(), uo.getUser_id());
			}
		}
		
		String uaid = request.getAttribute("ua_id").toString();		
		UserAccount ua = this.userService.getUserAccountById(Integer.parseInt(uaid));
		if(orgmap.get(org_id)!=null){
			OrgUser ouser = this.userService.getOrgUserById(orgmap.get(org_id));
			ua.setUser_id(orgmap.get(org_id));
			ua.setOrg_id(org_id);
			this.userService.saveUserAccount(ua);
			result.setData(ouser);
			result.setSuccess(true);
		}else{
			result.setSuccess(false);
			result.setMessage("找不到该机构");
		}		
		return result;
	}	
	
	@RequestMapping(value = "/getuserinfo")
	public @ResponseBody Result<OrgUser> getuserinfo(HttpServletRequest request) {
		Result<OrgUser> result = new Result<OrgUser>();		
		String orgUserId = request.getAttribute("orgUserId").toString();
//		String orgId = request.getAttribute("orgId").toString();
		OrgUser user = this.redisOrgUserDao.getUserById(orgUserId);
//		Organization organization = redisOrganizationDao.getByOrgId(orgId, null);
//		user.setOrganization(organization);
//		Teacher teacher = this.redisTeacherDao.getByUserId(orgUserId);
//		user.setTeacher(teacher);
//		OrgUser user = new OrgUser();
//		user.setUser_id(Integer.parseInt(orgUserId));
//		user.setOrg_id(Integer.parseInt(orgId));
		result.setData(user);
		result.setSuccess(true);
		return result;
	}	
	
	
	@RequestMapping(value = "/validuser1")
	@ApiVersion(1)
	public @ResponseBody Result<Map<String, Object>> validuser01(@RequestParam(value="token") String token,@RequestParam(value="udid") String udid,
			HttpServletRequest request) {
		String user_id =  request.getAttribute("user_id").toString();
		String type = request.getAttribute("type").toString();
		Result<Map<String, Object>> result = new Result<Map<String, Object>>();
		Map<String, Object> data = new HashMap<String, Object>();
		data.put("user_id", user_id);
		data.put("type", type);
		data.put("token", token);
		result.setData(data);
		result.setSuccess(true);
		return result;
	}
	
	
	@RequestMapping(value = "/validuser")
	@ApiVersion(10)
	public @ResponseBody Result<Map<String, Object>> validuser(@RequestParam(value="token") String token,@RequestParam(value="udid") String udid,
			HttpServletRequest request) {
		String user_id =  request.getAttribute("user_id").toString();
		String type = request.getAttribute("type").toString();
//		UcUser ucuser = null;
//		List<OrgUser> orgusers = new ArrayList<OrgUser>();
//		if("0".equals(type)){
//			UcuserOrguser uo = this.redisUcUserOrguserDao.getByUserId(user_id);
//			if(uo!=null){
//				ucuser = this.redisUcUserDao.getUserById(uo.getUc_id().toString());
//			}
//		}else{
//			ucuser = this.redisUcUserDao.getUserById(user_id);
//		}
//		if(ucuser!=null){
//			Map<String,UcuserOrguser> orgus = this.redisUcUserOrguserDao.getByUcId(user_id);
//			if(orgus!=null){
//				for (String orguserid : orgus.keySet()) {
//					OrgUser ou = this.redisOrgUserDao.getUserById(orguserid);
//					orgusers.add(ou);
//				}
//			}
//		}else{
//			OrgUser ou = this.redisOrgUserDao.getUserById(user_id);
//			orgusers.add(ou);
//		}
//		
		Result<Map<String, Object>> result = new Result<Map<String, Object>>();
		Map<String, Object> data = new HashMap<String, Object>();
//		data.put("ucuser", ucuser);
//		data.put("orgusers", orgusers);
		data.put("user_id", user_id);
		data.put("type", type);
		result.setData(data);
		result.setSuccess(true);
		return result;
	}
	
	@RequestMapping(value = "/getorgusers")
	@ApiVersion(10)
	public @ResponseBody Result<List<OrgUser>> getorgusers(@RequestParam(value="token") String token,@RequestParam(value="udid") String udid,
			HttpServletRequest request) {
		String user_id =  request.getAttribute("user_id").toString();
		String type = request.getAttribute("type").toString();
		UcUser ucuser = null;
		List<OrgUser> orgusers = new ArrayList<OrgUser>();
		if("0".equals(type)){
			UcuserOrguser uo = this.redisUcUserOrguserDao.getByUserId(user_id);
			if(uo!=null){
				ucuser = this.redisUcUserDao.getUserById(uo.getUc_id().toString());
			}
		}else{
			ucuser = this.redisUcUserDao.getUserById(user_id);
		}
		if(ucuser!=null){
			Map<String,UcuserOrguser> orgus = this.redisUcUserOrguserDao.getByUcId(user_id);
			if(orgus!=null){
				for (String orguserid : orgus.keySet()) {
					OrgUser ou = this.redisOrgUserDao.getUserById(orguserid);
					orgusers.add(ou);
				}
			}
		}else{
			OrgUser ou = this.redisOrgUserDao.getUserById(user_id);
			orgusers.add(ou);
		}
		Result<List<OrgUser>> result = new Result<List<OrgUser>>();
		result.setData(orgusers);
		result.setSuccess(true);
		return result;
	}
	
	@RequestMapping(value = "/uploadHead")
	public @ResponseBody Result<Map<String, Object>> uploadHead(@RequestParam(value="token") String token,@RequestParam(value="udid") String udid,
			HttpServletRequest request) throws IOException {
		Result<Map<String, Object>> result = new Result<Map<String, Object>>();
		Map<String, Object> data = new HashMap<String, Object>();
		String user_id =  request.getAttribute("user_id").toString();
		String type = request.getAttribute("type").toString();
		MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
		MultipartFile file = multipartRequest.getFile("file");
		BufferedImage bi = ImageIO.read(file.getInputStream());
		int width = 0;
        int height = 0;
		width = bi.getWidth();
        height = bi.getHeight();
		String ext = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf(".")+1);
		if (file==null||file.isEmpty()) {
			result.setSuccess(false);
			result.setMessage("请选择文件");
			return result;
		} 
		InputStream fin = file.getInputStream(); 
		 Long length = file.getSize();
		String filePathName = this.resourcesService.savePicture(Integer.parseInt(type),Integer.parseInt(user_id),fin,length.intValue(),ext,width,height);
		
		switch (type) {
		case "0":
			OrgUser user = this.redisOrgUserDao.getUserById(user_id);
			if(user.getIdentity()!=null&&user.getIdentity()==1){
				Teacher teacher = this.redisTeacherDao.getByUserId(user_id);
				teacher.setTech_head(filePathName);
				teacher.setUpdate_time(new Date());
				this.teacherService.update(teacher);
			}
			break;
		case "1":
			
			break;

		default:
			break;
		}
		
		data.put("url", this.util.getPathByPicName(filePathName));
		result.setData(data);
		result.setSuccess(true);
		return result;
	}
	
	@RequestMapping(value = "/uploadTeacherHead")
	public @ResponseBody Result<Map<String, Object>> uploadTeacherHead(@RequestParam Integer tech_id,
			HttpServletRequest request) throws IOException {
		Result<Map<String, Object>> result = new Result<Map<String, Object>>();
		Map<String, Object> data = new HashMap<String, Object>();
		String user_id =  request.getAttribute("user_id").toString();
		String type = request.getAttribute("type").toString();
		MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
		MultipartFile file = multipartRequest.getFile("file");
		BufferedImage bi = ImageIO.read(file.getInputStream());
		int width = 0;
        int height = 0;
		width = bi.getWidth();
        height = bi.getHeight();
		String ext = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf(".")+1);
		if (file==null||file.isEmpty()) {
			result.setSuccess(false);
			result.setMessage("请选择文件");
			return result;
		} 
		InputStream fin = file.getInputStream(); 
		 Long length = file.getSize();
		String filePathName = this.resourcesService.savePicture(Integer.parseInt(type),Integer.parseInt(user_id),fin,length.intValue(),ext,width,height);		
		Teacher teacher = this.redisTeacherDao.getByTechId(tech_id.toString(),null);
		teacher.setTech_head(filePathName);
		teacher.setUpdate_time(new Date());
		this.teacherService.update(teacher);
			
		data.put("url", this.util.getPathByPicName(filePathName));
		result.setData(data);
		result.setSuccess(true);
		return result;
	}
	
	@RequestMapping(value = "/uploadBase64")
	public @ResponseBody Result<String> uploadBase64(@RequestParam(value="ext") String ext,@RequestParam(value="file") String file,
			@RequestParam(value="width") Integer width,@RequestParam(value="height") Integer height,
			HttpServletRequest request,HttpServletResponse response) throws IOException {
		Result<String> result = new Result<String>();
		String user_id =  request.getAttribute("user_id").toString();
		String type = request.getAttribute("type").toString();
		byte[] buf = Base64.decode(file);
		InputStream sbs = new ByteArrayInputStream(buf); 
		String filePathName = this.resourcesService.savePicture(Integer.parseInt(type),Integer.parseInt(user_id),sbs,buf.length,ext,width,height);		
		result.setData(filePathName);
		result.setSuccess(true);
		return result;
	}
	
	@RequestMapping(value = "/uploadHeadBase64")
	public @ResponseBody Result<String> uploadHeadBase64(@RequestParam(value="ext") String ext,@RequestParam(value="file") String file,
			@RequestParam(value="width") Integer width,@RequestParam(value="height") Integer height,
			HttpServletRequest request,HttpServletResponse response) throws IOException {
		Result<String> result = new Result<String>();
		String user_id =  request.getAttribute("user_id").toString();
		String type = request.getAttribute("type").toString();
		byte[] buf = Base64.decode(file);
		InputStream sbs = new ByteArrayInputStream(buf); 
		String filePathName = this.resourcesService.savePicture(Integer.parseInt(type),Integer.parseInt(user_id),sbs,buf.length,ext,width,height);
		switch (type) {
		case "0":
			OrgUser user = this.redisOrgUserDao.getUserById(user_id);
			if(user.getIdentity()==1){
				Teacher teacher = this.redisTeacherDao.getByUserId(user_id);
				teacher.setTech_head(filePathName);
				teacher.setUpdate_time(new Date());
				this.teacherService.update(teacher);
			}
			break;
		case "1":
			
			break;

		default:
			break;
		}
		result.setData(this.util.getPathByPicName(filePathName));
		result.setSuccess(true);
		return result;
	}
	
	
	
	@RequestMapping(value="/modifyPwd")
	public @ResponseBody Result<Map<String, Object>> modifyPwd(@RequestParam("oldpwd") String oldpwd,@RequestParam("passwd") String passwd,
			HttpServletRequest request) {
		Result<Map<String, Object>> result = new Result<Map<String, Object>>();
		String user_id =  request.getAttribute("user_id").toString();
		String type = request.getAttribute("type").toString();
		
		try {			
			this.userService.modifyPwd(Integer.parseInt(user_id),Integer.parseInt(type),oldpwd,passwd);			
			result.setSuccess(true);
			result.setMessage("密码修改成功");
		} catch (EsbException e) {
			// TODO Auto-generated catch block
			result.setSuccess(false);
			result.setMessage(e.getMessage());
			result.setCode(e.getCode());
		}		
		return result;
	}
	
	@RequestMapping(value="/updateTeacherInfo")
	public @ResponseBody Result<Teacher> updateTeacherInfo(@RequestParam(defaultValue="") String tech_name,@RequestParam(defaultValue="") String tech_nick,
			@RequestParam(defaultValue="") String birthday,@RequestParam(defaultValue="") Integer sex,@RequestParam(defaultValue="") String user_mail,
			@RequestParam(defaultValue="") String tech_card,@RequestParam(defaultValue="") String tech_note,
			HttpServletRequest request) {
		Result<Teacher> result = new Result<Teacher>();
		String tech_id =  request.getParameter("tech_id");
		String type = request.getAttribute("type").toString();
		if(StringUtils.isEmpty(tech_id)){
			result.setSuccess(false);
			result.setMessage("参数错误");
		}
		else{
			switch (type) {
			case "0":
	//			OrgUser user = this.redisOrgUserDao.getUserById(user_id);
	//			if(user.getIdentity()==1){
				
				
				
					Teacher teacher = this.redisTeacherDao.getByTechId(tech_id, null);
					if(teacher!=null){
						if(!"".equals(tech_name)){
							teacher.setTech_name(tech_name);
						}
						if(!"".equals(tech_nick)){
							teacher.setTech_nick(tech_nick);
						}
						if(!"".equals(birthday)){
							teacher.setBirthday(DateUtil.toDate(birthday));
						}
						if(sex!=null){
							teacher.setSex((byte)sex.intValue());
						}
						if(!"".equals(tech_card)){
							teacher.setTech_card(tech_card);
						}
						if(!"".equals(tech_note)){
							teacher.setTech_note(tech_note);
						}
						if(StringUtils.isNotBlank(user_mail)){
							teacher.setUser_mail(user_mail);
						}
						teacher.setUpdate_time(new Date());
						this.teacherService.update(teacher);
						result.setSuccess(true);
						result.setMessage("更新成功");
					}else{
						result.setSuccess(false);
						result.setMessage("找不到教师信息");
					}
	//			}else{
	//				result.setSuccess(false);
	//				result.setMessage("您登陆的信息不是教师身份");
	//			}
				break;
			case "1":
				result.setSuccess(false);
				result.setMessage("暂不支持更新UC用户信息");
				break;
	
			default:
				result.setSuccess(false);
				result.setMessage("用户类型错误");
				break;
			}
		}
		
		return result;
	}
	
	@ResponseBody
	@RequestMapping(value = "/getOrgsByUCID")
	@EduJsonFilters(value={@EduJsonFilter(mixin=UcUserFilter.class, target=UcUser.class),
			@EduJsonFilter(mixin=OrgUserFilter.class, target=OrgUser.class)})
	public Result<Map<String, Object>> getOrgsByUCID(HttpServletRequest request, HttpServletResponse response) throws IOException  {		
		Result<Map<String, Object>> result = new Result<Map<String, Object>>();		
		Map<String, Object> data = new HashMap<String, Object>();
	
		try {
			
			String uc_id = request.getParameter("uc_id");
			List<UcuserOrguser> list = userService.getOrgByUCID(uc_id);
			data.put("orgusers", list);
			
			result.setData(data);
			result.setMessage("获取成功");
			result.setSuccess(true);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			result.setMessage(e.getMessage());
			result.setSuccess(false);
		}	
		return result;
	}
	
	@ResponseBody
	@RequestMapping(value = "/logout")
	public Result<String> logout(@RequestParam(defaultValue="0") Integer version,HttpServletRequest request, HttpServletResponse response) throws IOException  {		
		Result<String> result = new Result<String>();			
		try {
			String ua_id = request.getAttribute("ua_id").toString();
			this.userService.RemoveUserAccountByUaId(Integer.parseInt(ua_id));
			result.setMessage("退出成功");
			result.setSuccess(true);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			result.setMessage(e.getMessage());
			result.setSuccess(false);
		}	
		return result;
	}
	
	
	@RequestMapping(value = "/uploadExcel")
	public @ResponseBody
	Result<Map<String, Object>> uploadExcel(@RequestParam(value = "token") String token,
			@RequestParam(value = "udid") String udid, @RequestParam Integer org_id, HttpServletRequest request)
			throws IOException {
		Result<Map<String, Object>> result = new Result<Map<String, Object>>();
		Map<String, Object> data = new HashMap<String, Object>();
		String user_id = request.getAttribute("user_id").toString();
		String type = request.getAttribute("type").toString();
		MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
		MultipartFile file = multipartRequest.getFile("file");
		String ext = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf(".") + 1);
		if (file == null || file.isEmpty()) {
			result.setSuccess(false);
			result.setMessage("请选择文件");
			return result;
		}
		InputStream fin = file.getInputStream();
		Long length = file.getSize();
		String filePathName = this.resourcesService.saveExcel(Integer.parseInt(type), Integer.parseInt(user_id), fin,
				length.intValue(), ext, org_id, file.getOriginalFilename(), (byte) 1,false);

		data.put("url", this.util.getPathByExcelName(filePathName));
		result.setData(data);
		result.setSuccess(true);
		return result;
	}
	
	
	
	
	@ResponseBody
	@RequestMapping(value = "/wxlogin")
	@EduJsonFilters(value={@EduJsonFilter(mixin=UcUserFilter.class, target=UcUser.class),
			@EduJsonFilter(mixin=OrgUserFilterWX.class, target=OrgUser.class),
			@EduJsonFilter(mixin=RoleFilter.class, target=Role.class),
			@EduJsonFilter(mixin=TeacherFilter.class, target=Teacher.class),
			@EduJsonFilter(mixin=OrganizationShortFilter.class, target=Organization.class),
			@EduJsonFilter(mixin=StudentShortFilter.class, target=Student.class),
			@EduJsonFilter(mixin=ParentFilter.class, target=Parent.class),
			@EduJsonFilter(mixin=ClassesShortFilterBF.class, target=Classes.class),
			@EduJsonFilter(mixin=TeacherForGroupMemberFilter.class, target=Teacher.class)}
	)
	public Result<Map<String, Object>> wxlogin(@RequestParam(defaultValue="") String udid,
			HttpServletRequest request, HttpServletResponse response) throws IOException  {		
		Result<Map<String, Object>> result = new Result<Map<String, Object>>();		
		Map<String, Object> data = new HashMap<String, Object>();
		try {
			
			String identity = request.getParameter("identity");
			String org_id = request.getParameter("org_id");
			String login_name = request.getParameter("login_name");
			String login_pass = request.getParameter("login_pass");
			String version = request.getParameter("version");
			String openid = request.getParameter("openid");
			
			
			if(StringUtils.isEmpty(identity)||StringUtils.isEmpty(org_id)||StringUtils.isEmpty(login_name)||StringUtils.isEmpty(login_pass)||StringUtils.isEmpty(version)||StringUtils.isEmpty(openid)){
//				result.setMessage(EnumException.common_params_error.getMsg());
//				result.setCode(EnumException.common_params_error.getValue());
//				result.setSuccess(false);
//				return result;
				throw new EsbException(EnumException.common_params_error);
			}

			OrgUser user = this.userService.wxlogin(login_name,login_pass,identity,new Integer(org_id));			
			if(user==null){
				throw new EsbException(EnumException.user_password_wrong);
			}
			if("".equals(udid)){
				UUID uuid = UUID.randomUUID();
				udid = uuid.toString();
			}else{
				if(udid.length()<8){
					throw new EsbException(EnumException.user_invalid_decode_error);
				}
			}
			UcuserOrguser uo = this.redisUcUserOrguserDao.getByUserId(user.getUser_id().toString());
			UcUser ucuser = null;
			if(uo!=null){
				ucuser = this.redisUcUserDao.getUserById(uo.getUc_id().toString());
			}
			
			UserAccount ua = this.userService.getUserAccount(user.getUser_loginname(),version,"0");
			this.userService.addUserAccount(user.getUser_loginname(),new Integer(version),udid,user.getUser_id(),user.getOrg_id(),0);
			
			if(Constant.IDENTITY_TEACHER==user.getIdentity()){
				Teacher teacher = this.redisTeacherDao.getByUserId(user.getUser_id().toString());
				if(teacher==null){
					throw new EsbException(EnumException.user_name_not_exist);
				}
				
				List<Role> roles = new ArrayList<Role>();
				//roles.add(new Role());
				user.setRoles(roles);
				
				String rlids = "";
				Map<String, TeacherRole> maps = this.redisTeacherRoleDao.getByTeacherId(user.getUser_id().toString());
				if (maps != null) {
					for (String roleid : maps.keySet()) {
						Role role = this.redisRoleDao.getByRoleId(roleid);
						if (role != null && role.getOrg_id().intValue()==Integer.parseInt(org_id)) {
							rlids = rlids+role.getRl_id()+",";
						}
					}
				}
				user.setRlids(rlids);
				
				
				user.setTeacher(teacher);
				Map<String,String> rl_ids = new HashMap<String,String>();
				rl_ids.put(EnumRoleLabel.任课教师.getCode().toString(), EnumRoleLabel.任课教师.getCode().toString());
				rl_ids.put(EnumRoleLabel.班主任.getCode().toString(), EnumRoleLabel.班主任.getCode().toString());
				rl_ids.put(EnumRoleLabel.年级组长.getCode().toString(), EnumRoleLabel.年级组长.getCode().toString());
				List<Map<String,Object>> listIdentity = redisTechRangeDao.getIdentityByIds(teacher.getTech_id().toString(), rl_ids, null);
				user.setIdentityDatas(listIdentity);
			}else if(Constant.IDENTITY_PARENT==user.getIdentity()){
				Parent parent = redisParentDao.getParentsByUserId(user.getUser_id().toString());
				if(parent==null){
					throw new EsbException(EnumException.user_name_not_exist);
				}
				user.setParent(parent);
				List<Student> childrens = redisStudentParentDao.getStudsByPId(parent.getParent_id().toString());
				
				StudentToSortComparator mcg = new StudentToSortComparator();  
		        Collections.sort(childrens,mcg);
				
				
				user.setChildrens(childrens);
			}
			
			user.setOpenid(openid);
			this.userService.updateOrgUser(user);
			
			
			Boolean first_login = ua!=null?false:true;
			
			String type = "0";
			String token = this.userService.createToken(user.getUser_id(),user.getUser_salt(),new Integer(version),udid,type);
			data.put("ucuser", ucuser);
			data.put("orguser", user);
			data.put("token", token);
			data.put("udid", udid);
			data.put("first_login", first_login);
			
			Login login = new Login();
			login.setType(type);
			login.setUser_id(user.getUser_id().toString());
			login.setExtratime(0L);
			this.redisLoginDao.add(login);
			
			result.setData(data);
			result.setMessage("登陆成功");
			result.setSuccess(true);
		} catch (EsbException e) {
			// TODO Auto-generated catch block
			result.setMessage(e.getMessage());
			result.setCode(e.getCode());
			result.setSuccess(false);
		}	
		return result;
	}
	
	/***
	 * 微信openid登录
	 * ****/
	@ResponseBody
	@RequestMapping(value = "/wxredirect")
	@EduJsonFilters(value={@EduJsonFilter(mixin=UcUserFilter.class, target=UcUser.class),
			@EduJsonFilter(mixin=OrgUserFilterWX.class, target=OrgUser.class),
			@EduJsonFilter(mixin=RoleFilter.class, target=Role.class),
			@EduJsonFilter(mixin=TeacherFilter.class, target=Teacher.class),
			@EduJsonFilter(mixin=OrganizationShortFilter.class, target=Organization.class),
			@EduJsonFilter(mixin=StudentShortFilter.class, target=Student.class),
			@EduJsonFilter(mixin=ParentFilter.class, target=Parent.class),
			@EduJsonFilter(mixin=ClassesShortFilterBF.class, target=Classes.class),
			@EduJsonFilter(mixin=TeacherForGroupMemberFilter.class, target=Teacher.class)}
	)
	public Result<Map<String, Object>> wxredirect(@RequestParam(defaultValue="0") Integer version,@RequestParam(defaultValue="") String udid,HttpServletRequest request, HttpServletResponse response) throws IOException  {		
		Result<Map<String, Object>> result = new Result<Map<String, Object>>();		
		Map<String, Object> data = new HashMap<String, Object>();
		try {
			
			String identity = request.getParameter("identity");
			String org_id = request.getParameter("org_id");
			String openid = request.getParameter("openid");
			logger.info("----------------------wxredirect---------------------");
			logger.info("[org_id:"+org_id+"][identity:"+identity+"][openid:"+openid+"][version:"+version+"]");
			if(StringUtils.isBlank(identity)||StringUtils.isBlank(org_id)||StringUtils.isBlank(openid)){
				throw new EsbException(EnumException.common_params_error);
			}

			OrgUser user = redisOrgUserDao.getUserByOpenId(openid,new Integer(org_id),new Integer(identity));
			if(user==null){
				throw new EsbException(EnumException.user_password_wrong);
			}		
			if("".equals(udid)){
				UUID uuid = UUID.randomUUID();
				udid = uuid.toString();
			}else{
				if(udid.length()<8){
					throw new EsbException(EnumException.user_invalid_decode_error);
				}
			}
			UcuserOrguser uo = this.redisUcUserOrguserDao.getByUserId(user.getUser_id().toString());
			UcUser ucuser = null;
			if(uo!=null){
				ucuser = this.redisUcUserDao.getUserById(uo.getUc_id().toString());
			}
			
			UserAccount ua = this.userService.getUserAccount(user.getUser_loginname(),version.toString(),"0");
			this.userService.addUserAccount(user.getUser_loginname(),version,udid,user.getUser_id(),user.getOrg_id(),0);

			if(Constant.IDENTITY_TEACHER==user.getIdentity()){
				
				Teacher teacher = this.redisTeacherDao.getByUserId(user.getUser_id().toString());
				if(teacher==null){
					throw new EsbException(EnumException.user_name_not_exist);
				}
				List<Role> roles = new ArrayList<Role>();
				//roles.add(new Role());
				user.setRoles(roles);
				
				String rlids = "";
				Map<String, TeacherRole> maps = this.redisTeacherRoleDao.getByTeacherId(user.getUser_id().toString());
				if (maps != null) {
					for (String roleid : maps.keySet()) {
						Role role = this.redisRoleDao.getByRoleId(roleid);
						if (role != null && role.getOrg_id().intValue()==Integer.parseInt(org_id)) {
							rlids = rlids+role.getRl_id()+",";
						}
					}
				}
				user.setRlids(rlids);
				
				
				user.setTeacher(teacher);
				Map<String,String> rl_ids = new HashMap<String,String>();
				rl_ids.put(EnumRoleLabel.任课教师.getCode().toString(), EnumRoleLabel.任课教师.getCode().toString());
				rl_ids.put(EnumRoleLabel.班主任.getCode().toString(), EnumRoleLabel.班主任.getCode().toString());
				rl_ids.put(EnumRoleLabel.年级组长.getCode().toString(), EnumRoleLabel.年级组长.getCode().toString());
				List<Map<String,Object>> listIdentity = redisTechRangeDao.getIdentityByIds(teacher.getTech_id().toString(), rl_ids, null);
				user.setIdentityDatas(listIdentity);
			}else if(Constant.IDENTITY_PARENT==user.getIdentity()){
				Parent parent = redisParentDao.getParentsByUserId(user.getUser_id().toString());
				if(parent==null){
					throw new EsbException(EnumException.user_name_not_exist);
				}
				user.setParent(parent);
				List<Student> childrens = redisStudentParentDao.getStudsByPId(parent.getParent_id().toString());
				StudentToSortComparator mcg = new StudentToSortComparator();  
		        Collections.sort(childrens,mcg);
				user.setChildrens(childrens);
			}
						 
			Boolean first_login = ua!=null?false:true;
			
			String type = "0";
			String token = this.userService.createToken(user.getUser_id(),user.getUser_salt(),version,udid,type);
			data.put("ucuser", ucuser);
			data.put("orguser", user);
			data.put("token", token);
			data.put("udid", udid);
			data.put("first_login", first_login);
			
			Login login = new Login();
			login.setType(type);
			login.setUser_id(user.getUser_id().toString());
			login.setExtratime(0L);
			this.redisLoginDao.add(login);
			
			result.setData(data);
			result.setMessage("登陆成功");
			result.setSuccess(true);
		} catch (EsbException e) {
			// TODO Auto-generated catch block
			result.setMessage(e.getMessage());
			result.setCode(e.getCode());
			result.setSuccess(false);
		}	
		return result;
	}
	
	/***
	 * 微信验证码 登录
	 * ****/
	@ResponseBody
	@RequestMapping(value = "/wxlogincode")
	@EduJsonFilters(value={@EduJsonFilter(mixin=UcUserFilter.class, target=UcUser.class),
			@EduJsonFilter(mixin=OrgUserFilterWX.class, target=OrgUser.class),
			@EduJsonFilter(mixin=RoleFilter.class, target=Role.class),
			@EduJsonFilter(mixin=TeacherFilter.class, target=Teacher.class),
			@EduJsonFilter(mixin=OrganizationShortFilter.class, target=Organization.class),
			@EduJsonFilter(mixin=StudentShortFilter.class, target=Student.class),
			@EduJsonFilter(mixin=ParentFilter.class, target=Parent.class),
			@EduJsonFilter(mixin=ClassesShortFilterBF.class, target=Classes.class),
			@EduJsonFilter(mixin=TeacherForGroupMemberFilter.class, target=Teacher.class)}
	)
	public Result<Map<String, Object>> wxlogincode(@RequestParam(defaultValue="") String udid,HttpServletRequest request, HttpServletResponse response) throws IOException  {		
		Result<Map<String, Object>> result = new Result<Map<String, Object>>();		
		Map<String, Object> data = new HashMap<String, Object>();
		try {
			
			String identity = request.getParameter("identity");
			String org_id = request.getParameter("org_id");
			String phone = request.getParameter("phone");
			String code = request.getParameter("code");
			String openid = request.getParameter("openid");
			String version = request.getParameter("version");
			
			if(StringUtils.isEmpty(identity)||StringUtils.isEmpty(org_id)||StringUtils.isEmpty(phone)||StringUtils.isEmpty(code)||StringUtils.isEmpty(openid)||StringUtils.isEmpty(version)){
				throw new EsbException(EnumException.common_params_error);
			}
			try {
				this.userService.validCode(phone,code);
			}catch (EsbException e) {
				result.setSuccess(false);
				result.setMessage(e.getMessage());
				result.setCode(e.getCode());
				return result;
			}	
			
			OrgUser user = redisOrgUserDao.getUserByLoginNameWX(phone,new Integer(org_id),identity);
			if(user==null){
				throw new EsbException(EnumException.user_name_not_exist);
			}		
			if("".equals(udid)){
				UUID uuid = UUID.randomUUID();
				udid = uuid.toString();
			}else{
				if(udid.length()<8){
					throw new EsbException(EnumException.user_invalid_decode_error);
				}
			}
			UcuserOrguser uo = this.redisUcUserOrguserDao.getByUserId(user.getUser_id().toString());
			UcUser ucuser = null;
			if(uo!=null){
				ucuser = this.redisUcUserDao.getUserById(uo.getUc_id().toString());
			}
			
			UserAccount ua = this.userService.getUserAccount(user.getUser_loginname(),version,"0");
			this.userService.addUserAccount(user.getUser_loginname(),new Integer(version),udid,user.getUser_id(),user.getOrg_id(),0);

			if(Constant.IDENTITY_TEACHER==user.getIdentity()){
				Teacher teacher = this.redisTeacherDao.getByUserId(user.getUser_id().toString());
				if(teacher==null){
					throw new EsbException(EnumException.user_name_not_exist);
				}
				List<Role> roles = new ArrayList<Role>();
				//roles.add(new Role());
				user.setRoles(roles);
				
				String rlids = "";
				Map<String, TeacherRole> maps = this.redisTeacherRoleDao.getByTeacherId(user.getUser_id().toString());
				if (maps != null) {
					for (String roleid : maps.keySet()) {
						Role role = this.redisRoleDao.getByRoleId(roleid);
						if (role != null && role.getOrg_id().intValue()==Integer.parseInt(org_id)) {
							rlids = rlids+role.getRl_id()+",";
						}
					}
				}
				user.setRlids(rlids);
				
				
				user.setTeacher(teacher);
				Map<String,String> rl_ids = new HashMap<String,String>();
				rl_ids.put(EnumRoleLabel.任课教师.getCode().toString(), EnumRoleLabel.任课教师.getCode().toString());
				rl_ids.put(EnumRoleLabel.班主任.getCode().toString(), EnumRoleLabel.班主任.getCode().toString());
				rl_ids.put(EnumRoleLabel.年级组长.getCode().toString(), EnumRoleLabel.年级组长.getCode().toString());
				List<Map<String,Object>> listIdentity = redisTechRangeDao.getIdentityByIds(teacher.getTech_id().toString(), rl_ids, null);
				user.setIdentityDatas(listIdentity);
			}else if(Constant.IDENTITY_PARENT==user.getIdentity()){
				Parent parent = redisParentDao.getParentsByUserId(user.getUser_id().toString());
				if(parent==null){
					throw new EsbException(EnumException.user_name_not_exist);
				}
				user.setParent(parent);
				List<Student> childrens = redisStudentParentDao.getStudsByPId(parent.getParent_id().toString());
				StudentToSortComparator mcg = new StudentToSortComparator();  
		        Collections.sort(childrens,mcg);
				user.setChildrens(childrens);
			}
			user.setOpenid(openid);
			this.userService.updateOrgUser(user);			 
			Boolean first_login = ua!=null?false:true;
			
			String type = "0";
			String token = this.userService.createToken(user.getUser_id(),user.getUser_salt(),new Integer(version),udid,type);
			data.put("ucuser", ucuser);
			data.put("orguser", user);
			data.put("token", token);
			data.put("udid", udid);
			data.put("first_login", first_login);
			
			Login login = new Login();
			login.setType(type);
			login.setUser_id(user.getUser_id().toString());
			login.setExtratime(0L);
			this.redisLoginDao.add(login);
			
			result.setData(data);
			result.setMessage("登陆成功");
			result.setSuccess(true);
		} catch (EsbException e) {
			// TODO Auto-generated catch block
			result.setMessage(e.getMessage());
			result.setCode(e.getCode());
			result.setSuccess(false);
		}	
		return result;
	}
	
	
	
	
//	@RequestMapping(value = "/uploadimg")
//	public @ResponseBody Result<Object> uploadimg(
//			HttpServletRequest request,HttpServletResponse response) throws Exception {		
//		String user_id =  request.getAttribute("user_id").toString();
//		String type = request.getAttribute("type").toString();
//		String img =  request.getParameter("img");
//		String ext =  request.getParameter("ext");
//		String width =  request.getParameter("width");
//		String height =  request.getParameter("height");
//		String filePathName ="";
//		if(!"".equals(img)){
//			byte[] buf = Base64.decode(img);
//			InputStream sbs = new ByteArrayInputStream(buf); 
//			filePathName = this.resourcesService.savePicture(Integer.parseInt(type),Integer.parseInt(user_id),sbs,buf.length,ext,Integer.parseInt(width),Integer.parseInt(height));
//		}
////		String[] params = logo.split("\\.");
////		Integer id = Integer.parseInt(params[0]);
////		String url = this.getFilePathUrl(id, params[1]);
//		
//		result.setData(data);
//		result.setMessage("登陆成功");
//		result.setSuccess(true);
//		return filePathName;
//	}
	
}
