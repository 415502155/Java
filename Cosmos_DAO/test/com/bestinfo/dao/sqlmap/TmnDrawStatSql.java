///*
// * To change this license header, choose License Headers in Project Properties.
// * To change this template file, choose Tools | Templates
// * and open the template in the editor.
// */
//
//package com.bestinfo.dao.sqlmap;
//
///**
// * 站点期统计查询
// * @author YangRong
// */
//public class TmnDrawStatSql {
//    /**
//  * 投注机期统计数据列表
//  */
//    public static String SELECT_CurrentTmnDrawStat_LIST_byDraw = "SELECT operator_id, sum(sale_money) as sale_money,sum(undo_money) as undo_money,sum(cash_money) as cash_money from T_current_tmn_draw_stat where GAME_ID=?  AND DRAW_ID BETWEEN ? AND ?  AND TERMINAL_ID = ?  group by operator_id";
//    
//    public static String SELECT_CurrentTmnDrwaStat_LIST_byNoEndDraw = "SELECT operator_id, sum(sale_money) as sale_money,sum(undo_money) as undo_money,sum(cash_money) as cash_money  FROM T_current_tmn_draw_stat where GAME_ID=? AND DRAW_ID=? AND TERMINAL_ID = ? group by operator_id";
//
//}
