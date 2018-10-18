package com.bestinfo.dao.h5.comparison;

import com.bestinfo.bean.h5.comparison.StatProvinceOther;
import java.util.List;
import org.springframework.jdbc.core.JdbcTemplate;

/**
 *
 * @author Administrator
 */
public interface IProvincesSalesComparisonDao {
    public List<StatProvinceOther> getStatProvinceOtherList(JdbcTemplate jdbcTemplate,Integer start,Integer end);
}
