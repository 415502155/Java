<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>游戏期次信息查询</title>
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
                                        <div class="pull-left">期信息查询</div>
                                        <div class="widget-icons pull-right">
                                            <a href="#" class="wminimize"><i class="icon-chevron-up"></i></a> 
                                            <a href="#" class="wclose"><i class="icon-remove"></i></a>
                                        </div>  
                                        <div class="clearfix"></div>
                                    </div>
                                    <div class="widget-content">
                                        <div class="table-responsive">
                                            <form class="form-inline" role="form" action="<%=request.getContextPath()%>/gamedraw/list">
                                                <div class="form-group">                            
                                                    <p class="form-control-static">&nbsp;&nbsp;请选择游戏：</p>
                                                </div>  
                                                <div class="form-group">    
                                                    <label class="sr-only" for="game_id">游戏名</label>
                                                    <select name="game_id" id="game_id" class="form-control"></select>                            
                                                </div>
                                                <button type="submit" class="btn btn-primary active">查询</button>
                                            </form>
                                            <hr />
                                            <table class="table table-striped table-bordered table-hover">
                                                <thead>
                                                    <tr>
                                                        <th>游戏名称</th>
                                                        <!--<th>期号</th>-->
                                                        <th>期名称</th>
                                                        <th>销售开始时间</th>
                                                        <th>销售结束时间</th>
                                                        <th>兑奖开始时间</th>
                                                        <th>兑奖结束时间</th>
                                                        <th>期状态</th>
                                                        <!--                                                        <th>快开期数</th>
                                                                                                                <th>开始快开期号</th>
                                                                                                                <th>结束快开期号</th>-->
                                                        <th colspan="3">操作</th>
                                                    </tr>
                                                </thead>
                                                <tbody>
                                                    <c:forEach items="${page.rows}" var="game" >
                                                        <tr>
                                                            <td>${game.gameName}
                                                                <input id="draw_id" type="hidden" value="${game.game_id}"/>
                                                                <input id="draw_id" type="hidden" value="${game.draw_id}"/>
                                                            </td>
                                                            <td>${game.draw_name}</td>
                                                            <td><fmt:formatDate value="${game.sales_begin}" type="both"/></td>
                                                            <td><fmt:formatDate value="${game.sales_end}" type="both"/></td>
                                                            <td><fmt:formatDate value="${game.cash_begin}" type="both"/></td>
                                                            <td><fmt:formatDate value="${game.cash_end}" type="both"/></td>
                                                            <td>${game.drawProStatusName}</td>
                                                            <c:if test="${game.keno_draw_num gt 0}">
<!--                                                                <td>${game.keno_draw_num}</td>
                                                                <td>${game.begin_keno_draw_id}</td>
                                                                <td>${game.end_keno_draw_id}</td>                                                            -->
                                                                <td>
                                                                    <a href="<%=request.getContextPath()%>/gamedraw/gameKDrawPage?game_id=${game.game_id}&draw_id=${game.draw_id}" class="btn btn-large btn-primary">查询快开期信息</a>
                                                                </td>
                                                            </c:if>
                                                            <td><a href="<%=request.getContextPath()%>/gamedraw/2modify?game_id=${game.game_id}&draw_id=${game.draw_id}"  class="btn btn-large btn-primary">修改</a></td>
                                                            <td><a href="javascript:void(0)" onclick="newDraw(${game.game_id},${game.draw_id},${game.keno_draw_num})" class="btn btn-large btn-primary">开新期</a></td>
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
        <div class="modal fade" id="myModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
            <div class="modal-dialog">
                <div class="modal-content">
                    <div class="modal-header">
                        <h4 class="modal-title" id="myModalLabel">Modal title</h4>
                    </div>
                    <div class="modal-body">
                        ....
                    </div>
                    <div class="modal-footer">
                        <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
                        <button type="button" class="btn btn-primary">Save changes</button>
                    </div>
                </div>
            </div>
        </div>                                            


        <script src="<%=request.getContextPath()%>/js/test/jquery-1.10.2.min.js"></script>
        <script type="text/javascript" src="<%=request.getContextPath()%>/js/test/bootstrap.min.js"></script>

        <script type="text/javascript" src="<%=request.getContextPath()%>/js/common/selGameInfoList.js"></script>
        <script type="text/javascript">
                                                                var contextPath = "<%=request.getContextPath()%>";
                                                                $(function() {
                                                                    getGameInfoList("#game_id");
                                                                });
//                                                                $('.myModal').appendTo($('body'));
                                                                function newDraw(gameId, drawId, keno_draw_num) {
                                                                    $.ajax({
                                                                        type: "POST",
                                                                        url: "<%=request.getContextPath()%>/gamedraw/newDraw",
                                                                        data: {"game_id": gameId, "draw_id": drawId,"keno_draw_num":keno_draw_num},
                                                                        success: function(data) {
                                                                            alert(data.msg);
                                                                        },
                                                                        error: function(request) {
                                                                            alert("Connection error");
                                                                        }
                                                                    });
                                                                }
        </script>
    </body>
</html>
