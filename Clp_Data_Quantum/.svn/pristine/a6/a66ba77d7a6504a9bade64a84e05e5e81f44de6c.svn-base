<%-- 
    Document   : accRegister
    Created on : 2014-8-9, 11:41:24
    Author     : hhhh
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>开户</title>
        <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no">
        <!--<link href="<%=request.getContextPath()%>/css/style/bootstrap-combined.min.css" rel="stylesheet">-->
        <link rel="stylesheet" href="<%=request.getContextPath()%>/css/style/bootstrap.css" >
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
                                        <div class="pull-left">开户</div>
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
                                                    <div class="row" > 
                                                        <label for="account_id" class="control-label col-md-2 ">账户编号：</label>
                                                        <div class="col-md-3">
                                                            <input type="text" class="form-control" id="account_id" name="account_id" placeholder="账户编号">
                                                        </div>
                                                        <label for="account_name" class="control-label col-md-2 col-md-offset-1">账户名称：</label>
                                                        <div class="col-md-3">
                                                            <input type="text" class="form-control" id="account_name" name="account_name" placeholder="账户名称">
                                                        </div>
                                                    </div>
                                                    <div class="row" > 
                                                        <label for="owner_name" class="control-label col-md-2">户主名：</label>
                                                        <div class="col-md-3">
                                                            <input type="text" id="owner_name" name="owner_name" class="form-control" placeholder="户主名">
                                                        </div>
                                                        <label for="account_type" class="control-label col-md-2 col-md-offset-1">账户类型编号：</label>
                                                        <div class="col-md-3">
                                                            <select name="account_type" id="account_type" class="form-control">
                                                            </select>
                                                        </div>
                                                    </div>
                                                    <div class="row" > 
                                                        <!--                                                        <label for="acc_balance" class="control-label col-md-2 ">账户余额：</label>
                                                                                                                <div class="col-md-3">
                                                                                                                    <input type="text" class="form-control" id="acc_balance" name="acc_balance" placeholder="账户余额">
                                                                                                                </div>-->
                                                        <label for="temp_credit" class="control-label col-md-2">临时信誉度：</label>
                                                        <div class="col-md-3">
                                                            <input type="text" id="temp_credit" name="temp_credit" class="form-control" placeholder="临时信誉度">
                                                        </div>
                                                        <label for="settlement_type" class="control-label col-md-2 col-md-offset-1">资金结算类型：</label>
                                                        <div class="col-md-3">
                                                            <input type="text" class="form-control" id="settlement_type" name="settlement_type" placeholder="资金结算类型">
                                                        </div>
                                                    </div>
                                                    <div class="row" > 
                                                        <label for="credit_time" class="control-label col-md-2 ">临时信用额度有效时间：</label>
                                                        <div class="col-md-3 input-append date form_datetime" data-date-format="yyyy-mm-dd hh:ii:ss">
                                                            <input type="text" class="form-control" id="credit_time" name="credit_time_str">
                                                            <span class="add-on"><i class="icon-remove"></i></span>
                                                            <span class="add-on"><i class="icon-calendar"></i></span>
                                                        </div>
                                                        <label for="bank_id" class="control-label col-md-2 col-md-offset-1">银行编号：</label>
                                                        <div class="col-md-3">
                                                            <select name="bank_id" id="bank_id" class="form-control">
                                                            </select>
                                                        </div>
                                                    </div>
                                                    <div class="row" > 
                                                        <label for="default_credit" class="control-label col-md-2">默认信誉额度：</label>
                                                        <div class="col-md-3">
                                                            <input type="text" id="default_credit" name="default_credit" class="form-control" placeholder="默认信誉额度">
                                                        </div>
                                                        <label for="bound_card" class="control-label col-md-2 col-md-offset-1">绑定银行卡号：</label>
                                                        <div class="col-md-3">
                                                            <input type="text" class="form-control" id="bound_card" name="bound_card" placeholder="绑定银行卡号">
                                                        </div>
                                                    </div>
                                                    <div class="row" > 
                                                        <!--                                                        <label for="prize_balance" class="control-label col-md-2 ">奖金余额：</label>
                                                                                                                <div class="col-md-3">
                                                                                                                    <input type="text" class="form-control" id="prize_balance" name="prize_balance" placeholder="奖金余额">
                                                                                                                </div>-->
                                                        <label for="prize_method" class="control-label col-md-2">奖金处理方式：</label>
                                                        <div class="col-md-3">
                                                            <input type="text" id="prize_method" name="prize_method" class="form-control" placeholder="奖金处理方式">
                                                        </div>
                                                        <label for="freeze_money" class="control-label col-md-2 col-md-offset-1">冻结金额：</label>
                                                        <div class="col-md-3">
                                                            <input type="text" class="form-control" id="freeze_money" name="freeze_money" placeholder="冻结金额">
                                                        </div>
                                                    </div>
                                                    <div class="row" > 
                                                        <label for="account_status" class="control-label col-md-2">账户状态：</label>
                                                        <div class="col-md-3">
                                                            <input type="text" id="account_status" name="account_status" class="form-control" placeholder="账户状态">
                                                        </div>
                                                        <label for="acc_note" class="control-label col-md-2 col-md-offset-1">备注：</label>
                                                        <div class="col-md-3">
                                                            <input type="text" id="acc_note" name="acc_note" class="form-control" placeholder="备注">
                                                        </div>
                                                    </div>
<!--                                                    <div class="row" > 
                                                        <label for="account_serial_no" class="control-label col-md-2 ">当前账户流水号：</label>
                                                        <div class="col-md-3">
                                                            <input type="text" class="form-control" id="account_serial_no" name="account_serial_no" placeholder="当前账户流水号">
                                                        </div>
                                                    </div>-->
                                                    <div class="text-center">
                                                        <input type="button" id="add_button"  class="btn btn-primary" value="开 户"/>
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
        <script type="text/javascript"  src="<%=request.getContextPath()%>/js/test/bootstrap-datetimepicker.min.js" ></script>
        <script type="text/javascript"  src="<%=request.getContextPath()%>/js/common/selAccountTypeList.js" ></script>
        <script type="text/javascript"  src="<%=request.getContextPath()%>/js/common/selBankCodeList.js" ></script>
        <script type="text/javascript" src="<%=request.getContextPath()%>/js/businessRole/accRegister.js"></script>
        <script type="text/javascript">
            var contextPath = "<%=request.getContextPath()%>";
        </script>
    </body>
</html>
