package com.bestinfo.dao.impl.stat;

import com.bestinfo.bean.stat.StatGameDraw;
import com.bestinfo.dao.impl.BaseDaoImpl;
import com.bestinfo.dao.stat.IStatGameDrawDao;
import java.sql.SQLException;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

/**
 * 结算统计表-游戏期(T_stat_game_draw)
 *
 * @author yangyuefu
 */
@Repository
public class StatGameDrawDaoImpl extends BaseDaoImpl implements IStatGameDrawDao {

//    private final Logger logger = Logger.getLogger(StatGameDrawDaoImpl.class);
    /**
     * 插入StatGameDraw
     */
    private static final String INSERT_STATGAMEDRAW = "insert into t_stat_game_draw(game_id,draw_id,sale_money,sale_ticket_num,sale_stake_num,undo_money,undo_ticket_num,undo_stake_num,cash_money,cash_ticket_num,cash_stake_num,agent_fee_deduct,agent_fee,welfare_money_clp,welfare_money_center,welfare_money_city,issue_money_clp,issue_money_center,issue_money_city,return_jackpot,reserve_money,return_fee,cash_fee) values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
    private static final String INSERT_STATGAMEDRAW_MERGE = "merge into t_stat_game_draw statgd using (select ? game_id, ? draw_id from dual ) src on (statgd.game_id=src.game_id and statgd.draw_id=src.draw_id) when matched then update set sale_money=?,sale_ticket_num=?,sale_stake_num=?,undo_money=?,undo_ticket_num=?,undo_stake_num=?,cash_money=?,cash_ticket_num=?,cash_stake_num=?,agent_fee_deduct=?,agent_fee=?,welfare_money_clp=?,welfare_money_center=?,welfare_money_city=?,issue_money_clp=?,issue_money_center=?,issue_money_city=?,return_jackpot=?,reserve_money=?,return_fee=?,cash_fee=? when not matched then insert (game_id,draw_id,sale_money,sale_ticket_num,sale_stake_num,undo_money,undo_ticket_num,undo_stake_num,cash_money,cash_ticket_num,cash_stake_num,agent_fee_deduct,agent_fee,welfare_money_clp,welfare_money_center,welfare_money_city,issue_money_clp,issue_money_center,issue_money_city,return_jackpot,reserve_money,return_fee,cash_fee) values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

    // add by lvcahngrong  用于开奖公告
    private static final String SELECT_STATGAMEDRAW_ById = "select game_id,draw_id,sale_money,sale_ticket_num,sale_stake_num,undo_money,undo_ticket_num,undo_stake_num,cash_money,cash_ticket_num,cash_stake_num,agent_fee_deduct,agent_fee,welfare_money_clp,welfare_money_center,welfare_money_city,issue_money_clp,issue_money_center,issue_money_city,return_jackpot,reserve_money,return_fee,cash_fee from t_stat_game_draw where game_id = ? and draw_id = ?";

    //不包含电话投注数据
    private static final String GET_ALL_SYSTEM_SALEMONEY_WITHOUT_GAMB = "select g.game_id,"
            + "       g.draw_id,"
            + "       sum(g.sale_money) + sum(s.sale_money) as sale_money"
            + "  from t_stat_game_draw g,"
            + "       (select game_id, draw_id, sum(sale_money) as sale_money"
            + "          from t_stat_lottery_sales"
            + "         where game_id = ?"
            + "           and draw_id = ?"
            + "         group by game_id, draw_id) s"
            + " where g.game_id = ?"
            + "   and g.draw_id = ?"
            + "   and g.game_id = s.game_id"
            + "   and g.draw_id = s.draw_id"
            + " group by g.game_id, g.draw_id";

    //包含电话投注数据
    private static final String GET_ALL_SYSTEM_SALEMONEY = "select g.game_id,"
            + "       g.draw_id,"
            + "       sum(g.sale_money) + sum(s.sale_money) + sum(u.sale_money) as sale_money"
            + "  from t_stat_game_draw g,"
            + "       t_union_pronvice_statistics u,"
            + "       (select game_id, draw_id, sum(sale_money) as sale_money"
            + "          from t_stat_lottery_sales"
            + "         where game_id = ?"
            + "           and draw_id = ?"
            + "         group by game_id, draw_id) s"
            + " where g.game_id = ?"
            + "   and g.draw_id = ?"
            + "   and g.game_id = s.game_id"
            + "   and g.draw_id = s.draw_id"
            + "   and g.game_id = u.game_id"
            + "   and g.draw_id = u.draw_id"
            + " group by g.game_id, g.draw_id";

    /**
     * 新增游戏期信息
     *
     * @param jdbcTemplate
     * @param statGameDraw
     * @return
     */
    @Override
    public int insertStatGameDraw(JdbcTemplate jdbcTemplate, StatGameDraw statGameDraw) {
        int result = 0;
        try {
            result = jdbcTemplate.update(INSERT_STATGAMEDRAW, new Object[]{
                statGameDraw.getGame_id(),
                statGameDraw.getDraw_id(),
                statGameDraw.getSale_money(),
                statGameDraw.getSale_ticket_num(),
                statGameDraw.getSale_stake_num(),
                statGameDraw.getUndo_money(),
                statGameDraw.getUndo_ticket_num(),
                statGameDraw.getUndo_stake_num(),
                statGameDraw.getCash_money(),
                statGameDraw.getCash_ticket_num(),
                statGameDraw.getCash_stake_num(),
                statGameDraw.getAgent_fee_deduct(),
                statGameDraw.getAgent_fee(),
                statGameDraw.getWelfare_money_clp(),
                statGameDraw.getWelfare_money_center(),
                statGameDraw.getWelfare_money_city(),
                statGameDraw.getIssue_money_clp(),
                statGameDraw.getIssue_money_center(),
                statGameDraw.getIssue_money_city(),
                statGameDraw.getReturn_jackpot(),
                statGameDraw.getReserve_money(),
                statGameDraw.getReturn_fee(),
                statGameDraw.getCash_fee()
            });
        } catch (DataAccessException e) {
            logger.error("", e);
            SQLException sqle = (SQLException) e.getCause();
            logger.error("Error code: " + sqle.getErrorCode());
            logger.error("SQL state: " + sqle.getSQLState());
            result = -1;
        }
        return result;
    }

    /**
     * 新增游戏期信息
     *
     * @param jdbcTemplate
     * @param statGameDraw
     * @return
     */
    @Override
    public int insertStatGameDrawMerge(JdbcTemplate jdbcTemplate, StatGameDraw statGameDraw) {
        int result = 0;
        try {
            result = jdbcTemplate.update(INSERT_STATGAMEDRAW_MERGE, new Object[]{
                statGameDraw.getGame_id(),
                statGameDraw.getDraw_id(),
                statGameDraw.getSale_money(),
                statGameDraw.getSale_ticket_num(),
                statGameDraw.getSale_stake_num(),
                statGameDraw.getUndo_money(),
                statGameDraw.getUndo_ticket_num(),
                statGameDraw.getUndo_stake_num(),
                statGameDraw.getCash_money(),
                statGameDraw.getCash_ticket_num(),
                statGameDraw.getCash_stake_num(),
                statGameDraw.getAgent_fee_deduct(),
                statGameDraw.getAgent_fee(),
                statGameDraw.getWelfare_money_clp(),
                statGameDraw.getWelfare_money_center(),
                statGameDraw.getWelfare_money_city(),
                statGameDraw.getIssue_money_clp(),
                statGameDraw.getIssue_money_center(),
                statGameDraw.getIssue_money_city(),
                statGameDraw.getReturn_jackpot(),
                statGameDraw.getReserve_money(),
                statGameDraw.getReturn_fee(),
                statGameDraw.getCash_fee(),
                statGameDraw.getGame_id(),
                statGameDraw.getDraw_id(),
                statGameDraw.getSale_money(),
                statGameDraw.getSale_ticket_num(),
                statGameDraw.getSale_stake_num(),
                statGameDraw.getUndo_money(),
                statGameDraw.getUndo_ticket_num(),
                statGameDraw.getUndo_stake_num(),
                statGameDraw.getCash_money(),
                statGameDraw.getCash_ticket_num(),
                statGameDraw.getCash_stake_num(),
                statGameDraw.getAgent_fee_deduct(),
                statGameDraw.getAgent_fee(),
                statGameDraw.getWelfare_money_clp(),
                statGameDraw.getWelfare_money_center(),
                statGameDraw.getWelfare_money_city(),
                statGameDraw.getIssue_money_clp(),
                statGameDraw.getIssue_money_center(),
                statGameDraw.getIssue_money_city(),
                statGameDraw.getReturn_jackpot(),
                statGameDraw.getReserve_money(),
                statGameDraw.getReturn_fee(),
                statGameDraw.getCash_fee()
            });
        } catch (DataAccessException e) {
            logger.error("", e);
            SQLException sqle = (SQLException) e.getCause();
            logger.error("Error code: " + sqle.getErrorCode());
            logger.error("SQL state: " + sqle.getSQLState());
            result = -1;
        }
        return result;
    }

    /**
     * 根据gameId和draw_id获取游戏期信息
     *
     * @param jdebTemplate
     * @param game_id
     * @param draw_id
     * @return
     */
    @Override
    public StatGameDraw getStatGameDrawById(JdbcTemplate jdebTemplate, int game_id, int draw_id) {
        return this.queryForObject(jdebTemplate,
                SELECT_STATGAMEDRAW_ById,
                new Object[]{game_id, draw_id},
                StatGameDraw.class);
    }

    /**
     * 获取某期所有系统的销售额<br>
     * 包括贝英斯、联销省份、维赛特<br>
     *
     * @param jdebTemplate
     * @param game_id
     * @param draw_id
     * @return
     */
    @Override
    public StatGameDraw getAllSystemSaleMoney(JdbcTemplate jdebTemplate, int game_id, int draw_id) {
        return this.queryForObject(jdebTemplate,
                GET_ALL_SYSTEM_SALEMONEY_WITHOUT_GAMB,
                new Object[]{game_id, draw_id, game_id, draw_id},
                StatGameDraw.class);
    }

}
