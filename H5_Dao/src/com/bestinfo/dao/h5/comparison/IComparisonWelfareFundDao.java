/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.bestinfo.dao.h5.comparison;

import com.bestinfo.bean.h5.comparison.HTStatProvince;
import java.util.List;
import org.springframework.jdbc.core.JdbcTemplate;

/**
 * 公益金对比 接口
 * 
 * @author Administrator
 */
public interface IComparisonWelfareFundDao {
    /**
     * 获取公益金对比数据
     * @param jdbcTemplate
     * @param yearBegin
     * @param yearEnd
     * @return 
     */
    public List<HTStatProvince> getComparisonWelfareFund(JdbcTemplate jdbcTemplate, Integer yearBegin, Integer yearEnd);
}
