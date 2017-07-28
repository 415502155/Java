/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.bestinfo.dao.ticket.d2code;

import com.bestinfo.bean.stat.StatPlayDraw;
import com.bestinfo.bean.ticket.d2code.TicketBetQrCode;
import java.util.List;
import org.springframework.jdbc.core.JdbcTemplate;
/**
 * 彩票-投注数据明细(T_ticket_bet)
 * 
 * @author Administrator
 */
public interface ITicketBetQrCodeDao {
    /**
     * 根据game_id、draw_id、keno_draw_id统计票数、金额
     *
     * @param jdbcTemplate
     * @param game_id
     * @param draw_id
     * @param keno_draw_id
     * @return
     */
    public TicketBetQrCode getSumTicketBet(JdbcTemplate jdbcTemplate, int game_id, int draw_id, int keno_draw_id);

    /**
     * 根据game_id、draw_id、keno_draw_id统计销售票数、金额
     *
     * @param jdbcTemplate
     * @param game_id
     * @param draw_id
     * @param keno_draw_id
     * @return
     */
    public TicketBetQrCode getSaleSumTicketBet(JdbcTemplate jdbcTemplate, int game_id, int draw_id, int keno_draw_id);

    /**
     * 根据game_id、draw_id、keno_draw_id统计注销票数、金额
     *
     * @param jdbcTemplate
     * @param game_id
     * @param draw_id
     * @param keno_draw_id
     * @return
     */
    public TicketBetQrCode getUndoSumTicketBet(JdbcTemplate jdbcTemplate, int game_id, int draw_id, int keno_draw_id);

    /**
     * 根据game_id、draw_id、play_id统计销售金额
     *
     * @param jdbcTemplate
     * @param game_id
     * @param draw_id
     * @param play_id
     * @return
     */
    public StatPlayDraw getSumBetByPlayId(JdbcTemplate jdbcTemplate, int game_id, int draw_id, int play_id);

    /**
     * 根据game_id、draw_id、play_id统计注销金额
     *
     * @param jdbcTemplate
     * @param game_id
     * @param draw_id
     * @param play_id
     * @return
     */
    public StatPlayDraw getSumUndoBetByPlayId(JdbcTemplate jdbcTemplate, int game_id, int draw_id, int play_id);

    /**
     * 历史票查询
     *
     * @param jdbcTemplate
     * @param ticket_cipher
     * @return
     */
    public TicketBetQrCode getTicketBet(JdbcTemplate jdbcTemplate, String ticket_cipher);

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
    public TicketBetQrCode getTicketBet(JdbcTemplate jdbcTemplate, int gameId, int tkDraw, int serialNum, int terminalId);

    /**
     *
     * @param jdbcTemplate
     * @param cipher
     * @param gameId
     * @param terminalId
     * @return
     */
    public TicketBetQrCode getTicketBet(JdbcTemplate jdbcTemplate, String cipher, int gameId, int terminalId);

    /**
     * 根据游戏编号、票面期号、玩法编号查询投注数据明细
     *
     * @param jdbcTemplate
     * @param gameId
     * @param drawId
     * @param playId
     * @return
     */
    public List<TicketBetQrCode> getTicketBetByPlay(JdbcTemplate jdbcTemplate, int gameId, int drawId, int playId);

    /**
     * 投注存储过程--吕昌嵘
     *
     * @param jdbcTemplate
     * @param tb
     * @return
     */
    public int ticketBet(JdbcTemplate jdbcTemplate, TicketBetQrCode tb);

    /**
     * 历史票查询
     *
     * @param jdbcTemplate
     * @param ticket_cipher
     * @return
     */
//     public TicketBetQrCode getHistoryTicketBet(JdbcTemplate jdbcTemplate,String ticket_cipher);
    /**
     * 票数，金额统计
     *
     * @param jdbcTemplate
     * @param game_id
     * @param draw_id
     * @param dealer_id
     * @return
     */
    public TicketBetQrCode getSumTicketAndMoneyBet(JdbcTemplate jdbcTemplate, int game_id, int draw_id, String dealer_id);

    /**
     * 根据彩民Id查询当前期是否买过票
     *
     * @param jdbcTemplate
     * @param gameId
     * @param drawId
     * @param playId
     * @return
     */
    public List<TicketBetQrCode> getTicketBetByGamblerId(JdbcTemplate jdbcTemplate, String gamblerId);

    /**
     * 根据游戏查询相应的二维码属性值
     *
     * @param jdbcTemplate
     * @param game_id
     * @return
     */
    public List<TicketBetQrCode> getD2codeInfo(JdbcTemplate jdbcTemplate, int game_id);

}
