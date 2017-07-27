package com.bestinfo.dao.impl.h5.comparison;

import com.bestinfo.bean.h5.comparison.StatProvinceOther;
import com.bestinfo.dao.h5.comparison.IProvincesSalesComparisonDao;
import com.bestinfo.dao.impl.BaseDaoImpl;

import java.util.List;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

/**
 * 兄弟省份销量对比
 * @author Administrator
 */
@Repository
public class ProvinceSalesComparisonDaoImpl extends BaseDaoImpl implements IProvincesSalesComparisonDao{
    /**
     * 兄弟省份销量对比
     */
    private static final String QUERY_StatProvinceOtherList_LIST ="select year_id, ROUND(sales44/100000000, 2) as sales44, ROUND(sales37/100000000, 2) as sales37, ROUND(sales32/100000000, 2) as sales32,ROUND(sales33/100000000, 2) as sales33 from T_stat_Province_other  where YEAR_ID between ? and ? order by year_id asc";

    @Override
    public List<StatProvinceOther> getStatProvinceOtherList(JdbcTemplate jdbcTemplate, Integer start, Integer end) {
        try {
            return  this.queryForList(jdbcTemplate, QUERY_StatProvinceOtherList_LIST,  new Object[]{start,end},StatProvinceOther.class);
        } catch (Exception e) {
            logger.error("getStatProvinceOtherList ex :", e);
            return null;
        } 
    }

}
