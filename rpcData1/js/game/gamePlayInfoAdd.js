/**
 * 游戏玩法信息
 */
$(function() {
    //获取下拉列表数据
    getGameInfoList("#game_id");
    // 获取游戏玩法名称下拉列表查询条件
//    $("#game_id").change(function() {
//        var gameId = $("#game_id").val();
//        $.ajax({
//            type: "POST",
//            url: contextPath + "/gameplayinfo/getGamePlayInfoSelect?game_id=" + gameId,
//            error: function() {
//                alert("Connection error");
//            },
//            success: function(data) {
//                $("#play_id").empty();
//                if (data !== null && "" !== data && data.length > 0) {
////                     $("<option value='0' >--请选择玩法--</option>").appendTo($("#play_id"));
//                    for (var i in data) {
//                        $("<option value=" + data[i]["play_id"] + " >" + data[i]["play_name"] + "</option>").appendTo($("#play_id"));
//                    }
//                } else {
//                    alert("该游戏暂无玩法");
//                }
//            }
//        });
//    });

    //添加
    $("#add_button").click(function() {
        $.ajax({
            cache: true,
            type: "POST",
            url: contextPath + "/gameplayinfo/add",
            data: $('#gamePlayInfoAddForm').serialize(),
            async: false,
            error: function(request) {
//                    alert("Connection error");
            },
            success: function(data) {
                if (data.result === "success") {
                    alert(data.msg);
//                    $("#right").load(contextPath + "/gameplayinfo/list?game_id=" + gameid + "&&play_id=" + playid);
                    window.location.href = contextPath + "/gameplayinfo/2select";
                } else {
                    alert(data.msg);
                }
            }
        });
    });

    $('#gamePlayInfoAddForm').bootstrapValidator({
        message: 'This value is not valid',
        feedbackIcons: {
            valid: 'glyphicon glyphicon-ok',
            invalid: 'glyphicon glyphicon-remove',
            validating: 'glyphicon glyphicon-refresh'
        },
        fields: {
            game_id: {
                validators: {
                    notEmpty: {
                        message: '游戏编号不能为空'
                    },
                    numeric: {
                        message: '总期号为数值型'
                    }
                }
            },
            play_id: {
                validators: {
                    notEmpty: {
                        message: '玩法编号不能为空'
                    },
                    numeric: {
                        message: '总期号为数值型'
                    }
                }
            },
            draw_id: {
                validators: {
                    notEmpty: {
                        message: '总期号不能为空'
                    },
                    numeric: {
                        message: '总期号为数值型'
                    }
                }
            },
            play_name: {
                validators: {
                    notEmpty: {
                        message: '玩法名称不能为空'
                    },
                    stringLength: {
                        message: '玩法名称长度为8',
                        min: '0',
                        max: '8'
                    }
                }
            },
            play_type: {
                validators: {
                    notEmpty: {
                        message: '玩法类型不能为空'
                    }
                }
            },
            stakes_price: {
                validators: {
                    notEmpty: {
                        message: '单注金额不能为空'
                    },
                    numeric: {
                        message: '单注金额为数值型'
                    }
                }
            },
            max_multiple: {
                validators: {
                    notEmpty: {
                        message: '单注最大倍数不能为空'
                    },
                    numeric: {
                        message: '单注最大倍数为数值型'
                    }
                }
            },
            bet_no_num: {
                validators: {
                    notEmpty: {
                        message: '投注号码个数不能为空'
                    },
                    numeric: {
                        message: '投注号码个数为数值型'
                    }
                }
            },
            bet_min_no: {
                validators: {
                    notEmpty: {
                        message: '最小号码不能为空'
                    },
                    numeric: {
                        message: '最小号码为数值型'
                    }
                }
            },
            bet_max_no: {
                validators: {
                    notEmpty: {
                        message: '最大号码不能为空'
                    },
                    numeric: {
                        message: '最大号码为数值型'
                    }
                }
            },
            blue_no_num: {
                validators: {
                    notEmpty: {
                        message: '蓝球个数不能为空'
                    },
                    numeric: {
                        message: '蓝球个数为数值型'
                    }
                }
            },
            blue_min_no: {
                validators: {
                    notEmpty: {
                        message: '最小蓝球不能为空'
                    },
                    numeric: {
                        message: '最小蓝球为数值型'
                    }
                }
            },
            blue_max_no: {
                validators: {
                    notEmpty: {
                        message: '最大蓝球不能为空'
                    },
                    numeric: {
                        message: '最大蓝球为数值型'
                    }
                }
            },
            no_repeat: {
                validators: {
                    notEmpty: {
                        message: '号码可重复不能为空'
                    },
                    numeric: {
                        message: '号码可重复为数值型'
                    }
                }
            },
            sign_num: {
                validators: {
                    notEmpty: {
                        message: '符号个数不能为空'
                    },
                    numeric: {
                        message: '符号个数为数值型'
                    }
                }
            },
            prize_proportion: {
                validators: {
                    notEmpty: {
                        message: '奖金和比例不能为空'
                    },
                    numeric: {
                        message: '奖金和比例为数值型'
                    }
                }
            }
        }
    });
});

