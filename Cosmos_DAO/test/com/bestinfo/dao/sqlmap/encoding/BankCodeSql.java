///*
// * To change this license header, choose License Headers in Project Properties.
// * To change this template file, choose Tools | Templates
// * and open the template in the editor.
// */
//package com.bestinfo.dao.sqlmap.encoding;
//
///**
// *
// * @author user
// */
//public class BankCodeSql {
//
//    /**
//     * 查询银行编码表数据列表sql
//     */
//    public static String GET_BANKCODE_LIST = "select  *  from T_BANK_CODE";
//    /**
//     * 根据银行编码表编号更新银行卡类型sql
//     */
//    public static String UPDATE_BANKCODE= "UPDATE T_BANK_CODE SET "
//                                                      + "BANK_NAME = ?,WORK_STATUS = ?"
//                                                      + "WHERE BANK_ID = ?";
//    
//    /**
//     * 根据银行编号获取银行编码信息
//     */
//    public static String GET_BANKCODE_BY_BANKID = "SELECT BANK_NAME, WORK_STATUS FROM T_BANK_CODE WHERE BANK_ID = ? ";
//}
