<%-- 
    Document   : drawlucky
    Created on : 2014-8-22, 16:29:52
    Author     : chenliping
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>输入开奖号码</title>
        <link href="<%=request.getContextPath()%>/css/style/bootstrap.min.css" rel="stylesheet" />
        <link rel="stylesheet"  href="<%=request.getContextPath()%>/css/style/style.css" >
        <link href="<%=request.getContextPath()%>/wizard/bwizard.min.css" rel="stylesheet" />
    </head>
    <body>
        <div class="container">
            <div id="wizard">
                <ol>
                    <li>游戏开奖</li>
                    <li>停止销售</li>
                    <li>终端封机</li>
                    <li>数据校验</li>
                    <li>录入开奖号码</li>
                    <li>数据备份</li>
                    <li>开奖号码录入</li>
                    <li>中奖数据检索</li>
                    <li>派奖</li>
                    <li>生成中奖统计表</li>
                    <li>数据稽核</li>
                    <li>确认开奖</li>
                </ol>
                <div>
                    <form class="form-horizontal" role="form" id="luckyNoInputForm">
                        <div class="form-group">
                            <label class="col-md-2 control-label">游戏名称</label>
                            <div class="col-md-5">
                                <select name="game_id" id="gameid" class="form-control col-md-2"></select>
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-md-2 control-label">游戏期号</label>
                            <div class="col-md-5">
                                <input type="text" name="draw_id" class="form-control" placeholder="" value="${drawid}">
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-md-2 control-label">开奖号码</label>
                            <input type="hidden" name="prize_no_num" value="${multiOpen.basic_ball_num}">
                            <input type="hidden" name="special_no_num" value="${multiOpen.special_ball_num}">
                            <div class="col-md-5">
                                <input type="password" name="prize_no" class="form-control" placeholder="">
                            </div>
                            <div class="col-md-5">
                                <input type="password" name="special_no" class="form-control" placeholder="">
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-md-2 control-label">确认开奖号码</label>
                            <div class="col-md-5">
                                <input type="password" name="verify_prize_no" class="form-control" placeholder="">
                            </div>
                            <div class="col-md-5">
                                <input type="password" name="verify_special_no" class="form-control" placeholder="">
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-md-2 control-label">开奖次数</label>
                            <div class="col-md-5">
                                <input type="text" name="open_id" value="${multiOpen.open_id}">
                            </div>
                        </div>
                        <div class="form-group">
                            <div class="col-md-offset-2 col-md-5">
                                <input type="button" id="luckyNoInputButton" class="btn btn-primary" value="确认提交">
                            </div>
                        </div>
                    </form>
                </div>
                <div></div>
                <div></div>
                <div></div>
                <div></div>
                <div></div>
                <div></div>
                <div></div>
            </div>
        </div>
        <script src="<%=request.getContextPath()%>/js/test/jquery-1.10.2.min.js"></script>
        <script src="<%=request.getContextPath()%>/js/test/bootstrap.min.js"></script>
        <script src="<%=request.getContextPath()%>/wizard/bwizard.min.js" type="text/javascript"></script>
        <script src="<%=request.getContextPath()%>/js/gameBusiness/drawlucky.js" type="text/javascript"></script>
        <script src="<%=request.getContextPath()%>/js/common/selGameInfoList.js" type="text/javascript"></script>
        <script type="text/javascript">
            var contextPath = "<%=request.getContextPath()%>";
            $("#wizard").bwizard({clickableSteps: false});
        </script>
    </body>
</html>
