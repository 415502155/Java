package com.bestinfo.dao.impl.encoding;

import com.bestinfo.bean.encoding.PayType;
import com.bestinfo.dao.encoding.IPayType;
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
public class PayTypeImpl extends BaseDaoImpl implements IPayType {

    /**
     * 查询账户扣款类型数据列表sql
     */
    private static final String GET_PAYTYPE_LIST = "select  pay_type_id,pay_type_name,work_status  from t_pay_type";
    /**
     * 根据账户扣款类型编号更新证件类型sql
     */
    private static final String UPDATE_PAYTYPE = "update t_pay_type set pay_type_name = ?,work_status = ? where pay_type_id = ?";

    /**
     * 查询账户扣款类型的数据列表
     *
     * @param jdbcTemplate
     * @return 列表数据集合
     */
    @Override
    public List<PayType> selectPayType(JdbcTemplate jdbcTemplate) {
        return this.queryForList(jdbcTemplate, GET_PAYTYPE_LIST, null, PayType.class);
    }

    /**
     * 修改账户扣款类型数据
     *
     * @param jdbcTemplate
     * @param pt
     * @return
     */
    @Override
    public int updatePayType(JdbcTemplate jdbcTemplate, PayType pt) {
        int result;
        try {
            result = jdbcTemplate.update(UPDATE_PAYTYPE, new Object[]{pt.getPay_type_name(), pt.getWork_status(), pt.getPay_type_id()});
        } catch (DataAccessException e) {
            logger.error("pay_type_id:"+pt.getPay_type_id(), e);
            SQLException sqle = (SQLException) e.getCause();
            logger.error("Error code: " + sqle.getErrorCode());
            logger.error("SQL state: " + sqle.getSQLState());
            result = -1;
        }
        return result;
    }
}
