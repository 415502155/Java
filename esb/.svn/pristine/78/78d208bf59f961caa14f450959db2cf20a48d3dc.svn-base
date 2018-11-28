<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<style>
	th,td{text-align: center !important;}
</style>

<form id="pagerForm" method="post" action="${ctx}/manage/function/goLook">
	<input type="hidden" name="id" value="${function.fun_id}" />
	<input type="hidden" name="pageNum" value="1" />
	<input type="hidden" name="numPerPage" value="${modules.limit}" />
	<input type="hidden" name="totalPage" value="${modules.total}" />
</form>

<div class="pageHeader">
	当前功能模块 : ${function.fun_name}<br/><br/>
	<form onsubmit="return navTabSearch(this);" action="${ctx}/manage/function/goLook" method="post">
		<input type="hidden" name="id" value="${function.fun_id}" />
		<div class="searchBar">
			<table class="searchContent">
				<tr>
					<td>
						机构名称：<input type="text" name="orgName" value="" />
					</td>
					<td><div class="buttonActive"><div class="buttonContent"><button type="submit">检索</button></div></div></td>
				</tr>
			</table>
		</div>
	</form>
</div>
<div class="pageContent j-resizeGrid">
	<table class="table" width="100%" layoutH="138">
		<thead>
			<tr>
				<th width="15%">机构名称</th>
				<th width="10%">机构简称</th>
				<th width="15%">区域</th>
				<th width="10%">类型</th>
				<th width="20%">地址</th>
				<th width="30%">备注</th>
			</tr>
		</thead>
		<tbody>		
			<c:forEach var="org" items="${orgList.data}">
				<tr target="sid_user" rel="1">
					<td>${org.org_name_cn}</td>
					<td>${org.org_name_s_cn}</td>
					<td>${org.area}</td>
					<td>${org.type4Display}</td>
					<td>${org.address}</td>
					<td>${org.remark}</td>
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

		<div class="pagination" targetType="navTab" totalCount="${orgList.total}" numPerPage="${orgList.limit}" pageNum="${orgList.page}" pageNumShown="5" currentPage="${orgList.page}"></div>

	</div>
</div>


<script type="text/javascript">
$(".goLook").hover(function(){
	$(this)[0].style.backgroundColor ="#d0d0d0";
},function(){
	$(this)[0].style.backgroundColor ="";
});
</script>
