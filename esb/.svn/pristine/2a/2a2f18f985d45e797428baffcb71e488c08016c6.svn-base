<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<style>
.searchContent span.info {
    color: #7f7f7f;
    display: block;
    float: left;
    line-height: 21px;
}
</style>
<form id="pagerForm" method="post" action="../notice/list">
	<input type="hidden" name="title" value="${param.title}" />
	<input type="hidden" name="start" value="${param.start}" />
	<input type="hidden" name="end" value="${param.end}" />
	<input type="hidden" name="currentPage" value="${param.currentPage}" />
	<input type="hidden" name="numPerPage" value="${notices.limit}" />
	<input type="hidden" name="pageNum" value="${notices.page}" />
</form>


<div class="pageHeader">
	<form onsubmit="return navTabSearch(this);" action="../notice/list" method="post">
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
		<table class="searchContent">
			<tr>
				<td>
					公告标题：<input type="text" name="title" value="${param.title}" />
				</td>
				<td style="width: 290px;">
					<label style="width: 60px;">开始日期：</label>
					<input type="text" name="start" class="date" dateFmt="yyyy-MM-dd" value="${param.start}" style="float: left;"/>
					<a class="inputDateButton" href="javascript:;">选择</a>
					<span class="info">yyyy-MM-dd</span>
				</td>
				<td style="width: 290px;">
					<label style="width: 60px;">结束日期：</label>
					<input type="text" name="end" class="date" dateFmt="yyyy-MM-dd" value="${param.end}" style="float: left;"/>
					<a class="inputDateButton" href="javascript:;">选择</a>
					<span class="info">yyyy-MM-dd</span>
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
			<li><a class="add" href="../notice/add" target="dialog" rel="addnotice"><span>添加</span></a></li>	
		</ul>
	</div>
	<table class="table" width="100%" layoutH="138">
		<thead>
			<tr>
				<th width="30">选择</th>
				<th width="30">序号</th>
				<th>公告标题</th>
				<th width="130">发布时间</th>
				<th width="80">操作</th>
			</tr>
		</thead>
		<tbody>		
			<c:forEach var="notice" items="${notices.data}">
				<tr target="sid_user" rel="1">
					<td><input type="checkbox" /></td>
					<td>${notice.nid}</td>
					<td>${notice.title}</td>
					<td><fmt:formatDate value="${notice.inserttime}" type="date" pattern="yyyy-MM-dd HH:mm:ss"/></td>
					<td><a title="删除" target="ajaxTodo" href="../notice/delete?nid=${notice.nid}" class="btnDel">删除</a>
						<a title="编辑" target="dialog" href="../notice/edit?nid=${notice.nid}" class="btnEdit" rel="editmodule">编辑</a></td>
				</tr>
			</c:forEach>				
		</tbody>
	</table>
	<div class="panelBar">
		<div class="pages">
			<span>显示</span>
			<select class="combox" name="numPerPage" onchange="navTabPageBreak({numPerPage:this.value})">
				<option value="25" <c:if test="${notices.limit==25}">selected="selected"</c:if>>25</option>
				<option value="50" <c:if test="${notices.limit==50}">selected="selected"</c:if>>50</option>
				<option value="100" <c:if test="${notices.limit==100}">selected="selected"</c:if>>100</option>
				<option value="200" <c:if test="${notices.limit==200}">selected="selected"</c:if>>200</option>
			</select>
			<span>条，共${notices.total}条</span>
		</div>

		<div class="pagination" targetType="navTab" totalCount="${notices.total}" numPerPage="${notices.limit}" pageNum="${notices.page}" pageNumShown="5" currentPage="${notices.page}"></div>

	</div>
</div>


