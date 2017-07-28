package com.bestinfo.dao.ticket;

import com.bestinfo.bean.ticket.TicketPrize;
import java.util.List;
import org.springframework.jdbc.core.JdbcTemplate;

/**
 * 中奖票
 */
public interface ITicketPrizeDao {

    /**
     * 根据 票面密码查询中奖票详情
     *
     * @param jdbcTemplate
     * @param cipher
     * @return
     */
    public TicketPrize getTicketPrize(JdbcTemplate jdbcTemplate, String cipher);

    /**
     * 获取中奖数据奖级明细
     *
     * @param jdbcTemplate
     * @param game_id
     * @param draw_id
     * @param keno_draw_id
     * @return
     */
    public List<TicketPrize> getTicketPrizeList(JdbcTemplate jdbcTemplate, int game_id, int draw_id, int keno_draw_id);

    /**
     * 根据游戏和兑奖期号查询兑奖票
     *
     * @param jdbcTemplate
     * @param game_id
     * @param cashDrawId
     * @return
     */
    public List<TicketPrize> getCashedTicketByGameCashDraw(JdbcTemplate jdbcTemplate, int game_id, int cashDrawId);

    /**
     * 根据游戏、兑奖期号、玩法查询兑奖票
     *
     * @param jdbcTemplate
     * @param game_id
     * @param cashDrawId
     * @param playId
     * @return
     */
    public List<TicketPrize> getCashedTicketByGameCashDrawPlay(JdbcTemplate jdbcTemplate, int game_id, int cashDrawId, int playId);

    /**
     * 游戏编号，期号，快开期号，运营商编号获取中奖数据奖级明细
     *
     * @param jdbcTemplate
     * @param game_id
     * @param draw_id
     * @param keno_draw_id
     * @param dealer_id
     * @return
     */
    public List<TicketPrize> getTicketPrizeInfoList(JdbcTemplate jdbcTemplate, int game_id, int draw_id, int keno_draw_id, String dealer_id);

    /**
     * 游戏编号，期号，快开期号获取中奖数据奖级明细
     *
     * @param jdbcTemplate
     * @param keno_draw_id
     * @param time
     * @return
     */
    public List<TicketPrize> selectTicketPrizeInfo(JdbcTemplate jdbcTemplate, int keno_draw_id, String time);

    /**
     * 根据彩民编号查询中奖信息
     *
     * @param jdbcTemplate
     * @param gamblerId
     * @return
     */
    public List<TicketPrize> getTicketPrizeListInfo(JdbcTemplate jdbcTemplate, String gamblerId);
}
