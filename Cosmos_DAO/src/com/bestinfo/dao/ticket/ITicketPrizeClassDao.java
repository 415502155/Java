package com.bestinfo.dao.ticket;

import com.bestinfo.bean.ticket.TicketPrizeClass;
import java.util.List;
import org.springframework.jdbc.core.JdbcTemplate;

/**
 * 彩票-中奖数据奖级明细
 *
 * @author lvchangrong
 */
public interface ITicketPrizeClassDao {

    /**
     * 获取中奖数据奖级明细
     *
     * @param jdbcTemplate
     * @param cipher
     * @param game_id
     * @param draw_id
     * @param keno_draw_id
     * @return
     */
    public List<TicketPrizeClass> getTicketPrizeClassList(JdbcTemplate jdbcTemplate, String cipher, int game_id, int draw_id, int keno_draw_id);

    /**
     * 根据密码获取中奖数据奖级明细
     *
     * @param jdbcTemplate
     * @param cipher
     * @return
     */
    public List<TicketPrizeClass> getTicketPrizeClassList(JdbcTemplate jdbcTemplate, String cipher);

    /**
     * 根据密码得到缴税金额是否中大奖
     *
     * @param jdbcTemplate
     * @param cipher
     * @return
     */
    public TicketPrizeClass getTicketPrizeTaxFee(JdbcTemplate jdbcTemplate, String cipher);
}
