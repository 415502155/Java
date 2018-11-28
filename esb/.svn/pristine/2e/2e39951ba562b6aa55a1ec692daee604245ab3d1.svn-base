<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<div class="pageContent">
	<form method="post" action="../user/adduserrole" class="pageForm required-validate" onsubmit="return validateCallback(this, dialogAjaxDone);">
		<input type="hidden" name="username" value="${username}"/>
		<div class="pageFormContent" layoutH="56">
			<table class="table" width="100%" layoutH="134">
				<thead>
					<tr>
						<th width="80">选择</th>
						<th width="120">序号</th>
						<th width="120">组名称</th>
						<th width="100">备注</th>						
					</tr>
				</thead>
				<tbody>
				
				<c:forEach var="role" items="${roles}">
					<tr target="sid_user" rel="${role.rid}">
						<td><input type="checkbox" name="rid" value="${role.rid}" /></td>
						<td>${role.rid}</td>
						<td>${role.rolename}</td>
						<td>${role.remark}</td>
					</tr>
				</c:forEach>
					
				</tbody>
			</table>
		</div>
		<div class="formBar">
			<ul>
				<!--<li><a class="buttonActive" href="javascript:;"><span>保存</span></a></li>-->
				<li><div class="buttonActive"><div class="buttonContent"><button type="submit">添加</button></div></div></li>
				<li>
					<div class="button"><div class="buttonContent"><button type="button" class="close">取消</button></div></div>
				</li>
			</ul>
		</div>
	</form>
</div>
