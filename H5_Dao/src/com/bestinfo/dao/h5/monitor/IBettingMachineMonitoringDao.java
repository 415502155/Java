package com.bestinfo.dao.h5.monitor;

import com.bestinfo.bean.h5.monitor.CityTmnCount;
import com.bestinfo.bean.h5.monitor.EveryCityTmnSaleInfo;
import com.bestinfo.bean.h5.monitor.HMonitorGame;
import com.bestinfo.bean.h5.monitor.TmnMaxSaleCountTop5;
import java.util.List;
import java.util.Map;
import org.springframework.jdbc.core.JdbcTemplate;

/**
 * 投注机监控
 * @author Administrator
 */
public interface IBettingMachineMonitoringDao {
    /**
     * 广东省22地区的装机总量
     * @param jdbcTemplate
     * @return 
     */
    public List<CityTmnCount> getCityTmnCount(JdbcTemplate jdbcTemplate);    
    public List<Map> getCitySalesTop5(JdbcTemplate jdbcTemplate);
    public int getTotalTmnCount(JdbcTemplate jdbcTemplate);
    public List<EveryCityTmnSaleInfo> getEveryCityTmnSaleInfos(JdbcTemplate jdbcTemplate,Integer city_id);
    public List<TmnMaxSaleCountTop5> getTmnMaxSaleCountTop5(JdbcTemplate jdbcTemplate);  
    public int getActiveTmnCount(JdbcTemplate jdbcTemplate);
}
