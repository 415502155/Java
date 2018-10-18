/**
 * 游戏信息显示游戏下拉列表查询条件
 */
$(function() {
    /**
     * 初始化该页面的相关游戏下拉列表数据
     */
    getGameInfoList("#bind_game_id");
    //$(function() {   
    //添加游戏信息方法
    $("#add_button").click(function() {
        var Mon = $("#Mon")[0].checked === true ? "1" : "0";
        var Tues = $("#Tues")[0].checked === true ? "1" : "0";
        var Wednes = $("#Wednes")[0].checked === true ? "1" : "0";
        var Thurs = $("#Thurs")[0].checked === true ? "1" : "0";
        var Fri = $("#Fri")[0].checked === true ? "1" : "0";
        var Satur = $("#Satur")[0].checked === true ? "1" : "0";
        var Sun = $("#Sun")[0].checked === true ? "1" : "0";
        var draw_period = Mon + Tues + Wednes + Thurs + Fri + Satur + Sun;
        //验证输入表单数据
        if (!checkParams()) {
            return false;
        }
        $.ajax({
            cache: true,
            type: "POST",
            url: contextPath + "/gameinfo/add?draw_period="+draw_period,
            data: $('#gameInfoAddForm').serialize(),
            async: false,
            error: function(request) {
                alert("Connection error");
            },
            success: function(data) {
                if (data.result === "success") {
                    alert(data.msg);
                    window.location.href = contextPath + "/gameinfo/list";
                } else {
                    alert(data.msg);
                }
            }
        });
    });
//    });
});
//验证添加字段
function checkParams() {
    if (!isNull("game_id")) {
        alert("游戏编号不能为空");
        return false;
    }
    if (!isNumber("game_id")) {
        alert("游戏编号必须为数值型");
        return false;
    }
    if (!isNull("game_type")) {
        alert("游戏类型不能为空");
        return  false;
    }
    if (!checkLen("game_type", 4)) {  //YangRong 原来是２
        alert("游戏类型字符长度不能超过2");
        return false;
    }
    if (!isNull("game_name")) {
        alert("游戏名称不能为空");
        return false;
    }
    if (!checkLen("game_name", 20)) {
        alert("游戏名称字符长度不能超过20");
        return false;
    }
    if (!isNull("short_name")) {
        alert("游戏简称不能为空");
        return false;
    }
    if (!checkLen("short_name", 16)) {
        alert("游戏简称字符长度不能超过20");
        return false;
    }
    if (!isNull("game_code")) {
        alert("游戏代码不能为空");
        return false;
    }
    if (!checkLen("game_code", 8)) {
        alert("游戏代码字符长度不能超过20");
        return false;
    }
    if (!isNull("game_code")) {
        alert("游戏代码不能为空");
        return false;
    }
    if (!checkLen("game_code", 8)) {
        alert("游戏代码字符长度不能超过20");
        return false;
    }
    if (!isNull("play_num")) {
        alert("玩法个数不能为空");
        return false;
    }
    if (!isNumber("play_num")) {
        alert("玩法个数必须为数值型");
        return false;
    }
    if (!isNull("draw_time")) {
        alert("期结束时间不能为空");
        return false;
    }
    if (!isNull("begin_time")) {
        alert("日销售开始时间不能为空");
        return false;
    }
    if (!isNull("end_time")) {
        alert("日销售结束时间不能为空");
        return false;
    }
    if (!isNull("open_min_no")) {
        alert("开奖最小号码不能为空");
        return false;
    }
    if (!isNumber("open_min_no")) {
        alert("开奖最小号码必须为数值型");
        return false;
    }
    if (!isNull("open_max_no")) {
        alert("开奖最大号码不能为空");
        return false;
    }
    if (!isNumber("open_max_no")) {
        alert("开奖最大号码必须为数值型");
        return false;
    }
    if (!isNull("open_min_blue_no")) {
        alert("二区开奖最小号码不能为空");
        return false;
    }
    if (!isNumber("open_min_blue_no")) {
        alert("二区开奖最小号码必须为数值型");
        return false;
    }
    if (!isNull("open_max_blue_no")) {
        alert("二区开奖最大号码不能为空");
        return false;
    }
    if (!isNumber("open_max_blue_no")) {
        alert("二区开奖最大号码必须为数值型");
        return false;
    }
    if (!isNull("open_basic_num")) {
        alert("开奖基本号码个数不能为空");
        return false;
    }
    if (!isNumber("open_basic_num")) {
        alert("开奖基本号码个数必须为数值型");
        return false;
    }
    if (!isNull("open_special_num")) {
        alert("开奖特别号码个数不能为空");
        return false;
    }
    if (!isNumber("open_special_num")) {
        alert("开奖特别号码个数必须为数值型");
        return false;
    }
    if (!isNull("open_blue_num")) {
        alert("开奖蓝球号码个数不能为空");
        return false;
    }
    if (!isNumber("open_blue_num")) {
        alert("开奖蓝球号码个数必须为数值型");
        return false;
    }
    if (!isNull("lucky_no_group")) {
        alert("开奖号码组数不能为空");
        return false;
    }
    if (!isNumber("lucky_no_group")) {
        alert("开奖号码组数必须为数值型");
        return false;
    }
    if (!isNull("open_num")) {
        alert("最大开奖次数不能为空");
        return false;
    }
    if (!isNumber("open_num")) {
        alert("最大开奖次数必须为数值型");
        return false;
    }
    if (!isNull("prize_class_number")) {
        alert("奖级个数不能为空");
        return false;
    }
    if (!isNumber("prize_class_number")) {
        alert("奖级个数必须为数值型");
        return false;
    }
    if (!isNull("fix_prize_class_number")) {
        alert("固定奖级个数不能为空");
        return false;
    }
    if (!isNumber("fix_prize_class_number")) {
        alert("固定奖级个数必须为数值型");
        return false;
    }
    if (!isNull("center_max_cash_class")) {
        alert("一级中心最大可兑奖级别不能为空");
        return false;
    }
    if (!isNumber("center_max_cash_class")) {
        alert("一级中心最大可兑奖级别必须为数值型");
        return false;
    }
    if (!isNull("center_max_cash_money")) {
        alert("一级中心兑奖上限金额(不含)不能为空");
        return false;
    }
    if (!isNumber("center_max_cash_money")) {
        alert("一级中心兑奖上限金额(不含)必须为数值型");
        return false;
    }
    if (!isNull("department_max_cash_class")) {
        alert("二级中心最大可兑奖级别不能为空");
        return false;
    }
    if (!isNumber("department_max_cash_class")) {
        alert("二级中心最大可兑奖级别必须为数值型");
        return false;
    }
    if (!isNull("department_max_cash_money")) {
        alert("二级中心兑奖上限金额(不含)不能为空");
        return false;
    }
    if (!isNumber("department_max_cash_money")) {
        alert("二级中心兑奖上限金额(不含)必须为数值型");
        return false;
    }
    if (!isNull("terminal_max_cash_class")) {
        alert("投注机最大可兑奖级别不能为空");
        return false;
    }
    if (!isNumber("terminal_max_cash_class")) {
        alert("投注机最大可兑奖级别必须为数值型");
        return false;
    }
    if (!isNull("terminal_max_cash_money")) {
        alert("投注机兑奖上限金额(不含)不能为空");
        return false;
    }
    if (!isNumber("terminal_max_cash_money")) {
        alert("投注机兑奖上限金额(不含)必须为数值型");
        return false;
    }
    if (!isNull("cur_draw_id")) {
        alert("当前期号不能为空");
        return false;
    }
    if (!isNumber("cur_draw_id")) {
        alert("当前期号必须为数值型");
        return false;
    }
    if (!isNull("draw_period")) {
        alert("每期开奖日不能为空");
        return false;
    }
    if (!checkLen("draw_period", 32)) {
        alert("每期开奖日字符长度不能超过32");
        return false;
    }
    if (!isNull("cash_period_day")) {
        alert("兑奖期天数不能为空");
        return false;
    }
    if (!isNumber("cash_period_day")) {
        alert("兑奖期天数必须为数值型");
        return false;
    }
    if (!isNull("multi_draw_number")) {
        alert("多期期数不能为空");
        return false;
    }
    if (!isNumber("multi_draw_number")) {
        alert("多期期数必须为数值型");
        return false;
    }
    if (!isNull("used_mark")) {
        alert("游戏在用标志不能为空");
        return false;
    }
    if (!isNumber("used_mark")) {
        alert("游戏在用标志必须为数值型");
        return false;
    }
    if (!isNull("undo_permit")) {
        alert("注销许可标志不能为空");
        return false;
    }
    if (!isNumber("undo_permit")) {
        alert("注销许可标志必须为数值型");
        return false;
    }
    if (!isNull("sale_mark")) {
        alert("销售许可标志不能为空");
        return false;
    }
    if (!isNumber("sale_mark")) {
        alert("销售许可标志必须为数值型");
        return false;
    }
    if (!isNull("cash_mark")) {
        alert("兑奖许可标志不能为空");
        return false;
    }
    if (!isNumber("cash_mark")) {
        alert("兑奖许可标志必须为数值型");
        return false;
    }
    if (!isNull("data_save_day")) {
        alert("过期中奖数据保存时间不能为空");
        return false;
    }
    if (!isNumber("data_save_day")) {
        alert("过期中奖数据保存时间必须为数值型");
        return false;
    }
    if (!isNull("game_version")) {
        alert("本游戏系统版本号不能为空");
        return false;
    }
    if (!checkLen("game_version", 10)) {
        alert("本游戏系统版本号字符长度不能超过10");
        return false;
    }
    if (!isNull("terminal_bet_money")) {
        alert("投注机单票默认最大金额不能为空");
        return false;
    }
    if (!isNumber("terminal_bet_money")) {
        alert("投注机单票默认最大金额必须为数值型");
        return false;
    }
    if (!isNull("game_control_type")) {
        alert("游戏风险控制方式不能为空");
        return false;
    }
    if (!isNumber("game_control_type")) {
        alert("游戏风险控制方式必须为数值型");
        return false;
    }
    if (!isNull("control_group_num")) {
        alert("风险控制组数不能为空");
        return false;
    }
    if (!isNumber("control_group_num")) {
        alert("风险控制组数必须为数值型");
        return false;
    }
    if (!isNull("bind_game_id")) {
        alert("相关游戏不能为空");
        return false;
    }
    if (!isNull("prize_precision")) {
        alert("派奖奖金精度不能为空");
        return false;
    }
    if (!isNumber("prize_precision")) {
        alert("派奖奖金精度必须为数值型");
        return false;
    }
    if (!isNull("keno_draw_num")) {
        alert("keno期数不能为空");
        return false;
    }
    if (!isNull("draw_length")) {
        alert("每期销售时间长度不能为空");
        return false;
    }
    if (!isNumber("draw_length")) {
        alert("每期销售时间长度必须为数值型");
        return false;
    }
    if (!isNull("multi_keno_num")) {
        alert("KENO销售多期期数不能为空");
        return false;
    }
    if (!isNumber("multi_keno_num")) {
        alert("KENO销售多期期数必须为数值型");
        return false;
    }
    if (!isNull("next_draw_time")) {
        alert("下期开始时间不能为空");
        return false;
    }
    if (!isNumber("next_draw_time")) {
        alert("下期开始时间必须为数值型");
        return false;
    }
    if (!isNull("bulletin_time")) {
        alert("发布公告时间不能为空");
        return false;
    }
    if (!isNumber("bulletin_time")) {
        alert("发布公告时间必须为数值型");
        return false;
    }
    if (!isNull("re_bulletin_time")) {
        alert("公告间隔不能为空");
        return false;
    }
    if (!isNumber("re_bulletin_time")) {
        alert("公告间隔必须为数值型");
        return false;
    }
    return true;
}
