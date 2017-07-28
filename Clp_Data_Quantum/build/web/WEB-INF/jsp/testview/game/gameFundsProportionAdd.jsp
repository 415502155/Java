<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>新增资金分配信息</title>
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
                                        <div class="pull-left">新增资金分配信息</div>
                                        <div class="widget-icons pull-right">
                                            <a href="#" class="wminimize"><i class="icon-chevron-up"></i></a> 
                                            <a href="#" class="wclose"><i class="icon-remove"></i></a>
                                        </div>
                                        <div class="clearfix"></div>
                                    </div>

                                    <div class="widget-content">
                                        <div class="padd">
                                            <!-- Form starts.  -->
                                            <form class="form-horizontal"  role="form" id="gameFundsProporAddForm" method="post">
                                                <div class="form-group">
                                                    <label for="game_id" class="col-md-2 control-label">游戏名称：</label>
                                                    <div class="col-md-5">
                                                        <!--<input type="text"  name="game_id" id="game_id" class="form-control" placeholder="游戏名称">-->
                                                        <select id="game_id" name="game_id" class="form-control">
                                                            
                                                        </select>
                                                    </div>
                                                </div>
                                                <div class="form-group" > 
                                                    <label for="draw_id" class="col-md-2 control-label">期名称：</label>
                                                    <div class="col-md-5">
                                                         <select id="draw_id" name="draw_id" class="form-control">
                                                            
                                                        </select>
                                                        <!--<input type="text"  name="draw_id" id="draw_id" class="form-control" placeholder="期名称">-->
                                                    </div>
                                                </div>  
                                                <div class="form-group" > 
                                                    <label for="welfare_proportion" class="col-md-2 control-label">公益金总比例：</label>
                                                    <div class="col-md-5">
                                                        <input type="text"  name="welfare_proportion" id="welfare_proportion" class="form-control" placeholder="公益金总比例">
                                                    </div>
                                                </div> 
                                                <div class="form-group" > 
                                                    <label for="welfare_proportion_clp" class="col-md-2 control-label">中彩公益金：</label>
                                                    <div class="col-md-5">
                                                        <input type="text"  name="welfare_proportion_clp" id="welfare_proportion_clp" class="form-control" placeholder="中彩公益金">
                                                    </div>
                                                </div>
                                                <div class="form-group" > 
                                                    <label for="welfare_proportion_province" class="col-md-2 control-label">省级公益金：</label>
                                                    <div class="col-md-5">
                                                        <input type="text"  name="welfare_proportion_province" id="welfare_proportion_province" class="form-control" placeholder="省级公益金">
                                                    </div>
                                                </div>
                                                <div class="form-group" > 
                                                    <label for="issue_proportion" class="col-md-2 control-label">发行费总比例：</label>
                                                    <div class="col-md-5">
                                                        <input type="text"  name="issue_proportion" id="issue_proportion" class="form-control" placeholder="发行费总比例">
                                                    </div>
                                                </div>
                                                <div class="form-group" > 
                                                    <label for="issue_proportion_clp" class="col-md-2 control-label">中彩发行费：</label>
                                                    <div class="col-md-5">
                                                        <input type="text"  name="issue_proportion_clp" id="issue_proportion_clp" class="form-control" placeholder="中彩发行费">
                                                    </div>
                                                </div>
                                                <div class="form-group" > 
                                                    <label for="issue_proportion_province" class="col-md-2 control-label">省级发行费：</label>
                                                    <div class="col-md-5">
                                                        <input type="text"  name="issue_proportion_province" id="issue_proportion_province" class="form-control" placeholder="省级发行费">
                                                    </div>
                                                </div>
                                                <div class="form-group" > 
                                                    <label for="issue_proportion_city" class="col-md-2 control-label">市级发行费：</label>
                                                    <div class="col-md-5">
                                                        <input type="text"  name="issue_proportion_city" id="issue_proportion_city" class="form-control" placeholder="市级发行费">
                                                    </div>
                                                </div>
                                                <div class="form-group" > 
                                                    <label for="issue_proportion_station" class="col-md-2 control-label">站点发行费：</label>
                                                    <div class="col-md-5">
                                                        <input type="text"  name="issue_proportion_station" id="issue_proportion_station" class="form-control" placeholder="站点发行费">
                                                    </div>
                                                </div>
                                                <div class="form-group" > 
                                                    <label for="return_proportion" class="col-md-2 control-label">返奖比例：</label>
                                                    <div class="col-md-5">
                                                        <input type="text"  name="return_proportion" id="return_proportion" class="form-control" placeholder="返奖比例">
                                                    </div>
                                                </div>
                                                <div class="form-group" > 
                                                    <label for="reserve_proportion" class="col-md-2 control-label">调节基金比例：</label>
                                                    <div class="col-md-5">
                                                        <input type="text"  name="reserve_proportion" id="reserve_proportion" class="form-control" placeholder="调节基金比例">
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
    </body>
    <script src="<%=request.getContextPath()%>/js/test/jquery-1.10.2.min.js"></script>
    <script type="text/javascript" src="<%=request.getContextPath()%>/js/test/bootstrap.min.js"></script>
    <script type="text/javascript" src="<%=request.getContextPath()%>/js/test/bootstrapValidator.js"></script>
    <script type="text/javascript" src="<%=request.getContextPath()%>/js/game/gameFundsProportionAdd.js"></script>
    <script type="text/javascript" src="<%=request.getContextPath()%>/js/common/selGameInfoList.js"></script>
    <script type="text/javascript">
        var contextPath = "<%=request.getContextPath()%>";
    </script>
</html>
