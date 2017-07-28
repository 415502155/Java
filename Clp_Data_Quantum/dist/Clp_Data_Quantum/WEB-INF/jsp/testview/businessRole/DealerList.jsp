<%--
    Document   : DealerList
    Created on : 2014-8-4, 21:19:24
    Author     : hhhh
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no">
        <title>代销商信息列表</title>
        <link href="<%=request.getContextPath()%>/css/style/bootstrap.css" rel="stylesheet">
        <link rel="stylesheet"  href="<%=request.getContextPath()%>/css/style/style.css" >
        <style>
            .thClass{
                width:300px
            }
        </style>
    </head>
    <body>
        <div class="content">
            <div class="mainbar">
                <div class="matter">
                    <div class="container">
                        <!-- Table -->
                        <div class="row">
                            <div class="col-md-12">
                                <div class="widget">
                                    <div class="widget-head">
                                        <div class="pull-left">代销商信息列表</div>
                                        <div class="widget-icons pull-right">
                                            <a href="#" class="wminimize"><i class="icon-chevron-up"></i></a> 
                                            <a href="#" class="wclose"><i class="icon-remove"></i></a>
                                        </div>  
                                        <div class="clearfix"></div>
                                    </div>
                                    <div class="widget-content">
                                        <div class="table-responsive">
                                            <form class="form-inline" role="form">
                                                <div class="form-group">                            
                                                    <p class="form-control-static">&nbsp;&nbsp;代销商名称：</p>
                                                </div>  
                                                <div class="form-group">    
                                                    <label class="sr-only" for="dealer_name">代销商名称</label>
                                                    <select name="dealer_name" id="dealer_name" class="form-control"></select>                            
                                                </div>
                                                <input type="button" id="search_button"  class="btn btn-primary active" value="查询"/>
                                                <input type="button" id="add_button"  class="btn btn-primary active" value="注册"/>
                                            </form>
                                            <hr />
                                            <table class="table table-striped table-bordered table-hover">
                                                <thead>
                                                    <tr>
                                                        <th class="thClass">代销商编号</th>
                                                        <th class="thClass">代销商名称</th>
                                                        <th class="thClass">代销商类型</th>
                                                        <th class="thClass">地址</th>
                                                        <th class="thClass">负责人姓名</th>
                                                        <th class="thClass">负责人电话</th>
                                                        <th class="thClass">联系人</th>
                                                        <th class="thClass">联系电话</th>
                                                        <th class="thClass">工作电话</th>
                                                        <th class="thClass">省系统编号</th>
                                                        <th class="thClass">地市编号</th>
                                                        <th class="thClass">电子邮箱</th>
                                                        <th class="thClass">固定电话</th>
                                                        <th class="thClass">证件类型编号</th>
                                                        <th class="thClass">证件编号</th>
                                                        <th class="thClass">银行编码</th>
                                                        <th class="thClass">账户名称</th>
                                                        <th class="thClass">银行卡号</th>
                                                        <th class="thClass">备注</th>
                                                        <th class="thClass">终端情况</th>
                                                        <th class="thClass">注册时间</th>
                                                        <th class="thClass">使用状态</th>
                                                        <th class="thClass">游戏特权</th>
                                                        <th class="thClass">操作</th>
                                                    </tr>
                                                </thead>
                                                <tbody>
                                                    <c:forEach items="${dealerInfoList}" var="dealerInfo" >
                                                        <tr>
                                                            <th class="thClass">${dealerInfo.dealer_id}</th>
                                                            <th class="thClass">${dealerInfo.dealer_name}</th>
                                                            <th class="thClass">
                                                                <input type="hidden" value="${dealerInfo.dealer_type}"/>
                                                                ${dealerInfo.dealer_type_name}
                                                            </th>
                                                            <th class="thClass">${dealerInfo.dealer_address}</th>
                                                            <th class="thClass">${dealerInfo.owner_name}</th>
                                                            <th class="thClass">${dealerInfo.owner_phone}</th>
                                                            <th class="thClass">${dealerInfo.link_name}</th>
                                                            <th class="thClass">${dealerInfo.link_phone}</th>
                                                            <th class="thClass">${dealerInfo.dealer_phone}</th>
                                                            <th class="thClass">
                                                                <input type="hidden" value="${dealerInfo.province_id}"/>
                                                                ${dealerInfo.province_name}
                                                            </th>
                                                            <th class="thClass">
                                                                <input type="hidden" value="${dealerInfo.city_id}"/>
                                                                ${dealerInfo.city_name}
                                                            </th>
                                                            <th class="thClass">${dealerInfo.email}</th>
                                                            <th class="thClass">${dealerInfo.phone_no}</th>
                                                            <th class="thClass">
                                                                <input type="hidden" value="${dealerInfo.id_type_id}"/>
                                                                ${dealerInfo.id_type_name}
                                                            </th>
                                                            <th class="thClass">${dealerInfo.ID_no}</th>
                                                            <th class="thClass">
                                                                <input type="hidden" value="${dealerInfo.bank_id}"/>
                                                                ${dealerInfo.bank_name}
                                                            </th>
                                                            <th class="thClass">${dealerInfo.account_name}</th>
                                                            <th class="thClass">${dealerInfo.account_card}</th>
                                                            <th class="thClass">${dealerInfo.note}</th>
                                                            <th class="thClass">${dealerInfo.terminal_condition}</th>
                                                            <th class="thClass">
                                                                <fmt:formatDate value="${dealerInfo.regist_time}" type="both" />
                                                            </th>
                                                            <th class="thClass">
                                                                ${dealerInfo.work_status == '1'?"启用":"不启用"}
                                                            </th>
                                                            <th class="thClass">
                                                                <a href="javascript:void(0)" onclick="dealerPrivilege(${dealerInfo.dealer_id});">游戏特权</a>
                                                            </th>
                                                            <th class="thClass">
                                                                <a href="javascript:void(0)" onclick="edit(${dealerInfo.dealer_id});">修改</a>
                                                            </th>
                                                        </tr>
                                                    </c:forEach>
                                                </tbody>
                                            </table>
                                        </div>
                                        <div class="widget-foot">
                                            <ul class="pagination pull-right">
                                                <li><a href="#">Prev</a></li>
                                                <li><a href="#">1</a></li>
                                                <li><a href="#">2</a></li>
                                                <li><a href="#">3</a></li>
                                                <li><a href="#">4</a></li>
                                                <li><a href="#">Next</a></li>
                                            </ul>
                                            <div class="clearfix"></div> 
                                        </div>
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
        <script type="text/javascript" src="<%=request.getContextPath()%>/js/businessRole/DealerList.js"></script>

    </body>
</html>
