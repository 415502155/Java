/**
 * 游戏信息显示游戏下拉列表查询条件
 */
$(function() {
    //获取游戏名称下拉列表数据
    getGameInfoList("#game_id");
    //根据游戏id获取期信息列表
    setTimeout(function() {
        getGameDrawInfoByGameIdSel($("#game_id").val());
    }, 100);
    $("#game_id").change(function() {
        var gameid = $("#game_id").val();
        getGameDrawInfoByGameIdSel(gameid);
    });
    //添加
    $("#add_button").click(function() {
        $.ajax({
            type: "POST",
            url: contextPath + "/gameFundsPropor/add",
            data: $("#gameFundsProporAddForm").serialize(),
            error: function() {
                alert("添加失败");
            },
            success: function(data) {
                if (data.result === "success") {
                    alert(data.msg);
                    window.location.href = contextPath + "/gameFundsPropor/list";
                } else {
                    alert(data.msg);
                }
            }
        });
    });
    $('#gameLuckyRuleForm').bootstrapValidator({
        message: 'This value is not valid',
        feedbackIcons: {
            valid: 'glyphicon glyphicon-ok',
            invalid: 'glyphicon glyphicon-remove',
            validating: 'glyphicon glyphicon-refresh'
        },
        fields: {
            rule_id: {
                validators: {
                    notEmpty: {
                        message: '规则编号不能为空'
                    },
                    numeric: {
                        message: '规则编号为数值型'
                    }
                }
            },
            open_id: {
                validators: {
                    notEmpty: {
                        message: '开奖次数不能为空'
                    },
                    numeric: {
                        message: '开奖次数为数值型'
                    }
                }
            },
            play_id: {
                validators: {
                    notEmpty: {
                        message: '玩法名不能为空'
                    },
                    numeric: {
                        message: '玩法名为数值型'
                    }
                }
            },
            class_id: {
                validators: {
                    notEmpty: {
                        message: '奖级编号不能为空'
                    },
                    numeric: {
                        message: '奖级编号为数值型'
                    }
                }
            },
            basic_num: {
                validators: {
                    notEmpty: {
                        message: '特别号码匹配数量不能为空'
                    },
                    numeric: {
                        message: '特别号码匹配数量为数值型'
                    }
                }
            },
            blue_num: {
                validators: {
                    notEmpty: {
                        message: '二区蓝球匹配数量不能为空'
                    },
                    numeric: {
                        message: '二区蓝球匹配数量为数值型'
                    }
                }
            },
            no_order: {
                validators: {
                    notEmpty: {
                        message: '号码有序不能为空'
                    },
                    numeric: {
                        message: '号码有序为数值型'
                    }
                }
            },
            match_pos: {
                validators: {
                    notEmpty: {
                        message: '匹配位置不能为空'
                    },
                    numeric: {
                        message: '匹配位置为数值型'
                    }
                }
            },
            raffle_method: {
                validators: {
                    notEmpty: {
                        message: '抽奖方法不能为空'
                    },
                    numeric: {
                        message: '抽奖方法为数值型'
                    }
                }
            }
        }
    });
});

/**
 * 根据游戏id获取期信息下拉列表数据
 */
function getGameDrawInfoByGameIdSel(gameId) {
    $.ajax({
        type: "POST",
        url: contextPath + "/gamedraw/selGameDrawList?game_id=" + gameId,
        error: function() {
            alert("Connection error");
        },
        success: function(data) {
            $("#draw_id").empty();
            if (data !== null && "" !== data && data.length > 0) {
//                     $("<option value='0' >--请选择玩法--</option>").appendTo($("#play_id"));
                for (var i in data) {
                    $("<option value=" + data[i]["draw_id"] + " >" + data[i]["draw_name"] + "</option>").appendTo($("#draw_id"));
                }
            } else {
                //   alert("该游戏暂无玩法");
            }
        }
    });
}

