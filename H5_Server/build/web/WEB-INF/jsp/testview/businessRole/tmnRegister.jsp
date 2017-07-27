<%-- 
    Document   : tmnRegister
    Created on : 2014-8-7, 14:41:24
    Author     : hhhh
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>终端信息登记</title>
        <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no">
        <link rel="stylesheet" href="<%=request.getContextPath()%>/css/style/bootstrap.css" >
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
                                        <div class="pull-left">终端信息登记</div>
                                        <div class="widget-icons pull-right">
                                            <a href="#" class="wminimize"><i class="icon-chevron-up"></i></a> 
                                            <a href="#" class="wclose"><i class="icon-remove"></i></a>
                                        </div>
                                        <div class="clearfix"></div>
                                    </div>

                                    <div class="widget-content">
                                        <div class="padd">
                                            <form class="form-horizontal"  role="form"  id="TmninfoForm"  method="post">
                                                <div class="container text-center" >
                                                    <div class="row" > 
                                                        <label for="terminal_id" class="control-label col-md-2 ">终端编号：</label>
                                                        <div class="col-md-3">
                                                            <input type="text" id="terminal_id" name="terminal_id" class="form-control" placeholder="终端编号">
                                                        </div>
                                                        <label for="terminal_value_str" class="control-label col-md-2 ">终端设备使用价值：</label>
                                                        <div class="col-md-3">
                                                            <input type="text" id="terminal_value_str" name="terminal_value_str" class="form-control" placeholder="终端设备使用价值">
                                                        </div>
                                                    </div>
                                                    <div class="row" > 
                                                        <label for="terminal_status" class="control-label col-md-2">终端状态：</label>
                                                        <div class="col-md-3">
                                                            <select name="terminal_status" id="terminal_status" class="form-control">
                                                                <option value="1" selected="true">启用</option>
                                                                <option value="2">不启用</option>
                                                            </select>
                                                        </div>
                                                        <label for="dealer_id" class="control-label col-md-2 ">代销商：</label>
                                                        <div class="col-md-3">
                                                            <select name="dealer_id" id="dealer_id" class="form-control">
                                                            </select>
                                                        </div>
                                                    </div>
                                                    <div class="row" > 
                                                        <input type="hidden" value="" id="province_id" name="province_id"/>
                                                        <label for="city_id" class="control-label col-md-2">地市名称：</label>
                                                        <div class="col-md-3">
                                                            <select name="city_id" id="city_id" class="form-control">
                                                            </select>
                                                        </div>
                                                        <label for="district_id" class="control-label col-md-2 ">所属区县：</label>
                                                        <div class="col-md-3">
                                                            <select name="district_id" id="district_id" class="form-control">
                                                            </select>
                                                        </div>
                                                    </div>
                                                    <div class="row" >                                                       
                                                        <label for="station_name" class="control-label col-md-2">站点名称：</label>
                                                        <div class="col-md-3">
                                                            <input type="text" id="station_name" name="station_name" class="form-control" placeholder="站点名称">
                                                        </div>
                                                        <label for="terminal_address" class="control-label col-md-2 ">站点地址：</label>
                                                        <div class="col-md-3">
                                                            <input type="text" class="form-control" id="terminal_address" name="terminal_address" placeholder="站点地址">
                                                        </div>
                                                    </div>
                                                    <div class="row" > 
                                                        <label for="terminal_type" class="control-label col-md-2 ">终端类型：</label>
                                                        <div class="col-md-3">
                                                            <input type="text" class="form-control" id="terminal_type" name="terminal_type" placeholder="终端类型">
                                                        </div>
                                                        <label for="station_phone" class="control-label col-md-2 ">站点电话：</label>
                                                        <div class="col-md-3">
                                                            <input type="text" id="station_phone" name="station_phone" class="form-control" placeholder="站点电话">
                                                        </div>
                                                    </div>
                                                    <div class="row" > 
                                                        <label for="dial_name" class="control-label col-md-2 ">拨号账户：</label>
                                                        <div class="col-md-3">
                                                            <input type="text" class="form-control" id="dial_name" name="dial_name" placeholder="拨号账户">
                                                        </div>
                                                        <label for="owner_name" class="control-label col-md-2 ">户主名：</label>
                                                        <div class="col-md-3">
                                                            <input type="text" class="form-control" id="owner_name" name="owner_name" placeholder="户主名">
                                                        </div>
                                                    </div>
                                                    <div class="row" >                                                        
                                                        <label for="dial_pwd" class="control-label col-md-2">拨号密码：</label>
                                                        <div class="col-md-3">
                                                            <input type="text" id="dial_pwd" name="dial_pwd" class="form-control" placeholder="拨号密码">
                                                        </div>
                                                        <label for="owner_phone" class="control-label col-md-2 ">负责人电话：</label>
                                                        <div class="col-md-3">
                                                            <input type="text" id="owner_phone" name="owner_phone" class="form-control" placeholder="负责人电话">
                                                        </div>
                                                    </div>
                                                    <div class="row" >  
                                                        <label for="software_id" class="control-label col-md-2">软件类型：</label>
                                                        <div class="col-md-3">
                                                            <select name="software_id" id="software_id" class="form-control">
                                                            </select>
                                                        </div>                                                        
                                                        <label for="linkman" class="control-label col-md-2 ">联系人：</label>
                                                        <div class="col-md-3">
                                                            <input type="text" class="form-control" id="linkman" name="linkman" placeholder="联系人">
                                                        </div>
                                                    </div>
                                                    <div class="row" > 
                                                        <label for="software_version" class="control-label col-md-2">软件版本：</label>
                                                        <div class="col-md-3">
                                                            <input type="text" id="software_version" name="software_version" class="form-control" placeholder="软件版本">
                                                        </div>
                                                        <label for="linkman_phone" class="control-label col-md-2 ">联系人电话：</label>
                                                        <div class="col-md-3">
                                                            <input type="text" id="linkman_phone" name="linkman_phone" class="form-control" placeholder="联系人电话">
                                                        </div>
                                                    </div>
                                                    <div class="row" > 
                                                        <label for="agentfee_type" class="control-label col-md-2 ">代销费内扣方式：</label>
                                                        <div class="col-md-3">
                                                            <input type="text" class="form-control" id="agentfee_type" name="agentfee_type" placeholder="代销费内扣方式">
                                                        </div>
                                                        <label for="tmn_sale_deduct" class="control-label col-md-2 ">销售注销实时扣款：</label>
                                                        <div class="col-md-3">
                                                            <input type="text" id="tmn_sale_deduct" name="tmn_sale_deduct" class="form-control" placeholder="销售注销实时扣款">
                                                        </div>
                                                    </div>
                                                    <div class="row" > 
                                                        <label for="tmn_cash_deduct" class="control-label col-md-2 ">兑奖实时扣款：</label>
                                                        <div class="col-md-3">
                                                            <input type="text" class="form-control" id="tmn_cash_deduct" name="tmn_cash_deduct" placeholder="兑奖实时扣款">
                                                        </div>
                                                        <label for="account_id" class="control-label col-md-2 ">默认扣款账户：</label>
                                                        <div class="col-md-3">
                                                            <input type="text" class="form-control" id="account_id" name="account_id" placeholder="默认扣款账户">
                                                        </div>

                                                    </div>
                                                    <div class="row" > 
                                                        <label for="comm_type" class="control-label col-md-2 ">通讯方式：</label>
                                                        <div class="col-md-3">
                                                            <input type="text" id="comm_type" name="comm_type" class="form-control" placeholder="通讯方式">
                                                        </div>
                                                    </div>
                                                </div>
                                                <div class="text-center">
                                                    <input type="button" id="add_button"  class="btn btn-primary" value="注 册"/>
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
        <script type="text/javascript">
            var contextPath = "<%=request.getContextPath()%>";
        </script>
        <script src="<%=request.getContextPath()%>/js/test/jquery-1.10.2.min.js"></script>
        <script type="text/javascript" src="<%=request.getContextPath()%>/js/test/bootstrap.min.js"></script>
        <script type="text/javascript" src="<%=request.getContextPath()%>/js/test/bootstrapValidator.js"></script>
        <script type="text/javascript" src="<%=request.getContextPath()%>/js/common/selDealerInfoList.js"></script>
        <script type="text/javascript" src="<%=request.getContextPath()%>/js/common/selDistrictInfoList.js"></script>
        <script type="text/javascript" src="<%=request.getContextPath()%>/js/common/selCityInfoList.js"></script>
        <script type="text/javascript" src="<%=request.getContextPath()%>/js/common/selSoftWareTypeList.js"></script>
        <script type="text/javascript" src="<%=request.getContextPath()%>/js/businessRole/tmnRegister.js"></script>
    </body>
</html>
