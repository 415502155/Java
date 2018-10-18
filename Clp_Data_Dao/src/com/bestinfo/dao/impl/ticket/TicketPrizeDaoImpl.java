package com.bestinfo.dao.impl.ticket;

import com.bestinfo.bean.ticket.TicketPrize;
import com.bestinfo.dao.impl.BaseDaoImpl;
import com.bestinfo.dao.ticket.ITicketPrizeDao;
import java.util.List;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

/**
 * 中奖票
 */
@Repository
public class TicketPrizeDaoImpl extends BaseDaoImpl implements ITicketPrizeDao {

    private static final String GET_TICKET_PRIZE_BY_CIPHER = "select cipher,sequence_id, game_id, draw_id, keno_draw_id, terminal_id, serial_no, operator_id, play_id, bet_method, bet_time, bet_mode, bet_stakes, bet_money, bet_multiple, bet_line, bet_num, bet_section, cashed_mark, max_prize_class, prize_detail, cash_draw_id, cash_terminal_id, cash_time, common_prize, multi_prize_mark, total_prize, tax_fee, ticket_prize_md5, cash_fee from T_TICKET_PRIZE where cipher=?";

    private static final String SELECT_TICKETPRIZE = "select cipher,sequence_id,game_id,draw_id,keno_draw_id,terminal_id,serial_no,operator_id,play_id,bet_method,bet_time,bet_mode,bet_stakes,bet_money,bet_multiple,bet_line,bet_num,bet_section,cashed_mark,max_prize_class,prize_detail,cash_draw_id,cash_terminal_id,cash_time,common_prize,multi_prize_mark,total_prize,tax_fee,ticket_prize_md5,cash_fee from t_ticket_prize where game_id=? and draw_id=? and keno_draw_id=?";

    private static final String GET_TICKETPRIZE = "select a.cipher,a.sequence_id,a.game_id,a.draw_id,a.keno_draw_id,a.terminal_id,a.serial_no,a.operator_id,a.play_id,a.bet_method,a.bet_time,a.bet_mode,a.bet_stakes,a.bet_money,a.bet_multiple,a.bet_line,a.bet_num,a.bet_section,a.cashed_mark,a.max_prize_class,a.prize_detail,a.cash_draw_id,a.cash_terminal_id,a.cash_time,a.common_prize,a.multi_prize_mark,a.total_prize,a.tax_fee,a.ticket_prize_md5,a.cash_fee,b.gambler_serial_no,b.bet_mode betMode ,b.bet_line betLine from t_ticket_prize a,t_bet_scheme b where a.game_id=b.game_id and a.draw_id=b.draw_id and a.terminal_id=b.terminal_id and a.serial_no=b.serial_no and b.scheme_status=10 and a.game_id=? and a.draw_id=? and a.keno_draw_id=? and b.dealer_id=? order by a.sequence_id asc";

    private static final String SELECT_TICKETPRIZEINFO = "select a.cipher,a.sequence_id,a.game_id,a.draw_id,a.keno_draw_id,a.terminal_id,a.serial_no,a.operator_id,a.play_id,a.bet_method,a.bet_time,a.bet_mode,a.bet_stakes,a.bet_money,a.bet_multiple,a.bet_line,a.bet_num,a.bet_section,a.cashed_mark,a.max_prize_class,a.prize_detail,a.cash_draw_id,a.cash_terminal_id,a.cash_time,a.common_prize,a.multi_prize_mark,a.total_prize,a.tax_fee,a.ticket_prize_md5,a.cash_fee ,t.draw_name from t_ticket_prize a ,t_ticket_bet b ,t_game_draw_info t where a.cipher=b.cipher and a.game_id=t.game_id and a.draw_id=t.draw_id-1 and  a.keno_draw_id=? and to_date(?,'yyyy/MM/dd') between t.sales_begin and t.sales_end order by t. game_id ,a.sequence_id asc";

    private static final String GET_TICKET_PRIZELIST_INFO = " select a.cipher,a.sequence_id,a.game_id,a.draw_id,a.keno_draw_id,a.terminal_id,a.serial_no,a.operator_id,a.play_id,a.bet_method,a.bet_time,a.bet_mode,a.bet_stakes,a.bet_money,a.bet_multiple,a.bet_line,a.bet_num,a.bet_section,a.cashed_mark,a.max_prize_class,a.prize_detail,a.cash_draw_id,a.cash_terminal_id,a.cash_time,a.common_prize,a.multi_prize_mark,a.total_prize,a.tax_fee,a.ticket_prize_md5,a.cash_fee from t_ticket_prize a ,t_ticket_bet b where a.cashed_mark=0 and a.cipher=b.cipher and b.gambler_id=?";

    private static final String GET_CASHED_TICKET_BY_GAME_CASHDRAW = "select p.*, d.draw_name"
            + "  from T_TICKET_PRIZE p, t_game_draw_info d"
            + " where p.game_id = d.game_id"
            + "   and p.draw_id = d.draw_id"
            + "   and p.game_id = ?"
            + "   and p.cash_draw_id = ?"
            + "   and p.cashed_mark = 1";

    private static final String GET_CASHED_TICKET_BY_GAME_CASHDRAW_PLAY = "select p.*, d.draw_name"
            + "  from T_TICKET_PRIZE p, t_game_draw_info d"
            + " where p.game_id = d.game_id"
            + "   and p.draw_id = d.draw_id"
            + "   and p.game_id = ?"
            + "   and p.cash_draw_id = ?"
            + "   and p.play_id = ?"
            + "   and p.cashed_mark = 1";

    @Override
    public List<TicketPrize> getTicketPrizeList(JdbcTemplate jdbcTemplate, int game_id, int draw_id, int keno_draw_id) {
        return this.queryForList(jdbcTemplate, SELECT_TICKETPRIZE, new Object[]{game_id, draw_id, keno_draw_id}, TicketPrize.class);
    }

    /**
     * 根据游戏、兑奖期号查询兑奖票
     *
     * @param jdbcTemplate
     * @param game_id
     * @param cashDrawId
     * @return
     */
    @Override
    public List<TicketPrize> getCashedTicketByGameCashDraw(JdbcTemplate jdbcTemplate, int game_id, int cashDrawId) {
        return this.queryForList(jdbcTemplate, GET_CASHED_TICKET_BY_GAME_CASHDRAW, new Object[]{game_id, cashDrawId}, TicketPrize.class);
    }

    /**
     * 根据游戏、兑奖期号、玩法查询兑奖票
     *
     * @param jdbcTemplate
     * @param game_id
     * @param cashDrawId
     * @param playId
     * @return
     */
    @Override
    public List<TicketPrize> getCashedTicketByGameCashDrawPlay(JdbcTemplate jdbcTemplate, int game_id, int cashDrawId, int playId) {
        return this.queryForList(jdbcTemplate, GET_CASHED_TICKET_BY_GAME_CASHDRAW_PLAY,
                new Object[]{game_id, cashDrawId, playId}, TicketPrize.class);
    }

    /**
     * 根据票面密码查询票中奖详情
     *
     * @param jdbcTemplate
     * @param cipher
     * @return
     */
    @Override
    public TicketPrize getTicketPrize(JdbcTemplate jdbcTemplate, String cipher) {
        return this.queryForObject(jdbcTemplate, GET_TICKET_PRIZE_BY_CIPHER,
                new Object[]{cipher}, TicketPrize.class);
    }

    @Override
    public List<TicketPrize> getTicketPrizeInfoList(JdbcTemplate jdbcTemplate, int game_id, int draw_id, int keno_draw_id, String dealer_id) {
        return this.queryForList(jdbcTemplate, GET_TICKETPRIZE, new Object[]{game_id, draw_id, keno_draw_id, dealer_id}, TicketPrize.class);
    }

    @Override
    public List<TicketPrize> selectTicketPrizeInfo(JdbcTemplate jdbcTemplate, int keno_draw_id, String time) {
        return this.queryForList(jdbcTemplate, SELECT_TICKETPRIZEINFO, new Object[]{keno_draw_id, time}, TicketPrize.class);
    }

    @Override
    public List<TicketPrize> getTicketPrizeListInfo(JdbcTemplate jdbcTemplate, String gamblerId) {
        return this.queryForList(jdbcTemplate, GET_TICKET_PRIZELIST_INFO, new Object[]{gamblerId}, TicketPrize.class);

    }

}
