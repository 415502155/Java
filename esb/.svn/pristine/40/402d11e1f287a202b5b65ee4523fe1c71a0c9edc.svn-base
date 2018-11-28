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
	<form onsubmit="return navTabSearch(this);" action="${ctx}/manage/roleCode/list" method="post">
	<div class="searchBar">
		<table class="searchContent">
			<tr>
				<td>编码：<input type="text" name="code" value="" /></td>
				<td>名称：<input type="text" name="name" value="" /></td>
				<td><div class="buttonActive"><div class="buttonContent"><button type="submit">检索</button></div></div></td>
			</tr>
		</table>
	</div>
	</form>
</div>
<div class="pageContent j-resizeGrid">
	<div class="panelBar">
		<ul class="toolBar">			
			<li><a class="add" href="${ctx}/manage/roleCode/goAdd" width="630" height="760" target="dialog" rel="addFunction"><span>添加</span></a></li>
		</ul>
	</div>
	<table class="treetable" width="100%" layoutH="134">
		<thead>
			<tr>
				<th width="50" align="center">序号</th>
				<th width="100" align="center">名称</th>
				<th width="100" align="center">编码</th>
				<th width="100" align="center">权限名称</th>
				<th width="100" align="center">操作</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach var="roleCode" items="${list}" varStatus="i">
				<tr class="hover" target="sid_module" rel="${roleCode.role_code_id}" data-tt-id="${roleCode.role_code_id}">
					<td>${i.index}</td>
					<td><b style="color:green">${roleCode.name}</b></td>
					<td>${roleCode.code}</td>
					<td>管理</td>
					<td>
						<div>
							<a title="添加功能" target="dialog" width="630" height="760" href="${ctx}/manage/function/goAdd?id=${module.mod_id}" target="dialog" rel="addFunction" class="btnAdd"><span>添加</span></a>
							<a title="编辑模块" target="dialog" width="600" height="350" href="${ctx}/manage/function/goEdit?type=m&id=${module.mod_id}" class="btnEdit" rel="editmodule">编辑</a>
						</div>
					</td>
				</tr>
				<c:forEach var="s" items="${roleCode.list}" varStatus="i">
					<tr class="hover" data-tt-id="${roleCode.role_code_id}.${s.role_code_id}" data-tt-parent-id="${roleCode.role_code_id}">
						<td>${i.index }</td>
						<td>${s.name}</td>
						<td>${s.code}</td>
						<td>${s.value}</td>
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
