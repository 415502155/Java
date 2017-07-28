<%-- 
    Document   : gameMultiOpen
    Created on : 2014-7-24, 21:00:01
    Author     : chenliping
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>游戏开奖次数增加</title>
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
                                <div class="widget wgreen">
                                    <div class="widget-head">
                                        <div class="pull-left">编辑游戏开奖次数</div>
                                        <div class="widget-icons pull-right">
                                            <a href="#" class="wminimize"><i class="icon-chevron-up"></i></a> 
                                            <a href="#" class="wclose"><i class="icon-remove"></i></a>
                                        </div>
                                        <div class="clearfix"></div>
                                    </div>

                                    <div class="widget-content">
                                        <div class="padd">
                                            <!-- Form starts.  -->
                                            <form class="form-horizontal"  role="form" id="gameMultiOpenModifyForm" method="post">
                                                <input type="hidden" class="form-control" name="game_id" id="game_id" value="${gameMultiOpen.game_id}">
                                                <div class="form-group">
                                                    <label for="game_id" class="col-md-2 control-label">游戏名称：</label>
                                                    <div class="col-md-5">
                                                        <input type="text" class="form-control" value="${gameMultiOpen.game_name}" readonly="true">
                                                    </div>
                                                </div>
                                                <div class="form-group">
                                                    <label for="open_id" class="col-md-2 control-label">开奖次数：</label>
                                                    <div class="col-md-5">
                                                        <input type="text" class="form-control" name="open_id" id="open_id" value="${gameMultiOpen.open_id}" readonly="true">
                                                    </div>
                                                </div>
                                                <div class="form-group">
                                                    <label for="open_name" class="col-md-2 control-label">开奖名称：</label>
                                                    <div class="col-md-5">
                                                        <input type="text" class="form-control" name="open_name" id="open_name" value="${gameMultiOpen.open_name}">
                                                    </div>
                                                </div>
                                                <div class="form-group">
                                                    <label for="basic_ball_num" class="col-md-2 control-label">开奖基本号码个数：</label>
                                                    <div class="col-md-5">
                                                        <input type="text" class="form-control" name="basic_ball_num" id="basic_ball_num" value="${gameMultiOpen.basic_ball_num}">
                                                    </div>
                                                </div>
                                                <div class="form-group">
                                                    <label for="special_ball_num" class="col-md-2 control-label">开奖特别号码个数：</label>
                                                    <div class="col-md-5">
                                                        <input type="text" class="form-control" name="special_ball_num" id="special_ball_num" value="${gameMultiOpen.special_ball_num}">
                                                    </div>
                                                </div>
                                                <div class="form-group">
                                                    <label for="prize_num" class="col-md-2 control-label">中奖注数：</label>
                                                    <div class="col-md-5">
                                                        <input type="text" class="form-control" name="prize_num" id="prize_num" value="${gameMultiOpen.prize_num}">
                                                    </div>
                                                </div>
                                                <div class="form-group">
                                                    <label for="work_status" class="col-md-2 control-label">工作状态：</label>
                                                    <div class="col-md-5">
                                                        <select name="work_status" id="work_status" class="form-control">
                                                            <option value="1" <c:if test="${gameMultiOpen.work_status == '1'}">selected</c:if> >启用 </option>
                                                            <option value="2" <c:if test="${gameMultiOpen.work_status == '2'}">selected</c:if>   >不启用</option>
                                                        </select>
                                                        <!--<input type="text" class="form-control" name="work_status" id="work_status" value="${gameMultiOpen.work_status}">-->
                                                    </div>
                                                </div>
                                                <hr />
                                                <div class="form-group">
                                                    <div class="col-md-offset-1 col-md-9">
                                                        <input type="button" id="edit_button" class="btn btn-primary" value="确认提交">
                                                    </div>
                                                </div>
                                            </form>
                                            <!-- Form end.  -->
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
        <script src="<%=request.getContextPath()%>/js/test/jquery-1.10.2.min.js"></script>
        <script type="text/javascript"  src="<%=request.getContextPath()%>/js/test/bootstrap.min.js"></script>
        <script type="text/javascript" src="<%=request.getContextPath()%>/js/test/bootstrapValidator.js"></script>
        <script type="text/javascript" src="<%=request.getContextPath()%>/js/game/gameMultiOpenModify.js"></script>
        <script type="text/javascript">
            var contextPath = "<%=request.getContextPath()%>";
        </script>
    </body>
</html>
