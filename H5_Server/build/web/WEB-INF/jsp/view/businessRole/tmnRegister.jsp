<%-- 
    Document   : tmnRegister
    Created on : 2014-7-25, 10:41:24
    Author     : chenliping
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>终端注册</title>
        <script type="text/javascript" src="<%=request.getContextPath()%>/js/common/jquery-1.8.3.min.js"></script>
        <script type="text/javascript">
            $(function() {
                $("#TmninfoButton").click(function() {
                    $.ajax({
                        type: "POST",
                        url: "<%=request.getContextPath()%>/tmninfo/add",
                        data: $('#TmninfoForm').serialize(),
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
        <form id="TmninfoForm"  method="post">
            投注机号：<input type="text" name="terminal_id"/><br> 
            物理卡号：<input type="text" name="terminal_phy_id"/><br>    
            加密芯片：<input type="text" name="safe_card_id"/><br> 
            地市编号：<input type="text" name="city_id"/><br> 
            所属区县：<input type="text" name="district_id"/><br>             
            站点名称：<input type="text" name="station_name"/><br> 
            站点地址：<input type="text" name="terminal_address"/><br> 
            站点电话：<input type="text" name="station_phone"/><br> 
            户主名：<input type="text" name="owner_name"/><br> 
            负责人电话：<input type="text" name="owner_phone"/><br> 
            联系人：<input type="text" name="linkman"/><br> 
            联系人电话：<input type="text" name="linkman_phone"/><br> 
            软件类型：<input type="text" name="software_id"/><br> 
            升级标记：<input type="text" name="upgrade_mark"/><br>
            软件版本：<input type="text" name="software_version"/><br>
            终端类型：<input type="text" name="terminal_type"/><br>
            终端状态：<input type="text" name="terminal_status"/><br>
            代销费内扣方式：<input type="text" name="abstract_type"/><br>
            销售注销实时扣款：<input type="text" name="tmn_sale_deduct"/><br>
            兑奖实时扣款：<input type="text" name="tmn_cash_deduct"/><br>
            通讯方式：<input type="text" name="comm_type"/><br>
            拨号帐户：<input type="text" name="dial_name"/><br>
            拨号密码：<input type="text" name="dial_pwd"/><br>
            默认扣款帐户：<input type="text" name="account_id"/><br>
            代销商编号：<input type="text" name="dealer_id"/><br>
            <input type="button" id="TmninfoButton" value="增加"/>
        </form>
    </body>
</html>
