<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<script type="text/javascript">
$("#searchMobile").keyup(function(){  
   var c=$(this);  
   if(/[^\d]/.test(c.val())){//替换非数字字符  
     var temp_amount=c.val().replace(/[^\d]/g,'');  
     $(this).val(temp_amount);  
   }  
})  
</script>


<form id="pagerForm" method="post" action="${ctx}/manage/role/goLook">
	<input type="hidden" name="rid" value="${role_id}" />
	<input type="hidden" name="pageNum" value="1" />
	<input type="hidden" name="numPerPage" value="${users.limit}" />
	<input type="hidden" name="totalPage" value="${users.total}" />
</form>


<div class="pageHeader">
	<form onsubmit="return navTabSearch(this);" action="${ctx}/manage/role/goLook" method="post">
	<div class="searchBar">
		<input type="hidden" name="rid" value="${role_id}" />
		<table class="searchContent">
			<tr>
				<td>
					姓名：<input type="text" name="searchName" value="" />
				</td>
				<td>
					手机号：<input id="searchMobile" type="text" name="searchMobile" value="" />
				</td>
				<td>
					邮箱地址：<input type="text" name="searchMail" value="" />
				</td>
				<td>用户类型：</td>
				<td>
					<select class="combox" name="searchType">
						<option value="">所有类型</option>
						<option value="1">身份证</option>
						<option value="2">区域+编号</option>
					</select>
				</td>
				<td><div class="buttonActive"><div class="buttonContent"><button type="submit">检索</button></div></div></td>
			</tr>
		</table>
	</div>
	</form>
</div>
<div class="pageContent">
	<div class="panelBar">
		<ul class="toolBar">			
			<li><span>角色</span><span style="color:green">${roleName }</span><span>下属所有用户</span></li>	
		</ul>
	</div>
	<table class="table" width="100%" layoutH="134">
		<thead>
			<tr>
				<th width="50" align="center">序号</th>
				<th width="100" align="center">类型</th>
				<th width="100" align="center">姓名</th>
				<th width="150" align="center">手机号</th>
				<th width="200" align="center">邮箱地址</th>
				<th width="150" align="center">注册时间</th>
			</tr>
		</thead>
		<tbody>
		
		<c:forEach var="user" items="${users.data}">
			<tr target="sid_user">
				<td>${user.sso_id}</td>
				<td>${user.sso_type==1?'身份证':'区域+编号'}</td>
				<td>${user.login_name}</td>
				<td>${user.sso_mobile}</td>
				<td>${user.sso_mail}</td>
				<td><fmt:formatDate value="${user.insert_time}" type="date" dateStyle="long"/></td>
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

