package com.bestinfo.dao.h5.comparison;
import com.bestinfo.bean.h5.comparison.StatCountry;
import java.util.List;
import org.springframework.jdbc.core.JdbcTemplate;


/**
 * 
 * @author Administrator
 */
public interface IStatCountryDao {
    /***
     * 
     * @param jdbcTemplate
     * @param start
     * @param end
     * @return 
     */
    public List<StatCountry> getStatCountryList(JdbcTemplate jdbcTemplate,Integer start,Integer end);
}
