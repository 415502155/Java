package com.bestinfo.dao.impl.business;

import com.bestinfo.bean.game.TmnSerialNo;
import com.bestinfo.bean.game.TmnSerialNoG;
import com.bestinfo.dao.business.ITmnSerialNoDao;
import com.bestinfo.dao.impl.BaseDaoImpl;
import com.bestinfo.define.terminal.TmnStatus;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

/**
 * 投注机彩票投注流水号
 *
 * @author chenliping
 */
@Repository
public class TmnSerialNoDaoImpl extends BaseDaoImpl implements ITmnSerialNoDao {

    /**
     * 根据game_id和draw_id获取投注机流水号总和
     */
    private static final String getSumSerialNo = "select nvl(sum(serial_no),0) from t_tmn_serial_no where game_id=? and draw_id=?";

    /**
     * 根据game_id和draw_id获取投注机流水号记录
     */
    private static final String getSerialNoByGameIdAndDrawId = "select game_id,draw_id,terminal_id,serial_no,partion_id from t_tmn_serial_no where game_id = ? and draw_id = ?";

    /**
     * 根据game_id、draw_id、terminal_id获取投注机流水号记录
     */
    private static final String getTmnSerialNoByPrimary = "select game_id,draw_id,terminal_id,serial_no,partion_id from t_tmn_serial_no where game_id = ? and draw_id = ? and terminal_id = ?";

    /**
     * 插入投注机流水号记录
     */
    private static final String insertTmnSerialNo = "insert into t_tmn_serial_no(terminal_id,game_id,draw_id,serial_no,partion_id) values(?,?,?,?,?)";

    /**
     * 电话投注，根据game_id和draw_id获取代销商终端彩票流水号记录
     */
    private static final String getDealerSerialByGameDraw = "select a.terminal_id,"
            + "       a.city_id,"
            + "       a.terminal_phy_id,"
            + "       a.dealer_id,"
            + "       b.game_id,"
            + "       b.draw_id,"
            + "       b.serial_no,"
            + "       c.terminal_condition"
            + "  from t_tmn_info a, t_tmn_serial_no b, t_dealer_info c"
            + " where b.game_id = ?"
            + "   and b.draw_id = ?"
            + "   and a.terminal_status = " + TmnStatus.inuse
            + "   and a.terminal_id = b.terminal_id"
            + "   and a.dealer_id = c.dealer_id";

    /**
     * 电话投注，根据terminal_id获取代销商终端彩票流水号记录
     */
    private static final String getDealerSerialNoByTmn = "select a.terminal_id,"
            + "       a.city_id,"
            + "       a.terminal_phy_id,"
            + "       a.dealer_id,"
            + "       b.game_id,"
            + "       b.draw_id,"
            + "       b.serial_no,"
            + "       c.terminal_condition"
            + "  from t_tmn_info a, t_tmn_serial_no b, t_dealer_info c"
            + " where a.terminal_id = ?"
            + "   and a.terminal_id = b.terminal_id"
            + "   and a.dealer_id = c.dealer_id";
 //获取大厅某个游戏期的所有终端彩票流水号
    private static final String GET_APP_SERIAL_NO = "select b.game_id, b.draw_id, b.terminal_id, b.serial_no"
            + "  from t_tmn_info a, t_tmn_serial_no b"
            + " where b.game_id = ?"
            + "   and b.draw_id = ?"
            + "   and a.terminal_id = b.terminal_id"
            + "   and a.terminal_type = ?"
            + " order by terminal_id";

    /**
     * 获取指定游戏指定期的销售票数据
     *
     * @param jdbcTemplate
     * @param gameid
     * @param drawid
     * @return
     */
    
    @Override
    public long getSumTicketNo(JdbcTemplate jdbcTemplate, int gameid, int drawid) {
        try {
            long re = jdbcTemplate.queryForObject(getSumSerialNo, Integer.class, new Object[]{gameid, drawid});
            return re;
        } catch (DataAccessException e) {
            logger.error("gameid:" + gameid + ",drawid:" + drawid, e);
            return 0;
        }
    }

    /**
     * 得到某个游戏某期，所有终端的彩票流水号
     *
     * @param jdbcTemplate
     * @param gameid
     * @param drawid
     * @return
     */
    @Override
    public List<TmnSerialNo> getSerialNoByGameIdAndDrawId(JdbcTemplate jdbcTemplate, int gameid, int drawid) {
        return this.queryForList(jdbcTemplate, getSerialNoByGameIdAndDrawId, new Object[]{gameid, drawid}, TmnSerialNo.class);
    }

    /**
     * 得到某个终端中，某个游戏某个期的彩票流水号
     *
     * @param jdbcTemplate
     * @param gameid
     * @param drawid
     * @param terminal_id
     * @return
     */
    @Override
    public TmnSerialNo getTmnSerialNoByPrimary(JdbcTemplate jdbcTemplate, int gameid, int drawid, int terminal_id) {
        return this.queryForObject(jdbcTemplate, getTmnSerialNoByPrimary, new Object[]{gameid, drawid, terminal_id}, TmnSerialNo.class);
    }

    /**
     * 批量添加彩票流水号信息
     *
     * @param jdbcTemplate
     * @param tsnList
     * @return
     */
    @Override
    public int batchAddTmnSerialNos(JdbcTemplate jdbcTemplate, final List<TmnSerialNo> tsnList) {
        try {
            jdbcTemplate.batchUpdate(insertTmnSerialNo, new BatchPreparedStatementSetter() {
                @Override
                public void setValues(PreparedStatement ps, int i) throws SQLException {
                    TmnSerialNo tsn = tsnList.get(i);
                    ps.setInt(1, tsn.getTerminal_id());
                    ps.setInt(2, tsn.getGame_id());
                    ps.setInt(3, tsn.getDraw_id());
                    ps.setInt(4, tsn.getSerial_no());
                    ps.setInt(5, tsn.getPartion_id());
                }

                @Override
                public int getBatchSize() {
                    return tsnList.size();
                }
            });
            return 0;
        } catch (Exception e) {
            logger.error("batch add tmn serial nos error.", e);
            return -1;
        }
    }

    @Override
    public List<TmnSerialNoG> getSerialNoForDealer(JdbcTemplate jdbcTemplate, int gameid, int drawid) {
        return this.queryForList(jdbcTemplate, getDealerSerialByGameDraw, new Object[]{gameid, drawid}, TmnSerialNoG.class);
    }

    /**
     * 得到某个终端，所有代销商的彩票流水号
     *
     * @param jdbcTemplate
     * @param terminalId
     * @return
     */
    @Override
    public List<TmnSerialNoG> getTmnSerialNoForDealer(JdbcTemplate jdbcTemplate, int terminalId) {
        return this.queryForList(jdbcTemplate, getDealerSerialNoByTmn, new Object[]{terminalId}, TmnSerialNoG.class);
    } 
         /**
     * 获取大厅某个游戏期的所有终端彩票流水号
     *
     * @param jdbcTemplate
     * @param gameId
     * @param drawId
     * @param tmnType
     * @return
     */
    @Override
    public List<TmnSerialNo> getAppTmnSerialNo(JdbcTemplate jdbcTemplate, int gameId, int drawId, int tmnType) {
        return this.queryForList(jdbcTemplate, GET_APP_SERIAL_NO, new Object[]{gameId, drawId, tmnType}, TmnSerialNo.class);
    }
   
}
