package com.bestinfo.service.game;

import com.bestinfo.bean.game.GameFundsProportion;
import java.util.List;

/**
 * 游戏资金比例
 *
 * @author chenliping
 */
public interface IGameFundsProportionSer {

    public List<GameFundsProportion> getGameFundsProportionById(int gameid);

    public List<GameFundsProportion> getGameFundsProportionList();

    public int insertGameFundsProportion(GameFundsProportion gfp);

    public GameFundsProportion getGameFundsProportionByIds(int gameid, int drawid);

    public int updateGameFundsProportion(GameFundsProportion gameFundsProportion);
}
