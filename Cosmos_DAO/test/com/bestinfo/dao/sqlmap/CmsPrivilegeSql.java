//package com.bestinfo.dao.sqlmap;
//
///**
// * 内容发布权限
// *
// * @author chenliping
// */
//public class CmsPrivilegeSql {
//
//    /**
//     * 查询内容发布权限
//     */
//    public static String SELECT_CMSPRIVILEGE_LIST = "SELECT * FROM T_CMS_PRIVILEGE";
//    /**
//     * 查询
//     */
//    public static String SELECT_CMSPRIVILEGE_BY_PRIMARY = "select * from T_CMS_PRIVILEGE WHERE CMS_ID = ? AND CMS_RANGE = ? AND RECEIVING_OBJECT = ?";
//    /**
//     * *
//     * 修改
//     */
//    public static String UPDATE_CMSPRIVILEGE = " UPDATE T_CMS_PRIVILEGE SET CMS_RANGE = ?, RECEIVING_OBJECT = ?, WORK_STATUS = ?  WHERE CMS_ID = ? ";
//    /**
//     * 新增
//     */
//    public static String INSERT_CMSPRIVILEGE = "INSERT INTO T_CMS_PRIVILEGE(CMS_ID,CMS_RANGE,RECEIVING_OBJECT,WORK_STATUS) VALUES (?,?,?,?)";
//
//}
