package com.bestinfo.dao.stat;

import com.bestinfo.bean.game.GameDrawInfo;
import com.bestinfo.bean.stat.StatForgetPrize;
import java.util.List;
import org.springframework.jdbc.core.JdbcTemplate;

/**
 * 结算统计表-兑奖期结算统计(T_stat_forget_prize)
 */
public interface IStatForgetPrizeDao {

    /**
     *
     *
     * @param jdebTemplate
     * @param game_id
     * @param beginDrawId
     * @param endDrawId
     * @return
     */
    public StatForgetPrize sumForgetBetweenDraws(JdbcTemplate jdebTemplate, int game_id, int beginDrawId, int endDrawId);

    /**
     * 得到期范围内奖级明细
     *
     * @param jdebTemplate
     * @param game_id
     * @param beginDrawId
     * @param endDrawId
     * @return
     */
    public List<StatForgetPrize> getClassDetailBetweenDraws(JdbcTemplate jdebTemplate, int game_id, int beginDrawId, int endDrawId);

    /**
     * 得到一期弃奖总金额
     *
     * @param jdebTemplate
     * @param game_id
     * @param drawId
     * @return
     */
    public StatForgetPrize sumForgetByDraw(JdbcTemplate jdebTemplate, int game_id, int drawId);

    /**
     * 得到一期弃奖奖级明细
     *
     * @param jdebTemplate
     * @param game_id
     * @param drawId
     * @return
     */
    public List<StatForgetPrize> getClassDetailByDraw(JdbcTemplate jdebTemplate, int game_id, int drawId);

    /**
     * 查找在某期进行弃奖操作的所有弃奖期
     *
     * @param jdebTemplate
     * @param game_id
     * @param statDrawId
     * @return
     */
    public List<GameDrawInfo> getDrawListByStatDraw(JdbcTemplate jdebTemplate, int game_id, int statDrawId);

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
    public StatForgetPrize sumClassForgetBetweenDraws(JdbcTemplate jdebTemplate, int gameId, int classId, int beginDrawId, int endDrawId);

    /**
     * 得到一期某个奖级弃奖奖级明细
     *
     * @param jdebTemplate
     * @param game_id
     * @param drawId
     * @param classId
     * @return
     */
    public StatForgetPrize getClassDetailByDrawClass(JdbcTemplate jdebTemplate, int game_id, int drawId, int classId);
}
