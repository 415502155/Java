<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<%
	String path = request.getContextPath();
%>
<c:set var="path" value="<%=path%>"></c:set>

<style>
.searchContent span.info {
    color: #7f7f7f;
    display: block;
    float: left;
    line-height: 21px;
}
th,td{text-align: center !important;}
</style>

<form id="pagerForm" method="post" action="${ctx}/manage/menu/list">
	<input type="hidden" name="pageNum" value="1" />
	<input type="hidden" name="numPerPage" value="${orgList.limit}" />
	<input type="hidden" name="totalPage" value="${orgList.total}" />
</form>


<div class="pageHeader">
	<form onsubmit="return navTabSearch(this);"  rel="pagerForm" method="post" action="${ctx}/manage/menu/list">
		<div class="searchBar">
			<table class="searchContent">
				<tr>
					<td>
						机构名称：<input type="text" name="searchName" value="" />
					</td>
					<td><div class="buttonActive"><div class="buttonContent"><button type="submit">检索</button></div></div></td>
				</tr>
			</table>
		</div>
	</form>
</div>
<div class="pageContent">
	<table class="table" width="100%" layoutH="138">
		<thead>
			<tr>
				<th width="30%">机构名称</th>
				<th width="20%">机构简称</th>
				<th width="10%">类型</th>
				<th width="30%">备注</th>
				<th width="10%">操作</th>
			</tr>
		</thead>
		<tbody id="updateDataMenu">		
			<c:forEach var="org" items="${orgList.data}">
				<tr target="sid_user" rel="1">
					<td class="text-left">${org.org_name_cn}</td>
					<td>${org.org_name_s_cn}</td>
					<td>${org.type4Display}</td>
					<td>${org.remark}</td>
					<td>
						<a title="编辑菜单" onclick="assign($(this))" id='${org.org_id}' target="dialog" href="${ctx}/manage/menu/goEdit?id=${org.org_id}" class="btnEdit" rel="editMenuDialog" width="1024" height="768" mask="true">编辑菜单</a>
					</td>
				</tr>
			</c:forEach>				
		</tbody>
	</table>
	<div class="panelBar">
		<div class="pages">
			<span>显示</span>
			<select class="combox" name="numPerPage" onchange="navTabPageBreak({numPerPage:this.value})">
				<option value="25" <c:if test="${orgList.limit==25}">selected="selected"</c:if>>25</option>
				<option value="50" <c:if test="${orgList.limit==50}">selected="selected"</c:if>>50</option>
				<option value="100" <c:if test="${orgList.limit==100}">selected="selected"</c:if>>100</option>
				<option value="200" <c:if test="${orgList.limit==200}">selected="selected"</c:if>>200</option>
			</select>
			<span>条，共${orgList.total}条</span>
		</div>

		<div class="pagination"  targetType="navTab" totalCount="${orgList.total}" numPerPage="${orgList.limit}" pageNum="${orgList.page}" pageNumShown="5" currentPage="${orgList.page}"></div>

	</div>
</div>
<script type="text/javascript">
  var toOrg="";  
  function assign(a) {
	  toOrg=a.attr("id");
  }
</script>

