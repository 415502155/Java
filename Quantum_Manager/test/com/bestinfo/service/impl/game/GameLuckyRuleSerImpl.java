package com.bestinfo.service.impl.game;

import com.bestinfo.bean.game.GameRaffleRule;
import com.bestinfo.dao.game.IGameRaffleRuleDao;
import com.bestinfo.service.game.IGameLuckyRuleSer;
import java.util.List;
import javax.annotation.Resource;
import org.apache.log4j.Logger;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

/**
 *
 * @author chenliping
 */
@Service
public class GameLuckyRuleSerImpl implements IGameLuckyRuleSer {

    private final Logger logger = Logger.getLogger(GameLuckyRuleSerImpl.class);

    @Resource
    private IGameRaffleRuleDao gameRaffle;
    @Resource
    private JdbcTemplate metaJdbcTemplate;

    @Override
    public int addGameRaffleRule(GameRaffleRule grr) {
        int re = gameRaffle.insertGameRaffleRule(metaJdbcTemplate, grr);
        return re;
    }

    @Override
    public List<GameRaffleRule> getGameRaffleRuleByGameid(int gameid) {
        return gameRaffle.selectGameRaffleRuleByGameid(metaJdbcTemplate, gameid);
    }

    @Override
    public List<GameRaffleRule> getGameRaffleRuleList() {
        return gameRaffle.selectGameRaffleRuleList(metaJdbcTemplate);
    }

    @Override
    public GameRaffleRule getGameRaffleRuleByRuleId(int gameid,int playid,int ruleid) {
        return gameRaffle.selectGameRaffleRuleByRuleId(metaJdbcTemplate, gameid,playid,ruleid);
    }

    @Override
    public int updateGameRaffleRule(GameRaffleRule gameRaffleRule) {
        int result = gameRaffle.updateGameRaffleRule(metaJdbcTemplate, gameRaffleRule);
        return result > 0 ? 0 : -2;
    }
}
