package com.bestinfo.dao.game;

import com.bestinfo.bean.game.KenoDrawConfigure;
import java.util.List;
import org.springframework.jdbc.core.JdbcTemplate;

/**
 * 游戏-快开游戏期参数配置表(T_keno_draw_configure)
 *
 * @author yangyuefu
 */
public interface IKenoDrawConfigureDao {

    /**
     * 根据game_id获取快开游戏配置列表
     *
     * @param jdbcTemplate
     * @param game_id
     * @return
     */
    public List<KenoDrawConfigure> getConfigureByGame(JdbcTemplate jdbcTemplate,int game_id);
}
