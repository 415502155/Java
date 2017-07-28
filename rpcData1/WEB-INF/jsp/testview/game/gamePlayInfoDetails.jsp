<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>游戏玩法详情</title>
        <link href="<%=request.getContextPath()%>/css/style/bootstrap.css" rel="stylesheet">
        <link href="<%=request.getContextPath()%>/css/style/style.css" rel="stylesheet"> 
        <link href="<%=request.getContextPath()%>/css/style/bootstrapValidator.css" rel="stylesheet">
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
                                        <div class="pull-left">游戏玩法详情</div>
                                        <div class="widget-icons pull-right">
                                            <a href="#" class="wminimize"><i class="icon-chevron-up"></i></a> 
                                            <a href="#" class="wclose"><i class="icon-remove"></i></a>
                                        </div>
                                        <div class="clearfix"></div>
                                    </div>

                                    <div class="widget-content">
                                        <div class="padd">
                                            <!-- Form starts.  -->
                                            <form id="" method="post"  class="form-horizontal">
                                                <input type="hidden" class="form-control" name="game_id" id="game_id" value="${gamePlayInfo.game_id}" readonly="true">
                                                <input type="hidden" class="form-control" name="play_id" id="play_id" value="${gamePlayInfo.play_id}" readonly="true">
                                                <div class="form-group">
                                                    <label for="game_id" class="col-md-2 control-label">游戏编号：</label>
                                                    <div class="col-md-5">
                                                        <input type="text" class="form-control"  value="${gamePlayInfo.game_name}" readonly="true">
                                                    </div>
                                                </div>
                                                <!--                                                <div class="form-group">
                                                                                                    <label for="play_id" class="col-md-2 control-label">玩法编号：</label>
                                                                                                    <div class="col-md-5">
                                                                                                        <input type="text" class="form-control" name="play_id" id="play_id" value="${gamePlayInfo.play_id}" readonly="true">
                                                                                                    </div>
                                                                                                </div>   -->
                                                <div class="form-group">
                                                    <label for="play_name" class="col-md-2 control-label">玩法名称：</label>
                                                    <div class="col-md-5">
                                                        <input type="text" class="form-control" name="play_name" id="play_name" value="${gamePlayInfo.play_name}" readonly="true">
                                                    </div>
                                                </div> 
                                                <div class="form-group">
                                                    <label for="draw_id" class="col-md-2 control-label">总期号：</label>
                                                    <div class="col-md-5">
                                                        <input type="text" class="form-control" name="draw_id" id="draw_id" value="${gamePlayInfo.draw_id}" readonly="true">
                                                    </div>
                                                </div>    
                                                <div class="form-group">
                                                    <label for="play_type" class="col-md-2 control-label">玩法类型：</label>
                                                    <div class="col-md-5">
                                                        <input type="text" class="form-control" name="play_type" id="play_type" value="${gamePlayInfo.play_type}" readonly="true">
                                                    </div>
                                                </div>   
                                                <div class="form-group">
                                                    <label for="stakes_price" class="col-md-2 control-label">单注金额：</label>
                                                    <div class="col-md-5">
                                                        <input type="text" class="form-control" name="stakes_price" id="stakes_price" value="${gamePlayInfo.stakes_price}" readonly="true">
                                                    </div>
                                                </div>
                                                <div class="form-group">
                                                    <label for="max_multiple" class="col-md-2 control-label">单注最大倍数：</label>
                                                    <div class="col-md-5">
                                                        <input type="text" class="form-control" name="max_multiple" id="max_multiple" value="${gamePlayInfo.max_multiple}" readonly="true">
                                                    </div>
                                                </div>
                                                <div class="form-group">
                                                    <label for="bet_no_num" class="col-md-2 control-label">投注号码个数：</label>
                                                    <div class="col-md-5">
                                                        <input type="text" class="form-control" name="bet_no_num" id="bet_no_num" value="${gamePlayInfo.bet_no_num}" readonly="true">
                                                    </div>
                                                </div>
                                                <div class="form-group">
                                                    <label for="bet_min_no" class="col-md-2 control-label">最小号码：</label>
                                                    <div class="col-md-5">
                                                        <input type="text" class="form-control" name="bet_min_no" id="bet_min_no" value="${gamePlayInfo.bet_min_no}" readonly="true">
                                                    </div>
                                                </div>
                                                <div class="form-group">
                                                    <label for="bet_max_no" class="col-md-2 control-label">最大号码：</label>
                                                    <div class="col-md-5">
                                                        <input type="text" class="form-control" name="bet_max_no" id="bet_max_no" value="${gamePlayInfo.bet_max_no}" readonly="true">
                                                    </div>
                                                </div>
                                                <div class="form-group">
                                                    <label for="blue_no_num" class="col-md-2 control-label">蓝球个数：</label>
                                                    <div class="col-md-5">
                                                        <input type="text" class="form-control" name="blue_no_num" id="blue_no_num" value="${gamePlayInfo.blue_no_num}" readonly="true">
                                                    </div>
                                                </div>
                                                <div class="form-group">
                                                    <label for="blue_min_no" class="col-md-2 control-label">最小蓝球：</label>
                                                    <div class="col-md-5">
                                                        <input type="text" class="form-control" name="blue_min_no" id="blue_min_no" value="${gamePlayInfo.blue_min_no}" readonly="true">
                                                    </div>
                                                </div>
                                                <div class="form-group">
                                                    <label for="blue_max_no" class="col-md-2 control-label">最大蓝球：</label>
                                                    <div class="col-md-5">
                                                        <input type="text" class="form-control" name="blue_max_no" id="blue_max_no" value="${gamePlayInfo.blue_max_no}" readonly="true">
                                                    </div>
                                                </div>
                                                <div class="form-group">
                                                    <label for="no_repeat" class="col-md-2 control-label">号码可重复：</label>
                                                    <div class="col-md-5">
                                                        <select name="no_repeat" id="no_repeat" class="form-control" readonly="true">
                                                            <option value="1" <c:if test="${gamePlayInfo.no_repeat == '1'}">selected</c:if> >可重复</option>
                                                            <option value="0" <c:if test="${gamePlayInfo.no_repeat == '0'}">selected</c:if>    >不可重复</option>
                                                            </select>
                                                        </div>
                                                    </div>
                                                    <div class="form-group">
                                                        <label for="no_order" class="col-md-2 control-label">号码有序：</label>
                                                        <div class="col-md-5">
                                                            <select name="no_order" id="no_order" class="form-control" readonly="true">
                                                                <option value="1" <c:if test=" ${gamePlayInfo.no_order == '1'}">selected</c:if>  >有序-排列</option>
                                                            <option value="0" <c:if test="${gamePlayInfo.no_order == '0'}">selected</c:if>    >无序-组合</option>
                                                            </select>
                                                        </div>
                                                    </div>
                                                    <div class="form-group">
                                                        <label for="sign_num" class="col-md-2 control-label">符号个数：</label>
                                                        <div class="col-md-5">
                                                            <input type="text" class="form-control" name="sign_num" id="sign_num" value="${gamePlayInfo.sign_num}" readonly="true">
                                                    </div>
                                                </div>
                                                <div class="form-group">
                                                    <label for="prize_proportion" class="col-md-2 control-label">奖金和比例：</label>
                                                    <div class="col-md-5">
                                                        <input type="text" class="form-control" name="prize_proportion" id="prize_proportion" value="${gamePlayInfo.prize_proportion}" readonly="true">
                                                    </div>
                                                </div>   
                                                <div class="form-group">
                                                    <label for="prize_proportion" class="col-md-2 control-label">工作状态：</label>
                                                    <div class="col-md-5">
                                                        <!--<input type="text" class="form-control" name="work_status" id="work_status" placeholder="工作状态">-->
                                                        <select name="work_status" id="work_status" class="form-control" readonly="true">
                                                            <option value="1" <c:if test="${gamePlayInfo.work_status == '0'}">selected</c:if>    >启用</option>
                                                            <option value="2" <c:if test="${gamePlayInfo.work_status == '1'}">selected</c:if>   >不启用</option>

                                                            </select>
                                                        </div>
                                                    </div>   
                                                    <hr />
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
        <script>
            var contextPath = "<%=request.getContextPath()%>";
        </script>
    </body>
</html>
