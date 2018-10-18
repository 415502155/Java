package com.bestinfo.dao.impl.business;

import com.bestinfo.bean.business.DealerPrivilege;
import com.bestinfo.dao.business.IDealerPrivilegeDao;
import com.bestinfo.dao.impl.BaseDaoImpl;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

/**
 * 代销商游戏特权
 */
@Repository
public class DealerPrivilegeDaoImpl extends BaseDaoImpl implements IDealerPrivilegeDao {

    private static final String SELECT_DEALERPRIVILEGE_LIST = "select dealer_id,game_id,service_proportion,game_permit from t_dealer_privilege";
    private static final String UPDATE_DEALERPRIVILEGE = "update t_dealer_privilege set game_id = ?, service_proportion = ?, game_permit = ? where dealer_id=?";
    private static final String INSERT_DEALERPRIVILEGE = "insert into t_dealer_privilege(dealer_id,game_id,service_proportion,game_permit) values(?,?,?,?)";
    private static final String DELETE_DEALERPRIVILEGE_BY_DEALERID = "delete from t_dealer_privilege where dealer_id=?";

    private static final String MERGE_DERLER_PRIVILEGE = "merge into t_dealer_privilege tp"
            + " using (select ? dealer_id, ? game_id from dual) src"
            + " on (tp.dealer_id = src.dealer_id and tp.game_id = src.game_id)"
            + " when matched then"
            + "  update set service_proportion = ?, game_permit = ?"
            + " when not matched then"
            + "  insert"
            + "    (dealer_id, game_id, service_proportion, game_permit)"
            + "  values"
            + "    (?, ?, ?, ?)";

    /**
     * 查询代销商游戏特权的数据列表
     *
     * @param jdbcTemplate
     * @return 列表数据集合
     */
    @Override
    public List<DealerPrivilege> selectDealerPrivilege(JdbcTemplate jdbcTemplate) {
        return this.queryForList(jdbcTemplate, SELECT_DEALERPRIVILEGE_LIST, null, DealerPrivilege.class);
    }

    /**
     * 修改代销商游戏特权数据
     *
     * @param jdbcTemplate
     * @param dp
     * @return
     */
    @Override
    public int updateDealerPrivilege(JdbcTemplate jdbcTemplate, DealerPrivilege dp) {
        int result;
        try {
            result = jdbcTemplate.update(UPDATE_DEALERPRIVILEGE, new Object[]{
                dp.getGame_id(),
                dp.getService_proportion(),
                dp.getGame_permit(),
                dp.getDealer_id()
            });
        } catch (DataAccessException e) {
            logger.error("Dealer_id:" + dp.getDealer_id() + ",Game_id:" + dp.getGame_id(), e);
            SQLException sqle = (SQLException) e.getCause();
            logger.error("Error code: " + sqle.getErrorCode());
            logger.error("SQL state: " + sqle.getSQLState());
            result = -1;
        }
        return result;
    }

    /**
     * 批量插入代销商游戏特权数据
     *
     * @param jdbcTemplate
     * @param dpList
     * @return 0-成功 -1(失败)
     */
    @Override
    public int insertDealerPrivilege(JdbcTemplate jdbcTemplate, final List<DealerPrivilege> dpList) {
        try {
            jdbcTemplate.batchUpdate(INSERT_DEALERPRIVILEGE, new BatchPreparedStatementSetter() {
                @Override
                public void setValues(PreparedStatement ps, int i) throws SQLException {
                    DealerPrivilege dealerPrivilege = dpList.get(i);
                    ps.setString(1, dealerPrivilege.getDealer_id());
                    ps.setInt(2, dealerPrivilege.getGame_id());
                    ps.setBigDecimal(3, dealerPrivilege.getService_proportion());
                    ps.setInt(4, dealerPrivilege.getGame_permit());
                }

                @Override
                public int getBatchSize() {
                    return dpList.size();
                }
            });
            return 0;
        } catch (Exception e) {
            logger.error("", e);
            return -1;
        }
    }

    /**
     * 根据代销商编号删除它下的所有代销商游戏特权数据
     *
     * @param jdbcTemplate
     * @param dealerId
     * @return
     */
    @Override
    public int deleteDealerPrivilegeByDealerId(JdbcTemplate jdbcTemplate, String dealerId) {
        int result;
        try {
            result = jdbcTemplate.update(DELETE_DEALERPRIVILEGE_BY_DEALERID, new Object[]{dealerId});
        } catch (DataAccessException e) {
            logger.error("dealerId:" + dealerId, e);
            SQLException sqle = (SQLException) e.getCause();
            logger.error("Error code: " + sqle.getErrorCode());
            logger.error("SQL state: " + sqle.getSQLState());
            result = -1;
        }
        return result;
    }

    /**
     * 批量修改代理商游戏特权数据
     *
     * @param jdbcTemplate
     * @param privilegeList
     * @return
     */
    @Override
    public int mergeDealerPrivilege(JdbcTemplate jdbcTemplate, final List<DealerPrivilege> privilegeList) {
        try {
            jdbcTemplate.batchUpdate(MERGE_DERLER_PRIVILEGE, new BatchPreparedStatementSetter() {

                @Override
                public void setValues(PreparedStatement ps, int i) throws SQLException {
                    int count = 1;
                    DealerPrivilege dealerPrivilege = privilegeList.get(i);
                    ps.setString(count++, dealerPrivilege.getDealer_id());
                    ps.setInt(count++, dealerPrivilege.getGame_id());
                    ps.setBigDecimal(count++, dealerPrivilege.getService_proportion());
                    ps.setInt(count++, dealerPrivilege.getGame_permit());
                    ps.setString(count++, dealerPrivilege.getDealer_id());
                    ps.setInt(count++, dealerPrivilege.getGame_id());
                    ps.setBigDecimal(count++, dealerPrivilege.getService_proportion());
                    ps.setInt(count++, dealerPrivilege.getGame_permit());
                }

                @Override
                public int getBatchSize() {
                    return privilegeList.size();
                }
            });
            return 0;
        } catch (Exception e) {
            logger.error("merge dealer privilege error", e);
            return -1;
        }
    }

}
