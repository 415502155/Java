<%-- 
    Document   : tmninfoSelect
    Created on : 2014-7-25, 14:19:42
    Author     : chenliping
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>终端信息查询</title>
    </head>
    <body>
        <form action="<%=request.getContextPath()%>/tmninfo/select" method="post">
            终端机号：<input type="text" name="terid"/><br>
            终端物理机号：<input type="text" name="terphyid"/><br>
            地市编号：<input type="text" name="cityid"/><br>
            站点名称：<input type="text" name="statname"/><br>
            户主名：<input type="text" name="ownname"/><br>
            代销商编号：<input type="text" name="dealid"/><br>
            <input type="submit" value="查询"/>
        </form>
        <table border="0">
            <c:forEach items="${page.rows}" var="gml" >
                <tr>
                    <th>${gml.terminal_id}</th>
                    <th>${gml.terminal_phy_id}</th>
                    <th>${gml.city_id}</th>
                    <th>${gml.district_id}</th>
                    <th>${gml.station_name}</th>
                    <th>${gml.terminal_address}</th>
                    <th>${gml.station_phone}</th>
                </tr>
            </c:forEach>
        </table>    
    </body>
</html>
