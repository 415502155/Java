<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="org.springframework.security.web.WebAttributes" %>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<!DOCTYPE html>
<html>
<head>
<%@ include file="/common/global.jsp"%>
<title><spring:message code="app.name" /></title>
<%@ include file="/common/meta.jsp" %>
<%@ include file="/common/include-base-styles.jsp" %>
<script src="${ctx}/js/jquery-1.7.2.min.js" type="text/javascript" charset="utf-8"></script>
<script src="${ctx}/js/jquery.validate.js" type="text/javascript" charset="utf-8"></script>
<script src="${ctx}/js/ldws.js" type="text/javascript" charset="utf-8"></script>
</head>

<body>
	<div id="main">
		<div class="page-header-border " id="page-header">
			<div id="inner-page-header">
				<span class="home-icon-container" style="font-size:45px; line-height:64px">
					<a id="home-icon" href="${ctx}"><i class="login-imglogo"><img alt="" src="${ctx}/images/logo-big.png" width="60" height="60"></i>微校云用户管理平台</a>
				</span>
			</div>
			<div style="clear: both;"></div>
		</div>
		<div id="outer-frame">		    
			    <div id="page-content">
			        <div class="" id="login-and-register-container">
			            <div id="login-container">
			                <form method="post" name="loginform" novalidate="novalidate" action="${ctx}/LoginProcess">		                   
			                    <!-- <div class="page-header-text">登 录</div> -->
			                    <div id="login-partial">
			                        <input type="hidden" value="desktop" name="display">
			                        <div class="sick-input" id="username-field">
			                        	<span style="float: left">用户名</span>
			                            <input type="text" name="j_username" tabindex="1" autofocus="1" autocomplete="off" placeholder="请输入用户名">
			                        </div>
			                        <div class="sick-input" id="password-field">	
			                        	<span style="float: left">用户密码</span>	               
			                            <input type="password" tabindex="2" name="j_password" autocomplete="off" placeholder="请输入密码">
			                        </div>
			                        <div class="item-ifo">
			                        	<input type="hidden" value="form" name="X-Form-type">
			                        </div>
			                        <div class="clearfix" id="login-footer">			                          		                            
			                            <!-- <input type="button" tabindex="4" id="login_submit" placeholder="请输入密码" class="freshbutton-blue" value="登 录" name="login_submit_dummy"> -->
			                            <button type="button" tabindex="4" id="login_submit" name="login_submit_dummy" class="loginBtn">登 录</button>
			                        </div>	
			                        <label id="formerror" class="error">${sessionScope.SPRING_SECURITY_LAST_EXCEPTION.message}</label>             
			                    </div>
			                    
			                </form>
			            </div>		            
			        </div>
			    </div>
			    <div id="page-full-footer">
			      <!--   <div id="footer-top-margin"></div>
			        <div id="footer-border"></div> -->
			        <spring:message code="app.copyright" /> 
			        version:<spring:message code="app.version" /> 
			        <div class="clear"></div>
			    </div>
			    <noscript><p class="center"><spring:message code="app.name" /> requires JavaScript.</p></noscript>
			</div>
	</div>
</body>
</html>