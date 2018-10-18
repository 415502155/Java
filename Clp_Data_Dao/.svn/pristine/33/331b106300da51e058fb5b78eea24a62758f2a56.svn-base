package com.bestinfo.dao.game;

import com.bestinfo.bean.game.GameFundsProportion;
import java.util.List;
import java.util.Map;
import org.springframework.jdbc.core.JdbcTemplate;

/**
 * 游戏资金比例
 *
 * @author chenliping
 */
public interface IGameFundsProportionDao {

    /**
     * 根据gameid获取游戏资金比例
     *
     * @param jdbcTemplate
     * @param gameid
     * @return
     */
    public GameFundsProportion selectGameFundsByGameid(JdbcTemplate jdbcTemplate, int gameid);

    /**
     * 根据gameid列表获取游戏资金比例信息，拼装成Map返回（key为游戏编号，value为游戏资金比例信息）
     * @param jdbcTemplate
     * @param gameIds
     * @return 
     */
    public Map<Integer,GameFundsProportion> selectGameFundsMapByGameIdList(JdbcTemplate jdbcTemplate, List<Integer> gameIds);

    /**
     * 根据gameId获取游戏资金比例
     *
     * @param jdbcTemplate
     * @param gameid
     * @return
     */
    public List<GameFundsProportion> selectFundsByGameid(JdbcTemplate jdbcTemplate, int gameid);

    /**
     * 获取游戏资金数据列表
     *
     * @param jdbcTemplate
     * @return
     */
    public List<GameFundsProportion> selectFundsList(JdbcTemplate jdbcTemplate);

    /**
     * 添加游戏资金信息
     *
     * @param jdbcTemplate
     * @param gfp
     * @return
     */
    public int inserGameFundsProportion(JdbcTemplate jdbcTemplate, GameFundsProportion gfp);

    /**
     * 根据gameid获取资金比例信息
     *
     * @param jdbcTemplate
     * @param gameid
     * @return
     */
    public GameFundsProportion getGameFundsProportionById(JdbcTemplate jdbcTemplate, int gameid);

    /**
     * 修改游戏资金信息
     *
     * @param jdbcTemplate
     * @param gfp
     * @return
     */
    public int updateGameFundsProportion(JdbcTemplate jdbcTemplate, GameFundsProportion gfp);
}
