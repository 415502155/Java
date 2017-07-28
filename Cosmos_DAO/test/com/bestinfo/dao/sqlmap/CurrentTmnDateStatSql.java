//package com.bestinfo.dao.sqlmap;
//
///**
// * 终端日统计查询
// *
// * @author lvchangrong
// */
//public class CurrentTmnDateStatSql {
//
//    /**
//     * 终端游戏日统计数据列表
//     */
//    public static String SELECT_CurrentTmnDateStat_LIST_byDate = "SELECT game_id, sum(sale_money) as sale_money,sum(undo_money) as undo_money,sum(cash_money) as cash_money,sum(agent_fee_deduct) as agent_fee_deduct,sum(cash_fee) as cash_fee FROM T_current_tmn_date_stat where terminal_id = ?  and  OPERATOR_DATE between  TO_DATE(?, 'yyyy-MM-dd') and TO_DATE(?, 'yyyy-MM-dd') group by game_id ORDER BY GAME_ID ASC";
//    /**
//     * 查询
//     */
//    public static String SELECT_CurrentTmnDateStat_LIST_byNoEndDate = "SELECT game_id, sum(sale_money) as sale_money,sum(undo_money) as undo_money,sum(cash_money) as cash_money,sum(agent_fee_deduct) as agent_fee_deduct,sum(cash_fee) as cash_fee FROM T_current_tmn_date_stat where terminal_id = ? and OPERATOR_DATE = TO_DATE(?, 'yyyy-mm-dd') group by game_id ";
//    /**
//     * 终端操作员日统计数据列表
//     */
//    public static String SELECT_OperatorCurrentTmnDateStat_LIST_byDate = " select operator_id, sum(sale_money) as sale_money,sum(undo_money) as undo_money,sum(cash_money) as cash_money,sum(agent_fee_deduct) as agent_fee_deduct,sum(cash_fee) as cash_fee FROM T_current_tmn_date_stat where terminal_id = ?  and  OPERATOR_DATE between  TO_DATE(?, 'yyyy-MM-dd') and TO_DATE(?, 'yyyy-MM-dd') group by operator_id ORDER BY operator_id ASC";
//    /**
//     * 根据日期查询
//     */
//    public static String SELECT_OperatorCurrentTmnDateStat_LIST_byNoEndDate = "select operator_id, sum(sale_money) as sale_money,sum(undo_money) as undo_money,sum(cash_money) as cash_money,sum(agent_fee_deduct) as agent_fee_deduct,sum(cash_fee) as cash_fee FROM T_current_tmn_date_stat where terminal_id = ? and OPERATOR_DATE = TO_DATE(?, 'yyyy-mm-dd') group by operator_id";
//}
