package com.bestinfo.dao.stat;

import com.bestinfo.bean.stat.StatKenoPrizeAnn;
import com.bestinfo.bean.stat.StatPrizeAnn;
import java.util.List;
import org.springframework.jdbc.core.JdbcTemplate;

/**
 * 结算统计表-快开游戏中奖汇总(T_stat_keno_prize_ann)
 *
 * @author chenliping
 */
public interface IStatKenoPrizeAnnDao {

    /**
     * 快开游戏中奖汇总批量写入
     *
     * @param jdbcTemplate
     * @param lsp
     * @return
     */
    public int addStatPrizeAnn(JdbcTemplate jdbcTemplate, List<StatKenoPrizeAnn> lsp);

    /**
     * 指定游戏，期号，快开期号，奖级编号，更新单注奖金
     *
     * @param jdbcTemplate
     * @param lsp
     * @return
     */
    public int updateStatPrizeAnn(JdbcTemplate jdbcTemplate, List<StatKenoPrizeAnn> lsp);

    /**
     * 指定游戏、期、快开期，查询中奖汇总数据
     *
     * @param jdbcTemplate
     * @param gameid
     * @param drawid
     * @param keno_draw_id
     * @return
     */
    public List<StatKenoPrizeAnn> selectStatPrize(JdbcTemplate jdbcTemplate, int gameid, int drawid, int keno_draw_id);

    /**
     * 根据game_id、draw_id统计总中奖额
     *
     * @param jdbcTemplate
     * @param gameid
     * @param drawid
     * @return
     */
    public StatKenoPrizeAnn sumPrizeByGameAndDraw(JdbcTemplate jdbcTemplate, int gameid, int drawid);

    /**
     * keno 本期风采中奖金额
     *
     * @param jdbcTemplate
     * @param gameid
     * @param draw_id
     * @param keno_draw_id
     * @return
     */
    public Integer getKenoTotalMoney(JdbcTemplate jdbcTemplate, int gameid, int draw_id, int keno_draw_id);

    /**
     * 删除某个keno期的中奖数据
     *
     * @param jdbcTemplate
     * @param gameid
     * @param draw_id
     * @param keno_draw_id
     * @return
     */
    public Integer deletePrizeAnn(JdbcTemplate jdbcTemplate, int gameid, int draw_id, int keno_draw_id);

    /**
     * 获取基础库里的普通游戏中奖汇总数据
     *
     * @param jdbcTemplate
     * @return
     */
    public List<StatKenoPrizeAnn> getStatKenoPrizeAnnList(JdbcTemplate jdbcTemplate);

    /**
     * 将快开游戏中奖汇总数据统计计算到普通游戏中奖汇总数据表中
     *
     * @param jdbcTemplate
     * @param gameid
     * @param draw_id
     * @return
     */
    public List<StatPrizeAnn> getStatPrizeAnnList(JdbcTemplate jdbcTemplate, int gameid, int draw_id);

}
