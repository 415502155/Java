﻿﻿<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<%
	String path = request.getContextPath();
%>
<c:set var="path" value="<%=path%>"></c:set>

<script type="text/javascript" src="<%=path%>/js/cityselect/jquery.cityselect.js"></script>
<script type="text/javascript" src="<%=path%>/js/jquery.form.js"></script>

<style>
     /*公共样式*/
     a:hover{text-decoration:none;color:#fff}
	.fl{float:left}
	.fr{float:right}
	.clearfix{zoom:1}
	.clearfix:after{
		content:'';
		clear:both;
		display:block
	}
	/*公共样式end*/
	.content{
		overflow-y: scroll;
    	height: 800px
	}
	.header{
	    margin: 20px 50px;
	    line-height: 20px;
	    display:block
	}
	.school-name{font-size:20px}
	.add-button{
		border:0;
		background-color: #999;
	    padding: 12px;
	    color: #fff;
	    border-radius: 5px;
	    cursor: pointer
    }
    .data-table{
    	width:880px;
    	margin:20px 50px;
    	border-top:1px solid #ddd;
    	border-left:1px solid #ddd;
    }
	.data-table tbody td {
	    height: 30px;
	    border-bottom: 1px solid #ddd;
	    border-right: 1px solid #ddd;
	}
	.data-table thead{
		background: #f0eff0 url(./images/grid/tableth.png) repeat-x;
    }
	.data-table thead th {
	    height: 30px;
	    border-bottom: 1px solid #ddd;
	    border-right: 1px solid #ddd;
	}
	
	.del-machine{
		color:red;
		cursor:pointer
	}
</style>

<div class="content" >
	<div class="header clearfix">
		<h2 class="school-name fl">${orgEntity.org_name_cn}</h2>
		<a title="添加" target="dialog" href="<%=path%>/manage/attnMachineManage/openAddAttnMachineDialog?orgId=${orgEntity.org_id}" class="add-button fr" rel="addAttnMachineDialog" width="800" height="480" mask="true">添加考勤机</a>
	</div>
	<table class="data-table">
		<thead>
			<tr>
				<th>序号</th>
				<th>考勤机ID</th>
				<th>考勤机类型</th>
				<th>添加时间</th>
				<th>存放地点</th>
				<th>备注</th>
				<th>操作</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach var="m" items="${attnMachineList}" varStatus="i">
				<tr>
					<td class="list-num">${i.count}</td>
					<td class="machine-id" data-id="${m.id}">${m.serial_number}</td>
					<td class="machine-type">
						<c:if test="${m.type == 0}">近程门禁</c:if>
						<c:if test="${m.type == 1}">近程门禁(带照片)</c:if>
						<c:if test="${m.type == 2}">远程门禁</c:if>
						<c:if test="${m.type == 3}">近程门禁(带照片)</c:if>
					</td>
					<td class="insert-time"><fmt:formatDate value="${m.insert_time}" pattern="yyyy-M-d HH:mm" timeZone="GMT+8"/> </td>
					<td class="input-place">${m.place}</td>
					<td class="remarks">${m.remarks}</td>
					<td class="del-machine" data-id="${m.org_id}">删除</td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
	<button type="button" class="close closes" style="visibility:hidden">取消</button>
</div>
<script>
	$(function(){
		$("tbody tr:even").css("background-color","#fff");
		$("tbody tr:odd").css("background-color","#eee");
		$(".del-machine").on("click",function (){
			var $this = $(this);
			alertMsg.confirm("是否确认删除考勤机，如学校无考勤机则仅可使用软考勤打卡。", {
				okCall:	function(){
					$.post("<%=path%>/manage/attnMachineManage/deleteAttnMachine",{
						orgId:$this.attr("data-id"),
						recordId: +$this.siblings(".machine-id").attr('data-id'),
					},function (res){
						if(res.code == 200 && res.success == true){
							$this.parent().remove();
							$("tbody tr:even").css("background-color","#fff");
							$("tbody tr:odd").css("background-color","#eee");
						}else{
							alert(res.message);
						}
					});
				}
			});
		})
	})
	
</script>
