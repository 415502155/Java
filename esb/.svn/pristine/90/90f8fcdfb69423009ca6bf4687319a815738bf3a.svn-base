<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<% String path = request.getContextPath(); %>
<c:set var="path" value="<%=path%>"></c:set>
<div class="pageContent">
	<form id="form1" method="post" action="${ctx}/manage/menu/${action}" class="pageForm required-validate" enctype="multipart/form-data" >
		<div class="pageFormContent" style="height:666px">
			<div>
				<input type="hidden" value="${org_id }" name="org_id"/>
				<input type="hidden" value="${menu.menu_id }" name="menu_id"/>
				<input type="hidden" value="${menu.fun_code }" name="fun_code" id="fun_code"/>
				<input type="hidden" value="${menu.fun_id }" name="fun_id" id="fun_id"/>
				<input type="hidden" value="${menu.category }" name="category" id="category"/>
				<div style="clear: both; padding: 5px;">
					<label>菜单名称：</label>
					<input name="menu_name" class="required" style="width:40%" type="text" size="30" value="${menu.menu_name }"/>
				</div>
				<div class="unit">
					<label>LOGO图片：</label>
	                <img id="logo_preview" name="" src="${menu.menu_photo}" >
	                <label style="width:auto">${menu.menu_photo}</label>
	                <input id="logo_upload" name="logo_upload" type="file" />
	            </div>
				<div style="clear: both; padding: 5px;">
					<label>菜单顺序：</label>
					<input name="menu_order" class="required" style="width:40%" type="number" size="30" value="${menu.menu_order }"/>
				</div>
				<c:if test="${''!=oldName && null!=oldName }">
					<div style="clear: both; padding: 5px;">
						<label>原始名称：</label>
						<input style="width:40%" type="text" size="30" readonly="readonly" id="oldName" value="${oldName }"/>
					</div>
				</c:if>
				<div style="clear: both; padding: 5px;">
					<label>分类分组：</label>
					<select name="category_select" id="category_select" class="selector" style="width:61%">
						<c:forEach var="c" items="${category}">
							<option value="${c.value }" <c:if test="${menu.category==c.value}">selected="selected"</c:if>>${c.key}</option>
						</c:forEach>
					</select>
				</div>
				<div style="clear: both; padding: 5px;">
					<label>功能集合：</label>
					<div style="margin-left: 130px;margin-top: 10px">
						<ul>
							<c:forEach var="fun" items="${funList}">
								<li style="float:left;margin-right: 15px;margin-bottom: 10px"><a href="javascript:void(0);" onclick="funclick('${fun.fun_name}','${fun.fun_url}','${fun.fun_code}','${fun.fun_id}');">${fun.fun_name}</a></li>
							</c:forEach>
						</ul>
					</div>
				</div>
				<div style="clear: both; padding: 5px;">
					<label>上级菜单：</label>
					<input type="hidden" id="parent_id" name="parent_id" value="${menu.parent_id }"/>
					<c:choose>
						<c:when test="${action=='edit' &&(0==menu.parent_id || null==menu.parent_id)}">
							<select name="menu_id" disabled="disabled" id="menu_id" class="selector" style="width:61%">
								<option name="parent_id" value="0">已经是顶级菜单</option>
								<c:forEach var="m" items="${menus}">
									<option name="parent_id" <c:if test="${m.menu_id == menu.parent_id}">selected="selected"</c:if> value="${m.menu_id}" >${m.menu_name}</option>
								</c:forEach>	
							</select>
						</c:when>
						<c:otherwise>
							<select name="menu_id" id="menu_id" class="selector" style="width:61%">
								<option name="parent_id" value="">请选择上级菜单</option>
								<c:forEach var="m" items="${menus}">
									<option name="parent_id" <c:if test="${m.menu_id == menu.parent_id}">selected="selected"</c:if> value="${m.menu_id}" >${m.menu_name}</option>
								</c:forEach>	
							</select>
						</c:otherwise>
					</c:choose>
				</div>
				<div style="clear: both; padding: 5px;">
					<label>菜单类型：</label>
					<input id="menu_type" type="hidden" value="${menu.menu_type }"/>
					<label><input name="menu_type" value="0" type="radio" <c:if test="${menu.menu_type==0||menu.menu_type==null}">checked="true"</c:if>/>使用中</label>
					<label><input name="menu_type" value="1" type="radio" <c:if test="${menu.menu_type==1}">checked="true"</c:if>/>未使用</label>
					<label><input name="menu_type" value="2" type="radio" <c:if test="${menu.menu_type==2}">checked="true"</c:if>/>试用中</label>
				</div>
				<div style="clear: both; padding: 5px;">
					<label>当前状态：</label>
					<input id="menu_status" type="hidden" value="${menu.menu_status }"/>
					<label><input name="menu_status" value="0" type="radio" <c:if test="${menu.menu_status==0||menu.menu_status==null}">checked="true"</c:if>/>正常</label>
					<label><input name="menu_status" value="1" type="radio" <c:if test="${menu.menu_status==1}">checked="true"</c:if>/>维护</label>
					<label><input name="menu_status" value="2" type="radio" <c:if test="${menu.menu_status==2}">checked="true"</c:if>/>过期</label>
				</div>
				<div style="clear: both; padding: 5px;">
					<label>打开方式：</label>
					<input id="open_mode" type="hidden" value="${menu.open_mode }"/>
					<label><input name="open_mode" value="0" type="radio" <c:if test="${menu.open_mode==0||menu.open_mode==null}">checked="true"</c:if>/>默认</label>
					<label><input name="open_mode" value="1" type="radio" <c:if test="${menu.open_mode==1}">checked="true"</c:if>/>新窗口</label>
				</div>
				<div style="clear: both; padding: 5px;">
					<label>菜单地址：</label>
					<textarea name="menu_url" id="menu_url" rows="5" <c:if test="${action=='edit' }">readonly="readonly"</c:if> style="width:60%">${menu.menu_url }</textarea>
				</div>
				<div style="clear: both; padding: 5px;">
					<label>备注说明：</label>
					<textarea name="menu_note" rows="5" style="width:60%">${menu.menu_note }</textarea>
				</div>
			</div>
			</div>
			<div class="formBar">
				<ul>			
					<li><div class="buttonActive"><div class="buttonContent"><button onclick="toSave()" type="button">保存</button></div></div></li>
					<li><div class="button"><div class="buttonContent"><button type="button" class="close">取消</button></div></div></li>
				</ul>
			</div>
	</form>
</div>

<script type='text/javascript'>
function toSave(){
	$("#category").val($("#category_select").val());
	$("#menu_type").val($("input:radio[name=menu_type]:checked").val());
	$("#menu_status").val($("input:radio[name=menu_status]:checked").val());
	$("#open_mode").val($("input:radio[name=open_mode]:checked").val());
	$("#parent_id").val($("#menu_id").val());
	$("#form1").submit();
}

function funclick(funname,funurl,funcode,funid){
	$("#oldName").val(funname);
	$("#menu_url").val(funurl);
	$("#fun_code").val(funcode);
	$("#fun_id").val(funid);
 
}

$("#form1").submit(function(){
	$("#category").val($("#category_select").val());
	return iframeCallback(this,function(data){
		console.info(data.statusCode==200);
		if(data.statusCode==200){
			$.pdialog.closeCurrent()
			alertMsg.correct("修改成功！");
			$("#"+toOrg).click();
		}else{
			alertMsg.warn("保存失败！");
		}
	}); 
});
</script>
