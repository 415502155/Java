<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>游戏资金比例列表</title>
        <link href="<%=request.getContextPath()%>/css/style/bootstrap.css" rel="stylesheet">
        <link rel="stylesheet" href="<%=request.getContextPath()%>/css/style/bootstrap.css" >
        <link href="<%=request.getContextPath()%>/css/style/style.css" rel="stylesheet"> 
    </head>
    <body>
        <!--<form action="<%=request.getContextPath()%>/gameFundsPropor/select" method="post">-->
        <div class="content">
            <div class="mainbar">
                <div class="matter">
                    <div class="container">
                        <div class="row">
                            <div class="col-md-12">
                                <div class="widget">
                                    <div class="widget-head">
                                        <div class="pull-left">资金分配列表</div>
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
                                                    <!--<option value="-1">--请选择游戏名称--</option>-->
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
                                                <th>期名称</th>
                                                <th>公益金总比例</th>
                                                <th>中彩公益金</th>
                                                <th>省级公益金</th>
                                                <th>发行费总比例</th>
                                                <th>中彩发行费</th>
                                                <th>省级发行费</th>
                                                <th>市级发行费</th>
                                                <th>站点发行费</th>
                                                <th>返奖比例</th>
                                                <th>调节基金比例</th>
                                                <th>操作</th>
                                            </tr>
                                            <c:forEach items="${listGameFundsProportion}" var="gameFp" varStatus="status">
                                                <c:choose>
                                                    <c:when test="${status.index % 2 == 0}">
                                                        <tr class="warning">
                                                            <th>${gameFp.game_name}</th>
                                                            <th>${gameFp.draw_name}</th>
                                                            <th>${gameFp.welfare_proportion}</th>
                                                            <th>${gameFp.welfare_proportion_clp}</th>
                                                            <th>${gameFp.welfare_proportion_province}</th>
                                                            <th>${gameFp.issue_proportion}</th>
                                                            <th>${gameFp.issue_proportion_clp}</th>
                                                            <th>${gameFp.issue_proportion_province}</th>
                                                            <th>${gameFp.issue_proportion_city}</th>
                                                            <th>${gameFp.issue_proportion_station}</th>
                                                            <th>${gameFp.return_proportion}</th>
                                                            <th>${gameFp.reserve_proportion}</th>
                                                            <th><a href="<%=request.getContextPath()%>/gameFundsPropor/2modify?gameid=${gameFp.game_id}&&drawid=${gameFp.draw_id}">编辑</a></th>
                                                        </tr>
                                                    </c:when>
                                                    <c:otherwise>
                                                        <tr class="">
                                                            <th>${gameFp.game_name}</th>
                                                            <th>${gameFp.draw_name}</th>
                                                            <th>${gameFp.welfare_proportion}</th>
                                                            <th>${gameFp.welfare_proportion_clp}</th>
                                                            <th>${gameFp.welfare_proportion_province}</th>
                                                            <th>${gameFp.issue_proportion}</th>
                                                            <th>${gameFp.issue_proportion_clp}</th>
                                                            <th>${gameFp.issue_proportion_province}</th>
                                                            <th>${gameFp.issue_proportion_city}</th>
                                                            <th>${gameFp.issue_proportion_station}</th>
                                                            <th>${gameFp.return_proportion}</th>
                                                            <th>${gameFp.reserve_proportion}</th>
                                                            <th><a href="<%=request.getContextPath()%>/gameFundsPropor/2modify?gameid=${gameFp.game_id}&&drawid=${gameFp.draw_id}">编辑</a></th>
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
        <script type="text/javascript" src="<%=request.getContextPath()%>/js/game/gameFundsProportionList.js"></script>
        <script type="text/javascript" src="<%=request.getContextPath()%>/js/common/selGameInfoList.js"></script>
        <script type="text/javascript">
            var contextPath = "<%=request.getContextPath()%>";
        </script>
    </body>
</html>
