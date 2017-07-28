/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bestinfo.dao.encoding;

import com.bestinfo.bean.encoding.PrizeType;
import java.util.List;
import org.springframework.jdbc.core.JdbcTemplate;

/**
 *
 * @author user
 */
public interface IPrizeType {

    /**
     * 查询账户奖金返奖类型的数据列表
     *
     * @param jdbcTemplate
     * @return 列表数据集合
     */
    public List<PrizeType> selectPrizeType(JdbcTemplate jdbcTemplate);

    /**
     * 修改账户奖金返奖类型数据
     *
     * @param jdbcTemplate
     * @param pt
     * @return
     */
    public int updatePrizeType(JdbcTemplate jdbcTemplate, PrizeType pt);
}
