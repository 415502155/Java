<%-- 
    Document   : DealerRegister
    Created on : 2014-7-29, 13:36:47
    Author     : hhhh
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no">
        <title>代销商注册</title>
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
                                        <div class="pull-left">代销商注册</div>
                                        <div class="widget-icons pull-right">
                                            <a href="#" class="wminimize"><i class="icon-chevron-up"></i></a> 
                                            <a href="#" class="wclose"><i class="icon-remove"></i></a>
                                        </div>
                                        <div class="clearfix"></div>
                                    </div>

                                    <div class="widget-content">
                                        <div class="padd">

                                            <form id="DealerinfoForm" method="post" action="" role="form" class="form-horizontal">
                                                <div class="form-group">
                                                    <label for="dealer_id" class="col-md-2 control-label">代销商编号</label>
                                                    <div class="col-md-5">
                                                        <input type="text" class="form-control" name="dealer_id" id="dealer_id" placeholder="代销商编号">
                                                    </div>
                                                </div>

                                                <div class="form-group">
                                                    <label for="dealer_name" class="col-md-2 control-label">代销商名称</label>
                                                    <div class="col-md-5">
                                                        <input type="text" class="form-control" name="dealer_name" id="dealer_name" placeholder="代销商名称">
                                                    </div>
                                                </div>

                                                <div class="form-group">
                                                    <label for="dealer_type" class="col-md-2 control-label">代销商类型</label>
                                                    <div class="col-md-5">
                                                        <select name="dealer_type" id="dealer_type" class="form-control">
                                                            <option value="-1">--请选择--</option>
                                                        </select>
                                                    </div>
                                                </div>

                                                <div class="form-group">
                                                    <label for="dealer_address" class="col-md-2 control-label">地址</label>
                                                    <div class="col-md-5">
                                                        <input type="text" class="form-control" name="dealer_address" id="dealer_address" placeholder="地址">
                                                    </div>
                                                </div>

                                                <div class="form-group">
                                                    <label for="owner_name" class="col-md-2 control-label">负责人姓名</label>
                                                    <div class="col-md-5">
                                                        <input type="text" class="form-control" name="owner_name" id="owner_name" placeholder="负责人姓名">
                                                    </div>
                                                </div>

                                                <div class="form-group">
                                                    <label for="owner_phone" class="col-md-2 control-label">负责人电话</label>
                                                    <div class="col-md-5">
                                                        <input type="text" class="form-control" name="owner_phone" id="owner_phone" placeholder="负责人电话">
                                                    </div>
                                                </div>

                                                <div class="form-group">
                                                    <label for="link_name" class="col-md-2 control-label">联系人</label>
                                                    <div class="col-md-5">
                                                        <input type="text" class="form-control" name="link_name" id="link_name" placeholder="联系人">
                                                    </div>
                                                </div>

                                                <div class="form-group">
                                                    <label for="link_phone" class="col-md-2 control-label">联系电话</label>
                                                    <div class="col-md-5">
                                                        <input type="text" class="form-control" name="link_phone" id="link_phone" placeholder="联系电话">
                                                    </div>
                                                </div>

                                                <div class="form-group">
                                                    <label for="dealer_phone" class="col-md-2 control-label">工作电话</label>
                                                    <div class="col-md-5">
                                                        <input type="text" class="form-control" name="dealer_phone" id="dealer_phone" placeholder="工作电话">
                                                    </div>
                                                </div>

                                                <div class="form-group">
                                                    <label for="province_id" class="col-md-2 control-label">省系统</label>
                                                    <div class="col-md-2">
                                                        <select name="province_id" id="province_id" class="form-control">                                                            
                                                        </select>
                                                    </div>
                                                    <label for="city_id" class="col-md-1 control-label">地市</label>
                                                    <div class="col-md-2">
                                                        <select name="city_id" id="city_id" class="form-control">
                                                        </select>
                                                    </div>
                                                </div>

                                                <div class="form-group">
                                                    <label for="email" class="col-md-2 control-label">电子邮箱</label>
                                                    <div class="col-md-5">
                                                        <input type="text" class="form-control" name="email" id="email" placeholder="电子邮箱">
                                                    </div>
                                                </div>

                                                <div class="form-group">
                                                    <label for="phone_no" class="col-md-2 control-label">固定电话</label>
                                                    <div class="col-md-5">
                                                        <input type="text" class="form-control" name="phone_no" id="phone_no" placeholder="固定电话">
                                                    </div>
                                                </div>

                                                <div class="form-group">
                                                    <label for="id_type_id" class="col-md-2 control-label">证件类型</label>
                                                    <div class="col-md-5">
                                                        <select name="id_type_id" id="id_type_id" class="form-control">                                                            
                                                        </select>
                                                    </div>
                                                </div>

                                                <div class="form-group">
                                                    <label for="ID_no" class="col-md-2 control-label">证件编号</label>
                                                    <div class="col-md-5">
                                                        <input type="text" class="form-control" name="ID_no" id="ID_no" placeholder="证件编号">
                                                    </div>
                                                </div>

                                                <div class="form-group">
                                                    <label for="bank_id" class="col-md-2 control-label">银行</label>
                                                    <div class="col-md-5">
                                                        <select name="bank_id" id="bank_id" class="form-control">
                                                            <option value="-1">--请选择--</option>
                                                        </select>
                                                    </div>
                                                </div>

                                                <div class="form-group">
                                                    <label for="account_name" class="col-md-2 control-label">账户名称</label>
                                                    <div class="col-md-5">
                                                        <input type="text" class="form-control" name="account_name" id="account_name" placeholder="账户名称">
                                                    </div>
                                                </div>

                                                <div class="form-group">
                                                    <label for="account_card" class="col-md-2 control-label">银行卡号</label>
                                                    <div class="col-md-5">
                                                        <input type="text" class="form-control" name="account_card" id="account_card" placeholder="银行卡号">
                                                    </div>
                                                </div>

                                                <div class="form-group">
                                                    <label for="terminal_condition" class="col-md-2 control-label">终端情况</label>
                                                    <div class="col-md-5">
                                                        <input type="text" class="form-control" name="terminal_condition" id="terminal_condition" placeholder="终端情况">
                                                    </div>
                                                </div>

                                                <div class="form-group">
                                                    <label for="note" class="col-md-2 control-label">备注</label>
                                                    <div class="col-md-5">
                                                        <input type="text" class="form-control" name="note" id="note" placeholder="备注">
                                                    </div>
                                                </div>

                                                <div class="form-group">
                                                    <label class="col-md-2 control-label">代销商特权</label>
                                                    <div id="gameInfoCkxs" class="col-md-5">

                                                    </div>
                                                </div>
                                                <div class="form-group">
                                                    <label class="col-md-2 control-label">&nbsp;</label>
                                                    <div class="col-md-5 text-left">
                                                        <input type="button" id="DealerinfoButton" class="btn btn-primary" value="注 册">
                                                        <!--<button id="DealerinfoButton" class="btn btn-primary" type="submit">注 册</button>-->
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
        <script type="text/javascript" src="<%=request.getContextPath()%>/js/test/json2.js"></script>
        <script type="text/javascript">
            var contextPath = "<%=request.getContextPath()%>";
        </script>
        <script type="text/javascript" src="<%=request.getContextPath()%>/js/common/selDealerTypeList.js"></script>
        <script type="text/javascript" src="<%=request.getContextPath()%>/js/common/selSystemInfoList.js"></script>
        <script type="text/javascript" src="<%=request.getContextPath()%>/js/common/selCityInfoList.js"></script>
        <script type="text/javascript" src="<%=request.getContextPath()%>/js/common/selIdTypeList.js"></script>
        <script type="text/javascript" src="<%=request.getContextPath()%>/js/common/selBankCodeList.js"></script>
        <script type="text/javascript" src="<%=request.getContextPath()%>/js/common/selGameInfoList.js"></script>
        <script type="text/javascript" src="<%=request.getContextPath()%>/js/businessRole/DealerRegister.js"></script>

    </body>
</html>
