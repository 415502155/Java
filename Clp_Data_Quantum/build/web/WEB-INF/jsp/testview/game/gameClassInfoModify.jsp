<%-- 
    Document   : gameClassInfoModify
    Created on : 2014-8-8, 14:15:37
    Author     : YangRong 
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no">
        <title>游戏奖级信息修改</title>
        <link href="<%=request.getContextPath()%>/css/style/bootstrap.css" rel="stylesheet">
        <link href="<%=request.getContextPath()%>/css/style/bootstrapValidator.css" rel="stylesheet">
        <link href="<%=request.getContextPath()%>/css/style/style.css" rel="stylesheet"> 
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
                                        <div class="pull-left">游戏奖级信息修改</div>
                                        <div class="widget-icons pull-right">
                                            <a href="#" class="wminimize"><i class="icon-chevron-up"></i></a> 
                                            <a href="#" class="wclose"><i class="icon-remove"></i></a>
                                        </div>
                                        <div class="clearfix"></div>
                                    </div>

                                    <div class="widget-content">
                                        <div class="padd">
                                            <form id="GameClassInfoForm" method="post" action="" role="form" class="form-horizontal">
                                                <!--<fieldset>-->
                                                <!--<legend>游戏奖级信息修改</legend>-->
                                                <div class="form-group">
                                                    <label for="game_id" class="control-label col-md-2">游戏编号</label>
                                                    <div class="col-md-5">
                                                        <input type="text" class="form-control" name="game_id" id="game_id" value="${gameClassInfo.game_id}" readonly/>
                                                    </div>
                                                </div>
                                                <div class="form-group">
                                                    <label for="game_name" class="control-label col-md-2">游戏名称</label>
                                                    <div class="col-md-5">
                                                        <input type="text" class="form-control" name="game_name" id="game_name" value="${gameName}" readonly/>
                                                    </div>
                                                </div>
                                                <div class="form-group">
                                                    <label for="play_id" class="control-label col-md-2">玩法编号</label>
                                                    <div class="col-md-5">
                                                        <input type="text" class="form-control" name="play_id" id="play_id" value="${gameClassInfo.play_id}" readonly/>
                                                    </div>
                                                </div>
                                                <div class="form-group">
                                                    <label for="gameplay_name" class="control-label col-md-2">玩法名称</label>
                                                    <div class="col-md-5">
                                                        <input type="text" class="form-control" name="gameplay_name" id="gameplay_name" value="${gamePlayName}" readonly/>
                                                    </div>
                                                </div>
                                                <div class="form-group">
                                                    <label for="class_id" class="control-label col-md-2">奖级编号</label>
                                                    <div class="col-md-5">
                                                        <input type="text" class="form-control" name="class_id" id="class_id" value="${gameClassInfo.class_id}" readonly />
                                                    </div>
                                                </div>
                                                <div class="form-group">
                                                    <label for="class_name" class="control-label col-md-2">奖级名称</label>
                                                    <div class="col-md-5">
                                                        <input type="text" class="form-control" name="class_name" id="class_name" value="${gameClassInfo.class_name}" />
                                                    </div>
                                                </div>
                                                <div class="form-group">
                                                    <label for="fix_mark" class="control-label col-md-2">固定奖级</label>
                                                    <div class="col-md-5">
                                                        <input type="number" class="form-control" name="fix_mark" id="fix_mark" value="${gameClassInfo.fix_mark}" />
                                                    </div>
                                                </div>
                                                <div class="form-group">
                                                    <label for="prize_proportion" class="control-label col-md-2">奖金和比例</label>
                                                    <div class="col-md-5">
                                                        <input type="number" class="form-control" name="prize_proportion" id="prize_proportion" value="${gameClassInfo.prize_proportion}" />
                                                    </div>
                                                </div>
                                                <div class="form-group">
                                                    <label for="last_relation" class="control-label col-md-2">与下级关系</label>
                                                    <div class="col-md-5">
                                                        <input type="number" class="form-control" name="last_relation" id="last_relation" value="${gameClassInfo.last_relation}" />
                                                    </div>
                                                </div>
                                                <div class="form-group">
                                                    <label for="last_diff" class="control-label col-md-2">差额或倍数</label>
                                                    <div class="col-md-5">
                                                        <input type="number" class="form-control" name="last_diff" id="last_diff" value="${gameClassInfo.last_diff}" />
                                                    </div>
                                                </div>
                                                <div class="form-group">
                                                    <label for="class_status" class="control-label col-md-2">奖级状态</label>
                                                    <div class="col-md-5">
                                                        <input type="number" class="form-control" name="class_status" id="class_status" value="${gameClassInfo.class_status}" />
                                                    </div>
                                                </div>
                                                <div class="form-group">
                                                    <label for="top_money" class="control-label col-md-2">封顶金额</label>
                                                    <div class="col-md-5">
                                                        <input type="number" class="form-control" name="top_money" id="top_money" value="${gameClassInfo.top_money}" />
                                                    </div>
                                                </div>
                                                <div class="form-group">
                                                    <label for="open_id" class="control-label col-md-2">开奖次数</label>
                                                    <div class="col-md-5">
                                                        <input type="text" class="form-control" name="open_id" id="open_id" value="${gameClassInfo.open_id}" />
                                                    </div>
                                                </div>
                                                <hr>
                                                <div class="form-group">
                                                    <div class="col-md-offset-2 col-md-9">
                                                        <input type="button"  onclick="submitGameClassInfo()"  id="GameClassInfoButton1" class="btn btn-primary" value="确认提交">
                                                    </div>
                                                </div>
<!--                                                <div class="text-center">
                                                    <button type="submit" id="GameClassInfoButton" class="btn btn-primary">保 存</button>
                                                    <input type="button" onclick="submitGameClassInfo()"  id="GameClassInfoButton1" class="btn btn-primary" value="保 存">
                                                </div>-->
                                                <!--</fieldset>-->
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
                                                            $(document).ready(function() {

                                                                $('#GameClassInfoForm').bootstrapValidator({
                                                                    message: 'This value is not valid',
                                                                    feedbackIcons: {
                                                                        valid: 'glyphicon glyphicon-ok',
                                                                        invalid: 'glyphicon glyphicon-remove',
                                                                        validating: 'glyphicon glyphicon-refresh'
                                                                    },
                                                                    fields: {
                                                                        class_name: {
                                                                            validators: {
                                                                                notEmpty: {
                                                                                    message: '奖级名称不能为空'
                                                                                }
                                                                            }
                                                                        },
                                                                        fix_mark: {
                                                                            validators: {
                                                                                notEmpty: {
                                                                                    message: '固定奖级不能为空'
                                                                                }
                                                                            }
                                                                        }
                                                                    }
                                                                });
                                                            });
                                                            function submitGameClassInfo() {
                                                                $("#GameClassInfoForm").data('bootstrapValidator').validate();
                                                                if (!($("#GameClassInfoForm").data('bootstrapValidator').isValid()))
                                                                    return;
                                                                if (!confirm("确定保存?"))
                                                                    return;
                                                                $.ajax({
                                                                    type: "POST",
                                                                    url: contextPath + "/gameClassInfo/modifyGameClassInfo",
                                                                    data: $("#GameClassInfoForm").serialize(),
                                                                    success: function(data) {
                                                                        alert(data.msg);
                                                                        if ("success" == data.result) {
                                                                            window.location.reload();
                                                                        }
                                                                    },
                                                                    error: function(e) {
                                                                        alert("网络异常，请稍后重试！");
                                                                    }
                                                                });
                                                            }
        </script>                                                    
    </body>
</html>
