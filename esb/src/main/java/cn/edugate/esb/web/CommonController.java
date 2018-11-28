package cn.edugate.esb.web;

import io.swagger.client.model.ModifyGroup;
import io.swagger.client.model.Nickname;
import io.swagger.client.model.RegisterUsers;
import io.swagger.client.model.User;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.integration.Message;
import org.springframework.integration.MessageChannel;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import cn.edugate.esb.EduConfig;
import cn.edugate.esb.Result;
import cn.edugate.esb.eduEnum.EnumMessage;
import cn.edugate.esb.entity.App;
import cn.edugate.esb.entity.Department;
import cn.edugate.esb.entity.Grade;
import cn.edugate.esb.entity.Group;
import cn.edugate.esb.entity.GroupMember;
import cn.edugate.esb.entity.OrgUser;
import cn.edugate.esb.entity.Organization;
import cn.edugate.esb.entity.Parent;
import cn.edugate.esb.entity.Teacher;
import cn.edugate.esb.entity.UcUser;
import cn.edugate.esb.entity.UcuserOrguser;
import cn.edugate.esb.entity.UserAccount;
import cn.edugate.esb.exception.EnumException;
import cn.edugate.esb.exception.EsbException;
import cn.edugate.esb.im.api.ChatGroupAPI;
import cn.edugate.esb.im.api.IMUserAPI;
import cn.edugate.esb.params.EduJsonFilter;
import cn.edugate.esb.params.EduJsonFilters;
import cn.edugate.esb.params.filter.ParentFilter;
import cn.edugate.esb.params.filter.TeacherFilter;
import cn.edugate.esb.pojo.Appservice;
import cn.edugate.esb.pojo.Login;
import cn.edugate.esb.redis.dao.RedisAppDao;
import cn.edugate.esb.redis.dao.RedisDepartmentDao;
import cn.edugate.esb.redis.dao.RedisGroupDao;
import cn.edugate.esb.redis.dao.RedisGroupMemberDao;
import cn.edugate.esb.redis.dao.RedisLoginDao;
import cn.edugate.esb.redis.dao.RedisOrgUserDao;
import cn.edugate.esb.redis.dao.RedisOrganizationDao;
import cn.edugate.esb.redis.dao.RedisStudentParentDao;
import cn.edugate.esb.redis.dao.RedisTeacherDao;
import cn.edugate.esb.redis.dao.RedisUcUserDao;
import cn.edugate.esb.redis.dao.RedisUcUserOrguserDao;
import cn.edugate.esb.service.DepartmentService;
import cn.edugate.esb.service.GradeService;
import cn.edugate.esb.service.GroupMemberService;
import cn.edugate.esb.service.GroupService;
import cn.edugate.esb.service.TeacherService;
import cn.edugate.esb.service.UserService;
import cn.edugate.esb.util.CookieUtils;
import cn.edugate.esb.util.HttpRequest;
import cn.edugate.esb.util.Util;
import cn.edugate.esb.util.Utils;

@Controller
public class CommonController {
	static Logger logger=LoggerFactory.getLogger(CommonController.class);
	@Autowired
	@Qualifier("fanoutChannel")
	MessageChannel messageChannel;
	
	@Autowired
	@Qualifier("p2p-pollable-channel")
	MessageChannel channel;
	
	@Autowired
	@Qualifier("toRabbit")
	MessageChannel channel2;
	
	private UserService userService;	
	private RedisGroupMemberDao redisGroupMemberDao;
	private RedisGroupDao redisGroupDao; 	
	private ChatGroupAPI chatGroupAPI;
	private TeacherService teacherService;	
	private GroupService groupService;
	private DepartmentService departmentService;
	private IMUserAPI iMUserAPI;
	private RedisTeacherDao redisTeacherDao;
	private RedisDepartmentDao redisDepartmentDao;
	private Util util;
	private EduConfig eduConfig;
	private RedisOrgUserDao redisOrgUserDao;
	private RedisUcUserDao redisUcUserDao;
	private RedisUcUserOrguserDao redisUcUserOrguserDao;
	private RedisLoginDao redisLoginDao;
	private RedisStudentParentDao redisStudentParentDao;
	private RedisAppDao redisAppDao;
	private RedisOrganizationDao redisOrganizationDao;
	private GradeService gradeService;
	
	@Autowired
	public void setGradeService(GradeService gradeService) {
		this.gradeService = gradeService;
	}

	//	private RedisMenuDao redisMenuDao;
//	private RoleService roleService;
//	@Autowired
//	public void setRedisMenuDao(RedisMenuDao redisMenuDao) {
//		this.redisMenuDao = redisMenuDao;
//	}
//	@Autowired
//	public void setRoleService(RoleService roleService) {
//		this.roleService = roleService;
//	}
	@Autowired
	public void setRedisOrganizationDao(RedisOrganizationDao redisOrganizationDao) {
		this.redisOrganizationDao = redisOrganizationDao;
	}

	@Autowired
	public void setUtil(Util util) {
		this.util = util;
	}

	@Autowired
	public void setRedisAppDao(RedisAppDao redisAppDao) {
		this.redisAppDao = redisAppDao;
	}

	@Autowired
	public void setRedisDepartmentDao(RedisDepartmentDao redisDepartmentDao) {
		this.redisDepartmentDao = redisDepartmentDao;
	}
	
	@Autowired
	public void setRedisTeacherDao(RedisTeacherDao redisTeacherDao) {
		this.redisTeacherDao = redisTeacherDao;
	}
	@Autowired
	public void setiMUserAPI(IMUserAPI iMUserAPI) {
		this.iMUserAPI = iMUserAPI;
	}
	@Autowired
	public void setDepartmentService(DepartmentService departmentService) {
		this.departmentService = departmentService;
	}

	@Autowired
	public void setGroupService(GroupService groupService) {
		this.groupService = groupService;
	}
	
	@Autowired
	public void setChatGroupAPI(ChatGroupAPI chatGroupAPI) {
		this.chatGroupAPI = chatGroupAPI;
	}

	@Autowired
	public void setTeacherService(TeacherService teacherService) {
		this.teacherService = teacherService;
	}

	@Autowired
	public void setRedisGroupDao(RedisGroupDao redisGroupDao) {
		this.redisGroupDao = redisGroupDao;
	}

	@Autowired
	public void setRedisGroupMemberDao(RedisGroupMemberDao redisGroupMemberDao) {
		this.redisGroupMemberDao = redisGroupMemberDao;
	}
	private GroupMemberService groupMemberService;
	@Autowired
	public void setGroupMemberService(GroupMemberService groupMemberService) {
		this.groupMemberService = groupMemberService;
	}
	
	@Autowired
	public void setUserService(UserService userService) {
		this.userService = userService;
	}
	@Autowired
	public void setEduConfig(EduConfig eduConfig) {
		this.eduConfig = eduConfig;
	}
	@Autowired
	public void setRedisLoginDao(RedisLoginDao redisLoginDao) {
		this.redisLoginDao = redisLoginDao;
	}
	@Autowired
	public void setRedisUcUserOrguserDao(RedisUcUserOrguserDao redisUcUserOrguserDao) {
		this.redisUcUserOrguserDao = redisUcUserOrguserDao;
	}
	@Autowired
	public void setRedisUcUserDao(RedisUcUserDao redisUcUserDao) {
		this.redisUcUserDao = redisUcUserDao;
	}
	@Autowired
	public void setRedisOrgUserDao(RedisOrgUserDao redisOrgUserDao) {
		this.redisOrgUserDao = redisOrgUserDao;
	}
	@Autowired
	public void setRedisStudentParentDao(RedisStudentParentDao redisStudentParentDao) {
		this.redisStudentParentDao = redisStudentParentDao;
	}
	
//	@RequestMapping(value = "/login",produces="text/html;charset=UTF-8")
//	public ModelAndView login(HttpServletRequest request, HttpServletResponse response)  {
//		ModelAndView mav = new ModelAndView("/login");
////    	response.setContentType("text/html;charset=UTF-8");
//		return mav;
//	}
	@RequestMapping(value = "/manage")
	public ModelAndView mindex() {
		ModelAndView mav = new ModelAndView("/index");
		return mav;
	}
	
	@RequestMapping(value = "/testmq", method = RequestMethod.GET)
	@ResponseBody
	public Result<String> testmq(HttpServletResponse response) throws IOException {
		response.setHeader("Access-Control-Allow-Origin", "*");
		Result<String> result = new Result<String>();
		String request = Utils.streamToString(getClass().getResourceAsStream(
				"/data/payload.xml"));
		Message<String> message = MessageBuilder.withPayload(request)
				.build();
		//或者通过GenericMessage 生成msg
//		GenericMessage<String> msg = new GenericMessage<String>(request);
//		messageChannel.send(msg);
		messageChannel.send(message);
		result.setMessage("发送成功成功");
		result.setData(request);
		result.setSuccess(true);
		return result;
	}
	
	@RequestMapping(value = "/testmq1", method = RequestMethod.GET)
	@ResponseBody
	public Result<String> testmq1(HttpServletResponse response) throws IOException {
		response.setHeader("Access-Control-Allow-Origin", "*");
		Result<String> result = new Result<String>();
		String request = Utils.streamToString(getClass().getResourceAsStream(
				"/data/payload.xml"));

		Message<String> message = MessageBuilder.withPayload(request)
				.build();
		//或者通过GenericMessage 生成msg
//		GenericMessage<String> msg = new GenericMessage<String>(request);
//		messageChannel.send(msg);
		channel.send(message);
		result.setMessage("发送成功成功");
		result.setData(request);
		result.setSuccess(true);
		return result;
	}
	
	@RequestMapping(value = "/testmq2", method = RequestMethod.GET)
	@ResponseBody
	public Result<String> testmq2(HttpServletResponse response) throws IOException {
		response.setHeader("Access-Control-Allow-Origin", "*");
		Result<String> result = new Result<String>();
		String request = Utils.streamToString(getClass().getResourceAsStream(
				"/data/payload.xml"));

		Message<String> message = MessageBuilder.withPayload(request)
				.build();
		//或者通过GenericMessage 生成msg
//		GenericMessage<String> msg = new GenericMessage<String>(request);
//		messageChannel.send(msg);
		channel2.send(message);
		result.setMessage("发送成功成功");
		result.setData(request);
		result.setSuccess(true);
		return result;
	}
	
	@RequestMapping(value="/sendValidCode")
	public @ResponseBody Result<String> sendValidCode(@RequestParam("phone") String phone) {
		Result<String> result = new Result<String>();
		
		List<OrgUser> orgusers = this.userService.getOrgUserByMobile(phone);
		if(orgusers.size()>0){
			Map<String, String> data = this.userService.sendValidCode(phone);
			String message = data.get("message");
			if("成功".equals(message)){
//				result.setData(data.get("codestr"));
				result.setSuccess(true);
				result.setMessage("短信发送成功");
			}else{
				result.setSuccess(false);
				result.setMessage("短信发送失败,请重新发送");
			}	
		}else{
			result.setSuccess(false);
			result.setMessage("该手机号未注册");
		}
		return result;
	}
	
	@RequestMapping(value="/validCode")
	public @ResponseBody Result<String> validCode(HttpServletRequest request, HttpServletResponse response) {
		Result<String> result = new Result<String>();
		String phone = request.getParameter("phone");
		String code = request.getParameter("code");
		try {
			this.userService.validCode(phone,code);
			result.setSuccess(true);
			result.setMessage("验证码验证成功");
		}catch (EsbException e) {
			// TODO Auto-generated catch block
			result.setSuccess(false);
			result.setMessage(e.getMessage());
			result.setCode(e.getCode());
		}		
		return result;
	}
	
	@RequestMapping(value="/reSetPassword")
	public @ResponseBody Result<String> reSetPassword(HttpServletRequest request, HttpServletResponse response,@RequestParam(defaultValue="0") Integer org_id) {
		
		String phone = request.getParameter("phone");
		String code = request.getParameter("code");
		String passwd = request.getParameter("passwd");
		
		
		Result<String> result = new Result<String>();
		try {
			this.userService.reSetPassword(phone,code,passwd,org_id);
			result.setSuccess(true);
			result.setMessage("重置密码成功");
		}catch (EsbException e) {
			// TODO Auto-generated catch block
			result.setSuccess(false);
			result.setMessage(e.getMessage());
			result.setCode(e.getCode());
		}
		return result;
	}
	
	@RequestMapping(value="/syncteacher")
	public @ResponseBody Result<String> syncteacher(HttpServletRequest request, HttpServletResponse response) {	
		Result<String> result = new Result<String>();
		List<Teacher> list = teacherService.getAll();
		for (Teacher tr : list) {
			if(tr.getIs_del().equals(0)){
		        creatHxUser(tr.getUser_id().toString(),tr.getTech_name());
		        
		        if(tr.getDep_id()!=null&&!"".equals(tr.getDep_id())){ 
		        	Department dtp = this.redisDepartmentDao.getByDepId(tr.getDep_id().toString(), null);
		        	if(dtp.getHx_groupid()!=null&&!"".equals(dtp.getHx_groupid())){
						Object logresult = this.chatGroupAPI.addSingleUserToChatGroup(dtp.getHx_groupid(), tr.getUser_id().toString());
						if(logresult!=null){
				        	logger.info(logresult.toString());
				        }
		        	}
		        }
		        
			}else{
				this.iMUserAPI.deleteIMUserByUserName(tr.getUser_id().toString());
				
				if(tr.getDep_id()!=null){
					Department dtp = this.redisDepartmentDao.getByDepId(tr.getDep_id().toString(), null);
					if(dtp.getHx_groupid()!=null){
						this.chatGroupAPI.removeSingleUserFromChatGroup(dtp.getHx_groupid(), tr.getUser_id().toString());
					}
				}
			}
		}
		return result;
	}
	private void creatHxUser(String user_id,String name) {
		Object iresult = iMUserAPI.getIMUserByUserName(user_id);
		if(iresult==null){
			RegisterUsers users = new RegisterUsers();
		    User user = new User().username(user_id).password("123456");
		    users.add(user);
			Object aresult = iMUserAPI.createNewIMUserSingle(users);
			if(aresult!=null){
				logger.info(aresult.toString());
				Nickname nick = new Nickname();
		    	nick.setNickname(name);
		    	iMUserAPI.modifyIMUserNickNameWithAdminToken(user_id, nick);
			}
		}else{
			Nickname nick = new Nickname();
			nick.setNickname(name);
			iMUserAPI.modifyIMUserNickNameWithAdminToken(user_id, nick);
		}
	}
	
	@RequestMapping(value="/syncgroupmember")
	public @ResponseBody Result<String> syncgroupmember(HttpServletRequest request, HttpServletResponse response) {	
		Result<String> result = new Result<String>();
		this.redisGroupMemberDao.deleteAll();
		List<GroupMember> groupMembers = this.groupMemberService.getAll();
		for (GroupMember groupMember : groupMembers) {
			Group gp = this.redisGroupDao.getGroupById(groupMember.getGroup_id(), null);
			if(gp!=null){
				String groupId = gp.getHx_groupid();
				if(groupMember.getMem_id()!=null){
					Teacher t = this.teacherService.getTechById(groupMember.getMem_id());
					if(t!=null){
				        Object logresult = this.chatGroupAPI.addSingleUserToChatGroup(groupId, t.getUser_id().toString());
				        if(logresult!=null){
				        	logger.info(logresult.toString());
				        }
					}
				}
			}
		}
		return result;
	}
	
	@RequestMapping(value="/syncgroup")
	public @ResponseBody Result<String> syncgroup(HttpServletRequest request, HttpServletResponse response) {	
		Result<String> result = new Result<String>();
		List<Group> groups = this.groupService.getAll();
		for (Group gp : groups) {
			if(gp.getIs_del().equals(0)){
				if(gp.getHx_groupid()!=null&&!"".equals(gp.getHx_groupid())){
					ModifyGroup group = new ModifyGroup();
					group.description(gp.getNote()).groupname(gp.getGroup_name()).maxusers(500);
					Object resultg = this.chatGroupAPI.modifyChatGroup(gp.getHx_groupid(),group);
					if(resultg==null){
//						Message<String> message = MessageBuilder.withPayload(gp.getGroup_id()+"_"+0+"_"+"deletegp").build();
//						this.userService.messageChannelsend(message);
					}
				}else{
					creatGroup(gp);
				}
			}else{
				if(gp.getHx_groupid()!=null&&!"".equals(gp.getHx_groupid())){
					Object resultg = this.chatGroupAPI.deleteChatGroup(gp.getHx_groupid());    
					if(resultg!=null){
				        org.json.JSONObject obj = new org.json.JSONObject(resultg.toString());
						String dataString = obj.get("data").toString();
						org.json.JSONObject dataobj = new org.json.JSONObject(dataString);
						String groupid = dataobj.get("groupid").toString();
						logger.info(groupid);
				        logger.info(resultg.toString());
					}
			        Message<String> message = MessageBuilder.withPayload(gp.getGroup_id()+"_"+0+"_"+"deletegp").build();
					this.userService.messageChannelsend(message);
				}
			}
		}
		return result;
	}
	private void creatGroup(Group gp) {
		OrgUser user = this.userService.getAdminByOrgid(gp.getOrg_id());		
		if(user!=null){			
			if(gp.getHx_groupid()!=null&&!"".equals(gp.getHx_groupid())){
				ModifyGroup group = new ModifyGroup();
				group.description(gp.getNote()).groupname(gp.getGroup_name()).maxusers(500);
				Object result = this.chatGroupAPI.modifyChatGroup(gp.getHx_groupid(),group);
				if(result==null){
					Message<String> message = MessageBuilder.withPayload(gp.getGroup_id()+"_"+0+"_"+"deletegp").build();
					this.userService.messageChannelsend(message);
				}
			}else{
				Teacher teacher = this.redisTeacherDao.getByUserId(user.getUser_id().toString());
				creatHxUser(user.getUser_id().toString(),teacher.getTech_name());
				io.swagger.client.model.Group group = new io.swagger.client.model.Group();
				group.groupname(gp.getGroup_name()).desc(gp.getNote())._public(true).maxusers(500).approval(false).owner(user.getUser_id().toString());
				Object result = this.chatGroupAPI.createChatGroup(group);
				if(result!=null){
					org.json.JSONObject obj = new org.json.JSONObject(result.toString());
					String dataString = obj.get("data").toString();
					org.json.JSONObject dataobj = new org.json.JSONObject(dataString);
					String groupid = dataobj.get("groupid").toString();
					logger.info(groupid);
					logger.info(result.toString());
					Message<String> message = MessageBuilder.withPayload(gp.getGroup_id()+"_"+groupid+"_"+"addgp").build();
					this.userService.messageChannelsend(message);
				}
			}
		}
	}
	
	@RequestMapping(value="/syncdepartment")
	public @ResponseBody Result<String> syncdepartment(HttpServletRequest request, HttpServletResponse response) {	
		Result<String> result = new Result<String>();
		List<Department> list = departmentService.getAll();
		for (Department dt : list) {
			if(dt.getIs_del().equals(0)){
				if(dt.getHx_groupid()!=null&&!"".equals(dt.getHx_groupid())){
					ModifyGroup group = new ModifyGroup();
					group.description(dt.getDep_note()).groupname(dt.getDep_name()).maxusers(500);
					Object resultg = this.chatGroupAPI.modifyChatGroup(dt.getHx_groupid(),group);
					if(resultg!=null){
						logger.info(resultg.toString());
					}else{
//						dt.setHx_groupid(null);
//						this.departmentService.update(dt);
					}
				}else{
					creatDepartGroup(dt);
				}
			}else{
				if(dt.getHx_groupid()!=null&&!"".equals(dt.getHx_groupid())){
					Object resultg = this.chatGroupAPI.deleteChatGroup(dt.getHx_groupid());    
					if(result!=null){
				        org.json.JSONObject obj = new org.json.JSONObject(result.toString());
						String dataString = obj.get("data").toString();
						org.json.JSONObject dataobj = new org.json.JSONObject(dataString);
						String groupid = dataobj.get("groupid").toString();
						logger.info(groupid);
				        logger.info(resultg.toString());
					}
					dt.setHx_groupid(null);
					this.departmentService.update(dt);
				}
			}
		}
		return result;
	}

	private void creatDepartGroup(Department dt) {

		// TODO Auto-generated method stub
		OrgUser user = this.userService.getAdminByOrgid(dt.getOrg_id());
		if(user!=null){			
			if(dt.getHx_groupid()!=null&&!"".equals(dt.getHx_groupid())){
				ModifyGroup group = new ModifyGroup();
				group.description(dt.getDep_note()).groupname(dt.getDep_name()).maxusers(500);
				Object result = this.chatGroupAPI.modifyChatGroup(dt.getHx_groupid(),group);
				if(result==null){
					Message<String> message = MessageBuilder.withPayload(dt.getDep_id()+"_"+0+"_"+"deletedt").build();
					this.userService.messageChannelsend(message);
				}
			}else{
				Teacher teacher = this.redisTeacherDao.getByUserId(user.getUser_id().toString());
				creatHxUser(user.getUser_id().toString(),teacher.getTech_name());
				io.swagger.client.model.Group group = new io.swagger.client.model.Group();
				group.groupname(dt.getDep_name()).desc(dt.getDep_note())._public(true).maxusers(500).approval(false).owner(user.getUser_id().toString());
				Object result = this.chatGroupAPI.createChatGroup(group);
				if(result!=null){
					org.json.JSONObject obj = new org.json.JSONObject(result.toString());
					String dataString = obj.get("data").toString();
					org.json.JSONObject dataobj = new org.json.JSONObject(dataString);
					String groupid = dataobj.get("groupid").toString();
					logger.info(groupid);
					logger.info(result.toString());
					Message<String> message = MessageBuilder.withPayload(dt.getDep_id()+"_"+groupid+"_"+"adddt").build();
					this.userService.messageChannelsend(message);
				}
			}
		}
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
	
	@ResponseBody
	@RequestMapping(value = "/sendSmsYZ")
	public Result<Object> sendSmsYZ(HttpServletRequest request, HttpServletResponse response) throws Exception  {		
		Result<Object> result = new Result<Object>();	
		try{
			String phones = request.getParameter("phone");
			String[] es = null;
			if(phones.indexOf(",")==-1){
				es = new String[1];
				es[0] = phones;
			}
			else
				es = phones.split(",");
			String content = request.getParameter("content");
			String ss = "success";
		
//			String sms_name = FileProperties.getProperty("qxt_sms_name");
//			String sms_pass = FileProperties.getProperty("qxt_sms_pass");
//			String sms_url = FileProperties.getProperty("qxt_sms_url");
//			
//			for(int i=0;i<es.length;i++){
//				HttpRequestUtil.sendGet(sms_url, String.format("username=%s&password=%s&to=%s&text=%s&subid=%s&msgtype=4",sms_name,sms_pass,es[i],URLEncoder.encode(content,"gb2312"),""));
//			}
			
			this.util.schoolsms(es, content, "校园云办公");
			
			result.setData(ss);
			result.setSuccess(true);
			result.setMessage(EnumMessage.success.getMessage());
			result.setCode(EnumMessage.success.getCode());
		}catch(Exception e){
			e.printStackTrace();	
			result.setSuccess(false);
			result.setMessage(e.getMessage());
		}
		return result;
	}
	
	
	@RequestMapping(value = "/validtoken")
	public void validtoken(HttpServletRequest request, HttpServletResponse response) throws IOException {
		String service = request.getParameter("service");
		String uri = request.getParameter("uri");
		String c = request.getParameter("c");
		// Map<String, App> apps = this.eduConfig.getSso();
		// App app = apps.get(service);
		App app = this.redisAppDao.getAppByKey(service);

		String redirectUrl = "";
		if (app != null && !"".equals(app.getLogin())) {
			redirectUrl = app.getLogin();
		} else {
			redirectUrl = "login";
		}
		String sessionId = request.getParameter("jsessionid");
		Appservice as = new Appservice();
		as.setName(service);
		as.setSessionId(sessionId);
		if (app != null) {
			String tokenstr = CookieUtils.getCookieValue(request, this.eduConfig.getEduconfig().getToken());
			String udidstr = CookieUtils.getCookieValue(request, this.eduConfig.getEduconfig().getUdid());
			if (tokenstr != null && udidstr != null) {
				if (this.checkToken(tokenstr, udidstr, as)) {
					String url = app.getSetSession() + "?token=" + tokenstr + "&udid=" + udidstr;
					if (uri != null & !"".equals(uri)) {
						url = url + "&uri=" + uri;
					}
					redirectUrl = url;
				}
			}
		}
		if(StringUtils.isNotEmpty(c)){
			redirectUrl += "?c=" + c;
		}
		response.sendRedirect(redirectUrl);
		response.flushBuffer();
	}
	@SuppressWarnings("unchecked")
	private boolean checkToken(String tokenstr, String udidstr, Appservice service) {
		// TODO Auto-generated method stub
		if (StringUtils.isNotBlank(tokenstr) && StringUtils.isNotBlank(udidstr)) {
			String[] params = tokenstr.split("\\_");
			if (params.length >= 5) {
				String tokenseg = params[4];
				String md5str = params[0];
				String insertime = params[1];
				String user_id = params[2];
				String type = params[3];
				Long expired = this.eduConfig.getEduconfig().getExpired0();
				Long extratime = this.redisLoginDao.getExtratime(type, user_id);
				Long expiretime = (expired * 1000 + Long.parseLong(insertime)) + extratime;
				Long nowtime = (new Date()).getTime();
				String version = "0";
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
							ua = this.userService.getUserAccount(user_id, "0", "1");
						}
					}
					String md5 = Utils.MD5(insertime + ":" + user_id + ":" + type + ":" + user_salt + ":" + tokenseg + ":"
							+ this.eduConfig.getEduconfig().getSecret());
					if (ua != null && md5.equals(md5str)) {
						String udidseg = udidstr.substring(udidstr.length() - 8);

						Long timespan = Math.abs(ua.getUpdated_time().getTime() - Long.parseLong(insertime));
						if (udidseg.equals(tokenseg) && udidstr.equals(ua.getUdid()) && timespan < 10000) {
							Long addtime = (new Date()).getTime() - Long.parseLong(insertime);
							Login login = new Login();
							login.setType(type);
							login.setUser_id(user_id);
							login.setExtratime(addtime);
							this.redisLoginDao.add(login);
							Map<String, String> ss = new HashMap<String, String>();
							if (ua.getServices() != null && !"".equals(ua.getServices().trim())) {
								Map<String, String> services = this.util.getPojoFromJSON(ua.getServices(), Map.class);
								ss = services;
							}
							if (service != null && !"".equals(service)) {
								ss.put(service.getName(), service.getSessionId());
							}
							ua.setServices(this.util.getJSONFromPOJO(ss));
							this.userService.saveUserAccount(ua);
							return true;
						} else {
							return false;
						}
					} else {
						return false;
					}
				} else {
					return false;
				}
			} else {
				return false;
			}
		} else {
			return false;
		}
	}
	
	
	@ResponseBody
	@RequestMapping(value = "/checkOpenid")
	public int checkOpenid(HttpServletRequest request, HttpServletResponse response) throws Exception  {		
		int result = -1;	
		try{
			String identity = request.getParameter("identity");
			String org_id = request.getParameter("org_id");
			String openid = request.getParameter("openid");
			
			OrgUser user = redisOrgUserDao.getUserByOpenId(openid,new Integer(org_id),new Integer(identity));
			if(user!=null){
				result = 0;
			}
		}catch(Exception e){
			e.printStackTrace();	
			result = -1;
		}
		return result;
	}
	
	@RequestMapping(value = "/getuserinfo")
	public @ResponseBody Result<OrgUser> getuserinfo(HttpServletRequest request) {
		Result<OrgUser> result = new Result<OrgUser>();		
		String orgUserId = request.getParameter("orgUserId");
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
	
	@ResponseBody
	@RequestMapping(value = "/getParentPhone")
	@EduJsonFilters(value={@EduJsonFilter(mixin=ParentFilter.class, target=Parent.class)})
	public Result<List<Parent>> getParentPhone(
			@RequestParam(value="stud_id") String stud_id,
			HttpServletResponse response) throws IOException  {		
		Result<List<Parent>> result = new Result<List<Parent>>();	
		try {
			//List<Parent> list = parentService.getByStudId(stud_id);
			List<Parent> list = redisStudentParentDao.getParentsBySId(stud_id);
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
	
	@RequestMapping(value = "/login",produces="text/html;charset=UTF-8")
	public ModelAndView login(HttpServletRequest request, HttpServletResponse response) throws UnsupportedEncodingException  {

		ModelAndView mav = new ModelAndView("/login");
		String tokenstr = CookieUtils.getCookieValue(request, this.eduConfig.getEduconfig().getToken());
		String udidstr = CookieUtils.getCookieValue(request, this.eduConfig.getEduconfig().getUdid());
		
		if (tokenstr != null && udidstr != null) {
			if (this.checkToken(tokenstr, udidstr, null)) {
				String[] params = tokenstr.split("\\_");
				if (params.length >= 5) {
					// String tokenseg = params[4];
					// String md5str = params[0];
					// String insertime = params[1];
					String user_id = params[2];
					String type = params[3];
					String version = "0";
					UserAccount ua = null;
					List<OrgUser> orgusers = new ArrayList<OrgUser>();
					String uc_id=null;
					if ("0".equals(type)) {
						OrgUser user = this.redisOrgUserDao.getUserById(user_id);
						if (user != null) {
							UcuserOrguser uuser = this.redisUcUserOrguserDao.getByUserId(user_id);
							if (uuser != null) {
								ua = this.userService.getUserAccount(uuser.getUc_id().toString(), version, "1");

							} else {
								ua = this.userService.getUserAccount(user.getUser_loginname(), version, "0");
							}
						}
						UcuserOrguser uo = this.redisUcUserOrguserDao.getByUserId(user_id);
						if (uo != null) {
							uc_id = uo.getUc_id().toString();
						}
						
					} else if ("1".equals(type)) {
						UcUser user = this.redisUcUserDao.getUserById(user_id);
						if (user != null) {
							ua = this.userService.getUserAccount(user_id, version, "1");
						}
						uc_id = user_id;
					}
					if(uc_id!=null){
						Map<String, UcuserOrguser> orgus = this.redisUcUserOrguserDao.getByUcId(uc_id);
						if (orgus != null) {
							for (String orguserid : orgus.keySet()) {
								OrgUser ou = this.redisOrgUserDao.getUserById(orguserid);
								if(ou!=null){
									orgusers.add(ou);
								}
							}
						}
					}
					OrgUser user = this.userService.getOrgUserById(ua.getUser_id());
					mav.addObject("user", user);
					mav.addObject("orgusers", orgusers);				
					//App app = this.redisAppDao.getAppByKey(service);
				}
			}
		}
		if(request.getParameter("error")!=null&&!"".equals(request.getParameter("error"))){
			String tmp = request.getParameter("error");
			mav.addObject("error", tmp);
		}
		return mav;
	}
	
	@RequestMapping(value = "/LoginPost",produces="text/html;charset=UTF-8")
	public void LoginPost(@RequestParam String login_name,
			@RequestParam String login_pass,HttpServletRequest request, HttpServletResponse response) throws IOException  {
//		ModelAndView mav = new ModelAndView("/login");	

		OrgUser user = null;
		Integer version = 0;
		String url = "login";
		//得到客户端传入的验证码参数
		//得到session上的验证码
		try {
			Map<String, OrgUser> users = this.userService.login(login_name, login_pass);
			if (users == null || users.size() <= 0) {
				throw new EsbException(EnumException.user_password_wrong);
			}

			UUID uuid = UUID.randomUUID();
			String udid = uuid.toString();

			user = users.get(Utils.getFirstOrNull(users));

			UcuserOrguser uo = this.redisUcUserOrguserDao.getByUserId(user.getUser_id().toString());
			// UcUser ucuser = null;
			List<OrgUser> orgusers = new ArrayList<OrgUser>();
			// UserAccount ua = null;
			if (uo != null) {
				// ua = this.userService.getUserAccount(uo.getUc_id().toString(),version.toString(),"1");
				this.userService.addUserAccount(uo.getUc_id().toString(), version, udid, user.getUser_id(), user.getOrg_id(), 1);
				// UcUser ucuser = this.redisUcUserDao.getUserById(uo.getUc_id().toString());
				Map<String, UcuserOrguser> orgus = this.redisUcUserOrguserDao.getByUcId(uo.getUc_id().toString());
				if (orgus != null) {
					for (String orguserid : orgus.keySet()) {
						OrgUser ou = this.redisOrgUserDao.getUserById(orguserid);

						if(ou!=null){
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
							ou.setOrganization(org);
							orgusers.add(ou);
						}	
					}
				}
			} else {
				// ua = this.userService.getUserAccount(user.getUser_loginname(),version.toString(),"0");
				this.userService.addUserAccount(user.getUser_loginname(), version, udid, user.getUser_id(), user.getOrg_id(), 0);
				// OrgUser ou = this.redisOrgUserDao.getUserById(user.getUser_id().toString());
				for (OrgUser orgUser : users.values()) {
					Organization org = this.redisOrganizationDao.getByOrgId(orgUser.getOrg_id().toString(), null);
					if (org != null) {
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

					orgUser.setOrganization(org);
					orgusers.add(orgUser);
				}

			}

			String type = "0";
			String token = this.userService.createToken(user.getUser_id(), user.getUser_salt(), version, udid, type);
			CookieUtils.setCookie(request, response, this.eduConfig.getEduconfig().getToken(), token);
			CookieUtils.setCookie(request, response, this.eduConfig.getEduconfig().getUdid(), udid);
			Login login = new Login();
			login.setType(type);
			login.setUser_id(user.getUser_id().toString());
			login.setExtratime(0L);
			this.redisLoginDao.add(login);
//			mav.addObject("user", user);
		} catch (EsbException e) {
			// TODO Auto-generated catch block
//			mav.addObject("error", e.getMessage());
			url = url+"?error="+URLEncoder.encode(e.getMessage(),"UTF-8");
		}
		response.sendRedirect(url);
//		return mav;
	}
	
	@SuppressWarnings("unused")
	@RequestMapping(value = "/appLogin", produces = "text/html;charset=UTF-8")
	public void appLogin(@RequestParam String login_name, @RequestParam String login_pass, @RequestParam String service,
			HttpServletRequest request, HttpServletResponse response) throws IOException {

		OrgUser user = null;
		Integer version = 0;

		// App app = this.eduConfig.getSso().get(service);
		App app = this.redisAppDao.getAppByKey(service);

		String force = request.getParameter("force");

		if (app != null) {
			try {
				String identity = request.getParameter("identity")!=null?request.getParameter("identity"):null;
				Map<String, OrgUser> users = this.userService.login(login_name, login_pass,identity);
				if (users == null || users.size() <= 0) {
					throw new EsbException(EnumException.user_password_wrong);
				}

				UUID uuid = UUID.randomUUID();
				String udid = uuid.toString();

				String org_id = request.getParameter("org_id");
				if (org_id != null && !"".equals(org_id)) {
					user = users.get(org_id);
				}

				// user = users.get(Utils.getFirstOrNull(users));
				OrgUser firstuser = users.get(Utils.getFirstOrNull(users));
				UcuserOrguser uo = this.redisUcUserOrguserDao.getByUserId(firstuser.getUser_id().toString());
				UcUser ucuser = null;
				List<OrgUser> orgusers = new ArrayList<OrgUser>();
				UserAccount ua = null;
				Integer user_idL = null;
				Integer org_idL = null;
				if (uo != null) {
					ua = this.userService.getUserAccount(uo.getUc_id().toString(), version.toString(), "1");
					if (ua == null && user == null) {
						user_idL = users.get(Utils.getFirstOrNull(users)).getUser_id();
						org_idL = users.get(Utils.getFirstOrNull(users)).getOrg_id();
					}
					if (user != null) {
						user_idL = user.getUser_id();
						org_idL = user.getOrg_id();
					}
					this.userService.addUserAccount(uo.getUc_id().toString(), version, udid, user_idL, org_idL, 1);
					ucuser = this.redisUcUserDao.getUserById(uo.getUc_id().toString());
					Map<String, UcuserOrguser> orgus = this.redisUcUserOrguserDao.getByUcId(uo.getUc_id().toString());
					if (orgus != null) {
						for (String orguserid : orgus.keySet()) {
							OrgUser ou = this.redisOrgUserDao.getUserById(orguserid);
							if(ou!=null){
								Organization org = this.redisOrganizationDao.getByOrgId(ou.getOrg_id().toString(), null);
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
								ou.setOrganization(org);
								orgusers.add(ou);
							}
						}
					}
				} else {
					ua = this.userService.getUserAccount(firstuser.getUser_loginname(), version.toString(), "0");
					if (ua == null && user == null) {
						user_idL = users.get(Utils.getFirstOrNull(users)).getUser_id();
						org_idL = users.get(Utils.getFirstOrNull(users)).getOrg_id();
					}
					if (user != null) {
						user_idL = user.getUser_id();
						org_idL = user.getOrg_id();
					}
					this.userService.addUserAccount(firstuser.getUser_loginname(), version, udid, user_idL, org_idL, 0);
					// OrgUser ou = this.redisOrgUserDao.getUserById(user.getUser_id().toString());
					for (OrgUser orgUser : users.values()) {
						Organization org = this.redisOrganizationDao.getByOrgId(orgUser.getOrg_id().toString(), null);
						if (org != null) {
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

						orgUser.setOrganization(org);
						orgusers.add(orgUser);
					}

				}

				String type = "0";
				String token = this.userService
						.createToken(firstuser.getUser_id(), firstuser.getUser_salt(), version, udid, type);
				CookieUtils.setCookie(request, response, this.eduConfig.getEduconfig().getToken(), token);
				CookieUtils.setCookie(request, response, this.eduConfig.getEduconfig().getUdid(), udid);
				Login login = new Login();
				login.setType(type);
				login.setUser_id(firstuser.getUser_id().toString());
				login.setExtratime(0L);
				this.redisLoginDao.add(login);

				if (ua != null && ua.getServices() != null && !"".equals(ua.getServices())) {
					// Map<String, App> apps = this.eduConfig.getSso();
					@SuppressWarnings("unchecked")
					Map<String, String> services = this.util.getPojoFromJSON(ua.getServices(), Map.class);
					if (services.keySet().size() > 0) {
						for (String key : services.keySet()) {
							// App sapp = apps.get(key);
							App sapp = this.redisAppDao.getAppByKey(key);
							if (sapp != null) {
								String sessionkey = (sapp.getSessionkey()!=null&&!"".equals(sapp.getSessionkey()))?sapp.getSessionkey():"JSESSIONID";
								Map<String, String> cookies = new HashMap<String, String>();
								cookies.put("Cookie", sessionkey+"=" + services.get(key));
								try {
									String ret = HttpRequest.get(sapp.getLogout()).headers(cookies).body();
									logger.info(key + "===========ret===========" + ret);
								} catch (Exception e) {
									logger.info(key + "==========logout=ret==error=========" + e.getMessage());
								}
							}
						}
						UserAccount newua = this.userService.getUserAccountById(ua.getUa_id());
						newua.setServices("");
						this.userService.saveUserAccount(newua);
					}
				}
				String directurl = "";
				directurl = app.getLoginProcess() + "?t=success";
				if (org_id != null && !"".equals(org_id)) {
					directurl = directurl + "&org_id=" + org_id;
				}
				if (force != null && "true".equals(force)) {
					directurl = directurl + "&force=true";
				}
				response.sendRedirect(directurl);
			} catch (EsbException e) {
				// TODO Auto-generated catch block
				response.sendRedirect(app.getLoginProcess() + "?t=error&message=" + e.getMessage());
			}
		} else {
			response.sendRedirect("login");
		}
	}
	
	@RequestMapping(value = "/logout")
	public void logout(HttpServletRequest request, HttpServletResponse response) throws IOException {
		String tokenstr = CookieUtils.getCookieValue(request, this.eduConfig.getEduconfig().getToken());
		String udidstr = CookieUtils.getCookieValue(request, this.eduConfig.getEduconfig().getUdid());
		// String redirecturl = "logout";
		if (StringUtils.isNotBlank(tokenstr) && StringUtils.isNotBlank(udidstr)) {
			String[] params = tokenstr.split("\\_");
			if (params.length >= 5) {
				String tokenseg = params[4];
				String md5str = params[0];
				String insertime = params[1];
				String user_id = params[2];
				String type = params[3];
				Long expired = this.eduConfig.getEduconfig().getExpired0();
				Long extratime = this.redisLoginDao.getExtratime(type, user_id);
				Long expiretime = (expired * 1000 + Long.parseLong(insertime)) + extratime;
				Long nowtime = (new Date()).getTime();
				String version = "0";
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
							ua = this.userService.getUserAccount(user_id, "0", "1");
						}
					}
					String md5 = Utils.MD5(insertime + ":" + user_id + ":" + type + ":" + user_salt + ":" + tokenseg + ":"
							+ this.eduConfig.getEduconfig().getSecret());
					if (ua != null && md5.equals(md5str)) {
						String udidseg = udidstr.substring(udidstr.length() - 8);
						Long timespan = Math.abs(ua.getUpdated_time().getTime() - Long.parseLong(insertime));
						if (udidseg.equals(tokenseg) && udidstr.equals(ua.getUdid()) && timespan < 10000) {
							if (ua.getServices() != null && !"".equals(ua.getServices())) {
								// Map<String, App> apps = this.eduConfig.getSso();
								@SuppressWarnings("unchecked")
								Map<String, String> services = this.util.getPojoFromJSON(ua.getServices(), Map.class);
								for (String key : services.keySet()) {
									// App sapp = apps.get(key);
									App sapp = this.redisAppDao.getAppByKey(key);
									
									if (sapp != null) {
										String sessionkey = (sapp.getSessionkey()!=null&&!"".equals(sapp.getSessionkey()))?sapp.getSessionkey():"JSESSIONID";
										Map<String, String> cookies = new HashMap<String, String>();
										cookies.put("Cookie", sessionkey+"=" + services.get(key));
										try {
											String ret = HttpRequest.get(sapp.getLogout()).headers(cookies).body();
											logger.info(key+"===========ret==========="+ret);
										} catch (Exception e) {
										}										
									}	
								}
								ua.setServices("");
							}
							ua.setUpdated_time(new Date());
							ua.setUdid("");
							this.userService.saveUserAccount(ua);
						}
					}
				}
			}
		}

		response.sendRedirect("login");
		response.flushBuffer();
	}
	
	@RequestMapping(value="/sendValidCodeWX")
	public @ResponseBody Result<String> sendValidCodeWX(@RequestParam("phone") String phone) {
		Result<String> result = new Result<String>();
		logger.info("send code start:"+phone);
		List<OrgUser> orgusers = this.userService.getOrgUserByMobile(phone);
		if(orgusers.size()>0){
			Map<String, String> data = this.userService.sendValidCodeWX(phone);
			String message = data.get("message");
			if("成功".equals(message)){
//				result.setData(data.get("codestr"));
				result.setSuccess(true);
				result.setMessage("短信发送成功");
			}else{
				result.setSuccess(false);
				result.setMessage("短信发送失败,请重新发送");
			}	
		}else{
			result.setSuccess(false);
			result.setMessage("该手机号未注册");
		}
		logger.info("send code end");
		return result;
	}
	
	@RequestMapping(value="/syncgrade")
	public @ResponseBody Result<String> syncgrade() {
		Result<String> result = new Result<String>();
		List<Grade> grades = this.gradeService.getAll();
		for (Grade grade : grades) {
			Utils.formatGradeType(grade);
			this.gradeService.update(grade);
		}
		
		result.setSuccess(false);
		result.setMessage("年级更新成功");		
		return result;
	}
}
