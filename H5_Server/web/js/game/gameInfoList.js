
$(function() {
  //    初始化该页面的所有下拉列表数据
    getGameInfoList("#gameId");
    //跳转到添加页面
    $("#add_button").click(function() {
        window.location.href = contextPath + "/gameinfo/2add";
    });
    //跳转到编辑页面
    $("#toEdit_button").click(function() {
        var game_id = $("#game_id").val();
        window.location.href = contextPath + "/gameinfo/2modify?game_id=" + game_id;
    });
    //根据游戏名称（游戏编号）查询出游戏
    $("#search_button").click(function() {
        var gameId = $("#gameId").val();
        if (gameId === "-1") {
            window.location.href = contextPath + "/gameinfo/list";
        } else {
            window.location.href = contextPath + "/gameinfo/getGameInfoByidCache?game_id=" + gameId;
        }
    });
    //下一页
    $("#netxtPage").click(function() {
        var pageIndex = (parseInt($.trim($("#pageIndex").val())) + 1);
//        var pageIndex = parseInt($.trim($("#pageIndex").val()));
        window.location.href = contextPath + "/gameinfo/list?pageIndex=" + pageIndex;
    });
    //上一页
    $("#prevPage").click(function() {
        var pageIndex = (parseInt($.trim($("#pageIndex").val())) - 1);
        window.location.href = contextPath + "/gameinfo/list?pageIndex=" + pageIndex;
    });
    //当前页
    $(".page").click(function() {
        var pageIndex = parseInt($(this).text());
        window.location.href = contextPath + "/gameinfo/list?pageIndex=" + pageIndex;
    });
});
