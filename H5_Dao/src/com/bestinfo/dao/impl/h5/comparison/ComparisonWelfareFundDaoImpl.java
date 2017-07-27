/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bestinfo.dao.impl.h5.comparison;

import com.bestinfo.bean.h5.comparison.HTStatProvince;
import com.bestinfo.dao.h5.comparison.IComparisonWelfareFundDao;
import com.bestinfo.dao.impl.BaseDaoImpl;
import java.util.List;
import org.apache.log4j.Logger;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

/**
 * 公益金对比实现类
 *
 * @author Administrator
 */
@Repository
public class ComparisonWelfareFundDaoImpl extends BaseDaoImpl implements IComparisonWelfareFundDao {

    private static final Logger logger = Logger.getLogger(ComparisonWelfareFundDaoImpl.class);

    private static final String GET_WELFARE_FUND = "SELECT YEAR_ID"
            + "  ||'年' as year_id,"
            + "  ROUND(SALE_MONEY   /100000000, 2) AS SALE_MONEY,"
            + "  ROUND(WELFARE_MONEY/100000000, 2) AS WELFARE_MONEY"
            + " FROM T_STAT_PROVINCE"
            + " WHERE YEAR_ID BETWEEN ? AND ?"
            + " ORDER BY YEAR_ID ASC";

    @Override
    public List<HTStatProvince> getComparisonWelfareFund(JdbcTemplate jdbcTemplate, Integer yearBegin, Integer yearEnd) {
        return queryForList(jdbcTemplate,GET_WELFARE_FUND, new Object[]{yearBegin, yearEnd}, HTStatProvince.class);
    }

}
