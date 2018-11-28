package cn.edugate.esb;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.reflect.MethodSignature;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.support.DefaultMultipartHttpServletRequest;

import cn.edugate.esb.entity.OrgUser;
import cn.edugate.esb.entity.Role;
import cn.edugate.esb.entity.UcUser;
import cn.edugate.esb.entity.UcuserOrguser;
import cn.edugate.esb.entity.UserAccount;
import cn.edugate.esb.exception.EnumException;
import cn.edugate.esb.params.WebContext;
import cn.edugate.esb.pojo.Login;
import cn.edugate.esb.redis.dao.RedisLoginDao;
import cn.edugate.esb.redis.dao.RedisOrgUserDao;
import cn.edugate.esb.redis.dao.RedisUcUserDao;
import cn.edugate.esb.redis.dao.RedisUcUserOrguserDao;
import cn.edugate.esb.service.RoleService;
import cn.edugate.esb.service.UserService;
import cn.edugate.esb.util.Util;
import cn.edugate.esb.util.Utils;

public class AuthFilterAdvice {

	@SuppressWarnings("unused")
	private Util util;

	private EduConfig eduConfig;

	private RedisOrgUserDao redisOrgUserDao;

	private RedisUcUserDao redisUcUserDao;

	private RedisUcUserOrguserDao redisUcUserOrguserDao;

	private RedisLoginDao redisLoginDao;
	
	private RoleService roleService;
	
	
	@Autowired
	public void setRoleService(RoleService roleService) {
		this.roleService = roleService;
	}

	@Autowired
	public void setRedisLoginDao(RedisLoginDao redisLoginDao) {
		this.redisLoginDao = redisLoginDao;
	}

	@Autowired
	public void setRedisUcUserOrguserDao(RedisUcUserOrguserDao redisUcUserOrguserDao) {
		this.redisUcUserOrguserDao = redisUcUserOrguserDao;
	}

	private UserService userService;

	@Autowired
	public void setUserService(UserService userService) {
		this.userService = userService;
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
	public void setEduConfig(EduConfig eduConfig) {
		this.eduConfig = eduConfig;
	}

	@Autowired
	public void setUtil(Util util) {
		this.util = util;
	}

	public Object doBefore(ProceedingJoinPoint jp) throws Throwable {
		System.out.println("log PermissionAspect Before method: " + jp.getTarget().getClass().getName() + "."
				+ jp.getSignature().getName());
		Method soruceMethod = getSourceMethod(jp);
		// MethodSignature msig = (MethodSignature) jp.getSignature();

		if (soruceMethod != null) {
			Result<?> result = new Result<>();
			HttpServletResponse response = WebContext.getInstance().getResponse();
			response.setContentType("application/json;charset=utf-8");
			if ("wxlogincode".equals(soruceMethod.getName()) ||"wxredirect".equals(soruceMethod.getName()) ||"wxlogin".equals(soruceMethod.getName()) || "orglogin".equals(soruceMethod.getName()) || "uclogin".equals(soruceMethod.getName())) {
				return jp.proceed();
			}
			HttpServletRequest request = WebContext.getInstance().getRequest();
			String token = request.getParameter("token");
			String udid = request.getParameter("udid");
			String version = "0";
			if (request.getParameter("version") != null) {
				version = request.getParameter("version");
			}
			if (StringUtils.isBlank(token)) {
				// 再从head中获取一次
				token = request.getHeader("token");
				udid = request.getHeader("udid");
			}
			Object[] args = jp.getArgs();
			for (Object arg : args) {
				if (arg != null && arg.getClass() == DefaultMultipartHttpServletRequest.class) {
					MultipartHttpServletRequest multiReq = (MultipartHttpServletRequest) arg;
					token = multiReq.getParameter("token");
					udid = multiReq.getParameter("udid");
					version = multiReq.getParameter("version") != null ? multiReq.getParameter("version") : "0";
				}
			}
			// String org_id = request.getParameter("org_id");
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
								request.setAttribute("user_id", user_id);
								request.setAttribute("type", type);
								request.setAttribute("ua_id", ua.getUa_id());
								request.setAttribute("orgUserId", ua.getUser_id());
								request.setAttribute("orgId", ua.getOrg_id());
								Long addtime = (new Date()).getTime() - Long.parseLong(insertime);
								Login login = new Login();
								login.setType(type);
								login.setUser_id(user_id);
								login.setExtratime(addtime);
								this.redisLoginDao.add(login);
								return jp.proceed();
								/**************************权限判断*****************************/
								/*if(StringUtils.isEmpty(auth)){
									return jp.proceed();
								}else if(checkAuth(auth,user_id,type,org_id)){
									return jp.proceed();
								}else{
									result.setMessage(EnumException.common_permission_denied.getMsg());
									result.setCode(EnumException.common_permission_denied.getValue());
								}*/
								/**************************权限判断*****************************/
							} else {
								result.setMessage(EnumException.common_must_login.getMsg());
								result.setCode(EnumException.common_must_login.getValue());
							}
						} else {
							result.setMessage(EnumException.common_must_login.getMsg());
							result.setCode(EnumException.common_must_login.getValue());
						}
					} else {
						result.setMessage(EnumException.common_token_expire.getMsg());
						result.setCode(EnumException.common_token_expire.getValue());
					}
				} else {
					result.setMessage(EnumException.common_params_error.getMsg());
					result.setCode(EnumException.common_params_error.getValue());
				}
			} else {
				result.setMessage(EnumException.common_params_error.getMsg());
				result.setCode(EnumException.common_params_error.getValue());
			}
			result.setSuccess(false);
			return result;
		} else {

			return jp.proceed();
		}
	}

	/**
	 * 校验用户权限
	 * @param auth 权限CODE
	 * @param user_id 用户主键
	 * @param type 0机构用户  1中心用户
	 * @param org_id 机构主键
	 * @return
	 */
	@SuppressWarnings("unused")
	private boolean checkAuth(String auth,String user_id, String type, String org_id) {
		boolean isPass = false;
		String code = auth.split(":")[0];
		Integer right = Integer.parseInt(auth.split(":")[1]);
		List<Role> roles = new ArrayList<Role>();
		if(type.equals("1")){
			Map<String,UcuserOrguser> orgus = this.redisUcUserOrguserDao.getByUcId(user_id);
			if(orgus!=null){
				for (String orguserid : orgus.keySet()) {
					OrgUser ou = this.redisOrgUserDao.getUserById(orguserid);
					if(org_id.equals(ou.getOrg_id().toString())){
						roles = roleService.getByUserId(ou.getUser_id(),Integer.parseInt(org_id)); 
						break;
					}
				}
			}	
		}else{
			roles = roleService.getByUserId(Integer.parseInt(user_id),Integer.parseInt(org_id)); 
		}
		for (Role role : roles) {
			if(!"".equals(role.getAuthorities())){
				JSONObject authorities = new JSONObject(role.getAuthorities());
				if(authorities.has(code)){
					Integer value = authorities.getInt(code);
					Integer current = right|value;
					if(current.equals(value)){
						isPass = true;
						break;
					}
				}
			}
		}
		return isPass;
	}

	private Method getSourceMethod(JoinPoint jp) {
		Method proxyMethod = ((MethodSignature) jp.getSignature()).getMethod();
		try {
			return jp.getTarget().getClass().getMethod(proxyMethod.getName(), proxyMethod.getParameterTypes());
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		} catch (SecurityException e) {
			e.printStackTrace();
		}
		return null;
	}

}
