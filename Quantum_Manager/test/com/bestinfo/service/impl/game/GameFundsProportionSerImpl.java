package com.bestinfo.service.impl.game;

import com.bestinfo.bean.game.GameFundsProportion;
import com.bestinfo.dao.game.IGameFundsProportionDao;
import com.bestinfo.service.game.IGameFundsProportionSer;
import java.util.List;
import javax.annotation.Resource;
import org.apache.log4j.Logger;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

/**
 * 游戏资金比例
 *
 * @author chenliping
 */
@Service
public class GameFundsProportionSerImpl implements IGameFundsProportionSer {

    private final Logger logger = Logger.getLogger(GameFundsProportionSerImpl.class);

    @Resource
    private IGameFundsProportionDao gpd;
    @Resource
    private JdbcTemplate metaJdbcTemplate;

    /**
     * 根据id获取资金分配
     *
     * @param gameid
     * @return
     */
    @Override
    public List<GameFundsProportion> getGameFundsProportionById(int gameid) {
        return gpd.selectFundsByGameid(metaJdbcTemplate, gameid);
    }

    /**
     * 获取资金分配列表信息
     *
     * @return
     */
    @Override
    public List<GameFundsProportion> getGameFundsProportionList() {
        return gpd.selectFundsList(metaJdbcTemplate);
    }

    /**
     * 添加资金分配
     *
     * @param gfp
     * @return
     */
    @Override
    public int insertGameFundsProportion(GameFundsProportion gfp) {
        return gpd.inserGameFundsProportion(metaJdbcTemplate, gfp);
    }

    /**
     *
     * @param gameid
     * @param drawid
     * @return
     */
    @Override
    public GameFundsProportion getGameFundsProportionByIds(int gameid, int drawid) {
        return gpd.getGameFundsProportionById(metaJdbcTemplate, gameid);
    }

    /**
     * 编辑操作
     *
     * @param gameFundsProportion
     * @return
     */
    @Override
    public int updateGameFundsProportion(GameFundsProportion gameFundsProportion) {
        return gpd.updateGameFundsProportion(metaJdbcTemplate, gameFundsProportion);
    }

}
