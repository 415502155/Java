package sng.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import sng.entity.UserRegister;

/**
 * @类 名： UserRegisterService
 * @功能描述： 用户注册信息Service接口
 * @作者信息： LiuYang
 * @创建时间： 2018年12月12日下午1:56:07
 */
@Service
@Transactional
public interface UserRegisterService {

	/**
	 * 根据用户注册表主键查询用户注册信息
	 */
	UserRegister getById(Integer user_register_id);
	
	/**
	 * 保存用户注册信息
	 */
	void save(UserRegister ur);
	
	/**
	 * 更新用户注册信息
	 */
	void update(UserRegister ur);
	
	
	/**
	 * 根据用户注册表主键查询用户注册信息
	 */
	int getUserByOpenid(String org_id,String openid);
}
