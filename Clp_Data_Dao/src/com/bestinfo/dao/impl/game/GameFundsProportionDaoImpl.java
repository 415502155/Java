package com.bestinfo.dao.impl.game;

import com.bestinfo.bean.game.GameFundsProportion;
import com.bestinfo.dao.game.IGameFundsProportionDao;
import com.bestinfo.dao.impl.BaseDaoImpl;
import java.sql.SQLException;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.commons.lang.StringUtils;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

/**
 * 游戏资金比例
 *
 * @author chenliping
 */
@Repository
public class GameFundsProportionDaoImpl extends BaseDaoImpl implements IGameFundsProportionDao {

//    private final Logger logger = Logger.getLogger(GameFundsProportionDaoImpl.class);
    private static final int ORACLE_MAX_IN_NUM = 999;

    private static final String SELECT_GAME_FUNDS_BY_GAME_ID = "select issue_proportion_station,cash_fee_rate from t_game_funds_proportion where game_id = ?";

    private static final String SELECT_GAME_FUNDS = "select  game_id,welfare_proportion,welfare_proportion_clp,welfare_proportion_province,welfare_proportion_city,issue_proportion,issue_proportion_clp,issue_proportion_province,issue_proportion_city,issue_proportion_station,return_proportion,reserve_proportion,cash_fee_rate from t_game_funds_proportion where game_id=?";

    private static final String SELECT_GAME_FUNDS_LIST = "select  game_id,welfare_proportion,welfare_proportion_clp,welfare_proportion_province,welfare_proportion_city,issue_proportion,issue_proportion_clp,issue_proportion_province,issue_proportion_city,issue_proportion_station,return_proportion,reserve_proportion,cash_fee_rate  from t_game_funds_proportion";

    private static final String INSERT_GAME_FUNDS = "insert into t_game_funds_proportion (game_id, draw_id, welfare_proportion, welfare_proportion_clp, welfare_proportion_province, issue_proportion, issue_proportion_clp, issue_proportion_province, issue_proportion_city, issue_proportion_station, return_proportion, reserve_proportion) values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

    /**
     * 根据game_id获取资金比例记录
     */
    private static final String SELECT_GAME_FUNDS_BY_GAMEID = "select  game_id,welfare_proportion,welfare_proportion_clp,welfare_proportion_province,welfare_proportion_city,issue_proportion,issue_proportion_clp,issue_proportion_province,issue_proportion_city,issue_proportion_station,return_proportion,reserve_proportion,cash_fee_rate  from t_game_funds_proportion  where game_id=?";

    private static final String UPDATE_GAME_FUNDS = "update t_game_funds_proportion set welfare_proportion = ?, welfare_proportion_clp = ?, welfare_proportion_province = ?, issue_proportion = ?, issue_proportion_clp = ?, issue_proportion_province = ?, issue_proportion_city = ?, issue_proportion_station = ?, return_proportion = ?, reserve_proportion = ? where game_id = ? and draw_id = ?";

    /**
     * 根据gameId获取游戏资金比例列表信息
     *
     * @param jdbcTemplate
     * @param gameid
     * @return
     */
    @Override
    public List<GameFundsProportion> selectFundsByGameid(JdbcTemplate jdbcTemplate, int gameid) {
        List<GameFundsProportion> lgp = this.queryForList(jdbcTemplate, SELECT_GAME_FUNDS, new Object[]{gameid}, GameFundsProportion.class);
        return lgp;
    }

    /**
     * 获取游戏资金列表信息
     *
     * @param jdbcTemplate
     * @return
     */
    @Override
    public List<GameFundsProportion> selectFundsList(JdbcTemplate jdbcTemplate) {
        List<GameFundsProportion> lgp = this.queryForList(jdbcTemplate, SELECT_GAME_FUNDS_LIST, null, GameFundsProportion.class);
        return lgp;
    }

    /**
     * 新增游戏资金列表信息
     *
     * @param jdbcTemplate
     * @param gfp
     * @return
     */
    @Override
    public int inserGameFundsProportion(JdbcTemplate jdbcTemplate, GameFundsProportion gfp) {
        int re = -1;
        try {
            re = jdbcTemplate.update(INSERT_GAME_FUNDS, new Object[]{
                gfp.getGame_id(),
                gfp.getDraw_id(),
                gfp.getWelfare_proportion(),
                gfp.getWelfare_proportion_clp(),
                gfp.getWelfare_proportion_province(),
                gfp.getIssue_proportion(),
                gfp.getIssue_proportion_clp(),
                gfp.getIssue_proportion_province(),
                gfp.getIssue_proportion_city(),
                gfp.getIssue_proportion_station(),
                gfp.getReturn_proportion(),
                gfp.getReserve_proportion()
            });
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
     * 根据gameid获取资金比例信息----update by clp
     *
     * @param jdbcTemplate
     * @param gameid
     * @return
     */
    @Override
    public GameFundsProportion getGameFundsProportionById(JdbcTemplate jdbcTemplate, int gameid) {
        return this.queryForObject(jdbcTemplate, SELECT_GAME_FUNDS_BY_GAMEID, new Object[]{gameid}, GameFundsProportion.class);
    }

    /**
     * 修改
     *
     * @param jdbcTemplate
     * @param gfp
     * @return
     */
    @Override
    public int updateGameFundsProportion(JdbcTemplate jdbcTemplate, GameFundsProportion gfp) {
        int re = -1;
        try {
            re = jdbcTemplate.update(UPDATE_GAME_FUNDS, new Object[]{
                gfp.getWelfare_proportion(),
                gfp.getWelfare_proportion_clp(),
                gfp.getWelfare_proportion_province(),
                gfp.getIssue_proportion(),
                gfp.getIssue_proportion_clp(),
                gfp.getIssue_proportion_province(),
                gfp.getIssue_proportion_city(),
                gfp.getIssue_proportion_station(),
                gfp.getReturn_proportion(),
                gfp.getReserve_proportion(),
                gfp.getGame_id(),
                gfp.getDraw_id()
            });
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
     * 根据游戏编号获取资金信息
     *
     * @param jdbcTemplate
     * @param gameid
     * @return
     */
    @Override
    public GameFundsProportion selectGameFundsByGameid(JdbcTemplate jdbcTemplate, int gameid) {
        return this.queryForObject(jdbcTemplate, SELECT_GAME_FUNDS_BY_GAME_ID, new Object[]{gameid}, GameFundsProportion.class);
    }

    /**
     * 根据gameid列表获取游戏资金比例信息，拼装成Map返回（key为游戏编号，value为游戏资金比例信息）
     *
     * @param jdbcTemplate
     * @param gameIds
     * @return
     */
    @Override
    public Map<Integer, GameFundsProportion> selectGameFundsMapByGameIdList(JdbcTemplate jdbcTemplate, List<Integer> gameIds) {
        if (gameIds == null || gameIds.isEmpty() || gameIds.size() == 0 || gameIds.size() > ORACLE_MAX_IN_NUM) {
            logger.warn("para game id list is incorrect.");
            return Collections.EMPTY_MAP;
        }

        Map<Integer, GameFundsProportion> map = new HashMap<Integer, GameFundsProportion>();
        StringBuilder sb = new StringBuilder();
        sb.append("select GAME_ID,ISSUE_PROPORTION_STATION,CASH_FEE_RATE from T_GAME_FUNDS_PROPORTION where GAME_ID in (");
        sb.append(StringUtils.join(gameIds, ","));
        sb.append(") order by GAME_ID ASC");

        List<GameFundsProportion> gameFundsProList = this.queryForList(jdbcTemplate, sb.toString(), null, GameFundsProportion.class);
        for (GameFundsProportion gfp : gameFundsProList) {
            map.put(gfp.getGame_id(), gfp);
        }
        return map;
    }

}
