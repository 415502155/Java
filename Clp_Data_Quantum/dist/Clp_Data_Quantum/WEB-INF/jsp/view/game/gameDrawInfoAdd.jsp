<%-- 
    Document   : DealerRegister
    Created on : 2014-7-29, 13:36:47
    Author     : chenliping
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title></title>
        <script type="text/javascript" src="<%=request.getContextPath()%>/js/common/jquery-1.8.3.min.js"></script>
        <script type="text/javascript">
            $(function() {
                $("#DealerinfoButton").click(function() {
                    $.ajax({
                        type: "POST",
                        url: "<%=request.getContextPath()%>/gamedraw/modify",
                        data: $('#DealerinfoForm').serialize(),
                        success: function(data) {
                            alert(data.msg);
                        },
                        error: function(request) {
                            alert("Connection error");
                        }
                    });
                });
            });
        </script>     
    </head>
    <body>
        <form id="DealerinfoForm" method="post">
            游戏名称：<input type="text" name="game_id"/><br> 
            代销商名称：<input type="text" name="draw_id"/><br> 
            代销商类型：<input type="text" name="draw_type"/><br> 
            地址：<input type="text" name="process_status"/><br> 
            负责人姓名：<input type="text" name="keno_draw_num"/><br> 
            负责人电话：<input type="text" name="begin_keno_draw_id"/><br> 
            联系人：<input type="text" name="end_keno_draw_id"/><br> 
            联系电话：<input type="text" name="draw_name"/><br> 
            工作电话：<input type="text" name="sales_begin1"/><br> 
            省系统编号：<input type="text" name="sales_end1"/><br> 
            地市编号：<input type="text" name="cash_begin1"/><br> 
            电子邮箱：<input type="text" name="cash_end1"/><br> 
            <input type="button" id="DealerinfoButton" value="增加"/>
        </form>
    </body>
</html>
