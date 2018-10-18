package com.bestinfo.dao.impl.stat;

import com.bestinfo.bean.stat.JackpotPoolInfo;
import com.bestinfo.dao.impl.BaseDaoImpl;
import com.bestinfo.dao.stat.IJackpotPoolInfoDao;
import java.sql.SQLException;
import java.util.List;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

/**
 * 结算统计表-游戏奖池调节基金(T_jackpot_pool_info)
 *
 * @author yangyuefu
 */
@Repository
public class JackpotPoolInfoDaoImpl extends BaseDaoImpl implements IJackpotPoolInfoDao {

//    private final Logger logger = Logger.getLogger(JackpotPoolInfoDaoImpl.class);
    /**
     * 获取奖池
     */
    private static final String GET_JACKPOT = "select game_id,play_id,draw_id,keno_draw_id,sales_money,begin_jackpot, return_jackpot,append_jackpot,get_jackpot,prize_ticket_money,not_give_money,forget_money_jackpot,end_jackpot, begin_pool,return_pool,append_pool,get_pool,forget_moeny_pool,round_money,fill_prize,end_pool,note from t_stat_jackpot_info where game_id=? and play_id=? and draw_id=? and keno_draw_id=?";

    /**
     * 插入奖池
     */
    private static final String INSERT_JACKPOT = "insert into t_stat_jackpot_info(game_id,play_id,draw_id,keno_draw_id,sales_money,begin_jackpot, return_jackpot,append_jackpot,get_jackpot,prize_ticket_money,not_give_money,forget_money_jackpot,end_jackpot, begin_pool,return_pool,append_pool,get_pool,forget_moeny_pool,round_money,fill_prize,end_pool,note)  values(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

    /**
     * 插入或更新奖池
     */
    private static final String INSERT_JACKPOT_MERGE = "merge into t_stat_jackpot_info statji using (select ? game_id, ? play_id, ? draw_id, ? keno_draw_id from dual) src on (statji.game_id=src.game_id and statji.play_id=src.play_id and statji.draw_id=src.draw_id and statji.keno_draw_id=src.keno_draw_id) when matched then update set sales_money=?,begin_jackpot=?, return_jackpot=?,append_jackpot=?,get_jackpot=?,prize_ticket_money=?,not_give_money=?,forget_money_jackpot=?,end_jackpot=?, begin_pool=?,return_pool=?,append_pool=?,get_pool=?,forget_moeny_pool=?,round_money=?,fill_prize=?,end_pool=?,note=? when not matched then insert (game_id,play_id,draw_id,keno_draw_id,sales_money,begin_jackpot, return_jackpot,append_jackpot,get_jackpot,prize_ticket_money,not_give_money,forget_money_jackpot,end_jackpot, begin_pool,return_pool,append_pool,get_pool,forget_moeny_pool,round_money,fill_prize,end_pool,note) values(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

    /**
     * 更新奖池
     */
    private static final String UPDATE_JACKPOT = "update t_stat_jackpot_info set sales_money=?,begin_jackpot=?, return_jackpot=?,append_jackpot=?,get_jackpot=?,prize_ticket_money=?,not_give_money=?,forget_money_jackpot=?,end_jackpot=?, begin_pool=?,return_pool=?,append_pool=?,get_pool=?,forget_moeny_pool=?,round_money=?,fill_prize=?,end_pool=?,note=? where game_id=? and play_id=? and draw_id=? and keno_draw_id=?";

    /**
     * 获取当前期之前的奖池列表t_stat_jackpot_info
     */
    private static final String GET_FRONT_JACKPOT_LIST = "select game_id,play_id,draw_id,keno_draw_id,sales_money,begin_jackpot,return_jackpot,append_jackpot,get_jackpot,prize_ticket_money,not_give_money,forget_money_jackpot,end_jackpot,begin_pool,return_pool,append_pool,get_pool,forget_moeny_pool,round_money,fill_prize,end_pool,note  from t_stat_jackpot_info where game_id = ? and draw_id < ? order by draw_id desc";

    /**
     * 获取当前期的总投注额
     */
    private static final String GET_JACKPOTPOOL_ById = " select * from t_stat_jackpot_info where game_id = ? and draw_id = ? and keno_draw_id = ? ";

    /**
     * 获取JackpotPoolInfo对象
     *
     * @param jdbcTemplate
     * @param game_id
     * @param play_id
     * @param draw_id
     * @param keno_draw_id
     * @return
     */
    @Override
    public JackpotPoolInfo getJackpotPoolInfo(JdbcTemplate jdbcTemplate, int game_id, int play_id, int draw_id, int keno_draw_id) {
        return queryForObject(jdbcTemplate, GET_JACKPOT, new Object[]{game_id, play_id, draw_id, keno_draw_id}, JackpotPoolInfo.class);
    }

    /**
     * 新增游戏奖池
     *
     * @param jdbcTemplate
     * @param jackpot
     * @return
     */
    @Override
    public int insertJackpotPoolInfo(JdbcTemplate jdbcTemplate, JackpotPoolInfo jackpot) {
        int result;
        try {
            result = jdbcTemplate.update(INSERT_JACKPOT, new Object[]{
                jackpot.getGame_id(),
                jackpot.getPlay_id(),
                jackpot.getDraw_id(),
                jackpot.getKeno_draw_id(),
                jackpot.getSales_money(),
                jackpot.getBegin_jackpot(),
                jackpot.getReturn_jackpot(),
                jackpot.getAppend_jackpot(),
                jackpot.getGet_jackpot(),
                jackpot.getPrize_ticket_money(),
                jackpot.getNot_give_money(),
                jackpot.getForget_money_jackpot(),
                jackpot.getEnd_jackpot(),
                jackpot.getBegin_pool(),
                jackpot.getReturn_pool(),
                jackpot.getAppend_pool(),
                jackpot.getGet_pool(),
                jackpot.getForget_money_pool(),
                jackpot.getRound_money(),
                jackpot.getFill_prize(),
                jackpot.getEnd_pool(),
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
     * 新增游戏奖池（merge）
     *
     * @param jdbcTemplate
     * @param jackpot
     * @return
     */
    @Override
    public int insertJackpotPoolInfoMerge(JdbcTemplate jdbcTemplate, JackpotPoolInfo jackpot) {
        int result;
        try {
            result = jdbcTemplate.update(INSERT_JACKPOT_MERGE, new Object[]{
                jackpot.getGame_id(),
                jackpot.getPlay_id(),
                jackpot.getDraw_id(),
                jackpot.getKeno_draw_id(),
                jackpot.getSales_money(),
                jackpot.getBegin_jackpot(),
                jackpot.getReturn_jackpot(),
                jackpot.getAppend_jackpot(),
                jackpot.getGet_jackpot(),
                jackpot.getPrize_ticket_money(),
                jackpot.getNot_give_money(),
                jackpot.getForget_money_jackpot(),
                jackpot.getEnd_jackpot(),
                jackpot.getBegin_pool(),
                jackpot.getReturn_pool(),
                jackpot.getAppend_pool(),
                jackpot.getGet_pool(),
                jackpot.getForget_money_pool(),
                jackpot.getRound_money(),
                jackpot.getFill_prize(),
                jackpot.getEnd_pool(),
                jackpot.getNote(),
                jackpot.getGame_id(),
                jackpot.getPlay_id(),
                jackpot.getDraw_id(),
                jackpot.getKeno_draw_id(),
                jackpot.getSales_money(),
                jackpot.getBegin_jackpot(),
                jackpot.getReturn_jackpot(),
                jackpot.getAppend_jackpot(),
                jackpot.getGet_jackpot(),
                jackpot.getPrize_ticket_money(),
                jackpot.getNot_give_money(),
                jackpot.getForget_money_jackpot(),
                jackpot.getEnd_jackpot(),
                jackpot.getBegin_pool(),
                jackpot.getReturn_pool(),
                jackpot.getAppend_pool(),
                jackpot.getGet_pool(),
                jackpot.getForget_money_pool(),
                jackpot.getRound_money(),
                jackpot.getFill_prize(),
                jackpot.getEnd_pool(),
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
     * 更新游戏奖池
     *
     * @param jdbcTemplate
     * @param jackpot
     * @return
     */
    @Override
    public int updateJackpotPoolInfo(JdbcTemplate jdbcTemplate, JackpotPoolInfo jackpot) {
        int result;
        try {
            result = jdbcTemplate.update(UPDATE_JACKPOT, new Object[]{
                jackpot.getSales_money(),
                jackpot.getBegin_jackpot(),
                jackpot.getReturn_jackpot(),
                jackpot.getAppend_jackpot(),
                jackpot.getGet_jackpot(),
                jackpot.getPrize_ticket_money(),
                jackpot.getNot_give_money(),
                jackpot.getForget_money_jackpot(),
                jackpot.getEnd_jackpot(),
                jackpot.getBegin_pool(),
                jackpot.getReturn_pool(),
                jackpot.getAppend_pool(),
                jackpot.getGet_pool(),
                jackpot.getForget_money_pool(),
                jackpot.getRound_money(),
                jackpot.getFill_prize(),
                jackpot.getEnd_pool(),
                jackpot.getNote(),
                jackpot.getGame_id(),
                jackpot.getPlay_id(),
                jackpot.getDraw_id(),
                jackpot.getKeno_draw_id()
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
     * 获取当前期之前的奖池列表
     *
     * @param jdbcTemplate
     * @param game_id
     * @param draw_id
     * @return
     */
    @Override
    public List<JackpotPoolInfo> getFrontJackpotList(JdbcTemplate jdbcTemplate, int game_id, int draw_id) {
        return queryForList(jdbcTemplate, GET_FRONT_JACKPOT_LIST, new Object[]{game_id, draw_id}, JackpotPoolInfo.class);
    }

    /**
     * 获取当前期的总投注额度 add by lvchangrong
     *
     * @param jdbcTemplate
     * @param game_id
     * @param draw_id
     * @param kdraw_id
     * @return
     */
    @Override
    public JackpotPoolInfo getJackpotPoolInfoById(JdbcTemplate jdbcTemplate, int game_id, int draw_id, int kdraw_id) {
        return this.queryForObject(jdbcTemplate, GET_JACKPOTPOOL_ById, new Object[]{game_id, draw_id, kdraw_id}, JackpotPoolInfo.class);
    }
}
