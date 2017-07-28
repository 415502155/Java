/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bestinfo.dao.encoding;

import com.bestinfo.bean.encoding.PayType;
import java.util.List;
import org.springframework.jdbc.core.JdbcTemplate;

/**
 *
 * @author user
 */
public interface IPayType {

    /**
     * 查询账户扣款类型的数据列表
     *
     * @param jdbcTemplate
     * @return 列表数据集合
     */
    public List<PayType> selectPayType(JdbcTemplate jdbcTemplate);

    /**
     * 修改账户扣款类型数据
     *
     * @param jdbcTemplate
     * @param pt
     * @return
     */
    public int updatePayType(JdbcTemplate jdbcTemplate, PayType pt);
}
