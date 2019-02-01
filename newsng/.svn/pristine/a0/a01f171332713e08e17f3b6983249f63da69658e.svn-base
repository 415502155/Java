package sng.controller.web;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.core.type.TypeReference;

import sng.constant.Consts;
import sng.controller.common.BaseAction;
import sng.entity.UserRegister;
import sng.pojo.ParamObj;
import sng.pojo.Result;
import sng.service.UserAuthService;
import sng.service.UserRegisterService;
import sng.util.Constant;
import sng.util.DateUtil;
import sng.util.HttpClientUtil;
import sng.util.JsonUtil;
import sng.util.Utils;

/**
 * @desc 用户中心-详情认证
 * @author magq
 * @data 2018-11-27
 * @version 1.0
 */
@RequestMapping("/userAuth")
@Controller
public class UserAuthController extends BaseAction {

	private Logger log = Logger.getLogger(this.getClass());
	@Autowired
	private UserAuthService userAuthService;
	@Autowired
	private UserRegisterService userRegisterService;

	/**
	 * 查询用户详情认证信息 (线下认证)
	 * 
	 * @param req
	 * @param resp
	 * @param obj
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping("/queryUserDetailsInfo.json")
	public Result queryUserDetailsInfo(HttpServletRequest req, HttpServletResponse resp, ParamObj obj)
			throws Exception {
		Result result = userAuthService.queryUserAuthInfo(obj);
		return result;

	}

	/**
	 * 
	 * @param org_id
	 * @param telephone
	 * @param validateCode
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/isTelExist.json")
	public Result isTelExist(HttpServletRequest req, HttpServletResponse resp, Integer org_id, String telephone,
			String validateCode) throws Exception {
		Result result = new Result();
		if (StringUtils.isBlank(validateCode)) {
			result.setMessage("手机验证码为空");
			result.setSuccess(false);
			return result;
		}
		// 2:验证验证码（老师和家长都要验证）
		String validResult = HttpClientUtil.post(ESB_REQUEST_URL + "validCode",
				"phone=" + telephone + "&code=" + validateCode, "application/x-www-form-urlencoded");
		Result<String> vResult = JsonUtil.getObjectFromJson(validResult, new TypeReference<Result<String>>() {
		});
		if (200 != vResult.getCode()) {
			result.setSuccess(false);
			result.setMessage("验证码错误！");
			return result;
		}
		result = userAuthService.isTelExist(org_id, telephone);

		return result;

	}

	/**
	 * 更新用户详情认证信息 (线下认证)
	 * 
	 * @param req
	 * @param resp
	 * @param obj
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping("/updateUserDetailsInfo.json")
	public Result updateUserDetailsInfo(HttpServletRequest req, HttpServletResponse resp, UserRegister userAuth)
			throws Exception {
		Result result = new Result();

		if (userAuth.getUserRegisterId() == null) {
			result.setMessage("用户注册id为空");
			result.setSuccess(false);
			return result;
		}
		if (StringUtils.isBlank(userAuth.getCardNum())) {
			result.setMessage("要认证的孩子身份证为空");
			result.setSuccess(false);
			return result;
		}
		if (StringUtils.isBlank(userAuth.getStudName())) {
			result.setMessage("要认证的孩子名字为空为空");
			result.setSuccess(false);
			return result;
		}
		if (userAuth.getCardType() == null) {
			result.setMessage("证件类型为空");
			result.setSuccess(false);
			return result;
		}

		// 先判断当前用户的状态
		ParamObj isAuth = new ParamObj();
		isAuth.setUser_id(userAuth.getUserRegisterId());
		List<UserRegister> list_isAuth = userAuthService.queryUserAuthListInfo(isAuth);
		if (list_isAuth != null && !list_isAuth.isEmpty()) {
			UserRegister flag = list_isAuth.get(0);
			if (!userAuth.getCardNum().equals(flag.getCardNum())) {
				ParamObj obj = new ParamObj();
				obj.setOrg_id(userAuth.getOrg_id());
				obj.setCard_num(userAuth.getCardNum());
				List<UserRegister> list = userAuthService.queryUserAuthListInfo(obj);
				if (list != null && !list.isEmpty()) {
					if (list.size() > 0) {
						result.setMessage("此证件号码已认证，不可重复认证.");
						result.setSuccess(false);
						return result;
					}
				}
			}

			if (Consts.AUTH_STATUS_NO == flag.getAuthStatus()) { // 未认证
				// 更新newsng库数据
				result = userAuthService.updateUserAuthInfo(userAuth);
				if (userAuth.getAuthStatus() != null && userAuth.getAuthStatus() == 1 && result.isSuccess()) {

					UserRegister ur = userRegisterService.getById(userAuth.getUserRegisterId());

					userAuthService.certify(String.valueOf(userAuth.getUserRegisterId()), userAuth.getStudName(),
							userAuth.getCardNum(), userAuth.getCardType().intValue(), ur.getOpen_id(), DateUtil.formatDate(userAuth.getBrithday()), Consts.AUTH_TYPE2);
					

					// 发送线下认证通知
					log.info("发送线下认证通知 sendAuthMessage bengin");

					result = userAuthService.sendAuthMessage(userAuth);

					log.info("发送线下认证通知 sendAuthMessage end");

				}
			} else {// 已认证
				result = userAuthService.updateUserAuthInfo(userAuth);
			}
		}
		log.info("------------------result----------------------");
		log.info("getJSESSIONID:" + result.getJSESSIONID() + "<>getMessage:" + result.getMessage());
		log.info("getCode:" + result.getCode() + "<>isSuccess:" + result.isSuccess());
		log.info("------------------result----------------------");
		return result;
	}

	@ResponseBody
	@RequestMapping("/updateUserPhone.json")
	public Result updateUserPhone(HttpServletRequest req, HttpServletResponse resp, UserRegister userAuth)
			throws Exception {
		Result result = userAuthService.updateUserPhone(userAuth);
		log.info("---------------updateUserPhone---------------");
		log.info("user_mobile:" + userAuth.getTelephone());
		log.info("identity:" + userAuth.getIdentity());
		log.info("new_user_mobile:" + userAuth.getNew_user_mobil());
		log.info("org_id:" + userAuth.getOrg_id());
		if (result.isSuccess()) {
			Map<String, Object> map = new HashMap<>();
			map.put("user_mobile", userAuth.getTelephone());
			map.put("identity", userAuth.getIdentity());
			map.put("new_user_mobile", userAuth.getNew_user_mobil());
			map.put("org_id", userAuth.getOrg_id());
			String updateSngMobile = this.callPostUrl(req, "/api/student/updateSngMobile", map);
			result = JsonUtil.getObjectFromJson(updateSngMobile, new TypeReference<Result<String>>() {
			});
			if (200 == result.getCode() && result.isSuccess()) {
				result.setMessage("更新updateSngMobile成功");
				result.setSuccess(true);
			} else {
				result.setMessage("更新updateSngMobile失败");
				result.setSuccess(false);
			}
		}

		return result;
	}

	/**
	 * 删除用户详情认证信息 (线下认证)
	 * 
	 * @param req
	 * @param resp
	 * @param obj
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping("/delUserDetailsInfo.json")
	public Result delUserDetailsInfo(HttpServletRequest req, HttpServletResponse resp, ParamObj obj) throws Exception {
		Result result = userAuthService.delUserAuthInfo(obj);
		return result;
	}

	// -------------------------------------------测试模拟增加用户
	@ResponseBody
	@RequestMapping("/test.json")
	public Result test(HttpServletRequest req, HttpServletResponse resp, int num, int org_id) throws Exception {
		Result result = new Result();
		for (int i = 0; i < num; i++) {
			UserRegister userAuth = new UserRegister();
			String tel = getTel();
			userAuth.setTelephone(tel);
			userAuth.setLoginName(tel);
			userAuth.setOrgId(org_id);
			userAuth.setAuthStatus(0); // 未认证
			userAuth.setIsDel(0);
			String user_salt = Utils.makecode(); // 密码
			userAuth.setLoginSalt(user_salt);
			userAuth.setInsertTime(new Date());
			userAuth.setOpen_id("49975b7d-4fe1-4ee9-a6be-fe91959c8" + i);
			userAuth.setNick_name("nickName" + i);
			userAuth.setLoginPassword(Utils.MD5(user_salt + ":" + "111111"));
			// userAuth.setLoginPassword(Utils.MD5("111111"));
			userAuth.setRelation(Constant.DEFAULT_RELATION);
			userRegisterService.save(userAuth);
		}
		result.setMessage("添加用户" + num + "条");
		result.setSuccess(true);
		return result;
	}

	private String[] telFirst = "134,135,136,137,138,139,150,151,152,157,158,159,130,131,132,155,156,133,153"
			.split(",");

	private String getTel() {
		int index = getNum(0, telFirst.length - 1);
		String first = telFirst[index];
		String second = String.valueOf(getNum(1, 888) + 10000).substring(1);
		String third = String.valueOf(getNum(1, 9100) + 10000).substring(1);
		return first + second + third;
	}

	public int getNum(int start, int end) {
		return (int) (Math.random() * (end - start + 1) + start);
	}
	// -------------------------------------------测试模拟增加用户

}
