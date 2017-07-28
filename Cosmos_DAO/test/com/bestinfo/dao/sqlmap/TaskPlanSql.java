//package com.bestinfo.dao.sqlmap;
//
///**
// * 结算统计表--任务计划及执行结果
// *
// * @author zyk
// */
//public class TaskPlanSql {
//
//    public static String SELECT_TASKPLAN_BY_TASKIDPLANID = "select * from T_task_plan where task_id = ? and plan_id = ? ";
//    
//    public static String SELECT_FIRSTDAY_LASTDAY = "select YEARMONTH_ID,BEGIN_DATE,END_DATE from T_MONTH_INFO where YEARMONTH_ID = to_char(sysdate,'yyyymm')";
//    
//    public static String SELECT_FIRSTDAY_LASTDAY_NEXT_MONTH = "select YEARMONTH_ID,BEGIN_DATE,END_DATE from T_MONTH_INFO where YEARMONTH_ID = to_char(add_months(sysdate,1),'yyyymm')";
//    
//    public static String INSERT_TASK_PLAN = "insert into T_TASK_PLAN(TASK_ID,PLAN_ID,PLAN_NAME,BEGIN_DATE,END_DATE,STAT_DATE,PLAN_STATUS,USER_ID,TERMINAL_COUNT,TOTAL_MONEY) values(?,?,?,?,?,?,?,?,?,?)";
//    
//    public static String UPDATE_TASK_INFO_CUR_PLAN_ID = "update T_TASK_INFO set CUR_PLAN_ID=? WHERE TASK_ID=?";
//    
//    public static String UPDATE_TASK_PLAN_STATUS      = "update T_TASK_PLAN SET PLAN_STATUS=1, TERMINAL_COUNT=?, TOTAL_MONEY=? WHERE TASK_ID=? AND PLAN_ID=?";
//    
//    public static String SELECT_TRADE_TYPE = "SELECT  trade_type from  T_TASK_INFO  where task_id=?";
//    
//    public static String SELECT_TASK_NAME = "SELECT  task_name from  T_TASK_INFO  where task_id=?";
//    
//    
//    /** 扣款账户的项目信息，终端号，扣款账户，账户余额，扣除金额，账户序列号**/
//    public static String SELECT_TERMINAL_ACC_INFO_WORK0="SELECT C.ACCOUNT_SERIAL_NO,B.ACCOUNT_ID,A.TRADE_TYPE,C.ACC_BALANCE,A.DEDUCT_MONEY,A.TERMINAL_ID FROM T_TMN_AUTO_DEDUCTION A,T_TMN_INFO B ,T_ACCOUNT_INFO C WHERE A.TERMINAL_ID = B.TERMINAL_ID AND A.TRADE_TYPE = ? and B.TERMINAL_STATUS = 0 and C.ACCOUNT_ID = B.ACCOUNT_ID AND A.WORK_STATUS = 1 ";
//     
//    public static String UPDATE_ACCOUNT_SERIAL_NO   = " update  t_account_info set account_serial_no=? where account_id=? ";
//    
//    public static String UPDATE_ACCOUNT_ACC_BALANCE = " update  t_account_info set acc_balance=? where account_id=? ";
//    
//    public static String INSERT_ACCOUNT_DETAIL ="insert into T_ACCOUNT_DETAIL(ACCOUNT_SERIAL_NO,ACCOUNT_ID,OPERATOR_ID,TRADE_TIME,TRADE_TYPE,SOURCE_TYPE,RECHARGE_SOURCE,PRE_MONEY,INCOME_MONEY,OUT_MONEY,ACC_BALANCE,BANK_SERIAL_NO,SCHEME_ID,TRADE_NOTE) values(?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
//    
//    /** 定时扣款第二版 语句  **/
//    public static String SELECT_TERMINAL_ACC_INFO_WORK1 = "SELECT B.ACCOUNT_ID,A.DEDUCT_MONEY,A.TRADE_TYPE FROM T_TMN_AUTO_DEDUCTION A,T_TMN_INFO B ,T_ACCOUNT_INFO C WHERE A.TERMINAL_ID = B.TERMINAL_ID AND B.TERMINAL_STATUS = 0 AND C.ACCOUNT_ID = B.ACCOUNT_ID AND A.TRADE_TYPE = ? AND A.WORK_STATUS = 1 ";
//    
//    
//    
//}
