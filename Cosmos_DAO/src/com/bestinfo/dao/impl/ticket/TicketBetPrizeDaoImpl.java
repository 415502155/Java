package com.bestinfo.dao.impl.ticket;

import com.bestinfo.bean.ticket.TicketBetPrize;
import com.bestinfo.dao.impl.BaseDaoImpl;
import com.bestinfo.dao.ticket.ITicketBetPrizeDao;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

/**
 * 彩票数据明细
 */
@Repository
public class TicketBetPrizeDaoImpl extends BaseDaoImpl implements ITicketBetPrizeDao {

    private static final String QUERY_TICKET_CASH_BY_CIPHER = "select a.bet_time,a.terminal_id,a.bet_money,a.bet_line,b.prize_detail,c.draw_name,b.common_prize from t_ticket_bet a ,t_ticket_prize b,t_game_draw_info c \n" +
                                                              "where "
                                                              + "a.cipher=b.cipher "
                                                              + "and a.game_id=c.game_id "
                                                              + "and a.draw_id=c.draw_id "
                                                              + "and a.cipher = ?";

    /**
     * 根据cipher来查询票的其他信息
     *
     * @param jdbcTemplate
     * @param cipher
     * @return
     */
    @Override
    public TicketBetPrize getTicketBetPrizeByCipher(JdbcTemplate jdbcTemplate, String cipher) {
        return this.queryForObject(jdbcTemplate, QUERY_TICKET_CASH_BY_CIPHER, new Object[]{cipher}, TicketBetPrize.class);
    }

}
