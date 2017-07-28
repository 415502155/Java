<%-- 
    Document   : gameLuckyRule
    Created on : 2014-7-19, 17:17:17
    Author     : chenliping
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>游戏抽奖规则增加</title>
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
                                        <div class="pull-left">新增游戏抽奖规则</div>
                                        <div class="widget-icons pull-right">
                                            <a href="#" class="wminimize"><i class="icon-chevron-up"></i></a> 
                                            <a href="#" class="wclose"><i class="icon-remove"></i></a>
                                        </div>
                                        <div class="clearfix"></div>
                                    </div>

                                    <div class="widget-content">
                                        <div class="padd">
                                            <!-- Form starts.  -->
                                            <form class="form-horizontal"  role="form" id="gameLuckyRuleForm" method="post">
                                                <div class="form-group">
                                                    <label for="game_id" class="col-md-2 control-label">游戏名称：</label>
                                                    <div class="col-md-5">
                                                        <select name="game_id" id="game_id" class="form-control">
                                                            <!--<option value="-1">--请选择游戏--</option>-->
                                                        </select>
                                                        <!--                            <input type="text" class="form-control" name="game_id" id="game_id" placeholder="游戏名称">-->
                                                    </div>
                                                </div>
                                                <div class="form-group" > 
                                                    <label for="name-1" class="col-md-2 control-label">玩法名称：</label>
                                                    <div class="col-md-5">
                                                        <!--<input type="text"  name="play_id" id="play_id" class="form-control" placeholder="玩法名">-->
                                                        <select name="play_id" id="play_id" class="form-control">
                                                            <!--<option value="-1">--请选择玩法--</option>-->
                                                        </select>
                                                    </div>
                                                </div> 
                                                <div class="form-group">
                                                    <label for="rule_id" class="col-md-2 control-label">规则编号：</label>
                                                    <div class="col-md-5">
                                                        <input type="text" class="form-control" name="rule_id" id="rule_id" placeholder="规则编号">
                                                    </div>
                                                </div>
                                                <div class="form-group" > 
                                                    <label for="name-1" class="col-md-2 control-label">开奖次数：</label>
                                                    <div class="col-md-5">
                                                        <input type="text"  name="open_id" id="open_id" class="form-control" placeholder="开奖次数">
                                                    </div>
                                                </div>  
                                                <div class="form-group" > 
                                                    <label for="name-1" class="col-md-2 control-label">奖级编号：</label>
                                                    <div class="col-md-5">
                                                        <input type="text"  name="class_id" id="class_id" class="form-control" placeholder="奖级编号">
                                                    </div>
                                                </div>
                                                <div class="form-group" > 
                                                    <label for="name-1" class="col-md-2 control-label">基本号码匹配数量：</label>
                                                    <div class="col-md-5">
                                                        <input type="text"  name="basic_num" id="basic_num" class="form-control" placeholder="基本号码匹配数量">
                                                    </div>
                                                </div>
                                                <div class="form-group" > 
                                                    <label for="name-1" class="col-md-2 control-label">特别号码匹配数量：</label>
                                                    <div class="col-md-5">
                                                        <input type="text"  name="special_num" id="special_num" class="form-control" placeholder="特别号码匹配数量">
                                                    </div>
                                                </div>
                                                <div class="form-group" > 
                                                    <label for="name-1" class="col-md-2 control-label">二区蓝球匹配数量：</label>
                                                    <div class="col-md-5">
                                                        <input type="text"  name="blue_num" id="blue_num" class="form-control" placeholder="二区蓝球匹配数量">
                                                    </div>
                                                </div>
                                                <div class="form-group" > 
                                                    <label for="name-1" class="col-md-2 control-label">号码有序：</label>
                                                    <div class="col-md-5">
                                                        <input type="text"  name="no_order" id="no_order" class="form-control" placeholder="号码有序">
                                                    </div>
                                                </div>
                                                <div class="form-group" > 
                                                    <label for="name-1" class="col-md-2 control-label">匹配位置：</label>
                                                    <div class="col-md-5">
                                                        <input type="text"  name="match_pos" id="match_pos" class="form-control" placeholder="匹配位置">
                                                    </div>
                                                </div>
                                                <div class="form-group" > 
                                                    <label for="name-1" class="col-md-2 control-label">匹配相邻：</label>
                                                    <div class="col-md-5">
                                                        <!--<input type="text"  name="match_near" id="match_near" class="form-control" placeholder="匹配相邻">-->
                                                        <select id="match_near" name="match_near" class="form-control">
                                                            <option value="0">无关</option>
                                                            <option value="1">连续</option>
                                                        </select>
                                                    </div>
                                                </div>
                                                <div class="form-group" > 
                                                    <label for="name-1" class="col-md-2 control-label">抽奖方法：</label>
                                                    <div class="col-md-5">
                                                        <input type="text"  name="raffle_method" id="raffle_method" class="form-control" placeholder="抽奖方法">
                                                    </div>
                                                </div>
                                                <div class="form-group" > 
                                                    <label for="name-1" class="col-md-2 control-label">状态：</label>
                                                    <div class="col-md-5">
                                                        <select name="work_status" id="work_status" class="form-control">
                                                            <option value="1">启动</option>
                                                            <option value="2">不启动</option>
                                                        </select>
                                                    </div>
                                                </div>
                                                <hr />
                                                <div class="form-group">
                                                    <div class="col-md-offset-1 col-md-9">
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
        <script src="<%=request.getContextPath()%>/js/test/jquery-1.10.2.min.js"></script>
        <script type="text/javascript"  src="<%=request.getContextPath()%>/js/test/bootstrap.min.js"></script>
        <script type="text/javascript" src="<%=request.getContextPath()%>/js/common/selGameInfoList.js"></script>
        <script type="text/javascript" src="<%=request.getContextPath()%>/js/test/bootstrapValidator.js"></script>
        <script type="text/javascript" src="<%=request.getContextPath()%>/js/game/gameLuckyRuleAdd.js"></script>
        <script type="text/javascript">
            var contextPath = "<%=request.getContextPath()%>";
        </script>
    </body>
</html>
