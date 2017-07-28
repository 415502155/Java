//package com.bestinfo.dao.sqlmap;
//
///**
// *
// * @author YangRong
// */
//public class TermRegisterSql {
//     /**
//     * 根据投注终端逻辑id，更新投注机号表T_TMN_INFO的投注机初始化时间、加密芯片、公钥
//     */
//    public static String UPDATE_TMNINFO_REGISTER = "UPDATE T_TMN_INFO SET TERMINAL_INITIAL_TIME=?,SAFE_CARD_ID=?,PUBLIC_KEY=?  WHERE TERMINAL_ID=?";
//    
//     /**
//     * 根据投注终端逻辑id，更新投注机号表T_TMN_INFO的投注机初始化时间、加密芯片、公钥、升级标记
//     */
//    public static String UPDATE_TMNINFO = "UPDATE T_TMN_INFO SET TERMINAL_INITIAL_TIME=?,SAFE_CARD_ID=?,PUBLIC_KEY=?,upgrade_mark=?  WHERE TERMINAL_ID=?";
//    
//    public static String  UPDATE_TMNSOFTVERSION = "update t_tmn_info set software_version=? where terminal_id=?";
//    
//    /**
//     * 将终端CA插入证书表
//     */
//    public static String INSERT_TERMCERT = "";
// 
//     /**
//     * 根据终端id获取终端信息sql
//     */
//    public static String GET_TMNINFO_BY_TERMINAL_ID ="SELECT * FROM T_TMN_INFO WHERE TERMINAL_ID=?"; 
//    
//    /**
//     * 根据物理卡号查询终端信息 --clp
//     */
//    public static String GET_TMNINFO_BY_TMNPHYID ="select * from T_tmn_info where terminal_phy_id=?";
//    
//}
