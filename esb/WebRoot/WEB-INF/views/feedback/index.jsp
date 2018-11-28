<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<%
	String ctx = request.getContextPath();
%>
<style>
	.grid .gridTbody td div {
	    display: block;
	    line-height: 24px;
	    text-align: left;
	    padding:3px
	}
</style>
<form id="pagerForm" method="post" action="${ctx}/manage/feedback/index">
	<input type="hidden" name="pageNum" value="${data.page}" /> <input
		type="hidden" name="numPerPage" value="${data.limit}" /> <input
		type="hidden" name="totalPage" value="${data.total}" />
</form>
<div class="pageHeader">
	<form onsubmit="return navTabSearch(this);"
		action="${ctx}/manage/feedback/index" method="post">
		<div class="searchBar">
			<table class="searchContent">
				<tr>
					<td>机构：</td>
					<td><select class="combox" name="org_id">
							<option value=""
								<c:if test="${''==param.org_id}" > selected="selected" </c:if>>所有机构</option>
							<c:forEach items="${orgs}" var="org">
								<option value="${org.org_id}"
									<c:if test="${org.org_id==param.org_id}" > selected="selected" </c:if>>${org.org_name_cn}</option>
							</c:forEach>
					</select></td>
					<td><div class="buttonActive">
							<div class="buttonContent">
								<button type="submit" id="search">检索</button>
							</div>
						</div></td>
				</tr>
			</table>
		</div>
	</form>
</div>
<div class="pageContent">
	<table class="table" width="100%" layoutH="134">
		<thead>
			<tr>
				<th width="30" align="center">序号</th>
				<th width="80" align="center">反馈人</th>
				<th width="150" align="center">所在学校</th>
				<th width="80" align="center">角色</th>
				<th width="100" align="center">反馈人填写手机号码</th>
				<th width="100" align="center">系统手机号码</th>
				<th width="600" align="center">反馈意见</th>
				<th width="100" align="center">反馈时间</th>
			</tr>
		</thead>
		<tbody>

			<c:forEach var="f" items="${data.data}" varStatus="status">
				<tr target="sid_user">
					<td>${status.count+data.limit*(data.page-1)}</td>
					<td>${f.name}</td>
					<td>${f.org_name}</td>
					<td><c:choose>
							<c:when test="${f.identity == 0}">家长</c:when>
							<c:when test="${f.identity == 1}">教师</c:when>
							<c:otherwise>未知</c:otherwise>
						</c:choose></td>
					<td><c:choose>
							<c:when test="${f.mobile == '' || null==f.mobile}">未填写</c:when>
							<c:otherwise>${f.mobile}</c:otherwise>
						</c:choose></td>
					<td>${f.login_name}</td>
					<td>${f.content}</td>
					<td><fmt:formatDate value="${f.insert_time}" type="date"
							dateStyle="long" /></td>
				</tr>
			</c:forEach>

		</tbody>
	</table>

	<div class="panelBar">
		<div class="pages">
			<span>显示</span> <select class="combox" name="numPerPage"
				onchange="navTabPageBreak({numPerPage:this.value})">
				<option value="25"
					<c:if test="${data.limit==25}">selected="selected"</c:if>>25</option>
				<option value="50"
					<c:if test="${data.limit==50}">selected="selected"</c:if>>50</option>
				<option value="100"
					<c:if test="${data.limit==100}">selected="selected"</c:if>>100</option>
				<option value="200"
					<c:if test="${data.limit==200}">selected="selected"</c:if>>200</option>
			</select> <span>条，共${data.total}条</span>
		</div>

		<div class="pagination" targetType="navTab" totalCount="${data.total}"
			numPerPage="${data.limit}" pageNum="${data.page}" pageNumShown="5"
			currentPage="${data.page}"></div>

	</div>
</div>
