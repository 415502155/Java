/**
 * 游戏玩法信息
 */
$(function() {
    //编辑功能
    $("#modify_button").click(function() {
        $.ajax({
            cache: true,
            type: "POST",
            url: contextPath + "/gameplayinfo/modify",
            data: $('#gamePlayInfoModifyForm').serialize(),
            async: false,
            error: function(request) {
                alert("Connection error");
            },
            success: function(data) {
                if (data.result === "success") {
                    alert(data.msg);
//              $("#right").load(contextPath + "/gameplayinfo/gamePlayInfoList");      
                    window.location.href = contextPath + "/gameplayinfo/2select";
                } else {
                    alert(data.msg);
                }
            }
        });
    });
});