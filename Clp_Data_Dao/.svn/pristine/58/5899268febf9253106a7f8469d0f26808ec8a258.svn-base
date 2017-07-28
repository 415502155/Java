/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bestinfo.dao.encoding;

import com.bestinfo.bean.encoding.AccountRechargeType;
import java.util.List;
import org.springframework.jdbc.core.JdbcTemplate;

/**
 * 账户充值操作
 *
 * @author user
 */
public interface IAccountRechargeType {

    /**
     * 查询账户充值方式的数据列表
     *
     * @param jdbcTemplate
     * @return 列表数据集合
     */
    public List<AccountRechargeType> selectAccountRechargeType(JdbcTemplate jdbcTemplate);

    /**
     * 修改账户充值方式数据
     *
     * @param jdbcTemplate
     * @param accountRechargeType
     * @return
     */
    public int updateAccountRechargeType(JdbcTemplate jdbcTemplate, AccountRechargeType accountRechargeType);
}
