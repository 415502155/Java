package com.bestinfo.dao.impl.terminal;

import com.bestinfo.bean.business.TmnInfo;
import com.bestinfo.bean.terminal.SyncTmnRank;
import com.bestinfo.bean.terminal.TerminalRank;
import com.bestinfo.dao.impl.BaseDaoImpl;
import com.bestinfo.dao.terminal.ITmnStatusDao;
import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.sql.Types;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.CallableStatementCallback;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

/**
 * 投注机信息
 */
@Repository
public class TmnStatusDaoImpl extends BaseDaoImpl implements ITmnStatusDao {
    //查询明天计划要修改投注机星级的列表List<SyncTmnRank> strList
    private static final String QUERY_SYNC_TMN_RANK_DETAIL = "select a.execute_status, a.plan_id, b.terminal_id, b.STATION_RANK from T_SYNC_TMN_RANK_PLAN a,T_SYNC_TMN_RANK_DETAIL b\n" +
                                "where a.PLAN_ID = b.PLAN_ID\n" +
                                "and a.EXECUTE_TIME  > to_date(?,'yyyy-mm-dd') \n" +
                                "and a.EXECUTE_TIME <= to_date(?,'yyyy-mm-dd') and a.EXECUTE_STATUS = ?";
    //根据计划表中的终端编号查询出终端表的详细信息List<TmnInfo> tiList
    private static final String QUERY_TMNINFO_AND_SYNC_TMN_RANK = "select n.* from (\n" +
                                "select a.execute_status, b.terminal_id, b.STATION_RANK from meta.T_SYNC_TMN_RANK_PLAN a,meta.T_SYNC_TMN_RANK_DETAIL b\n" +
                                "where a.PLAN_ID = b.PLAN_ID\n" +
                                "and a.EXECUTE_TIME  > to_date(?,'yyyy-mm-dd') \n" +
                                "and a.EXECUTE_TIME <= to_date(?,'yyyy-mm-dd') and a.EXECUTE_STATUS = 0\n" +
                                ") m,\n" +
                                "(select * from term.t_tmn_info) n\n" +
                                "where m.terminal_id = n.terminal_id";
    //根据terminal_id修改terminal_status
    private static final String UPDATE_TMN_INFO_STATUS = "update t_tmn_info set terminal_status = ? where terminal_id = ?";
    //根据plan_id修改execute_status
    private static final String UPDATE_SYNC_TMN_RANK_DETAIL = "update T_SYNC_TMN_RANK_PLAN set EXECUTE_STATUS = ? where plan_id = ?";
    /******************************************************************************************job2************************************************************************************/
    //查询当天计划要修改投注机星级的列表
    private static final String QUERY_SYNC_TMN_RANK_DETAIL2 = "select a.execute_status, a.plan_id, b.terminal_id, b.STATION_RANK from T_SYNC_TMN_RANK_PLAN a,T_SYNC_TMN_RANK_DETAIL b\n" +
                                "where a.PLAN_ID = b.PLAN_ID\n" +
                                "and a.EXECUTE_TIME  >= to_date(?,'yyyy-mm-dd') \n" +
                                "and a.EXECUTE_TIME  < to_date(?,'yyyy-mm-dd') and a.EXECUTE_STATUS = ?";
    //根据计划表中的终端编号查询出终端表的详细信息List<TmnInfo> tiList
    private static final String QUERY_TMNINFO_AND_SYNC_TMN_RANK2 = "select n.* from (\n" +
                                "select a.execute_status, b.terminal_id, b.STATION_RANK from meta.T_SYNC_TMN_RANK_PLAN a,meta.T_SYNC_TMN_RANK_DETAIL b\n" +
                                "where a.PLAN_ID = b.PLAN_ID\n" +
                                "and a.EXECUTE_TIME  >= to_date(?,'yyyy-mm-dd') \n" +
                                "and a.EXECUTE_TIME < to_date(?,'yyyy-mm-dd') and a.EXECUTE_STATUS = 1\n" +
                                ") m,\n" +
                                "(select * from term.t_tmn_info) n\n" +
                                "where m.terminal_id = n.terminal_id";
    //根据terminal_id修改station_rank和terminal_status   update t_tmn_info set terminal_status = 1, station_rank = 1 where terminal_id = 44100295
    private static final String UPDATE_TMN_INFO_STATION_RANK = "update t_tmn_info set terminal_status = 0, station_rank = ? where terminal_id = ?";
    @Override
    public int batchUpdateTmnExecuteStatus(JdbcTemplate jdbcTemplate, final List<SyncTmnRank> strList) {
        try {
            jdbcTemplate.batchUpdate(UPDATE_SYNC_TMN_RANK_DETAIL, new BatchPreparedStatementSetter() {
                @Override
                public void setValues(PreparedStatement ps, int i) throws SQLException {
                    SyncTmnRank str = strList.get(i);
                    ps.setInt(1, str.getExecute_status());
                    ps.setInt(2, str.getPlan_id());
                }

                @Override
                public int getBatchSize() {
                    return strList.size();
                }
            });
            return 0;
        } catch (Exception e) {
            logger.error("batch update tmn execute status error.", e);
            return -1;
        }
    }
    @Override
    public int batchUpdateTmnStatus(JdbcTemplate jdbcTemplate, final List<TmnInfo> tiList) {
        try {
            jdbcTemplate.batchUpdate(UPDATE_TMN_INFO_STATUS, new BatchPreparedStatementSetter() {
                @Override
                public void setValues(PreparedStatement ps, int i) throws SQLException {
                    TmnInfo tmnInfo = tiList.get(i);
                    ps.setInt(1, tmnInfo.getTerminal_status());
                    ps.setInt(2, tmnInfo.getTerminal_id());
                }

                @Override
                public int getBatchSize() {
                    return tiList.size();
                }
            });
            return 0;
        } catch (Exception e) {
            logger.error("batch update tmn status error.", e);
            return -1;
        }
    }
    @Override
    public List<SyncTmnRank> getSyncTmnRanks(JdbcTemplate jdbcTemplate, String beginTime, String endTime, int executeStatus) {
        return this.queryForList(jdbcTemplate, QUERY_SYNC_TMN_RANK_DETAIL, new Object[]{beginTime,endTime,executeStatus}, SyncTmnRank.class);
    }

    @Override
    public List<TmnInfo> getTmnInfos(JdbcTemplate jdbcTemplate, String beginTime, String endTime) {
            return this.queryForList(jdbcTemplate, QUERY_TMNINFO_AND_SYNC_TMN_RANK, new Object[]{beginTime,endTime}, TmnInfo.class);               
    }

    @Override
    public List<SyncTmnRank> getCurDaySyncTmnRanks(JdbcTemplate jdbcTemplate, String beginTime, String endTime, int executeStatus) {
        return this.queryForList(jdbcTemplate, QUERY_SYNC_TMN_RANK_DETAIL2, new Object[]{beginTime,endTime,executeStatus}, SyncTmnRank.class);
    }

    @Override
    public List<TmnInfo> getCurDayTmnInfos(JdbcTemplate jdbcTemplate, String beginTime, String endTime) {
        return this.queryForList(jdbcTemplate, QUERY_TMNINFO_AND_SYNC_TMN_RANK2, new Object[]{beginTime, endTime}, TmnInfo.class);
    }

    @Override
    public int batchUpdateTmnStationRank(JdbcTemplate jdbcTemplate,final List<SyncTmnRank> strList) {
        try {
            jdbcTemplate.batchUpdate(UPDATE_TMN_INFO_STATION_RANK, new BatchPreparedStatementSetter() {
                @Override
                public void setValues(PreparedStatement ps, int i) throws SQLException {
                    SyncTmnRank str = strList.get(i);
                    ps.setInt(1, str.getStation_rank());
                    ps.setInt(2, str.getTerminal_id());
                }

                @Override
                public int getBatchSize() {
                    return strList.size();
                }
            });
            return 0;
        } catch (Exception e) {
            logger.error("batch update tmn status error.", e);
            return -1;
        }
    }

    @Override
    public int batchInsertTmnStationRank(JdbcTemplate jdbcTemplate, final TerminalRank tr, final int t_len) {
        try {
            String sql = "{call p_sync_tmn_rank(?,?,?,?,?,?,?)}";
            Integer errorcode = (Integer) jdbcTemplate.execute(sql, new CallableStatementCallback() {
                @Override
                public Object doInCallableStatement(CallableStatement cs) throws SQLException, DataAccessException {
                    try {
                        SimpleDateFormat sdf =   new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                        cs.setString(1, tr.getTerminal_id());
                        cs.setInt(2, t_len);
                        cs.setInt(3, tr.getRank());
                        Timestamp sqlsyncDate = new Timestamp(sdf.parse(tr.getSync_time()).getTime());
                        cs.setTimestamp(4, sqlsyncDate);
                        Timestamp sqlexecuteDate = new Timestamp(sdf.parse(tr.getExecute_time()).getTime());
                        cs.setTimestamp(5, sqlexecuteDate);
                        cs.registerOutParameter(6, Types.INTEGER);
                        cs.registerOutParameter(7, Types.VARCHAR);
                        cs.execute();
                        int errorcode = cs.getInt(6);
                        if (errorcode != 0) {
                            logger.error("terminal change rank into db error:" + errorcode + "\t" + cs.getString(7));
                        }
                        return Integer.valueOf(errorcode);
                    } catch (ParseException ex) {
                        logger.error("terminal change rank into db parameters exception:",ex);
                        return -3;
                    }
                }
            });
            return errorcode;
        } catch (DataAccessException e) {
            logger.error("terminal change rank into db exception:", e);
            return -2;
        }
    }
}
