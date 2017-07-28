package com.bestinfo.dao.stat;

import com.bestinfo.bean.stat.StatPrizeAnn;
import java.util.List;
import org.springframework.jdbc.core.JdbcTemplate;

/**
 * 彩票终端中奖情况查询
 *
 * @author YangRong
 */
public interface ITmnQueryPrizeDao {

    /**
     * 终端普通游戏中奖情况查询列表,只有起始期号
     *
     * @param jdbcTemplate
     * @param gameId
     * @param terminalId
     * @param beginDrawId
     * @return
     */
    public List<StatPrizeAnn> getTmnPrizeListByDraw(JdbcTemplate jdbcTemplate, int gameId, int terminalId, int beginDrawId);

    /**
     * 终端普通游戏中奖情况查询列表,有起始期号,终止期号
     *
     * @param jdbcTemplate
     * @param gameId
     * @param terminalId
     * @param beginDrawId
     * @param endDrawId
     * @return
     */
    public List<StatPrizeAnn> getTmnPrizeListBy2Draw(JdbcTemplate jdbcTemplate, int gameId, int terminalId, int beginDrawId, int endDrawId);

    /**
     * 终端快开游戏中奖情况查询列表,只有起始期号
     *
     * @param jdbcTemplate
     * @param gameId
     * @param terminalId
     * @param beginKenoDrawId
     * @return
     */
    public List<StatPrizeAnn> getTmnPrizeListByKenoDraw(JdbcTemplate jdbcTemplate, int gameId, int terminalId, int beginKenoDrawId);

    /**
     * 终端快开游戏中奖情况查询列表,有起始期号,结束期号
     *
     * @param jdbcTemplate
     * @param gameId
     * @param terminalId
     * @param beginKenoDrawId
     * @param endKenoDrawId
     * @return
     */
    public List<StatPrizeAnn> getTmnPrizeListByKeno2Draw(JdbcTemplate jdbcTemplate, int gameId, int terminalId, int beginKenoDrawId, int endKenoDrawId);

    /**
     * 站点普通游戏中奖情况查询列表,只有起始期号
     *
     * @param jdbcTemplate
     * @param gameId
     * @param terminalIds
     * @param beginDrawId
     * @return
     */
    public List<StatPrizeAnn> getSitePrizeListByDraw(JdbcTemplate jdbcTemplate, int gameId, String terminalIds, int beginDrawId);

    /**
     * 站点普通游戏中奖情况查询列表,有起始期号,终止期号
     *
     * @param jdbcTemplate
     * @param gameId
     * @param terminalIds
     * @param beginDrawId
     * @param endDrawId
     * @return
     */
    public List<StatPrizeAnn> getSitePrizeListBy2Draw(JdbcTemplate jdbcTemplate, int gameId, String terminalIds, int beginDrawId, int endDrawId);

    /**
     * 站点快开游戏中奖情况查询列表,只有起始期号
     *
     * @param jdbcTemplate
     * @param gameId
     * @param terminalIds
     * @param beginKenoDrawId
     * @return
     */
    public List<StatPrizeAnn> getSitePrizeListByKenoDraw(JdbcTemplate jdbcTemplate, int gameId, String terminalIds, int beginKenoDrawId);

    /**
     * 站点快开游戏中奖情况查询列表,有起始期号,结束期号
     *
     * @param jdbcTemplate
     * @param gameId
     * @param terminalIds
     * @param beginKenoDrawId
     * @param endKenoDrawId
     * @return
     */
    public List<StatPrizeAnn> getSitePrizeListByKeno2Draw(JdbcTemplate jdbcTemplate, int gameId, String terminalIds, int beginKenoDrawId, int endKenoDrawId);
}
