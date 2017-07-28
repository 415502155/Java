<%-- 
    Document   : json
    Created on : 2014-7-22, 15:17:58
    Author     : chenliping
--%>
<%--<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>--%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html">
<html>
    <head>
        <base href="${pageContext.request.scheme}://${pageContext.request.serverName}:${pageContext.request.serverPort}${pageContext.request.contextPath}/">
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <script type="text/javascript" src="<%=request.getContextPath()%>/js/common/jquery-1.8.3.min.js"></script>
        <title>添加用户</title>
<!--        <script type="text/javascript">
            $(function() {
                $("form :button").click(function() {
                    var name = $("#name").val();
                    var age = $("#age").val();
                    $.ajax({
                        type: "POST",
                        url: "testUser/addUser",
                        data: {name: name, age: age},
                        success: function(data) {
                            alert("名字:" + data.name + "年龄:" + data.age);
                        }
                    });
                });
            });
        </script>-->
        
        <script type="text/javascript">
            $(function() {
                $("form :button").click(function() {
                    $.ajax({
                        type: "POST",
                        url: "testUser/testJson",
                        success: function(data) {
                            alert("名字:" + data.name + "年龄:" + data.age);
                        }
                    });
                });
            });
        </script>
    </head>
    <body>
        <form  method="post">
            用户名:<input type="text" id="name" name="name" /><br/>
            年龄:<input type="text" id="age" name="age" />
            <input type="button" value="提交" />
        </form>
    </body>
</html>