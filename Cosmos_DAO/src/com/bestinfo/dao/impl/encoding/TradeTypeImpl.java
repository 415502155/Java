package com.bestinfo.dao.impl.encoding;

import com.bestinfo.bean.encoding.TradeType;
import com.bestinfo.dao.encoding.ITradeType;
import com.bestinfo.dao.impl.BaseDaoImpl;
import java.sql.SQLException;
import java.util.List;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

/**
 *
 * @author lvchangrong
 */
@Repository
public class TradeTypeImpl extends BaseDaoImpl implements ITradeType {

    /**
     * 查询资金交易类型数据列表sql
     */
    private static final String GET_TRADETYPE_LIST = "select  trade_type,trade_type_name,in_item,batch_item,work_status  from t_trade_type";

    /**
     * 根据是否批量处理查询出符合条件的数据sql
     */
    private static final String GET_TRADETYPE_LIST_BY_ISBATCH = "select  trade_type,trade_type_name,in_item,batch_item,work_status  from t_trade_type where batch_item = ?";

    /**
     * 根据资金交易类型编号更新资金交易类型sql
     */
    private static final String UPDATE_TRADETYPE = "update t_trade_type set trade_type_name = ?,work_status = ?  where trade_type = ?";

    /**
     * 查询资金交易类型的数据列表
     *
     * @param jdbcTemplate
     * @return 列表数据集合
     */
    @Override
    public List<TradeType> selectTradeType(JdbcTemplate jdbcTemplate) {
        return this.queryForList(jdbcTemplate, GET_TRADETYPE_LIST, null, TradeType.class);
    }

    /**
     * 根据是否批量处理查询出符合条件的数据
     *
     * @param jdbcTemplate
     * @param batch_item
     * @return
     */
    @Override
    public List<TradeType> selectTradeTypeListByBatchItem(JdbcTemplate jdbcTemplate, int batch_item) {
        return this.queryForList(jdbcTemplate, GET_TRADETYPE_LIST_BY_ISBATCH, new Object[]{batch_item}, TradeType.class);
    }

    /**
     * 修改资金交易类型数据
     *
     * @param jdbcTemplate
     * @param tt
     * @return
     */
    @Override
    public int updateTradeType(JdbcTemplate jdbcTemplate, TradeType tt) {
        int result;
        try {
            result = jdbcTemplate.update(UPDATE_TRADETYPE, new Object[]{
                tt.getTrade_type_name(),
                tt.getWork_status(),
                tt.getTrade_type()
            });
        } catch (DataAccessException e) {
            logger.error("Trade_type:"+tt.getTrade_type(), e);
            SQLException sqle = (SQLException) e.getCause();
            logger.error("Error code: " + sqle.getErrorCode());
            logger.error("SQL state: " + sqle.getSQLState());
            result = -1;
        }
        return result;
    }
}
