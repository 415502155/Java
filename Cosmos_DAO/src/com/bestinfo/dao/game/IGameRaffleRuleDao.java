package com.bestinfo.dao.game;

import com.bestinfo.bean.game.GameRaffleRule;
import java.util.List;
import org.springframework.jdbc.core.JdbcTemplate;

/**
 * 抽奖规则
 *
 * @author chenliping
 */
public interface IGameRaffleRuleDao {

    /**
     * 增加游戏抽奖规则
     *
     * @param jdbcTemplate
     * @param grr
     * @return 0成功
     */
    public int insertGameRaffleRule(JdbcTemplate jdbcTemplate, GameRaffleRule grr);

    /**
     * 根据游戏id查询该游戏抽奖规则
     *
     * @param jdbcTemplate
     * @param gameid
     * @return
     */
    public List<GameRaffleRule> selectGameRaffleRuleByGameid(JdbcTemplate jdbcTemplate, int gameid);

    /**
     * 更新游戏抽奖规则（条件游戏编号，规则编号）
     *
     * @param jdbcTemplate
     * @param grf
     */
    public int updateGameRaffleRule(JdbcTemplate jdbcTemplate, GameRaffleRule grf);

    /**
     * 获取游戏抽奖规则列表
     *
     * @param jdbcTemplate
     * @return
     */
    public List<GameRaffleRule> selectGameRaffleRuleList(JdbcTemplate jdbcTemplate);

    /**
     * 根据规则编号查询该游戏抽奖规则
     *
     * @param jdbcTemplate
     * @param gameid 游戏编号
     * @param playid 玩法编号
     * @param ruleid 规则编号
     * @return
     */
    public GameRaffleRule selectGameRaffleRuleByRuleId(JdbcTemplate jdbcTemplate, int gameid, int playid, int ruleid);
}
