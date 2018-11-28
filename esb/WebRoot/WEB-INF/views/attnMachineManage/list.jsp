﻿<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<%
	String path = request.getContextPath();
%>
<c:set var="path" value="<%=path%>"></c:set>

<script type="text/javascript" src="<%=path%>/js/jquery.form.js"></script>
<script type="text/javascript" src="<%=path%>/js/cityselect/jquery.cityselect.js"></script>

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
.msg-table{
	padding:30px
}
.msg-table td,.msg-table th{
	border: 1px solid #ccc;
    padding: 10px 30px
}
</style>

<form id="pagerForm" method="post" action="<%=path%>/manage/attnMachineManage/list">
	<input type="hidden" id="pageNum" name="pageNum" value="1" />
	<input type="hidden" id="numPerPage" name="numPerPage" value="${orgList.limit}" />
</form>



<div class="pageHeader">
	<form name="searchForm" id="searchForm" rel="pagerForm" method="post" action="<%=path%>/manage/attnMachineManage/list" onsubmit="return navTabSearch(this);">
		
		<div class="searchBar">
			<table class="searchContent">
				<tr >
					<!-- <td style="width: 770px;text-align: left">
						<dl class="nowrap">
			                <dt style="display:inline-block">所在区域：</dt>
			                <dd style="display:inline-block" id="area_select">
			                    <select class="prov" style="width:207px" onchange="prov_change();"></select>
			                    <select class="city" disabled="disabled" style="width:207px" onchange="city_change();"></select>
			                    <select class="dist" disabled="disabled" style="width:207px" onchange="dist_change();"></select>
			                    <select name="prov" id="prov" class="prov" style="width:207px"></select>
			                    <select name="city" id="city" class="city" disabled="disabled" style="width:207px"></select>
			                    <select name="dist" id="dist" class="dist" disabled="disabled" style="width:207px"></select>
			                </dd>
			            </dl>
		            </td> -->
					<td>
						机构名称：<input type="text" name="orgName" id="orgName" value="${orgName}" placeholder="请输入机构名称"/>
					</td>
					<td><div class="buttonActive"><div class="buttonContent"><button type="submit" id="classSubmit">查找</button></div></div></td>
					<td style="width: 580px;">
					</td>
				</tr>
			</table>
		</div>
	</form>
</div>
<div class="pageContent">
	<table class="table" width="100%" layoutH="138">
		<thead>
			<tr>
				<!-- <th width="50"><input type="checkbox" class="checkboxCtrl" group="org_checkbox" />全选</th> -->
				<!-- <th width="50">序号</th> -->
				<th width="100">机构名称</th>
				<th width="100">机构简称</th>
				<th width="100">类型</th>
				<th width="100">备注</th>
				<th width="50">操作</th>
			</tr>
		</thead>
		<tbody data-id="cont-tbody">		
			<c:forEach var="org" items="${orgList.data}" varStatus="i">
				<tr target="sid_user" rel="1">
					<%-- <td data-id="org-id"><input type="checkbox"  name="org_checkbox" value="${org.org_id}"></td> --%>
					<%-- <td>${i.count}</td> --%>
					<td>${org.org_name_cn}</td>
					<td>${org.org_name_s_cn}</td>
					<td>${org.type}</td>
					<td>${org.remark}</td>
					<td><a onclick="saveId(this)" attn-id="${org.org_id}" title="编辑" target="dialog" href="<%=path%>/manage/attnMachineManage/attnMachineList?orgId=${org.org_id}" class="btnEdit" rel="attnMachineListDialog" width="1000" height="900" mask="true">编辑</a></td>
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
				<%-- <option value="200" <c:if test="${orgList.limit==200}">selected="selected"</c:if>>200</option> --%>
			</select>
			<span>条，共${orgList.total}条</span>
		</div>
		<div class="pagination" targetType="navTab" totalCount="${orgList.total}" numPerPage="${orgList.limit}" pageNum="${orgList.page}" pageNumShown="5" currentPage="${orgList.page}"></div>
	</div>
</div>

<script type='text/javascript'>
	
	//存储点击的机构
	function saveId(ele){
		sessionStorage.attn_id = $(ele).attr("attn-id");
	}
	
		
	

	
</script>
