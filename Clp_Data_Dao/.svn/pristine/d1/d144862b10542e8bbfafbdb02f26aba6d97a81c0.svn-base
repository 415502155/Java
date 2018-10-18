package com.bestinfo.dao.stat;

import com.bestinfo.bean.stat.StatPrizeAnn;
import java.util.List;
import org.springframework.jdbc.core.JdbcTemplate;

/**
 * 普通游戏中奖汇总
 *
 * @author chenliping
 */
public interface IStatPrizeAnnDao {

    /**
     * 普通游戏中奖汇总批量写入
     *
     * @param jdbcTemplate
     * @param lsp
     * @return
     */
    public int addStatPrizeAnn(JdbcTemplate jdbcTemplate, List<StatPrizeAnn> lsp);

    /**
     * 普通游戏中奖汇总批量写入
     *
     * @param jdbcTemplate
     * @param lsp
     * @return
     */
    public int addStatPrizeAnnMerge(JdbcTemplate jdbcTemplate, List<StatPrizeAnn> lsp);

    /**
     * 指定游戏，期号，开奖次数，奖级编号，更新单注奖金
     *
     * @param jdbcTemplate
     * @param lsp
     * @return
     */
    public int updateStatPrizeAnn(JdbcTemplate jdbcTemplate, List<StatPrizeAnn> lsp);

    /**
     * 指定游戏指定期指定开奖次数，查询中奖汇总数据
     *
     * @param jdbcTemplate
     * @param gameid
     * @param drawid
     * @param openid
     * @return
     */
    public List<StatPrizeAnn> selectStatPrize(JdbcTemplate jdbcTemplate, int gameid, int drawid, int openid);

    /**
     * 指定游戏指定期指定开奖次数，查询中奖汇总数据
     *
     * @param jdbcTemplate
     * @param gameid
     * @param drawid
     * @param open_ids
     * @return
     */
    public List<StatPrizeAnn> selectStatPrizeByOpenIds(JdbcTemplate jdbcTemplate, int gameid, int drawid, String open_ids);

    /**
     * 获取中奖结果集
     *
     * @param jdbcTemplate
     * @param game_id
     * @param draw_id
     * @return
     */
    public List<StatPrizeAnn> getStatPrizeAnnListByIds(JdbcTemplate jdbcTemplate, int game_id, int draw_id);

    /**
     * 获取普通游戏中奖汇总数据
     *
     * @param jdbcTemplate
     * @return
     */
    public List<StatPrizeAnn> getStatPrizeAnnList(JdbcTemplate jdbcTemplate);

    /**
     * 删除某游戏某期的中奖数据
     *
     * @param jdbcTemplate
     * @param game_id
     * @param draw_id
     * @return
     */
    public int deleteStatPrizeAnnByGameDraw(JdbcTemplate jdbcTemplate, int game_id, int draw_id);

    /**
     * 指定游戏指定期指定开奖次数，奖级倒序查询中奖汇总数据
     *
     * @param jdbcTemplate
     * @param gameid
     * @param drawid
     * @param open_id
     * @return
     */
    public List<StatPrizeAnn> getPrizeByGameAndDrawAndOpenIdDesc(JdbcTemplate jdbcTemplate, int gameid, int drawid, String open_id);

    /**
     * 指定游戏指定期，奖级倒序查询中奖汇总数据
     *
     * @param jdbcTemplate
     * @param gameid
     * @param drawid
     * @return
     */
    public List<StatPrizeAnn> getPrizeByGameAndDrawDesc(JdbcTemplate jdbcTemplate, int gameid, int drawid);

    /**
     * 获取某期所有系统的中奖信息<br>
     * 包括贝英斯、联销省份、维赛特<br>
     *
     * @param jdbcTemplate
     * @param gameid
     * @param drawid
     * @return
     */
    public List<StatPrizeAnn> getAllSystemPrize(JdbcTemplate jdbcTemplate, int gameid, int drawid);
}
