<%-- 
    Document   : currentDrawSaleTime
    Created on : 2014-9-11, 10:25:22
    Author     : hhhh
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>当前期销售时间</title>

        <link href="<%=request.getContextPath()%>/css/style/bootstrap.css" rel="stylesheet">
        <link href="<%=request.getContextPath()%>/css/style/bootstrapValidator.css" rel="stylesheet">
        <link rel="stylesheet"  href="<%=request.getContextPath()%>/css/style/bootstrap-datetimepicker.min.css">
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
                                        <div class="pull-left">当前期销售时间</div>
                                        <div class="widget-icons pull-right">
                                            <a href="#" class="wminimize"><i class="icon-chevron-up"></i></a> 
                                            <a href="#" class="wclose"><i class="icon-remove"></i></a>
                                        </div>
                                        <div class="clearfix"></div>
                                    </div>

                                    <div class="widget-content">
                                        <div class="padd">
                                            <!-- Form starts.  -->
                                            <form class="form-horizontal" role="form" id="saleTimeEditForm">
                                                <div class="form-group">
                                                    <label class="col-md-2 control-label">游戏名</label>
                                                    <div class="col-md-5">
                                                        <select name="game_id" id="bind_game_id" class="form-control">
                                                        </select>
                                                        <input type="hidden" class="form-control sr-only" placeholder="" name="draw_id" id="draw_id">
                                                    </div>
                                                </div>
                                                
                                                <div class="form-group">
                                                    <label class="col-md-2 control-label">期名</label>
                                                    <div class="col-md-5">
                                                        <input  type="text" class="form-control" placeholder="" name="draw_name" id="draw_name" readonly>
                                                    </div>
                                                </div>

                                                <div class="form-group">
                                                    <label class="col-md-2 control-label">期开始时间</label>
                                                    <div class="col-md-5">
                                                        <input  type="text" class="form-control" placeholder="" name="begin_time" id="begin_time" readonly>
                                                    </div>
                                                </div>

                                                <div class="form-group">
                                                    <label class="col-md-2 control-label">期结束时间</label>
                                                    <div class="col-md-5 input-append date form_datetime" data-date-format="yyyy-mm-dd hh:ii:ss">
                                                        <input type="text" class="form-control" id="end_time" name="end_time">
                                                        <span class="add-on"><i class="icon-remove"></i></span>
                                                        <span class="add-on"><i class="icon-calendar"></i></span>
                                                    </div>
                                                </div>                
                                                <hr />
                                                <div class="form-group">
                                                    <div class="col-md-offset-2 col-md-5">
                                                        <input type="button" id="saleTimeEdit" class="btn btn-primary active" value="确认提交">
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
        <script type="text/javascript"  src="<%=request.getContextPath()%>/js/test/bootstrap-datetimepicker.min.js" ></script>
        <script type="text/javascript" src="<%=request.getContextPath()%>/js/common/selGameInfoList.js"></script>
        <script type="text/javascript">
            var contextPath = "<%=request.getContextPath()%>";
            $(function() {
                getGameInfoList("#bind_game_id");
                /**
                 * 初始化日期控件
                 */
                $('.form_datetime').datetimepicker({
                    language: 'zh',
                    todayBtn: 1,
                    autoclose: true,
                    todayHighlight: 1,
                    forceParse: 0
                });               

                //根据游戏联动出期开始和结束时间
                $("#bind_game_id").change(function() {
                    var gameId = $(this).val();
                    $.ajax({
                        type: "POST",
                        url: contextPath + "/saleTimeEdit/getTimeByGameId",
                        data: {"gameId": gameId},
                        success: function(data) {
                            if (data != null && "" != data) {
                                $("#draw_id").val(data.draw_id);
                                $("#draw_name").val(data.draw_name);
                                $("#begin_time").val(data.sales_begin_str);
                                $("#end_time").val(data.sales_end_str);
                            }else{
                                $("#draw_id").val("");
                                $("#draw_name").val("");
                                $("#begin_time").val("");
                                $("#end_time").val("");
                                alert("该游戏当前期的期信息不存在");
                            }
                        },
                        error: function(e) {
                            alert("网络异常，请稍后重试！");
                        }
                    });
                });

                $("#saleTimeEdit").click(function() {
                    var gameId = $("#bind_game_id").val();
                    var end_time = $("#end_time").val();
                    $.ajax({
                        type: "POST",
                        url: contextPath + "/saleTimeEdit/updateDrawTime",
                        data: {"gameId": gameId,"end_time":end_time},
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
