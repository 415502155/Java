<%-- 
    Document   : drawlucky
    Created on : 2014-8-22, 16:29:52
    Author     : chenliping
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>输入奖金</title>
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
                    <form class="form-horizontal" role="form" id="moneyInputForm">
                        <div class="form-group">
                            <label class="col-md-2 control-label">游戏名称</label>
                            <div class="col-md-5">
                                <select name="gameid" id="gameid" class="form-control col-md-2" value="${gameid}"></select>
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-md-2 control-label">游戏期号</label>
                            <div class="col-md-5">
                                <input type="text" name="drawid" class="form-control" placeholder="" value="${drawid}">
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-md-2 control-label">开奖次数</label>
                            <div class="col-md-5">
                                <input type="text" name="openid" id="openid" value="${openid}">
                            </div>
                        </div>
                        <table class="table">
                            <thead>
                                <tr>
                                    <th></th>
                                </tr>
                            </thead>
                            <tbody>
                                <tr>
                                    <td>中奖号码：<input type="text" name="luckyNo" id="luckyNo"></td>
                                </tr>
                                <tr>
                                    <td>特别奖号码：<input type="text" name="specialNo" id="specialNo"></td>
                                </tr>
                                <tr>
                                    <td>当期发型彩票金额：<input type="text" name="currentReleaseMoney" id="currentReleaseMoney"></td>
                                </tr>
                                <tr>
                                    <td>当期派彩金额：<input type="text" name="currentPayMoney" id="currentPayMoney"></td>
                                </tr>
                                <tr>
                                    <td>上期奖金滚入金额：<input type="text" name="lastRollMoney" id="lastRollMoney"></td>
                                </tr>
                                <tr>
                                    <td>滚入下期奖金金额：<input type="text" name="nextRollMoney" id="nextRollMoney"></td>
                                </tr>
                            </tbody>
                        </table>
                        <table class="table">
                            <thead>
                                <tr>
                                    <th>奖级</th>
                                    <th>中奖注数</th>
                                    <th>单注金额</th>
                                </tr>
                            </thead>
                            <tbody>
                                <c:forEach items="${classInfoList}" var="classInfo">
                                    <tr>
                                        <input type="hidden" name="game_id" value="${gameid}">
                                        <input type="hidden" name="play_id" value="${playid}">
                                        <input type="hidden" name="class_id" value="${classInfo.class_id}">
                                        <td>${classInfo.class_name}：</td>
                                        <td><input type="text" name="prizeNumber" value="0"></td>
                                        <td><input type="text" name="prize_proportion" value="0.00"></td>
                                    </tr>
                                </c:forEach>
                            </tbody>
                        </table>
                        <div class="form-group">
                            <div class="col-md-offset-2 col-md-5">
                                <input type="button" id="moneyInputButton" class="btn btn-primary" value="确认提交">
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
