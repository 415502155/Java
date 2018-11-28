﻿<%@ page language="java" import="java.util.*,cn.edugate.esb.util.FileProperties" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<%
	String path = request.getContextPath();
//String basePath = "https://"+request.getServerName()+"/esb/";
String basePath = FileProperties.getProperty("httpESBWebUrl");
%>
<c:set var="path" value="<%=path%>"></c:set>


<style>
.searchContent span.info {
    color: #7f7f7f;
    display: block;
    float: left;
    line-height: 21px;
}
td,th{
	text-align: center
}
</style>

<form id="pagerForm" method="post" action="${ctx}/manage/organization/list">
	<input type="hidden" name="pageNum" value="1" />
	<input type="hidden" name="numPerPage" value="${orgList.limit}" />
	<%--<input type="hidden" name="orgName" value="${roles.total}" />--%>
</form>



<div class="pageHeader">
	<form onsubmit="return navTabSearch(this);"  rel="pagerForm" method="post" action="${ctx}/manage/organization/list">
		<div class="searchBar">
			<table class="searchContent">
				<tr>
					<td>
						机构管理：<input type="text" name="orgName" value="${org.org_name_cn}" placeholder="请输入机构名称"/>
					</td>
					<td><div class="buttonActive"><div class="buttonContent"><button type="submit">查找</button></div></div></td>
					<td style="width: 580px;">
					</td>
				</tr>
			</table>
		</div>
	</form>
</div>
<div class="pageContent">
	<div class="panelBar">
		<ul class="toolBar">			
			<li><a class="add" href="${ctx}/manage/organization/goToAddOrgPage" target="dialog" rel="addOrgDialog" width="1000" height="900" mask="true"><span>添加机构</span></a></li>	
			<li><a class="add" href="${ctx}/no/goImportOrg" target="dialog" rel="importOrgDialog" width="600" height="200" mask="true"><span>导入机构</span></a></li>
			<%-- <li><a class="add" href="${ctx}/manage/organization/deleteOrgData" ><span>删除机构数据</span></a></li>
			<li><a class="add" href="${ctx}/manage/organization/initGradeType4Schools" ><span>初始化学校中的新增年级类型</span></a></li>
			<li><a class="add" href="${ctx}/manage/organization/testClassUpgrade" ><span>测试班级升年级功能</span></a></li> --%>
		</ul>
	</div>
	<table class="table" width="100%" layoutH="138">
		<thead>
			<tr>
				<th width="50">序号</th>
				<th width="150">机构名称</th>
				<th width="100">机构简称</th>
				<th width="100">区域</th>
				<th width="50">类型</th>
				<th width="100">下级机构</th>
				<th width="150">备注</th>
				<th width="100">操作</th>
			</tr>
		</thead>
		<tbody>		
			<c:forEach var="org" items="${orgList.data}" varStatus="i">
				<tr target="sid_user" rel="1">
					<td>${i.count}</td>
					<td>${org.org_name_cn}</td>
					<td>${org.org_name_s_cn}</td>
					<td>${org.area4Display}</td>
					<td>${org.type4Display}</td>
					<td><a title="设置下级机构" target="dialog" href="${ctx}/manage/organization/goToSetLowerOrgPage?orgID=${org.org_id}"  rel="setLowerOrgDialog" width="960" height="650" mask="true">${org.childOrgNum}</a></td>
					<td>${org.remark}</td>
					<td>
						<a title="编辑" target="dialog" href="${ctx}/manage/organization/goToEditOrgPage?orgID=${org.org_id}" class="btnEdit" rel="editOrgDialog" width="1000" height="900" mask="true">编辑</a>
						<a title="进入" class="btnView goLookBtn" onclick="goOrg(${org.org_id})"><span>查看机构</span></a>
						<a title="进入微信后台" class="btnView goLookBtn" onclick="goWX(${org.org_id})"><span>进入微信后台</span></a>
						<%-- <a title="删除机构" class="btnDel" href="${ctx}/manage/organization/delete?id=${org.org_id}" >删除</a> --%>
					</td>
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

<script>
var counter = 0;
function goOrg(id){
	$.ajax({
		type:'POST',
		url: "<%=basePath%>/manage/user/goOrg",
		data: {org_id:id},
    	dataType: 'json',
		success:function(result){
			if(result.code==200){
				counter=0;
				window.open(result.url);
			}else{
				if(counter<2){
					counter++;
					goOrg(id);
				}
			}
		}
	});	
};

function goWX(id){
	$.ajax({
		type:'POST',
		url: "<%=basePath%>/manage/user/goWX",
		data: {org_id:id},
    	dataType: 'json',
		success:function(result){
			if(result.code==200){
				counter=0;
				window.open(result.url);
			}else{
				if(counter<2){
					counter++;
					goWX(id);
				}
			}
		}
	});	
};
</script>
