<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<% String basePath = request.getContextPath();%>
<!DOCTYPE html>
<jsp:include page="common.jsp"/>
<html>
<head>
<link href="<%=basePath %>/static/css/student/student.css" rel="stylesheet" type="TEXT/CSS"/>
<script type="text/javascript" src="<%=basePath %>/static/js/student/student.js"></script>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>学生操作</title>
</head>
<body>
	<a class="return" onClick="returnClassHandle();"><< 班级操作</a><h2>学生操作</h2>
	<h3>添加学生</h3>
	<span>输入学生姓名：<input type="text" name="student_name"/></span>
	选择班级：<select id="check_class">
				<option value="0">不选择</option>
			</select>
	<input id="add_student" type="button" value="添加"/>
	<h3>根据班级查询学生</h3>
	选择班级：<select id="check_class_search">
				<option value="0">所有班级</option>
			</select>
	<input id="search_student_class" type="button" value="查询"/><br>
	输入姓名：<input name="search_student_name" type="text" />
	<input id="search_student_name" type="button" value="查询"/>
	<div id="student_list">
		<h3>学生列表</h3>
		<ul id="student_list_container">
			<li>
				<span>学号</span>&nbsp;&nbsp;&nbsp;&nbsp;
				<span>姓名</span>&nbsp;&nbsp;&nbsp;&nbsp;
				<span>班级</span>
			</li>
			<li id="student_list_template">
				<span>学号</span>&nbsp;&nbsp;&nbsp;&nbsp;
				<span>姓名</span>&nbsp;&nbsp;&nbsp;&nbsp;
				<span>班级</span>&nbsp;&nbsp;&nbsp;&nbsp;
				<input class="edit" type="button" value="修改"/>&nbsp;&nbsp;&nbsp;&nbsp;
				<input class="delete" type="button" value="删除"/><br>
			</li>
		</ul>
		
	</div>
	
	
	
	
	
	
	
	
	
</body>
</html>