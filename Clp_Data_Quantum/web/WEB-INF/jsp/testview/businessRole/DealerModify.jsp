<%-- 
    Document   : DealerModify
    Created on : 2014-8-6, 13:36:47
    Author     : hhhh
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8" import="com.bestinfo.bean.business.*,java.util.List,net.sf.json.*,java.math.*"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no">
        <title>代销商修改</title>
        <%
            DealerInfo di = (DealerInfo) request.getAttribute("dealerInfo");
            int dealerType = di.getDealer_type();
            int provinceId = di.getProvince_id();
            int cityId = di.getCity_id();
            int idTypeId = di.getId_type_id();
            String bankId = di.getBank_id();
            List<DealerPrivilege> dpList = (List<DealerPrivilege>) request.getAttribute("dpList");
        %>
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
                                        <div class="pull-left">代销商修改</div>
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
                                                    <label for="dealer_id" class="control-label col-md-2">代销商编号</label>
                                                    <div class="col-md-5">
                                                        <input type="text" class="form-control" name="dealer_id" id="dealer_id" placeholder="代销商编号" readonly="true" value="${dealerInfo.dealer_id}">
                                                    </div>
                                                </div>

                                                <div class="form-group">
                                                    <label for="dealer_name" class="control-label col-md-2">代销商名称</label>
                                                    <div class="col-md-5">
                                                        <input type="text" class="form-control" name="dealer_name" id="dealer_name" placeholder="代销商名称" value="${dealerInfo.dealer_name}">
                                                    </div>
                                                </div>

                                                <div class="form-group">
                                                    <label for="dealer_type" class="control-label col-md-2">代销商类型</label>
                                                    <div class="col-md-5">
                                                        <select name="dealer_type" id="dealer_type" class="form-control">
                                                            <option value="-1">--请选择--</option>
                                                        </select>
                                                    </div>
                                                </div>

                                                <div class="form-group">
                                                    <label for="dealer_address" class="control-label col-md-2">地址</label>
                                                    <div class="col-md-5">
                                                        <input type="text" class="form-control" name="dealer_address" id="dealer_address" placeholder="地址" value="${dealerInfo.dealer_address}">
                                                    </div>
                                                </div>

                                                <div class="form-group">
                                                    <label for="owner_name" class="control-label col-md-2">负责人姓名</label>
                                                    <div class="col-md-5">
                                                        <input type="text" class="form-control" name="owner_name" id="owner_name" placeholder="负责人姓名" value="${dealerInfo.owner_name}">
                                                    </div>
                                                </div>

                                                <div class="form-group">
                                                    <label for="owner_phone" class="control-label col-md-2">负责人电话</label>
                                                    <div class="col-md-5">
                                                        <input type="text" class="form-control" name="owner_phone" id="owner_phone" placeholder="负责人电话" value="${dealerInfo.owner_phone}">
                                                    </div>
                                                </div>

                                                <div class="form-group">
                                                    <label for="link_name" class="control-label col-md-2">联系人</label>
                                                    <div class="col-md-5">
                                                        <input type="text" class="form-control" name="link_name" id="link_name" placeholder="联系人" value="${dealerInfo.link_name}">
                                                    </div>
                                                </div>

                                                <div class="form-group">
                                                    <label for="link_phone" class="control-label col-md-2">联系电话</label>
                                                    <div class="col-md-5">
                                                        <input type="text" class="form-control" name="link_phone" id="link_phone" placeholder="联系电话" value="${dealerInfo.link_phone}">
                                                    </div>
                                                </div>

                                                <div class="form-group">
                                                    <label for="dealer_phone" class="control-label col-md-2">工作电话</label>
                                                    <div class="col-md-5">
                                                        <input type="text" class="form-control" name="dealer_phone" id="dealer_phone" placeholder="工作电话" value="${dealerInfo.dealer_phone}">
                                                    </div>
                                                </div>

                                                <div class="form-group">
                                                    <label for="province_id" class="control-label col-md-2">省系统</label>
                                                    <div class="col-md-2">
                                                        <select name="province_id" id="province_id" class="form-control">
                                                            <option value="-1">--请选择--</option>
                                                        </select>
                                                    </div>
                                                    <label for="city_id" class="control-label col-md-1">地市</label>
                                                    <div class="col-md-2">
                                                        <select name="city_id" id="city_id" class="form-control">
                                                        </select>
                                                    </div>
                                                </div>

                                                <div class="form-group">
                                                    <label for="email" class="control-label col-md-2">电子邮箱</label>
                                                    <div class="col-md-5">
                                                        <input type="text" class="form-control" name="email" id="email" placeholder="电子邮箱" value="${dealerInfo.email}">
                                                    </div>
                                                </div>

                                                <div class="form-group">
                                                    <label for="phone_no" class="control-label col-md-2">固定电话</label>
                                                    <div class="col-md-5">
                                                        <input type="text" class="form-control" name="phone_no" id="phone_no" placeholder="固定电话" value="${dealerInfo.phone_no}">
                                                    </div>
                                                </div>

                                                <div class="form-group">
                                                    <label for="id_type_id" class="control-label col-md-2">证件类型</label>
                                                    <div class="col-md-5">
                                                        <select name="id_type_id" id="id_type_id" class="form-control">
                                                            <option value="-1">--请选择--</option>
                                                        </select>
                                                    </div>
                                                </div>

                                                <div class="form-group">
                                                    <label for="ID_no" class="control-label col-md-2">证件编号</label>
                                                    <div class="col-md-5">
                                                        <input type="text" class="form-control" name="ID_no" id="ID_no" placeholder="证件编号" value="${dealerInfo.ID_no}">
                                                    </div>
                                                </div>

                                                <div class="form-group">
                                                    <label for="bank_id" class="control-label col-md-2">银行</label>
                                                    <div class="col-md-5">
                                                        <select name="bank_id" id="bank_id" class="form-control">
                                                            <option value="-1">--请选择--</option>
                                                        </select>
                                                    </div>
                                                </div>

                                                <div class="form-group">
                                                    <label for="account_name" class="control-label col-md-2">账户名称</label>
                                                    <div class="col-md-5">
                                                        <input type="text" class="form-control" name="account_name" id="account_name" placeholder="账户名称" value="${dealerInfo.account_name}">
                                                    </div>
                                                </div>

                                                <div class="form-group">
                                                    <label for="account_card" class="control-label col-md-2">银行卡号</label>
                                                    <div class="col-md-5">
                                                        <input type="text" class="form-control" name="account_card" id="account_card" placeholder="银行卡号" value="${dealerInfo.account_card}">
                                                    </div>
                                                </div>

                                                <div class="form-group">
                                                    <label for="terminal_condition" class="control-label col-md-2">终端情况</label>
                                                    <div class="col-md-5">
                                                        <input type="text" class="form-control" name="terminal_condition" id="terminal_condition" placeholder="终端情况" value="${dealerInfo.terminal_condition}">
                                                    </div>
                                                </div>

                                                <div class="form-group">
                                                    <label for="note" class="control-label col-md-2">备注</label>
                                                    <div class="col-md-5">
                                                        <input type="text" class="form-control" name="note" id="note" placeholder="备注" value="${dealerInfo.note}">
                                                    </div>
                                                </div>
                                                    
                                                <div class="form-group">
                                                    <label for="work_status" class="control-label col-md-2">工作状态</label>
                                                    <div class="col-md-5">
                                                        <select name="work_status" id="work_status" class="form-control">
                                                            <option value="1" <c:if test="${dealerInfo.work_status == '1'}">selected</c:if>>启用</option>
                                                            <option value="2" <c:if test="${dealerInfo.work_status == '2'}">selected</c:if>>不启用</option>
                                                        </select>
                                                    </div>
                                                </div>

                                                <div class="form-group">
                                                    <label class="control-label col-md-2">代销商特权</label>
                                                    <div id="gameInfoCkxs" class="col-md-5">

                                                    </div>
                                                </div>
                                                <div class="form-group">
                                                    <label class="col-md-2 control-label">&nbsp;</label>
                                                    <div class="col-md-5 text-left">
                                                        <input type="button" id="DealerinfoButton" class="btn btn-primary" value="修 改">
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

        <script type="text/javascript">
            var contextPath = "<%=request.getContextPath()%>";
            var dealerType = "<%=dealerType%>";
            var provinceId = "<%=provinceId%>";
            var cityId = "<%=cityId%>";
            var idTypeId = "<%=idTypeId%>";
            var bankId = "<%=bankId%>";
            var dpArr = [];
            <%
                for (DealerPrivilege dp : dpList) {
                    int gameId = dp.getGame_id();
                    BigDecimal bd = dp.getService_proportion();
                    int game_permit = dp.getGame_permit();
            %>
            var obj = {"gameId":<%=gameId%>, "bd":<%=bd%>, "game_permit":<%=game_permit%>};
            dpArr.push(obj);
            <%
                }
            %>
        </script>
        <script src="<%=request.getContextPath()%>/js/test/jquery-1.10.2.min.js"></script>
        <script type="text/javascript" src="<%=request.getContextPath()%>/js/test/bootstrap.min.js"></script>
        <script type="text/javascript" src="<%=request.getContextPath()%>/js/test/bootstrapValidator.js"></script>
        <script type="text/javascript" src="<%=request.getContextPath()%>/js/test/json2.js"></script>
        <script type="text/javascript" src="<%=request.getContextPath()%>/js/common/selDealerTypeList.js"></script>
        <script type="text/javascript" src="<%=request.getContextPath()%>/js/common/selCityInfoList.js"></script>
        <script type="text/javascript" src="<%=request.getContextPath()%>/js/common/selSystemInfoList.js"></script>
        <script type="text/javascript" src="<%=request.getContextPath()%>/js/common/selIdTypeList.js"></script>
        <script type="text/javascript" src="<%=request.getContextPath()%>/js/common/selBankCodeList.js"></script>
        <script type="text/javascript" src="<%=request.getContextPath()%>/js/common/selGameInfoList.js"></script>
        <script type="text/javascript" src="<%=request.getContextPath()%>/js/businessRole/DealerModify.js"></script>

    </body>
</html>
