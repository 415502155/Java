<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="org.springframework.security.web.WebAttributes" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
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
<%
	String path = request.getContextPath();
%>
<body>
	<div id="main">
		<div class="page-header-border " id="page-header">
			<div id="inner-page-header">
				<span class="home-icon-container" style="font-size:45px; line-height:64px">
					<a id="home-icon" href="${ctx}"><i class="login-imglogo"><img alt="" src="${ctx}/images/logo-big.png" width="60" height="60"></i>津南局SSO用户管理平台</a>
				</span>
			</div>
			<div style="clear: both;"></div>
		</div>
		<div id="outer-frame">
			    <div id="page-content">
			    <c:if test="${user==null}">
			        <div class="" id="login-and-register-container">
			            <div id="login-container">
			                <form method="post" name="loginform" novalidate="novalidate" action="${ctx}/LoginPost">		                   
			                    <!-- <div class="page-header-text">登 录</div> -->
			                    <div id="login-partial">
			                        <input type="hidden" value="desktop" name="display">
			                        <div class="sick-input" id="username-field">
			                        	<span style="float: left">用户名</span>
			                            <input type="text" name="login_name" tabindex="1" autofocus="1" autocomplete="off" placeholder="请输入用户名">
			                        </div>
			                        <div class="sick-input" id="password-field">	
			                        	<span style="float: left">用户密码</span>	               
			                            <input type="password" tabindex="2" name="login_pass" autocomplete="off" placeholder="请输入密码">
			                        </div>
			                        <div class="item-ifo">
			                        	<input type="hidden" value="form" name="X-Form-type">
			                        </div>
			                        <div class="clearfix" id="login-footer">			                          		                            
			                            <!-- <input type="button" tabindex="4" id="login_submit" placeholder="请输入密码" class="freshbutton-blue" value="登 录" name="login_submit_dummy"> -->
			                            <button type="submit" tabindex="4" name="loginpost_submit" class="loginBtn">登 录</button>
			                        </div>	
			                        <label id="formerror" class="error">${error}</label>             
			                    </div>
			                    
			                </form>
			            </div>		            
			        </div>			        
		        </c:if>							
				<c:if test="${user!=null}">
					 <ul>
		            	<c:if test="${fn:length(orgusers)>1}"><li id="dl-button" title="切换机构">切换机构</li></c:if>
		            	<li title="机构信息">${user.organization.org_name_cn}</li>
		            	<li title="用户信息">
		            	<c:if test="${user.identity==0}" >(身份:家长)</c:if>
					<c:if test="${user.identity==1}" >(身份:教师)</c:if>
					<c:if test="${user.identity==2}" >(身份:学生)</c:if>
					<c:if test="${user.identity==99}" >(身份:校管理员)</c:if>
		            	</li>
		                <li title="管理员消息">
		                	<c:if test="${user.identity==0||user.identity==99}" >
			                	<span class="userHead">
			                		<c:if test="${user.parent.headurl!=''&&user.parent.headurl!=null}" >
			                		<img src="${user.parent.headurl}" id="userHead" alt="" width="32" height="32" style="vertical-align: top;">
			                		</c:if>
			                		<c:if test="${user.parent.headurl==''||user.parent.headurl==null}" >
			                		<img src="${ctx}/front/images/defaultHead.jpg" id="userHead" alt="" width="32" height="32" style="vertical-align: top;">
			                		</c:if>
			                	</span>
			                    <span id="userName" class="userName">${user.parent.parent_name}</span>
		                	</c:if>
							<c:if test="${user.identity==1}" >
								<span class="userHead">
			                		<c:if test="${user.teacher.headurl!=''&&user.teacher.headurl!=null}" >
			                		<img src="${user.teacher.headurl}" id="userHead" alt="" width="32" height="32" style="vertical-align: top;">
			                		</c:if>
			                		<c:if test="${user.teacher.headurl==''||user.teacher.headurl==null}" >
			                		<img src="${ctx}/front/images/defaultHead.jpg" id="userHead" alt="" width="32" height="32" style="vertical-align: top;">
			                		</c:if>
			                	</span>
			                    <span id="userName" class="userName">${user.teacher.tech_name}</span>
							</c:if>
							<c:if test="${user.identity==2}" >
								<span class="userHead">
								${user.student.headurl}
			                		<c:if test="${user.student.headurl!=''&&user.student.headurl!=null}" >
			                		<img src="${user.student.headurl}" id="userHead" alt="" width="32" height="32" style="vertical-align: top;">
			                		</c:if>
			                		<c:if test="${user.student.headurl==''||user.student.headurl==null}" >
			                		<img src="${ctx}/front/images/defaultHead.jpg" id="userHead" alt="" width="32" height="32" style="vertical-align: top;">
			                		</c:if>
			                	</span>
			                    <span id="userName" class="userName">${user.student.stud_name}</span>
							</c:if>
		                </li>
		                <!--   <li title="查看消息" id="toSeeNews">
		                      <i class="fa fa-bell-o" style="font-size: 18px;"></i>
		                      <span class="newsMessagesTips">5</span>
		                  </li> -->
		                  <li id="pwd-button" title="密码修改">密码修改</li>
		                  <a href="logout"><li title="退出登录" id="quit">
		                  	<i class="fa fa-sign-out" style="font-size: 24px;"></i>
		                  </li></a>	                  
		              </ul>					
				</c:if>
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