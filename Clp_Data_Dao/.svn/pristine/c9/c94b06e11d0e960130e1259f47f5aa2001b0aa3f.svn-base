package com.bestinfo.dao.stat;

import com.bestinfo.bean.business.DealerUser;
import com.bestinfo.bean.realTimeStatistics.CurrentTmnDrawStat;
import com.bestinfo.bean.stat.StatTmnDraw;
import java.util.List;
import org.springframework.jdbc.core.JdbcTemplate;

/**
 * 实时统计--投注机期统计表T_current_tmn_draw_stat
 */
public interface ICurrentTmnDrawStatDao {

    /**
     * 根据游戏和期次进行统计
     *
     * @param jdbcTemplate
     * @param gameid
     * @param drawid
     * @return
     */
    public CurrentTmnDrawStat getSumStatByGameAndDraw(JdbcTemplate jdbcTemplate, int gameid, int drawid);

    /**
     * 根据终端、游戏、期次进行统计
     *
     * @param jdbcTemplate
     * @param gameid
     * @param drawid
     * @return
     */
    public List<CurrentTmnDrawStat> getSumStatByTerminalAndGameAndDraw(JdbcTemplate jdbcTemplate, int gameid, int drawid);

    /**
     * 开新期时，初始化终端库t_tmn_serial_no表
     *
     * @param jdbcTemplate
     * @param gameid
     * @param drawid
     * @return
     */
    public int initTmnSerialNo(JdbcTemplate jdbcTemplate, int gameid, int drawid);

    /**
     * 开新期时，初始化 实时统计-投注机期统计表(T_current_tmn_draw_stat)
     *
     * @param jdbcTemplate
     * @param gameid
     * @param drawid
     * @param ldealuser 运营商管理员用户列表，每个终端有5个操作用户
     * @return
     */
    public int initCurrentTmnDarwStat(JdbcTemplate jdbcTemplate, final int gameid, final int drawid, final List<DealerUser> ldealuser);

    /**
     * 根据终端编号、游戏编号、期号查询所有操作员的统计信息
     *
     * @param jdbcTemplate
     * @param terminalId
     * @param gameId
     * @param drawId
     * @return 操作员统计集合
     */
    public List<CurrentTmnDrawStat> selectCurrentTmnDrawStatList(JdbcTemplate jdbcTemplate, int terminalId, int gameId, int drawId);

    //yangrong
    /**
     * 根据游戏编号、开始时间、结束时间和终端编号获取操作员的统计信息
     *
     * @param jdbcTemplate
     * @param gameid
     * @param beginDraw
     * @param end_Draw
     * @param terminalId
     * @return
     */
    public List<CurrentTmnDrawStat> getCurrentTmnDrawStatList(JdbcTemplate jdbcTemplate, int gameid, int beginDraw, int end_Draw, int terminalId);

    /**
     * 根据游戏编号、开始时间和终端编号获取操作员的统计信息
     *
     * @param jdbcTemplate
     * @param gameid
     * @param beginDraw
     * @param terminalId
     * @return
     */
    public List<CurrentTmnDrawStat> getCurrentTmnDrawStatListNoEndDraw(JdbcTemplate jdbcTemplate, int gameid, int beginDraw, int terminalId);

    /**
     * 根据game_id和draw_id删除终端流水号
     *
     * @param jdbcTemplate
     * @param gameId
     * @param drawId
     * @return
     */
    public int deleteTmnSerialNoByGameIdAndDrawId(JdbcTemplate jdbcTemplate, int gameId, int drawId);

    /**
     * 根据game_id和draw_id删除终端期统计
     *
     * @param jdbcTemplate
     * @param gameId
     * @param drawId
     * @return
     */
    public int deleteTmnDrawStatByGameIdAndDrawId(JdbcTemplate jdbcTemplate, int gameId, int drawId);

    /**
     * 开新期时，merge初始化终端库t_tmn_serial_no表
     *
     * @param jdbcTemplate
     * @param gameId
     * @param drawId
     * @return
     */
    public int mergeTmnSerialNo(JdbcTemplate jdbcTemplate, int gameId, int drawId);

    /**
     * 开新期时，merge初始化实时统计-投注机期统计表(T_current_tmn_draw_stat)
     *
     * @param jdbcTemplate
     * @param gameId
     * @param drawId
     * @param dealerUserList 运营商管理员用户列表，每个终端有5个操作用户
     * @return
     */
    public int mergeCurrentTmnDarwStat(JdbcTemplate jdbcTemplate, final int gameId, final int drawId,
            final List<DealerUser> dealerUserList);

    //added by yfyang 20160826
    /**
     * 从实时统计表中获取终端期统计数据
     *
     * @param jdbcTemplate
     * @param game_id
     * @param draw_id
     * @return
     */
    public List<StatTmnDraw> sumFromCurrentTmnDraw(JdbcTemplate jdbcTemplate, int game_id, int draw_id);

}
