//package com.bestinfo.dao.sqlmap.encoding;
//
///**
// *
// * @author hhhh
// */
//public class SystemInfoSql {
//
//    /**
//     * 根据省系统编号获取省系统参数信息sql
//     */
//    public static String GET_SYSTEMINFO_BY_ID = "SELECT * FROM T_SYSTEM_INFO WHERE PROVINCE_ID = ?";
//
//    /**
//     * 查询省系统参数列表数据sql
//     */
//    public static String GET_SYSTEMINFO_List = "SELECT * FROM T_SYSTEM_INFO";
//
//    /**
//     * 根据省系统编号更新省系统参数sql
//     */
//    public static String UPDATE_SYSTEMINFO = "UPDATE T_SYSTEM_INFO SET "
//            + "PROVINCE_NAME = ?,PROVINCE_ADDRESS = ?,PROVINCE_PHONE = ?,AGENTFEE_TYPE = ?,"
//            + "DEDUCT_SWITCH = ?,CASH_FEE_TYPE=?,ONLINE_REPORT = ?,CENTER_STATUS = ?,MAX_TERMINAL = ?,CUR_MAX_GAME = ?,"
//            + "ONLINE_CASH_MARK = ?,SYSTEM_VERSION = ?,SYSTEM_SYN_NO = ?,CENTER_TYPE=?"
//            + " WHERE PROVINCE_ID = ?";
//
//}
