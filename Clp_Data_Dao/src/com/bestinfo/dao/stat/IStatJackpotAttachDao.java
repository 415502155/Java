package com.bestinfo.dao.stat;

import com.bestinfo.bean.stat.StatJackpotAttach;
import org.springframework.jdbc.core.JdbcTemplate;

/**
 * 奖池附属表，用来记录每个奖级的小数点和补足提取(t_stat_jackpot_attach)
 */
public interface IStatJackpotAttachDao {

    /**
     * 获取StatJackpotAttach对象
     *
     * @param jdbcTemplate
     * @param game_id
     * @param play_id
     * @param draw_id
     * @param keno_draw_id
     * @return
     */
    public StatJackpotAttach getStatJackpotAttach(JdbcTemplate jdbcTemplate, int game_id, int play_id, int draw_id, int keno_draw_id);

    /**
     * 新增游戏奖池（merge）
     *
     * @param jdbcTemplate
     * @param jackpot
     * @return
     */
    public int mergeStatJackpotAttach(JdbcTemplate jdbcTemplate, StatJackpotAttach jackpot);

    /**
     * 获取当前期的总投注额度
     *
     * @param jdbcTemplate
     * @param game_id
     * @param draw_id
     * @param kdraw_id
     * @return
     */
    public StatJackpotAttach getStatJackpotAttachByGameDraw(JdbcTemplate jdbcTemplate, int game_id, int draw_id, int kdraw_id);
}
