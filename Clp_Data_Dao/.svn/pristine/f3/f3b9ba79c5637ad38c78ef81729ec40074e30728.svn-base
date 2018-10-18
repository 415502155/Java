package com.bestinfo.dao.game;

import com.bestinfo.bean.game.GameDrawInfo;
import com.bestinfo.bean.game.GameInfo;
import com.bestinfo.dao.IBaseDao;
import com.bestinfo.dao.page.Pagination;
import java.util.Date;
import java.util.List;
import java.util.Map;
import org.springframework.jdbc.core.JdbcTemplate;

/**
 * 游戏大期表
 *
 * @author yangyuefu
 */
public interface IGameDrawInfoDao extends IBaseDao {

    /**
     * 根据游戏id和期名获取期号
     *
     * @param jdbcTemplate
     * @param gameId
     * @param drawName
     * @return
     */
    public GameDrawInfo getDrawByGameIdAndDrawName(JdbcTemplate jdbcTemplate, int gameId, String drawName);

    /**
     * 根据游戏id和期号获取期信息
     *
     * @param jdbcTemplate
     * @param gameId
     * @param drawId
     * @return
     */
    public GameDrawInfo getDrawByGameIdAndDrawId(JdbcTemplate jdbcTemplate, int gameId, int drawId);

    /**
     * 根据游戏id获取最大期号
     *
     * @param jdbcTemplate
     * @param gameId
     * @return
     */
    public int getMaxDrawIdByGameId(JdbcTemplate jdbcTemplate, int gameId);

    /**
     * 根据游戏id获取最大期记录
     *
     * @param jdbcTemplate
     * @param gameId
     * @return
     */
    public GameDrawInfo getMaxDrawByGameId(JdbcTemplate jdbcTemplate, int gameId);

    /**
     * 根据游戏id获取新期列表
     *
     * @param jdbcTemplate
     * @param gameId
     * @param drawStatus
     * @return
     */
    public List<GameDrawInfo> getDrawByGameIdAndDrawStatus(JdbcTemplate jdbcTemplate, int gameId, int drawStatus);

    /**
     * 获取两个状态之间的期列表
     *
     * @param jdbcTemplate
     * @param gameId
     * @param statusBegin 当前期状态
     * @param statusEnd 数据校验状态
     * @return
     */
    public List<GameDrawInfo> getDrawListBetween2Status(JdbcTemplate jdbcTemplate, int gameId, int statusBegin, int statusEnd);

    /**
     * 新增期信息
     *
     * @param jdbcTemplate
     * @param gameDrawInfo
     * @return 成功-影响记录数 失败-(-1)
     */
    public int insertGameDrawInfo(JdbcTemplate jdbcTemplate, GameDrawInfo gameDrawInfo);

    /**
     * 修改期信息
     *
     * @param jdbcTemplate
     * @param gameDrawInfo
     * @return 成功-影响记录数 失败-(-1)
     */
    public int updateGameDrawInfo(JdbcTemplate jdbcTemplate, GameDrawInfo gameDrawInfo);

    /**
     * 获取期信息分页列表
     *
     * @param jdbcTemplate
     * @param params
     * @return
     */
    public Pagination<GameDrawInfo> getGameDrawInfoPageList(JdbcTemplate jdbcTemplate, Map<String, Object> params);

    /**
     * 修改期状态
     *
     * @param jdbcTemplate
     * @param gameid
     * @param drawid
     * @param procesStatus
     * @return 1-成功 -1（失败）
     */
    public int updateProcessStatus(JdbcTemplate jdbcTemplate, int gameid, int drawid, int procesStatus);

    /**
     * 根据游戏id获取期信息
     *
     * @param gameId
     * @param jdbcTemplate
     * @return
     */
    public List<GameDrawInfo> getGameDrawInfoByGameId(JdbcTemplate jdbcTemplate, int gameId);

    /**
     * 开新期
     *
     * @param jdbcTemplate
     * @param gameid
     * @param drawid
     * @return
     */
    public int newDraw(JdbcTemplate jdbcTemplate, int gameid, int drawid);

    public GameDrawInfo getGameDrawInfoByIds(JdbcTemplate jdbcTemplate, int gameId, int drawId, int drawType);

    /**
     * 从基础库中查出所有处于当前期的游戏列表
     *
     * @param jdbcTemplate
     * @param processStatus
     * @return
     */
    public List<GameInfo> selectGameListInCurDraw(JdbcTemplate jdbcTemplate, int processStatus);

    /**
     * 根据游戏编号获取当前期的期时间信息
     *
     * @param jdbcTemplate
     * @param gameId
     * @param processStatus
     * @return
     */
    public GameDrawInfo getTimeByGameId(JdbcTemplate jdbcTemplate, int gameId, int processStatus);

    /**
     * 更新某一个游戏当前期的期结束时间
     *
     * @param jdbcTemplate
     * @param gameId
     * @param processStatus
     * @param end_time
     * @return
     */
    public int updateDrawTime(JdbcTemplate jdbcTemplate, int gameId, int processStatus, Date end_time);

    /**
     * 获取大于processStatus的期信息
     *
     * @param jdbcTemplate
     * @param processStatus
     * @return
     */
    public List<GameDrawInfo> getGameDrawInfoByProcessStatus(JdbcTemplate jdbcTemplate, int processStatus);

    /**
     * 获取 processStatus 为期数据准备 预售期和当前期的期信息
     *
     * @param jdbcTemplate
     * @param firstProcess
     * @param preSale
     * @param saling
     * @return
     */
    public List<GameDrawInfo> getGameDrawInfoByProcessStatus2(JdbcTemplate jdbcTemplate, int firstProcess, int preSale, int saling);

    /**
     * 获取某游戏中 processStatus 为 预售期和当前期的所有期信息
     *
     * @param jdbcTemplate
     * @param gameId
     * @param preSale
     * @param saling
     * @return
     */
    public List<GameDrawInfo> getGameDrawInfoByProcessStatus(JdbcTemplate jdbcTemplate, int gameId, int preSale, int saling);

    /**
     * 同步游戏期信息
     *
     * @param jdbcTemplate
     * @param firstProcess
     * @param status
     * @return
     */
    public List<GameDrawInfo> synGameinfo(JdbcTemplate jdbcTemplate, int firstProcess, int status);

    /**
     * 同步基础库数据到终端库
     *
     * @param jdbcTemplate
     * @return
     */
    public int syncMeta2Term(JdbcTemplate jdbcTemplate);

    /**
     * 同步基础库的期次信息到gamb库
     *
     * @param jdbcTemplate
     * @return
     */
    public int syncMeta2Gamb(JdbcTemplate jdbcTemplate);

    /**
     *
     * @param jdbcTemplate
     * @param gameDrawInfo
     * @return
     */
    public int mergeGamedrawinfoByGameidDrawid(JdbcTemplate jdbcTemplate, GameDrawInfo gameDrawInfo);

    /**
     * 获取游戏集合的当前和预售期的所有期信息集合
     *
     * @param jdbcTemplate
     * @param gameIds
     * @param PRESALE
     * @param SALING
     * @return
     */
    public List<GameDrawInfo> selectGameDrawInfoListByGameIds(JdbcTemplate jdbcTemplate, List<Integer> gameIds, int PRESALE, int SALING);

    /**
     * 获取某游戏从当前期到之前的20条记录
     *
     * @param jdbcTemplate
     * @param gameId
     * @param processStatus
     * @return
     */
    public List<GameDrawInfo> selectGameDrawInfoList(JdbcTemplate jdbcTemplate, int gameId, int processStatus);

    /**
     * 根据游戏id和期号删除期信息
     *
     * @param jdbcTemplate
     * @param gameId
     * @param drawId
     * @return
     */
    public int deleteDrawByGameIdAndDrawId(JdbcTemplate jdbcTemplate, int gameId, int drawId);

    /**
     * 获取某游戏销售截止时间为今天的这一期期数据
     *
     * @param jdbcTemplate
     * @param gameId
     * @return
     */
    public List<GameDrawInfo> getDrawInfoBySalesEndToday(JdbcTemplate jdbcTemplate, int gameId);

    /**
     * 批量更新期状态
     *
     * @param jdbcTemplate
     * @param gameDrawInfoList
     * @param procesStatus
     * @return
     */
    public int updateBatchProcessStatus(JdbcTemplate jdbcTemplate, final List<GameDrawInfo> gameDrawInfoList, final int procesStatus);
}
