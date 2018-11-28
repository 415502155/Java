<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="org.springframework.security.web.WebAttributes" %>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<!DOCTYPE html>
<html>
<head>
<%@ include file="/common/global.jsp"%>
<title><spring:message code="app.name" /></title>
<%@ include file="/common/meta.jsp" %>
<script type="text/javascript">
	window.location.href="${ctx}/manage/main/index";
</script>
</head>

</html>