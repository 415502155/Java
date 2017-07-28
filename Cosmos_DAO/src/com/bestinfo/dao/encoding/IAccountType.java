/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bestinfo.dao.encoding;

import com.bestinfo.bean.encoding.AccountType;
import java.util.List;
import org.springframework.jdbc.core.JdbcTemplate;

/**
 *
 * @author user
 */
public interface IAccountType {

    /**
     * 查询账户类型的数据列表
     *
     * @param jdbcTemplate
     * @return 列表数据集合
     */
    public List<AccountType> selectAccountType(JdbcTemplate jdbcTemplate);

    /**
     * 修改账户类型数据
     *
     * @param jdbcTemplate
     * @param at
     * @return
     */
    public int updateAccountType(JdbcTemplate jdbcTemplate, AccountType at);
}
