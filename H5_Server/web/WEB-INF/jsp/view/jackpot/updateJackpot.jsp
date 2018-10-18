<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>修改奖池</title>
        <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no">
        <link href="<%=request.getContextPath()%>/css/style/bootstrap-combined.min.css" rel="stylesheet">
        <script src="<%=request.getContextPath()%>/js/test/jquery-2.0.3.min.js"></script>
        <script>
            $(function() {
                $("#modify_button").click(function() {
                    $.ajax({
                        cache: true,
                        type: "POST",
                        url: "<%=request.getContextPath()%>/jackpot/modify",
                        data: $('#updateJackpotForm').serialize(),
                        async: false,
                        error: function(request) {
                            alert("Connection error");
                        },
                        success: function(data) {
                            alert(data.msg);
                        }
                    });
                });

                $("#show_button").click(function() {
                    var gameId = $("#game_id").val();
                    var drawId = $("#draw_id").val();
                    var playId = $("#play_id").val();
                    window.location.href = "<%=request.getContextPath()%>/jackpot/show?game_id=" + gameId + "&draw_id=" + drawId + "&play_id=" + playId;
                });
            });
        </script>
    </head>
    <body>
        <form id="updateJackpotForm">
            <br/>
            &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;游戏id&nbsp;&nbsp;&nbsp;&nbsp;<input type="text" id="game_id" name="game_id"/><br/>
            &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;期次id&nbsp;&nbsp;&nbsp;&nbsp;<input type="text" id="draw_id" name="draw_id"/><br/>
            &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;玩法id&nbsp;&nbsp;&nbsp;&nbsp;<input type="text" id="play_id" name="play_id" value="0"/><br/>
            &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;期初奖池<input type="text" id="begin_jackpot" name="begin_jackpot" value="0"/><br/>
            &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;期末奖池<input type="text" id="end_jackpot" name="end_jackpot" value="0"/><br/>
            &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;期初基金<input type="text" id="begin_pool" name="begin_pool" value="0"/><br/>
            &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;期末基金<input type="text" id="end_pool" name="end_pool" value="0"/><br/>
            &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<input type="button" id="modify_button" value="修改"/>
            &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<input type="button" id="show_button" value="查看"/><br/>
        </form>
    </body>
</html>
