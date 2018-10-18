<%-- 
    Document   : gameFundsProportion
    Created on : 2014-7-25, 9:13:26
    Author     : chenliping
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>游戏资金比例</title>
    </head>
    <body>
        <form action="<%=request.getContextPath()%>/gameFundsPropor/select" method="post">
            游戏名称：<input type="text" name="gameName"/><input type="submit" value="查询"/>
        </form>
            
        
            
    </body>
</html>
