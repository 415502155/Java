<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<style>
	th,td{text-align: center !important;}
</style>

<form id="pagerForm" method="post" action="${ctx}/manage/function/list">
	<input type="hidden" name="pageNum" value="1" />
	<input type="hidden" name="numPerPage" value="${modules.limit}" />
	<input type="hidden" name="totalPage" value="${modules.total}" />
</form>

<div class="pageHeader">
	<form onsubmit="return navTabSearch(this);" action="${ctx}/manage/function/list" method="post">
	<div class="searchBar">
		<table class="searchContent">
			<tr>
				<td>
					功能名称：<input type="text" name="searchName" value="" />
				</td>
				<td><div class="buttonActive"><div class="buttonContent"><button type="submit">检索</button></div></div></td>
			</tr>
		</table>
	</div>
	</form>
</div>
<div class="pageContent j-resizeGrid">
	<div class="panelBar">
		<ul class="toolBar">			
			<li><a class="add" href="${ctx}/manage/function/goAdd" width="630" height="760" target="dialog" rel="addFunction"><span>添加</span></a></li>
		</ul>
	</div>
	<table class="treetable" width="100%" layoutH="134">
		<thead>
			<tr>
				<th width="50" align="center">顺序号</th>
				<th width="50" align="center">名称</th>
				<th width="100" align="center">适用机构</th>
				<th width="100" align="center">备注</th>
				<th width="100" align="center">机构使用数量</th>
				<th width="100" align="center">当前版本</th>
				<th width="100" align="center">当前状态</th>
				<th width="100" align="center">操作</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach var="module" items="${modules.data}">
				<tr class="hover" target="sid_module" rel="${module.mod_id}" data-tt-id="${module.mod_id}">
					<td>${module.mod_order}</td>
					<td><b style="color:green">${module.mod_name}</b></td>
					<td></td>
					<td></td>
					<td></td>
					<td></td>
					<td></td>
					<td>
						<div>
							<a title="添加功能" target="dialog" width="630" height="760" href="${ctx}/manage/function/goAdd?id=${module.mod_id}" target="dialog" rel="addFunction" class="btnAdd"><span>添加</span></a>
							<a title="编辑模块" target="dialog" width="600" height="350" href="${ctx}/manage/function/goEdit?type=m&id=${module.mod_id}" class="btnEdit" rel="editmodule">编辑</a>
							<%-- <a title="删除模块" target="ajaxTodo" href="${ctx}/manage/function/delete?type=m&id=${module.mod_id}" class="btnDel">删除</a> --%>
						</div>
					</td>
				</tr>
				<c:forEach var="function" items="${module.functionList}">
					<tr class="hover" data-tt-id="${module.mod_id}.${function.fun_id}" data-tt-parent-id="${module.mod_id}">
						<td>${module.mod_order}-${function.fun_order }</td>
						<td>${function.fun_name}</td>
						<td class="userRange">${function.userRange}</td>
						<td>${function.fun_note}</td>
						<td class="goLook"><a title="查看机构" target="navTab" href="${ctx}/manage/function/goLook?id=${function.fun_id}" rel="lookOrg"><span>${function.useingNumber}</span></a></td>
						<td>${function.fun_version}</td>
						<td>${function.fun_status}</td>
						<td>
							<div style="margin-left:10%">
								<a title="编辑功能" target="dialog" width="630" height="760" href="${ctx}/manage/function/goEdit?type=f&id=${function.fun_id}" class="btnEdit" rel="editFunction">编辑</a>
								<%-- <a title="删除功能" target="ajaxTodo" href="${ctx}/manage/function/delete?type=m&id=${module.mod_id}" class="btnDel">删除</a> --%>
								<a title="查看机构" target="dialog" width="1024" height="780" href="${ctx}/manage/function/goLook?id=${function.fun_id}" class="btnView goLookBtn" rel="lookOrg"><span>查看机构</span></a>
								<%-- <a title="替换功能" target="ajaxTodo" href="${ctx}/manage/function/exchange?id=${module.mod_id}" rel="exchangeFunction" class="btnInfo"><span>替换</span></a> --%>
							</div>
						</td>
					</tr>	
				</c:forEach>
			</c:forEach>	
		</tbody>
	</table>
	<div class="panelBar">
		<div class="pages">
			<span>显示</span>
			<select class="combox" name="numPerPage" onchange="navTabPageBreak({numPerPage:this.value})">
				<option value="25" <c:if test="${modules.limit==25}">selected="selected"</c:if>>25</option>
				<option value="50" <c:if test="${modules.limit==50}">selected="selected"</c:if>>50</option>
				<option value="100" <c:if test="${modules.limit==100}">selected="selected"</c:if>>100</option>
				<option value="200" <c:if test="${modules.limit==200}">selected="selected"</c:if>>200</option>
			</select>
			<span>条，共${modules.total}条</span>
		</div>

		<div class="pagination" targetType="navTab" totalCount="${modules.total}" numPerPage="${modules.limit}" pageNum="${modules.page}" pageNumShown="5" currentPage="${modules.page}"></div>

	</div>
</div>


<script type="text/javascript">
$(".goLook").hover(function(){
	$(this)[0].style.backgroundColor ="#d0d0d0";
},function(){
	$(this)[0].style.backgroundColor ="";
});
$(".hover").hover(function(){
	$(this)[0].style.backgroundColor ="#ededed";
},function(){
	$(this)[0].style.backgroundColor ="";
});

$(".userRange").each(function(i){
	var a = $(this).html();
	var b = a.replace("0","学校").replace("2","培训机构").replace("3","教育局");
	$(this).html(b);
});
</script>
