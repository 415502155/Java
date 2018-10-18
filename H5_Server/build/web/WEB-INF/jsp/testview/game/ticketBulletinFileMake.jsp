<%-- 
    Document   : ticketBulletinFileMake
    Created on : 2014-10-14, 20:25:22
    Author     : YangRong
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>彩票公告文件生成</title>

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
                                        <div class="pull-left">彩票公告文件生成</div>
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
                                                    <div class="col-md-offset-2 col-md-5">
                                                        <input type="button" id="ticketBulletinFileMake" class="btn btn-primary active" value="生成彩票公告文件">
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
                
                //生成文件
                $("#ticketBulletinFileMake").click(function() {
                    $.ajax({
                        type: "POST",
                        url: contextPath + "/ticketBulletin/makeFile",
//                          url:contextPath+"/ticketCipher/getCipher?ticketCipher="+"9953B01DEC0938EE6BD445FA",
                        success: function(data) {
//                            alert(data.cipher);
                            alert(data.msg);
//                            if("操作成功" == data.msg){
//                                window.location.reload();
//                            }
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
