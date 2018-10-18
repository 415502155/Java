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
        <title>代销商注册</title>
        <script type="text/javascript" src="<%=request.getContextPath()%>/js/common/jquery-1.8.3.min.js"></script>
        <script type="text/javascript">
            $(function() {
                $("#DealerinfoButton").click(function() {
                    $.ajax({
                        type: "POST",
                        url: "<%=request.getContextPath()%>/dealerInfo/add",
                        data: $('#DealerinfoForm').serialize(),
                        success: function(data) {
                            alert(data.result);
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
            代销商编号：<input type="text" name="dealer_id"/><br> 
            代销商名称：<input type="text" name="dealer_name"/><br> 
            代销商类型：<input type="text" name="dealer_type"/><br> 
            地址：<input type="text" name="dealer_address"/><br> 
            负责人姓名：<input type="text" name="owner_name"/><br> 
            负责人电话：<input type="text" name="owner_phone"/><br> 
            联系人：<input type="text" name="link_name"/><br> 
            联系电话：<input type="text" name="link_phone"/><br> 
            工作电话：<input type="text" name="dealer_phone"/><br> 
            省系统编号：<input type="text" name="province_id"/><br> 
            地市编号：<input type="text" name="city_id"/><br> 
            电子邮箱：<input type="text" name="email"/><br> 
            固定电话：<input type="text" name="phone_no"/><br> 
            证件类型编号：<input type="text" name="id_type_id"/><br> 
            证件编号：<input type="text" name="ID_no"/><br> 
            银行编码：<input type="text" name="bank_id"/><br> 
            账户名称：<input type="text" name="account_name"/><br> 
            银行卡号：<input type="text" name="account_card"/><br> 
            备注：<input type="text" name="note"/><br> 
            终端情况：<input type="text" name="terminal_condition"/><br> 
            <!--注册时间：<input type="text" name="regist_time"/><br>--> 
            使用状态：<input type="text" name="work_status"/><br> 
            <input type="button" id="DealerinfoButton" value="增加"/>
        </form>
    </body>
</html>
