<%-- 
    Document   : gameDrawInfoAdd
    Created on : 2014-8-5, 14:25:22
    Author     : chenliping
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>游戏期生成</title>

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
                                        <div class="pull-left">期信息生成</div>
                                        <div class="widget-icons pull-right">
                                            <a href="#" class="wminimize"><i class="icon-chevron-up"></i></a> 
                                            <a href="#" class="wclose"><i class="icon-remove"></i></a>
                                        </div>
                                        <div class="clearfix"></div>
                                    </div>

                                    <div class="widget-content">
                                        <div class="padd">
                                            <!-- Form starts.  -->
                                            <form class="form-horizontal" role="form" id="drawinfoForm">
                                                <div class="form-group">
                                                    <label class="col-md-2 control-label">游戏名</label>
                                                    <div class="col-md-5">
                                                        <select name="game_id" id="bind_game_id" class="form-control">
                                                        </select>
                                                        <input type="text" class="form-control sr-only" placeholder="" name="keno_game" id="keno_game">
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
                                                    <div class="col-md-5">
                                                        <input  type="text" class="form-control" placeholder="" name="end_time" id="end_time" readonly>
                                                    </div>
                                                </div>                

                                                <!--                                                <div class="form-group">
                                                                                                    <label class="col-md-2 control-label">开始期号</label>
                                                                                                    <div class="col-md-5">
                                                                                                        <input type="text" class="form-control" placeholder="" name="user_pwd">
                                                                                                    </div>
                                                                                                </div>-->

                                                <div class="form-group">
                                                    <label class="col-md-2 control-label">生成期数</label>
                                                    <div class="col-md-5">
                                                        <input type="text" class="form-control" placeholder="" name="draw_no">
                                                    </div>
                                                </div>                                             

                                                <hr />
                                                <div class="form-group">
                                                    <div class="col-md-offset-2 col-md-5">
                                                        <input type="button" id="drawinfoAdd" class="btn btn-primary active" value="确认提交">
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

            function getGameInfoById(obj1, obj2, obj3, gameId) {
                $.ajax({
                    type: "POST",
                    url: contextPath + "/gameinfo/getGameInfoByidCache2",
                    data: {"gameId": gameId},
                    success: function(data) {
                        if (data != null && "" != data) {
                            obj1.val(data.begin_time);
                            obj2.val(data.end_time);
                            obj3.val(data.keno_game);
                        }
                    },
                    error: function(e) {
                        alert("网络异常，请稍后重试！");
                    }
                });
            }

            $(function() {
                getGameInfoList("#bind_game_id");

                $("#bind_game_id").bind("change", function() {
                    var gameid = $(this).val();
                    getGameInfoById($("#begin_time"), $("#end_time"), $("#keno_game"), gameid);
                });

                $('#drawinfoForm').bootstrapValidator({
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
                        draw_no: {
                            validators: {
                                notEmpty: {
                                    message: 'The password is required and cannot be empty'
                                }
                            }
                        },
                        begin_time: {
                            validators: {
                                notEmpty: {
                                    message: 'The confirm password is required and cannot be empty'
                                }
                            }
                        },
                        end_time: {
                            validators: {
                                notEmpty: {
                                    message: 'The confirm password is required and cannot be empty'
                                }
                            }
                        }
                    }
                });

                $("#drawinfoAdd").click(function() {
                    $('#drawinfoForm').bootstrapValidator('validate').on('error.form.bv', function(e) {
                    }).on('success.form.bv', function(e) {
                        var url = "";
                        var kemo_game = $("#keno_game").val();
                        if ("0" == kemo_game) {//0代表不为keno游戏
                            url = "<%=request.getContextPath()%>/gamedraw/preSchedule";
                        } else {
                            url = "<%=request.getContextPath()%>/gamedraw/preScheduleK";
                        }
                        $.ajax({
                            type: "POST",
                            url: url,
                            data: $('#drawinfoForm').serialize(),
                            success: function(data) {
                                alert(data.drawinfo);
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
