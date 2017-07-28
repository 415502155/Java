<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>玩法列表</title>
        <link rel="stylesheet" href="<%=request.getContextPath()%>/css/style/bootstrap.css" >
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
                                        <div class="pull-left">游戏玩法查询</div>
                                        <div class="widget-icons pull-right">
                                            <a href="#" class="wminimize"><i class="icon-chevron-up"></i></a> 
                                            <a href="#" class="wclose"><i class="icon-remove"></i></a>
                                        </div>  
                                        <div class="clearfix">
                                        </div>
                                    </div>
                                    <div  class="widget-content">
                                        <div   class="table-responsive">
                                            <!--search content start-->
                                            <form class="form-inline" role="form" action="">
                                                <div class="form-group">                            
                                                    <p class="form-control-static">&nbsp;&nbsp;游戏名称：</p>
                                                </div>  
                                                <div class="form-group">    
                                                    <select name="game_id" id="game_id" class="form-control col-md-2">
                                                        <!--<option value="-1">--请选择游戏--</option>-->
                                                    </select>                        
                                                </div>
                                                <div class="form-group">                            
                                                    <p class="form-control-static">&nbsp;&nbsp;&nbsp;&nbsp;玩法名称：</p>
                                                </div>
                                                <div class="form-group">    
                                                    <select name="play_id" id="play_id" class="form-control col-md-2">
                                                        <option value="0">--全部玩法--</option>
                                                    </select>                      
                                                </div>
                                                &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<button type="button" id="search_button"  class="btn btn-primary active" >查询</button>
                                                &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<button type="button" id="add_button"  class="btn btn-primary" >新增</button>
                                            </form>
                                            <!--search content end--> 
                                            <!--<hr />-->
                                            <table class="table table-hover table-bordered">
                                                <tr>
                                                    <!--<th>游戏编号</th>-->
                                                    <th>游戏名称</th>
                                                    <th>玩法名称</th>
                                                    <th>总期号</th>
                                                    <th>玩法类型</th>
                                                    <th>单注金额</th>
                                                    <th>单注最大倍数</th>
                                                    <th>投注号码个数</th>
                                                    <th>最小号码</th>
                                                    <th>最大号码</th>
                                                    <th>蓝球个数</th>
                                                    <th>最小蓝球</th>
                                                    <th>最大蓝球</th>
                                                    <th>号码可重复</th>
                                                    <th>号码有序</th>
                                                    <th>符号个数</th>
                                                    <th>奖金和比例</th>
                                                    <th>工作状态</th>
                                                    <th>操作</th>
                                                </tr>
                                                <c:forEach items="${listGamePlayInfo}" var="gamePlay" varStatus="status">
                                                    <c:choose>
                                                        <c:when test="${status.index % 2 == 0}">
                                                            <tr class="warning">
                                                                <th>${gamePlay.game_name}</th>
                                                                <th>${gamePlay.play_name}</th>
                                                                <th>${gamePlay.draw_id}</th>
                                                                <th>${gamePlay.play_type}</th>
                                                                <th>${gamePlay.stakes_price}</th>
                                                                <th>${gamePlay.max_multiple}</th>
                                                                <th>${gamePlay.bet_no_num}</th>
                                                                <th>${gamePlay.bet_min_no}</th>
                                                                <th>${gamePlay.bet_max_no}</th>
                                                                <th>${gamePlay.blue_no_num}</th>
                                                                <th>${gamePlay.blue_min_no}</th>
                                                                <th>${gamePlay.blue_max_no}</th>
                                                                 <th>${gamePlay.no_repeat=="0"?"不可重复":"可重复"}</th>
                                                                <th>${gamePlay.no_order=="0" ?"无序":"有序"}</th>
                                                                <th>${gamePlay.sign_num}</th>
                                                                <th>${gamePlay.prize_proportion}</th>
                                                                <th>${gamePlay.work_status=="1"?"启用":"不启用"}</th>
                                                                <th>
                                                                    <a href="<%= request.getContextPath()%>/gameplayinfo/2modify?game_id=${gamePlay.game_id}&play_id=${gamePlay.play_id}">修改</a>
                                                                    <!--<a href="<%=request.getContextPath()%>/gameplayinfo/2details?game_id=${gamePlay.game_id}&play_id=${gamePlay.play_id}">详情</a>-->
                                                                </th>
                                                            </tr>
                                                        </c:when>
                                                        <c:otherwise>
                                                            <tr>
                                                                <th>${gamePlay.game_name}</th>
                                                                <th>${gamePlay.play_name}</th>
                                                                 <th>${gamePlay.draw_id}</th>
                                                                <th>${gamePlay.play_type}</th>
                                                                <th>${gamePlay.stakes_price}</th>
                                                                <th>${gamePlay.max_multiple}</th>
                                                                <th>${gamePlay.bet_no_num}</th>
                                                                <th>${gamePlay.bet_min_no}</th>
                                                                <th>${gamePlay.bet_max_no}</th>
                                                                <th>${gamePlay.blue_no_num}</th>
                                                                <th>${gamePlay.blue_min_no}</th>
                                                                <th>${gamePlay.blue_max_no}</th>
                                                                <th>${gamePlay.no_repeat=="0"?"不可重复":"可重复"}</th>
                                                                <th>${gamePlay.no_order=="0" ?"无序":"有序"}</th>
                                                                <th>${gamePlay.sign_num}</th>
                                                                <th>${gamePlay.prize_proportion}</th>
                                                                <th>${gamePlay.work_status=="1"?"启用":"不启用"}</th>
                                                                <th>
                                                                    <a href="<%=request.getContextPath()%>/gameplayinfo/2modify?game_id=${gamePlay.game_id}&play_id=${gamePlay.play_id}">修改</a>
                                                                    <!--<a href="<%=request.getContextPath()%>/gameplayinfo/2details?game_id=${gamePlay.game_id}&play_id=${gamePlay.play_id}">详情</a>-->
                                                                </th>
                                                            </tr>
                                                        </c:otherwise>
                                                    </c:choose>
                                                </c:forEach>
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
        <!--JS区域-->
        <script src="<%=request.getContextPath()%>/js/test/jquery-1.10.2.min.js"></script>
        <script type="text/javascript" src="<%=request.getContextPath()%>/js/game/gamePlayInfoList.js"></script>
        <script type="text/javascript" src="<%=request.getContextPath()%>/js/common/selGameInfoList.js"></script>
        <script type="text/javascript">
            var contextPath = "<%=request.getContextPath()%>";
        </script>
    </body>
</html>
