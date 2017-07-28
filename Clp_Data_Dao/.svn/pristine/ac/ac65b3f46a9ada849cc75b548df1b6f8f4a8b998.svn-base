package com.bestinfo.dao.game;

import com.bestinfo.bean.game.PlayBetMode;
import java.util.List;
import org.springframework.jdbc.core.JdbcTemplate;

/**
 * 玩法投注方式配置
 *
 * @author user
 */
public interface IPlayBetModeDao {
    /**
     * 更新玩法投注方式配置
     *
     * @param jdbcTemplate
     * @param pm
     */
    public int updatePlayBetMode(JdbcTemplate jdbcTemplate, PlayBetMode pm);

       /**
     * 查询玩法投注方式配置数据列表
     *
     * @param jdbcTemplate
     * @return
     */
    public List<PlayBetMode> selectPlayBetModeList(JdbcTemplate jdbcTemplate);
    
     /**
     * 根据游戏Id和玩法Id查询玩法投注方式配置数据列表
     *
     * @param jdbcTemplate
     * @return
     */
    public List<PlayBetMode> selectPlayBetModeListById(JdbcTemplate jdbcTemplate,int gameId,int playId);
}
