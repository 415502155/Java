//package com.bestinfo.dao.sqlmap;
//
///**
// * 资金账户-变动明细信息
// *
// * @author hhhh
// */
//public class AccountDetailSql {
//
//    /**
//     * 插入资金帐户统一变动明细信息表sql
//     */
//    public static String INSERT_ACCOUNT_DETAIL = "INSERT INTO T_ACCOUNT_DETAIL("
//            + "ACCOUNT_SERIAL_NO, ACCOUNT_ID, OPERATOR_ID, TRADE_TIME, TRADE_TYPE, "
//            + "SOURCE_TYPE, RECHARGE_SOURCE, PRE_MONEY, INCOME_MONEY, OUT_MONEY, "
//            + "ACC_BALANCE, BANK_SERIAL_NO, SCHEME_ID, TRADE_NOTE) VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
//    
//    /**
//     * 根据账户编号，获取指定时间段内的所有日缴费明细
//     */
////    public static String GET_ACCOUNTDETAIL_BY_ID_AND_TIME = "SELECT a.TRADE_TIME,b.user_name,a.income_money FROM T_ACCOUNT_DETAIL a,T_DEALER_USER b "
////            + "WHERE a.OPERATOR_ID = b.OPERATOR_ID AND a.ACCOUNT_ID = ? "
////            + "AND a.TRADE_TIME between TO_DATE(?,'yyyy-mm-dd') and TO_DATE(?,'yyyy-mm-dd')";
//      public static String GET_ACCOUNTDETAIL_BY_ID_AND_TIME = "SELECT TRADE_TIME,income_money,operator_id FROM T_ACCOUNT_DETAIL  "
//            + "WHERE ACCOUNT_ID = ? "
//            + "AND TRADE_TIME between TO_DATE(?,'yyyy-mm-dd') and TO_DATE(?,'yyyy-mm-dd')";
//    /**
//     * 根据缴款日期、银行缴款交易流水号获取银行账号、交易时间、收入金额、支出金额
//     */
//    public static String GET_ACCOUNT_DETAIL_BY_TRADE_DATE_BANK_SERAL_NO = "SELECT B.BOUND_CARD, A.TRADE_TIME, A.INCOME_MONEY, A.OUT_MONEY "
//            + "FROM T_ACCOUNT_DETAIL A, T_ACCOUNT_INFO B "
//            + "WHERE A.ACCOUNT_ID=B.ACCOUNT_ID "
//            + "AND B.ACCOUNT_NAME = ? "
//            + "AND TO_CHAR(A.TRADE_TIME,'yyyy-mm-dd')=? "
//            + "AND A.BANK_SERIAL_NO=? AND A.TRADE_TYPE = ? ";
//    
//    /**
//     * 根据银行缴款交易流水号获取银行账号、交易时间、交易金额
//     */
//    public static String GET_ACCOUNT_DETAIL_BYBANK_SERAL_NO = "SELECT A.TRADE_TIME, A.INCOME_MONEY, A.ACCOUNT_ID "
//            + "FROM T_ACCOUNT_DETAIL A, T_ACCOUNT_INFO B "
//            + "WHERE A.ACCOUNT_ID=B.ACCOUNT_ID "
//            + "AND B.ACCOUNT_NAME = ? "
//            + "AND A.BANK_SERIAL_NO=? AND A.TRADE_TYPE = ? ";
//    
//}
