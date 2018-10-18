<%-- 
Document   : in
Created on : 2014-7-29, 15:35:08
Author     : chenliping
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <meta charset="utf-8">
        <title>首页</title>   
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <link href="<%=request.getContextPath()%>/css/style/bootstrap.css" rel="stylesheet">
        <link rel="stylesheet" href="<%=request.getContextPath()%>/css/style/font-awesome.css"> 
        <link rel="stylesheet" href="<%=request.getContextPath()%>/css/style/prettyPhoto.css">  
        <link href="<%=request.getContextPath()%>/css/style/style.css" rel="stylesheet"> 

        <!--JS-->
        <script src="<%=request.getContextPath()%>/js/test/jquery.js"></script> 
        <script src="<%=request.getContextPath()%>/js/test/bootstrap.js"></script> <!-- Bootstrap -->
        <script src="<%=request.getContextPath()%>/js/test/jquery-ui-1.9.2.custom.min.js"></script> <!-- jQuery UI -->
        <script src="<%=request.getContextPath()%>/js/test/fullcalendar.min.js"></script> <!-- Full Google Calendar - Calendar -->
        <script src="<%=request.getContextPath()%>/js/test/jquery.prettyPhoto.js"></script> <!-- prettyPhoto -->
        <script src="<%=request.getContextPath()%>/js/test/custom.js"></script>
        <script src="<%=request.getContextPath()%>/js/home/home.js"></script>
        <script type="text/javascript">
            var contextPath = "<%=request.getContextPath()%>";
        </script>
    </head>
    <body>
        <!-- Header starts -->
        <header>
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
                    <li><a href="#" class="open"><i class="icon-home"></i>首页</a></li>
<!--                    <li class="has_sub"><a href="#"><i class="icon-list-alt"></i>游戏管理<span class="pull-right"><i class="icon-chevron-right"></i></span></a>
                        <ul>
                            <li><a id="gameinfo" >游戏信息</a></li>
                            <li><a  id="gameplayinfo">游戏玩法</a></li>
                            <li><a id="gameClassInfo">游戏奖级</a></li>
                            <li><a id="gameluckyRule">抽奖规则</a></li>
                            <li><a id="gameMultiOpen" >开奖次数</a></li>
                            <li><a id="gameFundsPropor" >资金分配</a></li>
                        </ul>
                    </li>  
                    <li class="has_sub"><a href="#"><i class="icon-file-alt"></i>代销商管理<span class="pull-right"><i class="icon-chevron-right"></i></span></a>
                        <ul>
                            <li><a id="dealerInfoAdd">代销商注册</a></li>
                            <li><a id="dealerInfoSel">代销商信息管理</a></li>
                        </ul>
                    </li> 
                    <li class="has_sub"><a href="#"><i class="icon-file-alt"></i>终端管理 <span class="pull-right"><i class="icon-chevron-right"></i></span></a>
                        <ul>
                            <li><a id="tmnRegister">终端信息登记</a></li>
                            <li><a id="tmninfoSel">终端信息管理</a></li>
                            <li><a id="">终端软件管理</a></li>
                            <li><a id="">终端特权发放</a></li>
                        </ul>
                    </li>
                    <li class="has_sub"><a href="#"><i class="icon-file-alt"></i>系统用户<span class="pull-right"><i class="icon-chevron-right"></i></span></a>
                        <ul>
                            <li><a id="sysuserAdd">注册系统用户</a></li>
                            <li><a id="sysuserSelect">系统用户管理</a></li>
                            <li><a id="sysuserModifyPwd">用户密码修改</a></li>
                        </ul>
                    </li> 
                    <li class="has_sub"><a href="#"><i class="icon-file-alt"></i>游戏业务<span class="pull-right"><i class="icon-chevron-right"></i></span></a>
                        <ul>
                            <li><a id="drawinfoAdd">生成期信息</a></li>
                            <li><a id="currentDrawSaleTime">当前期销售时间</a></li>
                            <li><a id="drawinfoSelect">期信息管理</a></li>
                            <li><a id="kdrawinfoSelect">快开期信息管理</a></li>
                            <li><a href="#" id="drawlucky">手工开奖</a></li>
                        </ul>
                    </li> 
                    <li class="has_sub"><a href="#"><i class="icon-file-alt"></i>资金账户<span class="pull-right"><i class="icon-chevron-right"></i></span></a>
                        <ul>
                            <li><a id="openAccount">开户</a></li>
                        </ul>
                    </li>-->
                    <li class="has_sub"><a href="#"><i class="icon-file-alt"></i>文件生成<span class="pull-right"><i class="icon-chevron-right"></i></span></a>
                        <ul>
                            <li><a id="gameRuleFileMake">游戏规则文件生成</a></li>
                            <li><a id="ticketBulletinFileMake">彩票公告文件生成</a></li>
                        </ul>                        
                    </li>
                     <li class="has_sub"><a href="#"><i class="icon-file-alt"></i>系统工具<span class="pull-right"><i class="icon-chevron-right"></i></span></a>
                        <ul>
                            <li><a id="synehcachedata">缓存数据同步</a></li>
                        </ul>
                    </li>
                    <li><a href=""><i class="icon-magic"></i>系统退出</a></li>                    
                </ul>
            </div>
            <!-- Sidebar ends -->
            <!-- Main bar -->
            <!-- 此处为右边区域-->
            <div id="right" style="margin-left: 230px;">
                <!--<iframe  id="rightContent" name="rightContent"  width="100%" frameborder="no" border="no" scrolling="no"  onLoad="iFrameHeight()" ></iframe>-->
                <!--<iframe  id="rightContent" name="rightContent"  width="100%" frameborder="no" border="no" scrolling="no"  onLoad="iFrameLoad(this)" ></iframe>-->
                <iframe  id="rightContent" name="rightContent"  width="100%" frameborder="no" border="no" scrolling="no"  onLoad="iFrameLoad(this)" ></iframe>
            </div>
            <!-- Mainbar ends -->
            <div class="clearfix"></div>

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
    </body>
</html>
