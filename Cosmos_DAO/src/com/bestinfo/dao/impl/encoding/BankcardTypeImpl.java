package com.bestinfo.dao.impl.encoding;

import com.bestinfo.bean.encoding.BankcardType;
import com.bestinfo.dao.encoding.IBankcardType;
import com.bestinfo.dao.impl.BaseDaoImpl;
import java.sql.SQLException;
import java.util.List;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

/**
 * 银行类型
 *
 * @author user
 */
@Repository
public class BankcardTypeImpl extends BaseDaoImpl implements IBankcardType {

    /**
     * 查询银行卡类型数据列表sql
     */
    private static final String GET_BANKCARDTYPE_LIST = "select  card_type,card_type_name,work_status  from t_bankcard_type";
    /**
     * 根据银行卡类型编号更新银行卡类型sql
     */
    private static final String UPDATE_BANKCARDTYPE = "update t_bankcard_type set card_type_name = ?,work_status = ?  where card_type = ?";

    /**
     * 查询银行卡类型的数据列表
     *
     * @param jdbcTemplate
     * @return 列表数据集合
     */
    @Override
    public List<BankcardType> selectBankcardType(JdbcTemplate jdbcTemplate) {
        return this.queryForList(jdbcTemplate, GET_BANKCARDTYPE_LIST, null, BankcardType.class);
    }

    /**
     * 修改银行卡类型数据
     *
     * @param jdbcTemplate
     * @param bt
     * @return
     */
    @Override
    public int updateBankcardType(JdbcTemplate jdbcTemplate, BankcardType bt) {
        int result;
        try {
            result = jdbcTemplate.update(UPDATE_BANKCARDTYPE, new Object[]{
                bt.getCard_type_name(),
                bt.getWork_status(),
                bt.getCard_type()
            });
        } catch (DataAccessException e) {
            logger.error("Card_type:"+bt.getCard_type(), e);
            SQLException sqle = (SQLException) e.getCause();
            logger.error("Error code: " + sqle.getErrorCode());
            logger.error("SQL state: " + sqle.getSQLState());
            result = -1;
        }
        return result;
    }
}
