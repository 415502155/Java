package com.bestinfo.dao.impl.terminal;

import com.bestinfo.bean.terminal.TmnAutoDeduction;
import com.bestinfo.dao.impl.BaseDaoImpl;
import com.bestinfo.dao.terminal.ITmnAutoDeductionDao;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

/**
 * 投注机-终端自动扣除资金设置表(T_tmn_auto_deduction)
 *
 * @author hhhh
 */
@Repository
public class TmnAutoDeductionDaoImpl extends BaseDaoImpl implements ITmnAutoDeductionDao {

//    private final Logger logger = Logger.getLogger(TmnAutoDeductionDaoImpl.class);
    private static final String INSERT_Tmn_Auto_Deduction = "insert into t_tmn_auto_deduction(terminal_id,trade_type,deduct_money,work_status) values(?,?,?,?)";

    private static final String UPDATE_Tmn_Auto_Deduction = "update t_tmn_auto_deduction set work_status = ? where terminal_id = ?";

    /**
     * 批量插入
     *
     * @param jdbcTemplate
     * @param tadList
     * @return
     */
    @Override
    public int batchAddTmnAutoDeduction(JdbcTemplate jdbcTemplate, final List<TmnAutoDeduction> tadList) {
        try {
            jdbcTemplate.batchUpdate(INSERT_Tmn_Auto_Deduction, new BatchPreparedStatementSetter() {

                @Override
                public void setValues(PreparedStatement ps, int i) throws SQLException {
                    TmnAutoDeduction tad = tadList.get(i);
                    ps.setLong(1, tad.getTerminal_id());
                    ps.setInt(2, tad.getTrade_type());
                    ps.setBigDecimal(3, tad.getDeduct_money());
                    ps.setInt(4, tad.getWork_status());
                }

                @Override
                public int getBatchSize() {
                    return tadList.size();
                }
            });
            return 0;
        } catch (Exception e) {
            logger.error("", e);
            return -1;
        }
    }

    @Override
    public int batchUpdateTmnAutoDeduction(JdbcTemplate jdbcTemplate, final int status, final int terminal_id) {
        try {
            int res = jdbcTemplate.update(UPDATE_Tmn_Auto_Deduction, new Object[]{status, terminal_id});
            if (res < 0) {
              return -2;
            }
            return 0;
        } catch (Exception ex) {
            logger.error(ex);
            return -1;
        }
    }
}
