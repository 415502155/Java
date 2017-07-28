/*
 * 抽奖规则修改js区域
 */
$(function() {
    //获取游戏名称下拉列表数据
    getGameInfoList("#game_id");

    //编辑操作
    $("#edit_button").click(function() {
        $.ajax({
            type: "POST",
            url: contextPath + "/gameluckyRule/modify",
            data: $("#gameLuckyRuleModifyForm").serialize(),
            error: function() {
                alert("修改失败");
            },
            success: function(data) {
                alert(data.msg);
                window.location.href = contextPath + "/gameluckyRule/2select";
            }
        });
    });
    $('#gameLuckyRuleModifyForm').bootstrapValidator({
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


