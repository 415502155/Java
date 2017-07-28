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
        <title>游戏开奖次数描述查询、修改</title>
    </head>
    <body>
        <form action="<%=request.getContextPath()%>/gameMultiOpen/select" method="post">
            游戏名称：<input type="text" name="gameName"/><input type="submit" value="查询"/>
        </form>
        <table border="0">
            <c:forEach items="${gameMultilist}" var="gml" >
                <tr>
                    <th>${gml.game_id}</th>
                    <th>${gml.open_id}</th>
                    <th>${gml.open_name}</th>
                    <th>${gml.basic_ball_num}</th>
                    <th>${gml.special_ball_num}</th>
                    <th>${gml.prize_num}</th>
                    <th>${gml.work_status}</th>
                </tr>
            </c:forEach>
        </table>    
    </body>
</html>
