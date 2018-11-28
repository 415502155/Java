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
	<input type="hidden" name="currentPage" value="${param.currentPage}" />
	<input type="hidden" name="numPerPage" value="${apps.limit}" />
	<input type="hidden" name="pageNum" value="${apps.page}" />
</form>


<!-- <div class="pageHeader">
	<form onsubmit="return navTabSearch(this);" action="../notice/list" method="post">
	<div class="searchBar">
		<ul class="searchContent">
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
	</div> 
	</form>
</div> -->
<div class="pageContent">
	<div class="panelBar">
		<ul class="toolBar">			
			<li><a  width="700" height="300"  class="add" href="../app/add" target="dialog" rel="addapp"><span>添加</span></a></li>	
		</ul>
	</div>
	
	<table class="table" width="100%" layoutH="77">
		<thead>
			<tr>
				<th width="30" onclick="swapCheck()">全择</th>
				<th width="150">name</th>
				<th>key</th>
				<th width="150">操作</th>
			</tr>
		</thead>
		<tbody>		
			<c:forEach var="app" items="${apps.data}">
				<tr target="sid_user" rel="1">
					<td><input type="checkbox" /></td>
					<td>${app.name}</td>
					<td>${app.key}</td>
					<td><a title="删除" width="700" height="300" target="ajaxTodo" href="../app/delete?appId=${app.a_id}" class="btnDel">删除</a>
						<a title="编辑" width="700" height="300" target="dialog" href="../app/edit?appId=${app.a_id}" class="btnEdit" rel="editmodule">编辑</a></td>
				</tr>
			</c:forEach>				
		</tbody>
	</table>

	<div class="panelBar">
		<div class="pages">
			<span>显示</span>
			<select class="combox" name="numPerPage" onchange="navTabPageBreak({numPerPage:this.value})">
				<option value="25" <c:if test="${apps.limit==25}">selected="selected"</c:if>>25</option>
				<option value="50" <c:if test="${apps.limit==50}">selected="selected"</c:if>>50</option>
				<option value="100" <c:if test="${apps.limit==100}">selected="selected"</c:if>>100</option>
				<option value="200" <c:if test="${apps.limit==200}">selected="selected"</c:if>>200</option>
			</select>
			<span>条，共${apps.total}条</span>
		</div>

		<div class="pagination" targetType="navTab" totalCount="${apps.total}" numPerPage="${apps.limit}" pageNum="${apps.page}" pageNumShown="5" currentPage="${apps.page}"></div>

	</div>
</div>
<script type="text/javascript">  
        //checkbox 全选/取消全选  
        var isCheckAll = false;  
        function swapCheck() {  
            if (isCheckAll) {  
                $("input[type='checkbox']").each(function() {  
                    this.checked = false;  
                });  
                isCheckAll = false;  
            } else {  
                $("input[type='checkbox']").each(function() {  
                    this.checked = true;  
                });  
                isCheckAll = true;  
            }  
        }  
</script>  

