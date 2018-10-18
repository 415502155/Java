package com.bestinfo.dao.stat;

import com.bestinfo.bean.stat.StatGameDraw;
import org.springframework.jdbc.core.JdbcTemplate;

/**
 * 结算统计表-游戏期(T_stat_game_draw)
 *
 * @author yangyuefu
 */
public interface IStatGameDrawDao {

    /**
     * 新增游戏期信息
     *
     * @param jdebTemplate
     * @param statGameDraw
     * @return
     */
    int insertStatGameDraw(JdbcTemplate jdebTemplate, StatGameDraw statGameDraw);

    /**
     * 新增游戏期信息
     *
     * @param jdebTemplate
     * @param statGameDraw
     * @return
     */
    int insertStatGameDrawMerge(JdbcTemplate jdebTemplate, StatGameDraw statGameDraw);

    /**
     * 根据游戏编号和draw_id获取期信息
     *
     * @param jdebTemplate
     * @param game_id
     * @param draw_id
     * @return
     */
    public StatGameDraw getStatGameDrawById(JdbcTemplate jdebTemplate, int game_id, int draw_id);

    /**
     * 获取某期所有系统的销售额<br>
     * 包括贝英斯、联销省份、维赛特<br>
     *
     * @param jdebTemplate
     * @param game_id
     * @param draw_id
     * @return
     */
    public StatGameDraw getAllSystemSaleMoney(JdbcTemplate jdebTemplate, int game_id, int draw_id);
}
