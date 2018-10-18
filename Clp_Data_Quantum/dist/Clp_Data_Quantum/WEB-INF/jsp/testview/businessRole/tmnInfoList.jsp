<%-- 
    Document   : tmnInfoList
    Created on : 2014-8-8, 13:14:03
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
        <title>终端信息列表</title>
        <link href="<%=request.getContextPath()%>/css/style/bootstrap.css" rel="stylesheet">
        <link rel="stylesheet"  href="<%=request.getContextPath()%>/css/style/style.css" >
        
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
                                        <div class="pull-left">终端信息列表</div>
                                        <div class="widget-icons pull-right">
                                            <a href="#" class="wminimize"><i class="icon-chevron-up"></i></a> 
                                            <a href="#" class="wclose"><i class="icon-remove"></i></a>
                                        </div>  
                                        <div class="clearfix"></div>
                                    </div>
                                    <div class="widget-content">                                        
                                        <div class="table-responsive"><br>
                                            <form class="form-inline" role="form">
                                                <div class="form-group">                            
                                                    <p class="form-control-static">&nbsp;&nbsp;市：</p>
                                                </div>  
                                                <div class="form-group">    
                                                    <label class="sr-only" for="city_id">市</label>
                                                    <select name="city_id" id="city_id" class="form-control"></select>                            
                                                </div>
                                                <div class="form-group">                            
                                                    <p class="form-control-static">&nbsp;&nbsp;投注机号：</p>
                                                </div>  
                                                <div class="form-group">    
                                                    <label class="sr-only" for="terminal_id">投注机号</label>
                                                    <input name="terminal_id" id="terminal_id" class="form-control" placeholder="投注机号"/>                            
                                                </div>
                                                <div class="form-group">                            
                                                    <p class="form-control-static">&nbsp;&nbsp;代销商编号：</p>
                                                </div>  
                                                <div class="form-group">    
                                                    <label class="sr-only" for="dealer_id">代销商编号</label>
                                                    <input name="dealer_id" id="dealer_id" class="form-control" placeholder="代销商编号"/>                          
                                                </div>
                                                <input type="button" id="search_button"  class="btn btn-primary" style="margin-left: 20px" value="查询"/>
                                                <input type="button" id="add_button"  class="btn btn-primary" value="注册"/>
                                            </form>
                                            <hr />
                                            <table class="table table-striped table-bordered table-hover">
                                                <thead>
                                                    <tr>
                                                        <th >投注机号</th>
                                                        <th >投注机初始化时间</th>
                                                        <th >加密芯片</th>
                                                        <th >地市</th>
                                                        <th >所属区县</th>
                                                        <th >站点名称</th>
                                                        <th >站点地址</th>
                                                        <th >站点电话</th>
                                                        <th >户主名</th>
                                                        <th >负责人电话</th>
                                                        <th >联系人</th>
                                                        <th >联系人电话</th>
                                                        <th >注册时间</th>
                                                        <th >软件类型</th>
                                                        <th >升级标记</th>
                                                        <th >软件版本</th>
                                                        <th >终端类型</th>
                                                        <th >终端状态</th>
                                                        <th >代销费内扣方式</th>
                                                        <th >销售注销实时扣款</th>
                                                        <th >兑奖实时扣款</th>
                                                        <th >通讯方式</th>
                                                        <th >拨号账户</th>
                                                        <th >拨号密码</th>
                                                        <th >默认扣款账户</th>
                                                        <th >代销商编号</th>
                                                        <!--<th >终端同步字</th>-->
                                                        <th >终端特权</th>
                                                        <th >操作</th>
                                                    </tr>
                                                </thead>
                                                <tbody id="tbody">
                                                    <c:forEach items="${tmnInfoList}" var="tmnInfo" >
                                                        <tr>
                                                            <th >
                                                                <input type="hidden" value="${tmnInfo.terminal_serial_no}" name="terminal_serial_no"/>
                                                                ${tmnInfo.terminal_id}
                                                                <input type="hidden" value="${tmnInfo.terminal_phy_id}" name="terminal_phy_id"/>
                                                            </th>
                                                            <th >${tmnInfo.terminal_initial_time}</th>
                                                            <th >${tmnInfo.safe_card_id}</th>
                                                            <th >
                                                                <input type="hidden" value="${tmnInfo.city_id}"/>
                                                                ${tmnInfo.city_name}
                                                            </th>
                                                            <th >
                                                                <input type="hidden" value="${tmnInfo.district_id}"/>
                                                                ${tmnInfo.district_name}
                                                            </th>
                                                            <th >${tmnInfo.station_name}</th>
                                                            <th >${tmnInfo.terminal_address}</th>
                                                            <th >
                                                                ${tmnInfo.station_phone}
                                                            </th>
                                                            <th >
                                                                ${tmnInfo.owner_name}
                                                            </th>
                                                            <th >${tmnInfo.owner_phone}</th>
                                                            <th >${tmnInfo.linkman}</th>
                                                            <th >
                                                                ${tmnInfo.linkman_phone}
                                                            </th>
                                                            <th >
                                                                <fmt:formatDate value="${tmnInfo.regist_date}" type="both" />
                                                            </th>
                                                            <th >
                                                                ${tmnInfo.software_name}
                                                            </th>
                                                            <th >${tmnInfo.upgrade_mark}</th>
                                                            <th >${tmnInfo.software_version}</th>
                                                            <th >${tmnInfo.terminal_type}</th>
                                                            <th >
                                                                ${tmnInfo.terminal_status == '1'?'启用':'不启用'}
                                                            </th>
                                                            <th >
                                                                ${tmnInfo.agentfee_type}
                                                            </th>
                                                            <th >${tmnInfo.tmn_sale_deduct}</th>
                                                            <th >${tmnInfo.tmn_cash_deduct}</th>
                                                            <th >${tmnInfo.comm_type}</th>
                                                            <th >${tmnInfo.dial_name}</th>
                                                            <th >${tmnInfo.dial_pwd}</th>
                                                            <th >${tmnInfo.account_id}</th>
                                                            <th >${tmnInfo.dealer_id}</th>
                                                            <!--<th >${tmnInfo.terminal_syn_no}</th>-->
                                                            <th >
                                                                <a href="javascript:void(0)" onclick="editPrivilege(${tmnInfo.terminal_id});">终端特权</a>
                                                            </th>
                                                            <th >
                                                                <a href="javascript:void(0)" onclick="edit(${tmnInfo.terminal_id});">修改</a>
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

        <script src="<%=request.getContextPath()%>/js/test/jquery-1.10.2.min.js"></script>
        <script type="text/javascript" src="<%=request.getContextPath()%>/js/common/selCityInfoList.js"></script>
        <script type="text/javascript" src="<%=request.getContextPath()%>/js/businessRole/TmnInfoList.js"></script>
        <script type="text/javascript">
             var contextPath = "<%=request.getContextPath()%>";
        </script>
    </body>
</html>
