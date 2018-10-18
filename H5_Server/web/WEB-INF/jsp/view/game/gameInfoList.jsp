<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>游戏列表</title>
        <script type="text/javascript" src="<c:url value='/js/common/jquery-1.8.3.min.js'/> "></script>
        <script language="javascript">
            $(function() {
                $("#add_button").click(function() {
                    window.location.href = "<%=request.getContextPath()%>/gameinfo/2add";
                });
            });
        </script>
    </head>
    <body>
        <h1>Here are the game list!!!</h1>
        <input type="button" id="add_button" value="新增"/><br>
        <table border="1">
            <tr>
                <th>游戏编号</th>
                <th>游戏类型</th>
                <th>游戏名称</th>
                <th>操作</th>
            </tr>
            <c:forEach items="${page.rows}" var="gameInfo" >
                <tr>
                    <th>${gameInfo.game_id}</th>
                    <th>${gameInfo.game_type}</th>
                    <th>${gameInfo.game_name}</th>
                    <th><a href="<%=request.getContextPath()%>/gameinfo/2modify?game_id=${gameInfo.game_id}">修改</a></th>
                </tr>
            </c:forEach>
        </table>
    </body>
</html>
