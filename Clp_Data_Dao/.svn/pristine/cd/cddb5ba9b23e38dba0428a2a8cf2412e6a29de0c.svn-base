package com.bestinfo.dao.impl.encoding;

import com.bestinfo.bean.encoding.DealerType;
import com.bestinfo.dao.encoding.IDealerType;
import com.bestinfo.dao.impl.BaseDaoImpl;
import java.sql.SQLException;
import java.util.List;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

/**
 *
 * @author user
 */
@Repository
public class DealerTypeImpl extends BaseDaoImpl implements IDealerType {

    /**
     * 查询代销商类型数据列表sql
     */
    public static final String GET_DEALERTYPE_LIST = "select  dealer_type,dealer_type_name,work_status from t_dealer_type";
    /**
     * 根据代销商类型编号更新代销商类型sql
     */
    public static final String UPDATE_DEALERTYPE = "update t_dealer_type set dealer_type_name = ?,work_status = ?  where dealer_type = ?";

    /**
     * 查询代销商类型的数据列表
     *
     * @param jdbcTemplate
     * @return 列表数据集合
     */
    @Override
    public List<DealerType> selectDealerType(JdbcTemplate jdbcTemplate) {
        return this.queryForList(jdbcTemplate, GET_DEALERTYPE_LIST, null, DealerType.class);
    }

    /**
     * 修改代销商类型数据
     *
     * @param jdbcTemplate
     * @param dt
     * @return
     */
    @Override
    public int updateDealerType(JdbcTemplate jdbcTemplate, DealerType dt) {
        int result;
        try {
            result = jdbcTemplate.update(UPDATE_DEALERTYPE, new Object[]{
                dt.getDealer_type_name(),
                dt.getWork_status(),
                dt.getDealer_type()
            });
        } catch (DataAccessException e) {
            logger.error("Dealer_type:"+dt.getDealer_type(), e);
            SQLException sqle = (SQLException) e.getCause();
            logger.error("Error code: " + sqle.getErrorCode());
            logger.error("SQL state: " + sqle.getSQLState());
            result = -1;
        }
        return result;
    }
}
