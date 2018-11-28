<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<%
	String ctx = request.getContextPath();
%>
<c:set var="path" value="<%=ctx%>"></c:set>
<style>
input[disabled] {background: #f0f0f0;}
</style>
<form id="pagerForm" method="post" action="${ctx}/manage/user/list">
	<input type="hidden" name="loginname" value="${param.loginname}" />
	<input type="hidden" name="org_id" value="${param.org_id}" />
	<input type="hidden" name="mobile" value="${param.mobile}" />
	<input type="hidden" name="identity" value="${param.identity!=null?param.identity:1}" />	
	<input type="hidden" name="pageNum" value="${users.page}" />
	<input type="hidden" name="numPerPage" value="${users.limit}" />
	<input type="hidden" name="totalPage" value="${users.total}" />
</form>


<div class="pageHeader">
	<form onsubmit="return navTabSearch(this);" action="${ctx}/manage/user/list" method="post">
	<div class="searchBar">
		<table class="searchContent">
			<tr>
				<td>
					学生卡号：<input type="text" name="card" value="${param.card}"  id="card-num"/>
				</td>
				<td>
					手机号：<input id="mobile" type="text" name="mobile" value="${param.mobile}" />
				</td>
				<td>机构：</td>
				<td>
					<select class="combox" name="org_id">
						<option value="" <c:if test="${''==param.org_id}" > selected="selected" </c:if>>所有机构</option>
						<option value="0" <c:if test="${'0'==param.org_id}" > selected="selected" </c:if>>未分配用户</option>
						<c:forEach items="${orgs}" var="org">									
							<option value="${org.org_id}" <c:if test="${org.org_id==param.org_id}" > selected="selected" </c:if>>${org.org_name_cn}</option>									
						</c:forEach>
					</select>
				</td>
				<td>用户类型：</td>
				<td>
					<select class="combox" name="identity">
						<option value="2" <c:if test="${'2'==param.identity}" > selected="selected" </c:if>>学生</option>
						<option value="1" <c:if test="${'1'==param.identity}" > selected="selected" </c:if>>教师</option>
						<option value="0" <c:if test="${'0'==param.identity}" > selected="selected" </c:if>>家长</option>
					</select>
				</td>
				<td><div class="buttonActive"><div class="buttonContent"><button type="submit" id="search">检索</button></div></div></td>
			</tr>
		</table>
	</div>
	</form>
</div>
<div class="pageContent">
	<!--  <div class="panelBar">
		<ul class="toolBar">			
			<li><a class="add" width="700" height="500" href="${ctx}/manage/user/goAdd" target="dialog" rel="addUser"><span>添加</span></a></li>
			<li><a class="icon" href="${ctx}/manage/user/expertExcel" targettype="dialog" title="实要导出这些记录吗?"><span>导出EXCEL</span></a></li>			
			<li><a class="icon" href="${ctx}/manage/user/importExcel" target="dialog" rel="importExcel"><span>导入EXCEL</span></a></li>	
		</ul>
	</div>-->

			<table class="table" width="100%" layoutH="134">
				<thead>
					<tr>
						<th width="50" align="center">选择</th>
						<th width="50" align="center">序号</th>
						<th width="200" align="center">机构名称</th>
						<th width="100" align="center">人员名称</th>
						<th width="100" align="center">登录名</th>
						<th width="100" align="center">手机号</th>
						<th width="200" align="center">卡号</th>
						<th width="100" align="center">身份</th>
						<th width="100" align="center">注册时间</th>
						<th width="100" align="center">操作</th>
					</tr>
				</thead>
				<tbody>
				
				<c:forEach var="user" items="${users.data}" varStatus="status">
					<tr target="sid_user">
						<td><input type="checkbox" /></td>
						<td>${status.count+users.limit*(users.page-1)}</td>
						<td>${user.organization.org_name_cn}</td>
						<td>
							<c:choose>
						    	<c:when test="${user.identity == 0}">
							          ${user.parent.parent_name}
							    </c:when>
							    <c:when test="${user.identity == 1}">
							          ${user.teacher.tech_name}
							    </c:when>
							    <c:otherwise>
							           ${user.student.stud_name}
							    </c:otherwise>
							</c:choose>
						</td>
						<td>${user.user_loginname}</td>
						<td>${user.user_mobile}</td>
						<td>${user.user_mail}</td>
						<td>
							<c:choose>
						    	<c:when test="${user.identity == 0}">
							          <font style="color:green; margin-right:20px">家长</font>
							    </c:when>
							    <c:when test="${user.identity == 1}">
							          <font style="color:green; margin-right:20px">教师</font>
							    </c:when>
							    <c:otherwise>
							           <font style="color:green; margin-right:20px">学生</font>
							    </c:otherwise>
							</c:choose>					
						</td>
						<td><fmt:formatDate value="${user.insert_time}" type="date" dateStyle="long"/></td>
						<td>
							<div>
								<a title="编辑" target="dialog" href="${ctx}/manage/user/goEdit?uid=${user.user_id}&org_id=${user.org_id}" width="640" height="700" class="btnEdit" mask="true">编辑</a>
								<c:if test="${user.identity == 1 or user.identity == 2}">
								<a title="删除" target="ajaxTodo" href="${ctx}/manage/user/delete?uid=${user.user_id}&identity=${user.identity}" class="btnDel">删除</a>
								</c:if>
							</div>
						</td>
					</tr>
				</c:forEach>
					
				</tbody>
			</table>

	<div class="panelBar">
		<div class="pages">
			<span>显示</span>
			<select class="combox" name="numPerPage" onchange="navTabPageBreak({numPerPage:this.value})">
				<option value="25" <c:if test="${users.limit==25}">selected="selected"</c:if>>25</option>
				<option value="50" <c:if test="${users.limit==50}">selected="selected"</c:if>>50</option>
				<option value="100" <c:if test="${users.limit==100}">selected="selected"</c:if>>100</option>
				<option value="200" <c:if test="${users.limit==200}">selected="selected"</c:if>>200</option>
			</select>
			<span>条，共${users.total}条</span>
		</div>

		<div class="pagination" targetType="navTab" totalCount="${users.total}" numPerPage="${users.limit}" pageNum="${users.page}" pageNumShown="5" currentPage="${users.page}"></div>

	</div>
</div>

<script type="text/javascript">
	$("#mobile").keyup(function(){  
	   var c=$(this);  
	   if(/[^\d]/.test(c.val())){//替换非数字字符  
	     var temp_amount=c.val().replace(/[^\d]/g,'');  
	     $(this).val(temp_amount);  
	   }else if(c.val().trim().length==11&&(/^1[3|4|5|8][0-9]\d{4,8}$/.test(c.val()))){
	   	$("#search").trigger("click");
	   }  
})  

$("#mobile").blur(function(){
	var val=$.trim($(this).val());
	if(val!==""){
		$("#card-num").attr("disabled",true)
	}else{
		$("#card-num").attr("disabled",false)
	}
}).keyup(function(){
	var val=$.trim($(this).val());
	if(val!==""){
		$("#card-num").attr("disabled",true)
	}else{
		$("#card-num").attr("disabled",false)
	}
}).focus(function(){
	var val=$.trim($("#card-num").val());
	if(val!==""){
		$(this).blur().attr("disabled",true)
		return false
	} 
});
	
$("#card-num").keyup(function(){
	var val=$.trim($(this).val());
	if(val!==""){
		$("#mobile").attr("disabled",true)
	}else{
		$("#mobile").attr("disabled",false)
	}
}).blur(function(){
	var val=$.trim($(this).val());
	if(val!==""){
		$("#mobile").attr("disabled",true)
	}else{
		$("#mobile").attr("disabled",false)
	}
}).focus(function(){
	var val=$.trim($("#mobile").val());
	if(val!==""){
		$(this).blur().attr("disabled",true)
		return false
	} 
});

</script>