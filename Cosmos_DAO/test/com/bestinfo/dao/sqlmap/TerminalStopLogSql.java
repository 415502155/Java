//package com.bestinfo.dao.sqlmap;
//
///**
// * 封机历史日志表
// *
// * @author hhhh
// */
//public class TerminalStopLogSql {
//
//    /**
//     * 插入封机历史日志表sql
//     */
//    public static String INSERT_TERMINAL_STOP_LOG = "INSERT INTO T_TERMINAL_STOP_LOG(TERMINAL_ID,STOP_TIME,CONTINUE_TIME,STOP_REASON,REASON_TYPE,AUTO_CONTINUE,USER_ID) VALUES(?,?,?,?,?,?,?)";
//    
//     /**
//     * 更新开机时间sql
//     */
//    public static String UPDATE_continue_time = "UPDATE T_TERMINAL_STOP_LOG SET continue_time = ? WHERE TERMINAL_ID = ? AND stop_time = TO_DATE(?,'yyyy-mm-dd hh24:mi:ss')";
//    
//    /**
//     * 查询某个终端最后一次的封机时间sql
//     */
//    public static String SELECT_MAX_STOP_TIME_BY_TERMINAL_ID = "select TO_CHAR(max(STOP_TIME),'yyyy-mm-dd HH24:mi:ss') stop_time_str from T_TERMINAL_STOP_LOG where terminal_id = ?";
//    
//    /**
//     * 获取改时间段下自动解封的封机历史信息列表 
//     * add  by   lvchngrong  at  2014-11-09
//     */
//    public static String SELECT_TERMINAL_STOP_LOG_LIST_ByTimeAndAuto = "SELECT TERMINAL_ID,STOP_TIME,CONTINUE_TIME,STOP_REASON,REASON_TYPE,AUTO_CONTINUE,USER_ID FROM T_TERMINAL_STOP_LOG  where auto_continue = ? AND continue_time <= to_date(? ,'yyyy-MM-dd hh24:mi:ss') ";
//}
