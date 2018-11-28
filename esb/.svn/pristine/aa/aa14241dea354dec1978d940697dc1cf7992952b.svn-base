<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<form id="pagerForm" method="post" action="../user/activenesslist">
	<input type="hidden" name="username" value="${param.username}" />
	<input type="hidden" name="currentPage" value="${param.currentPage}" />
	<input type="hidden" name="numPerPage" value="${operations.limit}" />
	<input type="hidden" name="pageNum" value="${operations.page}" />
</form>


<div class="pageHeader">
	<form onsubmit="return navTabSearch(this);" action="../user/activenesslist" method="post">
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
				<td>${param.mid}
					用户昵称：<input type="text" name="username" value="${param.username}" />
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
					
		</ul>
	</div>
	<table class="table" width="100%" layoutH="134">
		<thead>
			<tr>
				<th width="80">选择</th>
				<th width="120">序号</th>
				<th width="120">用户名</th>
				<th width="100">昵称</th>
				<th width="100">活跃度</th>
				<th width="120" align="center">操作</th>
			</tr>
		</thead>
		<tbody>
		
		<c:forEach var="operation" items="${operations.data}">
			<tr target="sid_user" rel="${operation.oid}">
				<td><input type="checkbox" /></td>
				<td>${operation.oid}</td>
				<td>${operation.username}</td>
				<td>${operation.nickname}</td>
				<td>${operation.ocount}</td>
				<td>					
					<a title="查看详细" title="${operation.username}" target="dialog" width="700" height="400" href="../user/viewactivenes?uid=${operation.uid}" rel="viewactivenes">查看详细</a>
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

		<div class="pagination" targetType="navTab" totalCount="${operations.total}" numPerPage="${operations.limit}" pageNum="${operations.page}" pageNumShown="5" currentPage="${operations.page}"></div>

	</div>
</div>


