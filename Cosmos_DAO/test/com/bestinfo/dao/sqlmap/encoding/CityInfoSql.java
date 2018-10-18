//package com.bestinfo.dao.sqlmap.encoding;
//
///**
// *
// * @author hhhh
// */
//public class CityInfoSql {
//
//    /**
//     * 根据省编号和地市编号获取二级城市编号信息sql
//     */
//    public static String GET_CITYINFO_BY_ID = "SELECT * FROM T_CITY_INFO WHERE PROVINCE_ID = ? and CITY_ID = ?";
//
//    /**
//     * 查询二级城市编号列表数据sql
//     */
//    public static String GET_CITYINFO_List = "SELECT * FROM T_CITY_INFO";
//
//    /**
//     * 根据省编号和地市编号更新二级城市编号信息sql
//     */
//    public static String UPDATE_CITYINFO = "UPDATE T_CITY_INFO SET "
//            + "CITY_NAME = ?,LONG_CITY_ID = ?,CITY_ADDRESS = ?,CITY_PHONE = ?,"
//            + "TERMINAL_PASSWORD = ?,CITY_WORK_STATUS = ?"
//            + " WHERE PROVINCE_ID = ? AND CITY_ID = ?";
//
//}
