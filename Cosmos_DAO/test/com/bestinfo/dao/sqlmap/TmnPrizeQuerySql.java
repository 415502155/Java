///*
// * To change this license header, choose License Headers in Project Properties.
// * To change this template file, choose Tools | Templates
// * and open the template in the editor.
// */
//package com.bestinfo.dao.sqlmap;
//
///**
// * 彩票终端中奖情况查询
// *
// * @author YangRong
// */
//public class TmnPrizeQuerySql {
//
//    public static String SELECT_TMN_PRIZE_LIST_BY_DRAW = "select a.DRAW_ID,a.CLASS_ID , b.CLASS_NAME,a.PRIZE_NUM,b.STAKE_PRIZE "
//            + " from T_STAT_KENO_DISTRIBUTION  a ,T_STAT_PRIZE_ANN  b "
//            + " where a.CLASS_ID=b.CLASS_ID and a.DRAW_ID=b.DRAW_ID and a.GAME_ID=b.GAME_ID and a.GAME_ID=? and a.TERMINAL_ID=? and a.DRAW_ID=?  ";
//    public static String SELECT_TMN_PRIZE_LIST_BY_KENO_DRAW = "select a.KENO_DRAW_ID,a.CLASS_ID, b.CLASS_NAME,a.PRIZE_NUM,b.STAKE_PRIZE "
//            + " from T_STAT_KENO_DISTRIBUTION  a ,T_STAT_KENO_PRIZE_ANN  b "
//            + " where a.CLASS_ID=b.CLASS_ID and a.KENO_DRAW_ID=b.KENO_DRAW_ID and a.GAME_ID=b.GAME_ID and a.GAME_ID=? and a.TERMINAL_ID=? and a.KENO_DRAW_ID=?  ";
//    public static String SELECT_TMN_PRIZE_LIST_BY_2DRAW = "select a.DRAW_ID,a.CLASS_ID, b.CLASS_NAME,a.PRIZE_NUM,b.STAKE_PRIZE "
//            + " from T_STAT_KENO_DISTRIBUTION as a ,T_STAT_PRIZE_ANN as b "
//            + " where a.CLASS_ID=b.CLASS_ID and a.DRAW_ID=b.DRAW_ID and a.GAME_ID=b.GAME_ID and a.GAME_ID=? and a.TERMINAL_ID=? and a.DRAW_ID between ? and ?  ";
//    public static String SELECT_TMN_PRIZE_LIST_BY_KENO_2DRAW = "select a.KENO_DRAW_ID,a.CLASS_ID, b.CLASS_NAME,a.PRIZE_NUM,b.STAKE_PRIZE "
//            + " from T_STAT_KENO_DISTRIBUTION as a ,T_STAT_KENO_PRIZE_ANN as b "
//            + " where a.CLASS_ID=b.CLASS_ID and a.KENO_DRAW_ID=b.KENO_DRAW_ID and a.GAME_ID=b.GAME_ID and a.GAME_ID=? and a.TERMINAL_ID=? and a.KENO_DRAW_ID between ? and ?  ";
//    public static String SELECT_SITE_PRIZE_LIST_BY_DRAW = "select a.DRAW_ID,a.CLASS_ID, b.CLASS_NAME,a.PRIZE_NUM,b.STAKE_PRIZE "
//            + " from T_STAT_KENO_DISTRIBUTION as a ,T_STAT_PRIZE_ANN as b "
//            + " where a.CLASS_ID=b.CLASS_ID and a.DRAW_ID=b.DRAW_ID and a.GAME_ID=b.GAME_ID and a.GAME_ID=? and a.TERMINAL_ID in ? and a.DRAW_ID=? order by a.TERMINAL_ID ";
//    public static String SELECT_SITE_PRIZE_LIST_BY_KENO_DRAW = "select a.KENO_DRAW_ID,a.CLASS_ID, b.CLASS_NAME,a.PRIZE_NUM,b.STAKE_PRIZE "
//            + " from T_STAT_KENO_DISTRIBUTION as a ,T_STAT_KENO_PRIZE_ANN as b "
//            + " where a.CLASS_ID=b.CLASS_ID and a.KENO_DRAW_ID=b.KENO_DRAW_ID and a.GAME_ID=b.GAME_ID and a.GAME_ID=? and a.TERMINAL_ID in ? and a.KENO_DRAW_ID=? order by a.TERMINAL_ID   ";
//    public static String SELECT_SITE_PRIZE_LIST_BY_2DRAW = "select a.DRAW_ID,a.CLASS_ID, b.CLASS_NAME,a.PRIZE_NUM,b.STAKE_PRIZE "
//            + " from T_STAT_KENO_DISTRIBUTION as a ,T_STAT_PRIZE_ANN as b "
//            + " where a.CLASS_ID=b.CLASS_ID and a.DRAW_ID=b.DRAW_ID and a.GAME_ID=b.GAME_ID and a.GAME_ID=? and a.TERMINAL_ID in ? and a.DRAW_ID between ? and ?  order by a.TERMINAL_ID  ";
//    public static String SELECT_SITE_PRIZE_LIST_BY_KENO_2DRAW = "select a.KENO_DRAW_ID,a.CLASS_ID, b.CLASS_NAME,a.PRIZE_NUM,b.STAKE_PRIZE "
//            + " from T_STAT_KENO_DISTRIBUTION as a ,T_STAT_KENO_PRIZE_ANN as b "
//            + " where a.CLASS_ID=b.CLASS_ID and a.KENO_DRAW_ID=b.KENO_DRAW_ID and a.GAME_ID=b.GAME_ID and a.GAME_ID=? and a.TERMINAL_ID in ? and a.KENO_DRAW_ID between ? and ?  order by a.TERMINAL_ID  ";
//
//}
