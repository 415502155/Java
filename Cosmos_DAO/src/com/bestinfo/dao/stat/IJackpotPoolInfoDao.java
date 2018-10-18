package com.bestinfo.dao.stat;

import com.bestinfo.bean.stat.JackpotPoolInfo;
import java.util.List;
import org.springframework.jdbc.core.JdbcTemplate;

/**
 * 结算统计表-游戏奖池调节基金(T_jackpot_pool_info)
 *
 * @author yangyuefu
 */
public interface IJackpotPoolInfoDao {

    /**
     * 获取JackpotPoolInfo对象
     *
     * @param jdbcTemplate
     * @param game_id
     * @param play_id
     * @param draw_id
     * @param keno_draw_id
     * @return
     */
    public JackpotPoolInfo getJackpotPoolInfo(JdbcTemplate jdbcTemplate, int game_id, int play_id, int draw_id, int keno_draw_id);

    /**
     * 新增游戏奖池
     *
     * @param jdbcTemplate
     * @param jackpot
     * @return
     */
    public int insertJackpotPoolInfo(JdbcTemplate jdbcTemplate, JackpotPoolInfo jackpot);

    /**
     * 新增游戏奖池（merge）
     *
     * @param jdbcTemplate
     * @param jackpot
     * @return
     */
    public int insertJackpotPoolInfoMerge(JdbcTemplate jdbcTemplate, JackpotPoolInfo jackpot);

    /**
     * 更新游戏奖池
     *
     * @param jdbcTemplate
     * @param jackpot
     * @return
     */
    public int updateJackpotPoolInfo(JdbcTemplate jdbcTemplate, JackpotPoolInfo jackpot);

    /**
     * 获取当前期之前的奖池列表
     *
     * @param jdbcTemplate
     * @param game_id
     * @param draw_id
     * @return
     */
    public List<JackpotPoolInfo> getFrontJackpotList(JdbcTemplate jdbcTemplate, int game_id, int draw_id);

    /**
     * 获取当前期的总投注额度
     *
     * @param jdbcTemplate
     * @param game_id
     * @param draw_id
     * @param kdraw_id
     * @return
     */
    public JackpotPoolInfo getJackpotPoolInfoById(JdbcTemplate jdbcTemplate, int game_id, int draw_id, int kdraw_id);
}
