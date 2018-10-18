/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bestinfo.dao.encoding;

import com.bestinfo.bean.encoding.BankCode;
import java.util.List;
import org.springframework.jdbc.core.JdbcTemplate;

/**
 *
 * @author user
 */
public interface IBankCode {

    /**
     * 查询银行编码表的数据列表
     *
     * @param jdbcTemplate
     * @return 列表数据集合
     */
    public List<BankCode> selectBankCode(JdbcTemplate jdbcTemplate);

    /**
     * 修改银行编码表数据
     *
     * @param jdbcTemplate
     * @param bc
     * @return
     */
    public int updateBankCode(JdbcTemplate jdbcTemplate, BankCode bc);
    
    /**
     * 根据银行编码查询数据量
     * @param jdbcTemplate
     * @param bankId
     * @return 
     */
    public int selectBankCodeNumById(JdbcTemplate jdbcTemplate, String bankId);
}
