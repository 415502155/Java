package com.bestinfo.dao.ticket;

import com.bestinfo.bean.ticket.TicketBetPrize;
import org.springframework.jdbc.core.JdbcTemplate;

/**
 * 彩票数据明细
 */
public interface ITicketBetPrizeDao {

    /**
     * 根据cipher来查询bet_time,terminal_id,bet_money,bet_line,prize_detail等信息
     *
     * @param jdbcTemplate
     * @param cipher
     * @return
     */
    public TicketBetPrize getTicketBetPrizeByCipher(JdbcTemplate jdbcTemplate, String cipher);

    
}
