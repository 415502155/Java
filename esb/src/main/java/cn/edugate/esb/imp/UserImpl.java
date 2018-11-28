
package cn.edugate.esb.imp;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.integration.Message;
import org.springframework.integration.MessageChannel;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import cn.edugate.esb.EduConfig;
import cn.edugate.esb.dao.IActCodeDao;
import cn.edugate.esb.dao.IOrgUserDao;
import cn.edugate.esb.dao.IRightDao;
import cn.edugate.esb.dao.IRoleDao;
import cn.edugate.esb.dao.IRoleLabelDao;
import cn.edugate.esb.dao.ITeacherDao;
import cn.edugate.esb.dao.ITeacherRoleDao;
import cn.edugate.esb.dao.ITechRangeDao;
import cn.edugate.esb.dao.IUcUserDao;
import cn.edugate.esb.dao.IUcuserOrguserDao;
import cn.edugate.esb.dao.IUserAccountDao;
import cn.edugate.esb.entity.ActCode;
import cn.edugate.esb.entity.OrgUser;
import cn.edugate.esb.entity.Organization;
import cn.edugate.esb.entity.Right;
import cn.edugate.esb.entity.Role;
import cn.edugate.esb.entity.RoleLabel;
import cn.edugate.esb.entity.Teacher;
import cn.edugate.esb.entity.TeacherRole;
import cn.edugate.esb.entity.TechRange;
import cn.edugate.esb.entity.UcUser;
import cn.edugate.esb.entity.UcuserOrguser;
import cn.edugate.esb.entity.UserAccount;
import cn.edugate.esb.exception.EnumException;
import cn.edugate.esb.exception.EsbException;
import cn.edugate.esb.redis.dao.RedisOrgUserDao;
import cn.edugate.esb.redis.dao.RedisRoleDao;
import cn.edugate.esb.redis.dao.RedisTeacherRoleDao;
import cn.edugate.esb.redis.dao.RedisUcUserDao;
import cn.edugate.esb.redis.dao.RedisUserAccountDao;
import cn.edugate.esb.service.UserService;
import cn.edugate.esb.util.Constant;
import cn.edugate.esb.util.LSHelper;
import cn.edugate.esb.util.Paging;
import cn.edugate.esb.util.PassWordCreater;
import cn.edugate.esb.util.Util;
import cn.edugate.esb.util.Utils;

@Component
@Transactional("transactionManager")
public class UserImpl implements UserService {

	private static Logger logger = Logger.getLogger(UserImpl.class);

	private RedisOrgUserDao redisOrgUserDao;

	private RedisUcUserDao redisUcUserDao;

	private RedisUserAccountDao redisUserAccountDao;

	private IOrgUserDao orgUserDao;

	private IUcUserDao ucUserDao;

	private IUserAccountDao userAccountDao;

	private IUcuserOrguserDao ucuserOrguserDao;

	private EduConfig eduConfig;

	private ITeacherRoleDao teacherRoleDao;

	private IRoleDao roleDao;

	private IRightDao rightDao;

	private ITechRangeDao techRangeDao;

	private IRoleLabelDao roleLabelDao;

	private IActCodeDao actCodeDao;

	private Util util;

	private ITeacherDao teacherDao;

	private RedisTeacherRoleDao redisTeacherRoleDao;

	private RedisRoleDao redisRoleDao;

	@Autowired
	@Qualifier("syncgroupchannel")
	MessageChannel messageChannel;

	@Autowired
	public void setRedisRoleDao(RedisRoleDao redisRoleDao) {
		this.redisRoleDao = redisRoleDao;
	}

	@Autowired
	public void setRedisTeacherRoleDao(RedisTeacherRoleDao redisTeacherRoleDao) {
		this.redisTeacherRoleDao = redisTeacherRoleDao;
	}

	@Autowired
	public void setTeacherDao(ITeacherDao teacherDao) {
		this.teacherDao = teacherDao;
	}

	@Autowired
	public void setUtil(Util util) {
		this.util = util;
	}

	@Autowired
	public void setActCodeDao(IActCodeDao actCodeDao) {
		this.actCodeDao = actCodeDao;
	}

	@Autowired
	public void setRoleLabelDao(IRoleLabelDao roleLabelDao) {
		this.roleLabelDao = roleLabelDao;
	}

	@Autowired
	public void setTechRangeDao(ITechRangeDao techRangeDao) {
		this.techRangeDao = techRangeDao;
	}

	@Autowired
	public void setRightDao(IRightDao rightDao) {
		this.rightDao = rightDao;
	}

	@Autowired
	public void setRoleDao(IRoleDao roleDao) {
		this.roleDao = roleDao;
	}

	@Autowired
	public void setTeacherRoleDao(ITeacherRoleDao teacherRoleDao) {
		this.teacherRoleDao = teacherRoleDao;
	}

	@Autowired
	public void setRedisUserAccountDao(RedisUserAccountDao redisUserAccountDao) {
		this.redisUserAccountDao = redisUserAccountDao;
	}

	@Autowired
	public void setRedisUcUserDao(RedisUcUserDao redisUcUserDao) {
		this.redisUcUserDao = redisUcUserDao;
	}

	@Autowired
	public void setEduConfig(EduConfig eduConfig) {
		this.eduConfig = eduConfig;
	}

	@Autowired
	public void setUcuserOrguserDao(IUcuserOrguserDao ucuserOrguserDao) {
		this.ucuserOrguserDao = ucuserOrguserDao;
	}

	@Autowired
	public void setUserAccountDao(IUserAccountDao userAccountDao) {
		this.userAccountDao = userAccountDao;
	}

	@Autowired
	public void setUcUserDao(IUcUserDao ucUserDao) {
		this.ucUserDao = ucUserDao;
	}

	@Autowired
	public void setOrgUserDao(IOrgUserDao orgUserDao) {
		this.orgUserDao = orgUserDao;
	}

	@Autowired
	public void setRedisOrgUserDao(RedisOrgUserDao redisOrgUserDao) {
		this.redisOrgUserDao = redisOrgUserDao;
	}

	@Override
	public void add(UcUser user) {
		user.setInsert_time(new Date());
		try {
			ucUserDao.saveOrUpdate(user);
		} catch (Exception e) {
			logger.error("create User success");
		}
		logger.info("create User success");
	}

	@Override
	public int delete(Integer userID) {
		UcUser user = ucUserDao.get(UcUser.class, userID);
		user.setDel_time(new Date());
		ucUserDao.update(user);
		return userID;
	}

	@Override
	public int update(UcUser user) {
		user.setUpdate_time(new Date());
		ucUserDao.update(user);
		return 0;
	}

	@Override
	public UcUser getByID(Integer userID) {
		return ucUserDao.get(UcUser.class, userID);
	}

	@Override
	public List<UcUser> getAll(UcUser user) {
		Criterion criterion = Restrictions.ilike("uc_loginname", user.getUc_loginname());
		return ucUserDao.list(UcUser.class, criterion, Order.desc("insert_time"));
	}

	@Override
	public List<UcUser> getAllByRoleId(Integer roleId) {
		return ucUserDao.getAllByRoleId(roleId);
	}

	@Override
	public Paging<UcUser> getAllWithPaging(Paging<UcUser> paging, String name, String mobile, String ssoType, String mail,
			String orgType, String udid) {
		paging = ucUserDao.getAllWithPaging(paging, name, mobile, ssoType, mail, orgType, udid);
		return paging;
	}

	@Override
	public Paging<UcUser> getAllByRoleIdWithPaging(Paging<UcUser> paging, String name, String mobile, String mail, String type,
			String roleId, String orgType, String udid) {
		paging = ucUserDao.getAllByRoleIdWithPaging(paging, name, mobile, type, mail, roleId, orgType, udid);
		return paging;
	}

	@Override
	public boolean checkName(String name, String uid) {
		Criterion criterion = Restrictions.and(Restrictions.eq("user_loginname", name)).add(Restrictions.eq("is_del", false));
		List<OrgUser> list = orgUserDao.list(OrgUser.class, criterion);
		if (list.size() == 0) {
			return true;
		} else {
			return false;
		}
	}

	@Override
	public UcUser getUserByLoginName(String login_name, Integer org_type) {
		return this.ucUserDao.getUserByLoginName(login_name, org_type);
	}

	@Override
	public OrgUser login(String login_name, String login_pass, Integer org_id) throws EsbException {
		OrgUser user = this.redisOrgUserDao.getUserByLoginName(login_name, org_id);

		if (user != null) {
			String passwordMd5 = Utils.MD5(user.getUser_salt() + ":" + login_pass);
			if (passwordMd5.equals(user.getUser_loginpass())) {

			} else {
				throw new EsbException(EnumException.user_password_wrong);
			}

		} else {
			throw new EsbException(EnumException.user_password_wrong);
		}

		return user;
	}

	@Override
	public Map<String, OrgUser> login(String login_name, String login_pass) throws EsbException {
		// TODO Auto-generated method stub
		Map<String, OrgUser> users = this.redisOrgUserDao.getUserByLoginName(login_name);
		Map<String, OrgUser> temps = new LinkedHashMap<String, OrgUser>();
		if (users != null && users.size() > 0) {
			for (OrgUser user : users.values()) {
				String passwordMd5 = Utils.MD5(user.getUser_salt() + ":" + login_pass);
				if (passwordMd5.equals(user.getUser_loginpass())) {
					LSHelper.processInjection(user);
					temps.put(user.getOrg_id().toString() + "_" + user.getIdentity(), user);
				}
			}
		}
		return temps;
	}

	/**
	 * 中心用户登录
	 * 
	 * @param login_name
	 * @param login_pass
	 * @param version
	 * @return
	 */
	public UcUser uclogin(String login_name, String login_pass, Integer version) throws EsbException {
		UcUser user = this.redisUcUserDao.getUserByLoginName(login_name);

		if (user != null) {
			String passwordMd5 = Utils.MD5(user.getUc_salt() + ":" + login_pass);
			if (passwordMd5.equals(user.getUc_loginpass())) {

			} else {
				throw new EsbException(EnumException.user_password_wrong);
			}

		} else {
			throw new EsbException(EnumException.user_password_wrong);
		}

		return user;
	}

	@Override
	public List<OrgUser> getOrgUserList() {
		// TODO Auto-generated method stub
		return this.orgUserDao.getList();
	}

	@Override
	public void deleteById(Integer id) {
		// TODO Auto-generated method stub
		UcUser user = this.getByID(id);
		this.ucUserDao.delete(user);
	}

	@Override
	public List<UcUser> getUcUserList() {
		// TODO Auto-generated method stub
		return this.ucUserDao.getUserList();
	}

	@Override
	public UserAccount getUserAccount(String targetid, String version, String type) {
		// UserAccount ua = this.userAccountDao.getUserAccount(targetid,version,type);
		UserAccount ua = this.redisUserAccountDao.getUserAccount(targetid, version, type);
		return ua;
	}

	@Override
	public void addUserAccount(String targetid, Integer version, String udid, Integer user_id, Integer org_id, Integer type) {
		// TODO Auto-generated method stub
		UserAccount ua = this.userAccountDao.getUserAccount(targetid, version, type);
		if (ua == null) {
			ua = new UserAccount();
			ua.setTarget_id(targetid);
			ua.setType(type);
			ua.setVersion(version);
			ua.setCreated_time(new Date());
			if (user_id != null) {
				ua.setUser_id(user_id);
			}
			if (user_id != null) {
				ua.setOrg_id(org_id);
			}
		} else {
			if (ua.getUser_id() != null && !"".equals(ua.getUser_id())) {
				OrgUser ou = this.redisOrgUserDao.getUserById(ua.getUser_id().toString());
				if (ou == null) {
					ua.setUser_id(user_id);
					ua.setOrg_id(org_id);
				}
			}
		}
		ua.setUdid(udid);
		ua.setUpdated_time(new Date());
		this.userAccountDao.saveOrUpdate(ua);
	}

	@Override
	public String createToken(Integer user_id, String user_salt, Integer version, String udid, String type) {
		// TODO Auto-generated method stub
		long now = (new Date()).getTime();
		String tokenSeg = udid.substring(udid.length() - 8);
		String token = Utils.MD5(now + ":" + user_id + ":" + type + ":" + user_salt + ":" + tokenSeg + ":"
				+ this.eduConfig.getEduconfig().getSecret())
				+ "_" + now + "_" + user_id + "_" + type + "_" + tokenSeg;
		return token;
	}

	@Override
	public List<UcuserOrguser> getUcuserOrguserList() {
		// TODO Auto-generated method stub
		Criterion criterion = Restrictions.conjunction();
		List<UcuserOrguser> ls = this.ucuserOrguserDao.list(UcuserOrguser.class, criterion, Order.asc("uu_id"));
		return ls;
	}

	@Override
	public List<TeacherRole> getTeacherRoles() {
		// TODO Auto-generated method stub
		//Criterion criterion = Restrictions.conjunction();
		Criterion criterion = Restrictions.and(Restrictions.eq("is_del", false));
		List<TeacherRole> ls = this.teacherRoleDao.list(TeacherRole.class, criterion, Order.asc("tech2role_id"));
		return ls;
	}

	@Override
	public List<Role> getRoles() {
		// TODO Auto-generated method stub
		//Criterion criterion = Restrictions.conjunction();
		Criterion criterion = Restrictions.and(Restrictions.eq("is_del", false));
		List<Role> ls = this.roleDao.list(Role.class, criterion, Order.asc("role_id"));
		return ls;
	}

	@Override
	public List<Right> getRights() {
		// TODO Auto-generated method stub
		//Criterion criterion = Restrictions.conjunction();
		Criterion criterion = Restrictions.and(Restrictions.eq("is_del", false));
		List<Right> ls = this.rightDao.list(Right.class, criterion, Order.asc("right_id"));
		return ls;
	}

	@Override
	public OrgUser getOrgUserById(Integer uid) {
		// TODO Auto-generated method stub
		OrgUser user = this.orgUserDao.get(OrgUser.class, uid);
		return user;
	}

	@Override
	public void getOrgUserWithPaging(String loginname, String org_id, String mobile, Integer identity, Paging<OrgUser> pages) {
		// TODO Auto-generated method stub
		Long totalcount = this.orgUserDao.getTotalCount(loginname, org_id, mobile, identity);
		pages.setTotal(totalcount);
		this.orgUserDao.getOrgUserWithPaging(loginname, org_id, mobile, identity, pages);
	}

	@Override
	public void updateOrgUser(OrgUser user) {
		// 修改手机号时同步修改登录名
		if(StringUtils.isNotBlank(user.getUser_mobile())){
			user.setUser_loginname(user.getUser_mobile());
		}
		this.orgUserDao.saveOrUpdate(user);
	}

	@Override
	public void SaveOrgUser(OrgUser user) {
		// TODO Auto-generated method stub1
		this.orgUserDao.saveOrUpdate(user);
	}

	@Override
	public OrgUser getOrgUserByLoginName(String user_loginname, Integer org_id) {
		// TODO Auto-generated method stub
		OrgUser user = this.orgUserDao.getUserByLoginName(user_loginname, org_id);
		return user;
	}

	@Override
	public List<TechRange> getTechRanges() {
		// TODO Auto-generated method stub
		//Criterion criterion = Restrictions.conjunction();
		Criterion criterion = Restrictions.and(Restrictions.eq("is_del", 0));
		List<TechRange> ls = this.techRangeDao.list(TechRange.class, criterion, Order.desc("tr_id"));
		return ls;
	}

	@Override
	public List<RoleLabel> getRoleLabels() {
		// TODO Auto-generated method stub
		//Criterion criterion = Restrictions.conjunction();
		Criterion criterion = Restrictions.and(Restrictions.eq("is_del", false));
		return this.roleLabelDao.list(RoleLabel.class, criterion, Order.desc("rl_id"));
	}

	@Override
	public Map<String, String> sendValidCode(String phone) {
		// TODO Auto-generated method stub
		String codestr = "";

		ActCode code = this.actCodeDao.getValidCodeByMobile(phone);
		if (code != null) {
			// result.setMessage();
			codestr = code.getCode();
		} else {
			codestr = makecode(phone);
			ActCode codeitem = new ActCode();
			codeitem.setCode(codestr);
			codeitem.setExpire((long) 600);
			codeitem.setInserttime(new java.util.Date());
			codeitem.setMobile(phone);
			this.actCodeDao.save(codeitem);
		}
		String msg = "验证码：" + codestr + ",请勿将验证码透露给他人，如非本人操作请忽略。";

		Map<String, String> ret = new HashMap<String, String>();
		String message;
		try {
			// message = this.util.SMSsend(phone, msg);
			message = this.util.schoolsms(phone, msg, "校园云办公");
		} catch (Exception e) {
			message = "失败";
		}
		ret.put("codestr", codestr);
		ret.put("message", message);

		return ret;
	}

	private String makecode(String userid) {
		// String str = "abcdefghijkmnopqrstuvwsyzABCDEFGHIJKMNOPQRSTUVWSYZ0123456789";
		String str = "0123456789";
		String code = "";
		for (int i = 0; i < 6; i++) {
			Random rand = new Random();
			int j = rand.nextInt(str.length());
			code += str.substring(j, j + 1);
		}
		if (actCodeDao.isExit(userid, code)) {
			return makecode(userid);
		} else {
			return code;
		}
	}

	@Override
	public void validCode(String phone, String code) throws EsbException {
		// TODO Auto-generated method stub
		ActCode acode = this.actCodeDao.getValidCodeByMobile(phone);
		if (!(acode != null && code.equals(acode.getCode()))) {
			throw new EsbException(EnumException.user_invalid_sms_code);
		} else {
			acode.setExpire(0L);
			this.actCodeDao.saveOrUpdate(acode);
		}
	}

	@Override
	public void reSetPassword(String phone, String code, String passwd, Integer org_id) throws EsbException {
		// TODO Auto-generated method stub
//		ActCode acode = this.actCodeDao.getValidCodeByMobile(phone);
//		if (!(acode != null && code.equals(acode.getCode()))) {
//			throw new EsbException(EnumException.user_expire_sms_code);
//		}
		List<OrgUser> users = this.orgUserDao.getListByPhone(phone, org_id);
		if (users.size() > 0) {
			for (OrgUser orgUser : users) {
				orgUser.setUser_loginpass(Utils.MD5(orgUser.getUser_salt() + ":" + passwd));
				orgUser.setUpdate_time(new Date());
				this.orgUserDao.update(orgUser);
			}
//			acode.setExpire(0L);
//			this.actCodeDao.saveOrUpdate(acode);
		} else {
			throw new EsbException(EnumException.user_name_not_exist);
		}
	}

	@Override
	public void modifyPwd(Integer user_id, Integer type, String oldpwd, String passwd) throws EsbException {
		// TODO Auto-generated method stub
		switch (type) {
			case 0:
				OrgUser orguser = this.getOrgUserById(user_id);
				if (orguser != null && Utils.MD5(orguser.getUser_salt() + ":" + oldpwd).equals(orguser.getUser_loginpass())) {
					orguser.setUser_loginpass(Utils.MD5(orguser.getUser_salt() + ":" + passwd));
					orguser.setUpdate_time(new Date());
					this.orgUserDao.update(orguser);
				} else {
					throw new EsbException(EnumException.user_password_wrong);
				}
				break;
			case 1:
				UcUser ucuser = this.ucUserDao.get(UcUser.class, user_id);
				if (ucuser != null && Utils.MD5(ucuser.getUc_salt() + ":" + oldpwd).equals(ucuser.getUc_loginpass())) {
					ucuser.setUc_loginpass(Utils.MD5(ucuser.getUc_salt() + ":" + passwd));
					ucuser.setUpdate_time(new Date());
					this.ucUserDao.update(ucuser);
				} else {
					throw new EsbException(EnumException.user_password_wrong);
				}
				break;

			default:
				break;
		}

	}

	@Override
	public Teacher getTeacherByUserId(Integer user_id) {
		// TODO Auto-generated method stub
		Teacher teacher = this.teacherDao.getByUserId(user_id);
		return teacher;
	}

	@Override
	public List<UcuserOrguser> getOrgByUCID(String uc_id) {
		// TODO Auto-generated method stub
		return ucUserDao.getOrgByUCID(uc_id);
	}

	@Override
	public List<OrgUser> getOrgUserByMobile(String phone) {
		// TODO Auto-generated method stub
		Criterion criterion = Restrictions.and(Restrictions.eq("user_mobile", phone)).add(Restrictions.eq("is_del", false));
		List<OrgUser> ls = this.orgUserDao.list(OrgUser.class, criterion, Order.desc("user_id"));
		return ls;
	}

	@Override
	public void RemoveUserAccountByUaId(Integer ua_id) {
		// TODO Auto-generated method stub
		UserAccount ua = this.userAccountDao.get(UserAccount.class, ua_id);
		if (ua != null) {
			ua.setUdid("");
			ua.setUpdated_time(new Date());
			this.userAccountDao.saveOrUpdate(ua);
		}
	}

	@Override
	public void deleteOrgUser(Integer user_id) {
		// TODO Auto-generated method stub
		this.orgUserDao.deleteById(OrgUser.class, user_id);
	}

	@Override
	public boolean getUserIsExist(String loginName, String phone, int identity, int org_id) {
		// TODO Auto-generated method stub
		return this.teacherDao.getUserIsExist(loginName, phone, identity, org_id);
	}

	@Override
	public boolean isExist(String loginName, Integer identity, Integer org_id) {
		// TODO Auto-generated method stub
		return this.orgUserDao.getUserIsExist(loginName, identity, org_id);
	}

	@Override
	public OrgUser getUserForManager(Integer org_id) {
		try {
			return ucUserDao.getUserForManager(org_id);
		} catch (Exception e) {
			logger.error("OrgUser getUserForManager erroe");
			return null;
		}
	}

	@Override
	public OrgUser createManagerForOrg(Organization org) {

		PassWordCreater pwCreater = new PassWordCreater();
		String password = pwCreater.createPassWord(20);
		String loginname = pwCreater.createPassWord(20);
		String salt = pwCreater.createSalt();
		String md5Password = Utils.MD5(salt + ":" + password);
		OrgUser user = new OrgUser();
		user.setOrganization(org);
		user.setOrg_id(org.getOrg_id());
		user.setIs_current(Constant.CURRENTYES);
		user.setIdentity(Constant.IDENTITY_MANAGER);
		user.setUser_idnumber(password);
		user.setUser_salt(salt);
		user.setUser_loginname(loginname);
		user.setUser_loginpass(md5Password);
		user.setIs_del(false);
		user.setInsert_time(new Date());
		user.setUpdate_time(new Date());
		orgUserDao.save(user);
		return user;
	}

	@Override
	public void updateTeacher(Teacher t) {
		teacherDao.update(t);
	}

	@Override
	public void deleteOrgUser(List<OrgUser> list) {
		orgUserDao.deleteOrgUser(list);
	}

	@Override
	public void setRoles(OrgUser user) {
		// TODO Auto-generated method stub
		List<Role> data = new ArrayList<Role>();
		if (redisTeacherRoleDao != null) {
			Map<String, TeacherRole> maps = this.redisTeacherRoleDao.getByTeacherId(user.getUser_id().toString());
			if (maps != null) {
				for (String roleid : maps.keySet()) {
					Role role = this.redisRoleDao.getByRoleId(roleid);
					if (role != null && role.getOrg_id().equals(user.getOrg_id())) {
						data.add(role);
					}
				}
			}
		}
		user.setRoles(data);
	}

	@Override
	public UserAccount getUserAccountById(Integer ua_id) {
		// TODO Auto-generated method stub
		UserAccount ua = this.userAccountDao.get(UserAccount.class, ua_id);
		return ua;
	}

	@Override
	public void saveUserAccount(UserAccount ua) {
		// TODO Auto-generated method stub
		this.userAccountDao.saveOrUpdate(ua);
	}

	@Override
	public OrgUser getAdminByOrgid(Integer org_id) {
		// TODO Auto-generated method stub
		OrgUser user = null;
		if (org_id != null) {
			user = this.orgUserDao.getAdminByOrgid(org_id);
		}
		return user;
	}

	@Override
	public void messageChannelsend(Message<String> message) {
		// TODO Auto-generated method stub
		this.messageChannel.send(message);
	}

	@Override
	public Map<String, OrgUser> login(String login_name, String login_pass, String identity) {
		// TODO Auto-generated method stub
		Map<String, OrgUser> users = this.redisOrgUserDao.getUserByLoginName(login_name);
		Map<String, OrgUser> temps = new LinkedHashMap<String, OrgUser>();
		if (users != null && users.size() > 0) {
			for (OrgUser user : users.values()) {
				String uidentity = user.getIdentity().toString();
				if ("99".equals(uidentity)) {
					uidentity = "1";
				}
				if (identity == null || uidentity.equals(identity)) {
					String passwordMd5 = Utils.MD5(user.getUser_salt() + ":" + login_pass);
					if (user.getIs_del().equals(false) && passwordMd5.equals(user.getUser_loginpass())) {
						LSHelper.processInjection(user);
						temps.put(user.getOrg_id().toString(), user);
					}
				}
			}
		}
		return temps;
	}

	@Override
	public OrgUser wxlogin(String login_name, String login_pass, String identity, Integer org_id) {
		// TODO Auto-generated method stub
		OrgUser user = this.redisOrgUserDao.getUserByLoginNameWX(login_name, org_id, identity);
		if (user != null) {
			String uidentity = user.getIdentity().toString();
			if ("99".equals(uidentity)) {
				uidentity = "1";
			}
			if (identity == null || uidentity.equals(identity)) {
				String passwordMd5 = Utils.MD5(user.getUser_salt() + ":" + login_pass);
				if (user.getIs_del().equals(false) && passwordMd5.equals(user.getUser_loginpass())) {
					LSHelper.processInjection(user);
					return user;
				}
			}
		}
		return null;
	}

	@Override
	public List<TeacherRole> getTeacherRolesOfSql() {
		// TODO Auto-generated method stub
		return teacherRoleDao.getTeacherRolesOfSql();
	}

	@Override
	public Map<String, String> sendValidCodeWX(String phone) {
		// TODO Auto-generated method stub
		String codestr = "";

		ActCode code = this.actCodeDao.getValidCodeByMobile(phone);
		logger.info("code:"+code);
		if (code != null) {
			// result.setMessage();
			codestr = code.getCode();
		} else {
			codestr = makecode(phone);
			ActCode codeitem = new ActCode();
			codeitem.setCode(codestr);
			codeitem.setExpire((long) 600);
			codeitem.setInserttime(new java.util.Date());
			codeitem.setMobile(phone);
			this.actCodeDao.save(codeitem);
		}
		String msg = "验证码：" + codestr + ",请勿将验证码透露给他人，如非本人操作请忽略。";

		Map<String, String> ret = new HashMap<String, String>();
		String message;
		try {
			// message = this.util.SMSsend(phone, msg);
			message = this.util.wxsms(phone, msg);
			
		} catch (Exception e) {
			message = "失败";
		}
		ret.put("codestr", codestr);
		ret.put("message", message);

		return ret;
	}

	@Override
	public Paging<OrgUser> getOrgStudentByParentWithPaging(String card, String org_id, String mobile, Paging<OrgUser> pages) {
		return orgUserDao.getOrgStudentByParentWithPaging(card, org_id, mobile, pages);
	}

	@Override
	public OrgUser getOrgUser(int org_id, int identity, String user_mobile) {
		return orgUserDao.getOrgUser(org_id, identity, user_mobile);
	}

	@Override
	public List<OrgUser> getOrguserByOrgId(int org_id) {
		return orgUserDao.getOrguserByOrgId(org_id);
	}

	@Override
	public Paging<OrgUser> getOrgStudentByCard(String card, String org_id,
			Paging<OrgUser> pages) {
		return orgUserDao.getOrgStudentByCard(card, org_id, pages);
	}
}
