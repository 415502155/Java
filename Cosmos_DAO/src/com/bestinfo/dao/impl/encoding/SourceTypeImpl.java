package com.bestinfo.dao.impl.encoding;

import com.bestinfo.bean.encoding.SourceType;
import com.bestinfo.dao.encoding.ISourceType;
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
public class SourceTypeImpl extends BaseDaoImpl implements ISourceType {

    /**
     * 查询资金来源类型数据列表sql
     */
    private static final String GET_SOURCETYPE_LIST = "select  source_type,trade_type,source_name,work_status  from t_source_type";
    /**
     * 根据资金来源类型编号更新资金来源类型sql
     */
    private static final String UPDATE_SOURCETYPE = "update t_source_type set trade_type = ?, source_name = ?, work_status = ?  where source_type = ?";

    /**
     * 查询资金来源类型的数据列表
     *
     * @param jdbcTemplate
     * @return 列表数据集合
     */
    @Override
    public List<SourceType> selectSourceType(JdbcTemplate jdbcTemplate) {
        return this.queryForList(jdbcTemplate, GET_SOURCETYPE_LIST, null, SourceType.class);
    }

    /**
     * 修改资金交易类型数据
     *
     * @param jdbcTemplate
     * @param st
     * @return
     */
    @Override
    public int updateSourceType(JdbcTemplate jdbcTemplate, SourceType st) {
        int result;
        try {
            result = jdbcTemplate.update(UPDATE_SOURCETYPE, new Object[]{
                st.getTrade_type(),
                st.getSource_name(),
                st.getWork_status(),
                st.getSource_type()
            });
        } catch (DataAccessException e) {
            logger.error("Source_type:"+st.getSource_type(), e);
            SQLException sqle = (SQLException) e.getCause();
            logger.error("Error code: " + sqle.getErrorCode());
            logger.error("SQL state: " + sqle.getSQLState());
            result = -1;
        }
        return result;
    }
}
