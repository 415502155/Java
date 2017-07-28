//package com.bestinfo.dao.sqlmap;
//
///**
// * 投注机号
// *
// * @author chenliping
// */
//public class TmnInfoSql {
//
//    public static String INSERT_TMNINFO = "INSERT INTO T_TMN_INFO(TERMINAL_SERIAL_NO, TERMINAL_ID, TERMINAL_PHY_ID,TERMINAL_INITIAL_TIME, SAFE_CARD_ID, CITY_ID, DISTRICT_ID, "
//            + "STATION_NAME, TERMINAL_ADDRESS, STATION_PHONE, OWNER_NAME, OWNER_PHONE, LINKMAN, LINKMAN_PHONE, REGIST_DATE, SOFTWARE_ID, "
//            + "UPGRADE_MARK, SOFTWARE_VERSION, TERMINAL_TYPE, TERMINAL_STATUS, agentfee_type, TMN_SALE_DEDUCT, TMN_CASH_DEDUCT, COMM_TYPE, "
//            + "DIAL_NAME, DIAL_PWD, ACCOUNT_ID, DEALER_ID, TERMINAL_SYN_NO,terminal_value,local_terminal_id,public_key,notice_syn_no,station_rank) VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
//
//    public static String SELECT_TMNINFO_BY_TERMINALID = "SELECT TERMINAL_SERIAL_NO,TERMINAL_ID,TERMINAL_PHY_ID,TERMINAL_INITIAL_TIME,SAFE_CARD_ID,CITY_ID,DISTRICT_ID,"
//            + "STATION_NAME,TERMINAL_ADDRESS,STATION_PHONE,OWNER_NAME,OWNER_PHONE,LINKMAN,LINKMAN_PHONE,REGIST_DATE,SOFTWARE_ID,"
//            + "UPGRADE_MARK,SOFTWARE_VERSION,TERMINAL_TYPE,TERMINAL_STATUS,agentfee_type,TMN_SALE_DEDUCT,TMN_CASH_DEDUCT,COMM_TYPE,"
//            + "DIAL_NAME,DIAL_PWD,ACCOUNT_ID,DEALER_ID,TERMINAL_SYN_NO from T_TMN_INFO WHERE 1=1 ";
//
//    public static String SELECT_COUNT_TMNINFO = "select count(*) from T_TMN_INFO where 1=1 ";
//    /**
//     * 缓存同步使用
//     */
//    public static String SELECT_TMNINFO_LIST = "SELECT * FROM T_TMN_INFO";
//    public static String SELECT_TMNINFO_BY_ID = "SELECT * FROM T_TMN_INFO where TERMINAL_ID = ?";
//    public static String UPDATE_TMNINFO = "UPDATE T_TMN_INFO SET TERMINAL_SERIAL_NO =?, TERMINAL_PHY_ID=?, TERMINAL_INITIAL_TIME = ?, SAFE_CARD_ID = ?, CITY_ID = ?, DISTRICT_ID = ?, STATION_NAME = ?, TERMINAL_ADDRESS = ?, STATION_PHONE = ?, OWNER_NAME = ?, OWNER_PHONE = ?, LINKMAN = ?, LINKMAN_PHONE = ?, REGIST_DATE = ?, SOFTWARE_ID = ?, UPGRADE_MARK = ?, SOFTWARE_VERSION = ?, TERMINAL_TYPE = ?, TERMINAL_STATUS = ?, agentfee_type = ?, TMN_SALE_DEDUCT = ?, TMN_CASH_DEDUCT = ?, COMM_TYPE = ?, DIAL_NAME = ?, DIAL_PWD = ?, ACCOUNT_ID = ?, DEALER_ID = ?, TERMINAL_SYN_NO = ?, public_key = ? WHERE  TERMINAL_ID = ?";
//
//    public static String SELECT_TMNINFO_BY_CITYID_TMNID = "select * from T_TMN_INFO WHERE CITY_ID = ? AND TERMINAL_ID = ?";
//    public static String SELECT_TMNINFO_BY_CITYID_TMNID_DEALID = "select * from T_TMN_INFO WHERE CITY_ID = ? AND TERMINAL_ID = ? AND DEALER_ID = ?";
//
//    public static String SELECT_MAX_terminal_serial_no = "select MAX(terminal_serial_no) a FROM T_TMN_INFO";
//    public static String SELECT_TMNINFO_BY_dealer_id = " select terminal_id from  T_TMN_INFO where dealer_id = ?";
//
//    public static String UPDATE_UPGRADE_MARK = "update T_TMN_INFO set UPGRADE_MARK = ? WHERE TERMINAL_ID = ?";
//    public static String UPDATE_UPGRADE_MARK_SYN_NO = "update T_TMN_INFO set UPGRADE_MARK = ?,TERMINAL_SYN_NO=? WHERE TERMINAL_ID = ?";
//    public static String UPDATE_NOTICE_TMN_SYN_NO = "update T_TMN_INFO set TERMINAL_SYN_NO=?,notice_syn_no=? WHERE TERMINAL_ID = ?";
//    public static String SELECT_TMNINFO_BY_CITYID = "select * from T_TMN_INFO WHERE CITY_ID = ?";
//
//    public static String SELECT_TMNINFO_BY_CITYID_STATUS = "select * from T_TMN_INFO WHERE CITY_ID = ? AND TERMINAL_STATUS not in (?)";
//
//    public static String SELECT_TMNINFO_BY_AREA_TMNID = "select * from T_TMN_INFO WHERE TERMINAL_ID between ? and ?";
//    public static String SELECT_TMNINFO_BY_AREA_TMNID_STATUS = "select * from T_TMN_INFO WHERE TERMINAL_ID between ? and ? AND TERMINAL_STATUS not in (?)";
//
//    public static String UPDATE_DETAIL_INFO = "UPDATE T_TMN_INFO SET  OWNER_NAME = ?, OWNER_PHONE = ?, LINKMAN = ?, LINKMAN_PHONE = ?, local_terminal_id = ?, station_rank = ?,terminal_address = ? WHERE TERMINAL_ID = ?";
//
//    public static String UPDATE_COMM_PARA = "UPDATE T_TMN_INFO SET  comm_type = ?, dial_name = ?, dial_pwd = ? WHERE TERMINAL_ID = ?";
//
//    public static String UPDATE_ACCOUNT_ID = "UPDATE T_TMN_INFO SET  account_id = ? WHERE TERMINAL_ID = ?";
//
//    public static String CLEAR_UPGRADE_MARK = "merge into T_tmn_info a "
//            + " using (select SOFTWARE_ID,software_version FROM T_terminal_software) b "
//            + " on (a.SOFTWARE_ID = b.SOFTWARE_ID "
//            + " and a.software_version = b.software_version "
//            + " and (? is null or ?='-99999' or a.CITY_ID = to_number(?)) "
//            + " AND (? is null or  A.TERMINAL_ID >= to_number(?)) "
//            + " AND (? is null or  A.TERMINAL_ID <= to_number(?)) ) "
//            + " when matched then "
//            + " update set a.upgrade_mark = ?";
//
//    public static String SELECT_TMN_INFO_LIST_BY_CONDITIONS = "select * from T_Tmn_info a "
//            + " WHERE (? is null or ?='-99999' or A.CITY_ID = to_number(?)) "
//            + " AND (? is null or  A.TERMINAL_ID >= to_number(?)) "
//            + " AND (? is null or  A.TERMINAL_ID <= to_number(?)) "
//            + " AND a.software_version IN(SELECT software_version FROM T_TERMINAL_SOFTWARE D WHERE A.SOFTWARE_ID = D.SOFTWARE_ID)";
//
//}
