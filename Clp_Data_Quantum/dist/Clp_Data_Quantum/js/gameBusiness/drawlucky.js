
//手工开奖js区域
$(function() {

    //期次状态,全局变量
    var drawStatus = "0";

    //初始化手工开奖页面游戏名称下拉列表数据
    getGameInfoList("#gameid");

    //循环遍历期次状态,根据不同的状态调用不同处理业务步骤
    function loop() {
        while (true) {
            if (drawStatus === "30") {
                alert("期状态：30,下一步---停止销售游戏");
                stopGame();
            }
            if (drawStatus === "110") {
            }
            if (drawStatus === "120") {
                alert("期状态：120,停止销售游戏成功，下一步---终端封机");
                stopTerminal();
            }
            if (drawStatus === "130") {
                alert("期状态：130,终端封机成功，下一步---数据校验");
                dataCheck();
            }
            if (drawStatus === "140") {
            }
            if (drawStatus === "150") {
            }
            if (drawStatus === "160") {
                alert("期状态：160,数据校验成功，下一步---期结算");
                drawStat();
            }
            if (drawStatus === "170") {
            }
            if (drawStatus === "180") {
            }
            if (drawStatus === "190") {
            }
            if (drawStatus === "210") {
                alert("期状态：210,期结算成功，下一步---输入开奖号码");
                window.location.href = contextPath + "/drawlucky/2inputluckyno?gameid=" + $("#gameid").val() + "&drawid=" + $("#drawid").val();
                break;
            }
            if (drawStatus === "220") {
            }
            if (drawStatus === "230") {
                alert("期状态：230,读取号码完成，下一步---抽奖");
                luckyDraw();
            }
            if (drawStatus === "240") {
            }
            if (drawStatus === "250") {
                alert("期状态：250,抽奖完成，下一步---计算奖金");
                window.location.href = contextPath + "/drawlucky/2inputmoney?gameid=" + $("#gameid").val() + "&drawid=" + $("#drawid").val() +
                        "&openid=" + $("#openid").val();
                break;
            }
            if (drawStatus === "260") {
            }
            if (drawStatus === "270") {
            }
            if (drawStatus === "280") {
            }
            if (drawStatus === "290") {
            }
            if (drawStatus === "300") {
                alert("期状态：300,计算奖金完成，下一步---中奖分布");
                luckyDistribute();
            }
            if (drawStatus === "310") {
            }
            if (drawStatus === "320") {
            }
            if (drawStatus === "330") {
            }
            if (drawStatus === "410") {
                alert("期状态：410,中奖分布完成，下一步---中奖数据入库");
                luckyData();
            }
            if (drawStatus === "420") {
            }
            if (drawStatus === "430") {
                alert("期状态：430,公告已发布，开奖结束");
                break;
            }
            if (drawStatus === "error") {
                alert("开奖流程出错");
                break;
            }
        }
    }

    //把期次状态设置为error,退出循环
    function destroyDrawStatus() {
        drawStatus = "error";
    }

    //开始开奖流程
    $("#gameSurebutton").click(function() {
        alert("start draw lucky");
        selectGame();
        loop();
    });

    //选择游戏和期次，得到期次状态
    function selectGame() {
        $.ajax({
            cache: true,
            type: "POST",
            url: contextPath + "/drawlucky/selectGame",
            data: $('#gameSureForm').serialize(),
            async: false,
            error: function(request) {
                destroyDrawStatus();
            },
            success: function(data) {
                if (data.result === "success") {
                    drawStatus = "" + data.status;
                }
                if (data.result === "fail") {
                    destroyDrawStatus();
                    alert(data.msg);
                }
            }
        });
    }

    //停止销售
    function stopGame() {
        $.ajax({
            cache: true,
            type: "POST",
            url: contextPath + "/drawlucky/stopGame",
            data: $('#gameSureForm').serialize(),
            async: false,
            error: function(request) {
                destroyDrawStatus();
            },
            success: function(data) {
                if (data.result === "success") {
                    drawStatus = "" + data.status;
                }
                if (data.result === "fail") {
                    destroyDrawStatus();
                    alert(data.msg);
                }
            }
        });
    }

    //终端封机
    function stopTerminal() {
        $.ajax({
            cache: true,
            type: "POST",
            url: contextPath + "/drawlucky/stoptmn",
            data: $('#gameSureForm').serialize(),
            async: false,
            error: function(request) {
                destroyDrawStatus();
            },
            success: function(data) {
                if (data.result === "success") {
                    drawStatus = "" + data.status;
                }
                if (data.result === "fail") {
                    destroyDrawStatus();
                    alert(data.msg);
                }
            }
        });
    }

    //数据校验
    function dataCheck() {
        $.ajax({
            cache: true,
            type: "POST",
            url: contextPath + "/drawlucky/datacheck",
            data: $('#gameSureForm').serialize(),
            async: false,
            error: function(request) {
                destroyDrawStatus();
            },
            success: function(data) {
                if (data.result === "success") {
                    drawStatus = "" + data.status;
                }
                if (data.result === "fail") {
                    destroyDrawStatus();
                    alert(data.msg);
                }
            }
        });
    }

    //期结算
    function drawStat() {
        $.ajax({
            cache: true,
            type: "POST",
            url: contextPath + "/drawlucky/drawstat",
            data: $('#gameSureForm').serialize(),
            async: false,
            error: function(request) {
                destroyDrawStatus();
            },
            success: function(data) {
                if (data.result === "success") {
                    drawStatus = "" + data.status;
                }
                if (data.result === "fail") {
                    destroyDrawStatus();
                    alert(data.msg);
                }
            }
        });
    }

    //输入开奖号码
    $("#luckyNoInputButton").click(function() {
        alert("after input lucky no");
        inputLuckyNo();
        loop();
    });

    //输入开奖号码
    function inputLuckyNo() {
        $.ajax({
            cache: true,
            type: "POST",
            url: contextPath + "/drawlucky/inputlucky",
            data: $('#luckyNoInputForm').serialize(),
            async: false,
            error: function(request) {
                destroyDrawStatus();
            },
            success: function(data) {
                if (data.result === "success") {
                    drawStatus = "" + data.status;
                }
                if (data.result === "fail") {
                    destroyDrawStatus();
                    alert(data.msg);
                }
            }
        });
    }

    //抽奖
    function luckyDraw() {
        $.ajax({
            cache: true,
            type: "POST",
            url: contextPath + "/drawlucky/lucky",
            data: $('#luckyNoInputForm').serialize(),
            async: false,
            error: function(request) {
                destroyDrawStatus();
            },
            success: function(data) {
                if (data.result === "success") {
                    drawStatus = "" + data.status;
                }
                if (data.result === "fail") {
                    destroyDrawStatus();
                    alert(data.msg);
                }
            }
        });
    }

    //输入奖金
    $("#moneyInputButton").click(function() {
        alert("after input money");
        inputMoney();
        loop();
    });

    //计算奖金
    function inputMoney() {
        $.ajax({
            cache: true,
            type: "POST",
            url: contextPath + "/drawlucky/inputmoney",
            data: $('#moneyInputForm').serialize(),
            async: false,
            error: function(request) {
                destroyDrawStatus();
            },
            success: function(data) {
                if (data.result === "success") {
                    drawStatus = "" + data.status;
                }
                if (data.result === "fail") {
                    destroyDrawStatus();
                    alert(data.msg);
                }
            }
        });
    }

    //中奖分布
    function luckyDistribute() {
        $.ajax({
            cache: true,
            type: "POST",
            url: contextPath + "/drawlucky/luckyDistribution",
            data: $('#moneyInputForm').serialize(),
            async: false,
            error: function(request) {
                destroyDrawStatus();
            },
            success: function(data) {
                if (data.result === "success") {
                    drawStatus = "" + data.status;
                }
                if (data.result === "fail") {
                    destroyDrawStatus();
                    alert(data.msg);
                }
            }
        });
    }

    //中奖数据入库
    function luckyData() {
        $.ajax({
            cache: true,
            type: "POST",
            url: contextPath + "/drawlucky/luckyData",
            data: $('#moneyInputForm').serialize(),
            async: false,
            error: function(request) {
                destroyDrawStatus();
            },
            success: function(data) {
                if (data.result === "success") {
                    drawStatus = "" + data.status;
                }
                if (data.result === "fail") {
                    destroyDrawStatus();
                    alert(data.msg);
                }
            }
        });
    }

    //
    function getDrawStatus() {
        $.ajax({
            cache: true,
            type: "GET",
            url: contextPath + "/drawlucky/drawStatus",
            data: $('#gameSureForm').serialize(),
            async: false,
            error: function(request) {
                destroyDrawStatus();
            },
            success: function(data) {
                return data.result;
            }
        });
    }
});

