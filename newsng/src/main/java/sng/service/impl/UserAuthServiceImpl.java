package sng.service.impl;

import java.text.ParseException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import sng.constant.Consts;
import sng.dao.UserAuthDao;
import sng.entity.UserAuthentication;
import sng.entity.UserRegister;
import sng.pojo.ParamObj;
import sng.pojo.Result;
import sng.pojo.SessionInfo;
import sng.pojo.WXTemplateMessage;
import sng.service.AppParentService;
import sng.service.MQService;
import sng.service.TokenService;
import sng.service.UserAuthService;
import sng.service.UserRegisterService;
import sng.util.Constant;
import sng.util.DateUtil;
import sng.util.PropertyReader;
import sng.util.HttpClientUtil;
import sng.util.Paging;
import sng.util.SendMessageUtil;

/**
 * @desc 用户中心-用户详情认证
 * @author magq
 * @data 2018-11-27
 * @version 1.0
 */
@Component
@Transactional("transactionManager")
public class UserAuthServiceImpl implements UserAuthService {

	private Logger log = LoggerFactory.getLogger(getClass());

	@Autowired
	private UserAuthDao userAuthDao;
	@Autowired
	private UserRegisterService userRegisterService;
	@Autowired
	private TokenService tokenService;
	@Autowired
	private MQService mqService;
	@Autowired
	private AppParentService appParentService;

	private static String ESB_API_URL = PropertyReader.getProperty("esbNoRequestUrl");

	/**
	 * 手机号码是否存在
	 * @param org_id
	 * @param telephone
	 * @return
	 */
	public Result isTelExist(Integer org_id, String telephone) {
		Result result = new Result();
		if (org_id == null || StringUtils.isBlank(telephone)) {
			result.setMessage("手机号码是否存在请求参数有误");
			result.setSuccess(false);
			return result;
		}
		result = appParentService.isTelExist(org_id, telephone);
		return result;
	}

	/*
	 * 查询用户详情认证信息 (non-Javadoc) （线下认证）
	 * 
	 * @see sng.service.UserAuthService#queryStudentInfo(sng.pojo.ParamObj)
	 */
	@Override
	public Result queryUserAuthInfo(ParamObj obj) {
		// TODO Auto-generated method stub
		Result result = new Result();
		if (obj.getOrg_id() == null) {
			result.setSuccess(false);
			result.setMessage("查询用户认证信息请求参数有误");
			return result;
		}
		Paging map = userAuthDao.queryUserAuthInfo(obj);
		result.setData(map);
		result.setSuccess(true);
		return result;
	}

	/*
	 * 更新用户详情认证信息 (non-Javadoc) （线下认证） newsng库插入用户认证记录表，更新用户注册表
	 * 
	 * @see sng.service.UserAuthService#updateStudentInfo(sng.pojo.ParamObj)
	 */
	@Override
	public Result updateUserAuthInfo(UserRegister userAuth) {
		Result result = new Result();
		if (userAuth == null || userAuth.getUserRegisterId() == null || userAuth.getOrgId() == null) {
			result.setMessage("更新用户认证信息请求参数有误");
			result.setSuccess(false);
			return result;
		}

		userAuth.setUpdateTime(new Date());
		userAuth.setIsDel(0);
		// 更新用户注册表
		int count = userAuthDao.updateUserAuthInfo(userAuth);
		result.setMessage("更新newsng库成功");
		result.setSuccess(true);
		return result;
	}

	@Override
	public Result sendAuthMessage(UserRegister userAuth) {
		Result result = new Result();

		try {
			// 查询是否更新认证类型字段，如果没有更新认证字段则不发送通知
			ParamObj obj = new ParamObj();
			obj.setNeedCount(false);
			obj.setUser_id(userAuth.getUserRegisterId());
			obj.setOrg_id(userAuth.getOrgId());
			Paging paging = userAuthDao.queryUserAuthInfo(obj);
			if (paging != null) {
				if (paging.getData() != null && !paging.getData().isEmpty()) {
					Map<String, Object> map = (Map<String, Object>) paging.getData().get(0);
					Integer auth_status = (Integer) map.get("auth_status");
					String open_id = (String) map.get("open_id");
					if (Consts.AUTH_STATUS_OK == auth_status) {
						Map<String, Object> messageMap = new HashMap<String, Object>();
//						messageMap.put("mobile", userAuth.getTelephone());
//						messageMap.put("content", String.format(Constant.MOBILE_MESSAGE_CERTIFICATION_NOTICE, "已通过",
//								userAuth.getStudName(), userAuth.getCardNum()));
						log.info("准备发送微信信息");
						if (StringUtils.isNotBlank(open_id)) {
							messageMap.put("first", "实名认证审核成功。");
							messageMap.put("keyword1", userAuth.getStudName());
							messageMap.put("keyword2",DateUtil.formatDateTime(userAuth.getUpdateTime()));
							messageMap.put("open_id", open_id);
						}
						try {
							
							sendMessage(userAuth.getOrgId(), messageMap);
						} catch (Exception e) {
							log.error("updateUserAuthInfo>>sendMessage error:", e);
						}
					}
				}
			}
			result.setMessage("更新成功");
			result.setSuccess(true);
		} catch (Exception e) {
			result.setMessage("发送线下认证通知失败");
			result.setSuccess(false);
			log.error("updateUserAuthInfo error:", e);
		}
		return result;
	}

	/*
	 * 删除用户详情认证信息 (non-Javadoc) （线下认证）
	 * 
	 * @see sng.service.UserAuthService#deltudentInfo(sng.pojo.ParamObj)
	 */
	@Override
	public Result delUserAuthInfo(ParamObj obj) {
		Result result = new Result();
		if (obj.getOrg_id() == null || obj.getUser_id() == null) {
			result.setMessage("删除用户认证信息请求参数有误");
			result.setSuccess(false);
			return result;
		}
		int count = userAuthDao.delUserAuthInfo(obj);
		if (count > 0) {
			result.setMessage("删除用户认证信息成功");
			result.setSuccess(true);
		}
		return result;
	}

	/**
	 * 实名认证审核通知
	 * 
	 * @param orgId
	 * @throws Exception
	 */
	public void sendMessage(Integer orgId, Map<String, Object> messageMap) throws Exception {
		try {
			String templateId = tokenService.getSNGWXTemplateId(String.valueOf(orgId),
					Constant.TEMPLATE_NAME_REAL_NAME_CERT_NOTICE);
			log.info("微信模板id:"+templateId);
			if (StringUtils.isNotBlank(templateId)) {// 发送微信
				messageMap.put("templateId", templateId);
				
				WXTemplateMessage templateMessage = SendMessageUtil.makeTemplateMessage(messageMap);
				log.info("获取微信token开始");
				String access_token = tokenService.getAccessTokenByOrg_Id(orgId);
				log.info("获取微信token:"+access_token);
				String str = SendMessageUtil.sendTemplateMessageToWeiXin(access_token, templateMessage);
				log.info("获取微信微信发送完成:"+str); 
			}
			// 发送短信
			//mqService.sendMessage("send_certify_message_exchange", null, messageMap);
		} catch (Exception e) {
			log.error("实名认证审核通知失败:" + e.getMessage());
		}
	}

	@Override
	public Boolean isIdNumberExist(String idNumber, Integer org_id, String name, Integer identity) {
		int num = userAuthDao.countIdNumber(idNumber, org_id, name, identity);
		return num > 0;
	}

	@Override
	public UserRegister certify(String user_register_id, String name, String certificateCode, int cardType, String openid, String birthday,
			int certifyTypeOnline) {
		UserAuthentication ua = new UserAuthentication();
		ua.setAuthStatus(Constant.NO);
		ua.setAuthTime(new Date());
		if (Consts.AUTH_TYPE1 == certifyTypeOnline) {
			ua.setType(Consts.AUTH_TYPE1);
			ua.setTxnAmt(Constant.CERTIFY_FEE);
		} else {
			ua.setType(Consts.AUTH_TYPE2);
			ua.setTxnAmt("0");
		}
		ua.setUserRegisterId(Integer.parseInt(user_register_id));
		saveUserAuthentication(ua);
		UserRegister ur = userRegisterService.getById(Integer.parseInt(user_register_id));
		ur.setCardType(cardType);
		ur.setStudName(name);
		ur.setCardNum(certificateCode);
		ur.setAuthStatus(Constant.YES);
		try {
			if(StringUtils.isBlank(birthday)&&Constant.CARD_TYPE_IDCARD==cardType){
					ur.setBrithday(Constant.date.parse(certificateCode.substring(6, 10) + "-"
							+ certificateCode.substring(10, 12) + "-" + certificateCode.substring(12, 14)));
			}
		} catch (ParseException e1) {
		}
		ur.setOpen_id(openid);
		userRegisterService.update(ur);
		String param = "parent_salt=" + ur.getLoginSalt() + "&parent_loginpass=" + ur.getLoginPassword() + "&openid="
				+ openid + "&org_id=" + ur.getOrgId() + "&stud_idnumber=" + certificateCode + "&user_mobile="
				+ ur.getTelephone() + "&birthday=" + Constant.date.format(ur.getBrithday()) + "&stud_name="
				+ ur.getStudName() + "&sex=0&psex=0&parent_name=" + ur.getStudName() + "家长&relation="
				+ ur.getRelation();
		try {
			HttpClientUtil.post(ESB_API_URL + "addSngStudent", param, "application/x-www-form-urlencoded");
		} catch (Exception e) {
		}
		return null;
	}

	@Override
	public void saveUserAuthentication(UserAuthentication ua) {
		ua.setInsertTime(new Date());
		ua.setIsDel(Constant.IS_DEL_NO);
		userAuthDao.save(ua);
	}

	/**
	 * 判断当前用户是否已经认证
	 */
	@Override
	public UserRegister IsAuth(UserRegister userAuth) {
		UserRegister register = userRegisterService.getById(userAuth.getUserRegisterId());
		return register;
	}

	@Override
	public List<UserRegister> queryUserAuthListInfo(ParamObj obj) {
		List<UserRegister> list = userAuthDao.queryUserAuthListInfo(obj);
		return list;
	}

	@Override
	public Result updateUserPhone(UserRegister userAuth) {

		Result result = new Result();
		int count = userAuthDao.updateUserAuthInfo(userAuth);
		result.setSuccess(true);
		return result;
	}

}
