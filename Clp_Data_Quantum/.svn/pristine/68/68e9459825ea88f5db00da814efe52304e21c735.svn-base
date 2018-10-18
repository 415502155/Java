/**
 * 游戏信息显示游戏下拉列表查询条件
 */
$(function() {
    /**
     * 初始化该页面的相关游戏下拉列表数据
     */
    //获取游戏名称的下拉列表数据
    getGameInfoList("#game_id");
    //添加
    $("#add_button").click(function(){
        $.ajax({
            type: "POST",
            url:contextPath+"/gameMultiOpen/add" , 
            data:$("#gameMultiOpenAddForm").serialize(),
            error:function(){
                alert("添加失败");
            },
            success:function(data){
               if (data.result === "success") {
                        alert(data.msg);
                        window.location.href =contextPath+ "/gameMultiOpen/2select";
                    } else {
                        alert(data.msg);
                    }
            }
        });
    });
    //表单验证
      $('#gameMultiOpenAddForm').bootstrapValidator({
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
                        message: '游戏名称不能为空'
                    },
                    numeric: {
                        message: '游戏名称为数值型'
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
            open_name: {
                validators: {
                    notEmpty: {
                        message: '开奖名称不能为空'
                    },
                    stringLength: {
                        message: '开奖名称长度不能超过8',
                        min: '0',
                        max: '8'
                    }
                }
            },
            basic_ball_num: {
                validators: {
                    notEmpty: {
                        message: '开奖基本号码个数不能为空'
                    },
                    numeric: {
                        message: '奖开奖基本号码个数为数值型'
                    }
                }
            },
            special_ball_num: {
                validators: {
                    notEmpty: {
                        message: '开奖特别号码个数不能为空'
                    },
                    numeric: {
                        message: '开奖特别号码个数为数值型'
                    }
                }
            },
            prize_num: {
                validators: {
                    notEmpty: {
                        message: '中奖注数不能为空'
                    },
                    numeric: {
                        message: '中奖注数为数值型'
                    }
                }
            },
            work_status: {
                validators: {
                    notEmpty: {
                        message: '工作状态不能为空'
                    },
                    numeric: {
                        message: '工作状态为数值型'
                    }
                }
            }
        }
    });
});


