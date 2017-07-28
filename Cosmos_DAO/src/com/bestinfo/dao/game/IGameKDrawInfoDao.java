package com.bestinfo.dao.game;

import com.bestinfo.bean.game.GameKDrawInfo;
import com.bestinfo.dao.page.Pagination;
import java.util.Date;
import java.util.List;
import java.util.Map;
import org.springframework.jdbc.core.JdbcTemplate;

/**
 * 快开期表
 *
 * @author yangyuefu
 */
public interface IGameKDrawInfoDao {

    /**
     * 根据主键获取快开期记录
     *
     * @param jdbcTemplate
     * @param gameId
     * @param drawId
     * @param kenoDrawId
     * @return
     */
    public GameKDrawInfo getKDrawByPrimary(JdbcTemplate jdbcTemplate, int gameId, int drawId, int kenoDrawId);

    /**
     * 根据游戏id与期id查询快开期信息
     *
     * @param jdbcTemplate
     * @param gameId
     * @param drawId
     * @return
     */
    public List<GameKDrawInfo> getKDrawList(JdbcTemplate jdbcTemplate, int gameId, int drawId);

    /**
     * 根据游戏id与小期名查询快开期信息
     *
     * @param jdbcTemplate
     * @param gameId
     * @param kdrawName
     * @return
     */
    public GameKDrawInfo getGameKDrawInfoByGameIdKDrawName(JdbcTemplate jdbcTemplate, int gameId, String kdrawName);

    /**
     * 查询所有开奖未结束的快开期列表
     *
     * @param jdbcTemplate
     * @param gameId
     * @param drawId
     * @param status
     * @return
     */
    public List<GameKDrawInfo> getUnFinishedKDrawList(JdbcTemplate jdbcTemplate, int gameId, int drawId, int status);

    /**
     * 查询还在销售的快开期列表
     *
     * @param jdbcTemplate
     * @param gameId
     * @param drawId
     * @param status
     * @return
     */
    public List<GameKDrawInfo> getSalingKDrawList(JdbcTemplate jdbcTemplate, int gameId, int drawId, int status);

    /**
     * 查询所有已生成开奖号码的快开期信息
     *
     * @param jdbcTemplate
     * @param gameId
     * @param drawId
     * @param status
     * @return
     */
    public List<GameKDrawInfo> getHasMakeLuckyNumKDrawList(JdbcTemplate jdbcTemplate, int gameId, int drawId, int status);

    /**
     * 根据游戏id获取最大快开期号
     *
     * @param jdbcTemplate
     * @param gameId
     * @return
     */
    public int getMaxKDrawIdByGameId(JdbcTemplate jdbcTemplate, int gameId);

    /**
     * 根据游戏id获取最大快开期记录
     *
     * @param jdbcTemplate
     * @param gameId
     * @return
     */
    public GameKDrawInfo getMaxKDrawByGameId(JdbcTemplate jdbcTemplate, int gameId);

    /**
     * 新增快开期次
     *
     * @param jdbcTemplate
     * @param kDrawInfo
     * @return 成功-影响记录数 失败-(-1)
     */
    public int insertGameKDrawInfo(JdbcTemplate jdbcTemplate, GameKDrawInfo kDrawInfo);

    /**
     * 批量新增快开期次
     *
     * @param jdbcTemplate
     * @param kDrawInfoList
     * @return 成功-0 失败-(-1)
     */
    public int batchInsertKDrawInfo(JdbcTemplate jdbcTemplate, List<GameKDrawInfo> kDrawInfoList);

    /**
     * 修改快开期次
     *
     * @param jdbcTemplate
     * @param kDrawInfo
     * @return 成功-影响记录数 失败-(-1)
     */
    public int updateGameKDrawInfo(JdbcTemplate jdbcTemplate, GameKDrawInfo kDrawInfo);

    /**
     * 更新快开期次状态
     *
     * @param jdbcTemplate
     * @param game_id
     * @param draw_id
     * @param keno_draw_id
     * @param status
     * @return
     */
    public int updateGameKDrawStatus(JdbcTemplate jdbcTemplate, int game_id, int draw_id, int keno_draw_id, int status);

    /**
     * 快开期信息分页列表
     *
     * @param jdbcTemplate
     * @param params
     * @return
     */
    public Pagination<GameKDrawInfo> getGameKDrawInfoPageList(JdbcTemplate jdbcTemplate, Map<String, Object> params);

    /**
     * 根据游戏Id和drawId,快开期状态查询出符合条件的快开期信息（目前不考虑多期情况）
     *
     * @param jdbcTemplate
     * @param gameId
     * @param drawId
     * @param status
     * @return 快开期信息集合
     */
    public List<GameKDrawInfo> getGameKDrawInfoByConditions(JdbcTemplate jdbcTemplate, int gameId, int drawId, int status);

    /**
     * 同步基础库数据到终端库
     *
     * @param jdbcTemplate
     * @return
     */
    public int syncMeta2Term(JdbcTemplate jdbcTemplate);

    /**
     * 根据游戏编号、总期号、期状态查询出当前正在销售的期
     *
     * @param jdbcTemplate
     * @param gameId
     * @param drawId
     * @param kdrawProStatus
     * @param curDate
     * @return
     */
    public GameKDrawInfo getCurSalingKdrawInfo(JdbcTemplate jdbcTemplate, int gameId, int drawId, int kdrawProStatus, Date curDate);
    
    /**
     * 根据game_id和draw_id删除快开期信息
     *
     * @param jdbcTemplate
     * @param gameId
     * @param drawId
     * @return
     */
    public int deleteKDrawByGameIdAndDrawId(JdbcTemplate jdbcTemplate, int gameId, int drawId);

}
