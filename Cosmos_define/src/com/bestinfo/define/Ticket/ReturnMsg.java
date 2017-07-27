/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bestinfo.define.Ticket;

public enum ReturnMsg {

    SUCCESS("00000", "成功"),
    REQUEST_PARAM_EXCEPTION("10001","请求参数为空"),
    QUERY_TICKETBETPRIZE_EXCEPTION("10002","彩票查询无数据"),
    QUERY_DATA_EXCEPTION("10010","查询数据异常"),
    CHECK_DATA_MD5_EXCEPTION("10003","加密串校验失败"),
    INIT_BACK_MSG("10004","中心兑奖服务层初始值"),
    QUERY_CIPHER("10005","彩票密码为空"),
    QUERY_TICKET_PRIZE("10006","此票未中奖"),
    QUERY_CASH_YES("10007","此票已经兑奖"),
    CHECK_CIPHER("10008","票面密码错误"),
    CHECK_GAMEINFO("10009","游戏信息不存在"),
    CHECK_GAME_DRAW("10011","期校验错误"),
    CASH_CHECK_GAME("10012","中奖金额不超过1万，不能在中心兑奖"),
    CASH_SERVICE_EXCEPTION("10013","兑奖服务异常"),
    CENTER_CASH_EXCEPTION("10015","中心兑奖Repository异常"),
    PROCEDURE_3155("13155","此票未中奖"),
    PROCEDURE_6492("16492","没有当前期"),
    PROCEDURE_6493("16493","没有找到此游戏的指定期信息"),
    PROCEDURE_6494("16494","期不允许兑奖"),
    PROCEDURE_6495("16495","期不允许兑奖"),
    PROCEDURE_6496("16496","不在兑奖时间内"),
    PROCEDURE_6591("16591","快开游戏期不存在"),
    PROCEDURE_6658("16658","期不允许兑奖"),
    PROCEDURE_6659("16659","期不允许兑奖"),
    PROCEDURE_6660("16660","不在兑奖时间内"),
    PROCEDURE_6911("16911","中奖注数或者中奖总金额错误"),
    PROCEDURE_6931("16931","SQLERRM"),
    PROCEDURE_3003("13003","SQLERRM");
    private String code;
    private String msg;

    private ReturnMsg(String code, String msg) {
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
