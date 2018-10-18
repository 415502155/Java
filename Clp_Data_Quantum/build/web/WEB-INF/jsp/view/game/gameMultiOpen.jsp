<%-- 
    Document   : gameMultiOpen
    Created on : 2014-7-24, 21:00:01
    Author     : chenliping
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>游戏开奖次数增加</title>
        <script type="text/javascript" src="<%=request.getContextPath()%>/js/common/jquery-1.8.3.min.js"></script>
        <script type="text/javascript">
            $(function() {
                $("#gameMultiOpenButton").click(function() {
                    $.ajax({
                        type: "POST",
                        url: "<%=request.getContextPath()%>/gameMultiOpen/add",
                        data: $('#gameMultiOpenForm').serialize(),
                        success: function(data) {
                            alert(data.result);
                        },
                        error: function(request) {
                            alert("Connection error");
                        }
                    });
                });
            });
        </script>        
    </head>
    <body>
        <form id="gameMultiOpenForm"  method="post">            
            游戏名称：<input type="text" name="game_id"/><br>    
            开奖次数：<input type="text" name="open_id"/><br> 
            开奖名称：<input type="text" name="open_name"/><br> 
            开奖基本号码个数：<input type="text" name="basic_ball_num"/><br>             
            开奖特别号码个数：<input type="text" name="special_ball_num"/><br> 
            中奖注数：<input type="text" name="prize_num"/><br> 
            工作状态：<input type="text" name="work_status"/><br>             
            <input type="button" id="gameMultiOpenButton" value="增加"/>
        </form>
    </body>
</html>
