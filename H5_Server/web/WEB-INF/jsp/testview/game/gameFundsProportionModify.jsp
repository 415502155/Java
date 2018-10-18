<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>编辑资金分配信息</title>
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
                                        <div class="pull-left">编辑资金分配信息</div>
                                        <div class="widget-icons pull-right">
                                            <a href="#" class="wminimize"><i class="icon-chevron-up"></i></a> 
                                            <a href="#" class="wclose"><i class="icon-remove"></i></a>
                                        </div>
                                        <div class="clearfix"></div>
                                    </div>

                                    <div class="widget-content">
                                        <div class="padd">
                                            <!-- Form starts.  -->
                                            <form class="form-horizontal"  role="form" id="gameFundsProporModifyForm" method="post">
                                                 <input type="hidden"  name="game_id" id="game_id" class="form-control"  value="${gfp.game_id}">
                                                  <input type="hidden"  name="draw_id" id="draw_id" class="form-control" value="${gfp.draw_id}">
                                                <div class="form-group">
                                                    <label for="game_id" class="col-md-2 control-label">游戏名称：</label>
                                                    <div class="col-md-5">
                                                        <input type="text"  class="form-control"  value="${gfp.game_name}" readonly="true">
                                                    </div>
                                                </div>
                                                <div class="form-group" > 
                                                    <label for="draw_id" class="col-md-2 control-label">期名称：</label>
                                                    <div class="col-md-5">
                                                        <input type="text"   class="form-control" value="${gfp.draw_name}"  readonly="true">
                                                    </div>
                                                </div>  
                                                <div class="form-group" > 
                                                    <label for="welfare_proportion" class="col-md-2 control-label">公益金总比例：</label>
                                                    <div class="col-md-5">
                                                        <input type="text"  name="welfare_proportion" id="welfare_proportion" class="form-control" value="${gfp.welfare_proportion}">
                                                    </div>
                                                </div> 
                                                <div class="form-group" > 
                                                    <label for="welfare_proportion_clp" class="col-md-2 control-label">中彩公益金：</label>
                                                    <div class="col-md-5">
                                                        <input type="text"  name="welfare_proportion_clp" id="welfare_proportion_clp" class="form-control" value="${gfp.welfare_proportion_clp}">
                                                    </div>
                                                </div>
                                                <div class="form-group" > 
                                                    <label for="welfare_proportion_province" class="col-md-2 control-label">省级公益金：</label>
                                                    <div class="col-md-5">
                                                        <input type="text"  name="welfare_proportion_province" id="welfare_proportion_province" class="form-control" value="${gfp.welfare_proportion_province}">
                                                    </div>
                                                </div>
                                                <div class="form-group" > 
                                                    <label for="issue_proportion" class="col-md-2 control-label">发行费总比例：</label>
                                                    <div class="col-md-5">
                                                        <input type="text"  name="issue_proportion" id="issue_proportion" class="form-control" value="${gfp.issue_proportion}">
                                                    </div>
                                                </div>
                                                <div class="form-group" > 
                                                    <label for="issue_proportion_clp" class="col-md-2 control-label">中彩发行费：</label>
                                                    <div class="col-md-5">
                                                        <input type="text"  name="issue_proportion_clp" id="issue_proportion_clp" class="form-control" value="${gfp.issue_proportion_clp}">
                                                    </div>
                                                </div>
                                                <div class="form-group" > 
                                                    <label for="issue_proportion_province" class="col-md-2 control-label">省级发行费：</label>
                                                    <div class="col-md-5">
                                                        <input type="text"  name="issue_proportion_province" id="issue_proportion_province" class="form-control" value="${gfp.issue_proportion_province}">
                                                    </div>
                                                </div>
                                                <div class="form-group" > 
                                                    <label for="issue_proportion_city" class="col-md-2 control-label">市级发行费：</label>
                                                    <div class="col-md-5">
                                                        <input type="text"  name="issue_proportion_city" id="issue_proportion_city" class="form-control" value="${gfp.issue_proportion_city}">
                                                    </div>
                                                </div>
                                                <div class="form-group" > 
                                                    <label for="issue_proportion_station" class="col-md-2 control-label">站点发行费：</label>
                                                    <div class="col-md-5">
                                                        <input type="text"  name="issue_proportion_station" id="issue_proportion_station" class="form-control" value="${gfp.issue_proportion_station}">
                                                    </div>
                                                </div>
                                                <div class="form-group" > 
                                                    <label for="return_proportion" class="col-md-2 control-label">返奖比例：</label>
                                                    <div class="col-md-5">
                                                        <input type="text"  name="return_proportion" id="return_proportion" class="form-control" value="${gfp.return_proportion}">
                                                    </div>
                                                </div>
                                                <div class="form-group" > 
                                                    <label for="reserve_proportion" class="col-md-2 control-label">调节基金比例：</label>
                                                    <div class="col-md-5">
                                                        <input type="text"  name="reserve_proportion" id="reserve_proportion" class="form-control" value="${gfp.reserve_proportion}">
                                                    </div>
                                                </div>
<!--                                                <div class="text-center">
                                                    <input type="button" id="edit_button"  class="btn btn-primary" value="提 交"/>
                                                </div>-->
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
    </body>
    <script src="<%=request.getContextPath()%>/js/test/jquery-1.10.2.min.js"></script>
    <script type="text/javascript" src="<%=request.getContextPath()%>/js/test/bootstrap.min.js"></script>
    <script type="text/javascript" src="<%=request.getContextPath()%>/js/test/bootstrapValidator.js"></script>
    <script type="text/javascript" src="<%=request.getContextPath()%>/js/game/gameFundsProportionModify.js"></script>
    <script type="text/javascript">
        var contextPath = "<%=request.getContextPath()%>";
    </script>
</html>
