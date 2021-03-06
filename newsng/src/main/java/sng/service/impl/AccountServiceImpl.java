package sng.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import sng.dao.AccountDao;
import sng.entity.Account;
import sng.service.AccountService;

@Component
@Transactional("transactionManager")
public class AccountServiceImpl implements AccountService {

	@Autowired
	private AccountDao accountDao;

	@Override
	public List<Account> getList(Integer org_id) {
		return accountDao.getList(org_id);
	}

	@Override
	public Account getById(Integer accId) {
		return accountDao.getById(accId);
	}

	@Override
	public Account getByCId(Integer cid) {
		return accountDao.getByCId(cid);
	}

	@Override
	public Account getByOrgId(Integer org_id, Integer type, Integer accType) {
		return accountDao.getByOrgId(org_id,type,accType);
	}
	
}
