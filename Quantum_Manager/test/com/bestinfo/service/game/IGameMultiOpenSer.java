package com.bestinfo.service.game;

import com.bestinfo.bean.game.GameMultiOpen;
import java.util.List;
import org.springframework.jdbc.core.JdbcTemplate;

/**
 * 游戏多次开奖
 *
 * @author chenliping
 */
public interface IGameMultiOpenSer {

    /**
     * 游戏多次开奖增加
     * @param gmo
     * @return
     */
    public int addGameMultiOpen(GameMultiOpen gmo);

    /**
     * 根据游戏编号查询游戏多次开奖信息
     *
     * @param jdbcTemplate
     * @param gameid
     * @return
     */
    public List<GameMultiOpen> getGameMultiOpenByGameid(JdbcTemplate jdbcTemplate, int gameid);
    /**
     * 修改开奖次数信息
     * @param gameMultiOpen
     * @return 
     */
    public int updateGameMultiOpen(GameMultiOpen gameMultiOpen);
}
