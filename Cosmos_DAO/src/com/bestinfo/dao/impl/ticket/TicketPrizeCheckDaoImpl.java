package com.bestinfo.dao.impl.ticket;

import com.bestinfo.bean.ticket.TicketPrizeCheck;
import com.bestinfo.dao.impl.BaseDaoImpl;
import com.bestinfo.dao.ticket.ITicketPrizeCheckDao;
import java.sql.SQLException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

/**
 * 票中奖验证
 *
 * @author shijf
 */
@Repository
public class TicketPrizeCheckDaoImpl extends BaseDaoImpl implements ITicketPrizeCheckDao {

    private static final String INSERT = "insert into t_ticket_prize_check (cipher, sequence_id, game_id, draw_id, keno_draw_id, terminal_id, serial_no, bet_money, total_prize, check_mark, check_time, city_id, user_id) values (?,to_number(?),?,?,?,?,?,?,?,?,?,?,?)";

    private static final String GET_BY_CIPHER = "select * from t_ticket_prize_check t where t.cipher=?";

    @Override
    public int add(JdbcTemplate jdbcTemplate, TicketPrizeCheck ticket) {
        int result = -1;

        try {
            logger.info("ticket info ==" + ticket.toString());
            result = jdbcTemplate.update(INSERT, new Object[]{
                ticket.getCipher(),
                ticket.getSequence_id().toString(),
                ticket.getGame_id(),
                ticket.getDraw_id(),
                ticket.getKeno_draw_id(),
                ticket.getTerminal_id(),
                ticket.getSerial_no(),
                ticket.getBet_money(),
                ticket.getTotal_prize(),
                ticket.getCheck_mark(),
                ticket.getCheck_time(),
                ticket.getCity_id(),
                ticket.getUser_id()
            });
        } catch (Exception e) {
            result = -1;
            logger.error("", e);
            SQLException sqle = (SQLException) e.getCause();
            logger.error("Error code: " + sqle.getErrorCode());
            logger.error("SQL state: " + sqle.getSQLState());

        }
        return result;
    }

    @Override
    public TicketPrizeCheck getTicketPrizeCheckByCipher(JdbcTemplate jdbcTemplate, String cipher) {
        return this.queryForObject(jdbcTemplate, GET_BY_CIPHER,
                new Object[]{cipher}, TicketPrizeCheck.class);
    }

}
