<%-- 
    Document   : gameDrawInfoAdd
    Created on : 2014-8-5, 14:25:22
    Author     : chenliping
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>期信息修改</title>

        <link href="<%=request.getContextPath()%>/css/style/bootstrap.css" rel="stylesheet">
        <link href="<%=request.getContextPath()%>/css/style/bootstrapValidator.css" rel="stylesheet">
        <link href="<%=request.getContextPath()%>/css/style/bootstrap-combined.min.css" rel="stylesheet">
        <link rel="stylesheet" href="<%=request.getContextPath()%>/css/style/bootstrap-datetimepicker.min.css">
        <link rel="stylesheet"  href="<%=request.getContextPath()%>/css/style/style.css" >  
    </head>
    <body>
        <br>
        <div class="content">
            <!-- Form starts.  -->
            <form class="form-horizontal" role="form" id="drawinfoModifyForm">
                <fieldset>
                    <!--<h4>期信息修改</h4>-->
                    <div class="form-group">
                        <label class="col-lg-1 control-label">游戏名称</label>
                        <div class="col-lg-3">
                            <input  type="text" class="form-control" placeholder="" name="game_id" id="game_id" readonly value="${gameDrawInfo.game_id}">
                            <input  type="text" class="form-control sr-only" placeholder="" name="draw_id"  value="${gameDrawInfo.draw_id}">
                            <input  type="text" class="form-control sr-only" placeholder="" name="draw_type"  value="${gameDrawInfo.draw_type}">
                            <input  type="text" class="form-control sr-only" placeholder="" name="process_status"  value="${gameDrawInfo.process_status}">
                            <input  type="text" class="form-control sr-only" placeholder="" name="keno_draw_num"  value="${gameDrawInfo.keno_draw_num}">
                            <input  type="text" class="form-control sr-only" placeholder="" name="begin_keno_draw_id"  value="${gameDrawInfo.begin_keno_draw_id}">
                            <input  type="text" class="form-control sr-only" placeholder="" name="end_keno_draw_id"  value="${gameDrawInfo.end_keno_draw_id}">
                        </div>
                    </div>

                    <div class="form-group">
                        <label class="col-lg-1 control-label">期名</label>
                        <div class="col-lg-3">
                            <input  type="text" class="form-control" placeholder="" name="draw_name" id="draw_name" readonly value="${gameDrawInfo.draw_name}">
                        </div>
                    </div>

                    <div class="form-group">
                        <label class="col-lg-1 control-label">期销售开始时间</label>
                        <div class="input-append date form_datetime col-lg-3" id="datetimepicker"  data-date-format="yyyy-mm-dd hh:ii:ss">
                            <input class="form-control" size="16" type="text" value="<fmt:formatDate value='${gameDrawInfo.sales_begin}' type="both"/>" name="sales_begin1">
                            <span class="add-on"><i class="icon-remove"></i></span>
                            <span class="add-on"><i class="icon-th"></i></span>
                        </div> 
                    </div>

                    <div class="form-group">
                        <label class="col-lg-1 control-label">期销售结束时间</label>                        
                        <div class="input-append date form_datetime col-lg-3" id="datetimepicker"  data-date-format="yyyy-mm-dd hh:ii:ss">
                            <input class="form-control" size="16" type="text" value="<fmt:formatDate value='${gameDrawInfo.sales_end}' type="both"/>" name="sales_end1">
                            <span class="add-on"><i class="icon-remove"></i></span>
                            <span class="add-on"><i class="icon-th"></i></span>
                        </div> 
                    </div>                

                    <div class="form-group">
                        <label class="col-lg-1 control-label">期兑奖开始时间</label>
                        <div class="input-append date form_datetime col-lg-3" id="datetimepicker"  data-date-format="yyyy-mm-dd hh:ii:ss">
                            <input class="form-control" size="16" type="text" value="<fmt:formatDate value='${gameDrawInfo.cash_begin}' type="both"/>" name="cash_begin1">
                            <span class="add-on"><i class="icon-remove"></i></span>
                            <span class="add-on"><i class="icon-th"></i></span>
                        </div> 
                    </div>    

                    <div class="form-group">
                        <label class="col-lg-1 control-label">期兑奖结束时间</label>
                        <div class="input-append date form_datetime col-lg-3" id="datetimepicker"  data-date-format="yyyy-mm-dd hh:ii:ss">
                            <input class="form-control" size="16" type="text" value="<fmt:formatDate value='${gameDrawInfo.cash_end}' type="both"/>" name="cash_end1">
                            <span class="add-on"><i class="icon-remove"></i></span>
                            <span class="add-on"><i class="icon-th"></i></span>
                        </div> 
                    </div>                                             

                    <div class="form-group">
                        <div class="col-lg-offset-2 col-lg-3">
                            <input type="button" id="drawinfoAdd" class="btn btn-primary active" value="保存修改">
                        </div>
                    </div>
                </fieldset>
            </form>
            <div id="a"></div>
        </div>


        <script src="<%=request.getContextPath()%>/js/test/jquery-1.10.2.min.js" charset="UTF-8"></script>
        <script type="text/javascript" src="<%=request.getContextPath()%>/js/test/bootstrap.min.js"></script>
        <script type="text/javascript" src="<%=request.getContextPath()%>/js/test/bootstrapValidator.js"></script>
        <script src="<%=request.getContextPath()%>/js/test/bootstrap-datetimepicker.min.js" charset="UTF-8"></script>  
        <script type="text/javascript">
            var contextPath = "<%=request.getContextPath()%>";
            $('.form_datetime').datetimepicker({
                language: 'zh',
                todayBtn: 1,
                autoclose: true,
                todayHighlight: 1,
                forceParse: 0
            });

            $(function() {
                $('#drawinfoModifyForm').bootstrapValidator({
                    message: 'This value is not valid',
                    feedbackIcons: {
                        valid: 'glyphicon glyphicon-ok',
                        invalid: 'glyphicon glyphicon-remove',
                        validating: 'glyphicon glyphicon-refresh'
                    },
                    fields: {
                        game_id: {
                            message: '请填写正确的用户名',
                            validators: {
                                notEmpty: {
                                    message: 'The username is required and cannot be empty'
                                }
                            }
                        },
                        draw_id: {
                            validators: {
                                notEmpty: {
                                    message: 'The password is required and cannot be empty'
                                }
                            }
                        },
                        draw_name: {
                            validators: {
                                notEmpty: {
                                    message: 'The confirm password is required and cannot be empty'
                                }
                            }
                        },
                        sales_begin: {
                            validators: {
                                notEmpty: {
                                    message: 'The confirm password is required and cannot be empty'
                                }
                            }
                        },
                        sales_end: {
                            validators: {
                                notEmpty: {
                                    message: 'The confirm password is required and cannot be empty'
                                }
                            }
                        },
                        cash_begin: {
                            validators: {
                                notEmpty: {
                                    message: 'The confirm password is required and cannot be empty'
                                }
                            }
                        },
                        cash_end: {
                            validators: {
                                notEmpty: {
                                    message: 'The confirm password is required and cannot be empty'
                                }
                            }
                        }
                    }
                });

                $("#drawinfoAdd").click(function() {
                    $('#drawinfoModifyForm').bootstrapValidator('validate').on('error.form.bv', function(e) {
                    }).on('success.form.bv', function(e) {
                        $.ajax({
                            type: "POST",
                            url: "<%=request.getContextPath()%>/gamedraw/modify",
                            data: $("#drawinfoModifyForm").serialize(),
                            success: function(data) {
                                alert(data.msg);
                            },
                            error: function(request) {
                                alert("Connection error");
                            }
                        });
                    });
                });
            });
        </script>
    </body>
</html>
