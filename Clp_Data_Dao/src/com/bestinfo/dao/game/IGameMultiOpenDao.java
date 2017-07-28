package com.bestinfo.dao.game;

import com.bestinfo.bean.game.GameMultiOpen;
import java.util.List;
import org.springframework.jdbc.core.JdbcTemplate;

/**
 * 游戏开奖次数
 *
 * @author chenliping
 */
public interface IGameMultiOpenDao {

    /**
     * 增加游戏开奖次数
     *
     * @param jdbcTemplate
     * @param gmo
     */
    public int insertGameMultiOpen(JdbcTemplate jdbcTemplate, GameMultiOpen gmo);

    /**
     * 根据游戏编号查询游戏开奖次数
     *
     * @param jdbcTemplate
     * @param gameid 游戏编号
     * @return
     */
    public List<GameMultiOpen> selectGameMultiOpenByGameid(JdbcTemplate jdbcTemplate, int gameid);

    /**
     * 更新游戏开奖次数
     *
     * @param jdbcTemplate
     * @param gmo
     */
    public int updateGameMultiOpen(JdbcTemplate jdbcTemplate, GameMultiOpen gmo);

       /**
     * 查询游戏开奖次数数据列表
     *
     * @param jdbcTemplate
     * @return
     */
    public List<GameMultiOpen> selectGameMultiOpenList(JdbcTemplate jdbcTemplate);
}
