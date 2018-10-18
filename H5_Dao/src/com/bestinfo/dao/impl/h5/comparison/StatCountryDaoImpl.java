package com.bestinfo.dao.impl.h5.comparison;

import com.bestinfo.bean.h5.comparison.StatCountry;
import com.bestinfo.dao.h5.comparison.IStatCountryDao;
import com.bestinfo.dao.impl.BaseDaoImpl;

import java.util.List;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

/**
 * 体彩福彩销量对比
 * @author Administrator
 */
@Repository
public class StatCountryDaoImpl extends BaseDaoImpl implements IStatCountryDao{

    private static final String QUERY_STAT_COUNTRY ="select * from t_stat_country where year_id between ? and ? ";

    @Override
    public List<StatCountry> getStatCountryList(JdbcTemplate jdbcTemplate, Integer start, Integer end) {
        return this.queryForList(jdbcTemplate, QUERY_STAT_COUNTRY, new Object[]{start,end}, StatCountry.class);
    }
}
