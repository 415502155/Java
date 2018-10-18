package com.bestinfo.dao.impl.stat;

import com.bestinfo.bean.stat.StatPrizeAnn;
import com.bestinfo.dao.impl.BaseDaoImpl;
import com.bestinfo.dao.stat.ITmnQueryPrizeDao;
import java.util.List;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

/**
 * 彩票终端中奖情况查询
 *
 * @author YangRong
 */
@Repository
public class TmnQueryPrizeDaoImpl extends BaseDaoImpl implements ITmnQueryPrizeDao {

    private static final String SELECT_TMN_PRIZE_LIST_BY_DRAW = "select a.draw_id,a.class_id , b.class_name,a.prize_num,b.stake_prize  from t_stat_keno_distribution  a ,t_stat_prize_ann  b  where a.class_id=b.class_id and a.draw_id=b.draw_id and a.game_id=b.game_id and a.game_id=? and a.terminal_id=? and a.draw_id=?  ";
    private static final String SELECT_TMN_PRIZE_LIST_BY_KENO_DRAW = "select a.keno_draw_id,a.class_id, b.class_name,a.prize_num,b.stake_prize  from t_stat_keno_distribution  a ,t_stat_keno_prize_ann  b  where a.class_id=b.class_id and a.keno_draw_id=b.keno_draw_id and a.game_id=b.game_id and a.game_id=? and a.terminal_id=? and a.keno_draw_id=?  ";
    private static final String SELECT_TMN_PRIZE_LIST_BY_2DRAW = "select a.draw_id,a.class_id, b.class_name,a.prize_num,b.stake_prize  from t_stat_keno_distribution as a ,t_stat_prize_ann as b  where a.class_id=b.class_id and a.draw_id=b.draw_id and a.game_id=b.game_id and a.game_id=? and a.terminal_id=? and a.draw_id between ? and ?  ";
    private static final String SELECT_TMN_PRIZE_LIST_BY_KENO_2DRAW = "select a.keno_draw_id,a.class_id, b.class_name,a.prize_num,b.stake_prize  from t_stat_keno_distribution as a ,t_stat_keno_prize_ann as b  where a.class_id=b.class_id and a.keno_draw_id=b.keno_draw_id and a.game_id=b.game_id and a.game_id=? and a.terminal_id=? and a.keno_draw_id between ? and ?  ";
    private static final String SELECT_SITE_PRIZE_LIST_BY_DRAW = "select a.draw_id,a.class_id, b.class_name,a.prize_num,b.stake_prize  from t_stat_keno_distribution as a ,t_stat_prize_ann as b  where a.class_id=b.class_id and a.draw_id=b.draw_id and a.game_id=b.game_id and a.game_id=? and a.terminal_id in ? and a.draw_id=? order by a.terminal_id ";
    private static final String SELECT_SITE_PRIZE_LIST_BY_KENO_DRAW = "select a.keno_draw_id,a.class_id, b.class_name,a.prize_num,b.stake_prize  from t_stat_keno_distribution as a ,t_stat_keno_prize_ann as b  where a.class_id=b.class_id and a.keno_draw_id=b.keno_draw_id and a.game_id=b.game_id and a.game_id=? and a.terminal_id in ? and a.keno_draw_id=? order by a.terminal_id   ";
    private static final String SELECT_SITE_PRIZE_LIST_BY_2DRAW = "select a.draw_id,a.class_id, b.class_name,a.prize_num,b.stake_prize  from t_stat_keno_distribution as a ,t_stat_prize_ann as b  where a.class_id=b.class_id and a.draw_id=b.draw_id and a.game_id=b.game_id and a.game_id=? and a.terminal_id in ? and a.draw_id between ? and ?  order by a.terminal_id  ";
    private static final String SELECT_SITE_PRIZE_LIST_BY_KENO_2DRAW = "select a.keno_draw_id,a.class_id, b.class_name,a.prize_num,b.stake_prize  from t_stat_keno_distribution as a ,t_stat_keno_prize_ann as b  where a.class_id=b.class_id and a.keno_draw_id=b.keno_draw_id and a.game_id=b.game_id and a.game_id=? and a.terminal_id in ? and a.keno_draw_id between ? and ?  order by a.terminal_id  ";

    /**
     * 终端普通游戏中奖情况查询列表,只有起始期号
     *
     * @param jdbcTemplate
     * @param gameId
     * @param terminalId
     * @param beginDrawId
     * @return
     */
    @Override
    public List<StatPrizeAnn> getTmnPrizeListByDraw(JdbcTemplate jdbcTemplate, int gameId, int terminalId, int beginDrawId) {
        return this.queryForList(jdbcTemplate, SELECT_TMN_PRIZE_LIST_BY_DRAW, new Object[]{gameId, terminalId, beginDrawId}, StatPrizeAnn.class);

    }

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
    @Override
    public List<StatPrizeAnn> getTmnPrizeListBy2Draw(JdbcTemplate jdbcTemplate, int gameId, int terminalId, int beginDrawId, int endDrawId) {
        return this.queryForList(jdbcTemplate, SELECT_TMN_PRIZE_LIST_BY_2DRAW, new Object[]{gameId, terminalId, beginDrawId, endDrawId}, StatPrizeAnn.class);

    }

    /**
     * 终端快开游戏中奖情况查询列表,只有起始期号
     *
     * @param jdbcTemplate
     * @param gameId
     * @param terminalId
     * @param beginKenoDrawId
     * @return
     */
    @Override
    public List<StatPrizeAnn> getTmnPrizeListByKenoDraw(JdbcTemplate jdbcTemplate, int gameId, int terminalId, int beginKenoDrawId) {
        return this.queryForList(jdbcTemplate, SELECT_TMN_PRIZE_LIST_BY_KENO_DRAW, new Object[]{gameId, terminalId, beginKenoDrawId}, StatPrizeAnn.class);

    }

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
    @Override
    public List<StatPrizeAnn> getTmnPrizeListByKeno2Draw(JdbcTemplate jdbcTemplate, int gameId, int terminalId, int beginKenoDrawId, int endKenoDrawId) {
        return this.queryForList(jdbcTemplate, SELECT_TMN_PRIZE_LIST_BY_KENO_2DRAW, new Object[]{gameId, terminalId, beginKenoDrawId, endKenoDrawId}, StatPrizeAnn.class);

    }

    /**
     * 站点普通游戏中奖情况查询列表,只有起始期号
     *
     * @param jdbcTemplate
     * @param gameId
     * @param terminalIds
     * @param beginDrawId
     * @return
     */
    @Override
    public List<StatPrizeAnn> getSitePrizeListByDraw(JdbcTemplate jdbcTemplate, int gameId, String terminalIds, int beginDrawId) {
        return this.queryForList(jdbcTemplate, SELECT_SITE_PRIZE_LIST_BY_DRAW, new Object[]{gameId, terminalIds, beginDrawId}, StatPrizeAnn.class);

    }

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
    @Override
    public List<StatPrizeAnn> getSitePrizeListBy2Draw(JdbcTemplate jdbcTemplate, int gameId, String terminalIds, int beginDrawId, int endDrawId) {
        return this.queryForList(jdbcTemplate, SELECT_SITE_PRIZE_LIST_BY_2DRAW, new Object[]{gameId, terminalIds, beginDrawId, endDrawId}, StatPrizeAnn.class);

    }

    /**
     * 站点快开游戏中奖情况查询列表,只有起始期号
     *
     * @param jdbcTemplate
     * @param gameId
     * @param terminalIds
     * @param beginKenoDrawId
     * @return
     */
    @Override
    public List<StatPrizeAnn> getSitePrizeListByKenoDraw(JdbcTemplate jdbcTemplate, int gameId, String terminalIds, int beginKenoDrawId) {
        return this.queryForList(jdbcTemplate, SELECT_SITE_PRIZE_LIST_BY_KENO_DRAW, new Object[]{gameId, terminalIds, beginKenoDrawId}, StatPrizeAnn.class);

    }

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
    @Override
    public List<StatPrizeAnn> getSitePrizeListByKeno2Draw(JdbcTemplate jdbcTemplate, int gameId, String terminalIds, int beginKenoDrawId, int endKenoDrawId) {
        return this.queryForList(jdbcTemplate, SELECT_SITE_PRIZE_LIST_BY_KENO_2DRAW, new Object[]{gameId, terminalIds, beginKenoDrawId, endKenoDrawId}, StatPrizeAnn.class);

    }

}
