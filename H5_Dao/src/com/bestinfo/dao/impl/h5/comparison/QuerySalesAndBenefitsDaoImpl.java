package com.bestinfo.dao.impl.h5.comparison;

import com.bestinfo.bean.h5.comparison.StatProvince;
import com.bestinfo.dao.h5.comparison.ISalesAndBenefitsDao;
import com.bestinfo.dao.impl.BaseDaoImpl;

import java.util.List;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

/**
 * 某年到某年销量及公益金列表
 * @author Administrator
 */
@Repository
public class QuerySalesAndBenefitsDaoImpl extends BaseDaoImpl implements ISalesAndBenefitsDao{

    private static final String QUERY_GetStatProvinceList_LIST ="select * from t_stat_province where year_id between ? and ? ";
    @Override
    public List<StatProvince> getStatProvinceList(JdbcTemplate jdbcTemplate,Integer start,Integer end) {
            logger.info(start+"---"+end);
        try {
                return  this.queryForList(jdbcTemplate, QUERY_GetStatProvinceList_LIST, new Object[]{start,end}, StatProvince.class);
            } catch (Exception e) {
                logger.error("getStatProvinceList ex :", e);
                return null;
            }
    
    }
}
