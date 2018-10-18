//package com.bestinfo.dao.sqlmap;
//
///**
// * 运营商管理员用户列表
// *
// * @author yangyuefu
// */
//public class DealerUserSql {
//
//    /**
//     * 获取某一终端上指定角色的用户
//     */
//    public static String GET_DealerUser_List= "SELECT user_name,user_pwd  FROM T_dealer_user where role_id=? and terminal_id=?";
//    
//    /**
//     * 根据机号、用户名获取信息
//     */
//    public static String Get_info_By_TerminalId_AND_UserName= "SELECT * FROM T_dealer_user WHERE TERMINAL_ID = ? AND USER_NAME = ? and ROLE_ID=?";
//    
//    /**
//     * 更改密码
//     */
//    public static String update_user_pwd= "UPDATE T_DEALER_USER set USER_PWD = ? WHERE USER_NAME = ? AND TERMINAL_ID = ?";
//    
//    /**
//     * 根据用户名和密码获取信息
//     */
//    public static String Get_Pwd_By_User_Name = "SELECT USER_PWD,dealer_id,operator_id FROM T_dealer_user "
//            + " WHERE USER_NAME = ? AND ROLE_ID = ? ";
//    
//    public static String INSERT_Dealer_user="INSERT INTO T_DEALER_USER(USER_ID,USER_NAME,USER_PWD,FORCE_CHANGE_PWD,"
//            + " REAL_NAME,REGIST_DATE,ROLE_ID,WORK_STATUS,DEALER_ID,TERMINAL_ID,OPERATOR_ID,CITY_ID) "
//            + " VALUES(?,?,?,?,?,?,?,?,?,?,?,?)";
//    
//    /**
//     * 获取指定角色下的所有用户
//     */
//    public static String GETALL_DEALERUSER="SELECT OPERATOR_ID,TERMINAL_ID from T_DEALER_USER where role_id=?";
//    public static String getDealerUserByOperatorId = "SELECT user_name FROM T_DEALER_USER WHERE operator_id = ?";
//}
