package cn.edugate.esb.web.api;

import java.io.IOException;
import java.io.InputStream;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.MediaType;
import org.springframework.integration.MessageChannel;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import cn.edugate.esb.Result;
import cn.edugate.esb.comparator.TechToSortComparator;
import cn.edugate.esb.eduEnum.EnumMessage;
import cn.edugate.esb.eduEnum.EnumRoleLabel;
import cn.edugate.esb.entity.Course;
import cn.edugate.esb.entity.Department;
import cn.edugate.esb.entity.ExcelRes;
import cn.edugate.esb.entity.Group;
import cn.edugate.esb.entity.OrgUser;
import cn.edugate.esb.entity.Role;
import cn.edugate.esb.entity.Teacher;
import cn.edugate.esb.entity.TeacherRole;
import cn.edugate.esb.entity.TechRange;
import cn.edugate.esb.exception.EnumException;
import cn.edugate.esb.exception.EsbException;
import cn.edugate.esb.params.EduJsonFilter;
import cn.edugate.esb.params.EduJsonFilters;
import cn.edugate.esb.params.filter.RoleFilter;
import cn.edugate.esb.params.filter.TeacherFilter;
import cn.edugate.esb.params.filter.TeacherFilterWithOrgName;
import cn.edugate.esb.params.filter.TeacherShortFilter;
import cn.edugate.esb.params.filter.TeacherShortFilter_sk;
import cn.edugate.esb.params.filter.TechRangeFilter;
import cn.edugate.esb.pojo.TeacherList;
import cn.edugate.esb.redis.dao.RedisCourseDao;
import cn.edugate.esb.redis.dao.RedisDepartmentDao;
import cn.edugate.esb.redis.dao.RedisGroupDao;
import cn.edugate.esb.redis.dao.RedisOrgUserDao;
import cn.edugate.esb.redis.dao.RedisOrganizationDao;
import cn.edugate.esb.redis.dao.RedisRoleDao;
import cn.edugate.esb.redis.dao.RedisTeacherDao;
import cn.edugate.esb.redis.dao.RedisTeacherRoleDao;
import cn.edugate.esb.redis.dao.RedisTechRangeDao;
import cn.edugate.esb.service.GroupMemberService;
import cn.edugate.esb.service.ResourcesService;
import cn.edugate.esb.service.TeacherRoleService;
import cn.edugate.esb.service.TeacherService;
import cn.edugate.esb.service.TechRangeService;
import cn.edugate.esb.service.UserService;
import cn.edugate.esb.util.Constant;
import cn.edugate.esb.util.DateUtil;
import cn.edugate.esb.util.LSHelper;
import cn.edugate.esb.util.Paging;
import cn.edugate.esb.util.PinyinUtil;
import cn.edugate.esb.util.Util;
import cn.edugate.esb.util.Utils;

@Controller
@RequestMapping("/api/tech")
public class TeacherController {
	
	@Autowired
	private UserService userService;

	@Autowired
	private TechRangeService techRangeService;

	private RedisTeacherRoleDao redisTeacherRoleDao;

	private RedisRoleDao redisRoleDao;

	@Autowired
	public void setRedisRoleDao(RedisRoleDao redisRoleDao) {
		this.redisRoleDao = redisRoleDao;
	}

	@Autowired
	public void setRedisTeacherRoleDao(RedisTeacherRoleDao redisTeacherRoleDao) {
		this.redisTeacherRoleDao = redisTeacherRoleDao;
	}

	@Autowired
	private RedisOrgUserDao redisOrgUserDao;

	@Autowired
	private RedisTeacherDao redisTeacherDao;

	@Autowired
	private TeacherService teacherService;

	@Autowired
	private RedisTechRangeDao redisTechRangeDao;

	@Autowired
	private GroupMemberService groupMemberService;

	@Autowired
	private TeacherRoleService teacherRoleService;

	@Autowired
	private RedisDepartmentDao redisDepartmentDao;

	@Autowired
	private RedisCourseDao redisCourseDao;

	@Autowired
	private RedisGroupDao redisGroupDao;

	@Autowired
	private RedisOrganizationDao redisOrganizationDao;
	
	@Autowired
	private RedisCourseDao courseDao;
	
	@Autowired
	@Qualifier("batchAddTeacherSendChannel")
	MessageChannel messageChannel;
	
	@Autowired
	private ResourcesService resourcesService;
	
	@Autowired
	private Util util;

	// 教师id 获取教师详细
	@ResponseBody
	@RequestMapping(value = "/getTeacherInfo")
	@EduJsonFilters(value = { @EduJsonFilter(mixin = TeacherFilter.class, target = Teacher.class) })
	public Result<Teacher> getTeacherInfo(@RequestParam String tech_id, HttpServletRequest request, HttpServletResponse response)
			throws IOException {
		Result<Teacher> result = new Result<Teacher>();
		try {
			Teacher teacher = teacherService.getTechAndUserId(Integer.parseInt(tech_id));
			// Teacher Teacher = redisTeacherDao.getByTechId(tech_id,null);
			LSHelper.processInjection(teacher);
			result.setData(teacher);
			result.setMessage("获取成功");
			result.setSuccess(true);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			result.setMessage(e.getMessage());
			result.setSuccess(false);
		}
		return result;
	}

	// 根据部门id 获取教师集合
	@ResponseBody
	@RequestMapping(value = "/getTechsOfDepId")
	@EduJsonFilters(value = { @EduJsonFilter(mixin = TeacherShortFilter.class, target = Teacher.class) })
	public Result<Map<String, Object>> getTechsOfDepId(@RequestParam String dep_id, HttpServletRequest request,
			HttpServletResponse response) {
		Result<Map<String, Object>> result = new Result<Map<String, Object>>();
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			List<Teacher> list = redisTeacherDao.getTechsByDepId(dep_id, null);
			map.put("techs", list);
			result.setData(map);
			result.setMessage("获取成功");
			result.setSuccess(true);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			result.setMessage(e.getMessage());
			result.setSuccess(false);
		}
		return result;
	}

	// 身份集合
	@ResponseBody
	@RequestMapping(value = "/getIdentityById")
	@EduJsonFilters(value = { @EduJsonFilter(mixin = TeacherFilter.class, target = Teacher.class) })
	public Result<Object> getIdentityById(@RequestParam String tech_id,
			@RequestParam(value = "rl_id", defaultValue = "0") Integer rl_id,
			@RequestParam(value = "group_type", defaultValue = "0") Integer group_type,
			HttpServletRequest request,
			HttpServletResponse response) {
		Result<Object> result = new Result<Object>();
		try {
			List<Map<String, Object>> mapList = redisTechRangeDao.getIdentityById(tech_id, rl_id, null);
			
			
			result.setData(mapList);
			result.setMessage("获取成功");
			result.setSuccess(true);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			result.setMessage(e.getMessage());
			result.setSuccess(false);
		}
		return result;
	}
	
	// 外联组
	@ResponseBody
	@RequestMapping(value = "/getOutGroupById")
	@EduJsonFilters(value = { @EduJsonFilter(mixin = TeacherFilterWithOrgName.class, target = Teacher.class) })
	public Result<List<Map<String, Object>>> getOutGroupById(@RequestParam String tech_id, HttpServletRequest request,
			HttpServletResponse response) {
		Result<List<Map<String, Object>>> result = new Result<List<Map<String, Object>>>();
		try {
			List<Map<String, Object>> mapList = redisTechRangeDao.getManagerGroupById(tech_id);
			
//			MapToSortGroupComparator mc = new MapToSortGroupComparator();  
//	        Collections.sort(mapList,mc);
			
            if(mapList!=null && mapList.size()>0){
            	result.setData(mapList);
				result.setMessage("获取成功");
				result.setSuccess(true);
            	
            }else{
            	result.setSuccess(false);
    			result.setMessage(EnumMessage.group_not_found.getMessage());
    			result.setCode(EnumMessage.group_not_found.getCode());
    			return result;
            }
		} catch (Exception e) {
			// TODO Auto-generated catch block
			result.setMessage(e.getMessage());
			result.setSuccess(false);
		}
		return result;
	}
	
	

	// 根据机构id 和 名称 模糊查询
	@ResponseBody
	@RequestMapping(value = "/getTechSearch")
	@EduJsonFilters(value = { @EduJsonFilter(mixin = TeacherFilter.class, target = Teacher.class) })
	public Result<Map<String, Object>> getTechSearch(HttpServletRequest request, HttpServletResponse response) throws IOException {
		Result<Map<String, Object>> result = new Result<Map<String, Object>>();
		String org_id = request.getParameter("org_id");
		if (org.apache.commons.lang.StringUtils.isEmpty(org_id))
			org_id = "0";
		String sname = request.getParameter("sname");
		if (org.apache.commons.lang.StringUtils.isEmpty(sname))
			sname = "";
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			List<Teacher> list = teacherService.getTeacherSearch(Integer.parseInt(org_id), sname);
			map.put("techs", list);
			result.setData(map);
			result.setMessage("获取成功");
			result.setSuccess(true);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			result.setMessage(e.getMessage());
			result.setSuccess(false);
		}
		return result;
	}

	// 班级下的 授课教师
	@ResponseBody
	@RequestMapping(value = "/getSkClasTech")
	@EduJsonFilters(value = { @EduJsonFilter(mixin = TeacherFilter.class, target = Teacher.class) })
	public Result<Map<String, Object>> getSkClasTech(HttpServletRequest request, HttpServletResponse response) throws IOException {
		Result<Map<String, Object>> result = new Result<Map<String, Object>>();
		String clas_id = request.getParameter("clas_id");

		Map<String, Object> map = new HashMap<String, Object>();
		try {
			List<Map<String, Object>> list = redisTechRangeDao.getSkClasTech(clas_id, null);
			map.put("techs", list);
			result.setData(map);
			result.setMessage("获取成功");
			result.setSuccess(true);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			result.setMessage(e.getMessage());
			result.setSuccess(false);
		}
		return result;
	}

	// 年级下课程
	@ResponseBody
	@RequestMapping(value = "/getSkGradeCourse")
	@EduJsonFilters(value = { @EduJsonFilter(mixin = TeacherFilter.class, target = Teacher.class) })
	public Result<Object> getSkGradeCourse(HttpServletRequest request, HttpServletResponse response) throws IOException {
		Result<Object> result = new Result<Object>();
		String grade_id = request.getParameter("grade_id");

		try {
			Map<String, Object> map = redisTechRangeDao.getSkGradeCourse(grade_id);
			result.setData(map.values());
			result.setMessage("获取成功");
			result.setSuccess(true);
		} catch (Exception e) {
			result.setMessage(e.getMessage());
			result.setSuccess(false);
		}
		return result;
	}

	// 获取教师的角色
	@ResponseBody
	@RequestMapping(value = "/getRoles")
	@EduJsonFilters(value = { @EduJsonFilter(mixin = RoleFilter.class, target = Role.class) })
	public Result<List<Role>> getRoles(@RequestParam Integer tech_id, HttpServletRequest request, HttpServletResponse response)
			throws IOException {
		Result<List<Role>> result = new Result<List<Role>>();
		List<Role> data = new ArrayList<Role>();
		try {
			Teacher teacher = this.redisTeacherDao.getByTechId(tech_id.toString(), null);

			if (teacher != null) {
				Map<String, TeacherRole> maps = this.redisTeacherRoleDao.getByTeacherId(teacher.getUser_id().toString());
				if (maps != null) {
					for (String roleid : maps.keySet()) {
						Role role = this.redisRoleDao.getByRoleId(roleid);
						if (role.getOrg_id().intValue() == teacher.getOrg_id().intValue()) {
							data.add(role);
						}
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			data = null;
		}
		result.setData(data);
		result.setSuccess(true);
		return result;
	}

	// 年级下教师
	@ResponseBody
	@RequestMapping(value = "/getSkGradeTech")
	@EduJsonFilters(value = { @EduJsonFilter(mixin = TeacherFilter.class, target = Teacher.class) })
	public Result<Object> getSkGradeTech(HttpServletRequest request, HttpServletResponse response) throws IOException {
		Result<Object> result = new Result<Object>();
		String grade_id = request.getParameter("grade_id");

		try {
			Map<String, Object> map = redisTechRangeDao.getSkGradeTech(grade_id, null);
			result.setData(map.values());
			result.setMessage("获取成功");
			result.setSuccess(true);
		} catch (Exception e) {
			result.setMessage(e.getMessage());
			result.setSuccess(false);
		}
		return result;
	}

	// 部门下 管理员
	@ResponseBody
	@RequestMapping(value = "/getDepinfoManager")
	@EduJsonFilters(value = { @EduJsonFilter(mixin = TeacherFilter.class, target = Teacher.class) })
	public Result<Object> getDepinfoManager(HttpServletRequest request, HttpServletResponse response) throws IOException {
		Result<Object> result = new Result<Object>();
		String dep_id = request.getParameter("dep_id");

		try {
			Map<String, Object> map = redisTechRangeDao.getDepinfoManager(dep_id, null);
			result.setData(map.values());
			result.setMessage("获取成功");
			result.setSuccess(true);
		} catch (Exception e) {
			result.setMessage(e.getMessage());
			result.setSuccess(false);
		}
		return result;
	}

	/**
	 * 根据角色标签添加范围
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 */
	@ResponseBody
	@RequestMapping(value = "/addRange")
	public Result<Object> addRange(HttpServletRequest request, HttpServletResponse response) throws IOException {
		Result<Object> result = new Result<Object>();
		String org_id = request.getParameter("org_id");
		String tech_id = request.getParameter("tech_id");
		String rl_id = request.getParameter("rl_id");
		String clas_id = request.getParameter("clas_id");
		String grade_id = request.getParameter("grade_id");
		String group_id = request.getParameter("group_id");
		String dep_id = request.getParameter("dep_id");
		String cour_id = request.getParameter("cour_id");
		try {
			List<TechRange> list = techRangeService.createRange(org_id, tech_id, rl_id, clas_id, grade_id, group_id, dep_id,
					cour_id);
			result.setData(list);
			result.setCode(EnumMessage.success.getCode());
			result.setMessage("获取成功");
			result.setSuccess(true);
		} catch (Exception e) {
			result.setMessage(e.getMessage());
			result.setSuccess(false);
		}
		return result;
	}

	// 学生组下 管理员
	@ResponseBody
	@RequestMapping(value = "/getGroupStudManager")
	@EduJsonFilters(value = { @EduJsonFilter(mixin = TeacherFilter.class, target = Teacher.class) })
	public Result<Object> getGroupStudManager(HttpServletRequest request, HttpServletResponse response) throws IOException {
		Result<Object> result = new Result<Object>();
		String group_id = request.getParameter("group_id");

		try {
			Map<String, Object> map = redisTechRangeDao.getGroupStudManager(group_id);
			result.setData(map.values());
			result.setMessage("获取成功");
			result.setSuccess(true);
		} catch (Exception e) {
			result.setMessage(e.getMessage());
			result.setSuccess(false);
		}
		return result;
	}

	// 教师组下 管理员
	@ResponseBody
	@RequestMapping(value = "/getGroupTechManager")
	@EduJsonFilters(value = { @EduJsonFilter(mixin = TeacherFilter.class, target = Teacher.class) })
	public Result<Object> getGroupTechManager(HttpServletRequest request, HttpServletResponse response) throws IOException {
		Result<Object> result = new Result<Object>();
		String group_id = request.getParameter("group_id");

		try {
			Map<String, Object> map = redisTechRangeDao.getGroupTechManager(group_id);
			result.setData(map.values());
			result.setMessage("获取成功");
			result.setSuccess(true);
		} catch (Exception e) {
			result.setMessage(e.getMessage());
			result.setSuccess(false);
		}
		return result;
	}

	// 班级下 班主任
	@ResponseBody
	@RequestMapping(value = "/getSkClasBZR")
	@EduJsonFilters(value = { @EduJsonFilter(mixin = TeacherShortFilter_sk.class, target = Teacher.class) })
	public Result<Object> getSkClasBZR(@RequestParam String clas_id, HttpServletRequest request, HttpServletResponse response)
			throws IOException {
		Result<Object> result = new Result<Object>();
		try {
			Map<String, Object> map = redisTechRangeDao.getSkClasBZR(clas_id, null);
			result.setData(map.values());
			result.setMessage("获取成功");
			result.setSuccess(true);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			result.setMessage(e.getMessage());
			result.setSuccess(false);
		}
		return result;
	}

	// 年级课程下 班级
	@ResponseBody
	@RequestMapping(value = "/getSkGradeCourClas")
	@EduJsonFilters(value = { @EduJsonFilter(mixin = TeacherFilter.class, target = Teacher.class) })
	public Result<Object> getSkGradeCourClas(@RequestParam String grade_id, @RequestParam String cour_id,
			HttpServletRequest request, HttpServletResponse response) throws IOException {
		Result<Object> result = new Result<Object>();
		try {
			Map<String, Object> map = redisTechRangeDao.getSkGradeCourClas(grade_id, cour_id);
			result.setData(map.values());
			result.setMessage("获取成功");
			result.setSuccess(true);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			result.setMessage(e.getMessage());
			result.setSuccess(false);
		}
		return result;
	}

	// 教师授课 班级
	@ResponseBody
	@RequestMapping(value = "/getSkTechClas")
	@EduJsonFilters(value = { @EduJsonFilter(mixin = TeacherFilter.class, target = Teacher.class) })
	public Result<Map<String, Object>> getSkTechClas(HttpServletRequest request, HttpServletResponse response) throws IOException {
		Result<Map<String, Object>> result = new Result<Map<String, Object>>();
		String tech_id = request.getParameter("tech_id");

		Map<String, Object> map = new HashMap<String, Object>();
		try {
			List<Map<String, Object>> list = redisTechRangeDao.getSkTechClas(tech_id);
			map.put("class", list);
			result.setData(map);
			result.setMessage("获取成功");
			result.setSuccess(true);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			result.setMessage(e.getMessage());
			result.setSuccess(false);
		}
		return result;
	}

	/**
	 * 添加教师
	 * 
	 * @param groupName 【必填】组名称
	 * @param orgId 【必填】机构主键
	 * @param groupType 【必填】组类型
	 * @param teacherId 【必填】创建教师主键
	 * @param note [选填] 备注说明
	 * @param request
	 * @return 创建完成的组
	 */
	@RequestMapping(value = "/addTeacher", method = RequestMethod.POST)
	public @ResponseBody
	Result<Map<String, Object>> addTeacher(@RequestParam(value = "tech_name") String tech_name,
			@RequestParam(value = "org_id") Integer org_id,
			@RequestParam(value = "tech_nick", defaultValue = "") String tech_nick,
			@RequestParam(value = "tech_head", defaultValue = "") String tech_head,
			@RequestParam(value = "user_mobile", defaultValue = "") String user_mobile,
			@RequestParam(value = "user_mobile_type", defaultValue = "") String user_mobile_type,
			@RequestParam(value = "dep_id") Integer dep_id,
			@RequestParam(value = "tech_note", defaultValue = "") String tech_note,
			@RequestParam(value = "json", defaultValue = "") String json,
			@RequestParam(value = "user_mail", defaultValue = "") String user_mail,
			@RequestParam(value = "tech_card", defaultValue = "") String tech_card,
			@RequestParam(value = "birthday", defaultValue = "") String birthday,
			@RequestParam(value = "user_type", defaultValue = "") String user_type,
			@RequestParam(value = "sex", defaultValue = "") String sex,
			@RequestParam(value = "user_idnumber", defaultValue = "") String user_idnumber, HttpServletRequest request) {
		Result<Map<String, Object>> result = new Result<Map<String, Object>>();
		Map<String, Object> data = new HashMap<String, Object>();
		Teacher teacher = new Teacher();
		try {
			OrgUser user = new OrgUser();
			user.setIdentity(Constant.IDENTITY_TEACHER);
			user.setIs_current(Constant.CURRENTYES);
			// user.setLoginnum(loginnum);
			user.setOrg_id(org_id);
			// user.setStatus(status);
			user.setUser_idnumber(user_idnumber);
			user.setUser_loginname(user_mobile);
			String user_salt = Utils.makecode();// ((Math.random()*(9999-1000+1))+1000)+"";
			user.setUser_mail(user_mail);
			user.setUser_loginpass(Utils.MD5(user_salt + ":" + "111111"));
			// user.setUser_mail(user_mail);
			user.setUser_mobile(user_mobile);
			user.setIs_del(false);
			if (StringUtils.isNotEmpty(user_mobile_type))
				user.setUser_mobile_type(Byte.parseByte(user_mobile_type));
			// user.setUser_number(user_number);

			user.setUser_salt(user_salt);
			if (StringUtils.isNotEmpty(user_type))
				user.setUser_type(Integer.parseInt(user_type));

			teacher.setDep_id(dep_id);
			teacher.setJson(json);
			teacher.setOrg_id(org_id);
			teacher.setTech_head(tech_head);
			teacher.setTech_name(tech_name);
			teacher.setTech_nick(tech_nick);
			teacher.setTech_note(tech_note);
			teacher.setUser_mobile(user_mobile);
			if(StringUtils.isNotEmpty(sex))
				teacher.setSex(Byte.parseByte(sex));
			teacher.setIs_del(0);
			if (StringUtils.isNotEmpty(user_mobile_type))
				teacher.setUser_mobile_type(Byte.parseByte(user_mobile_type));
			teacher.setTech_card(tech_card);
			if (StringUtils.isNotEmpty(birthday))
				teacher.setBirthday(DateUtil.toDate(birthday));

			teacher.setUser(user);

			int res = teacherService.add(teacher);
			if(res==-1){
				result.setCode(EnumMessage.fail.getCode());
				result.setMessage("手机号或登录名已存在！");
				result.setSuccess(false);
				return result;
			}
		} catch (Exception e) {
			e.printStackTrace();
			result.setCode(EnumMessage.fail.getCode());
			result.setMessage(EnumMessage.fail.getMessage());
			result.setSuccess(false);
		}
		// 返回结果
		data.put("teacher", teacher);
		result.setData(data);
		result.setCode(EnumMessage.success.getCode());
		result.setMessage(EnumMessage.success.getMessage());
		result.setSuccess(true);
		return result;
	}

	@RequestMapping(value = "/addTeachers")
	@ResponseBody
	public Result<String> addTeachers(@RequestBody TeacherList teacherListEntity) {

		Result<String> result = new Result<String>();

		if (teacherListEntity != null && teacherListEntity.getTeacherList() != null
				&& teacherListEntity.getTeacherList().size() > 0) {
			List<cn.edugate.esb.pojo.Teacher> teachers = teacherListEntity.getTeacherList();
			List<Teacher> teacherList = new ArrayList<Teacher>(teachers.size());
			for (cn.edugate.esb.pojo.Teacher t : teachers) {
				Teacher teacher = new Teacher();
				// 将pojo的Teacher实体中的字段值复制到po类型的Teacher中，以便使用hibernate保存
				BeanUtils.copyProperties(t, teacher);

				OrgUser user = new OrgUser();
				user.setIdentity(Constant.IDENTITY_TEACHER);
				user.setIs_current(Constant.CURRENTYES);
				// user.setLoginnum(loginnum);
				user.setOrg_id(teacher.getOrg_id());
				// user.setStatus(status);
				user.setUser_idnumber(teacher.getUser_idnumber());
				user.setUser_loginname(teacher.getUser_mobile());
				String user_salt = Utils.makecode();// ((Math.random()*(9999-1000+1))+1000)+"";
				user.setUser_salt(user_salt);
				user.setUser_loginpass(Utils.MD5(user_salt + ":" + "111111"));
				user.setUser_mail(teacher.getUser_mail());
				user.setUser_mobile(teacher.getUser_mobile());
				user.setUser_mobile_type(teacher.getUser_mobile_type());
				// user.setUser_number(user_number);
				user.setUser_type(1);

				teacher.setUser(user);

				teacherList.add(teacher);
			}

			try {
				teacherService.batchAdd(teacherList);

				// result.setData(campus);
				result.setSuccess(true);
				result.setMessage(EnumMessage.success.getMessage());
				result.setCode(EnumMessage.success.getCode());
			} catch (Exception ex) {
				result.setSuccess(false);
				result.setMessage(EnumMessage.fail.getMessage());
				result.setCode(EnumMessage.fail.getCode());
			}
		} else {
			result.setSuccess(false);
			result.setMessage(EnumMessage.fail.getMessage());
			result.setCode(EnumMessage.fail.getCode());
		}

		return result;
	}

	/**
	 * 更新教师
	 * 
	 * @param groupName 【必填】组名称
	 * @param orgId 【必填】机构主键
	 * @param groupType 【必填】组类型
	 * @param teacherId 【必填】创建教师主键
	 * @param note [选填] 备注说明
	 * @param request
	 * @return 创建完成的组
	 */
	
	@RequestMapping(value = "/updateTeacher", method = RequestMethod.POST)
	public @ResponseBody
	Result<Teacher> updateTeacher(@RequestParam(value = "tech_id") String tech_id,
			@RequestParam(value = "tech_name") String tech_name, @RequestParam(value = "org_id") Integer org_id,
			@RequestParam(value = "tech_nick", defaultValue = "") String tech_nick,
			@RequestParam(value = "tech_head", defaultValue = "") String tech_head,
			@RequestParam(value = "user_mobile", defaultValue = "") String user_mobile,
			@RequestParam(value = "user_mobile_type", defaultValue = "") String user_mobile_type,
			@RequestParam(value = "dep_id") Integer dep_id,
			@RequestParam(value = "tech_note", defaultValue = "") String tech_note,
			@RequestParam(value = "json", defaultValue = "") String json,
			@RequestParam(value = "tech_card", defaultValue = "") String tech_card,
			@RequestParam(value = "user_mail", defaultValue = "") String user_mail,
			@RequestParam(value = "birthday", defaultValue = "") String birthday,
			@RequestParam(value = "user_type", defaultValue = "") String user_type,
			@RequestParam(value = "sex", defaultValue = "") String sex,
			@RequestParam(value = "user_idnumber", defaultValue = "") String user_idnumber,
			@RequestParam(value = "user_id") Integer user_id, HttpServletRequest request) {

		Result<Teacher> result = new Result<Teacher>();
		Map<String, Object> data = new HashMap<String, Object>();
		Teacher teacher = null;
		try {
			OrgUser user = userService.getOrgUserById(user_id);
			// OrgUser user = new OrgUser();
			user.setIdentity(1);
			user.setIs_current(0);
			// user.setLoginnum(loginnum);
			user.setOrg_id(org_id);
			// user.setStatus(status);
			user.setUser_idnumber(user_idnumber);
			user.setUser_mail(user_mail);
			// user.setUser_loginname(user_loginname);
			// user.setUser_loginpass(user_loginpass);
			// user.setUser_mail(user_mail);
			user.setUser_mobile(user_mobile);
			if (StringUtils.isNotEmpty(user_mobile_type))
				user.setUser_mobile_type(Byte.parseByte(user_mobile_type));
			// user.setUser_number(user_number);
			// user.setUser_salt(user_salt);
			if (StringUtils.isNotEmpty(user_type))
				user.setUser_type(Integer.parseInt(user_type));
			teacher = redisTeacherDao.getByTechId(tech_id, null);
			user.setUser_id(teacher.getUser_id());
			user.setUpdate_time(new Date());
			this.userService.updateOrgUser(user);	

			teacher.setDep_id(dep_id);
			teacher.setJson(json);
			teacher.setOrg_id(org_id);
			if(StringUtils.isNotEmpty(tech_head))
				teacher.setTech_head(tech_head);
			teacher.setTech_name(tech_name);
			teacher.setTech_nick(tech_nick);
			teacher.setTech_note(tech_note);
			teacher.setUser_mobile(user_mobile);
			teacher.setUpdate_time(new Date());
			if(StringUtils.isNotEmpty(sex))
				teacher.setSex(Byte.parseByte(sex));
			if (StringUtils.isNotEmpty(user_mobile_type))
				teacher.setUser_mobile_type(Byte.parseByte(user_mobile_type));
			teacher.setTech_card(tech_card);
			if (StringUtils.isNotEmpty(birthday))
				teacher.setBirthday(DateUtil.toDate(birthday));
			teacher.setUser(user);
			teacherService.update(teacher);
			data.put("teacher", teacher);
		} catch (Exception e) {
			e.printStackTrace();
			result.setCode(EnumMessage.fail.getCode());
			result.setMessage(EnumMessage.fail.getMessage());
			result.setSuccess(false);
		}
		// 返回结果
		result.setData(teacher);
		result.setCode(EnumMessage.success.getCode());
		result.setMessage(EnumMessage.success.getMessage());
		result.setSuccess(true);
		return result;
	}

	/**
	 * 删除教师
	 * 
	 * @param tech_id
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/deleteTeacher", method = RequestMethod.POST)
	public @ResponseBody
	Result<Map<String, Object>> deleteTeacher(@RequestParam(value = "tech_id") String tech_id, HttpServletRequest request) {
		Result<Map<String, Object>> result = new Result<Map<String, Object>>();
		try {
			// Teacher teacher = redisTeacherDao.getByTechId(tech_id, null);
			// //删除教师数据范围
			// List<TechRange> trList = redisTechRangeDao.getAllOfTechId(Integer.parseInt(tech_id));
			// for (TechRange techRange : trList) {
			// techRange.setIs_del(Constant.IS_DEL_YES);
			// techRange.setDel_time(new Date());
			// techRangeService.deleteRange(techRange);
			// }
			// //删除组成员
			// groupMemberService.deleteByMemberId(tech_id,Constant.GROUPMEMBER_TYPE_TEACHER);
			// //删除教师角色
			// teacherRoleService.deleteByUserId(teacher.getUser_id());
			// 删除教师
			teacherService.delete(Integer.parseInt(tech_id));
		} catch (Exception e) {
			e.printStackTrace();
			result.setCode(EnumMessage.fail.getCode());
			result.setMessage(EnumMessage.fail.getMessage());
			result.setSuccess(false);
		}
		// 返回结果
		result.setCode(EnumMessage.success.getCode());
		result.setMessage(EnumMessage.success.getMessage());
		result.setSuccess(true);
		return result;
	}

	/**
	 * 根据机构主键查询教师列表
	 * 
	 * @param tech_id
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/getTeacherList")
	public Result<Map<String, Object>> getTeacherList(@RequestParam(value = "org_id") String org_id, HttpServletRequest request) {
		Result<Map<String, Object>> result = new Result<Map<String, Object>>();
		Map<String, Object> data = new HashMap<String, Object>();
		try {
			List<Teacher> list = redisTeacherDao.getTeacherListByOrgId(org_id, null);
			// 返回结果
			data.put("teacher", list);
		} catch (Exception e) {

		}
		result.setData(data);
		result.setCode(EnumMessage.success.getCode());
		result.setMessage(EnumMessage.success.getMessage());
		result.setSuccess(true);
		return result;
	}

	@RequestMapping(value = "/getTeacherOfOrgByPage", method = RequestMethod.POST)
	public @ResponseBody
	Result<Paging<Teacher>> getTeachersByOrgId(@RequestParam String org_id, @RequestParam String role_id,
			@RequestParam String tech_name, @RequestParam String user_mobile,
			@RequestParam(value = "start", defaultValue = "0") Integer start,
			@RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum,
			@RequestParam(value = "numPerPage", defaultValue = "20") Integer numPerPage, HttpServletRequest request) {
		Result<Paging<Teacher>> result = new Result<Paging<Teacher>>();
		Paging<Teacher> pages = new Paging<Teacher>();
		if (pageNum == null) {
			pageNum = 1;
		}
		pages.setPage(pageNum);
		pages.setStart(start);
		pages.setLimit(numPerPage);

		this.teacherService.getPaging(org_id, role_id, tech_name, user_mobile, pages);

		result.setData(pages);
		result.setSuccess(true);
		result.setMessage(EnumMessage.success.getMessage());
		result.setCode(EnumMessage.success.getCode());
		return result;
	}

//	@RequestMapping(value = "/techImportExcel", method = RequestMethod.POST)
//	public @ResponseBody
//	ResultDwz techImportExcel(@RequestParam Integer org_id, HttpServletRequest request, HttpServletResponse response)
//			throws Exception {
//		ResultDwz result = new ResultDwz();
//		MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
//		MultipartFile file = multipartRequest.getFile("file_stu");
//		if (file == null || file.isEmpty()) {
//			result.setMessage("导入失败");
//			return result;
//		}
//
//		InputStream fin = file.getInputStream();
//		LinkedHashMap<String, String> map = new LinkedHashMap<String, String>();
//		map.put("教师名称", "tech_name");
//		map.put("用户邮箱", "user_mail");
//		map.put("用户手机", "user_mobile");
//		map.put("部门id", "dep_id");
//		List<OrgUser> ls = ExcelUtil.excelToList(fin, "机构用户", OrgUser.class, map);
//		List<OrgUser> errors = new ArrayList<OrgUser>();
//		for (OrgUser orgUser : ls) {
//			OrgUser user = this.userService.getOrgUserByLoginName(orgUser.getUser_loginname(), org_id);
//			if (user != null) {
//				errors.add(user);
//			} else {
//				orgUser.setInsert_time(new Date());
//				orgUser.setIs_del(false);
//				orgUser.setOrg_id(org_id);
//				orgUser.setStatus((byte) 0);
//				String salt = Utils.makecode();
//				orgUser.setUser_salt(salt);
//				orgUser.setUser_loginpass(Utils.MD5(salt + ":" + "111111"));
//				this.userService.SaveOrgUser(orgUser);
//			}
//		}
//		result.setStatusCode("200");
//		result.setMessage("导入成功，有" + errors.size() + "条失败");
//		result.setNavTabId("user");
//		result.setCallbackType("closeCurrent");
//		return result;
//	}

	// 教师授课 班级
	@ResponseBody
	@RequestMapping(value = "/getTeaching")
	@EduJsonFilters(value = { @EduJsonFilter(mixin = TeacherFilter.class, target = Teacher.class) })
	public Result<Map<String, Object>> getTeaching(HttpServletRequest request, HttpServletResponse response) throws IOException {
		Result<Map<String, Object>> result = new Result<Map<String, Object>>();
		String tech_id = request.getParameter("tech_id");
		String org_id = request.getParameter("org_id");
		String grade_id = request.getParameter("grade_id");
		String cour_id = request.getParameter("cour_id");
		String clas_id = request.getParameter("clas_id");

		Map<String, Object> map = new HashMap<String, Object>();
		try {
			List<Map<String, Object>> list = techRangeService.getTeaching(org_id, tech_id, grade_id, cour_id, clas_id);
			map.put("ranges", list);
			result.setData(map);
			result.setMessage("获取成功");
			result.setSuccess(true);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			result.setMessage(e.getMessage());
			result.setSuccess(false);
		}
		return result;
	}

	/**
	 * 通用方法：根据机构主键获取分部门的全部员工 根据参数为特定的教师打标记，标记is_select为1(默认为0)
	 * 
	 * @param org_id 【必填】机构主键，所有返回结果均在该机构下,无其他参数时返回的所有教师is_select值为0
	 * @param dep_id [选填]部门主键，该部门管理员的is_select值为1
	 * @param grade_id [选填]年级主键，该年级年级组长的is_select值为1
	 * @param clas_id [选填]班级主键，该班班主任的is_select值为1
	 * @param cour_id [选填,与clas_id联合使用]课程主键，该科目任课教师的is_select值为1
	 * @param group_id [选填]组主键，该组管理员的is_select值为1
	 * @return 分部门的教师列表，特定教师的属性is_select值为1
	 */
	@ResponseBody
	@RequestMapping(value = "/getTechofDepsWithStatus")
	@EduJsonFilters(value = { @EduJsonFilter(mixin = TeacherShortFilter.class, target = Teacher.class) })
	public Result<Map<String, Object>> getTechofDepsWithStatus(@RequestParam String org_id,
			@RequestParam(value = "dep_id", defaultValue = "") String dep_id,
			@RequestParam(value = "grade_id", defaultValue = "") String grade_id,
			@RequestParam(value = "clas_id", defaultValue = "") String clas_id,
			@RequestParam(value = "cour_id", defaultValue = "") String cour_id,
			@RequestParam(value = "group_id", defaultValue = "") String group_id, HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		Result<Map<String, Object>> result = new Result<Map<String, Object>>();
		List<Department> list = redisDepartmentDao.getDepsByOrgId(org_id);
		List<Object> deps = new ArrayList<Object>();
		Map<String, Object> resultMap = new HashMap<String, Object>();
		List<Map<String, Object>> resultList = new ArrayList<Map<String, Object>>();
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			if (StringUtils.isNotEmpty(dep_id)) {
				resultMap = redisTechRangeDao.getDepinfoManager(dep_id, null);
			} else if (StringUtils.isNotEmpty(grade_id) && StringUtils.isNotEmpty(clas_id) && StringUtils.isNotEmpty(cour_id)) {
				resultList = techRangeService.getTeaching(org_id, "", "", cour_id, clas_id);
			} else if (StringUtils.isNotEmpty(grade_id)) {
				resultMap = redisTechRangeDao.getGradeTechManager(grade_id, null);
			} else if (StringUtils.isNotEmpty(clas_id)) {
				resultMap = redisTechRangeDao.getSkClasBZR(clas_id, null);
			} else if (StringUtils.isNotEmpty(group_id)) {
				Group group = redisGroupDao.getGroupById(Integer.parseInt(group_id), null);
				if (null != group) {
					if (Constant.GROUP_TYPE_STUDENT == group.getGroup_type()) {
						resultMap = redisTechRangeDao.getGroupStudManager(group_id);
					} else {
						resultMap = redisTechRangeDao.getGroupTechManager(group_id);
					}
				} else {
					resultMap = redisTechRangeDao.getGroupStudManager(group_id);
					resultMap.putAll(redisTechRangeDao.getGroupTechManager(group_id));
				}
			} else {
				// do nothing
			}
			for (Department dep : list) {
				Map<String, Object> objmap = new HashMap<String, Object>();
				List<Teacher> teacherList = redisTeacherDao.getTechsByDepId(dep.getDep_id().toString(), null);
				if (null != resultList && resultList.size() != 0) {
					for (Teacher teacher : teacherList) {
						teacher.setIs_selected(Constant.NO);
						for (Map<String, Object> oMap : resultList) {
							
								if (Integer.parseInt(oMap.get("tech_id").toString()) == teacher.getTech_id()) {
									teacher.setIs_selected(Constant.YES);
									resultList.remove(map);
									break;
								}
							
						}
					}
				} else if (null != resultMap && resultMap.values().size() != 0) {
					for (Teacher teacher : teacherList) {
						if (resultMap.get(teacher.getTech_id().toString()) instanceof Teacher) {
							teacher.setIs_selected(Constant.YES);
						} else {
							teacher.setIs_selected(Constant.NO);
						}
					}
				} else {
					// do nothing
				}
				objmap.put("dep_id", dep.getDep_id());
				objmap.put("dep_name", dep.getDep_name());
				objmap.put("techData", teacherList);
				deps.add(objmap);
			}
			map.put("depData", deps);
			result.setData(map);
			result.setMessage("获取成功");
			result.setSuccess(true);
		} catch (Exception e) {
			result.setMessage(e.getMessage());
			result.setSuccess(false);
		}
		return result;
	}

	/**
	 * 获取班级下的课程及任课教师数据
	 * 
	 * @param org_id
	 * @param clas_id
	 * @param cour_id
	 */
	@ResponseBody
	@RequestMapping(value = "/getCourseTeacherofClass")
	@EduJsonFilters(value = { @EduJsonFilter(mixin = TeacherShortFilter.class, target = Teacher.class) })
	public Result<List<Map<String, String>>> getCourseTeacherofClass(@RequestParam String org_id,
			@RequestParam(value = "clas_id", defaultValue = "") String clas_id,
			@RequestParam(value = "cour_id", defaultValue = "") String cour_id, HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		Result<List<Map<String, String>>> result = new Result<List<Map<String, String>>>();
		try {
			List<Course> list = redisCourseDao.getCoursByOrgId(org_id);
			List<Map<String, String>> aheadList = new ArrayList<Map<String, String>>();
			List<Map<String, String>> lastList = new ArrayList<Map<String, String>>();
			for (Course course : list) {
				Map<String, String> map = new HashMap<String, String>();
				List<Map<String, Object>> infoList = techRangeService.getTeaching(org_id, "", "", course.getCour_id().toString(),
						clas_id);
				String teacherName = "";
				for (Map<String, Object> info : infoList) {
					teacherName += info.get("tech_name") + " ";
				}
				map.put("course_id", course.getCour_id().toString());
				map.put("course_name", course.getCour_name());
				map.put("teachers_name", teacherName);
				if (StringUtils.isEmpty(teacherName)) {
					lastList.add(map);
				} else {
					aheadList.add(map);
				}
			}
			aheadList.addAll(aheadList.size(), lastList);
			result.setData(aheadList);
			result.setMessage("获取成功");
			result.setSuccess(true);
		} catch (Exception e) {
			result.setMessage(e.getMessage());
			result.setSuccess(false);
		}
		return result;
	}

	/**
	 * 通用方法：根据参数替换教师数据范围
	 * 
	 * @param org_id 【必填】机构主键，所有操作均在此机构下
	 * @param tech_ids 【必填】教师主键，待替换的教师主键，多个主键用逗号连接
	 * @param dep_id [选填]部门主键，替换部门管理员
	 * @param grade_id [选填]年级主键，替换年级组长
	 * @param clas_id [选填]班级主键，替换班主任
	 * @param cour_id [选填,与clas_id联合使用]课程主键，替换任课教师
	 * @param group_id [选填]组主键，替换组管理员
	 */
	@RequestMapping(value = "/replaceTeachers")
	public @ResponseBody
	Result<Integer> replaceManager(@RequestParam(value = "tech_ids") String tech_ids,
			@RequestParam(value = "org_id") String org_id, @RequestParam(value = "dep_id", defaultValue = "") String dep_id,
			@RequestParam(value = "grade_id", defaultValue = "") String grade_id,
			@RequestParam(value = "clas_id", defaultValue = "") String clas_id,
			@RequestParam(value = "cour_id", defaultValue = "") String cour_id,
			@RequestParam(value = "group_id", defaultValue = "") String group_id,
			@RequestParam(value = "group_type", defaultValue = "") String group_type, HttpServletRequest request) {
		Result<Integer> result = new Result<Integer>();
		try {
			if (StringUtils.isNotEmpty("group_id") && StringUtils.isEmpty("group_type")) {
				Group group = redisGroupDao.getGroupById(Integer.parseInt(group_id), null);
				if (null != group) {
					group_type = group.getGroup_type().toString();
				}
			}
			techRangeService.replaceRange(tech_ids, org_id, dep_id, grade_id, clas_id, cour_id, group_id, group_type);
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
	
	
	@RequestMapping(value="/updateBZRTeacher")
	@ResponseBody
	public Result<String> updateBZRTeacher(@RequestParam(value = "org_id") String org_id,@RequestParam(value = "grade_id") String grade_id,
			@RequestParam(value = "clas_id", defaultValue = "") String clas_id,
			@RequestParam(value = "tech_ids") String tech_ids, HttpServletRequest request) {
		Result<String> result = new Result<String>();
		try {
			techRangeService.updateBZRTeacher(org_id, grade_id, clas_id, tech_ids);
			result.setSuccess(true);
			result.setMessage(EnumMessage.success.getMessage());
			result.setCode(EnumMessage.success.getCode());
		} catch (Exception ex) {
			result.setSuccess(false);
			result.setMessage(ex.getMessage());
			result.setCode(EnumMessage.fail.getCode());
		}

		return result;
	}
	

	@RequestMapping(value = "/getTechsOfOrgByPage")
	public @ResponseBody
	Result<Paging<Teacher>> getTechsOfOrgByPage(@RequestParam(value = "org_id", defaultValue = "-1") Integer org_id,
			@RequestParam(value = "type", defaultValue = "0") Integer type,
			@RequestParam(value = "seach_name", defaultValue = "") String seach_name,
			@RequestParam(value = "start", defaultValue = "0") Integer start,
			@RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum,
			@RequestParam(value = "numPerPage", defaultValue = "20") Integer numPerPage, HttpServletRequest request) {
		Result<Paging<Teacher>> result = new Result<Paging<Teacher>>();
		Paging<Teacher> pages = new Paging<Teacher>();
		pages.setPage(pageNum);
		pages.setStart(start);
		pages.setLimit(numPerPage);

		teacherService.getTechListWithPaging(pages, org_id, type, seach_name);

		result.setData(pages);
		result.setSuccess(true);
		result.setMessage(EnumMessage.success.getMessage());
		result.setCode(EnumMessage.success.getCode());
		return result;
	}

	// 教师id 获取教师详细
	@ResponseBody
	@RequestMapping(value = "/getByUserId")
	@EduJsonFilters(value = { @EduJsonFilter(mixin = TeacherFilter.class, target = Teacher.class) })
	public Result<Teacher> getByUserId(HttpServletRequest request, HttpServletResponse response) throws IOException {
		Result<Teacher> result = new Result<Teacher>();
		String user_id = request.getParameter("user_id");
		try {
			Teacher teacher = redisTeacherDao.getByUserId(user_id);
			// Teacher Teacher = redisTeacherDao.getByTechId(tech_id,null);
			result.setData(teacher); 
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
	@RequestMapping(value = "/getTechsByIds")
	@EduJsonFilters(value = { @EduJsonFilter(mixin = TeacherFilter.class, target = Teacher.class) })
	public Result<List<Teacher>> getTechsByIds(HttpServletRequest request, HttpServletResponse response) throws IOException  {		
		Result<List<Teacher>> result = new Result<List<Teacher>>();		
		List<Teacher> data = new ArrayList<Teacher>();	
		try {			
			String user_ids = request.getParameter("user_ids");
			if(StringUtils.isNotEmpty(user_ids)){
				String[] ids = user_ids.split(",");
				for(int i=0;i<ids.length;i++){
					Teacher teacher = redisTeacherDao.getByUserId(ids[i]);
					data.add(teacher);
				}
			}
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
	
	
	
	
	@RequestMapping(value="/getUploadRecord")
	@ResponseBody
	public Result<List<ExcelRes>> getUploadRecord(@RequestParam(value = "type") String type, @RequestParam(value = "orgID") String orgID, @RequestParam(value = "userID") String userID,
			@RequestParam(value = "proce_type") String proce_type) {

		// 按照类型查询教师或者学生的上传记录
		List<ExcelRes> excelResList = resourcesService.getExcelResList(Integer.valueOf(type), Integer.valueOf(userID), Integer.valueOf(orgID), Integer.valueOf(proce_type));

		Result<List<ExcelRes>> result = new Result<List<ExcelRes>>();
		result.setData(excelResList);
		result.setSuccess(true);
		result.setMessage(EnumMessage.success.getMessage());
		result.setCode(EnumMessage.success.getCode());

		return result;
	}

	@RequestMapping(value = "/uploadExcel4BatchAddTeacher", method = RequestMethod.POST, produces = { MediaType.APPLICATION_JSON_VALUE })
	@ResponseBody
	public Result<String> uploadExcel4BatchAddTeacher(@RequestParam(value = "orgID") String orgID,
			@RequestParam(value = "userID") String userID, @RequestParam(value = "file") MultipartFile file,
			@RequestParam(value = "fileName") String fileName) throws IOException {

		Result<String> result = new Result<String>();

		fileName = URLDecoder.decode(fileName, "UTF-8");
		String ext = fileName.substring(fileName.lastIndexOf(".") + 1);
		InputStream fin = file.getInputStream();
		Long length = file.getSize();
		String fileID = this.resourcesService.saveExcel(0, Integer.parseInt(userID), fin, length.intValue(), ext,
				Integer.parseInt(orgID), fileName, (byte) 3,false);
		if (StringUtils.isNotBlank(fileID)) {
			fileID = fileID.substring(0, fileID.indexOf("."));
			// 文件上传成功后将得到的文件ID放入队列
//			Message<String> message = MessageBuilder.withPayload(fileID).build();
//			messageChannel.send(message);
			
			this.teacherService.batchAddTeacher(fileID);

			result.setSuccess(true);
			result.setMessage(EnumMessage.success.getMessage());
			result.setCode(EnumMessage.success.getCode());
		} else {
			result.setSuccess(false);
			result.setMessage(EnumMessage.fail.getMessage());
			result.setCode(EnumMessage.fail.getCode());
		}

		return result;
	}
	
	@RequestMapping(value="/getExcelDownLoadPath")
	@ResponseBody
	public Result<String> getExcelDownLoadPath(@RequestParam(value = "fileID") String fileID) {

		Result<String> result = new Result<String>();

		if (StringUtils.isNotBlank(fileID)) {
			ExcelRes excelRes = resourcesService.getExcelRes(Integer.valueOf(fileID));

			String downLoadPath = (String) util.getPathByExcelName(fileID + "." + excelRes.getExt());

			result.setData(downLoadPath);
			result.setSuccess(true);
			result.setMessage(EnumMessage.success.getMessage());
			result.setCode(EnumMessage.success.getCode());
		} else {
			result.setData("");
			result.setSuccess(false);
			result.setMessage(EnumMessage.fail.getMessage());
			result.setCode(EnumMessage.fail.getCode());
		}

		return result;
	}
	
	
	
	
	/**
	 * 校验教师手机号
	 * @param depId
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/checkTechPhone")
	public @ResponseBody Result<Integer> checkTechPhone(
			@RequestParam(value="phone") String phone,
			@RequestParam(value="org_id") String orgId,
			@RequestParam(value="tech_id",defaultValue="") String tech_id,
			HttpServletRequest request){
		Result<Integer> result = new Result<Integer>();
		//List<Department> list = redisDepartmentDao.getDepsByOrgId(orgId.toString());
		List<Teacher> list = redisTeacherDao.getTeacherListByOrgId(orgId, null);
		for (Teacher teacher : list) {
			if(phone.equals(teacher.getUser_mobile())){	
				if(!tech_id.equals(teacher.getTech_id().toString())){
					result.setSuccess(false);
					result.setMessage(EnumMessage.phone_illegal.getMessage());
					result.setCode(EnumMessage.phone_illegal.getCode());
					return result;	
				}
			}
		}
		result.setSuccess(true);
		result.setMessage(EnumMessage.success.getMessage());
		result.setCode(EnumMessage.success.getCode());
		return result;
	}
	
	
	/**
	 * 根据机构主键查询教师列表
	 * 
	 * @param tech_id
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/getTechMapList", method = RequestMethod.GET)
	public @ResponseBody
	Result<Map<String, Object>> getTechMapList(@RequestParam(value = "org_id") String org_id, HttpServletRequest request) {
		Result<Map<String, Object>> result = new Result<Map<String, Object>>();
		Map<String, Object> data = new HashMap<String, Object>();

		try {
			List<Teacher> list = redisTeacherDao.getTechMapListByOrgId(org_id, null);
			// 返回结果
			data.put("teacher", list);
		} catch (Exception e) {
			result.setSuccess(false);
			result.setMessage(EnumMessage.fail.getMessage());
			result.setCode(EnumMessage.fail.getCode());
		}
		result.setData(data);
		result.setCode(EnumMessage.success.getCode());
		result.setMessage(EnumMessage.success.getMessage());
		result.setSuccess(true);
		return result;
	}
	
	
	
	
	
	
	@RequestMapping(value = "/getTechCour")
	public @ResponseBody
	Result<List<Course>> getTechCour(HttpServletRequest request) {
		Result<List<Course>> result = new Result<List<Course>>();
		String tech_id = request.getParameter("tech_id");
		try {
			List<Course> list = redisTechRangeDao.getTechCour(new Integer(tech_id));			
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
	
	
	
	
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/updateTechCour")
	public @ResponseBody
	Result<Integer> updateTechCour(HttpServletRequest request)  throws Exception{
		Result<Integer> result = new Result<Integer>();
		
		String tech_id = request.getParameter("tech_id");
		String cour_ids = request.getParameter("cour_ids");
		String org_id = request.getParameter("org_id");
		if(StringUtils.isEmpty(tech_id)||StringUtils.isEmpty(cour_ids)||StringUtils.isEmpty(org_id)){
			throw new EsbException(EnumException.common_must_login);
		}
		try {
			List<TechRange> list = redisTechRangeDao.getTechRK(new Integer(tech_id));

			techRangeService.replaceRangeSK(org_id,tech_id, cour_ids, list);
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
	
	
	
	// 
	@ResponseBody
	@RequestMapping(value = "/getTechsOfRlId")
	@EduJsonFilters(value = { @EduJsonFilter(mixin = TeacherShortFilter.class, target = Teacher.class) })
	public Result<Map<String, Object>> getTechsOfRlId(HttpServletRequest request,
			HttpServletResponse response) {
		
		
		String org_id = request.getParameter("org_id");

		Result<Map<String, Object>> result = new Result<Map<String, Object>>();
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			List<Map<String,Object>> list = teacherService.getTechsOfRlId(org_id,EnumRoleLabel.财务审批员.getCode());
			map.put("techs", list);
			result.setData(map);
			result.setMessage("获取成功");
			result.setSuccess(true);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			result.setMessage(e.getMessage());
			result.setSuccess(false);
		}
		return result;
	}
	
	
	// 班级下的 授课教师
	@ResponseBody
	@RequestMapping(value = "/getSkClasCourTech")
	@EduJsonFilters(value = { @EduJsonFilter(mixin = TeacherFilter.class, target = Teacher.class) })
	public Result<Map<String, Object>> getSkClasCourTech(HttpServletRequest request, HttpServletResponse response) throws IOException, EsbException {
		Result<Map<String, Object>> result = new Result<Map<String, Object>>();
		String clas_id = request.getParameter("clas_id");
		String cour_id = request.getParameter("cour_id");
		String org_id = request.getParameter("org_id");
		String grade_id = request.getParameter("grade_id");
		if(StringUtils.isEmpty(clas_id)||StringUtils.isEmpty(org_id)||StringUtils.isEmpty(grade_id)||StringUtils.isEmpty(cour_id)){
			throw new EsbException(EnumException.common_must_login);
		}
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			List<Map<String, Object>> list = redisTechRangeDao.getSkClasCourTech(clas_id, cour_id);
			map.put("techs", list);
			result.setData(map);
			result.setMessage("获取成功");
			result.setSuccess(true);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			result.setMessage(e.getMessage());
			result.setSuccess(false);
		}
		return result;
	}
	

	/**
	 * 获取通讯录
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 * @throws EsbException
	 */
	@ResponseBody
	@RequestMapping(value = "/getPhoneBook")
	@EduJsonFilters(value = { @EduJsonFilter(mixin = TeacherFilter.class, target = Teacher.class) })
	public Result<Map<String,List<Teacher>>> getPhoneBook(HttpServletRequest request, HttpServletResponse response) throws IOException, EsbException {
		Result<Map<String,List<Teacher>>> result = new Result<Map<String,List<Teacher>>>();
		Map<String,List<Teacher>> map = new HashMap<String,List<Teacher>>();
		String org_id = request.getParameter("org_id");
		try {
			List<Teacher> list = teacherService.getPhoneBook(Integer.parseInt(org_id));
			Collections.sort(list, new TechToSortComparator());
			String p = "";
			for (Teacher t : list) {
				p = PinyinUtil.hanziToPinyin(t.getTech_name()).substring(0, 1);
				if(map.containsKey(p)){
					map.get(p).add(t);
				}else{
					List<Teacher> ts = new ArrayList<Teacher>();
					ts.add(t);
					map.put(p, ts);
				}
			}
			result.setData(map);
			result.setMessage("获取成功");
			result.setSuccess(true);
		} catch (Exception e) {
			result.setMessage(e.getMessage());
			result.setSuccess(false);
		}
		return result;
	}
	
	
	
	// 根据机构id 获取教师范围集合
	@ResponseBody
	@RequestMapping(value = "/getTechRanges")
	@EduJsonFilters(value = { @EduJsonFilter(mixin = TechRangeFilter.class, target = TechRange.class) })
	public Result<List<TechRange>> getTechRanges(@RequestParam int org_id, HttpServletRequest request,
			HttpServletResponse response) {
		Result<List<TechRange>> result = new Result<List<TechRange>>();

		try {
			List<TechRange> list = teacherService.getTechRanges(org_id);
			result.setData(list);
			result.setMessage("获取成功");
			result.setSuccess(true);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			result.setMessage(e.getMessage());
			result.setSuccess(false);
		}
		return result;
	}
	
	
	// 根据机构id 获取教师范围集合
	@ResponseBody
	@RequestMapping(value = "/getTechRolesOforg")
	@EduJsonFilters(value = { @EduJsonFilter(mixin = TechRangeFilter.class, target = TechRange.class) })
	public Result<List<Map<String,String>>> getTechRolesOforg(@RequestParam int org_id, HttpServletRequest request,
			HttpServletResponse response) {
		Result<List<Map<String,String>>> result = new Result<List<Map<String,String>>>();

		try {
			List<Map<String,String>> list = teacherService.getTechsOforg(org_id);
			result.setData(list);
			result.setMessage("获取成功");
			result.setSuccess(true);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			result.setMessage(e.getMessage());
			result.setSuccess(false);
		}
		return result;
	}
	
	@RequestMapping(value = "/uploadExcelTechRanges", method = RequestMethod.POST, produces = { MediaType.APPLICATION_JSON_VALUE })
	@ResponseBody
	public Result<Map<String, Object>> uploadExcelTechRanges(@RequestParam(value = "org_id") int org_id,
			@RequestParam(value = "file") MultipartFile file,
			@RequestParam(value = "fileName") String fileName) throws IOException {

		Result<Map<String, Object>> result = new Result<Map<String, Object>>();
		try {
			fileName = URLDecoder.decode(fileName, "UTF-8");
			Map<String,String> map = new HashMap<String,String>();
			List<Course> courList = courseDao.getCoursByOrgId(org_id+"");
			for(int i = 0;i<courList.size();i++){
				Course cour = courList.get(i);
				map.put(cour.getCour_name(), cour.getCour_id()+"");
			}
			Map<String,Object> resultMap = this.teacherService.batchTechRange(org_id,file,map);
			result.setData(resultMap);
			result.setMessage("获取成功");
			result.setSuccess(true);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			result.setMessage(e.getMessage());
			result.setSuccess(false);
		}
		return result;
	}
	
	
}
