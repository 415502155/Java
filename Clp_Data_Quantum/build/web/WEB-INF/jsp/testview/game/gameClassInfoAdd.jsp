<%-- 
    Document   : gameClassInfoAdd
    Created on : 2014-8-8, 16:15:20
    Author     : YangRong 
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no">
        <title>游戏奖级信息添加</title>
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
                                        <div class="pull-left">游戏奖级信息添加</div>
                                        <div class="widget-icons pull-right">
                                            <a href="#" class="wminimize"><i class="icon-chevron-up"></i></a> 
                                            <a href="#" class="wclose"><i class="icon-remove"></i></a>
                                        </div>
                                        <div class="clearfix"></div>
                                    </div>

                                    <div class="widget-content">
                                        <div class="padd">
                                            <form id="GameClassInfoAddForm" method="post" action="" role="form" class="form-horizontal">
                                                <!--<fieldset>-->
                                                <!--<legend>游戏奖级信息添加</legend>-->
                                                <div class="form-group">
                                                    <label for="game_id" class="control-label col-md-2">游戏名称</label>
                                                    <div class="col-md-5">
                                                        <select name="game_id" id="game_id" class="form-control" >
                                                        </select>
                                                    </div>
                                                </div>
                                                <div class="form-group">
                                                    <label for="play_id" class="control-label col-md-2">玩法名称</label>
                                                    <div class="col-md-5">
                                                        <select name="play_id" id="play_id" class="form-control">
                                                        </select>
                                                    </div>
                                                </div>
                                                <div class="form-group">
                                                    <label for="class_id" class="control-label col-md-2">奖级编号</label>
                                                    <div class="col-md-5">
                                                        <input type="text" class="form-control" name="class_id" id="class_id"  placeholder="奖级编号" />
                                                    </div>
                                                </div>
                                                <div class="form-group">
                                                    <label for="class_name" class="control-label col-md-2">奖级名称</label>
                                                    <div class="col-md-5">
                                                        <input type="text" class="form-control" name="class_name" id="class_name" placeholder="奖级名称" />
                                                    </div>
                                                </div>
                                                <div class="form-group">
                                                    <label for="fix_mark" class="control-label col-md-2">固定奖级</label>
                                                    <div class="col-md-5">
                                                        <input type="text" class="form-control" name="fix_mark" id="fix_mark"  placeholder="固定奖级" />
                                                    </div>
                                                </div>
                                                <div class="form-group">
                                                    <label for="prize_proportion" class="control-label col-md-2">奖金和比例</label>
                                                    <div class="col-md-5">
                                                        <input type="number" class="form-control" name="prize_proportion" id="prize_proportion"  placeholder="奖金和比例" />
                                                    </div>
                                                </div>
                                                <div class="form-group">
                                                    <label for="last_relation" class="control-label col-md-2">与下级关系</label>
                                                    <div class="col-md-5">
                                                        <input type="number" class="form-control" name="last_relation" id="last_relation"  placeholder="与下级关系" />
                                                    </div>
                                                </div>
                                                <div class="form-group">
                                                    <label for="last_diff" class="control-label col-md-2">差额或倍数</label>
                                                    <div class="col-md-5">
                                                        <input type="number" class="form-control" name="last_diff" id="last_diff" placeholder="差额或倍数" />
                                                    </div>
                                                </div>
                                                <div class="form-group">
                                                    <label for="class_status" class="control-label col-md-2">奖级状态</label>
                                                    <div class="col-md-5">
                                                        <input type="number" class="form-control" name="class_status" id="class_status"  placeholder="奖级状态" />
                                                    </div>
                                                </div>
                                                <div class="form-group">
                                                    <label for="top_money" class="control-label col-md-2">封顶金额</label>
                                                    <div class="col-md-5">
                                                        <input type="number" class="form-control" name="top_money" id="top_money"  placeholder="封顶金额" />
                                                    </div>
                                                </div>
                                                <div class="form-group">
                                                    <label for="open_id" class="control-label col-md-2">开奖次数</label>
                                                    <div class="col-md-5">
                                                        <input type="number" class="form-control" name="open_id" id="open_id"  placeholder="开奖次数" />
                                                    </div>
                                                </div>
                                                <hr>
                                                 <div class="form-group">
                                                    <div class="col-md-offset-1 col-md-9">
                                                        <input type="button"  onclick="submitGameClassInfo()"  id="GameClassInfoButton1" class="btn btn-primary" value="确认提交">
                                                    </div>
                                                </div>
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

                                                        function getGameInfoList(selectObj) {
                                                            $.ajax({
                                                                type: "POST",
                                                                url: contextPath + "/gameinfo/getGameinfoCache",
                                                                async: false,
                                                                success: function(data) {
                                                                    if (data !== null && "" !== data && data.length > 0) {
                                                                        for (var i in data) {
                                                                            $("<option value=" + data[i]["game_id"] + ">" + data[i]["game_name"] + "</option>").appendTo(selectObj);
                                                                        }
                                                                    }
                                                                },
                                                                error: function(e) {
                                                                    alert("网络异常，请稍后重试！");
                                                                }
                                                            });
                                                        }

                                                        function getPlayInfoList(gameId)
                                                        {
                                                            $.ajax({
                                                                type: "POST",
                                                                url: contextPath + "/gameplayinfo/getGamePlayInfoSelect?game_id=" + gameId,
                                                                error: function() {
                                                                    alert("Connection error");
                                                                },
                                                                success: function(data) {
                                                                    if (data !== null && "" !== data && data.length > 0) {
                                                                        $("#play_id").empty();
//                                $("<option value='0' >--全部玩法--</option>").appendTo($("#play_id"));
                                                                        for (var i in data) {
                                                                            $("<option value=" + data[i]["play_id"] + " >" + data[i]["play_name"] + "</option>").appendTo($("#play_id"));
                                                                        }
                                                                    } else {
                                                                        $("#play_id").empty();
//                                $("<option value='0' >空</option>").appendTo($("#play_id"));
                                                                    }
                                                                }
                                                            });
                                                        }
                                                        $(document).ready(function() {
                                                            getGameInfoList("#game_id");
                                                            getPlayInfoList($("#game_id").val());

                                                            $("#game_id").change(function() {
                                                                var gameId = $("#game_id").val();
                                                                getPlayInfoList(gameId);

                                                            });

                                                            $('#GameClassInfoAddForm').bootstrapValidator({
                                                                message: 'This value is not valid',
                                                                feedbackIcons: {
                                                                    valid: 'glyphicon glyphicon-ok',
                                                                    invalid: 'glyphicon glyphicon-remove',
                                                                    validating: 'glyphicon glyphicon-refresh'
                                                                },
                                                                fields: {
                                                                    game_id: {
                                                                        validators: {
                                                                            notEmpty: {
                                                                                message: '游戏编号不能为空'
                                                                            }
                                                                        }
                                                                    },
                                                                    play_id: {
                                                                        validators: {
                                                                            notEmpty: {
                                                                                message: '玩法编号不能为空'
                                                                            }
                                                                        }
                                                                    },
                                                                    class_id: {
                                                                        validators: {
                                                                            notEmpty: {
                                                                                message: '奖级编号不能为空'
                                                                            }
                                                                        }
                                                                    },
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
                                                                            },
                                                                            numeric: {
                                                                                message: '总期号为数值型'
                                                                            }
                                                                        }
                                                                    }
                                                                }
                                                            });
                                                        });
                                                        function submitGameClassInfo() {
                                                            $("#GameClassInfoAddForm").data('bootstrapValidator').validate();
                                                            if (!($("#GameClassInfoAddForm").data('bootstrapValidator').isValid()))
                                                                return;
                                                            if (!confirm("确定添加?"))
                                                                return;
                                                            $.ajax({
                                                                cache: true,
                                                                type: "POST",
                                                                url: contextPath + "/gameClassInfo/addGameClassInfo",
                                                                data: $('#GameClassInfoAddForm').serialize(),
                                                                async: false,
                                                                error: function(request) {
                                                                    alert("网络故障!");
                                                                },
                                                                success: function(data) {
                                                                    if (data.result === "success") {
                                                                        alert(data.msg);
                                                                        window.location.href = contextPath + "/gameClassInfo/2select";
                                                                    } else {
                                                                        alert(data.msg);
                                                                    }
                                                                }
                                                            });
                                                        }
        </script>
    </body>
</html>
