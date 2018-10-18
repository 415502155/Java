package com.bestinfo.dao.ticket;

import com.bestinfo.bean.game.OpenprizeInfo;
import java.util.List;
import org.springframework.jdbc.core.JdbcTemplate;

/**
 * 公告信息
 */
public interface IOpenPrizeInfoDao {

    /**
     * 添加开奖公告信息
     *
     * @param jdbcTemplate
     * @param openPrizeInfo
     * @return
     */
    public int insertOpenPrizeInfo(JdbcTemplate jdbcTemplate, OpenprizeInfo openPrizeInfo);

    /**
     * 获取开奖公告信息
     *
     * @param jdbcTemplate
     * @param openPrizeInfo
     * @return
     */
    public OpenprizeInfo getOpenprizeInfo(JdbcTemplate jdbcTemplate, OpenprizeInfo openPrizeInfo);

    /**
     * 修改开奖公告信息
     *
     * @param jdbcTemplate
     * @param openPrizeInfo
     * @return
     */
    public int updateOpenprizeInfo(JdbcTemplate jdbcTemplate, OpenprizeInfo openPrizeInfo);

    /**
     * 删除某个keno期的开奖公告
     *
     * @param jdbcTemplate
     * @param game_id
     * @param draw_id
     * @param keno_draw_id
     * @return
     */
    public int deleteOpenprize(JdbcTemplate jdbcTemplate, int game_id, int draw_id, int keno_draw_id);

    /**
     * 获取开奖公告信息列表
     *
     * @param jdbcTemplate
     * @param game_id
     * @param begin_draw_name
     * @param end_draw_name
     * @return
     */
    public List<OpenprizeInfo> getOpenprizeInfoList(JdbcTemplate jdbcTemplate, int game_id, String begin_draw_name, String end_draw_name);

    /**
     * merge开奖公告信息
     *
     * @param jdbcTemplate
     * @param openPrizeInfo
     * @return
     */
    public int mergeOpenprize(JdbcTemplate jdbcTemplate, OpenprizeInfo openPrizeInfo);
}
