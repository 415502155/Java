package com.bestinfo.dao.encoding;

import com.bestinfo.bean.encoding.CityInfo;
import java.util.List;
import org.springframework.jdbc.core.JdbcTemplate;

/**
 *
 * @author hhhh
 */
public interface ICityInfo {
    /**
     * 查询二级城市编号数据列表
     * @param jdbcTemplate
     * @return 列表数据
     */
    public List<CityInfo> selectCityInfo(JdbcTemplate jdbcTemplate);
    
    /**
     * 修改二级城市编号数据
     * @param jdbcTemplate
     * @param ci
     * @return 
     */
    public int updateCityInfo(JdbcTemplate jdbcTemplate, CityInfo ci) ;
    
    /**
     * 批量修改二级城市编号数据
     * @param jdbcTemplate
     * @param ciList
     * @return 
     */
    public int updateBatchCityInfo(JdbcTemplate jdbcTemplate, final List<CityInfo> ciList);

}
