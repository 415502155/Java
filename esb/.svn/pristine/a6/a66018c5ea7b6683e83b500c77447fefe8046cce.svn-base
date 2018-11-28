<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<script type="text/javascript">    
 //遍历被选中CheckBox元素的集合 得到Value值 
$(document).ready(function() {
// put all your jQuery goodness in here.
	$('.treetable .syncorg').click(function(){
		var $this = $(this);
		
		var url = "../user/syncschoolorg?&sid="+$this.attr("rel");
		var param = {};
		  $.pdialog.open(url,"syncschoolsub","对应用户中心",{
			  height:600, 
			  width:780,
		      max:false,
		      mask:true,
		      mixable:false,
		      minable:false,
		      resizable:false,
		      drawable:true,
		      fresh:true,
		      close:"function",
		      param:param
		  });
	});
}); 
 
</script>
<form id="pagerForm" method="post" action="demo_page1.html">
	<input type="hidden" name="status" value="${param.status}">
	<input type="hidden" name="keywords" value="${param.keywords}" />
	<input type="hidden" name="pageNum" value="1" />
	<input type="hidden" name="numPerPage" value="${model.numPerPage}" />
	<input type="hidden" name="orderField" value="${param.orderField}" />
</form>


<div class="pageHeader">
	<h2>板块分类</h2>
</div>
<div class="pageContent">
	<div class="panelBar">
		<ul class="toolBar">			
			<li><a class="add" href="demo_page4.html" target="navTab"><span>添加</span></a></li>
			<li><a class="edit" href="demo_page4.html?uid={sid_user}" target="navTab"><span>修改</span></a></li>		
			<li><a title="确实要删除这些记录吗?" target="selectedTodo" rel="ids" href="demo/common/ajaxDone.html" class="delete"><span>批量删除</span></a></li>	
		</ul>
	</div>
	<table class="table" width="100%" layoutH="138">
		<thead>
			<tr>
				<th width="80">选择</th>
				<th width="120">序号</th>
				<th width="120">icon图标</th>
				<th width="100">板块名称</th>
				<th width="150">添加时间</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach var="articleClass" items="${ls}">
				<tr target="sid_user" rel="${articleClass.acid}">
					<td><input type="checkbox" /></td>
					<td>${articleClass.acid}</td>
					<td style="height: 90px;line-height: 90px;"><img alt="${articleClass.title}" src="${articleClass.iconUrl}"/></td>
					<td>${articleClass.title}</td>
					<td>${articleClass.inserttime}</td>
				</tr>
			</c:forEach>			
		</tbody>
	</table>
	<div class="panelBar">
		<div class="pages">
			<span>显示</span>
			<select class="combox" name="numPerPage" onchange="navTabPageBreak({numPerPage:this.value})">
				<option value="20">20</option>
				<option value="50">50</option>
				<option value="100">100</option>
				<option value="200">200</option>
			</select>
			<span>条，共${totalCount}条</span>
		</div>

		<div class="pagination" targetType="navTab" totalCount="200" numPerPage="20" pageNumShown="10" currentPage="1"></div>

	</div>
</div>

