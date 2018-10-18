package com.bestinfo.dao.impl.game;

import com.bestinfo.bean.game.GameDrawInfo;
import com.bestinfo.bean.game.GameInfo;
import com.bestinfo.dao.game.IGameDrawInfoDao;
import com.bestinfo.dao.impl.BaseDaoImpl;
import com.bestinfo.dao.page.Pagination;
import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Types;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.commons.lang.StringUtils;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.CallableStatementCallback;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

/**
 * 期次表
 *
 * @author yangyuefu
 */
@Repository
public class GameDrawInfoDaoImpl extends BaseDaoImpl implements IGameDrawInfoDao {

    private static final int ORACLE_MAX_IN_NUM = 999;

    /**
     * 根据游戏id和期号获取期次
     */
    private static final String GET_GAMEDRAWINFO_BY_GAME_ID_AND_DRAW_ID = "select * from t_game_draw_info where game_id = ? and draw_id = ?";

    /**
     * 根据游戏id和期号删除期次
     */
    private static final String DELETE_DRAWINFO_BY_GAME_ID_AND_DRAW_ID = "delete from t_game_draw_info WHERE game_id = ? AND draw_id = ?";

    /**
     * 根据游戏id和期名获取期次
     */
    private static final String GET_DRAW_BY_GAME_DRAWNAME = "select * from t_game_draw_info where game_id = ? and draw_name = ?";

    /**
     * 根据游戏id获取新期列表
     */
    private static final String GET_DRAW_BY_GAMEID_DRAWSTATUS = "select * from t_game_draw_info where game_id = ? and process_status=? order by draw_id asc";

    /**
     * 根据游戏id获取最大期号
     */
    private static final String GET_MAX_DRAWID_BY_GAME_ID = "select max(draw_id) from t_game_draw_info where game_id = ?";

    /**
     * 根据游戏id获取最大期
     */
    private static final String GET_MAX_DRAW_BY_GAME_ID = "select d.*, rownum rn from  (select * from t_game_draw_info where game_id = ? order by draw_id desc) d  where rownum=1";

    /**
     * 新增期次
     */
    private static final String INSERT_GAMEDRAWINFO = "insert into t_game_draw_info(game_id,draw_id,draw_name,draw_type,sales_begin,sales_end,cash_begin,cash_end,process_status,keno_draw_num,begin_keno_draw_id,end_keno_draw_id,overdue_no_detail) values(?,?,?,?,?,?,?,?,?,?,?,?,?)";

    /**
     * 根据游戏id和期号更新期信息
     */
    private static final String UPDATE_GAMEDRAWINFO = "update t_game_draw_info set draw_name=?,draw_type=?,sales_begin=?,sales_end=?,cash_begin=?,cash_end=?,process_status=?,keno_draw_num=?,begin_keno_draw_id=?,end_keno_draw_id=?,overdue_no_detail=? where game_id=? and draw_id=?";

    /**
     * 根据游戏id和期号修改期状态
     */
    private static final String UPDATE_PROCESSSTATUS = "update t_game_draw_info set process_status=? where game_id=? and draw_id=?";

    /**
     *
     * 根据游戏id获取期信息
     */
    private static final String SELECT_GAMEDRAWINFO_BYGAMEID = "select * from t_game_draw_info where game_id = ?";

    /**
     * 根据 game_id draw_id 和draw_type 查询游戏期信息
     */
    private static final String SELECT_GAMEDRAWINFO_BYIDS = "select * from t_game_draw_info where game_id = ? and draw_id=? and draw_type = ?";

    /**
     * 期信息下载协议中，根据游戏Id获取符合条件的期信息（所有可销售的期：目前不考虑多期情况，且只考虑当前期，不考虑预售期）
     */
//    private String SELECT_GAMEDRAWINFO_BY_CONDITIONS = "select * from t_game_draw_info where process_status = ?";
    /**
     * 查询出所有处于当前期的游戏列表sql
     */
    private static final String SELECT_GAME_LIST_IN_CURRENT_DRAW = "select gameinfo.game_id,gameinfo.game_name  from t_game_info gameinfo,t_game_draw_info drawinfo  where gameinfo.game_id = drawinfo.game_id  and drawinfo.process_status = ?";

    /**
     * 根据游戏编号获取当前期的期时间信息sql
     */
    private static final String SELECT_TIME_IN_CURRENT_DRAW_BY_GAMEID = "select draw_id,draw_name,sales_begin,sales_end  from t_game_draw_info  where game_id = ? and process_status = ?";

    /**
     * 更新某一个游戏当前期的期结束时间sql
     */
    private static final String UPDATE_DRAW_END_TIME = "update t_game_draw_info set sales_end = ? where game_id = ? and process_status = ?";

    private static final String SELECT_GAME_ID_DRAW_ID_BY_PROCESS_STATUS = "select * from t_game_draw_info where process_status > ?";

    private static final String SELECT_GAME_DRAW_INFO_BY_PROCESSSTATUS = " select * from t_game_draw_info where process_status in (?,?,?)";

    private static final String SELECT_GAME_DRAW_INFO_BY_PROCESSSTATUS2 = " select game_id,draw_id,draw_name,process_status,sales_begin, sales_end,cash_begin,cash_end  from t_game_draw_info  where game_id = ? and process_status in (?,?) order by draw_id asc";

    /**
     * 获取两个状态之间的期列表
     */
    private static final String GET_DRAWLIST_BETWEEN_2_STATUS = "select *  from t_game_draw_info where game_id = ? and process_status >= ? and process_status <= ? order by draw_id asc";

    /**
     * 同步期
     */
    private static final String SYN_GAMEDRAWINFO = "select * from t_game_draw_info where process_status >= ? and process_status <= ?";

    /**
     * 基础库同步终端库
     */
    private static final String SYNC_META_TO_TERM = "dbms_mview.refresh('term.t_game_draw_info')";

    /**
     *
     */
    private static final String MERGE_GAMEDRAWINFO_BY_GAMEID_DRAWID
            = " merge into t_game_draw_info gdi  using (select ? game_id, ? draw_id from dual) src  on (gdi.game_id = src.game_id and gdi.draw_id = src.draw_id )  when matched then update set   gdi.draw_name = ?, gdi.draw_type = ?, gdi.sales_begin = ?,  gdi.sales_end = ?, gdi.cash_begin = ?, gdi.cash_end = ?,  gdi.process_status = ?, gdi.keno_draw_num = ?, gdi.begin_keno_draw_id = ?,  gdi.end_keno_draw_id = ?,gdi.overdue_no_detail=?  when not matched then insert  (game_id, draw_id, draw_name, draw_type, sales_begin,  sales_end, cash_begin, cash_end, process_status,keno_draw_num,begin_keno_draw_id,end_keno_draw_id,overdue_no_detail)  values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?,?) ";

    private static final String SELECT_GAMEDRAW_LIST_20 = "select * from (select * from T_game_draw_info where game_id = ? and process_status >= ? order by draw_id desc) where rownum <=20";

    private static final String SELECT_GAMEDRAW_SALESEND_TODAY = "select * from t_game_draw_info where game_id = ? and to_char(sales_end,'yyyy-MM-dd') = to_char(sysdate, 'yyyy-MM-dd')";

    /**
     * 获取开奖时间在某天的两个状态之间的期列表，降序
     */
    private static final String GET_DAY_DRAWLIST_BETWEEN_2_STATUS_DESC = "select *"
            + "  from t_game_draw_info t"
            + " where game_id = ?"
            + "   and process_status >= ?"
            + "   and process_status <= ?"
            + "   and sales_end between"
            + "       to_date(?, 'yyyy/mm/dd hh24:mi:ss') and"
            + "       to_date(?, 'yyyy/mm/dd hh24:mi:ss')"
            + " order by draw_id desc";

    /**
     * 根据游戏id和期号获取期信息
     *
     * @param jdbcTemplate
     * @param gameId
     * @param drawId
     * @return
     */
    @Override
    public GameDrawInfo getDrawByGameIdAndDrawId(JdbcTemplate jdbcTemplate, int gameId, int drawId) {
        return queryForObject(
                jdbcTemplate,
                GET_GAMEDRAWINFO_BY_GAME_ID_AND_DRAW_ID,
                new Object[]{gameId, drawId},
                GameDrawInfo.class);
    }

    /**
     * 根据游戏id和期名获取期号
     *
     * @param jdbcTemplate
     * @param gameId
     * @param drawName
     * @return
     */
    @Override
    public GameDrawInfo getDrawByGameIdAndDrawName(JdbcTemplate jdbcTemplate, int gameId, String drawName) {
        return queryForObject(
                jdbcTemplate,
                GET_DRAW_BY_GAME_DRAWNAME,
                new Object[]{gameId, drawName},
                GameDrawInfo.class);
    }

    /**
     * 根据游戏id获取最大期号
     *
     * @param jdbcTemplate
     * @param gameId
     * @return
     */
    @Override
    public int getMaxDrawIdByGameId(JdbcTemplate jdbcTemplate, int gameId) {
        return queryForInteger(
                jdbcTemplate,
                GET_MAX_DRAWID_BY_GAME_ID,
                new Object[]{gameId});
    }

    /**
     * 根据游戏id获取最大期记录
     *
     * @param jdbcTemplate
     * @param gameId
     * @return
     */
    @Override
    public GameDrawInfo getMaxDrawByGameId(JdbcTemplate jdbcTemplate, int gameId) {
        return queryForObject(
                jdbcTemplate,
                GET_MAX_DRAW_BY_GAME_ID,
                new Object[]{gameId},
                GameDrawInfo.class);
    }

    /**
     * 根据游戏id查找某状态的期信息
     *
     * @param jdbcTemplate
     * @param gameId
     * @param drawStatus
     * @return
     */
    @Override
    public List<GameDrawInfo> getDrawByGameIdAndDrawStatus(JdbcTemplate jdbcTemplate, int gameId, int drawStatus) {
        return queryForList(
                jdbcTemplate,
                GET_DRAW_BY_GAMEID_DRAWSTATUS,
                new Object[]{gameId, drawStatus},
                GameDrawInfo.class);
    }

    /**
     * 新增期信息
     *
     * @param jdbcTemplate
     * @param gameDrawInfo
     * @return 成功-影响记录数 失败-(-1)
     */
    @Override
    public int insertGameDrawInfo(JdbcTemplate jdbcTemplate, GameDrawInfo gameDrawInfo) {
        int result;
        try {
            result = jdbcTemplate.update(INSERT_GAMEDRAWINFO, new Object[]{
                gameDrawInfo.getGame_id(),
                gameDrawInfo.getDraw_id(),
                gameDrawInfo.getDraw_name(),
                gameDrawInfo.getDraw_type(),
                gameDrawInfo.getSales_begin(),
                gameDrawInfo.getSales_end(),
                gameDrawInfo.getCash_begin(),
                gameDrawInfo.getCash_end(),
                gameDrawInfo.getProcess_status(),
                gameDrawInfo.getKeno_draw_num(),
                gameDrawInfo.getBegin_keno_draw_id(),
                gameDrawInfo.getEnd_keno_draw_id(),
                gameDrawInfo.getOverdue_no_detail()
            });
        } catch (DataAccessException e) {
            logger.error("", e);
            SQLException sqle = (SQLException) e.getCause();
            logger.error("Error code: " + sqle.getErrorCode());
            logger.error("SQL state: " + sqle.getSQLState());
            result = -1;
        }

        return result;
    }

    /**
     * 修改期信息
     *
     * @param jdbcTemplate
     * @param gameDrawInfo
     * @return 成功-影响记录数 失败-(-1)
     */
    @Override
    public int updateGameDrawInfo(JdbcTemplate jdbcTemplate, GameDrawInfo gameDrawInfo) {
        int result;
        try {
            result = jdbcTemplate.update(UPDATE_GAMEDRAWINFO, new Object[]{
                gameDrawInfo.getDraw_name(),
                gameDrawInfo.getDraw_type(),
                gameDrawInfo.getSales_begin(),
                gameDrawInfo.getSales_end(),
                gameDrawInfo.getCash_begin(),
                gameDrawInfo.getCash_end(),
                gameDrawInfo.getProcess_status(),
                gameDrawInfo.getKeno_draw_num(),
                gameDrawInfo.getBegin_keno_draw_id(),
                gameDrawInfo.getEnd_keno_draw_id(),
                gameDrawInfo.getOverdue_no_detail(),
                gameDrawInfo.getGame_id(),
                gameDrawInfo.getDraw_id()
            });
        } catch (DataAccessException e) {
            logger.error("", e);
            SQLException sqle = (SQLException) e.getCause();
            logger.error("Error code: " + sqle.getErrorCode());
            logger.error("SQL state: " + sqle.getSQLState());
            result = -1;
        }
        return result;
    }

    /**
     * 获取期信息分页列表
     *
     * @param jdbcTemplate
     * @param params
     * @return
     */
    @Override
    public Pagination<GameDrawInfo> getGameDrawInfoPageList(JdbcTemplate jdbcTemplate, Map<String, Object> params) {
        Map<String, Object> whereMap = getWhereStr(params);

        //sql语句
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT * FROM t_game_draw_info WHERE 1=1 ");
        sql.append(whereMap.get("whereSql"));

        StringBuilder countSql = new StringBuilder();
        countSql.append("SELECT COUNT(*) FROM t_game_draw_info WHERE 1=1 ");
        countSql.append(whereMap.get("whereSql"));

        //参数列表
        Object[] args = null;
        String paramStr = "";
        if (whereMap.get("whereParam") != null) {
            paramStr += whereMap.get("whereParam").toString();
        }
        if (!"".equals(paramStr)) {
            args = paramStr.split(",");
        }

        Pagination<GameDrawInfo> page = null;
        try {
            page = queryForPage(
                    jdbcTemplate,
                    sql.toString(),
                    countSql.toString(),
                    Integer.parseInt(params.get("pageNumber").toString()),
                    Integer.parseInt(params.get("pageSize").toString()),
                    args,
                    GameDrawInfo.class);
        } catch (NumberFormatException e) {
            logger.error("", e);
        }

        return page;
    }

    /**
     * 根据条件列表拼查询sql
     *
     * @param params
     * @return
     */
    private Map<String, Object> getWhereStr(Map<String, Object> params) {
        StringBuilder whereSql = new StringBuilder("");
        StringBuilder whereParam = new StringBuilder("");

        Object game_id = params.get("game_id");
        if (null != game_id) {
            whereSql.append(" AND game_id = ?");
            whereParam.append(game_id).append(",");
        }

        Object process_status = params.get("process_status");
        if (null != process_status) {
            whereSql.append(" AND process_status = ?");
            whereParam.append(process_status).append(",");
        }

        whereSql.append(" ORDER BY DRAW_ID");

        Map<String, Object> map = new HashMap<String, Object>();
        map.put("whereSql", whereSql.toString());
        map.put("whereParam", whereParam.toString());

        return map;
    }

    /**
     * 修改期状态
     *
     * @param jdbcTemplate
     * @param gameid
     * @param drawid
     * @param procesStatus
     * @return 1-成功 -1（失败）
     */
    @Override
    public int updateProcessStatus(JdbcTemplate jdbcTemplate, int gameid, int drawid, int procesStatus) {
        int result;
        try {
            result = jdbcTemplate.update(UPDATE_PROCESSSTATUS, new Object[]{
                procesStatus,
                gameid,
                drawid
            });
        } catch (DataAccessException e) {
            logger.error("", e);
            SQLException sqle = (SQLException) e.getCause();
            logger.error("Error code: " + sqle.getErrorCode());
            logger.error("SQL state: " + sqle.getSQLState());
            result = -1;
        }
        return result;
    }

    /**
     * 根据游戏id获取期信息
     *
     * @param jdbcTemplate
     * @param gameId
     * @return
     */
    @Override
    public List<GameDrawInfo> getGameDrawInfoByGameId(JdbcTemplate jdbcTemplate, int gameId) {
        return this.queryForList(jdbcTemplate, SELECT_GAMEDRAWINFO_BYGAMEID, new Object[]{gameId}, GameDrawInfo.class);
    }

    /**
     * 开新期
     *
     * @param jdbcTemplate
     * @param gameid
     * @param drawid
     */
    @Override
    public int newDraw(JdbcTemplate jdbcTemplate, final int gameid, final int drawid) {
        try {
            String sql = "{call newDraw(?,?,?,?)}";
            int re = (Integer) jdbcTemplate.execute(sql, new CallableStatementCallback() {
                @Override
                public Object doInCallableStatement(CallableStatement cs) throws SQLException, DataAccessException {
                    cs.setInt(1, gameid);
                    cs.setInt(2, drawid);
                    cs.registerOutParameter(3, Types.INTEGER);
                    cs.registerOutParameter(4, Types.VARCHAR);
                    cs.execute();
                    int errCode = cs.getInt(3);
                    String errMsg = cs.getString(4);
                    if (errCode != 0) {
                        logger.error("call newDraw(?,?,?,?) SP error,errCode:" + errCode + ",errMsg:" + errMsg);
                    }
                    return errCode;
                }
            });
            return re;
        } catch (DataAccessException e) {
            logger.error("", e);
            return -2;
        }
    }

    /**
     * 获取游戏期信息
     *
     * @param jdbcTemplate
     * @param gameId
     * @param drawId
     * @param drawType
     * @return
     */
    @Override
    public GameDrawInfo getGameDrawInfoByIds(JdbcTemplate jdbcTemplate, int gameId, int drawId, int drawType) {
        return this.queryForObject(jdbcTemplate, SELECT_GAMEDRAWINFO_BYIDS, new Object[]{gameId, drawId, drawType}, GameDrawInfo.class);
    }

    /**
     * 查出所有处于当前期的游戏列表
     *
     * @param jdbcTemplate
     * @param processStatus
     * @return
     */
    @Override
    public List<GameInfo> selectGameListInCurDraw(JdbcTemplate jdbcTemplate, int processStatus) {
        return this.queryForList(jdbcTemplate, SELECT_GAME_LIST_IN_CURRENT_DRAW, new Object[]{processStatus}, GameInfo.class);
    }

    /**
     * 根据游戏编号获取当前期的期时间信息
     *
     * @param jdbcTemplate
     * @param gameId
     * @param processStatus
     * @return
     */
    @Override
    public GameDrawInfo getTimeByGameId(JdbcTemplate jdbcTemplate, int gameId, int processStatus) {
        return this.queryForObject(
                jdbcTemplate,
                SELECT_TIME_IN_CURRENT_DRAW_BY_GAMEID,
                new Object[]{gameId, processStatus},
                GameDrawInfo.class);
    }

    /**
     * 更新某一个游戏当前期的期结束时间
     *
     * @param jdbcTemplate
     * @param gameId
     * @param processStatus
     * @param end_time
     * @return
     */
    @Override
    public int updateDrawTime(JdbcTemplate jdbcTemplate, int gameId, int processStatus, Date end_time) {
        int result;
        try {
            result = jdbcTemplate.update(UPDATE_DRAW_END_TIME, new Object[]{
                end_time,
                gameId,
                processStatus
            });
        } catch (DataAccessException e) {
            logger.error("update end_time error from T_game_draw_info table in DB where gameId = " + gameId + " and processStatus = " + processStatus, e);
            SQLException sqle = (SQLException) e.getCause();
            logger.error("Error code: " + sqle.getErrorCode());
            logger.error("SQL state: " + sqle.getSQLState());
            result = -1;
        }
        return result;
    }

    /**
     * 获取大于processStatus的期信息
     *
     * @param jdbcTemplate
     * @param processStatus
     * @return
     */
    @Override
    public List<GameDrawInfo> getGameDrawInfoByProcessStatus(JdbcTemplate jdbcTemplate, int processStatus) {
        return this.queryForList(jdbcTemplate,
                SELECT_GAME_ID_DRAW_ID_BY_PROCESS_STATUS,
                new Object[]{processStatus},
                GameDrawInfo.class);
    }

    /**
     * 获取processStatus 为FIRSTPROCESS 、 PRESALE 、 SALING 的期信息
     *
     * @param jdbcTemplate
     * @param FIRSTPROCESS
     * @param PRESALE
     * @param SALING
     * @return
     */
    @Override
    public List<GameDrawInfo> getGameDrawInfoByProcessStatus2(JdbcTemplate jdbcTemplate, int FIRSTPROCESS, int PRESALE, int SALING) {
        return this.queryForList(jdbcTemplate,
                SELECT_GAME_DRAW_INFO_BY_PROCESSSTATUS,
                new Object[]{FIRSTPROCESS, PRESALE, SALING},
                GameDrawInfo.class);
    }

    /**
     * 同步期信息（范围）
     *
     * @param jdbcTemplate
     * @param firstProcess
     * @param status
     * @return
     */
    @Override
    public List<GameDrawInfo> synGameinfo(JdbcTemplate jdbcTemplate, int firstProcess, int status) {
        return this.queryForList(jdbcTemplate,
                SYN_GAMEDRAWINFO,
                new Object[]{firstProcess, status},
                GameDrawInfo.class);
    }

    /**
     * 获取某游戏中 processStatus 为 预售期和当前期的所有期信息
     *
     * @param jdbcTemplate
     * @param gameId
     * @param PRESALE
     * @param SALING
     * @return
     */
    @Override
    public List<GameDrawInfo> getGameDrawInfoByProcessStatus(JdbcTemplate jdbcTemplate, int gameId, int PRESALE, int SALING) {
        return this.queryForList(jdbcTemplate,
                SELECT_GAME_DRAW_INFO_BY_PROCESSSTATUS2,
                new Object[]{gameId, PRESALE, SALING},
                GameDrawInfo.class);
    }

    /**
     * 获取两个状态之间的期列表
     *
     * @param jdbcTemplate
     * @param gameId
     * @param statusBegin
     * @param statusEnd
     * @return
     */
    @Override
    public List<GameDrawInfo> getDrawListBetween2Status(JdbcTemplate jdbcTemplate, int gameId, int statusBegin, int statusEnd) {
        return this.queryForList(jdbcTemplate,
                GET_DRAWLIST_BETWEEN_2_STATUS,
                new Object[]{gameId, statusBegin, statusEnd},
                GameDrawInfo.class);
    }

    /**
     * 同步基础库数据到终端库
     *
     * @param jdbcTemplate
     * @return
     */
    @Override
    public int syncMeta2Term(JdbcTemplate jdbcTemplate) {
        try {
            String sql = "{call dbms_mview.refresh(?)}";
            jdbcTemplate.execute(sql, new CallableStatementCallback() {
                @Override
                public Object doInCallableStatement(CallableStatement cs) throws SQLException, DataAccessException {
                    cs.setString(1, "term.T_GAME_DRAW_INFO");
                    return cs.execute();
                }
            });
            return 0;
        } catch (DataAccessException e) {
            logger.error("", e);
            return -1;
        }
    }

    /**
     * 同步基础库的期次信息到gamb库
     *
     * @param jdbcTemplate
     * @return
     */
    @Override
    public int syncMeta2Gamb(JdbcTemplate jdbcTemplate) {
        try {
            String sql = "{call dbms_mview.refresh(?)}";
            jdbcTemplate.execute(sql, new CallableStatementCallback() {
                @Override
                public Object doInCallableStatement(CallableStatement cs) throws SQLException, DataAccessException {
                    cs.setString(1, "gamb.T_GAME_DRAW_INFO");
                    return cs.execute();
                }
            });
            return 0;
        } catch (DataAccessException e) {
            logger.error("", e);
            return -1;
        }
    }

    /**
     * 期信息存在，则更新，不存在，则插入，条件为游戏编号，期编号
     *
     * @param jdbcTemplate
     * @param gdi
     * @return
     */
    @Override
    public int mergeGamedrawinfoByGameidDrawid(JdbcTemplate jdbcTemplate, GameDrawInfo gdi) {
        int result;
        try {
            result = jdbcTemplate.update(MERGE_GAMEDRAWINFO_BY_GAMEID_DRAWID, new Object[]{
                gdi.getGame_id(),
                gdi.getDraw_id(),
                gdi.getDraw_name(),
                gdi.getDraw_type(),
                gdi.getSales_begin(),
                gdi.getSales_end(),
                gdi.getCash_begin(),
                gdi.getCash_end(),
                gdi.getProcess_status(),
                gdi.getKeno_draw_num(),
                gdi.getBegin_keno_draw_id(),
                gdi.getEnd_keno_draw_id(),
                gdi.getOverdue_no_detail(),
                gdi.getGame_id(),
                gdi.getDraw_id(),
                gdi.getDraw_name(),
                gdi.getDraw_type(),
                gdi.getSales_begin(),
                gdi.getSales_end(),
                gdi.getCash_begin(),
                gdi.getCash_end(),
                gdi.getProcess_status(),
                gdi.getKeno_draw_num(),
                gdi.getBegin_keno_draw_id(),
                gdi.getEnd_keno_draw_id(),
                gdi.getOverdue_no_detail()
            });
        } catch (DataAccessException e) {
            logger.error("", e);
            SQLException sqle = (SQLException) e.getCause();
            logger.error("Error code: " + sqle.getErrorCode());
            logger.error("SQL state: " + sqle.getSQLState());
            result = -1;
        }
        return result;
    }

    /**
     * 获取游戏集合的当前和预售期的所有期信息集合
     *
     * @param jdbcTemplate
     * @param gameIds
     * @param PRESALE
     * @param SALING
     * @return
     */
    @Override
    public List<GameDrawInfo> selectGameDrawInfoListByGameIds(JdbcTemplate jdbcTemplate, List<Integer> gameIds, int PRESALE, int SALING) {
        if (gameIds == null || gameIds.isEmpty() || gameIds.size() > ORACLE_MAX_IN_NUM) {
            logger.warn("para game id list is incorrect.");
            return Collections.EMPTY_LIST;
        }

        StringBuilder sb = new StringBuilder();
        sb.append("SELECT game_id,draw_id,draw_name,PROCESS_STATUS,sales_begin, sales_end,cash_begin,cash_end FROM T_GAME_DRAW_INFO WHERE game_id in (");
        sb.append(StringUtils.join(gameIds, ","));
        sb.append(") and PROCESS_STATUS in (?,?) order by draw_id ASC");

        List<GameDrawInfo> gameDrawInfoList = this.queryForList(jdbcTemplate,
                sb.toString(),
                new Object[]{PRESALE, SALING},
                GameDrawInfo.class);
        return gameDrawInfoList;
    }

    /**
     * 获取某游戏从当前期到之前的20条记录
     *
     * @param jdbcTemplate
     * @param gameId
     * @param processStatus
     * @return
     */
    @Override
    public List<GameDrawInfo> selectGameDrawInfoList(JdbcTemplate jdbcTemplate, int gameId, int processStatus) {
        List<GameDrawInfo> gameDrawInfoList = this.queryForList(jdbcTemplate,
                SELECT_GAMEDRAW_LIST_20,
                new Object[]{gameId, processStatus},
                GameDrawInfo.class);
        return gameDrawInfoList;
    }

    /**
     * 根据游戏id和期号删除期信息
     *
     * @param jdbcTemplate
     * @param gameId
     * @param drawId
     * @return
     */
    @Override
    public int deleteDrawByGameIdAndDrawId(JdbcTemplate jdbcTemplate, int gameId, int drawId) {
        int result = 0;
        try {
            result = jdbcTemplate.update(DELETE_DRAWINFO_BY_GAME_ID_AND_DRAW_ID, new Object[]{gameId, drawId});
        } catch (DataAccessException e) {
            logger.error("", e);
            SQLException sqle = (SQLException) e.getCause();
            logger.error("Error code: " + sqle.getErrorCode());
            logger.error("SQL state: " + sqle.getSQLState());
            result = -1;
        }
        return result;
    }

    /**
     * 获取某游戏销售截止时间为今天的这一期期数据
     *
     * @param jdbcTemplate
     * @param gameId
     * @return
     */
    public List<GameDrawInfo> getDrawInfoBySalesEndToday(JdbcTemplate jdbcTemplate, int gameId) {
        List<GameDrawInfo> gameDrawInfoList = this.queryForList(jdbcTemplate,
                SELECT_GAMEDRAW_SALESEND_TODAY,
                new Object[]{gameId},
                GameDrawInfo.class);
        return gameDrawInfoList;
    }

    /**
     * 批量更新期状态
     *
     * @param jdbcTemplate
     * @param gameDrawInfoList
     * @param procesStatus
     * @return
     */
    @Override
    public int updateBatchProcessStatus(JdbcTemplate jdbcTemplate, final List<GameDrawInfo> gameDrawInfoList, final int procesStatus) {
        try {
            jdbcTemplate.batchUpdate(UPDATE_PROCESSSTATUS, new BatchPreparedStatementSetter() {
                @Override
                public void setValues(PreparedStatement ps, int i) throws SQLException {
                    GameDrawInfo gameDrawInfo = gameDrawInfoList.get(i);
                    ps.setInt(1, procesStatus);
                    ps.setInt(2, gameDrawInfo.getGame_id());
                    ps.setInt(3, gameDrawInfo.getDraw_id());
                }

                @Override
                public int getBatchSize() {
                    return gameDrawInfoList.size();
                }
            });
            return 0;
        } catch (Exception e) {
            logger.error("when batch modify city info error.", e);
            return -1;
        }
    }

    /**
     * 获取开奖日期在某天的两个状态之间的期列表，降序
     *
     * @param jdbcTemplate
     * @param gameId
     * @param statusBegin
     * @param statusEnd
     * @param day
     * @return
     */
    @Override
    public List<GameDrawInfo> getDayDrawListBetween2StatusDesc(JdbcTemplate jdbcTemplate, int gameId,
            int statusBegin, int statusEnd, String day) {
        return this.queryForList(jdbcTemplate,
                GET_DAY_DRAWLIST_BETWEEN_2_STATUS_DESC,
                new Object[]{gameId, statusBegin, statusEnd, day + " 00:00:01", day + " 23:59:59"},
                GameDrawInfo.class);
    }
}
