<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>玩法列表</title>
        <script type="text/javascript" src="<c:url value='/js/common/jquery-1.8.3.min.js'/> "></script>
        <script language="javascript">
            $(function() {
                $("#add_button").click(function() {
                    window.location.href = "<%=request.getContextPath()%>/gameplayinfo/2add";
                });
            });
        </script>
    </head>
    <body>
        <h1>Here are the game play list!!!</h1>
        <input type="button" id="add_button" value="新增"/><br>
        <table border="1">
            <tr>
                <th>游戏编号</th>
                <th>玩法编号</th>
                <th>玩法名称</th>
                <th>操作</th>
            </tr>
            <c:forEach items="${page.rows}" var="gamePlay" >
                <tr>
                    <th>${gamePlay.game_id}</th>
                    <th>${gamePlay.play_id}</th>
                    <th>${gamePlay.play_name}</th>
                    <th><a href="<%=request.getContextPath()%>/gameplayinfo/2modify?game_id=${gamePlay.game_id}&play_id=${gamePlay.play_id}">修改</a></th>
                </tr>
            </c:forEach>
        </table>
    </body>
</html>
