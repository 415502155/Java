/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bestinfo.dao.encoding;

import com.bestinfo.bean.encoding.BankcardType;
import java.util.List;
import org.springframework.jdbc.core.JdbcTemplate;

/**
 *
 * @author user
 */
public interface IBankcardType {

    /**
     * 查询银行卡类型的数据列表
     *
     * @param jdbcTemplate
     * @return 列表数据集合
     */
    public List<BankcardType> selectBankcardType(JdbcTemplate jdbcTemplate);

    /**
     * 修改银行卡类型数据
     *
     * @param jdbcTemplate
     * @param bt
     * @return
     */
    public int updateBankcardType(JdbcTemplate jdbcTemplate, BankcardType bt);
}
