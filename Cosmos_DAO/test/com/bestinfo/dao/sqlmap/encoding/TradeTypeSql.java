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
//public class TradeTypeSql {
//
//    /**
//     * 查询资金交易类型数据列表sql
//     */
//    public static String GET_TRADETYPE_LIST = "select  *  from T_TRADE_TYPE";
//    
//    /**
//     * 根据是否批量处理查询出符合条件的数据sql
//     */
//    public static String GET_TRADETYPE_LIST_BY_ISBATCH = "select  *  from T_TRADE_TYPE where batch_item = ?";
//    
//    /**
//     * 根据资金交易类型编号更新资金交易类型sql
//     */
//    public static String UPDATE_TRADETYPE = "UPDATE T_TRADE_TYPE SET "
//                                                      + "TRADE_TYPE_NAME = ?,WORK_STATUS = ?"
//                                                      + "WHERE TRADE_TYPE = ?";
//}
