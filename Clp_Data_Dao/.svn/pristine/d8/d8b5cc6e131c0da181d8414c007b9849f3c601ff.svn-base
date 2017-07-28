package com.bestinfo.dao.encoding;

import com.bestinfo.bean.encoding.TradeType;
import java.util.List;
import org.springframework.jdbc.core.JdbcTemplate;

/**
 * 资金交易类型
 *
 * @author user
 */
public interface ITradeType {

    /**
     * 查询资金交易类型的数据列表
     *
     * @param jdbcTemplate
     * @return 列表数据集合
     */
    public List<TradeType> selectTradeType(JdbcTemplate jdbcTemplate);

    /**
     * 根据是否批量处理查询出符合条件的数据
     *
     * @param jdbcTemplate
     * @param batch_item
     * @return
     */
    public List<TradeType> selectTradeTypeListByBatchItem(JdbcTemplate jdbcTemplate, int batch_item);

    /**
     * 修改资金交易类型数据
     *
     * @param jdbcTemplate
     * @param tt
     * @return
     */
    public int updateTradeType(JdbcTemplate jdbcTemplate, TradeType tt);
}
