<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<%
	String path = request.getContextPath();
%>

<style>
.tbody {
    display:block;
    height:373px;
    overflow-y:auto;
    overflow-x:hidden;
}
.thead, .tbody tr {
    display:table;
    width:100%;
    table-layout:fixed;
}
.thead {
    width: calc( 100% - 1em )
}
</style>
<c:set var="path" value="<%=path%>"></c:set>
<div class="pageContent" rel="editMenuDialog">
	<form id="form" method="post" action="${ctx}/manage/menu/update" class="pageForm required-validate" onsubmit="return validateCallback(this, dialogAjaxDone);">
		<input name="menuIds" value="${menuIds }" type="hidden"/>
		<div class="pageFormContent">
			<div>
				<p face="verdana" style="font-size: 16px;font-weight: 900">菜单配置 - ${org.org_name_cn }</p>
				<div style="clear: both; padding: 5px;">
					<label>机构主键：</label>
					<input readonly="readonly" style="width:40%" type="text" size="30" value="${org.org_id }"/>
				</div>
				<div style="clear: both; padding: 5px;">
					<label>机构名称：</label>
					<input readonly="readonly" style="width:60%" type="text" size="30" value="${org.org_name_cn }"/>
				</div>
				<div style="clear: both; padding: 5px;">
					<label>机构简称：</label>
					<input readonly="readonly" style="width:60%" type="text" size="30" value="${org.org_name_s_cn }"/>
				</div>
				<div style="clear: both; padding: 5px;">
					<label>所在区域：</label>
					<input readonly="readonly" style="width:60%" type="text" size="30" value="${org.area }"/>
				</div>
				<div style="clear: both; padding: 5px;">
					<label>机构类型：</label>
					<input readonly="readonly" style="width:60%" type="text" size="30" value="${org.type4Display }"/>
				</div>
				<div style="clear: both; padding: 5px;">
					<label>备注：</label>
					<textarea readonly="readonly" style="width:60%">${org.remark }</textarea>
				</div>
				<div style="clear: both; padding: 5px;">
					<label>机构UI包：</label>
					<select style="width:40%"></select>
				</div>
			</div>
			<div class="pageContent" style="margin:30px 0 0;max-width:1000px !important;">
				<div class="panelBar">
					<ul class="toolBar">			
						<li><a class="add" href="${ctx}/manage/menu/goMenu?id=0&org_id=${org.org_id}" target="dialog" rel="addOrgDialog" width="800" height="755" mask="true" title="添加自定义菜单"><span>添加菜单</span></a></li>	
					</ul>
				</div>
				<table class="treetable collapsed">
					<thead class="thead">
						<tr>
							<th width="8%" align="center">序号</th>
							<th width="15%" align="center">菜单名称</th>
							<th width="15%" align="center">原始名称</th>
							<th width="18%" align="center">备注</th>
							<th width="9%" align="center">功能状态</th>
							<th width="9%" align="center">使用状态</th>
							<th width="9%" align="center">打开位置</th>
							<th width="11%" align="center">顺序号</th>
							<th width="7%" align="center">操作</th>
						</tr>
					</thead>
					<tbody class="tbody">
						<c:forEach var="pmenu" varStatus="i" items="${menus}">
							<tr class="hover" target="sid_pmenu" rel="${pmenu.menu_id}" data-tt-id="${pmenu.menu_id}">
								<td width="8%">${i.count}</td>
								<td width="15%"><b style="color:green">${pmenu.menu_name}</b></td>
								<td width="15%">${pmenu.menu_name}</td>
								<td width="18%">${pmenu.menu_note}</td>
								<td width="9%">
									<c:if test="${pmenu.menu_status==0}">正常</c:if>
									<c:if test="${pmenu.menu_status==1}">维护</c:if>
									<c:if test="${pmenu.menu_status==2}">过期</c:if>
								</td>
								<td width="9%">
									<c:if test="${pmenu.menu_type==0}">使用中</c:if>
									<c:if test="${pmenu.menu_type==1}">未使用</c:if>
									<c:if test="${pmenu.menu_type==2}">试用中</c:if>
								</td>
								<td width="9%"><c:if test="${pmenu.open_mode==0}">当前窗口</c:if><c:if test="${pmenu.open_mode==1}">新窗口</c:if> </td>
								<td width="11%"><input readonly="readonly" class="order" name="order_${pmenu.menu_id }" value="${i.count}" type="number"/></td>
								<td>
									<div>
										<a title="编辑菜单" target="dialog" width="800" height="755" href="${ctx}/manage/menu/goMenu?id=${pmenu.menu_id }&org_id=${org.org_id}" mask="true" class="btnEdit" rel="editmodule">编辑菜单</a>
									</div>
								</td>
							</tr>
							<c:forEach var="smenu" varStatus="j" items="${pmenu.smenu}">
								<tr class="hover" data-tt-id="${pmenu.menu_id}.${smenu.menu_id}" data-tt-parent-id="${pmenu.menu_id}">
									<td width="8%">${j.count}</td>
									<td width="15%">${smenu.menu_name}</td>
									<td width="15%">${smenu.menu_name}</td>
									<td width="18%">${smenu.menu_note}</td>
									<td width="9%">
										<c:if test="${smenu.menu_status==0}">正常</c:if>
										<c:if test="${smenu.menu_status==1}">维护</c:if>
										<c:if test="${smenu.menu_status==2}">过期</c:if>
									</td>
									<td width="9%">
										<c:if test="${smenu.menu_type==0}">使用中</c:if>
										<c:if test="${smenu.menu_type==1}">未使用</c:if>
										<c:if test="${smenu.menu_type==2}">试用中</c:if>
									</td>
									<td width="9%"><c:if test="${smenu.open_mode==0}">当前窗口</c:if><c:if test="${smenu.open_mode==1}">新窗口</c:if> </td>
									<td width="11%"><input readonly="readonly" class="order" name="order_${smenu.menu_id }" value="${j.count}" type="number"/></td>
									<td>
										<div>
											<a title="编辑菜单" target="dialog" width="800" height="755" href="${ctx}/manage/menu/goMenu?id=${smenu.menu_id }&org_id=${org.org_id}" mask="true" class="btnEdit" rel="editmodule">编辑菜单</a>
										</div>
									</td>
								</tr>	
							</c:forEach>
						</c:forEach>	
					</tbody>
				</table>
				</div>
			</div>
			<div class="formBar">
				<ul>
					<li><div class="buttonActive"><div class="buttonContent"><button onclick="sub()" type="button">保存</button></div></div></li>
					<li><div class="button"><div class="buttonContent"><button type="button" class="close">取消</button></div></div></li>
				</ul>
			</div>
	</form>
</div>

<script type='text/javascript'>

	function sub(){
		$("#form").submit();
	}

	var currentOrder = 0;
	$(document).ready(function(){
		$(".order").css("background-color","#eef4f5").css("width","80%").css("margin-left","10%");
		$(".order").dblclick(function(){
			currentOrder = $(this).val();
			$(this).removeAttr("readonly");
			$(this).css("background-color","#b8d0d6").css("width","98%").css("margin-left","1%");
		});
		$(".order").blur(function(){
			$(this).attr("readonly","readonly");
			$(this).css("background-color","#eef4f5").css("width","80%").css("margin-left","10%");
			if(''==$(this).val()||null==$(this).val()){
				$(this).val(currentOrder);
			}
		});
	});
</script>
