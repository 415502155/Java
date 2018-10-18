package com.bestinfo.dao.impl.ticket;

import com.bestinfo.bean.ticket.TicketPrizeClass;
import com.bestinfo.dao.impl.BaseDaoImpl;
import com.bestinfo.dao.ticket.ITicketPrizeClassDao;
import java.util.List;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

/**
 * 获取中奖数据奖级明细
 *
 * @author lvchangrong
 */
@Repository
public class TicketPrizeClassImpl extends BaseDaoImpl implements ITicketPrizeClassDao {

    private static final String SELECT_TICKETPRIZECLASS = "select game_id,draw_id,keno_draw_id,stake_no,class_id,prize_num,total_prize from t_ticket_prize_class where cipher=? and game_id=? and draw_id=? and keno_draw_id=?";

    private static final String GET_PRIZE_CLASS_BY_CIPHER = "select cipher,sequence_id,game_id,draw_id,keno_draw_id,terminal_id,stake_no,open_id,class_id,prize_num,total_prize from t_ticket_prize_class where cipher=?";

    private static final String SELECT_TaxFee = "SELECT nvl(SUM(a.total_prize * b.tax_rate), 0) tax_fee FROM t_ticket_prize_class a, t_tax_class b WHERE a.cipher = ? AND a.total_prize > b.min_money AND a.total_prize <= b.max_money";

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
    @Override
    public List<TicketPrizeClass> getTicketPrizeClassList(JdbcTemplate jdbcTemplate, String cipher, int game_id, int draw_id, int keno_draw_id) {
        return this.queryForList(jdbcTemplate, SELECT_TICKETPRIZECLASS, new Object[]{cipher, game_id, draw_id, keno_draw_id}, TicketPrizeClass.class);
    }

    @Override
    public List<TicketPrizeClass> getTicketPrizeClassList(JdbcTemplate jdbcTemplate, String cipher) {
        return this.queryForList(jdbcTemplate, GET_PRIZE_CLASS_BY_CIPHER, new Object[]{cipher}, TicketPrizeClass.class);
    }

    @Override
    public TicketPrizeClass getTicketPrizeTaxFee(JdbcTemplate jdbcTemplate, String cipher) {
        return this.queryForObject(jdbcTemplate, SELECT_TaxFee, new Object[]{cipher}, TicketPrizeClass.class);

    }
}
