package com.bestinfo.sql;

//package com.bestinfo.dao.sqlmap;
//
///**
// * 游戏资金比例
// *
// * @author chenliping
// */
//public class GameFundProportionSql {
//    public static String SELECT_GAME_FUNDS_BY_GAME_ID = "select ISSUE_PROPORTION_STATION,CASH_FEE_RATE from T_GAME_FUNDS_PROPORTION where GAME_ID = ?";
//
//    public static String SELECT_GAME_FUNDS = "SELECT  *  FROM T_GAME_FUNDS_PROPORTION WHERE GAME_ID=?";
//    
//    public static String SELECT_GAME_FUNDS_LIST = "SELECT * FROM T_GAME_FUNDS_PROPORTION";
//    
//    public static String INSERT_GAME_FUNDS = "INSERT INTO T_GAME_FUNDS_PROPORTION (GAME_ID, DRAW_ID, WELFARE_PROPORTION, WELFARE_PROPORTION_CLP, WELFARE_PROPORTION_PROVINCE, ISSUE_PROPORTION, ISSUE_PROPORTION_CLP, ISSUE_PROPORTION_PROVINCE, ISSUE_PROPORTION_CITY, ISSUE_PROPORTION_STATION, RETURN_PROPORTION, RESERVE_PROPORTION) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
//   
//    /**
//     * 根据game_id获取资金比例记录
//     */
//    public static String SELECT_GAME_FUNDS_BY_GAMEID = "SELECT * FROM T_GAME_FUNDS_PROPORTION  WHERE GAME_ID=?";
//    
//    public static String UPDATE_GAME_FUNDS = "UPDATE T_GAME_FUNDS_PROPORTION SET WELFARE_PROPORTION = ?, WELFARE_PROPORTION_CLP = ?, WELFARE_PROPORTION_PROVINCE = ?, ISSUE_PROPORTION = ?, ISSUE_PROPORTION_CLP = ?, ISSUE_PROPORTION_PROVINCE = ?, ISSUE_PROPORTION_CITY = ?, ISSUE_PROPORTION_STATION = ?, RETURN_PROPORTION = ?, RESERVE_PROPORTION = ? WHERE GAME_ID = ? AND DRAW_ID = ?";
//}