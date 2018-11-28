package sng.dao;

import java.util.List;

import sng.entity.Account;

/**
 * 账户信息DAO接口
 * Title: AccountDao
 * Description: 
 * Company: 世纪伟业
 * @author Liuyang
 * @date 2018年1月24日下午2:37:57
 */
public interface AccountDao  extends BaseDao<Account>{

	/**
	 * 获取支付信息
	 * @param org_id
	 * @return
	 */
	List<Account> getList(Integer org_id);

	/**
	 * 获取账户信息
	 * @param accId
	 * @return
	 */
	Account getById(Integer accId);

	/**
	 * 根据CID获取account信息
	 * @param cid
	 * @return
	 */
	Account getByCId(Integer cid);

	/**
	 * @Title : getAccount 
	 * @功能描述: 获取默认的学费账户
	 * @返回类型：Account 
	 * @throws ：
	 */
	Account getAccount(Integer org_id, Integer cam_id, Integer yes);

}
