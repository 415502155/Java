<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>修改密码</title>
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
                                        <div class="pull-left">系统用户密码修改</div>
                                        <div class="widget-icons pull-right">
                                            <a href="#" class="wminimize"><i class="icon-chevron-up"></i></a> 
                                            <a href="#" class="wclose"><i class="icon-remove"></i></a>
                                        </div>
                                        <div class="clearfix"></div>
                                    </div>

                                    <div class="widget-content">
                                        <div class="padd">
                                            <!-- Form starts.  -->
                                            <form class="form-horizontal" role="form" id="SysuserModifyPwdForm" action="<%=request.getContextPath()%>/user/modifyPwd" method="post">
                                                <div class="form-group">
                                                    <label class="col-md-2 control-label">用户名</label>
                                                    <div class="col-md-5">
                                                        <input type="text" class="form-control" placeholder="" name="user_name" value="${sessionScope.user.user_name}">
                                                    </div>
                                                </div>

                                                <div class="form-group">
                                                    <label class="col-md-2 control-label">用户原密码</label>
                                                    <div class="col-md-5">
                                                        <input type="password" class="form-control" placeholder="" name="user_pwd">
                                                    </div>
                                                </div>

                                                <div class="form-group">
                                                    <label class="col-md-2 control-label">用户新密码</label>
                                                    <div class="col-md-5">
                                                        <input type="password" class="form-control" placeholder="" name="user_pwd1">
                                                    </div>
                                                </div>

                                                <hr />
                                                <div class="form-group">
                                                    <div class="col-md-offset-2 col-md-5">
                                                        <input type="button" id="SysuserModifyPwdSubmit" class="btn btn-primary" value="确认提交">
                                                        <!--<button type="submit" class="btn btn-primary" id="sysuseradd">Sign up</button>-->
                                                    </div>
                                                </div>
                                            </form>
                                            <!-- Form end.  -->
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
        <script>
            $(function() {
                $('#SysuserModifyPwdForm').bootstrapValidator({
                    message: 'This value is not valid',
                    feedbackIcons: {
                        valid: 'glyphicon glyphicon-ok',
                        invalid: 'glyphicon glyphicon-remove',
                        validating: 'glyphicon glyphicon-refresh'
                    },
                    fields: {
                        user_name: {
                            message: '请填写正确的用户名',
                            validators: {
                                notEmpty: {
                                    message: 'The username is required and cannot be empty'
                                }
                            }
                        },
                        user_pwd: {
                            validators: {
                                notEmpty: {
                                    message: 'The password is required and cannot be empty'
                                },
                                different: {
                                    field: 'user_pwd1',
                                    message: 'The password and its confirm are not the same'
                                }
                            }
                        },
                        user_pwd1: {
                            validators: {
                                notEmpty: {
                                    message: 'The confirm password is required and cannot be empty'
                                },
                                different: {
                                    field: 'user_pwd',
                                    message: 'The password and its confirm are not the same'
                                }
                            }
                        }
                    }
                });

                $("#SysuserModifyPwdSubmit").click(function() {
                    $('#SysuserModifyPwdForm').bootstrapValidator('validate').on('error.form.bv', function(e) {
                    }).on('success.form.bv', function(e) {
                        $.ajax({
                            type: "POST",
                            url: "<%=request.getContextPath()%>/user/modifyPwd",
                            data: $('#SysuserModifyPwdForm').serialize(),
                            success: function(data) {
                                alert(data.result);
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
