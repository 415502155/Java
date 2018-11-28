<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<form id="pagerForm" method="post" action="${ctx}/manage/range/setadmin">
	<input type="hidden" name="pageNum" value="1" />
	<input type="hidden" name="numPerPage" value="${roles.limit}" />
	<input type="hidden" name="totalPage" value="${roles.total}" />
</form>

<div class="pageHeader">
	<form onsubmit="return navTabSearch(this);" action="${ctx}/manage/range/setadmin" method="post">
	<div class="searchBar">
		<table class="searchContent">
			<tr>
				<td>
					教师姓名：<input type="text" name="searchName" value="" />
				</td>
				<td>
					<select class="combox" name="org_id">
						<option value="0">未分配机构用户</option>
						<c:forEach items="${orgs}" var="org">									
							<option value="${org.org_id}" <c:if test="${org.org_id==param.org_id}" >selected="selected"</c:if>>${org.org_name_cn}</option>									
						</c:forEach>
					</select>
				</td>
				<td><div class="buttonActive"><div class="buttonContent"><button type="submit">检索</button></div></div></td>
			</tr>
		</table>
	</div>
	</form>
</div>
<div class="pageContent">
	<div>	
		<div layoutH="146" style="float:left; display:block; overflow:auto; width:240px; border:solid 1px #CCC; line-height:21px; background:#fff">
		    <ul class="tree treeFolder">		    
		    <c:forEach var="dep" items="${deps}" varStatus="status">
				<li><a href="javascript">${dep.dep_name}</a>
					<c:if test="${fn:length(dep.teachers)>0}">
					<ul>
						<c:forEach var="teacher" items="${dep.teachers}" varStatus="status">
							<li><a href="${ctx}/manage/range/getAdminOrgs?tech_id=${teacher.tech_id}" target="ajax" rel="jbsxBox">${teacher.tech_name}</a></li>
						</c:forEach>						
					</ul>
					</c:if>
				</li>
			</c:forEach>
		    </ul>
		</div>
		
		<div id="jbsxBox" class="unitBox" style="margin-left:246px;">
			<!--#include virtual="list1.html" -->
		</div>

	</div>
</div>

