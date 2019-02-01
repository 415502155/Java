package sng.service.impl;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import sng.dao.UserAuthDao;
import sng.dao.UserRegisterDao;
import sng.entity.UserRegister;
import sng.pojo.ParamObj;
import sng.pojo.Result;
import sng.service.AppParentService;
import sng.util.Paging;
import sng.util.Utils;

/**
 * @author magq
 * @version 1.0
 * @desc 移动端---家长service
 * @data 2018-12-11
 */
@Component
@Transactional("transactionManager")
public class AppParentServiceImpl implements AppParentService {
	private Logger log = LoggerFactory.getLogger(getClass());

	@Autowired
	private UserAuthDao userAuthDao;
	
	@Autowired
	private UserRegisterDao userRegisterDao;

	/*
	 * (non-Javadoc)判断注册手机号是否存在
	 * 
	 * @see sng.service.AppParentService#isTelExist(java.lang.String,
	 * java.lang.String)
	 */
	@Override
	public Result isTelExist(Integer org_id, String telNum) {
		Result result = new Result();
		ParamObj obj = new ParamObj();
		obj.setNeedCount(false);
		obj.setUser_mobile(telNum);
		obj.setOrg_id(org_id);
		Paging paging = userAuthDao.queryUserAuthInfo(obj);
		if (paging != null) {
			if (paging.getData() != null && paging.getData().size() > 0) {
				result.setData(paging);
				result.setMessage("当前电话号码已经注册");
				result.setSuccess(false);
			} else {
				result.setSuccess(true);
			}
		}
		return result;
	}

	/*
	 * (non-Javadoc)
	 * @see sng.service.AppParentService#isOrgIdAndOpenIdExist(java.lang.Integer, java.lang.String)
	 */
	@Override
	public Result isOrgIdAndOpenIdExist(Integer org_id, String openId) {
		Result result = new Result();
		ParamObj obj = new ParamObj();
		obj.setOrg_id(org_id);
		obj.setOpenId(openId);
		obj.setNeedCount(false);
		Paging paging = userAuthDao.queryUserAuthInfo(obj);
		if(paging!=null) {
			if (paging.getData() != null && paging.getData().size() > 0) {
				result.setSuccess(true); //已经存在
			}else {
				result.setSuccess(false);//不存在
			}
		}
		return result;
	}
	/*
	 * (non-Javadoc)
	 * @see sng.service.AppParentService#updateCustomInfo(java.lang.Integer, java.lang.Integer, java.lang.String, java.lang.String, java.lang.Integer)
	 */
	@Override
	public Result updateCustomInfo(Integer user_register_id,Integer org_id, String mobile, String password,Integer relation) {
		Result result = new Result();
		UserRegister ur = new UserRegister();
		ur.setOrgId(org_id);
		ur.setTelephone(mobile);
		ur.setUserRegisterId(user_register_id);
		ur.setLoginPassword(password);
	    ur.setRelation(relation);
	    ur.setIsDel(0);
	    ur.setUpdateTime(new Date());
		int count = userAuthDao.updateUserAuthInfo(ur);
		if(count>0) {
			result.setMessage("更新用户信息成功");
			result.setSuccess(true);
		}
		return result;
	}

	/*
	 * (non-Javadoc)
	 * @see sng.service.AppParentService#validatePassWord(java.lang.Integer, java.lang.String)
	 */
	@Override
	public boolean validatePassWord(Integer user_register_id,String password) {
		boolean flag =false;
		UserRegister register = userRegisterDao.get(UserRegister.class, user_register_id);
		if(register!=null) {
			String salt = register.getLoginSalt();
			String pass = register.getLoginPassword();
			if(Utils.MD5(salt+":"+password.trim()).equals(pass)) {
				flag = true;
			}	
		}
		return flag;
	}
	
	

	

}
