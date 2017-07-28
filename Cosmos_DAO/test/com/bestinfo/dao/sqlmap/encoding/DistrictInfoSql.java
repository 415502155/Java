//package com.bestinfo.dao.sqlmap.encoding;
//
///**
// *
// * @author hhhh
// */
//public class DistrictInfoSql {
//
//    /**
//     * 根据省编号、地市编号、区县编号获取三级区县编号信息sql
//     */
//    public static String GET_DISTRICTINFO_BY_ID = "SELECT * FROM T_DISTRICT_INFO WHERE PROVINCE_ID = ? AND CITY_ID = ? AND DISTRICT_ID = ?";
//
//    /**
//     * 查询三级区县编号列表数据sql
//     */
//    public static String GET_DISTRICTINFO_List = "SELECT * FROM T_DISTRICT_INFO";
//
//    /**
//     * 根据省编号、地市编号、区县编号更新三级区县编号信息sql
//     */
//    public static String UPDATE_DISTRICTINFO = "UPDATE T_DISTRICT_INFO SET "
//            + "DISTRICT_NAME = ?,DISTRICT_ADDRESS = ?,DISTRICT_PHONE = ?,DISTRICT_WORK_STATUS = ?"
//            + " WHERE PROVINCE_ID = ? AND CITY_ID = ? AND DISTRICT_ID = ?";
//
//}
