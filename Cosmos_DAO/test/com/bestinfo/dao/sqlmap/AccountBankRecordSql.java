///*
// * To change this license header, choose License Headers in Project Properties.
// * To change this template file, choose Tools | Templates
// * and open the template in the editor.
// */
//
//package com.bestinfo.dao.sqlmap;
//
///**
// * 资金账户-缴款记录信息
// * @author zyk
// */
//public class AccountBankRecordSql {
//    /**
//     * 缴款查询
//     */
//    public static String getRecordByNameSerialDateMoney = 
//            "select b.account_name, a.bank_serial_no, a.account_no, a.pay_type, a.account_date, a.pay_time, a.pay_money "
//            + "from T_account_bank_record a, T_account_info b "
//            + "where a.account_id = b.account_id "
//            + "and trim(b.account_id) = ? "
//            + "and a.bank_serial_no = ? "
//            + "and to_char(a.account_date,'yyyyMMdd') = ? "
//            + "and to_char(a.pay_time, 'yyyy-MM-dd') = ? "
//            + "and a.pay_money = ?";
//    
//    /**
//     * 抹账查询
//     */
//    public static String getRecordByAccNameSerialAccDateUndoMoney = 
//            "select a.account_no, c.trade_time as pay_time "
//            + "from T_account_bank_record a, T_account_info b, t_account_detail c "
//            + "where a.account_id = b.account_id and a.account_id = c.account_id "
//            + "and trim(b.account_id) = ? "
//            + "and a.undo_bank_serial_no = ? "
//            + "and c.bank_serial_no = ? "
//            + "and a.bank_serial_no = ? "
//            + "and to_char(a.account_date,'yyyyMMdd') = ? "
//            + "and a.pay_money = ?";
//    
//    /**
//     * 缴款查询
//     */
//    public static String getRecordByAccountIdandTime = " select  *  from T_account_bank_record where "
//                                                      + "account_id=? "
//                                                      + "and pay_time between to_date( ?,'yyyy-mm-dd hh24:mi:ss') and to_date( ?,'yyyy-mm-dd hh24:mi:ss') "
//                                                      + "and undo_mark =?";
//}
