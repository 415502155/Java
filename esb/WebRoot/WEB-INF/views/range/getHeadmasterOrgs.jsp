<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<form id="pagerForm" onsubmit="return divSearch(this, 'jbsxBox');" action="${ctx}/manage/range/setAdminOrgs" method="post">
<table class="table" width="99%" layoutH="260" rel="jbsxBox">
<div class="buttonActive"><div class="buttonContent"><button type="submit">保存</button></div>
	<thead>
		<tr>
			<th width="80"></th>
			<th width="80">序号</th>
			<th width="120">中文简称</th>
			<th >机构中文名称</th>
			<th width="100">类型</th>				
		</tr>
	</thead>
	<tbody>
	<c:forEach var="org" items="${orgs}" varStatus="status">
		<tr target="sid_obj" rel="${org.org_id}">
			<td><input type="checkbox" name="orgids" value="${org.org_id}" <c:if test="${org.checked}" >checked="checked"</c:if> /></td>
			<td>${org.org_id}</td>
			<td>${org.org_name_s_cn}</td>
			<td>${org.org_name_cn}</td>
			<td>${org.type}</td>
		</tr>
	</c:forEach>
	
	</tbody>
	<input name="tech_id" value="${tech_id}" type="hidden" />
</table>
</form>

