package com.bestinfo.dao.impl.ticket;

import com.bestinfo.bean.stat.StatPlayDraw;
import com.bestinfo.bean.ticket.TicketBet;
import com.bestinfo.dao.impl.BaseDaoImpl;
import com.bestinfo.dao.ticket.ITicketBetDao;
import com.bestinfo.define.returncode.TerminalResultCode;
import com.bestinfo.util.TimeUtil;
import java.sql.CallableStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.sql.Types;
import java.util.List;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.CallableStatementCallback;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

/**
 * 彩票-投注数据明细(T_ticket_bet)
 */
@Repository
public class TicketBetDaoImpl extends BaseDaoImpl implements ITicketBetDao {

    /**
     * 根据票面密码 获取投注票信息详情
     */
    private static final String GET_TICKET_BY_CIPHER = "select cipher,sequence_id,game_id,draw_id,keno_draw_id,terminal_id,serial_no,operator_id,play_id,bet_method,bet_time,bet_mode,bet_stakes,bet_money,bet_multiple,bet_line,bet_num,bet_section,bet_line_md5,undo_mark,cur_agent_rate,agent_fee_deduct,scheme_id,dealer_id,gambler_id,gambler_serial_no,partion_id,print_mark,point_card,stake_nums from t_ticket_bet where cipher = ?";

    /**
     * 获取投注数据明细
     */
    private static final String GET_TICKET_BY_GAME_DRAW_SERIAL_TERMINAL = "select cipher,sequence_id,game_id,draw_id,keno_draw_id,terminal_id,serial_no,operator_id,play_id,bet_method,bet_time,bet_mode,bet_stakes,bet_money,bet_multiple,bet_line,bet_num,bet_section,bet_line_md5,undo_mark,cur_agent_rate,agent_fee_deduct,scheme_id,dealer_id,gambler_id,gambler_serial_no,partion_id,print_mark,point_card,stake_nums from t_ticket_bet where game_id = ? and draw_id = ? and serial_no = ? and terminal_id = ?";

    /**
     * 根据game_id、draw_id、keno_draw_id统计票数、金额
     */
    private static final String GET_SUM_BY_GAME_DRAW = "select count(*) ticket_count,nvl(sum(bet_money),0) bet_money from t_ticket_bet where game_id=? and draw_id=? and keno_draw_id=?";

    /**
     * 根据票面密码 获取投注票信息详情
     */
//    public static String GET_TicketBet_ByCipher= "SELECT operator_id,game_id,play_id,draw_id,keno_draw_id,serial_no,"
//            + "cipher,bet_time,bet_method,bet_mode,bet_multiple,bet_money,bet_section,bet_num,bet_line "
//            + "FROM T_ticket_bet where cipher = ?";
    /**
     * 根据game_id、draw_id,dealer_id统计票数、金额
     */
    private static final String GET_DEALER_TICKET_AND_MONEY = "select count(*) ticket_count,nvl(sum(bet_money),0) bet_money from t_ticket_bet where undo_mark=0 and game_id=? and draw_id=? and dealer_id=?";
    /**
     * 根据game_id、draw_id,dealer_id、keno_draw_id统计票数、金额
     */
    private static final String GET_DEALER_KENO_TICKET_AND_MONEY = "select count(*) ticket_count,nvl(sum(bet_money),0) bet_money from t_ticket_bet where undo_mark=0 and game_id=? and draw_id=? and dealer_id=? and keno_draw_id=?";
    private static final String GET_TICKET_BET_BY_GAMBLERID = "select a.sequence_id,a.game_id,a.draw_id,a.keno_draw_id,a.terminal_id,a.serial_no,a.operator_id,a.play_id,a.bet_method,a.bet_time,a.bet_mode,a.bet_stakes,a.bet_money,a.bet_multiple,a.bet_line,a.bet_num,a.bet_section,a.bet_line_md5,a.undo_mark,a.cur_agent_rate,a.agent_fee_deduct,a.scheme_id,a.dealer_id,a.gambler_id,a.gambler_serial_no,a.partion_id,a.print_mark,a.point_card,a.stake_nums from t_ticket_bet a ,t_game_draw_info b where a.game_id=b.game_id and a.draw_id=b.draw_id and b.process_status>=20 and b.process_status<430 and a.gambler_id=?";

    //统计某个玩法的销售数据
    private static final String SUM_BET_BY_PLAYID = "SELECT nvl(SUM(bet_money), 0) sale_money,"
            + "       nvl(count(1), 0) sale_ticket_num"
            + "  FROM t_ticket_bet"
            + " WHERE game_id = ?"
            + "   and draw_id = ?"
            + "   and play_id = ?"
            + "   and undo_mark = 0";

    //统计某个玩法的注销数据
    private static final String SUM_UNDO_BET_BY_PLAYID = "SELECT nvl(SUM(bet_money), 0) undo_money,"
            + "       nvl(count(1), 0) undo_ticket_num"
            + "  FROM t_ticket_bet"
            + " WHERE game_id = ?"
            + "   and draw_id = ?"
            + "   and play_id = ?"
            + "   and undo_mark = 1";

    /**
     * 根据game_id、draw_id、keno_draw_id统计票数、金额
     *
     * @param jdbcTemplate
     * @param game_id
     * @param draw_id
     * @param keno_draw_id
     * @return
     */
    @Override
    public TicketBet getSumTicketBet(JdbcTemplate jdbcTemplate, int game_id, int draw_id, int keno_draw_id) {
        return this.queryForObject(jdbcTemplate, GET_SUM_BY_GAME_DRAW,
                new Object[]{game_id, draw_id, keno_draw_id}, TicketBet.class);
    }

    /**
     * 获取投注票信息详情
     *
     * @param jdbcTemplate
     * @param ticket_cipher
     * @return
     */
    @Override
    public TicketBet getTicketBet(JdbcTemplate jdbcTemplate, String ticket_cipher) {
        return this.queryForObject(jdbcTemplate, GET_TICKET_BY_CIPHER, new Object[]{ticket_cipher}, TicketBet.class);
    }

    /**
     * 根据终端编号、游戏编号、票面期号、彩票流水号查询投注数据明细
     *
     * @param jdbcTemplate
     * @param gameId
     * @param tkDraw
     * @param serialNum
     * @param terminalId
     * @return 投注数据明细对象
     */
    @Override
    public TicketBet getTicketBet(JdbcTemplate jdbcTemplate, int gameId, int tkDraw, int serialNum, int terminalId) {
        return this.queryForObject(jdbcTemplate, GET_TICKET_BY_GAME_DRAW_SERIAL_TERMINAL, new Object[]{gameId, tkDraw, serialNum, terminalId}, TicketBet.class);
    }

    @Override
    public int ticketBet(JdbcTemplate jdbcTemplate, final TicketBet tb) {
        try {
            String sql = "{call p_term_ticket_bet(?,?,?,?,?,  ?,?,?,?,?,  ?,?,?,?,?,  ?,?,?,?,?,?,?,  ?,?,?)}";
            Integer errorcode = (Integer) jdbcTemplate.execute(sql, new CallableStatementCallback() {
                @Override
                public Object doInCallableStatement(CallableStatement cs) throws SQLException, DataAccessException {
                    cs.setString(1, tb.getCipher());
                    cs.setInt(2, tb.getGame_id());
                    cs.setInt(3, tb.getDraw_id());
                    cs.setInt(4, tb.getKeno_draw_id());
                    cs.setInt(5, tb.getTerminal_id());
                    cs.setInt(6, tb.getOperator_id());
                    cs.setInt(7, tb.getPlay_id());
                    cs.setInt(8, tb.getBet_method());
                    Timestamp sqlDate = new Timestamp(tb.getBet_time().getTime());
                    cs.setTimestamp(9, sqlDate);
                    cs.setInt(10, tb.getBet_mode());
                    cs.setBigDecimal(11, tb.getBet_money());
                    cs.setInt(12, tb.getBet_multiple());
                    cs.setString(13, tb.getBet_line());
                    cs.setInt(14, tb.getBet_num());
                    cs.setInt(15, tb.getBet_section());
                    cs.setString(16, tb.getBet_line_md5());
                    cs.setString(17, tb.getGambler_id());
                    cs.setInt(18, tb.getBet_stakes());
                    cs.setInt(19, tb.getDeduct_switch());
                    cs.setBigDecimal(20, tb.getCur_agent_rate());
                    cs.setInt(21, tb.getSerial_no());
                    cs.setString(22, tb.getPlayStakes());
                    cs.registerOutParameter(23, Types.INTEGER);
                    cs.registerOutParameter(24, Types.VARCHAR);
                    cs.registerOutParameter(25, Types.DOUBLE);
                    cs.execute();
                    int errorcode = cs.getInt(23);
                    tb.setAccount_money(cs.getBigDecimal(25));
                    if (errorcode != 0) {
                        logger.error(java.lang.Thread.currentThread().getId() + "\t" + "ticket into db error:" + errorcode + "\t" + cs.getString(24) + "\tbet_time：" + TimeUtil.formatDate_YMDT(tb.getBet_time()) + "\tbet_time2:" + tb.getBet_time());
                    }
                    return Integer.valueOf(errorcode);
                }
            });
            return errorcode;
        } catch (DataAccessException e) {
            logger.error(java.lang.Thread.currentThread().getId() + "\t", e);
            return TerminalResultCode.daoExceError;
        }
    }

    /**
     * 根据游戏编号，期号，运营商统计销售票数与金额
     *
     * @param jdbcTemplate
     * @param game_id
     * @param draw_id
     * @param dealer_id
     * @return
     */
    @Override
    public TicketBet getSumTicketAndMoneyBet(JdbcTemplate jdbcTemplate, int game_id, int draw_id, String dealer_id) {
        return this.queryForObject(jdbcTemplate, GET_DEALER_TICKET_AND_MONEY, new Object[]{game_id, draw_id, dealer_id}, TicketBet.class);
    }

    /**
     * 根据彩民Id查询当前期是否买过票
     *
     * @param jdbcTemplate
     * @param gamblerId
     * @return
     */
    @Override
    public List<TicketBet> getTicketBetByGamblerId(JdbcTemplate jdbcTemplate, String gamblerId) {
        return this.queryForList(jdbcTemplate, GET_TICKET_BET_BY_GAMBLERID, new Object[]{gamblerId}, TicketBet.class);
    }

    /**
     * 根据game_id、draw_id、play_id统计销售金额
     *
     * @param jdbcTemplate
     * @param game_id
     * @param draw_id
     * @param play_id
     * @return
     */
    @Override
    public StatPlayDraw getSumBetByPlayId(JdbcTemplate jdbcTemplate, int game_id, int draw_id, int play_id) {
        return this.queryForObject(jdbcTemplate, SUM_BET_BY_PLAYID,
                new Object[]{game_id, draw_id, play_id}, StatPlayDraw.class);
    }

    /**
     * 根据game_id、draw_id、play_id统计注销金额
     *
     * @param jdbcTemplate
     * @param game_id
     * @param draw_id
     * @param play_id
     * @return
     */
    @Override
    public StatPlayDraw getSumUndoBetByPlayId(JdbcTemplate jdbcTemplate, int game_id, int draw_id, int play_id) {
        return this.queryForObject(jdbcTemplate, SUM_UNDO_BET_BY_PLAYID,
                new Object[]{game_id, draw_id, play_id}, StatPlayDraw.class);
    }

    /**
     * 根据game_id,dealer_id,draw_id,keno_draw_id获得票数、金额
     * @param jdbcTemplate
     * @param game_id
     * @param draw_id
     * @param dealer_id
     * @param kdrawId
     * @return 
     */
    @Override
    public TicketBet getKSumTicketAndMoneyBet(JdbcTemplate jdbcTemplate, int game_id, int draw_id, String dealer_id, int kdrawId) {
        return this.queryForObject(jdbcTemplate, GET_DEALER_KENO_TICKET_AND_MONEY, new Object[]{game_id, draw_id, dealer_id,kdrawId}, TicketBet.class);
    }
}
