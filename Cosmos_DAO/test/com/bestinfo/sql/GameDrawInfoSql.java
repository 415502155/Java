package com.bestinfo.sql;

//package com.bestinfo.dao.sqlmap;
//
///**
// * 游戏大期
// *
// * @author yangyuefu
// */
//public class GameDrawInfoSql {
//
//    /**
//     * 根据游戏id和期号获取期次
//     */
//    public static String GET_GAMEDRAWINFO_BY_GAME_ID_AND_DRAW_ID = "SELECT GAME_ID,DRAW_ID,DRAW_NAME,DRAW_TYPE,"
//            + " SALES_BEGIN,SALES_END,CASH_BEGIN,CASH_END,PROCESS_STATUS,KENO_DRAW_NUM,"
//            + " BEGIN_KENO_DRAW_ID,END_KENO_DRAW_ID "
//            + " FROM t_game_draw_info WHERE game_id = ? AND draw_id = ?";
//
//    /**
//     * 根据游戏id和期名获取期次
//     */
//    public static String GET_DRAW_BY_GAME_DRAWNAME = "SELECT *"
//            + " FROM t_game_draw_info WHERE game_id = ? AND draw_name = ?";
//
//    /**
//     * 根据游戏id获取新期列表
//     */
//    public static String GET_DRAW_BY_GAMEID_DRAWSTATUS = "SELECT GAME_ID,DRAW_ID,DRAW_NAME,DRAW_TYPE,SALES_BEGIN,SALES_END,"
//            + " CASH_BEGIN,CASH_END,PROCESS_STATUS,KENO_DRAW_NUM,BEGIN_KENO_DRAW_ID,END_KENO_DRAW_ID "
//            + " FROM t_game_draw_info WHERE game_id = ? AND PROCESS_STATUS=? ORDER BY draw_id ASC";
//
//    /**
//     * 根据游戏id获取最大期号
//     */
//    public static String GET_MAX_DRAWID_BY_GAME_ID = "SELECT MAX(draw_id) FROM t_game_draw_info WHERE game_id = ?";
//
//    /**
//     * 根据游戏id获取最大期
//     */
//    public static String GET_MAX_DRAW_BY_GAME_ID = "SELECT d.*, ROWNUM rn from "
//            + " (SELECT * FROM t_game_draw_info WHERE game_id = ? ORDER BY draw_id desc) d "
//            + " where ROWNUM=1";
//
//    /**
//     * 新增期次
//     */
//    public static String INSERT_GAMEDRAWINFO = "INSERT INTO t_game_draw_info VALUES("
//            + "?,?,?,?,?,?,?,?,?,?,?,?"
//            + ")";
//
//    /**
//     * 根据游戏id和期号更新期信息
//     */
//    public static String UPDATE_GAMEDRAWINFO = "UPDATE t_game_draw_info SET "
//            + "draw_name=?,draw_type=?,sales_begin=?,sales_end=?,cash_begin=?,cash_end=?,"
//            + "process_status=?,keno_draw_num=?,begin_keno_draw_id=?,end_keno_draw_id=? "
//            + "WHERE game_id=? AND draw_id=?";
//
//    /**
//     * 根据游戏id和期号修改期状态
//     */
//    public static String UPDATE_PROCESSSTATUS = "UPDATE t_game_draw_info SET "
//            + "process_status=? "
//            + "WHERE game_id=? AND draw_id=?";
//
//    /**
//     *
//     * 根据游戏id获取期信息
//     */
//    public static String SELECT_GAMEDRAWINFO_BYGAMEID = "SELECT * FROM T_GAME_DRAW_INFO WHERE GAME_ID = ?";
//
//    /**
//     * 根据 game_id draw_id 和draw_type 查询游戏期信息
//     */
//    public static String SELECT_GAMEDRAWINFO_BYIDS = "SELECT * FROM T_GAME_DRAW_INFO WHERE GAME_ID = ? DRAW_ID=? AND DRAW_TYPE = ?";
//
//    /**
//     * 期信息下载协议中，根据游戏Id获取符合条件的期信息（所有可销售的期：目前不考虑多期情况，且只考虑当前期，不考虑预售期）
//     */
////    public static String SELECT_GAMEDRAWINFO_BY_CONDITIONS = "SELECT * FROM T_GAME_DRAW_INFO WHERE PROCESS_STATUS = ?";
//    /**
//     * 查询出所有处于当前期的游戏列表sql
//     */
//    public static String SELECT_GAME_LIST_IN_CURRENT_DRAW = "SELECT gameinfo.game_id,gameinfo.game_name "
//            + " FROM T_GAME_INFO gameinfo,T_GAME_DRAW_INFO drawinfo "
//            + " WHERE gameinfo.game_id = drawinfo.game_id "
//            + " AND drawinfo.PROCESS_STATUS = ?";
//
//    /**
//     * 根据游戏编号获取当前期的期时间信息sql
//     */
//    public static String SELECT_TIME_IN_CURRENT_DRAW_BY_GAMEID = "SELECT draw_id,draw_name,sales_begin,sales_end "
//            + " FROM T_GAME_DRAW_INFO "
//            + " WHERE GAME_ID = ? AND PROCESS_STATUS = ?";
//
//    /**
//     * 更新某一个游戏当前期的期结束时间sql
//     */
//    public static String UPDATE_DRAW_END_TIME = "update T_GAME_DRAW_INFO set sales_end = ? WHERE GAME_ID = ? AND PROCESS_STATUS = ?";
//
//    public static String SELECT_GAME_ID_DRAW_ID_BY_PROCESS_STATUS = "SELECT * FROM T_GAME_DRAW_INFO WHERE PROCESS_STATUS > ?";
//
//    public static String SELECT_GAME_DRAW_INFO_BY_PROCESSSTATUS = " SELECT * FROM T_GAME_DRAW_INFO WHERE PROCESS_STATUS in (?,?,?)";
//
//    public static String SELECT_GAME_DRAW_INFO_BY_PROCESSSTATUS2 = " SELECT game_id,draw_id,draw_name,PROCESS_STATUS,sales_begin,"
//            + " sales_end,cash_begin,cash_end "
//            + " FROM T_GAME_DRAW_INFO "
//            + " WHERE game_id = ? and PROCESS_STATUS in (?,?)";
//
//    /**
//     * 获取两个状态之间的期列表
//     */
//    public static String GET_DRAWLIST_BETWEEN_2_STATUS = "SELECT GAME_ID,DRAW_ID,DRAW_NAME,DRAW_TYPE,"
//            + " SALES_BEGIN,SALES_END,CASH_BEGIN,CASH_END,PROCESS_STATUS,KENO_DRAW_NUM,"
//            + " BEGIN_KENO_DRAW_ID,END_KENO_DRAW_ID "
//            + " FROM T_GAME_DRAW_INFO WHERE game_id = ? AND process_status >= ? AND process_status <= ?"
//            + " ORDER BY draw_id ASC";
//
//    /**
//     * 同步期
//     */
//    public static String SYN_GAMEDRAWINFO = "select * from T_GAME_DRAW_INFO WHERE process_status >= ? AND process_status <= ?";
//
//    /**
//     * 基础库同步终端库
//     */
//    public static String SYNC_META_TO_TERM = "dbms_mview.refresh('term.T_GAME_DRAW_INFO')";
//}
