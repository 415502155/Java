package cn.edugate.esb.web;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import cn.edugate.esb.EduConfig;
import cn.edugate.esb.Result;
import cn.edugate.esb.ResultDwz;
import cn.edugate.esb.comparator.GroupSorterBySchoolType;
import cn.edugate.esb.comparator.GroupToSortComparator;
import cn.edugate.esb.eduEnum.EnumMessage;
import cn.edugate.esb.entity.Campus;
import cn.edugate.esb.entity.Grade;
import cn.edugate.esb.entity.Group;
import cn.edugate.esb.entity.GroupMember;
import cn.edugate.esb.entity.OrgUser;
import cn.edugate.esb.entity.Organization;
import cn.edugate.esb.entity.Parent;
import cn.edugate.esb.entity.Student;
import cn.edugate.esb.entity.Teacher;
import cn.edugate.esb.entity.UcUser;
import cn.edugate.esb.entity.UcuserOrguser;
import cn.edugate.esb.entity.UserAccount;
import cn.edugate.esb.exception.EnumException;
import cn.edugate.esb.params.EduJsonFilter;
import cn.edugate.esb.params.EduJsonFilters;
import cn.edugate.esb.params.filter.GroupFilter;
import cn.edugate.esb.params.filter.GroupMemberMoreFilter;
import cn.edugate.esb.params.filter.OrganizationFilter;
import cn.edugate.esb.params.filter.OrganizationForGroupMemberFilter;
import cn.edugate.esb.params.filter.ParentFilter;
import cn.edugate.esb.params.filter.StudentForGroupMemberFilter;
import cn.edugate.esb.params.filter.TeacherForGroupMemberFilter;
import cn.edugate.esb.params.filter.TeacherShortFilter_sk;
import cn.edugate.esb.pojo.Login;
import cn.edugate.esb.redis.dao.RedisCampusDao;
import cn.edugate.esb.redis.dao.RedisLoginDao;
import cn.edugate.esb.redis.dao.RedisOrgUserDao;
import cn.edugate.esb.redis.dao.RedisOrganizationDao;
import cn.edugate.esb.redis.dao.RedisTechRangeDao;
import cn.edugate.esb.redis.dao.RedisUcUserDao;
import cn.edugate.esb.redis.dao.RedisUcUserOrguserDao;
import cn.edugate.esb.service.GroupService;
import cn.edugate.esb.service.OrganizationService;
import cn.edugate.esb.service.ParentService;
import cn.edugate.esb.service.RoleService;
import cn.edugate.esb.service.UserService;
import cn.edugate.esb.util.Constant;
import cn.edugate.esb.util.Util;
import cn.edugate.esb.util.Utils;

@Controller
@RequestMapping("/no")
public class NoFilterController {

	@Autowired
	private RedisOrganizationDao redisOrganizationDao;

	@Autowired
	private RedisCampusDao redisCampusDao;

	@Autowired
	private OrganizationService organizationService;

	@Autowired
	private ParentService parentService;

	@Autowired
	private Util util;

	@Autowired
	private RedisTechRangeDao redisTechRangeDao;
	
	@Autowired
	private EduConfig eduConfig;

	@Autowired
	private RedisOrgUserDao redisOrgUserDao;

	@Autowired
	private RedisUcUserDao redisUcUserDao;

	@Autowired
	private RedisUcUserOrguserDao redisUcUserOrguserDao;

	@Autowired
	private RedisLoginDao redisLoginDao;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private GroupService groupService;

	@ResponseBody
	@EduJsonFilters(value = { @EduJsonFilter(mixin = OrganizationFilter.class, target = Organization.class) })
	@RequestMapping(value = "/getOrgInfo")
	public Result<Organization> getOrgInfo(@RequestParam String org_id, HttpServletRequest request, HttpServletResponse response)
			throws IOException {
		Result<Organization> result = new Result<Organization>();
		Organization org = redisOrganizationDao.getByOrgId(org_id, null);
		if (org == null) {
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
		try {
			result.setData(org);
			result.setMessage("获取成功");
			result.setSuccess(true);
		} catch (Exception e) {
			result.setMessage(e.getMessage());
			result.setSuccess(false);
		}
		return result;
	}

	/**
	 * 跳转到批量导入机构页面
	 * 
	 * @return
	 */
	@RequestMapping(value = "/goImportOrg")
	public ModelAndView goImportOrg() {
		ModelAndView mav = new ModelAndView("/organization/importOrg");
		return mav;
	}

	/**
	 * 世纪伟业内部接口，根据school.foundation表中的school创建机构
	 * 
	 * @param district
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 */
	@ResponseBody
	@RequestMapping(value = "/importOrg")
	// http://127.0.0.1:8080/esb/no/importOrg?district=%E9%9D%99%E6%B5%B7
	public ResultDwz importOrg(@RequestParam String district, HttpServletRequest request, HttpServletResponse response)
			throws IOException {
		Integer count = 0;
		ResultDwz resultDWZ = new ResultDwz();
		String report = "";
		try {
			List<Organization> schoolList = organizationService.getSchool(district);
			if (null != schoolList && schoolList.size() != 0) {
				for (Organization org : schoolList) {
					org.setIs_campus(Constant.NO);
					org.setLayout(2);
					org.setType(org.getSchoolType().intValue());
					if (null == org.getArea()) {
						report += org.getOrg_name_cn() + ";";
						continue;
					}
					String[] areaArr = org.getArea().split("_");
					org.setArea("天津_" + areaArr[2].substring(0, 2) + "区_null");
					List<Grade> gradeList = new ArrayList<Grade>();
					if (org.getType() == Constant.FUR_TYPE_SCHOOLS.intValue()) {
						gradeList = Utils.addGradeByType(areaArr[3]);
						org.setGradeList(gradeList);
					}
					List<Campus> campusList = new ArrayList<Campus>();
					if (org.getType() != Constant.FUR_TYPE_BUREAU.intValue()) {
						Campus campus = new Campus();
						campus.setCam_name("主校区");
						campus.setCam_note(org.getAddress());
						campus.setCam_type(0);
						campusList.add(campus);
					}
					org.setCampusList(campusList);
					String save = organizationService.saveOrganization(org);
					// 执行批量导入数据的存储过程，暂时忽略失败的情况
					organizationService.executeBatchImport(org);
					if (save.startsWith("success")) {
						count++;
					}
				}
				if (StringUtils.isEmpty(report)) {
					resultDWZ.setMessage("导入完成，共有 " + schoolList.size() + " 条数据，成功导入 " + count + " 条;");
				} else {
					resultDWZ.setMessage("导入完成，共有 " + schoolList.size() + " 条数据，成功导入 " + count + " 条;\r\n失败学校名单:" + report);
				}
			} else {
				resultDWZ.setMessage("没有需要导入的数据");
			}
			resultDWZ.setStatusCode("200");
			resultDWZ.setCallbackType("closeCurrent");
		} catch (Exception e) {
			resultDWZ.setStatusCode("300");
			resultDWZ.setMessage("发生异常！");
		}
		return resultDWZ;
	}

	/**
	 * 根据学生主键获取所有家长的信息
	 * 
	 * @param stud_id
	 * @param response
	 * @return
	 * @throws IOException
	 */
	@ResponseBody
	@RequestMapping(value = "/student/getParentPhone")
	@EduJsonFilters(value = { @EduJsonFilter(mixin = ParentFilter.class, target = Parent.class) })
	public Result<List<Parent>> getParentPhone(@RequestParam(value = "stud_id") String stud_id, HttpServletResponse response)
			throws IOException {
		Result<List<Parent>> result = new Result<List<Parent>>();
		try {
			List<Parent> list = parentService.getByStudId(stud_id);
			result.setData(list);
			result.setSuccess(true);
			result.setMessage(EnumMessage.success.getMessage());
			result.setCode(EnumMessage.success.getCode());
		} catch (Exception e) {
			result.setSuccess(false);
			result.setMessage(e.getMessage());
			result.setCode(EnumMessage.fail.getCode());
		}
		return result;
	}

	/**
	 * 根据学生主键获取所有家长的信息
	 * 
	 * @param stud_id
	 * @param response
	 * @return
	 * @throws IOException
	 */
	@ResponseBody
	@RequestMapping(value = "/parent/remind")
	@EduJsonFilters(value = { @EduJsonFilter(mixin = ParentFilter.class, target = Parent.class) })
	public Result<List<Parent>> remind(@RequestParam(value = "stud_id") String stud_id, HttpServletResponse response)
			throws IOException {
		Result<List<Parent>> result = new Result<List<Parent>>();
		try {
			List<Parent> list = parentService.getByStudId(stud_id);
			result.setData(list);
			result.setSuccess(true);
			result.setMessage(EnumMessage.success.getMessage());
			result.setCode(EnumMessage.success.getCode());
		} catch (Exception e) {
			result.setSuccess(false);
			result.setMessage(e.getMessage());
			result.setCode(EnumMessage.fail.getCode());
		}
		return result;
	}

	// 班级下 班主任
	@ResponseBody
	@RequestMapping(value = "/getSkClasBZRs")
	@EduJsonFilters(value = { @EduJsonFilter(mixin = TeacherShortFilter_sk.class, target = Teacher.class) })
	public Result<Map<String, List<Teacher>>> getSkClasBZRs(HttpServletRequest request, HttpServletResponse response)
			throws IOException {
		Result<Map<String, List<Teacher>>> result = new Result<Map<String, List<Teacher>>>();
		try {
			String ids = request.getParameter("ids");
			if (StringUtils.isEmpty(ids))
				throw new Exception();
			Map<String, List<Teacher>> map = redisTechRangeDao.getSkClasBZRs(ids);
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
	
	@RequestMapping(value = "/validateToken")
	@ResponseBody
	public Result<String> validateToken(HttpServletRequest request, HttpServletResponse response) {
		Result<String> resultEntity = new Result<String>();
		String token = request.getParameter("token");
		String udid = request.getParameter("udid");
		String version = request.getParameter("version");
		
		if (StringUtils.isNotBlank(token) && StringUtils.isNotBlank(udid)) {
			String[] params = token.split("\\_");
			if (params.length >= 5) {

				String tokenseg = params[4];
				String md5str = params[0];
				String insertime = params[1];
				String user_id = params[2];
				String type = params[3];
				Long expired = 0L;
				switch (version) {
					case "0":
						expired = this.eduConfig.getEduconfig().getExpired0();
						break;
					case "1":
						expired = this.eduConfig.getEduconfig().getExpired1();
						break;
					case "2":
						expired = this.eduConfig.getEduconfig().getExpired2();
						break;
					default:
						expired = this.eduConfig.getEduconfig().getExpired0();
						break;
				}
				Long extratime = this.redisLoginDao.getExtratime(type, user_id);
				Long expiretime = (expired * 1000 + Long.parseLong(insertime)) + extratime;
				Long nowtime = (new Date()).getTime();
				if (expiretime >= nowtime) {
					String user_salt = "";
					UserAccount ua = null;
					if ("0".equals(type)) {
						OrgUser user = this.redisOrgUserDao.getUserById(user_id);
						if (user != null) {
							user_salt = user.getUser_salt();
							UcuserOrguser uuser = this.redisUcUserOrguserDao.getByUserId(user_id);
							if (uuser != null) {
								ua = this.userService.getUserAccount(uuser.getUc_id().toString(), version, "1");

							} else {
								ua = this.userService.getUserAccount(user.getUser_loginname(), version, "0");
							}
						}
					} else if ("1".equals(type)) {
						UcUser user = this.redisUcUserDao.getUserById(user_id);
						if (user != null) {
							user_salt = user.getUc_salt();
							ua = this.userService.getUserAccount(user_id, version, "1");
						}
					}
					String md5 = Utils.MD5(insertime + ":" + user_id + ":" + type + ":" + user_salt + ":" + tokenseg + ":"
							+ this.eduConfig.getEduconfig().getSecret());
					if (ua != null && md5.equals(md5str)) {
						String udidseg = udid.substring(udid.length() - 8);

						Long timespan = Math.abs(ua.getUpdated_time().getTime() - Long.parseLong(insertime));
						if (udidseg.equals(tokenseg) && udid.equals(ua.getUdid()) && timespan < 10000) {
							// 验证通过
							resultEntity.setSuccess(true);
							resultEntity.setData("success");
						}
					}
				}
			}
		}
		
		
		if (StringUtils.isBlank(resultEntity.getData())) {
			resultEntity.setSuccess(false);
			resultEntity.setData("error");
		}
		return resultEntity;
	}

	/**
	 * 震声要的根据机构用户主键返回组、组成员及详细信息
	 * @param request
	 * @param user_ids
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/getGroups")
	@EduJsonFilters(value={@EduJsonFilter(mixin=GroupFilter.class, target=Group.class),
			@EduJsonFilter(mixin=GroupMemberMoreFilter.class, target=GroupMember.class),
			@EduJsonFilter(mixin=TeacherForGroupMemberFilter.class, target=Teacher.class),
			@EduJsonFilter(mixin=OrganizationForGroupMemberFilter.class, target=Organization.class)})
	public @ResponseBody Result<List<Object>> getGroups(HttpServletRequest request,
			@RequestParam(value="user_ids",defaultValue="") String user_ids){
		Result<List<Object>> result = new Result<List<Object>>();
//		Map<String,Object> map = new HashMap<String,Object>();
//		List<Map<String,Object>> objs = new ArrayList<Map<String,Object>>();
		List<Object> list = groupService.getGroupsWithMembers(user_ids);
//		Map<String, Map<String,Object>> groupMap = new HashMap<String,Map<String,Object>>();
//		for (Object object : list) {
//			Object[] obj = (Object[]) object; 
//			if(!groupMap.containsKey(obj[1].toString())){
//				Map<String,Object> objmap = new HashMap<String,Object>();
//				try {
//					objmap.put("group_id", obj[1].toString());
//					objmap.put("group_name", obj[2].toString());	
//					objmap.put("group_type", obj[3].toString());
//				} catch (Exception e) {
//					continue;
//				}
//				objmap.put("group_note", obj[4]);
//				objmap.put("hx_groupid", obj[5]);
//				objmap.put("memberData", new ArrayList<Map<String,Object>>());
//				groupMap.put(obj[1].toString(), objmap);
//			}
//		}
//		for (Object o : list) {
//			Object[] obj = (Object[]) o; 
//			Map<String,Object> objmap = groupMap.get(obj[1].toString());
//			List<Map<String,Object>> memberList = (List<Map<String,Object>>) objmap.get("memberData");
//			Map<String,Object> memberInfo = new HashMap<String,Object>();
//			try {
//				memberInfo.put("group_member_id", obj[6].toString());
//				memberInfo.put("mem_id", obj[7].toString());
//				memberInfo.put("mem_name", obj[8].toString());
//				memberInfo.put("user_id", obj[11].toString());
//			} catch (Exception e) {
//				continue;
//			}
//			memberInfo.put("mobile", obj[9]);
//			memberInfo.put("mem_nick", obj[10]);
//			memberInfo.put("tech_head", obj[12]);
//			if(null!=util){
//				if (null!=obj[12]&&!"".equals(obj[12].toString())) {
//					memberInfo.put("headurl", util.getPathByPicName(obj[12].toString()));
//				} else {
//					memberInfo.put("headurl", "");
//				}
//			}
//	    	memberInfo.put("org_id", obj[13]);
//	    	memberInfo.put("org_name", obj[14]);
//    	    memberInfo.put("sorter", obj[0]);
//    	    memberList.add(memberInfo);
//    	    objmap.put("memberData", memberList);
//    	    groupMap.put(obj[1].toString(), objmap);
//		}
//		for (Map<String,Object> m : groupMap.values()) {
//			List<Map<String,Object>> memberList = (List<Map<String,Object>>) m.get("memberData");
//			GroupSorterBySchoolType mcg = new GroupSorterBySchoolType();  
//			Collections.sort(memberList,mcg);
//			objs.add(m);
//		}
//		GroupToSortComparator msc = new GroupToSortComparator();  
//		Collections.sort(objs,msc);
//		map.put("groupData", objs);
		result.setData(list);
		result.setSuccess(true);
		result.setMessage(EnumMessage.success.getMessage());
		result.setCode(EnumMessage.success.getCode());
		return result;
	}

}
