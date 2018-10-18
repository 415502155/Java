package com.bestinfo.service.game;

import com.bestinfo.bean.game.GameRaffleRule;
import java.util.List;

/**
 * 游戏抽奖规则
 *
 * @author chenliping
 */
public interface IGameLuckyRuleSer {

    /**
     * 增加游戏抽奖规则
     *
     * @param grr
     * @return
     */
    public int addGameRaffleRule(GameRaffleRule grr);

    /**
     * 根据游戏编号查询游戏抽奖规则
     *
     * @param gameid
     * @return
     */
    public List<GameRaffleRule> getGameRaffleRuleByGameid(int gameid);

    /**
     * 获取抽奖规则全部数据
     *
     * @return
     */
    public List<GameRaffleRule> getGameRaffleRuleList();

    /**
     * 根据规则编号获取抽奖规则
     *  @param gameid
     * @param playid
     * @param ruleid
     * @return
     */
    public GameRaffleRule getGameRaffleRuleByRuleId(int gameid,int playid,int ruleid);
    /**
     * 修改
     * @param gameRaffleRule
     * @return 
     */
     public int updateGameRaffleRule(GameRaffleRule gameRaffleRule);
}
