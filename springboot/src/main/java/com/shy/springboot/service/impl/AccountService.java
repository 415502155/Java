package com.shy.springboot.service.impl;

import java.util.Map;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.alibaba.fastjson.JSONObject;
import com.shy.springboot.dao.AccountDao;
import com.shy.springboot.entity.Account;
import com.shy.springboot.service.ICommonService;
import com.shy.springboot.utils.ReturnResult;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@Transactional
public class AccountService implements ICommonService {
	
	@Resource
	private AccountDao accountDao;

	@Override
	public ReturnResult execute(JSONObject json) throws Exception {
		
		Integer type = json.getInteger("type");
		ReturnResult result = null;
		switch (type) {
		case 1:
			result = getAccountMapList(json);
			log.info("根據type獲取到指定的service方法 ：getAccountMapList();" );
			break;
		case 2:
			result = getAccountById(json);
			log.info("根據type獲取到指定的service方法 ：getAccountById();" );
			break;
		default:
			break;
		}
		return result;
	}

	public ReturnResult getAccountMapList(JSONObject json) {
		String depositBank = json.getString("name"); 
		String cardNo = json.getString("cardNo");
		Integer page = json.getInteger("page");
		Integer pageSize = json.getInteger("pageSize");
		Map<String, Object> accountMapList = accountDao.getAccountMapList(depositBank, cardNo, page, pageSize);
		return ReturnResult.success(accountMapList);
	}
	
	public ReturnResult getAccountById(JSONObject json) {
		Integer id = json.getInteger("id");
		Account accountById = accountDao.getAccountById(id);
		return ReturnResult.success(accountById);
	}
	
}
