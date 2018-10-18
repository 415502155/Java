//package com.bestinfo.dao.sqlmap;
//
///**
// * 实时统计-投注机期统计表(T_current_tmn_draw_stat)
// *
// * @author chenliping
// */
//public class CurrentTmnDrawStatSql {
//
//    /**
//     * 查询
//     */
//    public static String SUM_BY_GAME_DRAW = "SELECT GAME_ID,DRAW_ID,"
//            + " sum(SALE_MONEY) SALE_MONEY,sum(SALE_TICKET_NUM) SALE_TICKET_NUM,sum(SALE_STAKE_NUM) SALE_STAKE_NUM,"
//            + " sum(UNDO_MONEY) UNDO_MONEY,sum(UNDO_TICKET_NUM) UNDO_TICKET_NUM,sum(UNDO_STAKE_NUM) UNDO_STAKE_NUM,"
//            + " sum(CASH_MONEY) CASH_MONEY,sum(CASH_TICKET_NUM) CASH_TICKET_NUM,sum(CASH_STAKE_NUM) CASH_STAKE_NUM,"
//            + " sum(AGENT_FEE_DEDUCT) AGENT_FEE_DEDUCT,sum(CASH_FEE) CASH_FEE"
//            + " from T_CURRENT_TMN_DRAW_STAT where game_id=? and draw_id=? GROUP BY game_id,draw_id";
//    /**
//     * 查询
//     */
//    public static String SUM_BY_TERM_GAME_DRAW = "SELECT TERMINAL_ID,GAME_ID,DRAW_ID,"
//            + " sum(SALE_MONEY) SALE_MONEY,sum(SALE_TICKET_NUM) SALE_TICKET_NUM,sum(SALE_STAKE_NUM) SALE_STAKE_NUM,"
//            + " sum(UNDO_MONEY) UNDO_MONEY,sum(UNDO_TICKET_NUM) UNDO_TICKET_NUM,sum(UNDO_STAKE_NUM) UNDO_STAKE_NUM,"
//            + " sum(CASH_MONEY) CASH_MONEY,sum(CASH_TICKET_NUM) CASH_TICKET_NUM,sum(CASH_STAKE_NUM) CASH_STAKE_NUM,"
//            + " sum(AGENT_FEE_DEDUCT) AGENT_FEE_DEDUCT,sum(CASH_FEE) CASH_FEE"
//            + " from T_CURRENT_TMN_DRAW_STAT where game_id=? and draw_id=? GROUP BY terminal_id,game_id,draw_id";
//    /**
//     * 查询
//     */
//    public static String SELECTTMNDRAW_BYOPERATOR = "SELECT operator_id,sale_money,undo_money,cash_money,agent_fee_deduct,cash_fee "
//            + " from T_CURRENT_TMN_DRAW_STAT  "
//            + " WHERE  terminal_id = ? and game_id=? and draw_id=?";
////    public static String SELECTTMNDRAW_BYOPERATOR = "SELECT a.sale_money,a.undo_money,a.cash_money,a.agent_fee_deduct,a.cash_fee,b.user_name "
////            + " from T_CURRENT_TMN_DRAW_STAT a,T_DEALER_USER b "
////            + " WHERE a.terminal_id = b.terminal_id and a.operator_id = b.operator_id "
////            + " and a.terminal_id = ? and a.game_id=? and a.draw_id=?";
//    /**
//     * 新增投注机期信息
//     */
//    public static String INIT_CURRENTDRAWSTAT = "INSERT INTO T_CURRENT_TMN_DRAW_STAT(TERMINAL_ID,"
//            + " GAME_ID,DRAW_ID,OPERATOR_ID,SALE_MONEY,"
//            + " SALE_TICKET_NUM,SALE_STAKE_NUM,UNDO_MONEY,UNDO_TICKET_NUM,UNDO_STAKE_NUM,"
//            + " CASH_MONEY,CASH_TICKET_NUM,CASH_STAKE_NUM,"
//            + " AGENT_FEE_DEDUCT,CASH_FEE) "
//            + " VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
//
//    public static String SELECT_CURRENTDRAWSTAT_BYDRAWS = "SELECT operator_id,sum(sale_money) as sale_money , "
//            + " sum(undo_money) as undo_money , "
//            + " sum( cash_money) as cash_money "
//            + " from T_CURRENT_TMN_DRAW_STAT  " //YangRong加 9/12
//            + " where   "
//            + " game_id=? "
//            + " and  draw_id between ? and ? "
//            + " and  terminal_id=? GROUP BY  operator_id ";
//
//    public static String SELECT_CURRENTDRAWSTAT_BYDRAWS_NoEndDraw = "SELECT operator_id,sum(sale_money) as sale_money , "
//            + " sum(undo_money) as undo_money , "
//            + " sum( cash_money) as cash_money "
//            + " from T_CURRENT_TMN_DRAW_STAT  " //YangRong加 9/12
//            + " where   "
//            + " game_id=? "
//            + " and  draw_id=? "
//            + " and  terminal_id=?  GROUP BY  operator_id ";
//}
