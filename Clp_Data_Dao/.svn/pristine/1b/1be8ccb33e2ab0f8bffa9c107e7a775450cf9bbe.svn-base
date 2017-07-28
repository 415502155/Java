package com.bestinfo.dao.impl.union;

import com.bestinfo.bean.union.UnionPronviceStatistics;
import com.bestinfo.dao.impl.BaseDaoImpl;
import com.bestinfo.dao.union.IUnionPronviceStatisticsDao;
import java.sql.SQLException;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import java.math.BigDecimal;

/**
 *
 * @author LH
 */
@Repository
public class UnionPronviceStatisticsDaoImpl extends BaseDaoImpl implements IUnionPronviceStatisticsDao {

    /**
     * 根据system_id,game_id,draw_id为主键，有记录则更新，无记录则插入，以无纸化上传的数据为准
     */
    private static final String INSERT_UPS = "merge into T_union_pronvice_statistics ups using (select ? system_id, ? game_id, ? draw_id from dual ) src on (ups.system_id=src.system_id and ups.game_id=src.game_id and ups.draw_id=src.draw_id) when matched then update set province_id=?,sale_money=?,sale_ticket_num=?,sale_stake_num=?,undo_money=?,undo_ticket_num=?,undo_stake_num=?,cash_money=?,cash_ticket_num=?,cash_stake_num=?,agent_fee_deduct=?,agent_fee=?,cash_fee=? when not matched then insert (province_id,system_id,game_id,draw_id,sale_money,sale_ticket_num,sale_stake_num,undo_money,undo_ticket_num,undo_stake_num,cash_money,cash_ticket_num,cash_stake_num,agent_fee_deduct,agent_fee,cash_fee) values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

    private static final String QUERY_SALE_MONEY = "select sale_money from t_union_pronvice_statistics where system_id=? and game_id=? and draw_id=?";

    /**
     * 查询销售额
     * @param jdbcTemplate
     * @param systemId
     * @param gameId
     * @param drawId
     * @return 
     */
    @Override
    public BigDecimal querySaleMoney(JdbcTemplate jdbcTemplate, int systemId, int gameId, int drawId) {
        try {
            return (BigDecimal) jdbcTemplate.queryForObject(QUERY_SALE_MONEY, new Object[]{systemId, gameId, drawId}, BigDecimal.class);
        } catch (org.springframework.dao.DataAccessException e) {
            return null;
        }
    }

    /**
     * 新增记录
     *
     * @param jdbcTemplate
     * @param ups
     * @return 成功-受影响的记录数 失败-(-1)
     */
    @Override
    public int insert(JdbcTemplate jdbcTemplate, UnionPronviceStatistics ups) {
        int result;
        try {
            result = jdbcTemplate.update(INSERT_UPS, new Object[]{
                ups.getSystem_id(),
                ups.getGame_id(),
                ups.getDraw_id(),
                ups.getProvince_id(),
                ups.getSale_money(),
                ups.getSale_ticket_num(),
                ups.getSale_stake_num(),
                ups.getUndo_money(),
                ups.getUndo_ticket_num(),
                ups.getUndo_stake_num(),
                ups.getCash_money(),
                ups.getCash_ticket_num(),
                ups.getCash_stake_num(),
                ups.getAgent_fee_deduct(),
                ups.getAgent_fee(),
                ups.getCash_fee(),
                ups.getProvince_id(),
                ups.getSystem_id(),
                ups.getGame_id(),
                ups.getDraw_id(),
                ups.getSale_money(),
                ups.getSale_ticket_num(),
                ups.getSale_stake_num(),
                ups.getUndo_money(),
                ups.getUndo_ticket_num(),
                ups.getUndo_stake_num(),
                ups.getCash_money(),
                ups.getCash_ticket_num(),
                ups.getCash_stake_num(),
                ups.getAgent_fee_deduct(),
                ups.getAgent_fee(),
                ups.getCash_fee()
            });
        } catch (DataAccessException e) {
            logger.error("", e);
            SQLException sqle = (SQLException) e.getCause();
            logger.error("Error code: " + sqle.getErrorCode());
            logger.error("SQL state: " + sqle.getSQLState());
            result = -1;
        }
        return result;
    }

}
