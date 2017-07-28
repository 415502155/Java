package com.bestinfo.dao.encoding;

import com.bestinfo.bean.encoding.DistrictInfo;
import java.util.List;
import org.springframework.jdbc.core.JdbcTemplate;

/**
 *
 * @author hhhh
 */
public interface IDistrictInfo {
    /**
     * 查询三级区县编号数据列表
     * @param jdbcTemplate
     * @return 列表数据
     */
    public List<DistrictInfo> selectDistrictInfo(JdbcTemplate jdbcTemplate);
    
    /**
     * 修改三级区县编号数据
     * @param jdbcTemplate
     * @param di
     * @return 
     */
    public int updateDistrictInfo(JdbcTemplate jdbcTemplate, DistrictInfo di) ;

}
