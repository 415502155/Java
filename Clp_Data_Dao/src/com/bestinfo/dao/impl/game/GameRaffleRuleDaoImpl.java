package com.bestinfo.dao.impl.game;

import com.bestinfo.bean.game.GameRaffleRule;
import com.bestinfo.dao.game.IGameRaffleRuleDao;
import com.bestinfo.dao.impl.BaseDaoImpl;
import java.sql.SQLException;
import java.util.List;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

/**
 * 游戏抽奖规则
 *
 * @author chenliping
 */
@Repository
public class GameRaffleRuleDaoImpl extends BaseDaoImpl implements IGameRaffleRuleDao {

//    private final Logger logger = Logger.getLogger(GameRaffleRuleDaoImpl.class);
    private static final String INSERT_GAME_RAFFLE_RULE = "insert into t_game_raffle_rule(game_id,open_id,play_id,class_id,rule_id,basic_num,special_num,blue_num,no_order,match_pos,match_near,raffle_method,work_status,relate_class) values(?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

    private static final String SELECT_GAME_RAFFLE_RULE_BYGAMEID = "select game_id,play_id,open_id,class_id,rule_id,basic_num,special_num,blue_num,no_order,match_pos,match_near,raffle_method,work_status,relate_class from t_game_raffle_rule where game_id=?";

    private static final String SELECT_GAME_RAFFLE_RULE_BYRULEID = "select game_id,play_id,open_id,class_id,rule_id,basic_num,special_num,blue_num,no_order,match_pos,match_near,raffle_method,work_status,relate_class from t_game_raffle_rule where rule_id=?";

    private static final String SELECT_GAME_RAFFLE_RULE_BYIDS = "select game_id,play_id,open_id,class_id,rule_id,basic_num,special_num,blue_num,no_order,match_pos,match_near,raffle_method,work_status,relate_class from t_game_raffle_rule where game_id=? and play_id=? and rule_id=?";

    private static final String SELECT_GAME_RAFFLE_RULE_LIST = "select game_id,play_id,open_id,class_id,rule_id,basic_num,special_num,blue_num,no_order,match_pos,match_near,raffle_method,work_status,relate_class from t_game_raffle_rule";

    private static final String UPDATE_GAME_RAFFLE_RULE = "update t_game_raffle_rule set open_id=?,play_id=?,class_id=?,basic_num=?,special_num=?,blue_num=?,no_order=?,match_pos=?,match_near=?,raffle_method=?,work_status=?,relate_class=? where game_id=? and rule_id=?";

    /**
     * 获取抽奖规则列表信息
     *
     * @param jdbcTemplate
     * @param grr
     * @return
     */
    @Override
    public int insertGameRaffleRule(JdbcTemplate jdbcTemplate, GameRaffleRule grr) {
        int re = -1;
        try {
            re = jdbcTemplate.update(INSERT_GAME_RAFFLE_RULE, new Object[]{
                grr.getGame_id(),
                grr.getOpen_id(),
                grr.getPlay_id(),
                grr.getClass_id(),
                grr.getRule_id(),
                grr.getBasic_num(),
                grr.getSpecial_num(),
                grr.getBlue_num(),
                grr.getNo_order(),
                grr.getMatch_pos(),
                grr.getMatch_near(),
                grr.getRaffle_method(),
                grr.getWork_status(),
                grr.getRelate_class()
            });
//            logger.info(re);
            return re;
        } catch (DataAccessException e) {
            logger.error("", e);
            SQLException sqle = (SQLException) e.getCause();
            logger.error("Error code: " + sqle.getErrorCode());
            logger.error("SQL state: " + sqle.getSQLState());
            return -2;
        }
    }

    /**
     * 根据游戏编号获取游戏抽奖规则信息
     *
     * @param jdbcTemplate
     * @param gameid
     * @return
     */
    @Override
    public List<GameRaffleRule> selectGameRaffleRuleByGameid(JdbcTemplate jdbcTemplate, int gameid) {
        List<GameRaffleRule> lgff = this.queryForList(jdbcTemplate, SELECT_GAME_RAFFLE_RULE_BYGAMEID, new Object[]{gameid}, GameRaffleRule.class);
        return lgff;
    }

    /**
     * 修改游戏抽奖规则信息
     *
     * @param jdbcTemplate
     * @param grf
     * @return
     */
    @Override
    public int updateGameRaffleRule(JdbcTemplate jdbcTemplate, GameRaffleRule grf) {
        int re = -1;
        try {
            re = jdbcTemplate.update(UPDATE_GAME_RAFFLE_RULE, new Object[]{
                grf.getOpen_id(),
                grf.getPlay_id(),
                grf.getClass_id(),
                // grf.getRule_id(),
                grf.getBasic_num(),
                grf.getSpecial_num(),
                grf.getBlue_num(),
                grf.getNo_order(),
                grf.getMatch_pos(),
                grf.getMatch_near(),
                grf.getRaffle_method(),
                grf.getWork_status(),
                grf.getRelate_class(),
                grf.getGame_id(),
                grf.getRule_id()
            });
            return re;
        } catch (DataAccessException e) {
            logger.error("", e);
            SQLException sqle = (SQLException) e.getCause();
            logger.error("Error code: " + sqle.getErrorCode());
            logger.error("SQL state: " + sqle.getSQLState());
            return -2;
        }
//        int[] update = jdbcTemplate.batchUpdate(sql, new BatchPreparedStatementSetter() {
//            @Override
//            public void setValues(PreparedStatement ps, int i) throws SQLException {
//                GameRaffleRule grf = lgrf.get(i);
//                int j = 1;
//                ps.setInt(j++, grf.getOpen_id());
//                ps.setInt(j++, grf.getPlay_id());
//                ps.setInt(j++, grf.getClass_id());
//                ps.setInt(j++, grf.getRule_id());
//                ps.setInt(j++, grf.getBasic_num());
//                ps.setInt(j++, grf.getSpecial_num());
//                ps.setInt(j++, grf.getBlue_num());
//                ps.setInt(j++, grf.getNo_order());
//                ps.setInt(j++, grf.getMatch_pos());
//                ps.setInt(j++, grf.getMatch_near());
//                ps.setInt(j++, grf.getRaffle_method());
//                ps.setInt(j++, grf.getWork_status());
//
//                if (i % 100 == 0) {
//                    ps.executeBatch();                                                
//                }
//            }
//
//            @Override
//            public int getBatchSize() {
//                return lgrf.size();
//            }
//        });
    }

    /**
     * 获取游戏抽奖规则信息列表
     *
     * @param jdbcTemplate
     * @return
     */
    @Override
    public List<GameRaffleRule> selectGameRaffleRuleList(JdbcTemplate jdbcTemplate) {
        return this.queryForList(jdbcTemplate, SELECT_GAME_RAFFLE_RULE_LIST, null, GameRaffleRule.class);
    }

    /**
     * 根据游戏编号、玩法编号和抽奖规则编号获取抽奖规则信息
     *
     * @param jdbcTemplate
     * @param gameid
     * @param playid
     * @param ruleid
     * @return
     */
    @Override
    public GameRaffleRule selectGameRaffleRuleByRuleId(JdbcTemplate jdbcTemplate, int gameid, int playid, int ruleid) {
        return this.queryForObject(jdbcTemplate, SELECT_GAME_RAFFLE_RULE_BYIDS, new Object[]{gameid, playid, ruleid}, GameRaffleRule.class);
    }
}
