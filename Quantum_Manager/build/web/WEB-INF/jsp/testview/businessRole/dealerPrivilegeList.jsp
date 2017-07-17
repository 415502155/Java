<%-- 
    Document   : dealerPrivilegeList
    Created on : 2014-8-6, 9:00:26
    Author     : hhhh
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no">
        <title>代销商游戏特权展示页面</title>
        <link href="<%=request.getContextPath()%>/css/style/bootstrap.css" rel="stylesheet">
        <link rel="stylesheet"  href="<%=request.getContextPath()%>/css/style/style.css" >
        <style>
            .thClass{
                width:25%
            }
        </style>

    </head>
    <body>
        <div class="content">
            <div class="mainbar">
                <div class="matter">
                    <div class="container">
                        <div class="row">
                            <div class="col-md-12">
                                <div class="widget wgreen">
                                    <div class="widget-head">
                                        <div class="pull-left">代销商游戏特权信息列表</div>
                                        <div class="widget-icons pull-right">
                                            <a href="#" class="wminimize"><i class="icon-chevron-up"></i></a> 
                                            <a href="#" class="wclose"><i class="icon-remove"></i></a>
                                        </div>
                                        <div class="clearfix"></div>
                                    </div>

                                    <div class="widget-content">
                                        <div class="padd">
                                            <form class="form-horizontal"  role="form" >
                                                <table class="table table-hover table-bordered">
                                                    <thead>
                                                        <tr>
                                                            <th class="thClass">代销商编号</th>
                                                            <th class="thClass">游戏名称</th>
                                                            <th class="thClass">代销商缴费</th>
                                                            <th class="thClass">游戏许可</th>
                                                        </tr>
                                                    </thead>
                                                    <tbody id="tbody">

                                                    </tbody>
                                                </table>
                                            </form>
                                        </div>
                                    </div>
                                    <div class="widget-foot">
                                        <!--Footer goes here--> 
                                    </div>
                                </div>  
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <script src="<%=request.getContextPath()%>/js/test/jquery-1.10.2.min.js"></script>
        <script type="text/javascript">
            var contextPath = "<%=request.getContextPath()%>";
            var dealerId = window.dialogArguments;
            $(function() {
                $.ajax({
                    type: "POST",
                    url: contextPath + "/dealerInfo/selectDealerPrivilegeList",
                    data: {"dealerId": dealerId},
                    success: function(data) {
                        $("#tbody").empty();
                        if (data.length > 0) {
                            for (var i in data) {
                                var trObj = "<tr><th class='thClass'>" + data[i].dealer_id + "</th><th class='thClass'>" + data[i].game_name + "</th><th class='thClass'>" + data[i].service_proportion + "</th><th class='thClass'>" + data[i].game_permit + "</th></tr>";
                                $("#tbody").append(trObj);
                            }
                        } else {
                            $("#tbody").append("<tr><th class='thClass' colspan='4'>没有游戏特权信息记录</th></tr>");
                        }

                    },
                    error: function(e) {
                        alert("网络异常，请稍后重试！");
                    }
                });
            });
        </script>
    </body>
</html>
