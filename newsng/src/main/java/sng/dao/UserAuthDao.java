package sng.dao;

import java.util.List;

import sng.entity.UserAuthentication;
import sng.entity.UserRegister;
import sng.pojo.ParamObj;
import sng.util.Paging;
/**
 * @desc 用户中心--用户详情认证
 * @author magq
 * @data 2018-11-27
 * @version 1.0
 */
public interface UserAuthDao extends BaseDao<UserAuthentication>{
	/**
	 * 查询用户详情认证信息
	 * @param obj
	 * @return
	 */
	public Paging queryUserAuthInfo(ParamObj obj);
	
	
	
	/**
	 * 查询用户详情认证信息
	 * @param obj
	 * @return
	 */
	public List queryUserAuthListInfo(ParamObj obj);


	/**
	 * 更新用户详情认证信息
	 * @param obj
	 * @return
	 */
	public int  updateUserAuthInfo(UserRegister userAuth);

	/**
	 * 删除用户详情认证信息
	 * @param obj
	 * @return
	 */
	public int delUserAuthInfo(ParamObj obj);

	/**
	 * 计算该机构下该身份证号有几条已认证用户数据
	 * 用于判断身份证号在当前机构下是否已经认证
	 */
	public int countIdNumber(String idNumber, Integer org_id, String name, Integer identity);

}
