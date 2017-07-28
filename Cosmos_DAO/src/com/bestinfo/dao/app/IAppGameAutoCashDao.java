package com.bestinfo.dao.app;

import com.bestinfo.bean.game.GameAutoCash;
import com.bestinfo.bean.game.GameKDrawInfo;
import com.bestinfo.dao.IBaseDao;
import java.util.List;
import org.springframework.jdbc.core.JdbcTemplate;

/**
 * 自动兑奖过程表
 */
public interface IAppGameAutoCashDao extends IBaseDao {

    /**
     * merge新增兑奖期信息
     *
     * @param jdbcTemplate
     * @param autoCash
     * @return 成功-影响记录数 失败-(-1)
     */
    public int mergeGameAutoCash(JdbcTemplate jdbcTemplate, GameAutoCash autoCash);

    /**
     * 批量merge新增兑奖期信息，针对快开游戏
     *
     * @param jdbcTemplate
     * @param kdrawList
     * @return
     */
    public int batchMergeGameAutoCash(JdbcTemplate jdbcTemplate, List<GameKDrawInfo> kdrawList);

    /**
     * 更新兑奖期状态为已完成
     *
     * @param jdbcTemplate
     * @param gameId
     * @param drawId
     * @param kenoDrawId
     * @return 成功-影响记录数 失败-(-1)
     */
    public int updateAutoCashStatus(JdbcTemplate jdbcTemplate, int gameId, int drawId, int kenoDrawId);

    /**
     * 获取未完成自动兑奖的大期信息
     *
     * @param jdbcTemplate
     * @return
     */
    public List<GameAutoCash> getUnFinishedCashAuto(JdbcTemplate jdbcTemplate);

    /**
     * 获取某个游戏未完成自动兑奖的快开期信息
     *
     * @param jdbcTemplate
     * @param gameId
     * @return
     */
    public List<GameAutoCash> getGameKenoUnFinishedCashAuto(JdbcTemplate jdbcTemplate, int gameId);

    /**
     * app小奖自动兑奖
     *
     * @param jdbcTemplate
     * @param gameID
     * @param drawID
     * @param kenoID
     */
    public int appAutoCash(JdbcTemplate jdbcTemplate, int gameID, int drawID, int kenoID);

    /**
     * 获取自动兑奖的期信息息
     *
     * @param jdbcTemplate
     * @param gameId
     * @param drawId
     * @param kenoDrawId
     * @return
     */
    public GameAutoCash getCashAuto(JdbcTemplate jdbcTemplate, final int gameId, final int drawId, final int kenoDrawId);

}
