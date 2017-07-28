<%-- 
    Document   : dataSyn
    Created on : 2014-9-17, 15:41:24
    Author     : hhhh
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>缓存数据同步</title>
        <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no">
        <!--<link href="<%=request.getContextPath()%>/css/style/bootstrap-combined.min.css" rel="stylesheet">-->
        <link rel="stylesheet" href="<%=request.getContextPath()%>/css/style/bootstrap.css" >
        <link href="<%=request.getContextPath()%>/css/style/bootstrapValidator.css" rel="stylesheet">
        <link rel="stylesheet"  href="<%=request.getContextPath()%>/css/style/style.css" > 
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
                                        <div class="pull-left">缓存数据同步</div>
                                        <div class="widget-icons pull-right">
                                            <a href="#" class="wminimize"><i class="icon-chevron-up"></i></a> 
                                            <a href="#" class="wclose"><i class="icon-remove"></i></a>
                                        </div>
                                        <div class="clearfix"></div>
                                    </div>

                                    <div class="widget-content">
                                        <div class="padd">
                                            <form class="form-horizontal"  role="form"  id="accountInfoForm"  method="post">
                                                <div class="container text-center" >
                                                    <div class="text-center">
                                                        <input type="button" id="dataSyn"  class="btn btn-primary" value="Ehcache数据同步"/>&nbsp;&nbsp;&nbsp;&nbsp;
                                                        <input type="button" id="periodDataSyn"  class="btn btn-primary" value="期数据同步"/>&nbsp;&nbsp;&nbsp;&nbsp;
                                                         <input type="button" id="tmnDataSyn"  class="btn btn-primary" value="终端数据同步"/>
                                                    </div>
                                                </div>
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
        <script type="text/javascript" src="<%=request.getContextPath()%>/js/test/bootstrap.min.js"></script>
        <script type="text/javascript" src="<%=request.getContextPath()%>/js/test/bootstrapValidator.js"></script>
        <script type="text/javascript">
            var contextPath = "<%=request.getContextPath()%>";
            /**
             * 缓存数据同步
             * @type type
             */
            $("#dataSyn").bind("click", function() {
                $.ajax({
                    type: "POST",
                    url: contextPath + "/synehcache/initEhcache",
                    success: function(data) {
                        if(data.failNum > 0){
                            alert("同步失败");
                        }else{
                            alert("同步成功");
                            window.location.reload();
                        }
                    },
                    error: function(e) {
                        alert("网络异常，请稍后重试！");
                    }
                });
            });
            
            /**
             * 期数据同步
             * @type type
             */
            $("#periodDataSyn").bind("click", function() {
                $.ajax({
                    type: "POST",
                    url: contextPath + "/synehcache/initGameDrawInfo",
                    success: function(data) {
                        if(data.failNum < 0){
                            alert("同步失败");
                        }else{
                            alert("同步成功");
                            window.location.reload();
                        }
                    },
                    error: function(e) {
                        alert("网络异常，请稍后重试！");
                    }
                });
            });
            /**
             * 终端数据同步
             * @type type
             */
            $("#tmnDataSyn").bind("click", function() {
                $.ajax({
                    type: "POST",
                    url: contextPath + "/synehcache/initTmnData",
                    success: function(data) {
                        if(data.failNum < 0){
                            alert("同步失败");
                        }else{
                            alert("同步成功");
                            window.location.reload();
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
