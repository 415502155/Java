<%-- 
    Document   : gameClassInfoList
    Created on : 2014-8-7, 19:49:35
    Author     : YangRong 
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>游戏奖级列表</title>
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
                                <div class="widget wgreen">
                                    <div class="widget-head">
                                        <div class="pull-left">游戏奖级列表</div>
                                        <div class="widget-icons pull-right">
                                            <a href="#" class="wminimize"><i class="icon-chevron-up"></i></a> 
                                            <a href="#" class="wclose"><i class="icon-remove"></i></a>
                                        </div>
                                        <div class="clearfix"></div>
                                    </div>

                                    <div class="widget-content">
                                        <!--<div class="padd">-->
                                        <div class="table-responsive">
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
                                            <table class="table table-hover table-bordered">
                                                <tr>
                                                    <th>奖级编号</th>
                                                    <th>游戏名称</th>
                                                    <th>玩法名称</th>
                                                    <th>奖级名称</th>
                                                    <th>固定奖级</th>
                                                    <th>奖金和比例</th>
                                                    <th>与下级关系</th>
                                                    <th>差额或倍数</th>
                                                    <th>奖级状态</th>
                                                    <th>封顶金额</th>
                                                    <th>开奖次数</th>
                                                    <th>操作</th>
                                                </tr>
                                                <c:forEach items="${gameClassInfoList}" var="gameClass" varStatus="status">
                                                    <c:choose>
                                                        <c:when test="${status.index % 2 == 0}">
                                                            <tr class="warning">
                                                                <th>${gameClass.class_id}</th>
                                                                <th>${gameClass.game_name}</th>
                                                                <th>${gameClass.play_name}</th>
                                                                <th>${gameClass.class_name}</th>
                                                                <th>${gameClass.fix_mark}</th>
                                                                <th>${gameClass.prize_proportion}</th>
                                                                <th>${gameClass.last_relation}</th>
                                                                <th>${gameClass.last_diff}</th>
                                                                <th>${gameClass.class_status=='1'?'启用':'不启用'}</th>
                                                                <th>${gameClass.top_money}</th>
                                                                <th>${gameClass.open_id}</th>
                                                                <th><a href="<%=request.getContextPath()%>/gameClassInfo/2modify?game_id=${gameClass.game_id}&play_id=${gameClass.play_id}&class_id=${gameClass.class_id}">修改</a>
                                                                    <a href="#" onclick="deleteGameClassInfo(${gameClass.game_id},${gameClass.play_id},${gameClass.class_id})">删除</a></th>
                                                            </tr>
                                                        </c:when>
                                                        <c:otherwise>
                                                            <tr >
                                                                <th>${gameClass.class_id}</th>
                                                                <th>${gameClass.game_name}</th>
                                                                <th>${gameClass.play_name}</th>
                                                                <th>${gameClass.class_name}</th>
                                                                <th>${gameClass.fix_mark}</th>
                                                                <th>${gameClass.prize_proportion}</th>
                                                                <th>${gameClass.last_relation}</th>
                                                                <th>${gameClass.last_diff}</th>
                                                                 <th>${gameClass.class_status=='1'?'启用':'不启用'}</th>
                                                                <th>${gameClass.top_money}</th>
                                                                <th>${gameClass.open_id}</th>
                                                                <th><a href="<%=request.getContextPath()%>/gameClassInfo/2modify?game_id=${gameClass.game_id}&play_id=${gameClass.play_id}&class_id=${gameClass.class_id}">修改</a>
                                                                    <a href="#" onclick="deleteGameClassInfo(${gameClass.game_id},${gameClass.play_id},${gameClass.class_id})">删除</a></th>
                                                            </tr>
                                                        </c:otherwise>
                                                    </c:choose>
                                                </c:forEach>
                                            </table>   
                                        </div>
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
        <script src="<%=request.getContextPath()%>/js/test/jquery-1.10.2.min.js"></script>
        <script type="text/javascript" src="<%=request.getContextPath()%>/js/test/bootstrap.min.js"></script>
        <script type="text/javascript" src="<%=request.getContextPath()%>/js/common/selGameInfoList.js"></script>
        <script type="text/javascript">
                                                                        var contextPath = "<%=request.getContextPath()%>";
        </script>
        <script type="text/javascript">
            function deleteGameClassInfo(gameid, playid, classid){
                if (!confirm("确定删除?"))
                    return;
                $.ajax({
                    type: "POST",
                    url: contextPath + "/gameClassInfo/delGameClassInfo",
                    //                   url: contextPath + "/gameClassInfo/delGameClassInfo?"+"gameId="+gameid+"&playId="+playid+"&classId="+classid,
                    data: {gameId: gameid, playId: playid, classId: classid},
                    dataType: 'json',
                    success: function(data) {
                        alert(data.msg);
                        if ("success" == data.result) {
                            window.location.reload();
                        }
                    },
                    error: function(e) {
                        alert("网络异常，请稍后重试！");
                    }
                });

            }
            $(function() {
                /**
                 * 初始化该页面的所有下拉列表数据
                 */
                getGameInfoList("#game_id");
                $("#add_button").click(function() {
                    window.location.href = contextPath + "/gameClassInfo/2add";
                });
                $("#search_button").click(function() {
                    var gameid = $("#game_id").val();
                    //window.location.href = contextPath + "/gameClassInfo/list?game_id=" + gameid+"&&play_id=";
                    var playid = $("#play_id").val();
                    document.forms[0].action = contextPath + "/gameClassInfo/list";
                    document.forms[0].submit();
                });
                $("#game_id").change(function() {
                    var gameId = $("#game_id").val();
                    $.ajax({
                        type: "POST",
                        url: contextPath + "/gameplayinfo/getGamePlayInfoSelect?game_id=" + gameId,
                        error: function() {
                            alert("Connection error");
                        },
                        success: function(data) {
                            if (data !== null && "" !== data && data.length > 0) {
                                $("#play_id").empty();
                                $("<option value='0' >--全部玩法--</option>").appendTo($("#play_id"));
                                for (var i in data) {
                                    $("<option value=" + data[i]["play_id"] + " >" + data[i]["play_name"] + "</option>").appendTo($("#play_id"));
                                }
                            } else {
                                $("#play_id").empty();
                                $("<option value='0' >--全部玩法--</option>").appendTo($("#play_id"));
                            }
                        }
                    });
                });
            });
        </script>  
    </body>
</html>
