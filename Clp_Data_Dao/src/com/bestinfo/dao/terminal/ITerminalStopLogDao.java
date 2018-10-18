package com.bestinfo.dao.terminal;

import com.bestinfo.bean.terminal.TerminalStopLog;
import java.util.Date;
import java.util.List;
import org.springframework.jdbc.core.JdbcTemplate;

/**
 * 封机历史日志表
 *
 * @author hhhh
 */
public interface ITerminalStopLogDao {

    /**
     * 新增封机历史日志信息
     *
     * @param jdbcTemplate
     * @param terminalStopLog
     * @return
     */
    public int addTerminalStopLog(JdbcTemplate jdbcTemplate, TerminalStopLog terminalStopLog);

    /**
     * 根据终端编号、结束时间修改封机历史日志信息
     * @param jdbcTemplate
     * @param terminal_id
     * @param stop_time
     * @param date
     * @param stopStatus
     * @param unlock_user_id
     * @return 
     */
    public int updateTerminalStopLog(JdbcTemplate jdbcTemplate, int terminal_id, String stop_time, Date date, int stopStatus, int unlock_user_id);

    /**
     * 根据终端编号获取封机历史日志信息
     *
     * @param jdbcTemplate
     * @param terminal_id
     * @return
     */
    public TerminalStopLog selectMaxStopTimeByTmnId(JdbcTemplate jdbcTemplate, int terminal_id);

    /**
     * 根据主键（终端编号+封机时间）获取封机历史日志信息
     *
     * @param jdbcTemplate
     * @param terminal_id
     * @param stop_time
     * @return
     */
    public TerminalStopLog selectLogByTmnIdAndStopTime(JdbcTemplate jdbcTemplate, int terminal_id, String stop_time);

    /**
     * 根据终端编号获取该终端所有的封机历史日志信息
     *
     * @param jdbcTemplate
     * @param terminal_id
     * @return
     */
    public List<TerminalStopLog> selectLogListByTmnId(JdbcTemplate jdbcTemplate, int terminal_id);

    /**
     * 获取封机历史信息数据列表
     *
     * @param jdbcTemplate
     * @param auto 自动解封标识
     * @param stop_status  封机状态
     * @param time 时间
     * @return
     */
    public List<TerminalStopLog> getTerminalStopLogList(JdbcTemplate jdbcTemplate, int auto, int stop_status,String time);
}
