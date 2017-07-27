<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>新增游戏</title>
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
        <script type="text/javascript"  src="<%=request.getContextPath()%>/js/game/gameInfoAdd.js" ></script>
        <script type="text/javascript"  src="<%=request.getContextPath()%>/js/common/common.js" ></script>
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
                                        <div class="pull-left">新增游戏信息</div>
                                        <!--                                        <div class="widget-icons pull-right">
                                                                                    <a href="#" class="wminimize"><i class="icon-chevron-up"></i></a> 
                                                                                    <a href="#" class="wclose"><i class="icon-remove"></i></a>
                                                                                </div>-->
                                        <div class="clearfix"></div>
                                    </div>

                                    <div class="widget-content">
                                        <!-- Form starts.  -->
                                        <form class="form-horizontal"  role="form" id="gameInfoAddForm" method="post">
                                            <div class="form-group" > 
                                                <label for="name-1" class="control-label col-md-2 ">游戏编号：</label>
                                                <div class="col-md-3">
                                                    <input type="text"  name="game_id" id="game_id" class="form-control" placeholder="游戏编号">
                                                </div>
                                            </div>
                                            <div class="form-group" > 
                                                <label for="name-1" class="control-label col-md-2 ">游戏名称：</label>
                                                <div class="col-md-3">
                                                    <input type="text"  id="game_name" name="game_name" class="form-control" placeholder="游戏名称">
                                                </div>   
                                                <label for="name-1" class="control-label col-md-2 ">游戏类型：</label>
                                                <div class="col-md-3">
                                                    <input type="text" id="game_type" name="game_type" class="form-control" placeholder="游戏类型">
                                                </div>
                                            </div>   
                                            <div class="form-group" > 
                                                <label for="name-1" class="control-label col-md-2 ">游戏简称：</label>
                                                <div class="col-md-3">
                                                    <input type="text" id="short_name" name="short_name" class="form-control" placeholder="游戏简称">
                                                </div>
                                                <label for="name-1" class="control-label col-md-2 ">游戏代码：</label>
                                                <div class="col-md-3">
                                                    <input type="text"  id="game_code" name="game_code" class="form-control" placeholder="游戏代码">
                                                </div>
                                            </div>   
                                            <div class="form-group" > 
                                                <label for="name-1" class="control-label col-md-2 ">玩法个数：</label>
                                                <div class="col-md-3">
                                                    <input type="text"  id="play_num" name="play_num" class="form-control" placeholder="玩法个数">
                                                </div>
                                                <label for="name-1" class="control-label col-md-2 ">开奖号码可重复：</label>
                                                <div class="col-md-3">
                                                    <select name="repeat_select" id="repeat_select" class="form-control">
                                                        <option value="1">可重复</option>
                                                        <option value="0" selected="true">不可重复</option>
                                                    </select>
                                                </div>
                                            </div>  
                                            <div class="form-group" > 
                                                <label for="name-1" class="control-label col-md-2 ">期结束时间：</label>
                                                <!--<div id="datetimepicker1" class="col-md-3 input-append time-picker">-->
                                                <div class="input-append date form_datetime col-md-3" id="datetimepicker" >
                                                    <input data-format="hh:mm:ss" class="form-control" type="text" id="draw_time" name="draw_time" placeholder="期结束时间">
                                                    <span class="add-on"><i class="icon-th"></i></span>
                                                </div> 
                                                <label for="name-1" class="control-label col-md-2 ">开奖频度类型：</label>
                                                <div class="col-md-3">
                                                    <select name="draw_period_type" id="draw_period_type" class="form-control">
                                                        <option value="1">日</option>
                                                        <option value="2">周</option>
                                                        <option value="3">月</option>
                                                        <option value="4">年</option>
                                                    </select>
                                                </div>
                                            </div>
                                            <div class="form-group" > 
                                                <label for="name-1" class="control-label col-md-2 ">自动新期时间：</label>
                                                <div class="input-append date form_datetime col-md-3" id="datetimepicker" >
                                                    <input data-format="hh:mm:ss" class="form-control" type="text" id="init_time" name="init_time" placeholder="自动新期时间">
                                                    <span class="add-on"><i class="icon-th"></i></span>
                                                </div> 
                                                <label for="name-1" class="control-label col-md-2 ">开奖最小号码：</label>
                                                <div class="col-md-3">
                                                    <input type="text" class="form-control" id="open_min_no" name="open_min_no" class="form-control" placeholder="开奖最小号码">
                                                </div>
                                            </div>
                                            <div class="form-group" > 
                                                <label for="name-1" class="control-label col-md-2 ">自动结算时间：</label>
                                                <div class="input-append date form_datetime col-md-3" id="datetimepicker" >
                                                    <input data-format="hh:mm:ss" class="form-control" type="text" id="stat_time" name="stat_time" placeholder="自动结算时间">
                                                    <span class="add-on"><i class="icon-th"></i></span>
                                                </div> 
                                                <label for="name-1" class="control-label col-md-2 ">开奖最大号码：</label>
                                                <div class="col-md-3">
                                                    <input type="text"  id="open_max_no" name="open_max_no" class="form-control" placeholder="开奖最大号码">
                                                </div>
                                            </div>
                                            <div class="form-group" > 
                                                <label for="name-1" class="control-label col-md-2 ">日销售开始时间：</label>
                                                <div class="input-append date form_datetime col-md-3" id="datetimepicker" >
                                                    <input data-format="hh:mm:ss" class="form-control" type="text" id="begin_time" name="begin_time" placeholder="日销售开始时间">
                                                    <span class="add-on"><i class="icon-th"></i></span>
                                                </div>
                                                <label for="name-1" class="control-label col-md-2 ">二区开奖最小号码：</label>
                                                <div class="col-md-3">
                                                    <input type="text" class="form-control" id="open_min_blue_no" name="open_min_blue_no" class="form-control" placeholder="二区开奖最小号码">
                                                </div>
                                            </div>
                                            <div class="form-group" > 
                                                <label for="name-1" class="control-label col-md-2 ">日销售结束时间：</label>
                                                <div class="input-append date form_datetime col-md-3" id="datetimepicker" >
                                                    <input data-format="hh:mm:ss" class="form-control" type="text" id="end_time" name="end_time" placeholder="日销售结束时间">
                                                    <span class="add-on"><i class="icon-th"></i></span>
                                                </div> 
                                                <label for="name-1" class="control-label col-md-2 ">二区开奖最大号码：</label>
                                                <div class="col-md-3">
                                                    <input type="text"  id="open_max_blue_no" name="open_max_blue_no" class="form-control" placeholder="二区开奖最大号码">
                                                </div>
                                            </div>
                                            <div class="form-group" > 
                                                <label for="name-1" class="control-label col-md-2 ">开奖基本号码个数：</label>
                                                <div class="col-md-3">
                                                    <input type="text" id="open_basic_num" name="open_basic_num" class="form-control" placeholder="开奖基本号码个数">
                                                </div>
                                                <label for="name-1" class="control-label col-md-2 ">开奖特别号码个数：</label>
                                                <div class="col-md-3">
                                                    <input type="text"  id="open_special_num" name="open_special_num" class="form-control" placeholder="开奖特别号码个数">
                                                </div>
                                            </div> 
                                            <div class="form-group" > 
                                                <label for="name-1" class="control-label col-md-2 ">开奖蓝球号码个数：</label>
                                                <div class="col-md-3">
                                                    <input type="text"  id="open_blue_num" name="open_blue_num" class="form-control" placeholder="开奖蓝球号码个数">
                                                </div>
                                                <label for="name-1" class="control-label col-md-2 ">开奖号码组数：</label>
                                                <div class="col-md-3">
                                                    <input type="text"  id="lucky_no_group" name="lucky_no_group" class="form-control" placeholder="开奖号码组数">
                                                </div>
                                            </div>  
                                            <div class="form-group" > 
                                                <label for="name-1" class="control-label col-md-2 ">最大开奖次数：</label>
                                                <div class="col-md-3">
                                                    <input type="text" id="open_num" name="open_num" class="form-control" placeholder="最大开奖次数">
                                                </div>
                                                <label for="name-1" class="control-label col-md-2 ">奖级个数：</label>
                                                <div class="col-md-3">
                                                    <input type="text"  id="prize_class_number" name="prize_class_number" class="form-control" placeholder="奖级个数">
                                                </div>
                                            </div>  
                                            <div class="form-group" > 
                                                <label for="name-1" class="control-label col-md-2 ">固定奖级个数：</label>
                                                <div class="col-md-3">
                                                    <input type="text" class="form-control" id="fix_prize_class_number" name="fix_prize_class_number" placeholder="固定奖级个数">
                                                </div>
                                                <label for="name-1" class="control-label col-md-2 ">一级中心最大可兑奖级别：</label>
                                                <div class="col-md-3">
                                                    <input type="text"  id="center_max_cash_class" name="center_max_cash_class" class="form-control" placeholder="一级中心最大可兑奖级别">
                                                </div>
                                            </div>
                                            <div class="form-group" > 
                                                <label for="name-1" class="control-label col-md-2 ">一级中心兑奖上限金额(不含)：</label>
                                                <div class="col-md-3">
                                                    <input type="text" class="form-control" id="center_max_cash_money" name="center_max_cash_money" placeholder="一级中心兑奖上限金额(不含)">
                                                </div>
                                                <label for="name-1" class="control-label col-md-2 ">二级中心最大可兑奖级别：</label>
                                                <div class="col-md-3">
                                                    <input type="text" id="department_max_cash_class" name="department_max_cash_class" class="form-control" placeholder="二级中心最大可兑奖级别">
                                                </div>
                                            </div>
                                            <div class="form-group" > 
                                                <label for="name-1" class="control-label col-md-2 ">二级中心兑奖上限金额(不含)：</label>
                                                <div class="col-md-3">
                                                    <input type="text" class="form-control" id="department_max_cash_money" name="department_max_cash_money" placeholder="二级中心兑奖上限金额(不含)">
                                                </div>
                                                <label for="name-1" class="control-label col-md-2 ">投注机最大可兑奖级别：</label>
                                                <div class="col-md-3">
                                                    <input type="text"  id="terminal_max_cash_class" name="terminal_max_cash_class" class="form-control" placeholder="投注机最大可兑奖级别">
                                                </div>
                                            </div>
                                            <div class="form-group" > 
                                                <label for="name-1" class="control-label col-md-2 ">投注机兑奖上限金额(不含)：</label>
                                                <div class="col-md-3">
                                                    <input type="text" class="form-control" id="terminal_max_cash_money" name="terminal_max_cash_money" placeholder="投注机兑奖上限金额(不含)">
                                                </div>
                                                <label for="name-1" class="control-label col-md-2 ">当前期号：</label>
                                                <div class="col-md-3">
                                                    <input type="text"  id="cur_draw_id" name="cur_draw_id" class="form-control" placeholder="当前期号">
                                                </div>
                                            </div>
                                            <!--<div class="form-group" >--> 
                                            <!--                                                    <label for="name-1" class="control-label col-md-2 ">开奖频度类型：</label>
                                                                                                <div class="col-md-8">
                                                                                                    <select name="draw_period_type" id="draw_period_type" class="form-control">
                                                                                                        <option value="1">日</option>
                                                                                                        <option value="2">周</option>
                                                                                                        <option value="3">月</option>
                                                                                                        <option value="4">年</option>
                                                                                                    </select>
                                                                                                </div>-->
                                            <!--                    <div class="col-md-3">
                                                                    <input type="text" class="form-control" id="draw_period_type" name="draw_period_type" placeholder="开奖频度类型">
                                                                </div>-->
                                            <!--                                                    <label for="name-1" class="control-label col-md-2 ">每期开奖日：</label>
                                                                                                <div class="col-md-3">
                                                                                                    <input type="text"   id="draw_period" name="draw_period" class="form-control" placeholder="每期开奖日">
                                                                                                </div>-->
                                            <!--</div>-->

                                            <div class="form-group" > 
                                                <label for="name-1" class="control-label text-right col-md-3 ">每期开奖日：</label>
                                                <div class="col-md-9">
                                                    <label class="checkbox-inline"><input type="checkbox" id="Mon" name="Mon" value=""   />星期一</label> &nbsp; &nbsp; &nbsp;
                                                    <label class="checkbox-inline"><input type="checkbox" id="Tues" name="Tues" value=""  />星期二</label> &nbsp; &nbsp; &nbsp;
                                                    <label class="checkbox-inline"><input type="checkbox" id="Wednes" name="Wednes" value=""  />星期三</label> &nbsp; &nbsp; &nbsp;
                                                    <label class="checkbox-inline"><input type="checkbox" id="Thurs" name="Thurs" value=""  />星期四</label> &nbsp; &nbsp; &nbsp;
                                                    <label class="checkbox-inline"><input type="checkbox" id="Fri" name="Fri" value="" />星期五</label> &nbsp; &nbsp; &nbsp;
                                                    <label class="checkbox-inline"><input type="checkbox" id="Satur" name="Satur" value=""  />星期六</label> &nbsp; &nbsp; &nbsp;
                                                    <label class="checkbox-inline"><input type="checkbox" id="Sun" name="Sun"  value=""  />星期日</label>
                                                </div>
                                            </div>    
                                            <div class="form-group" > 
                                                <label for="name-1" class="control-label col-md-2 ">兑奖期天数：</label>
                                                <div class="col-md-3">
                                                    <input type="text" class="form-control" id="cash_period_day" name="cash_period_day" placeholder="兑奖期天数">
                                                </div>
                                                <label for="name-1" class="control-label col-md-2 ">多期期数：</label>
                                                <div class="col-md-3">
                                                    <input type="text"   id="multi_draw_number" name="multi_draw_number" class="form-control" placeholder="多期期数">
                                                </div>
                                            </div>
                                            <div class="form-group" > 
                                                <label for="name-1" class="control-label col-md-2 ">注信息存放方法：</label>
                                                <div class="col-md-3">
                                                    <input type="text" class="form-control" id="bet_line_way" name="bet_line_way" placeholder="注信息存放方法">
                                                </div>
                                                <label for="name-1" class="control-label col-md-2 ">单式票最多注数：</label>
                                                <div class="col-md-3">
                                                    <input type="text"   id="single_stake_num" name="single_stake_num" class="form-control" placeholder="单式票最多注数">
                                                </div>
                                            </div>
                                            <div class="form-group" > 
                                                <label for="name-1" class="control-label col-md-2 ">联合销售类型：</label>
                                                <div class="col-md-3">
                                                    <select name="union_type" id="union_type" class="form-control">
                                                        <option value="0">地区</option>
                                                        <option value="1">本省</option>
                                                        <option value="2">区域</option>
                                                        <option value="3">全国</option>
                                                    </select>
                                                </div>
                                                <!--                    <div class="col-md-3">
                                                                        <input type="text" class="form-control" id="union_type" name="union_type" placeholder="联合销售类型">
                                                                    </div>-->
                                                <label for="name-1" class="control-label col-md-2 ">游戏在用标志：</label>
                                                <div class="col-md-3">
                                                    <input type="text"  id="used_mark" name="used_mark" class="form-control" placeholder="游戏在用标志">
                                                </div>
                                            </div>
                                            <div class="form-group" > 
                                                <label for="name-1" class="control-label col-md-2 ">注销许可标志：</label>
                                                <div class="col-md-3">
                                                    <input type="text" class="form-control" id="undo_permit" name="undo_permit" placeholder="注销许可标志">
                                                </div>
                                                <label for="name-1" class="control-label col-md-2 ">销售许可标志：</label>
                                                <div class="col-md-3">
                                                    <input type="text" id="sale_mark" name="sale_mark" class="form-control" placeholder="销售许可标志">
                                                </div>
                                            </div>
                                            <div class="form-group" > 
                                                <label for="name-1" class="control-label col-md-2 ">兑奖许可标志：</label>
                                                <div class="col-md-3">
                                                    <input type="text" class="form-control" id="cash_mark" name="cash_mark" placeholder="兑奖许可标志">
                                                </div>
                                                <label for="name-1" class="control-label col-md-2 ">过期中奖数据保存时间：</label>
                                                <div class="col-md-3">
                                                    <input type="text" id="data_save_day" name="data_save_day" class="form-control" placeholder="过期中奖数据保存时间">
                                                </div>
                                            </div>
                                            <div class="form-group" > 
                                                <label for="name-1" class="control-label col-md-2 ">本游戏系统版本号：</label>
                                                <div class="col-md-3">
                                                    <input type="text" class="form-control" id="game_version" name="game_version" placeholder="本游戏系统版本号">
                                                </div>
                                                <label for="name-1" class="control-label col-md-2 ">投注机单票默认最大金额：</label>
                                                <div class="col-md-3">
                                                    <input type="text" id="terminal_bet_money" name="terminal_bet_money" class="form-control" placeholder="投注机单票默认最大金额">
                                                </div>
                                            </div>
                                            <div class="form-group" > 
                                                <label for="name-1" class="control-label col-md-2 ">游戏风险控制方式：</label>
                                                <div class="col-md-3">
                                                    <input type="text" class="form-control" id="game_control_type" name="game_control_type" placeholder="游戏风险控制方式">
                                                </div>
                                                <label for="name-1" class="control-label col-md-2 ">风险控制组数：</label>
                                                <div class="col-md-3">
                                                    <input type="text"  id="control_group_num" name="control_group_num" class="form-control" placeholder="风险控制组数">
                                                </div>
                                            </div>
                                            <div class="form-group" > 
                                                <label for="name-1" class="control-label col-md-2 ">相关游戏：</label>
                                                <div class="col-md-3">
                                                    <select name="bind_game_id" id="bind_game_id" class="form-control">
                                                        <option value="-1">无</option>
                                                    </select>
                                                </div>
                                                <label for="name-1" class="control-label col-md-2 ">兑奖方式：</label>
                                                <div class="col-md-3">
                                                    <select name="cash_method" id="cash_method" class="form-control">
                                                        <option value="0">按奖级兑奖</option>
                                                        <option value="1">按奖金兑奖</option>
                                                    </select>
                                                </div>
                                            </div>
                                            <div class="form-group" > 
                                                <label for="name-1" class="control-label col-md-2 ">派奖奖金精度：</label>
                                                <div class="col-md-3">
                                                    <input type="text" class="form-control" id="prize_precision" name="prize_precision" placeholder="派奖奖金精度">
                                                </div>
                                                <label for="name-1" class="control-label col-md-2 ">快开游戏标记：</label>
                                                <div class="col-md-3">
                                                    <select name="keno_game" id="keno_game" class="form-control">
                                                        <option value="0">普通</option>
                                                        <option value="1">快开</option>
                                                    </select>
                                                    <!--<input type="text"  id="keno_game" name="keno_game" class="form-control" placeholder="快开游戏标记">-->
                                                </div>
                                            </div>
                                            <div class="form-group" > 
                                                <label for="name-1" class="control-label col-md-2 ">keno期数：</label>
                                                <div class="col-md-3">
                                                    <input type="text" class="form-control" id="keno_draw_num" name="keno_draw_num" placeholder="keno期数">
                                                </div>
                                                <label for="name-1" class="control-label col-md-2 ">每期销售时间长度：</label>
                                                <div class="col-md-3">
                                                    <input type="text"  id="draw_length" name="draw_length" class="form-control" placeholder="每期销售时间长度">
                                                </div>
                                            </div>
                                            <div class="form-group" > 
                                                <label for="name-1" class="control-label col-md-2 ">KENO销售多期期数：</label>
                                                <div class="col-md-3">
                                                    <input type="text" class="form-control" id="multi_keno_num" name="multi_keno_num" placeholder="KENO销售多期期数">
                                                </div>
                                                <label for="name-1" class="control-label col-md-2 ">下期开始时间：</label>
                                                <div class="col-md-3">
                                                    <input type="text" id="next_draw_time" name="next_draw_time" class="form-control" placeholder="下期开始时间">
                                                </div>
                                            </div>
                                            <div class="form-group" > 
                                                <label for="name-1" class="control-label col-md-2 ">发布公告时间：</label>
                                                <div class="col-md-3">
                                                    <input type="text" class="form-control" id="bulletin_time" name="bulletin_time" placeholder="发布公告时间">
                                                </div>
                                                <label for="name-1" class="control-label col-md-2 ">公告间隔：</label>
                                                <div class="col-md-3">
                                                    <input type="text" id="re_bulletin_time" name="re_bulletin_time" class="form-control" placeholder="公告间隔">
                                                </div>
                                            </div>
                                            <hr />
                                            <div class="form-group">
                                                <div class="text-center">
                                                    <input type="button" id="add_button" class="btn btn-primary" value="确认提交">
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
