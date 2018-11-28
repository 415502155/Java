<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<c:set var="path" value="<%=path%>"></c:set>
<!DOCTYPE html>
<html>
<head>
  <meta charset="utf-8">
  <title></title>
  <meta name="viewport" content="width=device-width, initial-scale=1, user-scalable=0">
  <link rel="stylesheet" href="<%=path%>/css/weixin/style.css">
  <script type="text/javascript">
  	function loadIndexPage() {
  		document.getElementById('submitForm').submit();
  	}
  </script>
</head>
<body onload="loadIndexPage();">
	<div style="display:none">
		<form id="submitForm" action="<%=path%>/wechat/portal/index.htm" method="post" enctype="application/x-www-form-urlencoded"></form>
	</div>
</body>
</html>

