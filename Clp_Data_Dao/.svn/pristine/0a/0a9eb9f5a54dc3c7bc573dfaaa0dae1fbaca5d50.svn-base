package com.bestinfo.dao.business;

import com.bestinfo.bean.business.DealerInfo;
import java.util.List;
import org.springframework.jdbc.core.JdbcTemplate;

/**
 * 代销商操作类
 *
 * @author
 */
public interface IDealerInfoDao {

    /**
     * 注册代销商
     *
     * @param jdbcTemplate
     * @param dealer
     * @return
     */
    public int insertDealerInfo(JdbcTemplate jdbcTemplate, DealerInfo dealer);

    /**
     * 查询所有的代销商信息
     *
     * @param jdbcTemplate
     * @return
     */
    public List<DealerInfo> getAllDealerInfo(JdbcTemplate jdbcTemplate);

    /**
     * 修改代销商信息数据
     *
     * @param jdbcTemplate
     * @param di
     * @return
     */
    public int updateDealerInfo(JdbcTemplate jdbcTemplate, DealerInfo di);

    /**
     * 根据代销商Id查询代销商信息
     *
     * @param jdbcTemplate
     * @param dealerId
     * @return
     */
    public DealerInfo getDealerInfoById(JdbcTemplate jdbcTemplate, String dealerId);

}
