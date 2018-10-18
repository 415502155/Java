//package com.bestinfo.dao.sqlmap;
//
///**
// * 彩票-投注数据明细(T_ticket_bet)
// *
// * @author
// */
//public class TicketBetSql {
//
//    /**
//     * 根据票面密码 获取投注票信息详情
//     */
//    public static String GET_TICKET_BY_CIPHER = "SELECT * FROM t_ticket_bet WHERE cipher = ?";
//
//    /**
//     * 获取投注数据明细
//     */
//    public static String GET_TICKET_BY_GAME_DRAW_SERIAL_TERMINAL = "SELECT operator_id,game_id,play_id,draw_id,keno_draw_id,serial_no,"
//            + " cipher,bet_time,bet_method,bet_mode,bet_stakes,bet_multiple,bet_money,bet_section,bet_num,bet_line "
//            + " FROM t_ticket_bet WHERE game_id = ? AND draw_id = ? AND serial_no = ? AND terminal_id = ?";
//
//    /**
//     * 根据game_id、draw_id、keno_draw_id统计票数、金额
//     */
//    public static String GET_SUM_BY_GAME_DRAW = "SELECT count(*) ticket_count,nvl(sum(bet_money),0) bet_money "
//            + " FROM t_ticket_bet WHERE game_id=? AND draw_id=? AND keno_draw_id=?";
//    
//    /**
//     *根据票面密码 获取投注票信息详情
//     */
////    public static String GET_TicketBet_ByCipher= "SELECT operator_id,game_id,play_id,draw_id,keno_draw_id,serial_no,"
////            + "cipher,bet_time,bet_method,bet_mode,bet_multiple,bet_money,bet_section,bet_num,bet_line "
////            + "FROM T_ticket_bet where cipher = ?";
//}
