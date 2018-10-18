//package com.bestinfo.dao.sqlmap;
//
///**
// * 资金帐户
// *
// * @author hhhh
// */
//public class AccountInfoSql {
//
//    /**
//     * 插入资金帐户统一汇总信息sql
//     */
//    public static String INSERT_ACCOUNT_INFO = "INSERT INTO T_ACCOUNT_INFO(ACCOUNT_ID, ACCOUNT_NAME, OWNER_NAME, ACCOUNT_TYPE, ACC_BALANCE, SETTLEMENT_TYPE, "
//                                                      + "SUM_BET_MONEY, SUM_UNDO_MONEY, SUM_PRIZE_MONEY, SUM_HAND_IN, SUM_HAND_OUNT, SUM_PAY_OUT, sum_agent_fee, sum_cash_fee, "
//                                                      + "PRIZE_METHOD, bank_id, BOUND_CARD, ACC_NOTE, PRIZE_BALANCE, FREEZE_MONEY, ACCOUNT_STATUS, "
//                                                      + "DEFAULT_CREDIT, CREDIT_TIME, TEMP_CREDIT, ACCOUNT_SERIAL_NO) VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
//    /**
//     * 查询资金账户统一汇总信息
//     */
//    public static String SELECT_ACCOUNTINFO_BYID = "SELECT ACCOUNT_ID, ACCOUNT_NAME, OWNER_NAME, ACCOUNT_TYPE, ACC_BALANCE, SETTLEMENT_TYPE, "
//                                                      + "SUM_BET_MONEY, SUM_UNDO_MONEY, SUM_PRIZE_MONEY, SUM_HAND_IN, SUM_HAND_OUNT, SUM_PAY_OUT, sum_agent_fee, sum_cash_fee, "
//                                                      + "PRIZE_METHOD, bank_id, BOUND_CARD, ACC_NOTE, PRIZE_BALANCE, FREEZE_MONEY, ACCOUNT_STATUS, "
//                                                      + "DEFAULT_CREDIT, CREDIT_TIME, TEMP_CREDIT, ACCOUNT_SERIAL_NO "
//                                                      + "FROM T_ACCOUNT_INFO WHERE TRIM(ACCOUNT_ID)=?";
////      public static String SELECT_ACCOUNTINFO_BYID = "select * from T_ACCOUNT_INFO where ACCOUNT_ID = ? ";
//
//    /**
//     * 根据帐号名称和帐号类型查询账户信息
//     */
//    public static String GET_ACCOUNTINFO_BY_ACCNAME_ACCTYPE = "SELECT OWNER_NAME, ACC_BALANCE, DEFAULT_CREDIT, CREDIT_TIME, TEMP_CREDIT "
//                                                      + "FROM T_ACCOUNT_INFO WHERE ACCOUNT_NAME=? ";
//
//    /**
//     * 根据账号id获取数据
//     */
//    public static String GET_ACCOUNTINFO_BY_ACCOUNT_ID = "SELECT * FROM T_ACCOUNT_INFO WHERE trim(ACCOUNT_ID)=? ";
//
//    /**
//     * 根据账号查询账户信息
//     */
//    public static String GET_ACCOUNTINFO_BY_ACCOUNT_NAME = "SELECT ACCOUNT_ID, ACCOUNT_NAME, ACC_BALANCE, SUM_HAND_IN, ACCOUNT_SERIAL_NO, BANK_ID "
//                                                      + "FROM T_ACCOUNT_INFO WHERE ACCOUNT_NAME=? ";
//
//    /**
//     * 根据账户名称更改账户余额、累计充值金额、当前账户流水号
//     */
//    public static String UPDATE_ACCOUNTINFO_BY_ACCOUNT_NAME = "UPDATE T_ACCOUNT_INFO SET ACC_BALANCE = ?, SUM_HAND_IN = ?, ACCOUNT_SERIAL_NO = ? "
//                                                      + "WHERE ACCOUNT_NAME = ?";
//
//    /**
//     * 根据账户名称更改账户余额、累计抹账金额、当前账户流水号
//     */
//    public static String UPDATE_ACCOUNTINFO_OUNT_BY_ACCOUNT_NAME = "UPDATE T_ACCOUNT_INFO SET ACC_BALANCE = ?, SUM_HAND_OUNT = ?, ACCOUNT_SERIAL_NO = ? "
//                                                      + "WHERE ACCOUNT_NAME = ?";
//    
//    public static String UPDATE_ACCOUNTINFO_BY_ACCID = "UPDATE T_ACCOUNT_INFO SET ACC_BALANCE = ?, ACCOUNT_SERIAL_NO = ACCOUNT_SERIAL_NO + 1 WHERE ACCOUNT_ID = ?";
//
//}
