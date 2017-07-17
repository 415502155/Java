<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<% String basePath = request.getContextPath();%>
<!DOCTYPE html>
<jsp:include page="common.jsp"/>
<html>
<head>
<link href="<%=basePath %>/static/css/class/class.css" rel="stylesheet" type="TEXT/CSS"/>
<script type="text/javascript" src="<%=basePath %>/static/js/class/class.js"></script>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>班级操作</title>
</head>
<body>
	<a class="return" onClick="returnStudentHandle();"><< 返回学生操作</a><h2>班级操作</h2>
	<span>添加班级名称：</span>
	<input name="class_name" type="text" /> 
	<input id="add_class" type="button" value="添加"/>
	<input id="search_class" type="button" value="查询"/>

	<div id="class_list">
		<h3>学生列表</h3>
		<ul id="class_list_container">
			<li>
				<span>班级编号</span>&nbsp;&nbsp;&nbsp;&nbsp;
				<span>班级名称</span>&nbsp;&nbsp;&nbsp;&nbsp;
			</li>
			<li id="class_list_template">
				<span>班级编号</span>&nbsp;&nbsp;&nbsp;&nbsp;
				<span>班级名称</span>&nbsp;&nbsp;&nbsp;&nbsp;
				<input class="edit" type="button" value="修改"/>&nbsp;&nbsp;&nbsp;&nbsp;
				<input class="delete" type="button" value="删除"/><br>
			</li>
		</ul>
	</div>
	<h3>未添加班级的学生列表</h3>
	<div id="student_list">
		<ul id="student_list_container">
			<li>
				<span>学号</span>&nbsp;&nbsp;&nbsp;&nbsp;
				<span>姓名</span>&nbsp;&nbsp;&nbsp;&nbsp;
				<span>选择班级</span>
			</li>
			<li id="student_list_template">
				<span>学号</span>&nbsp;&nbsp;&nbsp;&nbsp;
				<span>姓名</span>&nbsp;&nbsp;&nbsp;&nbsp;
				<select class="check_class">
				</select>
				<input class="add_class" type="button" value="添加"/>&nbsp;&nbsp;&nbsp;&nbsp;
			</li>
		</ul>
	</div>
</body>
</html>