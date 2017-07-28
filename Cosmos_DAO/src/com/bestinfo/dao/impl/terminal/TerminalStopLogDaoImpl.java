package com.bestinfo.dao.impl.terminal;

import com.bestinfo.bean.terminal.TerminalStopLog;
import com.bestinfo.dao.impl.BaseDaoImpl;
import com.bestinfo.dao.terminal.ITerminalStopLogDao;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

/**
 * 封机历史日志表
 *
 * @author hhhh
 */
@Repository
public class TerminalStopLogDaoImpl extends BaseDaoImpl implements ITerminalStopLogDao {

//    private final Logger logger = Logger.getLogger(TerminalStopLogDaoImpl.class);
    /**
     * 插入封机历史日志表sql
     */
    private static final String INSERT_TERMINAL_STOP_LOG = "insert into t_terminal_stop_log(terminal_id,stop_time,continue_time,stop_reason,reason_type,auto_continue,user_id,stop_status) values(?,?,?,?,?,?,?,?)";

    /**
     * 更新封机历史记录表sql
     */
    private static final String UPDATE_TERMINAL_STOP_LOG = "update t_terminal_stop_log set continue_time = ?,stop_status = ?,unlock_user_id=? where terminal_id = ? and stop_time = to_date(?,'yyyy-mm-dd hh24:mi:ss')";

    /**
     * 查询某个终端最后一次的封机时间sql
     */
    private static final String SELECT_MAX_STOP_TIME_BY_TERMINAL_ID = "select to_char(max(stop_time),'yyyy-mm-dd hh24:mi:ss') stop_time_str from t_terminal_stop_log where terminal_id = ?";

    /**
     * 获取改时间段下自动解封的封机历史信息列表 add by lvchangrong at 2014-11-09
     */
//    private static final String SELECT_TERMINAL_STOP_LOG_LIST_ByTimeAndAuto = "select terminal_id,stop_time,continue_time,stop_reason,reason_type,auto_continue,user_id from t_terminal_stop_log  where auto_continue = ? and continue_time <= to_date(? ,'yyyy-mm-dd hh24:mi:ss') ";
//    private static final String SELECT_TERMINAL_STOP_LOG_LIST_ByTimeAndAuto = "select a.terminal_id,a.stop_time,a.continue_time,a.stop_reason,a.reason_type,a.auto_continue,a.user_id,a.stop_status  from t_terminal_stop_log a ,(select max(stop_time) stop_time,terminal_id from t_terminal_stop_log group by  terminal_id) b where a.stop_time = b.stop_time  and a.terminal_id = b.terminal_id and a.auto_continue=?  and a.stop_status =?  and a.continue_time <= to_date(? ,'yyyy-mm-dd hh24:mi:ss') ";
      private static final String SELECT_TERMINAL_STOP_LOG_LIST_ByTimeAndAutoAndStatus="select terminal_id,stop_time,continue_time,stop_reason,reason_type,auto_continue,user_id from t_terminal_stop_log  where auto_continue = ? and stop_status =? and continue_time <= to_date(? ,'yyyy-mm-dd hh24:mi:ss')";
    /**
     * 查询某个终端指定封机时间的封机历史记录
     */
    private static final String SELECT_LOG_BY_TERMINAL_ID_AND_STOP_TIME = "select terminal_id,stop_time,continue_time,stop_reason,reason_type,auto_continue,user_id,stop_status,unlock_user_id from t_terminal_stop_log where terminal_id = ? and stop_time = to_date(?, 'yyyy-mm-dd hh24:mi:ss')";

    /**
     * 查询某个终端所有的封机历史记录
     */
    private static final String SELECT_LOG_LIST_BY_TERMINAL_ID = "select terminal_id,stop_time,continue_time,stop_reason,reason_type,auto_continue,user_id,stop_status,unlock_user_id from t_terminal_stop_log where terminal_id = ?";

    /**
     * 新增分级历史信息
     *
     * @param jdbcTemplate
     * @param terminalStopLog
     * @return
     */
    @Override
    public int addTerminalStopLog(JdbcTemplate jdbcTemplate, TerminalStopLog terminalStopLog) {
        String sql = INSERT_TERMINAL_STOP_LOG;
        int result = 0;
        try {
            result = jdbcTemplate.update(sql, new Object[]{
                terminalStopLog.getTerminal_id(),
                terminalStopLog.getStop_time(),
                terminalStopLog.getContinue_time(),
                terminalStopLog.getStop_reason(),
                terminalStopLog.getReason_type(),
                terminalStopLog.getAuto_continue(),
                terminalStopLog.getUser_id(),
                terminalStopLog.getStop_status()
            });
        } catch (DataAccessException e) {
            logger.error("when insert into t_terminal_stop_log occur error", e);
            SQLException sqle = (SQLException) e.getCause();
            logger.error("Error code: " + sqle.getErrorCode());
            logger.error("SQL state: " + sqle.getSQLState());
            result = -1;
        }
        return result;

    }

    /**
     * 根据终端编号、结束时间修改封机历史日志信息
     *
     * @param jdbcTemplate
     * @param terminal_id
     * @param stop_time
     * @param date
     * @param stopStatus
     * @param unlock_user_id
     * @return
     */
    @Override
    public int updateTerminalStopLog(JdbcTemplate jdbcTemplate, int terminal_id, String stop_time, Date date, int stopStatus, int unlock_user_id) {
        String sql = UPDATE_TERMINAL_STOP_LOG;
        int result = 0;
        try {
            result = jdbcTemplate.update(sql, new Object[]{
                date,
                stopStatus,
                unlock_user_id,
                terminal_id,
                stop_time
            });
        } catch (DataAccessException e) {
            logger.error("when update t_terminal_stop_log occur error where terminal_id = " + terminal_id + " and stop_time = " + stop_time, e);
            SQLException sqle = (SQLException) e.getCause();
            logger.error("Error code: " + sqle.getErrorCode());
            logger.error("SQL state: " + sqle.getSQLState());
            result = -1;
        }
        return result;
    }

    /**
     * 查询封机历史信息
     *
     * @param jdbcTemplate
     * @param terminal_id
     * @return
     */
    @Override
    public TerminalStopLog selectMaxStopTimeByTmnId(JdbcTemplate jdbcTemplate, int terminal_id) {
        return this.queryForObject(jdbcTemplate, SELECT_MAX_STOP_TIME_BY_TERMINAL_ID, new Object[]{terminal_id}, TerminalStopLog.class);
    }

    /**
     * 根据主键（终端编号+封机时间）获取封机历史日志信息
     *
     * @param jdbcTemplate
     * @param terminal_id
     * @param stop_time
     * @return
     */
    @Override
    public TerminalStopLog selectLogByTmnIdAndStopTime(JdbcTemplate jdbcTemplate, int terminal_id, String stop_time) {
        return this.queryForObject(jdbcTemplate, SELECT_LOG_BY_TERMINAL_ID_AND_STOP_TIME, new Object[]{terminal_id, stop_time}, TerminalStopLog.class);
    }

    /**
     * 根据终端编号获取该终端所有的封机历史日志信息
     *
     * @param jdbcTemplate
     * @param terminal_id
     * @return
     */
    @Override
    public List<TerminalStopLog> selectLogListByTmnId(JdbcTemplate jdbcTemplate, int terminal_id) {
        return this.queryForList(jdbcTemplate, SELECT_LOG_LIST_BY_TERMINAL_ID, new Object[]{terminal_id}, TerminalStopLog.class);
    }

    /**
     * 获取封机历史信息数据列表
     *
     * @param jdbcTemplate
     * @param auto 自动解封标识
     * @param stop_status 封机状态
     * @param time 时间
     * @return
     */
    @Override
    public List<TerminalStopLog> getTerminalStopLogList(JdbcTemplate jdbcTemplate, int auto, int stop_status , String time) {
        return this.queryForList(jdbcTemplate, SELECT_TERMINAL_STOP_LOG_LIST_ByTimeAndAutoAndStatus, new Object[]{auto, stop_status, time}, TerminalStopLog.class);
    }
}
