<%-- 
    Document   : gameRuleFileMake
    Created on : 2014-9-11, 20:25:22
    Author     : hhhh
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>游戏规则文件生成</title>

        <link href="<%=request.getContextPath()%>/css/style/bootstrap.css" rel="stylesheet">
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
                                        <div class="pull-left">游戏规则文件生成</div>
                                        <div class="widget-icons pull-right">
                                            <a href="#" class="wminimize"><i class="icon-chevron-up"></i></a> 
                                            <a href="#" class="wclose"><i class="icon-remove"></i></a>
                                        </div>
                                        <div class="clearfix"></div>
                                    </div>

                                    <div class="widget-content">
                                        <div class="padd">
                                            <!-- Form starts.  -->
                                            <form class="form-horizontal" role="form" id="gameRuleFileMakeForm">
                                                <div class="form-group">
                                                    <label class="col-md-2 control-label">游戏名</label>
                                                    <div class="col-md-5">
                                                        <select name="game_id" id="game_id" class="form-control">
                                                        </select>
                                                    </div>
                                                </div>
                                                
                                                <div class="form-group">
                                                    <div class="col-md-offset-2 col-md-5">
                                                        <input type="button" id="gameRuleFileMake" class="btn btn-primary active" value="生成游戏规则文件">
                                                        <!--<button type="submit" class="btn btn-primary" id="sysuseradd">Sign up</button>-->
                                                    </div>
                                                </div>
                                            </form>
                                        </div>
                                    </div>
                                    <div class="widget-foot">
                                        <!-- Footer goes here -->
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
        <script type="text/javascript" src="<%=request.getContextPath()%>/js/common/selGameInfoList.js"></script>
        <script type="text/javascript">
            var contextPath = "<%=request.getContextPath()%>";
            $(function() {
                getGameInfoList($("#game_id"));
                
                //生成游戏规则文件
                $("#gameRuleFileMake").click(function() {
                    var gameId = $("#game_id").val();
                    $.ajax({
                        type: "POST",
                        url: contextPath + "/gameRule/makeFile",
                        data: {"gameId": gameId},
                        success: function(data) {
                            alert(data.result);
                            if("操作成功" == data.result){
                                window.location.reload();
                            }
                        },
                        error: function(e) {
                            alert("网络异常，请稍后重试！");
                        }
                    });
                });

            });
        </script>
    </body>
</html>
