package com.bestinfo.dao.h5.comparison;
import com.bestinfo.bean.h5.comparison.StatProvince;
import java.util.List;
import org.springframework.jdbc.core.JdbcTemplate;


/**
 * 
 * @author Administrator
 */
public interface ISalesAndBenefitsDao {
    /***
     * 
     * @param jdbcTemplate
     * @param start
     * @param end
     * @return 
     */
    public List<StatProvince> getStatProvinceList(JdbcTemplate jdbcTemplate,Integer start,Integer end);
}
