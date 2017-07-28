package com.bestinfo.dao.impl.stat;

import com.bestinfo.bean.stat.StatJackpotAttach;
import com.bestinfo.dao.impl.BaseDaoImpl;
import com.bestinfo.dao.stat.IStatJackpotAttachDao;
import java.sql.SQLException;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

/**
 * 奖池附属表，用来记录每个奖级的小数点和补足提取(t_stat_jackpot_attach)
 */
@Repository
public class StatJackpotAttachDaoImpl extends BaseDaoImpl implements IStatJackpotAttachDao {

    /**
     * 获取奖池
     */
    private static final String GET_JACKPOT_BY_PRIMARY = "select * from t_stat_jackpot_attach where game_id=? and play_id=? and draw_id=? and keno_draw_id=?";

    /**
     * 插入或更新奖池
     */
    private static final String MERGE_JACKPOT = "merge into t_stat_jackpot_attach a"
            + " using (select ? game_id, ? play_id, ? draw_id, ? keno_draw_id from dual) d"
            + " on (a.game_id = d.game_id and a.play_id = d.play_id and a.draw_id = d.draw_id and a.keno_draw_id = d.keno_draw_id)"
            + " when matched then"
            + "  update"
            + "     set ROUND_MONEY_1  = ?,"
            + "         ROUND_MONEY_2  = ?,"
            + "         FILL_PRIZE_1   = ?,"
            + "         FILL_PRIZE_2   = ?,"
            + "         FILL_PRIZE_FIX = ?,"
            + "         note           = ?"
            + " when not matched then"
            + "  insert"
            + "    (game_id,"
            + "     play_id,"
            + "     draw_id,"
            + "     keno_draw_id,"
            + "     ROUND_MONEY_1,"
            + "     ROUND_MONEY_2,"
            + "     FILL_PRIZE_1,"
            + "     FILL_PRIZE_2,"
            + "     FILL_PRIZE_FIX,"
            + "     note)"
            + "  values"
            + "    (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

    /**
     * 获取当前期的总投注额
     */
    private static final String GET_JACKPOT_BY_GAME_DRAW = " select * from t_stat_jackpot_attach where game_id = ? and draw_id = ? and keno_draw_id = ? ";

    /**
     * 根据主键获取StatJackpotAttach对象
     *
     * @param jdbcTemplate
     * @param game_id
     * @param play_id
     * @param draw_id
     * @param keno_draw_id
     * @return
     */
    @Override
    public StatJackpotAttach getStatJackpotAttach(JdbcTemplate jdbcTemplate, int game_id, int play_id, int draw_id, int keno_draw_id) {
        return queryForObject(jdbcTemplate, GET_JACKPOT_BY_PRIMARY, new Object[]{game_id, play_id, draw_id, keno_draw_id}, StatJackpotAttach.class);
    }

    /**
     * 新增或更新（merge）
     *
     * @param jdbcTemplate
     * @param jackpot
     * @return
     */
    @Override
    public int mergeStatJackpotAttach(JdbcTemplate jdbcTemplate, StatJackpotAttach jackpot) {
        int result;
        try {
            result = jdbcTemplate.update(MERGE_JACKPOT, new Object[]{
                jackpot.getGame_id(),
                jackpot.getPlay_id(),
                jackpot.getDraw_id(),
                jackpot.getKeno_draw_id(),
                jackpot.getRound_money_1(),
                jackpot.getRound_money_2(),
                jackpot.getFill_prize_1(),
                jackpot.getFill_prize_2(),
                jackpot.getFill_prize_fix(),
                jackpot.getNote(),
                jackpot.getGame_id(),
                jackpot.getPlay_id(),
                jackpot.getDraw_id(),
                jackpot.getKeno_draw_id(),
                jackpot.getRound_money_1(),
                jackpot.getRound_money_2(),
                jackpot.getFill_prize_1(),
                jackpot.getFill_prize_2(),
                jackpot.getFill_prize_fix(),
                jackpot.getNote()
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
     * 根据游戏期获取
     *
     * @param jdbcTemplate
     * @param game_id
     * @param draw_id
     * @param kdraw_id
     * @return
     */
    @Override
    public StatJackpotAttach getStatJackpotAttachByGameDraw(JdbcTemplate jdbcTemplate, int game_id, int draw_id, int kdraw_id) {
        return this.queryForObject(jdbcTemplate, GET_JACKPOT_BY_GAME_DRAW, new Object[]{game_id, draw_id, kdraw_id}, StatJackpotAttach.class);
    }
}
