<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<!DOCTYPE html>
<html>
<head>
<%@ include file="/common/global.jsp"%>
<%-- <title>${apptitle}</title> --%>
<title>微校云用户管理平台</title>
<%@ include file="/common/meta.jsp" %>
<%@ include file="/common/include-base-styles.jsp" %>
<!--[if lt IE 9]><script src="js/speedup.js" type="text/javascript"></script><script src="${ctx}/js/jquery-1.11.3.min.js" type="text/javascript"></script><![endif]-->
<!--[if gte IE 9]><!--><script src="${ctx}/js/jquery-2.1.4.min.js" type="text/javascript"></script><!--<![endif]-->
<link rel="stylesheet" href="${ctx}/css/smoothness/jquery-ui-1.8.13.custom.css" type="text/css" media="screen" title="no title">
<link href="${ctx}/themes/default/style.css" rel="stylesheet" type="text/css" media="screen"/>
<link href="${ctx}/themes/default/jquery.treetable.css" rel="stylesheet" type="text/css" media="screen"/>
<link href="${ctx}/themes/default/jquery.treetable.theme.default.css" rel="stylesheet" type="text/css" media="screen"/>
<link href="${ctx}/themes/css/core.css" rel="stylesheet" type="text/css" media="screen"/>
<link href="${ctx}/themes/css/print.css" rel="stylesheet" type="text/css" media="print"/>
<link href="${ctx}/css/visualize.jQuery.css" type="text/css" rel="stylesheet"/>
<link href="${ctx}/uploadify/css/uploadify.css" rel="stylesheet" type="text/css" media="screen"/>
<script src="${ctx}/xheditor/xheditor-1.2.2.min.js" type="text/javascript"></script>
<script src="${ctx}/xheditor/xheditor_lang/zh-cn.js" type="text/javascript"></script>
<script src="${ctx}/uploadify/scripts/jquery.uploadify.js" type="text/javascript"></script>
<script src="${ctx}/js/jquery.cookie.js" type="text/javascript"></script>
<script src="${ctx}/js/jquery.validate.js" type="text/javascript"></script>
<script src="${ctx}/js/jquery.bgiframe.js" type="text/javascript"></script>
<script src="${ctx}/js/dwz.core.js" type="text/javascript"></script>
<script src="${ctx}/js/dwz.panel.js" type="text/javascript"></script>
<script src="${ctx}/js/dwz.util.date.js" type="text/javascript"></script>
<script src="${ctx}/js/dwz.validate.method.js" type="text/javascript"></script>
<script src="${ctx}/js/dwz.barDrag.js" type="text/javascript"></script>
<script src="${ctx}/js/dwz.drag.js" type="text/javascript"></script>
<script src="${ctx}/js/dwz.tree.js" type="text/javascript"></script>
<script src="${ctx}/js/dwz.accordion.js" type="text/javascript"></script>
<script src="${ctx}/js/dwz.ui.js" type="text/javascript"></script>
<script src="${ctx}/js/dwz.theme.js" type="text/javascript"></script>
<script src="${ctx}/js/dwz.switchEnv.js" type="text/javascript"></script>
<script src="${ctx}/js/dwz.alertMsg.js" type="text/javascript"></script>
<script src="${ctx}/js/dwz.contextmenu.js" type="text/javascript"></script>
<script src="${ctx}/js/dwz.navTab.js" type="text/javascript"></script>
<script src="${ctx}/js/dwz.tab.js" type="text/javascript"></script>
<script src="${ctx}/js/dwz.resize.js" type="text/javascript"></script>
<script src="${ctx}/js/dwz.dialog.js" type="text/javascript"></script>
<script src="${ctx}/js/dwz.dialogDrag.js" type="text/javascript"></script>
<script src="${ctx}/js/dwz.sortDrag.js" type="text/javascript"></script>
<script src="${ctx}/js/dwz.cssTable.js" type="text/javascript"></script>
<script src="${ctx}/js/dwz.stable.js" type="text/javascript"></script>
<script src="${ctx}/js/dwz.taskBar.js" type="text/javascript"></script>
<script src="${ctx}/js/dwz.ajax.js" type="text/javascript"></script>
<script src="${ctx}/js/dwz.pagination.js" type="text/javascript"></script>
<script src="${ctx}/js/dwz.database.js" type="text/javascript"></script>
<script src="${ctx}/js/dwz.datepicker.js" type="text/javascript"></script>
<script src="${ctx}/js/dwz.effects.js" type="text/javascript"></script>
<script src="${ctx}/js/dwz.checkbox.js" type="text/javascript"></script>
<script src="${ctx}/js/dwz.history.js" type="text/javascript"></script>
<script src="${ctx}/js/dwz.combox.js" type="text/javascript"></script>
<script src="${ctx}/js/dwz.print.js" type="text/javascript"></script>
<script src="${ctx}/js/jquery.treetable.js" type="text/javascript"></script>
<script src="${ctx}/js/echarts.min.js" type="text/javascript"></script>
<script src="${ctx}/js/zepto.min.js" type="text/javascript"></script>
<!--[if IE]><script type="text/javascript" src="${ctx}/js/excanvas.compiled.js"></script><![endif]-->
<script type="text/javascript" src="${ctx}/js/visualize.jQuery.js"></script>
<script src="${ctx}/js/jquery.zclip.min.js" type="text/javascript"></script>

<script src="${ctx}/js/emoji-list-with-image.js" type="text/javascript"></script>
<script src="${ctx}/js/punycode.min.js" type="text/javascript"></script>
<script src="${ctx}/js/emoji.js" type="text/javascript"></script>

<script type="text/javascript" charset="utf-8">
	$().ready(function() {	
		DWZ.init(ctx+"/dwz.frag.xml", {
			loginUrl:"${ctx}/manage/login", loginTitle:"津南局SSO用户管理平台",	// 弹出登录对话框
//			loginUrl:"login.html",	// 跳到登录页面
			statusCode:{ok:200, error:300, timeout:301}, //【可选】
			pageInfo:{pageNum:"pageNum", numPerPage:"numPerPage", orderField:"orderField", orderDirection:"orderDirection"}, //【可选】
			keys: {statusCode:"statusCode", message:"message"}, //【可选】
			ui:{hideMode:'offsets'}, //【可选】hideMode:navTab组件切换的隐藏方式，支持的值有’display’，’offsets’负数偏移位置的值，默认值为’display’
			debug:false,	// 调试模式 【true|false】
			callback:function(){
				initEnv();
				$("#themeList").theme({themeBase:"themes"}); // themeBase 相对于index页面的主题base路径
			}
		});
	});	
	$(function(){
		//make some charts	
		ajaxTodo("${ctx}/manage/main/getTree",function(data){
			setTimeout(function(){
				var sidebar = $("#sidebar .accordionContent .treeFolder");
				for(var i=0; i<data.length; i++){ 
					var li = $("<li><a>"+data[i].title+"</a></li>");
					var subUl = $("<ul></ul>");
					var innerData = data[i].subs;
					for(var j=0; j<innerData.length; j++){ 
						var subli = $("<li><a href="+"${ctx}/"+innerData[j].href+" target="+innerData[j].target+" rel="+innerData[j].rel+">"+innerData[j].title+"</a></li>");
						subUl.append(subli);
					}
					li.append(subUl);
					sidebar.append(li);
				}
				$("#sidebar").initUI();
			}, 100);
		});		
		
	/* 	if(decodeURIComponent(decodeURI(getCookie("baseuserjson")))==undefined){
			myConfirm("登录失败,请重新登陆？", function(){
				window.location.href="http://"+window.location.host+"/baseui/"; 
			}, null, null);
		}else{
			var orgInfo=JSON.parse(decodeURIComponent(decodeURI(getCookie("baseuserjson"))));
			if(orgInfo&&orgInfo.teacher){
	        	var userName=orgInfo.teacher.tech_name;
	        	
	        	$("#userName").text(userName);
			}
		} */
	});
	
	<%--获取登陆者信息--%>
	<%--获取cookie--%>
	function getCookie(name) {
		   var cookies = document.cookie.split(";");
		   for(var i=0;i<cookies.length;i++) {
		    var cookie = cookies[i];
		    var cookieStr = cookie.split("=");
		    if(cookieStr && cookieStr[0].trim()==name) {
		     return  decodeURI(cookieStr[1]);
		    }
		   }
		 }
	

</script>
<SCRIPT LANGUAGE="JScript" EVENT="Upload(path)" FOR="ldupload">
</SCRIPT>
</head>
<body scroll="no">
	<div id="layout">
		<div id="header">
			<div class="headerNav">
				<!-- <a class="logo" href="#">标志</a> -->
				<span class="org-info">
					<img alt="" src="${ctx}/images/logo-small.png" width="30" height="30" style="vertical-align: middle;">
				         微校云用户管理平台 	
				</span>
				<ul class="nav">		
				    <li id="userName" style="color:#fff;background:none"></li>		
					<li style="background:none"><a href="${ctx}/LoginOut?X-Form-type=form">退出</a></li>
				</ul>
			</div>

			<!-- navMenu -->
			<div id="mainbogy" style="top:0;position:absolute">
				<div id="cavcont" class="cavcont" style="width:100%; height:240px;"> 
				</div>
			</div>
		</div>

		<div id="leftside">
			<div id="sidebar_s">
				<div class="collapse">
					<div class="toggleCollapse"><div></div></div>
				</div>
			</div>
			<div id="sidebar">
				<div class="toggleCollapse"><h2>主菜单</h2><div>收缩</div></div>

				<div class="accordion" fillSpace="sidebar">
					<div class="accordionHeader">
						<h2><span>Folder</span>常用管理</h2>
					</div>
					<div class="accordionContent">
						<ul class="tree treeFolder">
																		
						</ul>
					</div>					
				</div>
			</div>
		</div>
		<div id="container">
			<div id="navTab" class="tabsPage">
				<div class="tabsPageHeader">
					<div class="tabsPageHeaderContent"><!-- 显示左右控制时添加 class="tabsPageHeaderMargin" -->
						<ul class="navTab-tab">
							<li tabid="main" class="main"><a href="javascript:;"><span><span class="home_icon">我的主页</span></span></a></li>
						</ul>
					</div>
					<div class="tabsLeft">left</div><!-- 禁用只需要添加一个样式 class="tabsLeft tabsLeftDisabled" -->
					<div class="tabsRight">right</div><!-- 禁用只需要添加一个样式 class="tabsRight tabsRightDisabled" -->
					<div class="tabsMore">more</div>
				</div>
				<ul class="tabsMoreList">
					<li><a href="javascript:;">我的主页</a></li>
				</ul>
				<div class="navTab-panel tabsPageContent layoutBox">
					<div class="page unitBox">
						<div class="pageFormContent" layoutH="80" style="margin-right:50px">							
							<div style="clear:both;">
								<h2>用户统计</h2>
								<div style="clear:both;"></div>
								<div style="width:110px;float: left; margin: 20px 50px 20px 50px;">
									<img alt="当前用户量" src="${ctx}/images/user.png">
									<div style="text-align: center;padding: 10px">用户量总数:${allcount}</div>
								</div>
								<div style="width:110px;float: left; margin: 20px 50px 20px 50px;">
									<img alt="最近一月新增用户" src="${ctx}/images/user.png">
									<div style="text-align: center; padding: 10px">本月新增用户:${currentMonthCount}</div>
								</div>
								<div style="clear:both;"></div>
							</div>
							<div style="clear:both;">
								<h2>搜索统计</h2>
								<div style="clear:both;"></div>								
								<div style="width:700px;float: left; margin-top: 20px;margin-left: 50px;">
									<table id="keywordCount" style="display:none;width:800px; height:400px;">
										<caption>搜索关键字统计</caption>
										<thead>
											<tr>
												<td></td>
												<th>查询量</th>
											</tr>
										</thead>
										<tbody>
											<c:forEach var="keyword" items="${keywords}">
												<tr>
													<th>${keyword.title}</th>
													<td>${keyword.click}</td>
												</tr>
											</c:forEach>	
										</tbody>
									</table>
								</div>
								<div style="clear:both;"></div>
							</div>			
						</div>						
					</div>
				</div>
			</div>
		</div>

	</div>

	<div id="footer">Copyright &copy; 2016 <a href="#" target="dialog"><spring:message code="app.name" /></a></div>


</body>
</html> 
