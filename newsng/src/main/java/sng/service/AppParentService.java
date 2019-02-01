package sng.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import sng.pojo.Result;

/**
 * @desc 移动端---家长
 * @author magq
 * @data 2018-12-11
 * @version 1.0
 */
@Service
@Transactional
public interface AppParentService {
	
	/**
	 * 注册手机号是否存在
	 * @param org_id 机构ID
	 * @param telNum 电话号码
	 * @return
	 */
	public Result isTelExist(Integer org_id,String telNum);
	
	
	/**
	 * 用户注册插入数据库之前判断orgId和openId存在
	 * @param org_id 机构ID
	 * @param openId 微信ID
	 * @return
	 */
	public Result isOrgIdAndOpenIdExist(Integer org_id,String openId);
	
	/**
	 * 更新用户信息---手机号码、密码、家长学生关系
	 * @param user_register_id
	 * @param org_id
	 * @param mobile
	 * @param password
	 * @param relation
	 * @return
	 */
	public Result updateCustomInfo(Integer user_register_id,Integer org_id,
			String mobile, String password,Integer relation);
	

	/**
	 * 更新用户信息--修改手机号码--验证输入密码是否正确
	 * @param user_register_id
	 * @param password
	 * @return
	 */
	public boolean validatePassWord(Integer user_register_id,String password);
	

}
