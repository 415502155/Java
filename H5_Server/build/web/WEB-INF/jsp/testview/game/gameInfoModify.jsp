<%@page import="java.util.ArrayList"%>
<%@page import="java.util.List"%>
<%@page import="com.bestinfo.bean.game.GameInfo"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>修改游戏</title>
        <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no">
        <link href="<%=request.getContextPath()%>/css/style/bootstrap-combined.min.css" rel="stylesheet">
        <link rel="stylesheet" href="<%=request.getContextPath()%>/css/style/bootstrap.css" >
        <link href="<%=request.getContextPath()%>/css/style/bootstrapValidator.css" rel="stylesheet">
        <link rel="stylesheet" href="<%=request.getContextPath()%>/css/style/bootstrap-datetimepicker.min.css">
        <link href="<%=request.getContextPath()%>/css/style/style.css" rel="stylesheet"> 

        <script src="<%=request.getContextPath()%>/js/test/jquery-1.10.2.min.js"></script>
        <script type="text/javascript">
            var contextPath = "<%=request.getContextPath()%>";
        </script>
        <script type="text/javascript"  src="<%=request.getContextPath()%>/js/test/bootstrap.min.js"></script>
        <script type="text/javascript" src="<%=request.getContextPath()%>/js/test/bootstrapValidator.js"></script>
        <script src="<%=request.getContextPath()%>/js/test/bootstrap-datetimepicker.min_1.js" charset="UTF-8"></script>  
        <script type="text/javascript" src="<%=request.getContextPath()%>/js/common/selGameInfoList.js"></script>
        <script type="text/javascript"  src="<%=request.getContextPath()%>/js/game/gameInfoModify.js" ></script>
        <script type="text/javascript"  src="<%=request.getContextPath()%>/js/common/common.js" ></script>
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
                                        <div class="pull-left">修改游戏信息</div>
                                        <!--                                        <div class="widget-icons pull-right">
                                                                                    <a href="#" class="wminimize"><i class="icon-chevron-up"></i></a> 
                                                                                    <a href="#" class="wclose"><i class="icon-remove"></i></a>
                                                                                </div>-->
                                        <div class="clearfix"></div>
                                    </div>

                                    <div class="widget-content">
                                        <div class="padd">
                                            <!-- Form starts.  -->
                                            <form class="form-horizontal"  role="form" id="gameInfoModifyForm" method="post">
                                                <input type="hidden" class="form-control" id="game_id" name="game_id" value="${gameInfo.game_id}" readonly/>
                                                <div class="form-group">
                                                    <label for="name-1" class="control-label col-md-2 ">游戏名称：</label>
                                                    <div class="col-md-3">
                                                        <input type="text" class="form-control" id="game_name" name="game_name" value="${gameInfo.game_name}">
                                                    </div>
                                                    <label for="name-1" class="control-label col-md-2">游戏类型：</label>
                                                    <div class="col-md-3">
                                                        <input type="text" class="form-control" id="game_type" name="game_type" value="${gameInfo.game_type}">
                                                    </div>
                                                </div>
                                                <div class="form-group">
                                                    <label for="name-1" class="control-label col-md-2 ">游戏简称：</label>
                                                    <div class="col-md-3">
                                                        <input type="text" class="form-control" id="short_name" name="short_name" value="${gameInfo.short_name}">
                                                    </div>
                                                    <label for="name-1" class="control-label col-md-2 ">游戏代码：</label>
                                                    <div class="col-md-3">
                                                        <input type="text" class="form-control" id="game_code" name="game_code" value="${gameInfo.game_code}">
                                                    </div>
                                                </div>
                                                <div class="form-group">
                                                    <label for="name-1" class="control-label col-md-2 ">玩法个数：</label>
                                                    <div class="col-md-3">
                                                        <input type="text" class="form-control" id="play_num" name="play_num" value="${gameInfo.play_num}">
                                                    </div>
                                                    <label for="name-1" class="control-label col-md-2 ">开奖号码可重复：</label>
                                                    <div class="col-md-3">
                                                        <select name="repeat_select" id="repeat_select" class="form-control">
                                                            <option value="1" <c:if test="${gameInfo.repeat_select == '1'}">selected</c:if>>可重复</option>
                                                            <option value="0"  <c:if test="${gameInfo.repeat_select == '0'}">selected</c:if>>不可重复</option>
                                                            </select>
                                                        </div>
                                                    </div>
                                                    <div class="form-group">             
                                                        <label for="name-1" class="control-label col-md-2 ">期结束时间：</label>
                                                        <div class="input-append date form_datetime col-md-3" id="datetimepicker" >
                                                            <input data-format="hh:mm:ss" class="form-control" type="text" id="draw_time" name="draw_time" value="${gameInfo.draw_time}">
                                                        <span class="add-on"><i class="icon-th"></i></span>
                                                    </div> 

                                                    <label for="name-1" class="control-label col-md-2 ">开奖频度类型：</label>
                                                    <div class="col-md-3">
                                                        <select name="draw_period_type" id="draw_period_type" class="form-control">
                                                            <option value="1" <c:if test="${gameInfo.draw_period_type == '1'}">selected</c:if>>日</option>
                                                            <option value="2"  <c:if test="${gameInfo.draw_period_type == '2'}">selected</c:if>>周</option>
                                                            <option value="3" <c:if test="${gameInfo.draw_period_type == '3'}">selected</c:if>>月</option>
                                                            <option value="4"  <c:if test="${gameInfo.draw_period_type == '4'}">selected</c:if>>年</option>
                                                            </select>
                                                        </div>
                                                    </div>    
                                                    <div class="form-group">
                                                        <label for="name-1" class="control-label col-md-2 ">自动新期时间：</label>
                                                        <div class="input-append date form_datetime col-md-3" id="datetimepicker" >
                                                            <input data-format="hh:mm:ss" class="form-control" type="text" id="init_time" name="init_time" value="${gameInfo.init_time}">
                                                        <span class="add-on"><i class="icon-th"></i></span>
                                                    </div> 
                                                    <label for="name-1" class="control-label col-md-2 ">开奖最小号码：</label>
                                                    <div class="col-md-3">
                                                        <input type="text" class="form-control"  id="open_min_no" name="open_min_no" value="${gameInfo.open_min_no}">
                                                    </div>
                                                </div>
                                                <div class="form-group">        
                                                    <label for="name-1" class="control-label col-md-2 ">自动结算时间：</label>
                                                    <div class="input-append date form_datetime col-md-3" id="datetimepicker" >
                                                        <input data-format="hh:mm:ss" class="form-control" type="text" id="stat_time" name="stat_time"  value="${gameInfo.stat_time}">
                                                        <span class="add-on"><i class="icon-th"></i></span>
                                                    </div> 
                                                    <label for="name-1" class="control-label col-md-2 ">开奖最大号码：</label>
                                                    <div class="col-md-3">
                                                        <input type="text" class="form-control"  id="open_max_no" name="open_max_no" value="${gameInfo.open_max_no}">
                                                    </div>
                                                </div>    
                                                <div class="form-group">
                                                    <label for="name-1" class="control-label col-md-2 ">日销售开始时间：</label>
                                                    <div class="input-append date form_datetime col-md-3" id="datetimepicker" >
                                                        <input data-format="hh:mm:ss" class="form-control" type="text" id="begin_time" name="begin_time" value="${gameInfo.begin_time}">
                                                        <span class="add-on"><i class="icon-th"></i></span>
                                                    </div> 
                                                    <label for="name-1" class="control-label col-md-2 ">二区开奖最小号码：</label>
                                                    <div class="col-md-3">
                                                        <input type="text" class="form-control"  id="open_min_blue_no" name="open_min_blue_no" value="${gameInfo.open_min_blue_no}">
                                                    </div>
                                                </div>
                                                <div class="form-group">        
                                                    <label for="name-1" class="control-label col-md-2 ">日销售结束时间：</label>
                                                    <div class="input-append date form_datetime col-md-3" id="datetimepicker" >
                                                        <input data-format="hh:mm:ss" class="form-control" type="text" id="end_time" name="end_time" value="${gameInfo.end_time}">
                                                        <span class="add-on"><i class="icon-th"></i></span>
                                                    </div> 
                                                    <label for="name-1" class="control-label col-md-2 ">二区开奖最大号码：</label>
                                                    <div class="col-md-3">
                                                        <input type="text" class="form-control"  id="open_max_blue_no" name="open_max_blue_no" value="${gameInfo.open_max_blue_no}">
                                                    </div>
                                                </div>  
                                                <div class="form-group">
                                                    <label for="name-1" class="control-label col-md-2 ">开奖基本号码个数：</label>
                                                    <div class="col-md-3">
                                                        <input type="text" class="form-control"  id="open_basic_num" name="open_basic_num" value="${gameInfo.open_basic_num}">
                                                    </div>
                                                    <label for="name-1" class="control-label col-md-2 ">开奖特别号码个数：</label>
                                                    <div class="col-md-3">
                                                        <input type="text" class="form-control"  id="open_special_num" name="open_special_num" value="${gameInfo.open_special_num}">
                                                    </div>
                                                </div>
                                                <div class="form-group">
                                                    <label for="name-1" class="control-label col-md-2 ">开奖蓝球号码个数：</label>
                                                    <div class="col-md-3">
                                                        <input type="text" class="form-control"  id="open_blue_num" name="open_blue_num" value="${gameInfo.open_blue_num}">
                                                    </div>
                                                    <label for="name-1" class="control-label col-md-2 ">开奖号码组数：</label>
                                                    <div class="col-md-3">
                                                        <input type="text" class="form-control"  id="lucky_no_group" name="lucky_no_group" value="${gameInfo.lucky_no_group}">
                                                    </div>
                                                </div> 
                                                <div class="form-group">
                                                    <label for="name-1" class="control-label col-md-2 ">最大开奖次数：</label>
                                                    <div class="col-md-3">
                                                        <input type="text" class="form-control"  id="open_num" name="open_num" value="${gameInfo.open_num}">
                                                    </div>
                                                    <label for="name-1" class="control-label col-md-2 ">奖级个数：</label>
                                                    <div class="col-md-3">
                                                        <input type="text" class="form-control"  id="prize_class_number" name="prize_class_number" value="${gameInfo.prize_class_number}">
                                                    </div>
                                                </div>
                                                <div class="form-group">
                                                    <label for="name-1" class="control-label col-md-2 ">固定奖级个数：</label>
                                                    <div class="col-md-3">
                                                        <input type="text" class="form-control"  id="fix_prize_class_number" name="fix_prize_class_number" value="${gameInfo.fix_prize_class_number}">
                                                    </div>
                                                    <label for="name-1" class="control-label col-md-2 ">一级中心最大可兑奖级别：</label>
                                                    <div class="col-md-3">
                                                        <input type="text" class="form-control"  id="center_max_cash_class" name="center_max_cash_class" value="${gameInfo.center_max_cash_class}">
                                                    </div>
                                                </div>
                                                <div class="form-group">
                                                    <label for="name-1" class="control-label col-md-2 ">一级中心兑奖上限金额(不含)：</label>
                                                    <div class="col-md-3">
                                                        <input type="text" class="form-control"  id="center_max_cash_money" name="center_max_cash_money" value="${gameInfo.center_max_cash_money}">
                                                    </div>
                                                    <label for="name-1" class="control-label col-md-2 ">二级中心最大可兑奖级别：</label>
                                                    <div class="col-md-3">
                                                        <input type="text" class="form-control" id="department_max_cash_class" name="department_max_cash_class" value="${gameInfo.department_max_cash_class}">
                                                    </div>
                                                </div>
                                                <div class="form-group">
                                                    <label for="name-1" class="control-label col-md-2 ">二级中心兑奖上限金额(不含)：</label>
                                                    <div class="col-md-3">
                                                        <input type="text" class="form-control"  id="department_max_cash_money" name="department_max_cash_money" value="${gameInfo.department_max_cash_money}">
                                                    </div>
                                                    <label for="name-1" class="control-label col-md-2 ">投注机最大可兑奖级别：</label>
                                                    <div class="col-md-3">
                                                        <input type="text" class="form-control" id="terminal_max_cash_class" name="terminal_max_cash_class" value="${gameInfo.terminal_max_cash_class}">
                                                    </div>
                                                </div>   
                                                <div class="form-group">
                                                    <label for="name-1" class="control-label col-md-2 ">投注机兑奖上限金额(不含)：</label>
                                                    <div class="col-md-3">
                                                        <input type="text" class="form-control" id="terminal_max_cash_money" name="terminal_max_cash_money" value="${gameInfo.terminal_max_cash_money}">
                                                    </div>
                                                    <label for="name-1" class="control-label col-md-2 ">当前期号：</label>
                                                    <div class="col-md-3">
                                                        <input type="text" class="form-control" id="cur_draw_id" name="cur_draw_id" value="${gameInfo.cur_draw_id}">
                                                    </div>
                                                </div>  
                                                <!--                                                        <label for="name-1" class="control-label col-md-2 ">每期开奖日：</label>
                                                                                                        <div class="col-md-3">
                                                                                                            <input type="text" class="form-control" id="draw_period" name="draw_period" value="${gameInfo.draw_period}">
                                                                                                        </div>-->
                                                <!--                                                </div>  -->
                                                <div class="form-group">
                                                    <label for="name-1" class="control-label text-right col-md-3 ">每期开奖日：</label>
                                                    <div class="col-md-9">
                                                        <%
                                                            GameInfo gameInfo = (GameInfo) request.getAttribute("gameInfo");
                                                            String draw_period = gameInfo.getDraw_period();
                                                            List<String> list = new ArrayList<String>();
                                                            while (draw_period.length() > 0) {
                                                                list.add(draw_period.substring(0, 1));
                                                                draw_period = draw_period.substring(1);
                                                            }
                                                        %>
                                                        <label class="checkbox-inline"><input type="checkbox" id="Mon" name="Mon" value="<%=list.get(0)%>"  <%if ("1".equals(list.get(0))) { %> checked="checked" <%}%> />星期一</label> &nbsp; &nbsp; &nbsp;
                                                        <label class="checkbox-inline"><input type="checkbox" id="Tues" name="Tues" value="<%=list.get(1)%>"  <%if ("1".equals(list.get(1))) { %> checked="checked" <%}%> />星期二</label> &nbsp; &nbsp; &nbsp;
                                                        <label class="checkbox-inline"><input type="checkbox" id="Wednes" name="Wednes" value="<%=list.get(2)%>"  <%if ("1".equals(list.get(2))) { %> checked="checked" <%}%> />星期三</label> &nbsp; &nbsp; &nbsp;
                                                        <label class="checkbox-inline"><input type="checkbox" id="Thurs" name="Thurs" value="<%=list.get(3)%>"  <%if ("1".equals(list.get(3))) { %> checked="checked" <%}%> />星期四</label> &nbsp; &nbsp; &nbsp;
                                                        <label class="checkbox-inline"><input type="checkbox" id="Fri" name="Fri" value="<%=list.get(4)%>"  <%if ("1".equals(list.get(4))) { %> checked="checked" <%}%> />星期五</label> &nbsp; &nbsp; &nbsp;
                                                        <label class="checkbox-inline"><input type="checkbox" id="Satur" name="Satur"  value="<%=list.get(5)%>"  <%if ("1".equals(list.get(5))) { %> checked="checked" <%}%> />星期六</label> &nbsp; &nbsp; &nbsp;
                                                        <label class="checkbox-inline"><input type="checkbox" id="Sun" name="Sun" value="<%=list.get(6)%>"  <%if ("1".equals(list.get(6))) { %> checked="checked" <%}%> />星期日</label>
                                                    </div>
                                                </div>

                                                <div class="form-group">
                                                    <label for="name-1" class="control-label col-md-2 ">兑奖期天数：</label>
                                                    <div class="col-md-3">
                                                        <input type="text" class="form-control" id="cash_period_day" name="cash_period_day" value="${gameInfo.cash_period_day}">
                                                    </div>
                                                    <label for="name-1" class="control-label col-md-2 ">多期期数：</label>
                                                    <div class="col-md-3">
                                                        <input type="text" class="form-control"  id="multi_draw_number" name="multi_draw_number" value="${gameInfo.multi_draw_number}">
                                                    </div>
                                                </div>
                                                <div class="form-group">
                                                    <label for="name-1" class="control-label text-right col-md-3 ">注信息存储方法：</label>
                                                    <div class="col-md-3">
                                                        <input type="text" class="form-control" id="bet_line_way" name="bet_line_way" value="${gameInfo.bet_line_way}" /> 
                                                    </div>
                                                    <label for="name-1" class="control-label text-right col-md-3 ">单式票最多注数：</label>
                                                    <div class="col-md-3">
                                                        <input type="text" class="form-control"  id="single_stake_num" name="single_stake_num" value="${gameInfo.single_stake_num}" />
                                                    </div>
                                                </div>
                                                <div class="form-group">
                                                    <label for="name-1" class="control-label col-md-2 ">联合销售类型：</label>
                                                    <div class="col-md-3">
                                                        <select name="union_type" id="union_type" class="form-control">
                                                            <option value="0" <c:if test="${gameInfo.union_type == '0'}">selected</c:if>>地区</option>
                                                            <option value="1" <c:if test="${gameInfo.union_type == '1'}">selected</c:if> >本省</option>
                                                            <option value="2" <c:if test="${gameInfo.union_type == '2'}">selected</c:if> >区域</option>
                                                            <option value="3" <c:if test="${gameInfo.union_type == '3'}">selected</c:if> >全国</option>
                                                            </select>
                                                        </div>
                                                        <label for="name-1" class="control-label col-md-2 ">游戏在用标志：</label>
                                                        <div class="col-md-3">
                                                            <input type="text" class="form-control"  id="used_mark" name="used_mark" value="${gameInfo.used_mark}">
                                                    </div>
                                                </div>  
                                                <div class="form-group">
                                                    <label for="name-1" class="control-label col-md-2 ">注销许可标志：</label>
                                                    <div class="col-md-3">
                                                        <input type="text" class="form-control" id="undo_permit" name="undo_permit" value="${gameInfo.undo_permit}">
                                                    </div>
                                                    <label for="name-1" class="control-label col-md-2 ">销售许可标志：</label>
                                                    <div class="col-md-3">
                                                        <input type="text" class="form-control"  id="sale_mark" name="sale_mark" value="${gameInfo.sale_mark}">
                                                    </div>
                                                </div> 
                                                <div class="form-group">
                                                    <label for="name-1" class="control-label col-md-2 ">兑奖许可标志：</label>
                                                    <div class="col-md-3">
                                                        <input type="text" class="form-control" id="cash_mark" name="cash_mark" value="${gameInfo.cash_mark}">
                                                    </div>
                                                    <label for="name-1" class="control-label col-md-2 ">过期中奖数据保存时间：</label>
                                                    <div class="col-md-3">
                                                        <input type="text" class="form-control" id="data_save_day" name="data_save_day" value="${gameInfo.data_save_day}">
                                                    </div>
                                                </div> 
                                                <div class="form-group">
                                                    <label for="name-1" class="control-label col-md-2 ">本游戏系统版本号：</label>
                                                    <div class="col-md-3">
                                                        <input type="text" class="form-control" id="game_version" name="game_version" value="${gameInfo.game_version}">
                                                    </div>
                                                    <label for="name-1" class="control-label col-md-2 ">投注机单票默认最大金额：</label>
                                                    <div class="col-md-3">
                                                        <input type="text" class="form-control" id="terminal_bet_money" name="terminal_bet_money" value="${gameInfo.terminal_bet_money}">
                                                    </div>
                                                </div> 
                                                <div class="form-group">
                                                    <label for="name-1" class="control-label col-md-2 ">游戏风险控制方式：</label>
                                                    <div class="col-md-3">
                                                        <input type="text" class="form-control" id="game_control_type" name="game_control_type" value="${gameInfo.game_control_type}">
                                                    </div>
                                                    <label for="name-1" class="control-label col-md-2 ">风险控制组数：</label>
                                                    <div class="col-md-3">
                                                        <input type="text" class="form-control" id="control_group_num" name="control_group_num" value="${gameInfo.control_group_num}">
                                                    </div>
                                                </div> 
                                                <div class="form-group">
                                                    <label for="name-1" class="control-label col-md-2 ">相关游戏：</label>

<!--<script> var bindGameId = ${gameInfo.bind_game_id}</script>-->

                                                    <div class="col-md-3">
                                                        <input type="hidden" id="bindGameId" value="${gameInfo.bind_game_id}"></input>
                                                        <select name="bind_game_id" id="bind_game_id" class="form-control">
                                                            <option value="-1">无</option>
                                                        </select>
                                                    </div>
                                                    <label for="name-1" class="control-label col-md-2 ">兑奖方式：</label>
                                                    <div class="col-md-3">
                                                        <select name="cash_method" id="cash_method" class="form-control">
                                                            <option value="0" <c:if test="${gameInfo.cash_method == '0'}">selected</c:if> >按奖级兑奖</option>
                                                            <option value="1" <c:if test="${gameInfo.cash_method == '1'}">selected</c:if> >按奖金兑奖</option>
                                                            </select>
                                                        </div>
                                                    </div>
                                                    <div class="form-group">
                                                        <label for="name-1" class="control-label col-md-2 ">派奖奖金精度：</label>
                                                        <div class="col-md-3">
                                                            <input type="text" class="form-control" id="prize_precision" name="prize_precision" value="${gameInfo.prize_precision}">
                                                    </div>
                                                    <label for="name-1" class="control-label col-md-2 ">快开游戏标记：</label>
                                                    <div class="col-md-3">
                                                        <select name="keno_game" id="keno_game" class="form-control">
                                                            <option value="0" <c:if test="${gameInfo.keno_game == '0'}">selected</c:if> >普通</option>
                                                            <option value="1" <c:if test="${gameInfo.keno_game == '1'}">selected</c:if> >快开</option>
                                                            </select>
                                                        </div>
                                                    </div> 
                                                    <div class="form-group">
                                                        <label for="name-1" class="control-label col-md-2 ">keno期数：</label>
                                                        <div class="col-md-3">
                                                            <input type="text" class="form-control" id="keno_draw_num" name="keno_draw_num" value="${gameInfo.keno_draw_num}">
                                                    </div>
                                                    <label for="name-1" class="control-label col-md-2 ">每期销售时间长度：</label>
                                                    <div class="col-md-3">
                                                        <input type="text" class="form-control" id="draw_length" name="draw_length" value="${gameInfo.draw_length}">
                                                    </div>
                                                </div> 
                                                <div class="form-group">
                                                    <label for="name-1" class="control-label col-md-2 ">KENO销售多期期数：</label>
                                                    <div class="col-md-3">
                                                        <input type="text" class="form-control" id="multi_keno_num" name="multi_keno_num" value="${gameInfo.multi_keno_num}">
                                                    </div>
                                                    <label for="name-1" class="control-label col-md-2 ">下期开始时间：</label>
                                                    <div class="col-md-3">
                                                        <input type="text" class="form-control" id="next_draw_time" name="next_draw_time" value="${gameInfo.next_draw_time}">
                                                    </div>
                                                </div> 
                                                <div class="form-group">
                                                    <label for="name-1" class="control-label col-md-2 ">发布公告时间：</label>
                                                    <div class="col-md-3">
                                                        <input type="text" class="form-control" id="bulletin_time" name="bulletin_time" value="${gameInfo.bulletin_time}">
                                                    </div>
                                                    <label for="name-1" class="control-label col-md-2 ">公告间隔：</label>
                                                    <div class="col-md-3">
                                                        <input type="text" class="form-control" id="re_bulletin_time" name="re_bulletin_time" value="${gameInfo.re_bulletin_time}">
                                                    </div>
                                                </div> 
                                                <hr />
                                                <div class="form-group">
                                                    <div class="text-center">
                                                        <input type="button" id="modify_button" class="btn btn-primary" value="确认提交">
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
        <script type="text/javascript">
            $(function() {
                $('.form_datetime').datetimepicker({
                    language: 'zh',
                    todayBtn: 1,
                    autoclose: true,
                    forceParse: 0,
                    pickDate: false
                });
            });
        </script>
    </body>
</html>
