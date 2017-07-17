/**
 * 终端特权修改
 */
$(function() {
    //    $('#tmnPrivilegeForm').bootstrapValidator({
//        message: 'This value is not valid',
//        feedbackIcons: {
//            valid: 'glyphicon glyphicon-ok',
//            invalid: 'glyphicon glyphicon-remove',
//            validating: 'glyphicon glyphicon-refresh'
//        },
//        fields: {
//            terminal_serial_no: {
//                validators: {
//                    notEmpty: {
//                        message: '投注机序号不能为空'
//                    }
//                }
//            }
//        }
//    });

    /**
     * 修改
     */
    $("#edit_button").bind("click", function() {
        var tmnPrivilegeArr = [];
        var terminalId = -1;
        $("#tbody").find("tr").each(function(i, o) {
            terminalId = $(o).find("th:first").find("input[name='terminalId']").val();
//            alert("terminalId: "+terminalId);
            var isChecked = $(o).find("th:first").find("input[name='cks']").get(0).checked;
//            alert("isChecked: "+isChecked);
            if (isChecked) {
                var game_id = $(o).find("input[name='game_id']").val();
                var sale_permit = $(o).find("select[name='sale_permit']").val();
                var cash_permit = $(o).find("select[name='cash_permit']").val();
                var game_permit = $(o).find("select[name='game_permit']").val();
                var undo_permit = $(o).find("select[name='undo_permit']").val();
                var presell_permit = $(o).find("select[name='presell_permit']").val();
                var agent_fee_rate = $(o).find("input[name='agent_fee_rate']").val();
                var cur_agent_rate = $(o).find("input[name='cur_agent_rate']").val();
                var min_bet_money = $(o).find("input[name='min_bet_money']").val();
                var max_bet_money = $(o).find("input[name='max_bet_money']").val();
                var max_sales_money = $(o).find("input[name='max_sales_money']").val();
                var cash_fee_rate = $(o).find("input[name='cash_fee_rate']").val();
                tmnPrivilegeArr.push({"terminalId": terminalId, "game_id": game_id, "sale_permit": sale_permit, "cash_permit": cash_permit,
                    "game_permit": game_permit, "undo_permit": undo_permit, "presell_permit": presell_permit,
                    "agent_fee_rate": agent_fee_rate, "cur_agent_rate": cur_agent_rate, "min_bet_money": min_bet_money,
                    "max_bet_money": max_bet_money, "max_sales_money": max_sales_money, "cash_fee_rate": cash_fee_rate
                });
            }
        });

//        alert(tmnPrivilegeArr.length+"-------------");
        var tmnPrivilegeStr = JSON.stringify(tmnPrivilegeArr);
        alert("tmnPrivilegeStr: "+tmnPrivilegeStr);
        $.ajax({
            type: "POST",
            url: contextPath + "/tmninfo/modifyTmnPrivilege",
            data: {"tmnPrivilegeStr": tmnPrivilegeStr, "terminalId": terminalId},
            success: function(data) {
                alert(data.result);
                if ("修改成功" == data.result) {
                    window.location.href = contextPath + "/tmninfo/selectAll";
                }
            },
            error: function(e) {
                alert("网络异常，请稍后重试！");
            }
        });
    });
});

//        var dealerPrivilegeArr = [];
//        $("#gameInfoCkxs").find("input:checkbox:checked").each(function(i, o) {
//            var game_id = (o.id.split("_"))[1];
//            var service_proportion = $(this).parent().parent().next().val().trim();
//            dealerPrivilegeArr.push({"game_id": game_id, "service_proportion": service_proportion});
//        });
//        var dealerPrivilegeStr = JSON.stringify(dealerPrivilegeArr);
////                    alert("dealerPrivilegeStr: "+dealerPrivilegeStr);
//        $.ajax({
//            type: "POST",
//            url: contextPath + "/dealerInfo/add?dealerPrivilegeStr=" + dealerPrivilegeStr,
//            data: $("#DealerinfoForm").serialize(),
//            success: function(data) {
//                alert(data.result);
//                if ("注册成功" == data.result) {
//                    window.location.reload();
//                }
//            },
//            error: function(e) {
//                alert("网络异常，请稍后重试！");
//            }
//        });
//    });


