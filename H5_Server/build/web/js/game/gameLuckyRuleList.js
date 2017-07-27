/**
 * 游戏信息显示游戏下拉列表查询条件
 */
$(function() {
    /**
     * 初始化该页面的所有下拉列表数据
     */
    getGameInfoList("#gameid");
    //查询
    $("#search_button").click(function() {
        var gameid = $.trim($("#gameid").val());
        if(gameid ==='-1' || gameid === -1){
            alert("请选择游戏");
            return;
        }
        window.location.href = contextPath + "/gameluckyRule/list?gameid=" + gameid;
    });
    //跳转到添加页面
    $("#toAdd_button").click(function() {
        window.location.href=contextPath + "/gameluckyRule/2add";
    });
});
