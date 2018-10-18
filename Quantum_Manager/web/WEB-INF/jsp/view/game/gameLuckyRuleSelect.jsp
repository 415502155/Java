<%-- 
    Document   : gameLuckyRuleSelect
    Created on : 2014-7-20, 10:32:15
    Author     : chenliping
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>游戏抽奖规则查询、修改</title>
    </head>
    <body>
        <form action="<%=request.getContextPath()%>/gameluckyRule/select" method="post">
            游戏名称：<input type="text" name="gameName"/><input type="submit" value="查询"/>
        </form>
        <table border="0">
            <c:forEach items="${gameluckylist}" var="lucky" >
                <tr>
                    <th>${lucky.game_id}</th>
                    <th>${lucky.open_id}</th>
                    <th>${lucky.play_id}</th>
                    <th>${lucky.class_id}</th>
                    <th>${lucky.rule_id}</th>
                    <th>${lucky.basic_num}</th>
                    <th>${lucky.special_num}</th>
                    <th>${lucky.blue_num}</th>
                    <th>${lucky.no_order}</th>
                    <th>${lucky.match_pos}</th>
                    <th>${lucky.match_near}</th>
                    <th>${lucky.raffle_method}</th>
                    <th>${lucky.work_status}</th>
                </tr>
            </c:forEach>
        </table>    
    </body>
</html>
