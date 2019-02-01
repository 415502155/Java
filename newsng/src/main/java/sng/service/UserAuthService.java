package sng.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import sng.entity.UserAuthentication;
import sng.entity.UserRegister;
import sng.pojo.ParamObj;
import sng.pojo.Result;
import sng.pojo.SessionInfo;
/**
 * @desc 用户中心-用户详情认证
 * @data 2018-11-27
 * @version 1.0
 */
@Service
@Transactional
public interface UserAuthService {

	/**
	 * 查询用户详情认证信息
	 * （线下认证）
	 * @author magq
	 * @param obj
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public Result queryUserAuthInfo(ParamObj obj);
	
	
	public List<UserRegister> queryUserAuthListInfo(ParamObj obj);
	
	public Result updateUserPhone(UserRegister userAuth);

	/**
	 * 判断电话号码是否存在
	 * @param org_id
	 * @param telephone
	 * @return
	 */
	public Result isTelExist(Integer org_id, String telephone);
	
	/**
	 * 更新用户详情认证信息
	 * （线下认证）
	 * @author magq
	 * @param obj
	 * @param req
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public Result updateUserAuthInfo(UserRegister userAuth);

	/**
	 * 删除用户详情认证信息
	 * （线下认证）
	 * @author magq
	 * @param obj
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public Result delUserAuthInfo(ParamObj obj);
	
	/**
	 *  发送线下认证通知
	 * @param info
	 * @param userAuth
	 * @return
	 */
	public Result sendAuthMessage(UserRegister userAuth);
	
	/**
	 * 身份证号在当前机构下是否已经有存在的用户
	 */
	public Boolean isIdNumberExist(String idNumber,Integer org_id,String name,Integer identity);
	/**
	 * 判断当前用户是否认证
	 * @param userAuth
	 * @return
	 */
	public UserRegister IsAuth(UserRegister userAuth);
	/**
	 * 用户认证
	 */
	public UserRegister certify(String user_register_id, String name,String certificateCode,int cardType, String openid, String birthday, int certifyTypeOnline);

	/**
	 * 保存用户认证记录
	 */
	public void saveUserAuthentication(UserAuthentication ua);



}
