<%-- 
    Document   : gameLuckyRuleSelect
    Created on : 2014-7-20, 10:32:15
    Author     : chenliping
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>游戏开奖次数列表</title>
        <link href="<%=request.getContextPath()%>/css/style/bootstrap.css" rel="stylesheet">
        <link href="<%=request.getContextPath()%>/css/style/style.css" rel="stylesheet"> 
    </head>
    <body>
        <div class="content">
            <div class="mainbar">
                <div class="matter">
                    <div class="container">
                        <div class="row">
                            <div class="col-md-12">
                                <div class="widget">
                                    <div class="widget-head">
                                        <div class="pull-left">游戏开奖次数列表</div>
                                        <div class="widget-icons pull-right">
                                            <a href="#" class="wminimize"><i class="icon-chevron-up"></i></a> 
                                            <a href="#" class="wclose"><i class="icon-remove"></i></a>
                                        </div>  
                                        <div class="clearfix">
                                        </div>
                                    </div>
                                    <div class="widget-content">
                                        <form class="form-inline" role="form" action="">
                                            <div class="form-group">                            
                                                <p class="form-control-static">&nbsp;&nbsp;游戏名称：</p>
                                            </div>  
                                            <div class="form-group">    
                                                <select name="gameid" id="gameid" class="form-control col-md-2">
                                                    <!--<option value="-1">--请选择游戏--</option>-->
                                                </select>                    
                                            </div>
                                            <div class="form-group"> 
                                                &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;   <button type="button" id="search_button"  class="btn btn-primary active" >查询</button>
                                            </div>
                                            <div class="form-group"> 
                                                &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;  <button type="button" id="toAdd_button"  class="btn btn-primary" >新增</button>
                                            </div>
                                        </form>
                                        <table class="table table-hover table-bordered">
                                            <tr>
                                                <th>游戏名称</th>
                                                <th>开奖名称</th>
                                                <th>开奖次数</th>
                                                <th>开奖基本号码个数</th>
                                                <th>开奖特别号码个数</th>
                                                <th>中奖注数</th>
                                                <th>工作状态</th>
                                                <th>操作</th>
                                            </tr>
                                            <%--<c:forEach items="${listGameMultiOpen}" var="gml" >--%>
                                            <c:forEach items="${listGameMultiOpen}" var="gml" varStatus="status">
                                                <c:choose>
                                                    <c:when test="${status.index % 2 == 0}">
                                                        <tr class="warning">
                                                            <th>${gml.game_name}</th>
                                                            <th>${gml.open_name}</th>
                                                            <th>${gml.open_id}</th>
                                                            <th>${gml.basic_ball_num}</th>
                                                            <th>${gml.special_ball_num}</th>
                                                            <th>${gml.prize_num}</th>
                                                            <th>${gml.work_status=="1"?"启用":"不启用"}</th>
                                                            <th><a href="<%=request.getContextPath()%>/gameMultiOpen/2modify?gameid=${gml.game_id}&&openid=${gml.open_id}">编辑</a></th>
                                                        </tr>
                                                    </c:when>
                                                    <c:otherwise>
                                                        <tr>
                                                            <th>${gml.game_name}</th>
                                                            <th>${gml.open_name}</th>
                                                            <th>${gml.open_id}</th>
                                                            <th>${gml.basic_ball_num}</th>
                                                            <th>${gml.special_ball_num}</th>
                                                            <th>${gml.prize_num}</th>
                                                            <th>${gml.work_status=="1"?"启用":"不启用"}</th>
                                                            <th><a href="<%=request.getContextPath()%>/gameMultiOpen/2modify?gameid=${gml.game_id}&&openid=${gml.open_id}">编辑</a></th>
                                                        </tr>
                                                    </c:otherwise>
                                                </c:choose>
                                            </c:forEach>
                                        </table>    

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
        <script type="text/javascript" src="<%=request.getContextPath()%>/js/game/gameMultiOpenList.js"></script>
        <script type="text/javascript" src="<%=request.getContextPath()%>/js/common/selGameInfoList.js"></script>
        <script type="text/javascript">
            var contextPath = "<%=request.getContextPath()%>";
        </script>
    </body>
</html>
