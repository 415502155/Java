<%-- 
    Document   : login
    Created on : 2014-7-18, 9:29:15
    Author     : yangyf
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>用户登录</title>

        <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=15.0">
        <!-- Stylesheets -->
        <link  rel="stylesheet" href="<%=request.getContextPath()%>/css/style/bootstrap.css">
        <link rel="stylesheet" href="<%=request.getContextPath()%>/css/style/font-awesome.css">
        <link rel="stylesheet"  href="<%=request.getContextPath()%>/css/style/style.css" >
        <!-- HTML5 Support for IE -->
        <!--[if lt IE 9]>
        <script src="js/html5shim.js"></script>
        <![endif]-->
    </head>
    <body>
        <div class="admin-form">
            <div class="container ">

                <div class="row">
                    <div class="col-md-22">
                        <!-- Widget starts -->
                        <div class="widget worange">
                            <!-- Widget head -->
                            <div class="widget-head">
                                <i class="icon-lock"></i> Login 
                            </div>

                            <div class="widget-content">
                                <div class="padd">
                                    <form class="form-horizontal" action="<%=request.getContextPath()%>/user/login" method="get">
                                        <!-- username -->
                                        <div class="form-group">
                                            <label class="control-label col-md-2" for="inputEmail">username:</label>
                                            <div class="col-md-9">
                                                <input type="text" class="form-control" placeholder="username" name="userName" value="qwerty">
                                               
                                            </div>
                                        </div>
                                        <!-- Password -->
                                        <div class="form-group">
                                            <label class="control-label col-md-2" for="inputPassword">password:</label>
                                            <div class="col-md-9">
                                                <input type="password" class="form-control" placeholder="Password" name="password" value="123456">
                                            </div>
                                        </div>
                                        <!-- Remember me checkbox and sign in button -->
                                        <div class="form-group">
                                            <div class="col-md-9 col-lg-offset-2">
                                                <div class="checkbox">
                                                    <label>
                                                        <input type="checkbox"> Remember me
                                                    </label>
                                                </div>
                                            </div>
                                        </div>
                                        <div class="col-md-9 col-lg-offset-2">
                                            <button type="submit" class="btn btn-danger">登录</button>
                                            <!--<button type="reset" class="btn btn-default">重置</button>-->
                                        </div>
                                        <br />
                                    </form>
                                </div>
                            </div>

                            <div class="widget-foot">
                                <!--Not Registred? <a href="#">Register here</a>-->
                            </div>
                        </div>  
                    </div>
                </div>
            </div> 
        </div>

        <!-- JS -->
        <!-- JS -->
        <script src="<%=request.getContextPath()%>/js/test/jquery.js"></script>
        <script src="<%=request.getContextPath()%>/js/test/bootstrap.js"></script>
        
    </body>
</html>
