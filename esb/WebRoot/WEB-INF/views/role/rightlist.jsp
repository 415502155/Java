<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<form id="pagerForm" method="post" action="${ctx}/manage/role/list">
	<input type="hidden" name="pageNum" value="1" />
	<input type="hidden" name="numPerPage" value="${paging.limit}" />
	<input type="hidden" name="totalPage" value="${paging.total}" />
</form>

<div class="pageHeader">
	<form onsubmit="return navTabSearch(this);" action="${ctx}/manage/role/rightlist" method="post">
	<div class="searchBar">
		<table class="searchContent">
			<tr>
				<td>
					权限名：<input type="text" name="searchName" value="" />
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
			<li><a class="add" href="${ctx}/manage/role/rightadd" target="dialog" rel="addRole"><span>添加</span></a></li>	
		</ul>
	</div>
	<table class="table" width="100%" layoutH="134">
		<thead>
			<tr>
				<th width="50" align="center">选择</th>
				<th width="50" align="center">序号</th>
				<th width="100" align="center">权限名</th>
				<th width="100" align="center">权限编码</th>
				<th width="100" align="center">操作</th>
			</tr>
		</thead>
		<tbody>
		
		<c:forEach var="right" items="${paging.data}" varStatus="status">
			<tr target="sid_role">
				<td><input type="checkbox" /></td>
				<td>${status.count+paging.limit*(paging.page-1)}</td>
				<td>${right.right_name}</td>
				<td>${right.right_code}</td>
				<td>
					<div>
						<a title="编辑" target="dialog" href="${ctx}/manage/role/rightEdit?rid=${right.right_id}" class="btnEdit">编辑</a>
						<a title="删除" target="ajaxTodo" href="${ctx}/manage/role/rightdelete?rid=${right.right_id}" class="btnDel">删除</a>
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
				<option value="25" <c:if test="${paging.limit==25}">selected="selected"</c:if>>25</option>
				<option value="50" <c:if test="${paging.limit==50}">selected="selected"</c:if>>50</option>
				<option value="100" <c:if test="${paging.limit==100}">selected="selected"</c:if>>100</option>
				<option value="200" <c:if test="${paging.limit==200}">selected="selected"</c:if>>200</option>
			</select>
			<span>条，共${paging.total}条</span>
		</div>

		<div class="pagination" targetType="navTab" totalCount="${paging.total}" numPerPage="${paging.limit}" pageNum="${paging.page}" pageNumShown="5" currentPage="${paging.page}"></div>

	</div>
</div>

