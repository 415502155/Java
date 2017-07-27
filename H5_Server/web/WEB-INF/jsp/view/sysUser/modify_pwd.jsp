<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>修改密码</title>
    </head>
    <body>
        <form action="<%=request.getContextPath()%>/user/modifyPwd.htm" method="get">
            username:<input type="text" value="${sessionScope.user.user_name}" name="userName"/><br>
            old_password:<input type="password" name="old_user_pwd"/><br>
            new_password:<input type="password" name="new_user_pwd"/><br>
            <input type="submit"/>
        </form>
    </body>
</html>
