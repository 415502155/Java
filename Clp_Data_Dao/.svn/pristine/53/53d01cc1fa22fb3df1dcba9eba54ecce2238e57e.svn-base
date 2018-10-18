package com.bestinfo.dao.impl.game;

import com.bestinfo.bean.game.GameKDrawInfo;
import com.bestinfo.dao.game.IGameKDrawInfoDao;
import com.bestinfo.dao.impl.BaseDaoImpl;
import com.bestinfo.dao.page.Pagination;
import com.bestinfo.util.TimeUtil;
import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.CallableStatementCallback;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

/**
 * 快开期次表
 *
 * @author yangyuefu
 */
@Repository
public class GameKDrawInfoDaoImpl extends BaseDaoImpl implements IGameKDrawInfoDao {

//    private final Logger logger = Logger.getLogger(GameKDrawInfoDaoImpl.class);
    /**
     * 根据主键获取快开期次
     */
    private static final String GET_GAMEKDRAWINFO_BY_PRIMARY = "select game_id,draw_id,keno_draw_id,keno_draw_name,draw_type, sales_begin,sales_end,cash_begin,cash_end,keno_process_status,kdraw_no,multi_kdraw_num from t_game_kdraw_info where game_id = ? and draw_id = ? and keno_draw_id = ?";

    /**
     * 根据主键获取快开期次
     */
    private static final String DELETE_KDRAWINFO_BY_GAME_ID_AND_DRAW_ID = "DELETE FROM t_game_kdraw_info WHERE game_id = ? AND draw_id = ?";

    /**
     * 根据游戏id获取最大快开期号
     */
    private static final String GET_MAX_KDRAWID_BY_GAME_ID = "select max(keno_draw_id) from t_game_kdraw_info where game_id = ?";

    /**
     * 根据游戏id获取最大快开期
     */
    private static final String GET_MAX_KDRAW_BY_GAME_ID = "select d.game_id,d.draw_id,d.keno_draw_id,d.keno_draw_name,d.draw_type,d.sales_begin,d.sales_end,d.cash_begin,d.cash_end,d.keno_process_status,d.kdraw_no,d.multi_kdraw_num , rownum rn from (select game_id,draw_id,keno_draw_id,keno_draw_name,draw_type, sales_begin,sales_end,cash_begin,cash_end,keno_process_status,kdraw_no,multi_kdraw_num from t_game_kdraw_info where game_id = ? order by keno_draw_id desc) d where rownum=1";

    /**
     * 新增快开期次
     */
    private static final String INSERT_GAMEKDRAWINFO = "insert into t_game_kdraw_info(game_id,draw_id,keno_draw_id,keno_draw_name,draw_type,sales_begin,sales_end,cash_begin,cash_end,keno_process_status,kdraw_no,multi_kdraw_num) values(?,?,?,?,?,?,?,?,?,?,?,?)";

    /**
     * 更新快开期次
     */
    private static final String UPDATE_GAMEKDRAWINFO = "update t_game_kdraw_info set keno_draw_name=?,draw_type=?,sales_begin=?,sales_end=?,cash_begin=?,cash_end=?,keno_process_status=?,keno_no=?,numti_kdraw_num=? where game_id=? and draw_id=? and keno_draw_id = ?";

    /**
     * 根据game_id、draw_id、keno_draw_id修改快开期状态
     */
    private static final String UPDATE_PROCESSSTATUS = "update t_game_kdraw_info set  keno_process_status=?  where game_id=? and draw_id=? and keno_draw_id=?";

    /**
     * 条件：游戏id+期id，结果：所有快开期信息
     */
    private static final String GET_GAMEKDRAWINFO = "select game_id,draw_id,keno_draw_id,keno_draw_name,draw_type, sales_begin,sales_end,cash_begin,cash_end,keno_process_status,kdraw_no,multi_kdraw_num from t_game_kdraw_info where game_id = ? and draw_id = ? order by keno_draw_id asc";

    /**
     * 查询所有正在销售的快开期信息
     */
    private static final String GET_SALING_KDRAW_LIST = "select game_id,draw_id,keno_draw_id,keno_draw_name,draw_type, sales_begin,sales_end,cash_begin,cash_end,keno_process_status,kdraw_no,multi_kdraw_num from t_game_kdraw_info where game_id = ? and draw_id = ? and keno_process_status = ?  order by keno_draw_id asc";

    /**
     * 查询所有未开奖结束的快开期信息
     */
    private static final String GET_UNFINISHED_KDRAW = "select game_id,draw_id,keno_draw_id,keno_draw_name,draw_type, sales_begin,sales_end,cash_begin,cash_end,keno_process_status,kdraw_no,multi_kdraw_num from t_game_kdraw_info where game_id = ? and draw_id = ? and keno_process_status < ?  order by keno_draw_id asc";

    /**
     * 查询所有已生成开奖号码的快开期信息
     */
    private static final String GET_HASMAKELUCKYNUM_KDRAW = "select game_id,draw_id,keno_draw_id,keno_draw_name,draw_type, sales_begin,sales_end,cash_begin,cash_end,keno_process_status,kdraw_no,multi_kdraw_num from t_game_kdraw_info where game_id = ? and draw_id = ? and keno_process_status > ? order by keno_draw_id asc";

    /**
     * 期信息下载协议中，根据游戏Id，drawId获取符合条件的keno期信息（所有可销售的期：目前不考虑多期情况，只考虑当前期）
     */
    private static final String SELECT_GAMEKDRAWINFO_BY_CONDITIONS = "select game_id,draw_id,keno_draw_id,keno_draw_name,draw_type, sales_begin,sales_end,cash_begin,cash_end,keno_process_status,kdraw_no,multi_kdraw_num from t_game_kdraw_info where game_id = ? and draw_id = ? and keno_process_status = ?";

    /**
     * 基础库同步终端库
     */
    private static final String SYNC_META_TO_TERM = "dbms_mview.refresh('term.t_game_kdraw_info')";

    /**
     * 查询当前正在销售的keno期（当前时间在开始时间和截止时间之间的）
     */
    private static final String SELECT_CUR_SALING_KDRAW_INFO = "select game_id,draw_id,keno_draw_id,keno_draw_name,draw_type, sales_begin,sales_end,cash_begin,cash_end,keno_process_status,kdraw_no,multi_kdraw_num from t_game_kdraw_info where game_id = ? and draw_id = ? and keno_process_status = ? and ? >= sales_begin and ? < sales_end ";

    /**
     * 根据游戏Id和小期名获取快开期信息
     */
    private static final String GET_KDRAW_BY_GAME_ID_KDRAW_NAME = "select game_id,draw_id,keno_draw_id,keno_draw_name,draw_type, sales_begin,sales_end,cash_begin,cash_end,keno_process_status,kdraw_no,multi_kdraw_num from t_game_kdraw_info where game_id = ? and keno_draw_name = ? ";

    /**
     * 根据主键获取快开期次
     *
     * @param jdbcTemplate
     * @param gameId
     * @param drawId
     * @return
     */
    @Override
    public GameKDrawInfo getKDrawByPrimary(JdbcTemplate jdbcTemplate, int gameId, int drawId, int kenoDrawId) {
        return queryForObject(
                jdbcTemplate,
                GET_GAMEKDRAWINFO_BY_PRIMARY,
                new Object[]{gameId, drawId, kenoDrawId},
                GameKDrawInfo.class);
    }

    /**
     * 根据gamid与期号查询快开期信息
     *
     * @param jdbcTemplate
     * @param gameId
     * @param drawId
     * @return
     */
    @Override
    public List<GameKDrawInfo> getKDrawList(JdbcTemplate jdbcTemplate, int gameId, int drawId) {
        return queryForList(jdbcTemplate, GET_GAMEKDRAWINFO, new Object[]{gameId, drawId}, GameKDrawInfo.class);
    }

    /**
     * 查询所有未开奖结束的快开期列表
     *
     * @param jdbcTemplate
     * @param gameId
     * @param drawId
     * @param status
     * @return
     */
    @Override
    public List<GameKDrawInfo> getUnFinishedKDrawList(JdbcTemplate jdbcTemplate, int gameId, int drawId, int status) {
        String sql = GET_UNFINISHED_KDRAW;
        return queryForList(jdbcTemplate, sql, new Object[]{gameId, drawId, status}, GameKDrawInfo.class);
    }

    /**
     * 查询还在销售的快开期列表
     *
     * @param jdbcTemplate
     * @param gameId
     * @param drawId
     * @param status
     * @return
     */
    @Override
    public List<GameKDrawInfo> getSalingKDrawList(JdbcTemplate jdbcTemplate, int gameId, int drawId, int status) {
        String sql = GET_SALING_KDRAW_LIST;
        return queryForList(jdbcTemplate, sql, new Object[]{gameId, drawId, status}, GameKDrawInfo.class);
    }

    /**
     * 查询所有已生成开奖号码的快开期信息
     *
     * @param jdbcTemplate
     * @param gameId
     * @param drawId
     * @param status
     * @return
     */
    @Override
    public List<GameKDrawInfo> getHasMakeLuckyNumKDrawList(JdbcTemplate jdbcTemplate, int gameId, int drawId, int status) {
        String sql = GET_HASMAKELUCKYNUM_KDRAW;
        return queryForList(jdbcTemplate, sql, new Object[]{gameId, drawId, status}, GameKDrawInfo.class);
    }

    /**
     * 根据游戏id获取最大快开期号
     *
     * @param jdbcTemplate
     * @param gameId
     * @return
     */
    @Override
    public int getMaxKDrawIdByGameId(JdbcTemplate jdbcTemplate, int gameId) {
        return queryForObject(
                jdbcTemplate,
                GET_MAX_KDRAWID_BY_GAME_ID,
                new Object[]{gameId},
                Integer.class);
    }

    /**
     * 根据游戏id获取最大快开期记录
     *
     * @param jdbcTemplate
     * @param gameId
     * @return
     */
    @Override
    public GameKDrawInfo getMaxKDrawByGameId(JdbcTemplate jdbcTemplate, int gameId) {
        return queryForObject(
                jdbcTemplate,
                GET_MAX_KDRAW_BY_GAME_ID,
                new Object[]{gameId},
                GameKDrawInfo.class);
    }

    /**
     * 新增快开期次
     *
     * @param jdbcTemplate
     * @param kDrawInfo
     * @return 成功-影响记录数 失败-(-1)
     */
    @Override
    public int insertGameKDrawInfo(JdbcTemplate jdbcTemplate, GameKDrawInfo kDrawInfo) {
        int result;
        try {
            result = jdbcTemplate.update(INSERT_GAMEKDRAWINFO, new Object[]{
                kDrawInfo.getGame_id(),
                kDrawInfo.getDraw_id(),
                kDrawInfo.getKeno_draw_id(),
                kDrawInfo.getKeno_draw_name(),
                kDrawInfo.getDraw_type(),
                kDrawInfo.getSales_begin(),
                kDrawInfo.getSales_end(),
                kDrawInfo.getCash_begin(),
                kDrawInfo.getCash_end(),
                kDrawInfo.getKeno_process_status(),
                kDrawInfo.getKdraw_no(),
                kDrawInfo.getMulti_kdraw_num()
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
     * 批量新增快开期次
     *
     * @param jdbcTemplate
     * @param kDrawInfoList
     * @return 成功-0 失败-(-1)
     */
    @Override
    public int batchInsertKDrawInfo(JdbcTemplate jdbcTemplate, final List<GameKDrawInfo> kDrawInfoList) {
        try {
            jdbcTemplate.batchUpdate(INSERT_GAMEKDRAWINFO, new BatchPreparedStatementSetter() {
                @Override
                public void setValues(PreparedStatement ps, int i) throws SQLException {
                    GameKDrawInfo kDrawInfo = kDrawInfoList.get(i);
                    int j = 1;
                    ps.setInt(j++, kDrawInfo.getGame_id());
                    ps.setInt(j++, kDrawInfo.getDraw_id());
                    ps.setInt(j++, kDrawInfo.getKeno_draw_id());
                    ps.setString(j++, kDrawInfo.getKeno_draw_name());
                    ps.setInt(j++, kDrawInfo.getDraw_type());
                    ps.setTimestamp(j++, new java.sql.Timestamp(kDrawInfo.getSales_begin().getTime()));
                    ps.setTimestamp(j++, new java.sql.Timestamp(kDrawInfo.getSales_end().getTime()));
                    ps.setTimestamp(j++, new java.sql.Timestamp(kDrawInfo.getCash_begin().getTime()));
                    ps.setTimestamp(j++, new java.sql.Timestamp(kDrawInfo.getCash_end().getTime()));
                    ps.setInt(j++, kDrawInfo.getKeno_process_status());
                    ps.setInt(j++, kDrawInfo.getKdraw_no());
                    ps.setInt(j++, kDrawInfo.getMulti_kdraw_num());
                }

                @Override
                public int getBatchSize() {
                    return kDrawInfoList.size();
                }
            });
            return 0;
        } catch (Exception e) {
            logger.error("", e);
            SQLException sqle = (SQLException) e.getCause();
            logger.error("Error code: " + sqle.getErrorCode());
            logger.error("SQL state: " + sqle.getSQLState());
            return -1;
        }
    }

    /**
     * 修改期次
     *
     * @param jdbcTemplate
     * @param kDrawInfo
     * @return 成功-影响记录数 失败-(-1)
     */
    @Override
    public int updateGameKDrawInfo(JdbcTemplate jdbcTemplate, GameKDrawInfo kDrawInfo) {
        int result;
        try {
            result = jdbcTemplate.update(UPDATE_GAMEKDRAWINFO, new Object[]{
                kDrawInfo.getKeno_draw_name(),
                kDrawInfo.getDraw_type(),
                kDrawInfo.getSales_begin(),
                kDrawInfo.getSales_end(),
                kDrawInfo.getCash_begin(),
                kDrawInfo.getCash_end(),
                kDrawInfo.getKeno_process_status(),
                kDrawInfo.getKdraw_no(),
                kDrawInfo.getMulti_kdraw_num(),
                kDrawInfo.getGame_id(),
                kDrawInfo.getDraw_id(),
                kDrawInfo.getKeno_draw_id()
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
     * 更新快开期次状态
     *
     * @param jdbcTemplate
     * @param game_id
     * @param draw_id
     * @param keno_draw_id
     * @param status
     * @return
     */
    @Override
    public int updateGameKDrawStatus(JdbcTemplate jdbcTemplate, int game_id, int draw_id, int keno_draw_id, int status) {
        int result;
        try {
            result = jdbcTemplate.update(UPDATE_PROCESSSTATUS,
                    new Object[]{status, game_id, draw_id, keno_draw_id});
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
     * 获取游戏分页列表
     *
     * @param jdbcTemplate
     * @param params
     * @return
     */
    @Override
    public Pagination<GameKDrawInfo> getGameKDrawInfoPageList(JdbcTemplate jdbcTemplate, Map<String, Object> params) {
        Map<String, Object> whereMap = getWhereStr(params);

        //sql语句
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT * FROM t_game_kdraw_info WHERE 1=1 ");
        sql.append(whereMap.get("whereSql"));

        StringBuilder countSql = new StringBuilder();
        countSql.append("SELECT COUNT(*) FROM t_game_kdraw_info WHERE 1=1 ");
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

        Pagination<GameKDrawInfo> page = null;
        try {
            page = queryForPage(
                    jdbcTemplate,
                    sql.toString(),
                    countSql.toString(),
                    Integer.parseInt(params.get("pageNumber").toString()),
                    Integer.parseInt(params.get("pageSize").toString()),
                    args,
                    GameKDrawInfo.class);
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

        Object draw_id = params.get("draw_id");
        if (null != draw_id) {
            whereSql.append(" AND draw_id = ?");
            whereParam.append(draw_id).append(",");
        }

        Map<String, Object> map = new HashMap<String, Object>();
        map.put("whereSql", whereSql.toString());
        map.put("whereParam", whereParam.toString());

        return map;
    }

    /**
     * 根据游戏Id和drawId,快开期状态查询出符合条件的快开期信息（目前不考虑多期情况）
     *
     * @param jdbcTemplate
     * @param gameId
     * @param drawId
     * @return 快开期信息集合
     */
    @Override
    public List<GameKDrawInfo> getGameKDrawInfoByConditions(JdbcTemplate jdbcTemplate, int gameId, int drawId, int status) {
        return jdbcTemplate.queryForList(SELECT_GAMEKDRAWINFO_BY_CONDITIONS,
                new Object[]{gameId, drawId, status},
                GameKDrawInfo.class);
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
                    cs.setString(1, "term.T_GAME_KDRAW_INFO");
                    return cs.execute();
                }
            });
            return 0;
        } catch (DataAccessException e) {
            logger.error("", e);
            SQLException sqle = (SQLException) e.getCause();
            logger.error("Error code: " + sqle.getErrorCode());
            logger.error("SQL state: " + sqle.getSQLState());
            return -1;
        }
    }

    /**
     * 根据游戏编号、总期号查询出当前正在销售的期
     *
     * @param jdbcTemplate
     * @param gameId
     * @param drawId
     * @param kdrawProStatus
     * @param curDate
     * @return
     */
    @Override
    public GameKDrawInfo getCurSalingKdrawInfo(JdbcTemplate jdbcTemplate, int gameId, int drawId, int kdrawProStatus, Date curDate) {
        GameKDrawInfo gkd = null;
        List<GameKDrawInfo> gkdList = jdbcTemplate.query(SELECT_CUR_SALING_KDRAW_INFO,
                new Object[]{gameId, drawId, kdrawProStatus, curDate, curDate}, new RowMapper() {
                    public Object mapRow(ResultSet rs, int num)
                    throws SQLException {
                        GameKDrawInfo gkd = new GameKDrawInfo();
                        gkd.setGame_id(rs.getInt("game_id"));
                        gkd.setDraw_id(rs.getInt("draw_id"));
                        gkd.setKeno_draw_id(rs.getInt("keno_draw_id"));
                        gkd.setKeno_draw_name(rs.getString("keno_draw_name"));
                        gkd.setDraw_type(rs.getInt("draw_type"));
                        gkd.setSales_begin(TimeUtil.timeStampToDate(rs.getTimestamp("sales_begin")));
                        gkd.setSales_end(TimeUtil.timeStampToDate(rs.getTimestamp("sales_end")));
                        gkd.setCash_begin(TimeUtil.timeStampToDate(rs.getTimestamp("cash_begin")));
                        gkd.setCash_end(TimeUtil.timeStampToDate(rs.getTimestamp("cash_end")));
                        gkd.setKeno_process_status(rs.getInt("keno_process_status"));
                        gkd.setKdraw_no(rs.getInt("kdraw_no"));
                        gkd.setMulti_kdraw_num(rs.getInt("multi_kdraw_num"));
                        return gkd;
                    }
                });

        if (gkdList != null && gkdList.size() > 0) {
            gkd = gkdList.get(0);
        }

        return gkd;
    }

    /**
     * 根据游戏id与小期名查询快开期信息
     *
     * @param jdbcTemplate
     * @param gameId
     * @param kdrawName
     * @return
     */
    @Override
    public GameKDrawInfo getGameKDrawInfoByGameIdKDrawName(JdbcTemplate jdbcTemplate, int gameId, String kdrawName) {
        return queryForObject(
                jdbcTemplate,
                GET_KDRAW_BY_GAME_ID_KDRAW_NAME,
                new Object[]{gameId, kdrawName},
                GameKDrawInfo.class);
    }

    /**
     * 根据game_id和draw_id删除快开期信息
     *
     * @param jdbcTemplate
     * @param gameId
     * @param drawId
     * @return
     */
    @Override
    public int deleteKDrawByGameIdAndDrawId(JdbcTemplate jdbcTemplate, int gameId, int drawId) {
        int result = 0;
        try {
            result = jdbcTemplate.update(DELETE_KDRAWINFO_BY_GAME_ID_AND_DRAW_ID, new Object[]{gameId, drawId});
        } catch (DataAccessException e) {
            logger.error("", e);
            SQLException sqle = (SQLException) e.getCause();
            logger.error("Error code: " + sqle.getErrorCode());
            logger.error("SQL state: " + sqle.getSQLState());
            result = -1;
        }
        return result;
    }

}
