package com.bestinfo.sql;

//package com.bestinfo.dao.sqlmap;
//
///**
// * 快开期
// *
// * @author yangyuefu
// */
//public class GameKDrawInfoSql {
//
//    /**
//     * 根据主键获取快开期次
//     */
//    public static String GET_GAMEKDRAWINFO_BY_PRIMARY = "SELECT game_id,draw_id,keno_draw_id,keno_draw_name,draw_type,"
//            + " sales_begin,sales_end,cash_begin,cash_end,keno_process_status,kdraw_no,multi_kdraw_num"
//            + " FROM t_game_kdraw_info WHERE game_id = ? AND draw_id = ? AND keno_draw_id = ?";
//
//    /**
//     * 根据游戏id获取最大快开期号
//     */
//    public static String GET_MAX_KDRAWID_BY_GAME_ID = "SELECT MAX(keno_draw_id) FROM t_game_kdraw_info WHERE game_id = ?";
//
//    /**
//     * 根据游戏id获取最大快开期
//     */
//    public static String GET_MAX_KDRAW_BY_GAME_ID = "SELECT d.*, ROWNUM rn from (SELECT game_id,draw_id,keno_draw_id,keno_draw_name,draw_type,"
//            + " sales_begin,sales_end,cash_begin,cash_end,keno_process_status,kdraw_no,multi_kdraw_num"
//            + " FROM t_game_kdraw_info WHERE game_id = ? ORDER BY keno_draw_id desc) d where ROWNUM=1";
//
//    /**
//     * 新增快开期次
//     */
//    public static String INSERT_GAMEKDRAWINFO = "INSERT INTO t_game_kdraw_info VALUES("
//            + "?,?,?,?,?,?,?,?,?,?,?,?)";
//
//    /**
//     * 更新快开期次
//     */
//    public static String UPDATE_GAMEKDRAWINFO = "UPDATE t_game_kdraw_info SET "
//            + "keno_draw_name=?,draw_type=?,sales_begin=?,sales_end=?,cash_begin=?,cash_end=?,"
//            + "keno_process_status=?,keno_no=?,numti_kdraw_num=? "
//            + "WHERE game_id=? AND draw_id=? AND keno_draw_id = ?";
//
//    /**
//     * 根据game_id、draw_id、keno_draw_id修改快开期状态
//     */
//    public static String UPDATE_PROCESSSTATUS = "UPDATE t_game_kdraw_info SET "
//            + " keno_process_status=? "
//            + " WHERE game_id=? AND draw_id=? AND keno_draw_id=?";
//
//    /**
//     * 条件：游戏id+期id，结果：所有快开期信息
//     */
//    public static String GET_GAMEKDRAWINFO = "SELECT game_id,draw_id,keno_draw_id,keno_draw_name,draw_type,"
//            + " sales_begin,sales_end,cash_begin,cash_end,keno_process_status,kdraw_no,multi_kdraw_num"
//            + " FROM t_game_kdraw_info WHERE game_id = ? AND draw_id = ? ORDER BY keno_draw_id ASC";
//
//    /**
//     * 查询所有正在销售的快开期信息
//     */
//    public static String GET_SALING_KDRAW_LIST = "SELECT game_id,draw_id,keno_draw_id,keno_draw_name,draw_type,"
//            + " sales_begin,sales_end,cash_begin,cash_end,keno_process_status,kdraw_no,multi_kdraw_num"
//            + " FROM t_game_kdraw_info WHERE game_id = ? AND draw_id = ? AND keno_process_status = ? "
//            + " ORDER BY keno_draw_id ASC";
//    
//    /**
//     * 查询所有未开奖结束的快开期信息
//     */
//    public static String GET_UNFINISHED_KDRAW = "SELECT game_id,draw_id,keno_draw_id,keno_draw_name,draw_type,"
//            + " sales_begin,sales_end,cash_begin,cash_end,keno_process_status,kdraw_no,multi_kdraw_num"
//            + " FROM t_game_kdraw_info WHERE game_id = ? AND draw_id = ? AND keno_process_status < ? "
//            + " ORDER BY keno_draw_id ASC";
//    
//    /**
//     * 查询所有已生成开奖号码的快开期信息
//     */
//    public static String GET_HASMAKELUCKYNUM_KDRAW = "SELECT game_id,draw_id,keno_draw_id,keno_draw_name,draw_type,"
//            + " sales_begin,sales_end,cash_begin,cash_end,keno_process_status,kdraw_no,multi_kdraw_num"
//            + " FROM t_game_kdraw_info WHERE game_id = ? AND draw_id = ? AND keno_process_status > ?"
//            + " ORDER BY keno_draw_id ASC";
//
//    /**
//     * 期信息下载协议中，根据游戏Id，drawId获取符合条件的keno期信息（所有可销售的期：目前不考虑多期情况，只考虑当前期）
//     */
//    public static String SELECT_GAMEKDRAWINFO_BY_CONDITIONS = "SELECT game_id,draw_id,keno_draw_id,keno_draw_name,draw_type,"
//            + " sales_begin,sales_end,cash_begin,cash_end,keno_process_status,kdraw_no,multi_kdraw_num"
//            + " FROM T_GAME_KDRAW_INFO WHERE GAME_ID = ? AND DRAW_ID = ? AND KENO_PROCESS_STATUS = ?";
//    
//    
//    /**
//     * 基础库同步终端库
//     */
//    public static String SYNC_META_TO_TERM = "dbms_mview.refresh('term.T_GAME_KDRAW_INFO')";
//
//}
