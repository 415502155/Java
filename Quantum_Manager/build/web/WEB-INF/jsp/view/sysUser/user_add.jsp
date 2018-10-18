<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>新增用户</title>
        <script type="text/javascript" src="<c:url value='/js/common/jquery-1.8.3.min.js'/> "></script>
        <script>
            $(function() {
                //点击添加按钮
                $("#add_button").click(function() {
                    $.post("<%=request.getContextPath()%>/user/add", {user_name: $("#user_name").val(), user_pwd: $("#user_pwd").val(), real_name: $("#real_name").val()}, 
                    function(data) {
                        if (data.result === "success") {
                            alert(data.msg);
                            window.location.href = "<%=request.getContextPath()%>/user/list";
                        } else {
                            alert(data.msg);
                        }
                    }, "json");
                });
            });
        </script>
    </head>
    <body>
        <form id="userAddForm" method="post">
            username:<input type="text" id="user_name" name="user_name"/><br/>
            password:<input type="password" id="user_pwd" name="user_pwd"/><br/>
            realname<input type="text" id="real_name" name="real_name"/><br/>
            <input type="button" id="add_button" value="增加"/>
        </form>
    </body>
</html>
