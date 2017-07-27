<%-- 
    Document   : systemInfo
    Created on : 2014-8-6, 19:10:16
    Author     : YangRong
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no">
        <title>省系统信息</title>
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
                                        <div class="pull-left">省系统信息</div>
                                        <div class="widget-icons pull-right">
                                            <a href="#" class="wminimize"><i class="icon-chevron-up"></i></a> 
                                            <a href="#" class="wclose"><i class="icon-remove"></i></a>
                                        </div>
                                        <div class="clearfix"></div>
                                    </div>

                                    <div class="widget-content">
                                        <div class="padd">
                                            <form id="SystemInfoForm" method="post" action="" role="form" class="form-horizontal">
                                                <!--<fieldset>-->
                                                <!--<legend>省系统信息</legend>-->
                                                <div class="form-group">
                                                    <label for="province_id" class="control-label col-md-2">省系统编号</label>
                                                    <div class="col-md-5">
                                                        <input type="text" class="form-control" name="province_id" id="province_id" value="${systemInfo.province_id}" readonly/>
                                                    </div>
                                                </div>
                                                <div class="form-group">
                                                    <label for="province_name" class="control-label col-md-2">省名</label>
                                                    <div class="col-md-5">
                                                        <input type="text" class="form-control" name="province_name" id="province_name" value="${systemInfo.province_name}" readonly/>
                                                    </div>
                                                </div>
                                                <div class="form-group">
                                                    <label for="province_address" class="control-label col-md-2">地址</label>
                                                    <div class="col-md-5">
                                                        <input type="text" class="form-control" name="province_address" id="province_address" value="${systemInfo.province_address}" />
                                                    </div>
                                                </div>
                                                <div class="form-group">
                                                    <label for="province_phone" class="control-label col-md-2">联系电话</label>
                                                    <div class="col-md-5">
                                                        <input type="text" class="form-control" name="province_phone" id="province_phone" value="${systemInfo.province_phone}" />
                                                    </div>
                                                </div>
                                                <div class="form-group">
                                                    <label for="agentfee_type" class="control-label col-md-2">付款方式</label>
                                                    <div class="col-md-5">
                                                        <input type="text" class="form-control" name="agentfee_type" id="agentfee_type" value="${systemInfo.agentfee_type}" />
                                                    </div>
                                                </div>
                                                <div class="form-group">
                                                    <label for="deduct_switch" class="control-label col-md-2">实时扣款标志</label>
                                                    <div class="col-md-5">
                                                        <input type="text" class="form-control" name="deduct_switch" id="deduct_switch" value="${systemInfo.deduct_switch}" />
                                                    </div>
                                                </div>
                                                <div class="form-group">
                                                    <label for="cash_fee_type" class="control-label col-md-2">兑奖代销费计算方式</label>
                                                    <div class="col-md-5">
                                                        <input type="text" class="form-control" name="cash_fee_type" id="cash_fee_type" value="${systemInfo.cash_fee_type}" />
                                                    </div>
                                                </div>
                                                <div class="form-group">
                                                    <label for="online_report" class="control-label col-md-2">心跳间隔</label>
                                                    <div class="col-md-5">
                                                        <input type="text" class="form-control" name="online_report" id="online_report" value="${systemInfo.online_report}" />
                                                    </div>
                                                </div>
                                                <div class="form-group">
                                                    <label for="center_status" class="control-label col-md-2">系统状态</label>
                                                    <div class="col-md-5">
                                                        <input type="text" class="form-control" name="center_status" id="center_status" value="${systemInfo.center_status}" />
                                                    </div>
                                                </div>
                                                <div class="form-group">
                                                    <label for="max_terminal" class="control-label col-md-2">本系统最多投注机数量</label>
                                                    <div class="col-md-5">
                                                        <input type="text" class="form-control" name="max_terminal" id="max_terminal" value="${systemInfo.max_terminal}" />
                                                    </div>
                                                </div>
                                                <div class="form-group">
                                                    <label for="cur_max_game" class="control-label col-md-2">同时最多游戏数量</label>
                                                    <div class="col-md-5">
                                                        <input type="text" class="form-control" name="cur_max_game" id="cur_max_game" value="${systemInfo.cur_max_game}" />
                                                    </div>
                                                </div>
                                                <div class="form-group">
                                                    <label for="online_cash_mark" class="control-label col-md-2">通兑许可标志</label>
                                                    <div class="col-md-5">
                                                        <input type="text" class="form-control" name="online_cash_mark" id="online_cash_mark" value="${systemInfo.online_cash_mark}" />
                                                    </div>
                                                </div>
                                                <div class="form-group">
                                                    <label for="system_version" class="control-label col-md-2">系统版本号</label>
                                                    <div class="col-md-5">
                                                        <input type="text" class="form-control" name="system_version" id="system_version" value="${systemInfo.system_version}" />
                                                    </div>
                                                </div>
                                                <div class="form-group">
                                                    <label for="system_syn_no" class="control-label col-md-2">系统同步字</label>
                                                    <div class="col-md-5">
                                                        <input type="text" class="form-control" name="system_syn_no" id="system_syn_no" value="${systemInfo.system_syn_no}" />
                                                    </div>
                                                </div>
                                                <div class="form-group">
                                                    <label for="center_type" class="control-label col-md-2">当前中心</label>
                                                    <div class="col-md-5">
                                                        <input type="text" class="form-control" name="center_type" id="center_type" value="${systemInfo.center_type}" />
                                                    </div>
                                                </div>
                                                <!--                                                <div class="form-group">
                                                                                                    <label for="system_syn_no" class="control-label col-md-2">系统同步字</label>
                                                                                                    <div class="col-md-5">
                                                                                                        <input type="text" class="form-control" name="system_syn_no" id="system_syn_no" value="${systemInfo.system_syn_no}" />
                                                                                                    </div>
                                                                                                </div>-->

                                                <hr />
                                                <div class="form-group">
                                                    <div class="col-md-offset-2 col-md-9">
                                                        <input type="button" id="SystemInfoButton1" class="btn btn-primary" value="确认提交" onclick="submitSystemInfo()">
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
        </script> 
        <script type="text/javascript">
            $(document).ready(function() {
                $('#SystemInfoForm').bootstrapValidator({
                    message: 'This value is not valid',
                    feedbackIcons: {
                        valid: 'glyphicon glyphicon-ok',
                        invalid: 'glyphicon glyphicon-remove',
                        validating: 'glyphicon glyphicon-refresh'
                    },
                    fields: {
                        province_address: {
                            validators: {
                                notEmpty: {
                                    message: '地址不能为空'
                                }
                            }
                        },
                        province_phone: {
                            validators: {
                                notEmpty: {
                                    message: '电话不能为空'
                                }
                            }
                        }
                    }
                });
            });
            function submitSystemInfo() {
                $("#SystemInfoForm").data('bootstrapValidator').validate();
                if (!($("#SystemInfoForm").data('bootstrapValidator').isValid()))
                    return;
                if (!confirm("确定保存?"))
                    return;
                $.ajax({
                    type: "POST",
                    url: contextPath + "/systemInfo/modifySystemInfo",
                    data: $("#SystemInfoForm").serialize(),
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
