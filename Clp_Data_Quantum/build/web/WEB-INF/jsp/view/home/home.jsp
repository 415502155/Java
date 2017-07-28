<%-- 
    Document   : in
    Created on : 2014-7-29, 15:35:08
    Author     : chenliping
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <meta charset="utf-8">
        <title>首页</title>   
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <!-- Stylesheets -->
        <link href="<%=request.getContextPath()%>/css/style/bootstrap.css" rel="stylesheet">
        <!-- Font awesome icon -->
        <link rel="stylesheet" href="<%=request.getContextPath()%>/css/style/font-awesome.css"> 
        <link rel="stylesheet" href="<%=request.getContextPath()%>/css/style/uniform.default.css"> 
        <!-- Main stylesheet -->
        <link href="<%=request.getContextPath()%>/css/style/style.css" rel="stylesheet"> 
    </head>
    <body>
        <!-- Header starts -->
        <header>
            <div class="container">
                <div class="row">
                    <!-- Logo section -->
                    <div class="col-md-4">
                        <!-- Logo. -->
                        <div class="logo">
                            <h1><a href="#">Quantum<span class="bold"></span></a></h1>
                            <p class="meta">后台管理系统</p>
                        </div>
                        <!-- Logo ends -->
                    </div>
                    <!-- Button section -->
                    <!-- Data section -->  
                </div>
            </div>
        </header>
        <!-- Header ends -->

        <!-- Main content starts -->
        <div class="content">
            <!-- Sidebar -->
            <div class="sidebar">
                <div class="sidebar-dropdown"><a href="#">导航</a></div>
                <!--- Sidebar navigation -->
                <!-- If the main navigation has sub navigation, then add the class "has_sub" to "li" of main navigation. -->
                <ul id="nav">
                    <!-- Main menu with font awesome icon -->
                    <li><a href="" class="open"><i class="icon-home"></i>首页</a></li>
                    <li class="has_sub"><a href="#"><i class="icon-list-alt"></i>游戏管理<span class="pull-right"><i class="icon-chevron-right"></i></span></a>
                        <ul>
                            <li><a href="<%=request.getContextPath()%>/gameinfo/2add">游戏信息</a></li>
                            <li><a href="<%=request.getContextPath()%>/gameplayinfo/2add">游戏玩法</a></li>
                            <li><a href="">游戏奖级</a></li>
                            <li><a href="<%=request.getContextPath()%>/gameluckyRule/2select">抽奖规则</a></li>
                            <li><a href="<%=request.getContextPath()%>/gameMultiOpen/2select">开奖次数</a></li>
                            <li><a href="<%=request.getContextPath()%>/gameFundsPropor/2select">资金分配</a></li>
                        </ul>
                    </li>  
                    <li class="has_sub"><a href="#"><i class="icon-file-alt"></i>代销商管理<span class="pull-right"><i class="icon-chevron-right"></i></span></a>
                        <ul>
                            <li><a href="">代销商注册</a></li>
                            <li><a href="">代销商信息管理</a></li>
                        </ul>
                    </li> 
                    <li class="has_sub"><a href="#"><i class="icon-file-alt"></i>终端管理 <span class="pull-right"><i class="icon-chevron-right"></i></span></a>
                        <ul>
                            <li><a href="">终端注册</a></li>
                            <li><a href="">终端信息管理</a></li>
                            <li><a href="">终端软件管理</a></li>
                            <li><a href="">终端特权发放</a></li>
                        </ul>
                    </li>
                    <li class="has_sub"><a href="#"><i class="icon-file-alt"></i>系统用户<span class="pull-right"><i class="icon-chevron-right"></i></span></a>
                        <ul>
                            <li><a href="">注册系统用户</a></li>
                            <li><a href="">系统用户管理</a></li>
                            <li><a href="">用户密码修改</a></li>
                        </ul>
                    </li> 
                    <li class="has_sub"><a href="#"><i class="icon-file-alt"></i>游戏业务<span class="pull-right"><i class="icon-chevron-right"></i></span></a>
                        <ul>
                            <li><a href="">生成新期</a></li>
                            <li><a href="">期信息管理</a></li>
                            <li><a href="">手工开奖</a></li>
                        </ul>
                    </li> 
                    <li><a href=""><i class="icon-magic"></i>系统退出</a></li>                    
                </ul>
            </div>
            <!-- Sidebar ends -->
            <!-- Main bar -->            
            <!-- Mainbar ends -->
            <!--<div class="clearfix"></div>-->
        </div>
        <!-- Content ends -->

        <!-- Footer starts -->
        <footer>
            <div class="container">
                <div class="row">
                    <div class="col-md-12">
                        <!-- Copyright info -->
                        <p class="copy">Copyright &copy; 2012 | <a href="#">Your Site</a> </p>
                    </div>
                </div>
            </div>
        </footer> 	
        <!-- Footer ends -->

        <!-- Scroll to top -->
        <span class="totop"><a href="#"><i class="icon-chevron-up"></i></a></span> 

        <!-- JS -->
        <script src="<%=request.getContextPath()%>/js/test/jquery.js"></script> 
        <!-- Bootstrap -->
        <script src="<%=request.getContextPath()%>/js/test/bootstrap.js"></script> 
        <!-- Custom codes -->
        <script src="<%=request.getContextPath()%>/js/test/custom.js"></script> 
        

        <!-- Script for this page -->
        
    </body>
</html>
