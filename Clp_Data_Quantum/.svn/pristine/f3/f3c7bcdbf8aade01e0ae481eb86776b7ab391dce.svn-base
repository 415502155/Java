<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>修改游戏</title>
        <script type="text/javascript" src="<c:url value='/js/common/jquery-1.8.3.min.js'/> "></script>
        <script>
            $(function() {
                $("#modify_button").click(function() {
                    $.ajax({
                        cache: true,
                        type: "POST",
                        url: "<%=request.getContextPath()%>/gameinfo/modify",
                        data: $('#gameInfoModifyForm').serialize(),
                        async: false,
                        error: function(request) {
                            alert("Connection error");
                        },
                        success: function(data) {
                            if (data.result === "success") {
                                alert(data.msg);
                                window.location.href = "<%=request.getContextPath()%>/gameinfo/list";
                            } else {
                                alert(data.msg);
                            }
                        }
                    });
                });
            });
        </script>
    </head>
    <body>
        <form id="gameInfoModifyForm" method="post">
            游戏编号<input type="text" id="game_id" name="game_id" value="${gameInfo.game_id}"/><br/>
            游戏类型<input type="text" id="game_type" name="game_type" value="${gameInfo.game_type}"/><br/>
            游戏名称<input type="text" id="game_name" name="game_name" value="${gameInfo.game_name}"/><br/>
            游戏简称<input type="text" id="short_name" name="short_name" value="${gameInfo.short_name}"/><br/>
            游戏代码<input type="text" id="game_code" name="game_code" value="${gameInfo.game_code}"/><br/>
            玩法个数<input type="text" id="play_num" name="play_num" value="${gameInfo.play_num}"/><br/>
            开奖号码可重复<input type="text" id="repeat_select" name="repeat_select" value="${gameInfo.repeat_select}"/><br/>
            
            期结束时间<input type="text" id="draw_time" name="draw_time" value="${gameInfo.draw_time}"/><br/>
            自动新期时间<input type="text" id="init_time" name="init_time" value="${gameInfo.init_time}"/><br/>
            自动结算时间<input type="text" id="stat_time" name="stat_time" value="${gameInfo.stat_time}"/><br/>
            日销售开始时间<input type="text" id="begin_time" name="begin_time" value="${gameInfo.begin_time}"/><br/>
            日销售结束时间<input type="text" id="end_time" name="end_time" value="${gameInfo.end_time}"/><br/>
            
            开奖最小号码<input type="text" id="open_min_no" name="open_min_no" value="${gameInfo.open_min_no}"/><br/>
            开奖最大号码<input type="text" id="open_max_no" name="open_max_no" value="${gameInfo.open_max_no}"/><br/>
            二区开奖最小号码<input type="text" id="open_min_blue_no" name="open_min_blue_no" value="${gameInfo.open_min_blue_no}"/><br/>
            二区开奖最大号码<input type="text" id="open_max_blue_no" name="open_max_blue_no" value="${gameInfo.open_max_blue_no}"/><br/>
            开奖基本号码个数<input type="text" id="open_basic_num" name="open_basic_num" value="${gameInfo.open_basic_num}"/><br/>
            开奖特别号码个数<input type="text" id="open_special_num" name="open_special_num" value="${gameInfo.open_special_num}"/><br/>
            开奖蓝球号码个数<input type="text" id="open_blue_num" name="open_blue_num" value="${gameInfo.open_blue_num}"/><br/>
            开奖号码组数<input type="text" id="lucky_no_group" name="lucky_no_group" value="${gameInfo.lucky_no_group}"/><br/>
            最大开奖次数<input type="text" id="open_num" name="open_num" value="${gameInfo.open_num}"/><br/>

            奖级个数<input type="text" id="prize_class_number" name="prize_class_number" value="${gameInfo.prize_class_number}"/><br/>
            固定奖级个数<input type="text" id="fix_prize_class_number" name="fix_prize_class_number" value="${gameInfo.fix_prize_class_number}"/><br/>
            一级中心最大可兑奖级别<input type="text" id="center_max_cash_class" name="center_max_cash_class" value="${gameInfo.center_max_cash_class}"/><br/>
            一级中心兑奖上限金额(不含)<input type="text" id="center_max_cash_money" name="center_max_cash_money" value="${gameInfo.center_max_cash_money}"/><br/>
            二级中心最大可兑奖级别<input type="text" id="department_max_cash_class" name="department_max_cash_class" value="${gameInfo.department_max_cash_class}"/><br/>
            二级中心兑奖上限金额(不含)<input type="text" id="department_max_cash_money" name="department_max_cash_money" value="${gameInfo.department_max_cash_money}"/><br/>
            投注机最大可兑奖级别<input type="text" id="terminal_max_cash_class" name="terminal_max_cash_class" value="${gameInfo.terminal_max_cash_class}"/><br/>
            投注机兑奖上限金额(不含)<input type="text" id="terminal_max_cash_money" name="terminal_max_cash_money" value="${gameInfo.terminal_max_cash_money}"/><br/>
            当前期号<input type="text" id="cur_draw_id" name="cur_draw_id" value="${gameInfo.cur_draw_id}"/><br/>
            开奖频度类型<input type="text" id="draw_period_type" name="draw_period_type" value="${gameInfo.draw_period_type}"/><br/>
            每期开奖日<input type="text" id="draw_period" name="draw_period" value="${gameInfo.draw_period}"/><br/>
            兑奖期天数<input type="text" id="cash_period_day" name="cash_period_day" value="${gameInfo.cash_period_day}"/><br/>
            多期期数<input type="text" id="multi_draw_number" name="multi_draw_number" value="${gameInfo.multi_draw_number}"/><br/>
            联合销售类型<input type="text" id="union_type" name="union_type" value="${gameInfo.union_type}"/><br/>
            游戏在用标志<input type="text" id="used_mark" name="used_mark" value="${gameInfo.used_mark}"/><br/>

            注销许可标志(<input type="text" id="undo_permit" name="undo_permit" value="${gameInfo.undo_permit}"/><br/>
            销售许可标志<input type="text" id="sale_mark" name="sale_mark" value="${gameInfo.sale_mark}"/><br/>
            兑奖许可标志<input type="text" id="cash_mark" name="cash_mark" value="${gameInfo.cash_mark}"/><br/>
            过期中奖数据保存时间<input type="text" id="data_save_day" name="data_save_day" value="${gameInfo.data_save_day}"/><br/>
            本游戏系统版本号<input type="text" id="game_version" name="game_version" value="${gameInfo.game_version}"/><br/>
            投注机单票默认最大金额<input type="text" id="terminal_bet_money" name="terminal_bet_money" value="${gameInfo.terminal_bet_money}"/><br/>
            游戏风险控制方式<input type="text" id="game_control_type" name="game_control_type" value="${gameInfo.game_control_type}"/><br/>
            风险控制组数<input type="text" id="control_group_num" name="control_group_num" value="${gameInfo.control_group_num}"/><br/>
            相关游戏<input type="text" id="bind_game_id" name="bind_game_id" value="${gameInfo.bind_game_id}"/><br/>
            兑奖方式<input type="text" id="cash_method" name="cash_method" value="${gameInfo.cash_method}"/><br/>
            派奖奖金精度<input type="text" id="prize_precision" name="prize_precision" value="${gameInfo.prize_precision}"/><br/>
            快开游戏标记<input type="text" id="keno_game" name="keno_game" value="${gameInfo.keno_game}"/><br/>
            keno期数<input type="text" id="keno_draw_num" name="keno_draw_num" value="${gameInfo.keno_draw_num}"/><br/>
            每期销售时间长度<input type="text" id="draw_length" name="draw_length" value="${gameInfo.draw_length}"/><br/>
            KENO销售多期期数<input type="text" id="multi_keno_num" name="multi_keno_num" value="${gameInfo.multi_keno_num}"/><br/>
            下期开始时间(<input type="text" id="next_draw_time" name="next_draw_time" value="${gameInfo.next_draw_time}"/><br/>
            发布公告时间<input type="text" id="bulletin_time" name="bulletin_time" value="${gameInfo.bulletin_time}"/><br/>
            公告间隔(<input type="text" id="re_bulletin_time" name="re_bulletin_time" value="${gameInfo.re_bulletin_time}"/><br/>

            <input type="button" id="modify_button" value="修改"/>
        </form>
    </body>
</html>
