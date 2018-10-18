//package com.bestinfo.dao.sqlmap;
//
///**
// * 资金账户统计
// *
// * @author shange
// */
//public class AccountSummarySql {
//
//    /**
//     * 按类型统计资金账户
//     */
//    //public static final String ACCOUNTSUMMARYQUERY_IN = "select sum(income_money) total_money,trade_type from t_account_detail where trade_time between ? and ? and trade_type not in (?) group by trade_type";
//    public static final String ACCOUNTSUMMARYQUERY = "select trade_time,income_money,out_money,trade_type from t_account_detail "
//            + " where trade_time between to_date(?,'yyyy-mm-dd hh24:mi:ss') and to_date(?,'yyyy-mm-dd hh24:mi:ss') "
//            + " and trade_type not in (?,?,?,?,?,?,?,?,?) and account_id = ?";
//
//   // public static final String ACCOUNTSUMMARYQUERY_OUT = "select sum(out_money) total_money,trade_type from t_account_detail where trade_time between ? and ? and trade_type not in (?) group by trade_type";
//}
