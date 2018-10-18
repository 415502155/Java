<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>用户列表</title>
        <script type="text/javascript" src="<c:url value='/js/common/jquery-1.8.3.min.js'/> "></script>
        <script language="javascript">
            $(function(){
                $("#add_button").click(function(){
                    window.location.href = "<%=request.getContextPath()%>/user/2add";
                });
            });
        </script>
    </head>
    <body>
        <h1>Here are the user list!!!</h1>
        <input type="button" id="add_button" value="新增"/><br>
        <table border="1">
            <c:forEach items="${page.rows}" var="user" >
                <tr>
                    <th>${user.user_name}</th>
                    <th>${user.user_pwd}</th>
                    <th>${user.real_name}</th>
                </tr>
            </c:forEach>
        </table>
    </body>
</html>
