<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<form id="pagerForm" method="post" action="../user/viewactivenes">
	<input type="hidden" name="uid" value="${param.uid}" />
	<input type="hidden" name="currentPage" value="${param.currentPage}" />
	<input type="hidden" name="numPerPage" value="${operations.limit}" />
	<input type="hidden" name="pageNum" value="${operations.page}" />
</form>


<div class="pageHeader">
	<form onsubmit="return navTabSearch(this);" action="../user/viewactivenes" method="post">
	<div class="searchBar">
		<!--<ul class="searchContent">
			<li>
				<label>我的客户：</label>
				<input type="text"/>
			</li>
			<li>
			<select class="combox" name="province">
				<option value="">所有省市</option>
				<option value="北京">北京</option>
				<option value="上海">上海</option>
				<option value="天津">天津</option>
				<option value="重庆">重庆</option>
				<option value="广东">广东</option>
			</select>
			</li>
		</ul>
		-->		
	</div>
	</form>
</div>
<div class="pageContent">
	<div class="panelBar">
		<ul class="toolBar">								
		</ul>
	</div>
	<table class="table" width="100%" layoutH="134" >
		<thead>
			<tr>
				<th width="80">选择</th>
				<th width="120">操作类型</th>
				<th width="100">app类型</th>
				<th width="100">app版本</th>
				<th width="120" align="center">时间</th>
			</tr>
		</thead>
		<tbody>
		
		<c:forEach var="operation" items="${operations.data}">
			<tr target="sid_user" rel="${operation.oid}">
				<td><input type="checkbox" /></td>
				<td>
					<c:choose>
					<c:when test="${operation.otype == 2}">
					app登陆
					</c:when>
					<c:when test="${operation.otype == 3}">
					app退出
					</c:when>
					<c:when test="${operation.otype == 0}">
					后台切入前台
					</c:when>
					<c:when test="${operation.otype == 1}">
					切入后台
					</c:when>
					<c:otherwise>
					普通操作
					</c:otherwise>
					</c:choose>
				</td>
				<td>${operation.apptype}</td>
				<td>${operation.oversion}</td>
				<td>					
					<fmt:formatDate value="${operation.inserttime}" type="both"/>
				</td>
			</tr>
		</c:forEach>
			
		</tbody>
	</table>
	<div class="panelBar">
		<div class="pages">
			<span>显示</span>
			<select class="combox" name="numPerPage" onchange="navTabPageBreak({numPerPage:this.value})">
				<option value="25" <c:if test="${operations.limit==25}">selected="selected"</c:if>>25</option>
				<option value="50" <c:if test="${operations.limit==50}">selected="selected"</c:if>>50</option>
				<option value="100" <c:if test="${operations.limit==100}">selected="selected"</c:if>>100</option>
				<option value="200" <c:if test="${operations.limit==200}">selected="selected"</c:if>>200</option>
			</select>
			<span>条，共${operations.total}条</span>
		</div>

		<div class="pagination" targetType="dialog" totalCount="${operations.total}" numPerPage="${operations.limit}" pageNum="${operations.page}" pageNumShown="5" currentPage="${operations.page}"></div>

	</div>
</div>


