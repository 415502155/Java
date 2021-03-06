package sng.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import sng.entity.Account;

/**
 * 账户信息接口
 * Title: AccountService
 * Description: 
 * Company: 世纪伟业
 * @author Liuyang
 * @date 2018年1月24日下午2:33:14
 */
@Service
@Transactional
public interface AccountService {

	/**
	 * 获取机构下可选的账户信息
	 * @param org_id
	 * @return
	 */
	List<Account> getList(Integer org_id);

	/**
	 * 查询
	 * @param accId
	 * @return
	 */
	Account getById(Integer accId);

	/**
	 * 根据CID查询账户信息
	 * @param cid
	 * @return
	 */
	Account getByCId(Integer cid);

	/**
	 * 根据机构主键查询账户信息
	 */
	Account getByOrgId(Integer org_id,Integer type,Integer accType);
}
