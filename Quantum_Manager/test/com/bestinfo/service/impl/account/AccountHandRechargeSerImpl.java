/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bestinfo.service.impl.account;

import com.bestinfo.dao.account.IAccountHandRechargeDao;
import com.bestinfo.service.account.IAccountHandRechargeSer;
import javax.annotation.Resource;
import org.apache.log4j.Logger;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

/**
 * 投注机手工充值service
 *
 * @author shange
 */
@Service
public class AccountHandRechargeSerImpl implements IAccountHandRechargeSer {

    private final Logger logger = Logger.getLogger(AccountHandRechargeSerImpl.class);
    @Resource
    private IAccountHandRechargeDao accountHandRechargeDao;
    @Resource
    private JdbcTemplate termJdbcTemplate;

    /**
     * 投注机手工充值
     *
     * @param terminalId 投注机编号
     * @param rechargeMoney 充值金额
     * @return 充值结果
     */
    @Override
    public int procHandRecharge(String terminalId, double rechargeMoney) {
        try {
            return accountHandRechargeDao.procHandRecharge(termJdbcTemplate, terminalId, rechargeMoney);
        } catch (Exception e) {
            logger.error("procHandRecharge serviceImpl error :", e);
            return -1;
        }
    }

}
