<%-- 
    Document   : tmnPrivilege
    Created on : 2014-8-14, 13:59:45
    Author     : hhhh
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no">
        <title>终端特权注册</title>
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
                                        <div class="pull-left">终端特权注册</div>
                                        <div class="widget-icons pull-right">
                                            <a href="#" class="wminimize"><i class="icon-chevron-up"></i></a> 
                                            <a href="#" class="wclose"><i class="icon-remove"></i></a>
                                        </div>  
                                        <div class="clearfix"></div>
                                    </div>
                                    <div class="widget-content">
                                        <div class="table-responsive">
                                            <table class="table table-striped table-bordered table-hover">
                                                <thead>
                                                    <tr>
                                                        <th class="thClass">

                                                        </th>
                                                        <th class="thClass">游戏名称</th>
                                                        <th class="thClass">销售许可</th>
                                                        <th class="thClass">兑奖许可</th>
                                                        <th class="thClass">游戏许可</th>
                                                        <th class="thClass">注销许可</th>
                                                        <th class="thClass">预售许可</th>
                                                        <th class="thClass">本期代销费比例</th>
                                                        <th class="thClass">内扣代销费比例</th>
                                                        <th class="thClass">单票最小金额</th>
                                                        <th class="thClass">单票最大金额</th>
                                                        <th class="thClass">单期最大金额</th>
                                                        <th class="thClass">兑奖代销费比例</th>
                                                    </tr>
                                                </thead>
                                                <tbody id="tbody">
                                                    <c:forEach items="${giList}" var="gameInfo" >
                                                        <tr>
                                                            <th class="thClass">
                                                                <input name="terminalId" type="hidden" value="${terminalId}"/>
                                                                <input type="checkbox" name="cks"/>
                                                            </th>
                                                            <th class="thClass">
                                                                <input type="hidden" name="game_id" value="${gameInfo.game_id}"/>
                                                                ${gameInfo.game_name}
                                                            </th>
                                                            <th class="thClass">
                                                                <select name="sale_permit" class="form-control">
                                                                    <option value="0" selected>许可</option>
                                                                    <option value="1">不许可</option>
                                                                </select>
                                                            </th>
                                                            <th class="thClass">
                                                                <select name="cash_permit" class="form-control">
                                                                    <option value="0" selected>许可</option>
                                                                    <option value="1">不许可</option>
                                                                </select>
                                                            </th>
                                                            <th class="thClass">
                                                                <select name="game_permit" class="form-control">
                                                                    <option value="0" selected>许可</option>
                                                                    <option value="1">不许可</option>
                                                                </select>
                                                            </th>
                                                            <th class="thClass">
                                                                <select name="undo_permit" class="form-control">
                                                                    <option value="0" selected>许可</option>
                                                                    <option value="1">不许可</option>
                                                                </select>
                                                            </th>
                                                            <th class="thClass">
                                                                <select name="presell_permit" class="form-control">
                                                                    <option value="0" selected>许可</option>
                                                                    <option value="1">不许可</option>
                                                                </select>
                                                            </th>
                                                            <th class="thClass">
                                                                <input class="form-control col-md-1" placeholder="本期代销费比例" name="agent_fee_rate" type="text" value=""/>
                                                            </th>
                                                            <th class="thClass">
                                                                <input class="form-control col-md-1" placeholder="内扣代销费比例" name="cur_agent_rate" type="text" value=""/>
                                                            </th>
                                                            <th class="thClass">
                                                                <input class="form-control col-md-1" placeholder="单票最小金额" name="min_bet_money" type="text" value=""/>
                                                            </th>
                                                            <th class="thClass">
                                                                <input class="form-control col-md-1" placeholder="单票最大金额" name="max_bet_money" type="text" value="${gameInfo.terminal_bet_money}" readonly="true"/>
                                                            </th>
                                                            <th class="thClass">
                                                                <input class="form-control col-md-1" placeholder="单期最大金额" name="max_sales_money" type="text" value=""/>
                                                            </th>
                                                            <th class="thClass">
                                                                <input class="form-control col-md-1" placeholder="兑奖代销费比例" name="cash_fee_rate" type="text" value="${gameInfo.cash_fee_rate}"/>
                                                            </th>
                                                        </tr>
                                                    </c:forEach>
                                                </tbody>
                                            </table>
                                            <div class="text-center" style="margin:10px 0px">
                                                 <input type="button" id="edit_button"  class="btn btn-primary" value="注 册"/>
                                             </div>
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
        <script type="text/javascript" src="<%=request.getContextPath()%>/js/test/json2.js"></script>
        <script type="text/javascript" src="<%=request.getContextPath()%>/js/businessRole/tmnPrivilegeModify.js"></script>

    </body>
</html>
