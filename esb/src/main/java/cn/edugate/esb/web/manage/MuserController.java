package cn.edugate.esb.web.manage;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import cn.edugate.esb.ResultDwz;
import cn.edugate.esb.comparator.ClassesSorter;
import cn.edugate.esb.comparator.CompusToSortComparator;
import cn.edugate.esb.comparator.GradeSorter;
import cn.edugate.esb.entity.Campus;
import cn.edugate.esb.entity.Card;
import cn.edugate.esb.entity.Clas2Student;
import cn.edugate.esb.entity.Classes;
import cn.edugate.esb.entity.Grade;
import cn.edugate.esb.entity.OrgUser;
import cn.edugate.esb.entity.Organization;
import cn.edugate.esb.entity.Parent;
import cn.edugate.esb.entity.Role;
import cn.edugate.esb.entity.Student;
import cn.edugate.esb.entity.StudentParent;
import cn.edugate.esb.entity.Teacher;
import cn.edugate.esb.entity.TeacherRole;
import cn.edugate.esb.redis.dao.RedisCampusDao;
import cn.edugate.esb.redis.dao.RedisCardDao;
import cn.edugate.esb.redis.dao.RedisClassStudentDao;
import cn.edugate.esb.redis.dao.RedisClassesDao;
import cn.edugate.esb.redis.dao.RedisGradeDao;
import cn.edugate.esb.redis.dao.RedisOrgUserDao;
import cn.edugate.esb.redis.dao.RedisOrganizationDao;
import cn.edugate.esb.redis.dao.RedisParentDao;
import cn.edugate.esb.redis.dao.RedisStudentDao;
import cn.edugate.esb.redis.dao.RedisTeacherDao;
import cn.edugate.esb.service.Class2StudentService;
import cn.edugate.esb.service.ClassesService;
import cn.edugate.esb.service.OrganizationService;
import cn.edugate.esb.service.ParentService;
import cn.edugate.esb.service.RoleService;
import cn.edugate.esb.service.StudentService;
import cn.edugate.esb.service.TeacherRoleService;
import cn.edugate.esb.service.TeacherService;
import cn.edugate.esb.service.UserService;
import cn.edugate.esb.util.Constant;
import cn.edugate.esb.util.ExcelUtil;
import cn.edugate.esb.util.FileProperties;
import cn.edugate.esb.util.Paging;
import cn.edugate.esb.util.Utils;

/**
 * 
 * @Name: 角色管理controller
 * @Description: 维护角色(添加和删除角色),维护角色的权限。
 * @date  2013-4-11
 * @version V1.0
 */
@Controller
@RequestMapping("/manage/user")
public class MuserController {
	static Logger logger=LoggerFactory.getLogger(MuserController.class);
	private static String httpBaseUIUrl = FileProperties.getProperty("httpBaseUIUrl");
	private static String httpWXWebUrl = FileProperties.getProperty("httpWXWebUrl");
	private UserService userService;
	private RoleService roleService;
	private TeacherRoleService user2roleService;
	private OrganizationService organizationService;
	
	@Autowired
	private RedisOrgUserDao redisOrgUserDao;
	
	@Autowired
	private RedisClassesDao redisClassesDao;
	
	@Autowired
	private Class2StudentService class2StudentService;
	
	@Autowired
	private ParentService parentService;
	
	@Autowired
	private StudentService studentService;
	
	@Autowired
	private RedisCampusDao redisCampusDao;
	
	@Autowired
	private RedisGradeDao redisGradeDao;
	
	@Autowired
	private ClassesService classesService;

	@Autowired
	private RedisParentDao redisParentDao;
	
	private RedisCardDao redisCardDao;
	
	private RedisStudentDao redisStudentDao;
	
	private RedisOrganizationDao redisOrganizationDao;	
	
	private RedisClassStudentDao redisClassStudentDao;
	
	@Autowired
	private RedisTeacherDao redisTeacherDao;
	
	@Autowired
	private TeacherService teacherService;
	
	@Autowired
	public void setRedisClassStudentDao(RedisClassStudentDao redisClassStudentDao) {
		this.redisClassStudentDao = redisClassStudentDao;
	}

	@Autowired
	public void setRedisOrganizationDao(RedisOrganizationDao redisOrganizationDao) {
		this.redisOrganizationDao = redisOrganizationDao;
	}

	@Autowired
	public void setRedisStudentDao(RedisStudentDao redisStudentDao) {
		this.redisStudentDao = redisStudentDao;
	}

	@Autowired
	public void setRedisCardDao(RedisCardDao redisCardDao) {
		this.redisCardDao = redisCardDao;
	}
	
	@Autowired
	public void setOrganizationService(OrganizationService organizationService) {
		this.organizationService = organizationService;
	}	
	@Autowired
	public void setUserService(UserService userService) {
		this.userService = userService;
	}
	@Autowired
	public void setRoleService(RoleService roleService) {
		this.roleService = roleService;
	}
	@Autowired
	public void setUser2roleService(TeacherRoleService user2roleService) {
		this.user2roleService = user2roleService;
	}

	@RequestMapping(value = "/list")
	public ModelAndView list(@RequestParam(value="card",defaultValue="") String card,
			@RequestParam(value="org_id",defaultValue="") String org_id,
			@RequestParam(value="mobile",defaultValue="") String mobile,
			@RequestParam(value="identity",defaultValue="2") Integer identity,
			@RequestParam(value="start",defaultValue="0") Integer start,
			@RequestParam(value="pageNum",defaultValue="1") Integer pageNum,
			@RequestParam(value="numPerPage",defaultValue="25") Integer numPerPage,
			HttpServletRequest request) {
		ModelAndView mav = new ModelAndView("/user/list");
		Paging<OrgUser> pages = new Paging<OrgUser>();
		pages.setPage(pageNum);
		pages.setStart(start);
		pages.setLimit(numPerPage);		
		//根据家长手机号码查询学生  手机号不为空  
		if(Constant.IDENTITY_STUDENT.equals(identity)&&StringUtils.isNotBlank(mobile)){
			pages = this.userService.getOrgStudentByParentWithPaging("",org_id,mobile,pages);
		}else{
			//根据卡号查询学生
			if(Constant.IDENTITY_STUDENT.equals(identity)&&StringUtils.isNotBlank(card)){
				this.userService.getOrgStudentByCard(card, org_id, pages);
			}else{
				this.userService.getOrgUserWithPaging("",org_id,mobile,identity,pages);
			}
		}
		List<Organization> orgs = this.organizationService.getOrgList();
		mav.addObject("orgs", orgs);
		mav.addObject("users", pages);
		mav.addObject("ctx", request.getContextPath());
		logger.info("orgs :" + orgs);
		logger.info("users :" + pages);
		logger.info("ctx :" + request.getContextPath());
		System.out.println("orgs" + orgs);
		return mav;
	}

	@RequestMapping(value = "/goAdd",method = RequestMethod.GET)
	public ModelAndView goUserAdd(HttpServletRequest request) {
		ModelAndView mav = new ModelAndView("/user/add");
		mav.addObject("ctx", request.getContextPath());
		List<Role> rolse = roleService.getAll();
		List<Organization> orgs = this.organizationService.getOrgList();
		mav.addObject("orgs", orgs);
		mav.addObject("roles", rolse);		
		return mav;
	}
	
	@RequestMapping(value = "/checkName")
	public void checkName(@RequestParam(value="name") String name,@RequestParam(value="uid") String uid,
			HttpServletResponse response) throws IOException {
		JSONObject json = new JSONObject(); 
    	response.setContentType("application/json; charset=UTF-8");
    	PrintWriter responseWriter = response.getWriter();        	
    	try {
    		boolean isUseable = userService.checkName(name,uid);
    		if(isUseable){
    			json.put("code", "200");
    		}else{
    			json.put("code", "201");
    		}
			json.write(responseWriter);
		} catch (JSONException e) {
			logger.error("JSONException====", e);
		}
    	responseWriter.flush();
    	responseWriter.close();
	}
	
	@RequestMapping(value = "/add",method = RequestMethod.POST)
	public @ResponseBody ResultDwz add(@RequestParam(value="login_name") String loginName,@RequestParam(value="login_pass") String loginPass,
			@RequestParam Integer user_idnumber,@RequestParam String user_mobile,
			@RequestParam String roleIds,@RequestParam Integer org_id,
			@RequestParam String user_mail,HttpServletRequest request) {
		OrgUser user = new OrgUser();
		user.setUser_loginname(loginName);	
		user.setOrg_id(org_id);
		String salt = Utils.makecode();
		user.setUser_salt(salt);
		user.setUser_loginpass(Utils.MD5(salt+":"+loginPass));
		user.setUser_id(user_idnumber);
		user.setUser_mobile(user_mobile);
		user.setUser_mail(user_mail);
		user.setInsert_time(new Date());
		user.setIs_del(false);
		user.setIdentity(1);
		this.userService.SaveOrgUser(user);
		String[] ids = roleIds.split(",");
		List<TeacherRole> user2roleList = new ArrayList<TeacherRole>();
		for (String id : ids) {
			TeacherRole u2r = new TeacherRole();
			u2r.setRole_id(Integer.parseInt(id));
			u2r.setTech_id(user.getUser_id());
			u2r.setInsert_time(new Date());
			u2r.setIs_del(false);			
			user2roleList.add(u2r);
		}
		this.user2roleService.saveOrUpdateUser2Role(user2roleList);
		ResultDwz result = new ResultDwz();
		result.setStatusCode("200");
		result.setMessage("操作成功");
		result.setNavTabId("user");
		result.setCallbackType("closeCurrent");
		return result;
	}

	@RequestMapping(value = "/goEdit")
	public ModelAndView goUserEdit(@RequestParam(value = "uid") Integer uid, @RequestParam(value = "org_id") Integer org_id) {
		ModelAndView mav = new ModelAndView("/user/edit");
		OrgUser ou = userService.getOrgUserById(uid);
		mav.addObject("user", ou);
		
		List<Organization> orgs = this.organizationService.getOrgList();
		mav.addObject("orgs", orgs);
		mav.addObject("sex", "");	
		if (ou != null && ou.getIdentity() != null) {
			Integer identity = ou.getIdentity();
			if (identity.intValue() == 1) {
				Teacher teacher = userService.getTeacherByUserId(uid);
				mav.addObject("teacher", teacher);
				mav.addObject("roles", roleService.getRoleListByOrgID(org_id));
				List<Role> roles = roleService.getByUserId(uid, org_id);
				mav.addObject("ownRoles", roles);
				mav.addObject("sex", teacher.getSex());
			} else if (identity.intValue() == 0) {
				Parent parent = parentService.getParentByUserID(ou.getOrg_id().intValue(), ou.getUser_id().intValue());
				mav.addObject("parent", parent);
				mav.addObject("sex", parent.getSex());
			} else if (identity.intValue() == 2) {
				Student student = studentService.getStudentByOrgUser(ou);
				List<Classes> classes = classesService.getClassesOfOrg(org_id);
				ClassesSorter cs = new ClassesSorter();  
		        Collections.sort(classes,cs); 
				
		    	List<Campus> campus = redisCampusDao.getCampusByOrgId(org_id.toString());
		    	CompusToSortComparator cmc = new CompusToSortComparator();  
		        Collections.sort(campus,cmc); 
				
				List<Grade> grades = redisGradeDao.getGrades(org_id);
				GradeSorter mc = new GradeSorter();  
				Collections.sort(grades,mc); 
				
				List<Parent> parents = parentService.getByStudId(student.getStud_id().toString());
				
				JSONObject obj = new JSONObject();
				for (Classes c : classes) {
					if(c.getClas_id().equals(student.getClas_id())){
						mav.addObject("cam_id", c.getCam_id());
					}
					if(obj.has(c.getCam_id().toString())){
						if(obj.getJSONObject(c.getCam_id().toString()).has(c.getGrade_id().toString())){
							if(obj.getJSONObject(c.getCam_id().toString()).getJSONObject(c.getGrade_id().toString()).has(c.getClas_id().toString())){
								continue;
							}else{
								obj.getJSONObject(c.getCam_id().toString()).getJSONObject(c.getGrade_id().toString()).put(c.getClas_id().toString(), c.getClas_name());
							}
						}else{
							JSONObject gra = new JSONObject();
							gra.put("name", c.getGrade_name());
							gra.put(c.getClas_id().toString(), c.getClas_name());
							obj.getJSONObject(c.getCam_id().toString()).put(c.getGrade_id().toString(), gra);
						}
					}else{
						JSONObject cam = new JSONObject();
						JSONObject gra = new JSONObject();
						gra.put("name", c.getGrade_name());
						gra.put(c.getClas_id().toString(), c.getClas_name());
						cam.put("name", c.getCam_name());
						cam.put(c.getGrade_id().toString(), gra);
						obj.put(c.getCam_id().toString(), cam);
					}
				}
				//校区下年级班级
				mav.addObject("obj", obj);
				//家长列表
				mav.addObject("parents", parents);
				//学生信息
				List<Card> cards = this.studentService.getCardsByStudentId(student.getStud_id());
				student.setCards(cards);
				mav.addObject("student", student);		
				mav.addObject("sex", student.getSex());		
			}
		}
		return mav;
	}
	
	@RequestMapping(value = "/edit",method = RequestMethod.POST)
	public @ResponseBody
	ResultDwz edit(@RequestParam Integer user_id, @RequestParam String loginname, @RequestParam String user_loginpass, @RequestParam String user_idnumber,
			@RequestParam String user_mail, @RequestParam(defaultValue="") String card_no,HttpServletRequest request) {
		OrgUser user = this.userService.getOrgUserById(user_id);
		user.setUser_idnumber(Utils.getValue(user_idnumber));
		user.setUser_mail(Utils.getValue(user_mail));
		String mobile = request.getParameter("user_mobile");
		if(StringUtils.isNotBlank(mobile)){
			List<OrgUser> uList = userService.getOrgUserByMobile(mobile);
			if(null!=uList&&uList.size()>0){
				for (OrgUser orgUser : uList) {
					if(!orgUser.getUser_id().equals(user.getUser_id())&&orgUser.getIdentity().equals(user.getIdentity())&&orgUser.getOrg_id().equals(user.getOrg_id())){
						ResultDwz result = new ResultDwz();
						result.setStatusCode("300");
						result.setMessage("手机号已存在");
						result.setNavTabId("user");
						return result;
					}
				}
			}
		}
		if(user.getUser_loginname().equals(user.getUser_mobile())&&StringUtils.isNotBlank(mobile)){
			user.setUser_loginname(mobile);
		}
		user.setUser_mobile(mobile);
		
		if (StringUtils.isNotBlank(user_loginpass)) {
			// 填写了密码则进行更改
			String user_salt = user.getUser_salt();
			if (StringUtils.isNotBlank(user_salt)) {
				user.setUser_loginpass(Utils.MD5(user_salt + ":" + user_loginpass));
			} else {
				// 密码盐为空
				user_salt = Utils.makecode();// ((Math.random()*(9999-1000+1))+1000)+"";
				user.setUser_salt(user_salt);
				user.setUser_loginpass(Utils.MD5(user_salt + ":" + user_loginpass));
			}
		}
		
		String user_number = request.getParameter("user_number");
		if (StringUtils.isNotBlank(user_number)) {
			user.setUser_number(user_number);
		}
		
		this.userService.updateOrgUser(user);
		
		if (user.getIdentity().intValue() == 1) {
			Teacher t = userService.getTeacherByUserId(user_id);
			
			String tech_name = request.getParameter("tech_name");
			if (StringUtils.isNotBlank(tech_name)) {
				t.setTech_name(tech_name.trim());
				userService.updateTeacher(t);
			}
			
			// 不在平台侧编辑权限了20170713
//			String[] roleIds = request.getParameterValues("roleIds");
//			List<TeacherRole> user2roleList = new ArrayList<TeacherRole>();
//			if (roleIds != null && roleIds.length > 0) {
//				for (String id : roleIds) {
//					TeacherRole u2r = this.user2roleService.getTeachRole(Integer.parseInt(id), t.getUser_id());
//					if (u2r == null) {
//						u2r = new TeacherRole();
//						u2r.setRole_id(Integer.parseInt(id));
//						u2r.setTech_id(t.getTech_id());
//						u2r.setInsert_time(new Date());
//						u2r.setUpdate_time(new Date());
//						u2r.setIs_del(false);
//					}
//					user2roleList.add(u2r);
//				}
//				this.user2roleService.saveOrUpdateUser2Role(user2roleList);
//			} else {
//				user2roleService.deleteByUserId(t.getUser_id());
//			}
		} else if (user.getIdentity().intValue() == 0) {
			Parent p = parentService.getParentByUserID(user.getOrg_id().intValue(), user.getUser_id().intValue());
			String parent_name = request.getParameter("parent_name");
			if (StringUtils.isNotBlank(parent_name)) {
				p.setParent_name(parent_name.trim());
				parentService.updateParent(p);
			}
		} else if (user.getIdentity().intValue() == 2) {
			String parentsInfo = request.getParameter("parentsInfo");
			String delparentId = request.getParameter("delparentId");
			String classes = request.getParameter("classes");
			String stud_name = request.getParameter("stud_name");
			String gender = request.getParameter("gender");
			String[] ids = delparentId.split("\\,");
			
			Student student = studentService.getStudentByUserID(user.getOrg_id().intValue(), user.getUser_id().intValue());	
			
			List<Card> removes = new ArrayList<Card>();
			String[] nos = card_no.split("\\,");
			
			String message = this.checkCards(nos,student.getStud_id());
			if(message!=null){
				ResultDwz result = new ResultDwz();
				result.setStatusCode("300");
				result.setMessage(message);
				result.setNavTabId("user");
				return result;
			}

			if(null==student||null==student.getStud_id()){
				ResultDwz result = new ResultDwz();
				result.setStatusCode("300");
				result.setMessage("找不到该学生");
				result.setNavTabId("user");
				return result;
			}
			student.setStud_name(stud_name);
			student.setSex(Byte.parseByte(gender));
			this.studentService.add(student);
			
			Set<String> noids = new HashSet<String>(Arrays.asList(nos));
			Set<String> noadds = new HashSet<String>();
			
			List<Card> cards = this.studentService.getCardsByStudentId(student.getStud_id());	
			if(nos.length>0){
				if(cards!=null){
					for (Card card : cards) {
						if(noids.contains(card.getCard_no())){
							noadds.add(card.getCard_no());
						}else{
							removes.add(card);
						}
					}
				}
			}else{
				removes = cards;
			}
			if(noadds.size()>0){
				noids.removeAll(noadds);
			}
			for (String card_noid : noids) {
				if(!StringUtils.isBlank(card_noid)){
					Card card = this.studentService.getCardById(card_noid);					
					if(card==null){
						card = new Card();
						card.setCard_no(card_noid);
						card.setInsert_time(new Date());
					}
					card.setStud_id(student.getStud_id());
					card.setUpdate_time(new Date());
					this.studentService.saveCard(card);
				}
			}
			for (Card card : removes) {
				card.setStud_id(null);
				this.studentService.saveCard(card);
			}
			
			//更新班级
			String[] cids = classes.split("\\,");
			for (int i = 0; i < cids.length; i++) {
				if(StringUtils.isNotBlank(cids[i])){
					Clas2Student cs = this.class2StudentService.getClas2Student(student.getStud_id(), Integer.parseInt(cids[i].toString()));
					if(cs==null||null==cs.getClas2stud_id()){					
						class2StudentService.deleteOld(student.getStud_id(),student.getOrg_id());
						cs = new Clas2Student();
						cs.setStud_id(student.getStud_id());
						cs.setClas_id(Integer.parseInt(cids[i]));
						cs.setInsert_time(new Date());
						cs.setIs_del(false);
						Classes cla = redisClassesDao.getClassesById(cids[i].toString(), null);
						cs.setStud_name(student.getStud_name());
						cs.setUser_id(student.getUser_id());
						cs.setClas_name(cla.getClas_name());
						cs.setOrg_id(student.getOrg_id());
						this.class2StudentService.add(cs);
					} else {
						Classes cla = redisClassesDao.getClassesById(cids[i].toString(), null);
						cs.setStud_name(student.getStud_name());
						cs.setClas_name(cla.getClas_name());
						this.class2StudentService.add(cs);
					}
				}
			}	
			
			//更新家长
			JSONArray parents = new JSONArray(parentsInfo);
			for (int i = 0; i < parents.length(); i++) {
				JSONObject parent = parents.getJSONObject(i);
				if(StringUtils.isNotBlank(parent.getString("id"))){
					//家长机构用户
//					if(StringUtils.isNotBlank(parent.getString("mobile"))){
//						String newMobile = parent.getString("mobile");
//						OrgUser porguser = redisOrgUserDao.getUserByLoginNameWX(parent.getString("oldmobile"),user.getOrg_id(),Constant.IDENTITY_PARENT.toString());
//						if(null==porguser||null==porguser.getUser_id()){
//							porguser = new OrgUser();
//						}
//						porguser.setIdentity(Constant.IDENTITY_PARENT);
//						porguser.setOrg_id(user.getOrg_id());
//						porguser.setIs_current(1);
//						if(StringUtils.isNotBlank(porguser.getUser_mobile())&&porguser.getUser_mobile().equals(porguser.getUser_loginname())&&StringUtils.isNotBlank(newMobile)){
//							porguser.setUser_loginname(newMobile);
//						}
//						porguser.setUser_mobile(newMobile);
//						porguser.setUser_mobile_type(Byte.parseByte(parent.getString("mobiletype")));
//						String psalt = Utils.makecode();
//						porguser.setUser_salt(psalt);
//						porguser.setUser_loginpass(Utils.MD5(psalt+":"+"111111"));
//						porguser.setLoginnum(0);
//						porguser.setStatus((byte)0);
//						porguser.setInsert_time(new Date());
//						porguser.setIs_del(false);		
//						this.userService.SaveOrgUser(porguser);
//						//家长用户
//						Parent existParent = this.parentService.getByMobile(newMobile,user.getOrg_id());
//						if(null==existParent||null==existParent.getParent_id()){
//							existParent = new Parent();
//							existParent.setInsert_time(new Date());
//						}
//						existParent.setOrg_id(user.getOrg_id());
//						existParent.setUser_id(porguser.getUser_id());
//						existParent.setParent_name(parent.getString("name"));
//						existParent.setSex(Byte.parseByte(parent.getString("gender")));
//						existParent.setUpdate_time(new Date());
//						existParent.setIs_del(false);
//						this.parentService.addParent(existParent);
//						//学生家长关系
//						StudentParent sp = this.parentService.getStudentParent(student.getStud_id(), existParent.getParent_id());
//						if(null==sp){
//							sp = new StudentParent();
//						}
//						sp.setStud_id(student.getStud_id());
//						sp.setParent_id(existParent.getParent_id());
//						sp.setRelation(Integer.parseInt(parent.getString("relation")));
//						sp.setInsert_time(new Date());
//						sp.setIs_del(false);
//						this.parentService.addStudentParent(sp);
//					}
					
					
					if(StringUtils.isNotBlank(parent.getString("mobile"))){
						String newMobile = parent.getString("mobile");
						//如果手机号码进行改变
						if(!parent.getString("mobile").equals(parent.getString("oldmobile"))){
							//查询新moblie用户
							OrgUser porguser = redisOrgUserDao.getUserByLoginNameWX(newMobile,user.getOrg_id(),Constant.IDENTITY_PARENT.toString());
							//新用户不存在 
							if(null==porguser||null==porguser.getUser_id()){
								//创建 用户
								porguser = new OrgUser();
								porguser.setIdentity(Constant.IDENTITY_PARENT);
								porguser.setOrg_id(user.getOrg_id());
								porguser.setIs_current(1);
								//if(StringUtils.isNotBlank(porguser.getUser_mobile())&&porguser.getUser_mobile().equals(porguser.getUser_loginname())&&StringUtils.isNotBlank(newMobile)){
									porguser.setUser_loginname(newMobile);
								//}
								porguser.setUser_mobile(newMobile);
								porguser.setUser_mobile_type(Byte.parseByte(parent.getString("mobiletype")));
								String psalt = Utils.makecode();
								porguser.setUser_salt(psalt);
								porguser.setUser_loginpass(Utils.MD5(psalt+":"+"111111"));
								porguser.setLoginnum(0);
								porguser.setStatus((byte)0);
								porguser.setInsert_time(new Date());
								porguser.setIs_del(false);		
								this.userService.SaveOrgUser(porguser);
								
								//创建家长
								Parent existParent = new Parent();
								existParent.setInsert_time(new Date());
								existParent.setOrg_id(user.getOrg_id());
								existParent.setUser_id(porguser.getUser_id());
								existParent.setParent_name(parent.getString("name"));
								existParent.setSex(Byte.parseByte(parent.getString("gender")));
								existParent.setUpdate_time(new Date());
								existParent.setIs_del(false);
								this.parentService.addParent(existParent);
								//创建新家长与学生关系
								StudentParent sp = new StudentParent();
								sp.setStud_id(student.getStud_id());
								sp.setParent_id(existParent.getParent_id());
								sp.setRelation(Integer.parseInt(parent.getString("relation")));
								sp.setInsert_time(new Date());
								sp.setIs_del(false);
								sp.setOrg_id(user.getOrg_id());
								this.parentService.addStudentParent(sp);
							}else{//已有用户
								//查询家长
								Parent newparent = redisParentDao.getParentsByUserId(porguser.getUser_id().toString());
								//家长不存在 创建
								if(null==newparent||null==newparent.getUser_id()){
									newparent = new Parent();
									newparent.setInsert_time(new Date());
									newparent.setOrg_id(user.getOrg_id());
									newparent.setUser_id(porguser.getUser_id());
									newparent.setParent_name(parent.getString("name"));
									newparent.setSex(Byte.parseByte(parent.getString("gender")));
									newparent.setUpdate_time(new Date());
									newparent.setIs_del(false);
									this.parentService.addParent(newparent);
								}else{//家长存在 修改家长信息
									newparent.setParent_name(parent.getString("name"));
									newparent.setSex(Byte.parseByte(parent.getString("gender")));
									newparent.setUpdate_time(new Date());
									this.parentService.updateParent(newparent);
								}
								
								//创建家长与学生关系
								StudentParent sp = this.parentService.getStudentParent(student.getStud_id(), newparent.getParent_id());
								if(null==sp){
									sp = new StudentParent();
								}
								sp.setStud_id(student.getStud_id());
								sp.setParent_id(newparent.getParent_id());
								sp.setRelation(Integer.parseInt(parent.getString("relation")));
								sp.setInsert_time(new Date());
								sp.setIs_del(false);
								this.parentService.addStudentParent(sp);
								
							}
							
							//删除老家长与学生关系
							OrgUser olduser = redisOrgUserDao.getUserByLoginNameWX(parent.getString("oldmobile"),user.getOrg_id(),Constant.IDENTITY_PARENT.toString());
							Parent oldparent = redisParentDao.getParentsByUserId(olduser.getUser_id().toString());
							
							StudentParent sp = this.parentService.getStudentParent(student.getStud_id(), oldparent.getParent_id());
							sp.setDel_time(new Date());
							sp.setIs_del(true);
							sp.setOrg_id(user.getOrg_id());
							this.parentService.updateStudentParent(sp);
						}else{//如果手机号码没有进行改变
							//修改家长信息
							Parent oldparent = redisParentDao.getByParentId(parent.getString("id"));
							oldparent.setParent_name(parent.getString("name"));
							oldparent.setSex(Byte.parseByte(parent.getString("gender")));
							oldparent.setUpdate_time(new Date());
							this.parentService.updateParent(oldparent);
							
							OrgUser olduser = redisOrgUserDao.getUserByLoginNameWX(parent.getString("mobile"),oldparent.getOrg_id(),Constant.IDENTITY_PARENT.toString());
							olduser.setUser_mobile_type(Byte.parseByte(parent.getString("mobiletype")));
							olduser.setUpdate_time(new Date());
							this.userService.SaveOrgUser(olduser);
							
							//修改家长与学生关系
							StudentParent sp = this.parentService.getStudentParent(student.getStud_id(), oldparent.getParent_id());
							if(null==sp){
								sp = new StudentParent();
							}
							sp.setStud_id(student.getStud_id());
							sp.setParent_id(oldparent.getParent_id());
							sp.setRelation(Integer.parseInt(parent.getString("relation")));
							sp.setInsert_time(new Date());
							sp.setIs_del(false);
							this.parentService.updateStudentParent(sp);
						}
						
					}
					
					
					
					
					
				}else{
					// 人员不存在，进行新建
					OrgUser orgUser = new OrgUser();
					orgUser.setIdentity(Constant.IDENTITY_PARENT);
					orgUser.setOrg_id(student.getOrg_id());
					orgUser.setIs_current(1);
					orgUser.setUser_mobile(parent.getString("mobile"));
					orgUser.setUser_mobile_type(Byte.parseByte(parent.getString("mobiletype")));
					orgUser.setUser_loginname(parent.getString("mobile"));
					String psalt = Utils.makecode();
					orgUser.setUser_salt(psalt);
					orgUser.setUser_loginpass(Utils.MD5(psalt + ":" + "111111"));
					orgUser.setLoginnum(0);
					orgUser.setStatus((byte) 0);
					orgUser.setInsert_time(new Date());
					orgUser.setIs_del(false);
					userService.SaveOrgUser(orgUser);

					// 新建家长记录
					Parent newParent = new Parent();
					newParent.setOrg_id(student.getOrg_id());
					newParent.setUser_id(orgUser.getUser_id().intValue());
					newParent.setParent_name(parent.getString("name"));
					newParent.setSex(Byte.parseByte(parent.getString("gender")));
					newParent.setInsert_time(new Date());
					newParent.setIs_del(false);
					parentService.addParent(newParent);

					// 最后建立家长与学生的关联
					StudentParent sp = new StudentParent();
					sp.setStud_id(student.getStud_id());
					sp.setParent_id(newParent.getParent_id());
					sp.setRelation(Integer.parseInt(parent.getString("relation")));
					sp.setInsert_time(new Date());
					sp.setIs_del(false);
					this.parentService.addStudentParent(sp);
				}
			}
			
			for (String id : ids) {
				if(StringUtils.isNotBlank(id)){
					StudentParent sp = this.parentService.getStudentParent(student.getStud_id(), Integer.parseInt(id));
					studentService.deleteStudent2Parent(sp.getStud2parent_id());
				}
			}
		}
		
		ResultDwz result = new ResultDwz();
		result.setStatusCode("200");
		result.setMessage("操作成功");
		result.setNavTabId("user");
		result.setCallbackType("closeCurrent");
		return result;
	}	
	
	private String checkCards(String[] nos, Integer studId) {
		// TODO Auto-generated method stub
		String message = null;
		for (String noid : nos) {			
			Integer stud_id = this.redisCardDao.getStudId(noid);
			if(stud_id!=null&&!stud_id.equals(studId)){
				if(stud_id!=null){
					Student student = this.redisStudentDao.getByStudentId(stud_id);
					if(student!=null&&student.getStud_id()!=null){
						Map<String, Clas2Student> csmap = this.redisClassStudentDao.getByStud_id(stud_id.toString());
						boolean is_graduated = true;
						for (String clsid : csmap.keySet()) {
							Classes cls = this.redisClassesDao.getClassesById(clsid, null);
							if(cls.getIs_graduated().equals(0)){
								is_graduated = false;
								break;
							}
						}
						if(!is_graduated){
							Organization org = this.redisOrganizationDao.getByOrgId(student.getOrg_id().toString(), null);
							if(message==null){
								message = "";
							}
							message += "卡号:"+noid+"已经绑定在"+org.getOrg_name_cn()+" "+student.getStud_name() +" 名下;\r";
						}
					}
				}
			}			
		}
		return message;
	}

	@RequestMapping(value = "/delete",method = RequestMethod.POST)
	public @ResponseBody ResultDwz delete(@RequestParam(value="uid") Integer ssoId,@RequestParam(value="identity") int identity) {
//		OrgUser ou = userService.getOrgUserById(ssoId);
//		if (ou != null) {
//			ou.setDel_time(new Date());
//			ou.setUpdate_time(new Date());
//			ou.setIs_del(true);
//			userService.updateOrgUser(ou);
//			
//			if (ou.getIdentity().intValue() == 1) {
//				Teacher t = userService.getTeacherByUserId(ou.getUser_id());
//				if (t != null) {
//					user2roleService.deleteByUserId(t.getUser_id());
//				}
//			}
//		}
		if(identity==Constant.IDENTITY_TEACHER){
			Teacher teacher = redisTeacherDao.getByUserId(ssoId+"");
			teacherService.delete(teacher.getTech_id());
		}else if(identity==Constant.IDENTITY_STUDENT){
			Student student = redisStudentDao.getByUserId(ssoId+"");
			studentService.delete(student.getStud_id());
		}

		ResultDwz result = new ResultDwz();
		result.setStatusCode("200");
		result.setMessage("删除成功");
		result.setNavTabId("user");
		//result.setCallbackType("closeCurrent");
		return result;
	}
	
	@RequestMapping(value = "/expertExcel")
	public void expertExcel(HttpServletRequest request,HttpServletResponse response){
		List<OrgUser> ls = this.userService.getOrgUserList();
		LinkedHashMap<String,String> map = new LinkedHashMap<String,String>();
		map.put("user_loginname", "登录名");
		map.put("user_mail", "用户邮箱");
		map.put("user_mobile", "用户手机");		
		ExcelUtil.listToExcel(ls, map, "机构用户", response);
	}
	
	@RequestMapping(value = "/importExcel",method = RequestMethod.GET)
	public ModelAndView importExcel() {
		ModelAndView mav = new ModelAndView("/user/importExcel");
		List<Organization> orgs = this.organizationService.getOrgList();
		mav.addObject("orgs", orgs);
		return mav;
	}
	
	@RequestMapping(value = "/importExcel",method = RequestMethod.POST)
	public @ResponseBody ResultDwz importExcelSave(@RequestParam Integer org_id,HttpServletRequest request,HttpServletResponse response) throws Exception {
		ResultDwz result = new ResultDwz();
		MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
		MultipartFile file = multipartRequest.getFile("file_stu");
		if (file==null||file.isEmpty()) {
			result.setMessage("导入失败");
			return result;
		} 
		 InputStream fin = file.getInputStream(); 
		 LinkedHashMap<String,String> map = new LinkedHashMap<String,String>();
			map.put("登录名","user_loginname");
			map.put("用户邮箱","user_mail");
			map.put("用户手机","user_mobile");
		List<OrgUser> ls = ExcelUtil.excelToList(fin,"机构用户",OrgUser.class,map);
		List<OrgUser> errors = new ArrayList<OrgUser>();
		for (OrgUser orgUser : ls) {
			OrgUser user = this.userService.getOrgUserByLoginName(orgUser.getUser_loginname(), org_id);
			if(user!=null){
				errors.add(user);
			}else{
				orgUser.setInsert_time(new Date());
				orgUser.setIs_del(false);
				orgUser.setOrg_id(org_id);
				orgUser.setStatus((byte)0);
				String salt = Utils.makecode();
				orgUser.setUser_salt(salt);
				orgUser.setUser_loginpass(Utils.MD5(salt+":"+"111111"));
				this.userService.SaveOrgUser(orgUser);
			}			
		}		
		result.setStatusCode("200");
		result.setMessage("导入成功，有"+errors.size()+"条失败");
		result.setNavTabId("user");
		result.setCallbackType("closeCurrent");
		return result;
	}
	
	@RequestMapping(value = "/editParent",method = RequestMethod.GET)
	public ModelAndView editParent(@RequestParam(value="uid") Integer uid,@RequestParam(value="org_id") Integer org_id) {
		ModelAndView mav = new ModelAndView("/user/editParent");
		mav.addObject("user", this.userService.getOrgUserById(uid));
		mav.addObject("roles", roleService.getAll());
		List<Role> roles = roleService.getByUserId(uid,org_id);
		mav.addObject("ownRoles", roles);
		List<Organization> orgs = this.organizationService.getOrgList();
		mav.addObject("orgs", orgs);
		return mav;
	}
	@RequestMapping(value = "/editTeacher",method = RequestMethod.GET)
	public ModelAndView editTeacher(@RequestParam(value="uid") Integer uid) {
		ModelAndView mav = new ModelAndView("/user/editTeacher");
		mav.addObject("user", this.userService.getOrgUserById(uid));
		mav.addObject("teacher", this.userService.getTeacherByUserId(uid));				
		return mav;
	}
	@RequestMapping(value = "/editStudent",method = RequestMethod.GET)
	public ModelAndView editStudent(@RequestParam(value="uid") Integer uid,@RequestParam(value="org_id") Integer org_id) {
		ModelAndView mav = new ModelAndView("/user/editStudent");
		mav.addObject("user", this.userService.getOrgUserById(uid));
		mav.addObject("roles", roleService.getAll());
		List<Role> roles = roleService.getByUserId(uid,org_id);
		mav.addObject("ownRoles", roles);
		List<Organization> orgs = this.organizationService.getOrgList();
		mav.addObject("orgs", orgs);
		return mav;
	}

	@RequestMapping(value = "/goOrg",method = RequestMethod.POST)
	public void goOrg(@RequestParam Integer org_id,
			HttpServletRequest request,HttpServletResponse response) throws IOException {
    	response.setContentType("application/json; charset=UTF-8");
    	OrgUser managerUser = userService.getUserForManager(org_id);
    	String login_name = managerUser.getUser_loginname();
    	String login_pass = managerUser.getUser_idnumber();
    	response.sendRedirect(httpBaseUIUrl+"user/s.htm?u="+login_name+"&p="+login_pass+"&o="+org_id);
    	//response.sendRedirect("https://t.shijiwxy.5tree.cn/baseui/user/s.htm?u="+login_name+"&p="+login_pass+"&o="+org_id);
	}
	
	
	@RequestMapping(value = "/goWX", method = RequestMethod.POST)
	public void goWX(@RequestParam Integer org_id, HttpServletRequest request, HttpServletResponse response) throws IOException {
		response.setContentType("application/json; charset=UTF-8");
		OrgUser managerUser = userService.getUserForManager(org_id);
		String login_name = managerUser.getUser_loginname();
		String login_pass = managerUser.getUser_idnumber();
		response.sendRedirect(httpWXWebUrl + "portal/login/loginFromESB.htm?u=" + login_name + "&p=" + login_pass + "&o=" + org_id);
	}

	
}
