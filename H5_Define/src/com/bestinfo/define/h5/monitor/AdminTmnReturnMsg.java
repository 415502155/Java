/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.bestinfo.define.h5.monitor;

/**
 *
 * @author YangRong
 */
public enum AdminTmnReturnMsg {
    SUCCESS("0","成功"),
    GENERIC_ERROR("900000", "错误"),
    EXCEPTION("800000","异常错误"),
    
    //105 device
    
    INVALID_DEVICE("105101", "输入设备信息为空"),
    REPEATED_DEVICE_ID("105102","设备ID已存在"),
    REPEATED_DEVICE_NUMBER("105103","设备序号已存在"),
    INSERT_DEVICE_FAILED("105104","错误"),
    
    INVALID_INPUT_DEVICE_STATUS("105201","设备状态修改值无效"),
    NOT_ALLOWED_MODIFY_DEVICE_STATUS("105202","当前设备状态不允许修改"),
    INVALID_DEVICE_ID("105203","设备无效"),
    UPDATE_DEVICE_STATUS_FAILED("105204","错误"),
    
    DEVICE_QUERY_RESULT_IS_NULL("105301","设备查询结果为空"),
    
    
    //106 gambler
    
     GAMBLER_NAME_IS_NULL("106001","彩民名称为空"),
     INVALID_GAMBLER_NAME("106002","无效彩民"),
     INVALID_TMN_ID_IN_SESSION("106003","错误"),
     GAMBLER_NOT_INUSE("106004","彩民不可用"),
     GABLER_HAS_LOGINED("106005","彩民已登录，需登出"),
     INVALID_GAMBLER_PASSWORD_WITH_NAME("106006","用户名密码错误"),
    INVALID_GAMBELR_ID_WITH_NAME("106007","无效凭条"),
    
    
    INVALID_REGIST_CHARGE_MONEY("106101","充值金额无效"),
    
    GENERATE_GAMBLER_NAME_FAILED("106102","错误"),
    GENERATE_GAMBLER_ID_FAILED("106103","错误"),
    GENERATE_DEFAULT_PASSWORD_MD5_FAILED("106104","错误"),
    DEALER_ID_IS_NULL_IN_SESSION("106105","错误"),
    REGIST_DB_PROCEDUE_EXEC_FAILED("106106","错误"),
    GERNERATE_LOGIN_CODE_FAILED("106107","错误"),
    FUND_GREATER_BOND("106108","沉淀金已大于保证金,不可注册新彩民"),
    QUERY_BOND_ERROR("106109","错误"),
    QUERY_FUND_ERROR("106110","错误"),
    
   
    INVALID_GAMBLER_MODIFY_STATUS("106201","彩民状态修改值无效"),
    UPDATE_GAMBLER_STATUS_FAILED("106202","错误"),
    
    
    GAMBLER_HAS_UNCASH_TICKET("106303","彩民有未兑奖彩票"),
    SETTLEMENT_DB_PROCEDUE_EXEC_FAILED("106304","错误"),
    QUERY_GAMBLER_UNCASH_SCHEME_FAILED("106305","错误"),
    QUERY_GAMBLER_INSELL_SCHEME_FAILED("106306","错误"),
    GAMBLER_HAS_INSELL_TICKET("106307","彩民有未到兑奖期的彩票"),
    GAMBLER_PWD_IS_DEFAULT_PWD("106308","不可用缺省密码结算"),
    
    
    INVALID_CHARGE_MONEY("106401","充值金额无效"),
    CHARGE_DB_PROCEDUE_EXEC_FAILED("106402","错误"),
    
    INVALID_GAMBLER_ID_AND_NAME("106501","无效凭条"),
    
    GENERATE_PASSWORD_MD5("106503","错误"),
    UPDATE_GAMBLER_PASSWORD_FAILED("106504","错误"),
    
    
    QUERY_GAMBLER_PRINT_SCHEME_FAILED("106601","查询结果为空"),
    
    UPDATE_GAMBLER_PRINT_SCHEME_STATUS_FAILED("106701","错误"),
    INVALID_GAMBLER_SCHEME("106702","无效方案"),
    SCHEME_HAS_PRINTED("106703","此票已打印,不能重复打印"),
    
    INVALID_LOGIN_ID_AND_NAME("106801","无效凭条"),
    UPDATE_LOGIN_CODE_FAILED("106802","错误"),    
    
    INVALID_QUERY_DRAW_INFO("106901","无效的期信息"),
    QUERY_GAMBLER_BET_RECORD_FAILED("106902","查询结果为空"),
    INVAILD_GAME_ID("106903","无效的游戏信息"),
    
    //103 terminal
    TERMINAL_ID_IS_NULL("103101","终端号为空"),
    INVALID_PUBLIC_KEY("103102","错误"),
    INVALID_TERMINAL_ID("103103","错误"),
    TERMINAL_HAS_REGISTED("103104","错误"),
    TERMINAL_UNUSE("103105","错误"),
    TERMINAL_IS_NOT_HALL_TMN("103106","错误"),
    TERMINAL_ACCOUNT_IS_NULL("103107","错误"),
    SERVER_PUBLIC_KEY_IS_NULL("103108","错误"),
    UPDATE_TERMINAL_INFO_FAILED("103109","错误"),
    
    
    TMN_INFO_IS_NULL("103201","无效终端号"),
    SYSTEM_INFO_IS_NULL_IN_ECACHE("103202","错误"),
    CITY_INFO_IS_NULL_IN_ECACHE("103203","错误"),
    GAME_LIST_IS_NULL("103204","错误"),
    
    
    //104 manager
    
    MANAGER_NAME_PWD_IS_NULL("104101","管理员名密码为空"),
    INVALID_MANAGER_PWD("104102","管理员密码错误"),
    GENERATE_DES_KEY_FAILED("104103","错误"),
    ADD_SESSION_TO_REDIS_FAILED("104104","错误"),
    INVALID_DEALER_USER("104105","无效管理员"),
    PASSWORD_IS_NULL("104301","密码为空"),
    NOT_LOGIN("104302","用户未登陆"),
    PWD_USERNAME_IN_SESSION_IS_NULL("104303","错误"),
    INVALID_PASSWORD("104304","密码无效"),
    UPDATE_MANAGER_PASSWORD_FAILED("104305","错误"),
    
    
    MANAGER_NAME_IS_NULL("104201","管理员名称为空"),
    MANGER_NAME_IS_NOT_EQUAL_WITH_SESSION("104202","管理员名无效"),
    MANAGER_HAS_LOGOUT("104203","管理员已登出"),
    
    
    QUERY_HALL_ACCOUNT_FAILED("107001","大厅账户查询结果为空"),
    DEALER_ID_IS_NULL("107002","错误"),
    GAMBLER_IS_NULL_IN_DB("107003","错误"),
    GAMBLER_NAME_IS_INVALID("107004","错误"),
    
    HALL_ACOUNT_DETAIL_QUERY_RESULT_IS_EMPTY("107101","无记录"),
    TERMINAL_ACCOUNT_DETAIL_QUERY_RESULT_IS_EMPTY("107301","无记录"),
    QUERY_GAMBLER_ACCOUNT_FAILED("107111","错误"),
    GAMBLER_ACCOUNT_DETAIL_QUERY_RESULT_IS_EMPTY("107121","无记录"),
    
    
    
    INVALID_GAMBLER_STATUS("107501","无效的彩民状态值"),
    QUERY_LOGIN_GAMBLER_ACCOUNT_FAILED("107502","已登陆彩民账户查询错误"),
    QUERY_ALL_GAMBLER_ACCOUNT_FAILED("107503","彩民账户查询错误"),
    
    QUERY_BOND_FAILED("107601","保证金查询结果为空"),
    QUERY_FUND_FAILED("107602","沉淀金查询结果为空"),
    
    //102   监控
    QUERY_MONITOR_GAME_DATA_FAILED("10201","游戏监控数据查询失败"),
    MONITOR_GAME_EXCEPTION("102404","游戏监控异常"),
    QUERY_LOGIN_CITY_FAILED("10202","查询登录城市未果"),
    QUERY_CITY_NAME_LIST_FAILED("10203","查询城市名称列表失败"),
    QUERY_LIST_BANK_PAYMENT_FAILED("10204","实时缴款账户监控数据没查出来"),
    
    //103   对比
    COMPARISON_EXCEPTION("103404","对比异常"),
    QUERY_WELFAREFUND_NO_DATA("10301","查询公益金对比没有数据"),
    QUERY_WEEKREPORTCITY_YEAR_FAILED("10302","查询票种同比增长年销量失败"),
    QUERY_WEEKREPORTCITY_WEEK_FAILED("10303","票种同比增长周销量查询未果");
    
    
    
    
    
    
    
    
    
    
    
    
    private String code;
    private String msg;

    private AdminTmnReturnMsg(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

   

    

    



}
