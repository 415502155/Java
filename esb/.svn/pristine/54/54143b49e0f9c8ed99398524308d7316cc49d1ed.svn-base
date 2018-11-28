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

<form id="pagerForm" method="post" action="${ctx}/manage/classUpgrade/list">
	<input type="hidden" id="pageNum" name="pageNum" value="1" />
	<input type="hidden" id="numPerPage" name="numPerPage" value="${orgList.limit}" />
	<input type="hidden" name="prov4Paging" value="${prov4Paging}" />
	<input type="hidden" name="city4Paging" value="${city4Paging}" />
	<input type="hidden" name="dist4Paging" value="${dist4Paging}" />
</form>



<div class="pageHeader">
	<form name="searchForm" id="searchForm" rel="pagerForm" method="post" action="${ctx}/manage/classUpgrade/list" onsubmit="return navTabSearch(this);">
		
		<div class="searchBar">
			<table class="searchContent">
				<tr >
					<td style="width: 770px;text-align: left">
						<dl class="nowrap">
			                <dt style="display:inline-block">所在区域：</dt>
			                <dd style="display:inline-block" id="area_select">
			                    <!-- <select class="prov" style="width:207px" onchange="prov_change();"></select>
			                    <select class="city" disabled="disabled" style="width:207px" onchange="city_change();"></select>
			                    <select class="dist" disabled="disabled" style="width:207px" onchange="dist_change();"></select> -->
			                    <select name="prov" id="prov" class="prov" style="width:207px"></select>
			                    <select name="city" id="city" class="city" disabled="disabled" style="width:207px"></select>
			                    <select name="dist" id="dist" class="dist" disabled="disabled" style="width:207px"></select>
			                </dd>
			            </dl>
		            </td>
		            <td>
		            	升级状态：
		            	<select id="upgradeStatus" name="upgradeStatus">
		            		<option value="">全部</option>
		            		<option value="0">未升级</option>
		            		<option value="1">已升级</option>
		            	</select>
		            </td>
					<td>
						学校名称：<input type="text" name="orgName" id="orgName" value="${orgName}" placeholder="请输入机构名称"/>
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
	<div class="panelBar">
		<ul class="toolBar">			
			<li><a class="add" onclick="batchUpgrade()" href="javascript:;" ><span>批量升级</span></a></li>	
			<li><a class="add" href="${ctx}/manage/classUpgrade/exportSchoolList" target="dwzExport" targetType="navTab"  title="确定要导出搜索条件下的所有记录吗?"><span>导出学校列表</span></a></li>
			<li><a class="add" href="${ctx}/manage/classUpgrade/openImportExcelDialog" target="dialog" width="520" height="250" mask="true"><span>导入升级表</span></a></li>
			<li><a class="add" href="${ctx}/manage/classUpgrade/openClassesDegradeDialog" target="dialog" width="520" height="250" mask="true"><span>班级调级</span></a></li>
		</ul>
	</div>
	<table class="table" width="100%" layoutH="138">
		<thead>
			<tr>
				<th width="50"><input type="checkbox" class="checkboxCtrl" group="org_checkbox" />全选</th>
				<!-- <th width="50">序号</th> -->
				<th width="100">所在城市</th>
				<th width="100">所在区县</th>
				<th width="100">学校ID</th>
				<th width="100">学校名称</th>
				<th width="100">年级类型</th>
				<th width="100">升级状态</th>
				<th width="100">升级时间</th>
				<!-- <th width="50">操作</th> -->
			</tr>
		</thead>
		<tbody data-id="cont-tbody">		
			<c:forEach var="org" items="${orgList.data}" varStatus="i">
				<tr target="sid_user" rel="1">
					<td data-id="org-id"><input type="checkbox"  name="org_checkbox" value="${org.org_id}"></td>
					<%-- <td>${i.count}</td> --%>
					<td>${org.city}</td>
					<td>${org.district}</td>
					<td>${org.org_id}</td>
					<td>${org.org_name_cn}</td>
					<td>${org.gradeType}</td>
					<td data-id="status">${org.upgradeStatus}</td>
					<td>${org.upgradeDate}</td>
					<!-- <td><c:if test="${org.upgradeStatus == '未升级'}" ><input type="button" value="升级" onclick=""></c:if></td> -->
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
	$(document).ready(function() {
		var prov = "${prov}";
		var city = "${city}";
		var dist = "${dist}";
		
		if (dist != null && dist != "" && dist != "null") {
			$("#area_select").citySelect({nodata:"none",required:false, prov:prov, city:city, dist:dist});
		} else if (city != null && city != "" && city != "null") {
			$("#area_select").citySelect({nodata:"none",required:false, prov:prov, city:city});
		} else if (prov != null && prov != "" && prov != "null") {
			$("#area_select").citySelect({nodata:"none",required:false, prov:prov});
		} else {
			$("#area_select").citySelect({nodata:"none",required:false});
		}
		
		var upgradeStatus = "${upgradeStatus}";
		if (upgradeStatus == "0") {
			$("#upgradeStatus").val("0");
		} else if (upgradeStatus == "1") {
			$("#upgradeStatus").val("1");
		} else {
			$("#upgradeStatus").val("");
		}
		
		//  初始化
		
		/* var areaStr = "${area}";
		
		console.log(areaStr);
		if (null == areaStr || "" == areaStr) {
			//  初始化
			$("#area_select").citySelect({nodata:"none",required:false});
			$("#area").val("");
		} else {
			$("#area").val(areaStr);
			var temp = areaStr.split("_");
			var prov = temp[0];
			var city = temp[1];
			var dist = temp[2];
			if ("" != dist && "null" != dist) {
				$("#area_select").citySelect({nodata:"none",required:false, prov:prov, city:city, dist:dist});
			} else if ("" != city && "null" != city) {
				$("#area_select").citySelect({nodata:"none",required:false, prov:prov, city:city});
			} else if ("" != prov && "null" != prov) {
				$("#area_select").citySelect({nodata:"none",required:false, prov:prov});
			}
		} */
		
	});
	//批量升级
	function batchUpgrade(){
		var port = "<%=path%>/manage/classUpgrade/batchUpgrade";
		var datas = '';
		var tbody = $("[data-id='cont-body']"),org = $("[data-id='org-id'] input"),status = $("[data-id='status']");
		var tempNum = 0;
		org.each(function (i){
			if($(this).prop("checked") == true){
				if($(this).parents("[data-id='org-id']").siblings("[data-id='status']").text()!="已升级"){
					tempNum += 1;
				}
				
			}
		})
		if(tempNum > 0){
			org.each(function (i){
				if($(this).prop("checked") == true){
					if($(this).parents("[data-id='org-id']").siblings("[data-id='status']").text()!="已升级"){
						datas += $(this).val() + ',';	
					}
				}
			})
			datas = datas.substring(0,datas.length-1);
			if(confirm("是否确定升级当前选择的所有学校？")){
				$.ajax({
					url:port,
					data:{
						orgIds : datas
					},
					method:"POST",
					success:function(res){
						//alert(JSON.stringify(res.data))
						var resHtml = '',tableHtml='',tbodyHtml='',errMsg=''
						for(var i in res.data){
							//errMsg += i.replace(/[0-9]{3}\_/,'') + ':';
							errMsg = '';
							for(var k=0;k<res.data[i].length;k++){
								errMsg += res.data[i][k] + '\n'
							}
							tbodyHtml += '<tr>'+
											'<td>'+i.split("_")[0]+'</td>'+
											'<td>'+i.split("_")[1]+'</td>'+
											'<td><pre style="padding:10px;line-height:20px">'+errMsg+'</pre></td>'+
										'</tr>'
							
						}
						console.log(errMsg);
						tableHtml += '<table class="table msg-table" >'+
										'<thead>'+
											'<tr>'+
												'<th>学校ID</th>'+
												'<th>学校名称</th>'+
												'<th>反馈信息</th>'+
											'</tr>'+
										'</thead>'+
										'<tbody>'+
											'<tr>'+tbodyHtml+'</tr>'+
										'</tbody>'+
									'</table>'
						resHtml += '<div style="overflow:scroll;height:100%;width:100%;position:absolute;z-index:20000;background-color:#fff"  id="errorUpdateMsg">'+
									//'<pre style="text-align:left;padding:30px;line-height:30px">' + errMsg + '</pre>'+
									tableHtml + 
									'<button style="margin: 30px;display: block;padding: 10px 30px;background: none;border: none;background-color: #3f80ff;color: #fff;border-radius: 5px;" type="button" id="closeErrMsg">返回</button>'
									'</div>'
									
						$('[action="/esb/manage/classUpgrade/list"]').parents(".page").css({//防止导出成功后页面偏移bug
							"top":0,
							"left":0,
							"z-index":10001
						});
						$('[action="/esb/manage/classUpgrade/list"]').parents(".page").find("#pagerForm").after(resHtml);
						$('#closeErrMsg').on('click',function (){
							$("#errorUpdateMsg").remove();
							//$(".pagination ul li.selected a").trigger("click");
							//$("#classSubmit").trigger("click");
						})
					},
					error:function (err){
						alert("发生错误");
					}
				})
			}
		}else{
			alert("请至少选择一个未升级过的机构!");
		}
		
	}
	
	
	// 点击搜索按钮触发
	function doSearch() {
		// 获取选择的地区
		var areaStr = $("#area_select > .prov").val() + "_" + $("#area_select > .city").val() + "_" + $("#area_select > .dist").val();
		//debugger;
		$("#area").val(areaStr);
		var statusStr = $("#upgradeStatus_select").val();
		$("#upgradeStatus").val(statusStr);
		var pageNum = $("#pageNum").val();
		var numPerPage = $("#numPerPage").val();
		
		$("#searchFrom").ajaxSubmit({
			url: "<%=path%>/manage/classUpgrade/list",
			type: "GET",
			//data : "area=" + areaStr + "&upgradeStatus="+statusStr + "&pageNum="+pageNum+"&numPerPage="+numPerPage,
			async: false,
			contentType : "application/x-www-form-urlencoded; charset=UTF-8",
			success: function(data) {
				console.log(data);

			},
			error: function(data) {
				myAlert("cztip","操作提示","网络异常！",null);
			}
		});
		//navTab.openTab("classUpgrade_list", $("#searchFrom").attr('action'), { title:"New Tab", fresh:true, data:JSON.stringify($("#searchFrom").serializeArray()) });
		//navTab.reload($("#searchFrom").attr('action'), $("#searchFrom").serializeArray());
		//return false;
	}
	
	
	
	// 地区选择框值变化时触发
	function prov_change() {
		var areaStr = $("#area_select > .prov").val() + "_" + $("#area_select > .city").val() + "_" + $("#area_select > .dist").val();
		$("#area").val(areaStr);
	}
	
	function city_change() {
		var areaStr = $("#area_select > .prov").val() + "_" + $("#area_select > .city").val() + "_" + $("#area_select > .dist").val();
		$("#area").val(areaStr);
	}
	
	function dist_change() {
		var areaStr = $("#area_select > .prov").val() + "_" + $("#area_select > .city").val() + "_" + $("#area_select > .dist").val();
		$("#area").val(areaStr);
	}
</script>
