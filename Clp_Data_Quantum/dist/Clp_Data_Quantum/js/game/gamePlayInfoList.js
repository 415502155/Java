
$(function() {
    /**
     * 初始化该页面的所有下拉列表数据
     */
    getGameInfoList("#game_id");
//跳转到添加页面
    $("#add_button").click(function() {
        window.location.href = contextPath + "/gameplayinfo/2add";
    });
    // 获取游戏玩法名称下拉列表查询条件
    $("#game_id").change(function() {
        var gameId = $("#game_id").val();
        $.ajax({
            type: "POST",
            url: contextPath + "/gameplayinfo/getGamePlayInfoSelect?game_id=" + gameId,
            error: function() {
                alert("Connection error");
            },
            success: function(data) {
                  $("#play_id").empty();
                if (data !== null && "" !== data && data.length > 0) {
                    $("<option value='0' >--全部玩法--</option>").appendTo($("#play_id"));
                    for (var i in data) {
                        $("<option value=" + data[i]["play_id"] + " >" + data[i]["play_name"] + "</option>").appendTo($("#play_id"));
                    }
                } else {
//                    alert("无数据");
                }
            }
        });
    });
    //查询游戏玩法游戏列表
    $("#search_button").click(function() {
        var gameid = $.trim($("#game_id").val());
        if (gameid === '-1' || gameid === -1) {
            alert("请选择游戏");
            return;
        }
        var playid = $("#play_id").val();
        window.location.href = contextPath + "/gameplayinfo/list?game_id=" + gameid + "&play_id=" + playid;
    });
});
