package com.bestinfo.dao.ticket;

import com.bestinfo.bean.ticket.TicketBet;
import java.util.List;
import org.springframework.jdbc.core.JdbcTemplate;

/**
 * 彩票-投注数据明细(T_ticket_bet)
 */
public interface ITicketBetDao {

    /**
     * 根据game_id、draw_id、keno_draw_id统计票数、金额
     *
     * @param jdbcTemplate
     * @param game_id
     * @param draw_id
     * @param keno_draw_id
     * @return
     */
    public TicketBet getSumTicketBet(JdbcTemplate jdbcTemplate, int game_id, int draw_id, int keno_draw_id);

    /**
     * 历史票查询
     *
     * @param jdbcTemplate
     * @param ticket_cipher
     * @return
     */
    public TicketBet getTicketBet(JdbcTemplate jdbcTemplate, String ticket_cipher);

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
    public TicketBet getTicketBet(JdbcTemplate jdbcTemplate, int gameId, int tkDraw, int serialNum, int terminalId);

    /**
     * 投注存储过程--吕昌嵘
     *
     * @param jdbcTemplate
     * @param tb
     * @return
     */
    public int ticketBet(JdbcTemplate jdbcTemplate, TicketBet tb);

    /**
     * 历史票查询
     *
     * @param jdbcTemplate
     * @param ticket_cipher
     * @return
     */
//     public TicketBet getHistoryTicketBet(JdbcTemplate jdbcTemplate,String ticket_cipher);
    /**
     * 票数，金额统计
     *
     * @param jdbcTemplate
     * @param game_id
     * @param draw_id
     * @param dealer_id
     * @return
     */
    public TicketBet getSumTicketAndMoneyBet(JdbcTemplate jdbcTemplate, int game_id, int draw_id, String dealer_id);

    /**
     * 根据彩民Id查询当前期是否买过票
     *
     * @param jdbcTemplate
     * @param gamblerId
     * @return
     */
    public List<TicketBet> getTicketBetByGamblerId(JdbcTemplate jdbcTemplate, String gamblerId);

}
