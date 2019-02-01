package sng.service.impl;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import sng.dao.UserRegisterDao;
import sng.entity.UserRegister;
import sng.service.UserRegisterService;
import sng.util.Constant;

/**
 * @类 名： UserRegisterServiceImpl
 * @功能描述： 用户注册服务实现类
 * @作者信息： LiuYang
 * @创建时间： 2018年12月12日下午1:58:45
 */
@Component
@Transactional("transactionManager")
public class UserRegisterServiceImpl implements UserRegisterService {

	@Autowired
	private UserRegisterDao userRegisterDao;

	@Override
	public UserRegister getById(Integer user_register_id) {
		return userRegisterDao.get(UserRegister.class, user_register_id);
	}

	@Override
	public void save(UserRegister ur) {
		ur.setInsertTime(new Date());
		ur.setIsDel(Constant.IS_DEL_NO);
		userRegisterDao.save(ur);
	}

	@Override
	public void update(UserRegister ur) {
		ur.setUpdateTime(new Date());
		userRegisterDao.update(ur);
	}

	@Override
	public int getUserByOpenid(String org_id, String openid) {
		// TODO Auto-generated method stub
		return userRegisterDao.getUserByOpenid(org_id, openid);
	}

}
