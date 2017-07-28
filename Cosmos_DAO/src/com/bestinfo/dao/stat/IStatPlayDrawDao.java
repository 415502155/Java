package com.bestinfo.dao.stat;

import com.bestinfo.bean.stat.StatPlayDraw;
import java.util.List;
import org.springframework.jdbc.core.JdbcTemplate;

/**
 * 结算统计表-玩法销售统计表(T_stat_play_draw)
 *
 */
public interface IStatPlayDrawDao {

    /**
     * merge插入玩法统计
     *
     * @param jdebTemplate
     * @param playStatList
     * @return
     */
    int mergeStatPlayDraw(JdbcTemplate jdebTemplate, List<StatPlayDraw> playStatList);

    /**
     * 获取玩法统计
     *
     * @param jdebTemplate
     * @param game_id
     * @param play_id
     * @param draw_id
     * @return
     */
    public StatPlayDraw getStatPlayDraw(JdbcTemplate jdebTemplate, int game_id, int play_id, int draw_id);
}
