<%@ page pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<script type="text/javascript">    
 //遍历被选中CheckBox元素的集合 得到Value值 
$(document).ready(function() {
// put all your jQuery goodness in here.
	$('#searchbtn').click(function(){
		var keyword = $("input[name='orgName']").val();
		var sid = $("input[name='sid']").val();
		var map = { keyword: keyword, sid: sid };
		console.log("map=======================",map);
		$('#searchform').submit(map);
		
	});
}); 
function dialogAjax(json){
	var dialog = $.pdialog.getCurrent();
	$.pdialog.reload(dialog.data("url"));
}
</script>
<div class="pageContent">	
	<form id="searchform" onsubmit="return dialogSearch(this);" action="../user/searchorg" method="post">	

		<div class="pageFormContent" layoutH="470">
			<p>
				<label>139学校：</label>
				${school.schoolname}
			</p>
			<p>
				<label>用户中心对应学校：</label>
				<input name="keyword" class="required" type="text" size="30" value=""/>
				<div class="buttonActive"><div class="buttonContent"><button type="submit">检索</button></div></div>
			<p>
		</div>
		<div>
			<div>
				<input type="hidden" name="sid" value="${school.sid}"/>	
				<c:if test="${school.blanklist!=null}">
					<c:forEach var="bl" items="${school.blanklist}">
						<span style="margin-left: 10px; display: inline-block;">
							<font style="color:green">${bl[0]}</font>								
							<a class="delete" href="../user/deleteBlankList?sid=${school.sid}&orgid=${bl[1]}" callback="dialogAjax" target="ajaxTodo" style="color:red"><span>删除</span></a>
						</span>	
					</c:forEach>
				</c:if>		
			</div>
		<table class="table" width="100%"  border="1" align="center"
	cellspacing="0" bordercolor="#999999" style="border-collapse:collapse" layoutH="270">
		        <thead>
		            <tr>
		                <th width="30%">组织名称</th>
		                <th>操作</th>
		            </tr>
		        </thead>
		        <tbody>
					<c:forEach var="user" items="${orglist}">
						<tr target="sid_user" rel="${user[0]}">
							<td>${user[0]}</td>									
							<td><a class="edit" href="../user/addBlankList?sid=${school.sid}&orgid=${user[1]}" callback="dialogAjax" target="ajaxTodo"><span>添加</span></a></td>
						</tr>	
					</c:forEach>		
				</tbody>
		    </table>
		</div>
	</form>
	
</div>
