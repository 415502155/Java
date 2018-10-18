<%-- 
    Document   : gameLuckyRule
    Created on : 2014-7-19, 17:17:17
    Author     : chenliping
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>游戏抽奖规则增加</title>
        <script type="text/javascript" src="<%=request.getContextPath()%>/js/common/jquery-1.8.3.min.js"></script>
        <script type="text/javascript">
            $(function() {
                $("#gameLuckyRuleButton").click(function() {                    
                    $.ajax({
                        type: "POST",
                        url: "<%=request.getContextPath()%>/gameluckyRule/add",
                        data: $('#gameLuckyRuleForm').serialize(),
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
        <form id="gameLuckyRuleForm"  method="post">
            规则编号：<input type="text" name="rule_id"/><br> 
            游戏名称：<input type="text" name="game_id"/><br>    
            开奖次数：<input type="text" name="open_id"/><br> 
            玩法名：<input type="text" name="play_id"/><br> 
            奖级编号：<input type="text" name="class_id"/><br>             
            基本号码匹配数量：<input type="text" name="basic_num"/><br> 
            特别号码匹配数量：<input type="text" name="special_num"/><br> 
            二区蓝球匹配数量：<input type="text" name="blue_num"/><br> 
            号码有序：<input type="text" name="no_order"/><br> 
            匹配位置：<input type="text" name="match_pos"/><br> 
            匹配相邻：<input type="text" name="match_near"/><br> 
            抽奖方法：<input type="text" name="raffle_method"/><br> 
            状态：<input type="text" name="work_status"/><br> 
            <input type="button" id="gameLuckyRuleButton" value="增加"/>
        </form>
    </body>
</html>
