package com.bestinfo.sql;

//package com.bestinfo.dao.sqlmap;
//
///**
// * 历史通知查询sql
// *
// * @author hhhh
// */
//public class HistoryNoticeSql {
//
//    /**
//     * 查询某个终端有权限看到的在一定时间范围内的所有通知
//     */
//    public static String selectNoticeByConditions = "SELECT DISTINCT A.CMS_ID,a.RELEASE_TIME "
//            + "FROM T_CMS_INFO A, T_CMS_PRIVILEGE B, T_TMN_INFO F "
//            + "WHERE A.CMS_TYPE = ? AND A.CMS_ID = B.CMS_ID "
//            + "AND ((B.CMS_RANGE = ? AND B.RECEIVING_OBJECT = TO_CHAR(F.CITY_ID) AND F.TERMINAL_ID = ?) "
//            + "OR (B.CMS_RANGE = ? AND B.RECEIVING_OBJECT = ?)) "
//            + "AND B.WORK_STATUS = ? "
//            + "AND a.RELEASE_TIME between to_date(?,'yyyy-mm-dd hh24:mi:ss') and to_date(?,'yyyy-mm-dd hh24:mi:ss') "
//            + "order by a.RELEASE_TIME DESC";
//
//    /**
//     * 根据主键查询内容
//     */
//    public static String selectNoticeById = "select * from T_CMS_INFO where CMS_ID = ?";
//}
