package com.bestinfo.dao.impl.stat;

import com.bestinfo.bean.game.GameDrawInfo;
import com.bestinfo.bean.stat.StatForgetPrize;
import com.bestinfo.dao.impl.BaseDaoImpl;
import com.bestinfo.dao.stat.IStatForgetPrizeDao;
import java.util.List;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

/**
 * 结算统计表-兑奖期结算统计(T_stat_forget_prize)
 */
@Repository
public class StatForgetPrizeDaoImpl extends BaseDaoImpl implements IStatForgetPrizeDao {

    private static final String SUM_FORGET_BETWEEN_DRAWS = "select sum(forget_money) as forget_money"
            + "  from t_stat_forget_prize"
            + " where game_id = ?"
            + "   and draw_id between ? and ?";

    private static final String GET_CLASS_DETAIL_BETWEEN_DRAWS = "select class_id,"
            + "       sum(forget_stakes) forget_stakes,"
            + "       sum(forget_money) forget_money"
            + "  from t_stat_forget_prize"
            + " where game_id = ?"
            + "   and draw_id between ? and ?"
            + " group by class_id"
            + " order by class_id";

    private static final String SUM_FORGET_BY_DRAW = "select sum(forget_money) forget_money"
            + "  from t_stat_forget_prize"
            + " where game_id = ?"
            + "   and draw_id = ?";

    private static final String GET_CLASS_DETAIL_BY_DRAW = "select class_id,"
            + "       stake_prize,"
            + "       sum(forget_stakes) forget_stakes,"
            + "       sum(forget_money) forget_money"
            + "  from t_stat_forget_prize"
            + " where game_id = ?"
            + "   and draw_id = ?"
            + " group by class_id, stake_prize"
            + " order by class_id";

    private static final String GET_DRAW_LIST_BY_STATDRAW = "select distinct f.draw_id, d.draw_name"
            + "  from t_stat_forget_prize f, t_game_draw_info d"
            + " where f.game_id = ?"
            + "   and f.stat_draw_id = ?"
            + "   and f.game_id = d.game_id"
            + "   and f.draw_id = d.draw_id"
            + " order by draw_id";

    private static final String SUM_CLASS_FORGET_BETWEEN_DRAWS = "select class_id,"
            + "       sum(forget_stakes) forget_stakes,"
            + "       sum(forget_money) forget_money"
            + "  from t_stat_forget_prize"
            + " where game_id = ?"
            + "   and class_id = ?"
            + "   and draw_id between ? and ?"
            + " group by class_id"
            + " order by class_id";

    private static final String GET_CLASS_DETAIL_BY_DRAW_CLASS = "select class_id,"
            + "       stake_prize,"
            + "       sum(forget_stakes) forget_stakes,"
            + "       sum(forget_money) forget_money"
            + "  from t_stat_forget_prize"
            + " where game_id = ?"
            + "   and draw_id = ?"
            + "   and class_id = ?"
            + " group by class_id, stake_prize"
            + " order by class_id";

    /**
     * 得到期范围内弃奖总金额
     *
     * @param jdebTemplate
     * @param game_id
     * @param beginDrawId
     * @param endDrawId
     * @return
     */
    @Override
    public StatForgetPrize sumForgetBetweenDraws(JdbcTemplate jdebTemplate, int game_id, int beginDrawId, int endDrawId) {
        return this.queryForObject(jdebTemplate,
                SUM_FORGET_BETWEEN_DRAWS,
                new Object[]{game_id, beginDrawId, endDrawId},
                StatForgetPrize.class);
    }

    /**
     * 得到期范围内弃奖奖级明细
     *
     * @param jdebTemplate
     * @param game_id
     * @param beginDrawId
     * @param endDrawId
     * @return
     */
    @Override
    public List<StatForgetPrize> getClassDetailBetweenDraws(JdbcTemplate jdebTemplate, int game_id, int beginDrawId, int endDrawId) {
        return this.queryForList(jdebTemplate,
                GET_CLASS_DETAIL_BETWEEN_DRAWS,
                new Object[]{game_id, beginDrawId, endDrawId},
                StatForgetPrize.class);
    }

    /**
     * 得到一期弃奖总金额
     *
     * @param jdebTemplate
     * @param game_id
     * @param drawId
     * @return
     */
    @Override
    public StatForgetPrize sumForgetByDraw(JdbcTemplate jdebTemplate, int game_id, int drawId) {
        return this.queryForObject(jdebTemplate,
                SUM_FORGET_BY_DRAW,
                new Object[]{game_id, drawId},
                StatForgetPrize.class);
    }

    /**
     * 得到一期弃奖奖级明细
     *
     * @param jdebTemplate
     * @param game_id
     * @param drawId
     * @return
     */
    @Override
    public List<StatForgetPrize> getClassDetailByDraw(JdbcTemplate jdebTemplate, int game_id, int drawId) {
        return this.queryForList(jdebTemplate,
                GET_CLASS_DETAIL_BY_DRAW,
                new Object[]{game_id, drawId},
                StatForgetPrize.class);
    }

    /**
     * 查找在某期进行弃奖操作的所有弃奖期
     *
     * @param jdebTemplate
     * @param game_id
     * @param statDrawId
     * @return
     */
    @Override
    public List<GameDrawInfo> getDrawListByStatDraw(JdbcTemplate jdebTemplate, int game_id, int statDrawId) {
        return this.queryForList(jdebTemplate,
                GET_DRAW_LIST_BY_STATDRAW,
                new Object[]{game_id, statDrawId},
                GameDrawInfo.class);
    }

    /**
     * 得到期范围内某个奖级弃奖总金额和总注数
     *
     * @param jdebTemplate
     * @param gameId
     * @param classId
     * @param beginDrawId
     * @param endDrawId
     * @return
     */
    @Override
    public StatForgetPrize sumClassForgetBetweenDraws(JdbcTemplate jdebTemplate, int gameId, int classId, int beginDrawId, int endDrawId) {
        return this.queryForObject(jdebTemplate,
                SUM_CLASS_FORGET_BETWEEN_DRAWS,
                new Object[]{gameId, classId, beginDrawId, endDrawId},
                StatForgetPrize.class);
    }

    /**
     * 得到一期某个奖级弃奖奖级明细
     *
     * @param jdebTemplate
     * @param game_id
     * @param drawId
     * @param classId
     * @return
     */
    @Override
    public StatForgetPrize getClassDetailByDrawClass(JdbcTemplate jdebTemplate, int game_id, int drawId, int classId) {
        return this.queryForObject(jdebTemplate,
                GET_CLASS_DETAIL_BY_DRAW_CLASS,
                new Object[]{game_id, drawId, classId},
                StatForgetPrize.class);
    }
}
