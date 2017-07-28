//package com.bestinfo.dao.sqlmap;
//
///**
// * 投注终端特权
// *
// * @author chenliping
// */
//public class TmnPrivilegeSql {
//
//    /**
//     * 缓存同步使用
//     */
//    public static String SELECT_TMNPRIVILEGE_LIST = "SELECT * FROM T_TMN_PRIVILEGE";
//
//    public static String UPDATE_TMNPRIVILEGE_BY_PRIKEY = "UPDATE T_TMN_PRIVILEGE SET SALE_PERMIT = ?, CASH_PERMIT = ?, UNDO_PERMIT = ?, "
//            + " GAME_PERMIT = ?, PRESELL_PERMIT = ?, GAME_STOP = ?, AGENT_FEE_RATE = ?, MIN_BET_MONEY = ?, "
//            + " MAX_BET_MONEY = ?, MAX_SALES_MONEY = ?, CASH_FEE_RATE = ? "
//            + " WHERE TERMINAL_ID=? AND GAME_ID = ? AND CUR_DRAW_ID = ?";
//
//    public static String INSERT_TMNPRIVILEGE = "INSERT INTO T_TMN_PRIVILEGE(TERMINAL_ID, GAME_ID, CUR_DRAW_ID, SALE_PERMIT, "
//            + "CASH_PERMIT, UNDO_PERMIT, GAME_PERMIT, PRESELL_PERMIT, GAME_STOP, AGENT_FEE_RATE, "
//            + "MIN_BET_MONEY, MAX_BET_MONEY, MAX_SALES_MONEY, CASH_FEE_RATE) "
//            + " VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
//
//    public static String DELETE_TMNPRIVILEGE_BY_ID = "delete from T_TMN_PRIVILEGE WHERE TERMINAL_ID = ?";
//
//    public static String UPDATE_GAMESTOP = "UPDATE T_TMN_PRIVILEGE set GAME_STOP=? where GAME_ID=?";
//
//    /**
//     * 根据game_id获取特权列表
//     */
//    public static String SELECT_RIVILEGE_BY_GAME = "SELECT * FROM t_tmn_privilege WHERE game_id=?";
//    
//    /**
//     * 根据game_id、terminal_id获某个终端某个游戏的特权信息
//     */
//    public static String GET_RIVILEGE_BY_GAME_TERMINAL = "SELECT * FROM t_tmn_privilege WHERE game_id=? AND terminal_id=?";
//}
