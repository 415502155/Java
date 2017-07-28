<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>快开期次信息</title>
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
                                        <div class="pull-left">快开期信息</div>
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
                                                        <th>游戏名称</th>
                                                        <th>大期名称</th>
                                                        <th>快开期名称</th>
                                                        <th>期类型</th>
                                                        <th>销售开始时间</th>
                                                        <th>销售结束时间</th>
                                                        <th>兑奖开始时间</th>
                                                        <th>兑奖结束时间</th>
                                                        <th>期状态</th>
                                                        <th>期序号</th>
                                                        <th>本期多期期数</th>
                                                        <!--<th colspan="3">操作</th>-->
                                                    </tr>
                                                </thead>
                                                <tbody>
                                                    <c:forEach items="${page.rows}" var="gameKDraw" >
                                                        <tr>
                                                            <td>${gameKDraw.gameName}</td>
                                                            <td>${gameKDraw.draw_id}</td>
                                                            <td>${gameKDraw.keno_draw_name}</td>
                                                            <td>${gameKDraw.draw_type}</td>
                                                            <td><fmt:formatDate value="${gameKDraw.sales_begin}" type="both"/></td>
                                                            <td><fmt:formatDate value="${gameKDraw.sales_end}" type="both"/></td>
                                                            <td><fmt:formatDate value="${gameKDraw.cash_begin}" type="both"/></td>
                                                            <td><fmt:formatDate value="${gameKDraw.cash_end}" type="both"/></td>
                                                            <td>${gameKDraw.kenoProStatusName}</td>
                                                            <td>${gameKDraw.kdraw_no}</td>
                                                            <td>${gameKDraw.multi_kdraw_num}</td>
                                                            <!--<td><a href="<%=request.getContextPath()%>/gamedraw/2modifyKDraw?game_id=${gameKDraw.game_id}&draw_id=${gameKDraw.draw_id}&keno_draw_id=${gameKDraw.keno_draw_id}"  class="btn btn-large btn-primary">修改</a></td>-->
                                                            <!--<td><a href="javascript:void(0)" onclick="newKDraw(${gameKDraw.game_id},${gameKDraw.draw_id},${gameKDraw.keno_draw_id})" class="btn btn-large btn-primary">开新期</a></td>-->
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

        <!-- Modal -->
        <!--        <div class="modal fade" id="myModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
                    <div class="modal-dialog">
                        <div class="modal-content">
                            <div class="modal-header">
                                <h4 class="modal-title" id="myModalLabel">Modal title</h4>
                            </div>
                            <div class="modal-body">
                                ...
                            </div>
                            <div class="modal-footer">
                                <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
                                <button type="button" class="btn btn-primary">Save changes</button>
                            </div>
                        </div>
                    </div>
                </div>                                            -->


        <script src="<%=request.getContextPath()%>/js/test/jquery-1.10.2.min.js"></script>
        <script type="text/javascript" src="<%=request.getContextPath()%>/js/test/bootstrap.min.js"></script>

        <!--<script type="text/javascript" src="<%=request.getContextPath()%>/js/common/selGameInfoList.js"></script>-->
        <!--        <script type="text/javascript">
                                                                        var contextPath = "<%=request.getContextPath()%>";
                                                                        $(function() {
                                                                            getGameInfoList("#game_id");
                                                                        });
        
                                                                        function newDraw(gameId, drawId) {
                                                                            $.ajax({
                                                                                type: "POST",
                                                                                url: "<%=request.getContextPath()%>/gamedraw/newDraw",
                                                                                data: {"game_id": gameId, "draw_id": drawId},
                                                                                success: function(data) {
                                                                                    alert(data.msg);
                                                                                },
                                                                                error: function(request) {
                                                                                    alert("Connection error");
                                                                                }
                                                                            });
                                                                        }
                </script>-->
    </body>
</html>
