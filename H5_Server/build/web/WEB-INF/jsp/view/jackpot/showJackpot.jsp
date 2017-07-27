<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>奖池信息</title>
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
                            <div class="col-md-22">
                                <div class="widget wgreen">
                                    <div class="widget-head">
                                        <div class="pull-left">奖池详情</div>
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
                                                <div class="form-group">
                                                    <label for="game_id" class="col-md-2 control-label">游戏编号：</label>
                                                    <div class="col-md-2">
                                                        <input type="text" class="form-control"  value="${jackpot.game_id}" >
                                                    </div>
                                                </div>
                                                <div class="form-group">
                                                    <label for="draw_id" class="col-md-2 control-label">总期号：</label>
                                                    <div class="col-md-2">
                                                        <input type="text" class="form-control" name="draw_id" id="draw_id" value="${jackpot.draw_id}" >
                                                    </div>
                                                </div>    
                                                <div class="form-group">
                                                    <label for="play_type" class="col-md-2 control-label">玩法编号：</label>
                                                    <div class="col-md-2">
                                                        <input type="text" class="form-control" name="play_type" id="play_type" value="${jackpot.play_id}" >
                                                    </div>
                                                </div>   
                                                <div class="form-group">
                                                    <label for="stakes_price" class="col-md-2 control-label">本期投注额：</label>
                                                    <div class="col-md-2">
                                                        <input type="text" class="form-control" name="stakes_price" id="stakes_price" value="${jackpot.sales_money}" >
                                                    </div>
                                                </div>
                                                <div class="form-group">
                                                    <label for="max_multiple" class="col-md-2 control-label">期初奖池：</label>
                                                    <div class="col-md-2">
                                                        <input type="text" class="form-control" name="max_multiple" id="max_multiple" value="${jackpot.begin_jackpot}" >
                                                    </div>
                                                </div>
                                                <div class="form-group">
                                                    <label for="bet_no_num" class="col-md-2 control-label">期末奖池：</label>
                                                    <div class="col-md-2">
                                                        <input type="text" class="form-control" name="bet_no_num" id="bet_no_num" value="${jackpot.end_jackpot}" >
                                                    </div>
                                                </div>
                                                <div class="form-group">
                                                    <label for="bet_min_no" class="col-md-2 control-label">销售额提取：</label>
                                                    <div class="col-md-2">
                                                        <input type="text" class="form-control" name="bet_min_no" id="bet_min_no" value="${jackpot.return_jackpot}" >
                                                    </div>
                                                </div>
                                                <div class="form-group">
                                                    <label for="bet_max_no" class="col-md-2 control-label">中奖金额：</label>
                                                    <div class="col-md-2">
                                                        <input type="text" class="form-control" name="bet_max_no" id="bet_max_no" value="${jackpot.prize_ticket_money}" >
                                                    </div>
                                                </div>
                                                <div class="form-group">
                                                    <label for="blue_no_num" class="col-md-2 control-label">追加奖池：</label>
                                                    <div class="col-md-2">
                                                        <input type="text" class="form-control" name="blue_no_num" id="blue_no_num" value="${jackpot.append_jackpot}" >
                                                    </div>
                                                </div>
                                                <div class="form-group">
                                                    <label for="blue_min_no" class="col-md-2 control-label">提取奖池：</label>
                                                    <div class="col-md-2">
                                                        <input type="text" class="form-control" name="blue_min_no" id="blue_min_no" value="${jackpot.get_jackpot}" >
                                                    </div>
                                                </div>
                                                <div class="form-group">
                                                    <label for="blue_max_no" class="col-md-2 control-label">未派出奖金：</label>
                                                    <div class="col-md-2">
                                                        <input type="text" class="form-control" name="blue_max_no" id="blue_max_no" value="${jackpot.not_give_money}" >
                                                    </div>
                                                </div>
												<div class="form-group">
                                                    <label for="max_multiple" class="col-md-2 control-label">期初调节基金：</label>
                                                    <div class="col-md-2">
                                                        <input type="text" class="form-control" name="max_multiple" id="max_multiple" value="${jackpot.begin_pool}" >
                                                    </div>
                                                </div>
                                                <div class="form-group">
                                                    <label for="bet_no_num" class="col-md-2 control-label">期末调节基金：</label>
                                                    <div class="col-md-2">
                                                        <input type="text" class="form-control" name="bet_no_num" id="bet_no_num" value="${jackpot.end_pool}" >
                                                    </div>
                                                </div>
                                                <div class="form-group">
                                                    <label for="bet_min_no" class="col-md-2 control-label">销售额提取：</label>
                                                    <div class="col-md-2">
                                                        <input type="text" class="form-control" name="bet_min_no" id="bet_min_no" value="${jackpot.return_pool}" >
                                                    </div>
                                                </div>
                                                <div class="form-group">
                                                    <label for="bet_max_no" class="col-md-2 control-label">取整余额：</label>
                                                    <div class="col-md-2">
                                                        <input type="text" class="form-control" name="bet_max_no" id="bet_max_no" value="${jackpot.round_money}" >
                                                    </div>
                                                </div>
                                                <div class="form-group">
                                                    <label for="blue_no_num" class="col-md-2 control-label">派奖补足提取：</label>
                                                    <div class="col-md-2">
                                                        <input type="text" class="form-control" name="blue_no_num" id="blue_no_num" value="${jackpot.fill_prize}" >
                                                    </div>
                                                </div>
                                                <div class="form-group">
                                                    <label for="blue_min_no" class="col-md-2 control-label">追加基金：</label>
                                                    <div class="col-md-2">
                                                        <input type="text" class="form-control" name="blue_min_no" id="blue_min_no" value="${jackpot.append_pool}" >
                                                    </div>
                                                </div>
                                                <div class="form-group">
                                                    <label for="blue_max_no" class="col-md-2 control-label">提取基金：</label>
                                                    <div class="col-md-2">
                                                        <input type="text" class="form-control" name="blue_max_no" id="blue_max_no" value="${jackpot.get_pool}" >
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
    </body>
</html>
